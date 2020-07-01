package com.facebook.soloader;

import android.util.Log;
import java.util.List;
import javax.annotation.Nullable;

public abstract class NativeLibrary {
    private static final String TAG = "com.facebook.soloader.NativeLibrary";
    private boolean mLibrariesLoaded = false;
    @Nullable
    private List<String> mLibraryNames;
    @Nullable
    private volatile UnsatisfiedLinkError mLinkError = null;
    private Boolean mLoadLibraries = Boolean.valueOf(true);
    private final Object mLock = new Object();

    protected void initialNativeCheck() throws UnsatisfiedLinkError {
    }

    protected NativeLibrary(List<String> list) {
        this.mLibraryNames = list;
    }

    @Nullable
    public boolean loadLibraries() {
        synchronized (this.mLock) {
            boolean z;
            if (this.mLoadLibraries.booleanValue()) {
                try {
                    if (this.mLibraryNames != null) {
                        for (String loadLibrary : this.mLibraryNames) {
                            SoLoader.loadLibrary(loadLibrary);
                        }
                    }
                    initialNativeCheck();
                    this.mLibrariesLoaded = true;
                    this.mLibraryNames = null;
                } catch (Throwable e) {
                    Log.e(TAG, "Failed to load native lib (initial check): ", e);
                    this.mLinkError = e;
                    this.mLibrariesLoaded = false;
                } catch (Throwable e2) {
                    Log.e(TAG, "Failed to load native lib (other error): ", e2);
                    this.mLinkError = new UnsatisfiedLinkError("Failed loading libraries");
                    this.mLinkError.initCause(e2);
                    this.mLibrariesLoaded = false;
                }
                this.mLoadLibraries = Boolean.valueOf(false);
                z = this.mLibrariesLoaded;
                return z;
            }
            z = this.mLibrariesLoaded;
            return z;
        }
    }

    public void ensureLoaded() throws UnsatisfiedLinkError {
        if (!loadLibraries()) {
            throw this.mLinkError;
        }
    }

    @Nullable
    public UnsatisfiedLinkError getError() {
        return this.mLinkError;
    }
}
