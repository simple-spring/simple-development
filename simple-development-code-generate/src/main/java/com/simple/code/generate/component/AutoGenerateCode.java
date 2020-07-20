package com.simple.code.generate.component;

import com.simple.code.generate.config.SimpleConfig;
import com.simple.code.generate.utils.ScriptUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * @author luke
 */
public class AutoGenerateCode extends Thread {
    private String projectPath;

    public AutoGenerateCode(String projectPath) {
        this.projectPath = projectPath;
    }

    @Override
    public void run() {

        while (true) {
            try {
                System.out.println("指定生成命令");
                File projectPathFile = new File(projectPath);
                if (projectPathFile.exists()) {
                    System.out.println("项目地址已存在："+projectPath);
                    if (isWindows()) {
                        String command = "cd " + System.getProperties().getProperty("user.dir").substring(0, 2) + projectPath + "\n set MAVEN_OPTS=\"-Dfile.encoding=UTF-8\" \n mvn mybatis-generator:generate";
                        System.out.println("执行window命令：" + command);
                        String scriptFileName = System.getProperties().getProperty("user.dir").substring(0, 2)+SimpleConfig.projectGenerateBasePath + "/" + UUID.randomUUID().toString() + ".bat";
                        File scriptFile = new File(scriptFileName);
                        if (!scriptFile.exists()) {
                            ScriptUtil.markScriptFile(scriptFileName, command);
                        }
                        Process process = Runtime.getRuntime().exec(scriptFileName);
                        InputStream in = process.getInputStream();
                        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
                        String line;
                        while((line=bufferedReader.readLine())!=null)
                        {
                            System.out.println(line + "\n");
                        }
                        System.out.println("执行结果：" + process.waitFor());
                        in.close();
                    } else {
                        String command = "cd " + projectPath + "\n set MAVEN_OPTS=\"-Dfile.encoding=UTF-8\" \n  mvn mybatis-generator:generate";
                        System.out.println("执行Linux命令：" + command);

                        String scriptFileName = SimpleConfig.projectGenerateBasePath + "/" + UUID.randomUUID().toString() + ".sh";
                        File scriptFile = new File(scriptFileName);
                        if (!scriptFile.exists()) {
                            ScriptUtil.markScriptFile(scriptFileName, command);
                        }
                        Runtime.getRuntime().exec("chmod +=x "+scriptFileName);
                        Process process = Runtime.getRuntime().exec(scriptFileName);
                        InputStream in = process.getInputStream();
                        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
                        String line;
                        while((line=bufferedReader.readLine())!=null)
                        {
                            System.out.println(line + "\n");
                        }
                        System.out.println("执行结果：" + process.waitFor());
                        in.close();
                    }
                    break;
                }
                System.out.println("开始等待");
                Thread.sleep(10000);
            } catch (Exception e) {
                break;
            }
        }
    }

    public boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
    }
}

