package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

final class ServiceProviders {

    public interface PriorityAccessor<T> {
        int getPriority(T t);

        boolean isAvailable(T t);
    }

    private ServiceProviders() {
    }

    public static <T> T load(Class<T> cls, Iterable<Class<?>> iterable, ClassLoader classLoader, PriorityAccessor<T> priorityAccessor) {
        List loadAll = loadAll(cls, iterable, classLoader, priorityAccessor);
        if (loadAll.isEmpty()) {
            return null;
        }
        return loadAll.get(0);
    }

    public static <T> List<T> loadAll(Class<T> cls, Iterable<Class<?>> iterable, ClassLoader classLoader, final PriorityAccessor<T> priorityAccessor) {
        Iterable candidatesViaHardCoded;
        if (isAndroid(classLoader)) {
            candidatesViaHardCoded = getCandidatesViaHardCoded(cls, iterable);
        } else {
            candidatesViaHardCoded = getCandidatesViaServiceLoader(cls, classLoader);
        }
        List arrayList = new ArrayList();
        for (Object next : candidatesViaHardCoded) {
            if (priorityAccessor.isAvailable(next)) {
                arrayList.add(next);
            }
        }
        Collections.sort(arrayList, Collections.reverseOrder(new Comparator<T>() {
            public int compare(T t, T t2) {
                return priorityAccessor.getPriority(t) - priorityAccessor.getPriority(t2);
            }
        }));
        return Collections.unmodifiableList(arrayList);
    }

    static boolean isAndroid(ClassLoader classLoader) {
        try {
            Class.forName("android.app.Application", false, classLoader);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    @VisibleForTesting
    public static <T> Iterable<T> getCandidatesViaServiceLoader(Class<T> cls, ClassLoader classLoader) {
        Iterable<T> load = ServiceLoader.load(cls, classLoader);
        return !load.iterator().hasNext() ? ServiceLoader.load(cls) : load;
    }

    @VisibleForTesting
    static <T> Iterable<T> getCandidatesViaHardCoded(Class<T> cls, Iterable<Class<?>> iterable) {
        Iterable arrayList = new ArrayList();
        for (Class create : iterable) {
            arrayList.add(create(cls, create));
        }
        return arrayList;
    }

    @VisibleForTesting
    static <T> T create(Class<T> cls, Class<?> cls2) {
        try {
            return cls2.asSubclass(cls).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable th) {
            ServiceConfigurationError serviceConfigurationError = new ServiceConfigurationError(String.format("Provider %s could not be instantiated %s", new Object[]{cls2.getName(), th}), th);
        }
    }
}
