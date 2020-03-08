### spring mvc组件
    简化spring mvc的配置,通过注解@EnableWebMvc选择是否启动
### @EnableWebMvc参数说明
|注解 | 说明 |
| --- | --- |
| urlPath | 项目过滤的地址,默认 / |
| packagePath | controller层包路径,默认启动类的下级包：controller|
| interceptorPackagePath | 拦截器扫描的包名,默认启动类的下级包：interceptor|
### pom依赖
```xml
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core-mvc</artifactId>
            <version>1.0</version>
        </dependency>
```
### 代码样例
```java
@EnableWebMvc
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}
```
