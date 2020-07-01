package com.google.firebase.ml.vision.barcode;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.firebase_ml.zzqs.zza;
import com.google.android.gms.internal.firebase_ml.zzsk;
import com.google.android.gms.internal.firebase_ml.zzue;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.BarcodeFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FirebaseVisionBarcodeDetectorOptions {
    private static final Map<Integer, zzsk> zzavq;
    private final int zzavy;

    public static class Builder {
        private int zzavz = 0;

        public Builder setBarcodeFormats(@BarcodeFormat int i, @BarcodeFormat int... iArr) {
            this.zzavz = i;
            if (iArr != null) {
                for (int i2 : iArr) {
                    this.zzavz = i2 | this.zzavz;
                }
            }
            return this;
        }

        public FirebaseVisionBarcodeDetectorOptions build() {
            return new FirebaseVisionBarcodeDetectorOptions(this.zzavz, null);
        }
    }

    private FirebaseVisionBarcodeDetectorOptions(int i) {
        this.zzavy = i;
    }

    public final int zznf() {
        return this.zzavy;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FirebaseVisionBarcodeDetectorOptions)) {
            return false;
        }
        return this.zzavy == ((FirebaseVisionBarcodeDetectorOptions) obj).zzavy;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzavy));
    }

    public final zza zzng() {
        Iterable arrayList = new ArrayList();
        if (this.zzavy == 0) {
            arrayList.addAll(zzavq.values());
        } else {
            for (Entry entry : zzavq.entrySet()) {
                if ((this.zzavy & ((Integer) entry.getKey()).intValue()) != 0) {
                    arrayList.add((zzsk) entry.getValue());
                }
            }
        }
        return (zza) ((zzue) zza.zzof().zzp(arrayList).zzrj());
    }

    /* synthetic */ FirebaseVisionBarcodeDetectorOptions(int i, zza zza) {
        this(i);
    }

    static {
        Map hashMap = new HashMap();
        zzavq = hashMap;
        hashMap.put(Integer.valueOf(1), zzsk.CODE_128);
        zzavq.put(Integer.valueOf(2), zzsk.CODE_39);
        zzavq.put(Integer.valueOf(4), zzsk.CODE_93);
        zzavq.put(Integer.valueOf(8), zzsk.CODABAR);
        zzavq.put(Integer.valueOf(16), zzsk.DATA_MATRIX);
        zzavq.put(Integer.valueOf(32), zzsk.EAN_13);
        zzavq.put(Integer.valueOf(64), zzsk.EAN_8);
        zzavq.put(Integer.valueOf(128), zzsk.ITF);
        zzavq.put(Integer.valueOf(256), zzsk.QR_CODE);
        zzavq.put(Integer.valueOf(512), zzsk.UPC_A);
        zzavq.put(Integer.valueOf(1024), zzsk.UPC_E);
        zzavq.put(Integer.valueOf(2048), zzsk.PDF417);
        zzavq.put(Integer.valueOf(4096), zzsk.AZTEC);
    }
}
