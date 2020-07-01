package com.squareup.okhttp.internal;

import com.facebook.cache.disk.DefaultDiskStorage.FileType;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.squareup.okhttp.internal.io.FileSystem;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class DiskLruCache implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,120}");
    static final String MAGIC = "libcore.io.DiskLruCache";
    private static final Sink NULL_SINK = new Sink() {
        public void close() throws IOException {
        }

        public void flush() throws IOException {
        }

        public void write(Buffer buffer, long j) throws IOException {
            buffer.skip(j);
        }

        public Timeout timeout() {
            return Timeout.NONE;
        }
    };
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    static final String VERSION_1 = "1";
    private final int appVersion;
    private final Runnable cleanupRunnable = new Runnable() {
        public void run() {
            synchronized (DiskLruCache.this) {
                if (((!DiskLruCache.this.initialized ? 1 : 0) | DiskLruCache.this.closed) != 0) {
                    return;
                }
                try {
                    DiskLruCache.this.trimToSize();
                    if (DiskLruCache.this.journalRebuildRequired()) {
                        DiskLruCache.this.rebuildJournal();
                        DiskLruCache.this.redundantOpCount = 0;
                    }
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };
    private boolean closed;
    private final File directory;
    private final Executor executor;
    private final FileSystem fileSystem;
    private boolean hasJournalErrors;
    private boolean initialized;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    private BufferedSink journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap(0, 0.75f, true);
    private long maxSize;
    private long nextSequenceNumber = 0;
    private int redundantOpCount;
    private long size = 0;
    private final int valueCount;

    public final class Editor {
        private boolean committed;
        private final Entry entry;
        private boolean hasErrors;
        private final boolean[] written;

        /* synthetic */ Editor(DiskLruCache diskLruCache, Entry entry, AnonymousClass1 anonymousClass1) {
            this(entry);
        }

        private Editor(Entry entry) {
            this.entry = entry;
            this.written = entry.readable ? null : new boolean[DiskLruCache.this.valueCount];
        }

        public Source newSource(int i) throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                } else if (this.entry.readable) {
                    try {
                        Source source = DiskLruCache.this.fileSystem.source(this.entry.cleanFiles[i]);
                        return source;
                    } catch (FileNotFoundException unused) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }

        public Sink newSink(int i) throws IOException {
            Sink anonymousClass1;
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor == this) {
                    if (!this.entry.readable) {
                        this.written[i] = true;
                    }
                    try {
                        anonymousClass1 = new FaultHidingSink(DiskLruCache.this.fileSystem.sink(this.entry.dirtyFiles[i])) {
                            protected void onException(IOException iOException) {
                                synchronized (DiskLruCache.this) {
                                    Editor.this.hasErrors = true;
                                }
                            }
                        };
                    } catch (FileNotFoundException unused) {
                        return DiskLruCache.NULL_SINK;
                    }
                }
                throw new IllegalStateException();
            }
            return anonymousClass1;
        }

        public void commit() throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.hasErrors) {
                    DiskLruCache.this.completeEdit(this, false);
                    DiskLruCache.this.removeEntry(this.entry);
                } else {
                    DiskLruCache.this.completeEdit(this, true);
                }
                this.committed = true;
            }
        }

        public void abort() throws IOException {
            synchronized (DiskLruCache.this) {
                DiskLruCache.this.completeEdit(this, false);
            }
        }

        public void abortUnlessCommitted() {
            synchronized (DiskLruCache.this) {
                if (!this.committed) {
                    DiskLruCache.this.completeEdit(this, false);
                }
                try {
                } catch (IOException unused) {
                }
            }
        }
    }

    private final class Entry {
        private final File[] cleanFiles;
        private Editor currentEditor;
        private final File[] dirtyFiles;
        private final String key;
        private final long[] lengths;
        private boolean readable;
        private long sequenceNumber;

        /* synthetic */ Entry(DiskLruCache diskLruCache, String str, AnonymousClass1 anonymousClass1) {
            this(str);
        }

        private Entry(String str) {
            this.key = str;
            this.lengths = new long[DiskLruCache.this.valueCount];
            this.cleanFiles = new File[DiskLruCache.this.valueCount];
            this.dirtyFiles = new File[DiskLruCache.this.valueCount];
            StringBuilder stringBuilder = new StringBuilder(str);
            stringBuilder.append('.');
            int length = stringBuilder.length();
            for (int i = 0; i < DiskLruCache.this.valueCount; i++) {
                stringBuilder.append(i);
                this.cleanFiles[i] = new File(DiskLruCache.this.directory, stringBuilder.toString());
                stringBuilder.append(FileType.TEMP);
                this.dirtyFiles[i] = new File(DiskLruCache.this.directory, stringBuilder.toString());
                stringBuilder.setLength(length);
            }
        }

        private void setLengths(String[] strArr) throws IOException {
            if (strArr.length == DiskLruCache.this.valueCount) {
                int i = 0;
                while (i < strArr.length) {
                    try {
                        this.lengths[i] = Long.parseLong(strArr[i]);
                        i++;
                    } catch (NumberFormatException unused) {
                        throw invalidLengths(strArr);
                    }
                }
                return;
            }
            throw invalidLengths(strArr);
        }

        void writeLengths(BufferedSink bufferedSink) throws IOException {
            for (long writeDecimalLong : this.lengths) {
                bufferedSink.writeByte(32).writeDecimalLong(writeDecimalLong);
            }
        }

        private IOException invalidLengths(String[] strArr) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected journal line: ");
            stringBuilder.append(Arrays.toString(strArr));
            throw new IOException(stringBuilder.toString());
        }

        Snapshot snapshot() {
            if (Thread.holdsLock(DiskLruCache.this)) {
                Source[] sourceArr = new Source[DiskLruCache.this.valueCount];
                long[] jArr = (long[]) this.lengths.clone();
                int i = 0;
                int i2 = 0;
                while (i2 < DiskLruCache.this.valueCount) {
                    try {
                        sourceArr[i2] = DiskLruCache.this.fileSystem.source(this.cleanFiles[i2]);
                        i2++;
                    } catch (FileNotFoundException unused) {
                        while (i < DiskLruCache.this.valueCount && sourceArr[i] != null) {
                            Util.closeQuietly(sourceArr[i]);
                            i++;
                        }
                        return null;
                    }
                }
                return new Snapshot(DiskLruCache.this, this.key, this.sequenceNumber, sourceArr, jArr, null);
            }
            throw new AssertionError();
        }
    }

    public final class Snapshot implements Closeable {
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;
        private final Source[] sources;

        /* synthetic */ Snapshot(DiskLruCache diskLruCache, String str, long j, Source[] sourceArr, long[] jArr, AnonymousClass1 anonymousClass1) {
            this(str, j, sourceArr, jArr);
        }

        private Snapshot(String str, long j, Source[] sourceArr, long[] jArr) {
            this.key = str;
            this.sequenceNumber = j;
            this.sources = sourceArr;
            this.lengths = jArr;
        }

        public String key() {
            return this.key;
        }

        public Editor edit() throws IOException {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public Source getSource(int i) {
            return this.sources[i];
        }

        public long getLength(int i) {
            return this.lengths[i];
        }

        public void close() {
            for (Closeable closeQuietly : this.sources) {
                Util.closeQuietly(closeQuietly);
            }
        }
    }

    DiskLruCache(FileSystem fileSystem, File file, int i, int i2, long j, Executor executor) {
        this.fileSystem = fileSystem;
        this.directory = file;
        this.appVersion = i;
        this.journalFile = new File(file, JOURNAL_FILE);
        this.journalFileTmp = new File(file, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(file, JOURNAL_FILE_BACKUP);
        this.valueCount = i2;
        this.maxSize = j;
        this.executor = executor;
    }

    public synchronized void initialize() throws IOException {
        if (!this.initialized) {
            if (this.fileSystem.exists(this.journalFileBackup)) {
                if (this.fileSystem.exists(this.journalFile)) {
                    this.fileSystem.delete(this.journalFileBackup);
                } else {
                    this.fileSystem.rename(this.journalFileBackup, this.journalFile);
                }
            }
            if (this.fileSystem.exists(this.journalFile)) {
                try {
                    readJournal();
                    processJournal();
                    this.initialized = true;
                    return;
                } catch (IOException e) {
                    Platform platform = Platform.get();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DiskLruCache ");
                    stringBuilder.append(this.directory);
                    stringBuilder.append(" is corrupt: ");
                    stringBuilder.append(e.getMessage());
                    stringBuilder.append(", removing");
                    platform.logW(stringBuilder.toString());
                    delete();
                    this.closed = false;
                }
            }
            rebuildJournal();
            this.initialized = true;
        }
    }

    public static DiskLruCache create(FileSystem fileSystem, File file, int i, int i2, long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i2 > 0) {
            return new DiskLruCache(fileSystem, file, i, i2, j, new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true)));
        } else {
            throw new IllegalArgumentException("valueCount <= 0");
        }
    }

    private void readJournal() throws IOException {
        String str = ", ";
        Closeable buffer = Okio.buffer(this.fileSystem.source(this.journalFile));
        int i;
        try {
            String readUtf8LineStrict = buffer.readUtf8LineStrict();
            String readUtf8LineStrict2 = buffer.readUtf8LineStrict();
            String readUtf8LineStrict3 = buffer.readUtf8LineStrict();
            String readUtf8LineStrict4 = buffer.readUtf8LineStrict();
            String readUtf8LineStrict5 = buffer.readUtf8LineStrict();
            if (MAGIC.equals(readUtf8LineStrict) && VERSION_1.equals(readUtf8LineStrict2) && Integer.toString(this.appVersion).equals(readUtf8LineStrict3) && Integer.toString(this.valueCount).equals(readUtf8LineStrict4) && "".equals(readUtf8LineStrict5)) {
                i = 0;
                while (true) {
                    readJournalLine(buffer.readUtf8LineStrict());
                    i++;
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unexpected journal header: [");
                stringBuilder.append(readUtf8LineStrict);
                stringBuilder.append(str);
                stringBuilder.append(readUtf8LineStrict2);
                stringBuilder.append(str);
                stringBuilder.append(readUtf8LineStrict4);
                stringBuilder.append(str);
                stringBuilder.append(readUtf8LineStrict5);
                stringBuilder.append("]");
                throw new IOException(stringBuilder.toString());
            }
        } catch (EOFException unused) {
            this.redundantOpCount = i - this.lruEntries.size();
            if (buffer.exhausted()) {
                this.journalWriter = newJournalWriter();
            } else {
                rebuildJournal();
            }
            Util.closeQuietly(buffer);
        } catch (Throwable th) {
            Util.closeQuietly(buffer);
        }
    }

    private BufferedSink newJournalWriter() throws FileNotFoundException {
        return Okio.buffer(new FaultHidingSink(this.fileSystem.appendingSink(this.journalFile)) {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class cls = DiskLruCache.class;
            }

            protected void onException(IOException iOException) {
                DiskLruCache.this.hasJournalErrors = true;
            }
        });
    }

    private void readJournalLine(String str) throws IOException {
        int indexOf = str.indexOf(32);
        String str2 = "unexpected journal line: ";
        StringBuilder stringBuilder;
        if (indexOf != -1) {
            String substring;
            int i = indexOf + 1;
            int indexOf2 = str.indexOf(32, i);
            if (indexOf2 == -1) {
                substring = str.substring(i);
                if (indexOf == 6 && str.startsWith(REMOVE)) {
                    this.lruEntries.remove(substring);
                    return;
                }
            }
            substring = str.substring(i, indexOf2);
            Entry entry = (Entry) this.lruEntries.get(substring);
            if (entry == null) {
                entry = new Entry(this, substring, null);
                this.lruEntries.put(substring, entry);
            }
            if (indexOf2 != -1 && indexOf == 5 && str.startsWith(CLEAN)) {
                String[] split = str.substring(indexOf2 + 1).split(" ");
                entry.readable = true;
                entry.currentEditor = null;
                entry.setLengths(split);
            } else if (indexOf2 == -1 && indexOf == 5 && str.startsWith(DIRTY)) {
                entry.currentEditor = new Editor(this, entry, null);
            } else if (!(indexOf2 == -1 && indexOf == 4 && str.startsWith(READ))) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(str);
                throw new IOException(stringBuilder.toString());
            }
            return;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(str);
        throw new IOException(stringBuilder.toString());
    }

    private void processJournal() throws IOException {
        this.fileSystem.delete(this.journalFileTmp);
        Iterator it = this.lruEntries.values().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            int i = 0;
            if (entry.currentEditor == null) {
                while (i < this.valueCount) {
                    this.size += entry.lengths[i];
                    i++;
                }
            } else {
                entry.currentEditor = null;
                while (i < this.valueCount) {
                    this.fileSystem.delete(entry.cleanFiles[i]);
                    this.fileSystem.delete(entry.dirtyFiles[i]);
                    i++;
                }
                it.remove();
            }
        }
    }

    private synchronized void rebuildJournal() throws IOException {
        if (this.journalWriter != null) {
            this.journalWriter.close();
        }
        BufferedSink buffer = Okio.buffer(this.fileSystem.sink(this.journalFileTmp));
        try {
            buffer.writeUtf8(MAGIC).writeByte(10);
            buffer.writeUtf8(VERSION_1).writeByte(10);
            buffer.writeDecimalLong((long) this.appVersion).writeByte(10);
            buffer.writeDecimalLong((long) this.valueCount).writeByte(10);
            buffer.writeByte(10);
            for (Entry entry : this.lruEntries.values()) {
                if (entry.currentEditor != null) {
                    buffer.writeUtf8(DIRTY).writeByte(32);
                    buffer.writeUtf8(entry.key);
                    buffer.writeByte(10);
                } else {
                    buffer.writeUtf8(CLEAN).writeByte(32);
                    buffer.writeUtf8(entry.key);
                    entry.writeLengths(buffer);
                    buffer.writeByte(10);
                }
            }
            if (this.fileSystem.exists(this.journalFile)) {
                this.fileSystem.rename(this.journalFile, this.journalFileBackup);
            }
            this.fileSystem.rename(this.journalFileTmp, this.journalFile);
            this.fileSystem.delete(this.journalFileBackup);
            this.journalWriter = newJournalWriter();
            this.hasJournalErrors = false;
        } finally {
            buffer.close();
        }
    }

    /* JADX WARNING: Missing block: B:15:0x004f, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:17:0x0051, code:
            return null;
     */
    public synchronized com.squareup.okhttp.internal.DiskLruCache.Snapshot get(java.lang.String r4) throws java.io.IOException {
        /*
        r3 = this;
        monitor-enter(r3);
        r3.initialize();	 Catch:{ all -> 0x0052 }
        r3.checkNotClosed();	 Catch:{ all -> 0x0052 }
        r3.validateKey(r4);	 Catch:{ all -> 0x0052 }
        r0 = r3.lruEntries;	 Catch:{ all -> 0x0052 }
        r0 = r0.get(r4);	 Catch:{ all -> 0x0052 }
        r0 = (com.squareup.okhttp.internal.DiskLruCache.Entry) r0;	 Catch:{ all -> 0x0052 }
        r1 = 0;
        if (r0 == 0) goto L_0x0050;
    L_0x0015:
        r2 = r0.readable;	 Catch:{ all -> 0x0052 }
        if (r2 != 0) goto L_0x001c;
    L_0x001b:
        goto L_0x0050;
    L_0x001c:
        r0 = r0.snapshot();	 Catch:{ all -> 0x0052 }
        if (r0 != 0) goto L_0x0024;
    L_0x0022:
        monitor-exit(r3);
        return r1;
    L_0x0024:
        r1 = r3.redundantOpCount;	 Catch:{ all -> 0x0052 }
        r1 = r1 + 1;
        r3.redundantOpCount = r1;	 Catch:{ all -> 0x0052 }
        r1 = r3.journalWriter;	 Catch:{ all -> 0x0052 }
        r2 = "READ";
        r1 = r1.writeUtf8(r2);	 Catch:{ all -> 0x0052 }
        r2 = 32;
        r1 = r1.writeByte(r2);	 Catch:{ all -> 0x0052 }
        r4 = r1.writeUtf8(r4);	 Catch:{ all -> 0x0052 }
        r1 = 10;
        r4.writeByte(r1);	 Catch:{ all -> 0x0052 }
        r4 = r3.journalRebuildRequired();	 Catch:{ all -> 0x0052 }
        if (r4 == 0) goto L_0x004e;
    L_0x0047:
        r4 = r3.executor;	 Catch:{ all -> 0x0052 }
        r1 = r3.cleanupRunnable;	 Catch:{ all -> 0x0052 }
        r4.execute(r1);	 Catch:{ all -> 0x0052 }
    L_0x004e:
        monitor-exit(r3);
        return r0;
    L_0x0050:
        monitor-exit(r3);
        return r1;
    L_0x0052:
        r4 = move-exception;
        monitor-exit(r3);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.get(java.lang.String):com.squareup.okhttp.internal.DiskLruCache$Snapshot");
    }

    public Editor edit(String str) throws IOException {
        return edit(str, -1);
    }

    /* JADX WARNING: Missing block: B:9:0x0024, code:
            return null;
     */
    private synchronized com.squareup.okhttp.internal.DiskLruCache.Editor edit(java.lang.String r6, long r7) throws java.io.IOException {
        /*
        r5 = this;
        monitor-enter(r5);
        r5.initialize();	 Catch:{ all -> 0x0067 }
        r5.checkNotClosed();	 Catch:{ all -> 0x0067 }
        r5.validateKey(r6);	 Catch:{ all -> 0x0067 }
        r0 = r5.lruEntries;	 Catch:{ all -> 0x0067 }
        r0 = r0.get(r6);	 Catch:{ all -> 0x0067 }
        r0 = (com.squareup.okhttp.internal.DiskLruCache.Entry) r0;	 Catch:{ all -> 0x0067 }
        r1 = -1;
        r3 = 0;
        r4 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1));
        if (r4 == 0) goto L_0x0025;
    L_0x0019:
        if (r0 == 0) goto L_0x0023;
    L_0x001b:
        r1 = r0.sequenceNumber;	 Catch:{ all -> 0x0067 }
        r4 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1));
        if (r4 == 0) goto L_0x0025;
    L_0x0023:
        monitor-exit(r5);
        return r3;
    L_0x0025:
        if (r0 == 0) goto L_0x002f;
    L_0x0027:
        r7 = r0.currentEditor;	 Catch:{ all -> 0x0067 }
        if (r7 == 0) goto L_0x002f;
    L_0x002d:
        monitor-exit(r5);
        return r3;
    L_0x002f:
        r7 = r5.journalWriter;	 Catch:{ all -> 0x0067 }
        r8 = "DIRTY";
        r7 = r7.writeUtf8(r8);	 Catch:{ all -> 0x0067 }
        r8 = 32;
        r7 = r7.writeByte(r8);	 Catch:{ all -> 0x0067 }
        r7 = r7.writeUtf8(r6);	 Catch:{ all -> 0x0067 }
        r8 = 10;
        r7.writeByte(r8);	 Catch:{ all -> 0x0067 }
        r7 = r5.journalWriter;	 Catch:{ all -> 0x0067 }
        r7.flush();	 Catch:{ all -> 0x0067 }
        r7 = r5.hasJournalErrors;	 Catch:{ all -> 0x0067 }
        if (r7 == 0) goto L_0x0051;
    L_0x004f:
        monitor-exit(r5);
        return r3;
    L_0x0051:
        if (r0 != 0) goto L_0x005d;
    L_0x0053:
        r0 = new com.squareup.okhttp.internal.DiskLruCache$Entry;	 Catch:{ all -> 0x0067 }
        r0.<init>(r5, r6, r3);	 Catch:{ all -> 0x0067 }
        r7 = r5.lruEntries;	 Catch:{ all -> 0x0067 }
        r7.put(r6, r0);	 Catch:{ all -> 0x0067 }
    L_0x005d:
        r6 = new com.squareup.okhttp.internal.DiskLruCache$Editor;	 Catch:{ all -> 0x0067 }
        r6.<init>(r5, r0, r3);	 Catch:{ all -> 0x0067 }
        r0.currentEditor = r6;	 Catch:{ all -> 0x0067 }
        monitor-exit(r5);
        return r6;
    L_0x0067:
        r6 = move-exception;
        monitor-exit(r5);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.edit(java.lang.String, long):com.squareup.okhttp.internal.DiskLruCache$Editor");
    }

    public File getDirectory() {
        return this.directory;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    public synchronized void setMaxSize(long j) {
        this.maxSize = j;
        if (this.initialized) {
            this.executor.execute(this.cleanupRunnable);
        }
    }

    public synchronized long size() throws IOException {
        initialize();
        return this.size;
    }

    /* JADX WARNING: Missing block: B:43:0x0111, code:
            return;
     */
    private synchronized void completeEdit(com.squareup.okhttp.internal.DiskLruCache.Editor r10, boolean r11) throws java.io.IOException {
        /*
        r9 = this;
        monitor-enter(r9);
        r0 = r10.entry;	 Catch:{ all -> 0x0118 }
        r1 = r0.currentEditor;	 Catch:{ all -> 0x0118 }
        if (r1 != r10) goto L_0x0112;
    L_0x000b:
        r1 = 0;
        if (r11 == 0) goto L_0x0051;
    L_0x000e:
        r2 = r0.readable;	 Catch:{ all -> 0x0118 }
        if (r2 != 0) goto L_0x0051;
    L_0x0014:
        r2 = 0;
    L_0x0015:
        r3 = r9.valueCount;	 Catch:{ all -> 0x0118 }
        if (r2 >= r3) goto L_0x0051;
    L_0x0019:
        r3 = r10.written;	 Catch:{ all -> 0x0118 }
        r3 = r3[r2];	 Catch:{ all -> 0x0118 }
        if (r3 == 0) goto L_0x0037;
    L_0x0021:
        r3 = r9.fileSystem;	 Catch:{ all -> 0x0118 }
        r4 = r0.dirtyFiles;	 Catch:{ all -> 0x0118 }
        r4 = r4[r2];	 Catch:{ all -> 0x0118 }
        r3 = r3.exists(r4);	 Catch:{ all -> 0x0118 }
        if (r3 != 0) goto L_0x0034;
    L_0x002f:
        r10.abort();	 Catch:{ all -> 0x0118 }
        monitor-exit(r9);
        return;
    L_0x0034:
        r2 = r2 + 1;
        goto L_0x0015;
    L_0x0037:
        r10.abort();	 Catch:{ all -> 0x0118 }
        r10 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0118 }
        r11 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0118 }
        r11.<init>();	 Catch:{ all -> 0x0118 }
        r0 = "Newly created entry didn't create value for index ";
        r11.append(r0);	 Catch:{ all -> 0x0118 }
        r11.append(r2);	 Catch:{ all -> 0x0118 }
        r11 = r11.toString();	 Catch:{ all -> 0x0118 }
        r10.<init>(r11);	 Catch:{ all -> 0x0118 }
        throw r10;	 Catch:{ all -> 0x0118 }
    L_0x0051:
        r10 = r9.valueCount;	 Catch:{ all -> 0x0118 }
        if (r1 >= r10) goto L_0x0091;
    L_0x0055:
        r10 = r0.dirtyFiles;	 Catch:{ all -> 0x0118 }
        r10 = r10[r1];	 Catch:{ all -> 0x0118 }
        if (r11 == 0) goto L_0x0089;
    L_0x005d:
        r2 = r9.fileSystem;	 Catch:{ all -> 0x0118 }
        r2 = r2.exists(r10);	 Catch:{ all -> 0x0118 }
        if (r2 == 0) goto L_0x008e;
    L_0x0065:
        r2 = r0.cleanFiles;	 Catch:{ all -> 0x0118 }
        r2 = r2[r1];	 Catch:{ all -> 0x0118 }
        r3 = r9.fileSystem;	 Catch:{ all -> 0x0118 }
        r3.rename(r10, r2);	 Catch:{ all -> 0x0118 }
        r10 = r0.lengths;	 Catch:{ all -> 0x0118 }
        r3 = r10[r1];	 Catch:{ all -> 0x0118 }
        r10 = r9.fileSystem;	 Catch:{ all -> 0x0118 }
        r5 = r10.size(r2);	 Catch:{ all -> 0x0118 }
        r10 = r0.lengths;	 Catch:{ all -> 0x0118 }
        r10[r1] = r5;	 Catch:{ all -> 0x0118 }
        r7 = r9.size;	 Catch:{ all -> 0x0118 }
        r7 = r7 - r3;
        r7 = r7 + r5;
        r9.size = r7;	 Catch:{ all -> 0x0118 }
        goto L_0x008e;
    L_0x0089:
        r2 = r9.fileSystem;	 Catch:{ all -> 0x0118 }
        r2.delete(r10);	 Catch:{ all -> 0x0118 }
    L_0x008e:
        r1 = r1 + 1;
        goto L_0x0051;
    L_0x0091:
        r10 = r9.redundantOpCount;	 Catch:{ all -> 0x0118 }
        r1 = 1;
        r10 = r10 + r1;
        r9.redundantOpCount = r10;	 Catch:{ all -> 0x0118 }
        r10 = 0;
        r0.currentEditor = r10;	 Catch:{ all -> 0x0118 }
        r10 = r0.readable;	 Catch:{ all -> 0x0118 }
        r10 = r10 | r11;
        r2 = 10;
        r3 = 32;
        if (r10 == 0) goto L_0x00d4;
    L_0x00a6:
        r0.readable = r1;	 Catch:{ all -> 0x0118 }
        r10 = r9.journalWriter;	 Catch:{ all -> 0x0118 }
        r1 = "CLEAN";
        r10 = r10.writeUtf8(r1);	 Catch:{ all -> 0x0118 }
        r10.writeByte(r3);	 Catch:{ all -> 0x0118 }
        r10 = r9.journalWriter;	 Catch:{ all -> 0x0118 }
        r1 = r0.key;	 Catch:{ all -> 0x0118 }
        r10.writeUtf8(r1);	 Catch:{ all -> 0x0118 }
        r10 = r9.journalWriter;	 Catch:{ all -> 0x0118 }
        r0.writeLengths(r10);	 Catch:{ all -> 0x0118 }
        r10 = r9.journalWriter;	 Catch:{ all -> 0x0118 }
        r10.writeByte(r2);	 Catch:{ all -> 0x0118 }
        if (r11 == 0) goto L_0x00f6;
    L_0x00c9:
        r10 = r9.nextSequenceNumber;	 Catch:{ all -> 0x0118 }
        r1 = 1;
        r1 = r1 + r10;
        r9.nextSequenceNumber = r1;	 Catch:{ all -> 0x0118 }
        r0.sequenceNumber = r10;	 Catch:{ all -> 0x0118 }
        goto L_0x00f6;
    L_0x00d4:
        r10 = r9.lruEntries;	 Catch:{ all -> 0x0118 }
        r11 = r0.key;	 Catch:{ all -> 0x0118 }
        r10.remove(r11);	 Catch:{ all -> 0x0118 }
        r10 = r9.journalWriter;	 Catch:{ all -> 0x0118 }
        r11 = "REMOVE";
        r10 = r10.writeUtf8(r11);	 Catch:{ all -> 0x0118 }
        r10.writeByte(r3);	 Catch:{ all -> 0x0118 }
        r10 = r9.journalWriter;	 Catch:{ all -> 0x0118 }
        r11 = r0.key;	 Catch:{ all -> 0x0118 }
        r10.writeUtf8(r11);	 Catch:{ all -> 0x0118 }
        r10 = r9.journalWriter;	 Catch:{ all -> 0x0118 }
        r10.writeByte(r2);	 Catch:{ all -> 0x0118 }
    L_0x00f6:
        r10 = r9.journalWriter;	 Catch:{ all -> 0x0118 }
        r10.flush();	 Catch:{ all -> 0x0118 }
        r10 = r9.size;	 Catch:{ all -> 0x0118 }
        r0 = r9.maxSize;	 Catch:{ all -> 0x0118 }
        r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1));
        if (r2 > 0) goto L_0x0109;
    L_0x0103:
        r10 = r9.journalRebuildRequired();	 Catch:{ all -> 0x0118 }
        if (r10 == 0) goto L_0x0110;
    L_0x0109:
        r10 = r9.executor;	 Catch:{ all -> 0x0118 }
        r11 = r9.cleanupRunnable;	 Catch:{ all -> 0x0118 }
        r10.execute(r11);	 Catch:{ all -> 0x0118 }
    L_0x0110:
        monitor-exit(r9);
        return;
    L_0x0112:
        r10 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0118 }
        r10.<init>();	 Catch:{ all -> 0x0118 }
        throw r10;	 Catch:{ all -> 0x0118 }
    L_0x0118:
        r10 = move-exception;
        monitor-exit(r9);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.completeEdit(com.squareup.okhttp.internal.DiskLruCache$Editor, boolean):void");
    }

    private boolean journalRebuildRequired() {
        int i = this.redundantOpCount;
        return i >= CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE && i >= this.lruEntries.size();
    }

    public synchronized boolean remove(String str) throws IOException {
        initialize();
        checkNotClosed();
        validateKey(str);
        Entry entry = (Entry) this.lruEntries.get(str);
        if (entry == null) {
            return false;
        }
        return removeEntry(entry);
    }

    private boolean removeEntry(Entry entry) throws IOException {
        if (entry.currentEditor != null) {
            entry.currentEditor.hasErrors = true;
        }
        for (int i = 0; i < this.valueCount; i++) {
            this.fileSystem.delete(entry.cleanFiles[i]);
            this.size -= entry.lengths[i];
            entry.lengths[i] = 0;
        }
        this.redundantOpCount++;
        this.journalWriter.writeUtf8(REMOVE).writeByte(32).writeUtf8(entry.key).writeByte(10);
        this.lruEntries.remove(entry.key);
        if (journalRebuildRequired()) {
            this.executor.execute(this.cleanupRunnable);
        }
        return true;
    }

    public synchronized boolean isClosed() {
        return this.closed;
    }

    private synchronized void checkNotClosed() {
        if (isClosed()) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void flush() throws IOException {
        if (this.initialized) {
            checkNotClosed();
            trimToSize();
            this.journalWriter.flush();
        }
    }

    public synchronized void close() throws IOException {
        if (!this.initialized || this.closed) {
            this.closed = true;
            return;
        }
        for (Entry entry : (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()])) {
            if (entry.currentEditor != null) {
                entry.currentEditor.abort();
            }
        }
        trimToSize();
        this.journalWriter.close();
        this.journalWriter = null;
        this.closed = true;
    }

    private void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            removeEntry((Entry) this.lruEntries.values().iterator().next());
        }
    }

    public void delete() throws IOException {
        close();
        this.fileSystem.deleteContents(this.directory);
    }

    public synchronized void evictAll() throws IOException {
        initialize();
        for (Entry removeEntry : (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()])) {
            removeEntry(removeEntry);
        }
    }

    private void validateKey(String str) {
        if (!LEGAL_KEY_PATTERN.matcher(str).matches()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("keys must match regex [a-z0-9_-]{1,120}: \"");
            stringBuilder.append(str);
            stringBuilder.append("\"");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public synchronized Iterator<Snapshot> snapshots() throws IOException {
        initialize();
        return new Iterator<Snapshot>() {
            final Iterator<Entry> delegate = new ArrayList(DiskLruCache.this.lruEntries.values()).iterator();
            Snapshot nextSnapshot;
            Snapshot removeSnapshot;

            public boolean hasNext() {
                if (this.nextSnapshot != null) {
                    return true;
                }
                synchronized (DiskLruCache.this) {
                    if (DiskLruCache.this.closed) {
                        return false;
                    }
                    while (this.delegate.hasNext()) {
                        Snapshot snapshot = ((Entry) this.delegate.next()).snapshot();
                        if (snapshot != null) {
                            this.nextSnapshot = snapshot;
                            return true;
                        }
                    }
                    return false;
                }
            }

            public Snapshot next() {
                if (hasNext()) {
                    this.removeSnapshot = this.nextSnapshot;
                    this.nextSnapshot = null;
                    return this.removeSnapshot;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                Snapshot snapshot = this.removeSnapshot;
                if (snapshot != null) {
                    try {
                        DiskLruCache.this.remove(snapshot.key);
                    } catch (IOException unused) {
                        this.removeSnapshot = null;
                    } catch (Throwable th) {
                        this.removeSnapshot = null;
                    }
                } else {
                    throw new IllegalStateException("remove() before next()");
                }
            }
        };
    }
}
