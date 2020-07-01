package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.internal.firebase_ml.zzue.zzf;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class zztn implements zzxr {
    private final zztl zzbkr;

    public static zztn zza(zztl zztl) {
        if (zztl.zzbla != null) {
            return zztl.zzbla;
        }
        return new zztn(zztl);
    }

    private zztn(zztl zztl) {
        this.zzbkr = (zztl) zzug.zza(zztl, "output");
    }

    public final int zzqh() {
        return zzf.zzboz;
    }

    public final void zzr(int i, int i2) throws IOException {
        this.zzbkr.zzk(i, i2);
    }

    public final void zzi(int i, long j) throws IOException {
        this.zzbkr.zza(i, j);
    }

    public final void zzj(int i, long j) throws IOException {
        this.zzbkr.zzc(i, j);
    }

    public final void zza(int i, float f) throws IOException {
        this.zzbkr.zza(i, f);
    }

    public final void zza(int i, double d) throws IOException {
        this.zzbkr.zza(i, d);
    }

    public final void zzs(int i, int i2) throws IOException {
        this.zzbkr.zzh(i, i2);
    }

    public final void zza(int i, long j) throws IOException {
        this.zzbkr.zza(i, j);
    }

    public final void zzh(int i, int i2) throws IOException {
        this.zzbkr.zzh(i, i2);
    }

    public final void zzc(int i, long j) throws IOException {
        this.zzbkr.zzc(i, j);
    }

    public final void zzk(int i, int i2) throws IOException {
        this.zzbkr.zzk(i, i2);
    }

    public final void zzb(int i, boolean z) throws IOException {
        this.zzbkr.zzb(i, z);
    }

    public final void zzb(int i, String str) throws IOException {
        this.zzbkr.zzb(i, str);
    }

    public final void zza(int i, zzsw zzsw) throws IOException {
        this.zzbkr.zza(i, zzsw);
    }

    public final void zzi(int i, int i2) throws IOException {
        this.zzbkr.zzi(i, i2);
    }

    public final void zzj(int i, int i2) throws IOException {
        this.zzbkr.zzj(i, i2);
    }

    public final void zzb(int i, long j) throws IOException {
        this.zzbkr.zzb(i, j);
    }

    public final void zza(int i, Object obj, zzwe zzwe) throws IOException {
        this.zzbkr.zza(i, (zzvo) obj, zzwe);
    }

    public final void zzb(int i, Object obj, zzwe zzwe) throws IOException {
        zztl zztl = this.zzbkr;
        zzvo zzvo = (zzvo) obj;
        zztl.zzg(i, 3);
        zzwe.zza(zzvo, zztl.zzbla);
        zztl.zzg(i, 4);
    }

    public final void zzde(int i) throws IOException {
        this.zzbkr.zzg(i, 3);
    }

    public final void zzdf(int i) throws IOException {
        this.zzbkr.zzg(i, 4);
    }

    public final void zza(int i, Object obj) throws IOException {
        if (obj instanceof zzsw) {
            this.zzbkr.zzb(i, (zzsw) obj);
        } else {
            this.zzbkr.zza(i, (zzvo) obj);
        }
    }

    public final void zza(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzcv(((Integer) list.get(i)).intValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzcq(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zzh(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzb(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzcy(((Integer) list.get(i)).intValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzct(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zzk(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzc(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzt(((Long) list.get(i)).longValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzq(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zza(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzd(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzu(((Long) list.get(i)).longValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzq(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zza(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zze(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzw(((Long) list.get(i)).longValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzs(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zzc(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzf(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzu(((Float) list.get(i)).floatValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzt(((Float) list.get(i2)).floatValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zza(i, ((Float) list.get(i2)).floatValue());
            i2++;
        }
    }

    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzd(((Double) list.get(i)).doubleValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzc(((Double) list.get(i2)).doubleValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zza(i, ((Double) list.get(i2)).doubleValue());
            i2++;
        }
    }

    public final void zzh(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzda(((Integer) list.get(i)).intValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzcq(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zzh(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzi(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzai(((Boolean) list.get(i)).booleanValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzah(((Boolean) list.get(i2)).booleanValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zzb(i, ((Boolean) list.get(i2)).booleanValue());
            i2++;
        }
    }

    public final void zza(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (list instanceof zzux) {
            zzux zzux = (zzux) list;
            while (i2 < list.size()) {
                Object raw = zzux.getRaw(i2);
                if (raw instanceof String) {
                    this.zzbkr.zzb(i, (String) raw);
                } else {
                    this.zzbkr.zza(i, (zzsw) raw);
                }
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zzb(i, (String) list.get(i2));
            i2++;
        }
    }

    public final void zzb(int i, List<zzsw> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zzbkr.zza(i, (zzsw) list.get(i2));
        }
    }

    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzcw(((Integer) list.get(i)).intValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzcr(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zzi(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzk(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzcz(((Integer) list.get(i)).intValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzct(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zzk(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzl(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzx(((Long) list.get(i)).longValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzs(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zzc(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzm(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzcx(((Integer) list.get(i)).intValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzcs(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zzj(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzbkr.zzg(i, 2);
            int i3 = 0;
            for (i = 0; i < list.size(); i++) {
                i3 += zztl.zzv(((Long) list.get(i)).longValue());
            }
            this.zzbkr.zzcr(i3);
            while (i2 < list.size()) {
                this.zzbkr.zzr(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbkr.zzb(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zza(int i, List<?> list, zzwe zzwe) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), zzwe);
        }
    }

    public final void zzb(int i, List<?> list, zzwe zzwe) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), zzwe);
        }
    }

    public final <K, V> void zza(int i, zzvh<K, V> zzvh, Map<K, V> map) throws IOException {
        for (Entry entry : map.entrySet()) {
            this.zzbkr.zzg(i, 2);
            this.zzbkr.zzcr(zzvg.zza(zzvh, entry.getKey(), entry.getValue()));
            zzvg.zza(this.zzbkr, (zzvh) zzvh, entry.getKey(), entry.getValue());
        }
    }
}
