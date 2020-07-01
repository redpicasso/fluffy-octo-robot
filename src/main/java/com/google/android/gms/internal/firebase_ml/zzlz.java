package com.google.android.gms.internal.firebase_ml;

import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

final class zzlz {
    private final ConcurrentHashMap<zzma, List<Throwable>> zzads = new ConcurrentHashMap(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzadt = new ReferenceQueue();

    zzlz() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        Object poll = this.zzadt.poll();
        while (poll != null) {
            this.zzads.remove(poll);
            poll = this.zzadt.poll();
        }
        List<Throwable> list = (List) this.zzads.get(new zzma(th, null));
        if (!z || list != null) {
            return list;
        }
        List vector = new Vector(2);
        List<Throwable> list2 = (List) this.zzads.putIfAbsent(new zzma(th, this.zzadt), vector);
        return list2 == null ? vector : list2;
    }
}
