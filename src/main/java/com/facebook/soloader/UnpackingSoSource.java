package com.facebook.soloader;

import android.content.Context;
import android.os.Parcel;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class UnpackingSoSource extends DirectorySoSource {
    private static final String DEPS_FILE_NAME = "dso_deps";
    private static final String LOCK_FILE_NAME = "dso_lock";
    private static final String MANIFEST_FILE_NAME = "dso_manifest";
    private static final byte MANIFEST_VERSION = (byte) 1;
    private static final byte STATE_CLEAN = (byte) 1;
    private static final byte STATE_DIRTY = (byte) 0;
    private static final String STATE_FILE_NAME = "dso_state";
    private static final String TAG = "fb-UnpackingSoSource";
    @Nullable
    private String[] mAbis;
    protected final Context mContext;
    @Nullable
    protected String mCorruptedLib;
    private final Map<String, Object> mLibsBeingLoaded = new HashMap();

    public static class Dso {
        public final String hash;
        public final String name;

        public Dso(String str, String str2) {
            this.name = str;
            this.hash = str2;
        }
    }

    public static final class DsoManifest {
        public final Dso[] dsos;

        public DsoManifest(Dso[] dsoArr) {
            this.dsos = dsoArr;
        }

        static final DsoManifest read(DataInput dataInput) throws IOException {
            if (dataInput.readByte() == (byte) 1) {
                int readInt = dataInput.readInt();
                if (readInt >= 0) {
                    Dso[] dsoArr = new Dso[readInt];
                    for (int i = 0; i < readInt; i++) {
                        dsoArr[i] = new Dso(dataInput.readUTF(), dataInput.readUTF());
                    }
                    return new DsoManifest(dsoArr);
                }
                throw new RuntimeException("illegal number of shared libraries");
            }
            throw new RuntimeException("wrong dso manifest version");
        }

        public final void write(DataOutput dataOutput) throws IOException {
            dataOutput.writeByte(1);
            dataOutput.writeInt(this.dsos.length);
            int i = 0;
            while (true) {
                Dso[] dsoArr = this.dsos;
                if (i < dsoArr.length) {
                    dataOutput.writeUTF(dsoArr[i].name);
                    dataOutput.writeUTF(this.dsos[i].hash);
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    protected static final class InputDso implements Closeable {
        public final InputStream content;
        public final Dso dso;

        public InputDso(Dso dso, InputStream inputStream) {
            this.dso = dso;
            this.content = inputStream;
        }

        public void close() throws IOException {
            this.content.close();
        }
    }

    protected static abstract class InputDsoIterator implements Closeable {
        public void close() throws IOException {
        }

        public abstract boolean hasNext();

        public abstract InputDso next() throws IOException;

        protected InputDsoIterator() {
        }
    }

    protected static abstract class Unpacker implements Closeable {
        public void close() throws IOException {
        }

        protected abstract DsoManifest getDsoManifest() throws IOException;

        protected abstract InputDsoIterator openDsoIterator() throws IOException;

        protected Unpacker() {
        }
    }

    protected abstract Unpacker makeUnpacker() throws IOException;

    protected UnpackingSoSource(Context context, String str) {
        super(getSoStorePath(context, str), 1);
        this.mContext = context;
    }

    protected UnpackingSoSource(Context context, File file) {
        super(file, 1);
        this.mContext = context;
    }

    public static File getSoStorePath(Context context, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getApplicationInfo().dataDir);
        stringBuilder.append("/");
        stringBuilder.append(str);
        return new File(stringBuilder.toString());
    }

    public String[] getSoSourceAbis() {
        String[] strArr = this.mAbis;
        return strArr == null ? super.getSoSourceAbis() : strArr;
    }

    public void setSoSourceAbis(String[] strArr) {
        this.mAbis = strArr;
    }

    /* JADX WARNING: Missing block: B:9:0x0026, code:
            if (r3 != null) goto L_0x0028;
     */
    /* JADX WARNING: Missing block: B:11:?, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:12:0x002c, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:13:0x002d, code:
            r3.addSuppressed(r0);
     */
    /* JADX WARNING: Missing block: B:14:0x0031, code:
            r0.close();
     */
    private static void writeState(java.io.File r3, byte r4) throws java.io.IOException {
        /*
        r0 = new java.io.RandomAccessFile;
        r1 = "rw";
        r0.<init>(r3, r1);
        r1 = 0;
        r3 = 0;
        r0.seek(r1);	 Catch:{ Throwable -> 0x0024 }
        r0.write(r4);	 Catch:{ Throwable -> 0x0024 }
        r1 = r0.getFilePointer();	 Catch:{ Throwable -> 0x0024 }
        r0.setLength(r1);	 Catch:{ Throwable -> 0x0024 }
        r4 = r0.getFD();	 Catch:{ Throwable -> 0x0024 }
        r4.sync();	 Catch:{ Throwable -> 0x0024 }
        r0.close();
        return;
    L_0x0022:
        r4 = move-exception;
        goto L_0x0026;
    L_0x0024:
        r3 = move-exception;
        throw r3;	 Catch:{ all -> 0x0022 }
    L_0x0026:
        if (r3 == 0) goto L_0x0031;
    L_0x0028:
        r0.close();	 Catch:{ Throwable -> 0x002c }
        goto L_0x0034;
    L_0x002c:
        r0 = move-exception;
        r3.addSuppressed(r0);
        goto L_0x0034;
    L_0x0031:
        r0.close();
    L_0x0034:
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.UnpackingSoSource.writeState(java.io.File, byte):void");
    }

    private void deleteUnmentionedFiles(Dso[] dsoArr) throws IOException {
        String[] list = this.soDirectory.list();
        if (list != null) {
            for (String str : list) {
                if (!(str.equals(STATE_FILE_NAME) || str.equals(LOCK_FILE_NAME) || str.equals(DEPS_FILE_NAME) || str.equals(MANIFEST_FILE_NAME))) {
                    Object obj = null;
                    int i = 0;
                    while (obj == null && i < dsoArr.length) {
                        if (dsoArr[i].name.equals(str)) {
                            obj = 1;
                        }
                        i++;
                    }
                    if (obj == null) {
                        File file = new File(this.soDirectory, str);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("deleting unaccounted-for file ");
                        stringBuilder.append(file);
                        Log.v(TAG, stringBuilder.toString());
                        SysUtil.dumbDeleteRecursive(file);
                    }
                }
            }
            return;
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("unable to list directory ");
        stringBuilder2.append(this.soDirectory);
        throw new IOException(stringBuilder2.toString());
    }

    private void extractDso(InputDso inputDso, byte[] bArr) throws IOException {
        String str = "rw";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("extracting DSO ");
        stringBuilder.append(inputDso.dso.name);
        String stringBuilder2 = stringBuilder.toString();
        String str2 = TAG;
        Log.i(str2, stringBuilder2);
        StringBuilder stringBuilder3;
        if (this.soDirectory.setWritable(true, true)) {
            RandomAccessFile randomAccessFile;
            File file = new File(this.soDirectory, inputDso.dso.name);
            try {
                randomAccessFile = new RandomAccessFile(file, str);
            } catch (Throwable e) {
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append("error overwriting ");
                stringBuilder4.append(file);
                stringBuilder4.append(" trying to delete and start over");
                Log.w(str2, stringBuilder4.toString(), e);
                SysUtil.dumbDeleteRecursive(file);
                randomAccessFile = new RandomAccessFile(file, str);
            }
            try {
                int available = inputDso.content.available();
                if (available > 1) {
                    SysUtil.fallocateIfSupported(randomAccessFile.getFD(), (long) available);
                }
                SysUtil.copyBytes(randomAccessFile, inputDso.content, Integer.MAX_VALUE, bArr);
                randomAccessFile.setLength(randomAccessFile.getFilePointer());
                if (file.setExecutable(true, false)) {
                    randomAccessFile.close();
                    return;
                }
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("cannot make file executable: ");
                stringBuilder3.append(file);
                throw new IOException(stringBuilder3.toString());
            } catch (IOException e2) {
                SysUtil.dumbDeleteRecursive(file);
                throw e2;
            } catch (Throwable th) {
                randomAccessFile.close();
            }
        } else {
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("cannot make directory writable for us: ");
            stringBuilder3.append(this.soDirectory);
            throw new IOException(stringBuilder3.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0048 A:{Catch:{ Throwable -> 0x003a, all -> 0x0037, Throwable -> 0x00de }} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005f A:{Catch:{ Throwable -> 0x003a, all -> 0x0037, Throwable -> 0x00de }} */
    private void regenerate(byte r12, com.facebook.soloader.UnpackingSoSource.DsoManifest r13, com.facebook.soloader.UnpackingSoSource.InputDsoIterator r14) throws java.io.IOException {
        /*
        r11 = this;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "regenerating DSO store ";
        r0.append(r1);
        r1 = r11.getClass();
        r1 = r1.getName();
        r0.append(r1);
        r0 = r0.toString();
        r1 = "fb-UnpackingSoSource";
        android.util.Log.v(r1, r0);
        r0 = new java.io.File;
        r2 = r11.soDirectory;
        r3 = "dso_manifest";
        r0.<init>(r2, r3);
        r2 = new java.io.RandomAccessFile;
        r3 = "rw";
        r2.<init>(r0, r3);
        r0 = 1;
        r3 = 0;
        if (r12 != r0) goto L_0x0044;
    L_0x0032:
        r12 = com.facebook.soloader.UnpackingSoSource.DsoManifest.read(r2);	 Catch:{ Exception -> 0x003e }
        goto L_0x0045;
    L_0x0037:
        r12 = move-exception;
        goto L_0x00d8;
    L_0x003a:
        r12 = move-exception;
        r3 = r12;
        goto L_0x00d7;
    L_0x003e:
        r12 = move-exception;
        r4 = "error reading existing DSO manifest";
        android.util.Log.i(r1, r4, r12);	 Catch:{ Throwable -> 0x003a }
    L_0x0044:
        r12 = r3;
    L_0x0045:
        r4 = 0;
        if (r12 != 0) goto L_0x004f;
    L_0x0048:
        r12 = new com.facebook.soloader.UnpackingSoSource$DsoManifest;	 Catch:{ Throwable -> 0x003a }
        r5 = new com.facebook.soloader.UnpackingSoSource.Dso[r4];	 Catch:{ Throwable -> 0x003a }
        r12.<init>(r5);	 Catch:{ Throwable -> 0x003a }
    L_0x004f:
        r13 = r13.dsos;	 Catch:{ Throwable -> 0x003a }
        r11.deleteUnmentionedFiles(r13);	 Catch:{ Throwable -> 0x003a }
        r13 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r13 = new byte[r13];	 Catch:{ Throwable -> 0x003a }
    L_0x0059:
        r5 = r14.hasNext();	 Catch:{ Throwable -> 0x003a }
        if (r5 == 0) goto L_0x00b7;
    L_0x005f:
        r5 = r14.next();	 Catch:{ Throwable -> 0x003a }
        r6 = 1;
        r7 = 0;
    L_0x0065:
        if (r6 == 0) goto L_0x0095;
    L_0x0067:
        r8 = r12.dsos;	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        r8 = r8.length;	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        if (r7 >= r8) goto L_0x0095;
    L_0x006c:
        r8 = r12.dsos;	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        r8 = r8[r7];	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        r8 = r8.name;	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        r9 = r5.dso;	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        r9 = r9.name;	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        r8 = r8.equals(r9);	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        if (r8 == 0) goto L_0x008d;
    L_0x007c:
        r8 = r12.dsos;	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        r8 = r8[r7];	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        r8 = r8.hash;	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        r9 = r5.dso;	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        r9 = r9.hash;	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        r8 = r8.equals(r9);	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        if (r8 == 0) goto L_0x008d;
    L_0x008c:
        r6 = 0;
    L_0x008d:
        r7 = r7 + 1;
        goto L_0x0065;
    L_0x0090:
        r12 = move-exception;
        r13 = r3;
        goto L_0x00a0;
    L_0x0093:
        r12 = move-exception;
        goto L_0x009b;
    L_0x0095:
        if (r6 == 0) goto L_0x00b1;
    L_0x0097:
        r11.extractDso(r5, r13);	 Catch:{ Throwable -> 0x0093, all -> 0x0090 }
        goto L_0x00b1;
    L_0x009b:
        throw r12;	 Catch:{ all -> 0x009c }
    L_0x009c:
        r13 = move-exception;
        r10 = r13;
        r13 = r12;
        r12 = r10;
    L_0x00a0:
        if (r5 == 0) goto L_0x00b0;
    L_0x00a2:
        if (r13 == 0) goto L_0x00ad;
    L_0x00a4:
        r5.close();	 Catch:{ Throwable -> 0x00a8 }
        goto L_0x00b0;
    L_0x00a8:
        r14 = move-exception;
        r13.addSuppressed(r14);	 Catch:{ Throwable -> 0x003a }
        goto L_0x00b0;
    L_0x00ad:
        r5.close();	 Catch:{ Throwable -> 0x003a }
    L_0x00b0:
        throw r12;	 Catch:{ Throwable -> 0x003a }
    L_0x00b1:
        if (r5 == 0) goto L_0x0059;
    L_0x00b3:
        r5.close();	 Catch:{ Throwable -> 0x003a }
        goto L_0x0059;
    L_0x00b7:
        r2.close();
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "Finished regenerating DSO store ";
        r12.append(r13);
        r13 = r11.getClass();
        r13 = r13.getName();
        r12.append(r13);
        r12 = r12.toString();
        android.util.Log.v(r1, r12);
        return;
    L_0x00d7:
        throw r3;	 Catch:{ all -> 0x0037 }
    L_0x00d8:
        if (r3 == 0) goto L_0x00e3;
    L_0x00da:
        r2.close();	 Catch:{ Throwable -> 0x00de }
        goto L_0x00e6;
    L_0x00de:
        r13 = move-exception;
        r3.addSuppressed(r13);
        goto L_0x00e6;
    L_0x00e3:
        r2.close();
    L_0x00e6:
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.UnpackingSoSource.regenerate(byte, com.facebook.soloader.UnpackingSoSource$DsoManifest, com.facebook.soloader.UnpackingSoSource$InputDsoIterator):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:66:0x00fa A:{ExcHandler: all (th java.lang.Throwable), Splitter: B:31:0x0092} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:66:0x00fa, code:
            r13 = th;
     */
    /* JADX WARNING: Missing block: B:67:0x00fb, code:
            r14 = null;
     */
    /* JADX WARNING: Missing block: B:71:0x00ff, code:
            r14 = move-exception;
     */
    /* JADX WARNING: Missing block: B:72:0x0100, code:
            r11 = r14;
            r14 = r13;
            r13 = r11;
     */
    private boolean refreshLocked(com.facebook.soloader.FileLocker r13, int r14, byte[] r15) throws java.io.IOException {
        /*
        r12 = this;
        r0 = "fb-UnpackingSoSource";
        r6 = new java.io.File;
        r1 = r12.soDirectory;
        r2 = "dso_state";
        r6.<init>(r1, r2);
        r1 = new java.io.RandomAccessFile;
        r2 = "rw";
        r1.<init>(r6, r2);
        r8 = 1;
        r3 = 0;
        r4 = 0;
        r5 = r1.readByte();	 Catch:{ EOFException -> 0x0036, Throwable -> 0x003a }
        if (r5 == r8) goto L_0x004c;
    L_0x001b:
        r5 = new java.lang.StringBuilder;	 Catch:{ EOFException -> 0x0036, Throwable -> 0x003a }
        r5.<init>();	 Catch:{ EOFException -> 0x0036, Throwable -> 0x003a }
        r7 = "dso store ";
        r5.append(r7);	 Catch:{ EOFException -> 0x0036, Throwable -> 0x003a }
        r7 = r12.soDirectory;	 Catch:{ EOFException -> 0x0036, Throwable -> 0x003a }
        r5.append(r7);	 Catch:{ EOFException -> 0x0036, Throwable -> 0x003a }
        r7 = " regeneration interrupted: wiping clean";
        r5.append(r7);	 Catch:{ EOFException -> 0x0036, Throwable -> 0x003a }
        r5 = r5.toString();	 Catch:{ EOFException -> 0x0036, Throwable -> 0x003a }
        android.util.Log.v(r0, r5);	 Catch:{ EOFException -> 0x0036, Throwable -> 0x003a }
    L_0x0036:
        r5 = 0;
        goto L_0x004c;
    L_0x0038:
        r13 = move-exception;
        goto L_0x003d;
    L_0x003a:
        r13 = move-exception;
        r3 = r13;
        throw r3;	 Catch:{ all -> 0x0038 }
    L_0x003d:
        if (r3 == 0) goto L_0x0048;
    L_0x003f:
        r1.close();	 Catch:{ Throwable -> 0x0043 }
        goto L_0x004b;
    L_0x0043:
        r14 = move-exception;
        r3.addSuppressed(r14);
        goto L_0x004b;
    L_0x0048:
        r1.close();
    L_0x004b:
        throw r13;
    L_0x004c:
        r1.close();
        r7 = new java.io.File;
        r1 = r12.soDirectory;
        r9 = "dso_deps";
        r7.<init>(r1, r9);
        r1 = new java.io.RandomAccessFile;
        r1.<init>(r7, r2);
        r9 = r1.length();	 Catch:{ Throwable -> 0x0116 }
        r2 = (int) r9;	 Catch:{ Throwable -> 0x0116 }
        r2 = new byte[r2];	 Catch:{ Throwable -> 0x0116 }
        r9 = r1.read(r2);	 Catch:{ Throwable -> 0x0116 }
        r10 = r2.length;	 Catch:{ Throwable -> 0x0116 }
        if (r9 == r10) goto L_0x0071;
    L_0x006b:
        r5 = "short read of so store deps file: marking unclean";
        android.util.Log.v(r0, r5);	 Catch:{ Throwable -> 0x0116 }
        r5 = 0;
    L_0x0071:
        r2 = java.util.Arrays.equals(r2, r15);	 Catch:{ Throwable -> 0x0116 }
        if (r2 != 0) goto L_0x007d;
    L_0x0077:
        r2 = "deps mismatch on deps store: regenerating";
        android.util.Log.v(r0, r2);	 Catch:{ Throwable -> 0x0116 }
        r5 = 0;
    L_0x007d:
        if (r5 == 0) goto L_0x0086;
    L_0x007f:
        r2 = r14 & 2;
        if (r2 == 0) goto L_0x0084;
    L_0x0083:
        goto L_0x0086;
    L_0x0084:
        r5 = r3;
        goto L_0x00a8;
    L_0x0086:
        r2 = "so store dirty: regenerating";
        android.util.Log.v(r0, r2);	 Catch:{ Throwable -> 0x0116 }
        writeState(r6, r4);	 Catch:{ Throwable -> 0x0116 }
        r0 = r12.makeUnpacker();	 Catch:{ Throwable -> 0x0116 }
        r2 = r0.getDsoManifest();	 Catch:{ Throwable -> 0x00fd, all -> 0x00fa }
        r9 = r0.openDsoIterator();	 Catch:{ Throwable -> 0x00fd, all -> 0x00fa }
        r12.regenerate(r5, r2, r9);	 Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
        if (r9 == 0) goto L_0x00a2;
    L_0x009f:
        r9.close();	 Catch:{ Throwable -> 0x00fd, all -> 0x00fa }
    L_0x00a2:
        if (r0 == 0) goto L_0x00a7;
    L_0x00a4:
        r0.close();	 Catch:{ Throwable -> 0x0116 }
    L_0x00a7:
        r5 = r2;
    L_0x00a8:
        r1.close();
        if (r5 != 0) goto L_0x00ae;
    L_0x00ad:
        return r4;
    L_0x00ae:
        r0 = new com.facebook.soloader.UnpackingSoSource$1;
        r1 = r0;
        r2 = r12;
        r3 = r7;
        r4 = r15;
        r7 = r13;
        r1.<init>(r3, r4, r5, r6, r7);
        r13 = r14 & 1;
        if (r13 == 0) goto L_0x00dc;
    L_0x00bc:
        r13 = new java.lang.Thread;
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r15 = "SoSync:";
        r14.append(r15);
        r15 = r12.soDirectory;
        r15 = r15.getName();
        r14.append(r15);
        r14 = r14.toString();
        r13.<init>(r0, r14);
        r13.start();
        goto L_0x00df;
    L_0x00dc:
        r0.run();
    L_0x00df:
        return r8;
    L_0x00e0:
        r13 = move-exception;
        r14 = r3;
        goto L_0x00e9;
    L_0x00e3:
        r13 = move-exception;
        throw r13;	 Catch:{ all -> 0x00e5 }
    L_0x00e5:
        r14 = move-exception;
        r11 = r14;
        r14 = r13;
        r13 = r11;
    L_0x00e9:
        if (r9 == 0) goto L_0x00f9;
    L_0x00eb:
        if (r14 == 0) goto L_0x00f6;
    L_0x00ed:
        r9.close();	 Catch:{ Throwable -> 0x00f1, all -> 0x00fa }
        goto L_0x00f9;
    L_0x00f1:
        r15 = move-exception;
        r14.addSuppressed(r15);	 Catch:{ Throwable -> 0x00fd, all -> 0x00fa }
        goto L_0x00f9;
    L_0x00f6:
        r9.close();	 Catch:{ Throwable -> 0x00fd, all -> 0x00fa }
    L_0x00f9:
        throw r13;	 Catch:{ Throwable -> 0x00fd, all -> 0x00fa }
    L_0x00fa:
        r13 = move-exception;
        r14 = r3;
        goto L_0x0103;
    L_0x00fd:
        r13 = move-exception;
        throw r13;	 Catch:{ all -> 0x00ff }
    L_0x00ff:
        r14 = move-exception;
        r11 = r14;
        r14 = r13;
        r13 = r11;
    L_0x0103:
        if (r0 == 0) goto L_0x0113;
    L_0x0105:
        if (r14 == 0) goto L_0x0110;
    L_0x0107:
        r0.close();	 Catch:{ Throwable -> 0x010b }
        goto L_0x0113;
    L_0x010b:
        r15 = move-exception;
        r14.addSuppressed(r15);	 Catch:{ Throwable -> 0x0116 }
        goto L_0x0113;
    L_0x0110:
        r0.close();	 Catch:{ Throwable -> 0x0116 }
    L_0x0113:
        throw r13;	 Catch:{ Throwable -> 0x0116 }
    L_0x0114:
        r13 = move-exception;
        goto L_0x0119;
    L_0x0116:
        r13 = move-exception;
        r3 = r13;
        throw r3;	 Catch:{ all -> 0x0114 }
    L_0x0119:
        if (r3 == 0) goto L_0x0124;
    L_0x011b:
        r1.close();	 Catch:{ Throwable -> 0x011f }
        goto L_0x0127;
    L_0x011f:
        r14 = move-exception;
        r3.addSuppressed(r14);
        goto L_0x0127;
    L_0x0124:
        r1.close();
    L_0x0127:
        throw r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.UnpackingSoSource.refreshLocked(com.facebook.soloader.FileLocker, int, byte[]):boolean");
    }

    protected byte[] getDepsBlock() throws IOException {
        Throwable th;
        Parcel obtain = Parcel.obtain();
        Unpacker makeUnpacker = makeUnpacker();
        try {
            Dso[] dsoArr = makeUnpacker.getDsoManifest().dsos;
            obtain.writeByte((byte) 1);
            obtain.writeInt(dsoArr.length);
            for (int i = 0; i < dsoArr.length; i++) {
                obtain.writeString(dsoArr[i].name);
                obtain.writeString(dsoArr[i].hash);
            }
            if (makeUnpacker != null) {
                makeUnpacker.close();
            }
            byte[] marshall = obtain.marshall();
            obtain.recycle();
            return marshall;
        } catch (Throwable th2) {
            th.addSuppressed(th2);
        }
    }

    protected void prepare(int i) throws IOException {
        String str = "releasing dso store lock for ";
        String str2 = " (syncer thread started)";
        String str3 = "not releasing dso store lock for ";
        String str4 = TAG;
        SysUtil.mkdirOrThrow(this.soDirectory);
        FileLocker lock = FileLocker.lock(new File(this.soDirectory, LOCK_FILE_NAME));
        try {
            StringBuilder stringBuilder;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("locked dso store ");
            stringBuilder2.append(this.soDirectory);
            Log.v(str4, stringBuilder2.toString());
            if (refreshLocked(lock, i, getDepsBlock())) {
                lock = null;
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("dso store is up-to-date: ");
                stringBuilder.append(this.soDirectory);
                Log.i(str4, stringBuilder.toString());
            }
            if (lock != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(this.soDirectory);
                Log.v(str4, stringBuilder.toString());
                lock.close();
                return;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str3);
            stringBuilder.append(this.soDirectory);
            stringBuilder.append(str2);
            Log.v(str4, stringBuilder.toString());
        } catch (Throwable th) {
            if (lock != null) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(str);
                stringBuilder3.append(this.soDirectory);
                Log.v(str4, stringBuilder3.toString());
                lock.close();
            } else {
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append(str3);
                stringBuilder4.append(this.soDirectory);
                stringBuilder4.append(str2);
                Log.v(str4, stringBuilder4.toString());
            }
        }
    }

    private Object getLibraryLock(String str) {
        Object obj;
        synchronized (this.mLibsBeingLoaded) {
            obj = this.mLibsBeingLoaded.get(str);
            if (obj == null) {
                obj = new Object();
                this.mLibsBeingLoaded.put(str, obj);
            }
        }
        return obj;
    }

    protected synchronized void prepare(String str) throws IOException {
        synchronized (getLibraryLock(str)) {
            this.mCorruptedLib = str;
            prepare(2);
        }
    }

    public int loadLibrary(String str, int i, ThreadPolicy threadPolicy) throws IOException {
        int loadLibraryFrom;
        synchronized (getLibraryLock(str)) {
            loadLibraryFrom = loadLibraryFrom(str, i, this.soDirectory, threadPolicy);
        }
        return loadLibraryFrom;
    }
}
