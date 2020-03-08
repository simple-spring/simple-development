### elasticsearch组件
    简化elasticsearch组件的配置,通过注解@EnableElasticSearch选择是否启动
### @EnableElasticSearch参数说明
|注解 | 说明 |
| --- | --- |
| 无 | 无 |
### @EnableElasticSearch在application.properties中的配置
```properties
#elastic search
#elasticsearch地址
spring.simple.elasticsearch.host=172.22.5.214
#elasticsearch端口
spring.simple.elasticsearch.port=9300
#elasticsearch集群
spring.simple.elasticsearch.cluster.name=elasticsearch
```
### pom依赖
```xml
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core-es</artifactId>
            <version>1.0</version>
        </dependency>
```
### 代码样例
```java
@EnableElasticSearch
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}
```
