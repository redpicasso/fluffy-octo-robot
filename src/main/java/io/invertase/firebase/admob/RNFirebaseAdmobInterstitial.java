package io.invertase.firebase.admob;

import android.app.Activity;
import android.content.Context;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import io.invertase.firebase.Utils;
import javax.annotation.Nullable;

class RNFirebaseAdmobInterstitial {
    private RNFirebaseAdMob adMob;
    private String adUnit;
    private InterstitialAd interstitialAd;

    RNFirebaseAdmobInterstitial(String str, RNFirebaseAdMob rNFirebaseAdMob) {
        this.adUnit = str;
        this.adMob = rNFirebaseAdMob;
        Context activity = this.adMob.getActivity();
        if (activity == null) {
            this.interstitialAd = new InterstitialAd(this.adMob.getContext());
        } else {
            this.interstitialAd = new InterstitialAd(activity);
        }
        this.interstitialAd.setAdUnitId(this.adUnit);
        this.interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                RNFirebaseAdmobInterstitial.this.sendEvent("onAdLoaded", null);
            }

            public void onAdOpened() {
                RNFirebaseAdmobInterstitial.this.sendEvent("onAdOpened", null);
            }

            public void onAdLeftApplication() {
                RNFirebaseAdmobInterstitial.this.sendEvent("onAdLeftApplication", null);
            }

            public void onAdClosed() {
                RNFirebaseAdmobInterstitial.this.sendEvent("onAdClosed", null);
            }

            public void onAdFailedToLoad(int i) {
                RNFirebaseAdmobInterstitial.this.sendEvent("onAdFailedToLoad", RNFirebaseAdMobUtils.errorCodeToMap(i));
            }
        });
    }

    void loadAd(final AdRequest adRequest) {
        Activity activity = this.adMob.getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    RNFirebaseAdmobInterstitial.this.interstitialAd.loadAd(adRequest);
                }
            });
        }
    }

    void show() {
        Activity activity = this.adMob.getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (RNFirebaseAdmobInterstitial.this.interstitialAd.isLoaded()) {
                        RNFirebaseAdmobInterstitial.this.interstitialAd.show();
                    }
                }
            });
        }
    }

    private void sendEvent(String str, @Nullable WritableMap writableMap) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("type", str);
        createMap.putString("adUnit", this.adUnit);
        if (writableMap != null) {
            createMap.putMap("payload", writableMap);
        }
        Utils.sendEvent(this.adMob.getContext(), "interstitial_event", createMap);
    }
}
