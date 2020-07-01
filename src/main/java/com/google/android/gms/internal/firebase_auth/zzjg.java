package com.google.android.gms.internal.firebase_auth;

import com.adobe.xmp.options.PropertyOptions;
import com.google.android.gms.internal.firebase_auth.zzhs.zzd;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import sun.misc.Unsafe;

final class zzjg<T> implements zzjs<T> {
    private static final int[] zzacv = new int[0];
    private static final Unsafe zzacw = zzkq.zzkt();
    private final zzjc zzacr;
    private final zzkk<?, ?> zzacs;
    private final boolean zzact;
    private final zzhh<?> zzacu;
    private final int[] zzacx;
    private final Object[] zzacy;
    private final int zzacz;
    private final int zzada;
    private final boolean zzadb;
    private final boolean zzadc;
    private final boolean zzadd;
    private final int[] zzade;
    private final int zzadf;
    private final int zzadg;
    private final zzjh zzadh;
    private final zzim zzadi;
    private final zziv zzadj;

    private zzjg(int[] iArr, Object[] objArr, int i, int i2, zzjc zzjc, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzjh zzjh, zzim zzim, zzkk<?, ?> zzkk, zzhh<?> zzhh, zziv zziv) {
        this.zzacx = iArr;
        this.zzacy = objArr;
        this.zzacz = i;
        this.zzada = i2;
        this.zzadb = zzjc instanceof zzhs;
        this.zzadc = z;
        boolean z3 = zzhh != null && zzhh.zzf(zzjc);
        this.zzact = z3;
        this.zzadd = false;
        this.zzade = iArr2;
        this.zzadf = i3;
        this.zzadg = i4;
        this.zzadh = zzjh;
        this.zzadi = zzim;
        this.zzacs = zzkk;
        this.zzacu = zzhh;
        this.zzacr = zzjc;
        this.zzadj = zziv;
    }

    private static boolean zzbd(int i) {
        return (i & PropertyOptions.DELETE_EXISTING) != 0;
    }

    static <T> zzjg<T> zza(Class<T> cls, zzja zzja, zzjh zzjh, zzim zzim, zzkk<?, ?> zzkk, zzhh<?> zzhh, zziv zziv) {
        zzja zzja2 = zzja;
        if (zzja2 instanceof zzjq) {
            int i;
            int i2;
            int i3;
            int i4;
            char charAt;
            int[] iArr;
            char c;
            char c2;
            int i5;
            int i6;
            int i7;
            char charAt2;
            int i8;
            int i9;
            boolean z;
            zzjq zzjq = (zzjq) zzja2;
            int i10 = 0;
            boolean z2 = zzjq.zzjo() == zzd.zzaaw;
            String zzjw = zzjq.zzjw();
            int length = zzjw.length();
            int charAt3 = zzjw.charAt(0);
            if (charAt3 >= 55296) {
                char charAt4;
                i = charAt3 & 8191;
                charAt3 = 1;
                i2 = 13;
                while (true) {
                    i3 = charAt3 + 1;
                    charAt4 = zzjw.charAt(charAt3);
                    if (charAt4 < 55296) {
                        break;
                    }
                    i |= (charAt4 & 8191) << i2;
                    i2 += 13;
                    charAt3 = i3;
                }
                charAt3 = (charAt4 << i2) | i;
            } else {
                i3 = 1;
            }
            i = i3 + 1;
            i2 = zzjw.charAt(i3);
            if (i2 >= 55296) {
                i2 &= 8191;
                i3 = 13;
                while (true) {
                    i4 = i + 1;
                    charAt = zzjw.charAt(i);
                    if (charAt < 55296) {
                        break;
                    }
                    i2 |= (charAt & 8191) << i3;
                    i3 += 13;
                    i = i4;
                }
                i2 |= charAt << i3;
            } else {
                i4 = i;
            }
            if (i2 == 0) {
                iArr = zzacv;
                i = 0;
                c = 0;
                i3 = 0;
                c2 = 0;
                i5 = 0;
                i6 = 0;
            } else {
                int i11;
                int i12;
                char charAt5;
                i = i4 + 1;
                i2 = zzjw.charAt(i4);
                if (i2 >= 55296) {
                    i2 &= 8191;
                    i3 = 13;
                    while (true) {
                        i4 = i + 1;
                        charAt = zzjw.charAt(i);
                        if (charAt < 55296) {
                            break;
                        }
                        i2 |= (charAt & 8191) << i3;
                        i3 += 13;
                        i = i4;
                    }
                    i2 = (charAt << i3) | i2;
                } else {
                    i4 = i;
                }
                i = i4 + 1;
                i3 = zzjw.charAt(i4);
                if (i3 >= 55296) {
                    i3 &= 8191;
                    i4 = 13;
                    while (true) {
                        i7 = i + 1;
                        charAt = zzjw.charAt(i);
                        if (charAt < 55296) {
                            break;
                        }
                        i3 |= (charAt & 8191) << i4;
                        i4 += 13;
                        i = i7;
                    }
                    i3 |= charAt << i4;
                } else {
                    i7 = i;
                }
                i = i7 + 1;
                charAt2 = zzjw.charAt(i7);
                if (charAt2 >= 55296) {
                    i4 = charAt2 & 8191;
                    i7 = 13;
                    while (true) {
                        i5 = i + 1;
                        charAt = zzjw.charAt(i);
                        if (charAt < 55296) {
                            break;
                        }
                        i4 |= (charAt & 8191) << i7;
                        i7 += 13;
                        i = i5;
                    }
                    charAt2 = (charAt << i7) | i4;
                } else {
                    i5 = i;
                }
                i = i5 + 1;
                c2 = zzjw.charAt(i5);
                if (c2 >= 55296) {
                    i7 = c2 & 8191;
                    i5 = 13;
                    while (true) {
                        i11 = i + 1;
                        charAt = zzjw.charAt(i);
                        if (charAt < 55296) {
                            break;
                        }
                        i7 |= (charAt & 8191) << i5;
                        i5 += 13;
                        i = i11;
                    }
                    c2 = (charAt << i5) | i7;
                } else {
                    i11 = i;
                }
                i = i11 + 1;
                i5 = zzjw.charAt(i11);
                if (i5 >= 55296) {
                    i5 &= 8191;
                    i11 = 13;
                    while (true) {
                        i6 = i + 1;
                        charAt = zzjw.charAt(i);
                        if (charAt < 55296) {
                            break;
                        }
                        i5 |= (charAt & 8191) << i11;
                        i11 += 13;
                        i = i6;
                    }
                    i5 = (charAt << i11) | i5;
                    i = i6;
                }
                i11 = i + 1;
                i = zzjw.charAt(i);
                if (i >= 55296) {
                    i &= 8191;
                    i6 = 13;
                    while (true) {
                        i12 = i11 + 1;
                        charAt5 = zzjw.charAt(i11);
                        if (charAt5 < 55296) {
                            break;
                        }
                        i |= (charAt5 & 8191) << i6;
                        i6 += 13;
                        i11 = i12;
                    }
                    i |= charAt5 << i6;
                    i11 = i12;
                }
                i6 = i11 + 1;
                i11 = zzjw.charAt(i11);
                if (i11 >= 55296) {
                    i12 = 13;
                    i8 = i6;
                    i6 = i11 & 8191;
                    i11 = i8;
                    while (true) {
                        i9 = i11 + 1;
                        charAt5 = zzjw.charAt(i11);
                        if (charAt5 < 55296) {
                            break;
                        }
                        i6 |= (charAt5 & 8191) << i12;
                        i12 += 13;
                        i11 = i9;
                    }
                    i11 = i6 | (charAt5 << i12);
                    i10 = i9;
                } else {
                    i10 = i6;
                }
                i6 = i10 + 1;
                i10 = zzjw.charAt(i10);
                if (i10 >= 55296) {
                    char charAt6;
                    i12 = 13;
                    i8 = i6;
                    i6 = i10 & 8191;
                    i10 = i8;
                    while (true) {
                        i9 = i10 + 1;
                        charAt6 = zzjw.charAt(i10);
                        if (charAt6 < 55296) {
                            break;
                        }
                        i6 |= (charAt6 & 8191) << i12;
                        i12 += 13;
                        i10 = i9;
                    }
                    i10 = i6 | (charAt6 << i12);
                    i6 = i9;
                }
                iArr = new int[((i10 + i) + i11)];
                i3 = (i2 << 1) + i3;
                i8 = i6;
                i6 = i2;
                c = charAt2;
                i4 = i8;
            }
            Unsafe unsafe = zzacw;
            Object[] zzjx = zzjq.zzjx();
            Class cls2 = zzjq.zzjq().getClass();
            i9 = i3;
            int[] iArr2 = new int[(i5 * 3)];
            Object[] objArr = new Object[(i5 << 1)];
            int i13 = i10 + i;
            int i14 = i10;
            int i15 = i13;
            i = 0;
            int i16 = 0;
            while (i4 < length) {
                int i17;
                int i18;
                int i19;
                int i20;
                int i21;
                int i22;
                String str;
                Class cls3;
                char c3;
                char c4;
                int i23;
                int i24 = i4 + 1;
                i4 = zzjw.charAt(i4);
                char c5 = 55296;
                if (i4 >= 55296) {
                    i17 = 13;
                    i8 = i24;
                    i24 = i4 & 8191;
                    i4 = i8;
                    while (true) {
                        i18 = i4 + 1;
                        charAt2 = zzjw.charAt(i4);
                        if (charAt2 < c5) {
                            break;
                        }
                        i24 |= (charAt2 & 8191) << i17;
                        i17 += 13;
                        i4 = i18;
                        c5 = 55296;
                    }
                    i4 = i24 | (charAt2 << i17);
                    i19 = i18;
                } else {
                    i19 = i24;
                }
                i24 = i19 + 1;
                i19 = zzjw.charAt(i19);
                i17 = length;
                char c6 = 55296;
                if (i19 >= 55296) {
                    i18 = 13;
                    i8 = i24;
                    i24 = i19 & 8191;
                    i19 = i8;
                    while (true) {
                        i20 = i19 + 1;
                        c5 = zzjw.charAt(i19);
                        if (c5 < c6) {
                            break;
                        }
                        i24 |= (c5 & 8191) << i18;
                        i18 += 13;
                        i19 = i20;
                        c6 = 55296;
                    }
                    i19 = i24 | (c5 << i18);
                    length = i20;
                } else {
                    length = i24;
                }
                i24 = i10;
                i10 = i19 & 255;
                z = z2;
                if ((i19 & 1024) != 0) {
                    i21 = i + 1;
                    iArr[i] = i16;
                    i = i21;
                }
                int i25 = i;
                char charAt7;
                if (i10 >= 51) {
                    int i26;
                    Field field;
                    i21 = length + 1;
                    length = zzjw.charAt(length);
                    charAt = 55296;
                    if (length >= 55296) {
                        int i27;
                        length &= 8191;
                        i26 = 13;
                        while (true) {
                            i27 = i21 + 1;
                            charAt7 = zzjw.charAt(i21);
                            if (charAt7 < charAt) {
                                break;
                            }
                            length |= (charAt7 & 8191) << i26;
                            i26 += 13;
                            i21 = i27;
                            charAt = 55296;
                        }
                        length |= charAt7 << i26;
                        i21 = i27;
                    }
                    i = i10 - 51;
                    i26 = i21;
                    if (i == 9 || i == 17) {
                        i21 = 1;
                        i22 = i9 + 1;
                        objArr[((i16 / 3) << 1) + 1] = zzjx[i9];
                        i9 = i22;
                    } else {
                        if (i == 12 && (charAt3 & 1) == 1) {
                            i21 = i9 + 1;
                            objArr[((i16 / 3) << 1) + 1] = zzjx[i9];
                            i9 = i21;
                        }
                        i21 = 1;
                    }
                    length <<= i21;
                    Object obj = zzjx[length];
                    if (obj instanceof Field) {
                        field = (Field) obj;
                    } else {
                        field = zza(cls2, (String) obj);
                        zzjx[length] = field;
                    }
                    charAt7 = c;
                    i2 = (int) unsafe.objectFieldOffset(field);
                    length++;
                    obj = zzjx[length];
                    i20 = i2;
                    if (obj instanceof Field) {
                        field = (Field) obj;
                    } else {
                        field = zza(cls2, (String) obj);
                        zzjx[length] = field;
                    }
                    str = zzjw;
                    i = (int) unsafe.objectFieldOffset(field);
                    cls3 = cls2;
                    i22 = i9;
                    i2 = i20;
                    length = 0;
                    c3 = charAt7;
                    c4 = c2;
                    i7 = i4;
                    i4 = i26;
                } else {
                    charAt7 = c;
                    i = i9 + 1;
                    Field zza = zza(cls2, (String) zzjx[i9]);
                    c4 = c2;
                    if (i10 == 9 || i10 == 17) {
                        c3 = charAt7;
                        objArr[((i16 / 3) << 1) + 1] = zza.getType();
                    } else {
                        if (i10 == 27 || i10 == 49) {
                            c3 = charAt7;
                            i22 = i + 1;
                            objArr[((i16 / 3) << 1) + 1] = zzjx[i];
                        } else if (i10 == 12 || i10 == 30 || i10 == 44) {
                            c3 = charAt7;
                            if ((charAt3 & 1) == 1) {
                                i22 = i + 1;
                                objArr[((i16 / 3) << 1) + 1] = zzjx[i];
                            }
                        } else if (i10 == 50) {
                            i7 = i14 + 1;
                            iArr[i14] = i16;
                            i14 = (i16 / 3) << 1;
                            i20 = i + 1;
                            objArr[i14] = zzjx[i];
                            if ((i19 & 2048) != 0) {
                                i = i20 + 1;
                                objArr[i14 + 1] = zzjx[i20];
                                c3 = charAt7;
                                i14 = i7;
                            } else {
                                i14 = i7;
                                i = i20;
                                c3 = charAt7;
                            }
                        } else {
                            c3 = charAt7;
                        }
                        i7 = i4;
                        i = i22;
                        i2 = (int) unsafe.objectFieldOffset(zza);
                        if ((charAt3 & 1) == 1 || i10 > 17) {
                            str = zzjw;
                            cls3 = cls2;
                            i22 = i;
                            i21 = length;
                            length = 0;
                            i = 0;
                        } else {
                            Field field2;
                            i21 = length + 1;
                            length = zzjw.charAt(length);
                            if (length >= 55296) {
                                int i28;
                                length &= 8191;
                                int i29 = 13;
                                while (true) {
                                    i28 = i21 + 1;
                                    charAt7 = zzjw.charAt(i21);
                                    if (charAt7 < 55296) {
                                        break;
                                    }
                                    length |= (charAt7 & 8191) << i29;
                                    i29 += 13;
                                    i21 = i28;
                                }
                                length |= charAt7 << i29;
                                i21 = i28;
                            }
                            i22 = (i6 << 1) + (length / 32);
                            Object obj2 = zzjx[i22];
                            str = zzjw;
                            if (obj2 instanceof Field) {
                                field2 = (Field) obj2;
                            } else {
                                field2 = zza(cls2, (String) obj2);
                                zzjx[i22] = field2;
                            }
                            cls3 = cls2;
                            i22 = i;
                            i = (int) unsafe.objectFieldOffset(field2);
                            length %= 32;
                        }
                        if (i10 >= 18 && i10 <= 49) {
                            i23 = i15 + 1;
                            iArr[i15] = i2;
                            i15 = i23;
                        }
                        i4 = i21;
                    }
                    i7 = i4;
                    i2 = (int) unsafe.objectFieldOffset(zza);
                    if ((charAt3 & 1) == 1) {
                    }
                    str = zzjw;
                    cls3 = cls2;
                    i22 = i;
                    i21 = length;
                    length = 0;
                    i = 0;
                    i23 = i15 + 1;
                    iArr[i15] = i2;
                    i15 = i23;
                    i4 = i21;
                }
                i23 = i16 + 1;
                iArr2[i16] = i7;
                i21 = i23 + 1;
                iArr2[i23] = ((i10 << 20) | (((i19 & 256) != 0 ? 268435456 : 0) | ((i19 & 512) != 0 ? PropertyOptions.DELETE_EXISTING : 0))) | i2;
                i16 = i21 + 1;
                iArr2[i21] = (length << 20) | i;
                cls2 = cls3;
                c2 = c4;
                i10 = i24;
                i9 = i22;
                length = i17;
                z2 = z;
                c = c3;
                i = i25;
                zzjw = str;
            }
            z = z2;
            return new zzjg(iArr2, objArr, c, c2, zzjq.zzjq(), z2, false, iArr, i10, i13, zzjh, zzim, zzkk, zzhh, zziv);
        }
        int zzjo = ((zzkd) zzja2).zzjo();
        int i30 = zzd.zzaaw;
        throw new NoSuchMethodError();
    }

    private static Field zza(Class<?> cls, String str) {
        Class cls2;
        try {
            cls2 = cls2.getDeclaredField(str);
            return cls2;
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls2.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls2.getName();
            String arrays = Arrays.toString(declaredFields);
            StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 40) + String.valueOf(name).length()) + String.valueOf(arrays).length());
            stringBuilder.append("Field ");
            stringBuilder.append(str);
            stringBuilder.append(" for ");
            stringBuilder.append(name);
            stringBuilder.append(" not found. Known fields are ");
            stringBuilder.append(arrays);
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    public final T newInstance() {
        return this.zzadh.newInstance(this.zzacr);
    }

    /* JADX WARNING: Missing block: B:8:0x0038, code:
            if (com.google.android.gms.internal.firebase_auth.zzju.zze(com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6), com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:14:0x006a, code:
            if (com.google.android.gms.internal.firebase_auth.zzju.zze(com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6), com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:18:0x007e, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:22:0x0090, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:26:0x00a4, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:30:0x00b6, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:34:0x00c8, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:38:0x00da, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:42:0x00f0, code:
            if (com.google.android.gms.internal.firebase_auth.zzju.zze(com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6), com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:46:0x0106, code:
            if (com.google.android.gms.internal.firebase_auth.zzju.zze(com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6), com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:50:0x011c, code:
            if (com.google.android.gms.internal.firebase_auth.zzju.zze(com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6), com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:54:0x012e, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzm(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzm(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:58:0x0140, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:62:0x0154, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:66:0x0165, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:70:0x0178, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:74:0x018b, code:
            if (com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:78:0x01a4, code:
            if (java.lang.Float.floatToIntBits(com.google.android.gms.internal.firebase_auth.zzkq.zzn(r10, r6)) == java.lang.Float.floatToIntBits(com.google.android.gms.internal.firebase_auth.zzkq.zzn(r11, r6))) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:82:0x01bf, code:
            if (java.lang.Double.doubleToLongBits(com.google.android.gms.internal.firebase_auth.zzkq.zzo(r10, r6)) == java.lang.Double.doubleToLongBits(com.google.android.gms.internal.firebase_auth.zzkq.zzo(r11, r6))) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:83:0x01c1, code:
            r3 = false;
     */
    public final boolean equals(T r10, T r11) {
        /*
        r9 = this;
        r0 = r9.zzacx;
        r0 = r0.length;
        r1 = 0;
        r2 = 0;
    L_0x0005:
        r3 = 1;
        if (r2 >= r0) goto L_0x01c9;
    L_0x0008:
        r4 = r9.zzbb(r2);
        r5 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        r6 = r4 & r5;
        r6 = (long) r6;
        r8 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r4 = r4 & r8;
        r4 = r4 >>> 20;
        switch(r4) {
            case 0: goto L_0x01a7;
            case 1: goto L_0x018e;
            case 2: goto L_0x017b;
            case 3: goto L_0x0168;
            case 4: goto L_0x0157;
            case 5: goto L_0x0144;
            case 6: goto L_0x0132;
            case 7: goto L_0x0120;
            case 8: goto L_0x010a;
            case 9: goto L_0x00f4;
            case 10: goto L_0x00de;
            case 11: goto L_0x00cc;
            case 12: goto L_0x00ba;
            case 13: goto L_0x00a8;
            case 14: goto L_0x0094;
            case 15: goto L_0x0082;
            case 16: goto L_0x006e;
            case 17: goto L_0x0058;
            case 18: goto L_0x004a;
            case 19: goto L_0x004a;
            case 20: goto L_0x004a;
            case 21: goto L_0x004a;
            case 22: goto L_0x004a;
            case 23: goto L_0x004a;
            case 24: goto L_0x004a;
            case 25: goto L_0x004a;
            case 26: goto L_0x004a;
            case 27: goto L_0x004a;
            case 28: goto L_0x004a;
            case 29: goto L_0x004a;
            case 30: goto L_0x004a;
            case 31: goto L_0x004a;
            case 32: goto L_0x004a;
            case 33: goto L_0x004a;
            case 34: goto L_0x004a;
            case 35: goto L_0x004a;
            case 36: goto L_0x004a;
            case 37: goto L_0x004a;
            case 38: goto L_0x004a;
            case 39: goto L_0x004a;
            case 40: goto L_0x004a;
            case 41: goto L_0x004a;
            case 42: goto L_0x004a;
            case 43: goto L_0x004a;
            case 44: goto L_0x004a;
            case 45: goto L_0x004a;
            case 46: goto L_0x004a;
            case 47: goto L_0x004a;
            case 48: goto L_0x004a;
            case 49: goto L_0x004a;
            case 50: goto L_0x003c;
            case 51: goto L_0x001c;
            case 52: goto L_0x001c;
            case 53: goto L_0x001c;
            case 54: goto L_0x001c;
            case 55: goto L_0x001c;
            case 56: goto L_0x001c;
            case 57: goto L_0x001c;
            case 58: goto L_0x001c;
            case 59: goto L_0x001c;
            case 60: goto L_0x001c;
            case 61: goto L_0x001c;
            case 62: goto L_0x001c;
            case 63: goto L_0x001c;
            case 64: goto L_0x001c;
            case 65: goto L_0x001c;
            case 66: goto L_0x001c;
            case 67: goto L_0x001c;
            case 68: goto L_0x001c;
            default: goto L_0x001a;
        };
    L_0x001a:
        goto L_0x01c2;
    L_0x001c:
        r4 = r9.zzbc(r2);
        r4 = r4 & r5;
        r4 = (long) r4;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r4);
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r4);
        if (r8 != r4) goto L_0x01c1;
    L_0x002c:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6);
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r5);
        if (r4 != 0) goto L_0x01c2;
    L_0x003a:
        goto L_0x01c1;
    L_0x003c:
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6);
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zze(r3, r4);
        goto L_0x01c2;
    L_0x004a:
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6);
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zze(r3, r4);
        goto L_0x01c2;
    L_0x0058:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x005e:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6);
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r5);
        if (r4 != 0) goto L_0x01c2;
    L_0x006c:
        goto L_0x01c1;
    L_0x006e:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0074:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6);
        r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6);
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x01c2;
    L_0x0080:
        goto L_0x01c1;
    L_0x0082:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0088:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x0092:
        goto L_0x01c1;
    L_0x0094:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x009a:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6);
        r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6);
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x01c2;
    L_0x00a6:
        goto L_0x01c1;
    L_0x00a8:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x00ae:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x00b8:
        goto L_0x01c1;
    L_0x00ba:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x00c0:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x00ca:
        goto L_0x01c1;
    L_0x00cc:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x00d2:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x00dc:
        goto L_0x01c1;
    L_0x00de:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x00e4:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6);
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r5);
        if (r4 != 0) goto L_0x01c2;
    L_0x00f2:
        goto L_0x01c1;
    L_0x00f4:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x00fa:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6);
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r5);
        if (r4 != 0) goto L_0x01c2;
    L_0x0108:
        goto L_0x01c1;
    L_0x010a:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0110:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6);
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r5);
        if (r4 != 0) goto L_0x01c2;
    L_0x011e:
        goto L_0x01c1;
    L_0x0120:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0126:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x0130:
        goto L_0x01c1;
    L_0x0132:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0138:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x0142:
        goto L_0x01c1;
    L_0x0144:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x014a:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6);
        r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6);
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x01c2;
    L_0x0156:
        goto L_0x01c1;
    L_0x0157:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x015d:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x0167:
        goto L_0x01c1;
    L_0x0168:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x016e:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6);
        r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6);
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x01c2;
    L_0x017a:
        goto L_0x01c1;
    L_0x017b:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0181:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6);
        r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6);
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x01c2;
    L_0x018d:
        goto L_0x01c1;
    L_0x018e:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0194:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r10, r6);
        r4 = java.lang.Float.floatToIntBits(r4);
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r11, r6);
        r5 = java.lang.Float.floatToIntBits(r5);
        if (r4 == r5) goto L_0x01c2;
    L_0x01a6:
        goto L_0x01c1;
    L_0x01a7:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x01ad:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r10, r6);
        r4 = java.lang.Double.doubleToLongBits(r4);
        r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r11, r6);
        r6 = java.lang.Double.doubleToLongBits(r6);
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x01c2;
    L_0x01c1:
        r3 = 0;
    L_0x01c2:
        if (r3 != 0) goto L_0x01c5;
    L_0x01c4:
        return r1;
    L_0x01c5:
        r2 = r2 + 3;
        goto L_0x0005;
    L_0x01c9:
        r0 = r9.zzacs;
        r0 = r0.zzs(r10);
        r2 = r9.zzacs;
        r2 = r2.zzs(r11);
        r0 = r0.equals(r2);
        if (r0 != 0) goto L_0x01dc;
    L_0x01db:
        return r1;
    L_0x01dc:
        r0 = r9.zzact;
        if (r0 == 0) goto L_0x01f1;
    L_0x01e0:
        r0 = r9.zzacu;
        r10 = r0.zzd(r10);
        r0 = r9.zzacu;
        r11 = r0.zzd(r11);
        r10 = r10.equals(r11);
        return r10;
    L_0x01f1:
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.equals(java.lang.Object, java.lang.Object):boolean");
    }

    /* JADX WARNING: Missing block: B:73:0x01c3, code:
            r2 = (r2 * 53) + r7;
     */
    /* JADX WARNING: Missing block: B:83:0x0227, code:
            r2 = r2 + r3;
     */
    /* JADX WARNING: Missing block: B:84:0x0228, code:
            r1 = r1 + 3;
     */
    public final int hashCode(T r9) {
        /*
        r8 = this;
        r0 = r8.zzacx;
        r0 = r0.length;
        r1 = 0;
        r2 = 0;
    L_0x0005:
        if (r1 >= r0) goto L_0x022c;
    L_0x0007:
        r3 = r8.zzbb(r1);
        r4 = r8.zzacx;
        r4 = r4[r1];
        r5 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        r5 = r5 & r3;
        r5 = (long) r5;
        r7 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r3 = r3 & r7;
        r3 = r3 >>> 20;
        r7 = 37;
        switch(r3) {
            case 0: goto L_0x0219;
            case 1: goto L_0x020e;
            case 2: goto L_0x0203;
            case 3: goto L_0x01f8;
            case 4: goto L_0x01f1;
            case 5: goto L_0x01e6;
            case 6: goto L_0x01df;
            case 7: goto L_0x01d4;
            case 8: goto L_0x01c7;
            case 9: goto L_0x01b9;
            case 10: goto L_0x01ad;
            case 11: goto L_0x01a5;
            case 12: goto L_0x019d;
            case 13: goto L_0x0195;
            case 14: goto L_0x0189;
            case 15: goto L_0x0181;
            case 16: goto L_0x0175;
            case 17: goto L_0x016a;
            case 18: goto L_0x015e;
            case 19: goto L_0x015e;
            case 20: goto L_0x015e;
            case 21: goto L_0x015e;
            case 22: goto L_0x015e;
            case 23: goto L_0x015e;
            case 24: goto L_0x015e;
            case 25: goto L_0x015e;
            case 26: goto L_0x015e;
            case 27: goto L_0x015e;
            case 28: goto L_0x015e;
            case 29: goto L_0x015e;
            case 30: goto L_0x015e;
            case 31: goto L_0x015e;
            case 32: goto L_0x015e;
            case 33: goto L_0x015e;
            case 34: goto L_0x015e;
            case 35: goto L_0x015e;
            case 36: goto L_0x015e;
            case 37: goto L_0x015e;
            case 38: goto L_0x015e;
            case 39: goto L_0x015e;
            case 40: goto L_0x015e;
            case 41: goto L_0x015e;
            case 42: goto L_0x015e;
            case 43: goto L_0x015e;
            case 44: goto L_0x015e;
            case 45: goto L_0x015e;
            case 46: goto L_0x015e;
            case 47: goto L_0x015e;
            case 48: goto L_0x015e;
            case 49: goto L_0x015e;
            case 50: goto L_0x0152;
            case 51: goto L_0x013c;
            case 52: goto L_0x012a;
            case 53: goto L_0x0118;
            case 54: goto L_0x0106;
            case 55: goto L_0x00f8;
            case 56: goto L_0x00e6;
            case 57: goto L_0x00d8;
            case 58: goto L_0x00c6;
            case 59: goto L_0x00b2;
            case 60: goto L_0x00a0;
            case 61: goto L_0x008e;
            case 62: goto L_0x0080;
            case 63: goto L_0x0072;
            case 64: goto L_0x0064;
            case 65: goto L_0x0052;
            case 66: goto L_0x0044;
            case 67: goto L_0x0032;
            case 68: goto L_0x0020;
            default: goto L_0x001e;
        };
    L_0x001e:
        goto L_0x0228;
    L_0x0020:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x0026:
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5);
        r2 = r2 * 53;
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x0032:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x0038:
        r2 = r2 * 53;
        r3 = zzi(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
        goto L_0x0227;
    L_0x0044:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x004a:
        r2 = r2 * 53;
        r3 = zzh(r9, r5);
        goto L_0x0227;
    L_0x0052:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x0058:
        r2 = r2 * 53;
        r3 = zzi(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
        goto L_0x0227;
    L_0x0064:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x006a:
        r2 = r2 * 53;
        r3 = zzh(r9, r5);
        goto L_0x0227;
    L_0x0072:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x0078:
        r2 = r2 * 53;
        r3 = zzh(r9, r5);
        goto L_0x0227;
    L_0x0080:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x0086:
        r2 = r2 * 53;
        r3 = zzh(r9, r5);
        goto L_0x0227;
    L_0x008e:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x0094:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5);
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x00a0:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x00a6:
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5);
        r2 = r2 * 53;
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x00b2:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x00b8:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5);
        r3 = (java.lang.String) r3;
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x00c6:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x00cc:
        r2 = r2 * 53;
        r3 = zzj(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzv(r3);
        goto L_0x0227;
    L_0x00d8:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x00de:
        r2 = r2 * 53;
        r3 = zzh(r9, r5);
        goto L_0x0227;
    L_0x00e6:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x00ec:
        r2 = r2 * 53;
        r3 = zzi(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
        goto L_0x0227;
    L_0x00f8:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x00fe:
        r2 = r2 * 53;
        r3 = zzh(r9, r5);
        goto L_0x0227;
    L_0x0106:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x010c:
        r2 = r2 * 53;
        r3 = zzi(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
        goto L_0x0227;
    L_0x0118:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x011e:
        r2 = r2 * 53;
        r3 = zzi(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
        goto L_0x0227;
    L_0x012a:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x0130:
        r2 = r2 * 53;
        r3 = zzg(r9, r5);
        r3 = java.lang.Float.floatToIntBits(r3);
        goto L_0x0227;
    L_0x013c:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x0142:
        r2 = r2 * 53;
        r3 = zzf(r9, r5);
        r3 = java.lang.Double.doubleToLongBits(r3);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
        goto L_0x0227;
    L_0x0152:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5);
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x015e:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5);
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x016a:
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5);
        if (r3 == 0) goto L_0x01c3;
    L_0x0170:
        r7 = r3.hashCode();
        goto L_0x01c3;
    L_0x0175:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
        goto L_0x0227;
    L_0x0181:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5);
        goto L_0x0227;
    L_0x0189:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
        goto L_0x0227;
    L_0x0195:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5);
        goto L_0x0227;
    L_0x019d:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5);
        goto L_0x0227;
    L_0x01a5:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5);
        goto L_0x0227;
    L_0x01ad:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5);
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x01b9:
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5);
        if (r3 == 0) goto L_0x01c3;
    L_0x01bf:
        r7 = r3.hashCode();
    L_0x01c3:
        r2 = r2 * 53;
        r2 = r2 + r7;
        goto L_0x0228;
    L_0x01c7:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5);
        r3 = (java.lang.String) r3;
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x01d4:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzv(r3);
        goto L_0x0227;
    L_0x01df:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5);
        goto L_0x0227;
    L_0x01e6:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
        goto L_0x0227;
    L_0x01f1:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5);
        goto L_0x0227;
    L_0x01f8:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
        goto L_0x0227;
    L_0x0203:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r9, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
        goto L_0x0227;
    L_0x020e:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r9, r5);
        r3 = java.lang.Float.floatToIntBits(r3);
        goto L_0x0227;
    L_0x0219:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r9, r5);
        r3 = java.lang.Double.doubleToLongBits(r3);
        r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3);
    L_0x0227:
        r2 = r2 + r3;
    L_0x0228:
        r1 = r1 + 3;
        goto L_0x0005;
    L_0x022c:
        r2 = r2 * 53;
        r0 = r8.zzacs;
        r0 = r0.zzs(r9);
        r0 = r0.hashCode();
        r2 = r2 + r0;
        r0 = r8.zzact;
        if (r0 == 0) goto L_0x024a;
    L_0x023d:
        r2 = r2 * 53;
        r0 = r8.zzacu;
        r9 = r0.zzd(r9);
        r9 = r9.hashCode();
        r2 = r2 + r9;
    L_0x024a:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.hashCode(java.lang.Object):int");
    }

    public final void zzd(T t, T t2) {
        if (t2 != null) {
            for (int i = 0; i < this.zzacx.length; i += 3) {
                int zzbb = zzbb(i);
                long j = (long) (1048575 & zzbb);
                int i2 = this.zzacx[i];
                switch ((zzbb & 267386880) >>> 20) {
                    case 0:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzo(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 1:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzn(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 2:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzl(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 3:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzl(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 4:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 5:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzl(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 6:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 7:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzm(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 8:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzp(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 9:
                        zza((Object) t, (Object) t2, i);
                        break;
                    case 10:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzp(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 11:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 12:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 13:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 14:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzl(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 15:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 16:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzl(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 17:
                        zza((Object) t, (Object) t2, i);
                        break;
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        this.zzadi.zza(t, t2, j);
                        break;
                    case 50:
                        zzju.zza(this.zzadj, (Object) t, (Object) t2, j);
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                        if (!zza((Object) t2, i2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzp(t2, j));
                        zzb((Object) t, i2, i);
                        break;
                    case 60:
                        zzb((Object) t, (Object) t2, i);
                        break;
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                        if (!zza((Object) t2, i2, i)) {
                            break;
                        }
                        zzkq.zza((Object) t, j, zzkq.zzp(t2, j));
                        zzb((Object) t, i2, i);
                        break;
                    case 68:
                        zzb((Object) t, (Object) t2, i);
                        break;
                    default:
                        break;
                }
            }
            if (!this.zzadc) {
                zzju.zza(this.zzacs, (Object) t, (Object) t2);
                if (this.zzact) {
                    zzju.zza(this.zzacu, (Object) t, (Object) t2);
                    return;
                }
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    private final void zza(T t, T t2, int i) {
        long zzbb = (long) (zzbb(i) & 1048575);
        if (zza((Object) t2, i)) {
            Object zzp = zzkq.zzp(t, zzbb);
            Object zzp2 = zzkq.zzp(t2, zzbb);
            if (zzp == null || zzp2 == null) {
                if (zzp2 != null) {
                    zzkq.zza((Object) t, zzbb, zzp2);
                    zzb((Object) t, i);
                }
                return;
            }
            zzkq.zza((Object) t, zzbb, zzht.zzb(zzp, zzp2));
            zzb((Object) t, i);
        }
    }

    private final void zzb(T t, T t2, int i) {
        int zzbb = zzbb(i);
        int i2 = this.zzacx[i];
        long j = (long) (zzbb & 1048575);
        if (zza((Object) t2, i2, i)) {
            Object zzp = zzkq.zzp(t, j);
            Object zzp2 = zzkq.zzp(t2, j);
            if (zzp == null || zzp2 == null) {
                if (zzp2 != null) {
                    zzkq.zza((Object) t, j, zzp2);
                    zzb((Object) t, i2, i);
                }
                return;
            }
            zzkq.zza((Object) t, j, zzht.zzb(zzp, zzp2));
            zzb((Object) t, i2, i);
        }
    }

    /* JADX WARNING: Missing block: B:398:0x0834, code:
            r8 = (r8 + r9) + r4;
     */
    /* JADX WARNING: Missing block: B:415:0x0900, code:
            r5 = r5 + r4;
     */
    /* JADX WARNING: Missing block: B:421:0x0915, code:
            r13 = 0;
     */
    /* JADX WARNING: Missing block: B:437:0x095a, code:
            r5 = r5 + r8;
     */
    /* JADX WARNING: Missing block: B:476:0x0a11, code:
            r5 = r5 + r8;
     */
    public final int zzq(T r20) {
        /*
        r19 = this;
        r0 = r19;
        r1 = r20;
        r2 = r0.zzadc;
        r3 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r4 = 0;
        r7 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        r8 = 1;
        r9 = 0;
        r11 = 0;
        if (r2 == 0) goto L_0x04f2;
    L_0x0012:
        r2 = zzacw;
        r12 = 0;
        r13 = 0;
    L_0x0016:
        r14 = r0.zzacx;
        r14 = r14.length;
        if (r12 >= r14) goto L_0x04ea;
    L_0x001b:
        r14 = r0.zzbb(r12);
        r15 = r14 & r3;
        r15 = r15 >>> 20;
        r3 = r0.zzacx;
        r3 = r3[r12];
        r14 = r14 & r7;
        r5 = (long) r14;
        r14 = com.google.android.gms.internal.firebase_auth.zzhn.DOUBLE_LIST_PACKED;
        r14 = r14.id();
        if (r15 < r14) goto L_0x0041;
    L_0x0031:
        r14 = com.google.android.gms.internal.firebase_auth.zzhn.SINT64_LIST_PACKED;
        r14 = r14.id();
        if (r15 > r14) goto L_0x0041;
    L_0x0039:
        r14 = r0.zzacx;
        r17 = r12 + 2;
        r14 = r14[r17];
        r14 = r14 & r7;
        goto L_0x0042;
    L_0x0041:
        r14 = 0;
    L_0x0042:
        switch(r15) {
            case 0: goto L_0x04d6;
            case 1: goto L_0x04ca;
            case 2: goto L_0x04ba;
            case 3: goto L_0x04aa;
            case 4: goto L_0x049a;
            case 5: goto L_0x048e;
            case 6: goto L_0x0482;
            case 7: goto L_0x0476;
            case 8: goto L_0x0458;
            case 9: goto L_0x0444;
            case 10: goto L_0x0433;
            case 11: goto L_0x0424;
            case 12: goto L_0x0415;
            case 13: goto L_0x040a;
            case 14: goto L_0x03ff;
            case 15: goto L_0x03f0;
            case 16: goto L_0x03e1;
            case 17: goto L_0x03cc;
            case 18: goto L_0x03c1;
            case 19: goto L_0x03b8;
            case 20: goto L_0x03af;
            case 21: goto L_0x03a6;
            case 22: goto L_0x039d;
            case 23: goto L_0x0394;
            case 24: goto L_0x038b;
            case 25: goto L_0x0382;
            case 26: goto L_0x0379;
            case 27: goto L_0x036c;
            case 28: goto L_0x0363;
            case 29: goto L_0x035a;
            case 30: goto L_0x0350;
            case 31: goto L_0x0346;
            case 32: goto L_0x033c;
            case 33: goto L_0x0332;
            case 34: goto L_0x0328;
            case 35: goto L_0x0308;
            case 36: goto L_0x02eb;
            case 37: goto L_0x02ce;
            case 38: goto L_0x02b1;
            case 39: goto L_0x0293;
            case 40: goto L_0x0275;
            case 41: goto L_0x0257;
            case 42: goto L_0x0239;
            case 43: goto L_0x021b;
            case 44: goto L_0x01fd;
            case 45: goto L_0x01df;
            case 46: goto L_0x01c1;
            case 47: goto L_0x01a3;
            case 48: goto L_0x0185;
            case 49: goto L_0x0177;
            case 50: goto L_0x0167;
            case 51: goto L_0x0159;
            case 52: goto L_0x014d;
            case 53: goto L_0x013d;
            case 54: goto L_0x012d;
            case 55: goto L_0x011d;
            case 56: goto L_0x0111;
            case 57: goto L_0x0105;
            case 58: goto L_0x00f9;
            case 59: goto L_0x00db;
            case 60: goto L_0x00c7;
            case 61: goto L_0x00b5;
            case 62: goto L_0x00a5;
            case 63: goto L_0x0095;
            case 64: goto L_0x0089;
            case 65: goto L_0x007d;
            case 66: goto L_0x006d;
            case 67: goto L_0x005d;
            case 68: goto L_0x0047;
            default: goto L_0x0045;
        };
    L_0x0045:
        goto L_0x04e4;
    L_0x0047:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x004d:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5);
        r5 = (com.google.android.gms.internal.firebase_auth.zzjc) r5;
        r6 = r0.zzay(r12);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5, r6);
        goto L_0x03c9;
    L_0x005d:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0063:
        r5 = zzi(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzf(r3, r5);
        goto L_0x03c9;
    L_0x006d:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0073:
        r5 = zzh(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzl(r3, r5);
        goto L_0x03c9;
    L_0x007d:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0083:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzh(r3, r9);
        goto L_0x03c9;
    L_0x0089:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x008f:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzn(r3, r11);
        goto L_0x03c9;
    L_0x0095:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x009b:
        r5 = zzh(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzo(r3, r5);
        goto L_0x03c9;
    L_0x00a5:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x00ab:
        r5 = zzh(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzk(r3, r5);
        goto L_0x03c9;
    L_0x00b5:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x00bb:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5);
        r5 = (com.google.android.gms.internal.firebase_auth.zzgf) r5;
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5);
        goto L_0x03c9;
    L_0x00c7:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x00cd:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5);
        r6 = r0.zzay(r12);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r3, r5, r6);
        goto L_0x03c9;
    L_0x00db:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x00e1:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5);
        r6 = r5 instanceof com.google.android.gms.internal.firebase_auth.zzgf;
        if (r6 == 0) goto L_0x00f1;
    L_0x00e9:
        r5 = (com.google.android.gms.internal.firebase_auth.zzgf) r5;
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5);
        goto L_0x03c9;
    L_0x00f1:
        r5 = (java.lang.String) r5;
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r5);
        goto L_0x03c9;
    L_0x00f9:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x00ff:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r3, r8);
        goto L_0x03c9;
    L_0x0105:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x010b:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzm(r3, r11);
        goto L_0x03c9;
    L_0x0111:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0117:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzg(r3, r9);
        goto L_0x03c9;
    L_0x011d:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0123:
        r5 = zzh(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzj(r3, r5);
        goto L_0x03c9;
    L_0x012d:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0133:
        r5 = zzi(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zze(r3, r5);
        goto L_0x03c9;
    L_0x013d:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0143:
        r5 = zzi(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r3, r5);
        goto L_0x03c9;
    L_0x014d:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0153:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r4);
        goto L_0x03c9;
    L_0x0159:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x015f:
        r5 = 0;
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r5);
        goto L_0x03c9;
    L_0x0167:
        r14 = r0.zzadj;
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5);
        r6 = r0.zzaz(r12);
        r3 = r14.zzb(r3, r5, r6);
        goto L_0x03c9;
    L_0x0177:
        r5 = zze(r1, r5);
        r6 = r0.zzay(r12);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzd(r3, r5, r6);
        goto L_0x03c9;
    L_0x0185:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzz(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0191:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x0199;
    L_0x0195:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x0199:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x01a3:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzad(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x01af:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x01b7;
    L_0x01b3:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x01b7:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x01c1:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x01cd:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x01d5;
    L_0x01d1:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x01d5:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x01df:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x01eb:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x01f3;
    L_0x01ef:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x01f3:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x01fd:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzaa(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0209:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x0211;
    L_0x020d:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x0211:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x021b:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzac(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0227:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x022f;
    L_0x022b:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x022f:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x0239:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzag(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0245:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x024d;
    L_0x0249:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x024d:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x0257:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0263:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x026b;
    L_0x0267:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x026b:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x0275:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0281:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x0289;
    L_0x0285:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x0289:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x0293:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzab(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x029f:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x02a7;
    L_0x02a3:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x02a7:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x02b1:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzy(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x02bd:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x02c5;
    L_0x02c1:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x02c5:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x02ce:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzx(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x02da:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x02e2;
    L_0x02de:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x02e2:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x02eb:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x02f7:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x02ff;
    L_0x02fb:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x02ff:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
        goto L_0x0324;
    L_0x0308:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0314:
        r6 = r0.zzadd;
        if (r6 == 0) goto L_0x031c;
    L_0x0318:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x031c:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3);
        r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5);
    L_0x0324:
        r3 = r3 + r6;
        r3 = r3 + r5;
        goto L_0x03c9;
    L_0x0328:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzq(r3, r5, r11);
        goto L_0x03c9;
    L_0x0332:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzu(r3, r5, r11);
        goto L_0x03c9;
    L_0x033c:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r3, r5, r11);
        goto L_0x03c9;
    L_0x0346:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r3, r5, r11);
        goto L_0x03c9;
    L_0x0350:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzr(r3, r5, r11);
        goto L_0x03c9;
    L_0x035a:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzt(r3, r5, r11);
        goto L_0x03c9;
    L_0x0363:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzd(r3, r5);
        goto L_0x03c9;
    L_0x036c:
        r5 = zze(r1, r5);
        r6 = r0.zzay(r12);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r3, r5, r6);
        goto L_0x03c9;
    L_0x0379:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r3, r5);
        goto L_0x03c9;
    L_0x0382:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzx(r3, r5, r11);
        goto L_0x03c9;
    L_0x038b:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r3, r5, r11);
        goto L_0x03c9;
    L_0x0394:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r3, r5, r11);
        goto L_0x03c9;
    L_0x039d:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzs(r3, r5, r11);
        goto L_0x03c9;
    L_0x03a6:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzp(r3, r5, r11);
        goto L_0x03c9;
    L_0x03af:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzo(r3, r5, r11);
        goto L_0x03c9;
    L_0x03b8:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r3, r5, r11);
        goto L_0x03c9;
    L_0x03c1:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r3, r5, r11);
    L_0x03c9:
        r13 = r13 + r3;
        goto L_0x04e4;
    L_0x03cc:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x03d2:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5);
        r5 = (com.google.android.gms.internal.firebase_auth.zzjc) r5;
        r6 = r0.zzay(r12);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5, r6);
        goto L_0x03c9;
    L_0x03e1:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x03e7:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzf(r3, r5);
        goto L_0x03c9;
    L_0x03f0:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x03f6:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzl(r3, r5);
        goto L_0x03c9;
    L_0x03ff:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0405:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzh(r3, r9);
        goto L_0x03c9;
    L_0x040a:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0410:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzn(r3, r11);
        goto L_0x03c9;
    L_0x0415:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x041b:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzo(r3, r5);
        goto L_0x03c9;
    L_0x0424:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x042a:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzk(r3, r5);
        goto L_0x03c9;
    L_0x0433:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0439:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5);
        r5 = (com.google.android.gms.internal.firebase_auth.zzgf) r5;
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5);
        goto L_0x03c9;
    L_0x0444:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x044a:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5);
        r6 = r0.zzay(r12);
        r3 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r3, r5, r6);
        goto L_0x03c9;
    L_0x0458:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x045e:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5);
        r6 = r5 instanceof com.google.android.gms.internal.firebase_auth.zzgf;
        if (r6 == 0) goto L_0x046e;
    L_0x0466:
        r5 = (com.google.android.gms.internal.firebase_auth.zzgf) r5;
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5);
        goto L_0x03c9;
    L_0x046e:
        r5 = (java.lang.String) r5;
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r5);
        goto L_0x03c9;
    L_0x0476:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x047c:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r3, r8);
        goto L_0x03c9;
    L_0x0482:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0488:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzm(r3, r11);
        goto L_0x03c9;
    L_0x048e:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0494:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzg(r3, r9);
        goto L_0x03c9;
    L_0x049a:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x04a0:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzj(r3, r5);
        goto L_0x03c9;
    L_0x04aa:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x04b0:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zze(r3, r5);
        goto L_0x03c9;
    L_0x04ba:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x04c0:
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r1, r5);
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r3, r5);
        goto L_0x03c9;
    L_0x04ca:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x04d0:
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r4);
        goto L_0x03c9;
    L_0x04d6:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x04dc:
        r5 = 0;
        r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r5);
        goto L_0x03c9;
    L_0x04e4:
        r12 = r12 + 3;
        r3 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        goto L_0x0016;
    L_0x04ea:
        r2 = r0.zzacs;
        r1 = zza(r2, r1);
        r13 = r13 + r1;
        return r13;
    L_0x04f2:
        r2 = zzacw;
        r3 = -1;
        r3 = 0;
        r5 = 0;
        r6 = -1;
        r12 = 0;
    L_0x04f9:
        r13 = r0.zzacx;
        r13 = r13.length;
        if (r3 >= r13) goto L_0x0a3f;
    L_0x04fe:
        r13 = r0.zzbb(r3);
        r14 = r0.zzacx;
        r15 = r14[r3];
        r16 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r17 = r13 & r16;
        r4 = r17 >>> 20;
        r11 = 17;
        if (r4 > r11) goto L_0x0525;
    L_0x0510:
        r11 = r3 + 2;
        r11 = r14[r11];
        r14 = r11 & r7;
        r18 = r11 >>> 20;
        r18 = r8 << r18;
        if (r14 == r6) goto L_0x0522;
    L_0x051c:
        r8 = (long) r14;
        r12 = r2.getInt(r1, r8);
        goto L_0x0523;
    L_0x0522:
        r14 = r6;
    L_0x0523:
        r6 = r14;
        goto L_0x0545;
    L_0x0525:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x0542;
    L_0x0529:
        r8 = com.google.android.gms.internal.firebase_auth.zzhn.DOUBLE_LIST_PACKED;
        r8 = r8.id();
        if (r4 < r8) goto L_0x0542;
    L_0x0531:
        r8 = com.google.android.gms.internal.firebase_auth.zzhn.SINT64_LIST_PACKED;
        r8 = r8.id();
        if (r4 > r8) goto L_0x0542;
    L_0x0539:
        r8 = r0.zzacx;
        r9 = r3 + 2;
        r8 = r8[r9];
        r11 = r8 & r7;
        goto L_0x0543;
    L_0x0542:
        r11 = 0;
    L_0x0543:
        r18 = 0;
    L_0x0545:
        r8 = r13 & r7;
        r8 = (long) r8;
        switch(r4) {
            case 0: goto L_0x0a24;
            case 1: goto L_0x0a13;
            case 2: goto L_0x0a01;
            case 3: goto L_0x09f0;
            case 4: goto L_0x09df;
            case 5: goto L_0x09cf;
            case 6: goto L_0x09bf;
            case 7: goto L_0x09b3;
            case 8: goto L_0x0997;
            case 9: goto L_0x0985;
            case 10: goto L_0x0976;
            case 11: goto L_0x0969;
            case 12: goto L_0x095c;
            case 13: goto L_0x0951;
            case 14: goto L_0x0946;
            case 15: goto L_0x0939;
            case 16: goto L_0x092c;
            case 17: goto L_0x0919;
            case 18: goto L_0x0905;
            case 19: goto L_0x08f5;
            case 20: goto L_0x08e9;
            case 21: goto L_0x08dd;
            case 22: goto L_0x08d1;
            case 23: goto L_0x08c5;
            case 24: goto L_0x08b9;
            case 25: goto L_0x08ad;
            case 26: goto L_0x08a2;
            case 27: goto L_0x0892;
            case 28: goto L_0x0886;
            case 29: goto L_0x0879;
            case 30: goto L_0x086c;
            case 31: goto L_0x085f;
            case 32: goto L_0x0852;
            case 33: goto L_0x0845;
            case 34: goto L_0x0838;
            case 35: goto L_0x0818;
            case 36: goto L_0x07fb;
            case 37: goto L_0x07de;
            case 38: goto L_0x07c1;
            case 39: goto L_0x07a3;
            case 40: goto L_0x0785;
            case 41: goto L_0x0767;
            case 42: goto L_0x0749;
            case 43: goto L_0x072b;
            case 44: goto L_0x070d;
            case 45: goto L_0x06ef;
            case 46: goto L_0x06d1;
            case 47: goto L_0x06b3;
            case 48: goto L_0x0695;
            case 49: goto L_0x0685;
            case 50: goto L_0x0675;
            case 51: goto L_0x0667;
            case 52: goto L_0x065a;
            case 53: goto L_0x064a;
            case 54: goto L_0x063a;
            case 55: goto L_0x062a;
            case 56: goto L_0x061c;
            case 57: goto L_0x060f;
            case 58: goto L_0x0602;
            case 59: goto L_0x05e4;
            case 60: goto L_0x05d0;
            case 61: goto L_0x05be;
            case 62: goto L_0x05ae;
            case 63: goto L_0x059e;
            case 64: goto L_0x0591;
            case 65: goto L_0x0583;
            case 66: goto L_0x0573;
            case 67: goto L_0x0563;
            case 68: goto L_0x054d;
            default: goto L_0x054b;
        };
    L_0x054b:
        goto L_0x0911;
    L_0x054d:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0553:
        r4 = r2.getObject(r1, r8);
        r4 = (com.google.android.gms.internal.firebase_auth.zzjc) r4;
        r8 = r0.zzay(r3);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4, r8);
        goto L_0x0910;
    L_0x0563:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0569:
        r8 = zzi(r1, r8);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzf(r15, r8);
        goto L_0x0910;
    L_0x0573:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0579:
        r4 = zzh(r1, r8);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzl(r15, r4);
        goto L_0x0910;
    L_0x0583:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0589:
        r8 = 0;
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzh(r15, r8);
        goto L_0x0910;
    L_0x0591:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0597:
        r4 = 0;
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzn(r15, r4);
        goto L_0x095a;
    L_0x059e:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x05a4:
        r4 = zzh(r1, r8);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzo(r15, r4);
        goto L_0x0910;
    L_0x05ae:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x05b4:
        r4 = zzh(r1, r8);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzk(r15, r4);
        goto L_0x0910;
    L_0x05be:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x05c4:
        r4 = r2.getObject(r1, r8);
        r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4);
        goto L_0x0910;
    L_0x05d0:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x05d6:
        r4 = r2.getObject(r1, r8);
        r8 = r0.zzay(r3);
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r15, r4, r8);
        goto L_0x0910;
    L_0x05e4:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x05ea:
        r4 = r2.getObject(r1, r8);
        r8 = r4 instanceof com.google.android.gms.internal.firebase_auth.zzgf;
        if (r8 == 0) goto L_0x05fa;
    L_0x05f2:
        r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4);
        goto L_0x0910;
    L_0x05fa:
        r4 = (java.lang.String) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r4);
        goto L_0x0910;
    L_0x0602:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0608:
        r4 = 1;
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r15, r4);
        goto L_0x095a;
    L_0x060f:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0615:
        r4 = 0;
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzm(r15, r4);
        goto L_0x095a;
    L_0x061c:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0622:
        r8 = 0;
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzg(r15, r8);
        goto L_0x0910;
    L_0x062a:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0630:
        r4 = zzh(r1, r8);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzj(r15, r4);
        goto L_0x0910;
    L_0x063a:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0640:
        r8 = zzi(r1, r8);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zze(r15, r8);
        goto L_0x0910;
    L_0x064a:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0650:
        r8 = zzi(r1, r8);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r15, r8);
        goto L_0x0910;
    L_0x065a:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0660:
        r4 = 0;
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r4);
        goto L_0x095a;
    L_0x0667:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x066d:
        r8 = 0;
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r8);
        goto L_0x0910;
    L_0x0675:
        r4 = r0.zzadj;
        r8 = r2.getObject(r1, r8);
        r9 = r0.zzaz(r3);
        r4 = r4.zzb(r15, r8, r9);
        goto L_0x0910;
    L_0x0685:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r8 = r0.zzay(r3);
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzd(r15, r4, r8);
        goto L_0x0910;
    L_0x0695:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzz(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x06a1:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x06a9;
    L_0x06a5:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x06a9:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x06b3:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzad(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x06bf:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x06c7;
    L_0x06c3:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x06c7:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x06d1:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x06dd:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x06e5;
    L_0x06e1:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x06e5:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x06ef:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x06fb:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x0703;
    L_0x06ff:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x0703:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x070d:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzaa(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0719:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x0721;
    L_0x071d:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x0721:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x072b:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzac(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0737:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x073f;
    L_0x073b:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x073f:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x0749:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzag(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0755:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x075d;
    L_0x0759:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x075d:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x0767:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0773:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x077b;
    L_0x0777:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x077b:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x0785:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0791:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x0799;
    L_0x0795:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x0799:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x07a3:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzab(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x07af:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x07b7;
    L_0x07b3:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x07b7:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x07c1:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzy(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x07cd:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x07d5;
    L_0x07d1:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x07d5:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x07de:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzx(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x07ea:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x07f2;
    L_0x07ee:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x07f2:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x07fb:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0807:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x080f;
    L_0x080b:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x080f:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
        goto L_0x0834;
    L_0x0818:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0824:
        r8 = r0.zzadd;
        if (r8 == 0) goto L_0x082c;
    L_0x0828:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x082c:
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15);
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4);
    L_0x0834:
        r8 = r8 + r9;
        r8 = r8 + r4;
        goto L_0x095a;
    L_0x0838:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r10 = 0;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzq(r15, r4, r10);
        goto L_0x0900;
    L_0x0845:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzu(r15, r4, r10);
        goto L_0x0900;
    L_0x0852:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r15, r4, r10);
        goto L_0x0900;
    L_0x085f:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r15, r4, r10);
        goto L_0x0900;
    L_0x086c:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzr(r15, r4, r10);
        goto L_0x0900;
    L_0x0879:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzt(r15, r4, r10);
        goto L_0x0910;
    L_0x0886:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzd(r15, r4);
        goto L_0x0910;
    L_0x0892:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r8 = r0.zzay(r3);
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r15, r4, r8);
        goto L_0x0910;
    L_0x08a2:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r15, r4);
        goto L_0x0910;
    L_0x08ad:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r10 = 0;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzx(r15, r4, r10);
        goto L_0x0900;
    L_0x08b9:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r15, r4, r10);
        goto L_0x0900;
    L_0x08c5:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r15, r4, r10);
        goto L_0x0900;
    L_0x08d1:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzs(r15, r4, r10);
        goto L_0x0900;
    L_0x08dd:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzp(r15, r4, r10);
        goto L_0x0900;
    L_0x08e9:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzo(r15, r4, r10);
        goto L_0x0900;
    L_0x08f5:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r15, r4, r10);
    L_0x0900:
        r5 = r5 + r4;
        r4 = 1;
    L_0x0902:
        r7 = 0;
        goto L_0x0915;
    L_0x0905:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r15, r4, r10);
    L_0x0910:
        r5 = r5 + r4;
    L_0x0911:
        r4 = 1;
    L_0x0912:
        r7 = 0;
        r10 = 0;
    L_0x0915:
        r13 = 0;
        goto L_0x0a34;
    L_0x0919:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x091d:
        r4 = r2.getObject(r1, r8);
        r4 = (com.google.android.gms.internal.firebase_auth.zzjc) r4;
        r8 = r0.zzay(r3);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4, r8);
        goto L_0x0910;
    L_0x092c:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x0930:
        r8 = r2.getLong(r1, r8);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzf(r15, r8);
        goto L_0x0910;
    L_0x0939:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x093d:
        r4 = r2.getInt(r1, r8);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzl(r15, r4);
        goto L_0x0910;
    L_0x0946:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x094a:
        r8 = 0;
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzh(r15, r8);
        goto L_0x0910;
    L_0x0951:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x0955:
        r4 = 0;
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzn(r15, r4);
    L_0x095a:
        r5 = r5 + r8;
        goto L_0x0911;
    L_0x095c:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x0960:
        r4 = r2.getInt(r1, r8);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzo(r15, r4);
        goto L_0x0910;
    L_0x0969:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x096d:
        r4 = r2.getInt(r1, r8);
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzk(r15, r4);
        goto L_0x0910;
    L_0x0976:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x097a:
        r4 = r2.getObject(r1, r8);
        r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4);
        goto L_0x0910;
    L_0x0985:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x0989:
        r4 = r2.getObject(r1, r8);
        r8 = r0.zzay(r3);
        r4 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r15, r4, r8);
        goto L_0x0910;
    L_0x0997:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x099b:
        r4 = r2.getObject(r1, r8);
        r8 = r4 instanceof com.google.android.gms.internal.firebase_auth.zzgf;
        if (r8 == 0) goto L_0x09ab;
    L_0x09a3:
        r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4);
        goto L_0x0910;
    L_0x09ab:
        r4 = (java.lang.String) r4;
        r4 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r4);
        goto L_0x0910;
    L_0x09b3:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x09b7:
        r4 = 1;
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r15, r4);
        r5 = r5 + r8;
        goto L_0x0912;
    L_0x09bf:
        r4 = 1;
        r8 = r12 & r18;
        if (r8 == 0) goto L_0x09cc;
    L_0x09c4:
        r10 = 0;
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzm(r15, r10);
        r5 = r5 + r8;
        goto L_0x0902;
    L_0x09cc:
        r10 = 0;
        goto L_0x0902;
    L_0x09cf:
        r4 = 1;
        r10 = 0;
        r8 = r12 & r18;
        if (r8 == 0) goto L_0x09dc;
    L_0x09d5:
        r13 = 0;
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzg(r15, r13);
        goto L_0x0a11;
    L_0x09dc:
        r13 = 0;
        goto L_0x0a21;
    L_0x09df:
        r4 = 1;
        r10 = 0;
        r13 = 0;
        r11 = r12 & r18;
        if (r11 == 0) goto L_0x0a21;
    L_0x09e7:
        r8 = r2.getInt(r1, r8);
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzj(r15, r8);
        goto L_0x0a11;
    L_0x09f0:
        r4 = 1;
        r10 = 0;
        r13 = 0;
        r11 = r12 & r18;
        if (r11 == 0) goto L_0x0a21;
    L_0x09f8:
        r8 = r2.getLong(r1, r8);
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zze(r15, r8);
        goto L_0x0a11;
    L_0x0a01:
        r4 = 1;
        r10 = 0;
        r13 = 0;
        r11 = r12 & r18;
        if (r11 == 0) goto L_0x0a21;
    L_0x0a09:
        r8 = r2.getLong(r1, r8);
        r8 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r15, r8);
    L_0x0a11:
        r5 = r5 + r8;
        goto L_0x0a21;
    L_0x0a13:
        r4 = 1;
        r10 = 0;
        r13 = 0;
        r8 = r12 & r18;
        if (r8 == 0) goto L_0x0a21;
    L_0x0a1b:
        r8 = 0;
        r9 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r8);
        r5 = r5 + r9;
    L_0x0a21:
        r7 = 0;
        goto L_0x0a34;
    L_0x0a24:
        r4 = 1;
        r8 = 0;
        r10 = 0;
        r13 = 0;
        r9 = r12 & r18;
        if (r9 == 0) goto L_0x0a21;
    L_0x0a2d:
        r7 = 0;
        r11 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r7);
        r5 = r5 + r11;
    L_0x0a34:
        r3 = r3 + 3;
        r9 = r13;
        r4 = 0;
        r7 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        r8 = 1;
        r11 = 0;
        goto L_0x04f9;
    L_0x0a3f:
        r10 = 0;
        r2 = r0.zzacs;
        r2 = zza(r2, r1);
        r5 = r5 + r2;
        r2 = r0.zzact;
        if (r2 == 0) goto L_0x0a99;
    L_0x0a4b:
        r2 = r0.zzacu;
        r1 = r2.zzd(r1);
        r2 = 0;
    L_0x0a52:
        r3 = r1.zzxh;
        r3 = r3.zzjy();
        if (r10 >= r3) goto L_0x0a72;
    L_0x0a5a:
        r3 = r1.zzxh;
        r3 = r3.zzbf(r10);
        r4 = r3.getKey();
        r4 = (com.google.android.gms.internal.firebase_auth.zzhk) r4;
        r3 = r3.getValue();
        r3 = com.google.android.gms.internal.firebase_auth.zzhi.zzb(r4, r3);
        r2 = r2 + r3;
        r10 = r10 + 1;
        goto L_0x0a52;
    L_0x0a72:
        r1 = r1.zzxh;
        r1 = r1.zzjz();
        r1 = r1.iterator();
    L_0x0a7c:
        r3 = r1.hasNext();
        if (r3 == 0) goto L_0x0a98;
    L_0x0a82:
        r3 = r1.next();
        r3 = (java.util.Map.Entry) r3;
        r4 = r3.getKey();
        r4 = (com.google.android.gms.internal.firebase_auth.zzhk) r4;
        r3 = r3.getValue();
        r3 = com.google.android.gms.internal.firebase_auth.zzhi.zzb(r4, r3);
        r2 = r2 + r3;
        goto L_0x0a7c;
    L_0x0a98:
        r5 = r5 + r2;
    L_0x0a99:
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.zzq(java.lang.Object):int");
    }

    private static <UT, UB> int zza(zzkk<UT, UB> zzkk, T t) {
        return zzkk.zzq(zzkk.zzs(t));
    }

    private static List<?> zze(Object obj, long j) {
        return (List) zzkq.zzp(obj, j);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x0513  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x0553  */
    /* JADX WARNING: Removed duplicated region for block: B:331:0x0a2b  */
    public final void zza(T r14, com.google.android.gms.internal.firebase_auth.zzlh r15) throws java.io.IOException {
        /*
        r13 = this;
        r0 = r15.zzhl();
        r1 = com.google.android.gms.internal.firebase_auth.zzhs.zzd.zzaaz;
        r2 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r3 = 0;
        r4 = 1;
        r5 = 0;
        r6 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        if (r0 != r1) goto L_0x0529;
    L_0x0010:
        r0 = r13.zzacs;
        zza(r0, r14, r15);
        r0 = r13.zzact;
        if (r0 == 0) goto L_0x0032;
    L_0x0019:
        r0 = r13.zzacu;
        r0 = r0.zzd(r14);
        r1 = r0.zzxh;
        r1 = r1.isEmpty();
        if (r1 != 0) goto L_0x0032;
    L_0x0027:
        r0 = r0.descendingIterator();
        r1 = r0.next();
        r1 = (java.util.Map.Entry) r1;
        goto L_0x0034;
    L_0x0032:
        r0 = r3;
        r1 = r0;
    L_0x0034:
        r7 = r13.zzacx;
        r7 = r7.length;
        r7 = r7 + -3;
    L_0x0039:
        if (r7 < 0) goto L_0x0511;
    L_0x003b:
        r8 = r13.zzbb(r7);
        r9 = r13.zzacx;
        r9 = r9[r7];
    L_0x0043:
        if (r1 == 0) goto L_0x0061;
    L_0x0045:
        r10 = r13.zzacu;
        r10 = r10.zza(r1);
        if (r10 <= r9) goto L_0x0061;
    L_0x004d:
        r10 = r13.zzacu;
        r10.zza(r15, r1);
        r1 = r0.hasNext();
        if (r1 == 0) goto L_0x005f;
    L_0x0058:
        r1 = r0.next();
        r1 = (java.util.Map.Entry) r1;
        goto L_0x0043;
    L_0x005f:
        r1 = r3;
        goto L_0x0043;
    L_0x0061:
        r10 = r8 & r2;
        r10 = r10 >>> 20;
        switch(r10) {
            case 0: goto L_0x04fe;
            case 1: goto L_0x04ee;
            case 2: goto L_0x04de;
            case 3: goto L_0x04ce;
            case 4: goto L_0x04be;
            case 5: goto L_0x04ae;
            case 6: goto L_0x049e;
            case 7: goto L_0x048d;
            case 8: goto L_0x047c;
            case 9: goto L_0x0467;
            case 10: goto L_0x0454;
            case 11: goto L_0x0443;
            case 12: goto L_0x0432;
            case 13: goto L_0x0421;
            case 14: goto L_0x0410;
            case 15: goto L_0x03ff;
            case 16: goto L_0x03ee;
            case 17: goto L_0x03d9;
            case 18: goto L_0x03c8;
            case 19: goto L_0x03b7;
            case 20: goto L_0x03a6;
            case 21: goto L_0x0395;
            case 22: goto L_0x0384;
            case 23: goto L_0x0373;
            case 24: goto L_0x0362;
            case 25: goto L_0x0351;
            case 26: goto L_0x0340;
            case 27: goto L_0x032b;
            case 28: goto L_0x031a;
            case 29: goto L_0x0309;
            case 30: goto L_0x02f8;
            case 31: goto L_0x02e7;
            case 32: goto L_0x02d6;
            case 33: goto L_0x02c5;
            case 34: goto L_0x02b4;
            case 35: goto L_0x02a3;
            case 36: goto L_0x0292;
            case 37: goto L_0x0281;
            case 38: goto L_0x0270;
            case 39: goto L_0x025f;
            case 40: goto L_0x024e;
            case 41: goto L_0x023d;
            case 42: goto L_0x022c;
            case 43: goto L_0x021b;
            case 44: goto L_0x020a;
            case 45: goto L_0x01f9;
            case 46: goto L_0x01e8;
            case 47: goto L_0x01d7;
            case 48: goto L_0x01c6;
            case 49: goto L_0x01b1;
            case 50: goto L_0x01a6;
            case 51: goto L_0x0195;
            case 52: goto L_0x0184;
            case 53: goto L_0x0173;
            case 54: goto L_0x0162;
            case 55: goto L_0x0151;
            case 56: goto L_0x0140;
            case 57: goto L_0x012f;
            case 58: goto L_0x011e;
            case 59: goto L_0x010d;
            case 60: goto L_0x00f8;
            case 61: goto L_0x00e5;
            case 62: goto L_0x00d4;
            case 63: goto L_0x00c3;
            case 64: goto L_0x00b2;
            case 65: goto L_0x00a1;
            case 66: goto L_0x0090;
            case 67: goto L_0x007f;
            case 68: goto L_0x006a;
            default: goto L_0x0068;
        };
    L_0x0068:
        goto L_0x050d;
    L_0x006a:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0070:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r10 = r13.zzay(r7);
        r15.zzb(r9, r8, r10);
        goto L_0x050d;
    L_0x007f:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0085:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = zzi(r14, r10);
        r15.zzb(r9, r10);
        goto L_0x050d;
    L_0x0090:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0096:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzh(r14, r10);
        r15.zzh(r9, r8);
        goto L_0x050d;
    L_0x00a1:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x00a7:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = zzi(r14, r10);
        r15.zzj(r9, r10);
        goto L_0x050d;
    L_0x00b2:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x00b8:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzh(r14, r10);
        r15.zzp(r9, r8);
        goto L_0x050d;
    L_0x00c3:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x00c9:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzh(r14, r10);
        r15.zzq(r9, r8);
        goto L_0x050d;
    L_0x00d4:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x00da:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzh(r14, r10);
        r15.zzg(r9, r8);
        goto L_0x050d;
    L_0x00e5:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x00eb:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (com.google.android.gms.internal.firebase_auth.zzgf) r8;
        r15.zza(r9, r8);
        goto L_0x050d;
    L_0x00f8:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x00fe:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r10 = r13.zzay(r7);
        r15.zza(r9, r8, r10);
        goto L_0x050d;
    L_0x010d:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0113:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        zza(r9, r8, r15);
        goto L_0x050d;
    L_0x011e:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0124:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzj(r14, r10);
        r15.zzc(r9, r8);
        goto L_0x050d;
    L_0x012f:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0135:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzh(r14, r10);
        r15.zzi(r9, r8);
        goto L_0x050d;
    L_0x0140:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0146:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = zzi(r14, r10);
        r15.zzc(r9, r10);
        goto L_0x050d;
    L_0x0151:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0157:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzh(r14, r10);
        r15.zzf(r9, r8);
        goto L_0x050d;
    L_0x0162:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0168:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = zzi(r14, r10);
        r15.zza(r9, r10);
        goto L_0x050d;
    L_0x0173:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0179:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = zzi(r14, r10);
        r15.zzi(r9, r10);
        goto L_0x050d;
    L_0x0184:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x018a:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzg(r14, r10);
        r15.zza(r9, r8);
        goto L_0x050d;
    L_0x0195:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x019b:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = zzf(r14, r10);
        r15.zza(r9, r10);
        goto L_0x050d;
    L_0x01a6:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r13.zza(r15, r9, r8, r7);
        goto L_0x050d;
    L_0x01b1:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        r10 = r13.zzay(r7);
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r9, r8, r15, r10);
        goto L_0x050d;
    L_0x01c6:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zze(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x01d7:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzj(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x01e8:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzg(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x01f9:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzl(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x020a:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzm(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x021b:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzi(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x022c:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzn(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x023d:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzk(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x024e:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzf(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x025f:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzh(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x0270:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzd(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x0281:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzc(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x0292:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x02a3:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zza(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x02b4:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zze(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x02c5:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzj(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x02d6:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzg(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x02e7:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzl(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x02f8:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzm(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x0309:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzi(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x031a:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r9, r8, r15);
        goto L_0x050d;
    L_0x032b:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        r10 = r13.zzay(r7);
        com.google.android.gms.internal.firebase_auth.zzju.zza(r9, r8, r15, r10);
        goto L_0x050d;
    L_0x0340:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zza(r9, r8, r15);
        goto L_0x050d;
    L_0x0351:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzn(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x0362:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzk(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x0373:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzf(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x0384:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzh(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x0395:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzd(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x03a6:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzc(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x03b7:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x03c8:
        r9 = r13.zzacx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_auth.zzju.zza(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x03d9:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x03df:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r10 = r13.zzay(r7);
        r15.zzb(r9, r8, r10);
        goto L_0x050d;
    L_0x03ee:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x03f4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r10);
        r15.zzb(r9, r10);
        goto L_0x050d;
    L_0x03ff:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0405:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10);
        r15.zzh(r9, r8);
        goto L_0x050d;
    L_0x0410:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0416:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r10);
        r15.zzj(r9, r10);
        goto L_0x050d;
    L_0x0421:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0427:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10);
        r15.zzp(r9, r8);
        goto L_0x050d;
    L_0x0432:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0438:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10);
        r15.zzq(r9, r8);
        goto L_0x050d;
    L_0x0443:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0449:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10);
        r15.zzg(r9, r8);
        goto L_0x050d;
    L_0x0454:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x045a:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r8 = (com.google.android.gms.internal.firebase_auth.zzgf) r8;
        r15.zza(r9, r8);
        goto L_0x050d;
    L_0x0467:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x046d:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        r10 = r13.zzay(r7);
        r15.zza(r9, r8, r10);
        goto L_0x050d;
    L_0x047c:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0482:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10);
        zza(r9, r8, r15);
        goto L_0x050d;
    L_0x048d:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0493:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r14, r10);
        r15.zzc(r9, r8);
        goto L_0x050d;
    L_0x049e:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04a4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10);
        r15.zzi(r9, r8);
        goto L_0x050d;
    L_0x04ae:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04b4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r10);
        r15.zzc(r9, r10);
        goto L_0x050d;
    L_0x04be:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04c4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10);
        r15.zzf(r9, r8);
        goto L_0x050d;
    L_0x04ce:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04d4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r10);
        r15.zza(r9, r10);
        goto L_0x050d;
    L_0x04de:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04e4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r10);
        r15.zzi(r9, r10);
        goto L_0x050d;
    L_0x04ee:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04f4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r14, r10);
        r15.zza(r9, r8);
        goto L_0x050d;
    L_0x04fe:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0504:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r14, r10);
        r15.zza(r9, r10);
    L_0x050d:
        r7 = r7 + -3;
        goto L_0x0039;
    L_0x0511:
        if (r1 == 0) goto L_0x0528;
    L_0x0513:
        r14 = r13.zzacu;
        r14.zza(r15, r1);
        r14 = r0.hasNext();
        if (r14 == 0) goto L_0x0526;
    L_0x051e:
        r14 = r0.next();
        r14 = (java.util.Map.Entry) r14;
        r1 = r14;
        goto L_0x0511;
    L_0x0526:
        r1 = r3;
        goto L_0x0511;
    L_0x0528:
        return;
    L_0x0529:
        r0 = r13.zzadc;
        if (r0 == 0) goto L_0x0a46;
    L_0x052d:
        r0 = r13.zzact;
        if (r0 == 0) goto L_0x054a;
    L_0x0531:
        r0 = r13.zzacu;
        r0 = r0.zzd(r14);
        r1 = r0.zzxh;
        r1 = r1.isEmpty();
        if (r1 != 0) goto L_0x054a;
    L_0x053f:
        r0 = r0.iterator();
        r1 = r0.next();
        r1 = (java.util.Map.Entry) r1;
        goto L_0x054c;
    L_0x054a:
        r0 = r3;
        r1 = r0;
    L_0x054c:
        r7 = r13.zzacx;
        r7 = r7.length;
        r8 = r1;
        r1 = 0;
    L_0x0551:
        if (r1 >= r7) goto L_0x0a29;
    L_0x0553:
        r9 = r13.zzbb(r1);
        r10 = r13.zzacx;
        r10 = r10[r1];
    L_0x055b:
        if (r8 == 0) goto L_0x0579;
    L_0x055d:
        r11 = r13.zzacu;
        r11 = r11.zza(r8);
        if (r11 > r10) goto L_0x0579;
    L_0x0565:
        r11 = r13.zzacu;
        r11.zza(r15, r8);
        r8 = r0.hasNext();
        if (r8 == 0) goto L_0x0577;
    L_0x0570:
        r8 = r0.next();
        r8 = (java.util.Map.Entry) r8;
        goto L_0x055b;
    L_0x0577:
        r8 = r3;
        goto L_0x055b;
    L_0x0579:
        r11 = r9 & r2;
        r11 = r11 >>> 20;
        switch(r11) {
            case 0: goto L_0x0a16;
            case 1: goto L_0x0a06;
            case 2: goto L_0x09f6;
            case 3: goto L_0x09e6;
            case 4: goto L_0x09d6;
            case 5: goto L_0x09c6;
            case 6: goto L_0x09b6;
            case 7: goto L_0x09a5;
            case 8: goto L_0x0994;
            case 9: goto L_0x097f;
            case 10: goto L_0x096c;
            case 11: goto L_0x095b;
            case 12: goto L_0x094a;
            case 13: goto L_0x0939;
            case 14: goto L_0x0928;
            case 15: goto L_0x0917;
            case 16: goto L_0x0906;
            case 17: goto L_0x08f1;
            case 18: goto L_0x08e0;
            case 19: goto L_0x08cf;
            case 20: goto L_0x08be;
            case 21: goto L_0x08ad;
            case 22: goto L_0x089c;
            case 23: goto L_0x088b;
            case 24: goto L_0x087a;
            case 25: goto L_0x0869;
            case 26: goto L_0x0858;
            case 27: goto L_0x0843;
            case 28: goto L_0x0832;
            case 29: goto L_0x0821;
            case 30: goto L_0x0810;
            case 31: goto L_0x07ff;
            case 32: goto L_0x07ee;
            case 33: goto L_0x07dd;
            case 34: goto L_0x07cc;
            case 35: goto L_0x07bb;
            case 36: goto L_0x07aa;
            case 37: goto L_0x0799;
            case 38: goto L_0x0788;
            case 39: goto L_0x0777;
            case 40: goto L_0x0766;
            case 41: goto L_0x0755;
            case 42: goto L_0x0744;
            case 43: goto L_0x0733;
            case 44: goto L_0x0722;
            case 45: goto L_0x0711;
            case 46: goto L_0x0700;
            case 47: goto L_0x06ef;
            case 48: goto L_0x06de;
            case 49: goto L_0x06c9;
            case 50: goto L_0x06be;
            case 51: goto L_0x06ad;
            case 52: goto L_0x069c;
            case 53: goto L_0x068b;
            case 54: goto L_0x067a;
            case 55: goto L_0x0669;
            case 56: goto L_0x0658;
            case 57: goto L_0x0647;
            case 58: goto L_0x0636;
            case 59: goto L_0x0625;
            case 60: goto L_0x0610;
            case 61: goto L_0x05fd;
            case 62: goto L_0x05ec;
            case 63: goto L_0x05db;
            case 64: goto L_0x05ca;
            case 65: goto L_0x05b9;
            case 66: goto L_0x05a8;
            case 67: goto L_0x0597;
            case 68: goto L_0x0582;
            default: goto L_0x0580;
        };
    L_0x0580:
        goto L_0x0a25;
    L_0x0582:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0588:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r11 = r13.zzay(r1);
        r15.zzb(r10, r9, r11);
        goto L_0x0a25;
    L_0x0597:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x059d:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = zzi(r14, r11);
        r15.zzb(r10, r11);
        goto L_0x0a25;
    L_0x05a8:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x05ae:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzh(r14, r11);
        r15.zzh(r10, r9);
        goto L_0x0a25;
    L_0x05b9:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x05bf:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = zzi(r14, r11);
        r15.zzj(r10, r11);
        goto L_0x0a25;
    L_0x05ca:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x05d0:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzh(r14, r11);
        r15.zzp(r10, r9);
        goto L_0x0a25;
    L_0x05db:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x05e1:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzh(r14, r11);
        r15.zzq(r10, r9);
        goto L_0x0a25;
    L_0x05ec:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x05f2:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzh(r14, r11);
        r15.zzg(r10, r9);
        goto L_0x0a25;
    L_0x05fd:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0603:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (com.google.android.gms.internal.firebase_auth.zzgf) r9;
        r15.zza(r10, r9);
        goto L_0x0a25;
    L_0x0610:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0616:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r11 = r13.zzay(r1);
        r15.zza(r10, r9, r11);
        goto L_0x0a25;
    L_0x0625:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x062b:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        zza(r10, r9, r15);
        goto L_0x0a25;
    L_0x0636:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x063c:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzj(r14, r11);
        r15.zzc(r10, r9);
        goto L_0x0a25;
    L_0x0647:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x064d:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzh(r14, r11);
        r15.zzi(r10, r9);
        goto L_0x0a25;
    L_0x0658:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x065e:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = zzi(r14, r11);
        r15.zzc(r10, r11);
        goto L_0x0a25;
    L_0x0669:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x066f:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzh(r14, r11);
        r15.zzf(r10, r9);
        goto L_0x0a25;
    L_0x067a:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0680:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = zzi(r14, r11);
        r15.zza(r10, r11);
        goto L_0x0a25;
    L_0x068b:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0691:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = zzi(r14, r11);
        r15.zzi(r10, r11);
        goto L_0x0a25;
    L_0x069c:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x06a2:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzg(r14, r11);
        r15.zza(r10, r9);
        goto L_0x0a25;
    L_0x06ad:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x06b3:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = zzf(r14, r11);
        r15.zza(r10, r11);
        goto L_0x0a25;
    L_0x06be:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r13.zza(r15, r10, r9, r1);
        goto L_0x0a25;
    L_0x06c9:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        r11 = r13.zzay(r1);
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r10, r9, r15, r11);
        goto L_0x0a25;
    L_0x06de:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zze(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x06ef:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzj(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0700:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzg(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0711:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzl(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0722:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzm(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0733:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzi(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0744:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzn(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0755:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzk(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0766:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzf(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0777:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzh(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0788:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzd(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0799:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzc(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x07aa:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x07bb:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zza(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x07cc:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zze(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x07dd:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzj(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x07ee:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzg(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x07ff:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzl(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x0810:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzm(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x0821:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzi(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x0832:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r10, r9, r15);
        goto L_0x0a25;
    L_0x0843:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        r11 = r13.zzay(r1);
        com.google.android.gms.internal.firebase_auth.zzju.zza(r10, r9, r15, r11);
        goto L_0x0a25;
    L_0x0858:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zza(r10, r9, r15);
        goto L_0x0a25;
    L_0x0869:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzn(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x087a:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzk(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x088b:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzf(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x089c:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzh(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x08ad:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzd(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x08be:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzc(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x08cf:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x08e0:
        r10 = r13.zzacx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zza(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x08f1:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x08f7:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r11 = r13.zzay(r1);
        r15.zzb(r10, r9, r11);
        goto L_0x0a25;
    L_0x0906:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x090c:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r11);
        r15.zzb(r10, r11);
        goto L_0x0a25;
    L_0x0917:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x091d:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11);
        r15.zzh(r10, r9);
        goto L_0x0a25;
    L_0x0928:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x092e:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r11);
        r15.zzj(r10, r11);
        goto L_0x0a25;
    L_0x0939:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x093f:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11);
        r15.zzp(r10, r9);
        goto L_0x0a25;
    L_0x094a:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0950:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11);
        r15.zzq(r10, r9);
        goto L_0x0a25;
    L_0x095b:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0961:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11);
        r15.zzg(r10, r9);
        goto L_0x0a25;
    L_0x096c:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0972:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r9 = (com.google.android.gms.internal.firebase_auth.zzgf) r9;
        r15.zza(r10, r9);
        goto L_0x0a25;
    L_0x097f:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0985:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        r11 = r13.zzay(r1);
        r15.zza(r10, r9, r11);
        goto L_0x0a25;
    L_0x0994:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x099a:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11);
        zza(r10, r9, r15);
        goto L_0x0a25;
    L_0x09a5:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09ab:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r14, r11);
        r15.zzc(r10, r9);
        goto L_0x0a25;
    L_0x09b6:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09bc:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11);
        r15.zzi(r10, r9);
        goto L_0x0a25;
    L_0x09c6:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09cc:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r11);
        r15.zzc(r10, r11);
        goto L_0x0a25;
    L_0x09d6:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09dc:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11);
        r15.zzf(r10, r9);
        goto L_0x0a25;
    L_0x09e6:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09ec:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r11);
        r15.zza(r10, r11);
        goto L_0x0a25;
    L_0x09f6:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09fc:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r11);
        r15.zzi(r10, r11);
        goto L_0x0a25;
    L_0x0a06:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0a0c:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r14, r11);
        r15.zza(r10, r9);
        goto L_0x0a25;
    L_0x0a16:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0a1c:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r14, r11);
        r15.zza(r10, r11);
    L_0x0a25:
        r1 = r1 + 3;
        goto L_0x0551;
    L_0x0a29:
        if (r8 == 0) goto L_0x0a40;
    L_0x0a2b:
        r1 = r13.zzacu;
        r1.zza(r15, r8);
        r1 = r0.hasNext();
        if (r1 == 0) goto L_0x0a3e;
    L_0x0a36:
        r1 = r0.next();
        r1 = (java.util.Map.Entry) r1;
        r8 = r1;
        goto L_0x0a29;
    L_0x0a3e:
        r8 = r3;
        goto L_0x0a29;
    L_0x0a40:
        r0 = r13.zzacs;
        zza(r0, r14, r15);
        return;
    L_0x0a46:
        r13.zzb(r14, r15);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.zza(java.lang.Object, com.google.android.gms.internal.firebase_auth.zzlh):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x04b5  */
    private final void zzb(T r19, com.google.android.gms.internal.firebase_auth.zzlh r20) throws java.io.IOException {
        /*
        r18 = this;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r3 = r0.zzact;
        if (r3 == 0) goto L_0x0023;
    L_0x000a:
        r3 = r0.zzacu;
        r3 = r3.zzd(r1);
        r5 = r3.zzxh;
        r5 = r5.isEmpty();
        if (r5 != 0) goto L_0x0023;
    L_0x0018:
        r3 = r3.iterator();
        r5 = r3.next();
        r5 = (java.util.Map.Entry) r5;
        goto L_0x0025;
    L_0x0023:
        r3 = 0;
        r5 = 0;
    L_0x0025:
        r6 = -1;
        r7 = r0.zzacx;
        r7 = r7.length;
        r8 = zzacw;
        r10 = r5;
        r5 = 0;
        r11 = 0;
    L_0x002e:
        if (r5 >= r7) goto L_0x04af;
    L_0x0030:
        r12 = r0.zzbb(r5);
        r13 = r0.zzacx;
        r14 = r13[r5];
        r15 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r15 = r15 & r12;
        r15 = r15 >>> 20;
        r4 = r0.zzadc;
        r16 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        if (r4 != 0) goto L_0x0064;
    L_0x0044:
        r4 = 17;
        if (r15 > r4) goto L_0x0064;
    L_0x0048:
        r4 = r5 + 2;
        r4 = r13[r4];
        r13 = r4 & r16;
        if (r13 == r6) goto L_0x0058;
    L_0x0050:
        r17 = r10;
        r9 = (long) r13;
        r11 = r8.getInt(r1, r9);
        goto L_0x005b;
    L_0x0058:
        r17 = r10;
        r13 = r6;
    L_0x005b:
        r4 = r4 >>> 20;
        r6 = 1;
        r9 = r6 << r4;
        r6 = r13;
        r10 = r17;
        goto L_0x0069;
    L_0x0064:
        r17 = r10;
        r10 = r17;
        r9 = 0;
    L_0x0069:
        if (r10 == 0) goto L_0x0088;
    L_0x006b:
        r4 = r0.zzacu;
        r4 = r4.zza(r10);
        if (r4 > r14) goto L_0x0088;
    L_0x0073:
        r4 = r0.zzacu;
        r4.zza(r2, r10);
        r4 = r3.hasNext();
        if (r4 == 0) goto L_0x0086;
    L_0x007e:
        r4 = r3.next();
        r4 = (java.util.Map.Entry) r4;
        r10 = r4;
        goto L_0x0069;
    L_0x0086:
        r10 = 0;
        goto L_0x0069;
    L_0x0088:
        r4 = r12 & r16;
        r12 = (long) r4;
        switch(r15) {
            case 0: goto L_0x049f;
            case 1: goto L_0x0492;
            case 2: goto L_0x0485;
            case 3: goto L_0x0478;
            case 4: goto L_0x046b;
            case 5: goto L_0x045e;
            case 6: goto L_0x0451;
            case 7: goto L_0x0444;
            case 8: goto L_0x0436;
            case 9: goto L_0x0424;
            case 10: goto L_0x0414;
            case 11: goto L_0x0406;
            case 12: goto L_0x03f8;
            case 13: goto L_0x03ea;
            case 14: goto L_0x03dc;
            case 15: goto L_0x03ce;
            case 16: goto L_0x03c0;
            case 17: goto L_0x03ae;
            case 18: goto L_0x039e;
            case 19: goto L_0x038e;
            case 20: goto L_0x037e;
            case 21: goto L_0x036e;
            case 22: goto L_0x035e;
            case 23: goto L_0x034e;
            case 24: goto L_0x033e;
            case 25: goto L_0x032e;
            case 26: goto L_0x031f;
            case 27: goto L_0x030c;
            case 28: goto L_0x02fd;
            case 29: goto L_0x02ed;
            case 30: goto L_0x02dd;
            case 31: goto L_0x02cd;
            case 32: goto L_0x02bd;
            case 33: goto L_0x02ad;
            case 34: goto L_0x029d;
            case 35: goto L_0x028d;
            case 36: goto L_0x027d;
            case 37: goto L_0x026d;
            case 38: goto L_0x025d;
            case 39: goto L_0x024d;
            case 40: goto L_0x023d;
            case 41: goto L_0x022d;
            case 42: goto L_0x021d;
            case 43: goto L_0x020d;
            case 44: goto L_0x01fd;
            case 45: goto L_0x01ed;
            case 46: goto L_0x01dd;
            case 47: goto L_0x01cd;
            case 48: goto L_0x01bd;
            case 49: goto L_0x01aa;
            case 50: goto L_0x01a1;
            case 51: goto L_0x0192;
            case 52: goto L_0x0183;
            case 53: goto L_0x0174;
            case 54: goto L_0x0165;
            case 55: goto L_0x0156;
            case 56: goto L_0x0147;
            case 57: goto L_0x0138;
            case 58: goto L_0x0129;
            case 59: goto L_0x011a;
            case 60: goto L_0x0107;
            case 61: goto L_0x00f7;
            case 62: goto L_0x00e9;
            case 63: goto L_0x00db;
            case 64: goto L_0x00cd;
            case 65: goto L_0x00bf;
            case 66: goto L_0x00b1;
            case 67: goto L_0x00a3;
            case 68: goto L_0x0091;
            default: goto L_0x008e;
        };
    L_0x008e:
        r15 = 0;
        goto L_0x04ab;
    L_0x0091:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x0097:
        r4 = r8.getObject(r1, r12);
        r9 = r0.zzay(r5);
        r2.zzb(r14, r4, r9);
        goto L_0x008e;
    L_0x00a3:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x00a9:
        r12 = zzi(r1, r12);
        r2.zzb(r14, r12);
        goto L_0x008e;
    L_0x00b1:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x00b7:
        r4 = zzh(r1, r12);
        r2.zzh(r14, r4);
        goto L_0x008e;
    L_0x00bf:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x00c5:
        r12 = zzi(r1, r12);
        r2.zzj(r14, r12);
        goto L_0x008e;
    L_0x00cd:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x00d3:
        r4 = zzh(r1, r12);
        r2.zzp(r14, r4);
        goto L_0x008e;
    L_0x00db:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x00e1:
        r4 = zzh(r1, r12);
        r2.zzq(r14, r4);
        goto L_0x008e;
    L_0x00e9:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x00ef:
        r4 = zzh(r1, r12);
        r2.zzg(r14, r4);
        goto L_0x008e;
    L_0x00f7:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x00fd:
        r4 = r8.getObject(r1, r12);
        r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4;
        r2.zza(r14, r4);
        goto L_0x008e;
    L_0x0107:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x010d:
        r4 = r8.getObject(r1, r12);
        r9 = r0.zzay(r5);
        r2.zza(r14, r4, r9);
        goto L_0x008e;
    L_0x011a:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x0120:
        r4 = r8.getObject(r1, r12);
        zza(r14, r4, r2);
        goto L_0x008e;
    L_0x0129:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x012f:
        r4 = zzj(r1, r12);
        r2.zzc(r14, r4);
        goto L_0x008e;
    L_0x0138:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x013e:
        r4 = zzh(r1, r12);
        r2.zzi(r14, r4);
        goto L_0x008e;
    L_0x0147:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x014d:
        r12 = zzi(r1, r12);
        r2.zzc(r14, r12);
        goto L_0x008e;
    L_0x0156:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x015c:
        r4 = zzh(r1, r12);
        r2.zzf(r14, r4);
        goto L_0x008e;
    L_0x0165:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x016b:
        r12 = zzi(r1, r12);
        r2.zza(r14, r12);
        goto L_0x008e;
    L_0x0174:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x017a:
        r12 = zzi(r1, r12);
        r2.zzi(r14, r12);
        goto L_0x008e;
    L_0x0183:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x0189:
        r4 = zzg(r1, r12);
        r2.zza(r14, r4);
        goto L_0x008e;
    L_0x0192:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x0198:
        r12 = zzf(r1, r12);
        r2.zza(r14, r12);
        goto L_0x008e;
    L_0x01a1:
        r4 = r8.getObject(r1, r12);
        r0.zza(r2, r14, r4, r5);
        goto L_0x008e;
    L_0x01aa:
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        r12 = r0.zzay(r5);
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r4, r9, r2, r12);
        goto L_0x008e;
    L_0x01bd:
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        r14 = 1;
        com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x01cd:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzj(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x01dd:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzg(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x01ed:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzl(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x01fd:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzm(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x020d:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzi(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x021d:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzn(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x022d:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzk(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x023d:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzf(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x024d:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzh(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x025d:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzd(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x026d:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzc(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x027d:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x028d:
        r14 = 1;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x029d:
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        r14 = 0;
        com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02ad:
        r14 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzj(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02bd:
        r14 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzg(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02cd:
        r14 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzl(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02dd:
        r14 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzm(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02ed:
        r14 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzi(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02fd:
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r4, r9, r2);
        goto L_0x008e;
    L_0x030c:
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        r12 = r0.zzay(r5);
        com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r9, r2, r12);
        goto L_0x008e;
    L_0x031f:
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r9, r2);
        goto L_0x008e;
    L_0x032e:
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        r15 = 0;
        com.google.android.gms.internal.firebase_auth.zzju.zzn(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x033e:
        r15 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzk(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x034e:
        r15 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzf(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x035e:
        r15 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzh(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x036e:
        r15 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzd(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x037e:
        r15 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzc(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x038e:
        r15 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zzb(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x039e:
        r15 = 0;
        r4 = r0.zzacx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x03ae:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x03b3:
        r4 = r8.getObject(r1, r12);
        r9 = r0.zzay(r5);
        r2.zzb(r14, r4, r9);
        goto L_0x04ab;
    L_0x03c0:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x03c5:
        r12 = r8.getLong(r1, r12);
        r2.zzb(r14, r12);
        goto L_0x04ab;
    L_0x03ce:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x03d3:
        r4 = r8.getInt(r1, r12);
        r2.zzh(r14, r4);
        goto L_0x04ab;
    L_0x03dc:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x03e1:
        r12 = r8.getLong(r1, r12);
        r2.zzj(r14, r12);
        goto L_0x04ab;
    L_0x03ea:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x03ef:
        r4 = r8.getInt(r1, r12);
        r2.zzp(r14, r4);
        goto L_0x04ab;
    L_0x03f8:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x03fd:
        r4 = r8.getInt(r1, r12);
        r2.zzq(r14, r4);
        goto L_0x04ab;
    L_0x0406:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x040b:
        r4 = r8.getInt(r1, r12);
        r2.zzg(r14, r4);
        goto L_0x04ab;
    L_0x0414:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x0419:
        r4 = r8.getObject(r1, r12);
        r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4;
        r2.zza(r14, r4);
        goto L_0x04ab;
    L_0x0424:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x0429:
        r4 = r8.getObject(r1, r12);
        r9 = r0.zzay(r5);
        r2.zza(r14, r4, r9);
        goto L_0x04ab;
    L_0x0436:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x043b:
        r4 = r8.getObject(r1, r12);
        zza(r14, r4, r2);
        goto L_0x04ab;
    L_0x0444:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x0449:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r1, r12);
        r2.zzc(r14, r4);
        goto L_0x04ab;
    L_0x0451:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x0456:
        r4 = r8.getInt(r1, r12);
        r2.zzi(r14, r4);
        goto L_0x04ab;
    L_0x045e:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x0463:
        r12 = r8.getLong(r1, r12);
        r2.zzc(r14, r12);
        goto L_0x04ab;
    L_0x046b:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x0470:
        r4 = r8.getInt(r1, r12);
        r2.zzf(r14, r4);
        goto L_0x04ab;
    L_0x0478:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x047d:
        r12 = r8.getLong(r1, r12);
        r2.zza(r14, r12);
        goto L_0x04ab;
    L_0x0485:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x048a:
        r12 = r8.getLong(r1, r12);
        r2.zzi(r14, r12);
        goto L_0x04ab;
    L_0x0492:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x0497:
        r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r1, r12);
        r2.zza(r14, r4);
        goto L_0x04ab;
    L_0x049f:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x04a4:
        r12 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r1, r12);
        r2.zza(r14, r12);
    L_0x04ab:
        r5 = r5 + 3;
        goto L_0x002e;
    L_0x04af:
        r17 = r10;
        r4 = r17;
    L_0x04b3:
        if (r4 == 0) goto L_0x04c9;
    L_0x04b5:
        r5 = r0.zzacu;
        r5.zza(r2, r4);
        r4 = r3.hasNext();
        if (r4 == 0) goto L_0x04c7;
    L_0x04c0:
        r4 = r3.next();
        r4 = (java.util.Map.Entry) r4;
        goto L_0x04b3;
    L_0x04c7:
        r4 = 0;
        goto L_0x04b3;
    L_0x04c9:
        r3 = r0.zzacs;
        zza(r3, r1, r2);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.zzb(java.lang.Object, com.google.android.gms.internal.firebase_auth.zzlh):void");
    }

    private final <K, V> void zza(zzlh zzlh, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzlh.zza(i, this.zzadj.zzn(zzaz(i2)), this.zzadj.zzj(obj));
        }
    }

    private static <UT, UB> void zza(zzkk<UT, UB> zzkk, T t, zzlh zzlh) throws IOException {
        zzkk.zza(zzkk.zzs(t), zzlh);
    }

    /* JADX WARNING: Removed duplicated region for block: B:173:0x05ed  */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x05dc A:{LOOP_END, LOOP:11: B:168:0x05d8->B:170:0x05dc} */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x05ec A:{REMOVE} */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x05e9  */
    /* JADX WARNING: Missing block: B:40:0x00a0, code:
            if (r14 == null) goto L_0x00a5;
     */
    /* JADX WARNING: Missing block: B:41:0x00a2, code:
            r11.zzg(r2, r14);
     */
    /* JADX WARNING: Missing block: B:42:0x00a5, code:
            return;
     */
    /* JADX WARNING: Missing block: B:174:0x05f2, code:
            throw new java.lang.NullPointerException();
     */
    public final void zza(T r18, com.google.android.gms.internal.firebase_auth.zzjp r19, com.google.android.gms.internal.firebase_auth.zzhf r20) throws java.io.IOException {
        /*
        r17 = this;
        r1 = r17;
        r2 = r18;
        r0 = r19;
        r10 = r20;
        if (r10 == 0) goto L_0x05ed;
    L_0x000a:
        r11 = r1.zzacs;
        r12 = r1.zzacu;
        r13 = 0;
        r3 = r13;
        r14 = r3;
    L_0x0011:
        r4 = r19.zzhg();	 Catch:{ all -> 0x05d5 }
        r5 = r1.zzacz;	 Catch:{ all -> 0x05d5 }
        r6 = -1;
        if (r4 < r5) goto L_0x003e;
    L_0x001a:
        r5 = r1.zzada;	 Catch:{ all -> 0x05d5 }
        if (r4 > r5) goto L_0x003e;
    L_0x001e:
        r5 = 0;
        r7 = r1.zzacx;	 Catch:{ all -> 0x05d5 }
        r7 = r7.length;	 Catch:{ all -> 0x05d5 }
        r7 = r7 / 3;
        r7 = r7 + -1;
    L_0x0026:
        if (r5 > r7) goto L_0x003e;
    L_0x0028:
        r8 = r7 + r5;
        r8 = r8 >>> 1;
        r9 = r8 * 3;
        r15 = r1.zzacx;	 Catch:{ all -> 0x05d5 }
        r15 = r15[r9];	 Catch:{ all -> 0x05d5 }
        if (r4 != r15) goto L_0x0036;
    L_0x0034:
        r6 = r9;
        goto L_0x003e;
    L_0x0036:
        if (r4 >= r15) goto L_0x003b;
    L_0x0038:
        r7 = r8 + -1;
        goto L_0x0026;
    L_0x003b:
        r5 = r8 + 1;
        goto L_0x0026;
    L_0x003e:
        if (r6 >= 0) goto L_0x00a6;
    L_0x0040:
        r5 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        if (r4 != r5) goto L_0x005c;
    L_0x0045:
        r0 = r1.zzadf;
    L_0x0047:
        r3 = r1.zzadg;
        if (r0 >= r3) goto L_0x0056;
    L_0x004b:
        r3 = r1.zzade;
        r3 = r3[r0];
        r14 = r1.zza(r2, r3, r14, r11);
        r0 = r0 + 1;
        goto L_0x0047;
    L_0x0056:
        if (r14 == 0) goto L_0x005b;
    L_0x0058:
        r11.zzg(r2, r14);
    L_0x005b:
        return;
    L_0x005c:
        r5 = r1.zzact;	 Catch:{ all -> 0x05d5 }
        if (r5 != 0) goto L_0x0062;
    L_0x0060:
        r5 = r13;
        goto L_0x0069;
    L_0x0062:
        r5 = r1.zzacr;	 Catch:{ all -> 0x05d5 }
        r4 = r12.zza(r10, r5, r4);	 Catch:{ all -> 0x05d5 }
        r5 = r4;
    L_0x0069:
        if (r5 == 0) goto L_0x0080;
    L_0x006b:
        if (r3 != 0) goto L_0x0071;
    L_0x006d:
        r3 = r12.zze(r2);	 Catch:{ all -> 0x05d5 }
    L_0x0071:
        r15 = r3;
        r3 = r12;
        r4 = r19;
        r6 = r20;
        r7 = r15;
        r8 = r14;
        r9 = r11;
        r14 = r3.zza(r4, r5, r6, r7, r8, r9);	 Catch:{ all -> 0x05d5 }
        r3 = r15;
        goto L_0x0011;
    L_0x0080:
        r11.zza(r0);	 Catch:{ all -> 0x05d5 }
        if (r14 != 0) goto L_0x0089;
    L_0x0085:
        r14 = r11.zzt(r2);	 Catch:{ all -> 0x05d5 }
    L_0x0089:
        r4 = r11.zza(r14, r0);	 Catch:{ all -> 0x05d5 }
        if (r4 != 0) goto L_0x0011;
    L_0x008f:
        r0 = r1.zzadf;
    L_0x0091:
        r3 = r1.zzadg;
        if (r0 >= r3) goto L_0x00a0;
    L_0x0095:
        r3 = r1.zzade;
        r3 = r3[r0];
        r14 = r1.zza(r2, r3, r14, r11);
        r0 = r0 + 1;
        goto L_0x0091;
    L_0x00a0:
        if (r14 == 0) goto L_0x00a5;
    L_0x00a2:
        r11.zzg(r2, r14);
    L_0x00a5:
        return;
    L_0x00a6:
        r5 = r1.zzbb(r6);	 Catch:{ all -> 0x05d5 }
        r7 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r7 = r7 & r5;
        r7 = r7 >>> 20;
        r8 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        switch(r7) {
            case 0: goto L_0x0582;
            case 1: goto L_0x0573;
            case 2: goto L_0x0564;
            case 3: goto L_0x0555;
            case 4: goto L_0x0546;
            case 5: goto L_0x0537;
            case 6: goto L_0x0528;
            case 7: goto L_0x0519;
            case 8: goto L_0x0511;
            case 9: goto L_0x04e0;
            case 10: goto L_0x04d1;
            case 11: goto L_0x04c2;
            case 12: goto L_0x04a0;
            case 13: goto L_0x0491;
            case 14: goto L_0x0482;
            case 15: goto L_0x0473;
            case 16: goto L_0x0464;
            case 17: goto L_0x0433;
            case 18: goto L_0x0426;
            case 19: goto L_0x0419;
            case 20: goto L_0x040c;
            case 21: goto L_0x03ff;
            case 22: goto L_0x03f2;
            case 23: goto L_0x03e5;
            case 24: goto L_0x03d8;
            case 25: goto L_0x03cb;
            case 26: goto L_0x03ab;
            case 27: goto L_0x039a;
            case 28: goto L_0x038d;
            case 29: goto L_0x0380;
            case 30: goto L_0x036b;
            case 31: goto L_0x035e;
            case 32: goto L_0x0351;
            case 33: goto L_0x0344;
            case 34: goto L_0x0337;
            case 35: goto L_0x032a;
            case 36: goto L_0x031d;
            case 37: goto L_0x0310;
            case 38: goto L_0x0303;
            case 39: goto L_0x02f6;
            case 40: goto L_0x02e9;
            case 41: goto L_0x02dc;
            case 42: goto L_0x02cf;
            case 43: goto L_0x02c2;
            case 44: goto L_0x02ad;
            case 45: goto L_0x02a0;
            case 46: goto L_0x0293;
            case 47: goto L_0x0286;
            case 48: goto L_0x0279;
            case 49: goto L_0x0267;
            case 50: goto L_0x0225;
            case 51: goto L_0x0213;
            case 52: goto L_0x0201;
            case 53: goto L_0x01ef;
            case 54: goto L_0x01dd;
            case 55: goto L_0x01cb;
            case 56: goto L_0x01b9;
            case 57: goto L_0x01a7;
            case 58: goto L_0x0195;
            case 59: goto L_0x018d;
            case 60: goto L_0x015c;
            case 61: goto L_0x014e;
            case 62: goto L_0x013c;
            case 63: goto L_0x0117;
            case 64: goto L_0x0105;
            case 65: goto L_0x00f3;
            case 66: goto L_0x00e1;
            case 67: goto L_0x00cf;
            case 68: goto L_0x00bd;
            default: goto L_0x00b5;
        };
    L_0x00b5:
        if (r14 != 0) goto L_0x0591;
    L_0x00b7:
        r14 = r11.zzkn();	 Catch:{ zzib -> 0x05ae }
        goto L_0x0591;
    L_0x00bd:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r1.zzay(r6);	 Catch:{ zzib -> 0x05ae }
        r5 = r0.zzb(r5, r10);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x00cf:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r15 = r19.zzgw();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Long.valueOf(r15);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x00e1:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r19.zzgv();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x00f3:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r15 = r19.zzgu();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Long.valueOf(r15);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0105:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r19.zzgt();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0117:
        r7 = r19.zzgs();	 Catch:{ zzib -> 0x05ae }
        r9 = r1.zzba(r6);	 Catch:{ zzib -> 0x05ae }
        if (r9 == 0) goto L_0x012e;
    L_0x0121:
        r9 = r9.zzd(r7);	 Catch:{ zzib -> 0x05ae }
        if (r9 == 0) goto L_0x0128;
    L_0x0127:
        goto L_0x012e;
    L_0x0128:
        r14 = com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r7, r14, r11);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x012e:
        r5 = r5 & r8;
        r8 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Integer.valueOf(r7);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r8, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x013c:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r19.zzgr();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x014e:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r19.zzgq();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x015c:
        r7 = r1.zza(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        if (r7 == 0) goto L_0x0178;
    L_0x0162:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r2, r7);	 Catch:{ zzib -> 0x05ae }
        r9 = r1.zzay(r6);	 Catch:{ zzib -> 0x05ae }
        r9 = r0.zza(r9, r10);	 Catch:{ zzib -> 0x05ae }
        r5 = com.google.android.gms.internal.firebase_auth.zzht.zzb(r5, r9);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0188;
    L_0x0178:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r1.zzay(r6);	 Catch:{ zzib -> 0x05ae }
        r5 = r0.zza(r5, r10);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
    L_0x0188:
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x018d:
        r1.zza(r2, r5, r0);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0195:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r19.zzgo();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Boolean.valueOf(r5);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x01a7:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r19.zzgn();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x01b9:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r15 = r19.zzgm();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Long.valueOf(r15);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x01cb:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r19.zzgl();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x01dd:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r15 = r19.zzgj();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Long.valueOf(r15);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x01ef:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r15 = r19.zzgk();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Long.valueOf(r15);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0201:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r19.readFloat();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Float.valueOf(r5);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0213:
        r5 = r5 & r8;
        r7 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r15 = r19.readDouble();	 Catch:{ zzib -> 0x05ae }
        r5 = java.lang.Double.valueOf(r15);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0225:
        r4 = r1.zzaz(r6);	 Catch:{ zzib -> 0x05ae }
        r5 = r1.zzbb(r6);	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r7 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r2, r5);	 Catch:{ zzib -> 0x05ae }
        if (r7 != 0) goto L_0x023f;
    L_0x0235:
        r7 = r1.zzadj;	 Catch:{ zzib -> 0x05ae }
        r7 = r7.zzm(r4);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r5, r7);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0256;
    L_0x023f:
        r8 = r1.zzadj;	 Catch:{ zzib -> 0x05ae }
        r8 = r8.zzk(r7);	 Catch:{ zzib -> 0x05ae }
        if (r8 == 0) goto L_0x0256;
    L_0x0247:
        r8 = r1.zzadj;	 Catch:{ zzib -> 0x05ae }
        r8 = r8.zzm(r4);	 Catch:{ zzib -> 0x05ae }
        r9 = r1.zzadj;	 Catch:{ zzib -> 0x05ae }
        r9.zzc(r8, r7);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r5, r8);	 Catch:{ zzib -> 0x05ae }
        r7 = r8;
    L_0x0256:
        r5 = r1.zzadj;	 Catch:{ zzib -> 0x05ae }
        r5 = r5.zzi(r7);	 Catch:{ zzib -> 0x05ae }
        r6 = r1.zzadj;	 Catch:{ zzib -> 0x05ae }
        r4 = r6.zzn(r4);	 Catch:{ zzib -> 0x05ae }
        r0.zza(r5, r4, r10);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0267:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r6 = r1.zzay(r6);	 Catch:{ zzib -> 0x05ae }
        r7 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r4 = r7.zza(r2, r4);	 Catch:{ zzib -> 0x05ae }
        r0.zzb(r4, r6, r10);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0279:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzw(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0286:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzv(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0293:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzu(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x02a0:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzt(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x02ad:
        r7 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r8 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r7.zza(r2, r8);	 Catch:{ zzib -> 0x05ae }
        r0.zzs(r5);	 Catch:{ zzib -> 0x05ae }
        r6 = r1.zzba(r6);	 Catch:{ zzib -> 0x05ae }
        r14 = com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r5, r6, r14, r11);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x02c2:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzr(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x02cf:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzo(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x02dc:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzn(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x02e9:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzm(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x02f6:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzl(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0303:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzj(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0310:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzk(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x031d:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzi(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x032a:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzh(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0337:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzw(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0344:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzv(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0351:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzu(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x035e:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzt(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x036b:
        r7 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r8 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r5 = r7.zza(r2, r8);	 Catch:{ zzib -> 0x05ae }
        r0.zzs(r5);	 Catch:{ zzib -> 0x05ae }
        r6 = r1.zzba(r6);	 Catch:{ zzib -> 0x05ae }
        r14 = com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r5, r6, r14, r11);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0380:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzr(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x038d:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzq(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x039a:
        r4 = r1.zzay(r6);	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r7 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r7.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zza(r5, r4, r10);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x03ab:
        r4 = zzbd(r5);	 Catch:{ zzib -> 0x05ae }
        if (r4 == 0) goto L_0x03be;
    L_0x03b1:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzp(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x03be:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.readStringList(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x03cb:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzo(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x03d8:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzn(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x03e5:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzm(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x03f2:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzl(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x03ff:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzj(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x040c:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzk(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0419:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzi(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0426:
        r4 = r1.zzadi;	 Catch:{ zzib -> 0x05ae }
        r5 = r5 & r8;
        r5 = (long) r5;	 Catch:{ zzib -> 0x05ae }
        r4 = r4.zza(r2, r5);	 Catch:{ zzib -> 0x05ae }
        r0.zzh(r4);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0433:
        r4 = r1.zza(r2, r6);	 Catch:{ zzib -> 0x05ae }
        if (r4 == 0) goto L_0x0451;
    L_0x0439:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r2, r4);	 Catch:{ zzib -> 0x05ae }
        r6 = r1.zzay(r6);	 Catch:{ zzib -> 0x05ae }
        r6 = r0.zzb(r6, r10);	 Catch:{ zzib -> 0x05ae }
        r6 = com.google.android.gms.internal.firebase_auth.zzht.zzb(r7, r6);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0451:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r1.zzay(r6);	 Catch:{ zzib -> 0x05ae }
        r7 = r0.zzb(r7, r10);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0464:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgw();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0473:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgv();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0482:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgu();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0491:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgt();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x04a0:
        r7 = r19.zzgs();	 Catch:{ zzib -> 0x05ae }
        r9 = r1.zzba(r6);	 Catch:{ zzib -> 0x05ae }
        if (r9 == 0) goto L_0x04b7;
    L_0x04aa:
        r9 = r9.zzd(r7);	 Catch:{ zzib -> 0x05ae }
        if (r9 == 0) goto L_0x04b1;
    L_0x04b0:
        goto L_0x04b7;
    L_0x04b1:
        r14 = com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r7, r14, r11);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x04b7:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x04c2:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgr();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x04d1:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgq();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x04e0:
        r4 = r1.zza(r2, r6);	 Catch:{ zzib -> 0x05ae }
        if (r4 == 0) goto L_0x04fe;
    L_0x04e6:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r2, r4);	 Catch:{ zzib -> 0x05ae }
        r6 = r1.zzay(r6);	 Catch:{ zzib -> 0x05ae }
        r6 = r0.zza(r6, r10);	 Catch:{ zzib -> 0x05ae }
        r6 = com.google.android.gms.internal.firebase_auth.zzht.zzb(r7, r6);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x04fe:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r1.zzay(r6);	 Catch:{ zzib -> 0x05ae }
        r7 = r0.zza(r7, r10);	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0511:
        r1.zza(r2, r5, r0);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0519:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgo();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0528:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgn();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0537:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgm();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0546:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgl();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0555:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgj();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0564:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.zzgk();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0573:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.readFloat();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0582:
        r4 = r5 & r8;
        r4 = (long) r4;	 Catch:{ zzib -> 0x05ae }
        r7 = r19.readDouble();	 Catch:{ zzib -> 0x05ae }
        com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7);	 Catch:{ zzib -> 0x05ae }
        r1.zzb(r2, r6);	 Catch:{ zzib -> 0x05ae }
        goto L_0x0011;
    L_0x0591:
        r4 = r11.zza(r14, r0);	 Catch:{ zzib -> 0x05ae }
        if (r4 != 0) goto L_0x0011;
    L_0x0597:
        r0 = r1.zzadf;
    L_0x0599:
        r3 = r1.zzadg;
        if (r0 >= r3) goto L_0x05a8;
    L_0x059d:
        r3 = r1.zzade;
        r3 = r3[r0];
        r14 = r1.zza(r2, r3, r14, r11);
        r0 = r0 + 1;
        goto L_0x0599;
    L_0x05a8:
        if (r14 == 0) goto L_0x05ad;
    L_0x05aa:
        r11.zzg(r2, r14);
    L_0x05ad:
        return;
    L_0x05ae:
        r11.zza(r0);	 Catch:{ all -> 0x05d5 }
        if (r14 != 0) goto L_0x05b8;
    L_0x05b3:
        r4 = r11.zzt(r2);	 Catch:{ all -> 0x05d5 }
        r14 = r4;
    L_0x05b8:
        r4 = r11.zza(r14, r0);	 Catch:{ all -> 0x05d5 }
        if (r4 != 0) goto L_0x0011;
    L_0x05be:
        r0 = r1.zzadf;
    L_0x05c0:
        r3 = r1.zzadg;
        if (r0 >= r3) goto L_0x05cf;
    L_0x05c4:
        r3 = r1.zzade;
        r3 = r3[r0];
        r14 = r1.zza(r2, r3, r14, r11);
        r0 = r0 + 1;
        goto L_0x05c0;
    L_0x05cf:
        if (r14 == 0) goto L_0x05d4;
    L_0x05d1:
        r11.zzg(r2, r14);
    L_0x05d4:
        return;
    L_0x05d5:
        r0 = move-exception;
        r3 = r1.zzadf;
    L_0x05d8:
        r4 = r1.zzadg;
        if (r3 >= r4) goto L_0x05e7;
    L_0x05dc:
        r4 = r1.zzade;
        r4 = r4[r3];
        r14 = r1.zza(r2, r4, r14, r11);
        r3 = r3 + 1;
        goto L_0x05d8;
    L_0x05e7:
        if (r14 == 0) goto L_0x05ec;
    L_0x05e9:
        r11.zzg(r2, r14);
    L_0x05ec:
        throw r0;
    L_0x05ed:
        r0 = new java.lang.NullPointerException;
        r0.<init>();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.zza(java.lang.Object, com.google.android.gms.internal.firebase_auth.zzjp, com.google.android.gms.internal.firebase_auth.zzhf):void");
    }

    private final zzjs zzay(int i) {
        i = (i / 3) << 1;
        zzjs zzjs = (zzjs) this.zzacy[i];
        if (zzjs != null) {
            return zzjs;
        }
        zzjs = zzjo.zzjv().zzf((Class) this.zzacy[i + 1]);
        this.zzacy[i] = zzjs;
        return zzjs;
    }

    private final Object zzaz(int i) {
        return this.zzacy[(i / 3) << 1];
    }

    private final zzhy zzba(int i) {
        return (zzhy) this.zzacy[((i / 3) << 1) + 1];
    }

    public final void zzf(T t) {
        int i;
        int i2 = this.zzadf;
        while (true) {
            i = this.zzadg;
            if (i2 >= i) {
                break;
            }
            long zzbb = (long) (zzbb(this.zzade[i2]) & 1048575);
            Object zzp = zzkq.zzp(t, zzbb);
            if (zzp != null) {
                zzkq.zza((Object) t, zzbb, this.zzadj.zzl(zzp));
            }
            i2++;
        }
        i2 = this.zzade.length;
        while (i < i2) {
            this.zzadi.zzb(t, (long) this.zzade[i]);
            i++;
        }
        this.zzacs.zzf(t);
        if (this.zzact) {
            this.zzacu.zzf((Object) t);
        }
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzkk<UT, UB> zzkk) {
        int i2 = this.zzacx[i];
        obj = zzkq.zzp(obj, (long) (zzbb(i) & 1048575));
        if (obj == null) {
            return ub;
        }
        zzhy zzba = zzba(i);
        if (zzba == null) {
            return ub;
        }
        return zza(i, i2, this.zzadj.zzi(obj), zzba, ub, zzkk);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzhy zzhy, UB ub, zzkk<UT, UB> zzkk) {
        zzit zzn = this.zzadj.zzn(zzaz(i));
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (!zzhy.zzd(((Integer) entry.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzkk.zzkn();
                }
                zzgn zzr = zzgf.zzr(zziu.zza(zzn, entry.getKey(), entry.getValue()));
                try {
                    zziu.zza(zzr.zzgh(), zzn, entry.getKey(), entry.getValue());
                    zzkk.zza((Object) ub, i2, zzr.zzgg());
                    it.remove();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    public final boolean zzp(T t) {
        int i = 0;
        int i2 = -1;
        int i3 = 0;
        while (true) {
            boolean z = true;
            if (i >= this.zzadf) {
                return !this.zzact || this.zzacu.zzd(t).isInitialized();
            } else {
                int i4;
                int i5;
                int i6 = this.zzade[i];
                int i7 = this.zzacx[i6];
                int zzbb = zzbb(i6);
                if (this.zzadc) {
                    i4 = 0;
                } else {
                    i4 = this.zzacx[i6 + 2];
                    i5 = i4 & 1048575;
                    i4 = 1 << (i4 >>> 20);
                    if (i5 != i2) {
                        i3 = zzacw.getInt(t, (long) i5);
                        i2 = i5;
                    }
                }
                if (((268435456 & zzbb) != 0 ? 1 : null) != null && !zza((Object) t, i6, i3, i4)) {
                    return false;
                }
                i5 = (267386880 & zzbb) >>> 20;
                if (i5 != 9 && i5 != 17) {
                    zzjs zzjs;
                    if (i5 != 27) {
                        if (i5 == 60 || i5 == 68) {
                            if (zza((Object) t, i7, i6) && !zza((Object) t, zzbb, zzay(i6))) {
                                return false;
                            }
                        } else if (i5 != 49) {
                            if (i5 != 50) {
                                continue;
                            } else {
                                Map zzj = this.zzadj.zzj(zzkq.zzp(t, (long) (zzbb & 1048575)));
                                if (!zzj.isEmpty()) {
                                    if (this.zzadj.zzn(zzaz(i6)).zzacm.zzkx() == zzle.MESSAGE) {
                                        zzjs = null;
                                        for (Object next : zzj.values()) {
                                            if (zzjs == null) {
                                                zzjs = zzjo.zzjv().zzf(next.getClass());
                                            }
                                            if (!zzjs.zzp(next)) {
                                                z = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (!z) {
                                    return false;
                                }
                            }
                        }
                    }
                    List list = (List) zzkq.zzp(t, (long) (zzbb & 1048575));
                    if (!list.isEmpty()) {
                        zzjs = zzay(i6);
                        for (zzbb = 0; zzbb < list.size(); zzbb++) {
                            if (!zzjs.zzp(list.get(zzbb))) {
                                z = false;
                                break;
                            }
                        }
                    }
                    if (!z) {
                        return false;
                    }
                } else if (zza((Object) t, i6, i3, i4) && !zza((Object) t, zzbb, zzay(i6))) {
                    return false;
                }
                i++;
            }
        }
    }

    private static boolean zza(Object obj, int i, zzjs zzjs) {
        return zzjs.zzp(zzkq.zzp(obj, (long) (i & 1048575)));
    }

    private static void zza(int i, Object obj, zzlh zzlh) throws IOException {
        if (obj instanceof String) {
            zzlh.zza(i, (String) obj);
        } else {
            zzlh.zza(i, (zzgf) obj);
        }
    }

    private final void zza(Object obj, int i, zzjp zzjp) throws IOException {
        if (zzbd(i)) {
            zzkq.zza(obj, (long) (i & 1048575), zzjp.zzgp());
        } else if (this.zzadb) {
            zzkq.zza(obj, (long) (i & 1048575), zzjp.readString());
        } else {
            zzkq.zza(obj, (long) (i & 1048575), zzjp.zzgq());
        }
    }

    private final int zzbb(int i) {
        return this.zzacx[i + 1];
    }

    private final int zzbc(int i) {
        return this.zzacx[i + 2];
    }

    private static <T> double zzf(T t, long j) {
        return ((Double) zzkq.zzp(t, j)).doubleValue();
    }

    private static <T> float zzg(T t, long j) {
        return ((Float) zzkq.zzp(t, j)).floatValue();
    }

    private static <T> int zzh(T t, long j) {
        return ((Integer) zzkq.zzp(t, j)).intValue();
    }

    private static <T> long zzi(T t, long j) {
        return ((Long) zzkq.zzp(t, j)).longValue();
    }

    private static <T> boolean zzj(T t, long j) {
        return ((Boolean) zzkq.zzp(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza((Object) t, i) == zza((Object) t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        if (this.zzadc) {
            return zza((Object) t, i);
        }
        return (i2 & i3) != 0;
    }

    private final boolean zza(T t, int i) {
        if (this.zzadc) {
            i = zzbb(i);
            long j = (long) (i & 1048575);
            switch ((i & 267386880) >>> 20) {
                case 0:
                    return zzkq.zzo(t, j) != 0.0d;
                case 1:
                    return zzkq.zzn(t, j) != 0.0f;
                case 2:
                    return zzkq.zzl(t, j) != 0;
                case 3:
                    return zzkq.zzl(t, j) != 0;
                case 4:
                    return zzkq.zzk(t, j) != 0;
                case 5:
                    return zzkq.zzl(t, j) != 0;
                case 6:
                    return zzkq.zzk(t, j) != 0;
                case 7:
                    return zzkq.zzm(t, j);
                case 8:
                    Object zzp = zzkq.zzp(t, j);
                    if (zzp instanceof String) {
                        return !((String) zzp).isEmpty();
                    } else {
                        if (zzp instanceof zzgf) {
                            return !zzgf.zzvv.equals(zzp);
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                case 9:
                    return zzkq.zzp(t, j) != null;
                case 10:
                    return !zzgf.zzvv.equals(zzkq.zzp(t, j));
                case 11:
                    return zzkq.zzk(t, j) != 0;
                case 12:
                    return zzkq.zzk(t, j) != 0;
                case 13:
                    return zzkq.zzk(t, j) != 0;
                case 14:
                    return zzkq.zzl(t, j) != 0;
                case 15:
                    return zzkq.zzk(t, j) != 0;
                case 16:
                    return zzkq.zzl(t, j) != 0;
                case 17:
                    return zzkq.zzp(t, j) != null;
                default:
                    throw new IllegalArgumentException();
            }
        }
        i = zzbc(i);
        return (zzkq.zzk(t, (long) (i & 1048575)) & (1 << (i >>> 20))) != 0;
    }

    private final void zzb(T t, int i) {
        if (!this.zzadc) {
            i = zzbc(i);
            long j = (long) (i & 1048575);
            zzkq.zzb((Object) t, j, zzkq.zzk(t, j) | (1 << (i >>> 20)));
        }
    }

    private final boolean zza(T t, int i, int i2) {
        return zzkq.zzk(t, (long) (zzbc(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzkq.zzb((Object) t, (long) (zzbc(i2) & 1048575), i);
    }
}
