package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzca implements zzce {
    @GuardedBy("ConfigurationContentLoader.class")
    static final Map<Uri, zzca> zzaah = new ArrayMap();
    private static final String[] zzaam = new String[]{"key", "value"};
    private final Uri uri;
    private final ContentResolver zzaai;
    private final Object zzaaj = new Object();
    private volatile Map<String, String> zzaak;
    @GuardedBy("this")
    private final List<zzcf> zzaal = new ArrayList();

    private zzca(ContentResolver contentResolver, Uri uri) {
        this.zzaai = contentResolver;
        this.uri = uri;
        this.zzaai.registerContentObserver(uri, false, new zzcc(this, null));
    }

    public static zzca zza(ContentResolver contentResolver, Uri uri) {
        synchronized (zzca.class) {
            zzca zzca = (zzca) zzaah.get(uri);
            if (zzca == null) {
                zzca zzca2 = new zzca(contentResolver, uri);
                try {
                    zzaah.put(uri, zzca2);
                } catch (SecurityException unused) {
                    zzca = zzca2;
                }
            }
            try {
            } catch (SecurityException unused2) {
                return zzca;
            }
        }
    }

    public final Map<String, String> zzre() {
        Map<String, String> map = this.zzaak;
        if (map == null) {
            synchronized (this.zzaaj) {
                map = this.zzaak;
                if (map == null) {
                    map = zzrg();
                    this.zzaak = map;
                }
            }
        }
        if (map != null) {
            return map;
        }
        return Collections.emptyMap();
    }

    public final void zzrf() {
        synchronized (this.zzaaj) {
            this.zzaak = null;
            zzcm.zzrl();
        }
        synchronized (this) {
            for (zzcf zzrk : this.zzaal) {
                zzrk.zzrk();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x000c A:{ExcHandler: java.lang.SecurityException (unused java.lang.SecurityException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x000c A:{ExcHandler: java.lang.SecurityException (unused java.lang.SecurityException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Missing block: B:3:0x000c, code:
            android.util.Log.e("ConfigurationContentLoader", "PhenotypeFlag unable to load ContentProvider, using default values");
     */
    /* JADX WARNING: Missing block: B:4:0x0014, code:
            return null;
     */
    private final java.util.Map<java.lang.String, java.lang.String> zzrg() {
        /*
        r2 = this;
        r0 = new com.google.android.gms.internal.measurement.zzcd;	 Catch:{ SecurityException -> 0x000c, SecurityException -> 0x000c, SecurityException -> 0x000c }
        r0.<init>(r2);	 Catch:{ SecurityException -> 0x000c, SecurityException -> 0x000c, SecurityException -> 0x000c }
        r0 = com.google.android.gms.internal.measurement.zzch.zza(r0);	 Catch:{ SecurityException -> 0x000c, SecurityException -> 0x000c, SecurityException -> 0x000c }
        r0 = (java.util.Map) r0;	 Catch:{ SecurityException -> 0x000c, SecurityException -> 0x000c, SecurityException -> 0x000c }
        return r0;
    L_0x000c:
        r0 = "ConfigurationContentLoader";
        r1 = "PhenotypeFlag unable to load ContentProvider, using default values";
        android.util.Log.e(r0, r1);
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzca.zzrg():java.util.Map<java.lang.String, java.lang.String>");
    }

    public final /* synthetic */ Object zzdd(String str) {
        return (String) zzre().get(str);
    }

    final /* synthetic */ Map zzrh() {
        Cursor query = this.zzaai.query(this.uri, zzaam, null, null, null);
        if (query == null) {
            return Collections.emptyMap();
        }
        try {
            int count = query.getCount();
            if (count == 0) {
                Map emptyMap = Collections.emptyMap();
                return emptyMap;
            }
            Map arrayMap;
            if (count <= 256) {
                arrayMap = new ArrayMap(count);
            } else {
                arrayMap = new HashMap(count, 1.0f);
            }
            while (query.moveToNext()) {
                arrayMap.put(query.getString(0), query.getString(1));
            }
            query.close();
            return arrayMap;
        } finally {
            query.close();
        }
    }
}
