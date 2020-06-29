package com.spring.simple.development.core.baseconfig.tomcat;

import com.spring.simple.development.core.annotation.config.EnableDubbo;
import com.spring.simple.development.core.annotation.config.EnableMybatis;
import com.spring.simple.development.core.init.SpringBootAppInitializer;
import com.spring.simple.development.core.spiconfig.support.DataSourceSpiConfig;
import com.spring.simple.development.core.spiconfig.support.DubboSpiConfig;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.ClassLoadUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liko.wang
 * @Date 2019/12/24/024 14:17
 * @return
 * @Description 启动项目
 **/
public class SimpleBootApplication {

    public static void run(Class appClass, String[] args) {
        String banner = "\n" +
                "                                                                                                                                                            \n" +
                "                                                                                                                                  .---.                     \n" +
                "         _________   _...._              .--.   _..._                                  .--. __  __   ___  _________   _...._      |   |      __.....__      \n" +
                "         \\        |.'      '-.           |__| .'     '.   .--./)                       |__||  |/  `.'   `.\\        |.'      '-.   |   |  .-''         '.    \n" +
                "          \\        .'```'.    '. .-,.--. .--..   .-.   . /.''\\\\                        .--.|   .-.  .-.   '\\        .'```'.    '. |   | /     .-''\"'-.  `.  \n" +
                "           \\      |       \\     \\|  .-. ||  ||  '   '  || |  | |                       |  ||  |  |  |  |  | \\      |       \\     \\|   |/     /________\\   \\ \n" +
                "       _    |     |        |    || |  | ||  ||  |   |  | \\`-' /                    _   |  ||  |  |  |  |  |  |     |        |    ||   ||                  | \n" +
                "     .' |   |      \\      /    . | |  | ||  ||  |   |  | /(\"'`                   .' |  |  ||  |  |  |  |  |  |      \\      /    . |   |\\    .-------------' \n" +
                "    .   | / |     |\\`'-.-'   .'  | |  '- |  ||  |   |  | \\ '---.                .   | /|  ||  |  |  |  |  |  |     |\\`'-.-'   .'  |   | \\    '-.____...---. \n" +
                "  .'.'| |// |     | '-....-'`    | |     |__||  |   |  |  /'\"\"'.\\             .'.'| |//|__||__|  |__|  |__|  |     | '-....-'`    |   |  `.             .'  \n" +
                ".'.'.-'  / .'     '.             | |         |  |   |  | ||     ||          .'.'.-'  /                      .'     '.             '---'    `''-...... -'    \n" +
                ".'   \\_.''-----------'           |_|         |  |   |  | \\'. __//           .'   \\_.'                     '-----------'                                     \n" +
                "                                             '--'   '--'  `'---'                                                                                            \n";

        System.out.println(banner);
        // check system properties
        Annotation[] annotations = appClass.getAnnotations();
        if (annotations.length == 0) {
            return;
        }
        Boolean isBaseConfig = false;
        for (Annotation annotation : annotations) {
            SpringBootAppInitializer.annotationSet.add(annotation);
            if (annotation instanceof SpringBootApplication) {
                SpringBootApplication springSimpleApplication = (SpringBootApplication) annotation;
                // 启动类名
                System.setProperty(SystemProperties.SPRING_APPLICATION_CLASS_NAME, appClass.getName());
                // 系统包名
                System.setProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME, appClass.getPackage().getName());
                isBaseConfig = true;
            }
        }
        // 是否有基础组件
        if (!isBaseConfig) {
            throw new RuntimeException(" no SpringSimpleApplication Component");
        }
        // 找到启动的组件
        List<String> components = SpringBootAppInitializer.getComponents();
        components.add(appClass.getPackage().getName());
        components.add("com.spring.simple.development.core.baseconfig");
        // 设置默认端口
        String port = PropertyConfigurer.getProperty("server.port");
        if (StringUtils.isEmpty(port)) {
            System.getProperties().setProperty("server.port", "8000");
        }else{
            System.getProperties().setProperty("server.port", port);
        }
        // 设置默认路徑
        String path = PropertyConfigurer.getProperty("server.path");
        if (!StringUtils.isEmpty(path)) {
            System.getProperties().setProperty("server.servlet.context-path", path.contains("/")?path:"/"+path);
        }
        if (CollectionUtils.isEmpty(components)) {
            SpringApplication.run(appClass, args);
        } else {
            // 检查是否有mybatis组件和dubbo组件
            Class enableMybatis = isEnableMybatis(appClass);
            Class enableDubbo = isEnableDubbo(enableMybatis);
            // 注册并启动
            Class javassistAppClass = ClassLoadUtil.javassistCompile(enableDubbo, "org.springframework.boot.autoconfigure.SpringBootApplication", components, "scanBasePackages");

            SpringApplication.run(javassistAppClass, args);
        }
        System.out.println("\n" +
                "                 _                   _                 _                       _ \n" +
                "                (_)                 (_)               | |                     | |\n" +
                "  ___ _ __  _ __ _ _ __   __ _   ___ _ _ __ ___  _ __ | | ___    ___ _ __   __| |\n" +
                " / __| '_ \\| '__| | '_ \\ / _` | / __| | '_ ` _ \\| '_ \\| |/ _ \\  / _ \\ '_ \\ / _` |\n" +
                " \\__ \\ |_) | |  | | | | | (_| | \\__ \\ | | | | | | |_) | |  __/ |  __/ | | | (_| |\n" +
                " |___/ .__/|_|  |_|_| |_|\\__, | |___/_|_| |_| |_| .__/|_|\\___|  \\___|_| |_|\\__,_|\n" +
                "     | |                  __/ |                 | |                              \n" +
                "     |_|                 |___/                  |_|                              \n");

        openService();
    }

    public static void openService() {
        try {
            boolean windows = System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
            if (windows) {
                String port = System.getProperties().getProperty("server.port");
                String path = System.getProperties().getProperty("server.servlet.context-path");
                String command = "cmd /c start http://127.0.0.1:" + port + (StringUtils.isEmpty(path) ? "" : path);
                Runtime.getRuntime().exec(command);
            }
        } catch (Exception e) {
            System.out.println("自动打开浏览器失败");
        }
    }

    private static Class isEnableMybatis(Class appClass) {
        Annotation annotation = appClass.getAnnotation(EnableMybatis.class);
        if (annotation != null) {
            Class javassistAppClassMapper = ClassLoadUtil.javassistCompile(appClass, "org.mybatis.spring.annotation.MapperScan", DataSourceSpiConfig.mapperPackageNames, "basePackages");
            return javassistAppClassMapper;
        }
        return appClass;
    }

    private static Class isEnableDubbo(Class appClass) {
        Annotation annotation = appClass.getAnnotation(EnableDubbo.class);
        if (annotation != null) {
            String dubboPackagePath = "com.spring.simple.development.demo.dubbo";
            List<String> dubboPaths = new ArrayList<>();
            dubboPaths.add(dubboPackagePath);
            Class javassistAppClassDubbo = ClassLoadUtil.javassistCompile(appClass, "com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan", DubboSpiConfig.dubboPackageNames, "basePackages");
            return javassistAppClassDubbo;
        }
        return appClass;
    }
}
