package io.grpc.internal;

import java.util.HashSet;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
abstract class InUseStateAggregator<T> {
    private final HashSet<T> inUseObjects = new HashSet();

    abstract void handleInUse();

    abstract void handleNotInUse();

    InUseStateAggregator() {
    }

    final void updateObjectInUse(T t, boolean z) {
        int size = this.inUseObjects.size();
        if (z) {
            this.inUseObjects.add(t);
            if (size == 0) {
                handleInUse();
            }
        } else if (this.inUseObjects.remove(t) && size == 1) {
            handleNotInUse();
        }
    }

    final boolean isInUse() {
        return this.inUseObjects.isEmpty() ^ 1;
    }
}
