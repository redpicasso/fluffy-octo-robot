package com.airbnb.android.react.maps;

import android.content.Context;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import java.util.ArrayList;
import java.util.List;

public class AirMapPolyline extends AirMapFeature {
    private int color;
    private List<LatLng> coordinates;
    private boolean geodesic;
    private Cap lineCap = new RoundCap();
    private List<PatternItem> pattern;
    private ReadableArray patternValues;
    private Polyline polyline;
    private PolylineOptions polylineOptions;
    private boolean tappable;
    private float width;
    private float zIndex;

    public AirMapPolyline(Context context) {
        super(context);
    }

    public void setCoordinates(ReadableArray readableArray) {
        this.coordinates = new ArrayList(readableArray.size());
        for (int i = 0; i < readableArray.size(); i++) {
            ReadableMap map = readableArray.getMap(i);
            this.coordinates.add(i, new LatLng(map.getDouble("latitude"), map.getDouble("longitude")));
        }
        Polyline polyline = this.polyline;
        if (polyline != null) {
            polyline.setPoints(this.coordinates);
        }
    }

    public void setColor(int i) {
        this.color = i;
        Polyline polyline = this.polyline;
        if (polyline != null) {
            polyline.setColor(i);
        }
    }

    public void setWidth(float f) {
        this.width = f;
        Polyline polyline = this.polyline;
        if (polyline != null) {
            polyline.setWidth(f);
        }
    }

    public void setZIndex(float f) {
        this.zIndex = f;
        Polyline polyline = this.polyline;
        if (polyline != null) {
            polyline.setZIndex(f);
        }
    }

    public void setTappable(boolean z) {
        this.tappable = z;
        Polyline polyline = this.polyline;
        if (polyline != null) {
            polyline.setClickable(this.tappable);
        }
    }

    public void setGeodesic(boolean z) {
        this.geodesic = z;
        Polyline polyline = this.polyline;
        if (polyline != null) {
            polyline.setGeodesic(z);
        }
    }

    public void setLineCap(Cap cap) {
        this.lineCap = cap;
        Polyline polyline = this.polyline;
        if (polyline != null) {
            polyline.setStartCap(cap);
            this.polyline.setEndCap(cap);
        }
        applyPattern();
    }

    public void setLineDashPattern(ReadableArray readableArray) {
        this.patternValues = readableArray;
        applyPattern();
    }

    private void applyPattern() {
        ReadableArray readableArray = this.patternValues;
        if (readableArray != null) {
            this.pattern = new ArrayList(readableArray.size());
            for (int i = 0; i < this.patternValues.size(); i++) {
                float f = (float) this.patternValues.getDouble(i);
                if ((i % 2 != 0 ? 1 : null) != null) {
                    this.pattern.add(new Gap(f));
                } else {
                    Object dot;
                    if (this.lineCap instanceof RoundCap) {
                        dot = new Dot();
                    } else {
                        dot = new Dash(f);
                    }
                    this.pattern.add(dot);
                }
            }
            Polyline polyline = this.polyline;
            if (polyline != null) {
                polyline.setPattern(this.pattern);
            }
        }
    }

    public PolylineOptions getPolylineOptions() {
        if (this.polylineOptions == null) {
            this.polylineOptions = createPolylineOptions();
        }
        return this.polylineOptions;
    }

    private PolylineOptions createPolylineOptions() {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(this.coordinates);
        polylineOptions.color(this.color);
        polylineOptions.width(this.width);
        polylineOptions.geodesic(this.geodesic);
        polylineOptions.zIndex(this.zIndex);
        polylineOptions.startCap(this.lineCap);
        polylineOptions.endCap(this.lineCap);
        polylineOptions.pattern(this.pattern);
        return polylineOptions;
    }

    public Object getFeature() {
        return this.polyline;
    }

    public void addToMap(GoogleMap googleMap) {
        this.polyline = googleMap.addPolyline(getPolylineOptions());
        this.polyline.setClickable(this.tappable);
    }

    public void removeFromMap(GoogleMap googleMap) {
        this.polyline.remove();
    }
}
