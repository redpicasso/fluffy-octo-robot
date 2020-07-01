package com.drew.tools;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.lang.StringUtil;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifThumbnailDirectory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ProcessAllImagesInFolderUtility {

    interface FileHandler {
        void onBeforeExtraction(@NotNull File file, @NotNull PrintStream printStream, @NotNull String str);

        void onExtractionError(@NotNull File file, @NotNull Throwable th, @NotNull PrintStream printStream);

        void onExtractionSuccess(@NotNull File file, @NotNull Metadata metadata, @NotNull String str, @NotNull PrintStream printStream);

        void onScanCompleted(@NotNull PrintStream printStream);

        void onStartingDirectory(@NotNull File file);

        boolean shouldProcess(@NotNull File file);
    }

    static abstract class FileHandlerBase implements FileHandler {
        private int _errorCount = 0;
        private int _exceptionCount = 0;
        private long _processedByteCount = 0;
        private int _processedFileCount = 0;
        private final Set<String> _supportedExtensions = new HashSet(Arrays.asList(new String[]{"jpg", "jpeg", "png", "gif", "bmp", "ico", "webp", "pcx", "ai", "eps", "nef", "crw", "cr2", "orf", "arw", "raf", "srw", "x3f", "rw2", "rwl", "tif", "tiff", "psd", "dng", "3g2", "3gp", "m4v", "mov", "mp4", "pbm", "pnm", "pgm"}));

        public void onStartingDirectory(@NotNull File file) {
        }

        FileHandlerBase() {
        }

        public boolean shouldProcess(@NotNull File file) {
            String extension = getExtension(file);
            return extension != null && this._supportedExtensions.contains(extension.toLowerCase());
        }

        public void onBeforeExtraction(@NotNull File file, @NotNull PrintStream printStream, @NotNull String str) {
            this._processedFileCount++;
            this._processedByteCount += file.length();
        }

        public void onExtractionError(@NotNull File file, @NotNull Throwable th, @NotNull PrintStream printStream) {
            this._exceptionCount++;
            printStream.printf("\t[%s] %s\n", new Object[]{th.getClass().getName(), th.getMessage()});
        }

        public void onExtractionSuccess(@NotNull File file, @NotNull Metadata metadata, @NotNull String str, @NotNull PrintStream printStream) {
            if (metadata.hasErrors()) {
                printStream.print(file);
                printStream.print(10);
                for (Directory directory : metadata.getDirectories()) {
                    if (directory.hasErrors()) {
                        for (String str2 : directory.getErrors()) {
                            printStream.printf("\t[%s] %s\n", new Object[]{directory.getName(), str2});
                            this._errorCount++;
                        }
                    }
                }
            }
        }

        public void onScanCompleted(@NotNull PrintStream printStream) {
            if (this._processedFileCount > 0) {
                printStream.print(String.format("Processed %,d files (%,d bytes) with %,d exceptions and %,d file errors\n", new Object[]{Integer.valueOf(this._processedFileCount), Long.valueOf(this._processedByteCount), Integer.valueOf(this._exceptionCount), Integer.valueOf(this._errorCount)}));
            }
        }

        @Nullable
        protected String getExtension(@NotNull File file) {
            String name = file.getName();
            int lastIndexOf = name.lastIndexOf(46);
            if (lastIndexOf == -1 || lastIndexOf == name.length() - 1) {
                return null;
            }
            return name.substring(lastIndexOf + 1);
        }
    }

    static class BasicFileHandler extends FileHandlerBase {
        BasicFileHandler() {
        }

        public void onExtractionSuccess(@NotNull File file, @NotNull Metadata metadata, @NotNull String str, @NotNull PrintStream printStream) {
            super.onExtractionSuccess(file, metadata, str, printStream);
            for (Directory directory : metadata.getDirectories()) {
                directory.getName();
                for (Tag tag : directory.getTags()) {
                    tag.getTagName();
                    tag.getDescription();
                }
            }
        }
    }

    static class MarkdownTableOutputHandler extends FileHandlerBase {
        private final Map<String, String> _extensionEquivalence = new HashMap();
        private final Map<String, List<Row>> _rowListByExtension = new HashMap();

        static class Row {
            @Nullable
            private String exifVersion;
            final File file;
            @Nullable
            private String makernote;
            @Nullable
            private String manufacturer;
            final Metadata metadata;
            @Nullable
            private String model;
            @NotNull
            final String relativePath;
            @Nullable
            private String thumbnail;

            Row(@NotNull File file, @NotNull Metadata metadata, @NotNull String str) {
                boolean containsTag;
                this.file = file;
                this.metadata = metadata;
                this.relativePath = str;
                ExifIFD0Directory exifIFD0Directory = (ExifIFD0Directory) metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
                ExifSubIFDDirectory exifSubIFDDirectory = (ExifSubIFDDirectory) metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                ExifThumbnailDirectory exifThumbnailDirectory = (ExifThumbnailDirectory) metadata.getFirstDirectoryOfType(ExifThumbnailDirectory.class);
                if (exifIFD0Directory != null) {
                    this.manufacturer = exifIFD0Directory.getDescription(271);
                    this.model = exifIFD0Directory.getDescription(272);
                }
                if (exifSubIFDDirectory != null) {
                    this.exifVersion = exifSubIFDDirectory.getDescription(ExifDirectoryBase.TAG_EXIF_VERSION);
                    containsTag = exifSubIFDDirectory.containsTag(ExifDirectoryBase.TAG_MAKERNOTE);
                } else {
                    containsTag = false;
                }
                if (exifThumbnailDirectory != null) {
                    String format = (exifThumbnailDirectory.getInteger(256) == null || exifThumbnailDirectory.getInteger(257) == null) ? "Yes" : String.format("Yes (%s x %s)", new Object[]{exifThumbnailDirectory.getInteger(256), exifThumbnailDirectory.getInteger(257)});
                    this.thumbnail = format;
                }
                for (Directory directory : metadata.getDirectories()) {
                    CharSequence charSequence = "Makernote";
                    if (directory.getClass().getName().contains(charSequence)) {
                        this.makernote = directory.getName().replace(charSequence, "").trim();
                        break;
                    }
                }
                if (this.makernote == null) {
                    this.makernote = containsTag ? "(Unknown)" : "N/A";
                }
            }
        }

        public MarkdownTableOutputHandler() {
            this._extensionEquivalence.put("jpeg", "jpg");
        }

        public void onExtractionSuccess(@NotNull File file, @NotNull Metadata metadata, @NotNull String str, @NotNull PrintStream printStream) {
            super.onExtractionSuccess(file, metadata, str, printStream);
            String extension = getExtension(file);
            if (extension != null) {
                Object toLowerCase = extension.toLowerCase();
                if (this._extensionEquivalence.containsKey(toLowerCase)) {
                    toLowerCase = (String) this._extensionEquivalence.get(toLowerCase);
                }
                List list = (List) this._rowListByExtension.get(toLowerCase);
                if (list == null) {
                    list = new ArrayList();
                    this._rowListByExtension.put(toLowerCase, list);
                }
                list.add(new Row(file, metadata, str));
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:33:0x004f  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x0054 A:{SYNTHETIC, Splitter: B:35:0x0054} */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x003e  */
        /* JADX WARNING: Removed duplicated region for block: B:41:? A:{SYNTHETIC, RETURN} */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x0043 A:{SYNTHETIC, Splitter: B:27:0x0043} */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x004f  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x0054 A:{SYNTHETIC, Splitter: B:35:0x0054} */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x003e  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x0043 A:{SYNTHETIC, Splitter: B:27:0x0043} */
        /* JADX WARNING: Removed duplicated region for block: B:41:? A:{SYNTHETIC, RETURN} */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x004f  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x0054 A:{SYNTHETIC, Splitter: B:35:0x0054} */
        public void onScanCompleted(@com.drew.lang.annotations.NotNull java.io.PrintStream r5) {
            /*
            r4 = this;
            super.onScanCompleted(r5);
            r5 = 0;
            r0 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0037, all -> 0x0034 }
            r1 = "../wiki/ImageDatabaseSummary.md";
            r2 = 0;
            r0.<init>(r1, r2);	 Catch:{ IOException -> 0x0037, all -> 0x0034 }
            r1 = new java.io.PrintStream;	 Catch:{ IOException -> 0x002f, all -> 0x002a }
            r1.<init>(r0, r2);	 Catch:{ IOException -> 0x002f, all -> 0x002a }
            r4.writeOutput(r1);	 Catch:{ IOException -> 0x0024, all -> 0x001e }
            r1.flush();	 Catch:{ IOException -> 0x0024, all -> 0x001e }
            r1.close();
            r0.close();	 Catch:{ IOException -> 0x0047 }
            goto L_0x004b;
        L_0x001e:
            r5 = move-exception;
            r3 = r0;
            r0 = r5;
            r5 = r1;
            r1 = r3;
            goto L_0x004d;
        L_0x0024:
            r5 = move-exception;
            r3 = r0;
            r0 = r5;
            r5 = r1;
            r1 = r3;
            goto L_0x0039;
        L_0x002a:
            r1 = move-exception;
            r3 = r1;
            r1 = r0;
            r0 = r3;
            goto L_0x004d;
        L_0x002f:
            r1 = move-exception;
            r3 = r1;
            r1 = r0;
            r0 = r3;
            goto L_0x0039;
        L_0x0034:
            r0 = move-exception;
            r1 = r5;
            goto L_0x004d;
        L_0x0037:
            r0 = move-exception;
            r1 = r5;
        L_0x0039:
            r0.printStackTrace();	 Catch:{ all -> 0x004c }
            if (r5 == 0) goto L_0x0041;
        L_0x003e:
            r5.close();
        L_0x0041:
            if (r1 == 0) goto L_0x004b;
        L_0x0043:
            r1.close();	 Catch:{ IOException -> 0x0047 }
            goto L_0x004b;
        L_0x0047:
            r5 = move-exception;
            r5.printStackTrace();
        L_0x004b:
            return;
        L_0x004c:
            r0 = move-exception;
        L_0x004d:
            if (r5 == 0) goto L_0x0052;
        L_0x004f:
            r5.close();
        L_0x0052:
            if (r1 == 0) goto L_0x005c;
        L_0x0054:
            r1.close();	 Catch:{ IOException -> 0x0058 }
            goto L_0x005c;
        L_0x0058:
            r5 = move-exception;
            r5.printStackTrace();
        L_0x005c:
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.drew.tools.ProcessAllImagesInFolderUtility.MarkdownTableOutputHandler.onScanCompleted(java.io.PrintStream):void");
        }

        private void writeOutput(@NotNull PrintStream printStream) throws IOException {
            Writer outputStreamWriter = new OutputStreamWriter(printStream);
            outputStreamWriter.write("# Image Database Summary\n\n");
            for (Entry entry : this._rowListByExtension.entrySet()) {
                String str = (String) entry.getKey();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("## ");
                stringBuilder.append(str.toUpperCase());
                stringBuilder.append(" Files\n\n");
                outputStreamWriter.write(stringBuilder.toString());
                outputStreamWriter.write("File|Manufacturer|Model|Dir Count|Exif?|Makernote|Thumbnail|All Data\n");
                outputStreamWriter.write("----|------------|-----|---------|-----|---------|---------|--------\n");
                List<Row> list = (List) entry.getValue();
                Collections.sort(list, new Comparator<Row>() {
                    public int compare(Row row, Row row2) {
                        int compare = StringUtil.compare(row.manufacturer, row2.manufacturer);
                        return compare != 0 ? compare : StringUtil.compare(row.model, row2.model);
                    }
                });
                for (Row row : list) {
                    Object[] objArr = new Object[11];
                    objArr[0] = row.file.getName();
                    objArr[1] = row.relativePath;
                    objArr[2] = StringUtil.urlEncode(row.file.getName());
                    String str2 = "";
                    objArr[3] = row.manufacturer == null ? str2 : row.manufacturer;
                    objArr[4] = row.model == null ? str2 : row.model;
                    objArr[5] = Integer.valueOf(row.metadata.getDirectoryCount());
                    objArr[6] = row.exifVersion == null ? str2 : row.exifVersion;
                    objArr[7] = row.makernote == null ? str2 : row.makernote;
                    if (row.thumbnail != null) {
                        str2 = row.thumbnail;
                    }
                    objArr[8] = str2;
                    objArr[9] = row.relativePath;
                    objArr[10] = StringUtil.urlEncode(row.file.getName()).toLowerCase();
                    outputStreamWriter.write(String.format("[%s](https://raw.githubusercontent.com/drewnoakes/metadata-extractor-images/master/%s/%s)|%s|%s|%d|%s|%s|%s|[metadata](https://raw.githubusercontent.com/drewnoakes/metadata-extractor-images/master/%s/metadata/%s.txt)\n", objArr));
                }
                outputStreamWriter.write(10);
            }
            outputStreamWriter.flush();
        }
    }

    static class TextFileOutputHandler extends FileHandlerBase {
        private static final String NEW_LINE = "\n";

        TextFileOutputHandler() {
        }

        public void onStartingDirectory(@NotNull File file) {
            super.onStartingDirectory(file);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(file);
            stringBuilder.append("/metadata");
            File file2 = new File(stringBuilder.toString());
            if (file2.exists()) {
                deleteRecursively(file2);
            }
        }

        private static void deleteRecursively(@NotNull File file) {
            if (file.isDirectory()) {
                if (file.exists()) {
                    String[] list = file.list();
                    if (list != null) {
                        for (String file2 : list) {
                            File file3 = new File(file2);
                            if (file3.isDirectory()) {
                                deleteRecursively(file3);
                            } else {
                                file3.delete();
                            }
                        }
                    }
                }
                file.delete();
                return;
            }
            throw new IllegalArgumentException("Must be a directory.");
        }

        public void onBeforeExtraction(@NotNull File file, @NotNull PrintStream printStream, @NotNull String str) {
            super.onBeforeExtraction(file, printStream, str);
            printStream.print(file.getAbsoluteFile());
            printStream.print("\n");
        }

        /* JADX WARNING: Removed duplicated region for block: B:92:0x005f A:{SYNTHETIC} */
        /* JADX WARNING: Removed duplicated region for block: B:74:0x012f A:{Catch:{ all -> 0x0141 }} */
        /* JADX WARNING: Removed duplicated region for block: B:74:0x012f A:{Catch:{ all -> 0x0141 }} */
        /* JADX WARNING: Removed duplicated region for block: B:92:0x005f A:{SYNTHETIC} */
        public void onExtractionSuccess(@com.drew.lang.annotations.NotNull java.io.File r17, @com.drew.lang.annotations.NotNull com.drew.metadata.Metadata r18, @com.drew.lang.annotations.NotNull java.lang.String r19, @com.drew.lang.annotations.NotNull java.io.PrintStream r20) {
            /*
            r16 = this;
            super.onExtractionSuccess(r17, r18, r19, r20);
            r2 = openWriter(r17);	 Catch:{ all -> 0x0143 }
            r0 = r18.hasErrors();	 Catch:{ all -> 0x0141 }
            r3 = 2;
            r4 = 3;
            r5 = 1;
            r6 = "\n";
            r7 = 0;
            if (r0 == 0) goto L_0x0057;
        L_0x0013:
            r0 = r18.getDirectories();	 Catch:{ all -> 0x0141 }
            r0 = r0.iterator();	 Catch:{ all -> 0x0141 }
        L_0x001b:
            r8 = r0.hasNext();	 Catch:{ all -> 0x0141 }
            if (r8 == 0) goto L_0x0054;
        L_0x0021:
            r8 = r0.next();	 Catch:{ all -> 0x0141 }
            r8 = (com.drew.metadata.Directory) r8;	 Catch:{ all -> 0x0141 }
            r9 = r8.hasErrors();	 Catch:{ all -> 0x0141 }
            if (r9 != 0) goto L_0x002e;
        L_0x002d:
            goto L_0x001b;
        L_0x002e:
            r9 = r8.getErrors();	 Catch:{ all -> 0x0141 }
            r9 = r9.iterator();	 Catch:{ all -> 0x0141 }
        L_0x0036:
            r10 = r9.hasNext();	 Catch:{ all -> 0x0141 }
            if (r10 == 0) goto L_0x001b;
        L_0x003c:
            r10 = r9.next();	 Catch:{ all -> 0x0141 }
            r10 = (java.lang.String) r10;	 Catch:{ all -> 0x0141 }
            r11 = "[ERROR: %s] %s%s";
            r12 = new java.lang.Object[r4];	 Catch:{ all -> 0x0141 }
            r13 = r8.getName();	 Catch:{ all -> 0x0141 }
            r12[r7] = r13;	 Catch:{ all -> 0x0141 }
            r12[r5] = r10;	 Catch:{ all -> 0x0141 }
            r12[r3] = r6;	 Catch:{ all -> 0x0141 }
            r2.format(r11, r12);	 Catch:{ all -> 0x0141 }
            goto L_0x0036;
        L_0x0054:
            r2.write(r6);	 Catch:{ all -> 0x0141 }
        L_0x0057:
            r0 = r18.getDirectories();	 Catch:{ all -> 0x0141 }
            r8 = r0.iterator();	 Catch:{ all -> 0x0141 }
        L_0x005f:
            r0 = r8.hasNext();	 Catch:{ all -> 0x0141 }
            if (r0 == 0) goto L_0x0134;
        L_0x0065:
            r0 = r8.next();	 Catch:{ all -> 0x0141 }
            r0 = (com.drew.metadata.Directory) r0;	 Catch:{ all -> 0x0141 }
            r9 = r0.getName();	 Catch:{ all -> 0x0141 }
            r10 = r0.getTags();	 Catch:{ all -> 0x0141 }
            r10 = r10.iterator();	 Catch:{ all -> 0x0141 }
        L_0x0077:
            r11 = r10.hasNext();	 Catch:{ all -> 0x0141 }
            r12 = 4;
            r13 = "";
            if (r11 == 0) goto L_0x00b5;
        L_0x0080:
            r11 = r10.next();	 Catch:{ all -> 0x0141 }
            r11 = (com.drew.metadata.Tag) r11;	 Catch:{ all -> 0x0141 }
            r14 = r11.getTagName();	 Catch:{ all -> 0x0141 }
            r15 = r11.getDescription();	 Catch:{ all -> 0x0141 }
            if (r15 != 0) goto L_0x0091;
        L_0x0090:
            goto L_0x0092;
        L_0x0091:
            r13 = r15;
        L_0x0092:
            r15 = r0 instanceof com.drew.metadata.file.FileSystemDirectory;	 Catch:{ all -> 0x0141 }
            if (r15 == 0) goto L_0x009e;
        L_0x0096:
            r15 = r11.getTagType();	 Catch:{ all -> 0x0141 }
            if (r15 != r4) goto L_0x009e;
        L_0x009c:
            r13 = "<omitted for regression testing as checkout dependent>";
        L_0x009e:
            r15 = "[%s - %s] %s = %s%s";
            r1 = 5;
            r1 = new java.lang.Object[r1];	 Catch:{ all -> 0x0141 }
            r1[r7] = r9;	 Catch:{ all -> 0x0141 }
            r11 = r11.getTagTypeHex();	 Catch:{ all -> 0x0141 }
            r1[r5] = r11;	 Catch:{ all -> 0x0141 }
            r1[r3] = r14;	 Catch:{ all -> 0x0141 }
            r1[r4] = r13;	 Catch:{ all -> 0x0141 }
            r1[r12] = r6;	 Catch:{ all -> 0x0141 }
            r2.format(r15, r1);	 Catch:{ all -> 0x0141 }
            goto L_0x0077;
        L_0x00b5:
            r1 = r0.getTagCount();	 Catch:{ all -> 0x0141 }
            if (r1 == 0) goto L_0x00be;
        L_0x00bb:
            r2.write(r6);	 Catch:{ all -> 0x0141 }
        L_0x00be:
            r1 = r0 instanceof com.drew.metadata.xmp.XmpDirectory;	 Catch:{ all -> 0x0141 }
            if (r1 == 0) goto L_0x005f;
        L_0x00c2:
            r0 = (com.drew.metadata.xmp.XmpDirectory) r0;	 Catch:{ all -> 0x0141 }
            r0 = r0.getXMPMeta();	 Catch:{ all -> 0x0141 }
            r0 = r0.iterator();	 Catch:{ XMPException -> 0x0128 }
            r1 = 0;
        L_0x00cd:
            r9 = r0.hasNext();	 Catch:{ XMPException -> 0x0126 }
            if (r9 == 0) goto L_0x012d;
        L_0x00d3:
            r9 = r0.next();	 Catch:{ XMPException -> 0x0126 }
            r9 = (com.adobe.xmp.properties.XMPPropertyInfo) r9;	 Catch:{ XMPException -> 0x0126 }
            r10 = r9.getNamespace();	 Catch:{ XMPException -> 0x0126 }
            r11 = r9.getPath();	 Catch:{ XMPException -> 0x0126 }
            r9 = r9.getValue();	 Catch:{ XMPException -> 0x0126 }
            if (r10 != 0) goto L_0x00e8;
        L_0x00e7:
            r10 = r13;
        L_0x00e8:
            if (r11 != 0) goto L_0x00eb;
        L_0x00ea:
            r11 = r13;
        L_0x00eb:
            if (r9 != 0) goto L_0x00ef;
        L_0x00ed:
            r4 = r13;
            goto L_0x0111;
        L_0x00ef:
            r14 = r9.length();	 Catch:{ XMPException -> 0x0126 }
            r15 = 512; // 0x200 float:7.175E-43 double:2.53E-321;
            if (r14 <= r15) goto L_0x0110;
        L_0x00f7:
            r14 = "%s <truncated from %d characters>";
            r4 = new java.lang.Object[r3];	 Catch:{ XMPException -> 0x0123 }
            r15 = r9.substring(r7, r15);	 Catch:{ XMPException -> 0x0123 }
            r4[r7] = r15;	 Catch:{ XMPException -> 0x0123 }
            r9 = r9.length();	 Catch:{ XMPException -> 0x0123 }
            r9 = java.lang.Integer.valueOf(r9);	 Catch:{ XMPException -> 0x0123 }
            r4[r5] = r9;	 Catch:{ XMPException -> 0x0123 }
            r4 = java.lang.String.format(r14, r4);	 Catch:{ XMPException -> 0x0123 }
            goto L_0x0111;
        L_0x0110:
            r4 = r9;
        L_0x0111:
            r9 = "[XMPMeta - %s] %s = %s%s";
            r14 = new java.lang.Object[r12];	 Catch:{ XMPException -> 0x0123 }
            r14[r7] = r10;	 Catch:{ XMPException -> 0x0123 }
            r14[r5] = r11;	 Catch:{ XMPException -> 0x0123 }
            r14[r3] = r4;	 Catch:{ XMPException -> 0x0123 }
            r4 = 3;
            r14[r4] = r6;	 Catch:{ XMPException -> 0x0126 }
            r2.format(r9, r14);	 Catch:{ XMPException -> 0x0126 }
            r1 = 1;
            goto L_0x00cd;
        L_0x0123:
            r0 = move-exception;
            r4 = 3;
            goto L_0x012a;
        L_0x0126:
            r0 = move-exception;
            goto L_0x012a;
        L_0x0128:
            r0 = move-exception;
            r1 = 0;
        L_0x012a:
            r0.printStackTrace();	 Catch:{ all -> 0x0141 }
        L_0x012d:
            if (r1 == 0) goto L_0x005f;
        L_0x012f:
            r2.write(r6);	 Catch:{ all -> 0x0141 }
            goto L_0x005f;
        L_0x0134:
            r1 = r18;
            r3 = 0;
            writeHierarchyLevel(r1, r2, r3, r7);	 Catch:{ all -> 0x0141 }
            r2.write(r6);	 Catch:{ all -> 0x0141 }
            closeWriter(r2);	 Catch:{ IOException -> 0x014a }
            goto L_0x014e;
        L_0x0141:
            r0 = move-exception;
            goto L_0x0146;
        L_0x0143:
            r0 = move-exception;
            r3 = 0;
            r2 = r3;
        L_0x0146:
            closeWriter(r2);	 Catch:{ IOException -> 0x014a }
            throw r0;	 Catch:{ IOException -> 0x014a }
        L_0x014a:
            r0 = move-exception;
            r0.printStackTrace();
        L_0x014e:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.drew.tools.ProcessAllImagesInFolderUtility.TextFileOutputHandler.onExtractionSuccess(java.io.File, com.drew.metadata.Metadata, java.lang.String, java.io.PrintStream):void");
        }

        private static void writeHierarchyLevel(@NotNull Metadata metadata, @NotNull PrintWriter printWriter, @Nullable Directory directory, int i) {
            for (Directory directory2 : metadata.getDirectories()) {
                if (directory == null) {
                    if (directory2.getParent() != null) {
                    }
                } else if (!directory.equals(directory2.getParent())) {
                }
                for (int i2 = 0; i2 < i * 4; i2++) {
                    printWriter.write(32);
                }
                printWriter.write("- ");
                printWriter.write(directory2.getName());
                printWriter.write("\n");
                writeHierarchyLevel(metadata, printWriter, directory2, i + 1);
            }
        }

        public void onExtractionError(@NotNull File file, @NotNull Throwable th, @NotNull PrintStream printStream) {
            String str = "\n";
            super.onExtractionError(file, th, printStream);
            Writer writer = null;
            try {
                writer = openWriter(file);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("EXCEPTION: ");
                stringBuilder.append(th.getMessage());
                stringBuilder.append(str);
                writer.write(stringBuilder.toString());
                writer.write(str);
                closeWriter(writer);
            } catch (IOException e) {
                printStream.printf("IO exception writing metadata file: %s%s", new Object[]{e.getMessage(), str});
            } catch (Throwable th2) {
                closeWriter(writer);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0092  */
        @com.drew.lang.annotations.NotNull
        private static java.io.PrintWriter openWriter(@com.drew.lang.annotations.NotNull java.io.File r7) throws java.io.IOException {
            /*
            r0 = new java.io.File;
            r1 = 1;
            r2 = new java.lang.Object[r1];
            r3 = r7.getParent();
            r4 = 0;
            r2[r4] = r3;
            r3 = "%s/metadata";
            r2 = java.lang.String.format(r3, r2);
            r0.<init>(r2);
            r2 = r0.exists();
            if (r2 != 0) goto L_0x001e;
        L_0x001b:
            r0.mkdir();
        L_0x001e:
            r0 = 2;
            r0 = new java.lang.Object[r0];
            r2 = r7.getParent();
            r0[r4] = r2;
            r2 = r7.getName();
            r0[r1] = r2;
            r2 = "%s/metadata/%s.txt";
            r0 = java.lang.String.format(r2, r0);
            r2 = new java.io.OutputStreamWriter;
            r3 = new java.io.FileOutputStream;
            r3.<init>(r0);
            r0 = "UTF-8";
            r2.<init>(r3, r0);
            r0 = new java.lang.StringBuilder;
            r0.<init>();
            r3 = "FILE: ";
            r0.append(r3);
            r3 = r7.getName();
            r0.append(r3);
            r3 = "\n";
            r0.append(r3);
            r0 = r0.toString();
            r2.write(r0);
            r0 = 0;
            r5 = new java.io.BufferedInputStream;	 Catch:{ all -> 0x008e }
            r6 = new java.io.FileInputStream;	 Catch:{ all -> 0x008e }
            r6.<init>(r7);	 Catch:{ all -> 0x008e }
            r5.<init>(r6);	 Catch:{ all -> 0x008e }
            r7 = com.drew.imaging.FileTypeDetector.detectFileType(r5);	 Catch:{ all -> 0x008c }
            r0 = "TYPE: %s\n";
            r1 = new java.lang.Object[r1];	 Catch:{ all -> 0x008c }
            r7 = r7.toString();	 Catch:{ all -> 0x008c }
            r7 = r7.toUpperCase();	 Catch:{ all -> 0x008c }
            r1[r4] = r7;	 Catch:{ all -> 0x008c }
            r7 = java.lang.String.format(r0, r1);	 Catch:{ all -> 0x008c }
            r2.write(r7);	 Catch:{ all -> 0x008c }
            r2.write(r3);	 Catch:{ all -> 0x008c }
            r5.close();
            r7 = new java.io.PrintWriter;
            r7.<init>(r2);
            return r7;
        L_0x008c:
            r7 = move-exception;
            goto L_0x0090;
        L_0x008e:
            r7 = move-exception;
            r5 = r0;
        L_0x0090:
            if (r5 == 0) goto L_0x0095;
        L_0x0092:
            r5.close();
        L_0x0095:
            throw r7;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.drew.tools.ProcessAllImagesInFolderUtility.TextFileOutputHandler.openWriter(java.io.File):java.io.PrintWriter");
        }

        private static void closeWriter(@Nullable Writer writer) throws IOException {
            if (writer != null) {
                writer.write("Generated using metadata-extractor\n");
                writer.write("https://drewnoakes.com/code/exif/\n");
                writer.flush();
                writer.close();
            }
        }
    }

    static class UnknownTagHandler extends FileHandlerBase {
        private HashMap<String, HashMap<Integer, Integer>> _occurrenceCountByTagByDirectory = new HashMap();

        UnknownTagHandler() {
        }

        public void onExtractionSuccess(@NotNull File file, @NotNull Metadata metadata, @NotNull String str, @NotNull PrintStream printStream) {
            super.onExtractionSuccess(file, metadata, str, printStream);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if (!tag.hasTagName()) {
                        HashMap hashMap = (HashMap) this._occurrenceCountByTagByDirectory.get(directory.getName());
                        if (hashMap == null) {
                            hashMap = new HashMap();
                            this._occurrenceCountByTagByDirectory.put(directory.getName(), hashMap);
                        }
                        Integer num = (Integer) hashMap.get(Integer.valueOf(tag.getTagType()));
                        if (num == null) {
                            Integer valueOf = Integer.valueOf(0);
                            hashMap.put(Integer.valueOf(tag.getTagType()), Integer.valueOf(0));
                            num = valueOf;
                        }
                        hashMap.put(Integer.valueOf(tag.getTagType()), Integer.valueOf(num.intValue() + 1));
                    }
                }
            }
        }

        public void onScanCompleted(@NotNull PrintStream printStream) {
            super.onScanCompleted(printStream);
            for (Entry entry : this._occurrenceCountByTagByDirectory.entrySet()) {
                String str = (String) entry.getKey();
                List<Entry> arrayList = new ArrayList(((HashMap) entry.getValue()).entrySet());
                Collections.sort(arrayList, new Comparator<Entry<Integer, Integer>>() {
                    public int compare(Entry<Integer, Integer> entry, Entry<Integer, Integer> entry2) {
                        return ((Integer) entry2.getValue()).compareTo((Integer) entry.getValue());
                    }
                });
                for (Entry entry2 : arrayList) {
                    Integer num = (Integer) entry2.getKey();
                    Integer num2 = (Integer) entry2.getValue();
                    printStream.format("%s, 0x%04X, %d\n", new Object[]{str, num, num2});
                }
            }
        }
    }

    public static void main(String[] strArr) throws IOException, JpegProcessingException {
        List<String> arrayList = new ArrayList();
        FileHandler fileHandler = null;
        PrintStream printStream = System.out;
        int i = 0;
        while (i < strArr.length) {
            String str = strArr[i];
            if (str.equalsIgnoreCase("--text")) {
                fileHandler = new TextFileOutputHandler();
            } else if (str.equalsIgnoreCase("--markdown")) {
                fileHandler = new MarkdownTableOutputHandler();
            } else if (str.equalsIgnoreCase("--unknown")) {
                fileHandler = new UnknownTagHandler();
            } else if (str.equalsIgnoreCase("--log-file")) {
                if (i == strArr.length - 1) {
                    printUsage();
                    System.exit(1);
                }
                i++;
                printStream = new PrintStream(new FileOutputStream(strArr[i], false), true);
            } else {
                arrayList.add(str);
            }
            i++;
        }
        if (arrayList.isEmpty()) {
            System.err.println("Expects one or more directories as arguments.");
            printUsage();
            System.exit(1);
        }
        if (fileHandler == null) {
            fileHandler = new BasicFileHandler();
        }
        long nanoTime = System.nanoTime();
        for (String file : arrayList) {
            processDirectory(new File(file), fileHandler, "", printStream);
        }
        fileHandler.onScanCompleted(printStream);
        System.out.println(String.format("Completed in %d ms", new Object[]{Long.valueOf((System.nanoTime() - nanoTime) / 1000000)}));
        if (printStream != System.out) {
            printStream.close();
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println();
        System.out.println("  java com.drew.tools.ProcessAllImagesInFolderUtility [--text|--markdown|--unknown] [--log-file <file-name>]");
    }

    private static void processDirectory(@NotNull File file, @NotNull FileHandler fileHandler, @NotNull String str, PrintStream printStream) {
        fileHandler.onStartingDirectory(file);
        String[] list = file.list();
        if (list != null) {
            Arrays.sort(list);
            for (String str2 : list) {
                String str22;
                File file2 = new File(file, str22);
                if (file2.isDirectory()) {
                    if (str.length() != 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(str);
                        stringBuilder.append("/");
                        stringBuilder.append(str22);
                        str22 = stringBuilder.toString();
                    }
                    processDirectory(file2, fileHandler, str22, printStream);
                } else if (fileHandler.shouldProcess(file2)) {
                    fileHandler.onBeforeExtraction(file2, printStream, str);
                    try {
                        fileHandler.onExtractionSuccess(file2, ImageMetadataReader.readMetadata(file2), str, printStream);
                    } catch (Throwable th) {
                        fileHandler.onExtractionError(file2, th, printStream);
                    }
                }
            }
        }
    }
}
