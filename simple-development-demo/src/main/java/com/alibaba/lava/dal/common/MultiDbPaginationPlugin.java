//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alibaba.lava.dal.common;

import com.alibaba.lava.base.LavaDo;
import com.alibaba.lava.base.LavaExample;
import com.alibaba.lava.base.LavaMapper;
import com.alibaba.lava.base.stateaction.StatefulDo;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class MultiDbPaginationPlugin extends PluginAdapter {
    private static FullyQualifiedJavaType longType = new FullyQualifiedJavaType("java.lang.Long");
    private static String XMLFILE_POSTFIX = "Ext";
    private static String JAVAFILE_POTFIX = "Ext";
    private static String SQLMAP_COMMON_POTFIX = "and is_deleted = 'n'";
    private static String SQLMAP_COMMON_POTFIX_PVG = "and full_org_path like CONCAT(#{fullOrgPath}, '%')";
    private static String SQLMAP_COMMON_POTFIX_OWNER = "and owner =#{owner,jdbcType=VARCHAR}";
    private static String ANNOTATION_RESOURCE = "javax.annotation.Resource";

    public MultiDbPaginationPlugin() {
    }

    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String stateAction = introspectedTable.getTableConfiguration().getProperties().getProperty("stateAction");
        FullyQualifiedJavaType baseDoType;
        if (StringUtils.equalsIgnoreCase("true", stateAction)) {
            baseDoType = new FullyQualifiedJavaType(StatefulDo.class.getSimpleName());
            Field field = new Field();
            field.setName("status");
            field.setType(FullyQualifiedJavaType.getStringInstance());
            field.setVisibility(JavaVisibility.PRIVATE);
            topLevelClass.addField(field);
            this.createMethod(JavaVisibility.PUBLIC, "getStatus", (String)null, FullyQualifiedJavaType.getStringInstance(), (List)null, "return this.status;", topLevelClass);
            List<Parameter> parms = new ArrayList();
            Parameter parm = new Parameter(FullyQualifiedJavaType.getStringInstance(), "status");
            parms.add(parm);
            this.createMethod(JavaVisibility.PUBLIC, "setStatus", (String)null, (FullyQualifiedJavaType)null, parms, "this.status = status;", topLevelClass);
        } else {
            baseDoType = new FullyQualifiedJavaType(LavaDo.class.getSimpleName());
        }

        topLevelClass.addImportedType(baseDoType);
        topLevelClass.setSuperClass(baseDoType);
        this.addBaseColumns(introspectedTable);
        String packagePath = introspectedTable.getTableConfiguration().getProperties().getProperty("boPackage");
        if (StringUtils.isBlank(packagePath)) {
            throw new RuntimeException("StateAction模式中，Do必须指定Bo");
        } else {
            String doName = introspectedTable.getBaseRecordType();
            String boName = doName.substring(doName.lastIndexOf(".") + 1).replace("Do", "") + BoPlugin.JAVAFILE_POTFIX;
            boName = packagePath + "." + boName;
            this.createMethod(JavaVisibility.PUBLIC, "getBoQualifiedIntfName", "@Override", FullyQualifiedJavaType.getStringInstance(), (List)null, "return \"" + boName + "\";", topLevelClass);
            return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
        }
    }

    protected void createMethod(JavaVisibility visi, String name, String annotation, FullyQualifiedJavaType returnType, List<Parameter> parms, String body, TopLevelClass topLevelClass) {
        Method method = new Method(name);
        method.setVisibility(visi);
        if (StringUtils.isNotBlank(annotation)) {
            method.addAnnotation(annotation);
        }

        if (returnType != null) {
            method.setReturnType(returnType);
            topLevelClass.addImportedType(returnType);
        }

        if (parms != null && !parms.isEmpty()) {
            Iterator var10 = parms.iterator();

            while(var10.hasNext()) {
                Parameter parm = (Parameter)var10.next();
                method.addParameter(parm);
                topLevelClass.addImportedType(parm.getType());
            }
        }

        method.addBodyLine(body);
        topLevelClass.addMethod(method);
    }

    protected void createInnerMethod(JavaVisibility visi, String name, String annotation, FullyQualifiedJavaType returnType, List<Parameter> parms, String body, InnerClass innerClass, TopLevelClass outClass) {
        Method method = new Method(name);
        method.setVisibility(visi);
        if (StringUtils.isNotBlank(annotation)) {
            method.addAnnotation(annotation);
        }

        if (returnType != null) {
            method.setReturnType(returnType);
            outClass.addImportedType(returnType);
        }

        if (parms != null && !parms.isEmpty()) {
            Iterator var11 = parms.iterator();

            while(var11.hasNext()) {
                Parameter parm = (Parameter)var11.next();
                method.addParameter(parm);
                outClass.addImportedType(parm.getType());
            }
        }

        method.addBodyLine(body);
        innerClass.addMethod(method);
    }

    protected void createCriteriaMethod(String fieldName, String propertyName, FullyQualifiedJavaType type, InnerClass returnClazz, InnerClass innerClazz, TopLevelClass outClazz) {
        boolean hasLike = false;
        if (FullyQualifiedJavaType.getStringInstance().equals(type)) {
            hasLike = true;
        }

        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "IsNull", (String)null, returnClazz.getType(), (List)null, "addCriterion(\"" + fieldName + " is null\");\n            return (Criteria) this;", innerClazz, outClazz);
        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "IsNotNull", (String)null, returnClazz.getType(), (List)null, "addCriterion(\"" + fieldName + " is not null\");\n            return (Criteria) this;", innerClazz, outClazz);
        List<Parameter> typeParms = new ArrayList();
        Parameter typeParm = new Parameter(type, "value");
        typeParms.add(typeParm);
        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "EqualTo", (String)null, returnClazz.getType(), typeParms, "addCriterion(\"" + fieldName + " =\", value, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "NotEqualTo", (String)null, returnClazz.getType(), typeParms, "addCriterion(\"" + fieldName + " <>\", value, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "GreaterThan", (String)null, returnClazz.getType(), typeParms, "addCriterion(\"" + fieldName + " >\", value, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "GreaterThanOrEqualTo", (String)null, returnClazz.getType(), typeParms, "addCriterion(\"" + fieldName + " >=\", value, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "LessThan", (String)null, returnClazz.getType(), typeParms, "addCriterion(\"" + fieldName + " <\", value, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "LessThanOrEqualTo", (String)null, returnClazz.getType(), typeParms, "addCriterion(\"" + fieldName + " <=\", value, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
        List<Parameter> listParms = new ArrayList();
        Parameter listParm = new Parameter(new FullyQualifiedJavaType(List.class.getSimpleName() + "<" + type.getFullyQualifiedName() + ">"), "values");
        listParms.add(listParm);
        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "In", (String)null, returnClazz.getType(), listParms, "addCriterion(\"" + fieldName + " in\", values, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "NotIn", (String)null, returnClazz.getType(), listParms, "addCriterion(\"" + fieldName + " not in\", values, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
        List<Parameter> twoParms = new ArrayList();
        Parameter oneParm = new Parameter(type, "value1");
        Parameter twoParm = new Parameter(type, "value2");
        twoParms.add(oneParm);
        twoParms.add(twoParm);
        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "Between", (String)null, returnClazz.getType(), twoParms, "addCriterion(\"" + fieldName + " between\", value1, value2, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
        this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "NotBetween", (String)null, returnClazz.getType(), twoParms, "addCriterion(\"" + fieldName + " not between\", value1, value2, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
        if (hasLike) {
            this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "Like", (String)null, returnClazz.getType(), typeParms, "addCriterion(\"" + fieldName + " like\", value, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
            this.createInnerMethod(JavaVisibility.PUBLIC, "and" + propertyName + "NotLike", (String)null, returnClazz.getType(), typeParms, "addCriterion(\"" + fieldName + " not like\", value, \"" + propertyName + "\");\n            return (Criteria) this;", innerClazz, outClazz);
        }

    }

    protected void addBaseColumns(IntrospectedTable introspectedTable) {
        this.addColumn("ID", "INTEGER", "id", longType, true, true, introspectedTable);
        this.addColumn("GMT_CREATE", "TIMESTAMP", "gmtCreate", FullyQualifiedJavaType.getDateInstance(), false, false, introspectedTable);
        this.addColumn("CREATOR", "VARCHAR", "creator", FullyQualifiedJavaType.getStringInstance(), false, false, introspectedTable);
        this.addColumn("GMT_MODIFIED", "TIMESTAMP", "gmtModified", FullyQualifiedJavaType.getDateInstance(), false, false, introspectedTable);
        this.addColumn("MODIFIER", "VARCHAR", "modifier", FullyQualifiedJavaType.getStringInstance(), false, false, introspectedTable);
        this.addColumn("IS_DELETED", "CHAR", "isDeleted", FullyQualifiedJavaType.getStringInstance(), false, false, introspectedTable);
        String stateAction = introspectedTable.getTableConfiguration().getProperties().getProperty("stateAction");
        if (StringUtils.equalsIgnoreCase("true", stateAction)) {
            this.addColumn("status", "CHAR", "status", FullyQualifiedJavaType.getStringInstance(), false, false, introspectedTable);
        }

    }

    protected void addColumn(String name, String jdbcType, String javaName, FullyQualifiedJavaType javaType, boolean isSequence, boolean isKey, IntrospectedTable introspectedTable) {
        IntrospectedColumn column = new IntrospectedColumn();
        column.setActualColumnName(name);
        column.setJdbcTypeName(jdbcType);
        column.setJavaProperty(javaName);
        column.setSequenceColumn(isSequence);
        column.setFullyQualifiedJavaType(javaType);
        introspectedTable.addColumn(column);
        if (isKey) {
            introspectedTable.addPrimaryKeyColumn(name);
        }

    }

    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String lavaExample = LavaExample.class.getSimpleName();
        List<InnerClass> innerClasses = topLevelClass.getInnerClasses();
        InnerClass innerClass = null;
        InnerClass returnClass = null;
        Iterator var8 = innerClasses.iterator();

        while(var8.hasNext()) {
            InnerClass ic = (InnerClass)var8.next();
            FullyQualifiedJavaType type = ic.getType();
            if ("GeneratedCriteria".equals(type.getShortName())) {
                innerClass = ic;
            } else if ("Criteria".equals(type.getShortName())) {
                returnClass = ic;
            }
        }

        this.createCriteriaMethod("ID", "Id", longType, returnClass, innerClass, topLevelClass);
        this.createCriteriaMethod("GMT_CREATE", "GmtCreate", FullyQualifiedJavaType.getDateInstance(), returnClass, innerClass, topLevelClass);
        this.createCriteriaMethod("CREATOR", "Creator", FullyQualifiedJavaType.getStringInstance(), returnClass, innerClass, topLevelClass);
        this.createCriteriaMethod("GMT_MODIFIED", "GmtModified", FullyQualifiedJavaType.getDateInstance(), returnClass, innerClass, topLevelClass);
        this.createCriteriaMethod("MODIFIER", "Modifier", FullyQualifiedJavaType.getStringInstance(), returnClass, innerClass, topLevelClass);
        this.createCriteriaMethod("IS_DELETED", "IsDeleted", FullyQualifiedJavaType.getStringInstance(), returnClass, innerClass, topLevelClass);
        String stateAction = introspectedTable.getTableConfiguration().getProperties().getProperty("stateAction");
        if (StringUtils.equalsIgnoreCase("true", stateAction)) {
            this.createCriteriaMethod("STATUS", "Status", FullyQualifiedJavaType.getStringInstance(), returnClass, innerClass, topLevelClass);
        }

        topLevelClass.setSuperClass(lavaExample);
        topLevelClass.addImportedType(lavaExample);
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();
        this.updateDocumentNameSpace(introspectedTable, parentElement);
        this.moveDocumentInsertSql(parentElement);
        this.sqlMapInsertSelectiveForMutilDatabaseGenerated(parentElement, introspectedTable);
        this.moveDocumentUpdateByPrimaryKeySql(parentElement);
        this.sqlDeleteByPrimaryKeyForMutilDatabaseGenerated(parentElement, introspectedTable);
        this.generateMultiDbPageSql(parentElement, introspectedTable);
        this.generateDataAccessSql(parentElement);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    private void generateMultiDbPageSql(XmlElement parentElement, IntrospectedTable introspectedTable) {
        XmlElement oraclePrefixElement = new XmlElement("sql");
        this.context.getCommentGenerator().addComment(oraclePrefixElement);
        oraclePrefixElement.addAttribute(new Attribute("id", "OracleDialectPrefix"));
        XmlElement oraclePageStart = new XmlElement("if");
        oraclePageStart.addAttribute(new Attribute("test", "page != null"));
        oraclePageStart.addElement(new TextElement("select * from ( select row_.*, rownum rownum_ from ( "));
        oraclePrefixElement.addElement(oraclePageStart);
        parentElement.addElement(oraclePrefixElement);
        XmlElement oracleSuffixElement = new XmlElement("sql");
        this.context.getCommentGenerator().addComment(oracleSuffixElement);
        oracleSuffixElement.addAttribute(new Attribute("id", "OracleDialectSuffix"));
        XmlElement oraclePageEnd = new XmlElement("if");
        oraclePageEnd.addAttribute(new Attribute("test", "page != null"));
        oraclePageEnd.addElement(new TextElement("<![CDATA[ ) row_ ) where rownum_ >= #{page.begin} and rownum_ < #{page.end} ]]>"));
        oracleSuffixElement.addElement(oraclePageEnd);
        parentElement.addElement(oracleSuffixElement);
        String tableName = introspectedTable.getTableConfiguration().getTableName();
        XmlElement mysqlPrefixElement = new XmlElement("sql");
        this.context.getCommentGenerator().addComment(mysqlPrefixElement);
        mysqlPrefixElement.addAttribute(new Attribute("id", "MysqlDialectPrefix"));
        XmlElement mysqlPageStart = new XmlElement("if");
        mysqlPageStart.addAttribute(new Attribute("test", "page != null"));
        mysqlPageStart.addElement(new TextElement("from " + tableName + " where id in ( select id from ( select id "));
        mysqlPrefixElement.addElement(mysqlPageStart);
        parentElement.addElement(mysqlPrefixElement);
        XmlElement mysqlSuffixElement = new XmlElement("sql");
        this.context.getCommentGenerator().addComment(mysqlSuffixElement);
        mysqlSuffixElement.addAttribute(new Attribute("id", "MysqlDialectSuffix"));
        XmlElement mysqlPageEnd = new XmlElement("if");
        mysqlPageEnd.addAttribute(new Attribute("test", "page != null"));
        mysqlPageEnd.addElement(new TextElement("<![CDATA[ limit #{page.begin}, #{page.length} ) as temp_page_table) ]]>"));
        mysqlSuffixElement.addElement(mysqlPageEnd);
        parentElement.addElement(mysqlSuffixElement);
    }

    private void generateDataAccessSql(XmlElement parentElement) {
        XmlElement fullOrgPathElement = new XmlElement("sql");
        this.context.getCommentGenerator().addComment(fullOrgPathElement);
        fullOrgPathElement.addAttribute(new Attribute("id", "fullOrgPath"));
        XmlElement pageStart = new XmlElement("if");
        pageStart.addAttribute(new Attribute("test", "fullOrgPath != null"));
        pageStart.addElement(new TextElement(SQLMAP_COMMON_POTFIX_PVG));
        fullOrgPathElement.addElement(pageStart);
        parentElement.addElement(fullOrgPathElement);
        XmlElement ownerElement = new XmlElement("sql");
        this.context.getCommentGenerator().addComment(ownerElement);
        ownerElement.addAttribute(new Attribute("id", "owner"));
        XmlElement pageEnd = new XmlElement("if");
        pageEnd.addAttribute(new Attribute("test", "owner != null"));
        pageEnd.addElement(new TextElement(SQLMAP_COMMON_POTFIX_OWNER));
        ownerElement.addElement(pageEnd);
        parentElement.addElement(ownerElement);
    }

    private void moveDocumentUpdateByPrimaryKeySql(XmlElement parentElement) {
        XmlElement updateElement = null;
        Iterator var4 = parentElement.getElements().iterator();

        while(true) {
            while(true) {
                XmlElement xmlElement;
                do {
                    if (!var4.hasNext()) {
                        parentElement.getElements().remove(updateElement);
                        return;
                    }

                    Element element = (Element)var4.next();
                    xmlElement = (XmlElement)element;
                } while(!xmlElement.getName().equals("update"));

                Iterator var7 = xmlElement.getAttributes().iterator();

                while(var7.hasNext()) {
                    Attribute attribute = (Attribute)var7.next();
                    if (attribute.getValue().equals("updateByPrimaryKey")) {
                        updateElement = xmlElement;
                        break;
                    }
                }
            }
        }
    }

    private void moveDocumentInsertSql(XmlElement parentElement) {
        XmlElement insertElement = null;
        Iterator var4 = parentElement.getElements().iterator();

        while(true) {
            while(true) {
                XmlElement xmlElement;
                do {
                    if (!var4.hasNext()) {
                        parentElement.getElements().remove(insertElement);
                        return;
                    }

                    Element element = (Element)var4.next();
                    xmlElement = (XmlElement)element;
                } while(!xmlElement.getName().equals("insert"));

                Iterator var7 = xmlElement.getAttributes().iterator();

                while(var7.hasNext()) {
                    Attribute attribute = (Attribute)var7.next();
                    if (attribute.getValue().equals("insert")) {
                        insertElement = xmlElement;
                        break;
                    }
                }
            }
        }
    }

    private void updateDocumentNameSpace(IntrospectedTable introspectedTable, XmlElement parentElement) {
        Attribute namespaceAttribute = null;
        Iterator var5 = parentElement.getAttributes().iterator();

        while(var5.hasNext()) {
            Attribute attribute = (Attribute)var5.next();
            if (attribute.getName().equals("namespace")) {
                namespaceAttribute = attribute;
            }
        }

        parentElement.getAttributes().remove(namespaceAttribute);
        parentElement.getAttributes().add(new Attribute("namespace", introspectedTable.getMyBatis3JavaMapperType() + JAVAFILE_POTFIX));
    }

    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        TextElement text = new TextElement(SQLMAP_COMMON_POTFIX);
        element.addElement(text);
        return super.sqlMapSelectByPrimaryKeyElementGenerated(element, introspectedTable);
    }

    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<Element> elements = element.getElements();
        XmlElement setItem = null;
        int modifierItemIndex = -1;
        int gmtModifiedItemIndex = -1;
        Iterator var8 = elements.iterator();

        while(true) {
            Element e;
            do {
                if (!var8.hasNext()) {
                    if (modifierItemIndex != -1 && setItem != null) {
                        this.addXmlElementModifier(setItem, modifierItemIndex);
                    }

                    if (gmtModifiedItemIndex != -1 && setItem != null) {
                        this.addGmtModifiedXmlElement(setItem, gmtModifiedItemIndex);
                    }

                    TextElement text = new TextElement(SQLMAP_COMMON_POTFIX);
                    element.addElement(text);
                    return super.sqlMapUpdateByPrimaryKeySelectiveElementGenerated(element, introspectedTable);
                }

                e = (Element)var8.next();
            } while(!(e instanceof XmlElement));

            setItem = (XmlElement)e;

            for(int i = 0; i < setItem.getElements().size(); ++i) {
                XmlElement xmlElement = (XmlElement)setItem.getElements().get(i);
                Iterator var12 = xmlElement.getAttributes().iterator();

                while(var12.hasNext()) {
                    Attribute att = (Attribute)var12.next();
                    if (att.getValue().equals("modifier != null")) {
                        modifierItemIndex = i;
                        break;
                    }

                    if (att.getValue().equals("gmtModified != null")) {
                        gmtModifiedItemIndex = i;
                        break;
                    }
                }
            }
        }
    }

    private void addGmtModifiedXmlElement(XmlElement setItem, int gmtModifiedItemIndex) {
        XmlElement defaultGmtModified = new XmlElement("if");
        defaultGmtModified.addAttribute(new Attribute("test", "gmtModified == null"));
        XmlElement oracleModifiedTest = new XmlElement("if");
        oracleModifiedTest.addAttribute(new Attribute("test", "_databaseId == 'oracle'"));
        oracleModifiedTest.addElement(new TextElement("GMT_MODIFIED = sysdate,"));
        defaultGmtModified.addElement(oracleModifiedTest);
        XmlElement mysqlModifiedTest = new XmlElement("if");
        mysqlModifiedTest.addAttribute(new Attribute("test", "_databaseId == 'mysql'"));
        mysqlModifiedTest.addElement(new TextElement("GMT_MODIFIED = current_timestamp,"));
        defaultGmtModified.addElement(mysqlModifiedTest);
        setItem.getElements().add(gmtModifiedItemIndex + 1, defaultGmtModified);
    }

    private void addXmlElementModifier(XmlElement setItem, int modifierItemIndex) {
        XmlElement defaultmodifier = new XmlElement("if");
        defaultmodifier.addAttribute(new Attribute("test", "modifier == null"));
        defaultmodifier.addElement(new TextElement("MODIFIER = 'system',"));
        setItem.getElements().add(modifierItemIndex + 1, defaultmodifier);
    }

    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        TextElement text = new TextElement(SQLMAP_COMMON_POTFIX);
        element.addElement(text);
        return super.sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    public void sqlInsertSelectiveGenerated(XmlElement parentElement, String dbType, XmlElement element, IntrospectedTable introspectedTable) {
        List<Element> elements = element.getElements();
        parentElement.addElement(element);
        Element fixE = (Element)elements.get(5);
        XmlElement fieldItem;
        if (dbType != null && dbType.equals("mysql")) {
            element.addAttribute(new Attribute("useGeneratedKeys", "true"));
            element.addAttribute(new Attribute("keyProperty", "id"));
            element.addAttribute(new Attribute("databaseId", "mysql"));
            if (fixE instanceof XmlElement && ((XmlElement)fixE).getName().equals("selectKey")) {
                elements.remove(5);
            }
        } else if (dbType != null && dbType.equals("oracle")) {
            element.addAttribute(new Attribute("databaseId", "oracle"));
            if (!(fixE instanceof XmlElement) || !((XmlElement)fixE).getName().equals("selectKey")) {
                fieldItem = new XmlElement("selectKey");
                fieldItem.addAttribute(new Attribute("keyProperty", "id"));
                fieldItem.addAttribute(new Attribute("order", "BEFORE"));
                fieldItem.addAttribute(new Attribute("resultType", "java.lang.Long"));
                TextElement selectKeySql = new TextElement("select SEQ_" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + ".nextval as ID from dual");
                fieldItem.addElement(selectKeySql);
                elements.add(5, fieldItem);
            }
        }

        fieldItem = null;
        XmlElement valueItem = null;
        Iterator var10 = elements.iterator();

        while(true) {
            while(true) {
                XmlElement xmlElement;
                do {
                    Element e;
                    do {
                        if (!var10.hasNext()) {
                            XmlElement defaultGmtModified;
                            XmlElement defaultCreator;
                            XmlElement defaultIsDeleted;
                            XmlElement defaultGmtCreate;
                            if (fieldItem != null) {
                                defaultGmtCreate = new XmlElement("if");
                                defaultGmtCreate.addAttribute(new Attribute("test", "gmtCreate == null"));
                                defaultGmtCreate.addElement(new TextElement("GMT_CREATE,"));
                                fieldItem.addElement(1, defaultGmtCreate);
                                defaultGmtModified = new XmlElement("if");
                                defaultGmtModified.addAttribute(new Attribute("test", "gmtModified == null"));
                                defaultGmtModified.addElement(new TextElement("GMT_MODIFIED,"));
                                fieldItem.addElement(1, defaultGmtModified);
                                xmlElement = new XmlElement("if");
                                xmlElement.addAttribute(new Attribute("test", "modifier == null"));
                                xmlElement.addElement(new TextElement("MODIFIER,"));
                                fieldItem.addElement(1, xmlElement);
                                defaultCreator = new XmlElement("if");
                                defaultCreator.addAttribute(new Attribute("test", "creator == null"));
                                defaultCreator.addElement(new TextElement("CREATOR,"));
                                fieldItem.addElement(1, defaultCreator);
                                defaultIsDeleted = new XmlElement("if");
                                defaultIsDeleted.addAttribute(new Attribute("test", "isDeleted == null"));
                                defaultIsDeleted.addElement(new TextElement("IS_DELETED,"));
                                fieldItem.addElement(1, defaultIsDeleted);
                            }

                            if (valueItem != null) {
                                defaultGmtCreate = new XmlElement("if");
                                defaultGmtCreate.addAttribute(new Attribute("test", "gmtCreate == null"));
                                if (dbType != null && dbType.equals("mysql")) {
                                    defaultGmtCreate.addElement(new TextElement("current_timestamp,"));
                                } else if (dbType != null && dbType.equals("oracle")) {
                                    defaultGmtCreate.addElement(new TextElement("sysdate,"));
                                }

                                valueItem.addElement(1, defaultGmtCreate);
                                defaultGmtModified = new XmlElement("if");
                                defaultGmtModified.addAttribute(new Attribute("test", "gmtModified == null"));
                                if (dbType != null && dbType.equals("mysql")) {
                                    defaultGmtModified.addElement(new TextElement("current_timestamp,"));
                                } else if (dbType != null && dbType.equals("oracle")) {
                                    defaultGmtModified.addElement(new TextElement("sysdate,"));
                                }

                                valueItem.addElement(1, defaultGmtModified);
                                xmlElement = new XmlElement("if");
                                xmlElement.addAttribute(new Attribute("test", "modifier == null"));
                                xmlElement.addElement(new TextElement("'system',"));
                                valueItem.addElement(1, xmlElement);
                                defaultCreator = new XmlElement("if");
                                defaultCreator.addAttribute(new Attribute("test", "creator == null"));
                                defaultCreator.addElement(new TextElement("'system',"));
                                valueItem.addElement(1, defaultCreator);
                                defaultIsDeleted = new XmlElement("if");
                                defaultIsDeleted.addAttribute(new Attribute("test", "isDeleted == null"));
                                defaultIsDeleted.addElement(new TextElement("'n',"));
                                valueItem.addElement(1, defaultIsDeleted);
                            }

                            return;
                        }

                        e = (Element)var10.next();
                    } while(!(e instanceof XmlElement));

                    xmlElement = (XmlElement)e;
                } while(!xmlElement.getName().equals("trim"));

                Iterator var13 = xmlElement.getAttributes().iterator();

                while(var13.hasNext()) {
                    Attribute arr = (Attribute)var13.next();
                    if (arr.getValue().equals("(")) {
                        fieldItem = xmlElement;
                        break;
                    }

                    if (arr.getValue().equals("values (")) {
                        valueItem = xmlElement;
                        break;
                    }
                }
            }
        }
    }

    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        Parameter parameter = new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "record");
        method.getParameters().clear();
        method.addParameter(parameter);
        return super.clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement lastXmlE = (XmlElement)element.getElements().remove(element.getElements().size() - 1);
        XmlElement oraclePrefixTest = new XmlElement("if");
        oraclePrefixTest.addAttribute(new Attribute("test", "_databaseId == 'oracle'"));
        XmlElement oraclePrefix = new XmlElement("include");
        oraclePrefix.addAttribute(new Attribute("refid", "OracleDialectPrefix"));
        oraclePrefixTest.addElement(oraclePrefix);
        element.getElements().add(5, oraclePrefixTest);
        XmlElement mysqlPrefixTest = new XmlElement("if");
        mysqlPrefixTest.addAttribute(new Attribute("test", "_databaseId == 'mysql'"));
        XmlElement mysqlPrefix = new XmlElement("include");
        mysqlPrefix.addAttribute(new Attribute("refid", "MysqlDialectPrefix"));
        mysqlPrefixTest.addElement(mysqlPrefix);
        element.getElements().add(9, mysqlPrefixTest);
        XmlElement isdeletedElement = new XmlElement("if");
        isdeletedElement.addAttribute(new Attribute("test", "oredCriteria.size != 0"));
        isdeletedElement.addElement(new TextElement(SQLMAP_COMMON_POTFIX));
        element.addElement(isdeletedElement);
        isdeletedElement = new XmlElement("if");
        isdeletedElement.addAttribute(new Attribute("test", "oredCriteria.size == 0"));
        isdeletedElement.addElement(new TextElement("where is_deleted = 'n'"));
        element.addElement(isdeletedElement);
        XmlElement fullOrgPath = new XmlElement("include");
        fullOrgPath.addAttribute(new Attribute("refid", "fullOrgPath"));
        element.addElement(fullOrgPath);
        XmlElement owner = new XmlElement("include");
        owner.addAttribute(new Attribute("refid", "owner"));
        element.addElement(owner);
        element.addElement(lastXmlE);
        XmlElement oracleSuffixTest = new XmlElement("if");
        oracleSuffixTest.addAttribute(new Attribute("test", "_databaseId == 'oracle'"));
        XmlElement oracleSuffix = new XmlElement("include");
        oracleSuffix.addAttribute(new Attribute("refid", "OracleDialectSuffix"));
        oracleSuffixTest.addElement(oracleSuffix);
        element.getElements().add(oracleSuffixTest);
        XmlElement mysqlSuffixTest = new XmlElement("if");
        mysqlSuffixTest.addAttribute(new Attribute("test", "_databaseId == 'mysql'"));
        XmlElement mysqlSuffix = new XmlElement("include");
        mysqlSuffix.addAttribute(new Attribute("refid", "MysqlDialectSuffix"));
        mysqlSuffixTest.addElement(mysqlSuffix);
        element.getElements().add(mysqlSuffixTest);
        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    public boolean sqlMapCountByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement isNotNullElement = new XmlElement("if");
        isNotNullElement.addAttribute(new Attribute("test", "oredCriteria.size != 0"));
        isNotNullElement.addElement(new TextElement(SQLMAP_COMMON_POTFIX));
        element.addElement(isNotNullElement);
        isNotNullElement = new XmlElement("if");
        isNotNullElement.addAttribute(new Attribute("test", "oredCriteria.size == 0"));
        isNotNullElement.addElement(new TextElement("where is_deleted = 'n'"));
        element.addElement(isNotNullElement);
        XmlElement fullOrgPath = new XmlElement("include");
        fullOrgPath.addAttribute(new Attribute("refid", "fullOrgPath"));
        element.addElement(fullOrgPath);
        XmlElement owner = new XmlElement("include");
        owner.addAttribute(new Attribute("refid", "owner"));
        element.addElement(owner);
        return super.sqlMapCountByExampleElementGenerated(element, introspectedTable);
    }

    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        String[] splitFile = introspectedTable.getMyBatis3XmlMapperFileName().split("\\.");
        String fileNameExt = null;
        if (splitFile[0] != null) {
            fileNameExt = splitFile[0] + XMLFILE_POSTFIX + ".xml";
        }

        if (this.isExistExtFile(this.context.getSqlMapGeneratorConfiguration().getTargetProject(), introspectedTable.getMyBatis3XmlMapperPackage(), fileNameExt)) {
            return super.contextGenerateAdditionalXmlFiles(introspectedTable);
        } else {
            Document document = new Document("-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
            XmlElement root = new XmlElement("mapper");
            document.setRootElement(root);
            String namespace = introspectedTable.getMyBatis3SqlMapNamespace() + XMLFILE_POSTFIX;
            root.addAttribute(new Attribute("namespace", namespace));
            GeneratedXmlFile gxf = new GeneratedXmlFile(document, fileNameExt, introspectedTable.getMyBatis3XmlMapperPackage(), this.context.getSqlMapGeneratorConfiguration().getTargetProject(), false, this.context.getXmlFormatter());
            List<GeneratedXmlFile> answer = new ArrayList(1);
            answer.add(gxf);
            return answer;
        }
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType() + JAVAFILE_POTFIX);
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        this.context.getCommentGenerator().addJavaFileComment(interfaze);
        FullyQualifiedJavaType annotation = new FullyQualifiedJavaType(ANNOTATION_RESOURCE);
        //interfaze.addImportedType(annotation);
        String doName = introspectedTable.getBaseRecordType();
        String exmpName = introspectedTable.getExampleType();
        FullyQualifiedJavaType lavaMapperType = new FullyQualifiedJavaType(LavaMapper.class.getSimpleName() + "<" + doName + "," + exmpName + ">");
        interfaze.addSuperInterface(lavaMapperType);
        interfaze.addImportedType(lavaMapperType);
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(interfaze, this.context.getJavaModelGeneratorConfiguration().getTargetProject(), this.context.getProperty("javaFileEncoding"), this.context.getJavaFormatter());
        if (this.isExistExtFile(generatedJavaFile.getTargetProject(), generatedJavaFile.getTargetPackage(), generatedJavaFile.getFileName())) {
            return super.contextGenerateAdditionalJavaFiles(introspectedTable);
        } else {
            List<GeneratedJavaFile> generatedJavaFiles = new ArrayList(1);
            generatedJavaFile.getFileName();
            generatedJavaFiles.add(generatedJavaFile);
            return generatedJavaFiles;
        }
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

    private void sqlDeleteByPrimaryKeyForMutilDatabaseGenerated(XmlElement parentElement, IntrospectedTable introspectedTable) {
        XmlElement oracleDeleteElement = new XmlElement("update");
        this.context.getCommentGenerator().addComment(oracleDeleteElement);
        Attribute oracleIdAttr = new Attribute("id", "deleteByPrimaryKey");
        Attribute oracleParameterTypeAttr = new Attribute("parameterType", introspectedTable.getBaseRecordType());
        Attribute oracleDatabaseIdAttr = new Attribute("databaseId", "oracle");
        oracleDeleteElement.getAttributes().add(oracleIdAttr);
        oracleDeleteElement.getAttributes().add(oracleParameterTypeAttr);
        oracleDeleteElement.getAttributes().add(oracleDatabaseIdAttr);
        TextElement oracleSqlElement = new TextElement("update " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() + " set is_deleted = 'y',modifier=#{modifier,jdbcType=VARCHAR},gmt_Modified=sysdate where ID = #{id,jdbcType=NUMERIC}");
        oracleDeleteElement.getElements().add(oracleSqlElement);
        parentElement.addElement(oracleDeleteElement);
        XmlElement mysqlDeleteElement = new XmlElement("update");
        this.context.getCommentGenerator().addComment(mysqlDeleteElement);
        Attribute mysqlIdAttr = new Attribute("id", "deleteByPrimaryKey");
        Attribute mysqlParameterTypeAttr = new Attribute("parameterType", introspectedTable.getBaseRecordType());
        Attribute mysqlDatabaseIdAttr = new Attribute("databaseId", "mysql");
        mysqlDeleteElement.getAttributes().add(mysqlIdAttr);
        mysqlDeleteElement.getAttributes().add(mysqlParameterTypeAttr);
        mysqlDeleteElement.getAttributes().add(mysqlDatabaseIdAttr);
        TextElement mysqlSqlElement = new TextElement("update " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() + " set is_deleted = 'y',modifier=#{modifier,jdbcType=VARCHAR},gmt_Modified=current_timestamp where ID = #{id,jdbcType=BIGINT}");
        mysqlDeleteElement.getElements().add(mysqlSqlElement);
        parentElement.addElement(mysqlDeleteElement);
    }

    private void sqlMapInsertSelectiveForMutilDatabaseGenerated(XmlElement parentElement, IntrospectedTable introspectedTable) {
        XmlElement oldElement = null;
        XmlElement mysqlElement = null;
        XmlElement oracleElement = null;
        Iterator var7 = parentElement.getElements().iterator();

        while(true) {
            while(true) {
                XmlElement xmlElement;
                do {
                    if (!var7.hasNext()) {
                        parentElement.getElements().remove(oldElement);
                        this.sqlInsertSelectiveGenerated(parentElement, "mysql", mysqlElement, introspectedTable);
                        this.sqlInsertSelectiveGenerated(parentElement, "oracle", oracleElement, introspectedTable);
                        return;
                    }

                    Element element = (Element)var7.next();
                    xmlElement = (XmlElement)element;
                } while(!xmlElement.getName().equals("insert"));

                Iterator var10 = xmlElement.getAttributes().iterator();

                while(var10.hasNext()) {
                    Attribute attribute = (Attribute)var10.next();
                    if (attribute.getValue().equals("insertSelective")) {
                        oldElement = xmlElement;
                        mysqlElement = (XmlElement)this.copyElement(xmlElement);
                        oracleElement = (XmlElement)this.copyElement(xmlElement);
                        break;
                    }
                }
            }
        }
    }

    private Element copyElement(Element element) {
        if (!(element instanceof XmlElement)) {
            if (element instanceof TextElement) {
                TextElement textElement = (TextElement)element;
                TextElement newElement = new TextElement(textElement.getContent());
                return newElement;
            } else {
                return null;
            }
        } else {
            XmlElement xmlElement = (XmlElement)element;
            String name = xmlElement.getName();
            List<Attribute> aList = xmlElement.getAttributes();
            List<Element> eList = xmlElement.getElements();
            XmlElement newElement = new XmlElement(name);
            int i;
            if (aList != null) {
                for(i = 0; i < aList.size(); ++i) {
                    newElement.addAttribute(new Attribute(((Attribute)aList.get(i)).getName(), ((Attribute)aList.get(i)).getValue()));
                }
            }

            if (eList != null) {
                for(i = 0; i < eList.size(); ++i) {
                    Element e = this.copyElement((Element)eList.get(i));
                    newElement.addElement(e);
                }
            }

            return newElement;
        }
    }

    public boolean validate(List<String> warnings) {
        return true;
    }

    public static void main(String[] args) {
        String config = MultiDbPaginationPlugin.class.getClassLoader().getResource("generatorConfig.xml").getFile();
        String[] arg = new String[]{"-configfile", config};
        ShellRunner.main(arg);
    }
}
