#### 防重复提交
    对接口的重复访问做校验,通过aop+redis分布式锁实现
#### 需增加依赖
```xml
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core-redis</artifactId>
            <version>1.0</version>
        </dependency>
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core-idempotent</artifactId>
            <version>1.0</version>
        </dependency>
```
#### 参数说明
|参数 | 说明 |
| --- | --- |
| value | 方法级别控制,一般写方法名,只要唯一即可|

#### 代码样例
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
    @Idempotent(value = "insertData")
    public void insertData(DemoVo demoVo) {

    }
    @Override
    @Idempotent(value = "getData")
    public DemoVo getData() {
        return null;
    }
}
```
