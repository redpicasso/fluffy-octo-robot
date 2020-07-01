package com.inprogress.reactnativeyoutube;

import android.app.Activity;
import android.content.Intent;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import java.util.ArrayList;
import java.util.List;

public class YouTubeStandaloneModule extends ReactContextBaseJavaModule {
    private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
    private static final String E_FAILED_TO_SHOW_PLAYER = "E_FAILED_TO_SHOW_PLAYER";
    private static final String E_PLAYER_ERROR = "E_PLAYER_ERROR";
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;
    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
            if (i == 1 && YouTubeStandaloneModule.this.mPickerPromise != null) {
                if (i2 != -1) {
                    YouTubeInitializationResult returnedInitializationResult = YouTubeStandalonePlayer.getReturnedInitializationResult(intent);
                    boolean isUserRecoverableError = returnedInitializationResult.isUserRecoverableError();
                    String str = YouTubeStandaloneModule.E_PLAYER_ERROR;
                    if (isUserRecoverableError) {
                        returnedInitializationResult.getErrorDialog(activity, i).show();
                        YouTubeStandaloneModule.this.mPickerPromise.reject(str);
                    } else {
                        YouTubeStandaloneModule.this.mPickerPromise.reject(str, String.format("There was an error initializing the YouTubePlayer (%1$s)", new Object[]{returnedInitializationResult.toString()}));
                    }
                } else {
                    YouTubeStandaloneModule.this.mPickerPromise.resolve(null);
                }
                YouTubeStandaloneModule.this.mPickerPromise = null;
            }
        }
    };
    private Promise mPickerPromise;
    private ReactApplicationContext mReactContext;

    public String getName() {
        return "YouTubeStandaloneModule";
    }

    public YouTubeStandaloneModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mReactContext = reactApplicationContext;
        this.mReactContext.addActivityEventListener(this.mActivityEventListener);
    }

    @ReactMethod
    public void playVideo(String str, String str2, boolean z, boolean z2, int i, Promise promise) {
        Activity currentActivity = access$700();
        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
        } else {
            play(YouTubeStandalonePlayer.createVideoIntent(currentActivity, str, str2, i, z, z2), promise);
        }
    }

    @ReactMethod
    public void playVideos(String str, ReadableArray readableArray, boolean z, boolean z2, int i, int i2, Promise promise) {
        Activity currentActivity = access$700();
        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
            return;
        }
        List arrayList = new ArrayList();
        for (int i3 = 0; i3 < readableArray.size(); i3++) {
            arrayList.add(readableArray.getString(i3));
        }
        play(YouTubeStandalonePlayer.createVideosIntent(currentActivity, str, arrayList, i, i2, z, z2), promise);
    }

    @ReactMethod
    public void playPlaylist(String str, String str2, boolean z, boolean z2, int i, int i2, Promise promise) {
        Activity currentActivity = access$700();
        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
        } else {
            play(YouTubeStandalonePlayer.createPlaylistIntent(currentActivity, str, str2, i, i2, z, z2), promise);
        }
    }

    private void play(Intent intent, Promise promise) {
        Activity currentActivity = access$700();
        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
            return;
        }
        this.mPickerPromise = promise;
        if (intent != null) {
            try {
                if (canResolveIntent(intent)) {
                    currentActivity.startActivityForResult(intent, 1);
                } else {
                    YouTubeInitializationResult.SERVICE_MISSING.getErrorDialog(currentActivity, 2).show();
                }
            } catch (Throwable e) {
                this.mPickerPromise.reject(E_FAILED_TO_SHOW_PLAYER, e);
                this.mPickerPromise = null;
            }
        }
    }

    private boolean canResolveIntent(Intent intent) {
        List queryIntentActivities = this.mReactContext.getPackageManager().queryIntentActivities(intent, 0);
        if (queryIntentActivities == null || queryIntentActivities.isEmpty()) {
            return false;
        }
        return true;
    }
}
