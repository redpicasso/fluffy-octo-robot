package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

final class zzwg {
    private static final Class<?> zzbru = zzsv();
    private static final zzww<?, ?> zzbrv = zzak(false);
    private static final zzww<?, ?> zzbrw = zzak(true);
    private static final zzww<?, ?> zzbrx = new zzwy();

    public static void zzl(Class<?> cls) {
        if (!zzue.class.isAssignableFrom(cls)) {
            Class cls2 = zzbru;
            if (cls2 != null && !cls2.isAssignableFrom(cls)) {
                throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
            }
        }
    }

    public static void zza(int i, List<Double> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzg(i, list, z);
        }
    }

    public static void zzb(int i, List<Float> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzf(i, list, z);
        }
    }

    public static void zzc(int i, List<Long> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzc(i, list, z);
        }
    }

    public static void zzd(int i, List<Long> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzd(i, list, z);
        }
    }

    public static void zze(int i, List<Long> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzn(i, list, z);
        }
    }

    public static void zzf(int i, List<Long> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zze(i, list, z);
        }
    }

    public static void zzg(int i, List<Long> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzl(i, list, z);
        }
    }

    public static void zzh(int i, List<Integer> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zza(i, (List) list, z);
        }
    }

    public static void zzi(int i, List<Integer> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzj(i, list, z);
        }
    }

    public static void zzj(int i, List<Integer> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzm(i, list, z);
        }
    }

    public static void zzk(int i, List<Integer> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzb(i, (List) list, z);
        }
    }

    public static void zzl(int i, List<Integer> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzk(i, list, z);
        }
    }

    public static void zzm(int i, List<Integer> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzh(i, list, z);
        }
    }

    public static void zzn(int i, List<Boolean> list, zzxr zzxr, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzi(i, list, z);
        }
    }

    public static void zza(int i, List<String> list, zzxr zzxr) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zza(i, (List) list);
        }
    }

    public static void zzb(int i, List<zzsw> list, zzxr zzxr) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzb(i, (List) list);
        }
    }

    public static void zza(int i, List<?> list, zzxr zzxr, zzwe zzwe) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zza(i, (List) list, zzwe);
        }
    }

    public static void zzb(int i, List<?> list, zzxr zzxr, zzwe zzwe) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzxr.zzb(i, (List) list, zzwe);
        }
    }

    static int zzi(List<Long> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzvc) {
            zzvc zzvc = (zzvc) list;
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzt(zzvc.getLong(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzt(((Long) list.get(i)).longValue());
                i++;
            }
        }
        return i2;
    }

    static int zzo(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzi(list) + (list.size() * zztl.zzcu(i));
    }

    static int zzj(List<Long> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzvc) {
            zzvc zzvc = (zzvc) list;
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzu(zzvc.getLong(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzu(((Long) list.get(i)).longValue());
                i++;
            }
        }
        return i2;
    }

    static int zzp(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzj(list) + (size * zztl.zzcu(i));
    }

    static int zzk(List<Long> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzvc) {
            zzvc zzvc = (zzvc) list;
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzv(zzvc.getLong(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzv(((Long) list.get(i)).longValue());
                i++;
            }
        }
        return i2;
    }

    static int zzq(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzk(list) + (size * zztl.zzcu(i));
    }

    static int zzl(List<Integer> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzuf) {
            zzuf zzuf = (zzuf) list;
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzda(zzuf.getInt(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzda(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return i2;
    }

    static int zzr(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzl((List) list) + (size * zztl.zzcu(i));
    }

    static int zzm(List<Integer> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzuf) {
            zzuf zzuf = (zzuf) list;
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzcv(zzuf.getInt(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzcv(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return i2;
    }

    static int zzs(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzm(list) + (size * zztl.zzcu(i));
    }

    static int zzn(List<Integer> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzuf) {
            zzuf zzuf = (zzuf) list;
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzcw(zzuf.getInt(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzcw(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return i2;
    }

    static int zzt(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzn(list) + (size * zztl.zzcu(i));
    }

    static int zzo(List<Integer> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzuf) {
            zzuf zzuf = (zzuf) list;
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzcx(zzuf.getInt(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zztl.zzcx(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return i2;
    }

    static int zzu(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzo(list) + (size * zztl.zzcu(i));
    }

    static int zzp(List<?> list) {
        return list.size() << 2;
    }

    static int zzv(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zztl.zzo(i, 0);
    }

    static int zzq(List<?> list) {
        return list.size() << 3;
    }

    static int zzw(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zztl.zzg(i, 0);
    }

    static int zzr(List<?> list) {
        return list.size();
    }

    static int zzx(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zztl.zzc(i, true);
    }

    static int zzc(int i, List<?> list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        i = zztl.zzcu(i) * size;
        Object raw;
        int zzb;
        if (list instanceof zzux) {
            zzux zzux = (zzux) list;
            while (i2 < size) {
                raw = zzux.getRaw(i2);
                if (raw instanceof zzsw) {
                    zzb = zztl.zzb((zzsw) raw);
                } else {
                    zzb = zztl.zzcp((String) raw);
                }
                i += zzb;
                i2++;
            }
        } else {
            while (i2 < size) {
                raw = list.get(i2);
                if (raw instanceof zzsw) {
                    zzb = zztl.zzb((zzsw) raw);
                } else {
                    zzb = zztl.zzcp((String) raw);
                }
                i += zzb;
                i2++;
            }
        }
        return i;
    }

    static int zzc(int i, Object obj, zzwe zzwe) {
        if (obj instanceof zzuv) {
            return zztl.zza(i, (zzuv) obj);
        }
        return zztl.zzb(i, (zzvo) obj, zzwe);
    }

    static int zzc(int i, List<?> list, zzwe zzwe) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        i = zztl.zzcu(i) * size;
        while (i2 < size) {
            int zza;
            Object obj = list.get(i2);
            if (obj instanceof zzuv) {
                zza = zztl.zza((zzuv) obj);
            } else {
                zza = zztl.zza((zzvo) obj, zzwe);
            }
            i += zza;
            i2++;
        }
        return i;
    }

    static int zzd(int i, List<zzsw> list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        size *= zztl.zzcu(i);
        while (i2 < list.size()) {
            size += zztl.zzb((zzsw) list.get(i2));
            i2++;
        }
        return size;
    }

    static int zzd(int i, List<zzvo> list, zzwe zzwe) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int i3 = 0;
        while (i2 < size) {
            i3 += zztl.zzc(i, (zzvo) list.get(i2), zzwe);
            i2++;
        }
        return i3;
    }

    public static zzww<?, ?> zzss() {
        return zzbrv;
    }

    public static zzww<?, ?> zzst() {
        return zzbrw;
    }

    public static zzww<?, ?> zzsu() {
        return zzbrx;
    }

    private static zzww<?, ?> zzak(boolean z) {
        try {
            Class zzsw = zzsw();
            if (zzsw == null) {
                return null;
            }
            return (zzww) zzsw.getConstructor(new Class[]{Boolean.TYPE}).newInstance(new Object[]{Boolean.valueOf(z)});
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzsv() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzsw() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean zzh(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    static <T> void zza(zzvj zzvj, T t, T t2, long j) {
        zzxc.zza((Object) t, j, zzvj.zzf(zzxc.zzp(t, j), zzxc.zzp(t2, j)));
    }

    static <T, FT extends zzty<FT>> void zza(zztt<FT> zztt, T t, T t2) {
        zztw zzo = zztt.zzo(t2);
        if (!zzo.zzblk.isEmpty()) {
            zztt.zzp(t).zza(zzo);
        }
    }

    static <T, UT, UB> void zza(zzww<UT, UB> zzww, T t, T t2) {
        zzww.zzi(t, zzww.zzk(zzww.zzae(t), zzww.zzae(t2)));
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzuj zzuj, UB ub, zzww<UT, UB> zzww) {
        if (zzuj == null) {
            return ub;
        }
        UB ub2;
        int intValue;
        if (!(list instanceof RandomAccess)) {
            Iterator it = list.iterator();
            loop1:
            while (true) {
                ub2 = ub;
                while (it.hasNext()) {
                    intValue = ((Integer) it.next()).intValue();
                    if (!zzuj.zze(intValue)) {
                        ub = zza(i, intValue, (Object) ub2, (zzww) zzww);
                        it.remove();
                    }
                }
                break loop1;
            }
        }
        int size = list.size();
        ub2 = ub;
        intValue = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int intValue2 = ((Integer) list.get(i2)).intValue();
            if (zzuj.zze(intValue2)) {
                if (i2 != intValue) {
                    list.set(intValue, Integer.valueOf(intValue2));
                }
                intValue++;
            } else {
                ub2 = zza(i, intValue2, (Object) ub2, (zzww) zzww);
            }
        }
        if (intValue != size) {
            list.subList(intValue, size).clear();
        }
        return ub2;
    }

    private static <UT, UB> UB zza(int i, int i2, UB ub, zzww<UT, UB> zzww) {
        Object ub2;
        if (ub2 == null) {
            ub2 = zzww.zztf();
        }
        zzww.zza(ub2, i, (long) i2);
        return ub2;
    }
}
