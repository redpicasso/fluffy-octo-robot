package com.google.android.gms.internal.firebase_auth;

final class zzat {
    static void zza(Object obj, Object obj2) {
        StringBuilder stringBuilder;
        if (obj == null) {
            String valueOf = String.valueOf(obj2);
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 24);
            stringBuilder.append("null key in entry: null=");
            stringBuilder.append(valueOf);
            throw new NullPointerException(stringBuilder.toString());
        } else if (obj2 == null) {
            String valueOf2 = String.valueOf(obj);
            stringBuilder = new StringBuilder(String.valueOf(valueOf2).length() + 26);
            stringBuilder.append("null value in entry: ");
            stringBuilder.append(valueOf2);
            stringBuilder.append("=null");
            throw new NullPointerException(stringBuilder.toString());
        }
    }
}
