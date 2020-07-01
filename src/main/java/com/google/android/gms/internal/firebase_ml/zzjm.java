package com.google.android.gms.internal.firebase_ml;

import java.util.List;

public final class zzjm extends zzgd {
    @zzho
    private zziv boundingBox;
    @zzho
    private Float confidence;
    @zzho
    private zzjt property;
    @zzho
    private List<zzjx> words;

    public final zziv zzhq() {
        return this.boundingBox;
    }

    public final Float getConfidence() {
        return this.confidence;
    }

    public final zzjt zzhr() {
        return this.property;
    }

    public final List<zzjx> getWords() {
        return this.words;
    }

    public final /* synthetic */ zzgd zza(String str, Object obj) {
        return (zzjm) zzb(str, obj);
    }

    public final /* synthetic */ zzgd zzeg() {
        return (zzjm) clone();
    }

    public final /* synthetic */ zzhm zzeh() {
        return (zzjm) clone();
    }

    public final /* synthetic */ zzhm zzb(String str, Object obj) {
        return (zzjm) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zzjm) super.clone();
    }
}
