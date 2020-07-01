package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.TreeTraverser;
import com.google.common.graph.SuccessorsFunction;
import com.google.common.graph.Traverser;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@GwtIncompatible
@Beta
public final class Files {
    private static final SuccessorsFunction<File> FILE_TREE = new SuccessorsFunction<File>() {
        public Iterable<File> successors(File file) {
            return Files.fileTreeChildren(file);
        }
    };
    private static final TreeTraverser<File> FILE_TREE_TRAVERSER = new TreeTraverser<File>() {
        public String toString() {
            return "Files.fileTreeTraverser()";
        }

        public Iterable<File> children(File file) {
            return Files.fileTreeChildren(file);
        }
    };
    private static final int TEMP_DIR_ATTEMPTS = 10000;

    private static final class FileByteSink extends ByteSink {
        private final File file;
        private final ImmutableSet<FileWriteMode> modes;

        /* synthetic */ FileByteSink(File file, FileWriteMode[] fileWriteModeArr, AnonymousClass1 anonymousClass1) {
            this(file, fileWriteModeArr);
        }

        private FileByteSink(File file, FileWriteMode... fileWriteModeArr) {
            this.file = (File) Preconditions.checkNotNull(file);
            this.modes = ImmutableSet.copyOf((Object[]) fileWriteModeArr);
        }

        public FileOutputStream openStream() throws IOException {
            return new FileOutputStream(this.file, this.modes.contains(FileWriteMode.APPEND));
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Files.asByteSink(");
            stringBuilder.append(this.file);
            stringBuilder.append(", ");
            stringBuilder.append(this.modes);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class FileByteSource extends ByteSource {
        private final File file;

        /* synthetic */ FileByteSource(File file, AnonymousClass1 anonymousClass1) {
            this(file);
        }

        private FileByteSource(File file) {
            this.file = (File) Preconditions.checkNotNull(file);
        }

        public FileInputStream openStream() throws IOException {
            return new FileInputStream(this.file);
        }

        public Optional<Long> sizeIfKnown() {
            if (this.file.isFile()) {
                return Optional.of(Long.valueOf(this.file.length()));
            }
            return Optional.absent();
        }

        public long size() throws IOException {
            if (this.file.isFile()) {
                return this.file.length();
            }
            throw new FileNotFoundException(this.file.toString());
        }

        public byte[] read() throws IOException {
            Closer create = Closer.create();
            try {
                FileInputStream fileInputStream = (FileInputStream) create.register(openStream());
                byte[] toByteArray = ByteStreams.toByteArray(fileInputStream, fileInputStream.getChannel().size());
                create.close();
                return toByteArray;
            } catch (Throwable th) {
                create.close();
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Files.asByteSource(");
            stringBuilder.append(this.file);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private enum FilePredicate implements Predicate<File> {
        IS_DIRECTORY {
            public String toString() {
                return "Files.isDirectory()";
            }

            public boolean apply(File file) {
                return file.isDirectory();
            }
        },
        IS_FILE {
            public String toString() {
                return "Files.isFile()";
            }

            public boolean apply(File file) {
                return file.isFile();
            }
        }
    }

    private Files() {
    }

    public static BufferedReader newReader(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    public static BufferedWriter newWriter(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    }

    public static ByteSource asByteSource(File file) {
        return new FileByteSource(file, null);
    }

    public static ByteSink asByteSink(File file, FileWriteMode... fileWriteModeArr) {
        return new FileByteSink(file, fileWriteModeArr, null);
    }

    public static CharSource asCharSource(File file, Charset charset) {
        return asByteSource(file).asCharSource(charset);
    }

    public static CharSink asCharSink(File file, Charset charset, FileWriteMode... fileWriteModeArr) {
        return asByteSink(file, fileWriteModeArr).asCharSink(charset);
    }

    public static byte[] toByteArray(File file) throws IOException {
        return asByteSource(file).read();
    }

    @Deprecated
    public static String toString(File file, Charset charset) throws IOException {
        return asCharSource(file, charset).read();
    }

    public static void write(byte[] bArr, File file) throws IOException {
        asByteSink(file, new FileWriteMode[0]).write(bArr);
    }

    @Deprecated
    public static void write(CharSequence charSequence, File file, Charset charset) throws IOException {
        asCharSink(file, charset, new FileWriteMode[0]).write(charSequence);
    }

    public static void copy(File file, OutputStream outputStream) throws IOException {
        asByteSource(file).copyTo(outputStream);
    }

    public static void copy(File file, File file2) throws IOException {
        Preconditions.checkArgument(file.equals(file2) ^ 1, "Source %s and destination %s must be different", (Object) file, (Object) file2);
        asByteSource(file).copyTo(asByteSink(file2, new FileWriteMode[0]));
    }

    @Deprecated
    public static void copy(File file, Charset charset, Appendable appendable) throws IOException {
        asCharSource(file, charset).copyTo(appendable);
    }

    @Deprecated
    public static void append(CharSequence charSequence, File file, Charset charset) throws IOException {
        asCharSink(file, charset, FileWriteMode.APPEND).write(charSequence);
    }

    public static boolean equal(File file, File file2) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        if (file == file2 || file.equals(file2)) {
            return true;
        }
        long length = file.length();
        long length2 = file2.length();
        if (length == 0 || length2 == 0 || length == length2) {
            return asByteSource(file).contentEquals(asByteSource(file2));
        }
        return false;
    }

    public static File createTempDir() {
        File file = new File(System.getProperty("java.io.tmpdir"));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.currentTimeMillis());
        stringBuilder.append("-");
        String stringBuilder2 = stringBuilder.toString();
        for (int i = 0; i < 10000; i++) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(stringBuilder2);
            stringBuilder3.append(i);
            File file2 = new File(file, stringBuilder3.toString());
            if (file2.mkdir()) {
                return file2;
            }
        }
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("Failed to create directory within 10000 attempts (tried ");
        stringBuilder4.append(stringBuilder2);
        stringBuilder4.append("0 to ");
        stringBuilder4.append(stringBuilder2);
        stringBuilder4.append(9999);
        stringBuilder4.append(')');
        throw new IllegalStateException(stringBuilder4.toString());
    }

    public static void touch(File file) throws IOException {
        Preconditions.checkNotNull(file);
        if (!file.createNewFile() && !file.setLastModified(System.currentTimeMillis())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to update modification time of ");
            stringBuilder.append(file);
            throw new IOException(stringBuilder.toString());
        }
    }

    public static void createParentDirs(File file) throws IOException {
        Preconditions.checkNotNull(file);
        File parentFile = file.getCanonicalFile().getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
            if (!parentFile.isDirectory()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to create parent directories of ");
                stringBuilder.append(file);
                throw new IOException(stringBuilder.toString());
            }
        }
    }

    public static void move(File file, File file2) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        Preconditions.checkArgument(file.equals(file2) ^ 1, "Source %s and destination %s must be different", (Object) file, (Object) file2);
        if (!file.renameTo(file2)) {
            copy(file, file2);
            if (!file.delete()) {
                String str = "Unable to delete ";
                StringBuilder stringBuilder;
                if (file2.delete()) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str);
                    stringBuilder.append(file);
                    throw new IOException(stringBuilder.toString());
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(file2);
                throw new IOException(stringBuilder.toString());
            }
        }
    }

    @Deprecated
    public static String readFirstLine(File file, Charset charset) throws IOException {
        return asCharSource(file, charset).readFirstLine();
    }

    public static List<String> readLines(File file, Charset charset) throws IOException {
        return (List) asCharSource(file, charset).readLines(new LineProcessor<List<String>>() {
            final List<String> result = Lists.newArrayList();

            public boolean processLine(String str) {
                this.result.add(str);
                return true;
            }

            public List<String> getResult() {
                return this.result;
            }
        });
    }

    @CanIgnoreReturnValue
    @Deprecated
    public static <T> T readLines(File file, Charset charset, LineProcessor<T> lineProcessor) throws IOException {
        return asCharSource(file, charset).readLines(lineProcessor);
    }

    @CanIgnoreReturnValue
    @Deprecated
    public static <T> T readBytes(File file, ByteProcessor<T> byteProcessor) throws IOException {
        return asByteSource(file).read(byteProcessor);
    }

    @Deprecated
    public static HashCode hash(File file, HashFunction hashFunction) throws IOException {
        return asByteSource(file).hash(hashFunction);
    }

    public static MappedByteBuffer map(File file) throws IOException {
        Preconditions.checkNotNull(file);
        return map(file, MapMode.READ_ONLY);
    }

    public static MappedByteBuffer map(File file, MapMode mapMode) throws IOException {
        return mapInternal(file, mapMode, -1);
    }

    public static MappedByteBuffer map(File file, MapMode mapMode, long j) throws IOException {
        Preconditions.checkArgument(j >= 0, "size (%s) may not be negative", j);
        return mapInternal(file, mapMode, j);
    }

    private static MappedByteBuffer mapInternal(File file, MapMode mapMode, long j) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(mapMode);
        Closer create = Closer.create();
        try {
            FileChannel fileChannel = (FileChannel) create.register(((RandomAccessFile) create.register(new RandomAccessFile(file, mapMode == MapMode.READ_ONLY ? "r" : "rw"))).getChannel());
            if (j == -1) {
                j = fileChannel.size();
            }
            MappedByteBuffer map = fileChannel.map(mapMode, 0, j);
            create.close();
            return map;
        } catch (Throwable th) {
            create.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0023 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0053  */
    /* JADX WARNING: Missing block: B:14:0x004d, code:
            if (r4.equals(r1) != false) goto L_0x0051;
     */
    public static java.lang.String simplifyPath(java.lang.String r11) {
        /*
        com.google.common.base.Preconditions.checkNotNull(r11);
        r0 = r11.length();
        r1 = ".";
        if (r0 != 0) goto L_0x000c;
    L_0x000b:
        return r1;
    L_0x000c:
        r0 = 47;
        r2 = com.google.common.base.Splitter.on(r0);
        r2 = r2.omitEmptyStrings();
        r2 = r2.split(r11);
        r3 = new java.util.ArrayList;
        r3.<init>();
        r2 = r2.iterator();
    L_0x0023:
        r4 = r2.hasNext();
        r5 = 0;
        if (r4 == 0) goto L_0x007d;
    L_0x002a:
        r4 = r2.next();
        r4 = (java.lang.String) r4;
        r6 = -1;
        r7 = r4.hashCode();
        r8 = 46;
        r9 = "..";
        r10 = 1;
        if (r7 == r8) goto L_0x0049;
    L_0x003c:
        r5 = 1472; // 0x5c0 float:2.063E-42 double:7.273E-321;
        if (r7 == r5) goto L_0x0041;
    L_0x0040:
        goto L_0x0050;
    L_0x0041:
        r5 = r4.equals(r9);
        if (r5 == 0) goto L_0x0050;
    L_0x0047:
        r5 = 1;
        goto L_0x0051;
    L_0x0049:
        r7 = r4.equals(r1);
        if (r7 == 0) goto L_0x0050;
    L_0x004f:
        goto L_0x0051;
    L_0x0050:
        r5 = -1;
    L_0x0051:
        if (r5 == 0) goto L_0x0023;
    L_0x0053:
        if (r5 == r10) goto L_0x0059;
    L_0x0055:
        r3.add(r4);
        goto L_0x0023;
    L_0x0059:
        r4 = r3.size();
        if (r4 <= 0) goto L_0x0079;
    L_0x005f:
        r4 = r3.size();
        r4 = r4 - r10;
        r4 = r3.get(r4);
        r4 = (java.lang.String) r4;
        r4 = r4.equals(r9);
        if (r4 != 0) goto L_0x0079;
    L_0x0070:
        r4 = r3.size();
        r4 = r4 - r10;
        r3.remove(r4);
        goto L_0x0023;
    L_0x0079:
        r3.add(r9);
        goto L_0x0023;
    L_0x007d:
        r2 = com.google.common.base.Joiner.on(r0);
        r2 = r2.join(r3);
        r11 = r11.charAt(r5);
        r3 = "/";
        if (r11 != r0) goto L_0x009d;
    L_0x008d:
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r11.append(r3);
        r11.append(r2);
        r11 = r11.toString();
        goto L_0x009e;
    L_0x009d:
        r11 = r2;
    L_0x009e:
        r0 = "/../";
        r0 = r11.startsWith(r0);
        if (r0 == 0) goto L_0x00ac;
    L_0x00a6:
        r0 = 3;
        r11 = r11.substring(r0);
        goto L_0x009e;
    L_0x00ac:
        r0 = "/..";
        r0 = r11.equals(r0);
        if (r0 == 0) goto L_0x00b6;
    L_0x00b4:
        r11 = r3;
        goto L_0x00bf;
    L_0x00b6:
        r0 = "";
        r0 = r0.equals(r11);
        if (r0 == 0) goto L_0x00bf;
    L_0x00be:
        r11 = r1;
    L_0x00bf:
        return r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.io.Files.simplifyPath(java.lang.String):java.lang.String");
    }

    public static String getFileExtension(String str) {
        Preconditions.checkNotNull(str);
        str = new File(str).getName();
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf == -1) {
            return "";
        }
        return str.substring(lastIndexOf + 1);
    }

    public static String getNameWithoutExtension(String str) {
        Preconditions.checkNotNull(str);
        str = new File(str).getName();
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
    }

    @Deprecated
    static TreeTraverser<File> fileTreeTraverser() {
        return FILE_TREE_TRAVERSER;
    }

    public static Traverser<File> fileTraverser() {
        return Traverser.forTree(FILE_TREE);
    }

    private static Iterable<File> fileTreeChildren(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                return Collections.unmodifiableList(Arrays.asList(listFiles));
            }
        }
        return Collections.emptyList();
    }

    public static Predicate<File> isDirectory() {
        return FilePredicate.IS_DIRECTORY;
    }

    public static Predicate<File> isFile() {
        return FilePredicate.IS_FILE;
    }
}
