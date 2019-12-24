package com.chexin.simple.development.core.tomcat;

import com.chexin.simple.development.support.properties.PropertyConfigurer;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;

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
        System.out.println("您可以直接在console里敲回车，重启tomcat");
        System.out.println("********************************************************");
    }

    public String getWebappsPath() {
        String file = getClass().getClassLoader().getResource(".").getFile();
        return file.substring(0, file.indexOf("target")) + "src/main/webapp";
    }


    public static void run() {
        // 读取项目配置文件
        PropertyConfigurer.loadApplicationProperties("application.properties");
        String port = PropertyConfigurer.getProperty("server.port");
        SpringApplication tomcatTest = new SpringApplication(StringUtils.isEmpty(port) ? DEFAULT_PORT : Long.valueOf(port).intValue(), true);
        tomcatTest.start();
    }

}
