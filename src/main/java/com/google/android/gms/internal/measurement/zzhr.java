package com.google.android.gms.internal.measurement;

final class zzhr extends zzhp<zzhs, zzhs> {
    zzhr() {
    }

    final boolean zza(zzgy zzgy) {
        return false;
    }

    private static void zza(Object obj, zzhs zzhs) {
        ((zzey) obj).zzahz = zzhs;
    }

    final void zzj(Object obj) {
        ((zzey) obj).zzahz.zzry();
    }

    final /* synthetic */ Object zzg(Object obj, Object obj2) {
        zzhs zzhs = (zzhs) obj;
        zzhs zzhs2 = (zzhs) obj2;
        if (zzhs2.equals(zzhs.zzwq())) {
            return zzhs;
        }
        return zzhs.zza(zzhs, zzhs2);
    }

    final /* synthetic */ Object zzy(Object obj) {
        zzhs zzhs = ((zzey) obj).zzahz;
        if (zzhs != zzhs.zzwq()) {
            return zzhs;
        }
        zzhs = zzhs.zzwr();
        zza(obj, zzhs);
        return zzhs;
    }

    final /* synthetic */ Object zzx(Object obj) {
        return ((zzey) obj).zzahz;
    }

    final /* synthetic */ Object zzp(Object obj) {
        zzhs zzhs = (zzhs) obj;
        zzhs.zzry();
        return zzhs;
    }

    final /* synthetic */ void zzb(Object obj, int i, long j) {
        ((zzhs) obj).zzb((i << 3) | 1, Long.valueOf(j));
    }

    final /* synthetic */ void zzc(Object obj, int i, int i2) {
        ((zzhs) obj).zzb((i << 3) | 5, Integer.valueOf(i2));
    }
}
