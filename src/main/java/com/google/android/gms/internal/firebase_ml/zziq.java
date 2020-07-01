package com.google.android.gms.internal.firebase_ml;

import java.util.List;

public final class zziq extends zzgd {
    @zzho
    private List<zzjc> features;
    @zzho
    private zzjd image;
    @zzho
    private zzjf imageContext;

    public final zziq zzb(List<zzjc> list) {
        this.features = list;
        return this;
    }

    public final zziq zza(zzjd zzjd) {
        this.image = zzjd;
        return this;
    }

    public final zziq zza(zzjf zzjf) {
        this.imageContext = zzjf;
        return this;
    }

    public final /* synthetic */ zzgd zza(String str, Object obj) {
        return (zziq) zzb(str, obj);
    }

    public final /* synthetic */ zzgd zzeg() {
        return (zziq) clone();
    }

    public final /* synthetic */ zzhm zzeh() {
        return (zziq) clone();
    }

    public final /* synthetic */ zzhm zzb(String str, Object obj) {
        return (zziq) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zziq) super.clone();
    }
}
