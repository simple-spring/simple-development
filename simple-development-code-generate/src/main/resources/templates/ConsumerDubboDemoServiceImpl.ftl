package ${packagePath}.dubbo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import ${packagePath}.dubbo.provider.ProviderDubboDemoService;
import org.springframework.stereotype.Service;

@Service
public class ConsumerDubboDemoServiceImpl implements ConsumerDubboDemoService {

    @Reference
    private ProviderDubboDemoService providerDubboDemoService;

    @Override
    public void consumerMethod() {
        System.out.println(providerDubboDemoService.sayHello());
    }
}
