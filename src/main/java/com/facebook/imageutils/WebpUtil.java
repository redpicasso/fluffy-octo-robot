package com.facebook.imageutils;

import android.util.Pair;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.drew.metadata.webp.WebpDirectory;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;

public class WebpUtil {
    private static final String VP8L_HEADER = "VP8L";
    private static final String VP8X_HEADER = "VP8X";
    private static final String VP8_HEADER = "VP8 ";

    private WebpUtil() {
    }

    @Nullable
    public static Pair<Integer, Integer> getSize(InputStream inputStream) {
        byte[] bArr = new byte[4];
        try {
            inputStream.read(bArr);
            if (compare(bArr, "RIFF")) {
                getInt(inputStream);
                inputStream.read(bArr);
                if (compare(bArr, WebpDirectory.FORMAT)) {
                    inputStream.read(bArr);
                    String header = getHeader(bArr);
                    Pair<Integer, Integer> vP8Dimension;
                    if ("VP8 ".equals(header)) {
                        vP8Dimension = getVP8Dimension(inputStream);
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return vP8Dimension;
                    } else if ("VP8L".equals(header)) {
                        vP8Dimension = getVP8LDimension(inputStream);
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        return vP8Dimension;
                    } else if ("VP8X".equals(header)) {
                        vP8Dimension = getVP8XDimension(inputStream);
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                        return vP8Dimension;
                    } else {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        return null;
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2222) {
                        e2222.printStackTrace();
                    }
                }
                return null;
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e22222) {
                    e22222.printStackTrace();
                }
            }
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e222222) {
                    e222222.printStackTrace();
                }
            }
        }
    }

    @Nullable
    private static Pair<Integer, Integer> getVP8Dimension(InputStream inputStream) throws IOException {
        inputStream.skip(7);
        return (getShort(inputStream) == (short) 157 && getShort(inputStream) == (short) 1 && getShort(inputStream) == (short) 42) ? new Pair(Integer.valueOf(get2BytesAsInt(inputStream)), Integer.valueOf(get2BytesAsInt(inputStream))) : null;
    }

    @Nullable
    private static Pair<Integer, Integer> getVP8LDimension(InputStream inputStream) throws IOException {
        getInt(inputStream);
        if (getByte(inputStream) != (byte) 47) {
            return null;
        }
        int read = ((byte) inputStream.read()) & 255;
        return new Pair(Integer.valueOf(((((byte) inputStream.read()) & 255) | ((read & 63) << 8)) + 1), Integer.valueOf((((((((byte) inputStream.read()) & 255) & 15) << 10) | ((((byte) inputStream.read()) & 255) << 2)) | ((read & JfifUtil.MARKER_SOFn) >> 6)) + 1));
    }

    private static Pair<Integer, Integer> getVP8XDimension(InputStream inputStream) throws IOException {
        inputStream.skip(8);
        return new Pair(Integer.valueOf(read3Bytes(inputStream) + 1), Integer.valueOf(read3Bytes(inputStream) + 1));
    }

    private static boolean compare(byte[] bArr, String str) {
        if (bArr.length != str.length()) {
            return false;
        }
        for (int i = 0; i < bArr.length; i++) {
            if (str.charAt(i) != bArr[i]) {
                return false;
            }
        }
        return true;
    }

    private static String getHeader(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bArr) {
            stringBuilder.append((char) b);
        }
        return stringBuilder.toString();
    }

    private static int getInt(InputStream inputStream) throws IOException {
        return ((((((byte) inputStream.read()) << 24) & ViewCompat.MEASURED_STATE_MASK) | ((((byte) inputStream.read()) << 16) & 16711680)) | ((((byte) inputStream.read()) << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((byte) inputStream.read()) & 255);
    }

    public static int get2BytesAsInt(InputStream inputStream) throws IOException {
        return ((((byte) inputStream.read()) << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (((byte) inputStream.read()) & 255);
    }

    private static int read3Bytes(InputStream inputStream) throws IOException {
        byte b = getByte(inputStream);
        return (((getByte(inputStream) << 16) & 16711680) | ((getByte(inputStream) << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (b & 255);
    }

    private static short getShort(InputStream inputStream) throws IOException {
        return (short) (inputStream.read() & 255);
    }

    private static byte getByte(InputStream inputStream) throws IOException {
        return (byte) (inputStream.read() & 255);
    }

    private static boolean isBitOne(byte b, int i) {
        return ((b >> (i % 8)) & 1) == 1;
    }
}
