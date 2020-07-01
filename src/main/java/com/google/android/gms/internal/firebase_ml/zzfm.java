package com.google.android.gms.internal.firebase_ml;

public final class zzfm {
    String content;
    String message;
    int statusCode;
    zzfe zzuh;
    String zzvb;

    public zzfm(int i, String str, zzfe zzfe) {
        zzks.checkArgument(i >= 0);
        this.statusCode = i;
        this.zzvb = str;
        this.zzuh = (zzfe) zzks.checkNotNull(zzfe);
    }

    public zzfm(zzfk zzfk) {
        this(zzfk.getStatusCode(), zzfk.getStatusMessage(), zzfk.zzfe());
        try {
            this.content = zzfk.zzfl();
            if (this.content.length() == 0) {
                this.content = null;
            }
        } catch (Throwable e) {
            zzlx.zzb(e);
        }
        StringBuilder zzc = zzfl.zzc(zzfk);
        if (this.content != null) {
            zzc.append(zzhz.zzaae);
            zzc.append(this.content);
        }
        this.message = zzc.toString();
    }

    public final zzfm zzah(String str) {
        this.message = str;
        return this;
    }

    public final zzfm zzai(String str) {
        this.content = str;
        return this;
    }
}
