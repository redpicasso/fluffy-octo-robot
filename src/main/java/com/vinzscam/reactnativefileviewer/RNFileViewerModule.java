package com.vinzscam.reactnativefileviewer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.webkit.MimeTypeMap;
import androidx.core.content.FileProvider;
import com.RNFetchBlob.RNFetchBlobConst;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import java.io.File;

public class RNFileViewerModule extends ReactContextBaseJavaModule {
    private static final String DISMISS_EVENT = "RNFileViewerDidDismiss";
    private static final String OPEN_EVENT = "RNFileViewerDidOpen";
    private static final Integer RN_FILE_VIEWER_REQUEST = Integer.valueOf(33341);
    private static final String SHOW_OPEN_WITH_DIALOG = "showOpenWithDialog";
    private static final String SHOW_STORE_SUGGESTIONS = "showAppsSuggestions";
    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
            RNFileViewerModule.this.sendEvent(RNFileViewerModule.DISMISS_EVENT, Integer.valueOf(i - RNFileViewerModule.RN_FILE_VIEWER_REQUEST.intValue()), null);
        }
    };
    private final ReactApplicationContext reactContext;

    public String getName() {
        return "RNFileViewer";
    }

    public RNFileViewerModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.reactContext = reactApplicationContext;
        reactApplicationContext.addActivityEventListener(this.mActivityEventListener);
    }

    @ReactMethod
    public void open(String str, Integer num, ReadableMap readableMap) {
        Parcelable parse;
        String str2 = SHOW_OPEN_WITH_DIALOG;
        boolean z = false;
        Boolean valueOf = Boolean.valueOf(readableMap.hasKey(str2) ? readableMap.getBoolean(str2) : false);
        String str3 = SHOW_STORE_SUGGESTIONS;
        if (readableMap.hasKey(str3)) {
            z = readableMap.getBoolean(str3);
        }
        Boolean valueOf2 = Boolean.valueOf(z);
        boolean startsWith = str.startsWith(RNFetchBlobConst.FILE_PREFIX_CONTENT);
        String str4 = OPEN_EVENT;
        if (startsWith) {
            parse = Uri.parse(str);
        } else {
            File file = new File(str);
            try {
                StringBuilder stringBuilder = new StringBuilder(access$700().getPackageName());
                stringBuilder.append(".provider");
                parse = FileProvider.getUriForFile(access$700(), stringBuilder.toString(), file);
            } catch (IllegalArgumentException e) {
                sendEvent(str4, num, e.getMessage());
                return;
            }
        }
        if (parse == null) {
            sendEvent(str4, num, "Invalid file");
            return;
        }
        str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(str).toLowerCase());
        Intent intent = new Intent();
        String str5 = "android.intent.action.VIEW";
        intent.setAction(str5);
        intent.addFlags(1);
        intent.setDataAndType(parse, str);
        intent.putExtra("android.intent.extra.STREAM", parse);
        Intent createChooser = valueOf.booleanValue() ? Intent.createChooser(intent, "Open with") : intent;
        if (intent.resolveActivity(access$700().getPackageManager()) != null) {
            try {
                access$700().startActivityForResult(createChooser, num.intValue() + RN_FILE_VIEWER_REQUEST.intValue());
                sendEvent(str4, num, null);
            } catch (Exception e2) {
                sendEvent(str4, num, e2.getMessage());
            }
        } else {
            try {
                if (valueOf2.booleanValue()) {
                    if (str == null) {
                        throw new Exception("It wasn't possible to detect the type of the file");
                    }
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("market://search?q=");
                    stringBuilder2.append(str);
                    stringBuilder2.append("&c=apps");
                    access$700().startActivity(new Intent(str5, Uri.parse(stringBuilder2.toString())));
                }
                throw new Exception("No app associated with this mime type");
            } catch (Exception e22) {
                sendEvent(str4, num, e22.getMessage());
            }
        }
    }

    private void sendEvent(String str, Integer num, String str2) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("id", num.intValue());
        if (str2 != null) {
            createMap.putString(ReactVideoView.EVENT_PROP_ERROR, str2);
        }
        ((RCTDeviceEventEmitter) this.reactContext.getJSModule(RCTDeviceEventEmitter.class)).emit(str, createMap);
    }
}
