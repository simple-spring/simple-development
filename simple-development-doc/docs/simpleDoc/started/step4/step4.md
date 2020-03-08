#### 1.环境隔离配置(默认dev)
```
<profiles>
        <profile>
            <id>dev</id>
            <properties>
                <package.environment>dev</package.environment>
            </properties>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
        </profile>
        <profile>
            <id>net</id>
            <properties>
                <package.environment>net</package.environment>
            </properties>
        </profile>
        <profile>
            <id>test</id>
            <properties>
                <package.environment>test</package.environment>
            </properties>
        </profile>
</profiles>
```
#### 2.打包指定环境
- 1.开发环境  mvn clean install -Pdev
- 2.测试环境  mvn clean install -Ptest
- 3.生产环境  mvn clean install -Pnet
- 4.跳过测试  mvn clean install -Pdev -Dmaven.test.skip=true 