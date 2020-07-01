package com.google.android.gms.internal.firebase_ml;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

public final class zzez extends zzhm {
    private static final zzif zzto = new zzig("=&-_.!~*'()@:$,;/?:", false);
    private String fragment;
    private int port;
    private String zztp;
    private String zztq;
    private String zztr;
    private List<String> zzts;

    public zzez() {
        this.port = -1;
    }

    public zzez(String str) {
        this(zzx(str));
    }

    public zzez(URL url) {
        this(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(), url.getRef(), url.getQuery(), url.getUserInfo());
    }

    private zzez(String str, String str2, int i, String str3, String str4, String str5, String str6) {
        this.port = -1;
        this.zztp = str.toLowerCase(Locale.US);
        this.zztq = str2;
        this.port = i;
        this.zzts = zzw(str3);
        str = null;
        this.fragment = str4 != null ? zzie.zzar(str4) : null;
        if (str5 != null) {
            zzfu.zze(str5, this);
        }
        if (str6 != null) {
            str = zzie.zzar(str6);
        }
        this.zztr = str;
    }

    public final int hashCode() {
        return zzew().hashCode();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof zzez)) {
            return false;
        }
        return zzew().equals(((zzez) obj).zzew());
    }

    public final String toString() {
        return zzew();
    }

    public final String zzew() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String) zzks.checkNotNull(this.zztp));
        stringBuilder.append("://");
        String str = this.zztr;
        if (str != null) {
            stringBuilder.append(zzie.zzau(str));
            stringBuilder.append('@');
        }
        stringBuilder.append((String) zzks.checkNotNull(this.zztq));
        int i = this.port;
        if (i != -1) {
            stringBuilder.append(':');
            stringBuilder.append(i);
        }
        String valueOf = String.valueOf(stringBuilder.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        List list = this.zzts;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) this.zzts.get(i2);
                if (i2 != 0) {
                    stringBuilder2.append('/');
                }
                if (str2.length() != 0) {
                    stringBuilder2.append(zzie.zzas(str2));
                }
            }
        }
        zza(entrySet(), stringBuilder2);
        String str3 = this.fragment;
        if (str3 != null) {
            stringBuilder2.append('#');
            stringBuilder2.append(zzto.zzaw(str3));
        }
        str = String.valueOf(stringBuilder2.toString());
        return str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
    }

    public final URL zzu(String str) {
        try {
            return new URL(zzx(zzew()), str);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    public final void zzv(String str) {
        this.zzts = zzw(null);
    }

    private static List<String> zzw(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        List<String> arrayList = new ArrayList();
        Object obj = 1;
        int i = 0;
        while (obj != null) {
            String substring;
            int indexOf = str.indexOf(47, i);
            Object obj2 = indexOf != -1 ? 1 : null;
            if (obj2 != null) {
                substring = str.substring(i, indexOf);
            } else {
                substring = str.substring(i);
            }
            arrayList.add(zzie.zzar(substring));
            i = indexOf + 1;
            obj = obj2;
        }
        return arrayList;
    }

    static void zza(Set<Entry<String, Object>> set, StringBuilder stringBuilder) {
        boolean z = true;
        for (Entry entry : set) {
            Object value = entry.getValue();
            if (value != null) {
                String zzav = zzie.zzav((String) entry.getKey());
                if (value instanceof Collection) {
                    for (Object zza : (Collection) value) {
                        z = zza(z, stringBuilder, zzav, zza);
                    }
                } else {
                    z = zza(z, stringBuilder, zzav, value);
                }
            }
        }
    }

    private static boolean zza(boolean z, StringBuilder stringBuilder, String str, Object obj) {
        if (z) {
            z = false;
            stringBuilder.append('?');
        } else {
            stringBuilder.append('&');
        }
        stringBuilder.append(str);
        str = zzie.zzav(obj.toString());
        if (str.length() != 0) {
            stringBuilder.append('=');
            stringBuilder.append(str);
        }
        return z;
    }

    private static URL zzx(String str) {
        try {
            return new URL(str);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    public final /* synthetic */ zzhm zzeh() {
        return (zzez) clone();
    }

    public final /* synthetic */ zzhm zzb(String str, Object obj) {
        return (zzez) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzez zzez = (zzez) super.clone();
        Collection collection = this.zzts;
        if (collection != null) {
            zzez.zzts = new ArrayList(collection);
        }
        return zzez;
    }
}
