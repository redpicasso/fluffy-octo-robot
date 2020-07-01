package com.bumptech.glide.module;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public final class ManifestParser {
    private static final String GLIDE_MODULE_VALUE = "GlideModule";
    private static final String TAG = "ManifestParser";
    private final Context context;

    public ManifestParser(Context context) {
        this.context = context;
    }

    public List<GlideModule> parse() {
        String str = TAG;
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "Loading Glide modules");
        }
        List<GlideModule> arrayList = new ArrayList();
        try {
            ApplicationInfo applicationInfo = this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 128);
            if (applicationInfo.metaData == null) {
                if (Log.isLoggable(str, 3)) {
                    Log.d(str, "Got null app info metadata");
                }
                return arrayList;
            }
            if (Log.isLoggable(str, 2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Got app info metadata: ");
                stringBuilder.append(applicationInfo.metaData);
                Log.v(str, stringBuilder.toString());
            }
            for (String str2 : applicationInfo.metaData.keySet()) {
                if (GLIDE_MODULE_VALUE.equals(applicationInfo.metaData.get(str2))) {
                    arrayList.add(parseModule(str2));
                    if (Log.isLoggable(str, 3)) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Loaded Glide module: ");
                        stringBuilder2.append(str2);
                        Log.d(str, stringBuilder2.toString());
                    }
                }
            }
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "Finished loading Glide modules");
            }
            return arrayList;
        } catch (Throwable e) {
            throw new RuntimeException("Unable to find metadata to parse GlideModules", e);
        }
    }

    private static GlideModule parseModule(String str) {
        try {
            Class cls = Class.forName(str);
            Object obj = null;
            try {
                obj = cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Exception e) {
                throwInstantiateGlideModuleException(cls, e);
            } catch (Exception e2) {
                throwInstantiateGlideModuleException(cls, e2);
            } catch (Exception e22) {
                throwInstantiateGlideModuleException(cls, e22);
            } catch (Exception e222) {
                throwInstantiateGlideModuleException(cls, e222);
            }
            if (obj instanceof GlideModule) {
                return (GlideModule) obj;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected instanceof GlideModule, but found: ");
            stringBuilder.append(obj);
            throw new RuntimeException(stringBuilder.toString());
        } catch (Throwable e3) {
            throw new IllegalArgumentException("Unable to find GlideModule implementation", e3);
        }
    }

    private static void throwInstantiateGlideModuleException(Class<?> cls, Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to instantiate GlideModule implementation for ");
        stringBuilder.append(cls);
        throw new RuntimeException(stringBuilder.toString(), exception);
    }
}
