package com.dylanvann.fastimage;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.text.TextUtils;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.facebook.react.views.imagehelper.ImageSource;
import javax.annotation.Nullable;

public class FastImageSource extends ImageSource {
    private static final String ANDROID_CONTENT_SCHEME = "content";
    private static final String ANDROID_RESOURCE_SCHEME = "android.resource";
    private static final String DATA_SCHEME = "data";
    private static final String LOCAL_FILE_SCHEME = "file";
    private static final String LOCAL_RESOURCE_SCHEME = "res";
    private Headers mHeaders;
    private Uri mUri;

    public static boolean isBase64Uri(Uri uri) {
        return "data".equals(uri.getScheme());
    }

    public static boolean isLocalResourceUri(Uri uri) {
        return "res".equals(uri.getScheme());
    }

    public static boolean isResourceUri(Uri uri) {
        return "android.resource".equals(uri.getScheme());
    }

    public static boolean isContentUri(Uri uri) {
        return "content".equals(uri.getScheme());
    }

    public static boolean isLocalFileUri(Uri uri) {
        return "file".equals(uri.getScheme());
    }

    public FastImageSource(Context context, String str) {
        this(context, str, null);
    }

    public FastImageSource(Context context, String str, @Nullable Headers headers) {
        this(context, str, 0.0d, 0.0d, headers);
    }

    public FastImageSource(Context context, String str, double d, double d2, @Nullable Headers headers) {
        super(context, str, d, d2);
        if (headers == null) {
            headers = Headers.DEFAULT;
        }
        this.mHeaders = headers;
        this.mUri = super.getUri();
        if (isResource() && TextUtils.isEmpty(this.mUri.toString())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Local Resource Not Found. Resource: '");
            stringBuilder.append(getSource());
            stringBuilder.append("'.");
            throw new NotFoundException(stringBuilder.toString());
        } else if (isLocalResourceUri(this.mUri)) {
            str = this.mUri.toString();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("android.resource://");
            stringBuilder2.append(context.getPackageName());
            stringBuilder2.append("/");
            this.mUri = Uri.parse(str.replace("res:/", stringBuilder2.toString()));
        }
    }

    public boolean isBase64Resource() {
        Uri uri = this.mUri;
        return uri != null && isBase64Uri(uri);
    }

    public boolean isResource() {
        Uri uri = this.mUri;
        return uri != null && isResourceUri(uri);
    }

    public boolean isLocalFile() {
        Uri uri = this.mUri;
        return uri != null && isLocalFileUri(uri);
    }

    public boolean isContentUri() {
        Uri uri = this.mUri;
        return uri != null && isContentUri(uri);
    }

    public Object getSourceForLoad() {
        if (isContentUri()) {
            return getSource();
        }
        if (isBase64Resource()) {
            return getSource();
        }
        if (isResource()) {
            return getUri();
        }
        if (isLocalFile()) {
            return getUri().toString();
        }
        return getGlideUrl();
    }

    public Uri getUri() {
        return this.mUri;
    }

    public Headers getHeaders() {
        return this.mHeaders;
    }

    public GlideUrl getGlideUrl() {
        return new GlideUrl(getUri().toString(), getHeaders());
    }
}
