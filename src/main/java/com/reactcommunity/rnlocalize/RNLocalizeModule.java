package com.reactcommunity.rnlocalize;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.LocaleList;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.text.format.DateFormat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import javax.annotation.Nullable;

@ReactModule(name = "RNLocalize")
public class RNLocalizeModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    public static final String MODULE_NAME = "RNLocalize";
    private final List<String> USES_FAHRENHEIT = Arrays.asList(new String[]{"BS", "BZ", "KY", "PR", "PW", "US"});
    private final List<String> USES_IMPERIAL = Arrays.asList(new String[]{"LR", "MM", "US"});
    private final List<String> USES_RTL_LAYOUT = Arrays.asList(new String[]{"ar", "ckb", "fa", "he", "ks", "lrc", "mzn", "ps", "ug", "ur", "yi"});
    private boolean emitChangeOnResume = false;
    private boolean mainActivityVisible = true;

    public String getName() {
        return MODULE_NAME;
    }

    public void onHostDestroy() {
    }

    public RNLocalizeModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.DATE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        BroadcastReceiver anonymousClass1 = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null) {
                    RNLocalizeModule.this.onLocalizationDidChange();
                }
            }
        };
        reactApplicationContext.addLifecycleEventListener(this);
        reactApplicationContext.registerReceiver(anonymousClass1, intentFilter);
    }

    @Nullable
    public Map<String, Object> getConstants() {
        Map hashMap = new HashMap();
        hashMap.put("initialConstants", getExported());
        return hashMap;
    }

    private void onLocalizationDidChange() {
        if (this.mainActivityVisible) {
            emitLocalizationDidChange();
        } else {
            this.emitChangeOnResume = true;
        }
    }

    public void onHostResume() {
        this.mainActivityVisible = true;
        if (this.emitChangeOnResume) {
            emitLocalizationDidChange();
            this.emitChangeOnResume = false;
        }
    }

    public void onHostPause() {
        this.mainActivityVisible = false;
    }

    private void emitLocalizationDidChange() {
        if (access$100().hasActiveCatalystInstance()) {
            ((RCTDeviceEventEmitter) access$100().getJSModule(RCTDeviceEventEmitter.class)).emit("localizationDidChange", getExported());
        }
    }

    private List<Locale> getLocales() {
        List<Locale> arrayList = new ArrayList();
        Configuration configuration = access$100().getResources().getConfiguration();
        if (VERSION.SDK_INT < 24) {
            arrayList.add(configuration.locale);
        } else {
            LocaleList locales = configuration.getLocales();
            for (int i = 0; i < locales.size(); i++) {
                arrayList.add(locales.get(i));
            }
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0038  */
    private java.lang.String getLanguageCode(java.util.Locale r5) {
        /*
        r4 = this;
        r5 = r5.getLanguage();
        r0 = r5.hashCode();
        r1 = 3365; // 0xd25 float:4.715E-42 double:1.6625E-320;
        r2 = 2;
        r3 = 1;
        if (r0 == r1) goto L_0x002b;
    L_0x000e:
        r1 = 3374; // 0xd2e float:4.728E-42 double:1.667E-320;
        if (r0 == r1) goto L_0x0021;
    L_0x0012:
        r1 = 3391; // 0xd3f float:4.752E-42 double:1.6754E-320;
        if (r0 == r1) goto L_0x0017;
    L_0x0016:
        goto L_0x0035;
    L_0x0017:
        r0 = "ji";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0035;
    L_0x001f:
        r0 = 2;
        goto L_0x0036;
    L_0x0021:
        r0 = "iw";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0035;
    L_0x0029:
        r0 = 0;
        goto L_0x0036;
    L_0x002b:
        r0 = "in";
        r0 = r5.equals(r0);
        if (r0 == 0) goto L_0x0035;
    L_0x0033:
        r0 = 1;
        goto L_0x0036;
    L_0x0035:
        r0 = -1;
    L_0x0036:
        if (r0 == 0) goto L_0x0043;
    L_0x0038:
        if (r0 == r3) goto L_0x0040;
    L_0x003a:
        if (r0 == r2) goto L_0x003d;
    L_0x003c:
        return r5;
    L_0x003d:
        r5 = "yi";
        return r5;
    L_0x0040:
        r5 = "id";
        return r5;
    L_0x0043:
        r5 = "he";
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactcommunity.rnlocalize.RNLocalizeModule.getLanguageCode(java.util.Locale):java.lang.String");
    }

    @Nullable
    private String getScriptCode(Locale locale) {
        if (VERSION.SDK_INT < 21) {
            return null;
        }
        String script = locale.getScript();
        if (script.equals("")) {
            script = null;
        }
        return script;
    }

    private String getCountryCode(Locale locale, String str) {
        try {
            String country = locale.getCountry();
            if (country == null || country.equals("")) {
                country = str;
            }
            return country;
        } catch (Exception unused) {
            return str;
        }
    }

    private String getCurrencyCode(Locale locale, String str) {
        try {
            Currency instance = Currency.getInstance(locale);
            if (instance != null) {
                str = instance.getCurrencyCode();
            }
        } catch (Exception unused) {
            return str;
        }
    }

    private boolean getIsRTL(Locale locale) {
        if (VERSION.SDK_INT < 17) {
            return this.USES_RTL_LAYOUT.contains(getLanguageCode(locale));
        }
        if (TextUtils.getLayoutDirectionFromLocale(locale) == 1) {
            return true;
        }
        return false;
    }

    private String getTemperatureUnit(Locale locale) {
        return this.USES_FAHRENHEIT.contains(getCountryCode(locale, "US")) ? "fahrenheit" : "celsius";
    }

    private boolean getUsesMetricSystem(Locale locale) {
        return this.USES_IMPERIAL.contains(getCountryCode(locale, "US")) ^ 1;
    }

    private WritableMap getNumberFormatSettings(Locale locale) {
        WritableMap createMap = Arguments.createMap();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale);
        createMap.putString("decimalSeparator", String.valueOf(decimalFormatSymbols.getDecimalSeparator()));
        createMap.putString("groupingSeparator", String.valueOf(decimalFormatSymbols.getGroupingSeparator()));
        return createMap;
    }

    private boolean getUsesAutoDateAndTime() {
        int i;
        ContentResolver contentResolver = access$100().getContentResolver();
        String str = "auto_time";
        if (VERSION.SDK_INT >= 17) {
            i = Global.getInt(contentResolver, str, 0);
        } else {
            i = System.getInt(contentResolver, str, 0);
        }
        if (i != 0) {
            return true;
        }
        return false;
    }

    private boolean getUsesAutoTimeZone() {
        int i;
        ContentResolver contentResolver = access$100().getContentResolver();
        String str = "auto_time_zone";
        if (VERSION.SDK_INT >= 17) {
            i = Global.getInt(contentResolver, str, 0);
        } else {
            i = System.getInt(contentResolver, str, 0);
        }
        if (i != 0) {
            return true;
        }
        return false;
    }

    private WritableMap getExported() {
        List<Locale> locales = getLocales();
        List arrayList = new ArrayList();
        Locale locale = (Locale) locales.get(0);
        String countryCode = getCountryCode(locale, "US");
        WritableArray createArray = Arguments.createArray();
        WritableArray createArray2 = Arguments.createArray();
        for (Locale locale2 : locales) {
            String languageCode = getLanguageCode(locale2);
            String scriptCode = getScriptCode(locale2);
            String countryCode2 = getCountryCode(locale2, countryCode);
            String currencyCode = getCurrencyCode(locale2, "USD");
            if (!arrayList.contains(currencyCode)) {
                arrayList.add(currencyCode);
                createArray2.pushString(currencyCode);
            }
            WritableMap createMap = Arguments.createMap();
            createMap.putString("languageCode", languageCode);
            createMap.putString("countryCode", countryCode2);
            createMap.putBoolean("isRTL", getIsRTL(locale2));
            String str = "-";
            if (scriptCode != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(languageCode);
                stringBuilder.append(str);
                stringBuilder.append(scriptCode);
                languageCode = stringBuilder.toString();
                createMap.putString("scriptCode", scriptCode);
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(languageCode);
            stringBuilder2.append(str);
            stringBuilder2.append(countryCode2);
            createMap.putString("languageTag", stringBuilder2.toString());
            createArray.pushMap(createMap);
        }
        WritableMap createMap2 = Arguments.createMap();
        createMap2.putString("calendar", "gregorian");
        createMap2.putString("country", countryCode);
        createMap2.putArray("currencies", createArray2);
        createMap2.putArray("locales", createArray);
        createMap2.putMap("numberFormatSettings", getNumberFormatSettings(locale));
        createMap2.putString("temperatureUnit", getTemperatureUnit(locale));
        createMap2.putString("timeZone", TimeZone.getDefault().getID());
        createMap2.putBoolean("uses24HourClock", DateFormat.is24HourFormat(access$100()));
        createMap2.putBoolean("usesMetricSystem", getUsesMetricSystem(locale));
        createMap2.putBoolean("usesAutoDateAndTime", getUsesAutoDateAndTime());
        createMap2.putBoolean("usesAutoTimeZone", getUsesAutoTimeZone());
        return createMap2;
    }
}
