package com.google.firebase.storage;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.common.net.HttpHeaders;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.storage.StorageTask.SnapshotBase;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.google.firebase.storage.network.GetNetworkRequest;
import com.google.firebase.storage.network.NetworkRequest;
import com.google.logging.type.LogSeverity;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

@PublicApi
/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class StreamDownloadTask extends StorageTask<TaskSnapshot> {
    static final long PREFERRED_CHUNK_SIZE = 262144;
    private static final String TAG = "StreamDownloadTask";
    private long mBytesDownloaded;
    private long mBytesDownloadedSnapped;
    private String mETagVerification;
    private volatile Exception mException = null;
    private InputStream mInputStream;
    private StreamProcessor mProcessor;
    private NetworkRequest mRequest;
    private volatile int mResultCode = 0;
    private ExponentialBackoffSender mSender;
    private StorageReference mStorageRef;
    private long mTotalBytes = -1;

    @PublicApi
    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    public interface StreamProcessor {
        void doInBackground(TaskSnapshot taskSnapshot, InputStream inputStream) throws IOException;
    }

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    static class StreamProgressWrapper extends InputStream {
        private long mDownloadedBytes;
        private Callable<InputStream> mInputStreamCallable;
        private long mLastExceptionPosition;
        @Nullable
        private StreamDownloadTask mParentTask;
        private boolean mStreamClosed;
        private IOException mTemporaryException;
        @Nullable
        private InputStream mWrappedStream;

        @PublicApi
        public void mark(int i) {
        }

        @PublicApi
        public boolean markSupported() {
            return false;
        }

        StreamProgressWrapper(@NonNull Callable<InputStream> callable, @Nullable StreamDownloadTask streamDownloadTask) {
            this.mParentTask = streamDownloadTask;
            this.mInputStreamCallable = callable;
        }

        private void checkCancel() throws IOException {
            StreamDownloadTask streamDownloadTask = this.mParentTask;
            if (streamDownloadTask != null && streamDownloadTask.getInternalState() == 32) {
                throw new CancelException();
            }
        }

        private void recordDownloadedBytes(long j) {
            StreamDownloadTask streamDownloadTask = this.mParentTask;
            if (streamDownloadTask != null) {
                streamDownloadTask.recordDownloadedBytes(j);
            }
            this.mDownloadedBytes += j;
        }

        private boolean ensureStream() throws IOException {
            checkCancel();
            if (this.mTemporaryException != null) {
                try {
                    if (this.mWrappedStream != null) {
                        this.mWrappedStream.close();
                    }
                } catch (IOException unused) {
                    this.mWrappedStream = null;
                    long j = this.mLastExceptionPosition;
                    long j2 = this.mDownloadedBytes;
                    String str = StreamDownloadTask.TAG;
                    if (j == j2) {
                        Log.i(str, "Encountered exception during stream operation. Aborting.", this.mTemporaryException);
                        return false;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Encountered exception during stream operation. Retrying at ");
                    stringBuilder.append(this.mDownloadedBytes);
                    Log.i(str, stringBuilder.toString(), this.mTemporaryException);
                    this.mLastExceptionPosition = this.mDownloadedBytes;
                    this.mTemporaryException = null;
                }
            }
            if (this.mStreamClosed) {
                throw new IOException("Can't perform operation on closed stream");
            }
            if (this.mWrappedStream == null) {
                try {
                    this.mWrappedStream = (InputStream) this.mInputStreamCallable.call();
                } catch (Throwable e) {
                    if (e instanceof IOException) {
                        throw ((IOException) e);
                    }
                    throw new IOException("Unable to open stream", e);
                }
            }
            return true;
        }

        @PublicApi
        public int read() throws IOException {
            while (ensureStream()) {
                try {
                    int read = this.mWrappedStream.read();
                    if (read != -1) {
                        recordDownloadedBytes(1);
                    }
                    return read;
                } catch (IOException e) {
                    this.mTemporaryException = e;
                }
            }
            throw this.mTemporaryException;
        }

        @PublicApi
        public int available() throws IOException {
            while (ensureStream()) {
                try {
                    return this.mWrappedStream.available();
                } catch (IOException e) {
                    this.mTemporaryException = e;
                }
            }
            throw this.mTemporaryException;
        }

        @PublicApi
        public void close() throws IOException {
            InputStream inputStream = this.mWrappedStream;
            if (inputStream != null) {
                inputStream.close();
            }
            this.mStreamClosed = true;
            StreamDownloadTask streamDownloadTask = this.mParentTask;
            if (!(streamDownloadTask == null || streamDownloadTask.mRequest == null)) {
                this.mParentTask.mRequest.performRequestEnd();
                this.mParentTask.mRequest = null;
            }
            checkCancel();
        }

        @PublicApi
        public int read(@NonNull byte[] bArr, int i, int i2) throws IOException {
            int i3 = 0;
            while (ensureStream()) {
                int read;
                while (((long) i2) > 262144) {
                    try {
                        read = this.mWrappedStream.read(bArr, i, 262144);
                        if (read == -1) {
                            if (i3 == 0) {
                                i3 = -1;
                            }
                            return i3;
                        }
                        i3 += read;
                        i += read;
                        i2 -= read;
                        recordDownloadedBytes((long) read);
                        checkCancel();
                    } catch (IOException e) {
                        this.mTemporaryException = e;
                    }
                }
                if (i2 > 0) {
                    read = this.mWrappedStream.read(bArr, i, i2);
                    if (read == -1) {
                        if (i3 == 0) {
                            i3 = -1;
                        }
                        return i3;
                    }
                    i += read;
                    i3 += read;
                    i2 -= read;
                    recordDownloadedBytes((long) read);
                }
                if (i2 == 0) {
                    return i3;
                }
            }
            throw this.mTemporaryException;
        }

        @PublicApi
        public long skip(long j) throws IOException {
            long j2 = j;
            j = 0;
            while (ensureStream()) {
                long skip;
                while (j2 > 262144) {
                    try {
                        skip = this.mWrappedStream.skip(262144);
                        if (skip < 0) {
                            if (j == 0) {
                                j = -1;
                            }
                            return j;
                        }
                        j += skip;
                        j2 -= skip;
                        recordDownloadedBytes(skip);
                        checkCancel();
                    } catch (IOException e) {
                        this.mTemporaryException = e;
                    }
                }
                if (j2 > 0) {
                    skip = this.mWrappedStream.skip(j2);
                    if (skip < 0) {
                        if (j == 0) {
                            j = -1;
                        }
                        return j;
                    }
                    j += skip;
                    j2 -= skip;
                    recordDownloadedBytes(skip);
                }
                if (j2 == 0) {
                    return j;
                }
            }
            throw this.mTemporaryException;
        }
    }

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
            return StreamDownloadTask.this.getTotalBytes();
        }

        @PublicApi
        public InputStream getStream() {
            return StreamDownloadTask.this.mInputStream;
        }
    }

    private boolean isValidHttpResponseCode(int i) {
        return i == 308 || (i >= LogSeverity.INFO_VALUE && i < 300);
    }

    StreamDownloadTask(@NonNull StorageReference storageReference) {
        this.mStorageRef = storageReference;
        FirebaseStorage storage = this.mStorageRef.getStorage();
        this.mSender = new ExponentialBackoffSender(storage.getApp().getApplicationContext(), storage.getAuthProvider(), storage.getMaxDownloadRetryTimeMillis());
    }

    StreamDownloadTask setStreamProcessor(@NonNull StreamProcessor streamProcessor) {
        Preconditions.checkNotNull(streamProcessor);
        Preconditions.checkState(this.mProcessor == null);
        this.mProcessor = streamProcessor;
        return this;
    }

    @NonNull
    StorageReference getStorage() {
        return this.mStorageRef;
    }

    long getTotalBytes() {
        return this.mTotalBytes;
    }

    void recordDownloadedBytes(long j) {
        this.mBytesDownloaded += j;
        if (this.mBytesDownloadedSnapped + 262144 > this.mBytesDownloaded) {
            return;
        }
        if (getInternalState() == 4) {
            tryChangeState(4, false);
        } else {
            this.mBytesDownloadedSnapped = this.mBytesDownloaded;
        }
    }

    protected void schedule() {
        StorageTaskScheduler.getInstance().scheduleDownload(getRunnable());
    }

    private InputStream createDownloadStream() throws Exception {
        this.mSender.reset();
        NetworkRequest networkRequest = this.mRequest;
        if (networkRequest != null) {
            networkRequest.performRequestEnd();
        }
        this.mRequest = new GetNetworkRequest(this.mStorageRef.getStorageUri(), this.mStorageRef.getApp(), this.mBytesDownloaded);
        boolean z = false;
        this.mSender.sendWithExponentialBackoff(this.mRequest, false);
        this.mResultCode = this.mRequest.getResultCode();
        this.mException = this.mRequest.getException() != null ? this.mRequest.getException() : this.mException;
        if (isValidHttpResponseCode(this.mResultCode) && this.mException == null && getInternalState() == 4) {
            z = true;
        }
        if (z) {
            Object resultString = this.mRequest.getResultString(HttpHeaders.ETAG);
            if (!TextUtils.isEmpty(resultString)) {
                String str = this.mETagVerification;
                if (!(str == null || str.equals(resultString))) {
                    this.mResultCode = 409;
                    throw new IOException("The ETag on the server changed.");
                }
            }
            this.mETagVerification = resultString;
            if (this.mTotalBytes == -1) {
                this.mTotalBytes = (long) this.mRequest.getResultingContentLength();
            }
            return this.mRequest.getStream();
        }
        throw new IOException("Could not open resulting stream.");
    }

    void run() {
        String str = TAG;
        int i = 64;
        if (this.mException != null) {
            tryChangeState(64, false);
        } else if (tryChangeState(4, false)) {
            InputStream streamProgressWrapper = new StreamProgressWrapper(new Callable<InputStream>() {
                public InputStream call() throws Exception {
                    return StreamDownloadTask.this.createDownloadStream();
                }
            }, this);
            this.mInputStream = new BufferedInputStream(streamProgressWrapper);
            try {
                streamProgressWrapper.ensureStream();
                if (this.mProcessor != null) {
                    try {
                        this.mProcessor.doInBackground((TaskSnapshot) snapState(), this.mInputStream);
                    } catch (Throwable e) {
                        Log.w(str, "Exception occurred calling doInBackground.", e);
                        this.mException = e;
                    }
                }
            } catch (Throwable e2) {
                Log.d(str, "Initial opening of Stream failed", e2);
                this.mException = e2;
            }
            if (this.mInputStream == null) {
                this.mRequest.performRequestEnd();
                this.mRequest = null;
            }
            Object obj = (this.mException == null && getInternalState() == 4) ? 1 : null;
            if (obj != null) {
                tryChangeState(4, false);
                tryChangeState(128, false);
            } else {
                if (getInternalState() == 32) {
                    i = 256;
                }
                if (!tryChangeState(i, false)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to change download task to final state from ");
                    stringBuilder.append(getInternalState());
                    Log.w(str, stringBuilder.toString());
                }
            }
        }
    }

    public boolean resume() {
        throw new UnsupportedOperationException("this operation is not supported on StreamDownloadTask.");
    }

    public boolean pause() {
        throw new UnsupportedOperationException("this operation is not supported on StreamDownloadTask.");
    }

    @NonNull
    TaskSnapshot snapStateImpl() {
        return new TaskSnapshot(StorageException.fromExceptionAndHttpCode(this.mException, this.mResultCode), this.mBytesDownloadedSnapped);
    }

    protected void onCanceled() {
        this.mSender.cancel();
        this.mException = StorageException.fromErrorStatus(Status.RESULT_CANCELED);
    }

    protected void onProgress() {
        this.mBytesDownloadedSnapped = this.mBytesDownloaded;
    }
}
