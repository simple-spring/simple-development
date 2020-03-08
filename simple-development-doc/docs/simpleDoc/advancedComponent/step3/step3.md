### mybatis组件
    简化spring-mybatis的配置,通过注解@EnableMybatis选择是否启动
### @EnableMybatis参数说明
|注解 | 说明 |
| --- | --- |
| mapperPath | mapper包路径,默认启动类的下级包：mapper |
| modelPath | model包路径,默认启动类的下级包：model|
| mapperXmlPath | mybatis xml路径,默认resource下的文件：mybatis|
| expression | 事务的表达式,默认对service层方法进行事务拦截,以方法add,save,insert,delete,update,exec,set默认有读写事务,以get,query,find,list,count,iss是只读事务，默认是读写事务|
### pom依赖
```xml
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core-jdbc</artifactId>
            <version>1.0</version>
        </dependency>
```
### @EnableMybatis在application.properties中的配置
```properties
#数据源基础信息
spring.simple.datasource.driverClassName=com.mysql.jdbc.Driver
spring.simple.datasource.url=jdbc:mysql://172.22.5.243:3306/insurance?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&autoReconnect=true&serverTimezone=CTT
spring.simple.datasource.username=root
spring.simple.datasource.password=123456
#连接池配置
spring.simple.datasource.initialSize=20
spring.simple.datasource.minIdle=20
spring.simple.datasource.maxActive=150
#连接等待超时时间
spring.simple.datasource.maxWait=3000
#配置隔多久进行一次检测(检测可以关闭的空闲连接)
spring.simple.datasource.timeBetweenEvictionRunsMillis=3000
#配置连接在池中的最小生存时间
spring.simple.datasource.minEvictableIdleTimeMillis=300000
spring.simple.datasource.validationQuery=SELECT 1 FROM DUAL
spring.simple.datasource.testWhileIdle=true
spring.simple.datasource.testOnBorrow=false
spring.simple.datasource.testOnReturn=false
```

参数的详细配置请参考详细配置说明
### 代码样例
```java
@EnableMybatis
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}
```
