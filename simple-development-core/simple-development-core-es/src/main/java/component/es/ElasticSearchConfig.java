package component.es;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;

/**
 * @author ys
 * @Date 2019年12月27日
 * @Description TODO
 */
@Configuration
public class ElasticSearchConfig {

	/**
	 * 地址
	 */
	private String host = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ELASTICSEARCH_HOST);
	
	/**
	 * 端口
	 */
	private Integer port = Integer.valueOf(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ELASTICSEARCH_PORT));
	
	/**
	 * 集群
	 */
	private String clusterName = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ELASTICSEARCH_CLUSTER_NAME);
	
	private TransportClient transportClient;
	
	public ElasticSearchConfig() {
		System.out.println("elasticsearch initialized...");
		Assert.notNull(host, "elasticsearch host is empty");
		Assert.notNull(port, "elasticsearch port is empty");
	}
	
	@Bean
    public TransportClient transportClient(){
        Settings settings = Settings.EMPTY;
        if(StringUtils.isNotEmpty(clusterName)){
            settings = Settings.builder().put("cluster.name", clusterName).build();
        }
        try {
            transportClient = new PreBuiltTransportClient(settings).addTransportAddress(
            		new TransportAddress(InetAddress.getByName(host), port));
        } catch (UnknownHostException e) {
            System.out.println("创建elasticsearch客户端失败");
            e.printStackTrace();
        }
        System.out.println("创建elasticsearch客户端成功");
        return transportClient;
    }

    @Bean
    public BulkProcessor bulkProcessor() throws UnknownHostException {

        Settings settings = Settings.EMPTY;
        if(StringUtils.isNotEmpty(clusterName)){
            settings = Settings.builder().put("cluster.name", clusterName).build();
        }

        TransportClient transportClient = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress
                (InetAddress.getByName(host), port));
                

        return BulkProcessor.builder(transportClient, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {

            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {

            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                System.out.println((bulkRequest.numberOfActions() + " data bulk failed"));
            }

        }).setBulkActions(1000)//分批，每10000条请求当成一批请求。默认值为1000
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))//每次5MB，刷新一次bulk。默认为5m
                .setFlushInterval(TimeValue.timeValueSeconds(5))//每5秒一定执行，不管已经队列积累了多少。默认不设置这个值
                .setConcurrentRequests(1)//设置并发请求数，如果是0，那表示只有一个请求就可以被执行，如果为1，则可以积累并被执行。默认为1.
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))//这里有个backoff策略，最初等待100ms,然后按照指数增长并重试3次。每当一个或者多个bulk请求失败,并出现EsRejectedExecutionException异常时.就会尝试重试。这个异常表示用于处理请求的可用计算资源太少。如果要禁用这个backoff策略，需要用backoff.nobackoff()。
                .build();
    }

    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
