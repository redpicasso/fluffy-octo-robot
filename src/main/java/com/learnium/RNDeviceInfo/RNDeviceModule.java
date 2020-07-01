package com.learnium.RNDeviceInfo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StatFs;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.WebSettings;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.os.EnvironmentCompat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.learnium.RNDeviceInfo.resolver.DeviceIdResolver;
import com.learnium.RNDeviceInfo.resolver.DeviceTypeResolver;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nonnull;

@ReactModule(name = "RNDeviceInfo")
public class RNDeviceModule extends ReactContextBaseJavaModule {
    private static String BATTERY_LEVEL = "batteryLevel";
    private static String BATTERY_STATE = "batteryState";
    private static String LOW_POWER_MODE = "lowPowerMode";
    public static final String NAME = "RNDeviceInfo";
    private final DeviceIdResolver deviceIdResolver;
    private final DeviceTypeResolver deviceTypeResolver;
    private RNInstallReferrerClient installReferrerClient;
    private double mLastBatteryLevel = -1.0d;
    private BroadcastReceiver receiver;
    private String sLastBatteryState = "";

    @Nonnull
    public String getName() {
        return NAME;
    }

    public RNDeviceModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.deviceTypeResolver = new DeviceTypeResolver(reactApplicationContext);
        this.deviceIdResolver = new DeviceIdResolver(reactApplicationContext);
        this.installReferrerClient = new RNInstallReferrerClient(reactApplicationContext.getBaseContext());
    }

    public void initialize() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        this.receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                WritableMap access$000 = RNDeviceModule.this.getPowerStateFromIntent(intent);
                if (access$000 != null) {
                    String string = access$000.getString(RNDeviceModule.BATTERY_STATE);
                    Double valueOf = Double.valueOf(access$000.getDouble(RNDeviceModule.BATTERY_LEVEL));
                    if (!RNDeviceModule.this.sLastBatteryState.equalsIgnoreCase(string)) {
                        RNDeviceModule rNDeviceModule = RNDeviceModule.this;
                        rNDeviceModule.sendEvent(rNDeviceModule.access$100(), "RNDeviceInfo_powerStateDidChange", string);
                        RNDeviceModule.this.sLastBatteryState = string;
                    }
                    if (RNDeviceModule.this.mLastBatteryLevel != valueOf.doubleValue()) {
                        RNDeviceModule rNDeviceModule2 = RNDeviceModule.this;
                        rNDeviceModule2.sendEvent(rNDeviceModule2.access$100(), "RNDeviceInfo_batteryLevelDidChange", valueOf);
                        if (valueOf.doubleValue() <= 0.15d) {
                            rNDeviceModule2 = RNDeviceModule.this;
                            rNDeviceModule2.sendEvent(rNDeviceModule2.access$100(), "RNDeviceInfo_batteryLevelIsLow", valueOf);
                        }
                        RNDeviceModule.this.mLastBatteryLevel = valueOf.doubleValue();
                    }
                }
            }
        };
        access$100().registerReceiver(this.receiver, intentFilter);
    }

    public void onCatalystInstanceDestroy() {
        access$100().unregisterReceiver(this.receiver);
    }

    @SuppressLint({"MissingPermission"})
    private WifiInfo getWifiInfo() {
        WifiManager wifiManager = (WifiManager) access$100().getApplicationContext().getSystemService("wifi");
        return wifiManager != null ? wifiManager.getConnectionInfo() : null;
    }

    public Map<String, Object> getConstants() {
        Object num;
        Object obj;
        Object obj2 = EnvironmentCompat.MEDIA_UNKNOWN;
        try {
            String str = getPackageInfo().versionName;
            num = Integer.toString(getPackageInfo().versionCode);
            obj2 = access$100().getApplicationInfo().loadLabel(access$100().getPackageManager()).toString();
            String str2 = str;
            obj = obj2;
            obj2 = str2;
        } catch (Exception unused) {
            obj = obj2;
            num = obj;
        }
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("uniqueId", getUniqueIdSync());
        hashMap.put("deviceId", Build.BOARD);
        hashMap.put("bundleId", access$100().getPackageName());
        hashMap.put("systemName", "Android");
        hashMap.put("systemVersion", VERSION.RELEASE);
        hashMap.put("appVersion", obj2);
        hashMap.put("buildNumber", num);
        hashMap.put("isTablet", Boolean.valueOf(this.deviceTypeResolver.isTablet()));
        hashMap.put("appName", obj);
        hashMap.put("brand", Build.BRAND);
        hashMap.put("model", Build.MODEL);
        hashMap.put("deviceType", this.deviceTypeResolver.getDeviceType().getValue());
        return hashMap;
    }

    @ReactMethod
    public void isEmulator(Promise promise) {
        promise.resolve(Boolean.valueOf(isEmulatorSync()));
    }

    @SuppressLint({"HardwareIds"})
    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isEmulatorSync() {
        String str = "generic";
        if (!(Build.FINGERPRINT.startsWith(str) || Build.FINGERPRINT.startsWith(EnvironmentCompat.MEDIA_UNKNOWN))) {
            CharSequence charSequence = "google_sdk";
            if (!(Build.MODEL.contains(charSequence) || Build.MODEL.toLowerCase(Locale.ROOT).contains("droid4x") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || Build.HARDWARE.contains("goldfish") || Build.HARDWARE.contains("ranchu") || Build.HARDWARE.contains("vbox86") || Build.PRODUCT.contains("sdk") || Build.PRODUCT.contains(charSequence) || Build.PRODUCT.contains("sdk_google") || Build.PRODUCT.contains("sdk_x86") || Build.PRODUCT.contains("vbox86p") || Build.PRODUCT.contains("emulator") || Build.PRODUCT.contains("simulator"))) {
                charSequence = "nox";
                if (!(Build.BOARD.toLowerCase(Locale.ROOT).contains(charSequence) || Build.BOOTLOADER.toLowerCase(Locale.ROOT).contains(charSequence) || Build.HARDWARE.toLowerCase(Locale.ROOT).contains(charSequence) || Build.PRODUCT.toLowerCase(Locale.ROOT).contains(charSequence) || Build.SERIAL.toLowerCase(Locale.ROOT).contains(charSequence) || (Build.BRAND.startsWith(str) && Build.DEVICE.startsWith(str)))) {
                    return false;
                }
            }
        }
        return true;
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public float getFontScaleSync() {
        return access$100().getResources().getConfiguration().fontScale;
    }

    @ReactMethod
    public void getFontScale(Promise promise) {
        promise.resolve(Float.valueOf(getFontScaleSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isPinOrFingerprintSetSync() {
        KeyguardManager keyguardManager = (KeyguardManager) access$100().getSystemService("keyguard");
        if (keyguardManager != null) {
            return keyguardManager.isKeyguardSecure();
        }
        System.err.println("Unable to determine keyguard status. KeyguardManager was null");
        return false;
    }

    @ReactMethod
    public void isPinOrFingerprintSet(Promise promise) {
        promise.resolve(Boolean.valueOf(isPinOrFingerprintSetSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getIpAddressSync() {
        try {
            return InetAddress.getByAddress(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(getWifiInfo().getIpAddress()).array()).getHostAddress();
        } catch (Exception unused) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    @ReactMethod
    public void getIpAddress(Promise promise) {
        promise.resolve(getIpAddressSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isCameraPresentSync() {
        boolean z = true;
        if (VERSION.SDK_INT >= 21) {
            try {
                if (((CameraManager) access$100().getSystemService("camera")).getCameraIdList().length <= 0) {
                    z = false;
                }
                return z;
            } catch (Exception unused) {
                return false;
            }
        }
        if (Camera.getNumberOfCameras() <= 0) {
            z = false;
        }
        return z;
    }

    @ReactMethod
    public void isCameraPresent(Promise promise) {
        promise.resolve(Boolean.valueOf(isCameraPresentSync()));
    }

    @SuppressLint({"HardwareIds"})
    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getMacAddressSync() {
        WifiInfo wifiInfo = getWifiInfo();
        String str = "";
        String macAddress = wifiInfo != null ? wifiInfo.getMacAddress() : str;
        if (access$100().checkCallingOrSelfPermission("android.permission.INTERNET") == 0) {
            try {
                for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                    if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                        byte[] hardwareAddress = networkInterface.getHardwareAddress();
                        if (hardwareAddress == null) {
                            macAddress = str;
                        } else {
                            StringBuilder stringBuilder = new StringBuilder();
                            int length = hardwareAddress.length;
                            for (int i = 0; i < length; i++) {
                                stringBuilder.append(String.format("%02X:", new Object[]{Byte.valueOf(hardwareAddress[i])}));
                            }
                            if (stringBuilder.length() > 0) {
                                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                            }
                            macAddress = stringBuilder.toString();
                        }
                    }
                }
            } catch (Exception unused) {
                return macAddress;
            }
        }
    }

    @ReactMethod
    public void getMacAddress(Promise promise) {
        promise.resolve(getMacAddressSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getCarrierSync() {
        TelephonyManager telephonyManager = (TelephonyManager) access$100().getSystemService("phone");
        if (telephonyManager != null) {
            return telephonyManager.getNetworkOperatorName();
        }
        System.err.println("Unable to get network operator name. TelephonyManager was null");
        return EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @ReactMethod
    public void getCarrier(Promise promise) {
        promise.resolve(getCarrierSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getTotalDiskCapacitySync() {
        try {
            StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
            return BigInteger.valueOf((long) statFs.getBlockCount()).multiply(BigInteger.valueOf((long) statFs.getBlockSize())).doubleValue();
        } catch (Exception unused) {
            return -1.0d;
        }
    }

    @ReactMethod
    public void getTotalDiskCapacity(Promise promise) {
        promise.resolve(Double.valueOf(getTotalDiskCapacitySync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getFreeDiskStorageSync() {
        try {
            long availableBlocks;
            long blockSize;
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (VERSION.SDK_INT < 18) {
                availableBlocks = (long) statFs.getAvailableBlocks();
                blockSize = (long) statFs.getBlockSize();
            } else {
                availableBlocks = statFs.getAvailableBlocksLong();
                blockSize = statFs.getBlockSizeLong();
            }
            return BigInteger.valueOf(availableBlocks).multiply(BigInteger.valueOf(blockSize)).doubleValue();
        } catch (Exception unused) {
            return -1.0d;
        }
    }

    @ReactMethod
    public void getFreeDiskStorage(Promise promise) {
        promise.resolve(Double.valueOf(getFreeDiskStorageSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isBatteryChargingSync() {
        Intent registerReceiver = access$100().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if ((registerReceiver != null ? registerReceiver.getIntExtra(NotificationCompat.CATEGORY_STATUS, -1) : 0) == 2) {
            return true;
        }
        return false;
    }

    @ReactMethod
    public void isBatteryCharging(Promise promise) {
        promise.resolve(Boolean.valueOf(isBatteryChargingSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public int getUsedMemorySync() {
        Runtime runtime = Runtime.getRuntime();
        return (int) (runtime.totalMemory() - runtime.freeMemory());
    }

    @ReactMethod
    public void getUsedMemory(Promise promise) {
        promise.resolve(Integer.valueOf(getUsedMemorySync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableMap getPowerStateSync() {
        return getPowerStateFromIntent(access$100().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")));
    }

    @ReactMethod
    public void getPowerState(Promise promise) {
        promise.resolve(getPowerStateSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getBatteryLevelSync() {
        WritableMap powerStateFromIntent = getPowerStateFromIntent(access$100().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")));
        if (powerStateFromIntent == null) {
            return 0.0d;
        }
        return powerStateFromIntent.getDouble(BATTERY_LEVEL);
    }

    @ReactMethod
    public void getBatteryLevel(Promise promise) {
        promise.resolve(Double.valueOf(getBatteryLevelSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isAirplaneModeSync() {
        String str = "airplane_mode_on";
        if (VERSION.SDK_INT < 17) {
            if (System.getInt(access$100().getContentResolver(), str, 0) != 0) {
                return true;
            }
        } else if (Global.getInt(access$100().getContentResolver(), str, 0) != 0) {
            return true;
        }
        return false;
    }

    @ReactMethod
    public void isAirplaneMode(Promise promise) {
        promise.resolve(Boolean.valueOf(isAirplaneModeSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean hasSystemFeatureSync(String str) {
        return (str == null || str.equals("")) ? false : access$100().getPackageManager().hasSystemFeature(str);
    }

    @ReactMethod
    public void hasSystemFeature(String str, Promise promise) {
        promise.resolve(Boolean.valueOf(hasSystemFeatureSync(str)));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableArray getSystemAvailableFeaturesSync() {
        FeatureInfo[] systemAvailableFeatures = access$100().getPackageManager().getSystemAvailableFeatures();
        WritableArray createArray = Arguments.createArray();
        for (FeatureInfo featureInfo : systemAvailableFeatures) {
            if (featureInfo.name != null) {
                createArray.pushString(featureInfo.name);
            }
        }
        return createArray;
    }

    @ReactMethod
    public void getSystemAvailableFeatures(Promise promise) {
        promise.resolve(getSystemAvailableFeaturesSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isLocationEnabledSync() {
        boolean isLocationEnabled;
        if (VERSION.SDK_INT >= 28) {
            try {
                isLocationEnabled = ((LocationManager) access$100().getSystemService(Param.LOCATION)).isLocationEnabled();
            } catch (Exception unused) {
                System.err.println("Unable to determine if location enabled. LocationManager was null");
                return false;
            }
        }
        isLocationEnabled = VERSION.SDK_INT >= 19 ? Secure.getInt(access$100().getContentResolver(), "location_mode", 0) != 0 : TextUtils.isEmpty(Secure.getString(access$100().getContentResolver(), "location_providers_allowed")) ^ true;
        return isLocationEnabled;
    }

    @ReactMethod
    public void isLocationEnabled(Promise promise) {
        promise.resolve(Boolean.valueOf(isLocationEnabledSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isHeadphonesConnectedSync() {
        AudioManager audioManager = (AudioManager) access$100().getSystemService("audio");
        return audioManager.isWiredHeadsetOn() || audioManager.isBluetoothA2dpOn();
    }

    @ReactMethod
    public void isHeadphonesConnected(Promise promise) {
        promise.resolve(Boolean.valueOf(isHeadphonesConnectedSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableMap getAvailableLocationProvidersSync() {
        LocationManager locationManager = (LocationManager) access$100().getSystemService(Param.LOCATION);
        WritableMap createMap = Arguments.createMap();
        try {
            for (String str : locationManager.getProviders(false)) {
                createMap.putBoolean(str, locationManager.isProviderEnabled(str));
            }
        } catch (Exception unused) {
            System.err.println("Unable to get location providers. LocationManager was null");
        }
        return createMap;
    }

    @ReactMethod
    public void getAvailableLocationProviders(Promise promise) {
        promise.resolve(getAvailableLocationProvidersSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInstallReferrerSync() {
        return access$100().getSharedPreferences("react-native-device-info", 0).getString("installReferrer", EnvironmentCompat.MEDIA_UNKNOWN);
    }

    @ReactMethod
    public void getInstallReferrer(Promise promise) {
        promise.resolve(getInstallReferrerSync());
    }

    private PackageInfo getPackageInfo() throws Exception {
        return access$100().getPackageManager().getPackageInfo(access$100().getPackageName(), 0);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInstallerPackageNameSync() {
        String installerPackageName = access$100().getPackageManager().getInstallerPackageName(access$100().getPackageName());
        return installerPackageName == null ? EnvironmentCompat.MEDIA_UNKNOWN : installerPackageName;
    }

    @ReactMethod
    public void getInstallerPackageName(Promise promise) {
        promise.resolve(getInstallerPackageNameSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getFirstInstallTimeSync() {
        try {
            return (double) getPackageInfo().firstInstallTime;
        } catch (Exception unused) {
            return -1.0d;
        }
    }

    @ReactMethod
    public void getFirstInstallTime(Promise promise) {
        promise.resolve(Double.valueOf(getFirstInstallTimeSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getLastUpdateTimeSync() {
        try {
            return (double) getPackageInfo().lastUpdateTime;
        } catch (Exception unused) {
            return -1.0d;
        }
    }

    @ReactMethod
    public void getLastUpdateTime(Promise promise) {
        promise.resolve(Double.valueOf(getLastUpdateTimeSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getDeviceNameSync() {
        try {
            String string = Secure.getString(access$100().getContentResolver(), "bluetooth_name");
            if (string != null) {
                return string;
            }
            if (VERSION.SDK_INT >= 25) {
                string = Global.getString(access$100().getContentResolver(), "device_name");
                if (string != null) {
                    return string;
                }
            }
        } catch (Exception unused) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    @ReactMethod
    public void getDeviceName(Promise promise) {
        promise.resolve(getDeviceNameSync());
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getSerialNumberSync() {
        try {
            if (VERSION.SDK_INT >= 26 && access$100().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
                return Build.getSerial();
            }
        } catch (Exception e) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getSerialNumber failed, it probably should not be used: ");
            stringBuilder.append(e.getMessage());
            printStream.println(stringBuilder.toString());
        }
        return EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @ReactMethod
    public void getSerialNumber(Promise promise) {
        promise.resolve(getSerialNumberSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getDeviceSync() {
        return Build.DEVICE;
    }

    @ReactMethod
    public void getDevice(Promise promise) {
        promise.resolve(getDeviceSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getBuildIdSync() {
        return Build.ID;
    }

    @ReactMethod
    public void getBuildId(Promise promise) {
        promise.resolve(getBuildIdSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public int getApiLevelSync() {
        return VERSION.SDK_INT;
    }

    @ReactMethod
    public void getApiLevel(Promise promise) {
        promise.resolve(Integer.valueOf(getApiLevelSync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getBootloaderSync() {
        return Build.BOOTLOADER;
    }

    @ReactMethod
    public void getBootloader(Promise promise) {
        promise.resolve(getBootloaderSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getDisplaySync() {
        return Build.DISPLAY;
    }

    @ReactMethod
    public void getDisplay(Promise promise) {
        promise.resolve(getDisplaySync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getFingerprintSync() {
        return Build.FINGERPRINT;
    }

    @ReactMethod
    public void getFingerprint(Promise promise) {
        promise.resolve(getFingerprintSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getHardwareSync() {
        return Build.HARDWARE;
    }

    @ReactMethod
    public void getHardware(Promise promise) {
        promise.resolve(getHardwareSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getHostSync() {
        return Build.HOST;
    }

    @ReactMethod
    public void getHost(Promise promise) {
        promise.resolve(getHostSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getProductSync() {
        return Build.PRODUCT;
    }

    @ReactMethod
    public void getProduct(Promise promise) {
        promise.resolve(getProductSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getTagsSync() {
        return Build.TAGS;
    }

    @ReactMethod
    public void getTags(Promise promise) {
        promise.resolve(getTagsSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getTypeSync() {
        return Build.TYPE;
    }

    @ReactMethod
    public void getType(Promise promise) {
        promise.resolve(getTypeSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getSystemManufacturerSync() {
        return Build.MANUFACTURER;
    }

    @ReactMethod
    public void getSystemManufacturer(Promise promise) {
        promise.resolve(getSystemManufacturerSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getCodenameSync() {
        return VERSION.CODENAME;
    }

    @ReactMethod
    public void getCodename(Promise promise) {
        promise.resolve(getCodenameSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getIncrementalSync() {
        return VERSION.INCREMENTAL;
    }

    @ReactMethod
    public void getIncremental(Promise promise) {
        promise.resolve(getIncrementalSync());
    }

    @SuppressLint({"HardwareIds"})
    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getUniqueIdSync() {
        return Secure.getString(access$100().getContentResolver(), "android_id");
    }

    @SuppressLint({"HardwareIds"})
    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getAndroidIdSync() {
        return getUniqueIdSync();
    }

    @ReactMethod
    public void getAndroidId(Promise promise) {
        promise.resolve(getAndroidIdSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getMaxMemorySync() {
        return (double) Runtime.getRuntime().maxMemory();
    }

    @ReactMethod
    public void getMaxMemory(Promise promise) {
        promise.resolve(Double.valueOf(getMaxMemorySync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getTotalMemorySync() {
        ActivityManager activityManager = (ActivityManager) access$100().getSystemService("activity");
        MemoryInfo memoryInfo = new MemoryInfo();
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
            return (double) memoryInfo.totalMem;
        }
        System.err.println("Unable to getMemoryInfo. ActivityManager was null");
        return -1.0d;
    }

    @ReactMethod
    public void getTotalMemory(Promise promise) {
        promise.resolve(Double.valueOf(getTotalMemorySync()));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInstanceIdSync() {
        return this.deviceIdResolver.getInstanceIdSync();
    }

    @ReactMethod
    public void getInstanceId(Promise promise) {
        promise.resolve(getInstanceIdSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getBaseOsSync() {
        return VERSION.SDK_INT >= 23 ? VERSION.BASE_OS : EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @ReactMethod
    public void getBaseOs(Promise promise) {
        promise.resolve(getBaseOsSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getPreviewSdkIntSync() {
        return VERSION.SDK_INT >= 23 ? Integer.toString(VERSION.PREVIEW_SDK_INT) : EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @ReactMethod
    public void getPreviewSdkInt(Promise promise) {
        promise.resolve(getPreviewSdkIntSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getSecurityPatchSync() {
        return VERSION.SDK_INT >= 23 ? VERSION.SECURITY_PATCH : EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @ReactMethod
    public void getSecurityPatch(Promise promise) {
        promise.resolve(getSecurityPatchSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getUserAgentSync() {
        String str = "http.agent";
        try {
            if (VERSION.SDK_INT >= 17) {
                return WebSettings.getDefaultUserAgent(access$100());
            }
            return System.getProperty(str);
        } catch (RuntimeException unused) {
            return System.getProperty(str);
        }
    }

    @ReactMethod
    public void getUserAgent(Promise promise) {
        promise.resolve(getUserAgentSync());
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getPhoneNumberSync() {
        if (access$100() != null && (access$100().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0 || ((VERSION.SDK_INT >= 23 && access$100().checkCallingOrSelfPermission("android.permission.READ_SMS") == 0) || (VERSION.SDK_INT >= 26 && access$100().checkCallingOrSelfPermission("android.permission.READ_PHONE_NUMBERS") == 0)))) {
            TelephonyManager telephonyManager = (TelephonyManager) access$100().getSystemService("phone");
            if (telephonyManager != null) {
                return telephonyManager.getLine1Number();
            }
            System.err.println("Unable to getPhoneNumber. TelephonyManager was null");
        }
        return EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @ReactMethod
    public void getPhoneNumber(Promise promise) {
        promise.resolve(getPhoneNumberSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableArray getSupportedAbisSync() {
        WritableArray writableNativeArray = new WritableNativeArray();
        if (VERSION.SDK_INT >= 21) {
            for (String pushString : Build.SUPPORTED_ABIS) {
                writableNativeArray.pushString(pushString);
            }
        } else {
            writableNativeArray.pushString(Build.CPU_ABI);
        }
        return writableNativeArray;
    }

    @ReactMethod
    public void getSupportedAbis(Promise promise) {
        promise.resolve(getSupportedAbisSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableArray getSupported32BitAbisSync() {
        WritableArray writableNativeArray = new WritableNativeArray();
        if (VERSION.SDK_INT >= 21) {
            for (String pushString : Build.SUPPORTED_32_BIT_ABIS) {
                writableNativeArray.pushString(pushString);
            }
        }
        return writableNativeArray;
    }

    @ReactMethod
    public void getSupported32BitAbis(Promise promise) {
        promise.resolve(getSupported32BitAbisSync());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableArray getSupported64BitAbisSync() {
        WritableArray writableNativeArray = new WritableNativeArray();
        if (VERSION.SDK_INT >= 21) {
            for (String pushString : Build.SUPPORTED_64_BIT_ABIS) {
                writableNativeArray.pushString(pushString);
            }
        }
        return writableNativeArray;
    }

    @ReactMethod
    public void getSupported64BitAbis(Promise promise) {
        promise.resolve(getSupported64BitAbisSync());
    }

    private WritableMap getPowerStateFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        int intExtra = intent.getIntExtra(Param.LEVEL, -1);
        int intExtra2 = intent.getIntExtra("scale", -1);
        int intExtra3 = intent.getIntExtra("plugged", -1);
        int intExtra4 = intent.getIntExtra(NotificationCompat.CATEGORY_STATUS, -1);
        float f = ((float) intExtra) / ((float) intExtra2);
        String str = intExtra3 == 0 ? "unplugged" : intExtra4 == 2 ? "charging" : intExtra4 == 5 ? "full" : EnvironmentCompat.MEDIA_UNKNOWN;
        PowerManager powerManager = (PowerManager) access$100().getSystemService("power");
        boolean z = false;
        if (VERSION.SDK_INT >= 21) {
            z = powerManager.isPowerSaveMode();
        }
        WritableMap createMap = Arguments.createMap();
        createMap.putString(BATTERY_STATE, str);
        createMap.putDouble(BATTERY_LEVEL, (double) f);
        createMap.putBoolean(LOW_POWER_MODE, z);
        return createMap;
    }

    private void sendEvent(ReactContext reactContext, String str, @Nullable Object obj) {
        ((RCTDeviceEventEmitter) reactContext.getJSModule(RCTDeviceEventEmitter.class)).emit(str, obj);
    }
}
