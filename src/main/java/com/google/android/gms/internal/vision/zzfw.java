package com.google.android.gms.internal.vision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class zzfw<T extends zzfk> {
    private static final Logger logger = Logger.getLogger(zzfe.class.getName());
    private static String zzwh = "com.google.protobuf.BlazeGeneratedExtensionRegistryLiteLoader";

    zzfw() {
    }

    protected abstract T zzew();

    static <T extends zzfk> T zza(Class<T> cls) {
        String str;
        ClassLoader classLoader = zzfw.class.getClassLoader();
        if (cls.equals(zzfk.class)) {
            str = zzwh;
        } else if (cls.getPackage().equals(zzfw.class.getPackage())) {
            str = String.format("%s.BlazeGenerated%sLoader", new Object[]{cls.getPackage().getName(), cls.getSimpleName()});
        } else {
            throw new IllegalArgumentException(cls.getName());
        }
        try {
            return (zzfk) cls.cast(((zzfw) Class.forName(str, true, classLoader).getConstructor(new Class[0]).newInstance(new Object[0])).zzew());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        } catch (Throwable e2) {
            throw new IllegalStateException(e2);
        } catch (Throwable e22) {
            throw new IllegalStateException(e22);
        } catch (Throwable e222) {
            throw new IllegalStateException(e222);
        } catch (ClassNotFoundException unused) {
            Iterator it = ServiceLoader.load(zzfw.class, classLoader).iterator();
            ArrayList arrayList = new ArrayList();
            while (it.hasNext()) {
                try {
                    arrayList.add((zzfk) cls.cast(((zzfw) it.next()).zzew()));
                } catch (Throwable e3) {
                    Throwable th = e3;
                    Logger logger = logger;
                    Level level = Level.SEVERE;
                    String str2 = "Unable to load ";
                    String valueOf = String.valueOf(cls.getSimpleName());
                    logger.logp(level, "com.google.protobuf.GeneratedExtensionRegistryLoader", "load", valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), th);
                }
            }
            if (arrayList.size() == 1) {
                return (zzfk) arrayList.get(0);
            }
            if (arrayList.size() == 0) {
                return null;
            }
            try {
                return (zzfk) cls.getMethod("combine", new Class[]{Collection.class}).invoke(null, new Object[]{arrayList});
            } catch (Throwable e4) {
                throw new IllegalStateException(e4);
            } catch (Throwable e42) {
                throw new IllegalStateException(e42);
            } catch (Throwable e422) {
                throw new IllegalStateException(e422);
            }
        }
    }
}
