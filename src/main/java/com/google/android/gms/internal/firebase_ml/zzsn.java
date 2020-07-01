package com.google.android.gms.internal.firebase_ml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class zzsn<MessageType extends zzsn<MessageType, BuilderType>, BuilderType extends zzso<MessageType, BuilderType>> implements zzvo {
    private static boolean zzbkb = false;
    protected int zzbka = 0;

    public final zzsw zzpp() {
        try {
            zzte zzcn = zzsw.zzcn(zzqy());
            zzb(zzcn.zzqc());
            return zzcn.zzqb();
        } catch (Throwable e) {
            String str = "ByteString";
            String name = getClass().getName();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(name).length() + 62) + str.length());
            stringBuilder.append("Serializing ");
            stringBuilder.append(name);
            stringBuilder.append(" to a ");
            stringBuilder.append(str);
            stringBuilder.append(" threw an IOException (should never happen).");
            throw new RuntimeException(stringBuilder.toString(), e);
        }
    }

    public final byte[] toByteArray() {
        try {
            byte[] bArr = new byte[zzqy()];
            zztl zzg = zztl.zzg(bArr);
            zzb(zzg);
            zzg.zzqf();
            return bArr;
        } catch (Throwable e) {
            String str = "byte array";
            String name = getClass().getName();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(name).length() + 62) + str.length());
            stringBuilder.append("Serializing ");
            stringBuilder.append(name);
            stringBuilder.append(" to a ");
            stringBuilder.append(str);
            stringBuilder.append(" threw an IOException (should never happen).");
            throw new RuntimeException(stringBuilder.toString(), e);
        }
    }

    int zzpq() {
        throw new UnsupportedOperationException();
    }

    void zzch(int i) {
        throw new UnsupportedOperationException();
    }

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzug.checkNotNull(iterable);
        String str = " is null.";
        String str2 = "Element at index ";
        int size;
        StringBuilder stringBuilder;
        String stringBuilder2;
        int size2;
        if (iterable instanceof zzux) {
            List zzrv = ((zzux) iterable).zzrv();
            zzux zzux = (zzux) list;
            int size3 = list.size();
            for (Object next : zzrv) {
                if (next == null) {
                    size = zzux.size() - size3;
                    stringBuilder = new StringBuilder(37);
                    stringBuilder.append(str2);
                    stringBuilder.append(size);
                    stringBuilder.append(str);
                    stringBuilder2 = stringBuilder.toString();
                    for (size2 = zzux.size() - 1; size2 >= size3; size2--) {
                        zzux.remove(size2);
                    }
                    throw new NullPointerException(stringBuilder2);
                } else if (next instanceof zzsw) {
                    zzux.zzc((zzsw) next);
                } else {
                    zzux.add((String) next);
                }
            }
        } else if (iterable instanceof zzwa) {
            list.addAll((Collection) iterable);
        } else {
            if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
                ((ArrayList) list).ensureCapacity(list.size() + ((Collection) iterable).size());
            }
            int size4 = list.size();
            for (Object next2 : iterable) {
                if (next2 == null) {
                    size = list.size() - size4;
                    stringBuilder = new StringBuilder(37);
                    stringBuilder.append(str2);
                    stringBuilder.append(size);
                    stringBuilder.append(str);
                    stringBuilder2 = stringBuilder.toString();
                    for (size2 = list.size() - 1; size2 >= size4; size2--) {
                        list.remove(size2);
                    }
                    throw new NullPointerException(stringBuilder2);
                }
                list.add(next2);
            }
        }
    }
}
