#### 自定义属性注解value
    用于读取application.properties中的配置
#### 参数说明
|参数 | 说明 |
| --- | --- |
| value | 对应application.properties中的key|
#### 代码样例
```java
@IsApiService
public class DemoBoImpl extends AbstractLavaBoImpl<DemoDo, DemoDoMapperExt, DemoDoExample> implements DemoBo {
    @Value("spring.simple.datasource.username")
    private String userName;

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DemoDoMapperExt mapper) {
        setMapper(mapper);
    }
}
```