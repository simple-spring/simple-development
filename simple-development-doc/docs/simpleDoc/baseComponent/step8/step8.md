#### 服务启动组件
    使用tomcat8.0作为框架启动的容器,通过SimpleApplication.run()作为入口启动
#### tomcat组件在application.properties中的配置

|参数 | 说明 |
| --- | --- |
| server.port | 服务启动的端口号默认8000,配置在application.properties里面 |
| server.path | 服务启动项目路径,默认为 / ,配置在application.properties里面|

#### 配置样例
```properties
server.port = 8000
server.path = demo
```
- 访问的路径为： IP:8000/demo/统一的访问地址(/data/api/v1)/接口名/方法名
-  请求的数据格式：application/json
