package com.facebook.soloader;

import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;

public class DirectorySoSource extends SoSource {
    public static final int ON_LD_LIBRARY_PATH = 2;
    public static final int RESOLVE_DEPENDENCIES = 1;
    protected final int flags;
    protected final File soDirectory;

    public DirectorySoSource(File file, int i) {
        this.soDirectory = file;
        this.flags = i;
    }

    public int loadLibrary(String str, int i, ThreadPolicy threadPolicy) throws IOException {
        return loadLibraryFrom(str, i, this.soDirectory, threadPolicy);
    }

    protected int loadLibraryFrom(String str, int i, File file, ThreadPolicy threadPolicy) throws IOException {
        File file2 = new File(file, str);
        String str2 = "SoLoader";
        StringBuilder stringBuilder;
        if (file2.exists()) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(str);
            stringBuilder2.append(" found on ");
            stringBuilder2.append(file.getCanonicalPath());
            Log.d(str2, stringBuilder2.toString());
            if ((i & 1) == 0 || (this.flags & 2) == 0) {
                if ((this.flags & 1) != 0) {
                    loadDependencies(file2, i, threadPolicy);
                } else {
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Not resolving dependencies for ");
                    stringBuilder3.append(str);
                    Log.d(str2, stringBuilder3.toString());
                }
                try {
                    SoLoader.sSoFileLoader.load(file2.getAbsolutePath(), i);
                    return 1;
                } catch (UnsatisfiedLinkError e) {
                    if (e.getMessage().contains("bad ELF magic")) {
                        Log.d(str2, "Corrupted lib file detected");
                        return 3;
                    }
                    throw e;
                }
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(" loaded implicitly");
            Log.d(str2, stringBuilder.toString());
            return 2;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" not found on ");
        stringBuilder.append(file.getCanonicalPath());
        Log.d(str2, stringBuilder.toString());
        return 0;
    }

    private void loadDependencies(File file, int i, ThreadPolicy threadPolicy) throws IOException {
        String[] dependencies = getDependencies(file);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Loading lib dependencies: ");
        stringBuilder.append(Arrays.toString(dependencies));
        Log.d("SoLoader", stringBuilder.toString());
        for (String str : dependencies) {
            if (!str.startsWith("/")) {
                SoLoader.loadLibraryBySoName(str, i | 1, threadPolicy);
            }
        }
    }

    private static String[] getDependencies(File file) throws IOException {
        if (SoLoader.SYSTRACE_LIBRARY_LOADING) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SoLoader.getElfDependencies[");
            stringBuilder.append(file.getName());
            stringBuilder.append("]");
            Api18TraceUtils.beginTraceSection(stringBuilder.toString());
        }
        try {
            String[] extract_DT_NEEDED = MinElf.extract_DT_NEEDED(file);
            return extract_DT_NEEDED;
        } finally {
            if (SoLoader.SYSTRACE_LIBRARY_LOADING) {
                Api18TraceUtils.endSection();
            }
        }
    }

    @Nullable
    public File unpackLibrary(String str) throws IOException {
        File file = new File(this.soDirectory, str);
        return file.exists() ? file : null;
    }

    public void addToLdLibraryPath(Collection<String> collection) {
        collection.add(this.soDirectory.getAbsolutePath());
    }

    public String toString() {
        String valueOf;
        try {
            valueOf = String.valueOf(this.soDirectory.getCanonicalPath());
        } catch (IOException unused) {
            valueOf = this.soDirectory.getName();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getName());
        stringBuilder.append("[root = ");
        stringBuilder.append(valueOf);
        stringBuilder.append(" flags = ");
        stringBuilder.append(this.flags);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}
