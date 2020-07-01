package com.google.firebase.storage;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Status;
import com.google.common.net.HttpHeaders;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.storage.StorageTask.SnapshotBase;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.google.firebase.storage.network.GetNetworkRequest;
import com.google.firebase.storage.network.NetworkRequest;
import com.google.logging.type.LogSeverity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@PublicApi
/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class FileDownloadTask extends StorageTask<TaskSnapshot> {
    static final int PREFERRED_CHUNK_SIZE = 262144;
    private static final String TAG = "FileDownloadTask";
    private long mBytesDownloaded;
    private final Uri mDestinationFile;
    private String mETagVerification = null;
    private volatile Exception mException = null;
    private int mResultCode;
    private long mResumeOffset = 0;
    private ExponentialBackoffSender mSender;
    private StorageReference mStorageRef;
    private long mTotalBytes = -1;

    @PublicApi
    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    public class TaskSnapshot extends SnapshotBase {
        private final long mBytesDownloaded;

        TaskSnapshot(Exception exception, long j) {
            super(exception);
            this.mBytesDownloaded = j;
        }

        @PublicApi
        public long getBytesTransferred() {
            return this.mBytesDownloaded;
        }

        @PublicApi
        public long getTotalByteCount() {
            return FileDownloadTask.this.getTotalBytes();
        }
    }

    private boolean isValidHttpResponseCode(int i) {
        return i == 308 || (i >= LogSeverity.INFO_VALUE && i < 300);
    }

    FileDownloadTask(@NonNull StorageReference storageReference, @NonNull Uri uri) {
        this.mStorageRef = storageReference;
        this.mDestinationFile = uri;
        FirebaseStorage storage = this.mStorageRef.getStorage();
        this.mSender = new ExponentialBackoffSender(storage.getApp().getApplicationContext(), storage.getAuthProvider(), storage.getMaxDownloadRetryTimeMillis());
    }

    long getDownloadedSizeInBytes() {
        return this.mBytesDownloaded;
    }

    long getTotalBytes() {
        return this.mTotalBytes;
    }

    @NonNull
    StorageReference getStorage() {
        return this.mStorageRef;
    }

    protected void schedule() {
        StorageTaskScheduler.getInstance().scheduleDownload(getRunnable());
    }

    @NonNull
    TaskSnapshot snapStateImpl() {
        return new TaskSnapshot(StorageException.fromExceptionAndHttpCode(this.mException, this.mResultCode), this.mBytesDownloaded + this.mResumeOffset);
    }

    private int fillBuffer(InputStream inputStream, byte[] bArr) {
        int i = 0;
        Object obj = null;
        while (i != bArr.length) {
            try {
                int read = inputStream.read(bArr, i, bArr.length - i);
                if (read == -1) {
                    break;
                }
                obj = 1;
                i += read;
            } catch (Exception e) {
                this.mException = e;
            }
        }
        return obj != null ? i : -1;
    }

    private boolean processResponse(NetworkRequest networkRequest) throws IOException {
        InputStream stream = networkRequest.getStream();
        if (stream != null) {
            OutputStream fileOutputStream;
            File file = new File(this.mDestinationFile.getPath());
            boolean exists = file.exists();
            String str = TAG;
            if (!exists) {
                if (this.mResumeOffset > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("The file downloading to has been deleted:");
                    stringBuilder.append(file.getAbsolutePath());
                    Log.e(str, stringBuilder.toString());
                    throw new IllegalStateException("expected a file to resume from.");
                } else if (!file.createNewFile()) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("unable to create file:");
                    stringBuilder2.append(file.getAbsolutePath());
                    Log.w(str, stringBuilder2.toString());
                }
            }
            exists = true;
            if (this.mResumeOffset > 0) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Resuming download file ");
                stringBuilder3.append(file.getAbsolutePath());
                stringBuilder3.append(" at ");
                stringBuilder3.append(this.mResumeOffset);
                Log.d(str, stringBuilder3.toString());
                fileOutputStream = new FileOutputStream(file, true);
            } else {
                fileOutputStream = new FileOutputStream(file);
            }
            try {
                byte[] bArr = new byte[262144];
                while (exists) {
                    int fillBuffer = fillBuffer(stream, bArr);
                    if (fillBuffer == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, fillBuffer);
                    this.mBytesDownloaded += (long) fillBuffer;
                    if (this.mException != null) {
                        Log.d(str, "Exception occurred during file download. Retrying.", this.mException);
                        this.mException = null;
                        exists = false;
                    }
                    if (!tryChangeState(4, false)) {
                        exists = false;
                    }
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                stream.close();
                return exists;
            } catch (Throwable th) {
                fileOutputStream.flush();
                fileOutputStream.close();
                stream.close();
            }
        } else {
            this.mException = new IllegalStateException("Unable to open Firebase Storage stream.");
            return false;
        }
    }

    void run() {
        if (this.mException != null) {
            tryChangeState(64, false);
        } else if (tryChangeState(4, false)) {
            do {
                this.mBytesDownloaded = 0;
                this.mException = null;
                this.mSender.reset();
                NetworkRequest getNetworkRequest = new GetNetworkRequest(this.mStorageRef.getStorageUri(), this.mStorageRef.getApp(), this.mResumeOffset);
                this.mSender.sendWithExponentialBackoff(getNetworkRequest, false);
                this.mResultCode = getNetworkRequest.getResultCode();
                this.mException = getNetworkRequest.getException() != null ? getNetworkRequest.getException() : this.mException;
                Object obj = 1;
                boolean z = isValidHttpResponseCode(this.mResultCode) && this.mException == null && getInternalState() == 4;
                String str = TAG;
                if (z) {
                    this.mTotalBytes = (long) getNetworkRequest.getResultingContentLength();
                    Object resultString = getNetworkRequest.getResultString(HttpHeaders.ETAG);
                    if (!TextUtils.isEmpty(resultString)) {
                        String str2 = this.mETagVerification;
                        if (!(str2 == null || str2.equals(resultString))) {
                            Log.w(str, "The file at the server has changed.  Restarting from the beginning.");
                            this.mResumeOffset = 0;
                            this.mETagVerification = null;
                            getNetworkRequest.performRequestEnd();
                            schedule();
                            return;
                        }
                    }
                    this.mETagVerification = resultString;
                    try {
                        z = processResponse(getNetworkRequest);
                    } catch (Throwable e) {
                        Log.e(str, "Exception occurred during file write.  Aborting.", e);
                        this.mException = e;
                    }
                }
                getNetworkRequest.performRequestEnd();
                if (!(z && this.mException == null && getInternalState() == 4)) {
                    obj = null;
                }
                if (obj != null) {
                    tryChangeState(128, false);
                    return;
                }
                File file = new File(this.mDestinationFile.getPath());
                if (file.exists()) {
                    this.mResumeOffset = file.length();
                } else {
                    this.mResumeOffset = 0;
                }
                if (getInternalState() == 8) {
                    tryChangeState(16, false);
                    return;
                } else if (getInternalState() == 32) {
                    if (!tryChangeState(256, false)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unable to change download task to final state from ");
                        stringBuilder.append(getInternalState());
                        Log.w(str, stringBuilder.toString());
                    }
                    return;
                }
            } while (this.mBytesDownloaded > 0);
            tryChangeState(64, false);
        }
    }

    @PublicApi
    protected void onCanceled() {
        this.mSender.cancel();
        this.mException = StorageException.fromErrorStatus(Status.RESULT_CANCELED);
    }
}
