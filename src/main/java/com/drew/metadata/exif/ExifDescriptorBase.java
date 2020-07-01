package com.drew.metadata.exif;

import androidx.exifinterface.media.ExifInterface;
import com.drew.imaging.PhotographicConversions;
import com.drew.lang.ByteArrayReader;
import com.drew.lang.Rational;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Directory;
import com.drew.metadata.TagDescriptor;
import com.drew.metadata.exif.makernotes.FujifilmMakernoteDirectory;
import com.drew.metadata.exif.makernotes.PanasonicMakernoteDirectory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class ExifDescriptorBase<T extends Directory> extends TagDescriptor<T> {
    private final boolean _allowDecimalRepresentationOfRationals = true;

    public ExifDescriptorBase(@NotNull T t) {
        super(t);
    }

    @Nullable
    public String getDescription(int i) {
        switch (i) {
            case 1:
                return getInteropIndexDescription();
            case 2:
                return getInteropVersionDescription();
            case ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE /*254*/:
                return getNewSubfileTypeDescription();
            case 255:
                return getSubfileTypeDescription();
            case 256:
                return getImageWidthDescription();
            case 257:
                return getImageHeightDescription();
            case 258:
                return getBitsPerSampleDescription();
            case 259:
                return getCompressionDescription();
            case 262:
                return getPhotometricInterpretationDescription();
            case 263:
                return getThresholdingDescription();
            case 266:
                return getFillOrderDescription();
            case 274:
                return getOrientationDescription();
            case 277:
                return getSamplesPerPixelDescription();
            case 278:
                return getRowsPerStripDescription();
            case 279:
                return getStripByteCountsDescription();
            case 282:
                return getXResolutionDescription();
            case 283:
                return getYResolutionDescription();
            case 284:
                return getPlanarConfigurationDescription();
            case 296:
                return getResolutionDescription();
            case 512:
                return getJpegProcDescription();
            case 530:
                return getYCbCrSubsamplingDescription();
            case 531:
                return getYCbCrPositioningDescription();
            case 532:
                return getReferenceBlackWhiteDescription();
            case ExifDirectoryBase.TAG_CFA_PATTERN_2 /*33422*/:
                return getCfaPattern2Description();
            case ExifDirectoryBase.TAG_EXPOSURE_TIME /*33434*/:
                return getExposureTimeDescription();
            case ExifDirectoryBase.TAG_FNUMBER /*33437*/:
                return getFNumberDescription();
            case ExifDirectoryBase.TAG_EXPOSURE_PROGRAM /*34850*/:
                return getExposureProgramDescription();
            case ExifDirectoryBase.TAG_ISO_EQUIVALENT /*34855*/:
                return getIsoEquivalentDescription();
            case ExifDirectoryBase.TAG_SENSITIVITY_TYPE /*34864*/:
                return getSensitivityTypeRangeDescription();
            case ExifDirectoryBase.TAG_EXIF_VERSION /*36864*/:
                return getExifVersionDescription();
            case ExifDirectoryBase.TAG_COMPONENTS_CONFIGURATION /*37121*/:
                return getComponentConfigurationDescription();
            case ExifDirectoryBase.TAG_COMPRESSED_AVERAGE_BITS_PER_PIXEL /*37122*/:
                return getCompressedAverageBitsPerPixelDescription();
            case ExifDirectoryBase.TAG_SHUTTER_SPEED /*37377*/:
                return getShutterSpeedDescription();
            case ExifDirectoryBase.TAG_APERTURE /*37378*/:
                return getApertureValueDescription();
            case ExifDirectoryBase.TAG_EXPOSURE_BIAS /*37380*/:
                return getExposureBiasDescription();
            case ExifDirectoryBase.TAG_MAX_APERTURE /*37381*/:
                return getMaxApertureValueDescription();
            case ExifDirectoryBase.TAG_SUBJECT_DISTANCE /*37382*/:
                return getSubjectDistanceDescription();
            case ExifDirectoryBase.TAG_METERING_MODE /*37383*/:
                return getMeteringModeDescription();
            case 37384:
                return getWhiteBalanceDescription();
            case ExifDirectoryBase.TAG_FLASH /*37385*/:
                return getFlashDescription();
            case ExifDirectoryBase.TAG_FOCAL_LENGTH /*37386*/:
                return getFocalLengthDescription();
            case ExifDirectoryBase.TAG_USER_COMMENT /*37510*/:
                return getUserCommentDescription();
            case ExifDirectoryBase.TAG_WIN_TITLE /*40091*/:
                return getWindowsTitleDescription();
            case ExifDirectoryBase.TAG_WIN_COMMENT /*40092*/:
                return getWindowsCommentDescription();
            case ExifDirectoryBase.TAG_WIN_AUTHOR /*40093*/:
                return getWindowsAuthorDescription();
            case ExifDirectoryBase.TAG_WIN_KEYWORDS /*40094*/:
                return getWindowsKeywordsDescription();
            case ExifDirectoryBase.TAG_WIN_SUBJECT /*40095*/:
                return getWindowsSubjectDescription();
            case ExifDirectoryBase.TAG_FLASHPIX_VERSION /*40960*/:
                return getFlashPixVersionDescription();
            case 40961:
                return getColorSpaceDescription();
            case ExifDirectoryBase.TAG_EXIF_IMAGE_WIDTH /*40962*/:
                return getExifImageWidthDescription();
            case ExifDirectoryBase.TAG_EXIF_IMAGE_HEIGHT /*40963*/:
                return getExifImageHeightDescription();
            case ExifDirectoryBase.TAG_FOCAL_PLANE_X_RESOLUTION /*41486*/:
                return getFocalPlaneXResolutionDescription();
            case ExifDirectoryBase.TAG_FOCAL_PLANE_Y_RESOLUTION /*41487*/:
                return getFocalPlaneYResolutionDescription();
            case ExifDirectoryBase.TAG_FOCAL_PLANE_RESOLUTION_UNIT /*41488*/:
                return getFocalPlaneResolutionUnitDescription();
            case ExifDirectoryBase.TAG_SENSING_METHOD /*41495*/:
                return getSensingMethodDescription();
            case ExifDirectoryBase.TAG_FILE_SOURCE /*41728*/:
                return getFileSourceDescription();
            case ExifDirectoryBase.TAG_SCENE_TYPE /*41729*/:
                return getSceneTypeDescription();
            case ExifDirectoryBase.TAG_CFA_PATTERN /*41730*/:
                return getCfaPatternDescription();
            case ExifDirectoryBase.TAG_CUSTOM_RENDERED /*41985*/:
                return getCustomRenderedDescription();
            case ExifDirectoryBase.TAG_EXPOSURE_MODE /*41986*/:
                return getExposureModeDescription();
            case ExifDirectoryBase.TAG_WHITE_BALANCE_MODE /*41987*/:
                return getWhiteBalanceModeDescription();
            case ExifDirectoryBase.TAG_DIGITAL_ZOOM_RATIO /*41988*/:
                return getDigitalZoomRatioDescription();
            case ExifDirectoryBase.TAG_35MM_FILM_EQUIV_FOCAL_LENGTH /*41989*/:
                return get35mmFilmEquivFocalLengthDescription();
            case ExifDirectoryBase.TAG_SCENE_CAPTURE_TYPE /*41990*/:
                return getSceneCaptureTypeDescription();
            case ExifDirectoryBase.TAG_GAIN_CONTROL /*41991*/:
                return getGainControlDescription();
            case ExifDirectoryBase.TAG_CONTRAST /*41992*/:
                return getContrastDescription();
            case ExifDirectoryBase.TAG_SATURATION /*41993*/:
                return getSaturationDescription();
            case ExifDirectoryBase.TAG_SHARPNESS /*41994*/:
                return getSharpnessDescription();
            case ExifDirectoryBase.TAG_SUBJECT_DISTANCE_RANGE /*41996*/:
                return getSubjectDistanceRangeDescription();
            case ExifDirectoryBase.TAG_LENS_SPECIFICATION /*42034*/:
                return getLensSpecificationDescription();
            default:
                return super.getDescription(i);
        }
    }

    @Nullable
    public String getInteropVersionDescription() {
        return getVersionBytesDescription(2, 2);
    }

    @Nullable
    public String getInteropIndexDescription() {
        String string = this._directory.getString(1);
        if (string == null) {
            return null;
        }
        if ("R98".equalsIgnoreCase(string.trim())) {
            string = "Recommended Exif Interoperability Rules (ExifR98)";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown (");
            stringBuilder.append(string);
            stringBuilder.append(")");
            string = stringBuilder.toString();
        }
        return string;
    }

    @Nullable
    public String getReferenceBlackWhiteDescription() {
        int[] intArray = this._directory.getIntArray(532);
        if (intArray == null || intArray.length < 6) {
            Object object = this._directory.getObject(532);
            if (object == null || !(object instanceof long[])) {
                return null;
            }
            long[] jArr = (long[]) object;
            if (jArr.length < 6) {
                return null;
            }
            int[] iArr = new int[jArr.length];
            for (int i = 0; i < jArr.length; i++) {
                iArr[i] = (int) jArr[i];
            }
            intArray = iArr;
        }
        int i2 = intArray[0];
        int i3 = intArray[1];
        int i4 = intArray[2];
        int i5 = intArray[3];
        int i6 = intArray[4];
        int i7 = intArray[5];
        return String.format("[%d,%d,%d] [%d,%d,%d]", new Object[]{Integer.valueOf(i2), Integer.valueOf(i4), Integer.valueOf(i6), Integer.valueOf(i3), Integer.valueOf(i5), Integer.valueOf(i7)});
    }

    @Nullable
    public String getYResolutionDescription() {
        Rational rational = this._directory.getRational(283);
        if (rational == null) {
            return null;
        }
        String resolutionDescription = getResolutionDescription();
        Object[] objArr = new Object[2];
        objArr[0] = rational.toSimpleString(true);
        objArr[1] = resolutionDescription == null ? "unit" : resolutionDescription.toLowerCase();
        return String.format("%s dots per %s", objArr);
    }

    @Nullable
    public String getXResolutionDescription() {
        Rational rational = this._directory.getRational(282);
        if (rational == null) {
            return null;
        }
        String resolutionDescription = getResolutionDescription();
        Object[] objArr = new Object[2];
        objArr[0] = rational.toSimpleString(true);
        objArr[1] = resolutionDescription == null ? "unit" : resolutionDescription.toLowerCase();
        return String.format("%s dots per %s", objArr);
    }

    @Nullable
    public String getYCbCrPositioningDescription() {
        return getIndexedDescription(531, 1, "Center of pixel array", "Datum point");
    }

    @Nullable
    public String getOrientationDescription() {
        return super.getOrientationDescription(274);
    }

    @Nullable
    public String getResolutionDescription() {
        return getIndexedDescription(296, 1, "(No unit)", "Inch", "cm");
    }

    @Nullable
    private String getUnicodeDescription(int i) {
        byte[] byteArray = this._directory.getByteArray(i);
        if (byteArray == null) {
            return null;
        }
        try {
            return new String(byteArray, "UTF-16LE").trim();
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    @Nullable
    public String getWindowsAuthorDescription() {
        return getUnicodeDescription(ExifDirectoryBase.TAG_WIN_AUTHOR);
    }

    @Nullable
    public String getWindowsCommentDescription() {
        return getUnicodeDescription(ExifDirectoryBase.TAG_WIN_COMMENT);
    }

    @Nullable
    public String getWindowsKeywordsDescription() {
        return getUnicodeDescription(ExifDirectoryBase.TAG_WIN_KEYWORDS);
    }

    @Nullable
    public String getWindowsTitleDescription() {
        return getUnicodeDescription(ExifDirectoryBase.TAG_WIN_TITLE);
    }

    @Nullable
    public String getWindowsSubjectDescription() {
        return getUnicodeDescription(ExifDirectoryBase.TAG_WIN_SUBJECT);
    }

    @Nullable
    public String getYCbCrSubsamplingDescription() {
        int[] intArray = this._directory.getIntArray(530);
        if (intArray == null || intArray.length < 2) {
            return null;
        }
        if (intArray[0] == 2 && intArray[1] == 1) {
            return "YCbCr4:2:2";
        }
        return (intArray[0] == 2 && intArray[1] == 2) ? "YCbCr4:2:0" : "(Unknown)";
    }

    @Nullable
    public String getPlanarConfigurationDescription() {
        return getIndexedDescription(284, 1, "Chunky (contiguous for each subsampling pixel)", "Separate (Y-plane/Cb-plane/Cr-plane format)");
    }

    @Nullable
    public String getSamplesPerPixelDescription() {
        String string = this._directory.getString(277);
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" samples/pixel");
        return stringBuilder.toString();
    }

    @Nullable
    public String getRowsPerStripDescription() {
        String string = this._directory.getString(278);
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" rows/strip");
        return stringBuilder.toString();
    }

    @Nullable
    public String getStripByteCountsDescription() {
        String string = this._directory.getString(279);
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" bytes");
        return stringBuilder.toString();
    }

    @Nullable
    public String getPhotometricInterpretationDescription() {
        Integer integer = this._directory.getInteger(262);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 32803) {
            return "Color Filter Array";
        }
        if (intValue == 32892) {
            return "Linear Raw";
        }
        switch (intValue) {
            case 0:
                return "WhiteIsZero";
            case 1:
                return "BlackIsZero";
            case 2:
                return "RGB";
            case 3:
                return "RGB Palette";
            case 4:
                return "Transparency Mask";
            case 5:
                return "CMYK";
            case 6:
                return "YCbCr";
            default:
                switch (intValue) {
                    case 8:
                        return "CIELab";
                    case 9:
                        return "ICCLab";
                    case 10:
                        return "ITULab";
                    default:
                        switch (intValue) {
                            case 32844:
                                return "Pixar LogL";
                            case 32845:
                                return "Pixar LogLuv";
                            default:
                                return "Unknown colour space";
                        }
                }
        }
    }

    @Nullable
    public String getBitsPerSampleDescription() {
        String string = this._directory.getString(258);
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" bits/component/pixel");
        return stringBuilder.toString();
    }

    @Nullable
    public String getImageWidthDescription() {
        String string = this._directory.getString(256);
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" pixels");
        return stringBuilder.toString();
    }

    @Nullable
    public String getImageHeightDescription() {
        String string = this._directory.getString(257);
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" pixels");
        return stringBuilder.toString();
    }

    @Nullable
    public String getNewSubfileTypeDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 0, "Full-resolution image", "Reduced-resolution image", "Single page of multi-page image", "Single page of multi-page reduced-resolution image", "Transparency mask", "Transparency mask of reduced-resolution image", "Transparency mask of multi-page image", "Transparency mask of reduced-resolution multi-page image");
    }

    @Nullable
    public String getSubfileTypeDescription() {
        return getIndexedDescription(255, 1, "Full-resolution image", "Reduced-resolution image", "Single page of multi-page image");
    }

    @Nullable
    public String getThresholdingDescription() {
        return getIndexedDescription(263, 1, "No dithering or halftoning", "Ordered dither or halftone", "Randomized dither");
    }

    @Nullable
    public String getFillOrderDescription() {
        return getIndexedDescription(266, 1, "Normal", "Reversed");
    }

    @Nullable
    public String getSubjectDistanceRangeDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_SUBJECT_DISTANCE_RANGE, "Unknown", "Macro", "Close view", "Distant view");
    }

    @Nullable
    public String getSensitivityTypeRangeDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_SENSITIVITY_TYPE, "Unknown", "Standard Output Sensitivity", "Recommended Exposure Index", "ISO Speed", "Standard Output Sensitivity and Recommended Exposure Index", "Standard Output Sensitivity and ISO Speed", "Recommended Exposure Index and ISO Speed", "Standard Output Sensitivity, Recommended Exposure Index and ISO Speed");
    }

    @Nullable
    public String getLensSpecificationDescription() {
        return getLensSpecificationDescription(ExifDirectoryBase.TAG_LENS_SPECIFICATION);
    }

    @Nullable
    public String getSharpnessDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_SHARPNESS, "None", "Low", "Hard");
    }

    @Nullable
    public String getSaturationDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_SATURATION, "None", "Low saturation", "High saturation");
    }

    @Nullable
    public String getContrastDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_CONTRAST, "None", "Soft", "Hard");
    }

    @Nullable
    public String getGainControlDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_GAIN_CONTROL, "None", "Low gain up", "Low gain down", "High gain up", "High gain down");
    }

    @Nullable
    public String getSceneCaptureTypeDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_SCENE_CAPTURE_TYPE, "Standard", "Landscape", "Portrait", "Night scene");
    }

    @Nullable
    public String get35mmFilmEquivFocalLengthDescription() {
        Integer integer = this._directory.getInteger(ExifDirectoryBase.TAG_35MM_FILM_EQUIV_FOCAL_LENGTH);
        if (integer == null) {
            return null;
        }
        return integer.intValue() == 0 ? "Unknown" : TagDescriptor.getFocalLengthDescription((double) integer.intValue());
    }

    @Nullable
    public String getDigitalZoomRatioDescription() {
        Rational rational = this._directory.getRational(ExifDirectoryBase.TAG_DIGITAL_ZOOM_RATIO);
        if (rational == null) {
            return null;
        }
        return rational.getNumerator() == 0 ? "Digital zoom not used" : new DecimalFormat("0.#").format(rational.doubleValue());
    }

    @Nullable
    public String getWhiteBalanceModeDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_WHITE_BALANCE_MODE, "Auto white balance", "Manual white balance");
    }

    @Nullable
    public String getExposureModeDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_EXPOSURE_MODE, "Auto exposure", "Manual exposure", "Auto bracket");
    }

    @Nullable
    public String getCustomRenderedDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_CUSTOM_RENDERED, "Normal process", "Custom process");
    }

    @Nullable
    public String getUserCommentDescription() {
        byte[] byteArray = this._directory.getByteArray(ExifDirectoryBase.TAG_USER_COMMENT);
        if (byteArray == null) {
            return null;
        }
        if (byteArray.length == 0) {
            return "";
        }
        Map hashMap = new HashMap();
        String str = "file.encoding";
        hashMap.put("ASCII", System.getProperty(str));
        hashMap.put("UNICODE", "UTF-16LE");
        hashMap.put("JIS", "Shift-JIS");
        try {
            if (byteArray.length >= 10) {
                String str2 = new String(byteArray, 0, 10);
                for (Entry entry : hashMap.entrySet()) {
                    String str3 = (String) entry.getKey();
                    String str4 = (String) entry.getValue();
                    if (str2.startsWith(str3)) {
                        for (int length = str3.length(); length < 10; length++) {
                            byte b = byteArray[length];
                            if (b != (byte) 0 && b != (byte) 32) {
                                return new String(byteArray, length, byteArray.length - length, str4).trim();
                            }
                        }
                        return new String(byteArray, 10, byteArray.length - 10, str4).trim();
                    }
                }
            }
            return new String(byteArray, System.getProperty(str)).trim();
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    @Nullable
    public String getIsoEquivalentDescription() {
        Integer integer = this._directory.getInteger(ExifDirectoryBase.TAG_ISO_EQUIVALENT);
        return integer != null ? Integer.toString(integer.intValue()) : null;
    }

    @Nullable
    public String getExifVersionDescription() {
        return getVersionBytesDescription(ExifDirectoryBase.TAG_EXIF_VERSION, 2);
    }

    @Nullable
    public String getFlashPixVersionDescription() {
        return getVersionBytesDescription(ExifDirectoryBase.TAG_FLASHPIX_VERSION, 2);
    }

    @Nullable
    public String getSceneTypeDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_SCENE_TYPE, 1, "Directly photographed image");
    }

    @Nullable
    public String getCfaPatternDescription() {
        return formatCFAPattern(decodeCfaPattern(ExifDirectoryBase.TAG_CFA_PATTERN));
    }

    @Nullable
    public String getCfaPattern2Description() {
        byte[] byteArray = this._directory.getByteArray(ExifDirectoryBase.TAG_CFA_PATTERN_2);
        if (byteArray == null) {
            return null;
        }
        int[] intArray = this._directory.getIntArray(ExifDirectoryBase.TAG_CFA_REPEAT_PATTERN_DIM);
        int i = 0;
        if (intArray == null) {
            return String.format("Repeat Pattern not found for CFAPattern (%s)", new Object[]{super.getDescription(ExifDirectoryBase.TAG_CFA_PATTERN_2)});
        } else if (intArray.length == 2 && byteArray.length == intArray[0] * intArray[1]) {
            int[] iArr = new int[(byteArray.length + 2)];
            iArr[0] = intArray[0];
            iArr[1] = intArray[1];
            while (i < byteArray.length) {
                iArr[i + 2] = byteArray[i] & 255;
                i++;
            }
            return formatCFAPattern(iArr);
        } else {
            return String.format("Unknown Pattern (%s)", new Object[]{super.getDescription(ExifDirectoryBase.TAG_CFA_PATTERN_2)});
        }
    }

    @Nullable
    private static String formatCFAPattern(@Nullable int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int i = 2;
        if (iArr.length < 2) {
            return "<truncated data>";
        }
        if (iArr[0] == 0 && iArr[1] == 0) {
            return "<zero pattern size>";
        }
        int i2 = (iArr[0] * iArr[1]) + 2;
        if (i2 > iArr.length) {
            return "<invalid pattern size>";
        }
        String[] strArr = new String[]{"Red", "Green", "Blue", "Cyan", "Magenta", "Yellow", "White"};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        while (i < i2) {
            if (iArr[i] <= strArr.length - 1) {
                stringBuilder.append(strArr[iArr[i]]);
            } else {
                stringBuilder.append("Unknown");
            }
            if ((i - 2) % iArr[1] == 0) {
                stringBuilder.append(",");
            } else if (i != i2 - 1) {
                stringBuilder.append("][");
            }
            i++;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Nullable
    private int[] decodeCfaPattern(int i) {
        byte[] byteArray = this._directory.getByteArray(i);
        if (byteArray == null) {
            return null;
        }
        int i2 = 4;
        int i3 = 0;
        int[] iArr;
        if (byteArray.length < 4) {
            iArr = new int[byteArray.length];
            while (i3 < byteArray.length) {
                iArr[i3] = byteArray[i3];
                i3++;
            }
            return iArr;
        }
        iArr = new int[(byteArray.length - 2)];
        try {
            ByteArrayReader byteArrayReader = new ByteArrayReader(byteArray);
            short int16 = byteArrayReader.getInt16(0);
            short int162 = byteArrayReader.getInt16(2);
            Boolean valueOf = Boolean.valueOf(false);
            if ((int16 * int162) + 2 > byteArray.length) {
                byteArrayReader.setMotorolaByteOrder(!byteArrayReader.isMotorolaByteOrder());
                int16 = byteArrayReader.getInt16(0);
                int162 = byteArrayReader.getInt16(2);
                if (byteArray.length >= (int16 * int162) + 2) {
                    valueOf = Boolean.valueOf(true);
                }
            } else {
                valueOf = Boolean.valueOf(true);
            }
            if (valueOf.booleanValue()) {
                iArr[0] = int16;
                iArr[1] = int162;
                while (i2 < byteArray.length) {
                    iArr[i2 - 2] = byteArrayReader.getInt8(i2);
                    i2++;
                }
            }
        } catch (IOException e) {
            Directory directory = this._directory;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IO exception processing data: ");
            stringBuilder.append(e.getMessage());
            directory.addError(stringBuilder.toString());
        }
        return iArr;
    }

    @Nullable
    public String getFileSourceDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_FILE_SOURCE, 1, "Film Scanner", "Reflection Print Scanner", "Digital Still Camera (DSC)");
    }

    @Nullable
    public String getExposureBiasDescription() {
        Rational rational = this._directory.getRational(ExifDirectoryBase.TAG_EXPOSURE_BIAS);
        if (rational == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(rational.toSimpleString(true));
        stringBuilder.append(" EV");
        return stringBuilder.toString();
    }

    @Nullable
    public String getMaxApertureValueDescription() {
        Double doubleObject = this._directory.getDoubleObject(ExifDirectoryBase.TAG_MAX_APERTURE);
        if (doubleObject == null) {
            return null;
        }
        return TagDescriptor.getFStopDescription(PhotographicConversions.apertureToFStop(doubleObject.doubleValue()));
    }

    @Nullable
    public String getApertureValueDescription() {
        Double doubleObject = this._directory.getDoubleObject(ExifDirectoryBase.TAG_APERTURE);
        if (doubleObject == null) {
            return null;
        }
        return TagDescriptor.getFStopDescription(PhotographicConversions.apertureToFStop(doubleObject.doubleValue()));
    }

    @Nullable
    public String getExposureProgramDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_EXPOSURE_PROGRAM, 1, "Manual control", "Program normal", "Aperture priority", "Shutter priority", "Program creative (slow program)", "Program action (high-speed program)", "Portrait mode", "Landscape mode");
    }

    @Nullable
    public String getFocalPlaneXResolutionDescription() {
        Rational rational = this._directory.getRational(ExifDirectoryBase.TAG_FOCAL_PLANE_X_RESOLUTION);
        if (rational == null) {
            return null;
        }
        String str;
        String focalPlaneResolutionUnitDescription = getFocalPlaneResolutionUnitDescription();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(rational.getReciprocal().toSimpleString(true));
        if (focalPlaneResolutionUnitDescription == null) {
            str = "";
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" ");
            stringBuilder2.append(focalPlaneResolutionUnitDescription.toLowerCase());
            str = stringBuilder2.toString();
        }
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    @Nullable
    public String getFocalPlaneYResolutionDescription() {
        Rational rational = this._directory.getRational(ExifDirectoryBase.TAG_FOCAL_PLANE_Y_RESOLUTION);
        if (rational == null) {
            return null;
        }
        String str;
        String focalPlaneResolutionUnitDescription = getFocalPlaneResolutionUnitDescription();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(rational.getReciprocal().toSimpleString(true));
        if (focalPlaneResolutionUnitDescription == null) {
            str = "";
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" ");
            stringBuilder2.append(focalPlaneResolutionUnitDescription.toLowerCase());
            str = stringBuilder2.toString();
        }
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    @Nullable
    public String getFocalPlaneResolutionUnitDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_FOCAL_PLANE_RESOLUTION_UNIT, 1, "(No unit)", "Inches", "cm");
    }

    @Nullable
    public String getExifImageWidthDescription() {
        Integer integer = this._directory.getInteger(ExifDirectoryBase.TAG_EXIF_IMAGE_WIDTH);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(integer);
        stringBuilder.append(" pixels");
        return stringBuilder.toString();
    }

    @Nullable
    public String getExifImageHeightDescription() {
        Integer integer = this._directory.getInteger(ExifDirectoryBase.TAG_EXIF_IMAGE_HEIGHT);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(integer);
        stringBuilder.append(" pixels");
        return stringBuilder.toString();
    }

    @Nullable
    public String getColorSpaceDescription() {
        Integer integer = this._directory.getInteger(40961);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() == 1) {
            return "sRGB";
        }
        if (integer.intValue() == 65535) {
            return "Undefined";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getFocalLengthDescription() {
        Rational rational = this._directory.getRational(ExifDirectoryBase.TAG_FOCAL_LENGTH);
        if (rational == null) {
            return null;
        }
        return TagDescriptor.getFocalLengthDescription(rational.doubleValue());
    }

    @Nullable
    public String getFlashDescription() {
        Integer integer = this._directory.getInteger(ExifDirectoryBase.TAG_FLASH);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if ((integer.intValue() & 1) != 0) {
            stringBuilder.append("Flash fired");
        } else {
            stringBuilder.append("Flash did not fire");
        }
        if ((integer.intValue() & 4) != 0) {
            if ((integer.intValue() & 2) != 0) {
                stringBuilder.append(", return detected");
            } else {
                stringBuilder.append(", return not detected");
            }
        }
        if ((integer.intValue() & 16) != 0) {
            stringBuilder.append(", auto");
        }
        if ((integer.intValue() & 64) != 0) {
            stringBuilder.append(", red-eye reduction");
        }
        return stringBuilder.toString();
    }

    @Nullable
    public String getWhiteBalanceDescription() {
        Integer integer = this._directory.getInteger(37384);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            return "Unknown";
        }
        if (intValue == 1) {
            return "Daylight";
        }
        if (intValue == 2) {
            return "Florescent";
        }
        if (intValue == 3) {
            return "Tungsten";
        }
        if (intValue == 4) {
            return ExifInterface.TAG_FLASH;
        }
        if (intValue == 255) {
            return "(Other)";
        }
        switch (intValue) {
            case 9:
                return "Fine Weather";
            case 10:
                return "Cloudy";
            case 11:
                return "Shade";
            case 12:
                return "Daylight Fluorescent";
            case 13:
                return "Day White Fluorescent";
            case 14:
                return "Cool White Fluorescent";
            case 15:
                return "White Fluorescent";
            case 16:
                return "Warm White Fluorescent";
            case 17:
                return "Standard light";
            case 18:
                return "Standard light (B)";
            case 19:
                return "Standard light (C)";
            case 20:
                return "D55";
            case 21:
                return "D65";
            case 22:
                return "D75";
            case 23:
                return "D50";
            case 24:
                return "Studio Tungsten";
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown (");
                stringBuilder.append(integer);
                stringBuilder.append(")");
                return stringBuilder.toString();
        }
    }

    @Nullable
    public String getMeteringModeDescription() {
        Integer integer = this._directory.getInteger(ExifDirectoryBase.TAG_METERING_MODE);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 255) {
            return "(Other)";
        }
        switch (intValue) {
            case 0:
                return "Unknown";
            case 1:
                return "Average";
            case 2:
                return "Center weighted average";
            case 3:
                return "Spot";
            case 4:
                return "Multi-spot";
            case 5:
                return "Multi-segment";
            case 6:
                return "Partial";
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown (");
                stringBuilder.append(integer);
                stringBuilder.append(")");
                return stringBuilder.toString();
        }
    }

    @Nullable
    public String getCompressionDescription() {
        Integer integer = this._directory.getInteger(259);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 32766) {
            return "Next";
        }
        if (intValue == 32767) {
            return "Sony ARW Compressed";
        }
        String str = "JPEG";
        switch (intValue) {
            case 1:
                return "Uncompressed";
            case 2:
                return "CCITT 1D";
            case 3:
                return "T4/Group 3 Fax";
            case 4:
                return "T6/Group 4 Fax";
            case 5:
                return "LZW";
            case 6:
                return "JPEG (old-style)";
            case 7:
                return str;
            case 8:
                return "Adobe Deflate";
            case 9:
                return "JBIG B&W";
            case 10:
                return "JBIG Color";
            default:
                switch (intValue) {
                    case 99:
                        return str;
                    case 262:
                        return "Kodak 262";
                    case 32809:
                        return "Thunderscan";
                    case 32867:
                        return "Kodak KDC Compressed";
                    case 34661:
                        return "JBIG";
                    case 34715:
                        return "JBIG2 TIFF FX";
                    case ExifInterface.DATA_LOSSY_JPEG /*34892*/:
                        return "Lossy JPEG";
                    case 65000:
                        return "Kodak DCR Compressed";
                    case 65535:
                        return "Pentax PEF Compressed";
                    default:
                        switch (intValue) {
                            case PanasonicMakernoteDirectory.TAG_SCENE_MODE /*32769*/:
                                return "Packed RAW";
                            case FujifilmMakernoteDirectory.TAG_ORDER_NUMBER /*32770*/:
                                return "Samsung SRW Compressed";
                            case FujifilmMakernoteDirectory.TAG_FRAME_NUMBER /*32771*/:
                                return "CCIRLEW";
                            case PanasonicMakernoteDirectory.TAG_WB_RED_LEVEL /*32772*/:
                                return "Samsung SRW Compressed 2";
                            case 32773:
                                return "PackBits";
                            default:
                                switch (intValue) {
                                    case 32895:
                                        return "IT8CTPAD";
                                    case 32896:
                                        return "IT8LW";
                                    case 32897:
                                        return "IT8MP";
                                    case 32898:
                                        return "IT8BL";
                                    default:
                                        switch (intValue) {
                                            case 32908:
                                                return "PixarFilm";
                                            case 32909:
                                                return "PixarLog";
                                            default:
                                                switch (intValue) {
                                                    case 32946:
                                                        return "Deflate";
                                                    case 32947:
                                                        return "DCS";
                                                    default:
                                                        switch (intValue) {
                                                            case 34676:
                                                                return "SGILog";
                                                            case 34677:
                                                                return "SGILog24";
                                                            default:
                                                                switch (intValue) {
                                                                    case 34712:
                                                                        return "JPEG 2000";
                                                                    case 34713:
                                                                        return "Nikon NEF Compressed";
                                                                    default:
                                                                        switch (intValue) {
                                                                            case 34718:
                                                                                return "Microsoft Document Imaging (MDI) Binary Level Codec";
                                                                            case 34719:
                                                                                return "Microsoft Document Imaging (MDI) Progressive Transform Codec";
                                                                            case 34720:
                                                                                return "Microsoft Document Imaging (MDI) Vector";
                                                                            default:
                                                                                StringBuilder stringBuilder = new StringBuilder();
                                                                                stringBuilder.append("Unknown (");
                                                                                stringBuilder.append(integer);
                                                                                stringBuilder.append(")");
                                                                                return stringBuilder.toString();
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
    }

    @Nullable
    public String getSubjectDistanceDescription() {
        Rational rational = this._directory.getRational(ExifDirectoryBase.TAG_SUBJECT_DISTANCE);
        if (rational == null) {
            return null;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.0##");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(decimalFormat.format(rational.doubleValue()));
        stringBuilder.append(" metres");
        return stringBuilder.toString();
    }

    @Nullable
    public String getCompressedAverageBitsPerPixelDescription() {
        Rational rational = this._directory.getRational(ExifDirectoryBase.TAG_COMPRESSED_AVERAGE_BITS_PER_PIXEL);
        if (rational == null) {
            return null;
        }
        StringBuilder stringBuilder;
        String str;
        String toSimpleString = rational.toSimpleString(true);
        if (rational.isInteger() && rational.intValue() == 1) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(toSimpleString);
            str = " bit/pixel";
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(toSimpleString);
            str = " bits/pixel";
        }
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    @Nullable
    public String getExposureTimeDescription() {
        String string = this._directory.getString(ExifDirectoryBase.TAG_EXPOSURE_TIME);
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" sec");
        return stringBuilder.toString();
    }

    @Nullable
    public String getShutterSpeedDescription() {
        return super.getShutterSpeedDescription(ExifDirectoryBase.TAG_SHUTTER_SPEED);
    }

    @Nullable
    public String getFNumberDescription() {
        Rational rational = this._directory.getRational(ExifDirectoryBase.TAG_FNUMBER);
        if (rational == null) {
            return null;
        }
        return TagDescriptor.getFStopDescription(rational.doubleValue());
    }

    @Nullable
    public String getSensingMethodDescription() {
        return getIndexedDescription(ExifDirectoryBase.TAG_SENSING_METHOD, 1, "(Not defined)", "One-chip color area sensor", "Two-chip color area sensor", "Three-chip color area sensor", "Color sequential area sensor", null, "Trilinear sensor", "Color sequential linear sensor");
    }

    @Nullable
    public String getComponentConfigurationDescription() {
        int[] intArray = this._directory.getIntArray(ExifDirectoryBase.TAG_COMPONENTS_CONFIGURATION);
        if (intArray == null) {
            return null;
        }
        String[] strArr = new String[]{"", "Y", "Cb", "Cr", "R", "G", "B"};
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Math.min(4, intArray.length); i++) {
            int i2 = intArray[i];
            if (i2 > 0 && i2 < strArr.length) {
                stringBuilder.append(strArr[i2]);
            }
        }
        return stringBuilder.toString();
    }

    @Nullable
    public String getJpegProcDescription() {
        Integer integer = this._directory.getInteger(512);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 1) {
            return "Baseline";
        }
        if (intValue == 14) {
            return "Lossless";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
