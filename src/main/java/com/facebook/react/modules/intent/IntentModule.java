package com.facebook.react.modules.intent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.module.annotations.ReactModule;
import com.google.common.primitives.Ints;
import javax.annotation.Nullable;

@ReactModule(name = "IntentAndroid")
public class IntentModule extends ReactContextBaseJavaModule {
    public static final String NAME = "IntentAndroid";

    /* renamed from: com.facebook.react.modules.intent.IntentModule$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$bridge$ReadableType = new int[ReadableType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$facebook$react$bridge$ReadableType[com.facebook.react.bridge.ReadableType.Boolean.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.facebook.react.bridge.ReadableType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$react$bridge$ReadableType = r0;
            r0 = $SwitchMap$com$facebook$react$bridge$ReadableType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.react.bridge.ReadableType.String;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$react$bridge$ReadableType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.react.bridge.ReadableType.Number;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$react$bridge$ReadableType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.react.bridge.ReadableType.Boolean;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.intent.IntentModule.1.<clinit>():void");
        }
    }

    public String getName() {
        return NAME;
    }

    public IntentModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void getInitialURL(Promise promise) {
        try {
            Activity currentActivity = access$700();
            Object obj = null;
            if (currentActivity != null) {
                Intent intent = currentActivity.getIntent();
                String action = intent.getAction();
                Uri data = intent.getData();
                if (data != null && ("android.intent.action.VIEW".equals(action) || "android.nfc.action.NDEF_DISCOVERED".equals(action))) {
                    obj = data.toString();
                }
            }
            promise.resolve(obj);
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not get the initial URL : ");
            stringBuilder.append(e.getMessage());
            promise.reject(new JSApplicationIllegalArgumentException(stringBuilder.toString()));
        }
    }

    @ReactMethod
    public void openURL(String str, Promise promise) {
        if (str == null || str.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid URL: ");
            stringBuilder.append(str);
            promise.reject(new JSApplicationIllegalArgumentException(stringBuilder.toString()));
            return;
        }
        try {
            Activity currentActivity = access$700();
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str).normalizeScheme());
            String packageName = access$200().getPackageName();
            ComponentName resolveActivity = intent.resolveActivity(access$200().getPackageManager());
            Object packageName2 = resolveActivity != null ? resolveActivity.getPackageName() : "";
            if (currentActivity == null || !packageName.equals(packageName2)) {
                intent.addFlags(268435456);
            }
            if (currentActivity != null) {
                currentActivity.startActivity(intent);
            } else {
                access$200().startActivity(intent);
            }
            promise.resolve(Boolean.valueOf(true));
        } catch (Exception e) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Could not open URL '");
            stringBuilder2.append(str);
            stringBuilder2.append("': ");
            stringBuilder2.append(e.getMessage());
            promise.reject(new JSApplicationIllegalArgumentException(stringBuilder2.toString()));
        }
    }

    @ReactMethod
    public void canOpenURL(String str, Promise promise) {
        if (str == null || str.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid URL: ");
            stringBuilder.append(str);
            promise.reject(new JSApplicationIllegalArgumentException(stringBuilder.toString()));
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.addFlags(268435456);
            promise.resolve(Boolean.valueOf(intent.resolveActivity(access$200().getPackageManager()) != null));
        } catch (Exception e) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Could not check if URL '");
            stringBuilder2.append(str);
            stringBuilder2.append("' can be opened: ");
            stringBuilder2.append(e.getMessage());
            promise.reject(new JSApplicationIllegalArgumentException(stringBuilder2.toString()));
        }
    }

    @ReactMethod
    public void openSettings(Promise promise) {
        try {
            Intent intent = new Intent();
            Activity currentActivity = access$700();
            String packageName = access$200().getPackageName();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.addCategory("android.intent.category.DEFAULT");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("package:");
            stringBuilder.append(packageName);
            intent.setData(Uri.parse(stringBuilder.toString()));
            intent.addFlags(268435456);
            intent.addFlags(Ints.MAX_POWER_OF_TWO);
            intent.addFlags(8388608);
            currentActivity.startActivity(intent);
            promise.resolve(Boolean.valueOf(true));
        } catch (Exception e) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Could not open the Settings: ");
            stringBuilder2.append(e.getMessage());
            promise.reject(new JSApplicationIllegalArgumentException(stringBuilder2.toString()));
        }
    }

    @ReactMethod
    public void sendIntent(String str, @Nullable ReadableArray readableArray, Promise promise) {
        String str2 = ".";
        StringBuilder stringBuilder;
        if (str == null || str.isEmpty()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid Action: ");
            stringBuilder.append(str);
            stringBuilder.append(str2);
            promise.reject(new JSApplicationIllegalArgumentException(stringBuilder.toString()));
            return;
        }
        Intent intent = new Intent(str);
        if (intent.resolveActivity(access$200().getPackageManager()) == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not launch Intent with action ");
            stringBuilder.append(str);
            stringBuilder.append(str2);
            promise.reject(new JSApplicationIllegalArgumentException(stringBuilder.toString()));
            return;
        }
        if (readableArray != null) {
            for (int i = 0; i < readableArray.size(); i++) {
                ReadableMap map = readableArray.getMap(i);
                String nextKey = map.keySetIterator().nextKey();
                int i2 = AnonymousClass1.$SwitchMap$com$facebook$react$bridge$ReadableType[map.getType(nextKey).ordinal()];
                if (i2 == 1) {
                    intent.putExtra(nextKey, map.getString(nextKey));
                } else if (i2 == 2) {
                    intent.putExtra(nextKey, Double.valueOf(map.getDouble(nextKey)));
                } else if (i2 != 3) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Extra type for ");
                    stringBuilder2.append(nextKey);
                    stringBuilder2.append(" not supported.");
                    promise.reject(new JSApplicationIllegalArgumentException(stringBuilder2.toString()));
                    return;
                } else {
                    intent.putExtra(nextKey, map.getBoolean(nextKey));
                }
            }
        }
        access$200().startActivity(intent);
    }
}
