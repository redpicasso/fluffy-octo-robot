package com.RNFetchBlob.Utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import com.RNFetchBlob.RNFetchBlobUtils;
import com.facebook.common.util.UriUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PathResolver {
    @TargetApi(19)
    public static String getRealPathFromURI(Context context, Uri uri) {
        String str = "content";
        Uri uri2 = null;
        if ((VERSION.SDK_INT >= 19 ? 1 : null) != null && DocumentsContract.isDocumentUri(context, uri)) {
            String str2 = ":";
            String documentId;
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(str2);
                if ("primary".equalsIgnoreCase(split[0])) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(Environment.getExternalStorageDirectory());
                    stringBuilder.append("/");
                    stringBuilder.append(split[1]);
                    return stringBuilder.toString();
                }
            } else if (isDownloadsDocument(uri)) {
                try {
                    documentId = DocumentsContract.getDocumentId(uri);
                    if (documentId == null || !documentId.startsWith("raw:/")) {
                        return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId).longValue()), null, null);
                    }
                    return Uri.parse(documentId).getPath();
                } catch (Exception unused) {
                    return null;
                }
            } else if (isMediaDocument(uri)) {
                Object obj = DocumentsContract.getDocumentId(uri).split(str2)[0];
                if ("image".equals(obj)) {
                    uri2 = Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(obj)) {
                    uri2 = Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(obj)) {
                    uri2 = Audio.Media.EXTERNAL_CONTENT_URI;
                }
                return getDataColumn(context, uri2, "_id=?", new String[]{r7[1]});
            } else if (!str.equalsIgnoreCase(uri.getScheme())) {
                try {
                    InputStream openInputStream = context.getContentResolver().openInputStream(uri);
                    if (openInputStream != null) {
                        documentId = getContentName(context.getContentResolver(), uri);
                        if (documentId != null) {
                            File file = new File(context.getCacheDir(), documentId);
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            byte[] bArr = new byte[1024];
                            while (openInputStream.read(bArr) > 0) {
                                fileOutputStream.write(bArr);
                            }
                            fileOutputStream.close();
                            openInputStream.close();
                            return file.getAbsolutePath();
                        }
                    }
                } catch (Exception e) {
                    RNFetchBlobUtils.emitWarningEvent(e.toString());
                    return null;
                }
            } else if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            } else {
                return getDataColumn(context, uri, null, null);
            }
        } else if (!str.equalsIgnoreCase(uri.getScheme())) {
            if (UriUtil.LOCAL_FILE_SCHEME.equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else if (isGooglePhotosUri(uri)) {
            return uri.getLastPathSegment();
        } else {
            return getDataColumn(context, uri, null, null);
        }
        return null;
    }

    private static String getContentName(ContentResolver contentResolver, Uri uri) {
        Cursor query = contentResolver.query(uri, null, null, null, null);
        query.moveToFirst();
        int columnIndex = query.getColumnIndex("_display_name");
        if (columnIndex < 0) {
            return null;
        }
        String string = query.getString(columnIndex);
        query.close();
        return string;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0036  */
    public static java.lang.String getDataColumn(android.content.Context r8, android.net.Uri r9, java.lang.String r10, java.lang.String[] r11) {
        /*
        r0 = "_data";
        r3 = new java.lang.String[]{r0};
        r7 = 0;
        r1 = r8.getContentResolver();	 Catch:{ Exception -> 0x002f, all -> 0x002c }
        r6 = 0;
        r2 = r9;
        r4 = r10;
        r5 = r11;
        r8 = r1.query(r2, r3, r4, r5, r6);	 Catch:{ Exception -> 0x002f, all -> 0x002c }
        if (r8 == 0) goto L_0x0026;
    L_0x0015:
        r9 = r8.moveToFirst();	 Catch:{ Exception -> 0x0024 }
        if (r9 == 0) goto L_0x0026;
    L_0x001b:
        r9 = r8.getColumnIndexOrThrow(r0);	 Catch:{ Exception -> 0x0024 }
        r7 = r8.getString(r9);	 Catch:{ Exception -> 0x0024 }
        goto L_0x0026;
    L_0x0024:
        r9 = move-exception;
        goto L_0x0031;
    L_0x0026:
        if (r8 == 0) goto L_0x002b;
    L_0x0028:
        r8.close();
    L_0x002b:
        return r7;
    L_0x002c:
        r9 = move-exception;
        r8 = r7;
        goto L_0x003b;
    L_0x002f:
        r9 = move-exception;
        r8 = r7;
    L_0x0031:
        r9.printStackTrace();	 Catch:{ all -> 0x003a }
        if (r8 == 0) goto L_0x0039;
    L_0x0036:
        r8.close();
    L_0x0039:
        return r7;
    L_0x003a:
        r9 = move-exception;
    L_0x003b:
        if (r8 == 0) goto L_0x0040;
    L_0x003d:
        r8.close();
    L_0x0040:
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.Utils.PathResolver.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
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
}
