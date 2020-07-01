package com.facebook.common.references;

import java.lang.ref.SoftReference;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OOMSoftReference<T> {
    SoftReference<T> softRef1 = null;
    SoftReference<T> softRef2 = null;
    SoftReference<T> softRef3 = null;

    public void set(@Nonnull T t) {
        this.softRef1 = new SoftReference(t);
        this.softRef2 = new SoftReference(t);
        this.softRef3 = new SoftReference(t);
    }

    @Nullable
    public T get() {
        SoftReference softReference = this.softRef1;
        return softReference == null ? null : softReference.get();
    }

    public void clear() {
        SoftReference softReference = this.softRef1;
        if (softReference != null) {
            softReference.clear();
            this.softRef1 = null;
        }
        softReference = this.softRef2;
        if (softReference != null) {
            softReference.clear();
            this.softRef2 = null;
        }
        softReference = this.softRef3;
        if (softReference != null) {
            softReference.clear();
            this.softRef3 = null;
        }
    }
}
