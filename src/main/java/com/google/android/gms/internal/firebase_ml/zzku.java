package com.google.android.gms.internal.firebase_ml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class zzku {
    private final int limit;
    private final zzkc zzabj;
    private final boolean zzabk;
    private final zzky zzabl;

    private zzku(zzky zzky) {
        this(zzky, false, zzkg.zzabc, Integer.MAX_VALUE);
    }

    private zzku(zzky zzky, boolean z, zzkc zzkc, int i) {
        this.zzabl = zzky;
        this.zzabk = false;
        this.zzabj = zzkc;
        this.limit = Integer.MAX_VALUE;
    }

    public static zzku zza(zzkc zzkc) {
        zzks.checkNotNull(zzkc);
        return new zzku(new zzkv(zzkc));
    }

    public final List<String> zza(CharSequence charSequence) {
        zzks.checkNotNull(charSequence);
        Iterator zza = this.zzabl.zza(this, charSequence);
        List arrayList = new ArrayList();
        while (zza.hasNext()) {
            arrayList.add((String) zza.next());
        }
        return Collections.unmodifiableList(arrayList);
    }
}
