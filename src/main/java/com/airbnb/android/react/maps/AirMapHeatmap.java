package com.airbnb.android.react.maps;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.HeatmapTileProvider.Builder;
import com.google.maps.android.heatmaps.WeightedLatLng;
import java.util.Arrays;
import java.util.List;

public class AirMapHeatmap extends AirMapFeature {
    private Gradient gradient;
    private TileOverlay heatmap;
    private TileOverlayOptions heatmapOptions;
    private HeatmapTileProvider heatmapTileProvider;
    private Double opacity;
    private List<WeightedLatLng> points;
    private Integer radius;

    public AirMapHeatmap(Context context) {
        super(context);
    }

    public void setPoints(WeightedLatLng[] weightedLatLngArr) {
        this.points = Arrays.asList(weightedLatLngArr);
        HeatmapTileProvider heatmapTileProvider = this.heatmapTileProvider;
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setWeightedData(this.points);
        }
        TileOverlay tileOverlay = this.heatmap;
        if (tileOverlay != null) {
            tileOverlay.clearTileCache();
        }
    }

    public void setGradient(Gradient gradient) {
        this.gradient = gradient;
        HeatmapTileProvider heatmapTileProvider = this.heatmapTileProvider;
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setGradient(gradient);
        }
        TileOverlay tileOverlay = this.heatmap;
        if (tileOverlay != null) {
            tileOverlay.clearTileCache();
        }
    }

    public void setOpacity(double d) {
        this.opacity = new Double(d);
        HeatmapTileProvider heatmapTileProvider = this.heatmapTileProvider;
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setOpacity(d);
        }
        TileOverlay tileOverlay = this.heatmap;
        if (tileOverlay != null) {
            tileOverlay.clearTileCache();
        }
    }

    public void setRadius(int i) {
        this.radius = new Integer(i);
        HeatmapTileProvider heatmapTileProvider = this.heatmapTileProvider;
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setRadius(i);
        }
        TileOverlay tileOverlay = this.heatmap;
        if (tileOverlay != null) {
            tileOverlay.clearTileCache();
        }
    }

    public TileOverlayOptions getHeatmapOptions() {
        if (this.heatmapOptions == null) {
            this.heatmapOptions = createHeatmapOptions();
        }
        return this.heatmapOptions;
    }

    private TileOverlayOptions createHeatmapOptions() {
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        if (this.heatmapTileProvider == null) {
            Builder weightedData = new Builder().weightedData(this.points);
            Integer num = this.radius;
            if (num != null) {
                weightedData.radius(num.intValue());
            }
            Double d = this.opacity;
            if (d != null) {
                weightedData.opacity(d.doubleValue());
            }
            Gradient gradient = this.gradient;
            if (gradient != null) {
                weightedData.gradient(gradient);
            }
            this.heatmapTileProvider = weightedData.build();
        }
        tileOverlayOptions.tileProvider(this.heatmapTileProvider);
        return tileOverlayOptions;
    }

    public Object getFeature() {
        return this.heatmap;
    }

    public void addToMap(GoogleMap googleMap) {
        Log.d("AirMapHeatmap", "ADD TO MAP");
        this.heatmap = googleMap.addTileOverlay(getHeatmapOptions());
    }

    public void removeFromMap(GoogleMap googleMap) {
        this.heatmap.remove();
    }
}
