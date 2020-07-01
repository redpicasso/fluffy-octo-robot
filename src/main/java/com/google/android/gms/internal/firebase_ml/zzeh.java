package com.google.android.gms.internal.firebase_ml;

public final class zzeh {
    public static final String VERSION;
    public static final Integer zzsg = Integer.valueOf(1);
    public static final Integer zzsh = Integer.valueOf(26);
    private static final Integer zzsi = Integer.valueOf(0);

    static {
        String valueOf = String.valueOf(zzsg);
        String valueOf2 = String.valueOf(zzsh);
        String valueOf3 = String.valueOf(zzsi);
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(valueOf).length() + 11) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length());
        stringBuilder.append(valueOf);
        valueOf = ".";
        stringBuilder.append(valueOf);
        stringBuilder.append(valueOf2);
        stringBuilder.append(valueOf);
        stringBuilder.append(valueOf3);
        stringBuilder.append("-SNAPSHOT");
        VERSION = stringBuilder.toString().toString();
    }
}
