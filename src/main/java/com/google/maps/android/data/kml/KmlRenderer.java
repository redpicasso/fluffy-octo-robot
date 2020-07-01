package com.google.maps.android.data.kml;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.Renderer;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class KmlRenderer extends Renderer {
    private static final String LOG_TAG = "KmlRenderer";
    private ArrayList<KmlContainer> mContainers;
    private boolean mGroundOverlayImagesDownloaded = false;
    private final ArrayList<String> mGroundOverlayUrls = new ArrayList();
    private HashMap<KmlGroundOverlay, GroundOverlay> mGroundOverlays;
    private boolean mMarkerIconsDownloaded = false;

    private class GroundOverlayImageDownload extends AsyncTask<String, Void, Bitmap> {
        private final String mGroundOverlayUrl;

        public GroundOverlayImageDownload(String str) {
            this.mGroundOverlayUrl = str;
        }

        protected Bitmap doInBackground(String... strArr) {
            try {
                return BitmapFactory.decodeStream((InputStream) new URL(this.mGroundOverlayUrl).getContent());
            } catch (MalformedURLException unused) {
                return BitmapFactory.decodeFile(this.mGroundOverlayUrl);
            } catch (Throwable e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Image [");
                stringBuilder.append(this.mGroundOverlayUrl);
                stringBuilder.append("] download issue");
                Log.e(KmlRenderer.LOG_TAG, stringBuilder.toString(), e);
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Image at this URL could not be found ");
                stringBuilder.append(this.mGroundOverlayUrl);
                Log.e(KmlRenderer.LOG_TAG, stringBuilder.toString());
                return;
            }
            KmlRenderer.this.putImagesCache(this.mGroundOverlayUrl, bitmap);
            if (KmlRenderer.this.isLayerOnMap()) {
                KmlRenderer kmlRenderer = KmlRenderer.this;
                kmlRenderer.addGroundOverlayToMap(this.mGroundOverlayUrl, kmlRenderer.mGroundOverlays, true);
                kmlRenderer = KmlRenderer.this;
                kmlRenderer.addGroundOverlayInContainerGroups(this.mGroundOverlayUrl, kmlRenderer.mContainers, true);
            }
        }
    }

    private class MarkerIconImageDownload extends AsyncTask<String, Void, Bitmap> {
        private final String mIconUrl;

        public MarkerIconImageDownload(String str) {
            this.mIconUrl = str;
        }

        protected Bitmap doInBackground(String... strArr) {
            try {
                return BitmapFactory.decodeStream((InputStream) new URL(this.mIconUrl).getContent());
            } catch (MalformedURLException unused) {
                return BitmapFactory.decodeFile(this.mIconUrl);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Image at this URL could not be found ");
                stringBuilder.append(this.mIconUrl);
                Log.e(KmlRenderer.LOG_TAG, stringBuilder.toString());
                return;
            }
            KmlRenderer.this.putImagesCache(this.mIconUrl, bitmap);
            if (KmlRenderer.this.isLayerOnMap()) {
                KmlRenderer kmlRenderer = KmlRenderer.this;
                kmlRenderer.addIconToMarkers(this.mIconUrl, kmlRenderer.access$000());
                kmlRenderer = KmlRenderer.this;
                kmlRenderer.addContainerGroupIconsToMarkers(this.mIconUrl, kmlRenderer.mContainers);
            }
        }
    }

    KmlRenderer(GoogleMap googleMap, Context context) {
        super(googleMap, context);
    }

    private static BitmapDescriptor scaleIcon(Bitmap bitmap, Double d) {
        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, (int) (((double) bitmap.getWidth()) * d.doubleValue()), (int) (((double) bitmap.getHeight()) * d.doubleValue()), false));
    }

    private void removePlacemarks(HashMap<? extends Feature, Object> hashMap) {
        Renderer.removeFeatures(hashMap);
    }

    static boolean getContainerVisibility(KmlContainer kmlContainer, boolean z) {
        String str = "visibility";
        Object obj = (kmlContainer.hasProperty(str) && Integer.parseInt(kmlContainer.getProperty(str)) == 0) ? null : 1;
        if (!z || obj == null) {
            return false;
        }
        return true;
    }

    private void removeGroundOverlays(HashMap<KmlGroundOverlay, GroundOverlay> hashMap) {
        for (GroundOverlay remove : hashMap.values()) {
            remove.remove();
        }
    }

    private void removeContainers(Iterable<KmlContainer> iterable) {
        for (KmlContainer kmlContainer : iterable) {
            removePlacemarks(kmlContainer.getPlacemarksHashMap());
            removeGroundOverlays(kmlContainer.getGroundOverlayHashMap());
            removeContainers(kmlContainer.getContainers());
        }
    }

    public void addLayerToMap() {
        setLayerVisibility(true);
        this.mGroundOverlays = getGroundOverlayMap();
        this.mContainers = getContainerList();
        putStyles();
        assignStyleMap(getStyleMaps(), getStylesRenderer());
        addGroundOverlays(this.mGroundOverlays, this.mContainers);
        addContainerGroupToMap(this.mContainers, true);
        addPlacemarksToMap(access$000());
        if (!this.mGroundOverlayImagesDownloaded) {
            downloadGroundOverlays();
        }
        if (!this.mMarkerIconsDownloaded) {
            downloadMarkerIcons();
        }
    }

    void storeKmlData(HashMap<String, KmlStyle> hashMap, HashMap<String, String> hashMap2, HashMap<KmlPlacemark, Object> hashMap3, ArrayList<KmlContainer> arrayList, HashMap<KmlGroundOverlay, GroundOverlay> hashMap4) {
        storeData(hashMap, hashMap2, hashMap3, arrayList, hashMap4);
    }

    public void setMap(GoogleMap googleMap) {
        removeLayerFromMap();
        super.setMap(googleMap);
        addLayerToMap();
    }

    boolean hasKmlPlacemarks() {
        return hasFeatures();
    }

    Iterable<? extends Feature> getKmlPlacemarks() {
        return getFeatures();
    }

    public boolean hasNestedContainers() {
        return this.mContainers.size() > 0;
    }

    public Iterable<KmlContainer> getNestedContainers() {
        return this.mContainers;
    }

    public Iterable<KmlGroundOverlay> getGroundOverlays() {
        return this.mGroundOverlays.keySet();
    }

    public void removeLayerFromMap() {
        removePlacemarks(access$000());
        removeGroundOverlays(this.mGroundOverlays);
        if (hasNestedContainers()) {
            removeContainers(getNestedContainers());
        }
        setLayerVisibility(false);
        clearStylesRenderer();
    }

    private void addPlacemarksToMap(HashMap<? extends Feature, Object> hashMap) {
        for (Feature addFeature : hashMap.keySet()) {
            addFeature(addFeature);
        }
    }

    private void addContainerGroupToMap(Iterable<KmlContainer> iterable, boolean z) {
        for (KmlContainer kmlContainer : iterable) {
            boolean containerVisibility = getContainerVisibility(kmlContainer, z);
            if (kmlContainer.getStyles() != null) {
                putStyles(kmlContainer.getStyles());
            }
            if (kmlContainer.getStyleMap() != null) {
                super.assignStyleMap(kmlContainer.getStyleMap(), getStylesRenderer());
            }
            addContainerObjectToMap(kmlContainer, containerVisibility);
            if (kmlContainer.hasContainers()) {
                addContainerGroupToMap(kmlContainer.getContainers(), containerVisibility);
            }
        }
    }

    private void addContainerObjectToMap(KmlContainer kmlContainer, boolean z) {
        for (Feature feature : kmlContainer.getPlacemarks()) {
            boolean z2 = z && Renderer.getPlacemarkVisibility(feature);
            if (feature.getGeometry() != null) {
                String id = feature.getId();
                Geometry geometry = feature.getGeometry();
                KmlStyle placemarkStyle = getPlacemarkStyle(id);
                KmlPlacemark kmlPlacemark = (KmlPlacemark) feature;
                Object addKmlPlacemarkToMap = addKmlPlacemarkToMap(kmlPlacemark, geometry, placemarkStyle, kmlPlacemark.getInlineStyle(), z2);
                kmlContainer.setPlacemark(kmlPlacemark, addKmlPlacemarkToMap);
                putContainerFeature(addKmlPlacemarkToMap, feature);
            }
        }
    }

    private void downloadMarkerIcons() {
        this.mMarkerIconsDownloaded = true;
        Iterator it = getMarkerIconUrls().iterator();
        while (it.hasNext()) {
            new MarkerIconImageDownload((String) it.next()).execute(new String[0]);
            it.remove();
        }
    }

    private void addIconToMarkers(String str, HashMap<KmlPlacemark, Object> hashMap) {
        for (Feature feature : hashMap.keySet()) {
            KmlStyle kmlStyle = (KmlStyle) getStylesRenderer().get(feature.getId());
            KmlPlacemark kmlPlacemark = (KmlPlacemark) feature;
            KmlStyle inlineStyle = kmlPlacemark.getInlineStyle();
            if ("Point".equals(feature.getGeometry().getGeometryType())) {
                Object obj = 1;
                Object obj2 = (inlineStyle == null || !str.equals(inlineStyle.getIconUrl())) ? null : 1;
                if (kmlStyle == null || !str.equals(kmlStyle.getIconUrl())) {
                    obj = null;
                }
                if (obj2 != null) {
                    scaleBitmap(inlineStyle, hashMap, kmlPlacemark);
                } else if (obj != null) {
                    scaleBitmap(kmlStyle, hashMap, kmlPlacemark);
                }
            }
        }
    }

    private void scaleBitmap(KmlStyle kmlStyle, HashMap<KmlPlacemark, Object> hashMap, KmlPlacemark kmlPlacemark) {
        double iconScale = kmlStyle.getIconScale();
        ((Marker) hashMap.get(kmlPlacemark)).setIcon(scaleIcon((Bitmap) getImagesCache().get(kmlStyle.getIconUrl()), Double.valueOf(iconScale)));
    }

    private void addContainerGroupIconsToMarkers(String str, Iterable<KmlContainer> iterable) {
        for (KmlContainer kmlContainer : iterable) {
            addIconToMarkers(str, kmlContainer.getPlacemarksHashMap());
            if (kmlContainer.hasContainers()) {
                addContainerGroupIconsToMarkers(str, kmlContainer.getContainers());
            }
        }
    }

    private void addGroundOverlays(HashMap<KmlGroundOverlay, GroundOverlay> hashMap, Iterable<KmlContainer> iterable) {
        addGroundOverlays(hashMap);
        for (KmlContainer kmlContainer : iterable) {
            addGroundOverlays(kmlContainer.getGroundOverlayHashMap(), kmlContainer.getContainers());
        }
    }

    private void addGroundOverlays(HashMap<KmlGroundOverlay, GroundOverlay> hashMap) {
        for (KmlGroundOverlay kmlGroundOverlay : hashMap.keySet()) {
            String imageUrl = kmlGroundOverlay.getImageUrl();
            if (!(imageUrl == null || kmlGroundOverlay.getLatLngBox() == null)) {
                if (getImagesCache().get(imageUrl) != null) {
                    addGroundOverlayToMap(imageUrl, this.mGroundOverlays, true);
                } else if (!this.mGroundOverlayUrls.contains(imageUrl)) {
                    this.mGroundOverlayUrls.add(imageUrl);
                }
            }
        }
    }

    private void downloadGroundOverlays() {
        this.mGroundOverlayImagesDownloaded = true;
        Iterator it = this.mGroundOverlayUrls.iterator();
        while (it.hasNext()) {
            new GroundOverlayImageDownload((String) it.next()).execute(new String[0]);
            it.remove();
        }
    }

    private void addGroundOverlayToMap(String str, HashMap<KmlGroundOverlay, GroundOverlay> hashMap, boolean z) {
        BitmapDescriptor fromBitmap = BitmapDescriptorFactory.fromBitmap((Bitmap) getImagesCache().get(str));
        for (KmlGroundOverlay kmlGroundOverlay : hashMap.keySet()) {
            if (kmlGroundOverlay.getImageUrl().equals(str)) {
                GroundOverlay attachGroundOverlay = attachGroundOverlay(kmlGroundOverlay.getGroundOverlayOptions().image(fromBitmap));
                if (!z) {
                    attachGroundOverlay.setVisible(false);
                }
                hashMap.put(kmlGroundOverlay, attachGroundOverlay);
            }
        }
    }

    private void addGroundOverlayInContainerGroups(String str, Iterable<KmlContainer> iterable, boolean z) {
        for (KmlContainer kmlContainer : iterable) {
            boolean containerVisibility = getContainerVisibility(kmlContainer, z);
            addGroundOverlayToMap(str, kmlContainer.getGroundOverlayHashMap(), containerVisibility);
            if (kmlContainer.hasContainers()) {
                addGroundOverlayInContainerGroups(str, kmlContainer.getContainers(), containerVisibility);
            }
        }
    }
}
