package com.airbnb.android.react.maps;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Point;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(name = "AirMapModule")
public class AirMapModule extends ReactContextBaseJavaModule {
    public static final String NAME = "AirMapModule";
    private static final String SNAPSHOT_FORMAT_JPG = "jpg";
    private static final String SNAPSHOT_FORMAT_PNG = "png";
    private static final String SNAPSHOT_RESULT_BASE64 = "base64";
    private static final String SNAPSHOT_RESULT_FILE = "file";

    public String getName() {
        return NAME;
    }

    public AirMapModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("legalNotice", "This license information is displayed in Settings > Google > Open Source on any device running Google Play services.");
        return hashMap;
    }

    public Activity getActivity() {
        return access$700();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    @ReactMethod
    public void takeSnapshot(int i, ReadableMap readableMap, Promise promise) {
        ReadableMap readableMap2 = readableMap;
        final ReactApplicationContext reactApplicationContext = access$900();
        String str = "format";
        boolean hasKey = readableMap2.hasKey(str);
        String str2 = SNAPSHOT_FORMAT_PNG;
        final String string = hasKey ? readableMap2.getString(str) : str2;
        CompressFormat compressFormat = string.equals(str2) ? CompressFormat.PNG : string.equals(SNAPSHOT_FORMAT_JPG) ? CompressFormat.JPEG : null;
        final CompressFormat compressFormat2 = compressFormat;
        str = "quality";
        final double d = readableMap2.hasKey(str) ? readableMap2.getDouble(str) : 1.0d;
        DisplayMetrics displayMetrics = reactApplicationContext.getResources().getDisplayMetrics();
        String str3 = "width";
        int i2 = 0;
        Integer valueOf = Integer.valueOf(readableMap2.hasKey(str3) ? (int) (((double) displayMetrics.density) * readableMap2.getDouble(str3)) : 0);
        str3 = "height";
        if (readableMap2.hasKey(str3)) {
            i2 = (int) (((double) displayMetrics.density) * readableMap2.getDouble(str3));
        }
        Integer valueOf2 = Integer.valueOf(i2);
        str = "result";
        final int i3 = i;
        final Promise promise2 = promise;
        final Integer num = valueOf;
        valueOf = valueOf2;
        final String string2 = readableMap2.hasKey(str) ? readableMap2.getString(str) : "file";
        ((UIManagerModule) reactApplicationContext.getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                AirMapView airMapView = (AirMapView) nativeViewHierarchyManager.resolveView(i3);
                if (airMapView == null) {
                    promise2.reject("AirMapView not found");
                } else if (airMapView.map == null) {
                    promise2.reject("AirMapView.map is not valid");
                } else {
                    airMapView.map.snapshot(new SnapshotReadyCallback() {
                        public void onSnapshotReady(@Nullable Bitmap bitmap) {
                            if (bitmap == null) {
                                promise2.reject("Failed to generate bitmap, snapshot = null");
                                return;
                            }
                            if (!(num.intValue() == 0 || valueOf.intValue() == 0 || (num.intValue() == bitmap.getWidth() && valueOf.intValue() == bitmap.getHeight()))) {
                                bitmap = Bitmap.createScaledBitmap(bitmap, num.intValue(), valueOf.intValue(), true);
                            }
                            if (string2.equals("file")) {
                                try {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(".");
                                    stringBuilder.append(string);
                                    File createTempFile = File.createTempFile("AirMapSnapshot", stringBuilder.toString(), reactApplicationContext.getCacheDir());
                                    Closeable fileOutputStream = new FileOutputStream(createTempFile);
                                    bitmap.compress(compressFormat2, (int) (d * 100.0d), fileOutputStream);
                                    AirMapModule.closeQuietly(fileOutputStream);
                                    promise2.resolve(Uri.fromFile(createTempFile).toString());
                                } catch (Throwable e) {
                                    promise2.reject(e);
                                }
                            } else if (string2.equals("base64")) {
                                Object byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(compressFormat2, (int) (d * 100.0d), byteArrayOutputStream);
                                AirMapModule.closeQuietly(byteArrayOutputStream);
                                promise2.resolve(Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2));
                            }
                        }
                    });
                }
            }
        });
    }

    @ReactMethod
    public void getCamera(final int i, final Promise promise) {
        ((UIManagerModule) access$900().getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                AirMapView airMapView = (AirMapView) nativeViewHierarchyManager.resolveView(i);
                if (airMapView == null) {
                    promise.reject("AirMapView not found");
                } else if (airMapView.map == null) {
                    promise.reject("AirMapView.map is not valid");
                } else {
                    CameraPosition cameraPosition = airMapView.map.getCameraPosition();
                    WritableMap writableNativeMap = new WritableNativeMap();
                    writableNativeMap.putDouble("latitude", cameraPosition.target.latitude);
                    writableNativeMap.putDouble("longitude", cameraPosition.target.longitude);
                    WritableMap writableNativeMap2 = new WritableNativeMap();
                    writableNativeMap2.putMap("center", writableNativeMap);
                    writableNativeMap2.putDouble("heading", (double) cameraPosition.bearing);
                    writableNativeMap2.putDouble("zoom", (double) cameraPosition.zoom);
                    writableNativeMap2.putDouble("pitch", (double) cameraPosition.tilt);
                    promise.resolve(writableNativeMap2);
                }
            }
        });
    }

    @ReactMethod
    public void pointForCoordinate(int i, ReadableMap readableMap, Promise promise) {
        ReactApplicationContext reactApplicationContext = access$900();
        final double d = (double) reactApplicationContext.getResources().getDisplayMetrics().density;
        String str = "latitude";
        double d2 = 0.0d;
        double d3 = readableMap.hasKey(str) ? readableMap.getDouble(str) : 0.0d;
        String str2 = "longitude";
        if (readableMap.hasKey(str2)) {
            d2 = readableMap.getDouble(str2);
        }
        final LatLng latLng = new LatLng(d3, d2);
        final int i2 = i;
        final Promise promise2 = promise;
        ((UIManagerModule) reactApplicationContext.getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                AirMapView airMapView = (AirMapView) nativeViewHierarchyManager.resolveView(i2);
                if (airMapView == null) {
                    promise2.reject("AirMapView not found");
                } else if (airMapView.map == null) {
                    promise2.reject("AirMapView.map is not valid");
                } else {
                    Point toScreenLocation = airMapView.map.getProjection().toScreenLocation(latLng);
                    WritableMap writableNativeMap = new WritableNativeMap();
                    writableNativeMap.putDouble("x", ((double) toScreenLocation.x) / d);
                    writableNativeMap.putDouble("y", ((double) toScreenLocation.y) / d);
                    promise2.resolve(writableNativeMap);
                }
            }
        });
    }

    @ReactMethod
    public void coordinateForPoint(final int i, ReadableMap readableMap, final Promise promise) {
        ReactApplicationContext reactApplicationContext = access$900();
        double d = (double) reactApplicationContext.getResources().getDisplayMetrics().density;
        String str = "x";
        int i2 = 0;
        int i3 = readableMap.hasKey(str) ? (int) (readableMap.getDouble(str) * d) : 0;
        String str2 = "y";
        if (readableMap.hasKey(str2)) {
            i2 = (int) (readableMap.getDouble(str2) * d);
        }
        final Point point = new Point(i3, i2);
        ((UIManagerModule) reactApplicationContext.getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                AirMapView airMapView = (AirMapView) nativeViewHierarchyManager.resolveView(i);
                if (airMapView == null) {
                    promise.reject("AirMapView not found");
                } else if (airMapView.map == null) {
                    promise.reject("AirMapView.map is not valid");
                } else {
                    LatLng fromScreenLocation = airMapView.map.getProjection().fromScreenLocation(point);
                    WritableMap writableNativeMap = new WritableNativeMap();
                    writableNativeMap.putDouble("latitude", fromScreenLocation.latitude);
                    writableNativeMap.putDouble("longitude", fromScreenLocation.longitude);
                    promise.resolve(writableNativeMap);
                }
            }
        });
    }

    @ReactMethod
    public void getMapBoundaries(final int i, final Promise promise) {
        ((UIManagerModule) access$900().getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                AirMapView airMapView = (AirMapView) nativeViewHierarchyManager.resolveView(i);
                if (airMapView == null) {
                    promise.reject("AirMapView not found");
                } else if (airMapView.map == null) {
                    promise.reject("AirMapView.map is not valid");
                } else {
                    double[][] mapBoundaries = airMapView.getMapBoundaries();
                    WritableMap writableNativeMap = new WritableNativeMap();
                    WritableMap writableNativeMap2 = new WritableNativeMap();
                    WritableMap writableNativeMap3 = new WritableNativeMap();
                    String str = "longitude";
                    writableNativeMap2.putDouble(str, mapBoundaries[0][0]);
                    String str2 = "latitude";
                    writableNativeMap2.putDouble(str2, mapBoundaries[0][1]);
                    writableNativeMap3.putDouble(str, mapBoundaries[1][0]);
                    writableNativeMap3.putDouble(str2, mapBoundaries[1][1]);
                    writableNativeMap.putMap("northEast", writableNativeMap2);
                    writableNativeMap.putMap("southWest", writableNativeMap3);
                    promise.resolve(writableNativeMap);
                }
            }
        });
    }
}
