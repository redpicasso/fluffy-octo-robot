package com.rnim.rn.audio;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

class AudioRecorderManager extends ReactContextBaseJavaModule {
    private static final String CachesDirectoryPath = "CachesDirectoryPath";
    private static final String DocumentDirectoryPath = "DocumentDirectoryPath";
    private static final String DownloadsDirectoryPath = "DownloadsDirectoryPath";
    private static final String LibraryDirectoryPath = "LibraryDirectoryPath";
    private static final String MainBundlePath = "MainBundlePath";
    private static final String MusicDirectoryPath = "MusicDirectoryPath";
    private static final String PicturesDirectoryPath = "PicturesDirectoryPath";
    private static final String TAG = "ReactNativeAudio";
    private Context context;
    private String currentOutputFile;
    private boolean includeBase64 = false;
    private boolean isPauseResumeCapable = false;
    private boolean isPaused = false;
    private boolean isRecording = false;
    private Method pauseMethod = null;
    private MediaRecorder recorder;
    private Method resumeMethod = null;
    private StopWatch stopWatch;
    private Timer timer;

    public String getName() {
        return "AudioRecorderManager";
    }

    public AudioRecorderManager(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.context = reactApplicationContext;
        this.stopWatch = new StopWatch();
        this.isPauseResumeCapable = VERSION.SDK_INT > 23;
        if (this.isPauseResumeCapable) {
            try {
                this.pauseMethod = MediaRecorder.class.getMethod("pause", new Class[0]);
                this.resumeMethod = MediaRecorder.class.getMethod("resume", new Class[0]);
            } catch (NoSuchMethodException unused) {
                Log.d("ERROR", "Failed to get a reference to pause and/or resume method");
            }
        }
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put(DocumentDirectoryPath, access$100().getFilesDir().getAbsolutePath());
        hashMap.put(PicturesDirectoryPath, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        String str = "";
        hashMap.put(MainBundlePath, str);
        hashMap.put(CachesDirectoryPath, access$100().getCacheDir().getAbsolutePath());
        hashMap.put(LibraryDirectoryPath, str);
        hashMap.put(MusicDirectoryPath, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());
        hashMap.put(DownloadsDirectoryPath, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        return hashMap;
    }

    @ReactMethod
    public void checkAuthorizationStatus(Promise promise) {
        promise.resolve(Boolean.valueOf(ContextCompat.checkSelfPermission(access$700(), "android.permission.RECORD_AUDIO") == 0));
    }

    @ReactMethod
    public void prepareRecordingAtPath(String str, ReadableMap readableMap, Promise promise) {
        if (this.isRecording) {
            logAndRejectPromise(promise, "INVALID_STATE", "Please call stopRecording before starting recording");
        }
        this.recorder = new MediaRecorder();
        try {
            this.recorder.setAudioSource(1);
            this.recorder.setOutputFormat(getOutputFormatFromString(readableMap.getString("OutputFormat")));
            this.recorder.setAudioEncoder(getAudioEncoderFromString(readableMap.getString("AudioEncoding")));
            this.recorder.setAudioSamplingRate(readableMap.getInt("SampleRate"));
            this.recorder.setAudioChannels(readableMap.getInt("Channels"));
            this.recorder.setAudioEncodingBitRate(readableMap.getInt("AudioEncodingBitRate"));
            this.recorder.setOutputFile(str);
            this.includeBase64 = readableMap.getBoolean("IncludeBase64");
            this.currentOutputFile = str;
            try {
                this.recorder.prepare();
                promise.resolve(this.currentOutputFile);
            } catch (Exception e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("COULDNT_PREPARE_RECORDING_AT_PATH ");
                stringBuilder.append(str);
                logAndRejectPromise(promise, stringBuilder.toString(), e.getMessage());
            }
        } catch (Exception e2) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Make sure you've added RECORD_AUDIO permission to your AndroidManifest.xml file ");
            stringBuilder2.append(e2.getMessage());
            logAndRejectPromise(promise, "COULDNT_CONFIGURE_MEDIA_RECORDER", stringBuilder2.toString());
        }
    }

    private int getAudioEncoderFromString(java.lang.String r8) {
        /*
        r7 = this;
        r0 = r8.hashCode();
        r1 = 0;
        r2 = 5;
        r3 = 4;
        r4 = 3;
        r5 = 2;
        r6 = 1;
        switch(r0) {
            case -1413784883: goto L_0x0040;
            case -1413784604: goto L_0x0036;
            case -1235069279: goto L_0x002c;
            case -1221333503: goto L_0x0022;
            case -810722925: goto L_0x0018;
            case 96323: goto L_0x000e;
            default: goto L_0x000d;
        };
    L_0x000d:
        goto L_0x004a;
    L_0x000e:
        r0 = "aac";
        r0 = r8.equals(r0);
        if (r0 == 0) goto L_0x004a;
    L_0x0016:
        r0 = 0;
        goto L_0x004b;
    L_0x0018:
        r0 = "vorbis";
        r0 = r8.equals(r0);
        if (r0 == 0) goto L_0x004a;
    L_0x0020:
        r0 = 5;
        goto L_0x004b;
    L_0x0022:
        r0 = "he_aac";
        r0 = r8.equals(r0);
        if (r0 == 0) goto L_0x004a;
    L_0x002a:
        r0 = 4;
        goto L_0x004b;
    L_0x002c:
        r0 = "aac_eld";
        r0 = r8.equals(r0);
        if (r0 == 0) goto L_0x004a;
    L_0x0034:
        r0 = 1;
        goto L_0x004b;
    L_0x0036:
        r0 = "amr_wb";
        r0 = r8.equals(r0);
        if (r0 == 0) goto L_0x004a;
    L_0x003e:
        r0 = 3;
        goto L_0x004b;
    L_0x0040:
        r0 = "amr_nb";
        r0 = r8.equals(r0);
        if (r0 == 0) goto L_0x004a;
    L_0x0048:
        r0 = 2;
        goto L_0x004b;
    L_0x004a:
        r0 = -1;
    L_0x004b:
        if (r0 == 0) goto L_0x007c;
    L_0x004d:
        if (r0 == r6) goto L_0x007b;
    L_0x004f:
        if (r0 == r5) goto L_0x007a;
    L_0x0051:
        if (r0 == r4) goto L_0x0079;
    L_0x0053:
        if (r0 == r3) goto L_0x0078;
    L_0x0055:
        if (r0 == r2) goto L_0x0076;
    L_0x0057:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r2 = "USING MediaRecorder.AudioEncoder.DEFAULT instead of ";
        r0.append(r2);
        r0.append(r8);
        r8 = ": ";
        r0.append(r8);
        r0.append(r1);
        r8 = r0.toString();
        r0 = "INVALID_AUDIO_ENCODER";
        android.util.Log.d(r0, r8);
        return r1;
    L_0x0076:
        r8 = 6;
        return r8;
    L_0x0078:
        return r3;
    L_0x0079:
        return r5;
    L_0x007a:
        return r6;
    L_0x007b:
        return r2;
    L_0x007c:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rnim.rn.audio.AudioRecorderManager.getAudioEncoderFromString(java.lang.String):int");
    }

    private int getOutputFormatFromString(java.lang.String r8) {
        /*
        r7 = this;
        r0 = r8.hashCode();
        r1 = 0;
        r2 = 5;
        r3 = 4;
        r4 = 3;
        r5 = 2;
        r6 = 1;
        switch(r0) {
            case -1558681978: goto L_0x0040;
            case -1413784883: goto L_0x0036;
            case -1413784604: goto L_0x002c;
            case -1067844614: goto L_0x0022;
            case 3645337: goto L_0x0018;
            case 367431774: goto L_0x000e;
            default: goto L_0x000d;
        };
    L_0x000d:
        goto L_0x004a;
    L_0x000e:
        r0 = "aac_adts";
        r8 = r8.equals(r0);
        if (r8 == 0) goto L_0x004a;
    L_0x0016:
        r8 = 1;
        goto L_0x004b;
    L_0x0018:
        r0 = "webm";
        r8 = r8.equals(r0);
        if (r8 == 0) goto L_0x004a;
    L_0x0020:
        r8 = 5;
        goto L_0x004b;
    L_0x0022:
        r0 = "mpeg_4";
        r8 = r8.equals(r0);
        if (r8 == 0) goto L_0x004a;
    L_0x002a:
        r8 = 0;
        goto L_0x004b;
    L_0x002c:
        r0 = "amr_wb";
        r8 = r8.equals(r0);
        if (r8 == 0) goto L_0x004a;
    L_0x0034:
        r8 = 3;
        goto L_0x004b;
    L_0x0036:
        r0 = "amr_nb";
        r8 = r8.equals(r0);
        if (r8 == 0) goto L_0x004a;
    L_0x003e:
        r8 = 2;
        goto L_0x004b;
    L_0x0040:
        r0 = "three_gpp";
        r8 = r8.equals(r0);
        if (r8 == 0) goto L_0x004a;
    L_0x0048:
        r8 = 4;
        goto L_0x004b;
    L_0x004a:
        r8 = -1;
    L_0x004b:
        if (r8 == 0) goto L_0x0067;
    L_0x004d:
        if (r8 == r6) goto L_0x0065;
    L_0x004f:
        if (r8 == r5) goto L_0x0064;
    L_0x0051:
        if (r8 == r4) goto L_0x0063;
    L_0x0053:
        if (r8 == r3) goto L_0x0062;
    L_0x0055:
        if (r8 == r2) goto L_0x005f;
    L_0x0057:
        r8 = "INVALID_OUPUT_FORMAT";
        r0 = "USING MediaRecorder.OutputFormat.DEFAULT : 0";
        android.util.Log.d(r8, r0);
        return r1;
    L_0x005f:
        r8 = 9;
        return r8;
    L_0x0062:
        return r6;
    L_0x0063:
        return r3;
    L_0x0064:
        return r4;
    L_0x0065:
        r8 = 6;
        return r8;
    L_0x0067:
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rnim.rn.audio.AudioRecorderManager.getOutputFormatFromString(java.lang.String):int");
    }

    @ReactMethod
    public void startRecording(Promise promise) {
        MediaRecorder mediaRecorder = this.recorder;
        if (mediaRecorder == null) {
            logAndRejectPromise(promise, "RECORDING_NOT_PREPARED", "Please call prepareRecordingAtPath before starting recording");
        } else if (this.isRecording) {
            logAndRejectPromise(promise, "INVALID_STATE", "Please call stopRecording before starting recording");
        } else {
            mediaRecorder.start();
            this.stopWatch.reset();
            this.stopWatch.start();
            this.isRecording = true;
            this.isPaused = false;
            startTimer();
            promise.resolve(this.currentOutputFile);
        }
    }

    /* JADX WARNING: Missing block: B:17:?, code:
            android.util.Log.e(r0, "FAILED TO PARSE FILE");
     */
    /* JADX WARNING: Missing block: B:25:?, code:
            logAndRejectPromise(r8, "RUNTIME_EXCEPTION", "No valid audio data received. You may be using a device that can't record audio.");
     */
    /* JADX WARNING: Missing block: B:26:0x0098, code:
            r7.recorder = null;
     */
    /* JADX WARNING: Missing block: B:27:0x009a, code:
            return;
     */
    /* JADX WARNING: Missing block: B:28:0x009b, code:
            r7.recorder = null;
     */
    @com.facebook.react.bridge.ReactMethod
    public void stopRecording(com.facebook.react.bridge.Promise r8) {
        /*
        r7 = this;
        r0 = "ReactNativeAudio";
        r1 = r7.isRecording;
        if (r1 != 0) goto L_0x000e;
    L_0x0006:
        r0 = "INVALID_STATE";
        r1 = "Please call startRecording before stopping recording";
        r7.logAndRejectPromise(r8, r0, r1);
        return;
    L_0x000e:
        r7.stopTimer();
        r1 = 0;
        r7.isRecording = r1;
        r7.isPaused = r1;
        r2 = 0;
        r3 = r7.recorder;	 Catch:{ RuntimeException -> 0x0091 }
        r3.stop();	 Catch:{ RuntimeException -> 0x0091 }
        r3 = r7.recorder;	 Catch:{ RuntimeException -> 0x0091 }
        r3.release();	 Catch:{ RuntimeException -> 0x0091 }
        r3 = r7.stopWatch;	 Catch:{ RuntimeException -> 0x0091 }
        r3.stop();	 Catch:{ RuntimeException -> 0x0091 }
        r7.recorder = r2;
        r2 = r7.currentOutputFile;
        r8.resolve(r2);
        r8 = com.facebook.react.bridge.Arguments.createMap();
        r2 = "status";
        r3 = "OK";
        r8.putString(r2, r3);
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "file://";
        r2.append(r3);
        r3 = r7.currentOutputFile;
        r2.append(r3);
        r2 = r2.toString();
        r3 = "audioFileURL";
        r8.putString(r3, r2);
        r2 = r7.includeBase64;
        if (r2 == 0) goto L_0x0082;
    L_0x0054:
        r2 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x007d }
        r3 = r7.currentOutputFile;	 Catch:{ FileNotFoundException -> 0x007d }
        r2.<init>(r3);	 Catch:{ FileNotFoundException -> 0x007d }
        r3 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r3 = new byte[r3];	 Catch:{ FileNotFoundException -> 0x007d }
        r4 = new java.io.ByteArrayOutputStream;	 Catch:{ FileNotFoundException -> 0x007d }
        r4.<init>();	 Catch:{ FileNotFoundException -> 0x007d }
    L_0x0064:
        r5 = r2.read(r3);	 Catch:{ IOException -> 0x006f }
        r6 = -1;
        if (r5 == r6) goto L_0x0074;
    L_0x006b:
        r4.write(r3, r1, r5);	 Catch:{ IOException -> 0x006f }
        goto L_0x0064;
    L_0x006f:
        r2 = "FAILED TO PARSE FILE";
        android.util.Log.e(r0, r2);	 Catch:{ FileNotFoundException -> 0x007d }
    L_0x0074:
        r2 = r4.toByteArray();	 Catch:{ FileNotFoundException -> 0x007d }
        r0 = android.util.Base64.encodeToString(r2, r1);	 Catch:{ FileNotFoundException -> 0x007d }
        goto L_0x0084;
    L_0x007d:
        r1 = "FAILED TO FIND FILE";
        android.util.Log.e(r0, r1);
    L_0x0082:
        r0 = "";
    L_0x0084:
        r1 = "base64";
        r8.putString(r1, r0);
        r0 = "recordingFinished";
        r7.sendEvent(r0, r8);
        return;
    L_0x008f:
        r8 = move-exception;
        goto L_0x009b;
    L_0x0091:
        r0 = "RUNTIME_EXCEPTION";
        r1 = "No valid audio data received. You may be using a device that can't record audio.";
        r7.logAndRejectPromise(r8, r0, r1);	 Catch:{ all -> 0x008f }
        r7.recorder = r2;
        return;
    L_0x009b:
        r7.recorder = r2;
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rnim.rn.audio.AudioRecorderManager.stopRecording(com.facebook.react.bridge.Promise):void");
    }

    @ReactMethod
    public void pauseRecording(Promise promise) {
        Exception e;
        String str = "Method not available on this version of Android.";
        String str2 = "RUNTIME_EXCEPTION";
        if (this.isPauseResumeCapable) {
            Method method = this.pauseMethod;
            if (method != null) {
                if (!this.isPaused) {
                    try {
                        method.invoke(this.recorder, new Object[0]);
                        this.stopWatch.stop();
                    } catch (InvocationTargetException e2) {
                        e = e2;
                    } catch (RuntimeException e3) {
                        e = e3;
                    } catch (IllegalAccessException e4) {
                        e = e4;
                    }
                }
                this.isPaused = true;
                promise.resolve(null);
                return;
            }
        }
        logAndRejectPromise(promise, str2, str);
        return;
        e.printStackTrace();
        logAndRejectPromise(promise, str2, str);
    }

    @ReactMethod
    public void resumeRecording(Promise promise) {
        Exception e;
        String str = "Method not available on this version of Android.";
        String str2 = "RUNTIME_EXCEPTION";
        if (this.isPauseResumeCapable) {
            Method method = this.resumeMethod;
            if (method != null) {
                if (this.isPaused) {
                    try {
                        method.invoke(this.recorder, new Object[0]);
                        this.stopWatch.start();
                    } catch (InvocationTargetException e2) {
                        e = e2;
                    } catch (RuntimeException e3) {
                        e = e3;
                    } catch (IllegalAccessException e4) {
                        e = e4;
                    }
                }
                this.isPaused = false;
                promise.resolve(null);
                return;
            }
        }
        logAndRejectPromise(promise, str2, str);
        return;
        e.printStackTrace();
        logAndRejectPromise(promise, str2, str);
    }

    private void startTimer() {
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (!AudioRecorderManager.this.isPaused) {
                    WritableMap createMap = Arguments.createMap();
                    createMap.putDouble(ReactVideoView.EVENT_PROP_CURRENT_TIME, (double) AudioRecorderManager.this.stopWatch.getTimeSeconds());
                    AudioRecorderManager.this.sendEvent("recordingProgress", createMap);
                }
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
            this.timer.purge();
            this.timer = null;
        }
    }

    private void sendEvent(String str, Object obj) {
        ((RCTDeviceEventEmitter) access$100().getJSModule(RCTDeviceEventEmitter.class)).emit(str, obj);
    }

    private void logAndRejectPromise(Promise promise, String str, String str2) {
        Log.e(TAG, str2);
        promise.reject(str, str2);
    }
}
