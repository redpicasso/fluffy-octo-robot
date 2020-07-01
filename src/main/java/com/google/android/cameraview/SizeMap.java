package com.google.android.cameraview;

import androidx.collection.ArrayMap;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

class SizeMap {
    private final ArrayMap<AspectRatio, SortedSet<Size>> mRatios = new ArrayMap();

    SizeMap() {
    }

    public boolean add(Size size) {
        SortedSet sortedSet;
        for (AspectRatio aspectRatio : this.mRatios.keySet()) {
            if (aspectRatio.matches(size)) {
                sortedSet = (SortedSet) this.mRatios.get(aspectRatio);
                if (sortedSet.contains(size)) {
                    return false;
                }
                sortedSet.add(size);
                return true;
            }
        }
        sortedSet = new TreeSet();
        sortedSet.add(size);
        this.mRatios.put(AspectRatio.of(size.getWidth(), size.getHeight()), sortedSet);
        return true;
    }

    public void remove(AspectRatio aspectRatio) {
        this.mRatios.remove(aspectRatio);
    }

    Set<AspectRatio> ratios() {
        return this.mRatios.keySet();
    }

    SortedSet<Size> sizes(AspectRatio aspectRatio) {
        return (SortedSet) this.mRatios.get(aspectRatio);
    }

    void clear() {
        this.mRatios.clear();
    }

    boolean isEmpty() {
        return this.mRatios.isEmpty();
    }
}
