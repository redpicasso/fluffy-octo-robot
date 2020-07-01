package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;

public class zzfl extends IOException {
    private final String content;
    private final int statusCode;
    private final transient zzfe zzuh;
    private final String zzvb;

    public zzfl(zzfk zzfk) {
        this(new zzfm(zzfk));
    }

    protected zzfl(zzfm zzfm) {
        super(zzfm.message);
        this.statusCode = zzfm.statusCode;
        this.zzvb = zzfm.zzvb;
        this.zzuh = zzfm.zzuh;
        this.content = zzfm.content;
    }

    public final int getStatusCode() {
        return this.statusCode;
    }

    public static StringBuilder zzc(zzfk zzfk) {
        StringBuilder stringBuilder = new StringBuilder();
        int statusCode = zzfk.getStatusCode();
        if (statusCode != 0) {
            stringBuilder.append(statusCode);
        }
        String statusMessage = zzfk.getStatusMessage();
        if (statusMessage != null) {
            if (statusCode != 0) {
                stringBuilder.append(' ');
            }
            stringBuilder.append(statusMessage);
        }
        return stringBuilder;
    }
}
