package com.google.android.gms.internal.firebase_ml;

import com.adobe.xmp.options.PropertyOptions;
import com.google.android.gms.internal.firebase_ml.zzue.zzf;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import sun.misc.Unsafe;

final class zzvs<T> implements zzwe<T> {
    private static final int[] zzbqv = new int[0];
    private static final Unsafe zzbqw = zzxc.zztl();
    private final int[] zzbqx;
    private final Object[] zzbqy;
    private final int zzbqz;
    private final int zzbra;
    private final zzvo zzbrb;
    private final boolean zzbrc;
    private final boolean zzbrd;
    private final boolean zzbre;
    private final boolean zzbrf;
    private final int[] zzbrg;
    private final int zzbrh;
    private final int zzbri;
    private final zzvw zzbrj;
    private final zzuy zzbrk;
    private final zzww<?, ?> zzbrl;
    private final zztt<?> zzbrm;
    private final zzvj zzbrn;

    private zzvs(int[] iArr, Object[] objArr, int i, int i2, zzvo zzvo, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzvw zzvw, zzuy zzuy, zzww<?, ?> zzww, zztt<?> zztt, zzvj zzvj) {
        this.zzbqx = iArr;
        this.zzbqy = objArr;
        this.zzbqz = i;
        this.zzbra = i2;
        this.zzbrd = zzvo instanceof zzue;
        this.zzbre = z;
        boolean z3 = zztt != null && zztt.zze(zzvo);
        this.zzbrc = z3;
        this.zzbrf = false;
        this.zzbrg = iArr2;
        this.zzbrh = i3;
        this.zzbri = i4;
        this.zzbrj = zzvw;
        this.zzbrk = zzuy;
        this.zzbrl = zzww;
        this.zzbrm = zztt;
        this.zzbrb = zzvo;
        this.zzbrn = zzvj;
    }

    static <T> zzvs<T> zza(Class<T> cls, zzvm zzvm, zzvw zzvw, zzuy zzuy, zzww<?, ?> zzww, zztt<?> zztt, zzvj zzvj) {
        zzvm zzvm2 = zzvm;
        if (zzvm2 instanceof zzwd) {
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
            zzwd zzwd = (zzwd) zzvm2;
            int i10 = 0;
            boolean z2 = zzwd.zzsh() == zzf.zzbox;
            String zzsq = zzwd.zzsq();
            int length = zzsq.length();
            int charAt3 = zzsq.charAt(0);
            if (charAt3 >= 55296) {
                char charAt4;
                i = charAt3 & 8191;
                charAt3 = 1;
                i2 = 13;
                while (true) {
                    i3 = charAt3 + 1;
                    charAt4 = zzsq.charAt(charAt3);
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
            i2 = zzsq.charAt(i3);
            if (i2 >= 55296) {
                i2 &= 8191;
                i3 = 13;
                while (true) {
                    i4 = i + 1;
                    charAt = zzsq.charAt(i);
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
                iArr = zzbqv;
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
                i2 = zzsq.charAt(i4);
                if (i2 >= 55296) {
                    i2 &= 8191;
                    i3 = 13;
                    while (true) {
                        i4 = i + 1;
                        charAt = zzsq.charAt(i);
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
                i3 = zzsq.charAt(i4);
                if (i3 >= 55296) {
                    i3 &= 8191;
                    i4 = 13;
                    while (true) {
                        i7 = i + 1;
                        charAt = zzsq.charAt(i);
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
                charAt2 = zzsq.charAt(i7);
                if (charAt2 >= 55296) {
                    i4 = charAt2 & 8191;
                    i7 = 13;
                    while (true) {
                        i5 = i + 1;
                        charAt = zzsq.charAt(i);
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
                c2 = zzsq.charAt(i5);
                if (c2 >= 55296) {
                    i7 = c2 & 8191;
                    i5 = 13;
                    while (true) {
                        i11 = i + 1;
                        charAt = zzsq.charAt(i);
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
                i5 = zzsq.charAt(i11);
                if (i5 >= 55296) {
                    i5 &= 8191;
                    i11 = 13;
                    while (true) {
                        i6 = i + 1;
                        charAt = zzsq.charAt(i);
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
                i = zzsq.charAt(i);
                if (i >= 55296) {
                    i &= 8191;
                    i6 = 13;
                    while (true) {
                        i12 = i11 + 1;
                        charAt5 = zzsq.charAt(i11);
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
                i11 = zzsq.charAt(i11);
                if (i11 >= 55296) {
                    i12 = 13;
                    i8 = i6;
                    i6 = i11 & 8191;
                    i11 = i8;
                    while (true) {
                        i9 = i11 + 1;
                        charAt5 = zzsq.charAt(i11);
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
                i10 = zzsq.charAt(i10);
                if (i10 >= 55296) {
                    char charAt6;
                    i12 = 13;
                    i8 = i6;
                    i6 = i10 & 8191;
                    i10 = i8;
                    while (true) {
                        i9 = i10 + 1;
                        charAt6 = zzsq.charAt(i10);
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
            Unsafe unsafe = zzbqw;
            Object[] zzsr = zzwd.zzsr();
            Class cls2 = zzwd.zzsj().getClass();
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
                i4 = zzsq.charAt(i4);
                char c5 = 55296;
                if (i4 >= 55296) {
                    i17 = 13;
                    i8 = i24;
                    i24 = i4 & 8191;
                    i4 = i8;
                    while (true) {
                        i18 = i4 + 1;
                        charAt2 = zzsq.charAt(i4);
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
                i19 = zzsq.charAt(i19);
                i17 = length;
                char c6 = 55296;
                if (i19 >= 55296) {
                    i18 = 13;
                    i8 = i24;
                    i24 = i19 & 8191;
                    i19 = i8;
                    while (true) {
                        i20 = i19 + 1;
                        c5 = zzsq.charAt(i19);
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
                    length = zzsq.charAt(length);
                    charAt = 55296;
                    if (length >= 55296) {
                        int i27;
                        length &= 8191;
                        i26 = 13;
                        while (true) {
                            i27 = i21 + 1;
                            charAt7 = zzsq.charAt(i21);
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
                        objArr[((i16 / 3) << 1) + 1] = zzsr[i9];
                        i9 = i22;
                    } else {
                        if (i == 12 && (charAt3 & 1) == 1) {
                            i21 = i9 + 1;
                            objArr[((i16 / 3) << 1) + 1] = zzsr[i9];
                            i9 = i21;
                        }
                        i21 = 1;
                    }
                    length <<= i21;
                    Object obj = zzsr[length];
                    if (obj instanceof Field) {
                        field = (Field) obj;
                    } else {
                        field = zza(cls2, (String) obj);
                        zzsr[length] = field;
                    }
                    charAt7 = c;
                    i2 = (int) unsafe.objectFieldOffset(field);
                    length++;
                    obj = zzsr[length];
                    i20 = i2;
                    if (obj instanceof Field) {
                        field = (Field) obj;
                    } else {
                        field = zza(cls2, (String) obj);
                        zzsr[length] = field;
                    }
                    str = zzsq;
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
                    Field zza = zza(cls2, (String) zzsr[i9]);
                    c4 = c2;
                    if (i10 == 9 || i10 == 17) {
                        c3 = charAt7;
                        objArr[((i16 / 3) << 1) + 1] = zza.getType();
                    } else {
                        if (i10 == 27 || i10 == 49) {
                            c3 = charAt7;
                            i22 = i + 1;
                            objArr[((i16 / 3) << 1) + 1] = zzsr[i];
                        } else if (i10 == 12 || i10 == 30 || i10 == 44) {
                            c3 = charAt7;
                            if ((charAt3 & 1) == 1) {
                                i22 = i + 1;
                                objArr[((i16 / 3) << 1) + 1] = zzsr[i];
                            }
                        } else if (i10 == 50) {
                            i7 = i14 + 1;
                            iArr[i14] = i16;
                            i14 = (i16 / 3) << 1;
                            i20 = i + 1;
                            objArr[i14] = zzsr[i];
                            if ((i19 & 2048) != 0) {
                                i = i20 + 1;
                                objArr[i14 + 1] = zzsr[i20];
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
                            str = zzsq;
                            cls3 = cls2;
                            i22 = i;
                            i21 = length;
                            length = 0;
                            i = 0;
                        } else {
                            Field field2;
                            i21 = length + 1;
                            length = zzsq.charAt(length);
                            if (length >= 55296) {
                                int i28;
                                length &= 8191;
                                int i29 = 13;
                                while (true) {
                                    i28 = i21 + 1;
                                    charAt7 = zzsq.charAt(i21);
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
                            Object obj2 = zzsr[i22];
                            str = zzsq;
                            if (obj2 instanceof Field) {
                                field2 = (Field) obj2;
                            } else {
                                field2 = zza(cls2, (String) obj2);
                                zzsr[i22] = field2;
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
                    str = zzsq;
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
                zzsq = str;
            }
            z = z2;
            return new zzvs(iArr2, objArr, c, c2, zzwd.zzsj(), z2, false, iArr, i10, i13, zzvw, zzuy, zzww, zztt, zzvj);
        }
        int zzsh = ((zzwr) zzvm2).zzsh();
        int i30 = zzf.zzbox;
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
        return this.zzbrj.newInstance(this.zzbrb);
    }

    /* JADX WARNING: Missing block: B:8:0x0038, code:
            if (com.google.android.gms.internal.firebase_ml.zzwg.zzh(com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6), com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:14:0x006a, code:
            if (com.google.android.gms.internal.firebase_ml.zzwg.zzh(com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6), com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:18:0x007e, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzl(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:22:0x0090, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:26:0x00a4, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzl(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:30:0x00b6, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:34:0x00c8, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:38:0x00da, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:42:0x00f0, code:
            if (com.google.android.gms.internal.firebase_ml.zzwg.zzh(com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6), com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:46:0x0106, code:
            if (com.google.android.gms.internal.firebase_ml.zzwg.zzh(com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6), com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:50:0x011c, code:
            if (com.google.android.gms.internal.firebase_ml.zzwg.zzh(com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6), com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:54:0x012e, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzm(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzm(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:58:0x0140, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:62:0x0154, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzl(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:66:0x0165, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:70:0x0178, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzl(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:74:0x018b, code:
            if (com.google.android.gms.internal.firebase_ml.zzxc.zzl(r10, r6) == com.google.android.gms.internal.firebase_ml.zzxc.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:78:0x01a4, code:
            if (java.lang.Float.floatToIntBits(com.google.android.gms.internal.firebase_ml.zzxc.zzn(r10, r6)) == java.lang.Float.floatToIntBits(com.google.android.gms.internal.firebase_ml.zzxc.zzn(r11, r6))) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:82:0x01bf, code:
            if (java.lang.Double.doubleToLongBits(com.google.android.gms.internal.firebase_ml.zzxc.zzo(r10, r6)) == java.lang.Double.doubleToLongBits(com.google.android.gms.internal.firebase_ml.zzxc.zzo(r11, r6))) goto L_0x01c2;
     */
    /* JADX WARNING: Missing block: B:83:0x01c1, code:
            r3 = false;
     */
    public final boolean equals(T r10, T r11) {
        /*
        r9 = this;
        r0 = r9.zzbqx;
        r0 = r0.length;
        r1 = 0;
        r2 = 0;
    L_0x0005:
        r3 = 1;
        if (r2 >= r0) goto L_0x01c9;
    L_0x0008:
        r4 = r9.zzdl(r2);
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
        r4 = r9.zzdm(r2);
        r4 = r4 & r5;
        r4 = (long) r4;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r4);
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r4);
        if (r8 != r4) goto L_0x01c1;
    L_0x002c:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6);
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzh(r4, r5);
        if (r4 != 0) goto L_0x01c2;
    L_0x003a:
        goto L_0x01c1;
    L_0x003c:
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6);
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzh(r3, r4);
        goto L_0x01c2;
    L_0x004a:
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6);
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzh(r3, r4);
        goto L_0x01c2;
    L_0x0058:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x005e:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6);
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzh(r4, r5);
        if (r4 != 0) goto L_0x01c2;
    L_0x006c:
        goto L_0x01c1;
    L_0x006e:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0074:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r10, r6);
        r6 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r11, r6);
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x01c2;
    L_0x0080:
        goto L_0x01c1;
    L_0x0082:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0088:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x0092:
        goto L_0x01c1;
    L_0x0094:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x009a:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r10, r6);
        r6 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r11, r6);
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x01c2;
    L_0x00a6:
        goto L_0x01c1;
    L_0x00a8:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x00ae:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x00b8:
        goto L_0x01c1;
    L_0x00ba:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x00c0:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x00ca:
        goto L_0x01c1;
    L_0x00cc:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x00d2:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x00dc:
        goto L_0x01c1;
    L_0x00de:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x00e4:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6);
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzh(r4, r5);
        if (r4 != 0) goto L_0x01c2;
    L_0x00f2:
        goto L_0x01c1;
    L_0x00f4:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x00fa:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6);
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzh(r4, r5);
        if (r4 != 0) goto L_0x01c2;
    L_0x0108:
        goto L_0x01c1;
    L_0x010a:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0110:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r6);
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzh(r4, r5);
        if (r4 != 0) goto L_0x01c2;
    L_0x011e:
        goto L_0x01c1;
    L_0x0120:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0126:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzm(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzm(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x0130:
        goto L_0x01c1;
    L_0x0132:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0138:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x0142:
        goto L_0x01c1;
    L_0x0144:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x014a:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r10, r6);
        r6 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r11, r6);
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x01c2;
    L_0x0156:
        goto L_0x01c1;
    L_0x0157:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x015d:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r10, r6);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r11, r6);
        if (r4 == r5) goto L_0x01c2;
    L_0x0167:
        goto L_0x01c1;
    L_0x0168:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x016e:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r10, r6);
        r6 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r11, r6);
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x01c2;
    L_0x017a:
        goto L_0x01c1;
    L_0x017b:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0181:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r10, r6);
        r6 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r11, r6);
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x01c2;
    L_0x018d:
        goto L_0x01c1;
    L_0x018e:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x0194:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzn(r10, r6);
        r4 = java.lang.Float.floatToIntBits(r4);
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzn(r11, r6);
        r5 = java.lang.Float.floatToIntBits(r5);
        if (r4 == r5) goto L_0x01c2;
    L_0x01a6:
        goto L_0x01c1;
    L_0x01a7:
        r4 = r9.zzc(r10, r11, r2);
        if (r4 == 0) goto L_0x01c1;
    L_0x01ad:
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzo(r10, r6);
        r4 = java.lang.Double.doubleToLongBits(r4);
        r6 = com.google.android.gms.internal.firebase_ml.zzxc.zzo(r11, r6);
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
        r0 = r9.zzbrl;
        r0 = r0.zzae(r10);
        r2 = r9.zzbrl;
        r2 = r2.zzae(r11);
        r0 = r0.equals(r2);
        if (r0 != 0) goto L_0x01dc;
    L_0x01db:
        return r1;
    L_0x01dc:
        r0 = r9.zzbrc;
        if (r0 == 0) goto L_0x01f1;
    L_0x01e0:
        r0 = r9.zzbrm;
        r10 = r0.zzo(r10);
        r0 = r9.zzbrm;
        r11 = r0.zzo(r11);
        r10 = r10.equals(r11);
        return r10;
    L_0x01f1:
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzvs.equals(java.lang.Object, java.lang.Object):boolean");
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
        r0 = r8.zzbqx;
        r0 = r0.length;
        r1 = 0;
        r2 = 0;
    L_0x0005:
        if (r1 >= r0) goto L_0x022c;
    L_0x0007:
        r3 = r8.zzdl(r1);
        r4 = r8.zzbqx;
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
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r9, r5);
        r2 = r2 * 53;
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x0032:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x0038:
        r2 = r2 * 53;
        r3 = zzi(r9, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
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
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
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
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r9, r5);
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x00a0:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x00a6:
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r9, r5);
        r2 = r2 * 53;
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x00b2:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x00b8:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r9, r5);
        r3 = (java.lang.String) r3;
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x00c6:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x00cc:
        r2 = r2 * 53;
        r3 = zzj(r9, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzaj(r3);
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
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
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
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
        goto L_0x0227;
    L_0x0118:
        r3 = r8.zza(r9, r4, r1);
        if (r3 == 0) goto L_0x0228;
    L_0x011e:
        r2 = r2 * 53;
        r3 = zzi(r9, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
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
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
        goto L_0x0227;
    L_0x0152:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r9, r5);
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x015e:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r9, r5);
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x016a:
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r9, r5);
        if (r3 == 0) goto L_0x01c3;
    L_0x0170:
        r7 = r3.hashCode();
        goto L_0x01c3;
    L_0x0175:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r9, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
        goto L_0x0227;
    L_0x0181:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r9, r5);
        goto L_0x0227;
    L_0x0189:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r9, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
        goto L_0x0227;
    L_0x0195:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r9, r5);
        goto L_0x0227;
    L_0x019d:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r9, r5);
        goto L_0x0227;
    L_0x01a5:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r9, r5);
        goto L_0x0227;
    L_0x01ad:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r9, r5);
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x01b9:
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r9, r5);
        if (r3 == 0) goto L_0x01c3;
    L_0x01bf:
        r7 = r3.hashCode();
    L_0x01c3:
        r2 = r2 * 53;
        r2 = r2 + r7;
        goto L_0x0228;
    L_0x01c7:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r9, r5);
        r3 = (java.lang.String) r3;
        r3 = r3.hashCode();
        goto L_0x0227;
    L_0x01d4:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzm(r9, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzaj(r3);
        goto L_0x0227;
    L_0x01df:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r9, r5);
        goto L_0x0227;
    L_0x01e6:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r9, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
        goto L_0x0227;
    L_0x01f1:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r9, r5);
        goto L_0x0227;
    L_0x01f8:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r9, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
        goto L_0x0227;
    L_0x0203:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r9, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
        goto L_0x0227;
    L_0x020e:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzn(r9, r5);
        r3 = java.lang.Float.floatToIntBits(r3);
        goto L_0x0227;
    L_0x0219:
        r2 = r2 * 53;
        r3 = com.google.android.gms.internal.firebase_ml.zzxc.zzo(r9, r5);
        r3 = java.lang.Double.doubleToLongBits(r3);
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zzz(r3);
    L_0x0227:
        r2 = r2 + r3;
    L_0x0228:
        r1 = r1 + 3;
        goto L_0x0005;
    L_0x022c:
        r2 = r2 * 53;
        r0 = r8.zzbrl;
        r0 = r0.zzae(r9);
        r0 = r0.hashCode();
        r2 = r2 + r0;
        r0 = r8.zzbrc;
        if (r0 == 0) goto L_0x024a;
    L_0x023d:
        r2 = r2 * 53;
        r0 = r8.zzbrm;
        r9 = r0.zzo(r9);
        r9 = r9.hashCode();
        r2 = r2 + r9;
    L_0x024a:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzvs.hashCode(java.lang.Object):int");
    }

    public final void zzg(T t, T t2) {
        if (t2 != null) {
            for (int i = 0; i < this.zzbqx.length; i += 3) {
                int zzdl = zzdl(i);
                long j = (long) (1048575 & zzdl);
                int i2 = this.zzbqx[i];
                switch ((zzdl & 267386880) >>> 20) {
                    case 0:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzo(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 1:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzn(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 2:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzl(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 3:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzl(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 4:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 5:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzl(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 6:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 7:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzm(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 8:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzp(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 9:
                        zza((Object) t, (Object) t2, i);
                        break;
                    case 10:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzp(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 11:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 12:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 13:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 14:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzl(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 15:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzk(t2, j));
                        zzb((Object) t, i);
                        break;
                    case 16:
                        if (!zza((Object) t2, i)) {
                            break;
                        }
                        zzxc.zza((Object) t, j, zzxc.zzl(t2, j));
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
                        this.zzbrk.zza(t, t2, j);
                        break;
                    case 50:
                        zzwg.zza(this.zzbrn, (Object) t, (Object) t2, j);
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
                        zzxc.zza((Object) t, j, zzxc.zzp(t2, j));
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
                        zzxc.zza((Object) t, j, zzxc.zzp(t2, j));
                        zzb((Object) t, i2, i);
                        break;
                    case 68:
                        zzb((Object) t, (Object) t2, i);
                        break;
                    default:
                        break;
                }
            }
            if (!this.zzbre) {
                zzwg.zza(this.zzbrl, (Object) t, (Object) t2);
                if (this.zzbrc) {
                    zzwg.zza(this.zzbrm, (Object) t, (Object) t2);
                    return;
                }
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    private final void zza(T t, T t2, int i) {
        long zzdl = (long) (zzdl(i) & 1048575);
        if (zza((Object) t2, i)) {
            Object zzp = zzxc.zzp(t, zzdl);
            Object zzp2 = zzxc.zzp(t2, zzdl);
            if (zzp == null || zzp2 == null) {
                if (zzp2 != null) {
                    zzxc.zza((Object) t, zzdl, zzp2);
                    zzb((Object) t, i);
                }
                return;
            }
            zzxc.zza((Object) t, zzdl, zzug.zze(zzp, zzp2));
            zzb((Object) t, i);
        }
    }

    private final void zzb(T t, T t2, int i) {
        int zzdl = zzdl(i);
        int i2 = this.zzbqx[i];
        long j = (long) (zzdl & 1048575);
        if (zza((Object) t2, i2, i)) {
            Object zzp = zzxc.zzp(t, j);
            Object zzp2 = zzxc.zzp(t2, j);
            if (zzp == null || zzp2 == null) {
                if (zzp2 != null) {
                    zzxc.zza((Object) t, j, zzp2);
                    zzb((Object) t, i2, i);
                }
                return;
            }
            zzxc.zza((Object) t, j, zzug.zze(zzp, zzp2));
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
    public final int zzaa(T r20) {
        /*
        r19 = this;
        r0 = r19;
        r1 = r20;
        r2 = r0.zzbre;
        r3 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r4 = 0;
        r7 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        r8 = 1;
        r9 = 0;
        r11 = 0;
        if (r2 == 0) goto L_0x04f2;
    L_0x0012:
        r2 = zzbqw;
        r12 = 0;
        r13 = 0;
    L_0x0016:
        r14 = r0.zzbqx;
        r14 = r14.length;
        if (r12 >= r14) goto L_0x04ea;
    L_0x001b:
        r14 = r0.zzdl(r12);
        r15 = r14 & r3;
        r15 = r15 >>> 20;
        r3 = r0.zzbqx;
        r3 = r3[r12];
        r14 = r14 & r7;
        r5 = (long) r14;
        r14 = com.google.android.gms.internal.firebase_ml.zztz.DOUBLE_LIST_PACKED;
        r14 = r14.id();
        if (r15 < r14) goto L_0x0041;
    L_0x0031:
        r14 = com.google.android.gms.internal.firebase_ml.zztz.SINT64_LIST_PACKED;
        r14 = r14.id();
        if (r15 > r14) goto L_0x0041;
    L_0x0039:
        r14 = r0.zzbqx;
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
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r1, r5);
        r5 = (com.google.android.gms.internal.firebase_ml.zzvo) r5;
        r6 = r0.zzdi(r12);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r3, r5, r6);
        goto L_0x03c9;
    L_0x005d:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0063:
        r5 = zzi(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzf(r3, r5);
        goto L_0x03c9;
    L_0x006d:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0073:
        r5 = zzh(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzn(r3, r5);
        goto L_0x03c9;
    L_0x007d:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0083:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzh(r3, r9);
        goto L_0x03c9;
    L_0x0089:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x008f:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzp(r3, r11);
        goto L_0x03c9;
    L_0x0095:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x009b:
        r5 = zzh(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzq(r3, r5);
        goto L_0x03c9;
    L_0x00a5:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x00ab:
        r5 = zzh(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzm(r3, r5);
        goto L_0x03c9;
    L_0x00b5:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x00bb:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r1, r5);
        r5 = (com.google.android.gms.internal.firebase_ml.zzsw) r5;
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r3, r5);
        goto L_0x03c9;
    L_0x00c7:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x00cd:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r1, r5);
        r6 = r0.zzdi(r12);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzc(r3, r5, r6);
        goto L_0x03c9;
    L_0x00db:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x00e1:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r1, r5);
        r6 = r5 instanceof com.google.android.gms.internal.firebase_ml.zzsw;
        if (r6 == 0) goto L_0x00f1;
    L_0x00e9:
        r5 = (com.google.android.gms.internal.firebase_ml.zzsw) r5;
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r3, r5);
        goto L_0x03c9;
    L_0x00f1:
        r5 = (java.lang.String) r5;
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r3, r5);
        goto L_0x03c9;
    L_0x00f9:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x00ff:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r3, r8);
        goto L_0x03c9;
    L_0x0105:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x010b:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzo(r3, r11);
        goto L_0x03c9;
    L_0x0111:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0117:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzg(r3, r9);
        goto L_0x03c9;
    L_0x011d:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0123:
        r5 = zzh(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzl(r3, r5);
        goto L_0x03c9;
    L_0x012d:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0133:
        r5 = zzi(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zze(r3, r5);
        goto L_0x03c9;
    L_0x013d:
        r14 = r0.zza(r1, r3, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0143:
        r5 = zzi(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzd(r3, r5);
        goto L_0x03c9;
    L_0x014d:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0153:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzb(r3, r4);
        goto L_0x03c9;
    L_0x0159:
        r5 = r0.zza(r1, r3, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x015f:
        r5 = 0;
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzb(r3, r5);
        goto L_0x03c9;
    L_0x0167:
        r14 = r0.zzbrn;
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r1, r5);
        r6 = r0.zzdj(r12);
        r3 = r14.zzd(r3, r5, r6);
        goto L_0x03c9;
    L_0x0177:
        r5 = zze(r1, r5);
        r6 = r0.zzdi(r12);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzd(r3, r5, r6);
        goto L_0x03c9;
    L_0x0185:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzk(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0191:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x0199;
    L_0x0195:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x0199:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x01a3:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzo(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x01af:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x01b7;
    L_0x01b3:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x01b7:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x01c1:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzq(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x01cd:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x01d5;
    L_0x01d1:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x01d5:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x01df:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzp(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x01eb:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x01f3;
    L_0x01ef:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x01f3:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x01fd:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzl(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0209:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x0211;
    L_0x020d:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x0211:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x021b:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzn(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0227:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x022f;
    L_0x022b:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x022f:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x0239:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzr(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0245:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x024d;
    L_0x0249:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x024d:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x0257:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzp(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0263:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x026b;
    L_0x0267:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x026b:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x0275:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzq(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0281:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x0289;
    L_0x0285:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x0289:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x0293:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzm(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x029f:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x02a7;
    L_0x02a3:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x02a7:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x02b1:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzj(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x02bd:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x02c5;
    L_0x02c1:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x02c5:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x02ce:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzi(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x02da:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x02e2;
    L_0x02de:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x02e2:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x02eb:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzp(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x02f7:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x02ff;
    L_0x02fb:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x02ff:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
        goto L_0x0324;
    L_0x0308:
        r5 = r2.getObject(r1, r5);
        r5 = (java.util.List) r5;
        r5 = com.google.android.gms.internal.firebase_ml.zzwg.zzq(r5);
        if (r5 <= 0) goto L_0x04e4;
    L_0x0314:
        r6 = r0.zzbrf;
        if (r6 == 0) goto L_0x031c;
    L_0x0318:
        r14 = (long) r14;
        r2.putInt(r1, r14, r5);
    L_0x031c:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r3);
        r6 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r5);
    L_0x0324:
        r3 = r3 + r6;
        r3 = r3 + r5;
        goto L_0x03c9;
    L_0x0328:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzq(r3, r5, r11);
        goto L_0x03c9;
    L_0x0332:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzu(r3, r5, r11);
        goto L_0x03c9;
    L_0x033c:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzw(r3, r5, r11);
        goto L_0x03c9;
    L_0x0346:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzv(r3, r5, r11);
        goto L_0x03c9;
    L_0x0350:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzr(r3, r5, r11);
        goto L_0x03c9;
    L_0x035a:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzt(r3, r5, r11);
        goto L_0x03c9;
    L_0x0363:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzd(r3, r5);
        goto L_0x03c9;
    L_0x036c:
        r5 = zze(r1, r5);
        r6 = r0.zzdi(r12);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzc(r3, r5, r6);
        goto L_0x03c9;
    L_0x0379:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzc(r3, r5);
        goto L_0x03c9;
    L_0x0382:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzx(r3, r5, r11);
        goto L_0x03c9;
    L_0x038b:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzv(r3, r5, r11);
        goto L_0x03c9;
    L_0x0394:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzw(r3, r5, r11);
        goto L_0x03c9;
    L_0x039d:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzs(r3, r5, r11);
        goto L_0x03c9;
    L_0x03a6:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzp(r3, r5, r11);
        goto L_0x03c9;
    L_0x03af:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzo(r3, r5, r11);
        goto L_0x03c9;
    L_0x03b8:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzv(r3, r5, r11);
        goto L_0x03c9;
    L_0x03c1:
        r5 = zze(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzw(r3, r5, r11);
    L_0x03c9:
        r13 = r13 + r3;
        goto L_0x04e4;
    L_0x03cc:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x03d2:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r1, r5);
        r5 = (com.google.android.gms.internal.firebase_ml.zzvo) r5;
        r6 = r0.zzdi(r12);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r3, r5, r6);
        goto L_0x03c9;
    L_0x03e1:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x03e7:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzf(r3, r5);
        goto L_0x03c9;
    L_0x03f0:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x03f6:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzn(r3, r5);
        goto L_0x03c9;
    L_0x03ff:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0405:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzh(r3, r9);
        goto L_0x03c9;
    L_0x040a:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0410:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzp(r3, r11);
        goto L_0x03c9;
    L_0x0415:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x041b:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzq(r3, r5);
        goto L_0x03c9;
    L_0x0424:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x042a:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzm(r3, r5);
        goto L_0x03c9;
    L_0x0433:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x0439:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r1, r5);
        r5 = (com.google.android.gms.internal.firebase_ml.zzsw) r5;
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r3, r5);
        goto L_0x03c9;
    L_0x0444:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x044a:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r1, r5);
        r6 = r0.zzdi(r12);
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zzc(r3, r5, r6);
        goto L_0x03c9;
    L_0x0458:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x045e:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r1, r5);
        r6 = r5 instanceof com.google.android.gms.internal.firebase_ml.zzsw;
        if (r6 == 0) goto L_0x046e;
    L_0x0466:
        r5 = (com.google.android.gms.internal.firebase_ml.zzsw) r5;
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r3, r5);
        goto L_0x03c9;
    L_0x046e:
        r5 = (java.lang.String) r5;
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r3, r5);
        goto L_0x03c9;
    L_0x0476:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x047c:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r3, r8);
        goto L_0x03c9;
    L_0x0482:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0488:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzo(r3, r11);
        goto L_0x03c9;
    L_0x048e:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x0494:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzg(r3, r9);
        goto L_0x03c9;
    L_0x049a:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x04a0:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzl(r3, r5);
        goto L_0x03c9;
    L_0x04aa:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x04b0:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zze(r3, r5);
        goto L_0x03c9;
    L_0x04ba:
        r14 = r0.zza(r1, r12);
        if (r14 == 0) goto L_0x04e4;
    L_0x04c0:
        r5 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r1, r5);
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzd(r3, r5);
        goto L_0x03c9;
    L_0x04ca:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x04d0:
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzb(r3, r4);
        goto L_0x03c9;
    L_0x04d6:
        r5 = r0.zza(r1, r12);
        if (r5 == 0) goto L_0x04e4;
    L_0x04dc:
        r5 = 0;
        r3 = com.google.android.gms.internal.firebase_ml.zztl.zzb(r3, r5);
        goto L_0x03c9;
    L_0x04e4:
        r12 = r12 + 3;
        r3 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        goto L_0x0016;
    L_0x04ea:
        r2 = r0.zzbrl;
        r1 = zza(r2, r1);
        r13 = r13 + r1;
        return r13;
    L_0x04f2:
        r2 = zzbqw;
        r3 = -1;
        r3 = 0;
        r5 = 0;
        r6 = -1;
        r12 = 0;
    L_0x04f9:
        r13 = r0.zzbqx;
        r13 = r13.length;
        if (r3 >= r13) goto L_0x0a3f;
    L_0x04fe:
        r13 = r0.zzdl(r3);
        r14 = r0.zzbqx;
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
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x0542;
    L_0x0529:
        r8 = com.google.android.gms.internal.firebase_ml.zztz.DOUBLE_LIST_PACKED;
        r8 = r8.id();
        if (r4 < r8) goto L_0x0542;
    L_0x0531:
        r8 = com.google.android.gms.internal.firebase_ml.zztz.SINT64_LIST_PACKED;
        r8 = r8.id();
        if (r4 > r8) goto L_0x0542;
    L_0x0539:
        r8 = r0.zzbqx;
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
        r4 = (com.google.android.gms.internal.firebase_ml.zzvo) r4;
        r8 = r0.zzdi(r3);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r15, r4, r8);
        goto L_0x0910;
    L_0x0563:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0569:
        r8 = zzi(r1, r8);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzf(r15, r8);
        goto L_0x0910;
    L_0x0573:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0579:
        r4 = zzh(r1, r8);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzn(r15, r4);
        goto L_0x0910;
    L_0x0583:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0589:
        r8 = 0;
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzh(r15, r8);
        goto L_0x0910;
    L_0x0591:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0597:
        r4 = 0;
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzp(r15, r4);
        goto L_0x095a;
    L_0x059e:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x05a4:
        r4 = zzh(r1, r8);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzq(r15, r4);
        goto L_0x0910;
    L_0x05ae:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x05b4:
        r4 = zzh(r1, r8);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzm(r15, r4);
        goto L_0x0910;
    L_0x05be:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x05c4:
        r4 = r2.getObject(r1, r8);
        r4 = (com.google.android.gms.internal.firebase_ml.zzsw) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r15, r4);
        goto L_0x0910;
    L_0x05d0:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x05d6:
        r4 = r2.getObject(r1, r8);
        r8 = r0.zzdi(r3);
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzc(r15, r4, r8);
        goto L_0x0910;
    L_0x05e4:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x05ea:
        r4 = r2.getObject(r1, r8);
        r8 = r4 instanceof com.google.android.gms.internal.firebase_ml.zzsw;
        if (r8 == 0) goto L_0x05fa;
    L_0x05f2:
        r4 = (com.google.android.gms.internal.firebase_ml.zzsw) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r15, r4);
        goto L_0x0910;
    L_0x05fa:
        r4 = (java.lang.String) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r15, r4);
        goto L_0x0910;
    L_0x0602:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0608:
        r4 = 1;
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r15, r4);
        goto L_0x095a;
    L_0x060f:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0615:
        r4 = 0;
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzo(r15, r4);
        goto L_0x095a;
    L_0x061c:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0622:
        r8 = 0;
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzg(r15, r8);
        goto L_0x0910;
    L_0x062a:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0630:
        r4 = zzh(r1, r8);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzl(r15, r4);
        goto L_0x0910;
    L_0x063a:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0640:
        r8 = zzi(r1, r8);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zze(r15, r8);
        goto L_0x0910;
    L_0x064a:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0650:
        r8 = zzi(r1, r8);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzd(r15, r8);
        goto L_0x0910;
    L_0x065a:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x0660:
        r4 = 0;
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzb(r15, r4);
        goto L_0x095a;
    L_0x0667:
        r4 = r0.zza(r1, r15, r3);
        if (r4 == 0) goto L_0x0911;
    L_0x066d:
        r8 = 0;
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzb(r15, r8);
        goto L_0x0910;
    L_0x0675:
        r4 = r0.zzbrn;
        r8 = r2.getObject(r1, r8);
        r9 = r0.zzdj(r3);
        r4 = r4.zzd(r15, r8, r9);
        goto L_0x0910;
    L_0x0685:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r8 = r0.zzdi(r3);
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzd(r15, r4, r8);
        goto L_0x0910;
    L_0x0695:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzk(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x06a1:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x06a9;
    L_0x06a5:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x06a9:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x06b3:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzo(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x06bf:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x06c7;
    L_0x06c3:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x06c7:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x06d1:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzq(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x06dd:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x06e5;
    L_0x06e1:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x06e5:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x06ef:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzp(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x06fb:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x0703;
    L_0x06ff:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x0703:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x070d:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzl(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0719:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x0721;
    L_0x071d:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x0721:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x072b:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzn(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0737:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x073f;
    L_0x073b:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x073f:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x0749:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzr(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0755:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x075d;
    L_0x0759:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x075d:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x0767:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzp(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0773:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x077b;
    L_0x0777:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x077b:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x0785:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzq(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0791:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x0799;
    L_0x0795:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x0799:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x07a3:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzm(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x07af:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x07b7;
    L_0x07b3:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x07b7:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x07c1:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzj(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x07cd:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x07d5;
    L_0x07d1:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x07d5:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x07de:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzi(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x07ea:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x07f2;
    L_0x07ee:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x07f2:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x07fb:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzp(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0807:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x080f;
    L_0x080b:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x080f:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
        goto L_0x0834;
    L_0x0818:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzq(r4);
        if (r4 <= 0) goto L_0x0911;
    L_0x0824:
        r8 = r0.zzbrf;
        if (r8 == 0) goto L_0x082c;
    L_0x0828:
        r8 = (long) r11;
        r2.putInt(r1, r8, r4);
    L_0x082c:
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzcu(r15);
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzcw(r4);
    L_0x0834:
        r8 = r8 + r9;
        r8 = r8 + r4;
        goto L_0x095a;
    L_0x0838:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r10 = 0;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzq(r15, r4, r10);
        goto L_0x0900;
    L_0x0845:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzu(r15, r4, r10);
        goto L_0x0900;
    L_0x0852:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzw(r15, r4, r10);
        goto L_0x0900;
    L_0x085f:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzv(r15, r4, r10);
        goto L_0x0900;
    L_0x086c:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzr(r15, r4, r10);
        goto L_0x0900;
    L_0x0879:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzt(r15, r4, r10);
        goto L_0x0910;
    L_0x0886:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzd(r15, r4);
        goto L_0x0910;
    L_0x0892:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r8 = r0.zzdi(r3);
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzc(r15, r4, r8);
        goto L_0x0910;
    L_0x08a2:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzc(r15, r4);
        goto L_0x0910;
    L_0x08ad:
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r10 = 0;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzx(r15, r4, r10);
        goto L_0x0900;
    L_0x08b9:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzv(r15, r4, r10);
        goto L_0x0900;
    L_0x08c5:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzw(r15, r4, r10);
        goto L_0x0900;
    L_0x08d1:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzs(r15, r4, r10);
        goto L_0x0900;
    L_0x08dd:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzp(r15, r4, r10);
        goto L_0x0900;
    L_0x08e9:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzo(r15, r4, r10);
        goto L_0x0900;
    L_0x08f5:
        r10 = 0;
        r4 = r2.getObject(r1, r8);
        r4 = (java.util.List) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzv(r15, r4, r10);
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
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzw(r15, r4, r10);
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
        r4 = (com.google.android.gms.internal.firebase_ml.zzvo) r4;
        r8 = r0.zzdi(r3);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r15, r4, r8);
        goto L_0x0910;
    L_0x092c:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x0930:
        r8 = r2.getLong(r1, r8);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzf(r15, r8);
        goto L_0x0910;
    L_0x0939:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x093d:
        r4 = r2.getInt(r1, r8);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzn(r15, r4);
        goto L_0x0910;
    L_0x0946:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x094a:
        r8 = 0;
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzh(r15, r8);
        goto L_0x0910;
    L_0x0951:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x0955:
        r4 = 0;
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzp(r15, r4);
    L_0x095a:
        r5 = r5 + r8;
        goto L_0x0911;
    L_0x095c:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x0960:
        r4 = r2.getInt(r1, r8);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzq(r15, r4);
        goto L_0x0910;
    L_0x0969:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x096d:
        r4 = r2.getInt(r1, r8);
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzm(r15, r4);
        goto L_0x0910;
    L_0x0976:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x097a:
        r4 = r2.getObject(r1, r8);
        r4 = (com.google.android.gms.internal.firebase_ml.zzsw) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r15, r4);
        goto L_0x0910;
    L_0x0985:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x0989:
        r4 = r2.getObject(r1, r8);
        r8 = r0.zzdi(r3);
        r4 = com.google.android.gms.internal.firebase_ml.zzwg.zzc(r15, r4, r8);
        goto L_0x0910;
    L_0x0997:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x099b:
        r4 = r2.getObject(r1, r8);
        r8 = r4 instanceof com.google.android.gms.internal.firebase_ml.zzsw;
        if (r8 == 0) goto L_0x09ab;
    L_0x09a3:
        r4 = (com.google.android.gms.internal.firebase_ml.zzsw) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r15, r4);
        goto L_0x0910;
    L_0x09ab:
        r4 = (java.lang.String) r4;
        r4 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r15, r4);
        goto L_0x0910;
    L_0x09b3:
        r4 = r12 & r18;
        if (r4 == 0) goto L_0x0911;
    L_0x09b7:
        r4 = 1;
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzc(r15, r4);
        r5 = r5 + r8;
        goto L_0x0912;
    L_0x09bf:
        r4 = 1;
        r8 = r12 & r18;
        if (r8 == 0) goto L_0x09cc;
    L_0x09c4:
        r10 = 0;
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzo(r15, r10);
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
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzg(r15, r13);
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
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzl(r15, r8);
        goto L_0x0a11;
    L_0x09f0:
        r4 = 1;
        r10 = 0;
        r13 = 0;
        r11 = r12 & r18;
        if (r11 == 0) goto L_0x0a21;
    L_0x09f8:
        r8 = r2.getLong(r1, r8);
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zze(r15, r8);
        goto L_0x0a11;
    L_0x0a01:
        r4 = 1;
        r10 = 0;
        r13 = 0;
        r11 = r12 & r18;
        if (r11 == 0) goto L_0x0a21;
    L_0x0a09:
        r8 = r2.getLong(r1, r8);
        r8 = com.google.android.gms.internal.firebase_ml.zztl.zzd(r15, r8);
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
        r9 = com.google.android.gms.internal.firebase_ml.zztl.zzb(r15, r8);
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
        r11 = com.google.android.gms.internal.firebase_ml.zztl.zzb(r15, r7);
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
        r2 = r0.zzbrl;
        r2 = zza(r2, r1);
        r5 = r5 + r2;
        r2 = r0.zzbrc;
        if (r2 == 0) goto L_0x0a99;
    L_0x0a4b:
        r2 = r0.zzbrm;
        r1 = r2.zzo(r1);
        r2 = 0;
    L_0x0a52:
        r3 = r1.zzblk;
        r3 = r3.zzsx();
        if (r10 >= r3) goto L_0x0a72;
    L_0x0a5a:
        r3 = r1.zzblk;
        r3 = r3.zzdp(r10);
        r4 = r3.getKey();
        r4 = (com.google.android.gms.internal.firebase_ml.zzty) r4;
        r3 = r3.getValue();
        r3 = com.google.android.gms.internal.firebase_ml.zztw.zzb(r4, r3);
        r2 = r2 + r3;
        r10 = r10 + 1;
        goto L_0x0a52;
    L_0x0a72:
        r1 = r1.zzblk;
        r1 = r1.zzsy();
        r1 = r1.iterator();
    L_0x0a7c:
        r3 = r1.hasNext();
        if (r3 == 0) goto L_0x0a98;
    L_0x0a82:
        r3 = r1.next();
        r3 = (java.util.Map.Entry) r3;
        r4 = r3.getKey();
        r4 = (com.google.android.gms.internal.firebase_ml.zzty) r4;
        r3 = r3.getValue();
        r3 = com.google.android.gms.internal.firebase_ml.zztw.zzb(r4, r3);
        r2 = r2 + r3;
        goto L_0x0a7c;
    L_0x0a98:
        r5 = r5 + r2;
    L_0x0a99:
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzvs.zzaa(java.lang.Object):int");
    }

    private static <UT, UB> int zza(zzww<UT, UB> zzww, T t) {
        return zzww.zzaa(zzww.zzae(t));
    }

    private static <E> List<E> zze(Object obj, long j) {
        return (List) zzxc.zzp(obj, j);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x0513  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x0553  */
    /* JADX WARNING: Removed duplicated region for block: B:331:0x0a2b  */
    public final void zza(T r14, com.google.android.gms.internal.firebase_ml.zzxr r15) throws java.io.IOException {
        /*
        r13 = this;
        r0 = r15.zzqh();
        r1 = com.google.android.gms.internal.firebase_ml.zzue.zzf.zzbpa;
        r2 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r3 = 0;
        r4 = 1;
        r5 = 0;
        r6 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        if (r0 != r1) goto L_0x0529;
    L_0x0010:
        r0 = r13.zzbrl;
        zza(r0, r14, r15);
        r0 = r13.zzbrc;
        if (r0 == 0) goto L_0x0032;
    L_0x0019:
        r0 = r13.zzbrm;
        r0 = r0.zzo(r14);
        r1 = r0.zzblk;
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
        r7 = r13.zzbqx;
        r7 = r7.length;
        r7 = r7 + -3;
    L_0x0039:
        if (r7 < 0) goto L_0x0511;
    L_0x003b:
        r8 = r13.zzdl(r7);
        r9 = r13.zzbqx;
        r9 = r9[r7];
    L_0x0043:
        if (r1 == 0) goto L_0x0061;
    L_0x0045:
        r10 = r13.zzbrm;
        r10 = r10.zza(r1);
        if (r10 <= r9) goto L_0x0061;
    L_0x004d:
        r10 = r13.zzbrm;
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
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r10 = r13.zzdi(r7);
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
        r15.zzj(r9, r8);
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
        r15.zzr(r9, r8);
        goto L_0x050d;
    L_0x00c3:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x00c9:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzh(r14, r10);
        r15.zzs(r9, r8);
        goto L_0x050d;
    L_0x00d4:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x00da:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzh(r14, r10);
        r15.zzi(r9, r8);
        goto L_0x050d;
    L_0x00e5:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x00eb:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (com.google.android.gms.internal.firebase_ml.zzsw) r8;
        r15.zza(r9, r8);
        goto L_0x050d;
    L_0x00f8:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x00fe:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r10 = r13.zzdi(r7);
        r15.zza(r9, r8, r10);
        goto L_0x050d;
    L_0x010d:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0113:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        zza(r9, r8, r15);
        goto L_0x050d;
    L_0x011e:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0124:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzj(r14, r10);
        r15.zzb(r9, r8);
        goto L_0x050d;
    L_0x012f:
        r10 = r13.zza(r14, r9, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0135:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = zzh(r14, r10);
        r15.zzk(r9, r8);
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
        r15.zzh(r9, r8);
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
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r13.zza(r15, r9, r8, r7);
        goto L_0x050d;
    L_0x01b1:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        r10 = r13.zzdi(r7);
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r9, r8, r15, r10);
        goto L_0x050d;
    L_0x01c6:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zze(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x01d7:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzj(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x01e8:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzg(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x01f9:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzl(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x020a:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzm(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x021b:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzi(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x022c:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzn(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x023d:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzk(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x024e:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzf(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x025f:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzh(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x0270:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzd(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x0281:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzc(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x0292:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x02a3:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r9, r8, r15, r4);
        goto L_0x050d;
    L_0x02b4:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zze(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x02c5:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzj(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x02d6:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzg(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x02e7:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzl(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x02f8:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzm(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x0309:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzi(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x031a:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r9, r8, r15);
        goto L_0x050d;
    L_0x032b:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        r10 = r13.zzdi(r7);
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r9, r8, r15, r10);
        goto L_0x050d;
    L_0x0340:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r9, r8, r15);
        goto L_0x050d;
    L_0x0351:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzn(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x0362:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzk(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x0373:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzf(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x0384:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzh(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x0395:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzd(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x03a6:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzc(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x03b7:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x03c8:
        r9 = r13.zzbqx;
        r9 = r9[r7];
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (java.util.List) r8;
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r9, r8, r15, r5);
        goto L_0x050d;
    L_0x03d9:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x03df:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r10 = r13.zzdi(r7);
        r15.zzb(r9, r8, r10);
        goto L_0x050d;
    L_0x03ee:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x03f4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r14, r10);
        r15.zzb(r9, r10);
        goto L_0x050d;
    L_0x03ff:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0405:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r10);
        r15.zzj(r9, r8);
        goto L_0x050d;
    L_0x0410:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0416:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r14, r10);
        r15.zzj(r9, r10);
        goto L_0x050d;
    L_0x0421:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0427:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r10);
        r15.zzr(r9, r8);
        goto L_0x050d;
    L_0x0432:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0438:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r10);
        r15.zzs(r9, r8);
        goto L_0x050d;
    L_0x0443:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0449:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r10);
        r15.zzi(r9, r8);
        goto L_0x050d;
    L_0x0454:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x045a:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r8 = (com.google.android.gms.internal.firebase_ml.zzsw) r8;
        r15.zza(r9, r8);
        goto L_0x050d;
    L_0x0467:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x046d:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        r10 = r13.zzdi(r7);
        r15.zza(r9, r8, r10);
        goto L_0x050d;
    L_0x047c:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0482:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r10);
        zza(r9, r8, r15);
        goto L_0x050d;
    L_0x048d:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0493:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzm(r14, r10);
        r15.zzb(r9, r8);
        goto L_0x050d;
    L_0x049e:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04a4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r10);
        r15.zzk(r9, r8);
        goto L_0x050d;
    L_0x04ae:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04b4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r14, r10);
        r15.zzc(r9, r10);
        goto L_0x050d;
    L_0x04be:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04c4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r10);
        r15.zzh(r9, r8);
        goto L_0x050d;
    L_0x04ce:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04d4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r14, r10);
        r15.zza(r9, r10);
        goto L_0x050d;
    L_0x04de:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04e4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r14, r10);
        r15.zzi(r9, r10);
        goto L_0x050d;
    L_0x04ee:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x04f4:
        r8 = r8 & r6;
        r10 = (long) r8;
        r8 = com.google.android.gms.internal.firebase_ml.zzxc.zzn(r14, r10);
        r15.zza(r9, r8);
        goto L_0x050d;
    L_0x04fe:
        r10 = r13.zza(r14, r7);
        if (r10 == 0) goto L_0x050d;
    L_0x0504:
        r8 = r8 & r6;
        r10 = (long) r8;
        r10 = com.google.android.gms.internal.firebase_ml.zzxc.zzo(r14, r10);
        r15.zza(r9, r10);
    L_0x050d:
        r7 = r7 + -3;
        goto L_0x0039;
    L_0x0511:
        if (r1 == 0) goto L_0x0528;
    L_0x0513:
        r14 = r13.zzbrm;
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
        r0 = r13.zzbre;
        if (r0 == 0) goto L_0x0a46;
    L_0x052d:
        r0 = r13.zzbrc;
        if (r0 == 0) goto L_0x054a;
    L_0x0531:
        r0 = r13.zzbrm;
        r0 = r0.zzo(r14);
        r1 = r0.zzblk;
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
        r7 = r13.zzbqx;
        r7 = r7.length;
        r8 = r1;
        r1 = 0;
    L_0x0551:
        if (r1 >= r7) goto L_0x0a29;
    L_0x0553:
        r9 = r13.zzdl(r1);
        r10 = r13.zzbqx;
        r10 = r10[r1];
    L_0x055b:
        if (r8 == 0) goto L_0x0579;
    L_0x055d:
        r11 = r13.zzbrm;
        r11 = r11.zza(r8);
        if (r11 > r10) goto L_0x0579;
    L_0x0565:
        r11 = r13.zzbrm;
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
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r11 = r13.zzdi(r1);
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
        r15.zzj(r10, r9);
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
        r15.zzr(r10, r9);
        goto L_0x0a25;
    L_0x05db:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x05e1:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzh(r14, r11);
        r15.zzs(r10, r9);
        goto L_0x0a25;
    L_0x05ec:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x05f2:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzh(r14, r11);
        r15.zzi(r10, r9);
        goto L_0x0a25;
    L_0x05fd:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0603:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (com.google.android.gms.internal.firebase_ml.zzsw) r9;
        r15.zza(r10, r9);
        goto L_0x0a25;
    L_0x0610:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0616:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r11 = r13.zzdi(r1);
        r15.zza(r10, r9, r11);
        goto L_0x0a25;
    L_0x0625:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x062b:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        zza(r10, r9, r15);
        goto L_0x0a25;
    L_0x0636:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x063c:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzj(r14, r11);
        r15.zzb(r10, r9);
        goto L_0x0a25;
    L_0x0647:
        r11 = r13.zza(r14, r10, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x064d:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = zzh(r14, r11);
        r15.zzk(r10, r9);
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
        r15.zzh(r10, r9);
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
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r13.zza(r15, r10, r9, r1);
        goto L_0x0a25;
    L_0x06c9:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        r11 = r13.zzdi(r1);
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r10, r9, r15, r11);
        goto L_0x0a25;
    L_0x06de:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zze(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x06ef:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzj(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0700:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzg(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0711:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzl(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0722:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzm(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0733:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzi(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0744:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzn(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0755:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzk(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0766:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzf(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0777:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzh(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0788:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzd(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x0799:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzc(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x07aa:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x07bb:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r10, r9, r15, r4);
        goto L_0x0a25;
    L_0x07cc:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zze(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x07dd:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzj(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x07ee:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzg(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x07ff:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzl(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x0810:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzm(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x0821:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzi(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x0832:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r10, r9, r15);
        goto L_0x0a25;
    L_0x0843:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        r11 = r13.zzdi(r1);
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r10, r9, r15, r11);
        goto L_0x0a25;
    L_0x0858:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r10, r9, r15);
        goto L_0x0a25;
    L_0x0869:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzn(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x087a:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzk(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x088b:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzf(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x089c:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzh(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x08ad:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzd(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x08be:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzc(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x08cf:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x08e0:
        r10 = r13.zzbqx;
        r10 = r10[r1];
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r10, r9, r15, r5);
        goto L_0x0a25;
    L_0x08f1:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x08f7:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r11 = r13.zzdi(r1);
        r15.zzb(r10, r9, r11);
        goto L_0x0a25;
    L_0x0906:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x090c:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r14, r11);
        r15.zzb(r10, r11);
        goto L_0x0a25;
    L_0x0917:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x091d:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r11);
        r15.zzj(r10, r9);
        goto L_0x0a25;
    L_0x0928:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x092e:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r14, r11);
        r15.zzj(r10, r11);
        goto L_0x0a25;
    L_0x0939:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x093f:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r11);
        r15.zzr(r10, r9);
        goto L_0x0a25;
    L_0x094a:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0950:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r11);
        r15.zzs(r10, r9);
        goto L_0x0a25;
    L_0x095b:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0961:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r11);
        r15.zzi(r10, r9);
        goto L_0x0a25;
    L_0x096c:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0972:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r9 = (com.google.android.gms.internal.firebase_ml.zzsw) r9;
        r15.zza(r10, r9);
        goto L_0x0a25;
    L_0x097f:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0985:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        r11 = r13.zzdi(r1);
        r15.zza(r10, r9, r11);
        goto L_0x0a25;
    L_0x0994:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x099a:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r14, r11);
        zza(r10, r9, r15);
        goto L_0x0a25;
    L_0x09a5:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09ab:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzm(r14, r11);
        r15.zzb(r10, r9);
        goto L_0x0a25;
    L_0x09b6:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09bc:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r11);
        r15.zzk(r10, r9);
        goto L_0x0a25;
    L_0x09c6:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09cc:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r14, r11);
        r15.zzc(r10, r11);
        goto L_0x0a25;
    L_0x09d6:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09dc:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzk(r14, r11);
        r15.zzh(r10, r9);
        goto L_0x0a25;
    L_0x09e6:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09ec:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r14, r11);
        r15.zza(r10, r11);
        goto L_0x0a25;
    L_0x09f6:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x09fc:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_ml.zzxc.zzl(r14, r11);
        r15.zzi(r10, r11);
        goto L_0x0a25;
    L_0x0a06:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0a0c:
        r9 = r9 & r6;
        r11 = (long) r9;
        r9 = com.google.android.gms.internal.firebase_ml.zzxc.zzn(r14, r11);
        r15.zza(r10, r9);
        goto L_0x0a25;
    L_0x0a16:
        r11 = r13.zza(r14, r1);
        if (r11 == 0) goto L_0x0a25;
    L_0x0a1c:
        r9 = r9 & r6;
        r11 = (long) r9;
        r11 = com.google.android.gms.internal.firebase_ml.zzxc.zzo(r14, r11);
        r15.zza(r10, r11);
    L_0x0a25:
        r1 = r1 + 3;
        goto L_0x0551;
    L_0x0a29:
        if (r8 == 0) goto L_0x0a40;
    L_0x0a2b:
        r1 = r13.zzbrm;
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
        r0 = r13.zzbrl;
        zza(r0, r14, r15);
        return;
    L_0x0a46:
        r13.zzb(r14, r15);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzvs.zza(java.lang.Object, com.google.android.gms.internal.firebase_ml.zzxr):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x04b5  */
    private final void zzb(T r19, com.google.android.gms.internal.firebase_ml.zzxr r20) throws java.io.IOException {
        /*
        r18 = this;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r3 = r0.zzbrc;
        if (r3 == 0) goto L_0x0023;
    L_0x000a:
        r3 = r0.zzbrm;
        r3 = r3.zzo(r1);
        r5 = r3.zzblk;
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
        r7 = r0.zzbqx;
        r7 = r7.length;
        r8 = zzbqw;
        r10 = r5;
        r5 = 0;
        r11 = 0;
    L_0x002e:
        if (r5 >= r7) goto L_0x04af;
    L_0x0030:
        r12 = r0.zzdl(r5);
        r13 = r0.zzbqx;
        r14 = r13[r5];
        r15 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r15 = r15 & r12;
        r15 = r15 >>> 20;
        r4 = r0.zzbre;
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
        r4 = r0.zzbrm;
        r4 = r4.zza(r10);
        if (r4 > r14) goto L_0x0088;
    L_0x0073:
        r4 = r0.zzbrm;
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
        r9 = r0.zzdi(r5);
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
        r2.zzj(r14, r4);
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
        r2.zzr(r14, r4);
        goto L_0x008e;
    L_0x00db:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x00e1:
        r4 = zzh(r1, r12);
        r2.zzs(r14, r4);
        goto L_0x008e;
    L_0x00e9:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x00ef:
        r4 = zzh(r1, r12);
        r2.zzi(r14, r4);
        goto L_0x008e;
    L_0x00f7:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x00fd:
        r4 = r8.getObject(r1, r12);
        r4 = (com.google.android.gms.internal.firebase_ml.zzsw) r4;
        r2.zza(r14, r4);
        goto L_0x008e;
    L_0x0107:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x010d:
        r4 = r8.getObject(r1, r12);
        r9 = r0.zzdi(r5);
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
        r2.zzb(r14, r4);
        goto L_0x008e;
    L_0x0138:
        r4 = r0.zza(r1, r14, r5);
        if (r4 == 0) goto L_0x008e;
    L_0x013e:
        r4 = zzh(r1, r12);
        r2.zzk(r14, r4);
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
        r2.zzh(r14, r4);
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
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        r12 = r0.zzdi(r5);
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r4, r9, r2, r12);
        goto L_0x008e;
    L_0x01bd:
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        r14 = 1;
        com.google.android.gms.internal.firebase_ml.zzwg.zze(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x01cd:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzj(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x01dd:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzg(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x01ed:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzl(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x01fd:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzm(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x020d:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzi(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x021d:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzn(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x022d:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzk(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x023d:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzf(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x024d:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzh(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x025d:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzd(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x026d:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzc(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x027d:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x028d:
        r14 = 1;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x029d:
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        r14 = 0;
        com.google.android.gms.internal.firebase_ml.zzwg.zze(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02ad:
        r14 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzj(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02bd:
        r14 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzg(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02cd:
        r14 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzl(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02dd:
        r14 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzm(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02ed:
        r14 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzi(r4, r9, r2, r14);
        goto L_0x008e;
    L_0x02fd:
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r4, r9, r2);
        goto L_0x008e;
    L_0x030c:
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        r12 = r0.zzdi(r5);
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r4, r9, r2, r12);
        goto L_0x008e;
    L_0x031f:
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r4, r9, r2);
        goto L_0x008e;
    L_0x032e:
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        r15 = 0;
        com.google.android.gms.internal.firebase_ml.zzwg.zzn(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x033e:
        r15 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzk(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x034e:
        r15 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzf(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x035e:
        r15 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzh(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x036e:
        r15 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzd(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x037e:
        r15 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzc(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x038e:
        r15 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zzb(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x039e:
        r15 = 0;
        r4 = r0.zzbqx;
        r4 = r4[r5];
        r9 = r8.getObject(r1, r12);
        r9 = (java.util.List) r9;
        com.google.android.gms.internal.firebase_ml.zzwg.zza(r4, r9, r2, r15);
        goto L_0x04ab;
    L_0x03ae:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x03b3:
        r4 = r8.getObject(r1, r12);
        r9 = r0.zzdi(r5);
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
        r2.zzj(r14, r4);
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
        r2.zzr(r14, r4);
        goto L_0x04ab;
    L_0x03f8:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x03fd:
        r4 = r8.getInt(r1, r12);
        r2.zzs(r14, r4);
        goto L_0x04ab;
    L_0x0406:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x040b:
        r4 = r8.getInt(r1, r12);
        r2.zzi(r14, r4);
        goto L_0x04ab;
    L_0x0414:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x0419:
        r4 = r8.getObject(r1, r12);
        r4 = (com.google.android.gms.internal.firebase_ml.zzsw) r4;
        r2.zza(r14, r4);
        goto L_0x04ab;
    L_0x0424:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x0429:
        r4 = r8.getObject(r1, r12);
        r9 = r0.zzdi(r5);
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
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzm(r1, r12);
        r2.zzb(r14, r4);
        goto L_0x04ab;
    L_0x0451:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x0456:
        r4 = r8.getInt(r1, r12);
        r2.zzk(r14, r4);
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
        r2.zzh(r14, r4);
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
        r4 = com.google.android.gms.internal.firebase_ml.zzxc.zzn(r1, r12);
        r2.zza(r14, r4);
        goto L_0x04ab;
    L_0x049f:
        r15 = 0;
        r4 = r11 & r9;
        if (r4 == 0) goto L_0x04ab;
    L_0x04a4:
        r12 = com.google.android.gms.internal.firebase_ml.zzxc.zzo(r1, r12);
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
        r5 = r0.zzbrm;
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
        r3 = r0.zzbrl;
        zza(r3, r1, r2);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzvs.zzb(java.lang.Object, com.google.android.gms.internal.firebase_ml.zzxr):void");
    }

    private final <K, V> void zza(zzxr zzxr, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzxr.zza(i, this.zzbrn.zzz(zzdj(i2)), this.zzbrn.zzv(obj));
        }
    }

    private static <UT, UB> void zza(zzww<UT, UB> zzww, T t, zzxr zzxr) throws IOException {
        zzww.zza(zzww.zzae(t), zzxr);
    }

    private static zzwx zzab(Object obj) {
        zzue zzue = (zzue) obj;
        zzwx zzwx = zzue.zzboh;
        if (zzwx != zzwx.zztg()) {
            return zzwx;
        }
        zzwx = zzwx.zzth();
        zzue.zzboh = zzwx;
        return zzwx;
    }

    /* JADX WARNING: Missing block: B:29:?, code:
            return r2 + 4;
     */
    /* JADX WARNING: Missing block: B:30:?, code:
            return r2 + 8;
     */
    private static int zza(byte[] r1, int r2, int r3, com.google.android.gms.internal.firebase_ml.zzxl r4, java.lang.Class<?> r5, com.google.android.gms.internal.firebase_ml.zzst r6) throws java.io.IOException {
        /*
        r0 = com.google.android.gms.internal.firebase_ml.zzvt.zzblp;
        r4 = r4.ordinal();
        r4 = r0[r4];
        switch(r4) {
            case 1: goto L_0x0099;
            case 2: goto L_0x0094;
            case 3: goto L_0x0087;
            case 4: goto L_0x007a;
            case 5: goto L_0x007a;
            case 6: goto L_0x006f;
            case 7: goto L_0x006f;
            case 8: goto L_0x0064;
            case 9: goto L_0x0057;
            case 10: goto L_0x0057;
            case 11: goto L_0x0057;
            case 12: goto L_0x004a;
            case 13: goto L_0x004a;
            case 14: goto L_0x003d;
            case 15: goto L_0x002b;
            case 16: goto L_0x0019;
            case 17: goto L_0x0013;
            default: goto L_0x000b;
        };
    L_0x000b:
        r1 = new java.lang.RuntimeException;
        r2 = "unsupported field type.";
        r1.<init>(r2);
        throw r1;
    L_0x0013:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzd(r1, r2, r6);
        goto L_0x00ae;
    L_0x0019:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r1, r2, r6);
        r2 = r6.zzbkh;
        r2 = com.google.android.gms.internal.firebase_ml.zzti.zzp(r2);
        r2 = java.lang.Long.valueOf(r2);
        r6.zzbki = r2;
        goto L_0x00ae;
    L_0x002b:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r1, r2, r6);
        r2 = r6.zzbkg;
        r2 = com.google.android.gms.internal.firebase_ml.zzti.zzcp(r2);
        r2 = java.lang.Integer.valueOf(r2);
        r6.zzbki = r2;
        goto L_0x00ae;
    L_0x003d:
        r4 = com.google.android.gms.internal.firebase_ml.zzwb.zzso();
        r4 = r4.zzk(r5);
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r4, r1, r2, r3, r6);
        goto L_0x00ae;
    L_0x004a:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r1, r2, r6);
        r2 = r6.zzbkh;
        r2 = java.lang.Long.valueOf(r2);
        r6.zzbki = r2;
        goto L_0x00ae;
    L_0x0057:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r1, r2, r6);
        r2 = r6.zzbkg;
        r2 = java.lang.Integer.valueOf(r2);
        r6.zzbki = r2;
        goto L_0x00ae;
    L_0x0064:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzd(r1, r2);
        r1 = java.lang.Float.valueOf(r1);
        r6.zzbki = r1;
        goto L_0x0084;
    L_0x006f:
        r3 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r1, r2);
        r1 = java.lang.Long.valueOf(r3);
        r6.zzbki = r1;
        goto L_0x0091;
    L_0x007a:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r1, r2);
        r1 = java.lang.Integer.valueOf(r1);
        r6.zzbki = r1;
    L_0x0084:
        r1 = r2 + 4;
        goto L_0x00ae;
    L_0x0087:
        r3 = com.google.android.gms.internal.firebase_ml.zzss.zzc(r1, r2);
        r1 = java.lang.Double.valueOf(r3);
        r6.zzbki = r1;
    L_0x0091:
        r1 = r2 + 8;
        goto L_0x00ae;
    L_0x0094:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zze(r1, r2, r6);
        goto L_0x00ae;
    L_0x0099:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r1, r2, r6);
        r2 = r6.zzbkh;
        r4 = 0;
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 == 0) goto L_0x00a7;
    L_0x00a5:
        r2 = 1;
        goto L_0x00a8;
    L_0x00a7:
        r2 = 0;
    L_0x00a8:
        r2 = java.lang.Boolean.valueOf(r2);
        r6.zzbki = r2;
    L_0x00ae:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzvs.zza(byte[], int, int, com.google.android.gms.internal.firebase_ml.zzxl, java.lang.Class, com.google.android.gms.internal.firebase_ml.zzst):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:92:0x01eb  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0239  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0171  */
    /* JADX WARNING: Missing block: B:272:?, code:
            return r4;
     */
    /* JADX WARNING: Missing block: B:273:?, code:
            return r2;
     */
    private final int zza(T r17, byte[] r18, int r19, int r20, int r21, int r22, int r23, int r24, long r25, int r27, long r28, com.google.android.gms.internal.firebase_ml.zzst r30) throws java.io.IOException {
        /*
        r16 = this;
        r0 = r16;
        r1 = r17;
        r3 = r18;
        r4 = r19;
        r5 = r20;
        r2 = r21;
        r6 = r23;
        r8 = r24;
        r9 = r28;
        r7 = r30;
        r11 = zzbqw;
        r11 = r11.getObject(r1, r9);
        r11 = (com.google.android.gms.internal.firebase_ml.zzun) r11;
        r12 = r11.zzps();
        r13 = 1;
        if (r12 != 0) goto L_0x0036;
    L_0x0023:
        r12 = r11.size();
        if (r12 != 0) goto L_0x002c;
    L_0x0029:
        r12 = 10;
        goto L_0x002d;
    L_0x002c:
        r12 = r12 << r13;
    L_0x002d:
        r11 = r11.zzck(r12);
        r12 = zzbqw;
        r12.putObject(r1, r9, r11);
    L_0x0036:
        r9 = 5;
        r14 = 0;
        r10 = 2;
        switch(r27) {
            case 18: goto L_0x03e4;
            case 19: goto L_0x03a6;
            case 20: goto L_0x0365;
            case 21: goto L_0x0365;
            case 22: goto L_0x034b;
            case 23: goto L_0x030c;
            case 24: goto L_0x02cd;
            case 25: goto L_0x0276;
            case 26: goto L_0x01c3;
            case 27: goto L_0x01a9;
            case 28: goto L_0x0151;
            case 29: goto L_0x034b;
            case 30: goto L_0x0119;
            case 31: goto L_0x02cd;
            case 32: goto L_0x030c;
            case 33: goto L_0x00cc;
            case 34: goto L_0x007f;
            case 35: goto L_0x03e4;
            case 36: goto L_0x03a6;
            case 37: goto L_0x0365;
            case 38: goto L_0x0365;
            case 39: goto L_0x034b;
            case 40: goto L_0x030c;
            case 41: goto L_0x02cd;
            case 42: goto L_0x0276;
            case 43: goto L_0x034b;
            case 44: goto L_0x0119;
            case 45: goto L_0x02cd;
            case 46: goto L_0x030c;
            case 47: goto L_0x00cc;
            case 48: goto L_0x007f;
            case 49: goto L_0x003f;
            default: goto L_0x003d;
        };
    L_0x003d:
        goto L_0x0422;
    L_0x003f:
        r1 = 3;
        if (r6 != r1) goto L_0x0422;
    L_0x0042:
        r1 = r0.zzdi(r8);
        r6 = r2 & -8;
        r6 = r6 | 4;
        r22 = r1;
        r23 = r18;
        r24 = r19;
        r25 = r20;
        r26 = r6;
        r27 = r30;
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r22, r23, r24, r25, r26, r27);
        r8 = r7.zzbki;
        r11.add(r8);
    L_0x005f:
        if (r4 >= r5) goto L_0x0422;
    L_0x0061:
        r8 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r9 = r7.zzbkg;
        if (r2 != r9) goto L_0x0422;
    L_0x0069:
        r22 = r1;
        r23 = r18;
        r24 = r8;
        r25 = r20;
        r26 = r6;
        r27 = r30;
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r22, r23, r24, r25, r26, r27);
        r8 = r7.zzbki;
        r11.add(r8);
        goto L_0x005f;
    L_0x007f:
        if (r6 != r10) goto L_0x00a3;
    L_0x0081:
        r11 = (com.google.android.gms.internal.firebase_ml.zzvc) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r2 = r7.zzbkg;
        r2 = r2 + r1;
    L_0x008a:
        if (r1 >= r2) goto L_0x009a;
    L_0x008c:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r1, r7);
        r4 = r7.zzbkh;
        r4 = com.google.android.gms.internal.firebase_ml.zzti.zzp(r4);
        r11.zzaa(r4);
        goto L_0x008a;
    L_0x009a:
        if (r1 != r2) goto L_0x009e;
    L_0x009c:
        goto L_0x0423;
    L_0x009e:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrm();
        throw r1;
    L_0x00a3:
        if (r6 != 0) goto L_0x0422;
    L_0x00a5:
        r11 = (com.google.android.gms.internal.firebase_ml.zzvc) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r4, r7);
        r8 = r7.zzbkh;
        r8 = com.google.android.gms.internal.firebase_ml.zzti.zzp(r8);
        r11.zzaa(r8);
    L_0x00b4:
        if (r1 >= r5) goto L_0x0423;
    L_0x00b6:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r1, r7);
        r6 = r7.zzbkg;
        if (r2 != r6) goto L_0x0423;
    L_0x00be:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r4, r7);
        r8 = r7.zzbkh;
        r8 = com.google.android.gms.internal.firebase_ml.zzti.zzp(r8);
        r11.zzaa(r8);
        goto L_0x00b4;
    L_0x00cc:
        if (r6 != r10) goto L_0x00f0;
    L_0x00ce:
        r11 = (com.google.android.gms.internal.firebase_ml.zzuf) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r2 = r7.zzbkg;
        r2 = r2 + r1;
    L_0x00d7:
        if (r1 >= r2) goto L_0x00e7;
    L_0x00d9:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r1, r7);
        r4 = r7.zzbkg;
        r4 = com.google.android.gms.internal.firebase_ml.zzti.zzcp(r4);
        r11.zzdh(r4);
        goto L_0x00d7;
    L_0x00e7:
        if (r1 != r2) goto L_0x00eb;
    L_0x00e9:
        goto L_0x0423;
    L_0x00eb:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrm();
        throw r1;
    L_0x00f0:
        if (r6 != 0) goto L_0x0422;
    L_0x00f2:
        r11 = (com.google.android.gms.internal.firebase_ml.zzuf) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r4 = r7.zzbkg;
        r4 = com.google.android.gms.internal.firebase_ml.zzti.zzcp(r4);
        r11.zzdh(r4);
    L_0x0101:
        if (r1 >= r5) goto L_0x0423;
    L_0x0103:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r1, r7);
        r6 = r7.zzbkg;
        if (r2 != r6) goto L_0x0423;
    L_0x010b:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r4 = r7.zzbkg;
        r4 = com.google.android.gms.internal.firebase_ml.zzti.zzcp(r4);
        r11.zzdh(r4);
        goto L_0x0101;
    L_0x0119:
        if (r6 != r10) goto L_0x0120;
    L_0x011b:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r11, r7);
        goto L_0x0131;
    L_0x0120:
        if (r6 != 0) goto L_0x0422;
    L_0x0122:
        r2 = r21;
        r3 = r18;
        r4 = r19;
        r5 = r20;
        r6 = r11;
        r7 = r30;
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zza(r2, r3, r4, r5, r6, r7);
    L_0x0131:
        r1 = (com.google.android.gms.internal.firebase_ml.zzue) r1;
        r3 = r1.zzboh;
        r4 = com.google.android.gms.internal.firebase_ml.zzwx.zztg();
        if (r3 != r4) goto L_0x013c;
    L_0x013b:
        r3 = 0;
    L_0x013c:
        r4 = r0.zzdk(r8);
        r5 = r0.zzbrl;
        r6 = r22;
        r3 = com.google.android.gms.internal.firebase_ml.zzwg.zza(r6, r11, r4, r3, r5);
        r3 = (com.google.android.gms.internal.firebase_ml.zzwx) r3;
        if (r3 == 0) goto L_0x014e;
    L_0x014c:
        r1.zzboh = r3;
    L_0x014e:
        r1 = r2;
        goto L_0x0423;
    L_0x0151:
        if (r6 != r10) goto L_0x0422;
    L_0x0153:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r4 = r7.zzbkg;
        if (r4 < 0) goto L_0x01a4;
    L_0x015b:
        r6 = r3.length;
        r6 = r6 - r1;
        if (r4 > r6) goto L_0x019f;
    L_0x015f:
        if (r4 != 0) goto L_0x0167;
    L_0x0161:
        r4 = com.google.android.gms.internal.firebase_ml.zzsw.zzbkl;
        r11.add(r4);
        goto L_0x016f;
    L_0x0167:
        r6 = com.google.android.gms.internal.firebase_ml.zzsw.zzc(r3, r1, r4);
        r11.add(r6);
    L_0x016e:
        r1 = r1 + r4;
    L_0x016f:
        if (r1 >= r5) goto L_0x0423;
    L_0x0171:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r1, r7);
        r6 = r7.zzbkg;
        if (r2 != r6) goto L_0x0423;
    L_0x0179:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r4 = r7.zzbkg;
        if (r4 < 0) goto L_0x019a;
    L_0x0181:
        r6 = r3.length;
        r6 = r6 - r1;
        if (r4 > r6) goto L_0x0195;
    L_0x0185:
        if (r4 != 0) goto L_0x018d;
    L_0x0187:
        r4 = com.google.android.gms.internal.firebase_ml.zzsw.zzbkl;
        r11.add(r4);
        goto L_0x016f;
    L_0x018d:
        r6 = com.google.android.gms.internal.firebase_ml.zzsw.zzc(r3, r1, r4);
        r11.add(r6);
        goto L_0x016e;
    L_0x0195:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrm();
        throw r1;
    L_0x019a:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrn();
        throw r1;
    L_0x019f:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrm();
        throw r1;
    L_0x01a4:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrn();
        throw r1;
    L_0x01a9:
        if (r6 != r10) goto L_0x0422;
    L_0x01ab:
        r1 = r0.zzdi(r8);
        r22 = r1;
        r23 = r21;
        r24 = r18;
        r25 = r19;
        r26 = r20;
        r27 = r11;
        r28 = r30;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r22, r23, r24, r25, r26, r27, r28);
        goto L_0x0423;
    L_0x01c3:
        if (r6 != r10) goto L_0x0422;
    L_0x01c5:
        r8 = 536870912; // 0x20000000 float:1.0842022E-19 double:2.652494739E-315;
        r8 = r25 & r8;
        r1 = "";
        r6 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1));
        if (r6 != 0) goto L_0x0216;
    L_0x01d0:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r6 = r7.zzbkg;
        if (r6 < 0) goto L_0x0211;
    L_0x01d8:
        if (r6 != 0) goto L_0x01de;
    L_0x01da:
        r11.add(r1);
        goto L_0x01e9;
    L_0x01de:
        r8 = new java.lang.String;
        r9 = com.google.android.gms.internal.firebase_ml.zzug.UTF_8;
        r8.<init>(r3, r4, r6, r9);
        r11.add(r8);
    L_0x01e8:
        r4 = r4 + r6;
    L_0x01e9:
        if (r4 >= r5) goto L_0x0422;
    L_0x01eb:
        r6 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r8 = r7.zzbkg;
        if (r2 != r8) goto L_0x0422;
    L_0x01f3:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r6, r7);
        r6 = r7.zzbkg;
        if (r6 < 0) goto L_0x020c;
    L_0x01fb:
        if (r6 != 0) goto L_0x0201;
    L_0x01fd:
        r11.add(r1);
        goto L_0x01e9;
    L_0x0201:
        r8 = new java.lang.String;
        r9 = com.google.android.gms.internal.firebase_ml.zzug.UTF_8;
        r8.<init>(r3, r4, r6, r9);
        r11.add(r8);
        goto L_0x01e8;
    L_0x020c:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrn();
        throw r1;
    L_0x0211:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrn();
        throw r1;
    L_0x0216:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r6 = r7.zzbkg;
        if (r6 < 0) goto L_0x0271;
    L_0x021e:
        if (r6 != 0) goto L_0x0224;
    L_0x0220:
        r11.add(r1);
        goto L_0x0237;
    L_0x0224:
        r8 = r4 + r6;
        r9 = com.google.android.gms.internal.firebase_ml.zzxe.zzf(r3, r4, r8);
        if (r9 == 0) goto L_0x026c;
    L_0x022c:
        r9 = new java.lang.String;
        r10 = com.google.android.gms.internal.firebase_ml.zzug.UTF_8;
        r9.<init>(r3, r4, r6, r10);
        r11.add(r9);
    L_0x0236:
        r4 = r8;
    L_0x0237:
        if (r4 >= r5) goto L_0x0422;
    L_0x0239:
        r6 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r8 = r7.zzbkg;
        if (r2 != r8) goto L_0x0422;
    L_0x0241:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r6, r7);
        r6 = r7.zzbkg;
        if (r6 < 0) goto L_0x0267;
    L_0x0249:
        if (r6 != 0) goto L_0x024f;
    L_0x024b:
        r11.add(r1);
        goto L_0x0237;
    L_0x024f:
        r8 = r4 + r6;
        r9 = com.google.android.gms.internal.firebase_ml.zzxe.zzf(r3, r4, r8);
        if (r9 == 0) goto L_0x0262;
    L_0x0257:
        r9 = new java.lang.String;
        r10 = com.google.android.gms.internal.firebase_ml.zzug.UTF_8;
        r9.<init>(r3, r4, r6, r10);
        r11.add(r9);
        goto L_0x0236;
    L_0x0262:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrr();
        throw r1;
    L_0x0267:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrn();
        throw r1;
    L_0x026c:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrr();
        throw r1;
    L_0x0271:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrn();
        throw r1;
    L_0x0276:
        r1 = 0;
        if (r6 != r10) goto L_0x029e;
    L_0x0279:
        r11 = (com.google.android.gms.internal.firebase_ml.zzsu) r11;
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r4 = r7.zzbkg;
        r4 = r4 + r2;
    L_0x0282:
        if (r2 >= r4) goto L_0x0295;
    L_0x0284:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r2, r7);
        r5 = r7.zzbkh;
        r8 = (r5 > r14 ? 1 : (r5 == r14 ? 0 : -1));
        if (r8 == 0) goto L_0x0290;
    L_0x028e:
        r5 = 1;
        goto L_0x0291;
    L_0x0290:
        r5 = 0;
    L_0x0291:
        r11.addBoolean(r5);
        goto L_0x0282;
    L_0x0295:
        if (r2 != r4) goto L_0x0299;
    L_0x0297:
        goto L_0x014e;
    L_0x0299:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrm();
        throw r1;
    L_0x029e:
        if (r6 != 0) goto L_0x0422;
    L_0x02a0:
        r11 = (com.google.android.gms.internal.firebase_ml.zzsu) r11;
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r4, r7);
        r8 = r7.zzbkh;
        r6 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1));
        if (r6 == 0) goto L_0x02ae;
    L_0x02ac:
        r6 = 1;
        goto L_0x02af;
    L_0x02ae:
        r6 = 0;
    L_0x02af:
        r11.addBoolean(r6);
    L_0x02b2:
        if (r4 >= r5) goto L_0x0422;
    L_0x02b4:
        r6 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r8 = r7.zzbkg;
        if (r2 != r8) goto L_0x0422;
    L_0x02bc:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r6, r7);
        r8 = r7.zzbkh;
        r6 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1));
        if (r6 == 0) goto L_0x02c8;
    L_0x02c6:
        r6 = 1;
        goto L_0x02c9;
    L_0x02c8:
        r6 = 0;
    L_0x02c9:
        r11.addBoolean(r6);
        goto L_0x02b2;
    L_0x02cd:
        if (r6 != r10) goto L_0x02ed;
    L_0x02cf:
        r11 = (com.google.android.gms.internal.firebase_ml.zzuf) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r2 = r7.zzbkg;
        r2 = r2 + r1;
    L_0x02d8:
        if (r1 >= r2) goto L_0x02e4;
    L_0x02da:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r1);
        r11.zzdh(r4);
        r1 = r1 + 4;
        goto L_0x02d8;
    L_0x02e4:
        if (r1 != r2) goto L_0x02e8;
    L_0x02e6:
        goto L_0x0423;
    L_0x02e8:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrm();
        throw r1;
    L_0x02ed:
        if (r6 != r9) goto L_0x0422;
    L_0x02ef:
        r11 = (com.google.android.gms.internal.firebase_ml.zzuf) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r18, r19);
        r11.zzdh(r1);
    L_0x02f8:
        r1 = r4 + 4;
        if (r1 >= r5) goto L_0x0423;
    L_0x02fc:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r1, r7);
        r6 = r7.zzbkg;
        if (r2 != r6) goto L_0x0423;
    L_0x0304:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4);
        r11.zzdh(r1);
        goto L_0x02f8;
    L_0x030c:
        if (r6 != r10) goto L_0x032c;
    L_0x030e:
        r11 = (com.google.android.gms.internal.firebase_ml.zzvc) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r2 = r7.zzbkg;
        r2 = r2 + r1;
    L_0x0317:
        if (r1 >= r2) goto L_0x0323;
    L_0x0319:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r1);
        r11.zzaa(r4);
        r1 = r1 + 8;
        goto L_0x0317;
    L_0x0323:
        if (r1 != r2) goto L_0x0327;
    L_0x0325:
        goto L_0x0423;
    L_0x0327:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrm();
        throw r1;
    L_0x032c:
        if (r6 != r13) goto L_0x0422;
    L_0x032e:
        r11 = (com.google.android.gms.internal.firebase_ml.zzvc) r11;
        r8 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r18, r19);
        r11.zzaa(r8);
    L_0x0337:
        r1 = r4 + 8;
        if (r1 >= r5) goto L_0x0423;
    L_0x033b:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r1, r7);
        r6 = r7.zzbkg;
        if (r2 != r6) goto L_0x0423;
    L_0x0343:
        r8 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r4);
        r11.zzaa(r8);
        goto L_0x0337;
    L_0x034b:
        if (r6 != r10) goto L_0x0353;
    L_0x034d:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r11, r7);
        goto L_0x0423;
    L_0x0353:
        if (r6 != 0) goto L_0x0422;
    L_0x0355:
        r22 = r18;
        r23 = r19;
        r24 = r20;
        r25 = r11;
        r26 = r30;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r21, r22, r23, r24, r25, r26);
        goto L_0x0423;
    L_0x0365:
        if (r6 != r10) goto L_0x0385;
    L_0x0367:
        r11 = (com.google.android.gms.internal.firebase_ml.zzvc) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r2 = r7.zzbkg;
        r2 = r2 + r1;
    L_0x0370:
        if (r1 >= r2) goto L_0x037c;
    L_0x0372:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r1, r7);
        r4 = r7.zzbkh;
        r11.zzaa(r4);
        goto L_0x0370;
    L_0x037c:
        if (r1 != r2) goto L_0x0380;
    L_0x037e:
        goto L_0x0423;
    L_0x0380:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrm();
        throw r1;
    L_0x0385:
        if (r6 != 0) goto L_0x0422;
    L_0x0387:
        r11 = (com.google.android.gms.internal.firebase_ml.zzvc) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r4, r7);
        r8 = r7.zzbkh;
        r11.zzaa(r8);
    L_0x0392:
        if (r1 >= r5) goto L_0x0423;
    L_0x0394:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r1, r7);
        r6 = r7.zzbkg;
        if (r2 != r6) goto L_0x0423;
    L_0x039c:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r4, r7);
        r8 = r7.zzbkh;
        r11.zzaa(r8);
        goto L_0x0392;
    L_0x03a6:
        if (r6 != r10) goto L_0x03c5;
    L_0x03a8:
        r11 = (com.google.android.gms.internal.firebase_ml.zzuc) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r2 = r7.zzbkg;
        r2 = r2 + r1;
    L_0x03b1:
        if (r1 >= r2) goto L_0x03bd;
    L_0x03b3:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zzd(r3, r1);
        r11.zzv(r4);
        r1 = r1 + 4;
        goto L_0x03b1;
    L_0x03bd:
        if (r1 != r2) goto L_0x03c0;
    L_0x03bf:
        goto L_0x0423;
    L_0x03c0:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrm();
        throw r1;
    L_0x03c5:
        if (r6 != r9) goto L_0x0422;
    L_0x03c7:
        r11 = (com.google.android.gms.internal.firebase_ml.zzuc) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzd(r18, r19);
        r11.zzv(r1);
    L_0x03d0:
        r1 = r4 + 4;
        if (r1 >= r5) goto L_0x0423;
    L_0x03d4:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r1, r7);
        r6 = r7.zzbkg;
        if (r2 != r6) goto L_0x0423;
    L_0x03dc:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzd(r3, r4);
        r11.zzv(r1);
        goto L_0x03d0;
    L_0x03e4:
        if (r6 != r10) goto L_0x0403;
    L_0x03e6:
        r11 = (com.google.android.gms.internal.firebase_ml.zzto) r11;
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r7);
        r2 = r7.zzbkg;
        r2 = r2 + r1;
    L_0x03ef:
        if (r1 >= r2) goto L_0x03fb;
    L_0x03f1:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zzc(r3, r1);
        r11.zze(r4);
        r1 = r1 + 8;
        goto L_0x03ef;
    L_0x03fb:
        if (r1 != r2) goto L_0x03fe;
    L_0x03fd:
        goto L_0x0423;
    L_0x03fe:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrm();
        throw r1;
    L_0x0403:
        if (r6 != r13) goto L_0x0422;
    L_0x0405:
        r11 = (com.google.android.gms.internal.firebase_ml.zzto) r11;
        r8 = com.google.android.gms.internal.firebase_ml.zzss.zzc(r18, r19);
        r11.zze(r8);
    L_0x040e:
        r1 = r4 + 8;
        if (r1 >= r5) goto L_0x0423;
    L_0x0412:
        r4 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r1, r7);
        r6 = r7.zzbkg;
        if (r2 != r6) goto L_0x0423;
    L_0x041a:
        r8 = com.google.android.gms.internal.firebase_ml.zzss.zzc(r3, r4);
        r11.zze(r8);
        goto L_0x040e;
    L_0x0422:
        r1 = r4;
    L_0x0423:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzvs.zza(java.lang.Object, byte[], int, int, int, int, int, int, long, int, long, com.google.android.gms.internal.firebase_ml.zzst):int");
    }

    private final <K, V> int zza(T t, byte[] bArr, int i, int i2, int i3, long j, zzst zzst) throws IOException {
        Unsafe unsafe = zzbqw;
        Object zzdj = zzdj(i3);
        Object object = unsafe.getObject(t, j);
        if (this.zzbrn.zzw(object)) {
            Object zzy = this.zzbrn.zzy(zzdj);
            this.zzbrn.zzf(zzy, object);
            unsafe.putObject(t, j, zzy);
            object = zzy;
        }
        zzvh zzz = this.zzbrn.zzz(zzdj);
        Map zzu = this.zzbrn.zzu(object);
        i = zzss.zza(bArr, i, zzst);
        int i4 = zzst.zzbkg;
        if (i4 < 0 || i4 > i2 - i) {
            throw zzuo.zzrm();
        }
        i4 += i;
        Object obj = zzz.zzbqp;
        Object obj2 = zzz.zzbqr;
        while (i < i4) {
            int i5 = i + 1;
            i = bArr[i];
            if (i < 0) {
                i5 = zzss.zza(i, bArr, i5, zzst);
                i = zzst.zzbkg;
            }
            int i6 = i5;
            i5 = i >>> 3;
            int i7 = i & 7;
            if (i5 != 1) {
                if (i5 == 2 && i7 == zzz.zzbqq.zztq()) {
                    i = zza(bArr, i6, i2, zzz.zzbqq, zzz.zzbqr.getClass(), zzst);
                    obj2 = zzst.zzbki;
                }
            } else if (i7 == zzz.zzbqo.zztq()) {
                i = zza(bArr, i6, i2, zzz.zzbqo, null, zzst);
                obj = zzst.zzbki;
            }
            i = zzss.zza(i, bArr, i6, i2, zzst);
        }
        if (i == i4) {
            zzu.put(obj, obj2);
            return i4;
        }
        throw zzuo.zzrq();
    }

    /* JADX WARNING: Missing block: B:62:0x018a, code:
            r2 = r4 + 4;
     */
    /* JADX WARNING: Missing block: B:66:0x019b, code:
            r2 = r4 + 8;
     */
    /* JADX WARNING: Missing block: B:67:0x019d, code:
            r12.putInt(r1, r13, r8);
     */
    /* JADX WARNING: Missing block: B:69:?, code:
            return r4;
     */
    /* JADX WARNING: Missing block: B:73:?, code:
            return r2;
     */
    private final int zza(T r17, byte[] r18, int r19, int r20, int r21, int r22, int r23, int r24, int r25, long r26, int r28, com.google.android.gms.internal.firebase_ml.zzst r29) throws java.io.IOException {
        /*
        r16 = this;
        r0 = r16;
        r1 = r17;
        r3 = r18;
        r4 = r19;
        r2 = r21;
        r8 = r22;
        r5 = r23;
        r9 = r26;
        r6 = r28;
        r11 = r29;
        r12 = zzbqw;
        r7 = r0.zzbqx;
        r13 = r6 + 2;
        r7 = r7[r13];
        r13 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        r7 = r7 & r13;
        r13 = (long) r7;
        r7 = 5;
        r15 = 2;
        switch(r25) {
            case 51: goto L_0x018d;
            case 52: goto L_0x017d;
            case 53: goto L_0x016d;
            case 54: goto L_0x016d;
            case 55: goto L_0x015d;
            case 56: goto L_0x014e;
            case 57: goto L_0x0140;
            case 58: goto L_0x0127;
            case 59: goto L_0x00f3;
            case 60: goto L_0x00c5;
            case 61: goto L_0x00b8;
            case 62: goto L_0x015d;
            case 63: goto L_0x008a;
            case 64: goto L_0x0140;
            case 65: goto L_0x014e;
            case 66: goto L_0x0075;
            case 67: goto L_0x0060;
            case 68: goto L_0x0028;
            default: goto L_0x0026;
        };
    L_0x0026:
        goto L_0x01a1;
    L_0x0028:
        r7 = 3;
        if (r5 != r7) goto L_0x01a1;
    L_0x002b:
        r2 = r2 & -8;
        r7 = r2 | 4;
        r2 = r0.zzdi(r6);
        r3 = r18;
        r4 = r19;
        r5 = r20;
        r6 = r7;
        r7 = r29;
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zza(r2, r3, r4, r5, r6, r7);
        r3 = r12.getInt(r1, r13);
        if (r3 != r8) goto L_0x004b;
    L_0x0046:
        r15 = r12.getObject(r1, r9);
        goto L_0x004c;
    L_0x004b:
        r15 = 0;
    L_0x004c:
        if (r15 != 0) goto L_0x0055;
    L_0x004e:
        r3 = r11.zzbki;
        r12.putObject(r1, r9, r3);
        goto L_0x019d;
    L_0x0055:
        r3 = r11.zzbki;
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zze(r15, r3);
        r12.putObject(r1, r9, r3);
        goto L_0x019d;
    L_0x0060:
        if (r5 != 0) goto L_0x01a1;
    L_0x0062:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r4, r11);
        r3 = r11.zzbkh;
        r3 = com.google.android.gms.internal.firebase_ml.zzti.zzp(r3);
        r3 = java.lang.Long.valueOf(r3);
        r12.putObject(r1, r9, r3);
        goto L_0x019d;
    L_0x0075:
        if (r5 != 0) goto L_0x01a1;
    L_0x0077:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r11);
        r3 = r11.zzbkg;
        r3 = com.google.android.gms.internal.firebase_ml.zzti.zzcp(r3);
        r3 = java.lang.Integer.valueOf(r3);
        r12.putObject(r1, r9, r3);
        goto L_0x019d;
    L_0x008a:
        if (r5 != 0) goto L_0x01a1;
    L_0x008c:
        r3 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r11);
        r4 = r11.zzbkg;
        r5 = r0.zzdk(r6);
        if (r5 == 0) goto L_0x00ae;
    L_0x0098:
        r5 = r5.zze(r4);
        if (r5 == 0) goto L_0x009f;
    L_0x009e:
        goto L_0x00ae;
    L_0x009f:
        r1 = zzab(r17);
        r4 = (long) r4;
        r4 = java.lang.Long.valueOf(r4);
        r1.zzb(r2, r4);
        r2 = r3;
        goto L_0x01a2;
    L_0x00ae:
        r2 = java.lang.Integer.valueOf(r4);
        r12.putObject(r1, r9, r2);
        r2 = r3;
        goto L_0x019d;
    L_0x00b8:
        if (r5 != r15) goto L_0x01a1;
    L_0x00ba:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zze(r3, r4, r11);
        r3 = r11.zzbki;
        r12.putObject(r1, r9, r3);
        goto L_0x019d;
    L_0x00c5:
        if (r5 != r15) goto L_0x01a1;
    L_0x00c7:
        r2 = r0.zzdi(r6);
        r5 = r20;
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zza(r2, r3, r4, r5, r11);
        r3 = r12.getInt(r1, r13);
        if (r3 != r8) goto L_0x00dc;
    L_0x00d7:
        r15 = r12.getObject(r1, r9);
        goto L_0x00dd;
    L_0x00dc:
        r15 = 0;
    L_0x00dd:
        if (r15 != 0) goto L_0x00e5;
    L_0x00df:
        r3 = r11.zzbki;
        r12.putObject(r1, r9, r3);
        goto L_0x00ee;
    L_0x00e5:
        r3 = r11.zzbki;
        r3 = com.google.android.gms.internal.firebase_ml.zzug.zze(r15, r3);
        r12.putObject(r1, r9, r3);
    L_0x00ee:
        r12.putInt(r1, r13, r8);
        goto L_0x01a2;
    L_0x00f3:
        if (r5 != r15) goto L_0x01a1;
    L_0x00f5:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r11);
        r4 = r11.zzbkg;
        if (r4 != 0) goto L_0x0103;
    L_0x00fd:
        r3 = "";
        r12.putObject(r1, r9, r3);
        goto L_0x0122;
    L_0x0103:
        r5 = 536870912; // 0x20000000 float:1.0842022E-19 double:2.652494739E-315;
        r5 = r24 & r5;
        if (r5 == 0) goto L_0x0117;
    L_0x0109:
        r5 = r2 + r4;
        r5 = com.google.android.gms.internal.firebase_ml.zzxe.zzf(r3, r2, r5);
        if (r5 == 0) goto L_0x0112;
    L_0x0111:
        goto L_0x0117;
    L_0x0112:
        r1 = com.google.android.gms.internal.firebase_ml.zzuo.zzrr();
        throw r1;
    L_0x0117:
        r5 = new java.lang.String;
        r6 = com.google.android.gms.internal.firebase_ml.zzug.UTF_8;
        r5.<init>(r3, r2, r4, r6);
        r12.putObject(r1, r9, r5);
        r2 = r2 + r4;
    L_0x0122:
        r12.putInt(r1, r13, r8);
        goto L_0x01a2;
    L_0x0127:
        if (r5 != 0) goto L_0x01a1;
    L_0x0129:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r4, r11);
        r3 = r11.zzbkh;
        r5 = 0;
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r7 == 0) goto L_0x0137;
    L_0x0135:
        r15 = 1;
        goto L_0x0138;
    L_0x0137:
        r15 = 0;
    L_0x0138:
        r3 = java.lang.Boolean.valueOf(r15);
        r12.putObject(r1, r9, r3);
        goto L_0x019d;
    L_0x0140:
        if (r5 != r7) goto L_0x01a1;
    L_0x0142:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zza(r18, r19);
        r2 = java.lang.Integer.valueOf(r2);
        r12.putObject(r1, r9, r2);
        goto L_0x018a;
    L_0x014e:
        r2 = 1;
        if (r5 != r2) goto L_0x01a1;
    L_0x0151:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r18, r19);
        r2 = java.lang.Long.valueOf(r2);
        r12.putObject(r1, r9, r2);
        goto L_0x019b;
    L_0x015d:
        if (r5 != 0) goto L_0x01a1;
    L_0x015f:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zza(r3, r4, r11);
        r3 = r11.zzbkg;
        r3 = java.lang.Integer.valueOf(r3);
        r12.putObject(r1, r9, r3);
        goto L_0x019d;
    L_0x016d:
        if (r5 != 0) goto L_0x01a1;
    L_0x016f:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r3, r4, r11);
        r3 = r11.zzbkh;
        r3 = java.lang.Long.valueOf(r3);
        r12.putObject(r1, r9, r3);
        goto L_0x019d;
    L_0x017d:
        if (r5 != r7) goto L_0x01a1;
    L_0x017f:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zzd(r18, r19);
        r2 = java.lang.Float.valueOf(r2);
        r12.putObject(r1, r9, r2);
    L_0x018a:
        r2 = r4 + 4;
        goto L_0x019d;
    L_0x018d:
        r2 = 1;
        if (r5 != r2) goto L_0x01a1;
    L_0x0190:
        r2 = com.google.android.gms.internal.firebase_ml.zzss.zzc(r18, r19);
        r2 = java.lang.Double.valueOf(r2);
        r12.putObject(r1, r9, r2);
    L_0x019b:
        r2 = r4 + 8;
    L_0x019d:
        r12.putInt(r1, r13, r8);
        goto L_0x01a2;
    L_0x01a1:
        r2 = r4;
    L_0x01a2:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzvs.zza(java.lang.Object, byte[], int, int, int, int, int, int, int, long, int, com.google.android.gms.internal.firebase_ml.zzst):int");
    }

    private final zzwe zzdi(int i) {
        i = (i / 3) << 1;
        zzwe zzwe = (zzwe) this.zzbqy[i];
        if (zzwe != null) {
            return zzwe;
        }
        zzwe = zzwb.zzso().zzk((Class) this.zzbqy[i + 1]);
        this.zzbqy[i] = zzwe;
        return zzwe;
    }

    private final Object zzdj(int i) {
        return this.zzbqy[(i / 3) << 1];
    }

    private final zzuj zzdk(int i) {
        return (zzuj) this.zzbqy[((i / 3) << 1) + 1];
    }

    /* JADX WARNING: Removed duplicated region for block: B:144:0x043a  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x03f8  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x03e7 A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x03f8  */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x043a  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x048e  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x049d  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x04cf  */
    /* JADX WARNING: Removed duplicated region for block: B:168:0x04e0  */
    /* JADX WARNING: Removed duplicated region for block: B:164:0x04d6  */
    /* JADX WARNING: Missing block: B:26:0x0094, code:
            r7 = r4;
     */
    /* JADX WARNING: Missing block: B:53:0x017a, code:
            r6 = r6 | r22;
     */
    /* JADX WARNING: Missing block: B:78:0x0219, code:
            r6 = r6 | r22;
     */
    /* JADX WARNING: Missing block: B:79:0x021b, code:
            r3 = r8;
            r2 = r9;
            r1 = r11;
            r9 = r13;
            r11 = r35;
            r13 = r5;
     */
    /* JADX WARNING: Missing block: B:83:0x0245, code:
            r33 = r7;
     */
    /* JADX WARNING: Missing block: B:96:0x02bc, code:
            r0 = r7 + 8;
     */
    /* JADX WARNING: Missing block: B:97:0x02be, code:
            r6 = r6 | r22;
            r7 = r33;
     */
    /* JADX WARNING: Missing block: B:98:0x02c2, code:
            r3 = r8;
            r2 = r9;
            r1 = r11;
            r9 = r13;
     */
    /* JADX WARNING: Missing block: B:99:0x02c6, code:
            r13 = r34;
            r11 = r35;
     */
    /* JADX WARNING: Missing block: B:100:0x02cc, code:
            r18 = r33;
            r22 = r6;
            r2 = r7;
            r6 = r8;
            r19 = r9;
            r27 = r10;
            r25 = r11;
            r7 = r35;
     */
    /* JADX WARNING: Missing block: B:117:0x0372, code:
            if (r0 == r15) goto L_0x03e1;
     */
    /* JADX WARNING: Missing block: B:125:0x03bb, code:
            if (r0 == r15) goto L_0x03e1;
     */
    final int zza(T r31, byte[] r32, int r33, int r34, int r35, com.google.android.gms.internal.firebase_ml.zzst r36) throws java.io.IOException {
        /*
        r30 = this;
        r15 = r30;
        r14 = r31;
        r12 = r32;
        r13 = r34;
        r11 = r35;
        r9 = r36;
        r10 = zzbqw;
        r16 = 0;
        r0 = r33;
        r1 = -1;
        r2 = 0;
        r3 = 0;
        r6 = 0;
        r7 = -1;
    L_0x0017:
        r17 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        if (r0 >= r13) goto L_0x047c;
    L_0x001c:
        r3 = r0 + 1;
        r0 = r12[r0];
        if (r0 >= 0) goto L_0x002b;
    L_0x0022:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r0, r12, r3, r9);
        r3 = r9.zzbkg;
        r4 = r0;
        r5 = r3;
        goto L_0x002d;
    L_0x002b:
        r5 = r0;
        r4 = r3;
    L_0x002d:
        r3 = r5 >>> 3;
        r0 = r5 & 7;
        r8 = 3;
        if (r3 <= r1) goto L_0x003a;
    L_0x0034:
        r2 = r2 / r8;
        r1 = r15.zzu(r3, r2);
        goto L_0x003e;
    L_0x003a:
        r1 = r15.zzdn(r3);
    L_0x003e:
        r2 = r1;
        r1 = -1;
        if (r2 != r1) goto L_0x0051;
    L_0x0042:
        r25 = r3;
        r2 = r4;
        r22 = r6;
        r18 = r7;
        r27 = r10;
        r7 = r11;
        r19 = 0;
        r6 = r5;
        goto L_0x03e5;
    L_0x0051:
        r1 = r15.zzbqx;
        r19 = r2 + 1;
        r8 = r1[r19];
        r19 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r19 = r8 & r19;
        r11 = r19 >>> 20;
        r19 = r5;
        r5 = r8 & r17;
        r12 = (long) r5;
        r5 = 17;
        r20 = r8;
        if (r11 > r5) goto L_0x02dc;
    L_0x0068:
        r5 = r2 + 2;
        r1 = r1[r5];
        r5 = r1 >>> 20;
        r8 = 1;
        r22 = r8 << r5;
        r1 = r1 & r17;
        if (r1 == r7) goto L_0x0083;
    L_0x0075:
        r5 = -1;
        if (r7 == r5) goto L_0x007c;
    L_0x0078:
        r8 = (long) r7;
        r10.putInt(r14, r8, r6);
    L_0x007c:
        r6 = (long) r1;
        r6 = r10.getInt(r14, r6);
        r7 = r1;
        goto L_0x0084;
    L_0x0083:
        r5 = -1;
    L_0x0084:
        r1 = 5;
        switch(r11) {
            case 0: goto L_0x02a4;
            case 1: goto L_0x028a;
            case 2: goto L_0x0264;
            case 3: goto L_0x0264;
            case 4: goto L_0x0249;
            case 5: goto L_0x0224;
            case 6: goto L_0x0201;
            case 7: goto L_0x01d9;
            case 8: goto L_0x01b4;
            case 9: goto L_0x017e;
            case 10: goto L_0x0163;
            case 11: goto L_0x0249;
            case 12: goto L_0x0131;
            case 13: goto L_0x0201;
            case 14: goto L_0x0224;
            case 15: goto L_0x0116;
            case 16: goto L_0x00e9;
            case 17: goto L_0x0097;
            default: goto L_0x0088;
        };
    L_0x0088:
        r12 = r32;
        r13 = r36;
        r9 = r2;
        r11 = r3;
        r33 = r7;
        r8 = r19;
        r19 = -1;
    L_0x0094:
        r7 = r4;
        goto L_0x02cc;
    L_0x0097:
        r8 = 3;
        if (r0 != r8) goto L_0x00dd;
    L_0x009a:
        r0 = r3 << 3;
        r8 = r0 | 4;
        r0 = r15.zzdi(r2);
        r1 = r32;
        r9 = r2;
        r2 = r4;
        r11 = r3;
        r3 = r34;
        r4 = r8;
        r8 = r19;
        r19 = -1;
        r5 = r36;
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r0, r1, r2, r3, r4, r5);
        r1 = r6 & r22;
        if (r1 != 0) goto L_0x00c0;
    L_0x00b8:
        r5 = r36;
        r1 = r5.zzbki;
        r10.putObject(r14, r12, r1);
        goto L_0x00cf;
    L_0x00c0:
        r5 = r36;
        r1 = r10.getObject(r14, r12);
        r2 = r5.zzbki;
        r1 = com.google.android.gms.internal.firebase_ml.zzug.zze(r1, r2);
        r10.putObject(r14, r12, r1);
    L_0x00cf:
        r6 = r6 | r22;
        r12 = r32;
        r13 = r34;
        r3 = r8;
        r2 = r9;
        r1 = r11;
        r11 = r35;
        r9 = r5;
        goto L_0x0017;
    L_0x00dd:
        r9 = r2;
        r11 = r3;
        r8 = r19;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        goto L_0x0245;
    L_0x00e9:
        r5 = r36;
        r9 = r2;
        r11 = r3;
        r8 = r19;
        r19 = -1;
        if (r0 != 0) goto L_0x0111;
    L_0x00f3:
        r2 = r12;
        r12 = r32;
        r13 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r12, r4, r5);
        r0 = r5.zzbkh;
        r17 = com.google.android.gms.internal.firebase_ml.zzti.zzp(r0);
        r0 = r10;
        r1 = r31;
        r33 = r13;
        r13 = r5;
        r4 = r17;
        r0.putLong(r1, r2, r4);
        r6 = r6 | r22;
        r0 = r33;
        goto L_0x02c2;
    L_0x0111:
        r12 = r32;
        r13 = r5;
        goto L_0x0245;
    L_0x0116:
        r9 = r2;
        r11 = r3;
        r2 = r12;
        r8 = r19;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        if (r0 != 0) goto L_0x0245;
    L_0x0123:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r12, r4, r13);
        r1 = r13.zzbkg;
        r1 = com.google.android.gms.internal.firebase_ml.zzti.zzcp(r1);
        r10.putInt(r14, r2, r1);
        goto L_0x017a;
    L_0x0131:
        r9 = r2;
        r11 = r3;
        r2 = r12;
        r8 = r19;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        if (r0 != 0) goto L_0x0245;
    L_0x013e:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r12, r4, r13);
        r1 = r13.zzbkg;
        r4 = r15.zzdk(r9);
        if (r4 == 0) goto L_0x015f;
    L_0x014a:
        r4 = r4.zze(r1);
        if (r4 == 0) goto L_0x0151;
    L_0x0150:
        goto L_0x015f;
    L_0x0151:
        r2 = zzab(r31);
        r3 = (long) r1;
        r1 = java.lang.Long.valueOf(r3);
        r2.zzb(r8, r1);
        goto L_0x02c2;
    L_0x015f:
        r10.putInt(r14, r2, r1);
        goto L_0x017a;
    L_0x0163:
        r9 = r2;
        r11 = r3;
        r2 = r12;
        r8 = r19;
        r1 = 2;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        if (r0 != r1) goto L_0x0245;
    L_0x0171:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zze(r12, r4, r13);
        r1 = r13.zzbki;
        r10.putObject(r14, r2, r1);
    L_0x017a:
        r6 = r6 | r22;
        goto L_0x02c2;
    L_0x017e:
        r9 = r2;
        r11 = r3;
        r2 = r12;
        r8 = r19;
        r1 = 2;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        if (r0 != r1) goto L_0x01b0;
    L_0x018c:
        r0 = r15.zzdi(r9);
        r5 = r34;
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r0, r12, r4, r5, r13);
        r1 = r6 & r22;
        if (r1 != 0) goto L_0x01a1;
    L_0x019a:
        r1 = r13.zzbki;
        r10.putObject(r14, r2, r1);
        goto L_0x0219;
    L_0x01a1:
        r1 = r10.getObject(r14, r2);
        r4 = r13.zzbki;
        r1 = com.google.android.gms.internal.firebase_ml.zzug.zze(r1, r4);
        r10.putObject(r14, r2, r1);
        goto L_0x0219;
    L_0x01b0:
        r5 = r34;
        goto L_0x0245;
    L_0x01b4:
        r5 = r34;
        r9 = r2;
        r11 = r3;
        r2 = r12;
        r8 = r19;
        r1 = 2;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        if (r0 != r1) goto L_0x0245;
    L_0x01c4:
        r0 = 536870912; // 0x20000000 float:1.0842022E-19 double:2.652494739E-315;
        r0 = r20 & r0;
        if (r0 != 0) goto L_0x01cf;
    L_0x01ca:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zzc(r12, r4, r13);
        goto L_0x01d3;
    L_0x01cf:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zzd(r12, r4, r13);
    L_0x01d3:
        r1 = r13.zzbki;
        r10.putObject(r14, r2, r1);
        goto L_0x0219;
    L_0x01d9:
        r5 = r34;
        r9 = r2;
        r11 = r3;
        r2 = r12;
        r8 = r19;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        if (r0 != 0) goto L_0x0245;
    L_0x01e8:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r12, r4, r13);
        r33 = r0;
        r0 = r13.zzbkh;
        r20 = 0;
        r4 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1));
        if (r4 == 0) goto L_0x01f8;
    L_0x01f6:
        r0 = 1;
        goto L_0x01f9;
    L_0x01f8:
        r0 = 0;
    L_0x01f9:
        com.google.android.gms.internal.firebase_ml.zzxc.zza(r14, r2, r0);
        r6 = r6 | r22;
        r0 = r33;
        goto L_0x021b;
    L_0x0201:
        r5 = r34;
        r9 = r2;
        r11 = r3;
        r2 = r12;
        r8 = r19;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        if (r0 != r1) goto L_0x0245;
    L_0x0210:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r12, r4);
        r10.putInt(r14, r2, r0);
        r0 = r4 + 4;
    L_0x0219:
        r6 = r6 | r22;
    L_0x021b:
        r3 = r8;
        r2 = r9;
        r1 = r11;
        r9 = r13;
        r11 = r35;
        r13 = r5;
        goto L_0x0017;
    L_0x0224:
        r5 = r34;
        r9 = r2;
        r11 = r3;
        r2 = r12;
        r8 = r19;
        r1 = 1;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        if (r0 != r1) goto L_0x0245;
    L_0x0234:
        r17 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r12, r4);
        r0 = r10;
        r1 = r31;
        r33 = r7;
        r7 = r4;
        r4 = r17;
        r0.putLong(r1, r2, r4);
        goto L_0x02bc;
    L_0x0245:
        r33 = r7;
        goto L_0x0094;
    L_0x0249:
        r9 = r2;
        r11 = r3;
        r33 = r7;
        r2 = r12;
        r8 = r19;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        r7 = r4;
        if (r0 != 0) goto L_0x02cc;
    L_0x0259:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r12, r7, r13);
        r1 = r13.zzbkg;
        r10.putInt(r14, r2, r1);
        goto L_0x02be;
    L_0x0264:
        r9 = r2;
        r11 = r3;
        r33 = r7;
        r2 = r12;
        r8 = r19;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        r7 = r4;
        if (r0 != 0) goto L_0x02cc;
    L_0x0274:
        r7 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r12, r7, r13);
        r4 = r13.zzbkh;
        r0 = r10;
        r1 = r31;
        r0.putLong(r1, r2, r4);
        r6 = r6 | r22;
        r0 = r7;
        r3 = r8;
        r2 = r9;
        r1 = r11;
        r9 = r13;
        r7 = r33;
        goto L_0x02c6;
    L_0x028a:
        r9 = r2;
        r11 = r3;
        r33 = r7;
        r2 = r12;
        r8 = r19;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        r7 = r4;
        if (r0 != r1) goto L_0x02cc;
    L_0x029a:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zzd(r12, r7);
        com.google.android.gms.internal.firebase_ml.zzxc.zza(r14, r2, r0);
        r0 = r7 + 4;
        goto L_0x02be;
    L_0x02a4:
        r9 = r2;
        r11 = r3;
        r33 = r7;
        r2 = r12;
        r8 = r19;
        r1 = 1;
        r19 = -1;
        r12 = r32;
        r13 = r36;
        r7 = r4;
        if (r0 != r1) goto L_0x02cc;
    L_0x02b5:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zzc(r12, r7);
        com.google.android.gms.internal.firebase_ml.zzxc.zza(r14, r2, r0);
    L_0x02bc:
        r0 = r7 + 8;
    L_0x02be:
        r6 = r6 | r22;
        r7 = r33;
    L_0x02c2:
        r3 = r8;
        r2 = r9;
        r1 = r11;
        r9 = r13;
    L_0x02c6:
        r13 = r34;
        r11 = r35;
        goto L_0x0017;
    L_0x02cc:
        r18 = r33;
        r22 = r6;
        r2 = r7;
        r6 = r8;
        r19 = r9;
        r27 = r10;
        r25 = r11;
        r7 = r35;
        goto L_0x03e5;
    L_0x02dc:
        r5 = r3;
        r18 = r7;
        r8 = r19;
        r19 = -1;
        r7 = r4;
        r28 = r12;
        r12 = r32;
        r13 = r9;
        r9 = r2;
        r2 = r28;
        r1 = 27;
        if (r11 != r1) goto L_0x0341;
    L_0x02f0:
        r1 = 2;
        if (r0 != r1) goto L_0x0334;
    L_0x02f3:
        r0 = r10.getObject(r14, r2);
        r0 = (com.google.android.gms.internal.firebase_ml.zzun) r0;
        r1 = r0.zzps();
        if (r1 != 0) goto L_0x0311;
    L_0x02ff:
        r1 = r0.size();
        if (r1 != 0) goto L_0x0308;
    L_0x0305:
        r1 = 10;
        goto L_0x030a;
    L_0x0308:
        r1 = r1 << 1;
    L_0x030a:
        r0 = r0.zzck(r1);
        r10.putObject(r14, r2, r0);
    L_0x0311:
        r11 = r0;
        r0 = r15.zzdi(r9);
        r1 = r8;
        r2 = r32;
        r3 = r7;
        r4 = r34;
        r7 = r5;
        r5 = r11;
        r22 = r6;
        r6 = r36;
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r0, r1, r2, r3, r4, r5, r6);
        r11 = r35;
        r1 = r7;
        r3 = r8;
        r2 = r9;
        r9 = r13;
        r7 = r18;
        r6 = r22;
        r13 = r34;
        goto L_0x0017;
    L_0x0334:
        r22 = r6;
        r25 = r5;
        r15 = r7;
        r26 = r8;
        r19 = r9;
        r27 = r10;
        goto L_0x03be;
    L_0x0341:
        r22 = r6;
        r6 = r5;
        r1 = 49;
        if (r11 > r1) goto L_0x0390;
    L_0x0348:
        r5 = r20;
        r4 = (long) r5;
        r1 = r0;
        r0 = r30;
        r33 = r1;
        r1 = r31;
        r23 = r2;
        r2 = r32;
        r3 = r7;
        r20 = r4;
        r4 = r34;
        r5 = r8;
        r25 = r6;
        r15 = r7;
        r7 = r33;
        r26 = r8;
        r8 = r9;
        r19 = r9;
        r27 = r10;
        r9 = r20;
        r12 = r23;
        r14 = r36;
        r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7, r8, r9, r11, r12, r14);
        if (r0 != r15) goto L_0x0376;
    L_0x0374:
        goto L_0x03e1;
    L_0x0376:
        r15 = r30;
        r14 = r31;
        r12 = r32;
        r13 = r34;
        r11 = r35;
        r9 = r36;
        r7 = r18;
        r2 = r19;
        r6 = r22;
        r1 = r25;
        r3 = r26;
    L_0x038c:
        r10 = r27;
        goto L_0x0017;
    L_0x0390:
        r33 = r0;
        r23 = r2;
        r25 = r6;
        r15 = r7;
        r26 = r8;
        r19 = r9;
        r27 = r10;
        r5 = r20;
        r0 = 50;
        if (r11 != r0) goto L_0x03c4;
    L_0x03a3:
        r7 = r33;
        r0 = 2;
        if (r7 != r0) goto L_0x03be;
    L_0x03a8:
        r0 = r30;
        r1 = r31;
        r2 = r32;
        r3 = r15;
        r4 = r34;
        r5 = r19;
        r6 = r23;
        r8 = r36;
        r0 = r0.zza(r1, r2, r3, r4, r5, r6, r8);
        if (r0 != r15) goto L_0x0376;
    L_0x03bd:
        goto L_0x03e1;
    L_0x03be:
        r7 = r35;
        r2 = r15;
    L_0x03c1:
        r6 = r26;
        goto L_0x03e5;
    L_0x03c4:
        r7 = r33;
        r0 = r30;
        r1 = r31;
        r2 = r32;
        r3 = r15;
        r4 = r34;
        r8 = r5;
        r5 = r26;
        r6 = r25;
        r9 = r11;
        r10 = r23;
        r12 = r19;
        r13 = r36;
        r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r12, r13);
        if (r0 != r15) goto L_0x0462;
    L_0x03e1:
        r7 = r35;
        r2 = r0;
        goto L_0x03c1;
    L_0x03e5:
        if (r6 != r7) goto L_0x03f2;
    L_0x03e7:
        if (r7 != 0) goto L_0x03ea;
    L_0x03e9:
        goto L_0x03f2;
    L_0x03ea:
        r8 = r30;
        r11 = r31;
        r9 = r2;
        r10 = r6;
        goto L_0x0487;
    L_0x03f2:
        r8 = r30;
        r0 = r8.zzbrc;
        if (r0 == 0) goto L_0x043a;
    L_0x03f8:
        r9 = r36;
        r0 = r9.zzix;
        r1 = com.google.android.gms.internal.firebase_ml.zztr.zzql();
        if (r0 == r1) goto L_0x0437;
    L_0x0402:
        r0 = r8.zzbrb;
        r1 = r9.zzix;
        r10 = r25;
        r0 = r1.zza(r0, r10);
        if (r0 != 0) goto L_0x0427;
    L_0x040e:
        r4 = zzab(r31);
        r0 = r6;
        r1 = r32;
        r3 = r34;
        r5 = r36;
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r0, r1, r2, r3, r4, r5);
        r14 = r31;
        r12 = r32;
        r13 = r34;
        r3 = r6;
        r11 = r7;
        r15 = r8;
        goto L_0x0473;
    L_0x0427:
        r11 = r31;
        r0 = r11;
        r0 = (com.google.android.gms.internal.firebase_ml.zzue.zzd) r0;
        r0.zzrk();
        r0 = r0.zzbon;
        r0 = new java.lang.NoSuchMethodError;
        r0.<init>();
        throw r0;
    L_0x0437:
        r11 = r31;
        goto L_0x043e;
    L_0x043a:
        r11 = r31;
        r9 = r36;
    L_0x043e:
        r10 = r25;
        r4 = zzab(r31);
        r0 = r6;
        r1 = r32;
        r3 = r34;
        r5 = r36;
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r0, r1, r2, r3, r4, r5);
        r12 = r32;
        r13 = r34;
        r3 = r6;
        r15 = r8;
        r1 = r10;
        r14 = r11;
        r2 = r19;
        r6 = r22;
        r10 = r27;
        r11 = r7;
        r7 = r18;
        goto L_0x0017;
    L_0x0462:
        r10 = r25;
        r6 = r26;
        r15 = r30;
        r14 = r31;
        r12 = r32;
        r13 = r34;
        r11 = r35;
        r9 = r36;
        r3 = r6;
    L_0x0473:
        r1 = r10;
        r7 = r18;
        r2 = r19;
        r6 = r22;
        goto L_0x038c;
    L_0x047c:
        r22 = r6;
        r18 = r7;
        r27 = r10;
        r7 = r11;
        r11 = r14;
        r8 = r15;
        r9 = r0;
        r10 = r3;
    L_0x0487:
        r0 = r18;
        r1 = r22;
        r2 = -1;
        if (r0 == r2) goto L_0x0494;
    L_0x048e:
        r2 = (long) r0;
        r0 = r27;
        r0.putInt(r11, r2, r1);
    L_0x0494:
        r0 = 0;
        r1 = r8.zzbrh;
        r5 = r0;
        r12 = r1;
    L_0x0499:
        r0 = r8.zzbri;
        if (r12 >= r0) goto L_0x04cd;
    L_0x049d:
        r0 = r8.zzbrg;
        r1 = r0[r12];
        r6 = r8.zzbrl;
        r0 = r8.zzbqx;
        r2 = r0[r1];
        r0 = r8.zzdl(r1);
        r0 = r0 & r17;
        r3 = (long) r0;
        r0 = com.google.android.gms.internal.firebase_ml.zzxc.zzp(r11, r3);
        if (r0 != 0) goto L_0x04b5;
    L_0x04b4:
        goto L_0x04c8;
    L_0x04b5:
        r4 = r8.zzdk(r1);
        if (r4 != 0) goto L_0x04bc;
    L_0x04bb:
        goto L_0x04c8;
    L_0x04bc:
        r3 = r8.zzbrn;
        r3 = r3.zzu(r0);
        r0 = r30;
        r5 = r0.zza(r1, r2, r3, r4, r5, r6);
    L_0x04c8:
        r5 = (com.google.android.gms.internal.firebase_ml.zzwx) r5;
        r12 = r12 + 1;
        goto L_0x0499;
    L_0x04cd:
        if (r5 == 0) goto L_0x04d4;
    L_0x04cf:
        r0 = r8.zzbrl;
        r0.zzj(r11, r5);
    L_0x04d4:
        if (r7 != 0) goto L_0x04e0;
    L_0x04d6:
        r0 = r34;
        if (r9 != r0) goto L_0x04db;
    L_0x04da:
        goto L_0x04e6;
    L_0x04db:
        r0 = com.google.android.gms.internal.firebase_ml.zzuo.zzrq();
        throw r0;
    L_0x04e0:
        r0 = r34;
        if (r9 > r0) goto L_0x04e7;
    L_0x04e4:
        if (r10 != r7) goto L_0x04e7;
    L_0x04e6:
        return r9;
    L_0x04e7:
        r0 = com.google.android.gms.internal.firebase_ml.zzuo.zzrq();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzvs.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.firebase_ml.zzst):int");
    }

    /* JADX WARNING: Missing block: B:52:0x010b, code:
            r2 = r4;
            r1 = r7;
     */
    /* JADX WARNING: Missing block: B:62:0x013d, code:
            r0 = r6;
     */
    /* JADX WARNING: Missing block: B:69:0x0159, code:
            r0 = r8 + 8;
     */
    /* JADX WARNING: Missing block: B:70:0x015b, code:
            r1 = r7;
            r2 = r10;
     */
    /* JADX WARNING: Missing block: B:71:0x015f, code:
            r24 = r7;
            r15 = r8;
            r18 = r9;
            r19 = r10;
     */
    /* JADX WARNING: Missing block: B:88:0x01e2, code:
            if (r0 == r15) goto L_0x0230;
     */
    /* JADX WARNING: Missing block: B:94:0x020f, code:
            if (r0 == r15) goto L_0x0230;
     */
    /* JADX WARNING: Missing block: B:97:0x022e, code:
            if (r0 == r15) goto L_0x0230;
     */
    public final void zza(T r28, byte[] r29, int r30, int r31, com.google.android.gms.internal.firebase_ml.zzst r32) throws java.io.IOException {
        /*
        r27 = this;
        r15 = r27;
        r14 = r28;
        r12 = r29;
        r13 = r31;
        r11 = r32;
        r0 = r15.zzbre;
        if (r0 == 0) goto L_0x025d;
    L_0x000e:
        r9 = zzbqw;
        r10 = -1;
        r16 = 0;
        r0 = r30;
        r1 = -1;
        r2 = 0;
    L_0x0017:
        if (r0 >= r13) goto L_0x0254;
    L_0x0019:
        r3 = r0 + 1;
        r0 = r12[r0];
        if (r0 >= 0) goto L_0x0029;
    L_0x001f:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r0, r12, r3, r11);
        r3 = r11.zzbkg;
        r8 = r0;
        r17 = r3;
        goto L_0x002c;
    L_0x0029:
        r17 = r0;
        r8 = r3;
    L_0x002c:
        r7 = r17 >>> 3;
        r6 = r17 & 7;
        if (r7 <= r1) goto L_0x0039;
    L_0x0032:
        r2 = r2 / 3;
        r0 = r15.zzu(r7, r2);
        goto L_0x003d;
    L_0x0039:
        r0 = r15.zzdn(r7);
    L_0x003d:
        r4 = r0;
        if (r4 != r10) goto L_0x004b;
    L_0x0040:
        r24 = r7;
        r2 = r8;
        r18 = r9;
        r19 = 0;
        r26 = -1;
        goto L_0x0231;
    L_0x004b:
        r0 = r15.zzbqx;
        r1 = r4 + 1;
        r5 = r0[r1];
        r0 = 267386880; // 0xff00000 float:2.3665827E-29 double:1.321066716E-315;
        r0 = r0 & r5;
        r3 = r0 >>> 20;
        r0 = 1048575; // 0xfffff float:1.469367E-39 double:5.18065E-318;
        r0 = r0 & r5;
        r1 = (long) r0;
        r0 = 17;
        r10 = 2;
        if (r3 > r0) goto L_0x0167;
    L_0x0060:
        r0 = 1;
        switch(r3) {
            case 0: goto L_0x014e;
            case 1: goto L_0x013f;
            case 2: goto L_0x012d;
            case 3: goto L_0x012d;
            case 4: goto L_0x011f;
            case 5: goto L_0x010f;
            case 6: goto L_0x00fe;
            case 7: goto L_0x00e8;
            case 8: goto L_0x00d1;
            case 9: goto L_0x00b0;
            case 10: goto L_0x00a3;
            case 11: goto L_0x011f;
            case 12: goto L_0x0094;
            case 13: goto L_0x00fe;
            case 14: goto L_0x010f;
            case 15: goto L_0x0081;
            case 16: goto L_0x0066;
            default: goto L_0x0064;
        };
    L_0x0064:
        goto L_0x01a4;
    L_0x0066:
        if (r6 != 0) goto L_0x01a4;
    L_0x0068:
        r6 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r12, r8, r11);
        r19 = r1;
        r0 = r11.zzbkh;
        r21 = com.google.android.gms.internal.firebase_ml.zzti.zzp(r0);
        r0 = r9;
        r2 = r19;
        r1 = r28;
        r10 = r4;
        r4 = r21;
        r0.putLong(r1, r2, r4);
        goto L_0x013d;
    L_0x0081:
        r2 = r1;
        r10 = r4;
        if (r6 != 0) goto L_0x015f;
    L_0x0085:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r12, r8, r11);
        r1 = r11.zzbkg;
        r1 = com.google.android.gms.internal.firebase_ml.zzti.zzcp(r1);
        r9.putInt(r14, r2, r1);
        goto L_0x015b;
    L_0x0094:
        r2 = r1;
        r10 = r4;
        if (r6 != 0) goto L_0x015f;
    L_0x0098:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r12, r8, r11);
        r1 = r11.zzbkg;
        r9.putInt(r14, r2, r1);
        goto L_0x015b;
    L_0x00a3:
        r2 = r1;
        if (r6 != r10) goto L_0x01a4;
    L_0x00a6:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zze(r12, r8, r11);
        r1 = r11.zzbki;
        r9.putObject(r14, r2, r1);
        goto L_0x010b;
    L_0x00b0:
        r2 = r1;
        if (r6 != r10) goto L_0x01a4;
    L_0x00b3:
        r0 = r15.zzdi(r4);
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r0, r12, r8, r13, r11);
        r1 = r9.getObject(r14, r2);
        if (r1 != 0) goto L_0x00c7;
    L_0x00c1:
        r1 = r11.zzbki;
        r9.putObject(r14, r2, r1);
        goto L_0x010b;
    L_0x00c7:
        r5 = r11.zzbki;
        r1 = com.google.android.gms.internal.firebase_ml.zzug.zze(r1, r5);
        r9.putObject(r14, r2, r1);
        goto L_0x010b;
    L_0x00d1:
        r2 = r1;
        if (r6 != r10) goto L_0x01a4;
    L_0x00d4:
        r0 = 536870912; // 0x20000000 float:1.0842022E-19 double:2.652494739E-315;
        r0 = r0 & r5;
        if (r0 != 0) goto L_0x00de;
    L_0x00d9:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zzc(r12, r8, r11);
        goto L_0x00e2;
    L_0x00de:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zzd(r12, r8, r11);
    L_0x00e2:
        r1 = r11.zzbki;
        r9.putObject(r14, r2, r1);
        goto L_0x010b;
    L_0x00e8:
        r2 = r1;
        if (r6 != 0) goto L_0x01a4;
    L_0x00eb:
        r1 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r12, r8, r11);
        r5 = r11.zzbkh;
        r19 = 0;
        r8 = (r5 > r19 ? 1 : (r5 == r19 ? 0 : -1));
        if (r8 == 0) goto L_0x00f8;
    L_0x00f7:
        goto L_0x00f9;
    L_0x00f8:
        r0 = 0;
    L_0x00f9:
        com.google.android.gms.internal.firebase_ml.zzxc.zza(r14, r2, r0);
        r0 = r1;
        goto L_0x010b;
    L_0x00fe:
        r2 = r1;
        r0 = 5;
        if (r6 != r0) goto L_0x01a4;
    L_0x0102:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r12, r8);
        r9.putInt(r14, r2, r0);
        r0 = r8 + 4;
    L_0x010b:
        r2 = r4;
        r1 = r7;
        goto L_0x0251;
    L_0x010f:
        r2 = r1;
        if (r6 != r0) goto L_0x01a4;
    L_0x0112:
        r5 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r12, r8);
        r0 = r9;
        r1 = r28;
        r10 = r4;
        r4 = r5;
        r0.putLong(r1, r2, r4);
        goto L_0x0159;
    L_0x011f:
        r2 = r1;
        r10 = r4;
        if (r6 != 0) goto L_0x015f;
    L_0x0123:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r12, r8, r11);
        r1 = r11.zzbkg;
        r9.putInt(r14, r2, r1);
        goto L_0x015b;
    L_0x012d:
        r2 = r1;
        r10 = r4;
        if (r6 != 0) goto L_0x015f;
    L_0x0131:
        r6 = com.google.android.gms.internal.firebase_ml.zzss.zzb(r12, r8, r11);
        r4 = r11.zzbkh;
        r0 = r9;
        r1 = r28;
        r0.putLong(r1, r2, r4);
    L_0x013d:
        r0 = r6;
        goto L_0x015b;
    L_0x013f:
        r2 = r1;
        r10 = r4;
        r0 = 5;
        if (r6 != r0) goto L_0x015f;
    L_0x0144:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zzd(r12, r8);
        com.google.android.gms.internal.firebase_ml.zzxc.zza(r14, r2, r0);
        r0 = r8 + 4;
        goto L_0x015b;
    L_0x014e:
        r2 = r1;
        r10 = r4;
        if (r6 != r0) goto L_0x015f;
    L_0x0152:
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zzc(r12, r8);
        com.google.android.gms.internal.firebase_ml.zzxc.zza(r14, r2, r0);
    L_0x0159:
        r0 = r8 + 8;
    L_0x015b:
        r1 = r7;
        r2 = r10;
        goto L_0x0251;
    L_0x015f:
        r24 = r7;
        r15 = r8;
        r18 = r9;
        r19 = r10;
        goto L_0x01ab;
    L_0x0167:
        r0 = 27;
        if (r3 != r0) goto L_0x01af;
    L_0x016b:
        if (r6 != r10) goto L_0x01a4;
    L_0x016d:
        r0 = r9.getObject(r14, r1);
        r0 = (com.google.android.gms.internal.firebase_ml.zzun) r0;
        r3 = r0.zzps();
        if (r3 != 0) goto L_0x018b;
    L_0x0179:
        r3 = r0.size();
        if (r3 != 0) goto L_0x0182;
    L_0x017f:
        r3 = 10;
        goto L_0x0184;
    L_0x0182:
        r3 = r3 << 1;
    L_0x0184:
        r0 = r0.zzck(r3);
        r9.putObject(r14, r1, r0);
    L_0x018b:
        r5 = r0;
        r0 = r15.zzdi(r4);
        r1 = r17;
        r2 = r29;
        r3 = r8;
        r19 = r4;
        r4 = r31;
        r6 = r32;
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r0, r1, r2, r3, r4, r5, r6);
        r1 = r7;
        r2 = r19;
        goto L_0x0251;
    L_0x01a4:
        r19 = r4;
        r24 = r7;
        r15 = r8;
        r18 = r9;
    L_0x01ab:
        r26 = -1;
        goto L_0x0212;
    L_0x01af:
        r19 = r4;
        r0 = 49;
        if (r3 > r0) goto L_0x01e5;
    L_0x01b5:
        r4 = (long) r5;
        r0 = r27;
        r20 = r1;
        r1 = r28;
        r2 = r29;
        r10 = r3;
        r3 = r8;
        r22 = r4;
        r4 = r31;
        r5 = r17;
        r30 = r6;
        r6 = r7;
        r24 = r7;
        r7 = r30;
        r15 = r8;
        r8 = r19;
        r18 = r9;
        r25 = r10;
        r26 = -1;
        r9 = r22;
        r11 = r25;
        r12 = r20;
        r14 = r32;
        r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7, r8, r9, r11, r12, r14);
        if (r0 != r15) goto L_0x0241;
    L_0x01e4:
        goto L_0x0230;
    L_0x01e5:
        r20 = r1;
        r25 = r3;
        r30 = r6;
        r24 = r7;
        r15 = r8;
        r18 = r9;
        r26 = -1;
        r0 = 50;
        r9 = r25;
        if (r9 != r0) goto L_0x0214;
    L_0x01f8:
        r7 = r30;
        if (r7 != r10) goto L_0x0212;
    L_0x01fc:
        r0 = r27;
        r1 = r28;
        r2 = r29;
        r3 = r15;
        r4 = r31;
        r5 = r19;
        r6 = r20;
        r8 = r32;
        r0 = r0.zza(r1, r2, r3, r4, r5, r6, r8);
        if (r0 != r15) goto L_0x0241;
    L_0x0211:
        goto L_0x0230;
    L_0x0212:
        r2 = r15;
        goto L_0x0231;
    L_0x0214:
        r7 = r30;
        r0 = r27;
        r1 = r28;
        r2 = r29;
        r3 = r15;
        r4 = r31;
        r8 = r5;
        r5 = r17;
        r6 = r24;
        r10 = r20;
        r12 = r19;
        r13 = r32;
        r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r12, r13);
        if (r0 != r15) goto L_0x0241;
    L_0x0230:
        r2 = r0;
    L_0x0231:
        r4 = zzab(r28);
        r0 = r17;
        r1 = r29;
        r3 = r31;
        r5 = r32;
        r0 = com.google.android.gms.internal.firebase_ml.zzss.zza(r0, r1, r2, r3, r4, r5);
    L_0x0241:
        r15 = r27;
        r14 = r28;
        r12 = r29;
        r13 = r31;
        r11 = r32;
        r9 = r18;
        r2 = r19;
        r1 = r24;
    L_0x0251:
        r10 = -1;
        goto L_0x0017;
    L_0x0254:
        r4 = r13;
        if (r0 != r4) goto L_0x0258;
    L_0x0257:
        return;
    L_0x0258:
        r0 = com.google.android.gms.internal.firebase_ml.zzuo.zzrq();
        throw r0;
    L_0x025d:
        r4 = r13;
        r5 = 0;
        r0 = r27;
        r1 = r28;
        r2 = r29;
        r3 = r30;
        r4 = r31;
        r6 = r32;
        r0.zza(r1, r2, r3, r4, r5, r6);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzvs.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.firebase_ml.zzst):void");
    }

    public final void zzq(T t) {
        int i;
        int i2 = this.zzbrh;
        while (true) {
            i = this.zzbri;
            if (i2 >= i) {
                break;
            }
            long zzdl = (long) (zzdl(this.zzbrg[i2]) & 1048575);
            Object zzp = zzxc.zzp(t, zzdl);
            if (zzp != null) {
                zzxc.zza((Object) t, zzdl, this.zzbrn.zzx(zzp));
            }
            i2++;
        }
        i2 = this.zzbrg.length;
        while (i < i2) {
            this.zzbrk.zzb(t, (long) this.zzbrg[i]);
            i++;
        }
        this.zzbrl.zzq(t);
        if (this.zzbrc) {
            this.zzbrm.zzq(t);
        }
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzuj zzuj, UB ub, zzww<UT, UB> zzww) {
        zzvh zzz = this.zzbrn.zzz(zzdj(i));
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (!zzuj.zze(((Integer) entry.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzww.zztf();
                }
                zzte zzcn = zzsw.zzcn(zzvg.zza(zzz, entry.getKey(), entry.getValue()));
                try {
                    zzvg.zza(zzcn.zzqc(), zzz, entry.getKey(), entry.getValue());
                    zzww.zza((Object) ub, i2, zzcn.zzqb());
                    it.remove();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    public final boolean zzac(T t) {
        int i = 0;
        int i2 = -1;
        int i3 = 0;
        while (true) {
            boolean z = true;
            if (i >= this.zzbrh) {
                return !this.zzbrc || this.zzbrm.zzo(t).isInitialized();
            } else {
                int i4;
                int i5;
                int i6 = this.zzbrg[i];
                int i7 = this.zzbqx[i6];
                int zzdl = zzdl(i6);
                if (this.zzbre) {
                    i4 = 0;
                } else {
                    i4 = this.zzbqx[i6 + 2];
                    i5 = i4 & 1048575;
                    i4 = 1 << (i4 >>> 20);
                    if (i5 != i2) {
                        i3 = zzbqw.getInt(t, (long) i5);
                        i2 = i5;
                    }
                }
                if (((268435456 & zzdl) != 0 ? 1 : null) != null && !zza((Object) t, i6, i3, i4)) {
                    return false;
                }
                i5 = (267386880 & zzdl) >>> 20;
                if (i5 != 9 && i5 != 17) {
                    zzwe zzwe;
                    if (i5 != 27) {
                        if (i5 == 60 || i5 == 68) {
                            if (zza((Object) t, i7, i6) && !zza((Object) t, zzdl, zzdi(i6))) {
                                return false;
                            }
                        } else if (i5 != 49) {
                            if (i5 != 50) {
                                continue;
                            } else {
                                Map zzv = this.zzbrn.zzv(zzxc.zzp(t, (long) (zzdl & 1048575)));
                                if (!zzv.isEmpty()) {
                                    if (this.zzbrn.zzz(zzdj(i6)).zzbqq.zztp() == zzxq.MESSAGE) {
                                        zzwe = null;
                                        for (Object next : zzv.values()) {
                                            if (zzwe == null) {
                                                zzwe = zzwb.zzso().zzk(next.getClass());
                                            }
                                            if (!zzwe.zzac(next)) {
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
                    List list = (List) zzxc.zzp(t, (long) (zzdl & 1048575));
                    if (!list.isEmpty()) {
                        zzwe = zzdi(i6);
                        for (zzdl = 0; zzdl < list.size(); zzdl++) {
                            if (!zzwe.zzac(list.get(zzdl))) {
                                z = false;
                                break;
                            }
                        }
                    }
                    if (!z) {
                        return false;
                    }
                } else if (zza((Object) t, i6, i3, i4) && !zza((Object) t, zzdl, zzdi(i6))) {
                    return false;
                }
                i++;
            }
        }
    }

    private static boolean zza(Object obj, int i, zzwe zzwe) {
        return zzwe.zzac(zzxc.zzp(obj, (long) (i & 1048575)));
    }

    private static void zza(int i, Object obj, zzxr zzxr) throws IOException {
        if (obj instanceof String) {
            zzxr.zzb(i, (String) obj);
        } else {
            zzxr.zza(i, (zzsw) obj);
        }
    }

    private final int zzdl(int i) {
        return this.zzbqx[i + 1];
    }

    private final int zzdm(int i) {
        return this.zzbqx[i + 2];
    }

    private static <T> double zzf(T t, long j) {
        return ((Double) zzxc.zzp(t, j)).doubleValue();
    }

    private static <T> float zzg(T t, long j) {
        return ((Float) zzxc.zzp(t, j)).floatValue();
    }

    private static <T> int zzh(T t, long j) {
        return ((Integer) zzxc.zzp(t, j)).intValue();
    }

    private static <T> long zzi(T t, long j) {
        return ((Long) zzxc.zzp(t, j)).longValue();
    }

    private static <T> boolean zzj(T t, long j) {
        return ((Boolean) zzxc.zzp(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza((Object) t, i) == zza((Object) t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        if (this.zzbre) {
            return zza((Object) t, i);
        }
        return (i2 & i3) != 0;
    }

    private final boolean zza(T t, int i) {
        if (this.zzbre) {
            i = zzdl(i);
            long j = (long) (i & 1048575);
            switch ((i & 267386880) >>> 20) {
                case 0:
                    return zzxc.zzo(t, j) != 0.0d;
                case 1:
                    return zzxc.zzn(t, j) != 0.0f;
                case 2:
                    return zzxc.zzl(t, j) != 0;
                case 3:
                    return zzxc.zzl(t, j) != 0;
                case 4:
                    return zzxc.zzk(t, j) != 0;
                case 5:
                    return zzxc.zzl(t, j) != 0;
                case 6:
                    return zzxc.zzk(t, j) != 0;
                case 7:
                    return zzxc.zzm(t, j);
                case 8:
                    Object zzp = zzxc.zzp(t, j);
                    if (zzp instanceof String) {
                        return !((String) zzp).isEmpty();
                    } else {
                        if (zzp instanceof zzsw) {
                            return !zzsw.zzbkl.equals(zzp);
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                case 9:
                    return zzxc.zzp(t, j) != null;
                case 10:
                    return !zzsw.zzbkl.equals(zzxc.zzp(t, j));
                case 11:
                    return zzxc.zzk(t, j) != 0;
                case 12:
                    return zzxc.zzk(t, j) != 0;
                case 13:
                    return zzxc.zzk(t, j) != 0;
                case 14:
                    return zzxc.zzl(t, j) != 0;
                case 15:
                    return zzxc.zzk(t, j) != 0;
                case 16:
                    return zzxc.zzl(t, j) != 0;
                case 17:
                    return zzxc.zzp(t, j) != null;
                default:
                    throw new IllegalArgumentException();
            }
        }
        i = zzdm(i);
        return (zzxc.zzk(t, (long) (i & 1048575)) & (1 << (i >>> 20))) != 0;
    }

    private final void zzb(T t, int i) {
        if (!this.zzbre) {
            i = zzdm(i);
            long j = (long) (i & 1048575);
            zzxc.zza((Object) t, j, zzxc.zzk(t, j) | (1 << (i >>> 20)));
        }
    }

    private final boolean zza(T t, int i, int i2) {
        return zzxc.zzk(t, (long) (zzdm(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzxc.zza((Object) t, (long) (zzdm(i2) & 1048575), i);
    }

    private final int zzdn(int i) {
        return (i < this.zzbqz || i > this.zzbra) ? -1 : zzv(i, 0);
    }

    private final int zzu(int i, int i2) {
        return (i < this.zzbqz || i > this.zzbra) ? -1 : zzv(i, i2);
    }

    private final int zzv(int i, int i2) {
        int length = (this.zzbqx.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzbqx[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }
}
