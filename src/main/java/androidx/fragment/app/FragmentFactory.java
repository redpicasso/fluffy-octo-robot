package androidx.fragment.app;

import androidx.annotation.NonNull;
import androidx.collection.SimpleArrayMap;
import androidx.fragment.app.Fragment.InstantiationException;

public class FragmentFactory {
    private static final SimpleArrayMap<String, Class<?>> sClassMap = new SimpleArrayMap();

    @NonNull
    private static Class<?> loadClass(@NonNull ClassLoader classLoader, @NonNull String str) throws ClassNotFoundException {
        Class<?> cls = (Class) sClassMap.get(str);
        if (cls != null) {
            return cls;
        }
        cls = Class.forName(str, false, classLoader);
        sClassMap.put(str, cls);
        return cls;
    }

    static boolean isFragmentClass(@NonNull ClassLoader classLoader, @NonNull String str) {
        try {
            return Fragment.class.isAssignableFrom(loadClass(classLoader, str));
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    @NonNull
    public static Class<? extends Fragment> loadFragmentClass(@NonNull ClassLoader classLoader, @NonNull String str) {
        StringBuilder stringBuilder;
        String str2 = "Unable to instantiate fragment ";
        try {
            return loadClass(classLoader, str);
        } catch (Exception e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(str);
            stringBuilder.append(": make sure class name exists");
            throw new InstantiationException(stringBuilder.toString(), e);
        } catch (Exception e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(str);
            stringBuilder.append(": make sure class is a valid subclass of Fragment");
            throw new InstantiationException(stringBuilder.toString(), e2);
        }
    }

    @NonNull
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String str) {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2;
        String str2 = ": make sure class name exists, is public, and has an empty constructor that is public";
        String str3 = "Unable to instantiate fragment ";
        try {
            return (Fragment) loadFragmentClass(classLoader, str).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str3);
            stringBuilder.append(str);
            stringBuilder.append(str2);
            throw new InstantiationException(stringBuilder.toString(), e);
        } catch (Exception e2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str3);
            stringBuilder.append(str);
            stringBuilder.append(str2);
            throw new InstantiationException(stringBuilder.toString(), e2);
        } catch (Exception e22) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(str3);
            stringBuilder2.append(str);
            stringBuilder2.append(": could not find Fragment constructor");
            throw new InstantiationException(stringBuilder2.toString(), e22);
        } catch (Exception e222) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(str3);
            stringBuilder2.append(str);
            stringBuilder2.append(": calling Fragment constructor caused an exception");
            throw new InstantiationException(stringBuilder2.toString(), e222);
        }
    }
}
