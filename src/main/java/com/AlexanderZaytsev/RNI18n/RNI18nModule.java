package com.AlexanderZaytsev.RNI18n;

import android.os.Build.VERSION;
import android.os.LocaleList;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RNI18nModule extends ReactContextBaseJavaModule {
    public String getName() {
        return "RNI18n";
    }

    public RNI18nModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    private String toLanguageTag(Locale locale) {
        String toLanguageTag;
        if (VERSION.SDK_INT >= 21) {
            toLanguageTag = locale.toLanguageTag();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(locale.getLanguage());
            if (locale.getCountry() != null) {
                stringBuilder.append("-");
                stringBuilder.append(locale.getCountry());
            }
            toLanguageTag = stringBuilder.toString();
        }
        return toLanguageTag.matches("^(iw|in|ji).*") ? toLanguageTag.replace("iw", "he").replace("in", "id").replace("ji", "yi") : toLanguageTag;
    }

    private WritableArray getLocaleList() {
        WritableArray createArray = Arguments.createArray();
        if (VERSION.SDK_INT >= 24) {
            LocaleList locales = access$900().getResources().getConfiguration().getLocales();
            for (int i = 0; i < locales.size(); i++) {
                createArray.pushString(toLanguageTag(locales.get(i)));
            }
        } else {
            createArray.pushString(toLanguageTag(access$900().getResources().getConfiguration().locale));
        }
        return createArray;
    }

    public Map<String, Object> getConstants() {
        Map hashMap = new HashMap();
        hashMap.put("languages", getLocaleList());
        return hashMap;
    }

    @ReactMethod
    public void getLanguages(Promise promise) {
        try {
            promise.resolve(getLocaleList());
        } catch (Throwable e) {
            promise.reject(e);
        }
    }
}
