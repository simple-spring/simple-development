#### 自定义权限注解
    通过aop拦截service方法实现访问控制
#### 参数说明
|参数 | 说明 |
| --- | --- |
| value | 权限key(自己业务定义)|
| LOGICAL | 权限key的逻辑关系 并且/或|
#### 实现类
    需要自己实现HasPermissionService接口
#### 代码示例
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
    @HasPermissions(value = {"updateData"})
    public void updateData(DemoVo demoVo) {

    }
    @Override
    @HasPermissions(value = {"insertData","getData"},LOGICAL = Logical.And)
    public void insertData(DemoVo demoVo) {

    }
    @Override
    @HasPermissions(value = {"insertData","getData"},LOGICAL = Logical.Or)
    public DemoVo getData() {
        return null;
    }
}
```