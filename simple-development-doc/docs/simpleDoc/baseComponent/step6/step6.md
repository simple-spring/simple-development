#### 动态切换数据源
    通过aop实现对多个数据源切换
#### 参数说明
|参数 | 说明 |
| --- | --- |
| value | 数据源标识(目前实现了主从),可标记在类和方法上,但方法上的优先级更高|

#### 数据源
- 主库： masterDataSource
- 从库： slaveDataSource

#### 代码示例
```java
@IsApiService
@DataSource(DynamicDataSource.master)
public class DemoBoImpl extends AbstractLavaBoImpl<DemoDo, DemoDoMapperExt, DemoDoExample> implements DemoBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DemoDoMapperExt mapper) {
        setMapper(mapper);
    }
    @Override
    @DataSource(DynamicDataSource.master)
    public void insertData(DemoVo demoVo) {

    }
    @Override
    @DataSource(DynamicDataSource.slave)
    public DemoVo getData() {
        return null;
    }
}
```

