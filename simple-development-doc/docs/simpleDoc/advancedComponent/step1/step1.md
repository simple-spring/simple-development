### 基础组件
    @SpringSimpleApplication是本框架的基础组件,所以的组件基于这个组件启动,内部是一个spring的容器
### @SpringSimpleApplication参数说明
|注解 | 说明 |
| --- | --- |
| applicationName | 应用名,容器的唯一标识 |
| appPackagePathName | 容器初始化扫描bean的包路径,默认是启动类的包路径|
### pom依赖
```xml
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core</artifactId>
            <version>2.3</version>
        </dependency>
```
### 代码样例
```java
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}
```
