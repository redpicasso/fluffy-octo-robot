package com.google.maps.android.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.collection.LruCache;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.google.maps.android.R;
import com.google.maps.android.data.geojson.BiMultiMap;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLineString;
import com.google.maps.android.data.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.data.geojson.GeoJsonMultiLineString;
import com.google.maps.android.data.geojson.GeoJsonMultiPoint;
import com.google.maps.android.data.geojson.GeoJsonMultiPolygon;
import com.google.maps.android.data.geojson.GeoJsonPoint;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import com.google.maps.android.data.geojson.GeoJsonPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlGroundOverlay;
import com.google.maps.android.data.kml.KmlMultiGeometry;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPoint;
import com.google.maps.android.data.kml.KmlPolygon;
import com.google.maps.android.data.kml.KmlStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Renderer {
    private static final Object FEATURE_NOT_ON_MAP = null;
    private static final int LRU_CACHE_SIZE = 50;
    private BiMultiMap<Feature> mContainerFeatures;
    private ArrayList<KmlContainer> mContainers;
    private Context mContext;
    private final GeoJsonLineStringStyle mDefaultLineStringStyle;
    private final GeoJsonPointStyle mDefaultPointStyle;
    private final GeoJsonPolygonStyle mDefaultPolygonStyle;
    private final BiMultiMap<Feature> mFeatures = new BiMultiMap();
    private HashMap<KmlGroundOverlay, GroundOverlay> mGroundOverlays;
    private final LruCache<String, Bitmap> mImagesCache;
    private boolean mLayerOnMap;
    private GoogleMap mMap;
    private final ArrayList<String> mMarkerIconUrls;
    private HashMap<String, String> mStyleMaps;
    private HashMap<String, KmlStyle> mStyles;
    private HashMap<String, KmlStyle> mStylesRenderer;

    public Renderer(GoogleMap googleMap, Context context) {
        this.mMap = googleMap;
        this.mContext = context;
        this.mLayerOnMap = false;
        this.mImagesCache = new LruCache(50);
        this.mMarkerIconUrls = new ArrayList();
        this.mStylesRenderer = new HashMap();
        this.mDefaultPointStyle = null;
        this.mDefaultLineStringStyle = null;
        this.mDefaultPolygonStyle = null;
        this.mContainerFeatures = new BiMultiMap();
    }

    public Renderer(GoogleMap googleMap, HashMap<? extends Feature, Object> hashMap) {
        this.mMap = googleMap;
        this.mFeatures.putAll(hashMap);
        this.mLayerOnMap = false;
        this.mMarkerIconUrls = null;
        this.mDefaultPointStyle = new GeoJsonPointStyle();
        this.mDefaultLineStringStyle = new GeoJsonLineStringStyle();
        this.mDefaultPolygonStyle = new GeoJsonPolygonStyle();
        this.mImagesCache = null;
        this.mContainerFeatures = null;
    }

    public boolean isLayerOnMap() {
        return this.mLayerOnMap;
    }

    protected void setLayerVisibility(boolean z) {
        this.mLayerOnMap = z;
    }

    public GoogleMap getMap() {
        return this.mMap;
    }

    public void setMap(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    protected void putContainerFeature(Object obj, Feature feature) {
        this.mContainerFeatures.put((Object) feature, obj);
    }

    public Set<Feature> getFeatures() {
        return this.mFeatures.keySet();
    }

    public Feature getFeature(Object obj) {
        return (Feature) this.mFeatures.getKey(obj);
    }

    public Feature getContainerFeature(Object obj) {
        BiMultiMap biMultiMap = this.mContainerFeatures;
        return biMultiMap != null ? (Feature) biMultiMap.getKey(obj) : null;
    }

    public Collection<Object> getValues() {
        return this.mFeatures.values();
    }

    /* renamed from: getAllFeatures */
    protected HashMap<? extends Feature, Object> access$000() {
        return this.mFeatures;
    }

    public ArrayList<String> getMarkerIconUrls() {
        return this.mMarkerIconUrls;
    }

    public HashMap<String, KmlStyle> getStylesRenderer() {
        return this.mStylesRenderer;
    }

    public HashMap<String, String> getStyleMaps() {
        return this.mStyleMaps;
    }

    public LruCache<String, Bitmap> getImagesCache() {
        return this.mImagesCache;
    }

    public HashMap<KmlGroundOverlay, GroundOverlay> getGroundOverlayMap() {
        return this.mGroundOverlays;
    }

    public ArrayList<KmlContainer> getContainerList() {
        return this.mContainers;
    }

    protected KmlStyle getPlacemarkStyle(String str) {
        return this.mStylesRenderer.get(str) != null ? (KmlStyle) this.mStylesRenderer.get(str) : (KmlStyle) this.mStylesRenderer.get(null);
    }

    public GeoJsonPointStyle getDefaultPointStyle() {
        return this.mDefaultPointStyle;
    }

    public GeoJsonLineStringStyle getDefaultLineStringStyle() {
        return this.mDefaultLineStringStyle;
    }

    public GeoJsonPolygonStyle getDefaultPolygonStyle() {
        return this.mDefaultPolygonStyle;
    }

    public void putFeatures(Feature feature, Object obj) {
        this.mFeatures.put((Object) feature, obj);
    }

    public void putStyles() {
        this.mStylesRenderer.putAll(this.mStyles);
    }

    public void putStyles(HashMap<String, KmlStyle> hashMap) {
        this.mStylesRenderer.putAll(hashMap);
    }

    public void putImagesCache(String str, Bitmap bitmap) {
        this.mImagesCache.put(str, bitmap);
    }

    public boolean hasFeatures() {
        return this.mFeatures.size() > 0;
    }

    protected static void removeFeatures(HashMap<Feature, Object> hashMap) {
        for (Object next : hashMap.values()) {
            if (next instanceof Marker) {
                ((Marker) next).remove();
            } else if (next instanceof Polyline) {
                ((Polyline) next).remove();
            } else if (next instanceof Polygon) {
                ((Polygon) next).remove();
            }
        }
    }

    protected void removeFeature(Feature feature) {
        if (this.mFeatures.containsKey(feature)) {
            removeFromMap(this.mFeatures.remove(feature));
        }
    }

    private void setFeatureDefaultStyles(GeoJsonFeature geoJsonFeature) {
        if (geoJsonFeature.getPointStyle() == null) {
            geoJsonFeature.setPointStyle(this.mDefaultPointStyle);
        }
        if (geoJsonFeature.getLineStringStyle() == null) {
            geoJsonFeature.setLineStringStyle(this.mDefaultLineStringStyle);
        }
        if (geoJsonFeature.getPolygonStyle() == null) {
            geoJsonFeature.setPolygonStyle(this.mDefaultPolygonStyle);
        }
    }

    public void clearStylesRenderer() {
        this.mStylesRenderer.clear();
    }

    protected void storeData(HashMap<String, KmlStyle> hashMap, HashMap<String, String> hashMap2, HashMap<KmlPlacemark, Object> hashMap3, ArrayList<KmlContainer> arrayList, HashMap<KmlGroundOverlay, GroundOverlay> hashMap4) {
        this.mStyles = hashMap;
        this.mStyleMaps = hashMap2;
        this.mFeatures.putAll(hashMap3);
        this.mContainers = arrayList;
        this.mGroundOverlays = hashMap4;
    }

    public void addFeature(Feature feature) {
        Object obj = FEATURE_NOT_ON_MAP;
        if (feature instanceof GeoJsonFeature) {
            setFeatureDefaultStyles((GeoJsonFeature) feature);
        }
        if (this.mLayerOnMap) {
            if (this.mFeatures.containsKey(feature)) {
                removeFromMap(this.mFeatures.get(feature));
            }
            if (feature.hasGeometry()) {
                if (feature instanceof KmlPlacemark) {
                    KmlPlacemark kmlPlacemark = (KmlPlacemark) feature;
                    obj = addKmlPlacemarkToMap(kmlPlacemark, feature.getGeometry(), getPlacemarkStyle(feature.getId()), kmlPlacemark.getInlineStyle(), getPlacemarkVisibility(feature));
                } else {
                    obj = addGeoJsonFeatureToMap(feature, feature.getGeometry());
                }
            }
        }
        this.mFeatures.put((Object) feature, obj);
    }

    public static void removeFromMap(Object obj) {
        if (obj instanceof Marker) {
            ((Marker) obj).remove();
        } else if (obj instanceof Polyline) {
            ((Polyline) obj).remove();
        } else if (obj instanceof Polygon) {
            ((Polygon) obj).remove();
        } else if (obj instanceof ArrayList) {
            Iterator it = ((ArrayList) obj).iterator();
            while (it.hasNext()) {
                removeFromMap(it.next());
            }
        }
    }

    protected java.lang.Object addGeoJsonFeatureToMap(com.google.maps.android.data.Feature r3, com.google.maps.android.data.Geometry r4) {
        /*
        r2 = this;
        r0 = r4.getGeometryType();
        r1 = r0.hashCode();
        switch(r1) {
            case -2116761119: goto L_0x0048;
            case -1065891849: goto L_0x003e;
            case -627102946: goto L_0x0034;
            case 77292912: goto L_0x002a;
            case 1267133722: goto L_0x0020;
            case 1806700869: goto L_0x0016;
            case 1950410960: goto L_0x000c;
            default: goto L_0x000b;
        };
    L_0x000b:
        goto L_0x0052;
    L_0x000c:
        r1 = "GeometryCollection";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0052;
    L_0x0014:
        r0 = 6;
        goto L_0x0053;
    L_0x0016:
        r1 = "LineString";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0052;
    L_0x001e:
        r0 = 1;
        goto L_0x0053;
    L_0x0020:
        r1 = "Polygon";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0052;
    L_0x0028:
        r0 = 2;
        goto L_0x0053;
    L_0x002a:
        r1 = "Point";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0052;
    L_0x0032:
        r0 = 0;
        goto L_0x0053;
    L_0x0034:
        r1 = "MultiLineString";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0052;
    L_0x003c:
        r0 = 4;
        goto L_0x0053;
    L_0x003e:
        r1 = "MultiPoint";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0052;
    L_0x0046:
        r0 = 3;
        goto L_0x0053;
    L_0x0048:
        r1 = "MultiPolygon";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0052;
    L_0x0050:
        r0 = 5;
        goto L_0x0053;
    L_0x0052:
        r0 = -1;
    L_0x0053:
        r1 = 0;
        switch(r0) {
            case 0: goto L_0x00c4;
            case 1: goto L_0x00a8;
            case 2: goto L_0x008c;
            case 3: goto L_0x007f;
            case 4: goto L_0x0072;
            case 5: goto L_0x0065;
            case 6: goto L_0x0058;
            default: goto L_0x0057;
        };
    L_0x0057:
        return r1;
    L_0x0058:
        r3 = (com.google.maps.android.data.geojson.GeoJsonFeature) r3;
        r4 = (com.google.maps.android.data.geojson.GeoJsonGeometryCollection) r4;
        r4 = r4.getGeometries();
        r3 = r2.addGeometryCollectionToMap(r3, r4);
        return r3;
    L_0x0065:
        r3 = (com.google.maps.android.data.geojson.GeoJsonFeature) r3;
        r3 = r3.getPolygonStyle();
        r4 = (com.google.maps.android.data.geojson.GeoJsonMultiPolygon) r4;
        r3 = r2.addMultiPolygonToMap(r3, r4);
        return r3;
    L_0x0072:
        r3 = (com.google.maps.android.data.geojson.GeoJsonFeature) r3;
        r3 = r3.getLineStringStyle();
        r4 = (com.google.maps.android.data.geojson.GeoJsonMultiLineString) r4;
        r3 = r2.addMultiLineStringToMap(r3, r4);
        return r3;
    L_0x007f:
        r3 = (com.google.maps.android.data.geojson.GeoJsonFeature) r3;
        r3 = r3.getPointStyle();
        r4 = (com.google.maps.android.data.geojson.GeoJsonMultiPoint) r4;
        r3 = r2.addMultiPointToMap(r3, r4);
        return r3;
    L_0x008c:
        r0 = r3 instanceof com.google.maps.android.data.geojson.GeoJsonFeature;
        if (r0 == 0) goto L_0x0097;
    L_0x0090:
        r3 = (com.google.maps.android.data.geojson.GeoJsonFeature) r3;
        r1 = r3.getPolygonOptions();
        goto L_0x00a1;
    L_0x0097:
        r0 = r3 instanceof com.google.maps.android.data.kml.KmlPlacemark;
        if (r0 == 0) goto L_0x00a1;
    L_0x009b:
        r3 = (com.google.maps.android.data.kml.KmlPlacemark) r3;
        r1 = r3.getPolygonOptions();
    L_0x00a1:
        r4 = (com.google.maps.android.data.DataPolygon) r4;
        r3 = r2.addPolygonToMap(r1, r4);
        return r3;
    L_0x00a8:
        r0 = r3 instanceof com.google.maps.android.data.geojson.GeoJsonFeature;
        if (r0 == 0) goto L_0x00b3;
    L_0x00ac:
        r3 = (com.google.maps.android.data.geojson.GeoJsonFeature) r3;
        r1 = r3.getPolylineOptions();
        goto L_0x00bd;
    L_0x00b3:
        r0 = r3 instanceof com.google.maps.android.data.kml.KmlPlacemark;
        if (r0 == 0) goto L_0x00bd;
    L_0x00b7:
        r3 = (com.google.maps.android.data.kml.KmlPlacemark) r3;
        r1 = r3.getPolylineOptions();
    L_0x00bd:
        r4 = (com.google.maps.android.data.geojson.GeoJsonLineString) r4;
        r3 = r2.addLineStringToMap(r1, r4);
        return r3;
    L_0x00c4:
        r0 = r3 instanceof com.google.maps.android.data.geojson.GeoJsonFeature;
        if (r0 == 0) goto L_0x00cf;
    L_0x00c8:
        r3 = (com.google.maps.android.data.geojson.GeoJsonFeature) r3;
        r1 = r3.getMarkerOptions();
        goto L_0x00d9;
    L_0x00cf:
        r0 = r3 instanceof com.google.maps.android.data.kml.KmlPlacemark;
        if (r0 == 0) goto L_0x00d9;
    L_0x00d3:
        r3 = (com.google.maps.android.data.kml.KmlPlacemark) r3;
        r1 = r3.getMarkerOptions();
    L_0x00d9:
        r4 = (com.google.maps.android.data.geojson.GeoJsonPoint) r4;
        r3 = r2.addPointToMap(r1, r4);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.maps.android.data.Renderer.addGeoJsonFeatureToMap(com.google.maps.android.data.Feature, com.google.maps.android.data.Geometry):java.lang.Object");
    }

    protected Object addKmlPlacemarkToMap(KmlPlacemark kmlPlacemark, Geometry geometry, KmlStyle kmlStyle, KmlStyle kmlStyle2, boolean z) {
        String geometryType = geometry.getGeometryType();
        String str = "drawOrder";
        boolean hasProperty = kmlPlacemark.hasProperty(str);
        float f = 0.0f;
        if (hasProperty) {
            try {
                f = Float.parseFloat(kmlPlacemark.getProperty(str));
            } catch (NumberFormatException unused) {
                hasProperty = false;
            }
        }
        int i = -1;
        switch (geometryType.hashCode()) {
            case 77292912:
                if (geometryType.equals("Point")) {
                    i = 0;
                    break;
                }
                break;
            case 89139371:
                if (geometryType.equals("MultiGeometry")) {
                    i = 3;
                    break;
                }
                break;
            case 1267133722:
                if (geometryType.equals(KmlPolygon.GEOMETRY_TYPE)) {
                    i = 2;
                    break;
                }
                break;
            case 1806700869:
                if (geometryType.equals("LineString")) {
                    i = 1;
                    break;
                }
                break;
        }
        if (i == 0) {
            MarkerOptions markerOptions = kmlStyle.getMarkerOptions();
            if (kmlStyle2 != null) {
                setInlinePointStyle(markerOptions, kmlStyle2, kmlStyle.getIconUrl());
            } else if (kmlStyle.getIconUrl() != null) {
                addMarkerIcons(kmlStyle.getIconUrl(), markerOptions);
            }
            Marker addPointToMap = addPointToMap(markerOptions, (KmlPoint) geometry);
            addPointToMap.setVisible(z);
            setMarkerInfoWindow(kmlStyle, addPointToMap, kmlPlacemark);
            if (hasProperty) {
                addPointToMap.setZIndex(f);
            }
            return addPointToMap;
        } else if (i == 1) {
            PolylineOptions polylineOptions = kmlStyle.getPolylineOptions();
            if (kmlStyle2 != null) {
                setInlineLineStringStyle(polylineOptions, kmlStyle2);
            } else if (kmlStyle.isLineRandomColorMode()) {
                polylineOptions.color(KmlStyle.computeRandomColor(polylineOptions.getColor()));
            }
            Polyline addLineStringToMap = addLineStringToMap(polylineOptions, (LineString) geometry);
            addLineStringToMap.setVisible(z);
            if (hasProperty) {
                addLineStringToMap.setZIndex(f);
            }
            return addLineStringToMap;
        } else if (i == 2) {
            PolygonOptions polygonOptions = kmlStyle.getPolygonOptions();
            if (kmlStyle2 != null) {
                setInlinePolygonStyle(polygonOptions, kmlStyle2);
            } else if (kmlStyle.isPolyRandomColorMode()) {
                polygonOptions.fillColor(KmlStyle.computeRandomColor(polygonOptions.getFillColor()));
            }
            Polygon addPolygonToMap = addPolygonToMap(polygonOptions, (DataPolygon) geometry);
            addPolygonToMap.setVisible(z);
            if (hasProperty) {
                addPolygonToMap.setZIndex(f);
            }
            return addPolygonToMap;
        } else if (i != 3) {
            return null;
        } else {
            return addMultiGeometryToMap(kmlPlacemark, (KmlMultiGeometry) geometry, kmlStyle, kmlStyle2, z);
        }
    }

    protected Marker addPointToMap(MarkerOptions markerOptions, Point point) {
        markerOptions.position(point.getGeometryObject());
        return this.mMap.addMarker(markerOptions);
    }

    private void setInlinePointStyle(MarkerOptions markerOptions, KmlStyle kmlStyle, String str) {
        MarkerOptions markerOptions2 = kmlStyle.getMarkerOptions();
        if (kmlStyle.isStyleSet("heading")) {
            markerOptions.rotation(markerOptions2.getRotation());
        }
        if (kmlStyle.isStyleSet("hotSpot")) {
            markerOptions.anchor(markerOptions2.getAnchorU(), markerOptions2.getAnchorV());
        }
        if (kmlStyle.isStyleSet("markerColor")) {
            markerOptions.icon(markerOptions2.getIcon());
        }
        if (kmlStyle.isStyleSet("iconUrl")) {
            addMarkerIcons(kmlStyle.getIconUrl(), markerOptions);
        } else if (str != null) {
            addMarkerIcons(str, markerOptions);
        }
    }

    protected Polyline addLineStringToMap(PolylineOptions polylineOptions, LineString lineString) {
        polylineOptions.addAll(lineString.getGeometryObject());
        Polyline addPolyline = this.mMap.addPolyline(polylineOptions);
        addPolyline.setClickable(true);
        return addPolyline;
    }

    private void setInlineLineStringStyle(PolylineOptions polylineOptions, KmlStyle kmlStyle) {
        PolylineOptions polylineOptions2 = kmlStyle.getPolylineOptions();
        if (kmlStyle.isStyleSet("outlineColor")) {
            polylineOptions.color(polylineOptions2.getColor());
        }
        if (kmlStyle.isStyleSet("width")) {
            polylineOptions.width(polylineOptions2.getWidth());
        }
        if (kmlStyle.isLineRandomColorMode()) {
            polylineOptions.color(KmlStyle.computeRandomColor(polylineOptions2.getColor()));
        }
    }

    protected Polygon addPolygonToMap(PolygonOptions polygonOptions, DataPolygon dataPolygon) {
        polygonOptions.addAll(dataPolygon.getOuterBoundaryCoordinates());
        for (List addHole : dataPolygon.getInnerBoundaryCoordinates()) {
            polygonOptions.addHole(addHole);
        }
        Polygon addPolygon = this.mMap.addPolygon(polygonOptions);
        addPolygon.setClickable(true);
        return addPolygon;
    }

    private void setInlinePolygonStyle(PolygonOptions polygonOptions, KmlStyle kmlStyle) {
        PolygonOptions polygonOptions2 = kmlStyle.getPolygonOptions();
        if (kmlStyle.hasFill() && kmlStyle.isStyleSet("fillColor")) {
            polygonOptions.fillColor(polygonOptions2.getFillColor());
        }
        if (kmlStyle.hasOutline()) {
            if (kmlStyle.isStyleSet("outlineColor")) {
                polygonOptions.strokeColor(polygonOptions2.getStrokeColor());
            }
            if (kmlStyle.isStyleSet("width")) {
                polygonOptions.strokeWidth(polygonOptions2.getStrokeWidth());
            }
        }
        if (kmlStyle.isPolyRandomColorMode()) {
            polygonOptions.fillColor(KmlStyle.computeRandomColor(polygonOptions2.getFillColor()));
        }
    }

    private ArrayList<Object> addGeometryCollectionToMap(GeoJsonFeature geoJsonFeature, List<Geometry> list) {
        ArrayList<Object> arrayList = new ArrayList();
        for (Geometry addGeoJsonFeatureToMap : list) {
            arrayList.add(addGeoJsonFeatureToMap(geoJsonFeature, addGeoJsonFeatureToMap));
        }
        return arrayList;
    }

    protected static boolean getPlacemarkVisibility(Feature feature) {
        String str = "visibility";
        return (feature.hasProperty(str) && Integer.parseInt(feature.getProperty(str)) == 0) ? false : true;
    }

    public void assignStyleMap(HashMap<String, String> hashMap, HashMap<String, KmlStyle> hashMap2) {
        for (String str : hashMap.keySet()) {
            String str2 = (String) hashMap.get(str);
            if (hashMap2.containsKey(str2)) {
                hashMap2.put(str, hashMap2.get(str2));
            }
        }
    }

    private ArrayList<Object> addMultiGeometryToMap(KmlPlacemark kmlPlacemark, KmlMultiGeometry kmlMultiGeometry, KmlStyle kmlStyle, KmlStyle kmlStyle2, boolean z) {
        ArrayList<Object> arrayList = new ArrayList();
        Iterator it = kmlMultiGeometry.getGeometryObject().iterator();
        while (it.hasNext()) {
            arrayList.add(addKmlPlacemarkToMap(kmlPlacemark, (Geometry) it.next(), kmlStyle, kmlStyle2, z));
        }
        return arrayList;
    }

    private ArrayList<Marker> addMultiPointToMap(GeoJsonPointStyle geoJsonPointStyle, GeoJsonMultiPoint geoJsonMultiPoint) {
        ArrayList<Marker> arrayList = new ArrayList();
        for (GeoJsonPoint addPointToMap : geoJsonMultiPoint.getPoints()) {
            arrayList.add(addPointToMap(geoJsonPointStyle.toMarkerOptions(), addPointToMap));
        }
        return arrayList;
    }

    private ArrayList<Polyline> addMultiLineStringToMap(GeoJsonLineStringStyle geoJsonLineStringStyle, GeoJsonMultiLineString geoJsonMultiLineString) {
        ArrayList<Polyline> arrayList = new ArrayList();
        for (GeoJsonLineString addLineStringToMap : geoJsonMultiLineString.getLineStrings()) {
            arrayList.add(addLineStringToMap(geoJsonLineStringStyle.toPolylineOptions(), addLineStringToMap));
        }
        return arrayList;
    }

    private ArrayList<Polygon> addMultiPolygonToMap(GeoJsonPolygonStyle geoJsonPolygonStyle, GeoJsonMultiPolygon geoJsonMultiPolygon) {
        ArrayList<Polygon> arrayList = new ArrayList();
        for (GeoJsonPolygon addPolygonToMap : geoJsonMultiPolygon.getPolygons()) {
            arrayList.add(addPolygonToMap(geoJsonPolygonStyle.toPolygonOptions(), addPolygonToMap));
        }
        return arrayList;
    }

    private void addMarkerIcons(String str, MarkerOptions markerOptions) {
        if (this.mImagesCache.get(str) != null) {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap((Bitmap) this.mImagesCache.get(str)));
        } else if (!this.mMarkerIconUrls.contains(str)) {
            this.mMarkerIconUrls.add(str);
        }
    }

    public GroundOverlay attachGroundOverlay(GroundOverlayOptions groundOverlayOptions) {
        return this.mMap.addGroundOverlay(groundOverlayOptions);
    }

    private void setMarkerInfoWindow(KmlStyle kmlStyle, Marker marker, KmlPlacemark kmlPlacemark) {
        String str = ConditionalUserProperty.NAME;
        boolean hasProperty = kmlPlacemark.hasProperty(str);
        String str2 = "description";
        boolean hasProperty2 = kmlPlacemark.hasProperty(str2);
        boolean hasBalloonStyle = kmlStyle.hasBalloonStyle();
        String str3 = "text";
        boolean containsKey = kmlStyle.getBalloonOptions().containsKey(str3);
        if (hasBalloonStyle && containsKey) {
            marker.setTitle((String) kmlStyle.getBalloonOptions().get(str3));
            createInfoWindow();
        } else if (hasBalloonStyle && hasProperty) {
            marker.setTitle(kmlPlacemark.getProperty(str));
            createInfoWindow();
        } else if (hasProperty && hasProperty2) {
            marker.setTitle(kmlPlacemark.getProperty(str));
            marker.setSnippet(kmlPlacemark.getProperty(str2));
            createInfoWindow();
        } else if (hasProperty2) {
            marker.setTitle(kmlPlacemark.getProperty(str2));
            createInfoWindow();
        } else if (hasProperty) {
            marker.setTitle(kmlPlacemark.getProperty(str));
            createInfoWindow();
        }
    }

    private void createInfoWindow() {
        this.mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
            public View getInfoWindow(Marker marker) {
                return null;
            }

            public View getInfoContents(Marker marker) {
                View inflate = LayoutInflater.from(Renderer.this.mContext).inflate(R.layout.amu_info_window, null);
                TextView textView = (TextView) inflate.findViewById(R.id.window);
                if (marker.getSnippet() != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(marker.getTitle());
                    stringBuilder.append("<br>");
                    stringBuilder.append(marker.getSnippet());
                    textView.setText(Html.fromHtml(stringBuilder.toString()));
                } else {
                    textView.setText(Html.fromHtml(marker.getTitle()));
                }
                return inflate;
            }
        });
    }
}
