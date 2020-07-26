package com.spring.simple.development.support.exception;

/**
 * 记录当前线程的语言
 *
 * @author luke
 */
public class LanguageThreadLocal {
    private static ThreadLocal<String> languageThreadLocal = new ThreadLocal<>();

    public static void setLanguage(String language) {
        languageThreadLocal.set(language);
    }

    public static String getLanguage() {
        return languageThreadLocal.get();
    }

    public static void removeLanguage() {
        languageThreadLocal.remove();
    }
}
