package com.reactnativecommunity.geolocation;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.PromiseImpl;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.common.SystemClock;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.facebook.react.modules.permissions.PermissionsModule;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.logging.type.LogSeverity;
import javax.annotation.Nullable;

@SuppressLint({"MissingPermission"})
@ReactModule(name = "RNCGeolocation")
public class GeolocationModule extends ReactContextBaseJavaModule {
    public static final String NAME = "RNCGeolocation";
    private static final float RCT_DEFAULT_LOCATION_ACCURACY = 100.0f;
    private final LocationListener mLocationListener = new LocationListener() {
        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onLocationChanged(Location location) {
            ((RCTDeviceEventEmitter) GeolocationModule.this.access$100().getJSModule(RCTDeviceEventEmitter.class)).emit("geolocationDidChange", GeolocationModule.locationToMap(location));
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            String str2 = "Provider ";
            GeolocationModule geolocationModule;
            int i2;
            StringBuilder stringBuilder;
            if (i == 0) {
                geolocationModule = GeolocationModule.this;
                i2 = PositionError.POSITION_UNAVAILABLE;
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(str);
                stringBuilder.append(" is out of service.");
                geolocationModule.emitError(i2, stringBuilder.toString());
            } else if (i == 1) {
                geolocationModule = GeolocationModule.this;
                i2 = PositionError.TIMEOUT;
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(str);
                stringBuilder.append(" is temporarily unavailable.");
                geolocationModule.emitError(i2, stringBuilder.toString());
            }
        }
    };
    @Nullable
    private String mWatchedProvider;

    private static class LocationOptions {
        private final float distanceFilter;
        private final boolean highAccuracy;
        private final double maximumAge;
        private final long timeout;

        private LocationOptions(long j, double d, boolean z, float f) {
            this.timeout = j;
            this.maximumAge = d;
            this.highAccuracy = z;
            this.distanceFilter = f;
        }

        private static LocationOptions fromReactMap(ReadableMap readableMap) {
            String str = "timeout";
            long j = readableMap.hasKey(str) ? (long) readableMap.getDouble(str) : Long.MAX_VALUE;
            str = "maximumAge";
            double d = readableMap.hasKey(str) ? readableMap.getDouble(str) : Double.POSITIVE_INFINITY;
            str = "enableHighAccuracy";
            boolean z = readableMap.hasKey(str) && readableMap.getBoolean(str);
            str = "distanceFilter";
            return new LocationOptions(j, d, z, readableMap.hasKey(str) ? (float) readableMap.getDouble(str) : GeolocationModule.RCT_DEFAULT_LOCATION_ACCURACY);
        }
    }

    private static class SingleUpdateRequest {
        private static final int TWO_MINUTES = 120000;
        private final Callback mError;
        private final Handler mHandler;
        private final LocationListener mLocationListener;
        private final LocationManager mLocationManager;
        private Location mOldLocation;
        private final String mProvider;
        private final Callback mSuccess;
        private final long mTimeout;
        private final Runnable mTimeoutRunnable;
        private boolean mTriggered;

        /* synthetic */ SingleUpdateRequest(LocationManager locationManager, String str, long j, Callback callback, Callback callback2, AnonymousClass1 anonymousClass1) {
            this(locationManager, str, j, callback, callback2);
        }

        private SingleUpdateRequest(LocationManager locationManager, String str, long j, Callback callback, Callback callback2) {
            this.mHandler = new Handler();
            this.mTimeoutRunnable = new Runnable() {
                public void run() {
                    synchronized (SingleUpdateRequest.this) {
                        if (!SingleUpdateRequest.this.mTriggered) {
                            SingleUpdateRequest.this.mError.invoke(PositionError.buildError(PositionError.TIMEOUT, "Location request timed out"));
                            SingleUpdateRequest.this.mLocationManager.removeUpdates(SingleUpdateRequest.this.mLocationListener);
                            FLog.i(ReactConstants.TAG, "LocationModule: Location request timed out");
                            SingleUpdateRequest.this.mTriggered = true;
                        }
                    }
                }
            };
            this.mLocationListener = new LocationListener() {
                public void onProviderDisabled(String str) {
                }

                public void onProviderEnabled(String str) {
                }

                public void onStatusChanged(String str, int i, Bundle bundle) {
                }

                public void onLocationChanged(Location location) {
                    synchronized (SingleUpdateRequest.this) {
                        if (!SingleUpdateRequest.this.mTriggered && SingleUpdateRequest.this.isBetterLocation(location, SingleUpdateRequest.this.mOldLocation)) {
                            SingleUpdateRequest.this.mSuccess.invoke(GeolocationModule.locationToMap(location));
                            SingleUpdateRequest.this.mHandler.removeCallbacks(SingleUpdateRequest.this.mTimeoutRunnable);
                            SingleUpdateRequest.this.mTriggered = true;
                            SingleUpdateRequest.this.mLocationManager.removeUpdates(SingleUpdateRequest.this.mLocationListener);
                        }
                        SingleUpdateRequest.this.mOldLocation = location;
                    }
                }
            };
            this.mLocationManager = locationManager;
            this.mProvider = str;
            this.mTimeout = j;
            this.mSuccess = callback;
            this.mError = callback2;
        }

        public void invoke(Location location) {
            this.mOldLocation = location;
            this.mLocationManager.requestLocationUpdates(this.mProvider, 100, 1.0f, this.mLocationListener);
            this.mHandler.postDelayed(this.mTimeoutRunnable, this.mTimeout);
        }

        private boolean isBetterLocation(Location location, Location location2) {
            if (location2 == null) {
                return true;
            }
            long time = location.getTime() - location2.getTime();
            Object obj = time > 120000 ? 1 : null;
            Object obj2 = time < -120000 ? 1 : null;
            Object obj3 = time > 0 ? 1 : null;
            if (obj != null) {
                return true;
            }
            if (obj2 != null) {
                return false;
            }
            int accuracy = (int) (location.getAccuracy() - location2.getAccuracy());
            obj = accuracy > 0 ? 1 : null;
            obj2 = accuracy < 0 ? 1 : null;
            Object obj4 = accuracy > LogSeverity.INFO_VALUE ? 1 : null;
            boolean isSameProvider = isSameProvider(location.getProvider(), location2.getProvider());
            if (obj2 != null) {
                return true;
            }
            if (obj3 != null && obj == null) {
                return true;
            }
            if (obj3 != null && obj4 == null && isSameProvider) {
                return true;
            }
            return false;
        }

        private boolean isSameProvider(String str, String str2) {
            if (str != null) {
                return str.equals(str2);
            }
            return str2 == null;
        }
    }

    public String getName() {
        return NAME;
    }

    public GeolocationModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void getCurrentPosition(final ReadableMap readableMap, final Callback callback, final Callback callback2) {
        if (VERSION.SDK_INT >= 23) {
            PermissionsModule permissionsModule = (PermissionsModule) access$100().getNativeModule(PermissionsModule.class);
            final Callback anonymousClass2 = new Callback() {
                public void invoke(Object... objArr) {
                    if (((String) objArr[0]) == "granted") {
                        GeolocationModule.this.getCurrentLocationData(readableMap, callback, callback2);
                        return;
                    }
                    callback2.invoke(PositionError.buildError(PositionError.PERMISSION_DENIED, "Location permission was not granted."));
                }
            };
            final Callback anonymousClass3 = new Callback() {
                public void invoke(Object... objArr) {
                    callback2.invoke(PositionError.buildError(PositionError.PERMISSION_DENIED, "Failed to request location permission."));
                }
            };
            final PermissionsModule permissionsModule2 = permissionsModule;
            final ReadableMap readableMap2 = readableMap;
            final Callback callback3 = callback;
            final Callback callback4 = callback2;
            String str = "android.permission.ACCESS_FINE_LOCATION";
            permissionsModule.checkPermission(str, new PromiseImpl(new Callback() {
                public void invoke(Object... objArr) {
                    if (((Boolean) objArr[0]).booleanValue()) {
                        GeolocationModule.this.getCurrentLocationData(readableMap2, callback3, callback4);
                        return;
                    }
                    permissionsModule2.requestPermission("android.permission.ACCESS_FINE_LOCATION", new PromiseImpl(anonymousClass2, anonymousClass3));
                }
            }, new Callback() {
                public void invoke(Object... objArr) {
                    callback2.invoke(PositionError.buildError(PositionError.PERMISSION_DENIED, "Failed to check location permission."));
                }
            }));
            return;
        }
        getCurrentLocationData(readableMap, callback, callback2);
    }

    public void getCurrentLocationData(ReadableMap readableMap, Callback callback, Callback callback2) {
        LocationOptions access$300 = LocationOptions.fromReactMap(readableMap);
        try {
            LocationManager locationManager = (LocationManager) access$100().getSystemService(Param.LOCATION);
            String validProvider = getValidProvider(locationManager, access$300.highAccuracy);
            if (validProvider == null) {
                callback2.invoke(PositionError.buildError(PositionError.POSITION_UNAVAILABLE, "No location provider available."));
                return;
            }
            Location lastKnownLocation = locationManager.getLastKnownLocation(validProvider);
            if (lastKnownLocation == null || ((double) (SystemClock.currentTimeMillis() - lastKnownLocation.getTime())) >= access$300.maximumAge) {
                new SingleUpdateRequest(locationManager, validProvider, access$300.timeout, callback, callback2, null).invoke(lastKnownLocation);
                return;
            }
            callback.invoke(locationToMap(lastKnownLocation));
        } catch (SecurityException e) {
            throwLocationPermissionMissing(e);
        }
    }

    @ReactMethod
    public void startObserving(ReadableMap readableMap) {
        if (!"gps".equals(this.mWatchedProvider)) {
            LocationOptions access$300 = LocationOptions.fromReactMap(readableMap);
            try {
                LocationManager locationManager = (LocationManager) access$100().getSystemService(Param.LOCATION);
                String validProvider = getValidProvider(locationManager, access$300.highAccuracy);
                if (validProvider == null) {
                    emitError(PositionError.POSITION_UNAVAILABLE, "No location provider available.");
                    return;
                }
                if (!validProvider.equals(this.mWatchedProvider)) {
                    locationManager.removeUpdates(this.mLocationListener);
                    locationManager.requestLocationUpdates(validProvider, 1000, access$300.distanceFilter, this.mLocationListener);
                }
                this.mWatchedProvider = validProvider;
            } catch (SecurityException e) {
                throwLocationPermissionMissing(e);
            }
        }
    }

    @ReactMethod
    public void stopObserving() {
        ((LocationManager) access$100().getSystemService(Param.LOCATION)).removeUpdates(this.mLocationListener);
        this.mWatchedProvider = null;
    }

    @Nullable
    private String getValidProvider(LocationManager locationManager, boolean z) {
        String str = "network";
        String str2 = "gps";
        String str3 = z ? str2 : str;
        if (!locationManager.isProviderEnabled(str3)) {
            str3 = str3.equals(str2) ? str : str2;
            if (!locationManager.isProviderEnabled(str3)) {
                return null;
            }
        }
        int checkSelfPermission = ContextCompat.checkSelfPermission(access$100(), "android.permission.ACCESS_FINE_LOCATION");
        if (!str3.equals(str2) || checkSelfPermission == 0) {
            return str3;
        }
        return null;
    }

    private static WritableMap locationToMap(Location location) {
        WritableMap createMap = Arguments.createMap();
        WritableMap createMap2 = Arguments.createMap();
        createMap2.putDouble("latitude", location.getLatitude());
        createMap2.putDouble("longitude", location.getLongitude());
        createMap2.putDouble("altitude", location.getAltitude());
        createMap2.putDouble("accuracy", (double) location.getAccuracy());
        createMap2.putDouble("heading", (double) location.getBearing());
        createMap2.putDouble("speed", (double) location.getSpeed());
        createMap.putMap("coords", createMap2);
        createMap.putDouble("timestamp", (double) location.getTime());
        if (VERSION.SDK_INT >= 18) {
            createMap.putBoolean("mocked", location.isFromMockProvider());
        }
        return createMap;
    }

    private void emitError(int i, String str) {
        ((RCTDeviceEventEmitter) access$100().getJSModule(RCTDeviceEventEmitter.class)).emit("geolocationError", PositionError.buildError(i, str));
    }

    private static void throwLocationPermissionMissing(SecurityException securityException) {
        throw new SecurityException("Looks like the app doesn't have the permission to access location.\nAdd the following line to your app's AndroidManifest.xml:\n<uses-permission android:name=\"android.permission.ACCESS_FINE_LOCATION\" />", securityException);
    }
}
