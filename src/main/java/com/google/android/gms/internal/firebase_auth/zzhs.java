package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class zzhs<MessageType extends zzhs<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzfx<MessageType, BuilderType> {
    private static Map<Object, zzhs<?, ?>> zzaal = new ConcurrentHashMap();
    protected zzkn zzaaj = zzkn.zzko();
    private int zzaak = -1;

    public enum zzd {
        public static final int zzaan = 1;
        public static final int zzaao = 2;
        public static final int zzaap = 3;
        public static final int zzaaq = 4;
        public static final int zzaar = 5;
        public static final int zzaas = 6;
        public static final int zzaat = 7;
        private static final /* synthetic */ int[] zzaau = new int[]{zzaan, zzaao, zzaap, zzaaq, zzaar, zzaas, zzaat};
        public static final int zzaav = 1;
        public static final int zzaaw = 2;
        private static final /* synthetic */ int[] zzaax = new int[]{zzaav, zzaaw};
        public static final int zzaay = 1;
        public static final int zzaaz = 2;
        private static final /* synthetic */ int[] zzaba = new int[]{zzaay, zzaaz};

        public static int[] zzip() {
            return (int[]) zzaau.clone();
        }
    }

    public static class zze<ContainingType extends zzjc, Type> extends zzhe<ContainingType, Type> {
    }

    public static class zzc<T extends zzhs<T, ?>> extends zzgc<T> {
        private final T zzaag;

        public zzc(T t) {
            this.zzaag = t;
        }

        public final /* synthetic */ Object zza(zzgr zzgr, zzhf zzhf) throws zzic {
            return zzhs.zza(this.zzaag, zzgr, zzhf);
        }
    }

    public static abstract class zza<MessageType extends zzhs<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzga<MessageType, BuilderType> {
        private final MessageType zzaag;
        protected MessageType zzaah;
        private boolean zzaai = false;

        protected zza(MessageType messageType) {
            this.zzaag = messageType;
            this.zzaah = (zzhs) messageType.zza(zzd.zzaaq, null, null);
        }

        protected final void zzid() {
            if (this.zzaai) {
                zzhs zzhs = (zzhs) this.zzaah.zza(zzd.zzaaq, null, null);
                zza(zzhs, this.zzaah);
                this.zzaah = zzhs;
                this.zzaai = false;
            }
        }

        public final boolean isInitialized() {
            return zzhs.zza(this.zzaah, false);
        }

        /* renamed from: zzie */
        public MessageType zzig() {
            if (this.zzaai) {
                return this.zzaah;
            }
            this.zzaah.zzfy();
            this.zzaai = true;
            return this.zzaah;
        }

        /* renamed from: zzif */
        public final MessageType zzih() {
            zzhs zzhs = (zzhs) zzig();
            if (zzhs.isInitialized()) {
                return zzhs;
            }
            throw new zzkl(zzhs);
        }

        public final BuilderType zza(MessageType messageType) {
            zzid();
            zza(this.zzaah, messageType);
            return this;
        }

        private static void zza(MessageType messageType, MessageType messageType2) {
            zzjo.zzjv().zzr(messageType).zzd(messageType, messageType2);
        }

        public final /* synthetic */ zzga zzfw() {
            return (zza) clone();
        }

        public final /* synthetic */ zzjc zzii() {
            return this.zzaag;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zza = (zza) this.zzaag.zza(zzd.zzaar, null, null);
            zza.zza((zzhs) zzig());
            return zza;
        }
    }

    public static abstract class zzb<MessageType extends zzb<MessageType, BuilderType>, BuilderType> extends zzhs<MessageType, BuilderType> implements zzje {
        protected zzhi<Object> zzaam = zzhi.zzhs();
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    public String toString() {
        return zzjd.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zzvm != 0) {
            return this.zzvm;
        }
        this.zzvm = zzjo.zzjv().zzr(this).hashCode(this);
        return this.zzvm;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (((zzhs) zza(zzd.zzaas, null, null)).getClass().isInstance(obj)) {
            return zzjo.zzjv().zzr(this).equals(this, (zzhs) obj);
        }
        return false;
    }

    protected final void zzfy() {
        zzjo.zzjv().zzr(this).zzf(this);
    }

    protected final <MessageType extends zzhs<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> BuilderType zzij() {
        return (zza) zza(zzd.zzaar, null, null);
    }

    public final boolean isInitialized() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    final int zzfu() {
        return this.zzaak;
    }

    final void zzl(int i) {
        this.zzaak = i;
    }

    public final void zzb(zzha zzha) throws IOException {
        zzjo.zzjv().zzf(getClass()).zza(this, zzhc.zza(zzha));
    }

    public final int zzik() {
        if (this.zzaak == -1) {
            this.zzaak = zzjo.zzjv().zzr(this).zzq(this);
        }
        return this.zzaak;
    }

    static <T extends zzhs<?, ?>> T zzd(Class<T> cls) {
        T t = (zzhs) zzaal.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzhs) zzaal.get(cls);
            } catch (Throwable e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (t == null) {
            t = (zzhs) ((zzhs) zzkq.zzh(cls)).zza(zzd.zzaas, null, null);
            if (t != null) {
                zzaal.put(cls, t);
            } else {
                throw new IllegalStateException();
            }
        }
        return t;
    }

    protected static <T extends zzhs<?, ?>> void zza(Class<T> cls, T t) {
        zzaal.put(cls, t);
    }

    protected static Object zza(zzjc zzjc, String str, Object[] objArr) {
        return new zzjq(zzjc, str, objArr);
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

    protected static final <T extends zzhs<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zzd.zzaan, null, null)).byteValue();
        if (byteValue == (byte) 1) {
            return true;
        }
        if (byteValue == (byte) 0) {
            return false;
        }
        boolean zzp = zzjo.zzjv().zzr(t).zzp(t);
        if (z) {
            t.zza(zzd.zzaao, zzp ? t : null, null);
        }
        return zzp;
    }

    protected static zzhx zzil() {
        return zzhu.zziq();
    }

    protected static <E> zzhz<E> zzim() {
        return zzjn.zzju();
    }

    static <T extends zzhs<T, ?>> T zza(T t, zzgr zzgr, zzhf zzhf) throws zzic {
        zzhs zzhs = (zzhs) t.zza(zzd.zzaaq, null, null);
        try {
            zzjo.zzjv().zzr(zzhs).zza(zzhs, zzgy.zza(zzgr), zzhf);
            zzhs.zzfy();
            return zzhs;
        } catch (IOException e) {
            if (e.getCause() instanceof zzic) {
                throw ((zzic) e.getCause());
            }
            throw new zzic(e.getMessage()).zzh(zzhs);
        } catch (RuntimeException e2) {
            if (e2.getCause() instanceof zzic) {
                throw ((zzic) e2.getCause());
            }
            throw e2;
        }
    }

    public final /* synthetic */ zzjb zzin() {
        zza zza = (zza) zza(zzd.zzaar, null, null);
        zza.zza(this);
        return zza;
    }

    public final /* synthetic */ zzjb zzio() {
        return (zza) zza(zzd.zzaar, null, null);
    }

    public final /* synthetic */ zzjc zzii() {
        return (zzhs) zza(zzd.zzaas, null, null);
    }
}
