package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class zzdf<MessageType extends zzdf<MessageType, BuilderType>, BuilderType extends zzdh<MessageType, BuilderType>> implements zzgi {
    private static boolean zzacu = false;
    protected int zzact = 0;

    public final zzdp zzrs() {
        try {
            zzdx zzas = zzdp.zzas(zzuk());
            zzb(zzas.zzsf());
            return zzas.zzse();
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
            byte[] bArr = new byte[zzuk()];
            zzee zzf = zzee.zzf(bArr);
            zzb(zzf);
            zzf.zzth();
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

    int zzrt() {
        throw new UnsupportedOperationException();
    }

    void zzam(int i) {
        throw new UnsupportedOperationException();
    }

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzez.checkNotNull(iterable);
        String str = " is null.";
        String str2 = "Element at index ";
        int size;
        StringBuilder stringBuilder;
        String stringBuilder2;
        int size2;
        if (iterable instanceof zzfp) {
            List zzvf = ((zzfp) iterable).zzvf();
            zzfp zzfp = (zzfp) list;
            int size3 = list.size();
            for (Object next : zzvf) {
                if (next == null) {
                    size = zzfp.size() - size3;
                    stringBuilder = new StringBuilder(37);
                    stringBuilder.append(str2);
                    stringBuilder.append(size);
                    stringBuilder.append(str);
                    stringBuilder2 = stringBuilder.toString();
                    for (size2 = zzfp.size() - 1; size2 >= size3; size2--) {
                        zzfp.remove(size2);
                    }
                    throw new NullPointerException(stringBuilder2);
                } else if (next instanceof zzdp) {
                    zzfp.zzc((zzdp) next);
                } else {
                    zzfp.add((String) next);
                }
            }
        } else if (iterable instanceof zzgu) {
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
