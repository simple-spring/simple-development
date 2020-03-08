### application.properties所有的参数说明

#### mybatis组件参数
##### 1.主库
参数名 |  说明|
| --- |      --- |
|spring.simple.datasource.driverClassName|数据库驱动|
|spring.simple.datasource.url|mysql连接地址|
|spring.simple.datasource.username|mysql账号|
|spring.simple.datasource.password|mysql密码|
|spring.simple.datasource.initialSize|mysql初始化连接数|
|spring.simple.datasource.minIdle|mysql允许的最小连接数|
|spring.simple.datasource.maxActive|mysql允许的最大连接数|
|spring.simple.datasource.maxWait|连接超时时间|
|spring.simple.datasource.timeBetweenEvictionRunsMillis|连接空闲回收每次执行时间|
|spring.simple.datasource.minEvictableIdleTimeMillis|池中的连接空闲30分钟后被回收|
|spring.simple.datasource.validationQuery|验证使用的SQL语句|
|spring.simple.datasource.testWhileIdle|指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除|
|spring.simple.datasource.testOnBorrow|是否检测池里连接的可用性,借出连接时不要测试，否则很影响性能|
|spring.simple.datasource.testOnReturn|连接归还连接池的时候检测|
##### 2.从库
参数名 |  说明|
| --- |      --- |
|spring.simple.is.open.slave.datasource|是否打开从库|
|spring.simple.slave.datasource.driverClassName|数据库驱动|
|spring.simple.slave.datasource.url|mysql连接地址|
|spring.simple.slave.datasource.username|mysql账号|
|spring.simple.slave.datasource.password|mysql密码|
|spring.simple.slave.datasource.initialSize|mysql初始化连接数|
|spring.simple.slave.datasource.minIdle|mysql允许的最小连接数|
|spring.simple.slave.datasource.maxActive|mysql允许的最大连接数|
|spring.simple.slave.datasource.maxWait|连接超时时间|
|spring.simple.slave.datasource.timeBetweenEvictionRunsMillis|连接空闲回收每次执行时间|
|spring.simple.slave.datasource.minEvictableIdleTimeMillis|池中的连接空闲30分钟后被回收|
|spring.simple.slave.datasource.validationQuery|验证使用的SQL语句|
|spring.simple.slave.datasource.testWhileIdle|指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除|
|spring.simple.slave.datasource.testOnBorrow|是否检测池里连接的可用性,借出连接时不要测试，否则很影响性能|
|spring.simple.slave.datasource.testOnReturn|连接归还连接池的时候检测|

#### redis参数配置
参数名 |  说明|
| --- |      --- |
|spring.simple.redisHost|redis地址 目前单节点|
|spring.simple.redisPort|redis端口|
|spring.simple.redisPwd|redis连接密码|
|spring.simple.maxTotal|redis最大连接数|
|spring.simple.maxWaitMillis|redis连接超时时间|
|spring.simple.maxIdle|reids最小连接数|
|spring.simple.timeout|reids连接超时时间|
#### dubbo参数配置
参数名 |  说明|
| --- |      --- |
|spring.simple.dubbo.application.name|dubbp应用名|
|spring.simple.dubbo.registry.address|dubbo-zookepper连接地址|
|spring.simple.dubbo.protocol.name|dubbo默认协议|
|spring.simple.dubbo.protocol.port|dubbo通信端口|

#### elasticsearch参数配置
参数名 |  说明|
| --- |      --- |
|spring.simple.elasticsearch.host|es服务地址 目前单节点
|spring.simple.elasticsearch.port|es java client端口  服务端口为9200
|spring.simple.elasticsearch.cluster.name|es集群名|
#### swagger参数配置
参数名 |  说明|
| --- |      --- |
|spring.simple.swagger.is_enable|swagger是否开启|

#### tomcat参数配置
参数名 |  说明|
| --- |      --- |
|server.port|服务端口|
|server.path|服务项目路径|