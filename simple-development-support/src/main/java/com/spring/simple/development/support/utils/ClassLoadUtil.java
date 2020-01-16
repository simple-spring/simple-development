package com.spring.simple.development.support.utils;

import javassist.*;
import javassist.bytecode.*;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

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
     * @param aclass
     * @param annotationName
     * @param packageNames
     * @param simpleAnnotationName
     * @return void
     * @author liko.wang
     * @Date 2019/12/20/020 17:51
     * @Description 类添加注解 有参数
     **/
    public static Class javassistCompile(Class aclass, String annotationName, List<String> packageNames, String simpleAnnotationName) throws NotFoundException, CannotCompileException, ClassNotFoundException {
        ClassPool classPool = ClassPool.getDefault();
        classPool.appendClassPath(new ClassClassPath(aclass));
        // 如果CtClass通过writeFile(),toClass(),toBytecode()转换了类文件，javassist冻结了CtClass对象。
        CtClass clazz = classPool.get(aclass.getName());
        clazz.stopPruning(true);
        // Defrost()执行后，CtClass对象将可以再次修改。
        clazz.defrost();
        ClassFile classFile = clazz.getClassFile();

        ConstPool constPool = classFile.getConstPool();
        Annotation tableAnnotation = new Annotation(annotationName, constPool);
        if (packageNames != null && simpleAnnotationName != null) {
            ArrayMemberValue arrayMemberValue = getArrayMemberValue(packageNames, constPool);
            tableAnnotation.addMemberValue(simpleAnnotationName, arrayMemberValue);
        }
        // 获取运行时注解属性
        AnnotationsAttribute attribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
        attribute.addAnnotation(tableAnnotation);
        classFile.addAttribute(attribute);
        classFile.setVersionToJava5();
        // 当前ClassLoader中必须尚未加载该实体。（同一个ClassLoader加载同一个类只会加载一次）
        ClassLoadUtil loader = new ClassLoadUtil(ClassLoadUtil.class.getClassLoader());
        Class aClass = clazz.toClass(loader, null);
        return aClass;
    }

    /**
     * @param aclass
     * @param annotationName
     * @return void
     * @author liko.wang
     * @Date 2019/12/20/020 17:51
     * @Description 类添加注解 无参数
     **/
    public static Class javassistCompileNoParam(Class aclass, String annotationName) throws NotFoundException, CannotCompileException, ClassNotFoundException {
        ClassPool classPool = ClassPool.getDefault();
        classPool.appendClassPath(new ClassClassPath(aclass));
        // 如果CtClass通过writeFile(),toClass(),toBytecode()转换了类文件，javassist冻结了CtClass对象。
        CtClass clazz = classPool.get(aclass.getName());
        clazz.stopPruning(true);
        // Defrost()执行后，CtClass对象将可以再次修改。
        clazz.defrost();
        ClassFile classFile = clazz.getClassFile();

        ConstPool constPool = classFile.getConstPool();
        // 类附上注解
        AnnotationsAttribute classAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation(annotationName, constPool);
        classAttr.addAnnotation(annotation);
        classFile.addAttribute(classAttr);

        // 当前ClassLoader中必须尚未加载该实体。（同一个ClassLoader加载同一个类只会加载一次）
        ClassLoadUtil loader = new ClassLoadUtil(ClassLoadUtil.class.getClassLoader());
        Class aClass = clazz.toClass(loader, null);
        return aClass;
    }

    private static ArrayMemberValue getArrayMemberValue(List<String> packageNames, ConstPool constPool) {
        ArrayMemberValue arrayMemberValue = new ArrayMemberValue(constPool);
        List<StringMemberValue> stringMemberValueList = new ArrayList<>();
        for (String str : packageNames) {
            StringMemberValue stringMemberValue = new StringMemberValue(str, constPool);
            stringMemberValueList.add(stringMemberValue);
        }
        arrayMemberValue.setValue(stringMemberValueList.toArray(new StringMemberValue[stringMemberValueList.size()]));
        return arrayMemberValue;
    }

    /**
     * @param
     * @return void
     * @author liko.wang
     * @Date 2019/12/20/020 17:52
     * @Description 修改属性注解参数值(未验证)
     **/
    public void javassistCompileFild() throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        //获取需要修改的类
        CtClass ct = pool.get("com.spring.simple.development.core.init.RootConfig");
        //获取类里的所有方法
        CtMethod[] cms = ct.getDeclaredMethods();
        CtMethod cm = cms[0];
        System.out.println("方法名称====" + cm.getName());

        MethodInfo minInfo = cm.getMethodInfo();
        //获取类里的em属性
        CtField cf = ct.getField("proxycreate");
        FieldInfo fieldInfo = cf.getFieldInfo();

        System.out.println("属性名称===" + cf.getName());

        ConstPool cp = fieldInfo.getConstPool();
        //获取注解信息
        AnnotationsAttribute attribute2 = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation("org.springframework.context.annotation.ComponentScan", cp);

        //修改名称为unitName的注解
        annotation.addMemberValue("basePackages", new StringMemberValue("124", cp));
        attribute2.setAnnotation(annotation);
        minInfo.addAttribute(attribute2);

        //打印修改后方法
        Annotation annotation2 = attribute2.getAnnotation("org.springframework.context.annotation.ComponentScan");
        String text = ((StringMemberValue) annotation2.getMemberValue("basePackages")).getValue();

        System.out.println("修改后的注解名称===" + text);

    }
}
