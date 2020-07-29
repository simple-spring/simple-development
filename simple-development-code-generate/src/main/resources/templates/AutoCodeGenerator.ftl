package ${packagePath};

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
* desc:    代码生成工具类
* author:  hjs
* date:    2020-06-18
*/
public class MysqlGenerator {

    // 数据库连接信息
    static String dbUrl = "jdbc:mysql://${simpleConfigDto.getMysqlIp()}:3306/${simpleConfigDto.getDataBaseName()}?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8";
    static String dbDriver = "com.mysql.cj.jdbc.Driver";
    static String uName =  "${simpleConfigDto.getMysqlUser()}";
    static String pwd =  "${simpleConfigDto.getMysqlPassword()}";
    // 包名
    static String parentPackage = "";
    // 模块名
    static String moduleName = "";
    // 注释
    static String author = "root";
    // 表名
    static String tableName = "";

    /**
    * RUN THIS
    */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dbUrl);
        dsc.setDriverName(dbDriver);
        dsc.setUsername(uName);
        dsc.setPassword(pwd);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(parentPackage);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        // 设置代码有改动只需重复生成 entity 和 mapper.xml
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建,这里调用默认的方法
                checkDir(filePath);
                // 对于已存在的文件，只需重复生成 entity 和 mapper.xml
                File file = new File(filePath);
                boolean exist = file.exists();
                if (exist) {
                    if (filePath.endsWith("Mapper.xml") || FileType.ENTITY == fileType) {
                        return true;
                    } else {
                        return false;
                    }
                }
                //不存在的文件都需要创建
                return true;
            }
        });


        // 如果模板引擎是 beetl
        String templatePath = "/templates/mapper.xml.btl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mybatis/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 设置任何一个模块如果设置 空 OR Null 将不生成该模块
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        tc.setController(null);
        tc.setMapper(null);

        // tc.setService("...");
        // tc.setServiceImpl("...");
        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new BeetlTemplateEngine());
        mpg.execute();
    }
}
