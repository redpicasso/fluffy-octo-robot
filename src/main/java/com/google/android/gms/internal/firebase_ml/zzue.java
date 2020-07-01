package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzue<MessageType extends zzue<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzsn<MessageType, BuilderType> {
    private static Map<Object, zzue<?, ?>> zzboj = new ConcurrentHashMap();
    protected zzwx zzboh = zzwx.zztg();
    private int zzboi = -1;

    public enum zzf {
        public static final int zzboo = 1;
        public static final int zzbop = 2;
        public static final int zzboq = 3;
        public static final int zzbor = 4;
        public static final int zzbos = 5;
        public static final int zzbot = 6;
        public static final int zzbou = 7;
        private static final /* synthetic */ int[] zzbov = new int[]{zzboo, zzbop, zzboq, zzbor, zzbos, zzbot, zzbou};
        public static final int zzbow = 1;
        public static final int zzbox = 2;
        private static final /* synthetic */ int[] zzboy = new int[]{zzbow, zzbox};
        public static final int zzboz = 1;
        public static final int zzbpa = 2;
        private static final /* synthetic */ int[] zzbpb = new int[]{zzboz, zzbpa};

        public static int[] values$50KLMJ33DTMIUPRFDTJMOP9FE1P6UT3FC9QMCBQ7CLN6ASJ1EHIM8JB5EDPM2PR59HKN8P949LIN8Q3FCHA6UIBEEPNMMP9R0() {
            return (int[]) zzbov.clone();
        }
    }

    public static class zze<ContainingType extends zzvo, Type> extends zztp<ContainingType, Type> {
    }

    public static class zzb<T extends zzue<T, ?>> extends zzsp<T> {
        private final T zzbok;

        public zzb(T t) {
            this.zzbok = t;
        }
    }

    public static abstract class zza<MessageType extends zzue<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzso<MessageType, BuilderType> {
        private final MessageType zzbok;
        protected MessageType zzbol;
        protected boolean zzbom = false;

        protected zza(MessageType messageType) {
            this.zzbok = messageType;
            this.zzbol = (zzue) messageType.zza(zzf.zzbor, null, null);
        }

        protected void zzrf() {
            if (this.zzbom) {
                zzue zzue = (zzue) this.zzbol.zza(zzf.zzbor, null, null);
                zza(zzue, this.zzbol);
                this.zzbol = zzue;
                this.zzbom = false;
            }
        }

        public final boolean isInitialized() {
            return zzue.zza(this.zzbol, false);
        }

        /* renamed from: zzrg */
        public MessageType zzri() {
            if (this.zzbom) {
                return this.zzbol;
            }
            this.zzbol.zzpt();
            this.zzbom = true;
            return this.zzbol;
        }

        /* renamed from: zzrh */
        public final MessageType zzrj() {
            zzue zzue = (zzue) zzri();
            if (zzue.isInitialized()) {
                return zzue;
            }
            throw new zzwv(zzue);
        }

        public final BuilderType zza(MessageType messageType) {
            zzrf();
            zza(this.zzbol, messageType);
            return this;
        }

        private static void zza(MessageType messageType, MessageType messageType2) {
            zzwb.zzso().zzad(messageType).zzg(messageType, messageType2);
        }

        public final /* synthetic */ zzso zzpr() {
            return (zza) clone();
        }

        public final /* synthetic */ zzvo zzre() {
            return this.zzbok;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zza = (zza) this.zzbok.zza(zzf.zzbos, null, null);
            zza.zza((zzue) zzri());
            return zza;
        }
    }

    public static abstract class zzc<MessageType extends zzd<MessageType, BuilderType>, BuilderType extends zzc<MessageType, BuilderType>> extends zza<MessageType, BuilderType> implements zzvq {
        protected zzc(MessageType messageType) {
            super(messageType);
        }

        protected final void zzrf() {
            if (this.zzbom) {
                super.zzrf();
                ((zzd) this.zzbol).zzbon = (zztw) ((zzd) this.zzbol).zzbon.clone();
            }
        }

        public /* synthetic */ zzue zzrg() {
            return (zzd) zzri();
        }

        public /* synthetic */ zzvo zzri() {
            if (this.zzbom) {
                return (zzd) this.zzbol;
            }
            ((zzd) this.zzbol).zzbon.zzpt();
            return (zzd) super.zzri();
        }
    }

    public static abstract class zzd<MessageType extends zzd<MessageType, BuilderType>, BuilderType extends zzc<MessageType, BuilderType>> extends zzue<MessageType, BuilderType> implements zzvq {
        protected zztw<Object> zzbon = zztw.zzqp();

        final zztw<Object> zzrk() {
            if (this.zzbon.isImmutable()) {
                this.zzbon = (zztw) this.zzbon.clone();
            }
            return this.zzbon;
        }
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    public String toString() {
        return zzvr.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zzbka != 0) {
            return this.zzbka;
        }
        this.zzbka = zzwb.zzso().zzad(this).hashCode(this);
        return this.zzbka;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (((zzue) zza(zzf.zzbot, null, null)).getClass().isInstance(obj)) {
            return zzwb.zzso().zzad(this).equals(this, (zzue) obj);
        }
        return false;
    }

    protected final void zzpt() {
        zzwb.zzso().zzad(this).zzq(this);
    }

    protected final <MessageType extends zzue<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> BuilderType zzqx() {
        return (zza) zza(zzf.zzbos, null, null);
    }

    public final boolean isInitialized() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    final int zzpq() {
        return this.zzboi;
    }

    final void zzch(int i) {
        this.zzboi = i;
    }

    public final void zzb(zztl zztl) throws IOException {
        zzwb.zzso().zzk(getClass()).zza(this, zztn.zza(zztl));
    }

    public final int zzqy() {
        if (this.zzboi == -1) {
            this.zzboi = zzwb.zzso().zzad(this).zzaa(this);
        }
        return this.zzboi;
    }

    static <T extends zzue<?, ?>> T zzi(Class<T> cls) {
        T t = (zzue) zzboj.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzue) zzboj.get(cls);
            } catch (Throwable e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (t == null) {
            t = (zzue) ((zzue) zzxc.zzm(cls)).zza(zzf.zzbot, null, null);
            if (t != null) {
                zzboj.put(cls, t);
            } else {
                throw new IllegalStateException();
            }
        }
        return t;
    }

    protected static <T extends zzue<?, ?>> void zza(Class<T> cls, T t) {
        zzboj.put(cls, t);
    }

    protected static Object zza(zzvo zzvo, String str, Object[] objArr) {
        return new zzwd(zzvo, str, objArr);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        Throwable e;
        try {
            return method.invoke(obj, objArr);
        } catch (Throwable e2) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e2);
        } catch (InvocationTargetException e3) {
            e2 = e3.getCause();
            if (e2 instanceof RuntimeException) {
                throw ((RuntimeException) e2);
            } else if (e2 instanceof Error) {
                throw ((Error) e2);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", e2);
            }
        }
    }

    protected static final <T extends zzue<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zzf.zzboo, null, null)).byteValue();
        if (byteValue == (byte) 1) {
            return true;
        }
        if (byteValue == (byte) 0) {
            return false;
        }
        boolean zzac = zzwb.zzso().zzad(t).zzac(t);
        if (z) {
            t.zza(zzf.zzbop, zzac ? t : null, null);
        }
        return zzac;
    }

    protected static zzul zzqz() {
        return zzuf.zzrl();
    }

    protected static zzul zza(zzul zzul) {
        int size = zzul.size();
        return zzul.zzdg(size == 0 ? 10 : size << 1);
    }

    protected static zzuk zzra() {
        return zzuc.zzqv();
    }

    protected static <E> zzun<E> zzrb() {
        return zzwc.zzsp();
    }

    protected static <E> zzun<E> zza(zzun<E> zzun) {
        int size = zzun.size();
        return zzun.zzck(size == 0 ? 10 : size << 1);
    }

    private static <T extends zzue<T, ?>> T zza(T t, byte[] bArr, int i, int i2, zztr zztr) throws zzuo {
        zzue zzue = (zzue) t.zza(zzf.zzbor, null, null);
        try {
            zzwb.zzso().zzad(zzue).zza(zzue, bArr, 0, i2, new zzst(zztr));
            zzue.zzpt();
            if (zzue.zzbka == 0) {
                return zzue;
            }
            throw new RuntimeException();
        } catch (IOException e) {
            if (e.getCause() instanceof zzuo) {
                throw ((zzuo) e.getCause());
            }
            throw new zzuo(e.getMessage()).zzg(zzue);
        } catch (IndexOutOfBoundsException unused) {
            throw zzuo.zzrm().zzg(zzue);
        }
    }

    protected static <T extends zzue<T, ?>> T zza(T t, byte[] bArr, zztr zztr) throws zzuo {
        t = zza(t, bArr, 0, bArr.length, zztr);
        if (t == null || t.isInitialized()) {
            return t;
        }
        throw new zzuo(new zzwv(t).getMessage()).zzg(t);
    }

    public final /* synthetic */ zzvp zzrc() {
        zza zza = (zza) zza(zzf.zzbos, null, null);
        zza.zza(this);
        return zza;
    }

    public final /* synthetic */ zzvp zzrd() {
        return (zza) zza(zzf.zzbos, null, null);
    }

    public final /* synthetic */ zzvo zzre() {
        return (zzue) zza(zzf.zzbot, null, null);
    }
}
