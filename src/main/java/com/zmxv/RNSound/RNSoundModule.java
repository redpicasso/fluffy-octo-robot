package com.zmxv.RNSound;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build.VERSION;
import android.util.Log;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RNSoundModule extends ReactContextBaseJavaModule implements OnAudioFocusChangeListener {
    static final Object NULL = null;
    String category;
    ReactApplicationContext context;
    Double focusedPlayerKey;
    Boolean mixWithOthers = Boolean.valueOf(true);
    Map<Double, MediaPlayer> playerPool = new HashMap();
    Boolean wasPlayingBeforeFocusChange = Boolean.valueOf(false);

    @ReactMethod
    public void enable(Boolean bool) {
    }

    public String getName() {
        return "RNSound";
    }

    public RNSoundModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.context = reactApplicationContext;
        this.category = null;
    }

    private void setOnPlay(boolean z, Double d) {
        ReactContext reactContext = this.context;
        WritableMap createMap = Arguments.createMap();
        createMap.putBoolean("isPlaying", z);
        createMap.putDouble("playerKey", d.doubleValue());
        ((RCTDeviceEventEmitter) reactContext.getJSModule(RCTDeviceEventEmitter.class)).emit("onPlayChange", createMap);
    }

    @ReactMethod
    public void prepare(String str, Double d, ReadableMap readableMap, final Callback callback) {
        String str2 = "loadSync";
        MediaPlayer createMediaPlayer = createMediaPlayer(str);
        int i = -1;
        if (createMediaPlayer == null) {
            WritableMap createMap = Arguments.createMap();
            createMap.putInt("code", -1);
            createMap.putString("message", "resource not found");
            callback.invoke(createMap, NULL);
            return;
        }
        this.playerPool.put(d, createMediaPlayer);
        String str3 = this.category;
        String str4 = "RNSoundModule";
        if (str3 != null) {
            Integer num = null;
            switch (str3.hashCode()) {
                case -1803461041:
                    if (str3.equals("System")) {
                        i = 2;
                        break;
                    }
                    break;
                case 2547280:
                    if (str3.equals("Ring")) {
                        i = 4;
                        break;
                    }
                    break;
                case 63343153:
                    if (str3.equals("Alarm")) {
                        i = 5;
                        break;
                    }
                    break;
                case 82833682:
                    if (str3.equals("Voice")) {
                        i = 3;
                        break;
                    }
                    break;
                case 772508280:
                    if (str3.equals("Ambient")) {
                        i = 1;
                        break;
                    }
                    break;
                case 1943812667:
                    if (str3.equals("Playback")) {
                        i = 0;
                        break;
                    }
                    break;
            }
            if (i == 0) {
                num = Integer.valueOf(3);
            } else if (i == 1) {
                num = Integer.valueOf(5);
            } else if (i == 2) {
                num = Integer.valueOf(1);
            } else if (i == 3) {
                num = Integer.valueOf(0);
            } else if (i == 4) {
                num = Integer.valueOf(2);
            } else if (i != 5) {
                Log.e(str4, String.format("Unrecognised category %s", new Object[]{this.category}));
            } else {
                num = Integer.valueOf(4);
            }
            if (num != null) {
                createMediaPlayer.setAudioStreamType(num.intValue());
            }
        }
        createMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
            boolean callbackWasCalled = false;

            public synchronized void onPrepared(MediaPlayer mediaPlayer) {
                if (!this.callbackWasCalled) {
                    this.callbackWasCalled = true;
                    Arguments.createMap().putDouble(ReactVideoView.EVENT_PROP_DURATION, ((double) mediaPlayer.getDuration()) * 0.001d);
                    try {
                        callback.invoke(RNSoundModule.NULL, r1);
                    } catch (Throwable e) {
                        Log.e("RNSoundModule", "Exception", e);
                    }
                } else {
                    return;
                }
                return;
            }
        });
        createMediaPlayer.setOnErrorListener(new OnErrorListener() {
            boolean callbackWasCalled = false;

            public synchronized boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                if (this.callbackWasCalled) {
                    return true;
                }
                this.callbackWasCalled = true;
                try {
                    WritableMap createMap = Arguments.createMap();
                    createMap.putInt(ReactVideoView.EVENT_PROP_WHAT, i);
                    createMap.putInt(ReactVideoView.EVENT_PROP_EXTRA, i2);
                    callback.invoke(createMap, RNSoundModule.NULL);
                } catch (Throwable e) {
                    Log.e("RNSoundModule", "Exception", e);
                }
                return true;
            }
        });
        try {
            if (readableMap.hasKey(str2) && readableMap.getBoolean(str2)) {
                createMediaPlayer.prepare();
            } else {
                createMediaPlayer.prepareAsync();
            }
        } catch (Throwable e) {
            Log.e(str4, "Exception", e);
        }
    }

    protected MediaPlayer createMediaPlayer(String str) {
        int identifier = this.context.getResources().getIdentifier(str, "raw", this.context.getPackageName());
        MediaPlayer mediaPlayer = new MediaPlayer();
        String str2 = "Exception";
        String str3 = "RNSoundModule";
        AssetFileDescriptor openRawResourceFd;
        if (identifier != 0) {
            try {
                openRawResourceFd = this.context.getResources().openRawResourceFd(identifier);
                mediaPlayer.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
                openRawResourceFd.close();
                return mediaPlayer;
            } catch (Throwable e) {
                Log.e(str3, str2, e);
                return null;
            }
        } else if (str.startsWith("http://") || str.startsWith("https://")) {
            mediaPlayer.setAudioStreamType(3);
            Log.i(str3, str);
            try {
                mediaPlayer.setDataSource(str);
                return mediaPlayer;
            } catch (Throwable e2) {
                Log.e(str3, str2, e2);
                return null;
            }
        } else {
            CharSequence charSequence = "asset:/";
            if (str.startsWith(charSequence)) {
                try {
                    openRawResourceFd = this.context.getAssets().openFd(str.replace(charSequence, ""));
                    mediaPlayer.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
                    openRawResourceFd.close();
                    return mediaPlayer;
                } catch (Throwable e22) {
                    Log.e(str3, str2, e22);
                    return null;
                }
            }
            if (new File(str).exists()) {
                mediaPlayer.setAudioStreamType(3);
                Log.i(str3, str);
                try {
                    mediaPlayer.setDataSource(str);
                    return mediaPlayer;
                } catch (Throwable e222) {
                    Log.e(str3, str2, e222);
                }
            }
            return null;
        }
    }

    @ReactMethod
    public void play(final Double d, final Callback callback) {
        MediaPlayer mediaPlayer = (MediaPlayer) this.playerPool.get(d);
        if (mediaPlayer == null) {
            setOnPlay(false, d);
            if (callback != null) {
                callback.invoke(Boolean.valueOf(false));
            }
        } else if (!mediaPlayer.isPlaying()) {
            if (!this.mixWithOthers.booleanValue()) {
                ((AudioManager) this.context.getSystemService("audio")).requestAudioFocus(this, 3, 1);
                this.focusedPlayerKey = d;
            }
            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                boolean callbackWasCalled = false;

                public synchronized void onCompletion(MediaPlayer mediaPlayer) {
                    if (!mediaPlayer.isLooping()) {
                        RNSoundModule.this.setOnPlay(false, d);
                        if (!this.callbackWasCalled) {
                            this.callbackWasCalled = true;
                            try {
                                callback.invoke(Boolean.valueOf(true));
                            } catch (Exception unused) {
                            }
                        }
                    }
                }
            });
            mediaPlayer.setOnErrorListener(new OnErrorListener() {
                boolean callbackWasCalled = false;

                public synchronized boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    RNSoundModule.this.setOnPlay(false, d);
                    if (!this.callbackWasCalled) {
                        this.callbackWasCalled = true;
                        try {
                            callback.invoke(Boolean.valueOf(true));
                        } catch (Exception unused) {
                            return true;
                        }
                    }
                    return true;
                }
            });
            mediaPlayer.start();
            setOnPlay(true, d);
        }
    }

    @ReactMethod
    public void pause(Double d, Callback callback) {
        MediaPlayer mediaPlayer = (MediaPlayer) this.playerPool.get(d);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        if (callback != null) {
            callback.invoke(new Object[0]);
        }
    }

    @ReactMethod
    public void stop(Double d, Callback callback) {
        MediaPlayer mediaPlayer = (MediaPlayer) this.playerPool.get(d);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
        if (!this.mixWithOthers.booleanValue() && d == this.focusedPlayerKey) {
            ((AudioManager) this.context.getSystemService("audio")).abandonAudioFocus(this);
        }
        callback.invoke(new Object[0]);
    }

    @ReactMethod
    public void reset(Double d) {
        MediaPlayer mediaPlayer = (MediaPlayer) this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
    }

    @ReactMethod
    public void release(Double d) {
        MediaPlayer mediaPlayer = (MediaPlayer) this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            this.playerPool.remove(d);
            if (!this.mixWithOthers.booleanValue() && d == this.focusedPlayerKey) {
                ((AudioManager) this.context.getSystemService("audio")).abandonAudioFocus(this);
            }
        }
    }

    public void onCatalystInstanceDestroy() {
        Iterator it = this.playerPool.entrySet().iterator();
        while (it.hasNext()) {
            MediaPlayer mediaPlayer = (MediaPlayer) ((Entry) it.next()).getValue();
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
            }
            it.remove();
        }
    }

    @ReactMethod
    public void setVolume(Double d, Float f, Float f2) {
        MediaPlayer mediaPlayer = (MediaPlayer) this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(f.floatValue(), f2.floatValue());
        }
    }

    @ReactMethod
    public void getSystemVolume(Callback callback) {
        try {
            AudioManager audioManager = (AudioManager) this.context.getSystemService("audio");
            callback.invoke(Float.valueOf(((float) audioManager.getStreamVolume(3)) / ((float) audioManager.getStreamMaxVolume(3))));
        } catch (Exception e) {
            WritableMap createMap = Arguments.createMap();
            createMap.putInt("code", -1);
            createMap.putString("message", e.getMessage());
            callback.invoke(createMap);
        }
    }

    @ReactMethod
    public void setSystemVolume(Float f) {
        AudioManager audioManager = (AudioManager) this.context.getSystemService("audio");
        audioManager.setStreamVolume(3, Math.round(((float) audioManager.getStreamMaxVolume(3)) * f.floatValue()), 0);
    }

    @ReactMethod
    public void setLooping(Double d, Boolean bool) {
        MediaPlayer mediaPlayer = (MediaPlayer) this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(bool.booleanValue());
        }
    }

    @ReactMethod
    public void setSpeed(Double d, Float f) {
        if (VERSION.SDK_INT < 23) {
            Log.w("RNSoundModule", "setSpeed ignored due to sdk limit");
            return;
        }
        MediaPlayer mediaPlayer = (MediaPlayer) this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(f.floatValue()));
        }
    }

    @ReactMethod
    public void setCurrentTime(Double d, Float f) {
        MediaPlayer mediaPlayer = (MediaPlayer) this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(Math.round(f.floatValue() * 1000.0f));
        }
    }

    @ReactMethod
    public void getCurrentTime(Double d, Callback callback) {
        if (((MediaPlayer) this.playerPool.get(d)) == null) {
            callback.invoke(Integer.valueOf(-1), Boolean.valueOf(false));
            return;
        }
        callback.invoke(Double.valueOf(((double) ((MediaPlayer) this.playerPool.get(d)).getCurrentPosition()) * 0.001d), Boolean.valueOf(((MediaPlayer) this.playerPool.get(d)).isPlaying()));
    }

    @ReactMethod
    public void setSpeakerphoneOn(Double d, Boolean bool) {
        MediaPlayer mediaPlayer = (MediaPlayer) this.playerPool.get(d);
        if (mediaPlayer != null) {
            mediaPlayer.setAudioStreamType(3);
            AudioManager audioManager = (AudioManager) this.context.getSystemService("audio");
            if (bool.booleanValue()) {
                audioManager.setMode(3);
            } else {
                audioManager.setMode(0);
            }
            audioManager.setSpeakerphoneOn(bool.booleanValue());
        }
    }

    @ReactMethod
    public void setCategory(String str, Boolean bool) {
        this.category = str;
        this.mixWithOthers = bool;
    }

    public void onAudioFocusChange(int i) {
        if (!this.mixWithOthers.booleanValue()) {
            MediaPlayer mediaPlayer = (MediaPlayer) this.playerPool.get(this.focusedPlayerKey);
            if (mediaPlayer == null) {
                return;
            }
            if (i <= 0) {
                this.wasPlayingBeforeFocusChange = Boolean.valueOf(mediaPlayer.isPlaying());
                if (this.wasPlayingBeforeFocusChange.booleanValue()) {
                    pause(this.focusedPlayerKey, null);
                }
            } else if (this.wasPlayingBeforeFocusChange.booleanValue()) {
                play(this.focusedPlayerKey, null);
                this.wasPlayingBeforeFocusChange = Boolean.valueOf(false);
            }
        }
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("IsAndroid", Boolean.valueOf(true));
        return hashMap;
    }
}
