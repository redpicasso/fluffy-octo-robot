package com.drew.metadata.photoshop;

import com.bumptech.glide.load.Key;
import com.drew.lang.ByteArrayReader;
import com.drew.lang.Charsets;
import com.drew.lang.RandomAccessReader;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class PhotoshopDescriptor extends TagDescriptor<PhotoshopDirectory> {
    public PhotoshopDescriptor(@NotNull PhotoshopDirectory photoshopDirectory) {
        super(photoshopDirectory);
    }

    public String getDescription(int i) {
        if (i != 1002) {
            if (i == PhotoshopDirectory.TAG_RESOLUTION_INFO) {
                return getResolutionInfoDescription();
            }
            if (i == 1028) {
                return getBinaryDataString(i);
            }
            if (i == 1030) {
                return getJpegQualityString();
            }
            if (!(i == PhotoshopDirectory.TAG_SEED_NUMBER || i == PhotoshopDirectory.TAG_URL_LIST)) {
                if (i == PhotoshopDirectory.TAG_VERSION) {
                    return getVersionDescription();
                }
                if (i == PhotoshopDirectory.TAG_PRINT_SCALE) {
                    return getPrintScaleDescription();
                }
                if (i == PhotoshopDirectory.TAG_PIXEL_ASPECT_RATIO) {
                    return getPixelAspectRatioString();
                }
                if (i == PhotoshopDirectory.TAG_CLIPPING_PATH_NAME) {
                    return getClippingPathNameString(i);
                }
                if (i != PhotoshopDirectory.TAG_GLOBAL_ALTITUDE) {
                    if (i == PhotoshopDirectory.TAG_SLICES) {
                        return getSlicesDescription();
                    }
                    switch (i) {
                        case PhotoshopDirectory.TAG_THUMBNAIL_OLD /*1033*/:
                        case PhotoshopDirectory.TAG_THUMBNAIL /*1036*/:
                            return getThumbnailDescription(i);
                        case PhotoshopDirectory.TAG_COPYRIGHT /*1034*/:
                            return getBooleanString(i);
                        case PhotoshopDirectory.TAG_URL /*1035*/:
                            break;
                        case 1037:
                            break;
                        default:
                            if (i < CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE || i > 2998) {
                                return super.getDescription(i);
                            }
                            return getPathString(i);
                    }
                }
            }
            return get32BitNumberString(i);
        }
        return getSimpleString(i);
    }

    @Nullable
    public String getJpegQualityString() {
        try {
            byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(1030);
            if (byteArray == null) {
                return ((PhotoshopDirectory) this._directory).getString(1030);
            }
            String str;
            RandomAccessReader byteArrayReader = new ByteArrayReader(byteArray);
            int uInt16 = byteArrayReader.getUInt16(0);
            int uInt162 = byteArrayReader.getUInt16(2);
            int uInt163 = byteArrayReader.getUInt16(4);
            int i = (uInt16 > 65535 || uInt16 < 65533) ? uInt16 <= 8 ? uInt16 + 4 : uInt16 : uInt16 - 65532;
            switch (uInt16) {
                case 0:
                    str = "Low";
                    break;
                case 1:
                case 2:
                case 3:
                    str = "Medium";
                    break;
                case 4:
                case 5:
                    str = "High";
                    break;
                case 6:
                case 7:
                case 8:
                    str = "Maximum";
                    break;
                default:
                    switch (uInt16) {
                        case 65533:
                        case 65534:
                        case 65535:
                            break;
                        default:
                            str = "Unknown";
                            break;
                    }
                    str = "Low";
                    break;
            }
            String str2 = "Unknown 0x%04X";
            String format = uInt162 != 0 ? uInt162 != 1 ? uInt162 != 257 ? String.format(str2, new Object[]{Integer.valueOf(uInt162)}) : "Progressive" : "Optimised" : "Standard";
            String format2 = (uInt163 < 1 || uInt163 > 3) ? String.format(str2, new Object[]{Integer.valueOf(uInt163)}) : String.format("%d", new Object[]{Integer.valueOf(uInt163 + 2)});
            return String.format("%d (%s), %s format, %s scans", new Object[]{Integer.valueOf(i), str, format, format2});
        } catch (IOException unused) {
            return null;
        }
    }

    @Nullable
    public String getPixelAspectRatioString() {
        String str = null;
        try {
            byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(PhotoshopDirectory.TAG_PIXEL_ASPECT_RATIO);
            if (byteArray == null) {
                return null;
            }
            str = Double.toString(new ByteArrayReader(byteArray).getDouble64(4));
        } catch (Exception unused) {
            return str;
        }
    }

    @Nullable
    public String getPrintScaleDescription() {
        String str = null;
        try {
            byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(PhotoshopDirectory.TAG_PRINT_SCALE);
            if (byteArray == null) {
                return null;
            }
            RandomAccessReader byteArrayReader = new ByteArrayReader(byteArray);
            int int32 = byteArrayReader.getInt32(0);
            float float32 = byteArrayReader.getFloat32(2);
            float float322 = byteArrayReader.getFloat32(6);
            float float323 = byteArrayReader.getFloat32(10);
            if (int32 == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Centered, Scale ");
                stringBuilder.append(float323);
                str = stringBuilder.toString();
            } else if (int32 == 1) {
                return "Size to fit";
            } else {
                if (int32 != 2) {
                    return String.format("Unknown %04X, X:%s Y:%s, Scale:%s", new Object[]{Integer.valueOf(int32), Float.valueOf(float32), Float.valueOf(float322), Float.valueOf(float323)});
                }
                return String.format("User defined, X:%s Y:%s, Scale:%s", new Object[]{Float.valueOf(float32), Float.valueOf(float322), Float.valueOf(float323)});
            }
        } catch (Exception unused) {
            return str;
        }
    }

    @Nullable
    public String getResolutionInfoDescription() {
        String str = null;
        try {
            byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(PhotoshopDirectory.TAG_RESOLUTION_INFO);
            if (byteArray == null) {
                return null;
            }
            RandomAccessReader byteArrayReader = new ByteArrayReader(byteArray);
            float s15Fixed16 = byteArrayReader.getS15Fixed16(0);
            float s15Fixed162 = byteArrayReader.getS15Fixed16(8);
            DecimalFormat decimalFormat = new DecimalFormat("0.##");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(decimalFormat.format((double) s15Fixed16));
            stringBuilder.append("x");
            stringBuilder.append(decimalFormat.format((double) s15Fixed162));
            stringBuilder.append(" DPI");
            str = stringBuilder.toString();
        } catch (Exception unused) {
            return str;
        }
    }

    @Nullable
    public String getVersionDescription() {
        String str = "UTF-16";
        try {
            byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(PhotoshopDirectory.TAG_VERSION);
            if (byteArray == null) {
                return null;
            }
            RandomAccessReader byteArrayReader = new ByteArrayReader(byteArray);
            int int32 = byteArrayReader.getInt32(0);
            int int322 = byteArrayReader.getInt32(5) * 2;
            String string = byteArrayReader.getString(9, int322, str);
            int i = 9 + int322;
            int322 = byteArrayReader.getInt32(i);
            i += 4;
            int322 *= 2;
            str = byteArrayReader.getString(i, int322, str);
            int int323 = byteArrayReader.getInt32(i + int322);
            return String.format("%d (%s, %s) %d", new Object[]{Integer.valueOf(int32), string, str, Integer.valueOf(int323)});
        } catch (IOException unused) {
            return null;
        }
    }

    @Nullable
    public String getSlicesDescription() {
        String str = null;
        try {
            byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(PhotoshopDirectory.TAG_SLICES);
            if (byteArray == null) {
                return null;
            }
            RandomAccessReader byteArrayReader = new ByteArrayReader(byteArray);
            int int32 = byteArrayReader.getInt32(20) * 2;
            String string = byteArrayReader.getString(24, int32, "UTF-16");
            int32 = byteArrayReader.getInt32(int32 + 24);
            str = String.format("%s (%d,%d,%d,%d) %d Slices", new Object[]{string, Integer.valueOf(byteArrayReader.getInt32(4)), Integer.valueOf(byteArrayReader.getInt32(8)), Integer.valueOf(byteArrayReader.getInt32(12)), Integer.valueOf(byteArrayReader.getInt32(16)), Integer.valueOf(int32)});
        } catch (IOException unused) {
            return str;
        }
    }

    @Nullable
    public String getThumbnailDescription(int i) {
        try {
            byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(i);
            if (byteArray == null) {
                return null;
            }
            RandomAccessReader byteArrayReader = new ByteArrayReader(byteArray);
            int int32 = byteArrayReader.getInt32(0);
            int int322 = byteArrayReader.getInt32(4);
            int int323 = byteArrayReader.getInt32(8);
            int int324 = byteArrayReader.getInt32(16);
            int int325 = byteArrayReader.getInt32(20);
            int int326 = byteArrayReader.getInt32(24);
            String str = "%s, %dx%d, Decomp %d bytes, %d bpp, %d bytes";
            Object[] objArr = new Object[6];
            objArr[0] = int32 == 1 ? "JpegRGB" : "RawRGB";
            objArr[1] = Integer.valueOf(int322);
            objArr[2] = Integer.valueOf(int323);
            objArr[3] = Integer.valueOf(int324);
            objArr[4] = Integer.valueOf(int326);
            objArr[5] = Integer.valueOf(int325);
            return String.format(str, objArr);
        } catch (IOException unused) {
            return null;
        }
    }

    @Nullable
    private String getBooleanString(int i) {
        byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(i);
        if (byteArray == null || byteArray.length == 0) {
            return null;
        }
        return byteArray[0] == (byte) 0 ? "No" : "Yes";
    }

    @Nullable
    private String get32BitNumberString(int i) {
        byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(i);
        if (byteArray == null) {
            return null;
        }
        RandomAccessReader byteArrayReader = new ByteArrayReader(byteArray);
        try {
            return String.format("%d", new Object[]{Integer.valueOf(byteArrayReader.getInt32(0))});
        } catch (IOException unused) {
            return null;
        }
    }

    @Nullable
    private String getSimpleString(int i) {
        byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(i);
        if (byteArray == null) {
            return null;
        }
        return new String(byteArray);
    }

    @Nullable
    private String getBinaryDataString(int i) {
        if (((PhotoshopDirectory) this._directory).getByteArray(i) == null) {
            return null;
        }
        return String.format("%d bytes binary data", new Object[]{Integer.valueOf(((PhotoshopDirectory) this._directory).getByteArray(i).length)});
    }

    @Nullable
    public String getClippingPathNameString(int i) {
        try {
            byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(i);
            if (byteArray == null) {
                return null;
            }
            RandomAccessReader byteArrayReader = new ByteArrayReader(byteArray);
            return new String(byteArrayReader.getBytes(1, byteArrayReader.getByte(0)), Key.STRING_CHARSET_NAME);
        } catch (Exception unused) {
            return null;
        }
    }

    @Nullable
    public String getPathString(int i) {
        String str = ")";
        String str2 = ",";
        String str3 = " (";
        try {
            byte[] byteArray = ((PhotoshopDirectory) this._directory).getByteArray(i);
            if (byteArray == null) {
                return null;
            }
            String str4;
            String str5;
            RandomAccessReader randomAccessReader;
            ArrayList arrayList;
            String str6;
            Subpath subpath;
            RandomAccessReader byteArrayReader = new ByteArrayReader(byteArray);
            short s = (short) 1;
            int length = ((int) ((byteArrayReader.getLength() - ((long) byteArrayReader.getByte(((int) byteArrayReader.getLength()) - 1))) - 1)) / 26;
            Subpath subpath2 = new Subpath();
            Subpath subpath3 = new Subpath();
            ArrayList arrayList2 = new ArrayList();
            String str7 = null;
            Subpath subpath4 = subpath3;
            subpath3 = subpath2;
            int i2 = 0;
            while (i2 < length) {
                int i3 = i2 * 26;
                try {
                    int i4;
                    short int16 = byteArrayReader.getInt16(i3);
                    Subpath subpath5 = subpath4;
                    String str8 = "Linked";
                    String str9 = "Unlinked";
                    Knot knot;
                    ArrayList arrayList3;
                    switch (int16) {
                        case (short) 0:
                            str4 = str;
                            str5 = str2;
                            i4 = length;
                            randomAccessReader = byteArrayReader;
                            arrayList = arrayList2;
                            str6 = str7;
                            subpath = subpath5;
                            if (subpath3.size() != 0) {
                                arrayList.add(subpath3);
                            }
                            subpath4 = subpath;
                            subpath3 = new Subpath("Closed Subpath");
                            break;
                        case (short) 1:
                        case (short) 2:
                            str4 = str;
                            str5 = str2;
                            i4 = length;
                            arrayList = arrayList2;
                            str6 = str7;
                            subpath = subpath5;
                            if (int16 == (short) 1) {
                                knot = new Knot(str8);
                            } else {
                                knot = new Knot(str9);
                            }
                            int i5 = 0;
                            while (i5 < 6) {
                                int i6 = i5 * 4;
                                double int8 = (double) byteArrayReader.getInt8((i6 + 2) + i3);
                                double int24 = (double) byteArrayReader.getInt24((i6 + 3) + i3);
                                randomAccessReader = byteArrayReader;
                                int i7 = i3;
                                knot.setPoint(i5, int8 + (int24 / Math.pow(2.0d, 24.0d)));
                                i5++;
                                byteArrayReader = randomAccessReader;
                                i3 = i7;
                            }
                            randomAccessReader = byteArrayReader;
                            subpath3.add(knot);
                            break;
                        case (short) 3:
                            str4 = str;
                            str5 = str2;
                            i4 = length;
                            arrayList3 = arrayList2;
                            str6 = str7;
                            subpath = subpath5;
                            if (subpath.size() != 0) {
                                arrayList = arrayList3;
                                arrayList.add(subpath);
                            } else {
                                arrayList = arrayList3;
                            }
                            subpath4 = new Subpath("Open Subpath");
                            randomAccessReader = byteArrayReader;
                            break;
                        case (short) 4:
                        case (short) 5:
                            if (int16 == (short) 4) {
                                knot = new Knot(str8);
                            } else {
                                knot = new Knot(str9);
                            }
                            int i8 = 0;
                            while (i8 < 6) {
                                int i9 = i8 * 4;
                                arrayList3 = arrayList2;
                                i4 = length;
                                str4 = str;
                                str5 = str2;
                                str6 = str7;
                                knot.setPoint(i8, ((double) byteArrayReader.getInt8((i9 + 2) + i3)) + (((double) byteArrayReader.getInt24((i9 + 3) + i3)) / Math.pow(2.0d, 24.0d)));
                                i8++;
                                arrayList2 = arrayList3;
                                length = i4;
                                str = str4;
                                str2 = str5;
                                str7 = str6;
                            }
                            str4 = str;
                            str5 = str2;
                            i4 = length;
                            arrayList3 = arrayList2;
                            str6 = str7;
                            subpath = subpath5;
                            subpath.add(knot);
                            randomAccessReader = byteArrayReader;
                            arrayList = arrayList3;
                            break;
                        case (short) 8:
                            str4 = str;
                            str5 = str2;
                            str7 = byteArrayReader.getInt16(i3 + 2) == s ? "with all pixels" : "without all pixels";
                            i4 = length;
                            randomAccessReader = byteArrayReader;
                            arrayList = arrayList2;
                            subpath4 = subpath5;
                            continue;
                        default:
                            str4 = str;
                            str5 = str2;
                            i4 = length;
                            randomAccessReader = byteArrayReader;
                            arrayList = arrayList2;
                            str6 = str7;
                            subpath = subpath5;
                            break;
                    }
                    subpath4 = subpath;
                    str7 = str6;
                    i2++;
                    arrayList2 = arrayList;
                    byteArrayReader = randomAccessReader;
                    length = i4;
                    str = str4;
                    str2 = str5;
                    s = (short) 1;
                } catch (Exception unused) {
                    str = null;
                }
            }
            str4 = str;
            str5 = str2;
            randomAccessReader = byteArrayReader;
            arrayList = arrayList2;
            subpath = subpath4;
            str6 = str7;
            if (subpath3.size() != 0) {
                arrayList.add(subpath3);
            }
            if (subpath.size() != 0) {
                arrayList.add(subpath);
            }
            int i10 = randomAccessReader.getByte(((int) randomAccessReader.getLength()) - 1);
            str = randomAccessReader.getString((((int) randomAccessReader.getLength()) - i10) - 1, i10, Charsets.ASCII);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('\"');
            stringBuilder.append(str);
            stringBuilder.append('\"');
            stringBuilder.append(" having ");
            if (str6 != null) {
                stringBuilder.append("initial fill rule \"");
                stringBuilder.append(str6);
                stringBuilder.append("\" and ");
            }
            stringBuilder.append(arrayList.size());
            stringBuilder.append(arrayList.size() == 1 ? " subpath:" : " subpaths:");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Subpath subpath6 = (Subpath) it.next();
                stringBuilder.append("\n- ");
                stringBuilder.append(subpath6.getType());
                stringBuilder.append(" with ");
                stringBuilder.append(arrayList.size());
                stringBuilder.append(arrayList.size() == 1 ? " knot:" : " knots:");
                for (Knot knot2 : subpath6.getKnots()) {
                    stringBuilder.append("\n  - ");
                    stringBuilder.append(knot2.getType());
                    stringBuilder.append(str3);
                    stringBuilder.append(knot2.getPoint(0));
                    String str10 = str5;
                    stringBuilder.append(str10);
                    stringBuilder.append(knot2.getPoint(1));
                    String str11 = str4;
                    stringBuilder.append(str11);
                    stringBuilder.append(str3);
                    stringBuilder.append(knot2.getPoint(2));
                    stringBuilder.append(str10);
                    stringBuilder.append(knot2.getPoint(3));
                    stringBuilder.append(str11);
                    stringBuilder.append(str3);
                    stringBuilder.append(knot2.getPoint(4));
                    stringBuilder.append(str10);
                    stringBuilder.append(knot2.getPoint(5));
                    stringBuilder.append(str11);
                    str5 = str10;
                    str4 = str11;
                }
            }
            return stringBuilder.toString();
        } catch (Exception unused2) {
            str = null;
            return str;
        }
    }
}
