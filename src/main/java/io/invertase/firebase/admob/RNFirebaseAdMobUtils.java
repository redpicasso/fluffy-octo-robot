package io.invertase.firebase.admob;

import androidx.core.os.EnvironmentCompat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.VideoOptions;
import io.invertase.firebase.Utils;

class RNFirebaseAdMobUtils {
    RNFirebaseAdMobUtils() {
    }

    static WritableMap errorCodeToMap(int i) {
        WritableMap createMap = Arguments.createMap();
        String str = "message";
        String str2 = "code";
        if (i == 0) {
            createMap.putString(str2, "admob/error-code-internal-error");
            createMap.putString(str, "Something happened internally; for instance, an invalid response was received from the ad server.");
        } else if (i == 1) {
            createMap.putString(str2, "admob/error-code-invalid-request");
            createMap.putString(str, "The ad request was invalid; for instance, the ad unit ID was incorrect.");
        } else if (i == 2) {
            createMap.putString(str2, "admob/error-code-network-error");
            createMap.putString(str, "The ad request was unsuccessful due to network connectivity.");
        } else if (i == 3) {
            createMap.putString(str2, "admob/error-code-no-fill");
            createMap.putString(str, "The ad request was successful, but no ad was returned due to lack of ad inventory.");
        }
        return createMap;
    }

    static Builder buildRequest(ReadableMap readableMap) {
        Builder builder = new Builder();
        String str = "isDesignedForFamilies";
        if (readableMap.hasKey(str)) {
            builder.setIsDesignedForFamilies(readableMap.getBoolean(str));
        }
        str = "tagForChildDirectedTreatment";
        if (readableMap.hasKey(str)) {
            builder.tagForChildDirectedTreatment(readableMap.getBoolean(str));
        }
        str = "contentUrl";
        if (readableMap.hasKey(str)) {
            builder.setContentUrl(readableMap.getString(str));
        }
        str = "requestAgent";
        if (readableMap.hasKey(str)) {
            builder.setRequestAgent(readableMap.getString(str));
        }
        str = "gender";
        if (readableMap.hasKey(str)) {
            str = readableMap.getString(str);
            Object obj = -1;
            int hashCode = str.hashCode();
            if (hashCode != -1278174388) {
                if (hashCode != -284840886) {
                    if (hashCode == 3343885 && str.equals("male")) {
                        obj = null;
                    }
                } else if (str.equals(EnvironmentCompat.MEDIA_UNKNOWN)) {
                    obj = 2;
                }
            } else if (str.equals("female")) {
                obj = 1;
            }
            if (obj == null) {
                builder.setGender(1);
            } else if (obj == 1) {
                builder.setGender(2);
            } else if (obj == 2) {
                builder.setGender(0);
            }
        }
        for (String next : Utils.recursivelyDeconstructReadableArray(readableMap.getArray("testDevices"))) {
            if (next == "DEVICE_ID_EMULATOR") {
                builder.addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB");
            } else {
                builder.addTestDevice(next);
            }
        }
        for (String str2 : Utils.recursivelyDeconstructReadableArray(readableMap.getArray("keywords"))) {
            builder.addKeyword(str2);
        }
        return builder;
    }

    static VideoOptions.Builder buildVideoOptions(ReadableMap readableMap) {
        VideoOptions.Builder builder = new VideoOptions.Builder();
        builder.setStartMuted(readableMap.getBoolean("startMuted"));
        return builder;
    }

    /* JADX WARNING: Missing block: B:25:0x0077, code:
            if (r4.equals("LARGE_BANNER") != false) goto L_0x007b;
     */
    static com.google.android.gms.ads.AdSize stringToAdSize(java.lang.String r4) {
        /*
        r0 = "([0-9]+)x([0-9]+)";
        r0 = java.util.regex.Pattern.compile(r0);
        r0 = r0.matcher(r4);
        r1 = r0.find();
        r2 = 2;
        r3 = 1;
        if (r1 == 0) goto L_0x0028;
    L_0x0012:
        r4 = r0.group(r3);
        r4 = java.lang.Integer.parseInt(r4);
        r0 = r0.group(r2);
        r0 = java.lang.Integer.parseInt(r0);
        r1 = new com.google.android.gms.ads.AdSize;
        r1.<init>(r4, r0);
        return r1;
    L_0x0028:
        r4 = r4.toUpperCase();
        r0 = -1;
        r1 = r4.hashCode();
        switch(r1) {
            case -1966536496: goto L_0x0071;
            case -1008851236: goto L_0x0067;
            case -140586366: goto L_0x005d;
            case -96588539: goto L_0x0053;
            case 446888797: goto L_0x0049;
            case 1669469470: goto L_0x003f;
            case 1951953708: goto L_0x0035;
            default: goto L_0x0034;
        };
    L_0x0034:
        goto L_0x007a;
    L_0x0035:
        r1 = "BANNER";
        r4 = r4.equals(r1);
        if (r4 == 0) goto L_0x007a;
    L_0x003d:
        r2 = 1;
        goto L_0x007b;
    L_0x003f:
        r1 = "SMART_BANNER_LANDSCAPE";
        r4 = r4.equals(r1);
        if (r4 == 0) goto L_0x007a;
    L_0x0047:
        r2 = 7;
        goto L_0x007b;
    L_0x0049:
        r1 = "LEADERBOARD";
        r4 = r4.equals(r1);
        if (r4 == 0) goto L_0x007a;
    L_0x0051:
        r2 = 5;
        goto L_0x007b;
    L_0x0053:
        r1 = "MEDIUM_RECTANGLE";
        r4 = r4.equals(r1);
        if (r4 == 0) goto L_0x007a;
    L_0x005b:
        r2 = 3;
        goto L_0x007b;
    L_0x005d:
        r1 = "SMART_BANNER";
        r4 = r4.equals(r1);
        if (r4 == 0) goto L_0x007a;
    L_0x0065:
        r2 = 6;
        goto L_0x007b;
    L_0x0067:
        r1 = "FULL_BANNER";
        r4 = r4.equals(r1);
        if (r4 == 0) goto L_0x007a;
    L_0x006f:
        r2 = 4;
        goto L_0x007b;
    L_0x0071:
        r1 = "LARGE_BANNER";
        r4 = r4.equals(r1);
        if (r4 == 0) goto L_0x007a;
    L_0x0079:
        goto L_0x007b;
    L_0x007a:
        r2 = -1;
    L_0x007b:
        switch(r2) {
            case 2: goto L_0x0090;
            case 3: goto L_0x008d;
            case 4: goto L_0x008a;
            case 5: goto L_0x0087;
            case 6: goto L_0x0084;
            case 7: goto L_0x0081;
            default: goto L_0x007e;
        };
    L_0x007e:
        r4 = com.google.android.gms.ads.AdSize.BANNER;
        return r4;
    L_0x0081:
        r4 = com.google.android.gms.ads.AdSize.SMART_BANNER;
        return r4;
    L_0x0084:
        r4 = com.google.android.gms.ads.AdSize.SMART_BANNER;
        return r4;
    L_0x0087:
        r4 = com.google.android.gms.ads.AdSize.LEADERBOARD;
        return r4;
    L_0x008a:
        r4 = com.google.android.gms.ads.AdSize.FULL_BANNER;
        return r4;
    L_0x008d:
        r4 = com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE;
        return r4;
    L_0x0090:
        r4 = com.google.android.gms.ads.AdSize.LARGE_BANNER;
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.invertase.firebase.admob.RNFirebaseAdMobUtils.stringToAdSize(java.lang.String):com.google.android.gms.ads.AdSize");
    }
}
