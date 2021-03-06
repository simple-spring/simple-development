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
			<directory>${r'${project.basedir}'}</directory>
			<outputDirectory>/</outputDirectory>
			<includes>
				<include>LICENSE*</include>
				<include>NOTICE*</include>
			</includes>
		</fileSet>

		<fileSet>
			<directory>${r'${project.basedir}'}\src\main\resources\scripts</directory>
			<outputDirectory></outputDirectory>
			<includes>
				<include>startup.*</include>
			</includes>
		</fileSet>

		<fileSet>
			<directory>${r'${project.build.directory}'}</directory>
			<outputDirectory></outputDirectory>
			<includes>
				<include>*.jar</include>
			</includes>
		</fileSet>
	</fileSets>
</assembly>