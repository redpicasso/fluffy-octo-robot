package com.google.android.gms.internal.firebase_ml;

public final class zzjc extends zzgd {
    @zzho
    private Integer maxResults;
    @zzho
    private String model;
    @zzho
    private String type;

    public final zzjc zza(Integer num) {
        this.maxResults = num;
        return this;
    }

    public final zzjc zzax(String str) {
        this.model = str;
        return this;
    }

    public final zzjc zzay(String str) {
        this.type = str;
        return this;
    }

    public final /* synthetic */ zzgd zza(String str, Object obj) {
        return (zzjc) zzb(str, obj);
    }

    public final /* synthetic */ zzgd zzeg() {
        return (zzjc) clone();
    }

    public final /* synthetic */ zzhm zzeh() {
        return (zzjc) clone();
    }

    public final /* synthetic */ zzhm zzb(String str, Object obj) {
        return (zzjc) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zzjc) super.clone();
    }
}
