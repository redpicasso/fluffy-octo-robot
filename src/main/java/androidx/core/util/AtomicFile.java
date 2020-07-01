package androidx.core.util;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile {
    private final File mBackupName;
    private final File mBaseName;

    public AtomicFile(@NonNull File file) {
        this.mBaseName = file;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getPath());
        stringBuilder.append(".bak");
        this.mBackupName = new File(stringBuilder.toString());
    }

    @NonNull
    public File getBaseFile() {
        return this.mBaseName;
    }

    public void delete() {
        this.mBaseName.delete();
        this.mBackupName.delete();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:androidx.core.util.AtomicFile.startWrite():java.io.FileOutputStream, dom blocks: []
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    @androidx.annotation.NonNull
    public java.io.FileOutputStream startWrite() throws java.io.IOException {
        /*
        r3 = this;
        r0 = r3.mBaseName;
        r0 = r0.exists();
        if (r0 == 0) goto L_0x0042;
    L_0x0008:
        r0 = r3.mBackupName;
        r0 = r0.exists();
        if (r0 != 0) goto L_0x003d;
    L_0x0010:
        r0 = r3.mBaseName;
        r1 = r3.mBackupName;
        r0 = r0.renameTo(r1);
        if (r0 != 0) goto L_0x0042;
    L_0x001a:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "Couldn't rename file ";
        r0.append(r1);
        r1 = r3.mBaseName;
        r0.append(r1);
        r1 = " to backup file ";
        r0.append(r1);
        r1 = r3.mBackupName;
        r0.append(r1);
        r0 = r0.toString();
        r1 = "AtomicFile";
        android.util.Log.w(r1, r0);
        goto L_0x0042;
    L_0x003d:
        r0 = r3.mBaseName;
        r0.delete();
    L_0x0042:
        r0 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x004a }
        r1 = r3.mBaseName;	 Catch:{ FileNotFoundException -> 0x004a }
        r0.<init>(r1);	 Catch:{ FileNotFoundException -> 0x004a }
        goto L_0x005e;
        r0 = r3.mBaseName;
        r0 = r0.getParentFile();
        r0 = r0.mkdirs();
        if (r0 == 0) goto L_0x0078;
    L_0x0057:
        r0 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x005f }
        r1 = r3.mBaseName;	 Catch:{ FileNotFoundException -> 0x005f }
        r0.<init>(r1);	 Catch:{ FileNotFoundException -> 0x005f }
    L_0x005e:
        return r0;
    L_0x005f:
        r0 = new java.io.IOException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Couldn't create ";
        r1.append(r2);
        r2 = r3.mBaseName;
        r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x0078:
        r0 = new java.io.IOException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Couldn't create directory ";
        r1.append(r2);
        r2 = r3.mBaseName;
        r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.util.AtomicFile.startWrite():java.io.FileOutputStream");
    }

    public void finishWrite(@Nullable FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            sync(fileOutputStream);
            try {
                fileOutputStream.close();
                this.mBackupName.delete();
            } catch (Throwable e) {
                Log.w("AtomicFile", "finishWrite: Got exception:", e);
            }
        }
    }

    public void failWrite(@Nullable FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            sync(fileOutputStream);
            try {
                fileOutputStream.close();
                this.mBaseName.delete();
                this.mBackupName.renameTo(this.mBaseName);
            } catch (Throwable e) {
                Log.w("AtomicFile", "failWrite: Got exception:", e);
            }
        }
    }

    @NonNull
    public FileInputStream openRead() throws FileNotFoundException {
        if (this.mBackupName.exists()) {
            this.mBaseName.delete();
            this.mBackupName.renameTo(this.mBaseName);
        }
        return new FileInputStream(this.mBaseName);
    }

    @NonNull
    public byte[] readFully() throws IOException {
        FileInputStream openRead = openRead();
        try {
            byte[] bArr = new byte[openRead.available()];
            int i = 0;
            while (true) {
                int read = openRead.read(bArr, i, bArr.length - i);
                if (read <= 0) {
                    break;
                }
                i += read;
                read = openRead.available();
                if (read > bArr.length - i) {
                    Object obj = new byte[(read + i)];
                    System.arraycopy(bArr, 0, obj, 0, i);
                    bArr = obj;
                }
            }
            return bArr;
        } finally {
            openRead.close();
        }
    }

    private static boolean sync(@NonNull FileOutputStream fileOutputStream) {
        try {
            fileOutputStream.getFD().sync();
            return true;
        } catch (IOException unused) {
            return false;
        }
    }
}
