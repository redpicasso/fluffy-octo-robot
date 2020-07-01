package com.google.android.gms.internal.firebase_ml;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public final class zzqq implements Closeable, Flushable {
    private static final String[] zzbav = new String[128];
    private static final String[] zzbaw;
    private final Writer out;
    private String separator;
    private boolean zzazy;
    private int[] zzbag = new int[32];
    private int zzbah = 0;
    private String zzbax;
    private String zzbay;
    private boolean zzbaz;

    public zzqq(Writer writer) {
        zzbs(6);
        this.separator = ":";
        this.zzbaz = true;
        this.out = writer;
    }

    public final void setIndent(String str) {
        if (str.length() == 0) {
            this.zzbax = null;
            this.separator = ":";
            return;
        }
        this.zzbax = str;
        this.separator = ": ";
    }

    public final void setLenient(boolean z) {
        this.zzazy = true;
    }

    public final zzqq zznx() throws IOException {
        zzob();
        return zza(1, "[");
    }

    public final zzqq zzny() throws IOException {
        return zzc(1, 2, "]");
    }

    public final zzqq zznz() throws IOException {
        zzob();
        return zza(3, "{");
    }

    public final zzqq zzoa() throws IOException {
        return zzc(3, 5, "}");
    }

    private final zzqq zza(int i, String str) throws IOException {
        zzoe();
        zzbs(i);
        this.out.write(str);
        return this;
    }

    private final zzqq zzc(int i, int i2, String str) throws IOException {
        int peek = peek();
        if (peek != i2 && peek != i) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.zzbay == null) {
            this.zzbah--;
            if (peek == i2) {
                zzod();
            }
            this.out.write(str);
            return this;
        } else {
            StringBuilder stringBuilder = new StringBuilder("Dangling name: ");
            stringBuilder.append(this.zzbay);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    private final void zzbs(int i) {
        int i2 = this.zzbah;
        Object obj = this.zzbag;
        if (i2 == obj.length) {
            Object obj2 = new int[(i2 << 1)];
            System.arraycopy(obj, 0, obj2, 0, i2);
            this.zzbag = obj2;
        }
        int[] iArr = this.zzbag;
        int i3 = this.zzbah;
        this.zzbah = i3 + 1;
        iArr[i3] = i;
    }

    private final int peek() {
        int i = this.zzbah;
        if (i != 0) {
            return this.zzbag[i - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    private final void zzbu(int i) {
        this.zzbag[this.zzbah - 1] = i;
    }

    public final zzqq zzcj(String str) throws IOException {
        if (str == null) {
            throw new NullPointerException("name == null");
        } else if (this.zzbay != null) {
            throw new IllegalStateException();
        } else if (this.zzbah != 0) {
            this.zzbay = str;
            return this;
        } else {
            throw new IllegalStateException("JsonWriter is closed.");
        }
    }

    private final void zzob() throws IOException {
        if (this.zzbay != null) {
            int peek = peek();
            if (peek == 5) {
                this.out.write(44);
            } else if (peek != 3) {
                throw new IllegalStateException("Nesting problem.");
            }
            zzod();
            zzbu(4);
            zzcl(this.zzbay);
            this.zzbay = null;
        }
    }

    public final zzqq zzck(String str) throws IOException {
        if (str == null) {
            return zzoc();
        }
        zzob();
        zzoe();
        zzcl(str);
        return this;
    }

    public final zzqq zzoc() throws IOException {
        if (this.zzbay != null) {
            if (this.zzbaz) {
                zzob();
            } else {
                this.zzbay = null;
                return this;
            }
        }
        zzoe();
        this.out.write("null");
        return this;
    }

    public final zzqq zzag(boolean z) throws IOException {
        zzob();
        zzoe();
        this.out.write(z ? "true" : "false");
        return this;
    }

    public final zzqq zzb(double d) throws IOException {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            StringBuilder stringBuilder = new StringBuilder("Numeric values must be finite, but was ");
            stringBuilder.append(d);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        zzob();
        zzoe();
        this.out.append(Double.toString(d));
        return this;
    }

    public final zzqq zzo(long j) throws IOException {
        zzob();
        zzoe();
        this.out.write(Long.toString(j));
        return this;
    }

    public final zzqq zza(Number number) throws IOException {
        if (number == null) {
            return zzoc();
        }
        zzob();
        CharSequence obj = number.toString();
        if (this.zzazy || !(obj.equals("-Infinity") || obj.equals("Infinity") || obj.equals("NaN"))) {
            zzoe();
            this.out.append(obj);
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder("Numeric values must be finite, but was ");
        stringBuilder.append(number);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final void flush() throws IOException {
        if (this.zzbah != 0) {
            this.out.flush();
            return;
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    public final void close() throws IOException {
        this.out.close();
        int i = this.zzbah;
        if (i > 1 || (i == 1 && this.zzbag[i - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.zzbah = 0;
    }

    private final void zzcl(String str) throws IOException {
        String[] strArr = zzbav;
        String str2 = "\"";
        this.out.write(str2);
        int length = str.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            String str3;
            char charAt = str.charAt(i2);
            if (charAt < 128) {
                str3 = strArr[charAt];
                if (str3 == null) {
                }
            } else if (charAt == 8232) {
                str3 = "\\u2028";
            } else if (charAt == 8233) {
                str3 = "\\u2029";
            } else {
            }
            if (i < i2) {
                this.out.write(str, i, i2 - i);
            }
            this.out.write(str3);
            i = i2 + 1;
        }
        if (i < length) {
            this.out.write(str, i, length - i);
        }
        this.out.write(str2);
    }

    private final void zzod() throws IOException {
        if (this.zzbax != null) {
            this.out.write(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
            int i = this.zzbah;
            for (int i2 = 1; i2 < i; i2++) {
                this.out.write(this.zzbax);
            }
        }
    }

    private final void zzoe() throws IOException {
        int peek = peek();
        if (peek == 1) {
            zzbu(2);
            zzod();
        } else if (peek == 2) {
            this.out.append(',');
            zzod();
        } else if (peek != 4) {
            if (peek != 6) {
                if (peek != 7) {
                    throw new IllegalStateException("Nesting problem.");
                } else if (!this.zzazy) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
            }
            zzbu(7);
        } else {
            this.out.append(this.separator);
            zzbu(5);
        }
    }

    static {
        for (int i = 0; i <= 31; i++) {
            zzbav[i] = String.format("\\u%04x", new Object[]{Integer.valueOf(i)});
        }
        Object obj = zzbav;
        obj[34] = "\\\"";
        obj[92] = "\\\\";
        obj[9] = "\\t";
        obj[8] = "\\b";
        obj[10] = "\\n";
        obj[13] = "\\r";
        obj[12] = "\\f";
        String[] strArr = (String[]) obj.clone();
        zzbaw = strArr;
        strArr[60] = "\\u003c";
        strArr = zzbaw;
        strArr[62] = "\\u003e";
        strArr[38] = "\\u0026";
        strArr[61] = "\\u003d";
        strArr[39] = "\\u0027";
    }
}
