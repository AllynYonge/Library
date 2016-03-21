package com.example.libaray.common;

import java.util.Collection;
import java.util.Map;

/**
 * 辅助判断
 *
 * @author mty
 * @date 2013-6-10下午5:50:57
 */
public class Check {

    public static boolean isEmpty(CharSequence str) {
        return isNull(str) || str.length() == 0;
    }

    public static boolean isEmpty(Object[] os) {
        return isNull(os) || os.length == 0;
    }

    public static boolean isEmpty(Collection<?> l) {
        return isNull(l) || l.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> m) {
        return isNull(m) || m.isEmpty();
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    public static boolean isNotEmpty(Object[] os) {
        return !isEmpty(os);
    }

    public static boolean isNotEmpty(Collection<?> l) {
        return !isEmpty(l);
    }

    public static boolean isNotEmpty(Map<?, ?> m) {
        return !isEmpty(m);
    }

    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }
}