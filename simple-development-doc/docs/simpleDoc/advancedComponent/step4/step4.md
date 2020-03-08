### redis组件
    简化redis的配置,通过注解@EnableRedis选择是否启动
### @EnableRedis参数说明
|注解 | 说明 |
| --- | --- |
| 无 | 无 |
### @EnableRedis在application.properties中的配置
```properties
#redis
spring.simple.redisHost=172.22.5.252
spring.simple.redisPort=6379
spring.simple.redisPwd=123456
## 最大连接数
spring.simple.maxTotal=7000
##最大阻塞时间
spring.simple.maxWaitMillis=3000
##空闲连接
spring.simple.maxIdle=10
##连接超时时间
spring.simple.timeout=3000
```
### pom依赖
```xml
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core-redis</artifactId>
            <version>1.0</version>
        </dependency>
```
### 代码样例
```java
@EnableRedis
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}
```
