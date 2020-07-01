package com.google.cloud.datastore.core.number;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class NumberParts {
    private static final int DOUBLE_EXPONENT_BIAS = 1023;
    private static final int DOUBLE_MIN_EXPONENT = -1022;
    private static final int DOUBLE_SIGNIFICAND_BITS = 52;
    private static final long DOUBLE_SIGN_BIT = Long.MIN_VALUE;
    static final int NEGATIVE_INFINITE_EXPONENT = Integer.MIN_VALUE;
    static final int POSITIVE_INFINITE_EXPONENT = Integer.MAX_VALUE;
    static final int SIGNIFICAND_BITS = 64;
    private final int exponent;
    private final boolean negative;
    private final long significand;

    private static String doubleRepresentationError() {
        return null;
    }

    private NumberParts(boolean z, int i, long j) {
        this.negative = z;
        this.exponent = i;
        this.significand = j;
    }

    public boolean negative() {
        return this.negative;
    }

    public int exponent() {
        return this.exponent;
    }

    public long significand() {
        return this.significand;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NumberParts)) {
            return false;
        }
        NumberParts numberParts = (NumberParts) obj;
        if (!(this.negative == numberParts.negative && this.exponent == numberParts.exponent && this.significand == numberParts.significand)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int i = ((this.negative * 31) + this.exponent) * 31;
        long j = this.significand;
        return i + ((int) (j ^ (j >>> 32)));
    }

    public boolean isZero() {
        return exponent() == Integer.MIN_VALUE && significand() == 0;
    }

    public boolean isNaN() {
        return exponent() == Integer.MAX_VALUE && significand() != 0;
    }

    public boolean isInfinite() {
        return exponent() == Integer.MAX_VALUE && significand() == 0;
    }

    public static NumberParts create(boolean z, int i, long j) {
        if (i != Integer.MAX_VALUE || j == 0 || (z && j == 1)) {
            return new NumberParts(z, i, j);
        }
        throw new IllegalArgumentException("Invalid number parts: non-normalized NaN");
    }

    public static NumberParts fromLong(long j) {
        boolean z = false;
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i == 0) {
            return create(false, Integer.MIN_VALUE, 0);
        }
        if (i < 0) {
            j = -j;
            z = true;
        }
        int numberOfLeadingZeros = Long.numberOfLeadingZeros(j);
        i = 63 - numberOfLeadingZeros;
        return create(z, i, (j & (~(1 << i))) << (numberOfLeadingZeros + 1));
    }

    public static NumberParts fromDouble(double d) {
        long doubleToLongBits = Double.doubleToLongBits(d);
        boolean z = d < 0.0d;
        int i = ((int) ((doubleToLongBits >>> 52) & 2047)) - DOUBLE_EXPONENT_BIAS;
        doubleToLongBits &= 4503599627370495L;
        if (i < DOUBLE_MIN_EXPONENT) {
            if (doubleToLongBits == 0) {
                return create(false, Integer.MIN_VALUE, 0);
            }
            int numberOfLeadingZeros = Long.numberOfLeadingZeros(doubleToLongBits);
            doubleToLongBits = (doubleToLongBits & (~(1 << (63 - numberOfLeadingZeros)))) << (numberOfLeadingZeros + 1);
            i -= numberOfLeadingZeros - 12;
        } else if (i > DOUBLE_EXPONENT_BIAS) {
            NumberParts create;
            if (doubleToLongBits != 0) {
                create = create(true, Integer.MAX_VALUE, 1);
            } else if (z) {
                create = create(true, Integer.MAX_VALUE, 0);
            } else {
                create = create(false, Integer.MAX_VALUE, 0);
            }
            return create;
        } else {
            doubleToLongBits <<= 12;
        }
        return create(z, i, doubleToLongBits);
    }

    public NumberParts negate() {
        return (isZero() || isNaN()) ? this : create(negative() ^ 1, exponent(), significand());
    }

    public boolean representableAsDouble() {
        return doubleRepresentationError() == null;
    }

    public boolean representableAsLong() {
        return longRepresentationError() == null;
    }

    public double asDouble() {
        String doubleRepresentationError = doubleRepresentationError();
        if (doubleRepresentationError != null) {
            throw new IllegalArgumentException(doubleRepresentationError);
        } else if (isZero()) {
            return 0.0d;
        } else {
            if (isInfinite()) {
                return negative() ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            } else if (isNaN()) {
                return Double.NaN;
            } else {
                long exponent = (long) exponent();
                long significand = significand() >>> 12;
                long j = 0;
                if (exponent >= -1022) {
                    exponent += 1023;
                } else {
                    int exponent2 = -1022 - exponent();
                    significand = (significand >>> exponent2) | (1 << (52 - exponent2));
                    exponent = 0;
                }
                exponent = (exponent << 52) | significand;
                if (negative()) {
                    j = Long.MIN_VALUE;
                }
                return Double.longBitsToDouble(exponent | j);
            }
        }
    }

    public long asLong() {
        String longRepresentationError = longRepresentationError();
        if (longRepresentationError != null) {
            throw new IllegalArgumentException(longRepresentationError);
        } else if (isZero()) {
            return 0;
        } else {
            if (exponent() == 63) {
                return Long.MIN_VALUE;
            }
            long significand = (significand() >>> ((63 - exponent()) + 1)) ^ (1 << exponent());
            if (negative()) {
                significand = -significand;
            }
            return significand;
        }
    }

    private String longRepresentationError() {
        if (isZero()) {
            return null;
        }
        String str = "Invalid encoded long ";
        StringBuilder stringBuilder;
        if (isInfinite()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(this);
            stringBuilder.append(": Infinity is not a long");
            return stringBuilder.toString();
        } else if (isNaN()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(this);
            stringBuilder.append(": NaN is not a long");
            return stringBuilder.toString();
        } else if (exponent() == 63) {
            if (significand() == 0 && negative()) {
                return null;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(this);
            stringBuilder.append(": overflow");
            return stringBuilder.toString();
        } else if (exponent() < 0 || exponent() > 63) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(this);
            stringBuilder.append(": exponent ");
            stringBuilder.append(exponent());
            stringBuilder.append(" too large");
            return stringBuilder.toString();
        } else {
            if (exponent() >= 64 - Long.numberOfTrailingZeros(significand())) {
                return null;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(this);
            stringBuilder.append(": contains fractional part");
            return stringBuilder.toString();
        }
    }
}
