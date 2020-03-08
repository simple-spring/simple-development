### job组件
    简化job的配置,通过注解@EnableXxlJob选择是否启动
### @EnableXxlJob参数说明
|注解 | 说明 |
| --- | --- |
| jobPackage | 指定任务Handler所在包路径,默认启动类的下级包：jobhandler |
| logPath | 执行器日志路径,默认:/data/logs/xxl-job |
| logRetentionDays | 日志保存天数,默认:永久 -1 |
| accessToken | 访问令牌 |
### @EnableXxlJob在application.properties中的配置
```properties
#xxl job
#执行器注册中心地址
spring.simple.xxl.job.addresses=http://172.22.5.200:9999
#执行器AppName
spring.simple.xxljob.appName=simple-demo-executor
#执行器Ip
spring.simple.xxljob.ip=172.22.5.200
#执行器端口号
spring.simple.xxljob.port=9997
```
### pom依赖
```xml
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core-job</artifactId>
            <version>1.0</version>
        </dependency>
```
### 代码样例
```java
@EnableXxlJob
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}
```
