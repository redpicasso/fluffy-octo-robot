package com.google.android.gms.common.data;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.ArrayList;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public abstract class EntityBuffer<T> extends AbstractDataBuffer<T> {
    private boolean zamh = false;
    private ArrayList<Integer> zami;

    @KeepForSdk
    protected EntityBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @KeepForSdk
    protected String getChildDataMarkerColumn() {
        return null;
    }

    @KeepForSdk
    protected abstract T getEntry(int i, int i2);

    @KeepForSdk
    protected abstract String getPrimaryDataMarkerColumn();

    /* JADX WARNING: Missing block: B:13:0x0063, code:
            if (r6.mDataHolder.getString(r4, r7, r3) == null) goto L_0x0067;
     */
    @com.google.android.gms.common.annotation.KeepForSdk
    public final T get(int r7) {
        /*
        r6 = this;
        r6.zabz();
        r0 = r6.zah(r7);
        r1 = 0;
        if (r7 < 0) goto L_0x0067;
    L_0x000a:
        r2 = r6.zami;
        r2 = r2.size();
        if (r7 != r2) goto L_0x0013;
    L_0x0012:
        goto L_0x0067;
    L_0x0013:
        r2 = r6.zami;
        r2 = r2.size();
        r3 = 1;
        r2 = r2 - r3;
        if (r7 != r2) goto L_0x0030;
    L_0x001d:
        r2 = r6.mDataHolder;
        r2 = r2.getCount();
        r4 = r6.zami;
        r4 = r4.get(r7);
        r4 = (java.lang.Integer) r4;
        r4 = r4.intValue();
        goto L_0x004a;
    L_0x0030:
        r2 = r6.zami;
        r4 = r7 + 1;
        r2 = r2.get(r4);
        r2 = (java.lang.Integer) r2;
        r2 = r2.intValue();
        r4 = r6.zami;
        r4 = r4.get(r7);
        r4 = (java.lang.Integer) r4;
        r4 = r4.intValue();
    L_0x004a:
        r2 = r2 - r4;
        if (r2 != r3) goto L_0x0066;
    L_0x004d:
        r7 = r6.zah(r7);
        r3 = r6.mDataHolder;
        r3 = r3.getWindowIndex(r7);
        r4 = r6.getChildDataMarkerColumn();
        if (r4 == 0) goto L_0x0066;
    L_0x005d:
        r5 = r6.mDataHolder;
        r7 = r5.getString(r4, r7, r3);
        if (r7 != 0) goto L_0x0066;
    L_0x0065:
        goto L_0x0067;
    L_0x0066:
        r1 = r2;
    L_0x0067:
        r7 = r6.getEntry(r0, r1);
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.EntityBuffer.get(int):T");
    }

    @KeepForSdk
    public int getCount() {
        zabz();
        return this.zami.size();
    }

    private final void zabz() {
        synchronized (this) {
            if (!this.zamh) {
                int count = this.mDataHolder.getCount();
                this.zami = new ArrayList();
                if (count > 0) {
                    this.zami.add(Integer.valueOf(0));
                    String primaryDataMarkerColumn = getPrimaryDataMarkerColumn();
                    Object string = this.mDataHolder.getString(primaryDataMarkerColumn, 0, this.mDataHolder.getWindowIndex(0));
                    int i = 1;
                    while (i < count) {
                        int windowIndex = this.mDataHolder.getWindowIndex(i);
                        String string2 = this.mDataHolder.getString(primaryDataMarkerColumn, i, windowIndex);
                        if (string2 != null) {
                            if (!string2.equals(string)) {
                                this.zami.add(Integer.valueOf(i));
                                string = string2;
                            }
                            i++;
                        } else {
                            StringBuilder stringBuilder = new StringBuilder(String.valueOf(primaryDataMarkerColumn).length() + 78);
                            stringBuilder.append("Missing value for markerColumn: ");
                            stringBuilder.append(primaryDataMarkerColumn);
                            stringBuilder.append(", at row: ");
                            stringBuilder.append(i);
                            stringBuilder.append(", for window: ");
                            stringBuilder.append(windowIndex);
                            throw new NullPointerException(stringBuilder.toString());
                        }
                    }
                }
                this.zamh = true;
            }
        }
    }

    private final int zah(int i) {
        if (i >= 0 && i < this.zami.size()) {
            return ((Integer) this.zami.get(i)).intValue();
        }
        StringBuilder stringBuilder = new StringBuilder(53);
        stringBuilder.append("Position ");
        stringBuilder.append(i);
        stringBuilder.append(" is out of bounds for this buffer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
