package androidx.exifinterface.media;

import android.content.res.AssetManager.AssetInputStream;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifReader;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.makernotes.OlympusImageProcessingMakernoteDirectory;
import com.drew.metadata.exif.makernotes.OlympusMakernoteDirectory;
import com.facebook.cache.disk.DefaultDiskStorage.FileType;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class ExifInterface {
    public static final short ALTITUDE_ABOVE_SEA_LEVEL = (short) 0;
    public static final short ALTITUDE_BELOW_SEA_LEVEL = (short) 1;
    static final Charset ASCII = Charset.forName("US-ASCII");
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_1 = new int[]{4};
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_2 = new int[]{8};
    public static final int[] BITS_PER_SAMPLE_RGB = new int[]{8, 8, 8};
    static final short BYTE_ALIGN_II = (short) 18761;
    static final short BYTE_ALIGN_MM = (short) 19789;
    public static final int COLOR_SPACE_S_RGB = 1;
    public static final int COLOR_SPACE_UNCALIBRATED = 65535;
    public static final short CONTRAST_HARD = (short) 2;
    public static final short CONTRAST_NORMAL = (short) 0;
    public static final short CONTRAST_SOFT = (short) 1;
    public static final int DATA_DEFLATE_ZIP = 8;
    public static final int DATA_HUFFMAN_COMPRESSED = 2;
    public static final int DATA_JPEG = 6;
    public static final int DATA_JPEG_COMPRESSED = 7;
    public static final int DATA_LOSSY_JPEG = 34892;
    public static final int DATA_PACK_BITS_COMPRESSED = 32773;
    public static final int DATA_UNCOMPRESSED = 1;
    private static final boolean DEBUG = false;
    static final byte[] EXIF_ASCII_PREFIX = new byte[]{(byte) 65, (byte) 83, (byte) 67, (byte) 73, (byte) 73, (byte) 0, (byte) 0, (byte) 0};
    private static final ExifTag[] EXIF_POINTER_TAGS = new ExifTag[]{new ExifTag(TAG_SUB_IFD_POINTER, ExifDirectoryBase.TAG_SUB_IFD_OFFSET, 4), new ExifTag(TAG_EXIF_IFD_POINTER, ExifIFD0Directory.TAG_EXIF_SUB_IFD_OFFSET, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, ExifIFD0Directory.TAG_GPS_INFO_OFFSET, 4), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, ExifSubIFDDirectory.TAG_INTEROP_OFFSET, 4), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, 1), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, OlympusMakernoteDirectory.TAG_IMAGE_PROCESSING, 1)};
    static final ExifTag[][] EXIF_TAGS;
    public static final short EXPOSURE_MODE_AUTO = (short) 0;
    public static final short EXPOSURE_MODE_AUTO_BRACKET = (short) 2;
    public static final short EXPOSURE_MODE_MANUAL = (short) 1;
    public static final short EXPOSURE_PROGRAM_ACTION = (short) 6;
    public static final short EXPOSURE_PROGRAM_APERTURE_PRIORITY = (short) 3;
    public static final short EXPOSURE_PROGRAM_CREATIVE = (short) 5;
    public static final short EXPOSURE_PROGRAM_LANDSCAPE_MODE = (short) 8;
    public static final short EXPOSURE_PROGRAM_MANUAL = (short) 1;
    public static final short EXPOSURE_PROGRAM_NORMAL = (short) 2;
    public static final short EXPOSURE_PROGRAM_NOT_DEFINED = (short) 0;
    public static final short EXPOSURE_PROGRAM_PORTRAIT_MODE = (short) 7;
    public static final short EXPOSURE_PROGRAM_SHUTTER_PRIORITY = (short) 4;
    public static final short FILE_SOURCE_DSC = (short) 3;
    public static final short FILE_SOURCE_OTHER = (short) 0;
    public static final short FILE_SOURCE_REFLEX_SCANNER = (short) 2;
    public static final short FILE_SOURCE_TRANSPARENT_SCANNER = (short) 1;
    public static final short FLAG_FLASH_FIRED = (short) 1;
    public static final short FLAG_FLASH_MODE_AUTO = (short) 24;
    public static final short FLAG_FLASH_MODE_COMPULSORY_FIRING = (short) 8;
    public static final short FLAG_FLASH_MODE_COMPULSORY_SUPPRESSION = (short) 16;
    public static final short FLAG_FLASH_NO_FLASH_FUNCTION = (short) 32;
    public static final short FLAG_FLASH_RED_EYE_SUPPORTED = (short) 64;
    public static final short FLAG_FLASH_RETURN_LIGHT_DETECTED = (short) 6;
    public static final short FLAG_FLASH_RETURN_LIGHT_NOT_DETECTED = (short) 4;
    private static final List<Integer> FLIPPED_ROTATION_ORDER;
    public static final short FORMAT_CHUNKY = (short) 1;
    public static final short FORMAT_PLANAR = (short) 2;
    public static final short GAIN_CONTROL_HIGH_GAIN_DOWN = (short) 4;
    public static final short GAIN_CONTROL_HIGH_GAIN_UP = (short) 2;
    public static final short GAIN_CONTROL_LOW_GAIN_DOWN = (short) 3;
    public static final short GAIN_CONTROL_LOW_GAIN_UP = (short) 1;
    public static final short GAIN_CONTROL_NONE = (short) 0;
    public static final String GPS_DIRECTION_MAGNETIC = "M";
    public static final String GPS_DIRECTION_TRUE = "T";
    public static final String GPS_DISTANCE_KILOMETERS = "K";
    public static final String GPS_DISTANCE_MILES = "M";
    public static final String GPS_DISTANCE_NAUTICAL_MILES = "N";
    public static final String GPS_MEASUREMENT_2D = "2";
    public static final String GPS_MEASUREMENT_3D = "3";
    public static final short GPS_MEASUREMENT_DIFFERENTIAL_CORRECTED = (short) 1;
    public static final String GPS_MEASUREMENT_INTERRUPTED = "V";
    public static final String GPS_MEASUREMENT_IN_PROGRESS = "A";
    public static final short GPS_MEASUREMENT_NO_DIFFERENTIAL = (short) 0;
    public static final String GPS_SPEED_KILOMETERS_PER_HOUR = "K";
    public static final String GPS_SPEED_KNOTS = "N";
    public static final String GPS_SPEED_MILES_PER_HOUR = "M";
    static final byte[] IDENTIFIER_EXIF_APP1 = ExifReader.JPEG_SEGMENT_PREAMBLE.getBytes(ASCII);
    private static final ExifTag[] IFD_EXIF_TAGS = new ExifTag[]{new ExifTag(TAG_EXPOSURE_TIME, ExifDirectoryBase.TAG_EXPOSURE_TIME, 5), new ExifTag(TAG_F_NUMBER, ExifDirectoryBase.TAG_FNUMBER, 5), new ExifTag(TAG_EXPOSURE_PROGRAM, ExifDirectoryBase.TAG_EXPOSURE_PROGRAM, 3), new ExifTag(TAG_SPECTRAL_SENSITIVITY, ExifDirectoryBase.TAG_SPECTRAL_SENSITIVITY, 2), new ExifTag(TAG_PHOTOGRAPHIC_SENSITIVITY, ExifDirectoryBase.TAG_ISO_EQUIVALENT, 3), new ExifTag(TAG_OECF, ExifDirectoryBase.TAG_OPTO_ELECTRIC_CONVERSION_FUNCTION, 7), new ExifTag(TAG_EXIF_VERSION, ExifDirectoryBase.TAG_EXIF_VERSION, 2), new ExifTag(TAG_DATETIME_ORIGINAL, ExifDirectoryBase.TAG_DATETIME_ORIGINAL, 2), new ExifTag(TAG_DATETIME_DIGITIZED, ExifDirectoryBase.TAG_DATETIME_DIGITIZED, 2), new ExifTag(TAG_COMPONENTS_CONFIGURATION, ExifDirectoryBase.TAG_COMPONENTS_CONFIGURATION, 7), new ExifTag(TAG_COMPRESSED_BITS_PER_PIXEL, ExifDirectoryBase.TAG_COMPRESSED_AVERAGE_BITS_PER_PIXEL, 5), new ExifTag(TAG_SHUTTER_SPEED_VALUE, ExifDirectoryBase.TAG_SHUTTER_SPEED, 10), new ExifTag(TAG_APERTURE_VALUE, ExifDirectoryBase.TAG_APERTURE, 5), new ExifTag(TAG_BRIGHTNESS_VALUE, ExifDirectoryBase.TAG_BRIGHTNESS_VALUE, 10), new ExifTag(TAG_EXPOSURE_BIAS_VALUE, ExifDirectoryBase.TAG_EXPOSURE_BIAS, 10), new ExifTag(TAG_MAX_APERTURE_VALUE, ExifDirectoryBase.TAG_MAX_APERTURE, 5), new ExifTag(TAG_SUBJECT_DISTANCE, ExifDirectoryBase.TAG_SUBJECT_DISTANCE, 5), new ExifTag(TAG_METERING_MODE, ExifDirectoryBase.TAG_METERING_MODE, 3), new ExifTag(TAG_LIGHT_SOURCE, 37384, 3), new ExifTag(TAG_FLASH, ExifDirectoryBase.TAG_FLASH, 3), new ExifTag(TAG_FOCAL_LENGTH, ExifDirectoryBase.TAG_FOCAL_LENGTH, 5), new ExifTag(TAG_SUBJECT_AREA, ExifDirectoryBase.TAG_SUBJECT_LOCATION_TIFF_EP, 3), new ExifTag(TAG_MAKER_NOTE, ExifDirectoryBase.TAG_MAKERNOTE, 7), new ExifTag(TAG_USER_COMMENT, ExifDirectoryBase.TAG_USER_COMMENT, 7), new ExifTag(TAG_SUBSEC_TIME, ExifDirectoryBase.TAG_SUBSECOND_TIME, 2), new ExifTag(TAG_SUBSEC_TIME_ORIGINAL, ExifDirectoryBase.TAG_SUBSECOND_TIME_ORIGINAL, 2), new ExifTag(TAG_SUBSEC_TIME_DIGITIZED, ExifDirectoryBase.TAG_SUBSECOND_TIME_DIGITIZED, 2), new ExifTag(TAG_FLASHPIX_VERSION, ExifDirectoryBase.TAG_FLASHPIX_VERSION, 7), new ExifTag(TAG_COLOR_SPACE, 40961, 3), new ExifTag(TAG_PIXEL_X_DIMENSION, ExifDirectoryBase.TAG_EXIF_IMAGE_WIDTH, 3, 4), new ExifTag(TAG_PIXEL_Y_DIMENSION, ExifDirectoryBase.TAG_EXIF_IMAGE_HEIGHT, 3, 4), new ExifTag(TAG_RELATED_SOUND_FILE, ExifDirectoryBase.TAG_RELATED_SOUND_FILE, 2), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, ExifSubIFDDirectory.TAG_INTEROP_OFFSET, 4), new ExifTag(TAG_FLASH_ENERGY, ExifDirectoryBase.TAG_FLASH_ENERGY, 5), new ExifTag(TAG_SPATIAL_FREQUENCY_RESPONSE, ExifDirectoryBase.TAG_SPATIAL_FREQ_RESPONSE, 7), new ExifTag(TAG_FOCAL_PLANE_X_RESOLUTION, ExifDirectoryBase.TAG_FOCAL_PLANE_X_RESOLUTION, 5), new ExifTag(TAG_FOCAL_PLANE_Y_RESOLUTION, ExifDirectoryBase.TAG_FOCAL_PLANE_Y_RESOLUTION, 5), new ExifTag(TAG_FOCAL_PLANE_RESOLUTION_UNIT, ExifDirectoryBase.TAG_FOCAL_PLANE_RESOLUTION_UNIT, 3), new ExifTag(TAG_SUBJECT_LOCATION, ExifDirectoryBase.TAG_SUBJECT_LOCATION, 3), new ExifTag(TAG_EXPOSURE_INDEX, ExifDirectoryBase.TAG_EXPOSURE_INDEX, 5), new ExifTag(TAG_SENSING_METHOD, ExifDirectoryBase.TAG_SENSING_METHOD, 3), new ExifTag(TAG_FILE_SOURCE, ExifDirectoryBase.TAG_FILE_SOURCE, 7), new ExifTag(TAG_SCENE_TYPE, ExifDirectoryBase.TAG_SCENE_TYPE, 7), new ExifTag(TAG_CFA_PATTERN, ExifDirectoryBase.TAG_CFA_PATTERN, 7), new ExifTag(TAG_CUSTOM_RENDERED, ExifDirectoryBase.TAG_CUSTOM_RENDERED, 3), new ExifTag(TAG_EXPOSURE_MODE, ExifDirectoryBase.TAG_EXPOSURE_MODE, 3), new ExifTag(TAG_WHITE_BALANCE, ExifDirectoryBase.TAG_WHITE_BALANCE_MODE, 3), new ExifTag(TAG_DIGITAL_ZOOM_RATIO, ExifDirectoryBase.TAG_DIGITAL_ZOOM_RATIO, 5), new ExifTag(TAG_FOCAL_LENGTH_IN_35MM_FILM, ExifDirectoryBase.TAG_35MM_FILM_EQUIV_FOCAL_LENGTH, 3), new ExifTag(TAG_SCENE_CAPTURE_TYPE, ExifDirectoryBase.TAG_SCENE_CAPTURE_TYPE, 3), new ExifTag(TAG_GAIN_CONTROL, ExifDirectoryBase.TAG_GAIN_CONTROL, 3), new ExifTag(TAG_CONTRAST, ExifDirectoryBase.TAG_CONTRAST, 3), new ExifTag(TAG_SATURATION, ExifDirectoryBase.TAG_SATURATION, 3), new ExifTag(TAG_SHARPNESS, ExifDirectoryBase.TAG_SHARPNESS, 3), new ExifTag(TAG_DEVICE_SETTING_DESCRIPTION, ExifDirectoryBase.TAG_DEVICE_SETTING_DESCRIPTION, 7), new ExifTag(TAG_SUBJECT_DISTANCE_RANGE, ExifDirectoryBase.TAG_SUBJECT_DISTANCE_RANGE, 3), new ExifTag(TAG_IMAGE_UNIQUE_ID, ExifDirectoryBase.TAG_IMAGE_UNIQUE_ID, 2), new ExifTag(TAG_DNG_VERSION, 50706, 1), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
    private static final int IFD_FORMAT_BYTE = 1;
    static final int[] IFD_FORMAT_BYTES_PER_FORMAT = new int[]{0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    private static final int IFD_FORMAT_DOUBLE = 12;
    private static final int IFD_FORMAT_IFD = 13;
    static final String[] IFD_FORMAT_NAMES = new String[]{"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE"};
    private static final int IFD_FORMAT_SBYTE = 6;
    private static final int IFD_FORMAT_SINGLE = 11;
    private static final int IFD_FORMAT_SLONG = 9;
    private static final int IFD_FORMAT_SRATIONAL = 10;
    private static final int IFD_FORMAT_SSHORT = 8;
    private static final int IFD_FORMAT_STRING = 2;
    private static final int IFD_FORMAT_ULONG = 4;
    private static final int IFD_FORMAT_UNDEFINED = 7;
    private static final int IFD_FORMAT_URATIONAL = 5;
    private static final int IFD_FORMAT_USHORT = 3;
    private static final ExifTag[] IFD_GPS_TAGS = new ExifTag[]{new ExifTag(TAG_GPS_VERSION_ID, 0, 1), new ExifTag(TAG_GPS_LATITUDE_REF, 1, 2), new ExifTag(TAG_GPS_LATITUDE, 2, 5), new ExifTag(TAG_GPS_LONGITUDE_REF, 3, 2), new ExifTag(TAG_GPS_LONGITUDE, 4, 5), new ExifTag(TAG_GPS_ALTITUDE_REF, 5, 1), new ExifTag(TAG_GPS_ALTITUDE, 6, 5), new ExifTag(TAG_GPS_TIMESTAMP, 7, 5), new ExifTag(TAG_GPS_SATELLITES, 8, 2), new ExifTag(TAG_GPS_STATUS, 9, 2), new ExifTag(TAG_GPS_MEASURE_MODE, 10, 2), new ExifTag(TAG_GPS_DOP, 11, 5), new ExifTag(TAG_GPS_SPEED_REF, 12, 2), new ExifTag(TAG_GPS_SPEED, 13, 5), new ExifTag(TAG_GPS_TRACK_REF, 14, 2), new ExifTag(TAG_GPS_TRACK, 15, 5), new ExifTag(TAG_GPS_IMG_DIRECTION_REF, 16, 2), new ExifTag(TAG_GPS_IMG_DIRECTION, 17, 5), new ExifTag(TAG_GPS_MAP_DATUM, 18, 2), new ExifTag(TAG_GPS_DEST_LATITUDE_REF, 19, 2), new ExifTag(TAG_GPS_DEST_LATITUDE, 20, 5), new ExifTag(TAG_GPS_DEST_LONGITUDE_REF, 21, 2), new ExifTag(TAG_GPS_DEST_LONGITUDE, 22, 5), new ExifTag(TAG_GPS_DEST_BEARING_REF, 23, 2), new ExifTag(TAG_GPS_DEST_BEARING, 24, 5), new ExifTag(TAG_GPS_DEST_DISTANCE_REF, 25, 2), new ExifTag(TAG_GPS_DEST_DISTANCE, 26, 5), new ExifTag(TAG_GPS_PROCESSING_METHOD, 27, 7), new ExifTag(TAG_GPS_AREA_INFORMATION, 28, 7), new ExifTag(TAG_GPS_DATESTAMP, 29, 2), new ExifTag(TAG_GPS_DIFFERENTIAL, 30, 3)};
    private static final ExifTag[] IFD_INTEROPERABILITY_TAGS = new ExifTag[]{new ExifTag(TAG_INTEROPERABILITY_INDEX, 1, 2)};
    private static final int IFD_OFFSET = 8;
    private static final ExifTag[] IFD_THUMBNAIL_TAGS = new ExifTag[]{new ExifTag(TAG_NEW_SUBFILE_TYPE, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 4), new ExifTag(TAG_SUBFILE_TYPE, 255, 4), new ExifTag(TAG_THUMBNAIL_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_THUMBNAIL_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, 258, 3), new ExifTag(TAG_COMPRESSION, 259, 3), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new ExifTag(TAG_IMAGE_DESCRIPTION, 270, 2), new ExifTag(TAG_MAKE, 271, 2), new ExifTag(TAG_MODEL, 272, 2), new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4), new ExifTag(TAG_ORIENTATION, 274, 3), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, 3), new ExifTag(TAG_ROWS_PER_STRIP, 278, 3, 4), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new ExifTag(TAG_X_RESOLUTION, 282, 5), new ExifTag(TAG_Y_RESOLUTION, 283, 5), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, 3), new ExifTag(TAG_RESOLUTION_UNIT, 296, 3), new ExifTag(TAG_TRANSFER_FUNCTION, ExifDirectoryBase.TAG_TRANSFER_FUNCTION, 3), new ExifTag(TAG_SOFTWARE, 305, 2), new ExifTag(TAG_DATETIME, 306, 2), new ExifTag(TAG_ARTIST, ExifDirectoryBase.TAG_ARTIST, 2), new ExifTag(TAG_WHITE_POINT, ExifDirectoryBase.TAG_WHITE_POINT, 5), new ExifTag(TAG_PRIMARY_CHROMATICITIES, ExifDirectoryBase.TAG_PRIMARY_CHROMATICITIES, 5), new ExifTag(TAG_SUB_IFD_POINTER, ExifDirectoryBase.TAG_SUB_IFD_OFFSET, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag(TAG_COPYRIGHT, ExifDirectoryBase.TAG_COPYRIGHT, 2), new ExifTag(TAG_EXIF_IFD_POINTER, ExifIFD0Directory.TAG_EXIF_SUB_IFD_OFFSET, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, ExifIFD0Directory.TAG_GPS_INFO_OFFSET, 4), new ExifTag(TAG_DNG_VERSION, 50706, 1), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
    private static final ExifTag[] IFD_TIFF_TAGS = new ExifTag[]{new ExifTag(TAG_NEW_SUBFILE_TYPE, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 4), new ExifTag(TAG_SUBFILE_TYPE, 255, 4), new ExifTag(TAG_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, 258, 3), new ExifTag(TAG_COMPRESSION, 259, 3), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new ExifTag(TAG_IMAGE_DESCRIPTION, 270, 2), new ExifTag(TAG_MAKE, 271, 2), new ExifTag(TAG_MODEL, 272, 2), new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4), new ExifTag(TAG_ORIENTATION, 274, 3), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, 3), new ExifTag(TAG_ROWS_PER_STRIP, 278, 3, 4), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new ExifTag(TAG_X_RESOLUTION, 282, 5), new ExifTag(TAG_Y_RESOLUTION, 283, 5), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, 3), new ExifTag(TAG_RESOLUTION_UNIT, 296, 3), new ExifTag(TAG_TRANSFER_FUNCTION, ExifDirectoryBase.TAG_TRANSFER_FUNCTION, 3), new ExifTag(TAG_SOFTWARE, 305, 2), new ExifTag(TAG_DATETIME, 306, 2), new ExifTag(TAG_ARTIST, ExifDirectoryBase.TAG_ARTIST, 2), new ExifTag(TAG_WHITE_POINT, ExifDirectoryBase.TAG_WHITE_POINT, 5), new ExifTag(TAG_PRIMARY_CHROMATICITIES, ExifDirectoryBase.TAG_PRIMARY_CHROMATICITIES, 5), new ExifTag(TAG_SUB_IFD_POINTER, ExifDirectoryBase.TAG_SUB_IFD_OFFSET, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag(TAG_COPYRIGHT, ExifDirectoryBase.TAG_COPYRIGHT, 2), new ExifTag(TAG_EXIF_IFD_POINTER, ExifIFD0Directory.TAG_EXIF_SUB_IFD_OFFSET, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, ExifIFD0Directory.TAG_GPS_INFO_OFFSET, 4), new ExifTag(TAG_RW2_SENSOR_TOP_BORDER, 4, 4), new ExifTag(TAG_RW2_SENSOR_LEFT_BORDER, 5, 4), new ExifTag(TAG_RW2_SENSOR_BOTTOM_BORDER, 6, 4), new ExifTag(TAG_RW2_SENSOR_RIGHT_BORDER, 7, 4), new ExifTag(TAG_RW2_ISO, 23, 3), new ExifTag(TAG_RW2_JPG_FROM_RAW, 46, 7)};
    private static final int IFD_TYPE_EXIF = 1;
    private static final int IFD_TYPE_GPS = 2;
    private static final int IFD_TYPE_INTEROPERABILITY = 3;
    private static final int IFD_TYPE_ORF_CAMERA_SETTINGS = 7;
    private static final int IFD_TYPE_ORF_IMAGE_PROCESSING = 8;
    private static final int IFD_TYPE_ORF_MAKER_NOTE = 6;
    private static final int IFD_TYPE_PEF = 9;
    static final int IFD_TYPE_PREVIEW = 5;
    static final int IFD_TYPE_PRIMARY = 0;
    static final int IFD_TYPE_THUMBNAIL = 4;
    private static final int IMAGE_TYPE_ARW = 1;
    private static final int IMAGE_TYPE_CR2 = 2;
    private static final int IMAGE_TYPE_DNG = 3;
    private static final int IMAGE_TYPE_JPEG = 4;
    private static final int IMAGE_TYPE_NEF = 5;
    private static final int IMAGE_TYPE_NRW = 6;
    private static final int IMAGE_TYPE_ORF = 7;
    private static final int IMAGE_TYPE_PEF = 8;
    private static final int IMAGE_TYPE_RAF = 9;
    private static final int IMAGE_TYPE_RW2 = 10;
    private static final int IMAGE_TYPE_SRW = 11;
    private static final int IMAGE_TYPE_UNKNOWN = 0;
    private static final ExifTag JPEG_INTERCHANGE_FORMAT_LENGTH_TAG = new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4);
    private static final ExifTag JPEG_INTERCHANGE_FORMAT_TAG = new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, 4);
    static final byte[] JPEG_SIGNATURE = new byte[]{(byte) -1, MARKER_SOI, (byte) -1};
    public static final String LATITUDE_NORTH = "N";
    public static final String LATITUDE_SOUTH = "S";
    public static final short LIGHT_SOURCE_CLOUDY_WEATHER = (short) 10;
    public static final short LIGHT_SOURCE_COOL_WHITE_FLUORESCENT = (short) 14;
    public static final short LIGHT_SOURCE_D50 = (short) 23;
    public static final short LIGHT_SOURCE_D55 = (short) 20;
    public static final short LIGHT_SOURCE_D65 = (short) 21;
    public static final short LIGHT_SOURCE_D75 = (short) 22;
    public static final short LIGHT_SOURCE_DAYLIGHT = (short) 1;
    public static final short LIGHT_SOURCE_DAYLIGHT_FLUORESCENT = (short) 12;
    public static final short LIGHT_SOURCE_DAY_WHITE_FLUORESCENT = (short) 13;
    public static final short LIGHT_SOURCE_FINE_WEATHER = (short) 9;
    public static final short LIGHT_SOURCE_FLASH = (short) 4;
    public static final short LIGHT_SOURCE_FLUORESCENT = (short) 2;
    public static final short LIGHT_SOURCE_ISO_STUDIO_TUNGSTEN = (short) 24;
    public static final short LIGHT_SOURCE_OTHER = (short) 255;
    public static final short LIGHT_SOURCE_SHADE = (short) 11;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_A = (short) 17;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_B = (short) 18;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_C = (short) 19;
    public static final short LIGHT_SOURCE_TUNGSTEN = (short) 3;
    public static final short LIGHT_SOURCE_UNKNOWN = (short) 0;
    public static final short LIGHT_SOURCE_WARM_WHITE_FLUORESCENT = (short) 16;
    public static final short LIGHT_SOURCE_WHITE_FLUORESCENT = (short) 15;
    public static final String LONGITUDE_EAST = "E";
    public static final String LONGITUDE_WEST = "W";
    static final byte MARKER = (byte) -1;
    static final byte MARKER_APP1 = (byte) -31;
    private static final byte MARKER_COM = (byte) -2;
    static final byte MARKER_EOI = (byte) -39;
    private static final byte MARKER_SOF0 = (byte) -64;
    private static final byte MARKER_SOF1 = (byte) -63;
    private static final byte MARKER_SOF10 = (byte) -54;
    private static final byte MARKER_SOF11 = (byte) -53;
    private static final byte MARKER_SOF13 = (byte) -51;
    private static final byte MARKER_SOF14 = (byte) -50;
    private static final byte MARKER_SOF15 = (byte) -49;
    private static final byte MARKER_SOF2 = (byte) -62;
    private static final byte MARKER_SOF3 = (byte) -61;
    private static final byte MARKER_SOF5 = (byte) -59;
    private static final byte MARKER_SOF6 = (byte) -58;
    private static final byte MARKER_SOF7 = (byte) -57;
    private static final byte MARKER_SOF9 = (byte) -55;
    private static final byte MARKER_SOI = (byte) -40;
    private static final byte MARKER_SOS = (byte) -38;
    private static final int MAX_THUMBNAIL_SIZE = 512;
    public static final short METERING_MODE_AVERAGE = (short) 1;
    public static final short METERING_MODE_CENTER_WEIGHT_AVERAGE = (short) 2;
    public static final short METERING_MODE_MULTI_SPOT = (short) 4;
    public static final short METERING_MODE_OTHER = (short) 255;
    public static final short METERING_MODE_PARTIAL = (short) 6;
    public static final short METERING_MODE_PATTERN = (short) 5;
    public static final short METERING_MODE_SPOT = (short) 3;
    public static final short METERING_MODE_UNKNOWN = (short) 0;
    private static final ExifTag[] ORF_CAMERA_SETTINGS_TAGS = new ExifTag[]{new ExifTag(TAG_ORF_PREVIEW_IMAGE_START, 257, 4), new ExifTag(TAG_ORF_PREVIEW_IMAGE_LENGTH, 258, 4)};
    private static final ExifTag[] ORF_IMAGE_PROCESSING_TAGS = new ExifTag[]{new ExifTag(TAG_ORF_ASPECT_FRAME, OlympusImageProcessingMakernoteDirectory.TagAspectFrame, 3)};
    private static final byte[] ORF_MAKER_NOTE_HEADER_1 = new byte[]{(byte) 79, (byte) 76, (byte) 89, (byte) 77, (byte) 80, (byte) 0};
    private static final int ORF_MAKER_NOTE_HEADER_1_SIZE = 8;
    private static final byte[] ORF_MAKER_NOTE_HEADER_2 = new byte[]{(byte) 79, (byte) 76, (byte) 89, (byte) 77, (byte) 80, (byte) 85, (byte) 83, (byte) 0, (byte) 73, (byte) 73};
    private static final int ORF_MAKER_NOTE_HEADER_2_SIZE = 12;
    private static final ExifTag[] ORF_MAKER_NOTE_TAGS = new ExifTag[]{new ExifTag(TAG_ORF_THUMBNAIL_IMAGE, 256, 7), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, 4), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, OlympusMakernoteDirectory.TAG_IMAGE_PROCESSING, 4)};
    private static final short ORF_SIGNATURE_1 = (short) 20306;
    private static final short ORF_SIGNATURE_2 = (short) 21330;
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    public static final int ORIGINAL_RESOLUTION_IMAGE = 0;
    private static final int PEF_MAKER_NOTE_SKIP_SIZE = 6;
    private static final String PEF_SIGNATURE = "PENTAX";
    private static final ExifTag[] PEF_TAGS = new ExifTag[]{new ExifTag(TAG_COLOR_SPACE, 55, 3)};
    public static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;
    public static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;
    public static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;
    public static final int PHOTOMETRIC_INTERPRETATION_YCBCR = 6;
    private static final int RAF_INFO_SIZE = 160;
    private static final int RAF_JPEG_LENGTH_VALUE_SIZE = 4;
    private static final int RAF_OFFSET_TO_JPEG_IMAGE_OFFSET = 84;
    private static final String RAF_SIGNATURE = "FUJIFILMCCD-RAW";
    public static final int REDUCED_RESOLUTION_IMAGE = 1;
    public static final short RENDERED_PROCESS_CUSTOM = (short) 1;
    public static final short RENDERED_PROCESS_NORMAL = (short) 0;
    public static final short RESOLUTION_UNIT_CENTIMETERS = (short) 3;
    public static final short RESOLUTION_UNIT_INCHES = (short) 2;
    private static final List<Integer> ROTATION_ORDER;
    private static final short RW2_SIGNATURE = (short) 85;
    public static final short SATURATION_HIGH = (short) 0;
    public static final short SATURATION_LOW = (short) 0;
    public static final short SATURATION_NORMAL = (short) 0;
    public static final short SCENE_CAPTURE_TYPE_LANDSCAPE = (short) 1;
    public static final short SCENE_CAPTURE_TYPE_NIGHT = (short) 3;
    public static final short SCENE_CAPTURE_TYPE_PORTRAIT = (short) 2;
    public static final short SCENE_CAPTURE_TYPE_STANDARD = (short) 0;
    public static final short SCENE_TYPE_DIRECTLY_PHOTOGRAPHED = (short) 1;
    public static final short SENSITIVITY_TYPE_ISO_SPEED = (short) 3;
    public static final short SENSITIVITY_TYPE_REI = (short) 2;
    public static final short SENSITIVITY_TYPE_REI_AND_ISO = (short) 6;
    public static final short SENSITIVITY_TYPE_SOS = (short) 1;
    public static final short SENSITIVITY_TYPE_SOS_AND_ISO = (short) 5;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI = (short) 4;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI_AND_ISO = (short) 7;
    public static final short SENSITIVITY_TYPE_UNKNOWN = (short) 0;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL = (short) 5;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL_LINEAR = (short) 8;
    public static final short SENSOR_TYPE_NOT_DEFINED = (short) 1;
    public static final short SENSOR_TYPE_ONE_CHIP = (short) 2;
    public static final short SENSOR_TYPE_THREE_CHIP = (short) 4;
    public static final short SENSOR_TYPE_TRILINEAR = (short) 7;
    public static final short SENSOR_TYPE_TWO_CHIP = (short) 3;
    public static final short SHARPNESS_HARD = (short) 2;
    public static final short SHARPNESS_NORMAL = (short) 0;
    public static final short SHARPNESS_SOFT = (short) 1;
    private static final int SIGNATURE_CHECK_SIZE = 5000;
    static final byte START_CODE = (byte) 42;
    public static final short SUBJECT_DISTANCE_RANGE_CLOSE_VIEW = (short) 2;
    public static final short SUBJECT_DISTANCE_RANGE_DISTANT_VIEW = (short) 3;
    public static final short SUBJECT_DISTANCE_RANGE_MACRO = (short) 1;
    public static final short SUBJECT_DISTANCE_RANGE_UNKNOWN = (short) 0;
    private static final String TAG = "ExifInterface";
    public static final String TAG_APERTURE_VALUE = "ApertureValue";
    public static final String TAG_ARTIST = "Artist";
    public static final String TAG_BITS_PER_SAMPLE = "BitsPerSample";
    public static final String TAG_BODY_SERIAL_NUMBER = "BodySerialNumber";
    public static final String TAG_BRIGHTNESS_VALUE = "BrightnessValue";
    public static final String TAG_CAMARA_OWNER_NAME = "CameraOwnerName";
    public static final String TAG_CFA_PATTERN = "CFAPattern";
    public static final String TAG_COLOR_SPACE = "ColorSpace";
    public static final String TAG_COMPONENTS_CONFIGURATION = "ComponentsConfiguration";
    public static final String TAG_COMPRESSED_BITS_PER_PIXEL = "CompressedBitsPerPixel";
    public static final String TAG_COMPRESSION = "Compression";
    public static final String TAG_CONTRAST = "Contrast";
    public static final String TAG_COPYRIGHT = "Copyright";
    public static final String TAG_CUSTOM_RENDERED = "CustomRendered";
    public static final String TAG_DATETIME = "DateTime";
    public static final String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final String TAG_DEFAULT_CROP_SIZE = "DefaultCropSize";
    public static final String TAG_DEVICE_SETTING_DESCRIPTION = "DeviceSettingDescription";
    public static final String TAG_DIGITAL_ZOOM_RATIO = "DigitalZoomRatio";
    public static final String TAG_DNG_VERSION = "DNGVersion";
    private static final String TAG_EXIF_IFD_POINTER = "ExifIFDPointer";
    public static final String TAG_EXIF_VERSION = "ExifVersion";
    public static final String TAG_EXPOSURE_BIAS_VALUE = "ExposureBiasValue";
    public static final String TAG_EXPOSURE_INDEX = "ExposureIndex";
    public static final String TAG_EXPOSURE_MODE = "ExposureMode";
    public static final String TAG_EXPOSURE_PROGRAM = "ExposureProgram";
    public static final String TAG_EXPOSURE_TIME = "ExposureTime";
    public static final String TAG_FILE_SOURCE = "FileSource";
    public static final String TAG_FLASH = "Flash";
    public static final String TAG_FLASHPIX_VERSION = "FlashpixVersion";
    public static final String TAG_FLASH_ENERGY = "FlashEnergy";
    public static final String TAG_FOCAL_LENGTH = "FocalLength";
    public static final String TAG_FOCAL_LENGTH_IN_35MM_FILM = "FocalLengthIn35mmFilm";
    public static final String TAG_FOCAL_PLANE_RESOLUTION_UNIT = "FocalPlaneResolutionUnit";
    public static final String TAG_FOCAL_PLANE_X_RESOLUTION = "FocalPlaneXResolution";
    public static final String TAG_FOCAL_PLANE_Y_RESOLUTION = "FocalPlaneYResolution";
    public static final String TAG_F_NUMBER = "FNumber";
    public static final String TAG_GAIN_CONTROL = "GainControl";
    public static final String TAG_GAMMA = "Gamma";
    public static final String TAG_GPS_ALTITUDE = "GPSAltitude";
    public static final String TAG_GPS_ALTITUDE_REF = "GPSAltitudeRef";
    public static final String TAG_GPS_AREA_INFORMATION = "GPSAreaInformation";
    public static final String TAG_GPS_DATESTAMP = "GPSDateStamp";
    public static final String TAG_GPS_DEST_BEARING = "GPSDestBearing";
    public static final String TAG_GPS_DEST_BEARING_REF = "GPSDestBearingRef";
    public static final String TAG_GPS_DEST_DISTANCE = "GPSDestDistance";
    public static final String TAG_GPS_DEST_DISTANCE_REF = "GPSDestDistanceRef";
    public static final String TAG_GPS_DEST_LATITUDE = "GPSDestLatitude";
    public static final String TAG_GPS_DEST_LATITUDE_REF = "GPSDestLatitudeRef";
    public static final String TAG_GPS_DEST_LONGITUDE = "GPSDestLongitude";
    public static final String TAG_GPS_DEST_LONGITUDE_REF = "GPSDestLongitudeRef";
    public static final String TAG_GPS_DIFFERENTIAL = "GPSDifferential";
    public static final String TAG_GPS_DOP = "GPSDOP";
    public static final String TAG_GPS_H_POSITIONING_ERROR = "GPSHPositioningError";
    public static final String TAG_GPS_IMG_DIRECTION = "GPSImgDirection";
    public static final String TAG_GPS_IMG_DIRECTION_REF = "GPSImgDirectionRef";
    private static final String TAG_GPS_INFO_IFD_POINTER = "GPSInfoIFDPointer";
    public static final String TAG_GPS_LATITUDE = "GPSLatitude";
    public static final String TAG_GPS_LATITUDE_REF = "GPSLatitudeRef";
    public static final String TAG_GPS_LONGITUDE = "GPSLongitude";
    public static final String TAG_GPS_LONGITUDE_REF = "GPSLongitudeRef";
    public static final String TAG_GPS_MAP_DATUM = "GPSMapDatum";
    public static final String TAG_GPS_MEASURE_MODE = "GPSMeasureMode";
    public static final String TAG_GPS_PROCESSING_METHOD = "GPSProcessingMethod";
    public static final String TAG_GPS_SATELLITES = "GPSSatellites";
    public static final String TAG_GPS_SPEED = "GPSSpeed";
    public static final String TAG_GPS_SPEED_REF = "GPSSpeedRef";
    public static final String TAG_GPS_STATUS = "GPSStatus";
    public static final String TAG_GPS_TIMESTAMP = "GPSTimeStamp";
    public static final String TAG_GPS_TRACK = "GPSTrack";
    public static final String TAG_GPS_TRACK_REF = "GPSTrackRef";
    public static final String TAG_GPS_VERSION_ID = "GPSVersionID";
    private static final String TAG_HAS_THUMBNAIL = "HasThumbnail";
    public static final String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    public static final String TAG_IMAGE_LENGTH = "ImageLength";
    public static final String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    public static final String TAG_IMAGE_WIDTH = "ImageWidth";
    private static final String TAG_INTEROPERABILITY_IFD_POINTER = "InteroperabilityIFDPointer";
    public static final String TAG_INTEROPERABILITY_INDEX = "InteroperabilityIndex";
    public static final String TAG_ISO_SPEED = "ISOSpeed";
    public static final String TAG_ISO_SPEED_LATITUDE_YYY = "ISOSpeedLatitudeyyy";
    public static final String TAG_ISO_SPEED_LATITUDE_ZZZ = "ISOSpeedLatitudezzz";
    @Deprecated
    public static final String TAG_ISO_SPEED_RATINGS = "ISOSpeedRatings";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT = "JPEGInterchangeFormat";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = "JPEGInterchangeFormatLength";
    public static final String TAG_LENS_MAKE = "LensMake";
    public static final String TAG_LENS_MODEL = "LensModel";
    public static final String TAG_LENS_SERIAL_NUMBER = "LensSerialNumber";
    public static final String TAG_LENS_SPECIFICATION = "LensSpecification";
    public static final String TAG_LIGHT_SOURCE = "LightSource";
    public static final String TAG_MAKE = "Make";
    public static final String TAG_MAKER_NOTE = "MakerNote";
    public static final String TAG_MAX_APERTURE_VALUE = "MaxApertureValue";
    public static final String TAG_METERING_MODE = "MeteringMode";
    public static final String TAG_MODEL = "Model";
    public static final String TAG_NEW_SUBFILE_TYPE = "NewSubfileType";
    public static final String TAG_OECF = "OECF";
    public static final String TAG_ORF_ASPECT_FRAME = "AspectFrame";
    private static final String TAG_ORF_CAMERA_SETTINGS_IFD_POINTER = "CameraSettingsIFDPointer";
    private static final String TAG_ORF_IMAGE_PROCESSING_IFD_POINTER = "ImageProcessingIFDPointer";
    public static final String TAG_ORF_PREVIEW_IMAGE_LENGTH = "PreviewImageLength";
    public static final String TAG_ORF_PREVIEW_IMAGE_START = "PreviewImageStart";
    public static final String TAG_ORF_THUMBNAIL_IMAGE = "ThumbnailImage";
    public static final String TAG_ORIENTATION = "Orientation";
    public static final String TAG_PHOTOGRAPHIC_SENSITIVITY = "PhotographicSensitivity";
    public static final String TAG_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    public static final String TAG_PIXEL_X_DIMENSION = "PixelXDimension";
    public static final String TAG_PIXEL_Y_DIMENSION = "PixelYDimension";
    public static final String TAG_PLANAR_CONFIGURATION = "PlanarConfiguration";
    public static final String TAG_PRIMARY_CHROMATICITIES = "PrimaryChromaticities";
    private static final ExifTag TAG_RAF_IMAGE_SIZE = new ExifTag(TAG_STRIP_OFFSETS, 273, 3);
    public static final String TAG_RECOMMENDED_EXPOSURE_INDEX = "RecommendedExposureIndex";
    public static final String TAG_REFERENCE_BLACK_WHITE = "ReferenceBlackWhite";
    public static final String TAG_RELATED_SOUND_FILE = "RelatedSoundFile";
    public static final String TAG_RESOLUTION_UNIT = "ResolutionUnit";
    public static final String TAG_ROWS_PER_STRIP = "RowsPerStrip";
    public static final String TAG_RW2_ISO = "ISO";
    public static final String TAG_RW2_JPG_FROM_RAW = "JpgFromRaw";
    public static final String TAG_RW2_SENSOR_BOTTOM_BORDER = "SensorBottomBorder";
    public static final String TAG_RW2_SENSOR_LEFT_BORDER = "SensorLeftBorder";
    public static final String TAG_RW2_SENSOR_RIGHT_BORDER = "SensorRightBorder";
    public static final String TAG_RW2_SENSOR_TOP_BORDER = "SensorTopBorder";
    public static final String TAG_SAMPLES_PER_PIXEL = "SamplesPerPixel";
    public static final String TAG_SATURATION = "Saturation";
    public static final String TAG_SCENE_CAPTURE_TYPE = "SceneCaptureType";
    public static final String TAG_SCENE_TYPE = "SceneType";
    public static final String TAG_SENSING_METHOD = "SensingMethod";
    public static final String TAG_SENSITIVITY_TYPE = "SensitivityType";
    public static final String TAG_SHARPNESS = "Sharpness";
    public static final String TAG_SHUTTER_SPEED_VALUE = "ShutterSpeedValue";
    public static final String TAG_SOFTWARE = "Software";
    public static final String TAG_SPATIAL_FREQUENCY_RESPONSE = "SpatialFrequencyResponse";
    public static final String TAG_SPECTRAL_SENSITIVITY = "SpectralSensitivity";
    public static final String TAG_STANDARD_OUTPUT_SENSITIVITY = "StandardOutputSensitivity";
    public static final String TAG_STRIP_BYTE_COUNTS = "StripByteCounts";
    public static final String TAG_STRIP_OFFSETS = "StripOffsets";
    public static final String TAG_SUBFILE_TYPE = "SubfileType";
    public static final String TAG_SUBJECT_AREA = "SubjectArea";
    public static final String TAG_SUBJECT_DISTANCE = "SubjectDistance";
    public static final String TAG_SUBJECT_DISTANCE_RANGE = "SubjectDistanceRange";
    public static final String TAG_SUBJECT_LOCATION = "SubjectLocation";
    public static final String TAG_SUBSEC_TIME = "SubSecTime";
    public static final String TAG_SUBSEC_TIME_DIGITIZED = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_ORIGINAL = "SubSecTimeOriginal";
    private static final String TAG_SUB_IFD_POINTER = "SubIFDPointer";
    private static final String TAG_THUMBNAIL_DATA = "ThumbnailData";
    public static final String TAG_THUMBNAIL_IMAGE_LENGTH = "ThumbnailImageLength";
    public static final String TAG_THUMBNAIL_IMAGE_WIDTH = "ThumbnailImageWidth";
    private static final String TAG_THUMBNAIL_LENGTH = "ThumbnailLength";
    private static final String TAG_THUMBNAIL_OFFSET = "ThumbnailOffset";
    public static final String TAG_TRANSFER_FUNCTION = "TransferFunction";
    public static final String TAG_USER_COMMENT = "UserComment";
    public static final String TAG_WHITE_BALANCE = "WhiteBalance";
    public static final String TAG_WHITE_POINT = "WhitePoint";
    public static final String TAG_X_RESOLUTION = "XResolution";
    public static final String TAG_Y_CB_CR_COEFFICIENTS = "YCbCrCoefficients";
    public static final String TAG_Y_CB_CR_POSITIONING = "YCbCrPositioning";
    public static final String TAG_Y_CB_CR_SUB_SAMPLING = "YCbCrSubSampling";
    public static final String TAG_Y_RESOLUTION = "YResolution";
    @Deprecated
    public static final int WHITEBALANCE_AUTO = 0;
    @Deprecated
    public static final int WHITEBALANCE_MANUAL = 1;
    public static final short WHITE_BALANCE_AUTO = (short) 0;
    public static final short WHITE_BALANCE_MANUAL = (short) 1;
    public static final short Y_CB_CR_POSITIONING_CENTERED = (short) 1;
    public static final short Y_CB_CR_POSITIONING_CO_SITED = (short) 2;
    private static final HashMap<Integer, Integer> sExifPointerTagMap = new HashMap();
    private static final HashMap<Integer, ExifTag>[] sExifTagMapsForReading;
    private static final HashMap<String, ExifTag>[] sExifTagMapsForWriting;
    private static SimpleDateFormat sFormatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    private static final Pattern sGpsTimestampPattern = Pattern.compile("^([0-9][0-9]):([0-9][0-9]):([0-9][0-9])$");
    private static final Pattern sNonZeroTimePattern = Pattern.compile(".*[1-9].*");
    private static final HashSet<String> sTagSetForCompatibility = new HashSet(Arrays.asList(new String[]{TAG_F_NUMBER, TAG_DIGITAL_ZOOM_RATIO, TAG_EXPOSURE_TIME, TAG_SUBJECT_DISTANCE, TAG_GPS_TIMESTAMP}));
    private final AssetInputStream mAssetInputStream;
    private final HashMap<String, ExifAttribute>[] mAttributes;
    private Set<Integer> mAttributesOffsets;
    private ByteOrder mExifByteOrder = ByteOrder.BIG_ENDIAN;
    private int mExifOffset;
    private final String mFilename;
    private boolean mHasThumbnail;
    private boolean mIsSupportedFile;
    private int mMimeType;
    private int mOrfMakerNoteOffset;
    private int mOrfThumbnailLength;
    private int mOrfThumbnailOffset;
    private int mRw2JpgFromRawOffset;
    private byte[] mThumbnailBytes;
    private int mThumbnailCompression;
    private int mThumbnailLength;
    private int mThumbnailOffset;

    private static class ByteOrderedDataInputStream extends InputStream implements DataInput {
        private static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
        private static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
        private ByteOrder mByteOrder;
        private DataInputStream mDataInputStream;
        final int mLength;
        int mPosition;

        public ByteOrderedDataInputStream(InputStream inputStream) throws IOException {
            this.mByteOrder = ByteOrder.BIG_ENDIAN;
            this.mDataInputStream = new DataInputStream(inputStream);
            this.mLength = this.mDataInputStream.available();
            this.mPosition = 0;
            this.mDataInputStream.mark(this.mLength);
        }

        public ByteOrderedDataInputStream(byte[] bArr) throws IOException {
            this(new ByteArrayInputStream(bArr));
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        public void seek(long j) throws IOException {
            int i = this.mPosition;
            if (((long) i) > j) {
                this.mPosition = 0;
                this.mDataInputStream.reset();
                this.mDataInputStream.mark(this.mLength);
            } else {
                j -= (long) i;
            }
            int i2 = (int) j;
            if (skipBytes(i2) != i2) {
                throw new IOException("Couldn't seek up to the byteCount");
            }
        }

        public int peek() {
            return this.mPosition;
        }

        public int available() throws IOException {
            return this.mDataInputStream.available();
        }

        public int read() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.read();
        }

        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read = this.mDataInputStream.read(bArr, i, i2);
            this.mPosition += read;
            return read;
        }

        public int readUnsignedByte() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.readUnsignedByte();
        }

        public String readLine() throws IOException {
            Log.d(ExifInterface.TAG, "Currently unsupported");
            return null;
        }

        public boolean readBoolean() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.readBoolean();
        }

        public char readChar() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readChar();
        }

        public String readUTF() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readUTF();
        }

        public void readFully(byte[] bArr, int i, int i2) throws IOException {
            this.mPosition += i2;
            if (this.mPosition > this.mLength) {
                throw new EOFException();
            } else if (this.mDataInputStream.read(bArr, i, i2) != i2) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        public void readFully(byte[] bArr) throws IOException {
            this.mPosition += bArr.length;
            if (this.mPosition > this.mLength) {
                throw new EOFException();
            } else if (this.mDataInputStream.read(bArr, 0, bArr.length) != bArr.length) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        public byte readByte() throws IOException {
            this.mPosition++;
            if (this.mPosition <= this.mLength) {
                int read = this.mDataInputStream.read();
                if (read >= 0) {
                    return (byte) read;
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public short readShort() throws IOException {
            this.mPosition += 2;
            if (this.mPosition <= this.mLength) {
                int read = this.mDataInputStream.read();
                int read2 = this.mDataInputStream.read();
                if ((read | read2) >= 0) {
                    ByteOrder byteOrder = this.mByteOrder;
                    if (byteOrder == LITTLE_ENDIAN) {
                        return (short) ((read2 << 8) + read);
                    }
                    if (byteOrder == BIG_ENDIAN) {
                        return (short) ((read << 8) + read2);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid byte order: ");
                    stringBuilder.append(this.mByteOrder);
                    throw new IOException(stringBuilder.toString());
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public int readInt() throws IOException {
            this.mPosition += 4;
            if (this.mPosition <= this.mLength) {
                int read = this.mDataInputStream.read();
                int read2 = this.mDataInputStream.read();
                int read3 = this.mDataInputStream.read();
                int read4 = this.mDataInputStream.read();
                if ((((read | read2) | read3) | read4) >= 0) {
                    ByteOrder byteOrder = this.mByteOrder;
                    if (byteOrder == LITTLE_ENDIAN) {
                        return (((read4 << 24) + (read3 << 16)) + (read2 << 8)) + read;
                    }
                    if (byteOrder == BIG_ENDIAN) {
                        return (((read << 24) + (read2 << 16)) + (read3 << 8)) + read4;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid byte order: ");
                    stringBuilder.append(this.mByteOrder);
                    throw new IOException(stringBuilder.toString());
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public int skipBytes(int i) throws IOException {
            i = Math.min(i, this.mLength - this.mPosition);
            int i2 = 0;
            while (i2 < i) {
                i2 += this.mDataInputStream.skipBytes(i - i2);
            }
            this.mPosition += i2;
            return i2;
        }

        public int readUnsignedShort() throws IOException {
            this.mPosition += 2;
            if (this.mPosition <= this.mLength) {
                int read = this.mDataInputStream.read();
                int read2 = this.mDataInputStream.read();
                if ((read | read2) >= 0) {
                    ByteOrder byteOrder = this.mByteOrder;
                    if (byteOrder == LITTLE_ENDIAN) {
                        return (read2 << 8) + read;
                    }
                    if (byteOrder == BIG_ENDIAN) {
                        return (read << 8) + read2;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid byte order: ");
                    stringBuilder.append(this.mByteOrder);
                    throw new IOException(stringBuilder.toString());
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public long readUnsignedInt() throws IOException {
            return ((long) readInt()) & 4294967295L;
        }

        public long readLong() throws IOException {
            this.mPosition += 8;
            if (this.mPosition <= this.mLength) {
                int read = this.mDataInputStream.read();
                int read2 = this.mDataInputStream.read();
                int read3 = this.mDataInputStream.read();
                int read4 = this.mDataInputStream.read();
                int read5 = this.mDataInputStream.read();
                int read6 = this.mDataInputStream.read();
                int read7 = this.mDataInputStream.read();
                int read8 = this.mDataInputStream.read();
                if ((((((((read | read2) | read3) | read4) | read5) | read6) | read7) | read8) >= 0) {
                    ByteOrder byteOrder = this.mByteOrder;
                    if (byteOrder == LITTLE_ENDIAN) {
                        return (((((((((long) read8) << 56) + (((long) read7) << 48)) + (((long) read6) << 40)) + (((long) read5) << 32)) + (((long) read4) << 24)) + (((long) read3) << 16)) + (((long) read2) << 8)) + ((long) read);
                    }
                    int i = read2;
                    if (byteOrder == BIG_ENDIAN) {
                        return (((((((((long) read) << 56) + (((long) i) << 48)) + (((long) read3) << 40)) + (((long) read4) << 32)) + (((long) read5) << 24)) + (((long) read6) << 16)) + (((long) read7) << 8)) + ((long) read8);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid byte order: ");
                    stringBuilder.append(this.mByteOrder);
                    throw new IOException(stringBuilder.toString());
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readInt());
        }

        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readLong());
        }
    }

    private static class ByteOrderedDataOutputStream extends FilterOutputStream {
        private ByteOrder mByteOrder;
        private final OutputStream mOutputStream;

        public ByteOrderedDataOutputStream(OutputStream outputStream, ByteOrder byteOrder) {
            super(outputStream);
            this.mOutputStream = outputStream;
            this.mByteOrder = byteOrder;
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        public void write(byte[] bArr) throws IOException {
            this.mOutputStream.write(bArr);
        }

        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.mOutputStream.write(bArr, i, i2);
        }

        public void writeByte(int i) throws IOException {
            this.mOutputStream.write(i);
        }

        public void writeShort(short s) throws IOException {
            if (this.mByteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((s >>> 0) & 255);
                this.mOutputStream.write((s >>> 8) & 255);
            } else if (this.mByteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((s >>> 8) & 255);
                this.mOutputStream.write((s >>> 0) & 255);
            }
        }

        public void writeInt(int i) throws IOException {
            if (this.mByteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((i >>> 0) & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 24) & 255);
            } else if (this.mByteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((i >>> 24) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write((i >>> 0) & 255);
            }
        }

        public void writeUnsignedShort(int i) throws IOException {
            writeShort((short) i);
        }

        public void writeUnsignedInt(long j) throws IOException {
            writeInt((int) j);
        }
    }

    private static class ExifAttribute {
        public final byte[] bytes;
        public final int format;
        public final int numberOfComponents;

        ExifAttribute(int i, int i2, byte[] bArr) {
            this.format = i;
            this.numberOfComponents = i2;
            this.bytes = bArr;
        }

        public static ExifAttribute createUShort(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[3] * iArr.length)]);
            wrap.order(byteOrder);
            for (int i : iArr) {
                wrap.putShort((short) i);
            }
            return new ExifAttribute(3, iArr.length, wrap.array());
        }

        public static ExifAttribute createUShort(int i, ByteOrder byteOrder) {
            return createUShort(new int[]{i}, byteOrder);
        }

        public static ExifAttribute createULong(long[] jArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[4] * jArr.length)]);
            wrap.order(byteOrder);
            for (long j : jArr) {
                wrap.putInt((int) j);
            }
            return new ExifAttribute(4, jArr.length, wrap.array());
        }

        public static ExifAttribute createULong(long j, ByteOrder byteOrder) {
            return createULong(new long[]{j}, byteOrder);
        }

        public static ExifAttribute createSLong(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[9] * iArr.length)]);
            wrap.order(byteOrder);
            for (int putInt : iArr) {
                wrap.putInt(putInt);
            }
            return new ExifAttribute(9, iArr.length, wrap.array());
        }

        public static ExifAttribute createSLong(int i, ByteOrder byteOrder) {
            return createSLong(new int[]{i}, byteOrder);
        }

        public static ExifAttribute createByte(String str) {
            if (str.length() != 1 || str.charAt(0) < '0' || str.charAt(0) > '1') {
                byte[] bytes = str.getBytes(ExifInterface.ASCII);
                return new ExifAttribute(1, bytes.length, bytes);
            }
            byte[] bArr = new byte[]{(byte) (str.charAt(0) - 48)};
            return new ExifAttribute(1, bArr.length, bArr);
        }

        public static ExifAttribute createString(String str) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(0);
            byte[] bytes = stringBuilder.toString().getBytes(ExifInterface.ASCII);
            return new ExifAttribute(2, bytes.length, bytes);
        }

        public static ExifAttribute createURational(Rational[] rationalArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[5] * rationalArr.length)]);
            wrap.order(byteOrder);
            for (Rational rational : rationalArr) {
                wrap.putInt((int) rational.numerator);
                wrap.putInt((int) rational.denominator);
            }
            return new ExifAttribute(5, rationalArr.length, wrap.array());
        }

        public static ExifAttribute createURational(Rational rational, ByteOrder byteOrder) {
            return createURational(new Rational[]{rational}, byteOrder);
        }

        public static ExifAttribute createSRational(Rational[] rationalArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[10] * rationalArr.length)]);
            wrap.order(byteOrder);
            for (Rational rational : rationalArr) {
                wrap.putInt((int) rational.numerator);
                wrap.putInt((int) rational.denominator);
            }
            return new ExifAttribute(10, rationalArr.length, wrap.array());
        }

        public static ExifAttribute createSRational(Rational rational, ByteOrder byteOrder) {
            return createSRational(new Rational[]{rational}, byteOrder);
        }

        public static ExifAttribute createDouble(double[] dArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[12] * dArr.length)]);
            wrap.order(byteOrder);
            for (double putDouble : dArr) {
                wrap.putDouble(putDouble);
            }
            return new ExifAttribute(12, dArr.length, wrap.array());
        }

        public static ExifAttribute createDouble(double d, ByteOrder byteOrder) {
            return createDouble(new double[]{d}, byteOrder);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append(ExifInterface.IFD_FORMAT_NAMES[this.format]);
            stringBuilder.append(", data length:");
            stringBuilder.append(this.bytes.length);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

        /* JADX WARNING: Removed duplicated region for block: B:104:0x011b A:{Catch:{ IOException -> 0x0191 }} */
        /* JADX WARNING: Removed duplicated region for block: B:161:0x01ab A:{SYNTHETIC, Splitter: B:161:0x01ab} */
        java.lang.Object getValue(java.nio.ByteOrder r11) {
            /*
            r10 = this;
            r0 = "IOException occurred while closing InputStream";
            r1 = "ExifInterface";
            r2 = 0;
            r3 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream;	 Catch:{ IOException -> 0x0196, all -> 0x0193 }
            r4 = r10.bytes;	 Catch:{ IOException -> 0x0196, all -> 0x0193 }
            r3.<init>(r4);	 Catch:{ IOException -> 0x0196, all -> 0x0193 }
            r3.setByteOrder(r11);	 Catch:{ IOException -> 0x0191 }
            r11 = r10.format;	 Catch:{ IOException -> 0x0191 }
            r4 = 1;
            r5 = 0;
            switch(r11) {
                case 1: goto L_0x014c;
                case 2: goto L_0x00fd;
                case 3: goto L_0x00e3;
                case 4: goto L_0x00c9;
                case 5: goto L_0x00a6;
                case 6: goto L_0x014c;
                case 7: goto L_0x00fd;
                case 8: goto L_0x008c;
                case 9: goto L_0x0072;
                case 10: goto L_0x004d;
                case 11: goto L_0x0032;
                case 12: goto L_0x0018;
                default: goto L_0x0016;
            };	 Catch:{ IOException -> 0x0191 }
        L_0x0016:
            goto L_0x0188;
        L_0x0018:
            r11 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            r11 = new double[r11];	 Catch:{ IOException -> 0x0191 }
        L_0x001c:
            r4 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            if (r5 >= r4) goto L_0x0029;
        L_0x0020:
            r6 = r3.readDouble();	 Catch:{ IOException -> 0x0191 }
            r11[r5] = r6;	 Catch:{ IOException -> 0x0191 }
            r5 = r5 + 1;
            goto L_0x001c;
        L_0x0029:
            r3.close();	 Catch:{ IOException -> 0x002d }
            goto L_0x0031;
        L_0x002d:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x0031:
            return r11;
        L_0x0032:
            r11 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            r11 = new double[r11];	 Catch:{ IOException -> 0x0191 }
        L_0x0036:
            r4 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            if (r5 >= r4) goto L_0x0044;
        L_0x003a:
            r4 = r3.readFloat();	 Catch:{ IOException -> 0x0191 }
            r6 = (double) r4;	 Catch:{ IOException -> 0x0191 }
            r11[r5] = r6;	 Catch:{ IOException -> 0x0191 }
            r5 = r5 + 1;
            goto L_0x0036;
        L_0x0044:
            r3.close();	 Catch:{ IOException -> 0x0048 }
            goto L_0x004c;
        L_0x0048:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x004c:
            return r11;
        L_0x004d:
            r11 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            r11 = new androidx.exifinterface.media.ExifInterface.Rational[r11];	 Catch:{ IOException -> 0x0191 }
        L_0x0051:
            r4 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            if (r5 >= r4) goto L_0x0069;
        L_0x0055:
            r4 = r3.readInt();	 Catch:{ IOException -> 0x0191 }
            r6 = (long) r4;	 Catch:{ IOException -> 0x0191 }
            r4 = r3.readInt();	 Catch:{ IOException -> 0x0191 }
            r8 = (long) r4;	 Catch:{ IOException -> 0x0191 }
            r4 = new androidx.exifinterface.media.ExifInterface$Rational;	 Catch:{ IOException -> 0x0191 }
            r4.<init>(r6, r8);	 Catch:{ IOException -> 0x0191 }
            r11[r5] = r4;	 Catch:{ IOException -> 0x0191 }
            r5 = r5 + 1;
            goto L_0x0051;
        L_0x0069:
            r3.close();	 Catch:{ IOException -> 0x006d }
            goto L_0x0071;
        L_0x006d:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x0071:
            return r11;
        L_0x0072:
            r11 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            r11 = new int[r11];	 Catch:{ IOException -> 0x0191 }
        L_0x0076:
            r4 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            if (r5 >= r4) goto L_0x0083;
        L_0x007a:
            r4 = r3.readInt();	 Catch:{ IOException -> 0x0191 }
            r11[r5] = r4;	 Catch:{ IOException -> 0x0191 }
            r5 = r5 + 1;
            goto L_0x0076;
        L_0x0083:
            r3.close();	 Catch:{ IOException -> 0x0087 }
            goto L_0x008b;
        L_0x0087:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x008b:
            return r11;
        L_0x008c:
            r11 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            r11 = new int[r11];	 Catch:{ IOException -> 0x0191 }
        L_0x0090:
            r4 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            if (r5 >= r4) goto L_0x009d;
        L_0x0094:
            r4 = r3.readShort();	 Catch:{ IOException -> 0x0191 }
            r11[r5] = r4;	 Catch:{ IOException -> 0x0191 }
            r5 = r5 + 1;
            goto L_0x0090;
        L_0x009d:
            r3.close();	 Catch:{ IOException -> 0x00a1 }
            goto L_0x00a5;
        L_0x00a1:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x00a5:
            return r11;
        L_0x00a6:
            r11 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            r11 = new androidx.exifinterface.media.ExifInterface.Rational[r11];	 Catch:{ IOException -> 0x0191 }
        L_0x00aa:
            r4 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            if (r5 >= r4) goto L_0x00c0;
        L_0x00ae:
            r6 = r3.readUnsignedInt();	 Catch:{ IOException -> 0x0191 }
            r8 = r3.readUnsignedInt();	 Catch:{ IOException -> 0x0191 }
            r4 = new androidx.exifinterface.media.ExifInterface$Rational;	 Catch:{ IOException -> 0x0191 }
            r4.<init>(r6, r8);	 Catch:{ IOException -> 0x0191 }
            r11[r5] = r4;	 Catch:{ IOException -> 0x0191 }
            r5 = r5 + 1;
            goto L_0x00aa;
        L_0x00c0:
            r3.close();	 Catch:{ IOException -> 0x00c4 }
            goto L_0x00c8;
        L_0x00c4:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x00c8:
            return r11;
        L_0x00c9:
            r11 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            r11 = new long[r11];	 Catch:{ IOException -> 0x0191 }
        L_0x00cd:
            r4 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            if (r5 >= r4) goto L_0x00da;
        L_0x00d1:
            r6 = r3.readUnsignedInt();	 Catch:{ IOException -> 0x0191 }
            r11[r5] = r6;	 Catch:{ IOException -> 0x0191 }
            r5 = r5 + 1;
            goto L_0x00cd;
        L_0x00da:
            r3.close();	 Catch:{ IOException -> 0x00de }
            goto L_0x00e2;
        L_0x00de:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x00e2:
            return r11;
        L_0x00e3:
            r11 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            r11 = new int[r11];	 Catch:{ IOException -> 0x0191 }
        L_0x00e7:
            r4 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            if (r5 >= r4) goto L_0x00f4;
        L_0x00eb:
            r4 = r3.readUnsignedShort();	 Catch:{ IOException -> 0x0191 }
            r11[r5] = r4;	 Catch:{ IOException -> 0x0191 }
            r5 = r5 + 1;
            goto L_0x00e7;
        L_0x00f4:
            r3.close();	 Catch:{ IOException -> 0x00f8 }
            goto L_0x00fc;
        L_0x00f8:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x00fc:
            return r11;
        L_0x00fd:
            r11 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            r6 = androidx.exifinterface.media.ExifInterface.EXIF_ASCII_PREFIX;	 Catch:{ IOException -> 0x0191 }
            r6 = r6.length;	 Catch:{ IOException -> 0x0191 }
            if (r11 < r6) goto L_0x011e;
        L_0x0104:
            r11 = 0;
        L_0x0105:
            r6 = androidx.exifinterface.media.ExifInterface.EXIF_ASCII_PREFIX;	 Catch:{ IOException -> 0x0191 }
            r6 = r6.length;	 Catch:{ IOException -> 0x0191 }
            if (r11 >= r6) goto L_0x0119;
        L_0x010a:
            r6 = r10.bytes;	 Catch:{ IOException -> 0x0191 }
            r6 = r6[r11];	 Catch:{ IOException -> 0x0191 }
            r7 = androidx.exifinterface.media.ExifInterface.EXIF_ASCII_PREFIX;	 Catch:{ IOException -> 0x0191 }
            r7 = r7[r11];	 Catch:{ IOException -> 0x0191 }
            if (r6 == r7) goto L_0x0116;
        L_0x0114:
            r4 = 0;
            goto L_0x0119;
        L_0x0116:
            r11 = r11 + 1;
            goto L_0x0105;
        L_0x0119:
            if (r4 == 0) goto L_0x011e;
        L_0x011b:
            r11 = androidx.exifinterface.media.ExifInterface.EXIF_ASCII_PREFIX;	 Catch:{ IOException -> 0x0191 }
            r5 = r11.length;	 Catch:{ IOException -> 0x0191 }
        L_0x011e:
            r11 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0191 }
            r11.<init>();	 Catch:{ IOException -> 0x0191 }
        L_0x0123:
            r4 = r10.numberOfComponents;	 Catch:{ IOException -> 0x0191 }
            if (r5 >= r4) goto L_0x013f;
        L_0x0127:
            r4 = r10.bytes;	 Catch:{ IOException -> 0x0191 }
            r4 = r4[r5];	 Catch:{ IOException -> 0x0191 }
            if (r4 != 0) goto L_0x012e;
        L_0x012d:
            goto L_0x013f;
        L_0x012e:
            r6 = 32;
            if (r4 < r6) goto L_0x0137;
        L_0x0132:
            r4 = (char) r4;	 Catch:{ IOException -> 0x0191 }
            r11.append(r4);	 Catch:{ IOException -> 0x0191 }
            goto L_0x013c;
        L_0x0137:
            r4 = 63;
            r11.append(r4);	 Catch:{ IOException -> 0x0191 }
        L_0x013c:
            r5 = r5 + 1;
            goto L_0x0123;
        L_0x013f:
            r11 = r11.toString();	 Catch:{ IOException -> 0x0191 }
            r3.close();	 Catch:{ IOException -> 0x0147 }
            goto L_0x014b;
        L_0x0147:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x014b:
            return r11;
        L_0x014c:
            r11 = r10.bytes;	 Catch:{ IOException -> 0x0191 }
            r11 = r11.length;	 Catch:{ IOException -> 0x0191 }
            if (r11 != r4) goto L_0x0176;
        L_0x0151:
            r11 = r10.bytes;	 Catch:{ IOException -> 0x0191 }
            r11 = r11[r5];	 Catch:{ IOException -> 0x0191 }
            if (r11 < 0) goto L_0x0176;
        L_0x0157:
            r11 = r10.bytes;	 Catch:{ IOException -> 0x0191 }
            r11 = r11[r5];	 Catch:{ IOException -> 0x0191 }
            if (r11 > r4) goto L_0x0176;
        L_0x015d:
            r11 = new java.lang.String;	 Catch:{ IOException -> 0x0191 }
            r4 = new char[r4];	 Catch:{ IOException -> 0x0191 }
            r6 = r10.bytes;	 Catch:{ IOException -> 0x0191 }
            r6 = r6[r5];	 Catch:{ IOException -> 0x0191 }
            r6 = r6 + 48;
            r6 = (char) r6;	 Catch:{ IOException -> 0x0191 }
            r4[r5] = r6;	 Catch:{ IOException -> 0x0191 }
            r11.<init>(r4);	 Catch:{ IOException -> 0x0191 }
            r3.close();	 Catch:{ IOException -> 0x0171 }
            goto L_0x0175;
        L_0x0171:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x0175:
            return r11;
        L_0x0176:
            r11 = new java.lang.String;	 Catch:{ IOException -> 0x0191 }
            r4 = r10.bytes;	 Catch:{ IOException -> 0x0191 }
            r5 = androidx.exifinterface.media.ExifInterface.ASCII;	 Catch:{ IOException -> 0x0191 }
            r11.<init>(r4, r5);	 Catch:{ IOException -> 0x0191 }
            r3.close();	 Catch:{ IOException -> 0x0183 }
            goto L_0x0187;
        L_0x0183:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x0187:
            return r11;
        L_0x0188:
            r3.close();	 Catch:{ IOException -> 0x018c }
            goto L_0x0190;
        L_0x018c:
            r11 = move-exception;
            android.util.Log.e(r1, r0, r11);
        L_0x0190:
            return r2;
        L_0x0191:
            r11 = move-exception;
            goto L_0x0198;
        L_0x0193:
            r11 = move-exception;
            r3 = r2;
            goto L_0x01a9;
        L_0x0196:
            r11 = move-exception;
            r3 = r2;
        L_0x0198:
            r4 = "IOException occurred during reading a value";
            android.util.Log.w(r1, r4, r11);	 Catch:{ all -> 0x01a8 }
            if (r3 == 0) goto L_0x01a7;
        L_0x019f:
            r3.close();	 Catch:{ IOException -> 0x01a3 }
            goto L_0x01a7;
        L_0x01a3:
            r11 = move-exception;
            android.util.Log.e(r1, r0, r11);
        L_0x01a7:
            return r2;
        L_0x01a8:
            r11 = move-exception;
        L_0x01a9:
            if (r3 == 0) goto L_0x01b3;
        L_0x01ab:
            r3.close();	 Catch:{ IOException -> 0x01af }
            goto L_0x01b3;
        L_0x01af:
            r2 = move-exception;
            android.util.Log.e(r1, r0, r2);
        L_0x01b3:
            throw r11;
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.ExifAttribute.getValue(java.nio.ByteOrder):java.lang.Object");
        }

        public double getDoubleValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a double value");
            } else if (value instanceof String) {
                return Double.parseDouble((String) value);
            } else {
                String str = "There are more than one component";
                if (value instanceof long[]) {
                    long[] jArr = (long[]) value;
                    if (jArr.length == 1) {
                        return (double) jArr[0];
                    }
                    throw new NumberFormatException(str);
                } else if (value instanceof int[]) {
                    int[] iArr = (int[]) value;
                    if (iArr.length == 1) {
                        return (double) iArr[0];
                    }
                    throw new NumberFormatException(str);
                } else if (value instanceof double[]) {
                    double[] dArr = (double[]) value;
                    if (dArr.length == 1) {
                        return dArr[0];
                    }
                    throw new NumberFormatException(str);
                } else if (value instanceof Rational[]) {
                    Rational[] rationalArr = (Rational[]) value;
                    if (rationalArr.length == 1) {
                        return rationalArr[0].calculate();
                    }
                    throw new NumberFormatException(str);
                } else {
                    throw new NumberFormatException("Couldn't find a double value");
                }
            }
        }

        public int getIntValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a integer value");
            } else if (value instanceof String) {
                return Integer.parseInt((String) value);
            } else {
                String str = "There are more than one component";
                if (value instanceof long[]) {
                    long[] jArr = (long[]) value;
                    if (jArr.length == 1) {
                        return (int) jArr[0];
                    }
                    throw new NumberFormatException(str);
                } else if (value instanceof int[]) {
                    int[] iArr = (int[]) value;
                    if (iArr.length == 1) {
                        return iArr[0];
                    }
                    throw new NumberFormatException(str);
                } else {
                    throw new NumberFormatException("Couldn't find a integer value");
                }
            }
        }

        public String getStringValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                return (String) value;
            }
            StringBuilder stringBuilder = new StringBuilder();
            String str = ",";
            int i = 0;
            if (value instanceof long[]) {
                long[] jArr = (long[]) value;
                while (i < jArr.length) {
                    stringBuilder.append(jArr[i]);
                    i++;
                    if (i != jArr.length) {
                        stringBuilder.append(str);
                    }
                }
                return stringBuilder.toString();
            } else if (value instanceof int[]) {
                int[] iArr = (int[]) value;
                while (i < iArr.length) {
                    stringBuilder.append(iArr[i]);
                    i++;
                    if (i != iArr.length) {
                        stringBuilder.append(str);
                    }
                }
                return stringBuilder.toString();
            } else if (value instanceof double[]) {
                double[] dArr = (double[]) value;
                while (i < dArr.length) {
                    stringBuilder.append(dArr[i]);
                    i++;
                    if (i != dArr.length) {
                        stringBuilder.append(str);
                    }
                }
                return stringBuilder.toString();
            } else if (!(value instanceof Rational[])) {
                return null;
            } else {
                Rational[] rationalArr = (Rational[]) value;
                while (i < rationalArr.length) {
                    stringBuilder.append(rationalArr[i].numerator);
                    stringBuilder.append('/');
                    stringBuilder.append(rationalArr[i].denominator);
                    i++;
                    if (i != rationalArr.length) {
                        stringBuilder.append(str);
                    }
                }
                return stringBuilder.toString();
            }
        }

        public int size() {
            return ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[this.format] * this.numberOfComponents;
        }
    }

    static class ExifTag {
        public final String name;
        public final int number;
        public final int primaryFormat;
        public final int secondaryFormat;

        ExifTag(String str, int i, int i2) {
            this.name = str;
            this.number = i;
            this.primaryFormat = i2;
            this.secondaryFormat = -1;
        }

        ExifTag(String str, int i, int i2, int i3) {
            this.name = str;
            this.number = i;
            this.primaryFormat = i2;
            this.secondaryFormat = i3;
        }

        boolean isFormatCompatible(int i) {
            int i2 = this.primaryFormat;
            if (!(i2 == 7 || i == 7 || i2 == i)) {
                int i3 = this.secondaryFormat;
                if (i3 != i) {
                    if ((i2 == 4 || i3 == 4) && i == 3) {
                        return true;
                    }
                    if ((this.primaryFormat == 9 || this.secondaryFormat == 9) && i == 8) {
                        return true;
                    }
                    if ((this.primaryFormat == 12 || this.secondaryFormat == 12) && i == 11) {
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
    }

    @RestrictTo({Scope.LIBRARY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IfdType {
    }

    private static class Rational {
        public final long denominator;
        public final long numerator;

        Rational(double d) {
            this((long) (d * 10000.0d), 10000);
        }

        Rational(long j, long j2) {
            if (j2 == 0) {
                this.numerator = 0;
                this.denominator = 1;
                return;
            }
            this.numerator = j;
            this.denominator = j2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.numerator);
            stringBuilder.append("/");
            stringBuilder.append(this.denominator);
            return stringBuilder.toString();
        }

        public double calculate() {
            return ((double) this.numerator) / ((double) this.denominator);
        }
    }

    static {
        Integer[] numArr = new Integer[4];
        Integer valueOf = Integer.valueOf(1);
        numArr[0] = valueOf;
        numArr[1] = Integer.valueOf(6);
        Integer valueOf2 = Integer.valueOf(3);
        Integer valueOf3 = Integer.valueOf(2);
        numArr[2] = valueOf2;
        Integer valueOf4 = Integer.valueOf(8);
        numArr[3] = valueOf4;
        ROTATION_ORDER = Arrays.asList(numArr);
        numArr = new Integer[4];
        Integer valueOf5 = Integer.valueOf(7);
        numArr[1] = valueOf5;
        numArr[2] = Integer.valueOf(4);
        Integer valueOf6 = Integer.valueOf(5);
        numArr[3] = valueOf6;
        FLIPPED_ROTATION_ORDER = Arrays.asList(numArr);
        r1 = new ExifTag[10][];
        ExifTag[] exifTagArr = IFD_TIFF_TAGS;
        r1[0] = exifTagArr;
        r1[1] = IFD_EXIF_TAGS;
        r1[2] = IFD_GPS_TAGS;
        r1[3] = IFD_INTEROPERABILITY_TAGS;
        r1[4] = IFD_THUMBNAIL_TAGS;
        r1[5] = exifTagArr;
        r1[6] = ORF_MAKER_NOTE_TAGS;
        r1[7] = ORF_CAMERA_SETTINGS_TAGS;
        r1[8] = ORF_IMAGE_PROCESSING_TAGS;
        r1[9] = PEF_TAGS;
        EXIF_TAGS = r1;
        r1 = EXIF_TAGS;
        sExifTagMapsForReading = new HashMap[r1.length];
        sExifTagMapsForWriting = new HashMap[r1.length];
        sFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            sExifTagMapsForReading[i] = new HashMap();
            sExifTagMapsForWriting[i] = new HashMap();
            for (ExifTag exifTag : EXIF_TAGS[i]) {
                sExifTagMapsForReading[i].put(Integer.valueOf(exifTag.number), exifTag);
                sExifTagMapsForWriting[i].put(exifTag.name, exifTag);
            }
        }
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[0].number), valueOf6);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[1].number), valueOf);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[2].number), valueOf3);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[3].number), valueOf2);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[4].number), valueOf5);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[5].number), valueOf4);
    }

    public ExifInterface(@NonNull String str) throws IOException {
        Throwable th;
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        if (str != null) {
            Closeable closeable = null;
            this.mAssetInputStream = null;
            this.mFilename = str;
            try {
                Closeable fileInputStream = new FileInputStream(str);
                try {
                    loadAttributes(fileInputStream);
                    closeQuietly(fileInputStream);
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    closeable = fileInputStream;
                    closeQuietly(closeable);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                closeQuietly(closeable);
                throw th;
            }
        }
        throw new IllegalArgumentException("filename cannot be null");
    }

    public ExifInterface(@NonNull InputStream inputStream) throws IOException {
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        if (inputStream != null) {
            this.mFilename = null;
            if (inputStream instanceof AssetInputStream) {
                this.mAssetInputStream = (AssetInputStream) inputStream;
            } else {
                this.mAssetInputStream = null;
            }
            loadAttributes(inputStream);
            return;
        }
        throw new IllegalArgumentException("inputStream cannot be null");
    }

    @Nullable
    private ExifAttribute getExifAttribute(@NonNull String str) {
        Object str2;
        if (TAG_ISO_SPEED_RATINGS.equals(str2)) {
            str2 = TAG_PHOTOGRAPHIC_SENSITIVITY;
        }
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[i].get(str2);
            if (exifAttribute != null) {
                return exifAttribute;
            }
        }
        return null;
    }

    @Nullable
    public String getAttribute(@NonNull String str) {
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            if (!sTagSetForCompatibility.contains(str)) {
                return exifAttribute.getStringValue(this.mExifByteOrder);
            }
            if (str.equals(TAG_GPS_TIMESTAMP)) {
                int i = exifAttribute.format;
                String str2 = TAG;
                if (i == 5 || exifAttribute.format == 10) {
                    Rational[] rationalArr = (Rational[]) exifAttribute.getValue(this.mExifByteOrder);
                    if (rationalArr == null || rationalArr.length != 3) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid GPS Timestamp array. array=");
                        stringBuilder.append(Arrays.toString(rationalArr));
                        Log.w(str2, stringBuilder.toString());
                        return null;
                    }
                    return String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf((int) (((float) rationalArr[0].numerator) / ((float) rationalArr[0].denominator))), Integer.valueOf((int) (((float) rationalArr[1].numerator) / ((float) rationalArr[1].denominator))), Integer.valueOf((int) (((float) rationalArr[2].numerator) / ((float) rationalArr[2].denominator)))});
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("GPS Timestamp format is not rational. format=");
                stringBuilder2.append(exifAttribute.format);
                Log.w(str2, stringBuilder2.toString());
                return null;
            }
            try {
                return Double.toString(exifAttribute.getDoubleValue(this.mExifByteOrder));
            } catch (NumberFormatException unused) {
                return null;
            }
        }
    }

    public int getAttributeInt(@NonNull String str, int i) {
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute == null) {
            return i;
        }
        try {
            return exifAttribute.getIntValue(this.mExifByteOrder);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    public double getAttributeDouble(@NonNull String str, double d) {
        ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute == null) {
            return d;
        }
        try {
            return exifAttribute.getDoubleValue(this.mExifByteOrder);
        } catch (NumberFormatException unused) {
            return d;
        }
    }

    /* JADX WARNING: Missing block: B:75:0x0264, code:
            r4 = r17;
     */
    /* JADX WARNING: Missing block: B:93:0x0313, code:
            r4 = r17;
     */
    public void setAttribute(@androidx.annotation.NonNull java.lang.String r17, @androidx.annotation.Nullable java.lang.String r18) {
        /*
        r16 = this;
        r0 = r16;
        r1 = r18;
        r2 = "ISOSpeedRatings";
        r3 = r17;
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x0011;
    L_0x000e:
        r2 = "PhotographicSensitivity";
        goto L_0x0012;
    L_0x0011:
        r2 = r3;
    L_0x0012:
        r3 = 2;
        r4 = "ExifInterface";
        r5 = 1;
        if (r1 == 0) goto L_0x00b1;
    L_0x0018:
        r6 = sTagSetForCompatibility;
        r6 = r6.contains(r2);
        if (r6 == 0) goto L_0x00b1;
    L_0x0020:
        r6 = "GPSTimeStamp";
        r6 = r2.equals(r6);
        r7 = " : ";
        r8 = "Invalid value for ";
        if (r6 == 0) goto L_0x008a;
    L_0x002c:
        r6 = sGpsTimestampPattern;
        r6 = r6.matcher(r1);
        r9 = r6.find();
        if (r9 != 0) goto L_0x0051;
    L_0x0038:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r3.append(r8);
        r3.append(r2);
        r3.append(r7);
        r3.append(r1);
        r1 = r3.toString();
        android.util.Log.w(r4, r1);
        return;
    L_0x0051:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r7 = r6.group(r5);
        r7 = java.lang.Integer.parseInt(r7);
        r1.append(r7);
        r7 = "/1,";
        r1.append(r7);
        r8 = r6.group(r3);
        r8 = java.lang.Integer.parseInt(r8);
        r1.append(r8);
        r1.append(r7);
        r7 = 3;
        r6 = r6.group(r7);
        r6 = java.lang.Integer.parseInt(r6);
        r1.append(r6);
        r6 = "/1";
        r1.append(r6);
        r1 = r1.toString();
        goto L_0x00b1;
    L_0x008a:
        r9 = java.lang.Double.parseDouble(r18);	 Catch:{ NumberFormatException -> 0x0098 }
        r6 = new androidx.exifinterface.media.ExifInterface$Rational;	 Catch:{ NumberFormatException -> 0x0098 }
        r6.<init>(r9);	 Catch:{ NumberFormatException -> 0x0098 }
        r1 = r6.toString();	 Catch:{ NumberFormatException -> 0x0098 }
        goto L_0x00b1;
    L_0x0098:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r3.append(r8);
        r3.append(r2);
        r3.append(r7);
        r3.append(r1);
        r1 = r3.toString();
        android.util.Log.w(r4, r1);
        return;
    L_0x00b1:
        r6 = 0;
        r7 = 0;
    L_0x00b3:
        r8 = EXIF_TAGS;
        r8 = r8.length;
        if (r7 >= r8) goto L_0x031e;
    L_0x00b8:
        r8 = 4;
        if (r7 != r8) goto L_0x00c1;
    L_0x00bb:
        r8 = r0.mHasThumbnail;
        if (r8 != 0) goto L_0x00c1;
    L_0x00bf:
        goto L_0x0316;
    L_0x00c1:
        r8 = sExifTagMapsForWriting;
        r8 = r8[r7];
        r8 = r8.get(r2);
        r8 = (androidx.exifinterface.media.ExifInterface.ExifTag) r8;
        if (r8 == 0) goto L_0x0316;
    L_0x00cd:
        if (r1 != 0) goto L_0x00d8;
    L_0x00cf:
        r8 = r0.mAttributes;
        r8 = r8[r7];
        r8.remove(r2);
        goto L_0x0316;
    L_0x00d8:
        r9 = guessDataFormat(r1);
        r10 = r8.primaryFormat;
        r11 = r9.first;
        r11 = (java.lang.Integer) r11;
        r11 = r11.intValue();
        r12 = -1;
        if (r10 == r11) goto L_0x01b6;
    L_0x00e9:
        r10 = r8.primaryFormat;
        r11 = r9.second;
        r11 = (java.lang.Integer) r11;
        r11 = r11.intValue();
        if (r10 != r11) goto L_0x00f7;
    L_0x00f5:
        goto L_0x01b6;
    L_0x00f7:
        r10 = r8.secondaryFormat;
        if (r10 == r12) goto L_0x0117;
    L_0x00fb:
        r10 = r8.secondaryFormat;
        r11 = r9.first;
        r11 = (java.lang.Integer) r11;
        r11 = r11.intValue();
        if (r10 == r11) goto L_0x0113;
    L_0x0107:
        r10 = r8.secondaryFormat;
        r11 = r9.second;
        r11 = (java.lang.Integer) r11;
        r11 = r11.intValue();
        if (r10 != r11) goto L_0x0117;
    L_0x0113:
        r8 = r8.secondaryFormat;
        goto L_0x01b8;
    L_0x0117:
        r10 = r8.primaryFormat;
        if (r10 == r5) goto L_0x01b3;
    L_0x011b:
        r10 = r8.primaryFormat;
        r11 = 7;
        if (r10 == r11) goto L_0x01b3;
    L_0x0120:
        r10 = r8.primaryFormat;
        if (r10 != r3) goto L_0x0126;
    L_0x0124:
        goto L_0x01b3;
    L_0x0126:
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Given tag (";
        r10.append(r11);
        r10.append(r2);
        r11 = ") value didn't match with one of expected ";
        r10.append(r11);
        r11 = "formats: ";
        r10.append(r11);
        r11 = IFD_FORMAT_NAMES;
        r13 = r8.primaryFormat;
        r11 = r11[r13];
        r10.append(r11);
        r11 = r8.secondaryFormat;
        r13 = "";
        r14 = ", ";
        if (r11 != r12) goto L_0x0150;
    L_0x014e:
        r8 = r13;
        goto L_0x0165;
    L_0x0150:
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r11.append(r14);
        r15 = IFD_FORMAT_NAMES;
        r8 = r8.secondaryFormat;
        r8 = r15[r8];
        r11.append(r8);
        r8 = r11.toString();
    L_0x0165:
        r10.append(r8);
        r8 = " (guess: ";
        r10.append(r8);
        r8 = IFD_FORMAT_NAMES;
        r11 = r9.first;
        r11 = (java.lang.Integer) r11;
        r11 = r11.intValue();
        r8 = r8[r11];
        r10.append(r8);
        r8 = r9.second;
        r8 = (java.lang.Integer) r8;
        r8 = r8.intValue();
        if (r8 != r12) goto L_0x0187;
    L_0x0186:
        goto L_0x01a2;
    L_0x0187:
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r8.append(r14);
        r11 = IFD_FORMAT_NAMES;
        r9 = r9.second;
        r9 = (java.lang.Integer) r9;
        r9 = r9.intValue();
        r9 = r11[r9];
        r8.append(r9);
        r13 = r8.toString();
    L_0x01a2:
        r10.append(r13);
        r8 = ")";
        r10.append(r8);
        r8 = r10.toString();
        android.util.Log.w(r4, r8);
        goto L_0x0316;
    L_0x01b3:
        r8 = r8.primaryFormat;
        goto L_0x01b8;
    L_0x01b6:
        r8 = r8.primaryFormat;
    L_0x01b8:
        r9 = "/";
        r10 = ",";
        switch(r8) {
            case 1: goto L_0x0305;
            case 2: goto L_0x02f6;
            case 3: goto L_0x02ce;
            case 4: goto L_0x02a6;
            case 5: goto L_0x0268;
            case 6: goto L_0x01bf;
            case 7: goto L_0x02f6;
            case 8: goto L_0x01bf;
            case 9: goto L_0x023f;
            case 10: goto L_0x01ff;
            case 11: goto L_0x01bf;
            case 12: goto L_0x01da;
            default: goto L_0x01bf;
        };
    L_0x01bf:
        r17 = r4;
        r15 = 1;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Data format isn't one of expected formats: ";
        r3.append(r4);
        r3.append(r8);
        r3 = r3.toString();
        r4 = r17;
        android.util.Log.w(r4, r3);
        goto L_0x0317;
    L_0x01da:
        r8 = r1.split(r10, r12);
        r9 = r8.length;
        r9 = new double[r9];
        r10 = 0;
    L_0x01e2:
        r11 = r8.length;
        if (r10 >= r11) goto L_0x01f0;
    L_0x01e5:
        r11 = r8[r10];
        r11 = java.lang.Double.parseDouble(r11);
        r9[r10] = r11;
        r10 = r10 + 1;
        goto L_0x01e2;
    L_0x01f0:
        r8 = r0.mAttributes;
        r8 = r8[r7];
        r10 = r0.mExifByteOrder;
        r9 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createDouble(r9, r10);
        r8.put(r2, r9);
        goto L_0x0316;
    L_0x01ff:
        r8 = r1.split(r10, r12);
        r10 = r8.length;
        r10 = new androidx.exifinterface.media.ExifInterface.Rational[r10];
        r11 = 0;
    L_0x0207:
        r13 = r8.length;
        if (r11 >= r13) goto L_0x022f;
    L_0x020a:
        r13 = r8[r11];
        r13 = r13.split(r9, r12);
        r14 = new androidx.exifinterface.media.ExifInterface$Rational;
        r15 = r13[r6];
        r17 = r4;
        r3 = java.lang.Double.parseDouble(r15);
        r3 = (long) r3;
        r13 = r13[r5];
        r5 = java.lang.Double.parseDouble(r13);
        r5 = (long) r5;
        r14.<init>(r3, r5);
        r10[r11] = r14;
        r11 = r11 + 1;
        r4 = r17;
        r3 = 2;
        r5 = 1;
        r6 = 0;
        goto L_0x0207;
    L_0x022f:
        r17 = r4;
        r3 = r0.mAttributes;
        r3 = r3[r7];
        r4 = r0.mExifByteOrder;
        r4 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createSRational(r10, r4);
        r3.put(r2, r4);
        goto L_0x0264;
    L_0x023f:
        r17 = r4;
        r3 = r1.split(r10, r12);
        r4 = r3.length;
        r4 = new int[r4];
        r5 = 0;
    L_0x0249:
        r6 = r3.length;
        if (r5 >= r6) goto L_0x0257;
    L_0x024c:
        r6 = r3[r5];
        r6 = java.lang.Integer.parseInt(r6);
        r4[r5] = r6;
        r5 = r5 + 1;
        goto L_0x0249;
    L_0x0257:
        r3 = r0.mAttributes;
        r3 = r3[r7];
        r5 = r0.mExifByteOrder;
        r4 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createSLong(r4, r5);
        r3.put(r2, r4);
    L_0x0264:
        r4 = r17;
        goto L_0x0316;
    L_0x0268:
        r17 = r4;
        r3 = r1.split(r10, r12);
        r4 = r3.length;
        r4 = new androidx.exifinterface.media.ExifInterface.Rational[r4];
        r5 = 0;
    L_0x0272:
        r6 = r3.length;
        if (r5 >= r6) goto L_0x0296;
    L_0x0275:
        r6 = r3[r5];
        r6 = r6.split(r9, r12);
        r8 = new androidx.exifinterface.media.ExifInterface$Rational;
        r11 = 0;
        r10 = r6[r11];
        r13 = java.lang.Double.parseDouble(r10);
        r13 = (long) r13;
        r15 = 1;
        r6 = r6[r15];
        r11 = java.lang.Double.parseDouble(r6);
        r10 = (long) r11;
        r8.<init>(r13, r10);
        r4[r5] = r8;
        r5 = r5 + 1;
        r12 = -1;
        goto L_0x0272;
    L_0x0296:
        r15 = 1;
        r3 = r0.mAttributes;
        r3 = r3[r7];
        r5 = r0.mExifByteOrder;
        r4 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createURational(r4, r5);
        r3.put(r2, r4);
        goto L_0x0313;
    L_0x02a6:
        r17 = r4;
        r3 = -1;
        r15 = 1;
        r3 = r1.split(r10, r3);
        r4 = r3.length;
        r4 = new long[r4];
        r5 = 0;
    L_0x02b2:
        r6 = r3.length;
        if (r5 >= r6) goto L_0x02c0;
    L_0x02b5:
        r6 = r3[r5];
        r8 = java.lang.Long.parseLong(r6);
        r4[r5] = r8;
        r5 = r5 + 1;
        goto L_0x02b2;
    L_0x02c0:
        r3 = r0.mAttributes;
        r3 = r3[r7];
        r5 = r0.mExifByteOrder;
        r4 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createULong(r4, r5);
        r3.put(r2, r4);
        goto L_0x0313;
    L_0x02ce:
        r17 = r4;
        r3 = -1;
        r15 = 1;
        r3 = r1.split(r10, r3);
        r4 = r3.length;
        r4 = new int[r4];
        r5 = 0;
    L_0x02da:
        r6 = r3.length;
        if (r5 >= r6) goto L_0x02e8;
    L_0x02dd:
        r6 = r3[r5];
        r6 = java.lang.Integer.parseInt(r6);
        r4[r5] = r6;
        r5 = r5 + 1;
        goto L_0x02da;
    L_0x02e8:
        r3 = r0.mAttributes;
        r3 = r3[r7];
        r5 = r0.mExifByteOrder;
        r4 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createUShort(r4, r5);
        r3.put(r2, r4);
        goto L_0x0313;
    L_0x02f6:
        r17 = r4;
        r15 = 1;
        r3 = r0.mAttributes;
        r3 = r3[r7];
        r4 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createString(r1);
        r3.put(r2, r4);
        goto L_0x0313;
    L_0x0305:
        r17 = r4;
        r15 = 1;
        r3 = r0.mAttributes;
        r3 = r3[r7];
        r4 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createByte(r1);
        r3.put(r2, r4);
    L_0x0313:
        r4 = r17;
        goto L_0x0317;
    L_0x0316:
        r15 = 1;
    L_0x0317:
        r7 = r7 + 1;
        r3 = 2;
        r5 = 1;
        r6 = 0;
        goto L_0x00b3;
    L_0x031e:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.setAttribute(java.lang.String, java.lang.String):void");
    }

    public void resetOrientation() {
        setAttribute(TAG_ORIENTATION, Integer.toString(1));
    }

    public void rotate(int i) {
        if (i % 90 == 0) {
            String str = TAG_ORIENTATION;
            int attributeInt = getAttributeInt(str, 1);
            int i2 = 0;
            if (ROTATION_ORDER.contains(Integer.valueOf(attributeInt))) {
                attributeInt = (ROTATION_ORDER.indexOf(Integer.valueOf(attributeInt)) + (i / 90)) % 4;
                if (attributeInt < 0) {
                    i2 = 4;
                }
                i2 = ((Integer) ROTATION_ORDER.get(attributeInt + i2)).intValue();
            } else if (FLIPPED_ROTATION_ORDER.contains(Integer.valueOf(attributeInt))) {
                attributeInt = (FLIPPED_ROTATION_ORDER.indexOf(Integer.valueOf(attributeInt)) + (i / 90)) % 4;
                if (attributeInt < 0) {
                    i2 = 4;
                }
                i2 = ((Integer) FLIPPED_ROTATION_ORDER.get(attributeInt + i2)).intValue();
            }
            setAttribute(str, Integer.toString(i2));
            return;
        }
        throw new IllegalArgumentException("degree should be a multiple of 90");
    }

    public void flipVertically() {
        int i = 1;
        String str = TAG_ORIENTATION;
        switch (getAttributeInt(str, 1)) {
            case 1:
                i = 4;
                break;
            case 2:
                i = 3;
                break;
            case 3:
                i = 2;
                break;
            case 4:
                break;
            case 5:
                i = 8;
                break;
            case 6:
                i = 7;
                break;
            case 7:
                i = 6;
                break;
            case 8:
                i = 5;
                break;
            default:
                i = 0;
                break;
        }
        setAttribute(str, Integer.toString(i));
    }

    public void flipHorizontally() {
        int i = 1;
        String str = TAG_ORIENTATION;
        switch (getAttributeInt(str, 1)) {
            case 1:
                i = 2;
                break;
            case 2:
                break;
            case 3:
                i = 4;
                break;
            case 4:
                i = 3;
                break;
            case 5:
                i = 6;
                break;
            case 6:
                i = 5;
                break;
            case 7:
                i = 8;
                break;
            case 8:
                i = 7;
                break;
            default:
                i = 0;
                break;
        }
        setAttribute(str, Integer.toString(i));
    }

    public boolean isFlipped() {
        int attributeInt = getAttributeInt(TAG_ORIENTATION, 1);
        return attributeInt == 2 || attributeInt == 7 || attributeInt == 4 || attributeInt == 5;
    }

    public int getRotationDegrees() {
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 8:
                return 270;
            case 6:
            case 7:
                return 90;
            default:
                return 0;
        }
    }

    private boolean updateAttribute(String str, ExifAttribute exifAttribute) {
        boolean z = false;
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            if (this.mAttributes[i].containsKey(str)) {
                this.mAttributes[i].put(str, exifAttribute);
                z = true;
            }
        }
        return z;
    }

    private void removeAttribute(String str) {
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            this.mAttributes[i].remove(str);
        }
    }

    /* JADX WARNING: Missing block: B:15:?, code:
            r4.mIsSupportedFile = false;
     */
    /* JADX WARNING: Missing block: B:18:0x0050, code:
            addDefaultValuesForCompatibility();
     */
    private void loadAttributes(@androidx.annotation.NonNull java.io.InputStream r5) throws java.io.IOException {
        /*
        r4 = this;
        r0 = 0;
        r1 = 0;
    L_0x0002:
        r2 = EXIF_TAGS;	 Catch:{ IOException -> 0x004a }
        r2 = r2.length;	 Catch:{ IOException -> 0x004a }
        if (r1 >= r2) goto L_0x0013;
    L_0x0007:
        r2 = r4.mAttributes;	 Catch:{ IOException -> 0x004a }
        r3 = new java.util.HashMap;	 Catch:{ IOException -> 0x004a }
        r3.<init>();	 Catch:{ IOException -> 0x004a }
        r2[r1] = r3;	 Catch:{ IOException -> 0x004a }
        r1 = r1 + 1;
        goto L_0x0002;
    L_0x0013:
        r1 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x004a }
        r2 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r1.<init>(r5, r2);	 Catch:{ IOException -> 0x004a }
        r5 = r1;
        r5 = (java.io.BufferedInputStream) r5;	 Catch:{ IOException -> 0x004a }
        r5 = r4.getMimeType(r5);	 Catch:{ IOException -> 0x004a }
        r4.mMimeType = r5;	 Catch:{ IOException -> 0x004a }
        r5 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream;	 Catch:{ IOException -> 0x004a }
        r5.<init>(r1);	 Catch:{ IOException -> 0x004a }
        r1 = r4.mMimeType;	 Catch:{ IOException -> 0x004a }
        switch(r1) {
            case 0: goto L_0x003e;
            case 1: goto L_0x003e;
            case 2: goto L_0x003e;
            case 3: goto L_0x003e;
            case 4: goto L_0x003a;
            case 5: goto L_0x003e;
            case 6: goto L_0x003e;
            case 7: goto L_0x0036;
            case 8: goto L_0x003e;
            case 9: goto L_0x0032;
            case 10: goto L_0x002e;
            case 11: goto L_0x003e;
            default: goto L_0x002d;
        };	 Catch:{ IOException -> 0x004a }
    L_0x002d:
        goto L_0x0041;
    L_0x002e:
        r4.getRw2Attributes(r5);	 Catch:{ IOException -> 0x004a }
        goto L_0x0041;
    L_0x0032:
        r4.getRafAttributes(r5);	 Catch:{ IOException -> 0x004a }
        goto L_0x0041;
    L_0x0036:
        r4.getOrfAttributes(r5);	 Catch:{ IOException -> 0x004a }
        goto L_0x0041;
    L_0x003a:
        r4.getJpegAttributes(r5, r0, r0);	 Catch:{ IOException -> 0x004a }
        goto L_0x0041;
    L_0x003e:
        r4.getRawAttributes(r5);	 Catch:{ IOException -> 0x004a }
    L_0x0041:
        r4.setThumbnailData(r5);	 Catch:{ IOException -> 0x004a }
        r5 = 1;
        r4.mIsSupportedFile = r5;	 Catch:{ IOException -> 0x004a }
        goto L_0x004c;
    L_0x0048:
        r5 = move-exception;
        goto L_0x0050;
    L_0x004a:
        r4.mIsSupportedFile = r0;	 Catch:{ all -> 0x0048 }
    L_0x004c:
        r4.addDefaultValuesForCompatibility();
        return;
    L_0x0050:
        r4.addDefaultValuesForCompatibility();
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.loadAttributes(java.io.InputStream):void");
    }

    private void printAttributes() {
        for (int i = 0; i < this.mAttributes.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The size of tag group[");
            stringBuilder.append(i);
            stringBuilder.append("]: ");
            stringBuilder.append(this.mAttributes[i].size());
            String stringBuilder2 = stringBuilder.toString();
            String str = TAG;
            Log.d(str, stringBuilder2);
            for (Entry entry : this.mAttributes[i].entrySet()) {
                ExifAttribute exifAttribute = (ExifAttribute) entry.getValue();
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("tagName: ");
                stringBuilder3.append((String) entry.getKey());
                stringBuilder3.append(", tagType: ");
                stringBuilder3.append(exifAttribute.toString());
                stringBuilder3.append(", tagValue: '");
                stringBuilder3.append(exifAttribute.getStringValue(this.mExifByteOrder));
                stringBuilder3.append("'");
                Log.d(str, stringBuilder3.toString());
            }
        }
    }

    public void saveAttributes() throws IOException {
        Closeable fileInputStream;
        Throwable th;
        if (!this.mIsSupportedFile || this.mMimeType != 4) {
            throw new IOException("ExifInterface only supports saving attributes on JPEG formats.");
        } else if (this.mFilename != null) {
            this.mThumbnailBytes = getThumbnail();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mFilename);
            stringBuilder.append(FileType.TEMP);
            File file = new File(stringBuilder.toString());
            if (new File(this.mFilename).renameTo(file)) {
                Closeable closeable = null;
                try {
                    Closeable fileOutputStream;
                    fileInputStream = new FileInputStream(file);
                    try {
                        fileOutputStream = new FileOutputStream(this.mFilename);
                    } catch (Throwable th2) {
                        th = th2;
                        closeQuietly(fileInputStream);
                        closeQuietly(closeable);
                        file.delete();
                        throw th;
                    }
                    try {
                        saveJpegAttributes(fileInputStream, fileOutputStream);
                        closeQuietly(fileInputStream);
                        closeQuietly(fileOutputStream);
                        file.delete();
                        this.mThumbnailBytes = null;
                        return;
                    } catch (Throwable th3) {
                        Closeable closeable2 = fileOutputStream;
                        th = th3;
                        closeable = closeable2;
                        closeQuietly(fileInputStream);
                        closeQuietly(closeable);
                        file.delete();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    fileInputStream = null;
                    closeQuietly(fileInputStream);
                    closeQuietly(closeable);
                    file.delete();
                    throw th;
                }
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Could not rename to ");
            stringBuilder2.append(file.getAbsolutePath());
            throw new IOException(stringBuilder2.toString());
        } else {
            throw new IOException("ExifInterface does not support saving attributes for the current input.");
        }
    }

    public boolean hasThumbnail() {
        return this.mHasThumbnail;
    }

    @Nullable
    public byte[] getThumbnail() {
        int i = this.mThumbnailCompression;
        if (i == 6 || i == 7) {
            return getThumbnailBytes();
        }
        return null;
    }

    @Nullable
    public byte[] getThumbnailBytes() {
        Throwable e;
        Throwable th;
        String str = TAG;
        if (!this.mHasThumbnail) {
            return null;
        }
        byte[] bArr = this.mThumbnailBytes;
        if (bArr != null) {
            return bArr;
        }
        Closeable closeable;
        try {
            if (this.mAssetInputStream != null) {
                closeable = this.mAssetInputStream;
                try {
                    if (closeable.markSupported()) {
                        closeable.reset();
                    } else {
                        Log.d(str, "Cannot read thumbnail from inputstream without mark/reset support");
                        closeQuietly(closeable);
                        return null;
                    }
                } catch (IOException e2) {
                    e = e2;
                    try {
                        Log.d(str, "Encountered exception while getting thumbnail", e);
                        closeQuietly(closeable);
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        closeQuietly(closeable);
                        throw th;
                    }
                }
            }
            closeable = this.mFilename != null ? new FileInputStream(this.mFilename) : null;
            if (closeable != null) {
                String str2 = "Corrupted image";
                if (closeable.skip((long) this.mThumbnailOffset) == ((long) this.mThumbnailOffset)) {
                    byte[] bArr2 = new byte[this.mThumbnailLength];
                    if (closeable.read(bArr2) == this.mThumbnailLength) {
                        this.mThumbnailBytes = bArr2;
                        closeQuietly(closeable);
                        return bArr2;
                    }
                    throw new IOException(str2);
                }
                throw new IOException(str2);
            }
            throw new FileNotFoundException();
        } catch (IOException e3) {
            e = e3;
            closeable = null;
            Log.d(str, "Encountered exception while getting thumbnail", e);
            closeQuietly(closeable);
            return null;
        } catch (Throwable th3) {
            th = th3;
            closeable = null;
            closeQuietly(closeable);
            throw th;
        }
    }

    @Nullable
    public Bitmap getThumbnailBitmap() {
        if (!this.mHasThumbnail) {
            return null;
        }
        if (this.mThumbnailBytes == null) {
            this.mThumbnailBytes = getThumbnailBytes();
        }
        int i = this.mThumbnailCompression;
        if (i == 6 || i == 7) {
            return BitmapFactory.decodeByteArray(this.mThumbnailBytes, 0, this.mThumbnailLength);
        }
        if (i == 1) {
            int[] iArr = new int[(this.mThumbnailBytes.length / 3)];
            for (int i2 = 0; i2 < iArr.length; i2++) {
                byte[] bArr = this.mThumbnailBytes;
                int i3 = i2 * 3;
                iArr[i2] = (((bArr[i3] << 16) + 0) + (bArr[i3 + 1] << 8)) + bArr[i3 + 2];
            }
            ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[4].get(TAG_IMAGE_LENGTH);
            ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[4].get(TAG_IMAGE_WIDTH);
            if (!(exifAttribute == null || exifAttribute2 == null)) {
                return Bitmap.createBitmap(iArr, exifAttribute2.getIntValue(this.mExifByteOrder), exifAttribute.getIntValue(this.mExifByteOrder), Config.ARGB_8888);
            }
        }
        return null;
    }

    public boolean isThumbnailCompressed() {
        int i = this.mThumbnailCompression;
        return i == 6 || i == 7;
    }

    @Nullable
    public long[] getThumbnailRange() {
        if (!this.mHasThumbnail) {
            return null;
        }
        return new long[]{(long) this.mThumbnailOffset, (long) this.mThumbnailLength};
    }

    @Deprecated
    public boolean getLatLong(float[] fArr) {
        double[] latLong = getLatLong();
        if (latLong == null) {
            return false;
        }
        fArr[0] = (float) latLong[0];
        fArr[1] = (float) latLong[1];
        return true;
    }

    @Nullable
    public double[] getLatLong() {
        String attribute = getAttribute(TAG_GPS_LATITUDE);
        String attribute2 = getAttribute(TAG_GPS_LATITUDE_REF);
        String attribute3 = getAttribute(TAG_GPS_LONGITUDE);
        String attribute4 = getAttribute(TAG_GPS_LONGITUDE_REF);
        if (!(attribute == null || attribute2 == null || attribute3 == null || attribute4 == null)) {
            try {
                double convertRationalLatLonToDouble = convertRationalLatLonToDouble(attribute, attribute2);
                double convertRationalLatLonToDouble2 = convertRationalLatLonToDouble(attribute3, attribute4);
                return new double[]{convertRationalLatLonToDouble, convertRationalLatLonToDouble2};
            } catch (IllegalArgumentException unused) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Latitude/longitude values are not parseable. ");
                stringBuilder.append(String.format("latValue=%s, latRef=%s, lngValue=%s, lngRef=%s", new Object[]{attribute, attribute2, attribute3, attribute4}));
                Log.w(TAG, stringBuilder.toString());
            }
        }
        return null;
    }

    public void setGpsInfo(Location location) {
        if (location != null) {
            setAttribute(TAG_GPS_PROCESSING_METHOD, location.getProvider());
            setLatLong(location.getLatitude(), location.getLongitude());
            setAltitude(location.getAltitude());
            setAttribute(TAG_GPS_SPEED_REF, "K");
            setAttribute(TAG_GPS_SPEED, new Rational((double) ((location.getSpeed() * ((float) TimeUnit.HOURS.toSeconds(1))) / 1000.0f)).toString());
            String[] split = sFormatter.format(new Date(location.getTime())).split("\\s+", -1);
            setAttribute(TAG_GPS_DATESTAMP, split[0]);
            setAttribute(TAG_GPS_TIMESTAMP, split[1]);
        }
    }

    public void setLatLong(double d, double d2) {
        String str = " is not valid.";
        if (d < -90.0d || d > 90.0d || Double.isNaN(d)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Latitude value ");
            stringBuilder.append(d);
            stringBuilder.append(str);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (d2 < -180.0d || d2 > 180.0d || Double.isNaN(d2)) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Longitude value ");
            stringBuilder2.append(d2);
            stringBuilder2.append(str);
            throw new IllegalArgumentException(stringBuilder2.toString());
        } else {
            setAttribute(TAG_GPS_LATITUDE_REF, d >= 0.0d ? "N" : LATITUDE_SOUTH);
            setAttribute(TAG_GPS_LATITUDE, convertDecimalDegree(Math.abs(d)));
            setAttribute(TAG_GPS_LONGITUDE_REF, d2 >= 0.0d ? LONGITUDE_EAST : LONGITUDE_WEST);
            setAttribute(TAG_GPS_LONGITUDE, convertDecimalDegree(Math.abs(d2)));
        }
    }

    public double getAltitude(double d) {
        double attributeDouble = getAttributeDouble(TAG_GPS_ALTITUDE, -1.0d);
        int attributeInt = getAttributeInt(TAG_GPS_ALTITUDE_REF, -1);
        if (attributeDouble < 0.0d || attributeInt < 0) {
            return d;
        }
        int i = 1;
        if (attributeInt == 1) {
            i = -1;
        }
        return attributeDouble * ((double) i);
    }

    public void setAltitude(double d) {
        String str = d >= 0.0d ? "0" : "1";
        setAttribute(TAG_GPS_ALTITUDE, new Rational(Math.abs(d)).toString());
        setAttribute(TAG_GPS_ALTITUDE_REF, str);
    }

    @RestrictTo({Scope.LIBRARY})
    public void setDateTime(long j) {
        long j2 = j % 1000;
        setAttribute(TAG_DATETIME, sFormatter.format(new Date(j)));
        setAttribute(TAG_SUBSEC_TIME, Long.toString(j2));
    }

    @RestrictTo({Scope.LIBRARY})
    public long getDateTime() {
        Object attribute = getAttribute(TAG_DATETIME);
        if (attribute != null && sNonZeroTimePattern.matcher(attribute).matches()) {
            try {
                Date parse = sFormatter.parse(attribute, new ParsePosition(0));
                if (parse == null) {
                    return -1;
                }
                long time = parse.getTime();
                String attribute2 = getAttribute(TAG_SUBSEC_TIME);
                if (attribute2 != null) {
                    try {
                        long parseLong = Long.parseLong(attribute2);
                        while (parseLong > 1000) {
                            parseLong /= 10;
                        }
                        time += parseLong;
                    } catch (NumberFormatException unused) {
                        return time;
                    }
                }
            } catch (IllegalArgumentException unused2) {
                return -1;
            }
        }
    }

    @RestrictTo({Scope.LIBRARY})
    public long getGpsDateTime() {
        Object attribute = getAttribute(TAG_GPS_DATESTAMP);
        Object attribute2 = getAttribute(TAG_GPS_TIMESTAMP);
        if (!(attribute == null || attribute2 == null)) {
            if (sNonZeroTimePattern.matcher(attribute).matches() || sNonZeroTimePattern.matcher(attribute2).matches()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(attribute);
                stringBuilder.append(' ');
                stringBuilder.append(attribute2);
                try {
                    Date parse = sFormatter.parse(stringBuilder.toString(), new ParsePosition(0));
                    if (parse == null) {
                        return -1;
                    }
                    return parse.getTime();
                } catch (IllegalArgumentException unused) {
                    return -1;
                }
            }
            return -1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0093 A:{ExcHandler: java.lang.NumberFormatException (unused java.lang.NumberFormatException), Splitter: B:1:0x0002} */
    /* JADX WARNING: Missing block: B:16:0x0098, code:
            throw new java.lang.IllegalArgumentException();
     */
    private static double convertRationalLatLonToDouble(java.lang.String r11, java.lang.String r12) {
        /*
        r0 = "/";
        r1 = ",";
        r2 = -1;
        r11 = r11.split(r1, r2);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r1 = 0;
        r3 = r11[r1];	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r3 = r3.split(r0, r2);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r4 = r3[r1];	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r4 = r4.trim();	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r4 = java.lang.Double.parseDouble(r4);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r6 = 1;
        r3 = r3[r6];	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r3 = r3.trim();	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r7 = java.lang.Double.parseDouble(r3);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r4 = r4 / r7;
        r3 = r11[r6];	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r3 = r3.split(r0, r2);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r7 = r3[r1];	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r7 = r7.trim();	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r7 = java.lang.Double.parseDouble(r7);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r3 = r3[r6];	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r3 = r3.trim();	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r9 = java.lang.Double.parseDouble(r3);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r7 = r7 / r9;
        r3 = 2;
        r11 = r11[r3];	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r11 = r11.split(r0, r2);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r0 = r11[r1];	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r0 = r0.trim();	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r0 = java.lang.Double.parseDouble(r0);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r11 = r11[r6];	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r11 = r11.trim();	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r2 = java.lang.Double.parseDouble(r11);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r0 = r0 / r2;
        r2 = 4633641066610819072; // 0x404e000000000000 float:0.0 double:60.0;
        r7 = r7 / r2;
        r4 = r4 + r7;
        r2 = 4660134898793709568; // 0x40ac200000000000 float:0.0 double:3600.0;
        r0 = r0 / r2;
        r4 = r4 + r0;
        r11 = "S";
        r11 = r12.equals(r11);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        if (r11 != 0) goto L_0x0091;
    L_0x0070:
        r11 = "W";
        r11 = r12.equals(r11);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        if (r11 == 0) goto L_0x0079;
    L_0x0078:
        goto L_0x0091;
    L_0x0079:
        r11 = "N";
        r11 = r12.equals(r11);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        if (r11 != 0) goto L_0x0090;
    L_0x0081:
        r11 = "E";
        r11 = r12.equals(r11);	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        if (r11 == 0) goto L_0x008a;
    L_0x0089:
        goto L_0x0090;
    L_0x008a:
        r11 = new java.lang.IllegalArgumentException;	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        r11.<init>();	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
        throw r11;	 Catch:{ NumberFormatException -> 0x0093, NumberFormatException -> 0x0093 }
    L_0x0090:
        return r4;
    L_0x0091:
        r11 = -r4;
        return r11;
    L_0x0093:
        r11 = new java.lang.IllegalArgumentException;
        r11.<init>();
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.convertRationalLatLonToDouble(java.lang.String, java.lang.String):double");
    }

    private String convertDecimalDegree(double d) {
        long j = (long) d;
        d -= (double) j;
        long j2 = (long) (d * 60.0d);
        long round = Math.round(((d - (((double) j2) / 60.0d)) * 3600.0d) * 1.0E7d);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(j);
        String str = "/1,";
        stringBuilder.append(str);
        stringBuilder.append(j2);
        stringBuilder.append(str);
        stringBuilder.append(round);
        stringBuilder.append("/10000000");
        return stringBuilder.toString();
    }

    private int getMimeType(BufferedInputStream bufferedInputStream) throws IOException {
        bufferedInputStream.mark(SIGNATURE_CHECK_SIZE);
        byte[] bArr = new byte[SIGNATURE_CHECK_SIZE];
        bufferedInputStream.read(bArr);
        bufferedInputStream.reset();
        if (isJpegFormat(bArr)) {
            return 4;
        }
        if (isRafFormat(bArr)) {
            return 9;
        }
        if (isOrfFormat(bArr)) {
            return 7;
        }
        return isRw2Format(bArr) ? 10 : 0;
    }

    private static boolean isJpegFormat(byte[] bArr) throws IOException {
        int i = 0;
        while (true) {
            byte[] bArr2 = JPEG_SIGNATURE;
            if (i >= bArr2.length) {
                return true;
            }
            if (bArr[i] != bArr2[i]) {
                return false;
            }
            i++;
        }
    }

    private boolean isRafFormat(byte[] bArr) throws IOException {
        byte[] bytes = RAF_SIGNATURE.getBytes(Charset.defaultCharset());
        for (int i = 0; i < bytes.length; i++) {
            if (bArr[i] != bytes[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isOrfFormat(byte[] bArr) throws IOException {
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
        this.mExifByteOrder = readByteOrder(byteOrderedDataInputStream);
        byteOrderedDataInputStream.setByteOrder(this.mExifByteOrder);
        short readShort = byteOrderedDataInputStream.readShort();
        byteOrderedDataInputStream.close();
        return readShort == ORF_SIGNATURE_1 || readShort == ORF_SIGNATURE_2;
    }

    private boolean isRw2Format(byte[] bArr) throws IOException {
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
        this.mExifByteOrder = readByteOrder(byteOrderedDataInputStream);
        byteOrderedDataInputStream.setByteOrder(this.mExifByteOrder);
        short readShort = byteOrderedDataInputStream.readShort();
        byteOrderedDataInputStream.close();
        return readShort == RW2_SIGNATURE;
    }

    /* JADX WARNING: Removed duplicated region for block: B:71:0x0088 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00f7 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00f7 A:{SYNTHETIC} */
    private void getJpegAttributes(androidx.exifinterface.media.ExifInterface.ByteOrderedDataInputStream r10, int r11, int r12) throws java.io.IOException {
        /*
        r9 = this;
        r0 = java.nio.ByteOrder.BIG_ENDIAN;
        r10.setByteOrder(r0);
        r0 = (long) r11;
        r10.seek(r0);
        r0 = r10.readByte();
        r1 = "Invalid marker: ";
        r2 = -1;
        if (r0 != r2) goto L_0x0153;
    L_0x0012:
        r3 = 1;
        r11 = r11 + r3;
        r4 = r10.readByte();
        r5 = -40;
        if (r4 != r5) goto L_0x0138;
    L_0x001c:
        r11 = r11 + r3;
    L_0x001d:
        r0 = r10.readByte();
        if (r0 != r2) goto L_0x011b;
    L_0x0023:
        r11 = r11 + r3;
        r0 = r10.readByte();
        r11 = r11 + r3;
        r1 = -39;
        if (r0 == r1) goto L_0x0115;
    L_0x002d:
        r1 = -38;
        if (r0 != r1) goto L_0x0033;
    L_0x0031:
        goto L_0x0115;
    L_0x0033:
        r1 = r10.readUnsignedShort();
        r1 = r1 + -2;
        r11 = r11 + 2;
        r4 = "Invalid length";
        if (r1 < 0) goto L_0x010f;
    L_0x003f:
        r5 = -31;
        r6 = 0;
        r7 = "Invalid exif";
        if (r0 == r5) goto L_0x00ba;
    L_0x0046:
        r5 = -2;
        if (r0 == r5) goto L_0x0090;
    L_0x0049:
        switch(r0) {
            case -64: goto L_0x0057;
            case -63: goto L_0x0057;
            case -62: goto L_0x0057;
            case -61: goto L_0x0057;
            default: goto L_0x004c;
        };
    L_0x004c:
        switch(r0) {
            case -59: goto L_0x0057;
            case -58: goto L_0x0057;
            case -57: goto L_0x0057;
            default: goto L_0x004f;
        };
    L_0x004f:
        switch(r0) {
            case -55: goto L_0x0057;
            case -54: goto L_0x0057;
            case -53: goto L_0x0057;
            default: goto L_0x0052;
        };
    L_0x0052:
        switch(r0) {
            case -51: goto L_0x0057;
            case -50: goto L_0x0057;
            case -49: goto L_0x0057;
            default: goto L_0x0055;
        };
    L_0x0055:
        goto L_0x00e4;
    L_0x0057:
        r0 = r10.skipBytes(r3);
        if (r0 != r3) goto L_0x0088;
    L_0x005d:
        r0 = r9.mAttributes;
        r0 = r0[r12];
        r5 = r10.readUnsignedShort();
        r5 = (long) r5;
        r7 = r9.mExifByteOrder;
        r5 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createULong(r5, r7);
        r6 = "ImageLength";
        r0.put(r6, r5);
        r0 = r9.mAttributes;
        r0 = r0[r12];
        r5 = r10.readUnsignedShort();
        r5 = (long) r5;
        r7 = r9.mExifByteOrder;
        r5 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createULong(r5, r7);
        r6 = "ImageWidth";
        r0.put(r6, r5);
        r1 = r1 + -5;
        goto L_0x00e4;
    L_0x0088:
        r10 = new java.io.IOException;
        r11 = "Invalid SOFx";
        r10.<init>(r11);
        throw r10;
    L_0x0090:
        r0 = new byte[r1];
        r5 = r10.read(r0);
        if (r5 != r1) goto L_0x00b4;
    L_0x0098:
        r1 = "UserComment";
        r5 = r9.getAttribute(r1);
        if (r5 != 0) goto L_0x00b2;
    L_0x00a0:
        r5 = r9.mAttributes;
        r5 = r5[r3];
        r7 = new java.lang.String;
        r8 = ASCII;
        r7.<init>(r0, r8);
        r0 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createString(r7);
        r5.put(r1, r0);
    L_0x00b2:
        r1 = 0;
        goto L_0x00e4;
    L_0x00b4:
        r10 = new java.io.IOException;
        r10.<init>(r7);
        throw r10;
    L_0x00ba:
        r0 = 6;
        if (r1 >= r0) goto L_0x00be;
    L_0x00bd:
        goto L_0x00e4;
    L_0x00be:
        r5 = new byte[r0];
        r8 = r10.read(r5);
        if (r8 != r0) goto L_0x0109;
    L_0x00c6:
        r11 = r11 + 6;
        r1 = r1 + -6;
        r0 = IDENTIFIER_EXIF_APP1;
        r0 = java.util.Arrays.equals(r5, r0);
        if (r0 != 0) goto L_0x00d3;
    L_0x00d2:
        goto L_0x00e4;
    L_0x00d3:
        if (r1 <= 0) goto L_0x0103;
    L_0x00d5:
        r9.mExifOffset = r11;
        r0 = new byte[r1];
        r5 = r10.read(r0);
        if (r5 != r1) goto L_0x00fd;
    L_0x00df:
        r11 = r11 + r1;
        r9.readExifSegment(r0, r12);
        goto L_0x00b2;
    L_0x00e4:
        if (r1 < 0) goto L_0x00f7;
    L_0x00e6:
        r0 = r10.skipBytes(r1);
        if (r0 != r1) goto L_0x00ef;
    L_0x00ec:
        r11 = r11 + r1;
        goto L_0x001d;
    L_0x00ef:
        r10 = new java.io.IOException;
        r11 = "Invalid JPEG segment";
        r10.<init>(r11);
        throw r10;
    L_0x00f7:
        r10 = new java.io.IOException;
        r10.<init>(r4);
        throw r10;
    L_0x00fd:
        r10 = new java.io.IOException;
        r10.<init>(r7);
        throw r10;
    L_0x0103:
        r10 = new java.io.IOException;
        r10.<init>(r7);
        throw r10;
    L_0x0109:
        r10 = new java.io.IOException;
        r10.<init>(r7);
        throw r10;
    L_0x010f:
        r10 = new java.io.IOException;
        r10.<init>(r4);
        throw r10;
    L_0x0115:
        r11 = r9.mExifByteOrder;
        r10.setByteOrder(r11);
        return;
    L_0x011b:
        r10 = new java.io.IOException;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "Invalid marker:";
        r11.append(r12);
        r12 = r0 & 255;
        r12 = java.lang.Integer.toHexString(r12);
        r11.append(r12);
        r11 = r11.toString();
        r10.<init>(r11);
        throw r10;
    L_0x0138:
        r10 = new java.io.IOException;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r11.append(r1);
        r12 = r0 & 255;
        r12 = java.lang.Integer.toHexString(r12);
        r11.append(r12);
        r11 = r11.toString();
        r10.<init>(r11);
        throw r10;
    L_0x0153:
        r10 = new java.io.IOException;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r11.append(r1);
        r12 = r0 & 255;
        r12 = java.lang.Integer.toHexString(r12);
        r11.append(r12);
        r11 = r11.toString();
        r10.<init>(r11);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.getJpegAttributes(androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream, int, int):void");
    }

    private void getRawAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        parseTiffHeaders(byteOrderedDataInputStream, byteOrderedDataInputStream.available());
        readImageFileDirectory(byteOrderedDataInputStream, 0);
        updateImageSizeValues(byteOrderedDataInputStream, 0);
        updateImageSizeValues(byteOrderedDataInputStream, 5);
        updateImageSizeValues(byteOrderedDataInputStream, 4);
        validateImages(byteOrderedDataInputStream);
        if (this.mMimeType == 8) {
            ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_MAKER_NOTE);
            if (exifAttribute != null) {
                ByteOrderedDataInputStream byteOrderedDataInputStream2 = new ByteOrderedDataInputStream(exifAttribute.bytes);
                byteOrderedDataInputStream2.setByteOrder(this.mExifByteOrder);
                byteOrderedDataInputStream2.seek(6);
                readImageFileDirectory(byteOrderedDataInputStream2, 9);
                HashMap hashMap = this.mAttributes[9];
                String str = TAG_COLOR_SPACE;
                exifAttribute = (ExifAttribute) hashMap.get(str);
                if (exifAttribute != null) {
                    this.mAttributes[1].put(str, exifAttribute);
                }
            }
        }
    }

    private void getRafAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        byteOrderedDataInputStream.skipBytes(84);
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[4];
        byteOrderedDataInputStream.read(bArr);
        byteOrderedDataInputStream.skipBytes(4);
        byteOrderedDataInputStream.read(bArr2);
        int i = ByteBuffer.wrap(bArr).getInt();
        int i2 = ByteBuffer.wrap(bArr2).getInt();
        getJpegAttributes(byteOrderedDataInputStream, i, 5);
        byteOrderedDataInputStream.seek((long) i2);
        byteOrderedDataInputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        i = byteOrderedDataInputStream.readInt();
        for (int i3 = 0; i3 < i; i3++) {
            int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
            int readUnsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
            if (readUnsignedShort == TAG_RAF_IMAGE_SIZE.number) {
                i = byteOrderedDataInputStream.readShort();
                int readShort = byteOrderedDataInputStream.readShort();
                ExifAttribute createUShort = ExifAttribute.createUShort(i, this.mExifByteOrder);
                ExifAttribute createUShort2 = ExifAttribute.createUShort(readShort, this.mExifByteOrder);
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, createUShort);
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, createUShort2);
                return;
            }
            byteOrderedDataInputStream.skipBytes(readUnsignedShort2);
        }
    }

    private void getOrfAttributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        getRawAttributes(byteOrderedDataInputStream);
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_MAKER_NOTE);
        if (exifAttribute != null) {
            ByteOrderedDataInputStream byteOrderedDataInputStream2 = new ByteOrderedDataInputStream(exifAttribute.bytes);
            byteOrderedDataInputStream2.setByteOrder(this.mExifByteOrder);
            byte[] bArr = new byte[ORF_MAKER_NOTE_HEADER_1.length];
            byteOrderedDataInputStream2.readFully(bArr);
            byteOrderedDataInputStream2.seek(0);
            byte[] bArr2 = new byte[ORF_MAKER_NOTE_HEADER_2.length];
            byteOrderedDataInputStream2.readFully(bArr2);
            if (Arrays.equals(bArr, ORF_MAKER_NOTE_HEADER_1)) {
                byteOrderedDataInputStream2.seek(8);
            } else if (Arrays.equals(bArr2, ORF_MAKER_NOTE_HEADER_2)) {
                byteOrderedDataInputStream2.seek(12);
            }
            readImageFileDirectory(byteOrderedDataInputStream2, 6);
            exifAttribute = (ExifAttribute) this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_START);
            ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_LENGTH);
            if (!(exifAttribute == null || exifAttribute2 == null)) {
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT, exifAttribute);
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, exifAttribute2);
            }
            exifAttribute = (ExifAttribute) this.mAttributes[8].get(TAG_ORF_ASPECT_FRAME);
            if (exifAttribute != null) {
                int[] iArr = (int[]) exifAttribute.getValue(this.mExifByteOrder);
                if (iArr == null || iArr.length != 4) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid aspect frame values. frame=");
                    stringBuilder.append(Arrays.toString(iArr));
                    Log.w(TAG, stringBuilder.toString());
                } else if (iArr[2] > iArr[0] && iArr[3] > iArr[1]) {
                    int i = (iArr[2] - iArr[0]) + 1;
                    int i2 = (iArr[3] - iArr[1]) + 1;
                    if (i < i2) {
                        i += i2;
                        i2 = i - i2;
                        i -= i2;
                    }
                    exifAttribute = ExifAttribute.createUShort(i, this.mExifByteOrder);
                    ExifAttribute createUShort = ExifAttribute.createUShort(i2, this.mExifByteOrder);
                    this.mAttributes[0].put(TAG_IMAGE_WIDTH, exifAttribute);
                    this.mAttributes[0].put(TAG_IMAGE_LENGTH, createUShort);
                }
            }
        }
    }

    private void getRw2Attributes(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        getRawAttributes(byteOrderedDataInputStream);
        if (((ExifAttribute) this.mAttributes[0].get(TAG_RW2_JPG_FROM_RAW)) != null) {
            getJpegAttributes(byteOrderedDataInputStream, this.mRw2JpgFromRawOffset, 5);
        }
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[0].get(TAG_RW2_ISO);
        HashMap hashMap = this.mAttributes[1];
        String str = TAG_PHOTOGRAPHIC_SENSITIVITY;
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(str);
        if (exifAttribute != null && exifAttribute2 == null) {
            this.mAttributes[1].put(str, exifAttribute);
        }
    }

    private void saveJpegAttributes(InputStream inputStream, OutputStream outputStream) throws IOException {
        InputStream dataInputStream = new DataInputStream(inputStream);
        OutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.BIG_ENDIAN);
        String str = "Invalid marker";
        if (dataInputStream.readByte() == (byte) -1) {
            byteOrderedDataOutputStream.writeByte(-1);
            if (dataInputStream.readByte() == MARKER_SOI) {
                byteOrderedDataOutputStream.writeByte(-40);
                byteOrderedDataOutputStream.writeByte(-1);
                byteOrderedDataOutputStream.writeByte(-31);
                writeExifSegment(byteOrderedDataOutputStream, 6);
                byte[] bArr = new byte[4096];
                while (dataInputStream.readByte() == (byte) -1) {
                    byte readByte = dataInputStream.readByte();
                    if (readByte == MARKER_EOI || readByte == MARKER_SOS) {
                        byteOrderedDataOutputStream.writeByte(-1);
                        byteOrderedDataOutputStream.writeByte(readByte);
                        copy(dataInputStream, byteOrderedDataOutputStream);
                        return;
                    }
                    String str2 = "Invalid length";
                    int readUnsignedShort;
                    if (readByte != MARKER_APP1) {
                        byteOrderedDataOutputStream.writeByte(-1);
                        byteOrderedDataOutputStream.writeByte(readByte);
                        readUnsignedShort = dataInputStream.readUnsignedShort();
                        byteOrderedDataOutputStream.writeUnsignedShort(readUnsignedShort);
                        readUnsignedShort -= 2;
                        if (readUnsignedShort >= 0) {
                            while (readUnsignedShort > 0) {
                                int read = dataInputStream.read(bArr, 0, Math.min(readUnsignedShort, bArr.length));
                                if (read < 0) {
                                    break;
                                }
                                byteOrderedDataOutputStream.write(bArr, 0, read);
                                readUnsignedShort -= read;
                            }
                        } else {
                            throw new IOException(str2);
                        }
                    }
                    int readUnsignedShort2 = dataInputStream.readUnsignedShort() - 2;
                    if (readUnsignedShort2 >= 0) {
                        byte[] bArr2 = new byte[6];
                        if (readUnsignedShort2 >= 6) {
                            if (dataInputStream.read(bArr2) != 6) {
                                throw new IOException("Invalid exif");
                            } else if (Arrays.equals(bArr2, IDENTIFIER_EXIF_APP1)) {
                                readUnsignedShort2 -= 6;
                                if (dataInputStream.skipBytes(readUnsignedShort2) != readUnsignedShort2) {
                                    throw new IOException(str2);
                                }
                            }
                        }
                        byteOrderedDataOutputStream.writeByte(-1);
                        byteOrderedDataOutputStream.writeByte(readByte);
                        byteOrderedDataOutputStream.writeUnsignedShort(readUnsignedShort2 + 2);
                        if (readUnsignedShort2 >= 6) {
                            readUnsignedShort2 -= 6;
                            byteOrderedDataOutputStream.write(bArr2);
                        }
                        while (readUnsignedShort2 > 0) {
                            readUnsignedShort = dataInputStream.read(bArr, 0, Math.min(readUnsignedShort2, bArr.length));
                            if (readUnsignedShort < 0) {
                                break;
                            }
                            byteOrderedDataOutputStream.write(bArr, 0, readUnsignedShort);
                            readUnsignedShort2 -= readUnsignedShort;
                        }
                    } else {
                        throw new IOException(str2);
                    }
                }
                throw new IOException(str);
            }
            throw new IOException(str);
        }
        throw new IOException(str);
    }

    private void readExifSegment(byte[] bArr, int i) throws IOException {
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
        parseTiffHeaders(byteOrderedDataInputStream, bArr.length);
        readImageFileDirectory(byteOrderedDataInputStream, i);
    }

    private void addDefaultValuesForCompatibility() {
        String attribute = getAttribute(TAG_DATETIME_ORIGINAL);
        if (attribute != null) {
            String str = TAG_DATETIME;
            if (getAttribute(str) == null) {
                this.mAttributes[0].put(str, ExifAttribute.createString(attribute));
            }
        }
        attribute = TAG_IMAGE_WIDTH;
        if (getAttribute(attribute) == null) {
            this.mAttributes[0].put(attribute, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        attribute = TAG_IMAGE_LENGTH;
        if (getAttribute(attribute) == null) {
            this.mAttributes[0].put(attribute, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        attribute = TAG_ORIENTATION;
        if (getAttribute(attribute) == null) {
            this.mAttributes[0].put(attribute, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        attribute = TAG_LIGHT_SOURCE;
        if (getAttribute(attribute) == null) {
            this.mAttributes[1].put(attribute, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
    }

    private ByteOrder readByteOrder(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        short readShort = byteOrderedDataInputStream.readShort();
        if (readShort == BYTE_ALIGN_II) {
            return ByteOrder.LITTLE_ENDIAN;
        }
        if (readShort == BYTE_ALIGN_MM) {
            return ByteOrder.BIG_ENDIAN;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid byte order: ");
        stringBuilder.append(Integer.toHexString(readShort));
        throw new IOException(stringBuilder.toString());
    }

    private void parseTiffHeaders(ByteOrderedDataInputStream byteOrderedDataInputStream, int i) throws IOException {
        this.mExifByteOrder = readByteOrder(byteOrderedDataInputStream);
        byteOrderedDataInputStream.setByteOrder(this.mExifByteOrder);
        int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
        int i2 = this.mMimeType;
        StringBuilder stringBuilder;
        if (i2 == 7 || i2 == 10 || readUnsignedShort == 42) {
            readUnsignedShort = byteOrderedDataInputStream.readInt();
            if (readUnsignedShort < 8 || readUnsignedShort >= i) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid first Ifd offset: ");
                stringBuilder.append(readUnsignedShort);
                throw new IOException(stringBuilder.toString());
            }
            readUnsignedShort -= 8;
            if (readUnsignedShort > 0 && byteOrderedDataInputStream.skipBytes(readUnsignedShort) != readUnsignedShort) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't jump to first Ifd: ");
                stringBuilder.append(readUnsignedShort);
                throw new IOException(stringBuilder.toString());
            }
            return;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid start code: ");
        stringBuilder.append(Integer.toHexString(readUnsignedShort));
        throw new IOException(stringBuilder.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00f5  */
    /* JADX WARNING: Missing block: B:87:0x027d, code:
            if (TAG_MODEL.equals(r6.name) != false) goto L_0x027f;
     */
    /* JADX WARNING: Missing block: B:89:0x028b, code:
            if (r5.getStringValue(r0.mExifByteOrder).contains(PEF_SIGNATURE) == false) goto L_0x028d;
     */
    /* JADX WARNING: Missing block: B:91:0x0293, code:
            if (r8.equals(r6.name) == false) goto L_0x02a2;
     */
    /* JADX WARNING: Missing block: B:93:0x029e, code:
            if (r5.getIntValue(r0.mExifByteOrder) != 65535) goto L_0x02a2;
     */
    /* JADX WARNING: Missing block: B:94:0x02a0, code:
            r0.mMimeType = 8;
     */
    /* JADX WARNING: Missing block: B:96:0x02a9, code:
            if (((long) r24.peek()) == r13) goto L_0x02ae;
     */
    /* JADX WARNING: Missing block: B:97:0x02ab, code:
            r1.seek(r13);
     */
    private void readImageFileDirectory(androidx.exifinterface.media.ExifInterface.ByteOrderedDataInputStream r24, int r25) throws java.io.IOException {
        /*
        r23 = this;
        r0 = r23;
        r1 = r24;
        r2 = r25;
        r3 = r0.mAttributesOffsets;
        r4 = r1.mPosition;
        r4 = java.lang.Integer.valueOf(r4);
        r3.add(r4);
        r3 = r1.mPosition;
        r3 = r3 + 2;
        r4 = r1.mLength;
        if (r3 <= r4) goto L_0x001a;
    L_0x0019:
        return;
    L_0x001a:
        r3 = r24.readShort();
        r4 = r1.mPosition;
        r5 = r3 * 12;
        r4 = r4 + r5;
        r5 = r1.mLength;
        if (r4 > r5) goto L_0x0326;
    L_0x0027:
        if (r3 > 0) goto L_0x002b;
    L_0x0029:
        goto L_0x0326;
    L_0x002b:
        r5 = 0;
    L_0x002c:
        r9 = "ExifInterface";
        if (r5 >= r3) goto L_0x02b7;
    L_0x0030:
        r10 = r24.readUnsignedShort();
        r11 = r24.readUnsignedShort();
        r12 = r24.readInt();
        r13 = r24.peek();
        r13 = (long) r13;
        r15 = 4;
        r13 = r13 + r15;
        r17 = sExifTagMapsForReading;
        r4 = r17[r2];
        r8 = java.lang.Integer.valueOf(r10);
        r4 = r4.get(r8);
        r4 = (androidx.exifinterface.media.ExifInterface.ExifTag) r4;
        r8 = 7;
        if (r4 != 0) goto L_0x006b;
    L_0x0055:
        r15 = new java.lang.StringBuilder;
        r15.<init>();
        r6 = "Skip the tag entry since tag number is not defined: ";
        r15.append(r6);
        r15.append(r10);
        r6 = r15.toString();
        android.util.Log.w(r9, r6);
        goto L_0x00e7;
    L_0x006b:
        if (r11 <= 0) goto L_0x00d3;
    L_0x006d:
        r6 = IFD_FORMAT_BYTES_PER_FORMAT;
        r6 = r6.length;
        if (r11 < r6) goto L_0x0073;
    L_0x0072:
        goto L_0x00d3;
    L_0x0073:
        r6 = r4.isFormatCompatible(r11);
        if (r6 != 0) goto L_0x009c;
    L_0x0079:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Skip the tag entry since data format (";
        r6.append(r7);
        r7 = IFD_FORMAT_NAMES;
        r7 = r7[r11];
        r6.append(r7);
        r7 = ") is unexpected for tag: ";
        r6.append(r7);
        r7 = r4.name;
        r6.append(r7);
        r6 = r6.toString();
        android.util.Log.w(r9, r6);
        goto L_0x00e7;
    L_0x009c:
        if (r11 != r8) goto L_0x00a0;
    L_0x009e:
        r11 = r4.primaryFormat;
    L_0x00a0:
        r6 = (long) r12;
        r15 = IFD_FORMAT_BYTES_PER_FORMAT;
        r15 = r15[r11];
        r16 = r9;
        r8 = (long) r15;
        r6 = r6 * r8;
        r8 = 0;
        r15 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r15 < 0) goto L_0x00bc;
    L_0x00b0:
        r8 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r15 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r15 <= 0) goto L_0x00b8;
    L_0x00b7:
        goto L_0x00bc;
    L_0x00b8:
        r8 = 1;
        r9 = r16;
        goto L_0x00ea;
    L_0x00bc:
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "Skip the tag entry since the number of components is invalid: ";
        r8.append(r9);
        r8.append(r12);
        r8 = r8.toString();
        r9 = r16;
        android.util.Log.w(r9, r8);
        goto L_0x00e9;
    L_0x00d3:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Skip the tag entry since data format is invalid: ";
        r6.append(r7);
        r6.append(r11);
        r6 = r6.toString();
        android.util.Log.w(r9, r6);
    L_0x00e7:
        r6 = 0;
    L_0x00e9:
        r8 = 0;
    L_0x00ea:
        if (r8 != 0) goto L_0x00f5;
    L_0x00ec:
        r1.seek(r13);
        r16 = r3;
        r18 = r5;
        goto L_0x02ae;
    L_0x00f5:
        r8 = "Compression";
        r15 = 4;
        r18 = (r6 > r15 ? 1 : (r6 == r15 ? 0 : -1));
        if (r18 <= 0) goto L_0x01a2;
    L_0x00fd:
        r15 = r24.readInt();
        r16 = r3;
        r3 = r0.mMimeType;
        r18 = r5;
        r5 = 7;
        if (r3 != r5) goto L_0x0165;
    L_0x010a:
        r3 = r4.name;
        r5 = "MakerNote";
        r3 = r5.equals(r3);
        if (r3 == 0) goto L_0x0117;
    L_0x0114:
        r0.mOrfMakerNoteOffset = r15;
        goto L_0x0160;
    L_0x0117:
        r3 = 6;
        if (r2 != r3) goto L_0x0160;
    L_0x011a:
        r5 = r4.name;
        r3 = "ThumbnailImage";
        r3 = r3.equals(r5);
        if (r3 == 0) goto L_0x0160;
    L_0x0124:
        r0.mOrfThumbnailOffset = r15;
        r0.mOrfThumbnailLength = r12;
        r3 = r0.mExifByteOrder;
        r5 = 6;
        r3 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createUShort(r5, r3);
        r5 = r0.mOrfThumbnailOffset;
        r20 = r11;
        r19 = r12;
        r11 = (long) r5;
        r5 = r0.mExifByteOrder;
        r5 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createULong(r11, r5);
        r11 = r0.mOrfThumbnailLength;
        r11 = (long) r11;
        r2 = r0.mExifByteOrder;
        r2 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createULong(r11, r2);
        r11 = r0.mAttributes;
        r12 = 4;
        r11 = r11[r12];
        r11.put(r8, r3);
        r3 = r0.mAttributes;
        r3 = r3[r12];
        r11 = "JPEGInterchangeFormat";
        r3.put(r11, r5);
        r3 = r0.mAttributes;
        r3 = r3[r12];
        r5 = "JPEGInterchangeFormatLength";
        r3.put(r5, r2);
        goto L_0x0179;
    L_0x0160:
        r20 = r11;
        r19 = r12;
        goto L_0x0179;
    L_0x0165:
        r20 = r11;
        r19 = r12;
        r2 = 10;
        if (r3 != r2) goto L_0x0179;
    L_0x016d:
        r2 = r4.name;
        r3 = "JpgFromRaw";
        r2 = r3.equals(r2);
        if (r2 == 0) goto L_0x0179;
    L_0x0177:
        r0.mRw2JpgFromRawOffset = r15;
    L_0x0179:
        r2 = (long) r15;
        r11 = r2 + r6;
        r5 = r1.mLength;
        r21 = r4;
        r4 = (long) r5;
        r22 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1));
        if (r22 > 0) goto L_0x0189;
    L_0x0185:
        r1.seek(r2);
        goto L_0x01ac;
    L_0x0189:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Skip the tag entry since data offset is invalid: ";
        r2.append(r3);
        r2.append(r15);
        r2 = r2.toString();
        android.util.Log.w(r9, r2);
        r1.seek(r13);
        goto L_0x02ae;
    L_0x01a2:
        r16 = r3;
        r21 = r4;
        r18 = r5;
        r20 = r11;
        r19 = r12;
    L_0x01ac:
        r2 = sExifPointerTagMap;
        r3 = java.lang.Integer.valueOf(r10);
        r2 = r2.get(r3);
        r2 = (java.lang.Integer) r2;
        r3 = 8;
        r4 = 3;
        if (r2 == 0) goto L_0x0245;
    L_0x01bd:
        r5 = -1;
        r11 = r20;
        if (r11 == r4) goto L_0x01e2;
    L_0x01c3:
        r4 = 4;
        if (r11 == r4) goto L_0x01dd;
    L_0x01c6:
        if (r11 == r3) goto L_0x01d8;
    L_0x01c8:
        r3 = 9;
        if (r11 == r3) goto L_0x01d3;
    L_0x01cc:
        r3 = 13;
        if (r11 == r3) goto L_0x01d3;
    L_0x01d0:
        r3 = 0;
        goto L_0x01e8;
    L_0x01d3:
        r3 = r24.readInt();
        goto L_0x01e6;
    L_0x01d8:
        r3 = r24.readShort();
        goto L_0x01e6;
    L_0x01dd:
        r5 = r24.readUnsignedInt();
        goto L_0x01d0;
    L_0x01e2:
        r3 = r24.readUnsignedShort();
    L_0x01e6:
        r5 = (long) r3;
        goto L_0x01d0;
    L_0x01e8:
        r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r7 <= 0) goto L_0x022d;
    L_0x01ec:
        r3 = r1.mLength;
        r3 = (long) r3;
        r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r7 >= 0) goto L_0x022d;
    L_0x01f3:
        r3 = r0.mAttributesOffsets;
        r4 = (int) r5;
        r4 = java.lang.Integer.valueOf(r4);
        r3 = r3.contains(r4);
        if (r3 != 0) goto L_0x020b;
    L_0x0200:
        r1.seek(r5);
        r2 = r2.intValue();
        r0.readImageFileDirectory(r1, r2);
        goto L_0x0241;
    L_0x020b:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Skip jump into the IFD since it has already been read: IfdType ";
        r3.append(r4);
        r3.append(r2);
        r2 = " (at ";
        r3.append(r2);
        r3.append(r5);
        r2 = ")";
        r3.append(r2);
        r2 = r3.toString();
        android.util.Log.w(r9, r2);
        goto L_0x0241;
    L_0x022d:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Skip jump into the IFD since its offset is invalid: ";
        r2.append(r3);
        r2.append(r5);
        r2 = r2.toString();
        android.util.Log.w(r9, r2);
    L_0x0241:
        r1.seek(r13);
        goto L_0x02ae;
    L_0x0245:
        r11 = r20;
        r2 = (int) r6;
        r2 = new byte[r2];
        r1.readFully(r2);
        r5 = new androidx.exifinterface.media.ExifInterface$ExifAttribute;
        r6 = r19;
        r5.<init>(r11, r6, r2);
        r2 = r0.mAttributes;
        r2 = r2[r25];
        r6 = r21;
        r7 = r6.name;
        r2.put(r7, r5);
        r2 = r6.name;
        r7 = "DNGVersion";
        r2 = r7.equals(r2);
        if (r2 == 0) goto L_0x026b;
    L_0x0269:
        r0.mMimeType = r4;
    L_0x026b:
        r2 = r6.name;
        r4 = "Make";
        r2 = r4.equals(r2);
        if (r2 != 0) goto L_0x027f;
    L_0x0275:
        r2 = r6.name;
        r4 = "Model";
        r2 = r4.equals(r2);
        if (r2 == 0) goto L_0x028d;
    L_0x027f:
        r2 = r0.mExifByteOrder;
        r2 = r5.getStringValue(r2);
        r4 = "PENTAX";
        r2 = r2.contains(r4);
        if (r2 != 0) goto L_0x02a0;
    L_0x028d:
        r2 = r6.name;
        r2 = r8.equals(r2);
        if (r2 == 0) goto L_0x02a2;
    L_0x0295:
        r2 = r0.mExifByteOrder;
        r2 = r5.getIntValue(r2);
        r4 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        if (r2 != r4) goto L_0x02a2;
    L_0x02a0:
        r0.mMimeType = r3;
    L_0x02a2:
        r2 = r24.peek();
        r2 = (long) r2;
        r4 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1));
        if (r4 == 0) goto L_0x02ae;
    L_0x02ab:
        r1.seek(r13);
    L_0x02ae:
        r5 = r18 + 1;
        r5 = (short) r5;
        r2 = r25;
        r3 = r16;
        goto L_0x002c;
    L_0x02b7:
        r2 = r24.peek();
        r3 = 4;
        r2 = r2 + r3;
        r3 = r1.mLength;
        if (r2 > r3) goto L_0x0326;
    L_0x02c1:
        r2 = r24.readInt();
        r3 = (long) r2;
        r5 = 0;
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r7 <= 0) goto L_0x0312;
    L_0x02cc:
        r5 = r1.mLength;
        if (r2 >= r5) goto L_0x0312;
    L_0x02d0:
        r5 = r0.mAttributesOffsets;
        r6 = java.lang.Integer.valueOf(r2);
        r5 = r5.contains(r6);
        if (r5 != 0) goto L_0x02fd;
    L_0x02dc:
        r1.seek(r3);
        r2 = r0.mAttributes;
        r3 = 4;
        r2 = r2[r3];
        r2 = r2.isEmpty();
        if (r2 == 0) goto L_0x02ee;
    L_0x02ea:
        r0.readImageFileDirectory(r1, r3);
        goto L_0x0326;
    L_0x02ee:
        r2 = r0.mAttributes;
        r3 = 5;
        r2 = r2[r3];
        r2 = r2.isEmpty();
        if (r2 == 0) goto L_0x0326;
    L_0x02f9:
        r0.readImageFileDirectory(r1, r3);
        goto L_0x0326;
    L_0x02fd:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r3 = "Stop reading file since re-reading an IFD may cause an infinite loop: ";
        r1.append(r3);
        r1.append(r2);
        r1 = r1.toString();
        android.util.Log.w(r9, r1);
        goto L_0x0326;
    L_0x0312:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r3 = "Stop reading file since a wrong offset may cause an infinite loop: ";
        r1.append(r3);
        r1.append(r2);
        r1 = r1.toString();
        android.util.Log.w(r9, r1);
    L_0x0326:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.readImageFileDirectory(androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream, int):void");
    }

    private void retrieveJpegImageSize(ByteOrderedDataInputStream byteOrderedDataInputStream, int i) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[i].get(TAG_IMAGE_WIDTH);
        if (((ExifAttribute) this.mAttributes[i].get(TAG_IMAGE_LENGTH)) == null || exifAttribute == null) {
            ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[i].get(TAG_JPEG_INTERCHANGE_FORMAT);
            if (exifAttribute2 != null) {
                getJpegAttributes(byteOrderedDataInputStream, exifAttribute2.getIntValue(this.mExifByteOrder), i);
            }
        }
    }

    private void setThumbnailData(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        HashMap hashMap = this.mAttributes[4];
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_COMPRESSION);
        if (exifAttribute != null) {
            this.mThumbnailCompression = exifAttribute.getIntValue(this.mExifByteOrder);
            int i = this.mThumbnailCompression;
            if (i != 1) {
                if (i == 6) {
                    handleThumbnailFromJfif(byteOrderedDataInputStream, hashMap);
                    return;
                } else if (i != 7) {
                    return;
                }
            }
            if (isSupportedDataType(hashMap)) {
                handleThumbnailFromStrips(byteOrderedDataInputStream, hashMap);
                return;
            }
            return;
        }
        this.mThumbnailCompression = 6;
        handleThumbnailFromJfif(byteOrderedDataInputStream, hashMap);
    }

    private void handleThumbnailFromJfif(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap hashMap) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_JPEG_INTERCHANGE_FORMAT);
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (exifAttribute != null && exifAttribute2 != null) {
            int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
            int min = Math.min(exifAttribute2.getIntValue(this.mExifByteOrder), byteOrderedDataInputStream.available() - intValue);
            int i = this.mMimeType;
            if (i == 4 || i == 9 || i == 10) {
                i = this.mExifOffset;
            } else {
                if (i == 7) {
                    i = this.mOrfMakerNoteOffset;
                }
                if (intValue > 0 && min > 0) {
                    this.mHasThumbnail = true;
                    this.mThumbnailOffset = intValue;
                    this.mThumbnailLength = min;
                    if (this.mFilename == null && this.mAssetInputStream == null) {
                        byte[] bArr = new byte[min];
                        byteOrderedDataInputStream.seek((long) intValue);
                        byteOrderedDataInputStream.readFully(bArr);
                        this.mThumbnailBytes = bArr;
                        return;
                    }
                    return;
                }
            }
            intValue += i;
            if (intValue > 0) {
            }
        }
    }

    private void handleThumbnailFromStrips(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap hashMap) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_STRIP_OFFSETS);
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_STRIP_BYTE_COUNTS);
        if (!(exifAttribute == null || exifAttribute2 == null)) {
            long[] convertToLongArray = convertToLongArray(exifAttribute.getValue(this.mExifByteOrder));
            long[] convertToLongArray2 = convertToLongArray(exifAttribute2.getValue(this.mExifByteOrder));
            String str = TAG;
            if (convertToLongArray == null) {
                Log.w(str, "stripOffsets should not be null.");
            } else if (convertToLongArray2 == null) {
                Log.w(str, "stripByteCounts should not be null.");
            } else {
                long j = 0;
                for (long j2 : convertToLongArray2) {
                    j += j2;
                }
                Object obj = new byte[((int) j)];
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < convertToLongArray.length; i3++) {
                    int i4 = (int) convertToLongArray2[i3];
                    int i5 = ((int) convertToLongArray[i3]) - i;
                    if (i5 < 0) {
                        Log.d(str, "Invalid strip offset value");
                    }
                    byteOrderedDataInputStream.seek((long) i5);
                    i += i5;
                    Object obj2 = new byte[i4];
                    byteOrderedDataInputStream.read(obj2);
                    i += i4;
                    System.arraycopy(obj2, 0, obj, i2, obj2.length);
                    i2 += obj2.length;
                }
                this.mHasThumbnail = true;
                this.mThumbnailBytes = obj;
                this.mThumbnailLength = obj.length;
            }
        }
    }

    private boolean isSupportedDataType(HashMap hashMap) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_BITS_PER_SAMPLE);
        if (exifAttribute != null) {
            int[] iArr = (int[]) exifAttribute.getValue(this.mExifByteOrder);
            if (Arrays.equals(BITS_PER_SAMPLE_RGB, iArr)) {
                return true;
            }
            if (this.mMimeType == 3) {
                ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_PHOTOMETRIC_INTERPRETATION);
                if (exifAttribute2 != null) {
                    int intValue = exifAttribute2.getIntValue(this.mExifByteOrder);
                    if ((intValue == 1 && Arrays.equals(iArr, BITS_PER_SAMPLE_GREYSCALE_2)) || (intValue == 6 && Arrays.equals(iArr, BITS_PER_SAMPLE_RGB))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isThumbnail(HashMap hashMap) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_IMAGE_LENGTH);
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_IMAGE_WIDTH);
        if (!(exifAttribute == null || exifAttribute2 == null)) {
            int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
            int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
            if (intValue <= 512 && intValue2 <= 512) {
                return true;
            }
        }
        return false;
    }

    private void validateImages(InputStream inputStream) throws IOException {
        swapBasedOnImageSize(0, 5);
        swapBasedOnImageSize(0, 4);
        swapBasedOnImageSize(5, 4);
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_PIXEL_X_DIMENSION);
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[1].get(TAG_PIXEL_Y_DIMENSION);
        if (!(exifAttribute == null || exifAttribute2 == null)) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, exifAttribute);
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, exifAttribute2);
        }
        if (this.mAttributes[4].isEmpty() && isThumbnail(this.mAttributes[5])) {
            HashMap[] hashMapArr = this.mAttributes;
            hashMapArr[4] = hashMapArr[5];
            hashMapArr[5] = new HashMap();
        }
        if (!isThumbnail(this.mAttributes[4])) {
            Log.d(TAG, "No image meets the size requirements of a thumbnail image.");
        }
    }

    private void updateImageSizeValues(ByteOrderedDataInputStream byteOrderedDataInputStream, int i) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute) this.mAttributes[i].get(TAG_DEFAULT_CROP_SIZE);
        ExifAttribute exifAttribute2 = (ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_TOP_BORDER);
        ExifAttribute exifAttribute3 = (ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_LEFT_BORDER);
        ExifAttribute exifAttribute4 = (ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_BOTTOM_BORDER);
        ExifAttribute exifAttribute5 = (ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_RIGHT_BORDER);
        String str = TAG_IMAGE_LENGTH;
        String str2 = TAG_IMAGE_WIDTH;
        int i2;
        if (exifAttribute != null) {
            Object createURational;
            Object createURational2;
            i2 = exifAttribute.format;
            String str3 = "Invalid crop size values. cropSize=";
            String str4 = TAG;
            StringBuilder stringBuilder;
            if (i2 == 5) {
                Rational[] rationalArr = (Rational[]) exifAttribute.getValue(this.mExifByteOrder);
                if (rationalArr == null || rationalArr.length != 2) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str3);
                    stringBuilder.append(Arrays.toString(rationalArr));
                    Log.w(str4, stringBuilder.toString());
                    return;
                }
                createURational = ExifAttribute.createURational(rationalArr[0], this.mExifByteOrder);
                createURational2 = ExifAttribute.createURational(rationalArr[1], this.mExifByteOrder);
            } else {
                int[] iArr = (int[]) exifAttribute.getValue(this.mExifByteOrder);
                if (iArr == null || iArr.length != 2) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str3);
                    stringBuilder.append(Arrays.toString(iArr));
                    Log.w(str4, stringBuilder.toString());
                    return;
                }
                createURational = ExifAttribute.createUShort(iArr[0], this.mExifByteOrder);
                createURational2 = ExifAttribute.createUShort(iArr[1], this.mExifByteOrder);
            }
            this.mAttributes[i].put(str2, createURational);
            this.mAttributes[i].put(str, createURational2);
        } else if (exifAttribute2 == null || exifAttribute3 == null || exifAttribute4 == null || exifAttribute5 == null) {
            retrieveJpegImageSize(byteOrderedDataInputStream, i);
        } else {
            i2 = exifAttribute2.getIntValue(this.mExifByteOrder);
            int intValue = exifAttribute4.getIntValue(this.mExifByteOrder);
            int intValue2 = exifAttribute5.getIntValue(this.mExifByteOrder);
            int intValue3 = exifAttribute3.getIntValue(this.mExifByteOrder);
            if (intValue > i2 && intValue2 > intValue3) {
                intValue2 -= intValue3;
                ExifAttribute createUShort = ExifAttribute.createUShort(intValue - i2, this.mExifByteOrder);
                exifAttribute = ExifAttribute.createUShort(intValue2, this.mExifByteOrder);
                this.mAttributes[i].put(str, createUShort);
                this.mAttributes[i].put(str2, exifAttribute);
            }
        }
    }

    private int writeExifSegment(ByteOrderedDataOutputStream byteOrderedDataOutputStream, int i) throws IOException {
        int i2;
        int i3;
        int i4;
        ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = byteOrderedDataOutputStream;
        ExifTag[][] exifTagArr = EXIF_TAGS;
        int[] iArr = new int[exifTagArr.length];
        int[] iArr2 = new int[exifTagArr.length];
        for (ExifTag exifTag : EXIF_POINTER_TAGS) {
            removeAttribute(exifTag.name);
        }
        removeAttribute(JPEG_INTERCHANGE_FORMAT_TAG.name);
        removeAttribute(JPEG_INTERCHANGE_FORMAT_LENGTH_TAG.name);
        for (i2 = 0; i2 < EXIF_TAGS.length; i2++) {
            for (Object obj : this.mAttributes[i2].entrySet().toArray()) {
                Entry entry = (Entry) obj;
                if (entry.getValue() == null) {
                    this.mAttributes[i2].remove(entry.getKey());
                }
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (this.mHasThumbnail) {
            this.mAttributes[4].put(JPEG_INTERCHANGE_FORMAT_TAG.name, ExifAttribute.createULong(0, this.mExifByteOrder));
            this.mAttributes[4].put(JPEG_INTERCHANGE_FORMAT_LENGTH_TAG.name, ExifAttribute.createULong((long) this.mThumbnailLength, this.mExifByteOrder));
        }
        for (i2 = 0; i2 < EXIF_TAGS.length; i2++) {
            i4 = 0;
            for (Entry value : this.mAttributes[i2].entrySet()) {
                int size = ((ExifAttribute) value.getValue()).size();
                if (size > 4) {
                    i4 += size;
                }
            }
            iArr2[i2] = iArr2[i2] + i4;
        }
        i4 = 8;
        for (int i5 = 0; i5 < EXIF_TAGS.length; i5++) {
            if (!this.mAttributes[i5].isEmpty()) {
                iArr[i5] = i4;
                i4 += (((this.mAttributes[i5].size() * 12) + 2) + 4) + iArr2[i5];
            }
        }
        if (this.mHasThumbnail) {
            this.mAttributes[4].put(JPEG_INTERCHANGE_FORMAT_TAG.name, ExifAttribute.createULong((long) i4, this.mExifByteOrder));
            this.mThumbnailOffset = i + i4;
            i4 += this.mThumbnailLength;
        }
        i4 += 8;
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong((long) iArr[1], this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong((long) iArr[2], this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong((long) iArr[3], this.mExifByteOrder));
        }
        byteOrderedDataOutputStream2.writeUnsignedShort(i4);
        byteOrderedDataOutputStream2.write(IDENTIFIER_EXIF_APP1);
        byteOrderedDataOutputStream2.writeShort(this.mExifByteOrder == ByteOrder.BIG_ENDIAN ? BYTE_ALIGN_MM : BYTE_ALIGN_II);
        byteOrderedDataOutputStream2.setByteOrder(this.mExifByteOrder);
        byteOrderedDataOutputStream2.writeUnsignedShort(42);
        byteOrderedDataOutputStream2.writeUnsignedInt(8);
        for (int i6 = 0; i6 < EXIF_TAGS.length; i6++) {
            if (!this.mAttributes[i6].isEmpty()) {
                byteOrderedDataOutputStream2.writeUnsignedShort(this.mAttributes[i6].size());
                i2 = ((iArr[i6] + 2) + (this.mAttributes[i6].size() * 12)) + 4;
                for (Entry entry2 : this.mAttributes[i6].entrySet()) {
                    i3 = ((ExifTag) sExifTagMapsForWriting[i6].get(entry2.getKey())).number;
                    ExifAttribute exifAttribute = (ExifAttribute) entry2.getValue();
                    int size2 = exifAttribute.size();
                    byteOrderedDataOutputStream2.writeUnsignedShort(i3);
                    byteOrderedDataOutputStream2.writeUnsignedShort(exifAttribute.format);
                    byteOrderedDataOutputStream2.writeInt(exifAttribute.numberOfComponents);
                    if (size2 > 4) {
                        byteOrderedDataOutputStream2.writeUnsignedInt((long) i2);
                        i2 += size2;
                    } else {
                        byteOrderedDataOutputStream2.write(exifAttribute.bytes);
                        if (size2 < 4) {
                            while (size2 < 4) {
                                byteOrderedDataOutputStream2.writeByte(0);
                                size2++;
                            }
                        }
                    }
                }
                if (i6 != 0 || this.mAttributes[4].isEmpty()) {
                    byteOrderedDataOutputStream2.writeUnsignedInt(0);
                } else {
                    byteOrderedDataOutputStream2.writeUnsignedInt((long) iArr[4]);
                }
                for (Entry value2 : this.mAttributes[i6].entrySet()) {
                    ExifAttribute exifAttribute2 = (ExifAttribute) value2.getValue();
                    if (exifAttribute2.bytes.length > 4) {
                        byteOrderedDataOutputStream2.write(exifAttribute2.bytes, 0, exifAttribute2.bytes.length);
                    }
                }
            }
        }
        if (this.mHasThumbnail) {
            byteOrderedDataOutputStream2.write(getThumbnailBytes());
        }
        byteOrderedDataOutputStream2.setByteOrder(ByteOrder.BIG_ENDIAN);
        return i4;
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ff A:{ExcHandler: java.lang.NumberFormatException (unused java.lang.NumberFormatException), Splitter: B:35:0x00b7} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:66:?, code:
            java.lang.Double.parseDouble(r12);
     */
    /* JADX WARNING: Missing block: B:67:0x015c, code:
            return new android.util.Pair(java.lang.Integer.valueOf(12), r7);
     */
    /* JADX WARNING: Missing block: B:69:0x0162, code:
            return new android.util.Pair(r5, r7);
     */
    private static android.util.Pair<java.lang.Integer, java.lang.Integer> guessDataFormat(java.lang.String r12) {
        /*
        r0 = ",";
        r1 = r12.contains(r0);
        r2 = 0;
        r3 = 1;
        r4 = 2;
        r5 = java.lang.Integer.valueOf(r4);
        r6 = -1;
        r7 = java.lang.Integer.valueOf(r6);
        if (r1 == 0) goto L_0x00a6;
    L_0x0014:
        r12 = r12.split(r0, r6);
        r0 = r12[r2];
        r0 = guessDataFormat(r0);
        r1 = r0.first;
        r1 = (java.lang.Integer) r1;
        r1 = r1.intValue();
        if (r1 != r4) goto L_0x0029;
    L_0x0028:
        return r0;
    L_0x0029:
        r1 = r12.length;
        if (r3 >= r1) goto L_0x00a5;
    L_0x002c:
        r1 = r12[r3];
        r1 = guessDataFormat(r1);
        r2 = r1.first;
        r2 = (java.lang.Integer) r2;
        r4 = r0.first;
        r2 = r2.equals(r4);
        if (r2 != 0) goto L_0x004d;
    L_0x003e:
        r2 = r1.second;
        r2 = (java.lang.Integer) r2;
        r4 = r0.first;
        r2 = r2.equals(r4);
        if (r2 == 0) goto L_0x004b;
    L_0x004a:
        goto L_0x004d;
    L_0x004b:
        r2 = -1;
        goto L_0x0055;
    L_0x004d:
        r2 = r0.first;
        r2 = (java.lang.Integer) r2;
        r2 = r2.intValue();
    L_0x0055:
        r4 = r0.second;
        r4 = (java.lang.Integer) r4;
        r4 = r4.intValue();
        if (r4 == r6) goto L_0x0080;
    L_0x005f:
        r4 = r1.first;
        r4 = (java.lang.Integer) r4;
        r8 = r0.second;
        r4 = r4.equals(r8);
        if (r4 != 0) goto L_0x0077;
    L_0x006b:
        r1 = r1.second;
        r1 = (java.lang.Integer) r1;
        r4 = r0.second;
        r1 = r1.equals(r4);
        if (r1 == 0) goto L_0x0080;
    L_0x0077:
        r1 = r0.second;
        r1 = (java.lang.Integer) r1;
        r1 = r1.intValue();
        goto L_0x0081;
    L_0x0080:
        r1 = -1;
    L_0x0081:
        if (r2 != r6) goto L_0x008b;
    L_0x0083:
        if (r1 != r6) goto L_0x008b;
    L_0x0085:
        r12 = new android.util.Pair;
        r12.<init>(r5, r7);
        return r12;
    L_0x008b:
        if (r2 != r6) goto L_0x0097;
    L_0x008d:
        r0 = new android.util.Pair;
        r1 = java.lang.Integer.valueOf(r1);
        r0.<init>(r1, r7);
        goto L_0x00a2;
    L_0x0097:
        if (r1 != r6) goto L_0x00a2;
    L_0x0099:
        r0 = new android.util.Pair;
        r1 = java.lang.Integer.valueOf(r2);
        r0.<init>(r1, r7);
    L_0x00a2:
        r3 = r3 + 1;
        goto L_0x0029;
    L_0x00a5:
        return r0;
    L_0x00a6:
        r0 = "/";
        r1 = r12.contains(r0);
        r8 = 0;
        if (r1 == 0) goto L_0x0105;
    L_0x00b0:
        r12 = r12.split(r0, r6);
        r0 = r12.length;
        if (r0 != r4) goto L_0x00ff;
    L_0x00b7:
        r0 = r12[r2];	 Catch:{ NumberFormatException -> 0x00ff }
        r0 = java.lang.Double.parseDouble(r0);	 Catch:{ NumberFormatException -> 0x00ff }
        r0 = (long) r0;	 Catch:{ NumberFormatException -> 0x00ff }
        r12 = r12[r3];	 Catch:{ NumberFormatException -> 0x00ff }
        r2 = java.lang.Double.parseDouble(r12);	 Catch:{ NumberFormatException -> 0x00ff }
        r2 = (long) r2;	 Catch:{ NumberFormatException -> 0x00ff }
        r12 = 10;
        r4 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r4 < 0) goto L_0x00f5;
    L_0x00cb:
        r4 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r4 >= 0) goto L_0x00d0;
    L_0x00cf:
        goto L_0x00f5;
    L_0x00d0:
        r4 = 5;
        r8 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r6 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r6 > 0) goto L_0x00eb;
    L_0x00d8:
        r0 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r0 <= 0) goto L_0x00dd;
    L_0x00dc:
        goto L_0x00eb;
    L_0x00dd:
        r0 = new android.util.Pair;	 Catch:{ NumberFormatException -> 0x00ff }
        r12 = java.lang.Integer.valueOf(r12);	 Catch:{ NumberFormatException -> 0x00ff }
        r1 = java.lang.Integer.valueOf(r4);	 Catch:{ NumberFormatException -> 0x00ff }
        r0.<init>(r12, r1);	 Catch:{ NumberFormatException -> 0x00ff }
        return r0;
    L_0x00eb:
        r12 = new android.util.Pair;	 Catch:{ NumberFormatException -> 0x00ff }
        r0 = java.lang.Integer.valueOf(r4);	 Catch:{ NumberFormatException -> 0x00ff }
        r12.<init>(r0, r7);	 Catch:{ NumberFormatException -> 0x00ff }
        return r12;
    L_0x00f5:
        r0 = new android.util.Pair;	 Catch:{ NumberFormatException -> 0x00ff }
        r12 = java.lang.Integer.valueOf(r12);	 Catch:{ NumberFormatException -> 0x00ff }
        r0.<init>(r12, r7);	 Catch:{ NumberFormatException -> 0x00ff }
        return r0;
    L_0x00ff:
        r12 = new android.util.Pair;
        r12.<init>(r5, r7);
        return r12;
    L_0x0105:
        r0 = java.lang.Long.parseLong(r12);	 Catch:{ NumberFormatException -> 0x014e }
        r0 = java.lang.Long.valueOf(r0);	 Catch:{ NumberFormatException -> 0x014e }
        r1 = r0.longValue();	 Catch:{ NumberFormatException -> 0x014e }
        r3 = 4;
        r4 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1));
        if (r4 < 0) goto L_0x0130;
    L_0x0116:
        r1 = r0.longValue();	 Catch:{ NumberFormatException -> 0x014e }
        r10 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r4 = (r1 > r10 ? 1 : (r1 == r10 ? 0 : -1));
        if (r4 > 0) goto L_0x0130;
    L_0x0121:
        r0 = new android.util.Pair;	 Catch:{ NumberFormatException -> 0x014e }
        r1 = 3;
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ NumberFormatException -> 0x014e }
        r2 = java.lang.Integer.valueOf(r3);	 Catch:{ NumberFormatException -> 0x014e }
        r0.<init>(r1, r2);	 Catch:{ NumberFormatException -> 0x014e }
        return r0;
    L_0x0130:
        r0 = r0.longValue();	 Catch:{ NumberFormatException -> 0x014e }
        r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r2 >= 0) goto L_0x0144;
    L_0x0138:
        r0 = new android.util.Pair;	 Catch:{ NumberFormatException -> 0x014e }
        r1 = 9;
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ NumberFormatException -> 0x014e }
        r0.<init>(r1, r7);	 Catch:{ NumberFormatException -> 0x014e }
        return r0;
    L_0x0144:
        r0 = new android.util.Pair;	 Catch:{ NumberFormatException -> 0x014e }
        r1 = java.lang.Integer.valueOf(r3);	 Catch:{ NumberFormatException -> 0x014e }
        r0.<init>(r1, r7);	 Catch:{ NumberFormatException -> 0x014e }
        return r0;
    L_0x014e:
        java.lang.Double.parseDouble(r12);	 Catch:{ NumberFormatException -> 0x015d }
        r12 = new android.util.Pair;	 Catch:{ NumberFormatException -> 0x015d }
        r0 = 12;
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ NumberFormatException -> 0x015d }
        r12.<init>(r0, r7);	 Catch:{ NumberFormatException -> 0x015d }
        return r12;
    L_0x015d:
        r12 = new android.util.Pair;
        r12.<init>(r5, r7);
        return r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.guessDataFormat(java.lang.String):android.util.Pair<java.lang.Integer, java.lang.Integer>");
    }

    private void swapBasedOnImageSize(int i, int i2) throws IOException {
        if (!this.mAttributes[i].isEmpty() && !this.mAttributes[i2].isEmpty()) {
            HashMap hashMap = this.mAttributes[i];
            String str = TAG_IMAGE_LENGTH;
            ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(str);
            HashMap hashMap2 = this.mAttributes[i];
            String str2 = TAG_IMAGE_WIDTH;
            ExifAttribute exifAttribute2 = (ExifAttribute) hashMap2.get(str2);
            ExifAttribute exifAttribute3 = (ExifAttribute) this.mAttributes[i2].get(str);
            ExifAttribute exifAttribute4 = (ExifAttribute) this.mAttributes[i2].get(str2);
            if (exifAttribute != null && exifAttribute2 != null && exifAttribute3 != null && exifAttribute4 != null) {
                int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
                int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
                int intValue3 = exifAttribute3.getIntValue(this.mExifByteOrder);
                int intValue4 = exifAttribute4.getIntValue(this.mExifByteOrder);
                if (intValue < intValue3 && intValue2 < intValue4) {
                    HashMap[] hashMapArr = this.mAttributes;
                    HashMap hashMap3 = hashMapArr[i];
                    hashMapArr[i] = hashMapArr[i2];
                    hashMapArr[i2] = hashMap3;
                }
            }
        }
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }

    private static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return i;
            }
            i += read;
            outputStream.write(bArr, 0, read);
        }
    }

    private static long[] convertToLongArray(Object obj) {
        if (!(obj instanceof int[])) {
            return obj instanceof long[] ? (long[]) obj : null;
        } else {
            int[] iArr = (int[]) obj;
            long[] jArr = new long[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                jArr[i] = (long) iArr[i];
            }
            return jArr;
        }
    }
}
