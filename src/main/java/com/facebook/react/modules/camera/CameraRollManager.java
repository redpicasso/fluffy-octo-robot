package com.facebook.react.modules.camera;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore.Files;
import android.text.TextUtils;
import com.android.vending.expansion.zipfile.APEZProvider;
import com.brentvatne.react.ReactVideoView;
import com.facebook.common.logging.FLog;
import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.module.annotations.ReactModule;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

@ReactModule(name = "CameraRollManager")
public class CameraRollManager extends ReactContextBaseJavaModule {
    private static final String ASSET_TYPE_ALL = "All";
    private static final String ASSET_TYPE_PHOTOS = "Photos";
    private static final String ASSET_TYPE_VIDEOS = "Videos";
    private static final String ERROR_UNABLE_TO_FILTER = "E_UNABLE_TO_FILTER";
    private static final String ERROR_UNABLE_TO_LOAD = "E_UNABLE_TO_LOAD";
    private static final String ERROR_UNABLE_TO_LOAD_PERMISSION = "E_UNABLE_TO_LOAD_PERMISSION";
    private static final String ERROR_UNABLE_TO_SAVE = "E_UNABLE_TO_SAVE";
    public static final String NAME = "CameraRollManager";
    private static final String[] PROJECTION = new String[]{APEZProvider.FILEID, "mime_type", "bucket_display_name", "datetaken", "width", "height", "longitude", "latitude", "_data"};
    private static final String SELECTION_BUCKET = "bucket_display_name = ?";
    private static final String SELECTION_DATE_TAKEN = "datetaken < ?";

    private static class GetMediaTask extends GuardedAsyncTask<Void, Void> {
        @Nullable
        private final String mAfter;
        private final String mAssetType;
        private final Context mContext;
        private final int mFirst;
        @Nullable
        private final String mGroupName;
        @Nullable
        private final ReadableArray mMimeTypes;
        private final Promise mPromise;

        private GetMediaTask(ReactContext reactContext, int i, @Nullable String str, @Nullable String str2, @Nullable ReadableArray readableArray, String str3, Promise promise) {
            super(reactContext);
            this.mContext = reactContext;
            this.mFirst = i;
            this.mAfter = str;
            this.mGroupName = str2;
            this.mMimeTypes = readableArray;
            this.mPromise = promise;
            this.mAssetType = str3;
        }

        protected void doInBackgroundGuarded(Void... voidArr) {
            StringBuilder stringBuilder = new StringBuilder("1");
            List arrayList = new ArrayList();
            if (!TextUtils.isEmpty(this.mAfter)) {
                stringBuilder.append(" AND datetaken < ?");
                arrayList.add(this.mAfter);
            }
            if (!TextUtils.isEmpty(this.mGroupName)) {
                stringBuilder.append(" AND bucket_display_name = ?");
                arrayList.add(this.mGroupName);
            }
            String str = this.mAssetType;
            String str2 = CameraRollManager.ASSET_TYPE_PHOTOS;
            if (str.equals(str2)) {
                stringBuilder.append(" AND media_type = 1");
            } else {
                str = this.mAssetType;
                String str3 = CameraRollManager.ASSET_TYPE_VIDEOS;
                if (str.equals(str3)) {
                    stringBuilder.append(" AND media_type = 3");
                } else {
                    str = this.mAssetType;
                    String str4 = CameraRollManager.ASSET_TYPE_ALL;
                    if (str.equals(str4)) {
                        stringBuilder.append(" AND media_type IN (3,1)");
                    } else {
                        Promise promise = this.mPromise;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Invalid filter option: '");
                        stringBuilder2.append(this.mAssetType);
                        stringBuilder2.append("'. Expected one of '");
                        stringBuilder2.append(str2);
                        stringBuilder2.append("', '");
                        stringBuilder2.append(str3);
                        stringBuilder2.append("' or '");
                        stringBuilder2.append(str4);
                        stringBuilder2.append("'.");
                        promise.reject(CameraRollManager.ERROR_UNABLE_TO_FILTER, stringBuilder2.toString());
                        return;
                    }
                }
            }
            ReadableArray readableArray = this.mMimeTypes;
            if (readableArray != null && readableArray.size() > 0) {
                stringBuilder.append(" AND mime_type IN (");
                for (int i = 0; i < this.mMimeTypes.size(); i++) {
                    stringBuilder.append("?,");
                    arrayList.add(this.mMimeTypes.getString(i));
                }
                stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ")");
            }
            WritableMap writableNativeMap = new WritableNativeMap();
            ContentResolver contentResolver = this.mContext.getContentResolver();
            Cursor query;
            try {
                Uri contentUri = Files.getContentUri("external");
                String[] access$200 = CameraRollManager.PROJECTION;
                String stringBuilder3 = stringBuilder.toString();
                String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                stringBuilder = new StringBuilder();
                stringBuilder.append("datetaken DESC, date_modified DESC LIMIT ");
                stringBuilder.append(this.mFirst + 1);
                query = contentResolver.query(contentUri, access$200, stringBuilder3, strArr, stringBuilder.toString());
                if (query == null) {
                    this.mPromise.reject(CameraRollManager.ERROR_UNABLE_TO_LOAD, "Could not get media");
                } else {
                    CameraRollManager.putEdges(contentResolver, query, writableNativeMap, this.mFirst);
                    CameraRollManager.putPageInfo(query, writableNativeMap, this.mFirst);
                    query.close();
                    this.mPromise.resolve(writableNativeMap);
                }
            } catch (Throwable e) {
                this.mPromise.reject(CameraRollManager.ERROR_UNABLE_TO_LOAD_PERMISSION, "Could not get media: need READ_EXTERNAL_STORAGE permission", e);
            } catch (Throwable th) {
                query.close();
                this.mPromise.resolve(writableNativeMap);
            }
        }
    }

    private static class SaveToCameraRoll extends GuardedAsyncTask<Void, Void> {
        private static final int SAVE_BUFFER_SIZE = 1048576;
        private final Context mContext;
        private final Promise mPromise;
        private final Uri mUri;

        public SaveToCameraRoll(ReactContext reactContext, Uri uri, Promise promise) {
            super(reactContext);
            this.mContext = reactContext;
            this.mUri = uri;
            this.mPromise = promise;
        }

        protected void doInBackgroundGuarded(Void... voidArr) {
            ReadableByteChannel readableByteChannel;
            FileChannel fileChannel;
            Throwable e;
            ReadableByteChannel readableByteChannel2;
            String str = "Could not close output channel";
            String str2 = "Could not close input channel";
            String str3 = ReactConstants.TAG;
            File file = new File(this.mUri.getPath());
            FileChannel fileChannel2 = null;
            ReadableByteChannel newChannel;
            try {
                String scheme = this.mUri.getScheme();
                if (scheme.equals(UriUtil.HTTP_SCHEME) || scheme.equals(UriUtil.HTTPS_SCHEME)) {
                    newChannel = Channels.newChannel(new URL(this.mUri.toString()).openStream());
                } else {
                    newChannel = new FileInputStream(file).getChannel();
                }
                try {
                    File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    externalStoragePublicDirectory.mkdirs();
                    if (externalStoragePublicDirectory.isDirectory()) {
                        String substring;
                        int i;
                        File file2 = new File(externalStoragePublicDirectory, file.getName());
                        String name = file.getName();
                        if (name.indexOf(46) >= 0) {
                            substring = name.substring(0, name.lastIndexOf(46));
                            i = 0;
                            String str4 = substring;
                            substring = name.substring(name.lastIndexOf(46));
                            name = str4;
                        } else {
                            substring = "";
                            i = 0;
                        }
                        while (!file2.createNewFile()) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(name);
                            stringBuilder.append("_");
                            int i2 = i + 1;
                            stringBuilder.append(i);
                            stringBuilder.append(substring);
                            file2 = new File(externalStoragePublicDirectory, stringBuilder.toString());
                            i = i2;
                        }
                        FileChannel channel = new FileOutputStream(file2).getChannel();
                        try {
                            ByteBuffer allocate = ByteBuffer.allocate(1048576);
                            while (newChannel.read(allocate) > 0) {
                                allocate.flip();
                                channel.write(allocate);
                                allocate.compact();
                            }
                            allocate.flip();
                            while (allocate.hasRemaining()) {
                                channel.write(allocate);
                            }
                            newChannel.close();
                            channel.close();
                            MediaScannerConnection.scanFile(this.mContext, new String[]{file2.getAbsolutePath()}, null, new OnScanCompletedListener() {
                                public void onScanCompleted(String str, Uri uri) {
                                    if (uri != null) {
                                        SaveToCameraRoll.this.mPromise.resolve(uri.toString());
                                    } else {
                                        SaveToCameraRoll.this.mPromise.reject(CameraRollManager.ERROR_UNABLE_TO_SAVE, "Could not add image to gallery");
                                    }
                                }
                            });
                            if (newChannel != null && newChannel.isOpen()) {
                                try {
                                    newChannel.close();
                                } catch (Throwable e2) {
                                    FLog.e(str3, str2, e2);
                                }
                            }
                            if (channel != null && channel.isOpen()) {
                                try {
                                    channel.close();
                                } catch (Throwable e3) {
                                    FLog.e(str3, str, e3);
                                }
                            }
                        } catch (Throwable e22) {
                            readableByteChannel = newChannel;
                            fileChannel = channel;
                            e = e22;
                            readableByteChannel2 = readableByteChannel;
                            try {
                                this.mPromise.reject(e);
                                try {
                                    readableByteChannel2.close();
                                } catch (Throwable e4) {
                                    FLog.e(str3, str2, e4);
                                }
                                fileChannel.close();
                            } catch (Throwable th) {
                                e4 = th;
                                FileChannel fileChannel3 = fileChannel;
                                newChannel = readableByteChannel2;
                                fileChannel2 = fileChannel3;
                                if (newChannel != null && newChannel.isOpen()) {
                                    try {
                                        newChannel.close();
                                    } catch (Throwable e5) {
                                        FLog.e(str3, str2, e5);
                                    }
                                }
                                if (fileChannel2 != null && fileChannel2.isOpen()) {
                                    try {
                                        fileChannel2.close();
                                    } catch (Throwable e32) {
                                        FLog.e(str3, str, e32);
                                    }
                                }
                                throw e4;
                            }
                        } catch (Throwable e222) {
                            Throwable th2 = e222;
                            fileChannel2 = channel;
                            e4 = th2;
                            newChannel.close();
                            fileChannel2.close();
                            throw e4;
                        }
                    }
                    this.mPromise.reject(CameraRollManager.ERROR_UNABLE_TO_LOAD, "External media storage directory not available");
                    if (newChannel != null && newChannel.isOpen()) {
                        try {
                            newChannel.close();
                        } catch (Throwable e6) {
                            FLog.e(str3, str2, e6);
                        }
                    }
                } catch (IOException e7) {
                    e4 = e7;
                    readableByteChannel = newChannel;
                    fileChannel = null;
                    readableByteChannel2 = readableByteChannel;
                    this.mPromise.reject(e4);
                    if (readableByteChannel2 != null && readableByteChannel2.isOpen()) {
                        readableByteChannel2.close();
                    }
                    if (fileChannel != null && fileChannel.isOpen()) {
                        fileChannel.close();
                    }
                } catch (Throwable th3) {
                    e4 = th3;
                    newChannel.close();
                    fileChannel2.close();
                    throw e4;
                }
            } catch (IOException e8) {
                e4 = e8;
                fileChannel = null;
                this.mPromise.reject(e4);
                readableByteChannel2.close();
                fileChannel.close();
            } catch (Throwable th4) {
                e4 = th4;
                newChannel = null;
                newChannel.close();
                fileChannel2.close();
                throw e4;
            }
        }
    }

    public String getName() {
        return NAME;
    }

    public CameraRollManager(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void saveToCameraRoll(String str, String str2, Promise promise) {
        new SaveToCameraRoll(access$100(), Uri.parse(str), promise).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @ReactMethod
    public void getPhotos(ReadableMap readableMap, Promise promise) {
        int i = readableMap.getInt("first");
        String str = "after";
        String string = readableMap.hasKey(str) ? readableMap.getString(str) : null;
        str = "groupName";
        String string2 = readableMap.hasKey(str) ? readableMap.getString(str) : null;
        str = "assetType";
        String string3 = readableMap.hasKey(str) ? readableMap.getString(str) : ASSET_TYPE_PHOTOS;
        str = "mimeTypes";
        ReadableArray array = readableMap.hasKey(str) ? readableMap.getArray(str) : null;
        if (readableMap.hasKey("groupTypes")) {
            throw new JSApplicationIllegalArgumentException("groupTypes is not supported on Android");
        }
        new GetMediaTask(access$100(), i, string, string2, array, string3, promise).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private static void putPageInfo(Cursor cursor, WritableMap writableMap, int i) {
        WritableMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putBoolean("has_next_page", i < cursor.getCount());
        if (i < cursor.getCount()) {
            cursor.moveToPosition(i - 1);
            writableNativeMap.putString("end_cursor", cursor.getString(cursor.getColumnIndex("datetaken")));
        }
        writableMap.putMap("page_info", writableNativeMap);
    }

    private static void putEdges(ContentResolver contentResolver, Cursor cursor, WritableMap writableMap, int i) {
        WritableMap writableNativeMap;
        Cursor cursor2 = cursor;
        WritableNativeArray writableNativeArray = new WritableNativeArray();
        cursor.moveToFirst();
        int columnIndex = cursor2.getColumnIndex(APEZProvider.FILEID);
        int columnIndex2 = cursor2.getColumnIndex("mime_type");
        int columnIndex3 = cursor2.getColumnIndex("bucket_display_name");
        int columnIndex4 = cursor2.getColumnIndex("datetaken");
        int columnIndex5 = cursor2.getColumnIndex("width");
        int columnIndex6 = cursor2.getColumnIndex("height");
        int columnIndex7 = cursor2.getColumnIndex("longitude");
        int columnIndex8 = cursor2.getColumnIndex("latitude");
        int columnIndex9 = cursor2.getColumnIndex("_data");
        int i2 = i;
        int i3 = 0;
        while (i3 < i2 && !cursor.isAfterLast()) {
            WritableArray writableArray;
            WritableNativeMap writableNativeMap2 = new WritableNativeMap();
            writableNativeMap = new WritableNativeMap();
            WritableMap writableMap2 = writableNativeMap;
            WritableNativeArray writableNativeArray2 = writableNativeArray;
            WritableNativeMap writableNativeMap3 = writableNativeMap2;
            int i4 = i3;
            int i5 = columnIndex;
            columnIndex = columnIndex8;
            int i6 = columnIndex5;
            columnIndex5 = columnIndex7;
            if (putImageInfo(contentResolver, cursor, writableNativeMap, columnIndex, columnIndex5, columnIndex6, columnIndex9, columnIndex2)) {
                WritableMap writableMap3 = writableMap2;
                putBasicNodeInfo(cursor2, writableMap3, columnIndex2, columnIndex3, columnIndex4);
                putLocationInfo(cursor2, writableMap3, columnIndex5, columnIndex);
                writableNativeMap3.putMap("node", writableMap3);
                writableArray = writableNativeArray2;
                writableArray.pushMap(writableNativeMap3);
            } else {
                writableArray = writableNativeArray2;
                i4--;
            }
            cursor.moveToNext();
            i3 = i4 + 1;
            i2 = i;
            WritableArray writableNativeArray3 = writableArray;
            columnIndex8 = columnIndex;
            columnIndex7 = columnIndex5;
            columnIndex = i5;
            columnIndex5 = i6;
        }
        writableNativeMap = writableMap;
        writableNativeMap.putArray("edges", writableNativeArray3);
    }

    private static void putBasicNodeInfo(Cursor cursor, WritableMap writableMap, int i, int i2, int i3) {
        writableMap.putString("type", cursor.getString(i));
        writableMap.putString("group_name", cursor.getString(i2));
        writableMap.putDouble("timestamp", ((double) cursor.getLong(i3)) / 1000.0d);
    }

    private static boolean putImageInfo(ContentResolver contentResolver, Cursor cursor, WritableMap writableMap, int i, int i2, int i3, int i4, int i5) {
        StringBuilder stringBuilder;
        WritableMap writableNativeMap = new WritableNativeMap();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("file://");
        stringBuilder2.append(cursor.getString(i4));
        Uri parse = Uri.parse(stringBuilder2.toString());
        writableNativeMap.putString("uri", parse.toString());
        float f = (float) cursor.getInt(i2);
        float f2 = (float) cursor.getInt(i3);
        String string = cursor.getString(i5);
        String str = "r";
        String str2 = ReactConstants.TAG;
        if (string != null && string.startsWith("video")) {
            AssetFileDescriptor openAssetFileDescriptor;
            MediaMetadataRetriever mediaMetadataRetriever;
            try {
                openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(parse, str);
                mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(openAssetFileDescriptor.getFileDescriptor());
                if (f <= 0.0f || f2 <= 0.0f) {
                    try {
                        f = (float) Integer.parseInt(mediaMetadataRetriever.extractMetadata(18));
                        f2 = (float) Integer.parseInt(mediaMetadataRetriever.extractMetadata(19));
                    } catch (Throwable e) {
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("Number format exception occurred while trying to fetch video metadata for ");
                        stringBuilder3.append(parse.toString());
                        FLog.e(str2, stringBuilder3.toString(), e);
                        mediaMetadataRetriever.release();
                        openAssetFileDescriptor.close();
                        return false;
                    }
                }
                writableNativeMap.putInt(ReactVideoView.EVENT_PROP_PLAYABLE_DURATION, Integer.parseInt(mediaMetadataRetriever.extractMetadata(9)) / 1000);
                mediaMetadataRetriever.release();
                openAssetFileDescriptor.close();
            } catch (Throwable e2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Could not get video metadata for ");
                stringBuilder.append(parse.toString());
                FLog.e(str2, stringBuilder.toString(), e2);
                return false;
            } catch (Throwable th) {
                mediaMetadataRetriever.release();
                openAssetFileDescriptor.close();
            }
        }
        if (f <= 0.0f || r12 <= 0.0f) {
            try {
                AssetFileDescriptor openAssetFileDescriptor2 = contentResolver.openAssetFileDescriptor(parse, str);
                Options options = new Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(openAssetFileDescriptor2.getFileDescriptor(), null, options);
                f2 = (float) options.outWidth;
                f = (float) options.outHeight;
                openAssetFileDescriptor2.close();
                float f3 = f2;
                f2 = f;
                f = f3;
            } catch (Throwable e22) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Could not get width/height for ");
                stringBuilder.append(parse.toString());
                FLog.e(str2, stringBuilder.toString(), e22);
                return false;
            }
        }
        writableNativeMap.putDouble("width", (double) f);
        writableNativeMap.putDouble("height", (double) f2);
        writableMap.putMap("image", writableNativeMap);
        return true;
    }

    private static void putLocationInfo(Cursor cursor, WritableMap writableMap, int i, int i2) {
        double d = cursor.getDouble(i);
        double d2 = cursor.getDouble(i2);
        if (d > 0.0d || d2 > 0.0d) {
            WritableMap writableNativeMap = new WritableNativeMap();
            writableNativeMap.putDouble("longitude", d);
            writableNativeMap.putDouble("latitude", d2);
            writableMap.putMap(Param.LOCATION, writableNativeMap);
        }
    }
}
