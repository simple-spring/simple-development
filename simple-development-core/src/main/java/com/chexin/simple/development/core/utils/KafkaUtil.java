package com.chexin.simple.development.core.utils;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * desc:    KafkaUtil工具类
 * author:  chenjia
 * date:    2017-06-25 18:02
 */
public class KafkaUtil {
    private static final Logger logger = LogManager.getLogger(KafkaUtil.class);
    private static List<String> m_replicaBrokers = new ArrayList<String>();

    public KafkaUtil() {
        m_replicaBrokers = new ArrayList<String>();
    }

    public static SimpleConsumer getSimpleConsumer(String a_topic, int a_partition, String KAFKA_SERVERS) {
        SimpleConsumer consumer = null;
        try {
            PartitionMetadata metadata = findLeader(KAFKA_SERVERS, a_topic, a_partition);
            if (metadata == null) {
                //System.out.println("Can't find metadata for Topic and Partition. Exiting");
                return null;
            }
            if (metadata.leader() == null) {
                //System.out.println("Can't find Leader for Topic and Partition. Exiting");
                return null;
            }
            String leadBrokerIp = metadata.leader().host();
            int leadBrokerPort = metadata.leader().port();
            String clientName = "Client_" + a_topic + "_" + a_partition;

            consumer = new SimpleConsumer(leadBrokerIp, leadBrokerPort, 100000, 64 * 1024, clientName);
            long readOffset = getLastOffset(consumer, a_topic, a_partition, kafka.api.OffsetRequest.EarliestTime());
            int numErrors = 0;
            do {
                if (consumer == null) {
                    consumer = new SimpleConsumer(leadBrokerIp, leadBrokerPort, 100000, 64 * 1024, clientName);
                }
                FetchRequest req = new FetchRequestBuilder().clientId(clientName).addFetch(a_topic, a_partition, readOffset, 100000).build();
                FetchResponse fetchResponse = consumer.fetch(req);
                if (fetchResponse.hasError()) {
                    numErrors++;
                    // Something went wrong!
                    short code = fetchResponse.errorCode(a_topic, a_partition);
                    System.out.println("Error fetching data from the Broker:" + leadBrokerIp + " Reason: " + code);
                    if (numErrors > 5) {
                        break;
                    }
                    if (code == ErrorMapping.OffsetOutOfRangeCode()) {
                        // We asked for an invalid offset. For simple case ask for
                        // the last element to reset
                        readOffset = getLastOffset(consumer, a_topic, a_partition, kafka.api.OffsetRequest.LatestTime());
                        continue;
                    }
                    consumer.close();
                    consumer = null;
                    leadBrokerIp = findNewLeader(KAFKA_SERVERS, leadBrokerIp, a_topic, a_partition, leadBrokerPort);
                    continue;
                }
                numErrors = 0;
            } while (numErrors >= 1 && numErrors <= 5);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.info("KafkaUtil.getSimpleConsumer 90 line " + e.toString());
        }
        return consumer;

    }

    /**
     * @param a_oldLeader
     * @param a_topic
     * @param a_partition
     * @param a_port
     * @return String
     * @throws Exception 找一个leader broker
     */
    private static String findNewLeader(String KAFKA_SERVERS, String a_oldLeader, String a_topic, int a_partition, int a_port) throws Exception {
        for (int i = 0; i < 3; i++) {
            boolean goToSleep = false;
            PartitionMetadata metadata = findLeader(KAFKA_SERVERS, a_topic, a_partition);
            if (metadata == null) {
                goToSleep = true;
            } else if (metadata.leader() == null) {
                goToSleep = true;
            } else if (a_oldLeader.equalsIgnoreCase(metadata.leader().host()) && i == 0) {
                // first time through if the leader hasn't changed give
                // ZooKeeper a second to recover
                // second time, assume the broker did recover before failover,
                // or it was a non-Broker issue
                //
                goToSleep = true;
            } else {
                return metadata.leader().host();
            }
            if (goToSleep) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        }
        System.out.println("Unable to find new leader after Broker failure. Exiting");
        throw new Exception("Unable to find new leader after Broker failure. Exiting");
    }

    public static Long getLastOffset(SimpleConsumer consumer, String topicName, int partitionId, long offsetRequestTime) {
        Long logSize = null;
        try {
            TopicAndPartition topicAndPartition = new TopicAndPartition(topicName, partitionId);
            Map<TopicAndPartition, PartitionOffsetRequestInfo> map = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
            map.put(topicAndPartition, new PartitionOffsetRequestInfo(offsetRequestTime, 1));
            OffsetRequest request = new OffsetRequest(map, kafka.api.OffsetRequest.CurrentVersion(), kafka.api.OffsetRequest.DefaultClientId());
            OffsetResponse response = consumer.getOffsetsBefore(request);
            long[] aa = response.offsets(topicName, partitionId);
            if (aa.length != 0) {
                logSize = aa[0];
            }
        } catch (Exception e) {
            //e.printStackTrace();
            //logger.info("KafkaUtil "+e);
        } finally {
            if (consumer != null) {
                consumer.close();
                consumer = null;
            }
        }
        return logSize;
    }

    private static PartitionMetadata findLeader(String KAFKA_SERVERS, String a_topic, int a_partition) throws Exception {
        PartitionMetadata returnMetaData = null;
        loop:
        for (String ip_port : KAFKA_SERVERS.split("-")) {
            SimpleConsumer consumer = null;
            try {
                consumer = new SimpleConsumer(ip_port.split(":")[0], Integer.valueOf(ip_port.split(":")[1]), 100000, 64 * 1024, "leaderLookup");
                List<String> topics = Collections.singletonList(a_topic);
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                TopicMetadataResponse resp = consumer.send(req);

                List<TopicMetadata> metaData = resp.topicsMetadata();
                for (TopicMetadata item : metaData) {
                    for (PartitionMetadata part : item.partitionsMetadata()) {
                        if (part.partitionId() == a_partition) {
                            returnMetaData = part;
                            break loop;
                        }
                    }
                }
            } catch (Exception e) {
                // System.out.println("Error communicating with Broker [" + ip_port + "] to find Leader for [" + a_topic + ", " + a_partition + "] Reason: " + e);
                //logger.info("KafkaUtil "+e);
            } finally {
                if (consumer != null) {
                    consumer.close();
                }
            }
        }
        if (returnMetaData != null) {
            m_replicaBrokers.clear();
            for (kafka.cluster.BrokerEndPoint replica : returnMetaData.replicas()) {
                m_replicaBrokers.add(replica.host());
            }
        }
        return returnMetaData;
    }
}
