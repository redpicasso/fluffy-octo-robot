package com.google.android.gms.internal.firebase_ml;

import java.util.List;

public final class zzjx extends zzgd {
    @zzho
    private zziv boundingBox;
    @zzho
    private Float confidence;
    @zzho
    private zzjt property;
    @zzho
    private List<zzjr> symbols;

    public final zziv zzhq() {
        return this.boundingBox;
    }

    public final Float getConfidence() {
        return this.confidence;
    }

    public final zzjt zzhr() {
        return this.property;
    }

    public final List<zzjr> getSymbols() {
        return this.symbols;
    }

    public final /* synthetic */ zzgd zza(String str, Object obj) {
        return (zzjx) zzb(str, obj);
    }

    public final /* synthetic */ zzgd zzeg() {
        return (zzjx) clone();
    }

    public final /* synthetic */ zzhm zzeh() {
        return (zzjx) clone();
    }

    public final /* synthetic */ zzhm zzb(String str, Object obj) {
        return (zzjx) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zzjx) super.clone();
    }
}
