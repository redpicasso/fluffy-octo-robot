package io.opencensus.internal;

import java.util.ServiceConfigurationError;

public final class Provider {
    private Provider() {
    }

    public static <T> T createInstance(Class<?> cls, Class<T> cls2) {
        Class cls3;
        try {
            cls3 = cls3.asSubclass(cls2).getConstructor(new Class[0]).newInstance(new Object[0]);
            return cls3;
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provider ");
            stringBuilder.append(cls3.getName());
            stringBuilder.append(" could not be instantiated.");
            throw new ServiceConfigurationError(stringBuilder.toString(), e);
        }
    }
}
