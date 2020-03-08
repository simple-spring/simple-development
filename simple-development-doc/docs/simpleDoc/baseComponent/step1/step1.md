#### 1.IsApiService作用
    IsApiService是将service层的类+方法注册成到spring mvc mapping中去,并通过统一的入口访问
#### 2.统一访问地址
- 1. ip地址:端口/服务名/data/api/v1/接口名/方法名     需要登录(统一登录的controller方法上没有@noLogin)
- 2. ip地址:端口/服务名/data/config/v1/接口名/方法名  不需要登录(统一登录的controller方法上有@noLogin)
#### 3.功能清单
 注解 | 说明 |
| --- | --- |
| @IsApiService | 标注在service层,并注入到spring bean 工厂 |
| @NoApiMethod | 表示标注的方法不注入到spring mvc mapping|
| @IsApiMethodService | 表示标注的方法注入到spring mvc mapping(默认注入到spring mvc mapping) |
#### 4.使用场景
```java
@IsApiService
public class DemoBoImpl extends AbstractLavaBoImpl<DemoDo, DemoDoMapperExt, DemoDoExample> implements DemoBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DemoDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    @IsApiService
    public void insertData(DemoVo demoVo) {
    }

    @Override
    public DemoVo getData() {
        return null;
    }
}
```
#### 5.详细参数
##### 1.@IsApiService
|注解 | 说明 |
| --- | --- |
| value | 服务名,默认接口名 |
| isLogin | 是否需要登录(默认需要登录)|
##### 2.@NoApiMethod
|注解 | 说明 |
| --- | --- |
| 无 | 无 |
##### 3.@IsApiMethodService
|注解 | 说明 |
| --- | --- |
| value | 修改注入到spring mvc mapping的方法名 |

