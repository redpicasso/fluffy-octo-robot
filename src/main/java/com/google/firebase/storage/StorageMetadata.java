package com.google.firebase.storage;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.storage.internal.SlashUtil;
import com.google.firebase.storage.internal.Util;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@PublicApi
/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class StorageMetadata {
    @NonNull
    private static final String BUCKET_KEY = "bucket";
    @NonNull
    private static final String CACHE_CONTROL = "cacheControl";
    @NonNull
    private static final String CONTENT_DISPOSITION = "contentDisposition";
    @NonNull
    private static final String CONTENT_ENCODING = "contentEncoding";
    @NonNull
    private static final String CONTENT_LANGUAGE = "contentLanguage";
    @NonNull
    private static final String CONTENT_TYPE_KEY = "contentType";
    @NonNull
    private static final String CUSTOM_METADATA_KEY = "metadata";
    @NonNull
    private static final String GENERATION_KEY = "generation";
    @NonNull
    private static final String MD5_HASH_KEY = "md5Hash";
    @NonNull
    private static final String META_GENERATION_KEY = "metageneration";
    @NonNull
    private static final String NAME_KEY = "name";
    @NonNull
    private static final String SIZE_KEY = "size";
    private static final String TAG = "StorageMetadata";
    @NonNull
    private static final String TIME_CREATED_KEY = "timeCreated";
    @NonNull
    private static final String TIME_UPDATED_KEY = "updated";
    private String mBucket;
    private MetadataValue<String> mCacheControl;
    private MetadataValue<String> mContentDisposition;
    private MetadataValue<String> mContentEncoding;
    private MetadataValue<String> mContentLanguage;
    private MetadataValue<String> mContentType;
    private String mCreationTime;
    private MetadataValue<Map<String, String>> mCustomMetadata;
    private String mGeneration;
    private String mMD5Hash;
    private String mMetadataGeneration;
    private String mPath;
    private long mSize;
    private FirebaseStorage mStorage;
    private StorageReference mStorageRef;
    private String mUpdatedTime;

    @PublicApi
    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    public static class Builder {
        boolean mFromJSON;
        StorageMetadata mMetadata;

        @PublicApi
        public Builder() {
            this.mMetadata = new StorageMetadata();
        }

        @PublicApi
        public Builder(StorageMetadata storageMetadata) {
            this.mMetadata = new StorageMetadata(false);
        }

        Builder(JSONObject jSONObject, StorageReference storageReference) throws JSONException {
            this(jSONObject);
            this.mMetadata.mStorageRef = storageReference;
        }

        Builder(JSONObject jSONObject) throws JSONException {
            this.mMetadata = new StorageMetadata();
            if (jSONObject != null) {
                parseJSON(jSONObject);
                this.mFromJSON = true;
            }
        }

        @Nullable
        private String extractString(JSONObject jSONObject, String str) throws JSONException {
            return (!jSONObject.has(str) || jSONObject.isNull(str)) ? null : jSONObject.getString(str);
        }

        private void parseJSON(JSONObject jSONObject) throws JSONException {
            this.mMetadata.mGeneration = jSONObject.optString(StorageMetadata.GENERATION_KEY);
            this.mMetadata.mPath = jSONObject.optString("name");
            this.mMetadata.mBucket = jSONObject.optString(StorageMetadata.BUCKET_KEY);
            this.mMetadata.mMetadataGeneration = jSONObject.optString(StorageMetadata.META_GENERATION_KEY);
            this.mMetadata.mCreationTime = jSONObject.optString(StorageMetadata.TIME_CREATED_KEY);
            this.mMetadata.mUpdatedTime = jSONObject.optString(StorageMetadata.TIME_UPDATED_KEY);
            this.mMetadata.mSize = jSONObject.optLong(StorageMetadata.SIZE_KEY);
            this.mMetadata.mMD5Hash = jSONObject.optString(StorageMetadata.MD5_HASH_KEY);
            String str = "metadata";
            if (jSONObject.has(str) && !jSONObject.isNull(str)) {
                JSONObject jSONObject2 = jSONObject.getJSONObject(str);
                Iterator keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    String str2 = (String) keys.next();
                    setCustomMetadata(str2, jSONObject2.getString(str2));
                }
            }
            str = extractString(jSONObject, StorageMetadata.CONTENT_TYPE_KEY);
            if (str != null) {
                setContentType(str);
            }
            str = extractString(jSONObject, StorageMetadata.CACHE_CONTROL);
            if (str != null) {
                setCacheControl(str);
            }
            str = extractString(jSONObject, StorageMetadata.CONTENT_DISPOSITION);
            if (str != null) {
                setContentDisposition(str);
            }
            str = extractString(jSONObject, StorageMetadata.CONTENT_ENCODING);
            if (str != null) {
                setContentEncoding(str);
            }
            String extractString = extractString(jSONObject, StorageMetadata.CONTENT_LANGUAGE);
            if (extractString != null) {
                setContentLanguage(extractString);
            }
        }

        @PublicApi
        public StorageMetadata build() {
            return new StorageMetadata(this.mFromJSON);
        }

        @PublicApi
        public Builder setContentLanguage(@Nullable String str) {
            this.mMetadata.mContentLanguage = MetadataValue.withUserValue(str);
            return this;
        }

        @PublicApi
        public Builder setContentEncoding(@Nullable String str) {
            this.mMetadata.mContentEncoding = MetadataValue.withUserValue(str);
            return this;
        }

        @PublicApi
        public Builder setContentDisposition(@Nullable String str) {
            this.mMetadata.mContentDisposition = MetadataValue.withUserValue(str);
            return this;
        }

        @PublicApi
        public Builder setCacheControl(@Nullable String str) {
            this.mMetadata.mCacheControl = MetadataValue.withUserValue(str);
            return this;
        }

        @PublicApi
        public Builder setCustomMetadata(String str, String str2) {
            if (!this.mMetadata.mCustomMetadata.isUserProvided()) {
                this.mMetadata.mCustomMetadata = MetadataValue.withUserValue(new HashMap());
            }
            ((Map) this.mMetadata.mCustomMetadata.getValue()).put(str, str2);
            return this;
        }

        @PublicApi
        public Builder setContentType(@Nullable String str) {
            this.mMetadata.mContentType = MetadataValue.withUserValue(str);
            return this;
        }
    }

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    private static class MetadataValue<T> {
        private final boolean userProvided;
        @Nullable
        private final T value;

        MetadataValue(@Nullable T t, boolean z) {
            this.userProvided = z;
            this.value = t;
        }

        static <T> MetadataValue<T> withDefaultValue(T t) {
            return new MetadataValue(t, false);
        }

        static <T> MetadataValue<T> withUserValue(@Nullable T t) {
            return new MetadataValue(t, true);
        }

        boolean isUserProvided() {
            return this.userProvided;
        }

        @Nullable
        T getValue() {
            return this.value;
        }
    }

    @PublicApi
    public StorageMetadata() {
        this.mPath = null;
        this.mStorage = null;
        this.mStorageRef = null;
        this.mBucket = null;
        this.mGeneration = null;
        String str = "";
        this.mContentType = MetadataValue.withDefaultValue(str);
        this.mMetadataGeneration = null;
        this.mCreationTime = null;
        this.mUpdatedTime = null;
        this.mMD5Hash = null;
        this.mCacheControl = MetadataValue.withDefaultValue(str);
        this.mContentDisposition = MetadataValue.withDefaultValue(str);
        this.mContentEncoding = MetadataValue.withDefaultValue(str);
        this.mContentLanguage = MetadataValue.withDefaultValue(str);
        this.mCustomMetadata = MetadataValue.withDefaultValue(Collections.emptyMap());
    }

    private StorageMetadata(@NonNull StorageMetadata storageMetadata, boolean z) {
        this.mPath = null;
        this.mStorage = null;
        this.mStorageRef = null;
        this.mBucket = null;
        this.mGeneration = null;
        String str = "";
        this.mContentType = MetadataValue.withDefaultValue(str);
        this.mMetadataGeneration = null;
        this.mCreationTime = null;
        this.mUpdatedTime = null;
        this.mMD5Hash = null;
        this.mCacheControl = MetadataValue.withDefaultValue(str);
        this.mContentDisposition = MetadataValue.withDefaultValue(str);
        this.mContentEncoding = MetadataValue.withDefaultValue(str);
        this.mContentLanguage = MetadataValue.withDefaultValue(str);
        this.mCustomMetadata = MetadataValue.withDefaultValue(Collections.emptyMap());
        Preconditions.checkNotNull(storageMetadata);
        this.mPath = storageMetadata.mPath;
        this.mStorage = storageMetadata.mStorage;
        this.mStorageRef = storageMetadata.mStorageRef;
        this.mBucket = storageMetadata.mBucket;
        this.mContentType = storageMetadata.mContentType;
        this.mCacheControl = storageMetadata.mCacheControl;
        this.mContentDisposition = storageMetadata.mContentDisposition;
        this.mContentEncoding = storageMetadata.mContentEncoding;
        this.mContentLanguage = storageMetadata.mContentLanguage;
        this.mCustomMetadata = storageMetadata.mCustomMetadata;
        if (z) {
            this.mMD5Hash = storageMetadata.mMD5Hash;
            this.mSize = storageMetadata.mSize;
            this.mUpdatedTime = storageMetadata.mUpdatedTime;
            this.mCreationTime = storageMetadata.mCreationTime;
            this.mMetadataGeneration = storageMetadata.mMetadataGeneration;
            this.mGeneration = storageMetadata.mGeneration;
        }
    }

    @PublicApi
    public String getContentType() {
        return (String) this.mContentType.getValue();
    }

    @PublicApi
    public String getCustomMetadata(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (String) ((Map) this.mCustomMetadata.getValue()).get(str);
    }

    @PublicApi
    @NonNull
    public Set<String> getCustomMetadataKeys() {
        return ((Map) this.mCustomMetadata.getValue()).keySet();
    }

    @PublicApi
    @NonNull
    public String getPath() {
        String str = this.mPath;
        return str != null ? str : "";
    }

    @PublicApi
    @Nullable
    public String getName() {
        String path = getPath();
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        int lastIndexOf = path.lastIndexOf(47);
        if (lastIndexOf != -1) {
            path = path.substring(lastIndexOf + 1);
        }
        return path;
    }

    @PublicApi
    @Nullable
    public String getBucket() {
        return this.mBucket;
    }

    @PublicApi
    @Nullable
    public String getGeneration() {
        return this.mGeneration;
    }

    @PublicApi
    @Nullable
    public String getMetadataGeneration() {
        return this.mMetadataGeneration;
    }

    @PublicApi
    public long getCreationTimeMillis() {
        return Util.parseDateTime(this.mCreationTime);
    }

    @PublicApi
    public long getUpdatedTimeMillis() {
        return Util.parseDateTime(this.mUpdatedTime);
    }

    @PublicApi
    public long getSizeBytes() {
        return this.mSize;
    }

    @PublicApi
    @Nullable
    public String getMd5Hash() {
        return this.mMD5Hash;
    }

    @PublicApi
    @Nullable
    public String getCacheControl() {
        return (String) this.mCacheControl.getValue();
    }

    @PublicApi
    @Nullable
    public String getContentDisposition() {
        return (String) this.mContentDisposition.getValue();
    }

    @PublicApi
    @Nullable
    public String getContentEncoding() {
        return (String) this.mContentEncoding.getValue();
    }

    @PublicApi
    @Nullable
    public String getContentLanguage() {
        return (String) this.mContentLanguage.getValue();
    }

    @PublicApi
    @Nullable
    public StorageReference getReference() {
        if (this.mStorageRef != null || this.mStorage == null) {
            return this.mStorageRef;
        }
        String bucket = getBucket();
        String path = getPath();
        if (TextUtils.isEmpty(bucket) || TextUtils.isEmpty(path)) {
            return null;
        }
        try {
            bucket = new android.net.Uri.Builder().scheme("gs").authority(bucket).encodedPath(SlashUtil.preserveSlashEncode(path)).build();
            return new StorageReference(bucket, this.mStorage);
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to create a valid default Uri. ");
            stringBuilder.append(bucket);
            stringBuilder.append(path);
            Log.e(TAG, stringBuilder.toString(), e);
            throw new IllegalStateException(e);
        }
    }

    @NonNull
    JSONObject createJSONObject() throws JSONException {
        Map hashMap = new HashMap();
        if (this.mContentType.isUserProvided()) {
            hashMap.put(CONTENT_TYPE_KEY, getContentType());
        }
        if (this.mCustomMetadata.isUserProvided()) {
            hashMap.put("metadata", new JSONObject((Map) this.mCustomMetadata.getValue()));
        }
        if (this.mCacheControl.isUserProvided()) {
            hashMap.put(CACHE_CONTROL, getCacheControl());
        }
        if (this.mContentDisposition.isUserProvided()) {
            hashMap.put(CONTENT_DISPOSITION, getContentDisposition());
        }
        if (this.mContentEncoding.isUserProvided()) {
            hashMap.put(CONTENT_ENCODING, getContentEncoding());
        }
        if (this.mContentLanguage.isUserProvided()) {
            hashMap.put(CONTENT_LANGUAGE, getContentLanguage());
        }
        return new JSONObject(hashMap);
    }
}
