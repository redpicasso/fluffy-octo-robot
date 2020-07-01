package com.bumptech.glide.load.data.mediastore;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

class ThumbnailStreamOpener {
    private static final FileService DEFAULT_SERVICE = new FileService();
    private static final String TAG = "ThumbStreamOpener";
    private final ArrayPool byteArrayPool;
    private final ContentResolver contentResolver;
    private final List<ImageHeaderParser> parsers;
    private final ThumbnailQuery query;
    private final FileService service;

    ThumbnailStreamOpener(List<ImageHeaderParser> list, ThumbnailQuery thumbnailQuery, ArrayPool arrayPool, ContentResolver contentResolver) {
        this(list, DEFAULT_SERVICE, thumbnailQuery, arrayPool, contentResolver);
    }

    ThumbnailStreamOpener(List<ImageHeaderParser> list, FileService fileService, ThumbnailQuery thumbnailQuery, ArrayPool arrayPool, ContentResolver contentResolver) {
        this.service = fileService;
        this.query = thumbnailQuery;
        this.byteArrayPool = arrayPool;
        this.contentResolver = contentResolver;
        this.parsers = list;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0023 A:{Catch:{ all -> 0x0017 }} */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0039 A:{SYNTHETIC, Splitter: B:16:0x0039} */
    int getOrientation(android.net.Uri r6) {
        /*
        r5 = this;
        r0 = "ThumbStreamOpener";
        r1 = 0;
        r2 = r5.contentResolver;	 Catch:{ IOException -> 0x001b, NullPointerException -> 0x0019 }
        r1 = r2.openInputStream(r6);	 Catch:{ IOException -> 0x001b, NullPointerException -> 0x0019 }
        r2 = r5.parsers;	 Catch:{ IOException -> 0x001b, NullPointerException -> 0x0019 }
        r3 = r5.byteArrayPool;	 Catch:{ IOException -> 0x001b, NullPointerException -> 0x0019 }
        r6 = com.bumptech.glide.load.ImageHeaderParserUtils.getOrientation(r2, r1, r3);	 Catch:{ IOException -> 0x001b, NullPointerException -> 0x0019 }
        if (r1 == 0) goto L_0x0016;
    L_0x0013:
        r1.close();	 Catch:{ IOException -> 0x0016 }
    L_0x0016:
        return r6;
    L_0x0017:
        r6 = move-exception;
        goto L_0x003e;
    L_0x0019:
        r2 = move-exception;
        goto L_0x001c;
    L_0x001b:
        r2 = move-exception;
    L_0x001c:
        r3 = 3;
        r3 = android.util.Log.isLoggable(r0, r3);	 Catch:{ all -> 0x0017 }
        if (r3 == 0) goto L_0x0037;
    L_0x0023:
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0017 }
        r3.<init>();	 Catch:{ all -> 0x0017 }
        r4 = "Failed to open uri: ";
        r3.append(r4);	 Catch:{ all -> 0x0017 }
        r3.append(r6);	 Catch:{ all -> 0x0017 }
        r6 = r3.toString();	 Catch:{ all -> 0x0017 }
        android.util.Log.d(r0, r6, r2);	 Catch:{ all -> 0x0017 }
    L_0x0037:
        if (r1 == 0) goto L_0x003c;
    L_0x0039:
        r1.close();	 Catch:{ IOException -> 0x003c }
    L_0x003c:
        r6 = -1;
        return r6;
    L_0x003e:
        if (r1 == 0) goto L_0x0043;
    L_0x0040:
        r1.close();	 Catch:{ IOException -> 0x0043 }
    L_0x0043:
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.data.mediastore.ThumbnailStreamOpener.getOrientation(android.net.Uri):int");
    }

    public InputStream open(Uri uri) throws FileNotFoundException {
        Object path = getPath(uri);
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = this.service.get(path);
        if (!isValid(file)) {
            return null;
        }
        Uri fromFile = Uri.fromFile(file);
        Object uri2;
        try {
            uri2 = this.contentResolver.openInputStream(fromFile);
            return uri2;
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NPE opening uri: ");
            stringBuilder.append(uri2);
            stringBuilder.append(" -> ");
            stringBuilder.append(fromFile);
            throw ((FileNotFoundException) new FileNotFoundException(stringBuilder.toString()).initCause(e));
        }
    }

    @Nullable
    private String getPath(@NonNull Uri uri) {
        Cursor query = this.query.query(uri);
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    String string = query.getString(0);
                    return string;
                }
            } finally {
                if (query != null) {
                    query.close();
                }
            }
        }
        if (query != null) {
            query.close();
        }
        return null;
    }

    private boolean isValid(File file) {
        return this.service.exists(file) && 0 < this.service.length(file);
    }
}
