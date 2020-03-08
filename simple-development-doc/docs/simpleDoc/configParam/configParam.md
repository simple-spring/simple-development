#### application.properties所有的参数说明
参数名 | 默认值样例 | 说明|
| --- |     --- |     --- |
|spring.simple.datasource.driverClassName|com.mysql.jdbc.Driver|数据库驱动|
|spring.simple.datasource.url|jdbc:mysql://172.22.5.239:3306/demo|mysql连接地址|
|spring.simple.datasource.username|root|mysql账号|
|spring.simple.datasource.password|123456|mysql密码|
|spring.simple.datasource.initialSize|20|mysql初始化连接数|
|spring.simple.datasource.minIdle|20|mysql允许的最小连接数|
|spring.simple.datasource.maxActive|150|mysql允许的最大连接数|
|spring.simple.datasource.maxWait|3000|连接超时时间|
|spring.simple.datasource.timeBetweenEvictionRunsMillis|3000|连接空闲回收每次执行时间|
|spring.simple.datasource.minEvictableIdleTimeMillis|300000|池中的连接空闲30分钟后被回收|
|spring.simple.datasource.validationQuery|SELECT 1 FROM DUAL|验证使用的SQL语句|
|spring.simple.datasource.testWhileIdle|true|指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除|
|spring.simple.datasource.testOnBorrow|false|借出连接时不要测试，否则很影响性能|
|spring.simple.datasource.testOnReturn|false|连接归还连接池的时候检测|
|spring.simple.redisHost|172.22.5.252|redis地址 目前单节点|
|spring.simple.redisPort|6379|redis端口|
|spring.simple.redisPwd|123456|redis连接密码|
|spring.simple.maxTotal|7000|redis最大连接数|
|spring.simple.maxWaitMillis|3000|redis连接超时时间|
|spring.simple.maxIdle|10|reids最小连接数|
|spring.simple.timeout|3000|reids连接超时时间|
|spring.simple.dubbo.application.name|simple-developmen|dubbp应用名|
|spring.simple.dubbo.registry.address|zookeeper://172.22.5.214:2181|dubbo-zookepper连接地址|
|spring.simple.dubbo.protocol.name|dubbo|dubbo默认协议|
|spring.simple.dubbo.protocol.port|20880|dubbo通信端口|
|spring.simple.elasticsearch.host|172.22.5.214|es服务地址 目前单节点
|spring.simple.elasticsearch.port|9300|es java client端口  服务端口为9200
|spring.simple.elasticsearch.cluster.name|elasticsearch|es集群名|
