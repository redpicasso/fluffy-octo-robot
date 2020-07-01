package com.facebook.common.util;

import android.net.Uri;
import android.net.Uri.Builder;
import android.provider.ContactsContract;
import android.provider.MediaStore.Images.Media;
import java.io.File;
import java.net.URL;
import javax.annotation.Nullable;

public class UriUtil {
    public static final String DATA_SCHEME = "data";
    public static final String HTTPS_SCHEME = "https";
    public static final String HTTP_SCHEME = "http";
    public static final String LOCAL_ASSET_SCHEME = "asset";
    private static final Uri LOCAL_CONTACT_IMAGE_URI = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "display_photo");
    public static final String LOCAL_CONTENT_SCHEME = "content";
    public static final String LOCAL_FILE_SCHEME = "file";
    public static final String LOCAL_RESOURCE_SCHEME = "res";
    public static final String QUALIFIED_RESOURCE_SCHEME = "android.resource";

    @Nullable
    public static URL uriToUrl(@Nullable Uri uri) {
        if (uri == null) {
            return null;
        }
        try {
            return new URL(uri.toString());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isNetworkUri(@Nullable Uri uri) {
        String schemeOrNull = getSchemeOrNull(uri);
        return HTTPS_SCHEME.equals(schemeOrNull) || HTTP_SCHEME.equals(schemeOrNull);
    }

    public static boolean isLocalFileUri(@Nullable Uri uri) {
        return LOCAL_FILE_SCHEME.equals(getSchemeOrNull(uri));
    }

    public static boolean isLocalContentUri(@Nullable Uri uri) {
        return "content".equals(getSchemeOrNull(uri));
    }

    public static boolean isLocalContactUri(Uri uri) {
        if (isLocalContentUri(uri)) {
            if ("com.android.contacts".equals(uri.getAuthority()) && !uri.getPath().startsWith(LOCAL_CONTACT_IMAGE_URI.getPath())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLocalCameraUri(Uri uri) {
        String uri2 = uri.toString();
        return uri2.startsWith(Media.EXTERNAL_CONTENT_URI.toString()) || uri2.startsWith(Media.INTERNAL_CONTENT_URI.toString());
    }

    public static boolean isLocalAssetUri(@Nullable Uri uri) {
        return LOCAL_ASSET_SCHEME.equals(getSchemeOrNull(uri));
    }

    public static boolean isLocalResourceUri(@Nullable Uri uri) {
        return LOCAL_RESOURCE_SCHEME.equals(getSchemeOrNull(uri));
    }

    public static boolean isQualifiedResourceUri(@Nullable Uri uri) {
        return QUALIFIED_RESOURCE_SCHEME.equals(getSchemeOrNull(uri));
    }

    public static boolean isDataUri(@Nullable Uri uri) {
        return "data".equals(getSchemeOrNull(uri));
    }

    @Nullable
    public static String getSchemeOrNull(@Nullable Uri uri) {
        return uri == null ? null : uri.getScheme();
    }

    @Nullable
    public static Uri parseUriOrNull(@Nullable String str) {
        return str != null ? Uri.parse(str) : null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0034  */
    @javax.annotation.Nullable
    public static java.lang.String getRealPathFromUri(android.content.ContentResolver r8, android.net.Uri r9) {
        /*
        r0 = isLocalContentUri(r9);
        r1 = 0;
        if (r0 == 0) goto L_0x0038;
    L_0x0007:
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r2 = r8;
        r3 = r9;
        r8 = r2.query(r3, r4, r5, r6, r7);	 Catch:{ all -> 0x0030 }
        if (r8 == 0) goto L_0x002a;
    L_0x0013:
        r9 = r8.moveToFirst();	 Catch:{ all -> 0x0028 }
        if (r9 == 0) goto L_0x002a;
    L_0x0019:
        r9 = "_data";
        r9 = r8.getColumnIndex(r9);	 Catch:{ all -> 0x0028 }
        r0 = -1;
        if (r9 == r0) goto L_0x002a;
    L_0x0022:
        r9 = r8.getString(r9);	 Catch:{ all -> 0x0028 }
        r1 = r9;
        goto L_0x002a;
    L_0x0028:
        r9 = move-exception;
        goto L_0x0032;
    L_0x002a:
        if (r8 == 0) goto L_0x0042;
    L_0x002c:
        r8.close();
        goto L_0x0042;
    L_0x0030:
        r9 = move-exception;
        r8 = r1;
    L_0x0032:
        if (r8 == 0) goto L_0x0037;
    L_0x0034:
        r8.close();
    L_0x0037:
        throw r9;
    L_0x0038:
        r8 = isLocalFileUri(r9);
        if (r8 == 0) goto L_0x0042;
    L_0x003e:
        r1 = r9.getPath();
    L_0x0042:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.common.util.UriUtil.getRealPathFromUri(android.content.ContentResolver, android.net.Uri):java.lang.String");
    }

    public static Uri getUriForFile(File file) {
        return Uri.fromFile(file);
    }

    public static Uri getUriForResourceId(int i) {
        return new Builder().scheme(LOCAL_RESOURCE_SCHEME).path(String.valueOf(i)).build();
    }

    public static Uri getUriForQualifiedResource(String str, int i) {
        return new Builder().scheme(QUALIFIED_RESOURCE_SCHEME).authority(str).path(String.valueOf(i)).build();
    }
}
