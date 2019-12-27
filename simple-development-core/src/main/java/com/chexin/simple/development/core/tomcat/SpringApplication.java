package com.chexin.simple.development.core.tomcat;

import com.chexin.simple.development.core.annotation.config.SimpleConfig;
import com.chexin.simple.development.core.annotation.config.SpringSimpleApplication;
import com.chexin.simple.development.core.config.SimpleSpiConfig;
import com.chexin.simple.development.core.constant.SystemProperties;
import com.chexin.simple.development.core.init.AppInitializer;
import com.chexin.simple.development.support.properties.PropertyConfigurer;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.reflections.Reflections;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import java.lang.annotation.Annotation;
import java.util.Properties;
import java.util.Set;

/**
 * @author liko.wang
 * @Date 2019/12/24/024 14:17
 * @return
 * @Description tomcat启动项目
 **/
public class SpringApplication {
    private static final char ENTER_CHAR = '\n';
    private static final int DEFAULT_PORT = 8000;
    private int port = DEFAULT_PORT;

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
    public SpringApplication(int port, boolean isServlet3Enable) {
        this.port = port;
        this.isServlet3Enable = isServlet3Enable;
    }

    /**
     * 构建一个<code>TomcatBootstrapHelper.java</code><br>
     * 不启用servlet 3.0 支持、环境变量spring.profiles.active=dev
     *
     * @param port 端口
     */
    public SpringApplication(int port) {
        this(port, false);
    }

    /**
     * 构建一个<code>TomcatBootstrapHelper.java</code><br>
     * 端口：8080、不启用servlet 3.0 支持、环境变量spring.profiles.active=dev
     */
    public SpringApplication() {
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
            //在控制台回车就可以重启，提高效率
            while (true) {
                char c = (char) System.in.read();
                if (c == ENTER_CHAR) {
                    begin = System.currentTimeMillis();
                    System.out.println("重启tomcat...");
                    tomcat.stop();
                    tomcat.start();
                    end = System.currentTimeMillis();
                    log(end - begin);
                }
            }
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
        System.out.println("启动成功: http://127.0.0.1:" + port + "   in:" + time + "ms");
        System.out.println("小提示：直接在console里敲回车，可以重启tomcat");
        System.out.println("********************************************************");
    }

    public String getWebappsPath() {
        String file = getClass().getClassLoader().getResource(".").getFile();
        return file.substring(0, file.indexOf("target")) + "src/main/webapp";
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
        for (Annotation annotation : annotations) {
            AppInitializer.annotationSet.add(annotation);
            if (annotation instanceof SpringSimpleApplication) {
                SpringSimpleApplication springSimpleApplication = (SpringSimpleApplication) annotation;
                // 启动类名
                System.setProperty(SystemProperties.SPRINGAPPLICATION_CLASS_NAME, appClass.getName());
                // 应用名
                System.setProperty(SystemProperties.APPLICATION_ROOT_CONFIG_NAME, springSimpleApplication.applicationName());
                // 系统包名
                if (StringUtils.isEmpty(springSimpleApplication.appPackagePathName())) {
                    System.setProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APPPACKAGEPATHNAME, appClass.getPackage().getName());
                } else {
                    System.setProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APPPACKAGEPATHNAME, springSimpleApplication.appPackagePathName());
                }
                isBaseConfig = true;
            }
        }
        // 是否有基础组件
        if (!isBaseConfig) {
            throw new RuntimeException(" no SpringSimpleApplication Component");
        }
        // 读取项目配置文件
        PropertyConfigurer.loadApplicationProperties("application.properties");
        String port = PropertyConfigurer.getProperty("server.port");
        SpringApplication tomcatTest = new SpringApplication(StringUtils.isEmpty(port) ? DEFAULT_PORT : Long.valueOf(port).intValue(), true);
        tomcatTest.start();
    }

}
