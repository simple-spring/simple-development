### dubbo组件
    简化dubbo组件的配置,通过注解@EnableDubbo选择是否启动
### @EnableDubbo参数说明
|注解 | 说明 |
| --- | --- |
| dubboPackage | dubbo扫描包路径,默认启动类下级包名：dubbo |
### @EnableDubbo在application.properties中的配置

```properties
#dubbo 消费者和调用者都是这个配置 通过dubbo@service和@Reference来确定调用关系
spring.simple.dubbo.application.name=simple-developmen
spring.simple.dubbo.registry.address=zookeeper://172.22.5.214:2181
spring.simple.dubbo.protocol.name=dubbo
spring.simple.dubbo.protocol.port=20880
```
### pom依赖
```xml
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core-dubbo</artifactId>
            <version>1.0</version>
        </dependency>
```

### 代码样例
```java
@EnableDubbo
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}
```
