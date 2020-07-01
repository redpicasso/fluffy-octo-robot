package com.drew.lang;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import java.io.Serializable;

public class Rational extends Number implements Comparable<Rational>, Serializable {
    private static final long serialVersionUID = 510688928138848770L;
    private final long _denominator;
    private final long _numerator;

    public Rational(long j, long j2) {
        this._numerator = j;
        this._denominator = j2;
    }

    public double doubleValue() {
        long j = this._numerator;
        return j == 0 ? 0.0d : ((double) j) / ((double) this._denominator);
    }

    public float floatValue() {
        long j = this._numerator;
        return j == 0 ? 0.0f : ((float) j) / ((float) this._denominator);
    }

    public final byte byteValue() {
        return (byte) ((int) doubleValue());
    }

    public final int intValue() {
        return (int) doubleValue();
    }

    public final long longValue() {
        return (long) doubleValue();
    }

    public final short shortValue() {
        return (short) ((int) doubleValue());
    }

    public final long getDenominator() {
        return this._denominator;
    }

    public final long getNumerator() {
        return this._numerator;
    }

    @NotNull
    public Rational getReciprocal() {
        return new Rational(this._denominator, this._numerator);
    }

    public boolean isInteger() {
        long j = this._denominator;
        return j == 1 || ((j != 0 && this._numerator % j == 0) || (this._denominator == 0 && this._numerator == 0));
    }

    public boolean isZero() {
        return this._numerator == 0 || this._denominator == 0;
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._numerator);
        stringBuilder.append("/");
        stringBuilder.append(this._denominator);
        return stringBuilder.toString();
    }

    @NotNull
    public String toSimpleString(boolean z) {
        if (this._denominator == 0 && this._numerator != 0) {
            return toString();
        }
        if (isInteger()) {
            return Integer.toString(intValue());
        }
        long j = this._numerator;
        if (j != 1) {
            long j2 = this._denominator;
            if (j2 % j == 0) {
                return new Rational(1, j2 / j).toSimpleString(z);
            }
        }
        Rational simplifiedInstance = getSimplifiedInstance();
        if (z) {
            String d = Double.toString(simplifiedInstance.doubleValue());
            if (d.length() < 5) {
                return d;
            }
        }
        return simplifiedInstance.toString();
    }

    public int compareTo(@NotNull Rational rational) {
        return Double.compare(doubleValue(), rational.doubleValue());
    }

    public boolean equals(Rational rational) {
        return rational.doubleValue() == doubleValue();
    }

    public boolean equalsExact(Rational rational) {
        return getDenominator() == rational.getDenominator() && getNumerator() == rational.getNumerator();
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == null || !(obj instanceof Rational)) {
            return false;
        }
        if (doubleValue() == ((Rational) obj).doubleValue()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((int) this._denominator) * 23) + ((int) this._numerator);
    }

    @NotNull
    public Rational getSimplifiedInstance() {
        long GCD = GCD(this._numerator, this._denominator);
        return new Rational(this._numerator / GCD, this._denominator / GCD);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x001e  */
    private static long GCD(long r4, long r6) {
        /*
        r0 = 0;
        r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
        if (r2 >= 0) goto L_0x0007;
    L_0x0006:
        r4 = -r4;
    L_0x0007:
        r2 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1));
        if (r2 >= 0) goto L_0x000c;
    L_0x000b:
        r6 = -r6;
    L_0x000c:
        r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
        if (r2 == 0) goto L_0x001c;
    L_0x0010:
        r3 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1));
        if (r3 == 0) goto L_0x001c;
    L_0x0014:
        r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r2 <= 0) goto L_0x001a;
    L_0x0018:
        r4 = r4 % r6;
        goto L_0x000c;
    L_0x001a:
        r6 = r6 % r4;
        goto L_0x000c;
    L_0x001c:
        if (r2 != 0) goto L_0x001f;
    L_0x001e:
        r4 = r6;
    L_0x001f:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.drew.lang.Rational.GCD(long, long):long");
    }
}
