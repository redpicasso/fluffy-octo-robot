package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class zzfx<MessageType extends zzfx<MessageType, BuilderType>, BuilderType extends zzga<MessageType, BuilderType>> implements zzjc {
    private static boolean zzvn = false;
    protected int zzvm = 0;

    public final zzgf zzft() {
        try {
            zzgn zzr = zzgf.zzr(zzik());
            zzb(zzr.zzgh());
            return zzr.zzgg();
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
            byte[] bArr = new byte[zzik()];
            zzha zzc = zzha.zzc(bArr);
            zzb(zzc);
            zzc.zzhj();
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

    int zzfu() {
        throw new UnsupportedOperationException();
    }

    void zzl(int i) {
        throw new UnsupportedOperationException();
    }

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzht.checkNotNull(iterable);
        String str = " is null.";
        String str2 = "Element at index ";
        int size;
        StringBuilder stringBuilder;
        String stringBuilder2;
        int size2;
        if (iterable instanceof zzij) {
            List zzjd = ((zzij) iterable).zzjd();
            zzij zzij = (zzij) list;
            int size3 = list.size();
            for (Object next : zzjd) {
                if (next == null) {
                    size = zzij.size() - size3;
                    stringBuilder = new StringBuilder(37);
                    stringBuilder.append(str2);
                    stringBuilder.append(size);
                    stringBuilder.append(str);
                    stringBuilder2 = stringBuilder.toString();
                    for (size2 = zzij.size() - 1; size2 >= size3; size2--) {
                        zzij.remove(size2);
                    }
                    throw new NullPointerException(stringBuilder2);
                } else if (next instanceof zzgf) {
                    zzij.zzc((zzgf) next);
                } else {
                    zzij.add((String) next);
                }
            }
        } else if (iterable instanceof zzjl) {
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
