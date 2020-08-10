### spring simple2.4


### 实现
    基于ssm javaconfig 零xml配置，基于javassist,cglib,Reflections技术实现。
### 快速开发
    常用的三层结构dao层简化开发，service简化开发,controller层零开发。
### 简单启动
    main方法启动
   ```java
/**
 * @Description 程序启动
 **/
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}
```

### 提供开发过程中基础场景实现：
> 1. 参数校验
> 2. 统一异常处理
> 3. 接口幂等
> 4. 动态数据源
> 5. 对象深度复制
> 6. 方法映射api接口
> 7. 权限控制
> 8. 用户登录注册组件
### 丰富高级场景实现
     提供基础场景实现和中间件丰富场景实现,如redis,dubbo,kafka,job,elasticsearch,cas,shiro
### 辅助工具
    swagger ,告警组件alert实现,调用链路zipkin监控。

   

