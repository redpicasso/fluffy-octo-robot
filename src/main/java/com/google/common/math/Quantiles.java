package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@GwtIncompatible
@Beta
public final class Quantiles {

    public static final class Scale {
        private final int scale;

        private Scale(int i) {
            Preconditions.checkArgument(i > 0, "Quantile scale must be positive");
            this.scale = i;
        }

        public ScaleAndIndex index(int i) {
            return new ScaleAndIndex(this.scale, i);
        }

        public ScaleAndIndexes indexes(int... iArr) {
            return new ScaleAndIndexes(this.scale, (int[]) iArr.clone());
        }

        public ScaleAndIndexes indexes(Collection<Integer> collection) {
            return new ScaleAndIndexes(this.scale, Ints.toArray(collection));
        }
    }

    public static final class ScaleAndIndex {
        private final int index;
        private final int scale;

        private ScaleAndIndex(int i, int i2) {
            Quantiles.checkIndex(i2, i);
            this.scale = i;
            this.index = i2;
        }

        public double compute(Collection<? extends Number> collection) {
            return computeInPlace(Doubles.toArray(collection));
        }

        public double compute(double... dArr) {
            return computeInPlace((double[]) dArr.clone());
        }

        public double compute(long... jArr) {
            return computeInPlace(Quantiles.longsToDoubles(jArr));
        }

        public double compute(int... iArr) {
            return computeInPlace(Quantiles.intsToDoubles(iArr));
        }

        public double computeInPlace(double... dArr) {
            Preconditions.checkArgument(dArr.length > 0, "Cannot calculate quantiles of an empty dataset");
            if (Quantiles.containsNaN(dArr)) {
                return Double.NaN;
            }
            long length = ((long) this.index) * ((long) (dArr.length - 1));
            int divide = (int) LongMath.divide(length, (long) this.scale, RoundingMode.DOWN);
            int i = (int) (length - (((long) divide) * ((long) this.scale)));
            Quantiles.selectInPlace(divide, dArr, 0, dArr.length - 1);
            if (i == 0) {
                return dArr[divide];
            }
            int i2 = divide + 1;
            Quantiles.selectInPlace(i2, dArr, i2, dArr.length - 1);
            return Quantiles.interpolate(dArr[divide], dArr[i2], (double) i, (double) this.scale);
        }
    }

    public static final class ScaleAndIndexes {
        private final int[] indexes;
        private final int scale;

        private ScaleAndIndexes(int i, int[] iArr) {
            for (int access$300 : iArr) {
                Quantiles.checkIndex(access$300, i);
            }
            this.scale = i;
            this.indexes = iArr;
        }

        public Map<Integer, Double> compute(Collection<? extends Number> collection) {
            return computeInPlace(Doubles.toArray(collection));
        }

        public Map<Integer, Double> compute(double... dArr) {
            return computeInPlace((double[]) dArr.clone());
        }

        public Map<Integer, Double> compute(long... jArr) {
            return computeInPlace(Quantiles.longsToDoubles(jArr));
        }

        public Map<Integer, Double> compute(int... iArr) {
            return computeInPlace(Quantiles.intsToDoubles(iArr));
        }

        public Map<Integer, Double> computeInPlace(double... dArr) {
            double[] dArr2 = dArr;
            int i = 0;
            int i2 = 1;
            Preconditions.checkArgument(dArr2.length > 0, "Cannot calculate quantiles of an empty dataset");
            Map hashMap;
            int[] iArr;
            int length;
            if (Quantiles.containsNaN(dArr)) {
                hashMap = new HashMap();
                iArr = this.indexes;
                length = iArr.length;
                while (i < length) {
                    hashMap.put(Integer.valueOf(iArr[i]), Double.valueOf(Double.NaN));
                    i++;
                }
                return Collections.unmodifiableMap(hashMap);
            }
            int[] iArr2 = this.indexes;
            int[] iArr3 = new int[iArr2.length];
            int[] iArr4 = new int[iArr2.length];
            iArr2 = new int[(iArr2.length * 2)];
            length = 0;
            int i3 = 0;
            while (true) {
                int[] iArr5 = this.indexes;
                if (length >= iArr5.length) {
                    break;
                }
                long length2 = ((long) iArr5[length]) * ((long) (dArr2.length - i2));
                int divide = (int) LongMath.divide(length2, (long) this.scale, RoundingMode.DOWN);
                int i4 = length;
                i2 = (int) (length2 - (((long) divide) * ((long) this.scale)));
                iArr3[i4] = divide;
                iArr4[i4] = i2;
                iArr2[i3] = divide;
                i3++;
                if (i2 != 0) {
                    iArr2[i3] = divide + 1;
                    i3++;
                }
                length = i4 + 1;
                i2 = 1;
            }
            Arrays.sort(iArr2, 0, i3);
            Quantiles.selectAllInPlace(iArr2, 0, i3 - 1, dArr, 0, dArr2.length - 1);
            hashMap = new HashMap();
            while (true) {
                iArr = this.indexes;
                if (i >= iArr.length) {
                    return Collections.unmodifiableMap(hashMap);
                }
                length = iArr3[i];
                i3 = iArr4[i];
                if (i3 == 0) {
                    hashMap.put(Integer.valueOf(iArr[i]), Double.valueOf(dArr2[length]));
                } else {
                    hashMap.put(Integer.valueOf(iArr[i]), Double.valueOf(Quantiles.interpolate(dArr2[length], dArr2[length + 1], (double) i3, (double) this.scale)));
                }
                i++;
            }
        }
    }

    private static double interpolate(double d, double d2, double d3, double d4) {
        return d == Double.NEGATIVE_INFINITY ? d2 == Double.POSITIVE_INFINITY ? Double.NaN : Double.NEGATIVE_INFINITY : d2 == Double.POSITIVE_INFINITY ? Double.POSITIVE_INFINITY : d + (((d2 - d) * d3) / d4);
    }

    public static ScaleAndIndex median() {
        return scale(2).index(1);
    }

    public static Scale quartiles() {
        return scale(4);
    }

    public static Scale percentiles() {
        return scale(100);
    }

    public static Scale scale(int i) {
        return new Scale(i);
    }

    private static boolean containsNaN(double... dArr) {
        for (double isNaN : dArr) {
            if (Double.isNaN(isNaN)) {
                return true;
            }
        }
        return false;
    }

    private static void checkIndex(int i, int i2) {
        if (i < 0 || i > i2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Quantile indexes must be between 0 and the scale, which is ");
            stringBuilder.append(i2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    private static double[] longsToDoubles(long[] jArr) {
        int length = jArr.length;
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            dArr[i] = (double) jArr[i];
        }
        return dArr;
    }

    private static double[] intsToDoubles(int[] iArr) {
        int length = iArr.length;
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            dArr[i] = (double) iArr[i];
        }
        return dArr;
    }

    private static void selectInPlace(int i, double[] dArr, int i2, int i3) {
        int i4;
        if (i == i2) {
            i4 = i2;
            for (i = i2 + 1; i <= i3; i++) {
                if (dArr[i4] > dArr[i]) {
                    i4 = i;
                }
            }
            if (i4 != i2) {
                swap(dArr, i4, i2);
            }
            return;
        }
        while (i3 > i2) {
            i4 = partition(dArr, i2, i3);
            if (i4 >= i) {
                i3 = i4 - 1;
            }
            if (i4 <= i) {
                i2 = i4 + 1;
            }
        }
    }

    private static int partition(double[] dArr, int i, int i2) {
        movePivotToStartOfSlice(dArr, i, i2);
        double d = dArr[i];
        int i3 = i2;
        while (i2 > i) {
            if (dArr[i2] > d) {
                swap(dArr, i3, i2);
                i3--;
            }
            i2--;
        }
        swap(dArr, i, i3);
        return i3;
    }

    private static void movePivotToStartOfSlice(double[] dArr, int i, int i2) {
        int i3 = 1;
        int i4 = (i + i2) >>> 1;
        int i5 = dArr[i2] < dArr[i4] ? 1 : 0;
        int i6 = dArr[i4] < dArr[i] ? 1 : 0;
        if (dArr[i2] >= dArr[i]) {
            i3 = 0;
        }
        if (i5 == i6) {
            swap(dArr, i4, i);
        } else if (i5 != i3) {
            swap(dArr, i, i2);
        }
    }

    private static void selectAllInPlace(int[] iArr, int i, int i2, double[] dArr, int i3, int i4) {
        int chooseNextSelection = chooseNextSelection(iArr, i, i2, i3, i4);
        int i5 = iArr[chooseNextSelection];
        selectInPlace(i5, dArr, i3, i4);
        int i6 = chooseNextSelection - 1;
        while (i6 >= i && iArr[i6] == i5) {
            i6--;
        }
        if (i6 >= i) {
            selectAllInPlace(iArr, i, i6, dArr, i3, i5 - 1);
        }
        int i7 = chooseNextSelection + 1;
        while (i7 <= i2 && iArr[i7] == i5) {
            i7++;
        }
        if (i7 <= i2) {
            selectAllInPlace(iArr, i7, i2, dArr, i5 + 1, i4);
        }
    }

    private static int chooseNextSelection(int[] iArr, int i, int i2, int i3, int i4) {
        if (i == i2) {
            return i;
        }
        i3 += i4;
        i4 = i3 >>> 1;
        while (i2 > i + 1) {
            int i5 = (i + i2) >>> 1;
            if (iArr[i5] > i4) {
                i2 = i5;
            } else if (iArr[i5] >= i4) {
                return i5;
            } else {
                i = i5;
            }
        }
        return (i3 - iArr[i]) - iArr[i2] > 0 ? i2 : i;
    }

    private static void swap(double[] dArr, int i, int i2) {
        double d = dArr[i];
        dArr[i] = dArr[i2];
        dArr[i2] = d;
    }
}
