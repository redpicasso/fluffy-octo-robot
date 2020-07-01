package com.reactnative.ivpusic.imagepicker;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import com.facebook.common.util.UriUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class RealPathUtil {
    RealPathUtil() {
    }

    @TargetApi(19)
    static String getRealPathFromURI(Context context, Uri uri) throws IOException {
        Uri uri2 = null;
        if ((VERSION.SDK_INT == 19 ? 1 : null) == null || !DocumentsContract.isDocumentUri(context, uri)) {
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
            String documentId = DocumentsContract.getDocumentId(uri);
            String[] split = documentId.split(str);
            String str2 = "/";
            if ("primary".equalsIgnoreCase(split[0])) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Environment.getExternalStorageDirectory());
                stringBuilder.append(str2);
                stringBuilder.append(split[1]);
                return stringBuilder.toString();
            }
            int indexOf = documentId.indexOf(58, 1);
            String substring = documentId.substring(0, indexOf);
            documentId = documentId.substring(indexOf + 1);
            String pathToNonPrimaryVolume = getPathToNonPrimaryVolume(context, substring);
            if (pathToNonPrimaryVolume != null) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(pathToNonPrimaryVolume);
                stringBuilder2.append(str2);
                stringBuilder2.append(documentId);
                pathToNonPrimaryVolume = stringBuilder2.toString();
                File file = new File(pathToNonPrimaryVolume);
                if (file.exists() && file.canRead()) {
                    return pathToNonPrimaryVolume;
                }
                return null;
            }
        } else if (isDownloadsDocument(uri)) {
            return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
        } else if (isMediaDocument(uri)) {
            Object obj = DocumentsContract.getDocumentId(uri).split(str)[0];
            if ("image".equals(obj)) {
                uri2 = Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(obj)) {
                uri2 = Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(obj)) {
                uri2 = Audio.Media.EXTERNAL_CONTENT_URI;
            }
            return getDataColumn(context, uri2, "_id=?", new String[]{r7[1]});
        }
        return null;
    }

    private static File writeToFile(Context context, String str, Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getCacheDir());
        stringBuilder.append("/react-native-image-crop-picker");
        String stringBuilder2 = stringBuilder.toString();
        Boolean.valueOf(new File(stringBuilder2).mkdir());
        File file = new File(new File(stringBuilder2), str.substring(str.lastIndexOf(47) + 1));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[8192];
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            while (true) {
                int read = openInputStream.read(bArr, 0, bArr.length);
                if (read <= 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            openInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0056  */
    private static java.lang.String getDataColumn(android.content.Context r9, android.net.Uri r10, java.lang.String r11, java.lang.String[] r12) {
        /*
        r0 = "_display_name";
        r1 = "_data";
        r4 = new java.lang.String[]{r1, r0};
        r8 = 0;
        r2 = r9.getContentResolver();	 Catch:{ all -> 0x0052 }
        r7 = 0;
        r3 = r10;
        r5 = r11;
        r6 = r12;
        r11 = r2.query(r3, r4, r5, r6, r7);	 Catch:{ all -> 0x0052 }
        if (r11 == 0) goto L_0x004c;
    L_0x0017:
        r12 = r11.moveToFirst();	 Catch:{ all -> 0x004a }
        if (r12 == 0) goto L_0x004c;
    L_0x001d:
        r12 = r11.getColumnIndex(r1);	 Catch:{ all -> 0x004a }
        r1 = -1;
        if (r12 <= r1) goto L_0x0028;
    L_0x0024:
        r8 = r11.getString(r12);	 Catch:{ all -> 0x004a }
    L_0x0028:
        if (r8 == 0) goto L_0x0034;
    L_0x002a:
        r9 = r11.getString(r12);	 Catch:{ all -> 0x004a }
        if (r11 == 0) goto L_0x0033;
    L_0x0030:
        r11.close();
    L_0x0033:
        return r9;
    L_0x0034:
        r12 = r11.getColumnIndexOrThrow(r0);	 Catch:{ all -> 0x004a }
        r12 = r11.getString(r12);	 Catch:{ all -> 0x004a }
        r9 = writeToFile(r9, r12, r10);	 Catch:{ all -> 0x004a }
        r9 = r9.getAbsolutePath();	 Catch:{ all -> 0x004a }
        if (r11 == 0) goto L_0x0049;
    L_0x0046:
        r11.close();
    L_0x0049:
        return r9;
    L_0x004a:
        r9 = move-exception;
        goto L_0x0054;
    L_0x004c:
        if (r11 == 0) goto L_0x0051;
    L_0x004e:
        r11.close();
    L_0x0051:
        return r8;
    L_0x0052:
        r9 = move-exception;
        r11 = r8;
    L_0x0054:
        if (r11 == 0) goto L_0x0059;
    L_0x0056:
        r11.close();
    L_0x0059:
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactnative.ivpusic.imagepicker.RealPathUtil.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @TargetApi(19)
    private static String getPathToNonPrimaryVolume(Context context, String str) {
        File[] externalCacheDirs = context.getExternalCacheDirs();
        if (externalCacheDirs != null) {
            for (File file : externalCacheDirs) {
                if (file != null) {
                    String absolutePath = file.getAbsolutePath();
                    if (absolutePath != null) {
                        int indexOf = absolutePath.indexOf(str);
                        if (indexOf != -1) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(absolutePath.substring(0, indexOf));
                            stringBuilder.append(str);
                            return stringBuilder.toString();
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return null;
    }
}
