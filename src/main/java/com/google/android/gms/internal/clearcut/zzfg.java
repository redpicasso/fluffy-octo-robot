package com.google.android.gms.internal.clearcut;

import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.facebook.imageutils.JfifUtil;
import java.nio.ByteBuffer;

abstract class zzfg {
    zzfg() {
    }

    static void zzc(CharSequence charSequence, ByteBuffer byteBuffer) {
        char charAt;
        int length = charSequence.length();
        int position = byteBuffer.position();
        int i = 0;
        while (i < length) {
            try {
                charAt = charSequence.charAt(i);
                if (charAt >= 128) {
                    break;
                }
                byteBuffer.put(position + i, (byte) charAt);
                i++;
            } catch (IndexOutOfBoundsException unused) {
                length = byteBuffer.position() + Math.max(i, (position - byteBuffer.position()) + 1);
                char charAt2 = charSequence.charAt(i);
                StringBuilder stringBuilder = new StringBuilder(37);
                stringBuilder.append("Failed writing ");
                stringBuilder.append(charAt2);
                stringBuilder.append(" at index ");
                stringBuilder.append(length);
                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            }
        }
        if (i == length) {
            byteBuffer.position(position + i);
            return;
        }
        position += i;
        while (i < length) {
            charAt = charSequence.charAt(i);
            int i2;
            if (charAt < 128) {
                byteBuffer.put(position, (byte) charAt);
            } else if (charAt < 2048) {
                i2 = position + 1;
                try {
                    byteBuffer.put(position, (byte) ((charAt >>> 6) | JfifUtil.MARKER_SOFn));
                    byteBuffer.put(i2, (byte) ((charAt & 63) | 128));
                    position = i2;
                } catch (IndexOutOfBoundsException unused2) {
                    position = i2;
                }
            } else if (charAt < 55296 || 57343 < charAt) {
                i2 = position + 1;
                byteBuffer.put(position, (byte) ((charAt >>> 12) | CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY));
                position = i2 + 1;
                byteBuffer.put(i2, (byte) (((charAt >>> 6) & 63) | 128));
                byteBuffer.put(position, (byte) ((charAt & 63) | 128));
            } else {
                i2 = i + 1;
                if (i2 != length) {
                    try {
                        char charAt3 = charSequence.charAt(i2);
                        if (Character.isSurrogatePair(charAt, charAt3)) {
                            i = Character.toCodePoint(charAt, charAt3);
                            int i3 = position + 1;
                            try {
                                byteBuffer.put(position, (byte) ((i >>> 18) | 240));
                                position = i3 + 1;
                                byteBuffer.put(i3, (byte) (((i >>> 12) & 63) | 128));
                                i3 = position + 1;
                                byteBuffer.put(position, (byte) (((i >>> 6) & 63) | 128));
                                byteBuffer.put(i3, (byte) ((i & 63) | 128));
                                position = i3;
                                i = i2;
                            } catch (IndexOutOfBoundsException unused3) {
                                position = i3;
                            }
                        } else {
                            i = i2;
                        }
                    } catch (IndexOutOfBoundsException unused4) {
                        i = i2;
                    }
                }
                throw new zzfi(i, length);
            }
            i++;
            position++;
        }
        byteBuffer.position(position);
    }

    abstract int zzb(int i, byte[] bArr, int i2, int i3);

    abstract int zzb(CharSequence charSequence, byte[] bArr, int i, int i2);

    abstract void zzb(CharSequence charSequence, ByteBuffer byteBuffer);

    final boolean zze(byte[] bArr, int i, int i2) {
        return zzb(0, bArr, i, i2) == 0;
    }
}
