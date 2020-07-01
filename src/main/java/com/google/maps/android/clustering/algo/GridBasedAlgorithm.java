package com.google.maps.android.clustering.algo;

import androidx.collection.LongSparseArray;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.geometry.Point;
import com.google.maps.android.projection.SphericalMercatorProjection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GridBasedAlgorithm<T extends ClusterItem> implements Algorithm<T> {
    private static final int GRID_SIZE = 100;
    private final Set<T> mItems = Collections.synchronizedSet(new HashSet());

    public void addItem(T t) {
        this.mItems.add(t);
    }

    public void addItems(Collection<T> collection) {
        this.mItems.addAll(collection);
    }

    public void clearItems() {
        this.mItems.clear();
    }

    public void removeItem(T t) {
        this.mItems.remove(t);
    }

    public Set<? extends Cluster<T>> getClusters(double d) {
        long ceil = (long) Math.ceil((Math.pow(2.0d, d) * 256.0d) / 100.0d);
        SphericalMercatorProjection sphericalMercatorProjection = new SphericalMercatorProjection((double) ceil);
        Set hashSet = new HashSet();
        LongSparseArray longSparseArray = new LongSparseArray();
        synchronized (this.mItems) {
            for (ClusterItem clusterItem : this.mItems) {
                long j;
                Point toPoint = sphericalMercatorProjection.toPoint(clusterItem.getPosition());
                long coord = getCoord(ceil, toPoint.x, toPoint.y);
                StaticCluster staticCluster = (StaticCluster) longSparseArray.get(coord);
                if (staticCluster == null) {
                    j = ceil;
                    staticCluster = new StaticCluster(sphericalMercatorProjection.toLatLng(new Point(Math.floor(toPoint.x) + 0.5d, Math.floor(toPoint.y) + 0.5d)));
                    longSparseArray.put(coord, staticCluster);
                    hashSet.add(staticCluster);
                } else {
                    j = ceil;
                }
                staticCluster.add(clusterItem);
                ceil = j;
            }
        }
        return hashSet;
    }

    public Collection<T> getItems() {
        return this.mItems;
    }

    private static long getCoord(long j, double d, double d2) {
        return (long) ((((double) j) * Math.floor(d)) + Math.floor(d2));
    }
}
