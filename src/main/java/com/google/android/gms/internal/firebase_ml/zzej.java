package com.google.android.gms.internal.firebase_ml;

import java.util.List;

public class zzej extends zzgd {
    @zzho
    private int code;
    @zzho
    private List<zza> errors;
    @zzho
    private String message;

    public static class zza extends zzgd {
        @zzho
        private String domain;
        @zzho
        private String location;
        @zzho
        private String locationType;
        @zzho
        private String message;
        @zzho
        private String reason;

        public final String getReason() {
            return this.reason;
        }

        public final /* synthetic */ zzgd zza(String str, Object obj) {
            return (zza) zzb(str, obj);
        }

        public final /* synthetic */ zzgd zzeg() {
            return (zza) clone();
        }

        public final /* synthetic */ zzhm zzeh() {
            return (zza) clone();
        }

        public final /* synthetic */ zzhm zzb(String str, Object obj) {
            return (zza) super.zzb(str, obj);
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return (zza) super.clone();
        }
    }

    public final List<zza> zzef() {
        return this.errors;
    }

    public final /* synthetic */ zzgd zza(String str, Object obj) {
        return (zzej) zzb(str, obj);
    }

    public final /* synthetic */ zzgd zzeg() {
        return (zzej) clone();
    }

    public final /* synthetic */ zzhm zzeh() {
        return (zzej) clone();
    }

    public final /* synthetic */ zzhm zzb(String str, Object obj) {
        return (zzej) super.zzb(str, obj);
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zzej) super.clone();
    }

    static {
        zzhf.zzd(zza.class);
    }
}
