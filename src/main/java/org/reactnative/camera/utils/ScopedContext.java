package org.reactnative.camera.utils;

import android.content.Context;
import java.io.File;

public class ScopedContext {
    private File cacheDirectory = null;

    public ScopedContext(Context context) {
        createCacheDirectory(context);
    }

    public void createCacheDirectory(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getCacheDir());
        stringBuilder.append("/Camera/");
        this.cacheDirectory = new File(stringBuilder.toString());
    }

    public File getCacheDirectory() {
        return this.cacheDirectory;
    }
}
