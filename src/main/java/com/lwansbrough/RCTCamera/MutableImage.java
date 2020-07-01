package com.lwansbrough.RCTCamera;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.brentvatne.react.ReactVideoView;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.facebook.react.bridge.ReadableMap;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MutableImage {
    private static final String TAG = "RNCamera";
    private Bitmap currentRepresentation;
    private boolean hasBeenReoriented = false;
    private final byte[] originalImageData;
    private Metadata originalImageMetaData;

    private static class GPS {
        private static String latitudeRef(double d) {
            return d < 0.0d ? ExifInterface.LATITUDE_SOUTH : "N";
        }

        private static String longitudeRef(double d) {
            return d < 0.0d ? ExifInterface.LONGITUDE_WEST : ExifInterface.LONGITUDE_EAST;
        }

        private GPS() {
        }

        public static void writeExifData(double d, double d2, ExifInterface exifInterface) throws IOException {
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, toDegreeMinuteSecods(d));
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, latitudeRef(d));
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, toDegreeMinuteSecods(d2));
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, longitudeRef(d2));
        }

        private static String toDegreeMinuteSecods(double d) {
            d = Math.abs(d);
            int i = (int) d;
            d = (d * 60.0d) - (((double) i) * 60.0d);
            int i2 = (int) d;
            int i3 = (int) (((d * 60.0d) - (((double) i2) * 60.0d)) * 1000.0d);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(i);
            String str = "/1,";
            stringBuffer.append(str);
            stringBuffer.append(i2);
            stringBuffer.append(str);
            stringBuffer.append(i3);
            stringBuffer.append("/1000,");
            return stringBuffer.toString();
        }
    }

    public static class ImageMutationFailedException extends Exception {
        public ImageMutationFailedException(String str, Throwable th) {
            super(str, th);
        }

        public ImageMutationFailedException(String str) {
            super(str);
        }
    }

    public MutableImage(byte[] bArr) {
        this.originalImageData = bArr;
        this.currentRepresentation = toBitmap(bArr);
    }

    public int getWidth() {
        return this.currentRepresentation.getWidth();
    }

    public int getHeight() {
        return this.currentRepresentation.getHeight();
    }

    public void mirrorImage() throws ImageMutationFailedException {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        Bitmap createBitmap = Bitmap.createBitmap(this.currentRepresentation, 0, 0, getWidth(), getHeight(), matrix, false);
        if (createBitmap != null) {
            this.currentRepresentation = createBitmap;
            return;
        }
        throw new ImageMutationFailedException("failed to mirror");
    }

    public void fixOrientation() throws ImageMutationFailedException {
        Throwable e;
        try {
            ExifIFD0Directory exifIFD0Directory = (ExifIFD0Directory) originalImageMetaData().getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (exifIFD0Directory != null && exifIFD0Directory.containsTag(274)) {
                int i = exifIFD0Directory.getInt(274);
                if (i != 1) {
                    rotate(i);
                    exifIFD0Directory.setInt(274, 1);
                }
            }
        } catch (ImageProcessingException e2) {
            e = e2;
            throw new ImageMutationFailedException("failed to fix orientation", e);
        } catch (IOException e3) {
            e = e3;
            throw new ImageMutationFailedException("failed to fix orientation", e);
        } catch (MetadataException e4) {
            e = e4;
            throw new ImageMutationFailedException("failed to fix orientation", e);
        }
    }

    public void cropToPreview(double d) throws IllegalArgumentException {
        int i;
        int i2;
        int width = getWidth();
        int height = getHeight();
        double d2 = ((double) height) * d;
        double d3 = (double) width;
        if (d2 > d3) {
            i = (int) (d3 / d);
            i2 = width;
        } else {
            i2 = (int) d2;
            i = height;
        }
        this.currentRepresentation = Bitmap.createBitmap(this.currentRepresentation, (width - i2) / 2, (height - i) / 2, i2, i);
    }

    private void rotate(int i) throws ImageMutationFailedException {
        Matrix matrix = new Matrix();
        switch (i) {
            case 1:
                return;
            case 2:
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 3:
                matrix.postRotate(180.0f);
                break;
            case 4:
                matrix.postRotate(180.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 5:
                matrix.postRotate(90.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 6:
                matrix.postRotate(90.0f);
                break;
            case 7:
                matrix.postRotate(270.0f);
                matrix.postScale(-1.0f, 1.0f);
                break;
            case 8:
                matrix.postRotate(270.0f);
                break;
        }
        Bitmap createBitmap = Bitmap.createBitmap(this.currentRepresentation, 0, 0, getWidth(), getHeight(), matrix, false);
        if (createBitmap != null) {
            this.currentRepresentation = createBitmap;
            this.hasBeenReoriented = true;
            return;
        }
        throw new ImageMutationFailedException("failed to rotate");
    }

    private static Bitmap toBitmap(byte[] bArr) {
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            Bitmap decodeStream = BitmapFactory.decodeStream(byteArrayInputStream);
            byteArrayInputStream.close();
            return decodeStream;
        } catch (Throwable e) {
            throw new IllegalStateException("Will not happen", e);
        }
    }

    public String toBase64(int i) {
        return Base64.encodeToString(toJpeg(this.currentRepresentation, i), 2);
    }

    public void writeDataToFile(File file, ReadableMap readableMap, int i) throws IOException {
        Throwable e;
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(toJpeg(this.currentRepresentation, i));
        fileOutputStream.close();
        try {
            ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
            for (Directory directory : originalImageMetaData().getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    exifInterface.setAttribute(tag.getTagName(), directory.getObject(tag.getTagType()).toString());
                }
            }
            ExifSubIFDDirectory exifSubIFDDirectory = (ExifSubIFDDirectory) originalImageMetaData().getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            for (Tag tag2 : exifSubIFDDirectory.getTags()) {
                int tagType = tag2.getTagType();
                String replaceAll = tag2.getTagName().replaceAll(" ", "");
                Object object = exifSubIFDDirectory.getObject(tagType);
                if (replaceAll.equals(ExifInterface.TAG_EXPOSURE_TIME)) {
                    exifInterface.setAttribute(replaceAll, convertExposureTimeToDoubleFormat(object.toString()));
                } else {
                    exifInterface.setAttribute(replaceAll, object.toString());
                }
            }
            writeLocationExifData(readableMap, exifInterface);
            if (this.hasBeenReoriented) {
                rewriteOrientation(exifInterface);
            }
            exifInterface.saveAttributes();
        } catch (ImageProcessingException e2) {
            e = e2;
            Log.e(TAG, "failed to save exif data", e);
        } catch (IOException e3) {
            e = e3;
            Log.e(TAG, "failed to save exif data", e);
        }
    }

    private String convertExposureTimeToDoubleFormat(String str) {
        Object obj = "/";
        if (str.contains(obj)) {
            return Double.toString(1.0d / Double.parseDouble(str.split(obj)[1]));
        }
        return "";
    }

    private void rewriteOrientation(ExifInterface exifInterface) {
        exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(1));
    }

    private void writeLocationExifData(ReadableMap readableMap, ExifInterface exifInterface) {
        String str = ReactVideoView.EVENT_PROP_METADATA;
        if (readableMap.hasKey(str)) {
            readableMap = readableMap.getMap(str);
            str = Param.LOCATION;
            if (readableMap.hasKey(str)) {
                readableMap = readableMap.getMap(str);
                str = "coords";
                if (readableMap.hasKey(str)) {
                    try {
                        readableMap = readableMap.getMap(str);
                        GPS.writeExifData(readableMap.getDouble("latitude"), readableMap.getDouble("longitude"), exifInterface);
                    } catch (Throwable e) {
                        Log.e(TAG, "Couldn't write location data", e);
                    }
                }
            }
        }
    }

    private Metadata originalImageMetaData() throws ImageProcessingException, IOException {
        if (this.originalImageMetaData == null) {
            this.originalImageMetaData = ImageMetadataReader.readMetadata(new BufferedInputStream(new ByteArrayInputStream(this.originalImageData)), (long) this.originalImageData.length);
        }
        return this.originalImageMetaData;
    }

    private static byte[] toJpeg(Bitmap bitmap, int i) throws OutOfMemoryError {
        String str = "problem compressing jpeg";
        String str2 = TAG;
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, i, byteArrayOutputStream);
        try {
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            return toByteArray;
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable e) {
                Log.e(str2, str, e);
            }
        }
    }
}
