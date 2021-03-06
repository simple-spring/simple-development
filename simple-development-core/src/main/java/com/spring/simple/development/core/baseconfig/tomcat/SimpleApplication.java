package com.spring.simple.development.core.baseconfig.tomcat;

import com.alibaba.fastjson.JSONObject;
import com.spring.simple.development.core.annotation.config.EnableFastGoConfig;
import com.spring.simple.development.core.annotation.config.SpringSimpleApplication;
import com.spring.simple.development.core.init.AppInitializer;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.HttpUtils;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author liko.wang
 * @Date 2019/12/24/024 14:17
 * @return
 * @Description tomcat启动项目
 **/
public class SimpleApplication {
    private static final char ENTER_CHAR = '\n';
    private static final int DEFAULT_PORT = 8000;
    private int port = DEFAULT_PORT;
    private String projectName = "";

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 是否启用servlet 3.0 支持，如果启用的话，就需要扫描jar包中是否有Servlet等annotation，这个会影响启动时间，默认不开启
     */
    private boolean isServlet3Enable = false;

    /**
     * 构建一个<code>TomcatBootstrapHelper.java</code>
     *
     * @param port             端口
     * @param isServlet3Enable 是否启用servlet 3.0
     *                         支持，如果启用的话，就需要扫描jar包中是否有Servlet等annotation，这个会影响启动时间，默认不开启
     */
    public SimpleApplication(int port, boolean isServlet3Enable) {
        this.port = port;
        this.isServlet3Enable = isServlet3Enable;
    }

    /**
     * 构建一个<code>TomcatBootstrapHelper.java</code><br>
     * 不启用servlet 3.0 支持、环境变量spring.profiles.active=dev
     *
     * @param port 端口
     */
    public SimpleApplication(int port) {
        this(port, false);
    }

    /**
     * 构建一个<code>TomcatBootstrapHelper.java</code><br>
     * 端口：8080、不启用servlet 3.0 支持、环境变量spring.profiles.active=dev
     */
    public SimpleApplication() {
        this(DEFAULT_PORT);
    }


    public void start() {
        try {
            long begin = System.currentTimeMillis();
            Tomcat tomcat = new Tomcat();
            configTomcat(tomcat);
            tomcat.start();
            long end = System.currentTimeMillis();
            log(end - begin);
            System.out.println("\n" +
                    "                 _                   _                 _                       _ \n" +
                    "                (_)                 (_)               | |                     | |\n" +
                    "  ___ _ __  _ __ _ _ __   __ _   ___ _ _ __ ___  _ __ | | ___    ___ _ __   __| |\n" +
                    " / __| '_ \\| '__| | '_ \\ / _` | / __| | '_ ` _ \\| '_ \\| |/ _ \\  / _ \\ '_ \\ / _` |\n" +
                    " \\__ \\ |_) | |  | | | | | (_| | \\__ \\ | | | | | | |_) | |  __/ |  __/ | | | (_| |\n" +
                    " |___/ .__/|_|  |_|_| |_|\\__, | |___/_|_| |_| |_| .__/|_|\\___|  \\___|_| |_|\\__,_|\n" +
                    "     | |                  __/ |                 | |                              \n" +
                    "     |_|                 |___/                  |_|                              \n");
            if (isWindows()) {
                String command = "cmd /c start http://127.0.0.1:" + port + (StringUtils.isEmpty(projectName) ? "" : "/" + projectName);
                Runtime.getRuntime().exec(command);
            }
            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startTest() {
        try {
            long begin = System.currentTimeMillis();
            Tomcat tomcat = new Tomcat();
            configTomcat(tomcat);
            tomcat.start();
            long end = System.currentTimeMillis();
            log(end - begin);
            System.out.println("\n" +
                    "                 _                   _                 _                       _ \n" +
                    "                (_)                 (_)               | |                     | |\n" +
                    "  ___ _ __  _ __ _ _ __   __ _   ___ _ _ __ ___  _ __ | | ___    ___ _ __   __| |\n" +
                    " / __| '_ \\| '__| | '_ \\ / _` | / __| | '_ ` _ \\| '_ \\| |/ _ \\  / _ \\ '_ \\ / _` |\n" +
                    " \\__ \\ |_) | |  | | | | | (_| | \\__ \\ | | | | | | |_) | |  __/ |  __/ | | | (_| |\n" +
                    " |___/ .__/|_|  |_|_| |_|\\__, | |___/_|_| |_| |_| .__/|_|\\___|  \\___|_| |_|\\__,_|\n" +
                    "     | |                  __/ |                 | |                              \n" +
                    "     |_|                 |___/                  |_|                              \n");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void configTomcat(final Tomcat tomcat) throws ServletException {
        tomcat.setBaseDir("target");
        tomcat.setPort(port);
        Connector connector = new Connector("HTTP/1.1");
        connector.setPort(port);
        connector.setURIEncoding("utf-8");
        tomcat.setConnector(connector);
        tomcat.getService().addConnector(connector);
        String webappPath = getWebappsPath();
        System.out.println("webapp目录：" + webappPath);
        String path = PropertyConfigurer.getProperty("server.path");
        Context ctx = tomcat.addWebapp(StringUtils.isEmpty(path) ? "/" : path, webappPath);
        StandardJarScanner scanner = (StandardJarScanner) ctx.getJarScanner();
        if (!isServlet3Enable) {
            scanner.setScanAllDirectories(false);
            scanner.setScanClassPath(false);
        }
        tomcat.setSilent(true);
        System.setProperty("org.apache.catalina.SESSION_COOKIE_NAME", "JSESSIONID" + port);
    }

    private void log(long time) {
        System.out.println("********************************************************");
        System.out.println("启动成功: http://127.0.0.1:" + port + (StringUtils.isEmpty(projectName) ? "" : "/" + projectName) + "   in:" + time + "ms");
        System.out.println("********************************************************");
    }

    public String getWebappsPath() {
        String file = getClass().getClassLoader().getResource(".").getFile();
        //return file.substring(0, file.indexOf("target")) + "src/main/webapp";
        return file;
    }


    public static void run(Class appClass) {
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
        Boolean isEnableFastGoConfig = false;
        for (Annotation annotation : annotations) {
            AppInitializer.annotationSet.add(annotation);
            if (annotation instanceof SpringSimpleApplication) {
                SpringSimpleApplication springSimpleApplication = (SpringSimpleApplication) annotation;
                // 启动类名
                System.setProperty(SystemProperties.SPRING_APPLICATION_CLASS_NAME, appClass.getName());
                // 应用名
                System.setProperty(SystemProperties.APPLICATION_ROOT_CONFIG_NAME, springSimpleApplication.applicationName());
                // 系统包名
                if (StringUtils.isEmpty(springSimpleApplication.appPackagePathName())) {
                    System.setProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME, appClass.getPackage().getName());
                } else {
                    System.setProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME, springSimpleApplication.appPackagePathName());
                }
                isBaseConfig = true;
            }
            if (annotation instanceof EnableFastGoConfig) {
                isEnableFastGoConfig = true;
                EnableFastGoConfig enableFastGoConfig = (EnableFastGoConfig) annotation;
                String url = enableFastGoConfig.fastGoServer();
                String branch = enableFastGoConfig.branch();
                String projectCode = enableFastGoConfig.projectCode();
                String configUrl = url + "?projectCode=" + projectCode + "&branch=" + branch;
                String json = HttpUtils.doGet(configUrl);
                Map map = JSONObject.parseObject(json, Map.class);
                PropertyConfigurer.loadApplicationProperties(map);
            }
        }
        // 是否有基础组件
        if (!isBaseConfig) {
            throw new RuntimeException(" no SpringSimpleApplication Component");
        }
        // 读取项目配置文件
        if (isEnableFastGoConfig == false) {
            PropertyConfigurer.loadApplicationProperties("application.properties");
        }

        String port = PropertyConfigurer.getProperty("server.port");
        SimpleApplication tomcatTest = new SimpleApplication(StringUtils.isEmpty(port) ? DEFAULT_PORT : Long.valueOf(port).intValue(), true);
        tomcatTest.setProjectName(PropertyConfigurer.getProperty("server.path"));
        tomcatTest.start();
    }

    public static void runTest(Class appClass) {
        String banner = "              (_)                 (_)               | |      | |          | |  \n" +
                " ___ _ __  _ __ _ _ __   __ _   ___ _ _ __ ___  _ __ | | ___  | |_ ___  ___| |_ \n" +
                "/ __| '_ \\| '__| | '_ \\ / _` | / __| | '_ ` _ \\| '_ \\| |/ _ \\ | __/ _ \\/ __| __|\n" +
                "\\__ \\ |_) | |  | | | | | (_| | \\__ \\ | | | | | | |_) | |  __/ | ||  __/\\__ \\ |_ \n" +
                "|___/ .__/|_|  |_|_| |_|\\__, | |___/_|_| |_| |_| .__/|_|\\___|  \\__\\___||___/\\__|\n" +
                "    | |                  __/ |                 | |                              \n" +
                "    |_|                 |___/                  |_|                            ";

        System.out.println(banner);
        // check system properties
        Annotation[] annotations = appClass.getAnnotations();
        if (annotations.length == 0) {
            return;
        }
        Boolean isBaseConfig = false;
        Boolean isEnableFastGoConfig = false;
        for (Annotation annotation : annotations) {
            AppInitializer.annotationSet.add(annotation);
            if (annotation instanceof com.spring.simple.development.core.annotation.config.SpringSimpleApplication) {
                com.spring.simple.development.core.annotation.config.SpringSimpleApplication springSimpleApplication = (com.spring.simple.development.core.annotation.config.SpringSimpleApplication) annotation;
                // 启动类名
                System.setProperty(SystemProperties.SPRING_APPLICATION_CLASS_NAME, appClass.getName());
                // 应用名
                System.setProperty(SystemProperties.APPLICATION_ROOT_CONFIG_NAME, springSimpleApplication.applicationName());
                // 系统包名
                if (StringUtils.isEmpty(springSimpleApplication.appPackagePathName())) {
                    System.setProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME, appClass.getPackage().getName());
                } else {
                    System.setProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME, springSimpleApplication.appPackagePathName());
                }
                isBaseConfig = true;
            }
            if (annotation instanceof EnableFastGoConfig) {
                isEnableFastGoConfig = true;
                EnableFastGoConfig enableFastGoConfig = (EnableFastGoConfig) annotation;
                String url = enableFastGoConfig.fastGoServer();
                String branch = enableFastGoConfig.branch();
                String projectCode = enableFastGoConfig.projectCode();
                String configUrl = url + "?projectCode=" + projectCode + "&branch=" + branch;
                String json = HttpUtils.doGet(configUrl);
                Map map = JSONObject.parseObject(json, Map.class);
                PropertyConfigurer.loadApplicationProperties(map);
            }
        }
        // 是否有基础组件
        if (!isBaseConfig) {
            throw new RuntimeException(" no SpringSimpleApplication Component");
        }
        // 读取项目配置文件
        if (isEnableFastGoConfig == false) {
            PropertyConfigurer.loadApplicationProperties("application.properties");
        }
        String port = PropertyConfigurer.getProperty("server.port");
        SimpleApplication tomcatTest = new SimpleApplication(StringUtils.isEmpty(port) ? DEFAULT_PORT : Long.valueOf(port).intValue(), true);
        tomcatTest.setProjectName(PropertyConfigurer.getProperty("server.path"));
        tomcatTest.startTest();
    }

    public boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
    }

}
