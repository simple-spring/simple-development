package com.simple.code.generate.component;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.simple.code.generate.dto.SimpleConfigDto;
import com.simple.code.generate.utils.ConnectionUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luke
 */
public class AutoGenerateCode{

    private SimpleConfigDto simpleConfigDto;
    private ComponentNameGenerate componentNameGenerate;

    public AutoGenerateCode(SimpleConfigDto simpleConfigDto, ComponentNameGenerate componentNameGenerate) {
        this.simpleConfigDto = simpleConfigDto;
        this.componentNameGenerate = componentNameGenerate;
    }

    public void run() {
        try {
            List<String> tableList = ConnectionUtil.getMetaData(simpleConfigDto);

            // 代码生成器
            AutoGenerator mpg = new AutoGenerator();

            // 全局配置
            GlobalConfig gc = new GlobalConfig();
            gc.setOutputDir(componentNameGenerate.projectJavaPath);
            gc.setAuthor("luke");
            gc.setOpen(false);
            gc.setServiceName("%sService");
            gc.setEntityName("%sDo");
            gc.setIdType(IdType.AUTO);
            // gc.setSwagger2(true); 实体属性 Swagger2 注解
            mpg.setGlobalConfig(gc);

            // 数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setUrl("jdbc:mysql://" + simpleConfigDto.getMysqlIp() + ":" + simpleConfigDto.getMysqlPort() + "/" + simpleConfigDto.getDataBaseName() + "?useUnicode=true&useSSL=false&characterEncoding=utf8&useSSL=false");
            dsc.setDriverName("com.mysql.jdbc.Driver");
            dsc.setUsername(simpleConfigDto.getMysqlUser());
            dsc.setPassword(simpleConfigDto.getMysqlPassword());
            mpg.setDataSource(dsc);

            // 包配置
            PackageConfig pc = new PackageConfig();
            pc.setParent(componentNameGenerate.projectPackage);
            pc.setEntity("model");
            mpg.setPackageInfo(pc);

            // 自定义配置
            InjectionConfig cfg = new InjectionConfig() {
                @Override
                public void initMap() {
                    // to do nothing
                }
            };

            // 如果模板引擎是 freemarker
            //String templatePath = "/templates/mapper.xml.ftl";
            // 如果模板引擎是 velocity
            String templatePath = "/templates/mapper.xml.vm";

            // 自定义输出配置
            List<FileOutConfig> focList = new ArrayList<>();
            // 自定义配置会被优先输出
            focList.add(new FileOutConfig(templatePath) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return componentNameGenerate.projectResourcesPath + "/mapper/"+ "/" + componentNameGenerate.projectName
                            + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
            });
            cfg.setFileCreate(new IFileCreate() {
                @Override
                public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                    // 判断自定义文件夹是否需要创建
                    checkDir(filePath);
                    if (fileType == FileType.CONTROLLER) {
                        // 已经生成 CONTROLLER 文件判断存在，不想重新生成返回 false
                        return false;
                    }
                    if (fileType == FileType.MAPPER) {
                        // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                        return !new File(filePath).exists();
                    }
                    if (fileType == FileType.XML) {
                        // 已经生成 XML 文件判断存在，不想重新生成返回 false
                        return !new File(filePath).exists();
                    }
                    if (fileType == FileType.ENTITY) {
                        // 已经生成 ENTITY 文件判断存在，不想重新生成返回 false
                        return true;
                    }
                    if (fileType == FileType.SERVICE) {
                        // 已经生成 SERVICE 文件判断存在，不想重新生成返回 false
                        return !new File(filePath).exists();
                    }
                    if (fileType == FileType.SERVICE_IMPL) {
                        // 已经生成 SERVICE_IMPL 文件判断存在，不想重新生成返回 false
                        return !new File(filePath).exists();
                    }
                    // 允许生成模板文件
                    return true;
                }
            });
            cfg.setFileOutConfigList(focList);
            mpg.setCfg(cfg);

            // 配置模板
            TemplateConfig templateConfig = new TemplateConfig();

            // 配置自定义输出模板
            //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
            // templateConfig.setEntity("templates/entity2.java");
            // templateConfig.setService();
            // templateConfig.setController();

            templateConfig.setXml(null);
            mpg.setTemplate(templateConfig);

            // 策略配置
            StrategyConfig strategy = new StrategyConfig();
            strategy.setNaming(NamingStrategy.underline_to_camel);
            strategy.setColumnNaming(NamingStrategy.underline_to_camel);
            strategy.setEntityLombokModel(true);
            strategy.setRestControllerStyle(true);
            // 写于父类中的公共字段
            //strategy.setSuperEntityColumns("id");
            String[] tablesNames = new String[tableList.size()];
            for (int i = 0; i < tableList.size(); i++) {
                tablesNames[i] = tableList.get(i);
            }
            strategy.setInclude(tablesNames);
            strategy.setControllerMappingHyphenStyle(true);
            strategy.setTablePrefix("t_");
            mpg.setStrategy(strategy);
            mpg.execute();
        } catch (Exception e) {
            throw new RuntimeException("生成代码出错", e);
        }
    }
}

