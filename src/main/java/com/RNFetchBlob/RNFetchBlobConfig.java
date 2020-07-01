package com.RNFetchBlob;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

class RNFetchBlobConfig {
    public ReadableMap addAndroidDownloads;
    public String appendExt;
    public Boolean auto;
    public ReadableArray binaryContentTypes;
    public Boolean fileCache;
    public Boolean followRedirect;
    public Boolean increment;
    public String key;
    public String mime;
    public Boolean overwrite;
    public String path;
    public long timeout = 60000;
    public Boolean trusty;
    public Boolean wifiOnly;

    RNFetchBlobConfig(ReadableMap readableMap) {
        boolean z = false;
        Boolean valueOf = Boolean.valueOf(false);
        this.wifiOnly = valueOf;
        Boolean valueOf2 = Boolean.valueOf(true);
        this.overwrite = valueOf2;
        this.increment = valueOf;
        this.followRedirect = valueOf2;
        String str = null;
        this.binaryContentTypes = null;
        if (readableMap != null) {
            String str2 = "fileCache";
            this.fileCache = Boolean.valueOf(readableMap.hasKey(str2) ? readableMap.getBoolean(str2) : false);
            str2 = RNFetchBlobConst.RNFB_RESPONSE_PATH;
            this.path = readableMap.hasKey(str2) ? readableMap.getString(str2) : null;
            str2 = "appendExt";
            this.appendExt = readableMap.hasKey(str2) ? readableMap.getString(str2) : "";
            str2 = "trusty";
            this.trusty = Boolean.valueOf(readableMap.hasKey(str2) ? readableMap.getBoolean(str2) : false);
            str2 = "wifiOnly";
            this.wifiOnly = Boolean.valueOf(readableMap.hasKey(str2) ? readableMap.getBoolean(str2) : false);
            str2 = "addAndroidDownloads";
            if (readableMap.hasKey(str2)) {
                this.addAndroidDownloads = readableMap.getMap(str2);
            }
            str2 = "binaryContentTypes";
            if (readableMap.hasKey(str2)) {
                this.binaryContentTypes = readableMap.getArray(str2);
            }
            str2 = this.path;
            if (str2 != null && str2.toLowerCase().contains("?append=true")) {
                this.overwrite = valueOf;
            }
            String str3 = "overwrite";
            if (readableMap.hasKey(str3)) {
                this.overwrite = Boolean.valueOf(readableMap.getBoolean(str3));
            }
            str3 = "followRedirect";
            if (readableMap.hasKey(str3)) {
                this.followRedirect = Boolean.valueOf(readableMap.getBoolean(str3));
            }
            str3 = "key";
            this.key = readableMap.hasKey(str3) ? readableMap.getString(str3) : null;
            str3 = "contentType";
            if (readableMap.hasKey(str3)) {
                str = readableMap.getString(str3);
            }
            this.mime = str;
            str3 = "increment";
            this.increment = Boolean.valueOf(readableMap.hasKey(str3) ? readableMap.getBoolean(str3) : false);
            if (readableMap.hasKey("auto")) {
                z = readableMap.getBoolean("auto");
            }
            this.auto = Boolean.valueOf(z);
            if (readableMap.hasKey("timeout")) {
                this.timeout = (long) readableMap.getInt("timeout");
            }
        }
    }
}
