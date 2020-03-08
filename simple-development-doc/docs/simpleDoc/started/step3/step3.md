
### application.properties的参数说明
```properties
#数据源基础信息
spring.simple.datasource.driverClassName=com.mysql.jdbc.Driver
spring.simple.datasource.url=jdbc:mysql://172.22.5.243:3306/demo
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
