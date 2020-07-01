package io.invertase.firebase.admob;

import android.app.Activity;
import android.util.Log;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.ads.MobileAds;
import java.util.HashMap;
import java.util.Map;

public class RNFirebaseAdMob extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFirebaseAdMob";
    private HashMap<String, RNFirebaseAdmobInterstitial> interstitials = new HashMap();
    private HashMap<String, RNFirebaseAdMobRewardedVideo> rewardedVideos = new HashMap();

    public String getName() {
        return TAG;
    }

    RNFirebaseAdMob(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        Log.d(TAG, "New instance");
    }

    ReactApplicationContext getContext() {
        return access$700();
    }

    Activity getActivity() {
        return access$700();
    }

    @ReactMethod
    public void initialize(String str) {
        MobileAds.initialize(getContext(), str);
    }

    @ReactMethod
    public void openDebugMenu(String str) {
        MobileAds.openDebugMenu(getActivity(), str);
    }

    @ReactMethod
    public void interstitialLoadAd(String str, ReadableMap readableMap) {
        getOrCreateInterstitial(str).loadAd(RNFirebaseAdMobUtils.buildRequest(readableMap).build());
    }

    @ReactMethod
    public void interstitialShowAd(String str) {
        getOrCreateInterstitial(str).show();
    }

    @ReactMethod
    public void rewardedVideoLoadAd(String str, ReadableMap readableMap) {
        getOrCreateRewardedVideo(str).loadAd(RNFirebaseAdMobUtils.buildRequest(readableMap).build());
    }

    @ReactMethod
    public void rewardedVideoSetCustomData(String str, String str2) {
        getOrCreateRewardedVideo(str).setCustomData(str2);
    }

    @ReactMethod
    public void rewardedVideoShowAd(String str) {
        getOrCreateRewardedVideo(str).show();
    }

    private RNFirebaseAdmobInterstitial getOrCreateInterstitial(String str) {
        if (this.interstitials.containsKey(str)) {
            return (RNFirebaseAdmobInterstitial) this.interstitials.get(str);
        }
        RNFirebaseAdmobInterstitial rNFirebaseAdmobInterstitial = new RNFirebaseAdmobInterstitial(str, this);
        this.interstitials.put(str, rNFirebaseAdmobInterstitial);
        return rNFirebaseAdmobInterstitial;
    }

    private RNFirebaseAdMobRewardedVideo getOrCreateRewardedVideo(String str) {
        if (this.rewardedVideos.containsKey(str)) {
            return (RNFirebaseAdMobRewardedVideo) this.rewardedVideos.get(str);
        }
        RNFirebaseAdMobRewardedVideo rNFirebaseAdMobRewardedVideo = new RNFirebaseAdMobRewardedVideo(str, this);
        this.rewardedVideos.put(str, rNFirebaseAdMobRewardedVideo);
        return rNFirebaseAdMobRewardedVideo;
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("DEVICE_ID_EMULATOR", "B3EEABB8EE11C2BE770B684D95219ECB");
        return hashMap;
    }
}
