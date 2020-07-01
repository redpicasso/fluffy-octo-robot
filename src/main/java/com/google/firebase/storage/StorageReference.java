package com.google.firebase.storage;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApp;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.storage.StreamDownloadTask.StreamProcessor;
import com.google.firebase.storage.StreamDownloadTask.TaskSnapshot;
import com.google.firebase.storage.internal.SlashUtil;
import java.io.File;
import java.io.InputStream;
import java.util.List;

@PublicApi
/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class StorageReference {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = "StorageReference";
    private final FirebaseStorage mFirebaseStorage;
    private final Uri mStorageUri;

    StorageReference(@NonNull Uri uri, @NonNull FirebaseStorage firebaseStorage) {
        boolean z = true;
        Preconditions.checkArgument(uri != null, "storageUri cannot be null");
        if (firebaseStorage == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "FirebaseApp cannot be null");
        this.mStorageUri = uri;
        this.mFirebaseStorage = firebaseStorage;
    }

    @PublicApi
    @NonNull
    public StorageReference child(@NonNull String str) {
        Preconditions.checkArgument(TextUtils.isEmpty(str) ^ 1, "childName cannot be null or empty");
        str = SlashUtil.normalizeSlashes(str);
        try {
            str = this.mStorageUri.buildUpon().appendEncodedPath(SlashUtil.preserveSlashEncode(str)).build();
            return new StorageReference(str, this.mFirebaseStorage);
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to create a valid default Uri. ");
            stringBuilder.append(str);
            Log.e(TAG, stringBuilder.toString(), e);
            throw new IllegalArgumentException("childName");
        }
    }

    @PublicApi
    @Nullable
    public StorageReference getParent() {
        String path = this.mStorageUri.getPath();
        if (!TextUtils.isEmpty(path)) {
            String str = "/";
            if (!path.equals(str)) {
                int lastIndexOf = path.lastIndexOf(47);
                if (lastIndexOf != -1) {
                    str = path.substring(0, lastIndexOf);
                }
                return new StorageReference(this.mStorageUri.buildUpon().path(str).build(), this.mFirebaseStorage);
            }
        }
        return null;
    }

    @PublicApi
    @NonNull
    public StorageReference getRoot() {
        return new StorageReference(this.mStorageUri.buildUpon().path("").build(), this.mFirebaseStorage);
    }

    @PublicApi
    @NonNull
    public String getName() {
        String path = this.mStorageUri.getPath();
        int lastIndexOf = path.lastIndexOf(47);
        return lastIndexOf != -1 ? path.substring(lastIndexOf + 1) : path;
    }

    @PublicApi
    @NonNull
    public String getPath() {
        return this.mStorageUri.getPath();
    }

    @PublicApi
    @NonNull
    public String getBucket() {
        return this.mStorageUri.getAuthority();
    }

    @PublicApi
    @NonNull
    public FirebaseStorage getStorage() {
        return this.mFirebaseStorage;
    }

    @NonNull
    FirebaseApp getApp() {
        return getStorage().getApp();
    }

    @PublicApi
    @NonNull
    public UploadTask putBytes(@NonNull byte[] bArr) {
        Preconditions.checkArgument(bArr != null, "bytes cannot be null");
        UploadTask uploadTask = new UploadTask(this, null, bArr);
        uploadTask.queue();
        return uploadTask;
    }

    @PublicApi
    @NonNull
    public UploadTask putBytes(@NonNull byte[] bArr, @NonNull StorageMetadata storageMetadata) {
        boolean z = true;
        Preconditions.checkArgument(bArr != null, "bytes cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, bArr);
        uploadTask.queue();
        return uploadTask;
    }

    @PublicApi
    @NonNull
    public UploadTask putFile(@NonNull Uri uri) {
        Preconditions.checkArgument(uri != null, "uri cannot be null");
        UploadTask uploadTask = new UploadTask(this, null, uri, null);
        uploadTask.queue();
        return uploadTask;
    }

    @PublicApi
    @NonNull
    public UploadTask putFile(@NonNull Uri uri, @NonNull StorageMetadata storageMetadata) {
        boolean z = true;
        Preconditions.checkArgument(uri != null, "uri cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, uri, null);
        uploadTask.queue();
        return uploadTask;
    }

    @PublicApi
    @NonNull
    public UploadTask putFile(@NonNull Uri uri, @Nullable StorageMetadata storageMetadata, @Nullable Uri uri2) {
        boolean z = true;
        Preconditions.checkArgument(uri != null, "uri cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, uri, uri2);
        uploadTask.queue();
        return uploadTask;
    }

    @PublicApi
    @NonNull
    public UploadTask putStream(@NonNull InputStream inputStream) {
        Preconditions.checkArgument(inputStream != null, "stream cannot be null");
        UploadTask uploadTask = new UploadTask(this, null, inputStream);
        uploadTask.queue();
        return uploadTask;
    }

    @PublicApi
    @NonNull
    public UploadTask putStream(@NonNull InputStream inputStream, @NonNull StorageMetadata storageMetadata) {
        boolean z = true;
        Preconditions.checkArgument(inputStream != null, "stream cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, inputStream);
        uploadTask.queue();
        return uploadTask;
    }

    @PublicApi
    @NonNull
    public List<UploadTask> getActiveUploadTasks() {
        return StorageTaskManager.getInstance().getUploadTasksUnder(this);
    }

    @PublicApi
    @NonNull
    public List<FileDownloadTask> getActiveDownloadTasks() {
        return StorageTaskManager.getInstance().getDownloadTasksUnder(this);
    }

    @PublicApi
    @NonNull
    public Task<StorageMetadata> getMetadata() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        StorageTaskScheduler.getInstance().scheduleCommand(new GetMetadataTask(this, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    @PublicApi
    @NonNull
    public Task<Uri> getDownloadUrl() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        StorageTaskScheduler.getInstance().scheduleCommand(new GetDownloadUrlTask(this, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    @PublicApi
    @NonNull
    public Task<StorageMetadata> updateMetadata(@NonNull StorageMetadata storageMetadata) {
        Preconditions.checkNotNull(storageMetadata);
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        StorageTaskScheduler.getInstance().scheduleCommand(new UpdateMetadataTask(this, taskCompletionSource, storageMetadata));
        return taskCompletionSource.getTask();
    }

    @PublicApi
    @NonNull
    public Task<byte[]> getBytes(final long j) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        StreamDownloadTask streamDownloadTask = new StreamDownloadTask(this);
        streamDownloadTask.setStreamProcessor(new StreamProcessor() {
            /* JADX WARNING: Missing block: B:10:0x002c, code:
            r0.flush();
            r0.setResult(r0.toByteArray());
     */
            @com.google.firebase.annotations.PublicApi
            public void doInBackground(com.google.firebase.storage.StreamDownloadTask.TaskSnapshot r11, java.io.InputStream r12) throws java.io.IOException {
                /*
                r10 = this;
                r11 = "the maximum allowed buffer size was exceeded.";
                r0 = new java.io.ByteArrayOutputStream;	 Catch:{ all -> 0x003c }
                r0.<init>();	 Catch:{ all -> 0x003c }
                r1 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;
                r1 = new byte[r1];	 Catch:{ all -> 0x003c }
                r2 = 0;
                r3 = 0;
            L_0x000d:
                r4 = r1.length;	 Catch:{ all -> 0x003c }
                r4 = r12.read(r1, r2, r4);	 Catch:{ all -> 0x003c }
                r5 = -1;
                if (r4 == r5) goto L_0x002c;
            L_0x0015:
                r3 = r3 + r4;
                r5 = (long) r3;	 Catch:{ all -> 0x003c }
                r7 = r4;	 Catch:{ all -> 0x003c }
                r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1));
                if (r9 > 0) goto L_0x0021;
            L_0x001d:
                r0.write(r1, r2, r4);	 Catch:{ all -> 0x003c }
                goto L_0x000d;
            L_0x0021:
                r0 = "StorageReference";
                android.util.Log.e(r0, r11);	 Catch:{ all -> 0x003c }
                r0 = new java.lang.IndexOutOfBoundsException;	 Catch:{ all -> 0x003c }
                r0.<init>(r11);	 Catch:{ all -> 0x003c }
                throw r0;	 Catch:{ all -> 0x003c }
            L_0x002c:
                r0.flush();	 Catch:{ all -> 0x003c }
                r11 = r0;	 Catch:{ all -> 0x003c }
                r0 = r0.toByteArray();	 Catch:{ all -> 0x003c }
                r11.setResult(r0);	 Catch:{ all -> 0x003c }
                r12.close();
                return;
            L_0x003c:
                r11 = move-exception;
                r12.close();
                throw r11;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.storage.StorageReference.3.doInBackground(com.google.firebase.storage.StreamDownloadTask$TaskSnapshot, java.io.InputStream):void");
            }
        }).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
            @PublicApi
            public void onSuccess(TaskSnapshot taskSnapshot) {
                if (!taskCompletionSource.getTask().isComplete()) {
                    Log.e(StorageReference.TAG, "getBytes 'succeeded', but failed to set a Result.");
                    taskCompletionSource.setException(StorageException.fromErrorStatus(Status.RESULT_INTERNAL_ERROR));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class cls = StorageReference.class;
            }

            @PublicApi
            public void onFailure(@NonNull Exception exception) {
                taskCompletionSource.setException(StorageException.fromExceptionAndHttpCode(exception, 0));
            }
        });
        streamDownloadTask.queue();
        return taskCompletionSource.getTask();
    }

    @PublicApi
    @NonNull
    public FileDownloadTask getFile(@NonNull Uri uri) {
        FileDownloadTask fileDownloadTask = new FileDownloadTask(this, uri);
        fileDownloadTask.queue();
        return fileDownloadTask;
    }

    @PublicApi
    @NonNull
    public FileDownloadTask getFile(@NonNull File file) {
        return getFile(Uri.fromFile(file));
    }

    @PublicApi
    @NonNull
    public StreamDownloadTask getStream() {
        StreamDownloadTask streamDownloadTask = new StreamDownloadTask(this);
        streamDownloadTask.queue();
        return streamDownloadTask;
    }

    @PublicApi
    @NonNull
    public StreamDownloadTask getStream(@NonNull StreamProcessor streamProcessor) {
        StreamDownloadTask streamDownloadTask = new StreamDownloadTask(this);
        streamDownloadTask.setStreamProcessor(streamProcessor);
        streamDownloadTask.queue();
        return streamDownloadTask;
    }

    @PublicApi
    public Task<Void> delete() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        StorageTaskScheduler.getInstance().scheduleCommand(new DeleteStorageTask(this, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    @NonNull
    Uri getStorageUri() {
        return this.mStorageUri;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("gs://");
        stringBuilder.append(this.mStorageUri.getAuthority());
        stringBuilder.append(this.mStorageUri.getEncodedPath());
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof StorageReference) {
            return ((StorageReference) obj).toString().equals(toString());
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }
}
