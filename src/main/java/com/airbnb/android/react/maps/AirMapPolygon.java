package com.airbnb.android.react.maps;

import android.content.Context;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import java.util.ArrayList;
import java.util.List;

public class AirMapPolygon extends AirMapFeature {
    private List<LatLng> coordinates;
    private int fillColor;
    private boolean geodesic;
    private List<List<LatLng>> holes;
    private Polygon polygon;
    private PolygonOptions polygonOptions;
    private int strokeColor;
    private float strokeWidth;
    private boolean tappable;
    private float zIndex;

    public AirMapPolygon(Context context) {
        super(context);
    }

    public void setCoordinates(ReadableArray readableArray) {
        this.coordinates = new ArrayList(readableArray.size());
        for (int i = 0; i < readableArray.size(); i++) {
            ReadableMap map = readableArray.getMap(i);
            this.coordinates.add(i, new LatLng(map.getDouble("latitude"), map.getDouble("longitude")));
        }
        Polygon polygon = this.polygon;
        if (polygon != null) {
            polygon.setPoints(this.coordinates);
        }
    }

    public void setHoles(ReadableArray readableArray) {
        if (readableArray != null) {
            this.holes = new ArrayList(readableArray.size());
            for (int i = 0; i < readableArray.size(); i++) {
                ReadableArray array = readableArray.getArray(i);
                if (array.size() >= 3) {
                    List arrayList = new ArrayList();
                    for (int i2 = 0; i2 < array.size(); i2++) {
                        ReadableMap map = array.getMap(i2);
                        arrayList.add(new LatLng(map.getDouble("latitude"), map.getDouble("longitude")));
                    }
                    if (arrayList.size() == 3) {
                        arrayList.add(arrayList.get(0));
                    }
                    this.holes.add(arrayList);
                }
            }
            Polygon polygon = this.polygon;
            if (polygon != null) {
                polygon.setHoles(this.holes);
            }
        }
    }

    public void setFillColor(int i) {
        this.fillColor = i;
        Polygon polygon = this.polygon;
        if (polygon != null) {
            polygon.setFillColor(i);
        }
    }

    public void setStrokeColor(int i) {
        this.strokeColor = i;
        Polygon polygon = this.polygon;
        if (polygon != null) {
            polygon.setStrokeColor(i);
        }
    }

    public void setStrokeWidth(float f) {
        this.strokeWidth = f;
        Polygon polygon = this.polygon;
        if (polygon != null) {
            polygon.setStrokeWidth(f);
        }
    }

    public void setTappable(boolean z) {
        this.tappable = z;
        Polygon polygon = this.polygon;
        if (polygon != null) {
            polygon.setClickable(this.tappable);
        }
    }

    public void setGeodesic(boolean z) {
        this.geodesic = z;
        Polygon polygon = this.polygon;
        if (polygon != null) {
            polygon.setGeodesic(z);
        }
    }

    public void setZIndex(float f) {
        this.zIndex = f;
        Polygon polygon = this.polygon;
        if (polygon != null) {
            polygon.setZIndex(f);
        }
    }

    public PolygonOptions getPolygonOptions() {
        if (this.polygonOptions == null) {
            this.polygonOptions = createPolygonOptions();
        }
        return this.polygonOptions;
    }

    private PolygonOptions createPolygonOptions() {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(this.coordinates);
        polygonOptions.fillColor(this.fillColor);
        polygonOptions.strokeColor(this.strokeColor);
        polygonOptions.strokeWidth(this.strokeWidth);
        polygonOptions.geodesic(this.geodesic);
        polygonOptions.zIndex(this.zIndex);
        if (this.holes != null) {
            for (int i = 0; i < this.holes.size(); i++) {
                polygonOptions.addHole((Iterable) this.holes.get(i));
            }
        }
        return polygonOptions;
    }

    public Object getFeature() {
        return this.polygon;
    }

    public void addToMap(GoogleMap googleMap) {
        this.polygon = googleMap.addPolygon(getPolygonOptions());
        this.polygon.setClickable(this.tappable);
    }

    public void removeFromMap(GoogleMap googleMap) {
        this.polygon.remove();
    }
}
