package com.google.android.gms.internal.firebase_ml;

import java.util.List;

public final class zzjb extends zzgd {
    @zzho
    private zziv boundingPoly;
    @zzho
    private Float confidence;
    @zzho
    private String description;
    @zzho
    private String locale;
    @zzho
    private List<zzjk> locations;
    @zzho
    private String mid;
    @zzho
    private List<Object> properties;
    @zzho
    private Float score;
    @zzho
    private Float topicality;

    public final zziv zzhu() {
        return this.boundingPoly;
    }

    public final String getDescription() {
        return this.description;
    }

    public final List<zzjk> getLocations() {
        return this.locations;
    }

    public final String getMid() {
        return this.mid;
    }

    public final Float zzhv() {
        return this.score;
    }

    public final /* synthetic */ zzgd zza(String str, Object obj) {
        return (zzjb) zzb(str, obj);
    }

    public final /* synthetic */ zzgd zzeg() {
        return (zzjb) clone();
    }

    public final /* synthetic */ zzhm zzeh() {
        return (zzjb) clone();
    }

    public final /* synthetic */ zzhm zzb(String str, Object obj) {
        return (zzjb) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zzjb) super.clone();
    }
}
