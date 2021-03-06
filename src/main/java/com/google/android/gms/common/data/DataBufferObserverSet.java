package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBufferObserver.Observable;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class DataBufferObserverSet implements DataBufferObserver, Observable {
    private HashSet<DataBufferObserver> zalq = new HashSet();

    public final boolean hasObservers() {
        return !this.zalq.isEmpty();
    }

    public final void clear() {
        this.zalq.clear();
    }

    public final void addObserver(DataBufferObserver dataBufferObserver) {
        this.zalq.add(dataBufferObserver);
    }

    public final void removeObserver(DataBufferObserver dataBufferObserver) {
        this.zalq.remove(dataBufferObserver);
    }

    public final void onDataChanged() {
        Iterator it = this.zalq.iterator();
        while (it.hasNext()) {
            ((DataBufferObserver) it.next()).onDataChanged();
        }
    }

    public final void onDataRangeChanged(int i, int i2) {
        Iterator it = this.zalq.iterator();
        while (it.hasNext()) {
            ((DataBufferObserver) it.next()).onDataRangeChanged(i, i2);
        }
    }

    public final void onDataRangeInserted(int i, int i2) {
        Iterator it = this.zalq.iterator();
        while (it.hasNext()) {
            ((DataBufferObserver) it.next()).onDataRangeInserted(i, i2);
        }
    }

    public final void onDataRangeRemoved(int i, int i2) {
        Iterator it = this.zalq.iterator();
        while (it.hasNext()) {
            ((DataBufferObserver) it.next()).onDataRangeRemoved(i, i2);
        }
    }

    public final void onDataRangeMoved(int i, int i2, int i3) {
        Iterator it = this.zalq.iterator();
        while (it.hasNext()) {
            ((DataBufferObserver) it.next()).onDataRangeMoved(i, i2, i3);
        }
    }
}
