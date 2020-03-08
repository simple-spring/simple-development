#### app.java
``` java
@EnableMybatis
@EnableWebMvc
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}
```
#### 说明
 参数 | 说明 |
| --- | --- |
| EnableMybatis | mybatis组件 |
| EnableWebMvc | spring mvc组件|
| SpringSimpleApplication | spring 容器 |
| SimpleApplication| tomcat启动类 |
