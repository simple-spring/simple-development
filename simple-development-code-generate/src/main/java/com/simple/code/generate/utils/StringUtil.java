package com.simple.code.generate.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luke
 */
public class StringUtil {

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * split.
     *
     * @param ch char.
     * @return string array.
     */
    public static String[] split(String str, char ch) {
        List<String> list = null;
        char c;
        int ix = 0, len = str.length();
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (c == ch) {
                if (list == null) {
                    list = new ArrayList<String>();
                }
                list.add(str.substring(ix, i));
                ix = i + 1;
            }
        }
        if (ix > 0) {
            list.add(str.substring(ix));
        }
        return list == null ? EMPTY_STRING_ARRAY : (String[]) list.toArray(EMPTY_STRING_ARRAY);
    }
}
