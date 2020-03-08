
### application.properties的参数说明
1.mybatis的配置参数
参数名 | 说明|
| --- |     --- |     --- |
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
|spring.simple.datasource.testOnBorrow|借出连接时不要测试，否则很影响性能|
|spring.simple.datasource.testOnReturn|连接归还连接池的时候检测|
