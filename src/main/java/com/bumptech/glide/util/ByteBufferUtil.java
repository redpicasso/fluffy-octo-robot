package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;

public final class ByteBufferUtil {
    private static final AtomicReference<byte[]> BUFFER_REF = new AtomicReference();
    private static final int BUFFER_SIZE = 16384;

    private static class ByteBufferStream extends InputStream {
        private static final int UNSET = -1;
        @NonNull
        private final ByteBuffer byteBuffer;
        private int markPos = -1;

        public boolean markSupported() {
            return true;
        }

        ByteBufferStream(@NonNull ByteBuffer byteBuffer) {
            this.byteBuffer = byteBuffer;
        }

        public int available() {
            return this.byteBuffer.remaining();
        }

        public int read() {
            if (this.byteBuffer.hasRemaining()) {
                return this.byteBuffer.get();
            }
            return -1;
        }

        public synchronized void mark(int i) {
            this.markPos = this.byteBuffer.position();
        }

        public int read(@NonNull byte[] bArr, int i, int i2) throws IOException {
            if (!this.byteBuffer.hasRemaining()) {
                return -1;
            }
            i2 = Math.min(i2, available());
            this.byteBuffer.get(bArr, i, i2);
            return i2;
        }

        public synchronized void reset() throws IOException {
            if (this.markPos != -1) {
                this.byteBuffer.position(this.markPos);
            } else {
                throw new IOException("Cannot reset to unset mark position");
            }
        }

        public long skip(long j) throws IOException {
            if (!this.byteBuffer.hasRemaining()) {
                return -1;
            }
            j = Math.min(j, (long) available());
            ByteBuffer byteBuffer = this.byteBuffer;
            byteBuffer.position((int) (((long) byteBuffer.position()) + j));
            return j;
        }
    }

    static final class SafeArray {
        final byte[] data;
        final int limit;
        final int offset;

        SafeArray(@NonNull byte[] bArr, int i, int i2) {
            this.data = bArr;
            this.offset = i;
            this.limit = i2;
        }
    }

    private ByteBufferUtil() {
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.bumptech.glide.util.ByteBufferUtil.fromFile(java.io.File):java.nio.ByteBuffer, dom blocks: []
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
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004e A:{SYNTHETIC, Splitter: B:28:0x004e} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0055 A:{SYNTHETIC, Splitter: B:32:0x0055} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004e A:{SYNTHETIC, Splitter: B:28:0x004e} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0055 A:{SYNTHETIC, Splitter: B:32:0x0055} */
    @androidx.annotation.NonNull
    public static java.nio.ByteBuffer fromFile(@androidx.annotation.NonNull java.io.File r9) throws java.io.IOException {
        /*
        r0 = 0;
        r5 = r9.length();	 Catch:{ all -> 0x004a }
        r1 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;	 Catch:{ all -> 0x004a }
        r3 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1));	 Catch:{ all -> 0x004a }
        if (r3 > 0) goto L_0x0042;	 Catch:{ all -> 0x004a }
    L_0x000c:
        r1 = 0;	 Catch:{ all -> 0x004a }
        r3 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1));	 Catch:{ all -> 0x004a }
        if (r3 == 0) goto L_0x003a;	 Catch:{ all -> 0x004a }
    L_0x0012:
        r7 = new java.io.RandomAccessFile;	 Catch:{ all -> 0x004a }
        r1 = "r";	 Catch:{ all -> 0x004a }
        r7.<init>(r9, r1);	 Catch:{ all -> 0x004a }
        r9 = r7.getChannel();	 Catch:{ all -> 0x0038 }
        r2 = java.nio.channels.FileChannel.MapMode.READ_ONLY;	 Catch:{ all -> 0x0033 }
        r3 = 0;	 Catch:{ all -> 0x0033 }
        r1 = r9;	 Catch:{ all -> 0x0033 }
        r0 = r1.map(r2, r3, r5);	 Catch:{ all -> 0x0033 }
        r0 = r0.load();	 Catch:{ all -> 0x0033 }
        if (r9 == 0) goto L_0x002f;
    L_0x002c:
        r9.close();	 Catch:{ IOException -> 0x002f }
    L_0x002f:
        r7.close();	 Catch:{ IOException -> 0x0032 }
    L_0x0032:
        return r0;
    L_0x0033:
        r0 = move-exception;
        r8 = r0;
        r0 = r9;
        r9 = r8;
        goto L_0x004c;
    L_0x0038:
        r9 = move-exception;
        goto L_0x004c;
    L_0x003a:
        r9 = new java.io.IOException;	 Catch:{ all -> 0x004a }
        r1 = "File unsuitable for memory mapping";	 Catch:{ all -> 0x004a }
        r9.<init>(r1);	 Catch:{ all -> 0x004a }
        throw r9;	 Catch:{ all -> 0x004a }
    L_0x0042:
        r9 = new java.io.IOException;	 Catch:{ all -> 0x004a }
        r1 = "File too large to map into memory";	 Catch:{ all -> 0x004a }
        r9.<init>(r1);	 Catch:{ all -> 0x004a }
        throw r9;	 Catch:{ all -> 0x004a }
    L_0x004a:
        r9 = move-exception;
        r7 = r0;
    L_0x004c:
        if (r0 == 0) goto L_0x0053;
    L_0x004e:
        r0.close();	 Catch:{ IOException -> 0x0052 }
        goto L_0x0053;
    L_0x0053:
        if (r7 == 0) goto L_0x0058;
    L_0x0055:
        r7.close();	 Catch:{ IOException -> 0x0058 }
    L_0x0058:
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.util.ByteBufferUtil.fromFile(java.io.File):java.nio.ByteBuffer");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.bumptech.glide.util.ByteBufferUtil.toFile(java.nio.ByteBuffer, java.io.File):void, dom blocks: []
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
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002b A:{SYNTHETIC, Splitter: B:15:0x002b} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0032 A:{SYNTHETIC, Splitter: B:19:0x0032} */
    /* JADX WARNING: Failed to process nested try/catch */
    public static void toFile(@androidx.annotation.NonNull java.nio.ByteBuffer r4, @androidx.annotation.NonNull java.io.File r5) throws java.io.IOException {
        /*
        r0 = 0;
        r4.position(r0);
        r1 = 0;
        r2 = new java.io.RandomAccessFile;	 Catch:{ all -> 0x0027 }
        r3 = "rw";	 Catch:{ all -> 0x0027 }
        r2.<init>(r5, r3);	 Catch:{ all -> 0x0027 }
        r1 = r2.getChannel();	 Catch:{ all -> 0x0025 }
        r1.write(r4);	 Catch:{ all -> 0x0025 }
        r1.force(r0);	 Catch:{ all -> 0x0025 }
        r1.close();	 Catch:{ all -> 0x0025 }
        r2.close();	 Catch:{ all -> 0x0025 }
        if (r1 == 0) goto L_0x0021;
    L_0x001e:
        r1.close();	 Catch:{ IOException -> 0x0021 }
    L_0x0021:
        r2.close();	 Catch:{ IOException -> 0x0024 }
    L_0x0024:
        return;
    L_0x0025:
        r4 = move-exception;
        goto L_0x0029;
    L_0x0027:
        r4 = move-exception;
        r2 = r1;
    L_0x0029:
        if (r1 == 0) goto L_0x0030;
    L_0x002b:
        r1.close();	 Catch:{ IOException -> 0x002f }
        goto L_0x0030;
    L_0x0030:
        if (r2 == 0) goto L_0x0035;
    L_0x0032:
        r2.close();	 Catch:{ IOException -> 0x0035 }
    L_0x0035:
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.util.ByteBufferUtil.toFile(java.nio.ByteBuffer, java.io.File):void");
    }

    public static void toStream(@NonNull ByteBuffer byteBuffer, @NonNull OutputStream outputStream) throws IOException {
        SafeArray safeArray = getSafeArray(byteBuffer);
        if (safeArray != null) {
            outputStream.write(safeArray.data, safeArray.offset, safeArray.offset + safeArray.limit);
            return;
        }
        Object obj = (byte[]) BUFFER_REF.getAndSet(null);
        if (obj == null) {
            obj = new byte[16384];
        }
        while (byteBuffer.remaining() > 0) {
            int min = Math.min(byteBuffer.remaining(), obj.length);
            byteBuffer.get(obj, 0, min);
            outputStream.write(obj, 0, min);
        }
        BUFFER_REF.set(obj);
    }

    @NonNull
    public static byte[] toBytes(@NonNull ByteBuffer byteBuffer) {
        SafeArray safeArray = getSafeArray(byteBuffer);
        if (safeArray != null && safeArray.offset == 0 && safeArray.limit == safeArray.data.length) {
            return byteBuffer.array();
        }
        byteBuffer = byteBuffer.asReadOnlyBuffer();
        byte[] bArr = new byte[byteBuffer.limit()];
        byteBuffer.position(0);
        byteBuffer.get(bArr);
        return bArr;
    }

    @NonNull
    public static InputStream toStream(@NonNull ByteBuffer byteBuffer) {
        return new ByteBufferStream(byteBuffer);
    }

    @NonNull
    public static ByteBuffer fromStream(@NonNull InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(16384);
        Object obj = (byte[]) BUFFER_REF.getAndSet(null);
        if (obj == null) {
            obj = new byte[16384];
        }
        while (true) {
            int read = inputStream.read(obj);
            if (read >= 0) {
                byteArrayOutputStream.write(obj, 0, read);
            } else {
                BUFFER_REF.set(obj);
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                return (ByteBuffer) ByteBuffer.allocateDirect(toByteArray.length).put(toByteArray).position(0);
            }
        }
    }

    @Nullable
    private static SafeArray getSafeArray(@NonNull ByteBuffer byteBuffer) {
        return (byteBuffer.isReadOnly() || !byteBuffer.hasArray()) ? null : new SafeArray(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit());
    }
}
