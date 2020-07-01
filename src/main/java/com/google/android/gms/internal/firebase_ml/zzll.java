package com.google.android.gms.internal.firebase_ml;

import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class zzll<E> extends zzlf<E> implements Set<E> {
    @NullableDecl
    private transient zzlg<E> zzadf;

    zzll() {
    }

    public boolean equals(@NullableDecl Object obj) {
        return obj == this ? true : zzls.zza(this, obj);
    }

    public int hashCode() {
        return zzls.zzb(this);
    }

    public zzlg<E> zzin() {
        zzlg<E> zzlg = this.zzadf;
        if (zzlg != null) {
            return zzlg;
        }
        zzlg = zziu();
        this.zzadf = zzlg;
        return zzlg;
    }

    zzlg<E> zziu() {
        return zzlg.zza(toArray());
    }
}
