package com.drew.tools;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.jpeg.JpegSegmentData;
import com.drew.imaging.jpeg.JpegSegmentReader;
import com.drew.imaging.jpeg.JpegSegmentType;
import com.drew.lang.Iterables;
import com.drew.lang.annotations.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;

public class ExtractJpegSegmentTool {
    public static void main(String[] strArr) throws IOException, JpegProcessingException {
        if (strArr.length < 1) {
            printUsage();
            System.exit(1);
        }
        String str = strArr[0];
        if (!new File(str).exists()) {
            System.err.println("File does not exist");
            printUsage();
            System.exit(1);
        }
        Iterable hashSet = new HashSet();
        for (int i = 1; i < strArr.length; i++) {
            JpegSegmentType valueOf = JpegSegmentType.valueOf(strArr[i].toUpperCase());
            if (!valueOf.canContainMetadata) {
                System.err.printf("WARNING: Segment type %s cannot contain metadata so it may not be necessary to extract it%n", new Object[]{valueOf});
            }
            hashSet.add(valueOf);
        }
        if (hashSet.size() == 0) {
            hashSet.addAll(JpegSegmentType.canContainMetadataTypes);
        }
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Reading: ");
        stringBuilder.append(str);
        printStream.println(stringBuilder.toString());
        saveSegmentFiles(str, JpegSegmentReader.readSegments(new File(str), hashSet));
    }

    public static void saveSegmentFiles(@NotNull String str, @NotNull JpegSegmentData jpegSegmentData) throws IOException {
        for (JpegSegmentType segments : jpegSegmentData.getSegmentTypes()) {
            List toList = Iterables.toList(jpegSegmentData.getSegments(segments));
            if (toList.size() != 0) {
                String str2 = "Writing: ";
                if (toList.size() > 1) {
                    for (int i = 0; i < toList.size(); i++) {
                        String format = String.format("%s.%s.%d", new Object[]{str, segments.toString().toLowerCase(), Integer.valueOf(i)});
                        PrintStream printStream = System.out;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(str2);
                        stringBuilder.append(format);
                        printStream.println(stringBuilder.toString());
                        FileUtil.saveBytes(new File(format), (byte[]) toList.get(i));
                    }
                } else {
                    String format2 = String.format("%s.%s", new Object[]{str, segments.toString().toLowerCase()});
                    PrintStream printStream2 = System.out;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(str2);
                    stringBuilder2.append(format2);
                    printStream2.println(stringBuilder2.toString());
                    FileUtil.saveBytes(new File(format2), (byte[]) toList.get(0));
                }
            }
        }
    }

    private static void printUsage() {
        System.out.println("USAGE:\n");
        System.out.println("\tjava com.drew.tools.ExtractJpegSegmentTool <filename> [<segment> ...]\n");
        System.out.print("Where <segment> is zero or more of:");
        for (JpegSegmentType jpegSegmentType : (JpegSegmentType[]) JpegSegmentType.class.getEnumConstants()) {
            if (jpegSegmentType.canContainMetadata) {
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" ");
                stringBuilder.append(jpegSegmentType.toString());
                printStream.print(stringBuilder.toString());
            }
        }
        System.out.println();
    }
}
