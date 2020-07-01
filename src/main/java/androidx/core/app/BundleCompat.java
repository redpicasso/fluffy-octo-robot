package androidx.core.app;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class BundleCompat {

    static class BundleCompatBaseImpl {
        private static final String TAG = "BundleCompatBaseImpl";
        private static Method sGetIBinderMethod;
        private static boolean sGetIBinderMethodFetched;
        private static Method sPutIBinderMethod;
        private static boolean sPutIBinderMethodFetched;

        private BundleCompatBaseImpl() {
        }

        public static IBinder getBinder(Bundle bundle, String str) {
            Throwable e;
            boolean z = sGetIBinderMethodFetched;
            String str2 = TAG;
            if (!z) {
                try {
                    sGetIBinderMethod = Bundle.class.getMethod("getIBinder", new Class[]{String.class});
                    sGetIBinderMethod.setAccessible(true);
                } catch (Throwable e2) {
                    Log.i(str2, "Failed to retrieve getIBinder method", e2);
                }
                sGetIBinderMethodFetched = true;
            }
            Method method = sGetIBinderMethod;
            if (method != null) {
                try {
                    return (IBinder) method.invoke(bundle, new Object[]{str});
                } catch (InvocationTargetException e3) {
                    e = e3;
                    Log.i(str2, "Failed to invoke getIBinder via reflection", e);
                    sGetIBinderMethod = null;
                    return null;
                } catch (IllegalAccessException e4) {
                    e = e4;
                    Log.i(str2, "Failed to invoke getIBinder via reflection", e);
                    sGetIBinderMethod = null;
                    return null;
                } catch (IllegalArgumentException e5) {
                    e = e5;
                    Log.i(str2, "Failed to invoke getIBinder via reflection", e);
                    sGetIBinderMethod = null;
                    return null;
                }
            }
            return null;
        }

        public static void putBinder(Bundle bundle, String str, IBinder iBinder) {
            Throwable e;
            boolean z = sPutIBinderMethodFetched;
            String str2 = TAG;
            if (!z) {
                try {
                    sPutIBinderMethod = Bundle.class.getMethod("putIBinder", new Class[]{String.class, IBinder.class});
                    sPutIBinderMethod.setAccessible(true);
                } catch (Throwable e2) {
                    Log.i(str2, "Failed to retrieve putIBinder method", e2);
                }
                sPutIBinderMethodFetched = true;
            }
            Method method = sPutIBinderMethod;
            if (method != null) {
                try {
                    method.invoke(bundle, new Object[]{str, iBinder});
                    return;
                } catch (InvocationTargetException e3) {
                    e = e3;
                } catch (IllegalAccessException e4) {
                    e = e4;
                } catch (IllegalArgumentException e5) {
                    e = e5;
                }
            } else {
                return;
            }
            Log.i(str2, "Failed to invoke putIBinder via reflection", e);
            sPutIBinderMethod = null;
        }
    }

    private BundleCompat() {
    }

    @Nullable
    public static IBinder getBinder(@NonNull Bundle bundle, @Nullable String str) {
        if (VERSION.SDK_INT >= 18) {
            return bundle.getBinder(str);
        }
        return BundleCompatBaseImpl.getBinder(bundle, str);
    }

    public static void putBinder(@NonNull Bundle bundle, @Nullable String str, @Nullable IBinder iBinder) {
        if (VERSION.SDK_INT >= 18) {
            bundle.putBinder(str, iBinder);
        } else {
            BundleCompatBaseImpl.putBinder(bundle, str, iBinder);
        }
    }
}
