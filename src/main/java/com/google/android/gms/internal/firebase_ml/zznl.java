package com.google.android.gms.internal.firebase_ml;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class zznl {
    private static final GmsLogger zzaoz = new GmsLogger("LibraryVersion", "");
    private static zznl zzapa = new zznl();
    private ConcurrentHashMap<String, String> zzapb = new ConcurrentHashMap();

    public static zznl zzll() {
        return zzapa;
    }

    private zznl() {
    }

    public final String getVersion(@NonNull String str) {
        String str2 = "Failed to get app version for libraryName: ";
        String str3 = "LibraryVersion";
        Preconditions.checkNotEmpty(str, "Please provide a valid libraryName");
        if (this.zzapb.containsKey(str)) {
            return (String) this.zzapb.get(str);
        }
        Properties properties = new Properties();
        String str4 = null;
        try {
            InputStream resourceAsStream = zznl.class.getResourceAsStream(String.format("/%s.properties", new Object[]{str}));
            GmsLogger gmsLogger;
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
                str4 = properties.getProperty("version", null);
                gmsLogger = zzaoz;
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 12) + String.valueOf(str4).length());
                stringBuilder.append(str);
                stringBuilder.append(" version is ");
                stringBuilder.append(str4);
                gmsLogger.v(str3, stringBuilder.toString());
            } else {
                gmsLogger = zzaoz;
                String valueOf = String.valueOf(str);
                gmsLogger.e(str3, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
        } catch (Throwable e) {
            GmsLogger gmsLogger2 = zzaoz;
            String valueOf2 = String.valueOf(str);
            gmsLogger2.e(str3, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2), e);
        }
        if (str4 == null) {
            zzaoz.d(str3, ".properties file is dropped during release process. Failure to read app version isexpected druing Google internal testing where locally-built libraries are used");
            str4 = "UNKNOWN";
        }
        this.zzapb.put(str, str4);
        return str4;
    }
}
