package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class zzel {
    private static final Logger logger = Logger.getLogger(zzel.class.getName());
    private final zzfi zzsl;
    private final zzeq zzsm;
    private final String zzsn;
    private final String zzso;
    private final String zzsp;
    private final String zzsq;
    private final zzhu zzsr;
    private final boolean zzss;
    private final boolean zzst;

    public static abstract class zza {
        zzeq zzsm;
        String zzsn;
        String zzso;
        String zzsp;
        String zzsq;
        final zzhu zzsr;
        final zzfo zzsu;
        zzfj zzsv;

        protected zza(zzfo zzfo, String str, String str2, zzhu zzhu, zzfj zzfj) {
            this.zzsu = (zzfo) zzks.checkNotNull(zzfo);
            this.zzsr = zzhu;
            zzk(str);
            zzl(str2);
            this.zzsv = zzfj;
        }

        public zza zzk(String str) {
            this.zzsn = zzel.zzi(str);
            return this;
        }

        public zza zzl(String str) {
            this.zzso = zzel.zzj(str);
            return this;
        }

        public zza zzm(String str) {
            this.zzsp = str;
            return this;
        }

        public zza zza(zzeq zzeq) {
            this.zzsm = zzeq;
            return this;
        }

        public zza zzn(String str) {
            this.zzsq = str;
            return this;
        }
    }

    protected zzel(zza zza) {
        zzfi zza2;
        this.zzsm = zza.zzsm;
        this.zzsn = zzi(zza.zzsn);
        this.zzso = zzj(zza.zzso);
        this.zzsp = zza.zzsp;
        if (zzla.zzbb(zza.zzsq)) {
            logger.logp(Level.WARNING, "com.google.api.client.googleapis.services.AbstractGoogleClient", "<init>", "Application name is not set. Call Builder#setApplicationName.");
        }
        this.zzsq = zza.zzsq;
        if (zza.zzsv == null) {
            zza2 = zza.zzsu.zza(null);
        } else {
            zza2 = zza.zzsu.zza(zza.zzsv);
        }
        this.zzsl = zza2;
        this.zzsr = zza.zzsr;
        this.zzss = false;
        this.zzst = false;
    }

    public final String zzej() {
        String valueOf = String.valueOf(this.zzsn);
        String valueOf2 = String.valueOf(this.zzso);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public final String zzek() {
        return this.zzsq;
    }

    public final zzfi zzel() {
        return this.zzsl;
    }

    public zzhu zzem() {
        return this.zzsr;
    }

    protected void zza(zzem<?> zzem) throws IOException {
        zzeq zzeq = this.zzsm;
        if (zzeq != null) {
            zzeq.zza(zzem);
        }
    }

    static String zzi(String str) {
        zzks.checkNotNull(str, "root URL cannot be null.");
        String str2 = "/";
        return !str.endsWith(str2) ? String.valueOf(str).concat(str2) : str;
    }

    static String zzj(String str) {
        zzks.checkNotNull(str, "service path cannot be null");
        String str2 = "/";
        if (str.length() == 1) {
            zzks.checkArgument(str2.equals(str), "service path must equal \"/\" if it is of length 1.");
            return "";
        } else if (str.length() <= 0) {
            return str;
        } else {
            if (!str.endsWith(str2)) {
                str = String.valueOf(str).concat(str2);
            }
            return str.startsWith(str2) ? str.substring(1) : str;
        }
    }
}
