package com.airbnb.android.react.maps;

import com.BV.LinearGradient.LinearGradientManager;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.WeightedLatLng;

public class AirMapHeatmapManager extends ViewGroupManager<AirMapHeatmap> {
    public String getName() {
        return "AIRMapHeatmap";
    }

    public AirMapHeatmap createViewInstance(ThemedReactContext themedReactContext) {
        return new AirMapHeatmap(themedReactContext);
    }

    @ReactProp(name = "points")
    public void setPoints(AirMapHeatmap airMapHeatmap, ReadableArray readableArray) {
        WeightedLatLng[] weightedLatLngArr = new WeightedLatLng[readableArray.size()];
        for (int i = 0; i < readableArray.size(); i++) {
            WeightedLatLng weightedLatLng;
            ReadableMap map = readableArray.getMap(i);
            LatLng latLng = new LatLng(map.getDouble("latitude"), map.getDouble("longitude"));
            String str = "weight";
            if (map.hasKey(str)) {
                weightedLatLng = new WeightedLatLng(latLng, map.getDouble(str));
            } else {
                weightedLatLng = new WeightedLatLng(latLng);
            }
            weightedLatLngArr[i] = weightedLatLng;
        }
        airMapHeatmap.setPoints(weightedLatLngArr);
    }

    @ReactProp(name = "gradient")
    public void setGradient(AirMapHeatmap airMapHeatmap, ReadableMap readableMap) {
        ReadableArray array = readableMap.getArray(LinearGradientManager.PROP_COLORS);
        int[] iArr = new int[array.size()];
        for (int i = 0; i < array.size(); i++) {
            iArr[i] = array.getInt(i);
        }
        array = readableMap.getArray("startPoints");
        float[] fArr = new float[array.size()];
        for (int i2 = 0; i2 < array.size(); i2++) {
            fArr[i2] = (float) array.getDouble(i2);
        }
        String str = "colorMapSize";
        if (readableMap.hasKey(str)) {
            airMapHeatmap.setGradient(new Gradient(iArr, fArr, readableMap.getInt(str)));
        } else {
            airMapHeatmap.setGradient(new Gradient(iArr, fArr));
        }
    }

    @ReactProp(name = "opacity")
    public void setOpacity(AirMapHeatmap airMapHeatmap, double d) {
        airMapHeatmap.setOpacity(d);
    }

    @ReactProp(name = "radius")
    public void setRadius(AirMapHeatmap airMapHeatmap, int i) {
        airMapHeatmap.setRadius(i);
    }
}
