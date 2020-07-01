package com.RNFetchBlob;

import android.content.res.AssetFileDescriptor;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.util.Base64;
import androidx.core.app.NotificationCompat;
import com.RNFetchBlob.Utils.PathResolver;
import com.bumptech.glide.load.Key;
import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

class RNFetchBlobFS {
    private static HashMap<String, RNFetchBlobFS> fileStreams = new HashMap();
    private RCTDeviceEventEmitter emitter;
    private String encoding = RNFetchBlobConst.RNFB_RESPONSE_BASE64;
    private ReactApplicationContext mCtx;
    private OutputStream writeStreamInstance = null;

    RNFetchBlobFS(ReactApplicationContext reactApplicationContext) {
        this.mCtx = reactApplicationContext;
        this.emitter = (RCTDeviceEventEmitter) reactApplicationContext.getJSModule(RCTDeviceEventEmitter.class);
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x00c0 A:{Catch:{ all -> 0x00e1, FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }} */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00c5 A:{Catch:{ all -> 0x00e1, FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00c0 A:{Catch:{ all -> 0x00e1, FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }} */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00c5 A:{Catch:{ all -> 0x00e1, FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }} */
    static void writeFile(java.lang.String r6, java.lang.String r7, java.lang.String r8, boolean r9, com.facebook.react.bridge.Promise r10) {
        /*
        r0 = "EUNSPECIFIED";
        r1 = "File '";
        r2 = "ENOENT";
        r3 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r3.<init>(r6);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r4 = r3.getParentFile();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r5 = r3.exists();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        if (r5 != 0) goto L_0x005b;
    L_0x0015:
        if (r4 == 0) goto L_0x003d;
    L_0x0017:
        r5 = r4.exists();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        if (r5 != 0) goto L_0x003d;
    L_0x001d:
        r4 = r4.mkdirs();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        if (r4 != 0) goto L_0x003d;
    L_0x0023:
        r7 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r7.<init>();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8 = "Failed to create parent directory of '";
        r7.append(r8);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r7.append(r6);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8 = "'";
        r7.append(r8);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r7 = r7.toString();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r10.reject(r0, r7);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        return;
    L_0x003d:
        r4 = r3.createNewFile();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        if (r4 != 0) goto L_0x005b;
    L_0x0043:
        r7 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r7.<init>();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r7.append(r1);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r7.append(r6);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8 = "' does not exist and could not be created";
        r7.append(r8);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r7 = r7.toString();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r10.reject(r2, r7);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        return;
    L_0x005b:
        r4 = "uri";
        r4 = r7.equalsIgnoreCase(r4);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        if (r4 == 0) goto L_0x00c9;
    L_0x0063:
        r7 = normalizePath(r8);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8.<init>(r7);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r4 = r8.exists();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        if (r4 != 0) goto L_0x0094;
    L_0x0072:
        r8 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8.<init>();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r9 = "No such file '";
        r8.append(r9);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8.append(r6);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r9 = "' ('";
        r8.append(r9);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8.append(r7);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r7 = "')";
        r8.append(r7);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r7 = r8.toString();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r10.reject(r2, r7);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        return;
    L_0x0094:
        r7 = 10240; // 0x2800 float:1.4349E-41 double:5.059E-320;
        r7 = new byte[r7];	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r4 = 0;
        r5 = new java.io.FileInputStream;	 Catch:{ all -> 0x00bc }
        r5.<init>(r8);	 Catch:{ all -> 0x00bc }
        r8 = new java.io.FileOutputStream;	 Catch:{ all -> 0x00ba }
        r8.<init>(r3, r9);	 Catch:{ all -> 0x00ba }
        r9 = 0;
        r3 = 0;
    L_0x00a5:
        r4 = r5.read(r7);	 Catch:{ all -> 0x00b7 }
        if (r4 <= 0) goto L_0x00b0;
    L_0x00ab:
        r8.write(r7, r9, r4);	 Catch:{ all -> 0x00b7 }
        r3 = r3 + r4;
        goto L_0x00a5;
    L_0x00b0:
        r5.close();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8.close();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        goto L_0x00d9;
    L_0x00b7:
        r7 = move-exception;
        r4 = r8;
        goto L_0x00be;
    L_0x00ba:
        r7 = move-exception;
        goto L_0x00be;
    L_0x00bc:
        r7 = move-exception;
        r5 = r4;
    L_0x00be:
        if (r5 == 0) goto L_0x00c3;
    L_0x00c0:
        r5.close();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
    L_0x00c3:
        if (r4 == 0) goto L_0x00c8;
    L_0x00c5:
        r4.close();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
    L_0x00c8:
        throw r7;	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
    L_0x00c9:
        r7 = stringToBytes(r8, r7);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8.<init>(r3, r9);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r8.write(r7);	 Catch:{ all -> 0x00e1 }
        r3 = r7.length;	 Catch:{ all -> 0x00e1 }
        r8.close();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
    L_0x00d9:
        r7 = java.lang.Integer.valueOf(r3);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        r10.resolve(r7);	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        goto L_0x0106;
    L_0x00e1:
        r7 = move-exception;
        r8.close();	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
        throw r7;	 Catch:{ FileNotFoundException -> 0x00ef, Exception -> 0x00e6 }
    L_0x00e6:
        r6 = move-exception;
        r6 = r6.getLocalizedMessage();
        r10.reject(r0, r6);
        goto L_0x0106;
    L_0x00ef:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r7.append(r1);
        r7.append(r6);
        r6 = "' does not exist and could not be created, or it is a directory";
        r7.append(r6);
        r6 = r7.toString();
        r10.reject(r2, r6);
    L_0x0106:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobFS.writeFile(java.lang.String, java.lang.String, java.lang.String, boolean, com.facebook.react.bridge.Promise):void");
    }

    static void writeFile(String str, ReadableArray readableArray, boolean z, Promise promise) {
        String str2 = "' does not exist and could not be created";
        String str3 = "File '";
        String str4 = "ENOENT";
        StringBuilder stringBuilder;
        FileOutputStream fileOutputStream;
        try {
            File file = new File(str);
            File parentFile = file.getParentFile();
            if (!file.exists()) {
                if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Failed to create parent directory of '");
                    stringBuilder2.append(str);
                    stringBuilder2.append("'");
                    promise.reject("ENOTDIR", stringBuilder2.toString());
                    return;
                } else if (!file.createNewFile()) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str3);
                    stringBuilder.append(str);
                    stringBuilder.append(str2);
                    promise.reject(str4, stringBuilder.toString());
                    return;
                }
            }
            fileOutputStream = new FileOutputStream(file, z);
            byte[] bArr = new byte[readableArray.size()];
            for (int i = 0; i < readableArray.size(); i++) {
                bArr[i] = (byte) readableArray.getInt(i);
            }
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            promise.resolve(Integer.valueOf(readableArray.size()));
        } catch (FileNotFoundException unused) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str3);
            stringBuilder.append(str);
            stringBuilder.append(str2);
            promise.reject(str4, stringBuilder.toString());
        } catch (Exception e) {
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        } catch (Throwable th) {
            fileOutputStream.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0093 A:{Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }} */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0076 A:{Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }} */
    static void readFile(java.lang.String r8, java.lang.String r9, com.facebook.react.bridge.Promise r10) {
        /*
        r0 = "bundle-assets://";
        r1 = normalizePath(r8);
        if (r1 == 0) goto L_0x0009;
    L_0x0008:
        r8 = r1;
    L_0x0009:
        r2 = "EUNSPECIFIED";
        r3 = 0;
        if (r1 == 0) goto L_0x003d;
    L_0x000e:
        r4 = r1.startsWith(r0);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        if (r4 == 0) goto L_0x003d;
    L_0x0014:
        r1 = "";
        r0 = r8.replace(r0, r1);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r1 = com.RNFetchBlob.RNFetchBlob.RCTContext;	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r1 = r1.getAssets();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r1 = r1.openFd(r0);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r4 = r1.getLength();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r1 = (int) r4;	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r4 = new byte[r1];	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r5 = com.RNFetchBlob.RNFetchBlob.RCTContext;	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r5 = r5.getAssets();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r0 = r5.open(r0);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r5 = r0.read(r4, r3, r1);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r0.close();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        goto L_0x0074;
    L_0x003d:
        if (r1 != 0) goto L_0x005b;
    L_0x003f:
        r0 = com.RNFetchBlob.RNFetchBlob.RCTContext;	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r0 = r0.getContentResolver();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r1 = android.net.Uri.parse(r8);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r0 = r0.openInputStream(r1);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r1 = r0.available();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r4 = new byte[r1];	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r5 = r0.read(r4);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r0.close();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        goto L_0x0074;
    L_0x005b:
        r0 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r0.<init>(r8);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r4 = r0.length();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r1 = (int) r4;	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r4 = new byte[r1];	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r5 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r5.<init>(r0);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r0 = r5.read(r4);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r5.close();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r5 = r0;
    L_0x0074:
        if (r5 >= r1) goto L_0x0093;
    L_0x0076:
        r9 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r9.<init>();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r0 = "Read only ";
        r9.append(r0);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r9.append(r5);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r0 = " bytes of ";
        r9.append(r0);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r9.append(r1);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r9 = r9.toString();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r10.reject(r2, r9);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        return;
    L_0x0093:
        r9 = r9.toLowerCase();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r0 = -1;
        r1 = r9.hashCode();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r5 = -1396204209; // 0xffffffffacc79d4f float:-5.673385E-12 double:NaN;
        r6 = 2;
        r7 = 1;
        if (r1 == r5) goto L_0x00c2;
    L_0x00a3:
        r5 = 3600241; // 0x36ef71 float:5.045012E-39 double:1.7787554E-317;
        if (r1 == r5) goto L_0x00b8;
    L_0x00a8:
        r5 = 93106001; // 0x58caf51 float:1.3229938E-35 double:4.60004765E-316;
        if (r1 == r5) goto L_0x00ae;
    L_0x00ad:
        goto L_0x00cb;
    L_0x00ae:
        r1 = "ascii";
        r9 = r9.equals(r1);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        if (r9 == 0) goto L_0x00cb;
    L_0x00b6:
        r0 = 1;
        goto L_0x00cb;
    L_0x00b8:
        r1 = "utf8";
        r9 = r9.equals(r1);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        if (r9 == 0) goto L_0x00cb;
    L_0x00c0:
        r0 = 2;
        goto L_0x00cb;
    L_0x00c2:
        r1 = "base64";
        r9 = r9.equals(r1);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        if (r9 == 0) goto L_0x00cb;
    L_0x00ca:
        r0 = 0;
    L_0x00cb:
        if (r0 == 0) goto L_0x00f7;
    L_0x00cd:
        if (r0 == r7) goto L_0x00e4;
    L_0x00cf:
        if (r0 == r6) goto L_0x00db;
    L_0x00d1:
        r9 = new java.lang.String;	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r9.<init>(r4);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r10.resolve(r9);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        goto L_0x0150;
    L_0x00db:
        r9 = new java.lang.String;	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r9.<init>(r4);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r10.resolve(r9);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        goto L_0x0150;
    L_0x00e4:
        r9 = com.facebook.react.bridge.Arguments.createArray();	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r0 = r4.length;	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
    L_0x00e9:
        if (r3 >= r0) goto L_0x00f3;
    L_0x00eb:
        r1 = r4[r3];	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r9.pushInt(r1);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r3 = r3 + 1;
        goto L_0x00e9;
    L_0x00f3:
        r10.resolve(r9);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        goto L_0x0150;
    L_0x00f7:
        r9 = android.util.Base64.encodeToString(r4, r6);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        r10.resolve(r9);	 Catch:{ FileNotFoundException -> 0x0108, Exception -> 0x00ff }
        goto L_0x0150;
    L_0x00ff:
        r8 = move-exception;
        r8 = r8.getLocalizedMessage();
        r10.reject(r2, r8);
        goto L_0x0150;
    L_0x0108:
        r9 = move-exception;
        r9 = r9.getLocalizedMessage();
        r0 = "EISDIR";
        r1 = r9.contains(r0);
        if (r1 == 0) goto L_0x0132;
    L_0x0115:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Expecting a file but '";
        r1.append(r2);
        r1.append(r8);
        r8 = "' is a directory; ";
        r1.append(r8);
        r1.append(r9);
        r8 = r1.toString();
        r10.reject(r0, r8);
        goto L_0x0150;
    L_0x0132:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "No such file '";
        r0.append(r1);
        r0.append(r8);
        r8 = "'; ";
        r0.append(r8);
        r0.append(r9);
        r8 = r0.toString();
        r9 = "ENOENT";
        r10.reject(r9, r8);
    L_0x0150:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobFS.readFile(java.lang.String, java.lang.String, com.facebook.react.bridge.Promise):void");
    }

    static Map<String, Object> getSystemfolders(ReactApplicationContext reactApplicationContext) {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("DocumentDir", reactApplicationContext.getFilesDir().getAbsolutePath());
        hashMap.put("CacheDir", reactApplicationContext.getCacheDir().getAbsolutePath());
        hashMap.put("DCIMDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
        hashMap.put("PictureDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        hashMap.put("MusicDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());
        hashMap.put("DownloadDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        hashMap.put("MovieDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath());
        hashMap.put("RingtoneDir", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getAbsolutePath());
        if (Environment.getExternalStorageState().equals("mounted")) {
            hashMap.put("SDCardDir", Environment.getExternalStorageDirectory().getAbsolutePath());
            File externalFilesDir = reactApplicationContext.getExternalFilesDir(null);
            String str = "SDCardApplicationDir";
            if (externalFilesDir != null) {
                hashMap.put(str, externalFilesDir.getParentFile().getAbsolutePath());
            } else {
                hashMap.put(str, "");
            }
        }
        hashMap.put("MainBundleDir", reactApplicationContext.getApplicationInfo().dataDir);
        return hashMap;
    }

    public static void getSDCardDir(Promise promise) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            promise.resolve(Environment.getExternalStorageDirectory().getAbsolutePath());
        } else {
            promise.reject("RNFetchBlob.getSDCardDir", "External storage not mounted");
        }
    }

    public static void getSDCardApplicationDir(ReactApplicationContext reactApplicationContext, Promise promise) {
        String str = "RNFetchBlob.getSDCardApplicationDir";
        if (Environment.getExternalStorageState().equals("mounted")) {
            try {
                promise.resolve(reactApplicationContext.getExternalFilesDir(null).getParentFile().getAbsolutePath());
                return;
            } catch (Exception e) {
                promise.reject(str, e.getLocalizedMessage());
                return;
            }
        }
        promise.reject(str, "External storage not mounted");
    }

    static String getTmpPath(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(RNFetchBlob.RCTContext.getFilesDir());
        stringBuilder.append("/RNFetchBlobTmp_");
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x010e A:{Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0094 A:{Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0069 A:{SYNTHETIC, Splitter: B:23:0x0069} */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x010e A:{Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }} */
    void readStream(java.lang.String r17, java.lang.String r18, int r19, int r20, java.lang.String r21) {
        /*
        r16 = this;
        r1 = r16;
        r2 = r18;
        r0 = r20;
        r3 = r21;
        r4 = "bundle-assets://";
        r5 = "base64";
        r6 = "error";
        r7 = normalizePath(r17);
        if (r7 == 0) goto L_0x0016;
    L_0x0014:
        r8 = r7;
        goto L_0x0018;
    L_0x0016:
        r8 = r17;
    L_0x0018:
        r9 = r2.equalsIgnoreCase(r5);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        if (r9 == 0) goto L_0x0021;
    L_0x001e:
        r9 = 4095; // 0xfff float:5.738E-42 double:2.023E-320;
        goto L_0x0023;
    L_0x0021:
        r9 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
    L_0x0023:
        if (r19 <= 0) goto L_0x0027;
    L_0x0025:
        r9 = r19;
    L_0x0027:
        r10 = "";
        if (r7 == 0) goto L_0x0040;
    L_0x002b:
        r11 = r8.startsWith(r4);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        if (r11 == 0) goto L_0x0040;
    L_0x0031:
        r7 = com.RNFetchBlob.RNFetchBlob.RCTContext;	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r7 = r7.getAssets();	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r4 = r8.replace(r4, r10);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r4 = r7.open(r4);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        goto L_0x005b;
    L_0x0040:
        if (r7 != 0) goto L_0x0051;
    L_0x0042:
        r4 = com.RNFetchBlob.RNFetchBlob.RCTContext;	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r4 = r4.getContentResolver();	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r7 = android.net.Uri.parse(r8);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r4 = r4.openInputStream(r7);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        goto L_0x005b;
    L_0x0051:
        r4 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r7 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r7.<init>(r8);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r4.<init>(r7);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
    L_0x005b:
        r7 = new byte[r9];	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r11 = "utf8";
        r11 = r2.equalsIgnoreCase(r11);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r12 = -1;
        r13 = "data";
        r14 = 0;
        if (r11 == 0) goto L_0x0094;
    L_0x0069:
        r5 = "UTF-8";
        r5 = java.nio.charset.Charset.forName(r5);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r5 = r5.newEncoder();	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
    L_0x0073:
        r9 = r4.read(r7);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        if (r9 == r12) goto L_0x00bb;
    L_0x0079:
        r11 = java.nio.ByteBuffer.wrap(r7);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r11 = r11.asCharBuffer();	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r5.encode(r11);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r11 = new java.lang.String;	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r11.<init>(r7, r14, r9);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r1.emitStreamEvent(r3, r13, r11);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        if (r0 <= 0) goto L_0x0092;
    L_0x008e:
        r14 = (long) r0;	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        android.os.SystemClock.sleep(r14);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
    L_0x0092:
        r14 = 0;
        goto L_0x0073;
    L_0x0094:
        r11 = "ascii";
        r11 = r2.equalsIgnoreCase(r11);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        if (r11 == 0) goto L_0x00bd;
    L_0x009c:
        r5 = r4.read(r7);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        if (r5 == r12) goto L_0x00bb;
    L_0x00a2:
        r9 = com.facebook.react.bridge.Arguments.createArray();	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r11 = 0;
    L_0x00a7:
        if (r11 >= r5) goto L_0x00b1;
    L_0x00a9:
        r14 = r7[r11];	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r9.pushInt(r14);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r11 = r11 + 1;
        goto L_0x00a7;
    L_0x00b1:
        r1.emitStreamEvent(r3, r13, r9);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        if (r0 <= 0) goto L_0x009c;
    L_0x00b6:
        r14 = (long) r0;	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        android.os.SystemClock.sleep(r14);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        goto L_0x009c;
    L_0x00bb:
        r15 = 0;
        goto L_0x00ee;
    L_0x00bd:
        r5 = r2.equalsIgnoreCase(r5);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        if (r5 == 0) goto L_0x00f0;
    L_0x00c3:
        r5 = r4.read(r7);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        if (r5 == r12) goto L_0x00bb;
    L_0x00c9:
        r11 = 2;
        if (r5 >= r9) goto L_0x00da;
    L_0x00cc:
        r14 = new byte[r5];	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r15 = 0;
        java.lang.System.arraycopy(r7, r15, r14, r15, r5);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r5 = android.util.Base64.encodeToString(r14, r11);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r1.emitStreamEvent(r3, r13, r5);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        goto L_0x00e2;
    L_0x00da:
        r15 = 0;
        r5 = android.util.Base64.encodeToString(r7, r11);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r1.emitStreamEvent(r3, r13, r5);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
    L_0x00e2:
        if (r0 <= 0) goto L_0x00c3;
    L_0x00e4:
        r19 = r13;
        r12 = (long) r0;	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        android.os.SystemClock.sleep(r12);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r13 = r19;
        r12 = -1;
        goto L_0x00c3;
    L_0x00ee:
        r14 = 0;
        goto L_0x010c;
    L_0x00f0:
        r0 = "EINVAL";
        r5 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r5.<init>();	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r7 = "Unrecognized encoding `";
        r5.append(r7);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r5.append(r2);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r7 = "`, should be one of `base64`, `utf8`, `ascii`";
        r5.append(r7);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r5 = r5.toString();	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r1.emitStreamEvent(r3, r6, r0, r5);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        r14 = 1;
    L_0x010c:
        if (r14 != 0) goto L_0x0113;
    L_0x010e:
        r0 = "end";
        r1.emitStreamEvent(r3, r0, r10);	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
    L_0x0113:
        r4.close();	 Catch:{ FileNotFoundException -> 0x0137, Exception -> 0x0117 }
        goto L_0x0152;
    L_0x0117:
        r0 = move-exception;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Failed to convert data to ";
        r4.append(r5);
        r4.append(r2);
        r2 = " encoded string. This might be because this encoding cannot be used for this data.";
        r4.append(r2);
        r2 = r4.toString();
        r4 = "EUNSPECIFIED";
        r1.emitStreamEvent(r3, r6, r4, r2);
        r0.printStackTrace();
        goto L_0x0152;
    L_0x0137:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r2 = "No such file '";
        r0.append(r2);
        r0.append(r8);
        r2 = "'";
        r0.append(r2);
        r0 = r0.toString();
        r2 = "ENOENT";
        r1.emitStreamEvent(r3, r6, r2, r0);
    L_0x0152:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobFS.readStream(java.lang.String, java.lang.String, int, int, java.lang.String):void");
    }

    void writeStream(String str, String str2, boolean z, Callback callback) {
        try {
            File file = new File(str);
            File parentFile = file.getParentFile();
            Object[] objArr;
            StringBuilder stringBuilder;
            if (file.exists()) {
                if (file.isDirectory()) {
                    objArr = new Object[2];
                    objArr[0] = "EISDIR";
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Expecting a file but '");
                    stringBuilder.append(str);
                    stringBuilder.append("' is a directory");
                    objArr[1] = stringBuilder.toString();
                    callback.invoke(objArr);
                    return;
                }
            } else if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                objArr = new Object[2];
                objArr[0] = "ENOTDIR";
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to create parent directory of '");
                stringBuilder.append(str);
                stringBuilder.append("'");
                objArr[1] = stringBuilder.toString();
                callback.invoke(objArr);
                return;
            } else if (!file.createNewFile()) {
                objArr = new Object[2];
                objArr[0] = "ENOENT";
                stringBuilder = new StringBuilder();
                stringBuilder.append("File '");
                stringBuilder.append(str);
                stringBuilder.append("' does not exist and could not be created");
                objArr[1] = stringBuilder.toString();
                callback.invoke(objArr);
                return;
            }
            OutputStream fileOutputStream = new FileOutputStream(str, z);
            this.encoding = str2;
            fileStreams.put(UUID.randomUUID().toString(), this);
            this.writeStreamInstance = fileOutputStream;
            callback.invoke(null, null, str2);
        } catch (Exception e) {
            Object[] objArr2 = new Object[2];
            objArr2[0] = "EUNSPECIFIED";
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Failed to create write stream at path `");
            stringBuilder2.append(str);
            stringBuilder2.append("`; ");
            stringBuilder2.append(e.getLocalizedMessage());
            objArr2[1] = stringBuilder2.toString();
            callback.invoke(objArr2);
        }
    }

    static void writeChunk(String str, String str2, Callback callback) {
        RNFetchBlobFS rNFetchBlobFS = (RNFetchBlobFS) fileStreams.get(str);
        try {
            rNFetchBlobFS.writeStreamInstance.write(stringToBytes(str2, rNFetchBlobFS.encoding));
            callback.invoke(new Object[0]);
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage());
        }
    }

    static void writeArrayChunk(String str, ReadableArray readableArray, Callback callback) {
        try {
            OutputStream outputStream = ((RNFetchBlobFS) fileStreams.get(str)).writeStreamInstance;
            byte[] bArr = new byte[readableArray.size()];
            for (int i = 0; i < readableArray.size(); i++) {
                bArr[i] = (byte) readableArray.getInt(i);
            }
            outputStream.write(bArr);
            callback.invoke(new Object[0]);
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage());
        }
    }

    static void closeStream(String str, Callback callback) {
        try {
            OutputStream outputStream = ((RNFetchBlobFS) fileStreams.get(str)).writeStreamInstance;
            fileStreams.remove(str);
            outputStream.close();
            callback.invoke(new Object[0]);
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage());
        }
    }

    static void unlink(String str, Callback callback) {
        try {
            deleteRecursive(new File(normalizePath(str)));
            callback.invoke(null, Boolean.valueOf(true));
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage(), Boolean.valueOf(false));
        }
    }

    private static void deleteRecursive(File file) throws IOException {
        StringBuilder stringBuilder;
        String str = "'";
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File deleteRecursive : listFiles) {
                    deleteRecursive(deleteRecursive);
                }
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Received null trying to list files of directory '");
                stringBuilder.append(file);
                stringBuilder.append(str);
                throw new NullPointerException(stringBuilder.toString());
            }
        }
        if (!file.delete()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to delete '");
            stringBuilder.append(file);
            stringBuilder.append(str);
            throw new IOException(stringBuilder.toString());
        }
    }

    static void mkdir(String str, Promise promise) {
        String str2 = "EUNSPECIFIED";
        File file = new File(str);
        if (file.exists()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(file.isDirectory() ? "Folder" : "File");
            stringBuilder.append(" '");
            stringBuilder.append(str);
            stringBuilder.append("' already exists");
            promise.reject("EEXIST", stringBuilder.toString());
            return;
        }
        try {
            if (file.mkdirs()) {
                promise.resolve(Boolean.valueOf(true));
                return;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("mkdir failed to create some or all directories in '");
            stringBuilder2.append(str);
            stringBuilder2.append("'");
            promise.reject(str2, stringBuilder2.toString());
        } catch (Exception e) {
            promise.reject(str2, e.getLocalizedMessage());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f4 A:{SYNTHETIC, Splitter: B:56:0x00f4} */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00fc A:{Catch:{ Exception -> 0x00f8 }} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c2 A:{SYNTHETIC, Splitter: B:44:0x00c2} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ca A:{Catch:{ Exception -> 0x00c6 }} */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f4 A:{SYNTHETIC, Splitter: B:56:0x00f4} */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00fc A:{Catch:{ Exception -> 0x00f8 }} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c2 A:{SYNTHETIC, Splitter: B:44:0x00c2} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ca A:{Catch:{ Exception -> 0x00c6 }} */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f4 A:{SYNTHETIC, Splitter: B:56:0x00f4} */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00fc A:{Catch:{ Exception -> 0x00f8 }} */
    static void cp(java.lang.String r6, java.lang.String r7, com.facebook.react.bridge.Callback r8) {
        /*
        r0 = "";
        r6 = normalizePath(r6);
        r1 = 0;
        r2 = 1;
        r3 = 0;
        r4 = isPathExists(r6);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        if (r4 != 0) goto L_0x002d;
    L_0x000f:
        r7 = new java.lang.Object[r2];	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r4.<init>();	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r5 = "Source file at path`";
        r4.append(r5);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r4.append(r6);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r6 = "` does not exist";
        r4.append(r6);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r6 = r4.toString();	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r7[r3] = r6;	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r8.invoke(r7);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        return;
    L_0x002d:
        r4 = new java.io.File;	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r4.<init>(r7);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r4 = r4.exists();	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        if (r4 != 0) goto L_0x0061;
    L_0x0038:
        r4 = new java.io.File;	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r4.<init>(r7);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r4 = r4.createNewFile();	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        if (r4 != 0) goto L_0x0061;
    L_0x0043:
        r6 = new java.lang.Object[r2];	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r4.<init>();	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r5 = "Destination file at '";
        r4.append(r5);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r4.append(r7);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r7 = "' already exists";
        r4.append(r7);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r7 = r4.toString();	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r6[r3] = r7;	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r8.invoke(r6);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        return;
    L_0x0061:
        r6 = inputStreamFromPath(r6);	 Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
        r4 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x00a3, all -> 0x009e }
        r4.<init>(r7);	 Catch:{ Exception -> 0x00a3, all -> 0x009e }
        r7 = 10240; // 0x2800 float:1.4349E-41 double:5.059E-320;
        r7 = new byte[r7];	 Catch:{ Exception -> 0x009c, all -> 0x009a }
    L_0x006e:
        r1 = r6.read(r7);	 Catch:{ Exception -> 0x009c, all -> 0x009a }
        if (r1 <= 0) goto L_0x0078;
    L_0x0074:
        r4.write(r7, r3, r1);	 Catch:{ Exception -> 0x009c, all -> 0x009a }
        goto L_0x006e;
    L_0x0078:
        if (r6 == 0) goto L_0x0080;
    L_0x007a:
        r6.close();	 Catch:{ Exception -> 0x007e }
        goto L_0x0080;
    L_0x007e:
        r6 = move-exception;
        goto L_0x0086;
    L_0x0080:
        r4.close();	 Catch:{ Exception -> 0x007e }
        r6 = r0;
        goto L_0x00e1;
    L_0x0086:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r7.append(r0);
        r6 = r6.getLocalizedMessage();
        r7.append(r6);
        r6 = r7.toString();
        goto L_0x00e1;
    L_0x009a:
        r7 = move-exception;
        goto L_0x00a0;
    L_0x009c:
        r7 = move-exception;
        goto L_0x00a5;
    L_0x009e:
        r7 = move-exception;
        r4 = r1;
    L_0x00a0:
        r1 = r6;
        r6 = r7;
        goto L_0x00f2;
    L_0x00a3:
        r7 = move-exception;
        r4 = r1;
    L_0x00a5:
        r1 = r6;
        r6 = r7;
        goto L_0x00ad;
    L_0x00a8:
        r6 = move-exception;
        r4 = r1;
        goto L_0x00f2;
    L_0x00ab:
        r6 = move-exception;
        r4 = r1;
    L_0x00ad:
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f1 }
        r7.<init>();	 Catch:{ all -> 0x00f1 }
        r7.append(r0);	 Catch:{ all -> 0x00f1 }
        r6 = r6.getLocalizedMessage();	 Catch:{ all -> 0x00f1 }
        r7.append(r6);	 Catch:{ all -> 0x00f1 }
        r6 = r7.toString();	 Catch:{ all -> 0x00f1 }
        if (r1 == 0) goto L_0x00c8;
    L_0x00c2:
        r1.close();	 Catch:{ Exception -> 0x00c6 }
        goto L_0x00c8;
    L_0x00c6:
        r7 = move-exception;
        goto L_0x00ce;
    L_0x00c8:
        if (r4 == 0) goto L_0x00e1;
    L_0x00ca:
        r4.close();	 Catch:{ Exception -> 0x00c6 }
        goto L_0x00e1;
    L_0x00ce:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r1.append(r6);
        r6 = r7.getLocalizedMessage();
        r1.append(r6);
        r6 = r1.toString();
    L_0x00e1:
        if (r6 == r0) goto L_0x00eb;
    L_0x00e3:
        r7 = new java.lang.Object[r2];
        r7[r3] = r6;
        r8.invoke(r7);
        goto L_0x00f0;
    L_0x00eb:
        r6 = new java.lang.Object[r3];
        r8.invoke(r6);
    L_0x00f0:
        return;
    L_0x00f1:
        r6 = move-exception;
    L_0x00f2:
        if (r1 == 0) goto L_0x00fa;
    L_0x00f4:
        r1.close();	 Catch:{ Exception -> 0x00f8 }
        goto L_0x00fa;
    L_0x00f8:
        r7 = move-exception;
        goto L_0x0100;
    L_0x00fa:
        if (r4 == 0) goto L_0x0112;
    L_0x00fc:
        r4.close();	 Catch:{ Exception -> 0x00f8 }
        goto L_0x0112;
    L_0x0100:
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r8.append(r0);
        r7 = r7.getLocalizedMessage();
        r8.append(r7);
        r8.toString();
    L_0x0112:
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobFS.cp(java.lang.String, java.lang.String, com.facebook.react.bridge.Callback):void");
    }

    static void mv(String str, String str2, Callback callback) {
        File file = new File(str);
        if (file.exists()) {
            try {
                InputStream fileInputStream = new FileInputStream(str);
                OutputStream fileOutputStream = new FileOutputStream(str2);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileInputStream.close();
                        fileOutputStream.flush();
                        file.delete();
                        callback.invoke(new Object[0]);
                        return;
                    }
                }
            } catch (FileNotFoundException unused) {
                callback.invoke("Source file not found.");
                return;
            } catch (Exception e) {
                callback.invoke(e.toString());
                return;
            }
        }
        Object[] objArr = new Object[1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Source file at path `");
        stringBuilder.append(str);
        stringBuilder.append("` does not exist");
        objArr[0] = stringBuilder.toString();
        callback.invoke(objArr);
    }

    static void exists(String str, Callback callback) {
        boolean isAsset = isAsset(str);
        Boolean valueOf = Boolean.valueOf(false);
        if (isAsset) {
            try {
                RNFetchBlob.RCTContext.getAssets().openFd(str.replace(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET, ""));
                callback.invoke(Boolean.valueOf(true), valueOf);
                return;
            } catch (IOException unused) {
                callback.invoke(valueOf, valueOf);
                return;
            }
        }
        str = normalizePath(str);
        if (str != null) {
            isAsset = new File(str).exists();
            boolean isDirectory = new File(str).isDirectory();
            callback.invoke(Boolean.valueOf(isAsset), Boolean.valueOf(isDirectory));
            return;
        }
        callback.invoke(valueOf, valueOf);
    }

    static void ls(String str, Promise promise) {
        try {
            str = normalizePath(str);
            File file = new File(str);
            String str2 = "'";
            StringBuilder stringBuilder;
            if (!file.exists()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("No such file '");
                stringBuilder.append(str);
                stringBuilder.append(str2);
                promise.reject("ENOENT", stringBuilder.toString());
            } else if (file.isDirectory()) {
                String[] list = new File(str).list();
                WritableArray createArray = Arguments.createArray();
                for (String pushString : list) {
                    createArray.pushString(pushString);
                }
                promise.resolve(createArray);
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Not a directory '");
                stringBuilder.append(str);
                stringBuilder.append(str2);
                promise.reject("ENOTDIR", stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        }
    }

    static void slice(String str, String str2, int i, int i2, String str3, Promise promise) {
        str3 = "EUNSPECIFIED";
        try {
            str = normalizePath(str);
            File file = new File(str);
            StringBuilder stringBuilder;
            if (file.isDirectory()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Expecting a file but '");
                stringBuilder.append(str);
                stringBuilder.append("' is a directory");
                promise.reject("EISDIR", stringBuilder.toString());
            } else if (file.exists()) {
                int length = (int) file.length();
                i2 = Math.min(length, i2) - i;
                FileInputStream fileInputStream = new FileInputStream(new File(str));
                FileOutputStream fileOutputStream = new FileOutputStream(new File(str2));
                int skip = (int) fileInputStream.skip((long) i);
                if (skip != i) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Skipped ");
                    stringBuilder2.append(skip);
                    stringBuilder2.append(" instead of the specified ");
                    stringBuilder2.append(i);
                    stringBuilder2.append(" bytes, size is ");
                    stringBuilder2.append(length);
                    promise.reject(str3, stringBuilder2.toString());
                    return;
                }
                byte[] bArr = new byte[10240];
                skip = 0;
                while (skip < i2) {
                    int read = fileInputStream.read(bArr, 0, 10240);
                    int i3 = i2 - skip;
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, Math.min(i3, read));
                    skip += read;
                }
                fileInputStream.close();
                fileOutputStream.flush();
                fileOutputStream.close();
                promise.resolve(str2);
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("No such file '");
                stringBuilder.append(str);
                stringBuilder.append("'");
                promise.reject("ENOENT", stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(str3, e.getLocalizedMessage());
        }
    }

    static void lstat(String str, final Callback callback) {
        str = normalizePath(str);
        new AsyncTask<String, Integer, Integer>() {
            protected Integer doInBackground(String... strArr) {
                WritableArray createArray = Arguments.createArray();
                Integer valueOf = Integer.valueOf(0);
                if (strArr[0] == null) {
                    callback.invoke("the path specified for lstat is either `null` or `undefined`.");
                    return valueOf;
                }
                File file = new File(strArr[0]);
                if (file.exists()) {
                    if (file.isDirectory()) {
                        for (String str : file.list()) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(file.getPath());
                            stringBuilder.append("/");
                            stringBuilder.append(str);
                            createArray.pushMap(RNFetchBlobFS.statFile(stringBuilder.toString()));
                        }
                    } else {
                        createArray.pushMap(RNFetchBlobFS.statFile(file.getAbsolutePath()));
                    }
                    callback.invoke(null, createArray);
                    return valueOf;
                }
                Callback callback = callback;
                Object[] objArr = new Object[1];
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("failed to lstat path `");
                stringBuilder2.append(strArr[0]);
                stringBuilder2.append("` because it does not exist or it is not a folder");
                objArr[0] = stringBuilder2.toString();
                callback.invoke(objArr);
                return valueOf;
            }
        }.execute(new String[]{str});
    }

    static void stat(String str, Callback callback) {
        try {
            str = normalizePath(str);
            if (statFile(str) == null) {
                Object[] objArr = new Object[2];
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("failed to stat path `");
                stringBuilder.append(str);
                stringBuilder.append("` because it does not exist or it is not a folder");
                objArr[0] = stringBuilder.toString();
                objArr[1] = null;
                callback.invoke(objArr);
                return;
            }
            callback.invoke(null, statFile(str));
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage());
        }
    }

    static WritableMap statFile(String str) {
        try {
            str = normalizePath(str);
            WritableMap createMap = Arguments.createMap();
            boolean isAsset = isAsset(str);
            String str2 = "lastModified";
            String str3 = "size";
            String str4 = "type";
            String str5 = RNFetchBlobConst.RNFB_RESPONSE_PATH;
            String str6 = "filename";
            if (isAsset) {
                String replace = str.replace(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET, "");
                AssetFileDescriptor openFd = RNFetchBlob.RCTContext.getAssets().openFd(replace);
                createMap.putString(str6, replace);
                createMap.putString(str5, str);
                createMap.putString(str4, UriUtil.LOCAL_ASSET_SCHEME);
                createMap.putString(str3, String.valueOf(openFd.getLength()));
                createMap.putInt(str2, 0);
            } else {
                File file = new File(str);
                if (!file.exists()) {
                    return null;
                }
                createMap.putString(str6, file.getName());
                createMap.putString(str5, file.getPath());
                createMap.putString(str4, file.isDirectory() ? "directory" : UriUtil.LOCAL_FILE_SCHEME);
                createMap.putString(str3, String.valueOf(file.length()));
                createMap.putString(str2, String.valueOf(file.lastModified()));
            }
            return createMap;
        } catch (Exception unused) {
            return null;
        }
    }

    void scanFile(String[] strArr, String[] strArr2, final Callback callback) {
        try {
            MediaScannerConnection.scanFile(this.mCtx, strArr, strArr2, new OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                    callback.invoke(null, Boolean.valueOf(true));
                }
            });
        } catch (Exception e) {
            callback.invoke(e.getLocalizedMessage(), null);
        }
    }

    static void hash(String str, String str2, Promise promise) {
        try {
            Map hashMap = new HashMap();
            hashMap.put("md5", "MD5");
            hashMap.put("sha1", "SHA-1");
            hashMap.put("sha224", "SHA-224");
            hashMap.put("sha256", "SHA-256");
            hashMap.put("sha384", "SHA-384");
            hashMap.put("sha512", "SHA-512");
            StringBuilder stringBuilder;
            if (hashMap.containsKey(str2)) {
                File file = new File(str);
                if (file.isDirectory()) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Expecting a file but '");
                    stringBuilder.append(str);
                    stringBuilder.append("' is a directory");
                    promise.reject("EISDIR", stringBuilder.toString());
                    return;
                } else if (file.exists()) {
                    int read;
                    MessageDigest instance = MessageDigest.getInstance((String) hashMap.get(str2));
                    FileInputStream fileInputStream = new FileInputStream(str);
                    byte[] bArr = new byte[1048576];
                    if (file.length() != 0) {
                        while (true) {
                            read = fileInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            instance.update(bArr, 0, read);
                        }
                    }
                    StringBuilder stringBuilder2 = new StringBuilder();
                    int length = instance.digest().length;
                    for (read = 0; read < length; read++) {
                        stringBuilder2.append(String.format("%02x", new Object[]{Byte.valueOf(r8[read])}));
                    }
                    promise.resolve(stringBuilder2.toString());
                    return;
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("No such file '");
                    stringBuilder.append(str);
                    stringBuilder.append("'");
                    promise.reject("ENOENT", stringBuilder.toString());
                    return;
                }
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid algorithm '");
            stringBuilder.append(str2);
            stringBuilder.append("', must be one of md5, sha1, sha224, sha256, sha384, sha512");
            promise.reject("EINVAL", stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        }
    }

    static void createFile(String str, String str2, String str3, Promise promise) {
        try {
            File file = new File(str);
            boolean createNewFile = file.createNewFile();
            StringBuilder stringBuilder;
            if (str3.equals("uri")) {
                File file2 = new File(str2.replace(RNFetchBlobConst.FILE_PREFIX, ""));
                if (file2.exists()) {
                    FileInputStream fileInputStream = new FileInputStream(file2);
                    OutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] bArr = new byte[10240];
                    for (int read = fileInputStream.read(bArr); read > 0; read = fileInputStream.read(bArr)) {
                        fileOutputStream.write(bArr, 0, read);
                    }
                    fileInputStream.close();
                    fileOutputStream.close();
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Source file : ");
                    stringBuilder.append(str2);
                    stringBuilder.append(" does not exist");
                    promise.reject("ENOENT", stringBuilder.toString());
                    return;
                }
            } else if (createNewFile) {
                new FileOutputStream(file).write(stringToBytes(str2, str3));
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("File `");
                stringBuilder.append(str);
                stringBuilder.append("` already exists");
                promise.reject("EEXIST", stringBuilder.toString());
                return;
            }
            promise.resolve(str);
        } catch (Exception e) {
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        }
    }

    static void createFileASCII(String str, ReadableArray readableArray, Promise promise) {
        try {
            File file = new File(str);
            if (file.createNewFile()) {
                OutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[readableArray.size()];
                for (int i = 0; i < readableArray.size(); i++) {
                    bArr[i] = (byte) readableArray.getInt(i);
                }
                fileOutputStream.write(bArr);
                promise.resolve(str);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("File at path `");
            stringBuilder.append(str);
            stringBuilder.append("` already exists");
            promise.reject("EEXIST", stringBuilder.toString());
        } catch (Exception e) {
            promise.reject("EUNSPECIFIED", e.getLocalizedMessage());
        }
    }

    static void df(Callback callback) {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        WritableMap createMap = Arguments.createMap();
        if (VERSION.SDK_INT >= 18) {
            createMap.putString("internal_free", String.valueOf(statFs.getFreeBytes()));
            createMap.putString("internal_total", String.valueOf(statFs.getTotalBytes()));
            statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            createMap.putString("external_free", String.valueOf(statFs.getFreeBytes()));
            createMap.putString("external_total", String.valueOf(statFs.getTotalBytes()));
        }
        callback.invoke(null, createMap);
    }

    static void removeSession(ReadableArray readableArray, final Callback callback) {
        new AsyncTask<ReadableArray, Integer, Integer>() {
            protected Integer doInBackground(ReadableArray... readableArrayArr) {
                try {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < readableArrayArr[0].size(); i++) {
                        String string = readableArrayArr[0].getString(i);
                        File file = new File(string);
                        if (file.exists() && !file.delete()) {
                            arrayList.add(string);
                        }
                    }
                    if (arrayList.isEmpty()) {
                        callback.invoke(null, Boolean.valueOf(true));
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Failed to delete: ");
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            stringBuilder.append((String) it.next());
                            stringBuilder.append(", ");
                        }
                        callback.invoke(stringBuilder.toString());
                    }
                } catch (Exception e) {
                    callback.invoke(e.getLocalizedMessage());
                }
                return Integer.valueOf(readableArrayArr[0].size());
            }
        }.execute(new ReadableArray[]{readableArray});
    }

    private static byte[] stringToBytes(String str, String str2) {
        String str3 = "US-ASCII";
        if (str2.equalsIgnoreCase("ascii")) {
            return str.getBytes(Charset.forName(str3));
        }
        if (str2.toLowerCase().contains(RNFetchBlobConst.RNFB_RESPONSE_BASE64)) {
            return Base64.decode(str, 2);
        }
        if (str2.equalsIgnoreCase(RNFetchBlobConst.RNFB_RESPONSE_UTF8)) {
            return str.getBytes(Charset.forName(Key.STRING_CHARSET_NAME));
        }
        return str.getBytes(Charset.forName(str3));
    }

    private void emitStreamEvent(String str, String str2, String str3) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(NotificationCompat.CATEGORY_EVENT, str2);
        createMap.putString("detail", str3);
        this.emitter.emit(str, createMap);
    }

    private void emitStreamEvent(String str, String str2, WritableArray writableArray) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(NotificationCompat.CATEGORY_EVENT, str2);
        createMap.putArray("detail", writableArray);
        this.emitter.emit(str, createMap);
    }

    private void emitStreamEvent(String str, String str2, String str3, String str4) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(NotificationCompat.CATEGORY_EVENT, str2);
        createMap.putString("code", str3);
        createMap.putString("detail", str4);
        this.emitter.emit(str, createMap);
    }

    private static InputStream inputStreamFromPath(String str) throws IOException {
        CharSequence charSequence = RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET;
        if (str.startsWith(charSequence)) {
            return RNFetchBlob.RCTContext.getAssets().open(str.replace(charSequence, ""));
        }
        return new FileInputStream(new File(str));
    }

    private static boolean isPathExists(String str) {
        CharSequence charSequence = RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET;
        if (!str.startsWith(charSequence)) {
            return new File(str).exists();
        }
        try {
            RNFetchBlob.RCTContext.getAssets().open(str.replace(charSequence, ""));
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    static boolean isAsset(String str) {
        return str != null && str.startsWith(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET);
    }

    static String normalizePath(String str) {
        if (str == null) {
            return null;
        }
        if (!str.matches("\\w+\\:.*")) {
            return str;
        }
        CharSequence charSequence = "file://";
        if (str.startsWith(charSequence)) {
            return str.replace(charSequence, "");
        }
        Uri parse = Uri.parse(str);
        if (str.startsWith(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET)) {
            return str;
        }
        return PathResolver.getRealPathFromURI(RNFetchBlob.RCTContext, parse);
    }
}
