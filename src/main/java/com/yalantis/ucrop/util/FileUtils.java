package com.yalantis.ucrop.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.common.util.UriUtil;

public class FileUtils {
    private static final String TAG = "FileUtils";

    private FileUtils() {
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0056  */
    /* JADX WARNING: Missing block: B:12:0x002b, code:
            if (r8 != null) goto L_0x002d;
     */
    /* JADX WARNING: Missing block: B:13:0x002d, code:
            r8.close();
     */
    /* JADX WARNING: Missing block: B:20:0x004f, code:
            if (r8 != null) goto L_0x002d;
     */
    /* JADX WARNING: Missing block: B:21:0x0052, code:
            return null;
     */
    public static java.lang.String getDataColumn(android.content.Context r8, android.net.Uri r9, java.lang.String r10, java.lang.String[] r11) {
        /*
        r0 = "_data";
        r3 = new java.lang.String[]{r0};
        r7 = 0;
        r1 = r8.getContentResolver();	 Catch:{ IllegalArgumentException -> 0x0034, all -> 0x0031 }
        r6 = 0;
        r2 = r9;
        r4 = r10;
        r5 = r11;
        r8 = r1.query(r2, r3, r4, r5, r6);	 Catch:{ IllegalArgumentException -> 0x0034, all -> 0x0031 }
        if (r8 == 0) goto L_0x002b;
    L_0x0015:
        r9 = r8.moveToFirst();	 Catch:{ IllegalArgumentException -> 0x0029 }
        if (r9 == 0) goto L_0x002b;
    L_0x001b:
        r9 = r8.getColumnIndexOrThrow(r0);	 Catch:{ IllegalArgumentException -> 0x0029 }
        r9 = r8.getString(r9);	 Catch:{ IllegalArgumentException -> 0x0029 }
        if (r8 == 0) goto L_0x0028;
    L_0x0025:
        r8.close();
    L_0x0028:
        return r9;
    L_0x0029:
        r9 = move-exception;
        goto L_0x0036;
    L_0x002b:
        if (r8 == 0) goto L_0x0052;
    L_0x002d:
        r8.close();
        goto L_0x0052;
    L_0x0031:
        r9 = move-exception;
        r8 = r7;
        goto L_0x0054;
    L_0x0034:
        r9 = move-exception;
        r8 = r7;
    L_0x0036:
        r10 = "FileUtils";
        r11 = java.util.Locale.getDefault();	 Catch:{ all -> 0x0053 }
        r0 = "getDataColumn: _data - [%s]";
        r1 = 1;
        r1 = new java.lang.Object[r1];	 Catch:{ all -> 0x0053 }
        r2 = 0;
        r9 = r9.getMessage();	 Catch:{ all -> 0x0053 }
        r1[r2] = r9;	 Catch:{ all -> 0x0053 }
        r9 = java.lang.String.format(r11, r0, r1);	 Catch:{ all -> 0x0053 }
        android.util.Log.i(r10, r9);	 Catch:{ all -> 0x0053 }
        if (r8 == 0) goto L_0x0052;
    L_0x0051:
        goto L_0x002d;
    L_0x0052:
        return r7;
    L_0x0053:
        r9 = move-exception;
    L_0x0054:
        if (r8 == 0) goto L_0x0059;
    L_0x0056:
        r8.close();
    L_0x0059:
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yalantis.ucrop.util.FileUtils.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    @SuppressLint({"NewApi"})
    public static String getPath(Context context, Uri uri) {
        Uri uri2 = null;
        if ((VERSION.SDK_INT >= 19 ? 1 : null) == null || !DocumentsContract.isDocumentUri(context, uri)) {
            if (!"content".equalsIgnoreCase(uri.getScheme())) {
                if (UriUtil.LOCAL_FILE_SCHEME.equalsIgnoreCase(uri.getScheme())) {
                    return uri.getPath();
                }
            } else if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            } else {
                return getDataColumn(context, uri, null, null);
            }
        }
        String str = ":";
        if (isExternalStorageDocument(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(str);
            if ("primary".equalsIgnoreCase(split[0])) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Environment.getExternalStorageDirectory());
                stringBuilder.append("/");
                stringBuilder.append(split[1]);
                return stringBuilder.toString();
            }
        } else if (isDownloadsDocument(uri)) {
            Object documentId = DocumentsContract.getDocumentId(uri);
            if (!TextUtils.isEmpty(documentId)) {
                try {
                    return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId).longValue()), null, null);
                } catch (NumberFormatException e) {
                    Log.i(TAG, e.getMessage());
                    return null;
                }
            }
        } else if (isMediaDocument(uri)) {
            Object obj = DocumentsContract.getDocumentId(uri).split(str)[0];
            if ("image".equals(obj)) {
                uri2 = Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(obj)) {
                uri2 = Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(obj)) {
                uri2 = Audio.Media.EXTERNAL_CONTENT_URI;
            }
            return getDataColumn(context, uri2, "_id=?", new String[]{r6[1]});
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0051  */
    public static void copyFile(@androidx.annotation.NonNull java.lang.String r10, @androidx.annotation.NonNull java.lang.String r11) throws java.io.IOException {
        /*
        r0 = r10.equalsIgnoreCase(r11);
        if (r0 == 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r0 = 0;
        r1 = new java.io.FileInputStream;	 Catch:{ all -> 0x0048 }
        r2 = new java.io.File;	 Catch:{ all -> 0x0048 }
        r2.<init>(r10);	 Catch:{ all -> 0x0048 }
        r1.<init>(r2);	 Catch:{ all -> 0x0048 }
        r10 = r1.getChannel();	 Catch:{ all -> 0x0048 }
        r1 = new java.io.FileOutputStream;	 Catch:{ all -> 0x0042 }
        r2 = new java.io.File;	 Catch:{ all -> 0x0042 }
        r2.<init>(r11);	 Catch:{ all -> 0x0042 }
        r1.<init>(r2);	 Catch:{ all -> 0x0042 }
        r11 = r1.getChannel();	 Catch:{ all -> 0x0042 }
        r4 = 0;
        r6 = r10.size();	 Catch:{ all -> 0x003d }
        r3 = r10;
        r8 = r11;
        r3.transferTo(r4, r6, r8);	 Catch:{ all -> 0x003d }
        r10.close();	 Catch:{ all -> 0x003d }
        if (r10 == 0) goto L_0x0037;
    L_0x0034:
        r10.close();
    L_0x0037:
        if (r11 == 0) goto L_0x003c;
    L_0x0039:
        r11.close();
    L_0x003c:
        return;
    L_0x003d:
        r0 = move-exception;
        r9 = r0;
        r0 = r10;
        r10 = r9;
        goto L_0x004a;
    L_0x0042:
        r11 = move-exception;
        r9 = r0;
        r0 = r10;
        r10 = r11;
        r11 = r9;
        goto L_0x004a;
    L_0x0048:
        r10 = move-exception;
        r11 = r0;
    L_0x004a:
        if (r0 == 0) goto L_0x004f;
    L_0x004c:
        r0.close();
    L_0x004f:
        if (r11 == 0) goto L_0x0054;
    L_0x0051:
        r11.close();
    L_0x0054:
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yalantis.ucrop.util.FileUtils.copyFile(java.lang.String, java.lang.String):void");
    }
}
