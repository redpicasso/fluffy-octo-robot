package org.reactnative.camera.utils;

public class ObjectUtils {
    public static boolean equals(Object obj, Object obj2) {
        if (obj == null && obj2 == null) {
            return true;
        }
        return obj == null ? false : obj.equals(obj2);
    }
}
