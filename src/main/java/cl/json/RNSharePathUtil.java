package cl.json;

import android.app.Application;
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
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.ReactContext;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

public class RNSharePathUtil {
    private static final ArrayList<String> authorities = new ArrayList();

    public static void compileAuthorities(ReactContext reactContext) {
        if (authorities.size() == 0) {
            Application application = (Application) reactContext.getApplicationContext();
            if (application instanceof ShareApplication) {
                authorities.add(((ShareApplication) application).getFileProviderAuthority());
            }
            ArrayList arrayList = authorities;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(reactContext.getPackageName());
            stringBuilder.append(".rnshare.fileprovider");
            arrayList.add(stringBuilder.toString());
        }
    }

    public static Uri compatUriFromFile(@NonNull ReactContext reactContext, @NonNull File file) {
        compileAuthorities(reactContext);
        CharSequence authority = Uri.fromFile(file).getAuthority();
        if (!TextUtils.isEmpty(authority) && authorities.contains(authority)) {
            return Uri.fromFile(file);
        }
        if (file.getAbsolutePath().startsWith(RNFetchBlobConst.FILE_PREFIX_CONTENT)) {
            return Uri.fromFile(file);
        }
        Uri uri = null;
        int i = 0;
        while (i < authorities.size()) {
            try {
                uri = FileProvider.getUriForFile(reactContext, (String) authorities.get(i), file);
                if (uri != null) {
                    break;
                }
                i++;
            } catch (Exception e) {
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("RNSharePathUtil::compatUriFromFile ERROR ");
                stringBuilder.append(e.getMessage());
                printStream.println(stringBuilder.toString());
            }
        }
        return uri;
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        String str = "";
        Uri uri2 = null;
        StringBuilder stringBuilder;
        if (VERSION.SDK_INT < 19 || !DocumentsContract.isDocumentUri(context, uri)) {
            if (!"content".equalsIgnoreCase(uri.getScheme())) {
                if (UriUtil.LOCAL_FILE_SCHEME.equalsIgnoreCase(uri.getScheme())) {
                    return uri.getPath();
                }
            } else if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(getDataColumn(context, uri, null, null));
                return stringBuilder.toString();
            }
        }
        String str2 = "raw";
        String str3 = ":";
        StringBuilder stringBuilder2;
        String str4;
        StringBuilder stringBuilder3;
        if (isExternalStorageDocument(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(str3);
            Object obj = split[0];
            str3 = "/";
            if ("primary".equalsIgnoreCase(obj) || "0".equalsIgnoreCase(obj)) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append(Environment.getExternalStorageDirectory());
                stringBuilder2.append(str3);
                stringBuilder2.append(split[1]);
                return stringBuilder2.toString();
            } else if (str2.equalsIgnoreCase(obj)) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append(split[1]);
                return stringBuilder2.toString();
            } else if (!TextUtils.isEmpty(obj)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append("/storage/");
                stringBuilder.append(obj);
                stringBuilder.append(str3);
                stringBuilder.append(split[1]);
                return stringBuilder.toString();
            }
        } else if (isDownloadsDocument(uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            str4 = "raw:";
            if (documentId.startsWith(str4)) {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(str);
                stringBuilder3.append(documentId.replaceFirst(str4, str));
                return stringBuilder3.toString();
            }
            uri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId).longValue());
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(getDataColumn(context, uri, null, null));
            return stringBuilder.toString();
        } else if (isMediaDocument(uri)) {
            String[] split2 = DocumentsContract.getDocumentId(uri).split(str3);
            str4 = split2[0];
            if ("image".equals(str4)) {
                uri2 = Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(str4)) {
                uri2 = Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(str4)) {
                uri2 = Audio.Media.EXTERNAL_CONTENT_URI;
            } else if (str2.equalsIgnoreCase(str4)) {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(str);
                stringBuilder3.append(split2[1]);
                return stringBuilder3.toString();
            }
            String[] strArr = new String[]{split2[1]};
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(str);
            stringBuilder2.append(getDataColumn(context, uri2, "_id=?", strArr));
            return stringBuilder2.toString();
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0038  */
    public static java.lang.String getDataColumn(android.content.Context r10, android.net.Uri r11, java.lang.String r12, java.lang.String[] r13) {
        /*
        r0 = "_data";
        r4 = new java.lang.String[]{r0};
        r8 = 0;
        r9 = new androidx.loader.content.CursorLoader;	 Catch:{ all -> 0x0034 }
        r7 = 0;
        r1 = r9;
        r2 = r10;
        r3 = r11;
        r5 = r12;
        r6 = r13;
        r1.<init>(r2, r3, r4, r5, r6, r7);	 Catch:{ all -> 0x0034 }
        r10 = r9.loadInBackground();	 Catch:{ all -> 0x0034 }
        if (r10 == 0) goto L_0x002e;
    L_0x0018:
        r11 = r10.moveToFirst();	 Catch:{ all -> 0x002c }
        if (r11 == 0) goto L_0x002e;
    L_0x001e:
        r11 = r10.getColumnIndexOrThrow(r0);	 Catch:{ all -> 0x002c }
        r11 = r10.getString(r11);	 Catch:{ all -> 0x002c }
        if (r10 == 0) goto L_0x002b;
    L_0x0028:
        r10.close();
    L_0x002b:
        return r11;
    L_0x002c:
        r11 = move-exception;
        goto L_0x0036;
    L_0x002e:
        if (r10 == 0) goto L_0x0033;
    L_0x0030:
        r10.close();
    L_0x0033:
        return r8;
    L_0x0034:
        r11 = move-exception;
        r10 = r8;
    L_0x0036:
        if (r10 == 0) goto L_0x003b;
    L_0x0038:
        r10.close();
    L_0x003b:
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: cl.json.RNSharePathUtil.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
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
