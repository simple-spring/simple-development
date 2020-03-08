#### 参数校验框架Valid
##### 1.使用技术
    借助hibernate-validator 框架来校验参数
##### 2.增加依赖
```xml
    <dependency>
        <groupId>com.spring</groupId>
        <artifactId>simple-development-core-valid</artifactId>
        <version>1.0</version>
    </dependency>
```
##### 3.使用场景
    应用于service层方法上
##### 4.参数说明
|参数 | 说明 |
| --- | --- |
| key | 参数key,与方法参数对应|
| value | 方法参数的类型|
| isPage | 是否是分页|
| isReqBody | 是否是统一参数|
##### 5.代码样例
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
    @ValidHandler(key = "demoVo",value = DemoVo.class,isReqBody = false)
    public void insertData(DemoVo demoVo) {

    }
}
```
```java
@Data
public class DemoVo {
    @NotBlank(message = "name cannot be empty")
    private String name;
    @NotBlank(message = "password cannot be empty")
    private String password;
}
```