package com.google.firebase.firestore.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class ApiUtil {
    public static AssertionError newAssertionError(String str, Throwable th) {
        AssertionError assertionError = new AssertionError(str);
        assertionError.initCause(th);
        return assertionError;
    }

    static <T> T newInstance(Constructor<T> constructor) {
        try {
            return constructor.newInstance(new Object[0]);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        } catch (Throwable e22) {
            throw new RuntimeException(e22);
        }
    }

    static Object invoke(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }
}
