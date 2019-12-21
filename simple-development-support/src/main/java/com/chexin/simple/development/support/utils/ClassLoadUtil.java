package com.chexin.simple.development.support.utils;

import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 16:47
 * @Description //TODO
 **/
public class ClassLoadUtil extends ClassLoader {
    private ClassLoader parent;

    public ClassLoadUtil(ClassLoader parent) {
        this.parent = parent;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return this.loadClass(name, false);
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = this.findLoadedClass(name);
        if (null != parent)
            clazz = parent.loadClass(name);
        if (null == clazz)
            this.findSystemClass(name);
        if (null == clazz)
            throw new ClassNotFoundException();
        if (resolve)
            this.resolveClass(clazz);
        return clazz;
    }
    /**
     * @author liko.wang
     * @Date 2019/12/20/020 17:51
     * @param className
     * @param annotationName
     * @param packageNames
     * @param simpleAnnotationName 
     * @return void
     * @Description 修改类注册参数值
     **/
    public static Class javassistCompile(String className, String annotationName, List<String> packageNames, String simpleAnnotationName) throws NotFoundException, CannotCompileException, ClassNotFoundException {
        ClassPool classPool = ClassPool.getDefault();
        classPool.appendClassPath(new ClassClassPath(Class.forName(className)));
        // 如果CtClass通过writeFile(),toClass(),toBytecode()转换了类文件，javassist冻结了CtClass对象。
        // 以后是不允许修改这个 CtClass对象。这是为了警告开发人员当他们试图修改一个类文件时，已经被JVM载入的类不允许被重新载入。
        CtClass clazz = classPool.get(className);
        clazz.stopPruning(true);
        // Defrost()执行后，CtClass对象将可以再次修改。
        clazz.defrost();
        ClassFile classFile = clazz.getClassFile();

        ConstPool constPool = classFile.getConstPool();
        Annotation tableAnnotation = new Annotation(annotationName, constPool);
        ArrayMemberValue arrayMemberValue = new ArrayMemberValue(constPool);
        List<StringMemberValue> stringMemberValueList = new ArrayList<>();
        for (String str:packageNames){
            StringMemberValue stringMemberValue = new StringMemberValue(str, constPool);
            stringMemberValueList.add(stringMemberValue);
        }
        arrayMemberValue.setValue(stringMemberValueList.toArray(new StringMemberValue[stringMemberValueList.size()]));
        tableAnnotation.addMemberValue(simpleAnnotationName, arrayMemberValue);
        // 获取运行时注解属性
        AnnotationsAttribute attribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
        attribute.addAnnotation(tableAnnotation);
        classFile.addAttribute(attribute);
        classFile.setVersionToJava5();
        // 当前ClassLoader中必须尚未加载该实体。（同一个ClassLoader加载同一个类只会加载一次）
        ClassLoadUtil loader = new ClassLoadUtil(ClassLoadUtil.class.getClassLoader());
        Class aClass = clazz.toClass(loader, null);
        ComponentScan annotation = (ComponentScan)aClass.getAnnotation(ComponentScan.class);
        System.out.println(annotation.basePackages().toString());
        return aClass;
    }
    /**
     * @author liko.wang
     * @Date 2019/12/20/020 17:52
     * @param
     * @return void
     * @Description 修改属性注解参数值(未验证)
     **/
    public void test(){
        //        ClassPool pool = ClassPool.getDefault();
//        //获取需要修改的类
//        CtClass ct = pool.get("com.chexin.simple.development.core.init.RootConfig");
//        //获取类里的所有方法
//        CtMethod[] cms = ct.getDeclaredMethods();
//        CtMethod cm = cms[0];
//        System.out.println("方法名称====" + cm.getName());
//
//        MethodInfo minInfo = cm.getMethodInfo();
//        //获取类里的em属性
//        CtField cf = ct.getField("proxycreate");
//        FieldInfo fieldInfo = cf.getFieldInfo();
//
//        System.out.println("属性名称===" + cf.getName());
//
//        ConstPool cp = fieldInfo.getConstPool();
//        //获取注解信息
//        AnnotationsAttribute attribute2 = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
//        Annotation annotation = new Annotation("org.springframework.context.annotation.ComponentScan", cp);
//
//        //修改名称为unitName的注解
//        annotation.addMemberValue("basePackages", new StringMemberValue("124", cp));
//        attribute2.setAnnotation(annotation);
//        minInfo.addAttribute(attribute2);
//
//        //打印修改后方法
//        Annotation annotation2 = attribute2.getAnnotation("org.springframework.context.annotation.ComponentScan");
//        String text = ((StringMemberValue) annotation2.getMemberValue("basePackages")).getValue();
//
//        System.out.println("修改后的注解名称===" + text);

    }
}
