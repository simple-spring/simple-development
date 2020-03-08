#### jar包
- 1.在build节点增加
- 2.设置启动类app.java的路径
- 3.jar只能在打包的当前文件夹启动
```xml
 <plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <configuration>
            <classesDirectory>target/classes/</classesDirectory>
            <archive>
                <manifest>
                    <!--设置启动类的路径 -->
                    <mainClass>com.spring.simple.development.demo.App</mainClass>
                    <!-- 打包时 MANIFEST.MF文件不记录的时间戳版本 -->
                    <useUniqueVersions>false</useUniqueVersions>
                    <addClasspath>true</addClasspath>
                    <classpathPrefix>lib/</classpathPrefix>
                </manifest>
                <manifestEntries>
                    <Class-Path>.</Class-Path>
                </manifestEntries>
            </archive>
        </configuration>
    </plugin>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-dependency-plugin</artifactId>
        <executions>
            <execution>
                <id>copy-dependencies</id>
                <phase>package</phase>
                <goals>
                    <goal>copy-dependencies</goal>
                </goals>
                <configuration>
                    <type>jar</type>
                    <includeTypes>jar</includeTypes>
                    <outputDirectory>
                        ${project.build.directory}/lib
                    </outputDirectory>
                </configuration>
            </execution>
        </executions>
    </plugin>
</plugins>
```
#### zip包
- 1.在build节点下plugins节点增加
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <configuration>
        <descriptors>
            <descriptor>src/main/resources/assembly.xml</descriptor>
        </descriptors>
    </configuration>
    <executions>
        <execution>
            <id>make-assembly</id>
            <phase>package</phase>
            <goals>
                <goal>single</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
- 2.配置resource下的assembly.xml
```xml
<assembly
	xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2 http://maven.apache.org/xsd/assembly-1.1.2.xsd">
	<id>bin</id>
	<formats>
		<format>zip</format>
	</formats>

	<dependencySets>
		<dependencySet>
			<useProjectArtifact>false</useProjectArtifact>
			<outputDirectory>lib</outputDirectory>
			<unpack>false</unpack>
		</dependencySet>
	</dependencySets>
	
	<fileSets>
		<fileSet>
			<directory>${project.basedir}</directory>
			<outputDirectory>/</outputDirectory>
			<includes>
				<include>LICENSE*</include>
				<include>NOTICE*</include>
			</includes>
		</fileSet>

		<fileSet>
			<directory>${project.basedir}\src\main\resources\scripts</directory>
			<outputDirectory></outputDirectory>
			<includes>
				<include>startup.*</include>
			</includes>
		</fileSet>

		<fileSet>
			<directory>${project.build.directory}</directory>
			<outputDirectory></outputDirectory>
			<includes>
				<include>*.jar</include>
			</includes>
		</fileSet>
	</fileSets>
</assembly>
```
