package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class zzhr<T extends zzhf> {
    private static final Logger logger = Logger.getLogger(zzha.class.getName());
    private static String zzaaf = "com.google.protobuf.BlazeGeneratedExtensionRegistryLiteLoader";

    zzhr() {
    }

    protected abstract T zzic();

    static <T extends zzhf> T zzc(Class<T> cls) {
        String str;
        ClassLoader classLoader = zzhr.class.getClassLoader();
        if (cls.equals(zzhf.class)) {
            str = zzaaf;
        } else if (cls.getPackage().equals(zzhr.class.getPackage())) {
            str = String.format("%s.BlazeGenerated%sLoader", new Object[]{cls.getPackage().getName(), cls.getSimpleName()});
        } else {
            throw new IllegalArgumentException(cls.getName());
        }
        try {
            return (zzhf) cls.cast(((zzhr) Class.forName(str, true, classLoader).getConstructor(new Class[0]).newInstance(new Object[0])).zzic());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        } catch (Throwable e2) {
            throw new IllegalStateException(e2);
        } catch (Throwable e22) {
            throw new IllegalStateException(e22);
        } catch (Throwable e222) {
            throw new IllegalStateException(e222);
        } catch (ClassNotFoundException unused) {
            Iterator it = ServiceLoader.load(zzhr.class, classLoader).iterator();
            ArrayList arrayList = new ArrayList();
            while (it.hasNext()) {
                try {
                    arrayList.add(cls.cast(((zzhr) it.next()).zzic()));
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
                return (zzhf) arrayList.get(0);
            }
            if (arrayList.size() == 0) {
                return null;
            }
            try {
                return (zzhf) cls.getMethod("combine", new Class[]{Collection.class}).invoke(null, new Object[]{arrayList});
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
