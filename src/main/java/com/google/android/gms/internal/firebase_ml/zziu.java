package com.google.android.gms.internal.firebase_ml;

import java.util.List;

public final class zziu extends zzgd {
    @zzho
    private String blockType;
    @zzho
    private zziv boundingBox;
    @zzho
    private Float confidence;
    @zzho
    private List<zzjm> paragraphs;
    @zzho
    private zzjt property;

    public final zziv zzhq() {
        return this.boundingBox;
    }

    public final Float getConfidence() {
        return this.confidence;
    }

    public final List<zzjm> getParagraphs() {
        return this.paragraphs;
    }

    public final zzjt zzhr() {
        return this.property;
    }

    public final /* synthetic */ zzgd zza(String str, Object obj) {
        return (zziu) zzb(str, obj);
    }

    public final /* synthetic */ zzgd zzeg() {
        return (zziu) clone();
    }

    public final /* synthetic */ zzhm zzeh() {
        return (zziu) clone();
    }

    public final /* synthetic */ zzhm zzb(String str, Object obj) {
        return (zziu) super.zzb(str, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return (zziu) super.clone();
    }
}
