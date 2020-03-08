### 文档swagger组件
    简化wagger组件的配置,通过注解@EnableSwagger选择是否启动
### @EnableSwagger参数说明
|注解 | 说明 |
| --- | --- |
| title | 文档标题|
| description | 文档描述|
| contact | 文档作者|
| version | 文档版本|
| url | 项目地址|
| headerParam | header参数数组 默认一个参数：token1:tokenData1,token2:tokenData2,token3:tokenData3 默认参数为token|
| headerParamDescription | header 参数描述: Description1,Description2,Description3 默认为:123456789
### @EnableSwagger在application.properties中的配置
```properties
#是否启动swagger 默认启动为true,关闭为false
spring.simple.swagger.is_enable=true
```
### pom依赖
```xml
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core-swagger</artifactId>
            <version>1.0</version>
        </dependency>
```

### 启动代码样例
```java
@EnableSwagger
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}
```
### 标记文档注解
  应用场景在server层,配合IsApiService组件使用
### 标记文档注解
|注解 | 说明 |
| --- | --- |
| @Api | 文档分类名称,标记在类上有@IsApiService注解的类上|
| @ApiOperation | 接口描述,标记在类上有@IsApiService注解的方法上|
| @ApiImplicitParam | 返回值类型描述,标记在类上有@IsApiService注解的方法上|

#### 1.@Api注解参数说明
|参数 | 说明 |
| --- | --- |
| tags | 描述文档分类|

#### 2.@ApiOperation注解参数说明
|参数 | 说明 |
| --- | --- |
| value | 接口title|
| notes | 接口描述|

#### 3.@ApiImplicitParam注解参数说明
|注解 | 说明 |
| --- | --- |
| name | 参数名|
| description | 参数的说明、解释|
| required | 参数是否必须传|
| paramType | 描述文档分类|
| dataType |  请求参数的类型,默认body|
| resultDataType | 参数类型 默认String|

### 标记文档注解代码样例
```java
@IsApiService
@Api(tags = "订单相关")
public class DemoBoImpl extends AbstractLavaBoImpl<DemoDo, DemoDoMapperExt, DemoDoExample> implements DemoBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DemoDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    @ApiOperation(value = "插入", notes = "插入一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo")
    public void insertData(DemoVo demoVo) {
        DemoDo demoDo = baseSupport.objectCopy(demoVo, DemoDo.class);
        insert(demoDo);
    }

    @Override
    @ApiOperation(value = "删除", notes = "删除一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo")
    public void deleteData(DemoVo demoVo) {
        DemoDo demoDo = baseSupport.objectCopy(demoVo, DemoDo.class);
        delete(demoDo.getId());
    }

    @Override
    @ApiOperation(value = "修改", notes = "修改一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo")
    public void updateData(DemoVo demoVo) {
        DemoDo demoDo = baseSupport.objectCopy(demoVo, DemoDo.class);
        update(demoDo);
    }
    @Override
    @ApiOperation(value = "查询", notes = "查询一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo", resultDataType = DemoVo.class)
    public DemoVo getData() {
        DemoDo demoDo = selectByPrimaryKey(1L);
        return baseSupport.objectCopy(demoDo, DemoVo.class);
    }
}
```