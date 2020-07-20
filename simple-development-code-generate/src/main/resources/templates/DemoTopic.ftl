package ${packagePath}.kafka;

import com.spring.simple.development.core.annotation.base.SimpleTopic;
import com.spring.simple.development.core.component.kafka.service.SpringSimpleKafkaService;

/**
 * @author liko.wang
 * @Date 2020/4/3/003 9:08
 * @Description 消费kafka消息
 **/
@SimpleTopic(value = "demoTopic", groupName = "demoTopicGroup")
public class DemoTopic implements SpringSimpleKafkaService {

    @Override
    public void getConsumerMessage(String message) {
        System.out.println(message);
    }
}