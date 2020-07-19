//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.lava.dal.common;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.alibaba.lava.base.stateaction.DataResult;
import com.alibaba.lava.base.stateaction.LAStateBoImpl;
import com.alibaba.lava.base.stateaction.StateBo;
import org.apache.commons.lang.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BoPlugin extends PluginAdapter {
    private static FullyQualifiedJavaType longType = new FullyQualifiedJavaType("java.lang.Long");
    public static String JAVAFILE_POTFIX = "Bo";
    public static String JAVAFILE_IMPL_POTFIX = "Impl";

    public BoPlugin() {
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        String bathPath = this.properties.getProperty("basePath");
        String packagePath = introspectedTable.getTableConfiguration().getProperties().getProperty("boPackage");
        String doName = introspectedTable.getBaseRecordType();
        String exmpName = introspectedTable.getExampleType();
        String mapperName = introspectedTable.getMyBatis3JavaMapperType() + "Ext";
        String boName = doName.substring(doName.lastIndexOf(".") + 1).replace("Do", "") + JAVAFILE_POTFIX;
        String stateAction = introspectedTable.getTableConfiguration().getProperties().getProperty("stateAction");
        boolean isStateAction = StringUtils.equalsIgnoreCase("true", stateAction);
        FullyQualifiedJavaType boType = new FullyQualifiedJavaType(packagePath + "." + boName);
        Interface boInterfaze = new Interface(boType);
        boInterfaze.setVisibility(JavaVisibility.PUBLIC);
        this.context.getCommentGenerator().addJavaFileComment(boInterfaze);
        FullyQualifiedJavaType supperInterface;
        if (isStateAction) {
            supperInterface = new FullyQualifiedJavaType(StateBo.class.getSimpleName() + "<" + doName + "," + exmpName + ">");
        } else {
            supperInterface = new FullyQualifiedJavaType("com.alibaba.lava.base.LavaBo"+ "<" + doName + "," + exmpName + ">");
        }

        boInterfaze.addImportedType(supperInterface);
        boInterfaze.addSuperInterface(supperInterface);
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(boInterfaze, bathPath, this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());
        if (this.isExistExtFile(bathPath, generatedJavaFile.getTargetPackage(), generatedJavaFile.getFileName())) {
            return super.contextGenerateAdditionalJavaFiles(introspectedTable);
        } else {
            List<GeneratedJavaFile> generatedJavaFiles = new ArrayList(2);
            generatedJavaFiles.add(generatedJavaFile);
            FullyQualifiedJavaType implType = new FullyQualifiedJavaType(packagePath + ".impl." + boName + JAVAFILE_IMPL_POTFIX);
            TopLevelClass clazz = new TopLevelClass(implType);
            FullyQualifiedJavaType supperType;
            if (isStateAction) {
                supperType = new FullyQualifiedJavaType(LAStateBoImpl.class.getSimpleName() + "<" + doName + "," + mapperName + "," + exmpName + ">");
            } else {
                supperType = new FullyQualifiedJavaType(AbstractLavaBoImpl.class.getSimpleName() + "<" + doName + "," + mapperName + "," + exmpName + ">");
            }

            clazz.addImportedType("org.springframework.stereotype.Service");
            clazz.addImportedType("com.spring.simple.development.core.annotation.base.IsApiService");
            clazz.addImportedType("org.springframework.beans.factory.annotation.Autowired");
            clazz.addImportedType("com.spring.simple.development.core.annotation.base.NoApiMethod");
            clazz.addImportedType("com.alibaba.lava.base.AbstractLavaBoImpl");

            clazz.addImportedType(supperType);
            clazz.setSuperClass(supperType);
            clazz.addImportedType(boType);
            clazz.addSuperInterface(boType);
            clazz.addAnnotation("@Service");
            clazz.addAnnotation("@IsApiService");
            clazz.setVisibility(JavaVisibility.PUBLIC);
            this.createMethod("setBaseMapper", (FullyQualifiedJavaType)null, new FullyQualifiedJavaType(mapperName), "mapper", "@Autowired,@NoApiMethod", "setMapper(mapper);", clazz);
            GeneratedJavaFile implFile = new GeneratedJavaFile(clazz, bathPath, this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());
            generatedJavaFiles.add(implFile);
            return generatedJavaFiles;
        }
    }

    protected void addBaseMethods(String doName, String exmpName, Interface interfaze) {
        FullyQualifiedJavaType intType = FullyQualifiedJavaType.getIntInstance();
        FullyQualifiedJavaType doType = new FullyQualifiedJavaType(doName);
        FullyQualifiedJavaType examType = new FullyQualifiedJavaType(exmpName);
        this.createMethod("insert", intType, doType, "dataObject", interfaze);
        this.createMethod("delete", intType, longType, "id", interfaze);
        this.createMethod("selectByExample", new FullyQualifiedJavaType("java.util.List<" + doName + ">"), examType, "example", interfaze);
        this.createMethod("selectByPrimaryKey", doType, longType, "id", interfaze);
        this.createMethod("update", intType, doType, "dataObject", interfaze);
        this.createMethod("getPageByExample", new FullyQualifiedJavaType(DataResult.class.getSimpleName() + "<" + doName + ">"), examType, "example", interfaze);
        this.createMethod("isValidDo", FullyQualifiedJavaType.getBooleanPrimitiveInstance(), doType, "dataObject", interfaze);
    }

    private void createMethod(String name, FullyQualifiedJavaType rsType, FullyQualifiedJavaType parmType, String parmName, Interface interfaze) {
        Method method = new Method(name);
        method.setVisibility(JavaVisibility.PUBLIC);
        if (rsType != null) {
            method.setReturnType(rsType);
            interfaze.addImportedType(rsType);
        }

        if (parmType != null) {
            Parameter param = new Parameter(parmType, parmName);
            method.addParameter(param);
            interfaze.addImportedType(parmType);
        }

        interfaze.addMethod(method);
    }

    private void createMethod(String name, FullyQualifiedJavaType rsType, FullyQualifiedJavaType parmType, String parmName, String annotation, String body, TopLevelClass clazz) {
        Method method = new Method(name);
        method.setVisibility(JavaVisibility.PUBLIC);
        if (rsType != null) {
            method.setReturnType(rsType);
            clazz.addImportedType(rsType);
        }

        if (parmType != null) {
            Parameter param = new Parameter(parmType, parmName);
            method.addParameter(param);
            clazz.addImportedType(parmType);
        }

        String[] annotations = annotation.split(",");
        String[] var10 = annotations;
        int var11 = annotations.length;

        for(int var12 = 0; var12 < var11; ++var12) {
            String annotationName = var10[var12];
            method.addAnnotation(annotationName);
        }

        clazz.addImportedType("Autowired");
        method.addBodyLine(body);
        clazz.addMethod(method);
    }

    private boolean isExistExtFile(String targetProject, String targetPackage, String fileName) {
        File project = new File(targetProject);
        if (!project.isDirectory()) {
            return true;
        } else {
            StringBuilder sb = new StringBuilder();
            StringTokenizer st = new StringTokenizer(targetPackage, ".");

            while(st.hasMoreTokens()) {
                sb.append(st.nextToken());
                sb.append(File.separatorChar);
            }

            File directory = new File(project, sb.toString());
            if (!directory.isDirectory()) {
                boolean rc = directory.mkdirs();
                if (!rc) {
                    return true;
                }
            }

            File testFile = new File(directory, fileName);
            return testFile.exists();
        }
    }

    public boolean validate(List<String> warnings) {
        return true;
    }
}
