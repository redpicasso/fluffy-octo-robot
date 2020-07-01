package com.google.android.gms.internal.firebase_auth;

final class zzag extends zzad {
    private final char zzgj;

    zzag(char c) {
        this.zzgj = c;
    }

    public final boolean zza(char c) {
        return c == this.zzgj;
    }

    public final String toString() {
        String zzc = zzae.zzb(this.zzgj);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(zzc).length() + 18);
        stringBuilder.append("CharMatcher.is('");
        stringBuilder.append(zzc);
        stringBuilder.append("')");
        return stringBuilder.toString();
    }
}
