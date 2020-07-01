package com.bumptech.glide.load.resource.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.facebook.common.util.UriUtil;
import java.util.List;

public class ResourceDrawableDecoder implements ResourceDecoder<Uri, Drawable> {
    private static final String ANDROID_PACKAGE_NAME = "android";
    private static final int ID_PATH_SEGMENTS = 1;
    private static final int MISSING_RESOURCE_ID = 0;
    private static final int NAME_PATH_SEGMENT_INDEX = 1;
    private static final int NAME_URI_PATH_SEGMENTS = 2;
    private static final int RESOURCE_ID_SEGMENT_INDEX = 0;
    private static final int TYPE_PATH_SEGMENT_INDEX = 0;
    private final Context context;

    public ResourceDrawableDecoder(Context context) {
        this.context = context.getApplicationContext();
    }

    public boolean handles(@NonNull Uri uri, @NonNull Options options) {
        return uri.getScheme().equals(UriUtil.QUALIFIED_RESOURCE_SCHEME);
    }

    @Nullable
    public Resource<Drawable> decode(@NonNull Uri uri, int i, int i2, @NonNull Options options) {
        Context findContextForPackage = findContextForPackage(uri, uri.getAuthority());
        return NonOwnedDrawableResource.newInstance(DrawableDecoderCompat.getDrawable(this.context, findContextForPackage, findResourceIdFromUri(findContextForPackage, uri)));
    }

    @NonNull
    private Context findContextForPackage(Uri uri, String str) {
        if (str.equals(this.context.getPackageName())) {
            return this.context;
        }
        Object uri2;
        try {
            uri2 = this.context.createPackageContext(str, 0);
            return uri2;
        } catch (Throwable e) {
            if (str.contains(this.context.getPackageName())) {
                return this.context;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to obtain context or unrecognized Uri format for: ");
            stringBuilder.append(uri2);
            throw new IllegalArgumentException(stringBuilder.toString(), e);
        }
    }

    @DrawableRes
    private int findResourceIdFromUri(Context context, Uri uri) {
        List pathSegments = uri.getPathSegments();
        if (pathSegments.size() == 2) {
            return findResourceIdFromTypeAndNameResourceUri(context, uri);
        }
        if (pathSegments.size() == 1) {
            return findResourceIdFromResourceIdUri(uri);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unrecognized Uri format: ");
        stringBuilder.append(uri);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @DrawableRes
    private int findResourceIdFromTypeAndNameResourceUri(Context context, Uri uri) {
        List pathSegments = uri.getPathSegments();
        String str = (String) pathSegments.get(0);
        String str2 = (String) pathSegments.get(1);
        int identifier = context.getResources().getIdentifier(str2, str, uri.getAuthority());
        if (identifier == 0) {
            identifier = Resources.getSystem().getIdentifier(str2, str, ANDROID_PACKAGE_NAME);
        }
        if (identifier != 0) {
            return identifier;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to find resource id for: ");
        stringBuilder.append(uri);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @DrawableRes
    private int findResourceIdFromResourceIdUri(Uri uri) {
        Object uri2;
        try {
            uri2 = Integer.parseInt((String) uri2.getPathSegments().get(0));
            return uri2;
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unrecognized Uri format: ");
            stringBuilder.append(uri2);
            throw new IllegalArgumentException(stringBuilder.toString(), e);
        }
    }
}
