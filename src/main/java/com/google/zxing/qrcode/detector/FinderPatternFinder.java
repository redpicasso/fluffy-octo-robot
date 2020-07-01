package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FinderPatternFinder {
    private static final int CENTER_QUORUM = 2;
    protected static final int MAX_MODULES = 97;
    protected static final int MIN_SKIP = 3;
    private final int[] crossCheckStateCount;
    private boolean hasSkipped;
    private final BitMatrix image;
    private final List<FinderPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;

    private static final class CenterComparator implements Serializable, Comparator<FinderPattern> {
        private final float average;

        private CenterComparator(float f) {
            this.average = f;
        }

        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            int compare = Integer.compare(finderPattern2.getCount(), finderPattern.getCount());
            return compare == 0 ? Float.compare(Math.abs(finderPattern.getEstimatedModuleSize() - this.average), Math.abs(finderPattern2.getEstimatedModuleSize() - this.average)) : compare;
        }
    }

    private static final class FurthestFromAverageComparator implements Serializable, Comparator<FinderPattern> {
        private final float average;

        private FurthestFromAverageComparator(float f) {
            this.average = f;
        }

        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            return Float.compare(Math.abs(finderPattern2.getEstimatedModuleSize() - this.average), Math.abs(finderPattern.getEstimatedModuleSize() - this.average));
        }
    }

    public FinderPatternFinder(BitMatrix bitMatrix) {
        this(bitMatrix, null);
    }

    public FinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        this.image = bitMatrix;
        this.possibleCenters = new ArrayList();
        this.crossCheckStateCount = new int[5];
        this.resultPointCallback = resultPointCallback;
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final List<FinderPattern> getPossibleCenters() {
        return this.possibleCenters;
    }

    final FinderPatternInfo find(Map<DecodeHintType, ?> map) throws NotFoundException {
        Object obj = (map == null || !map.containsKey(DecodeHintType.TRY_HARDER)) ? null : 1;
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int i = (height * 3) / 388;
        if (i < 3 || obj != null) {
            i = 3;
        }
        int[] iArr = new int[5];
        int i2 = i - 1;
        int i3 = i;
        boolean z = false;
        while (i2 < height && !z) {
            clearCounts(iArr);
            boolean z2 = z;
            int i4 = i3;
            i = 0;
            i3 = 0;
            while (i < width) {
                if (this.image.get(i, i2)) {
                    if ((i3 & 1) == 1) {
                        i3++;
                    }
                    iArr[i3] = iArr[i3] + 1;
                } else if ((i3 & 1) != 0) {
                    iArr[i3] = iArr[i3] + 1;
                } else if (i3 == 4) {
                    if (!foundPatternCross(iArr)) {
                        shiftCounts2(iArr);
                    } else if (handlePossibleCenter(iArr, i2, i)) {
                        if (this.hasSkipped) {
                            z2 = haveMultiplyConfirmedCenters();
                        } else {
                            i3 = findRowSkip();
                            if (i3 > iArr[2]) {
                                i2 += (i3 - iArr[2]) - 2;
                                i = width - 1;
                            }
                        }
                        clearCounts(iArr);
                        i3 = 0;
                        i4 = 2;
                    } else {
                        shiftCounts2(iArr);
                    }
                    i3 = 3;
                } else {
                    i3++;
                    iArr[i3] = iArr[i3] + 1;
                }
                i++;
            }
            if (foundPatternCross(iArr) && handlePossibleCenter(iArr, i2, width)) {
                i = iArr[0];
                if (this.hasSkipped) {
                    i3 = i;
                    z = haveMultiplyConfirmedCenters();
                    i2 += i3;
                } else {
                    i3 = i;
                }
            } else {
                i3 = i4;
            }
            z = z2;
            i2 += i3;
        }
        ResultPoint[] selectBestPatterns = selectBestPatterns();
        ResultPoint.orderBestPatterns(selectBestPatterns);
        return new FinderPatternInfo(selectBestPatterns);
    }

    private static float centerFromEnd(int[] iArr, int i) {
        return ((float) ((i - iArr[4]) - iArr[3])) - (((float) iArr[2]) / 2.0f);
    }

    protected static boolean foundPatternCross(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 5; i2++) {
            int i3 = iArr[i2];
            if (i3 == 0) {
                return false;
            }
            i += i3;
        }
        if (i < 7) {
            return false;
        }
        float f = ((float) i) / 7.0f;
        float f2 = f / 2.0f;
        if (Math.abs(f - ((float) iArr[0])) >= f2 || Math.abs(f - ((float) iArr[1])) >= f2 || Math.abs((f * 3.0f) - ((float) iArr[2])) >= 3.0f * f2 || Math.abs(f - ((float) iArr[3])) >= f2 || Math.abs(f - ((float) iArr[4])) >= f2) {
            return false;
        }
        return true;
    }

    protected static boolean foundPatternDiagonal(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 5; i2++) {
            int i3 = iArr[i2];
            if (i3 == 0) {
                return false;
            }
            i += i3;
        }
        if (i < 7) {
            return false;
        }
        float f = ((float) i) / 7.0f;
        float f2 = f / 1.333f;
        if (Math.abs(f - ((float) iArr[0])) >= f2 || Math.abs(f - ((float) iArr[1])) >= f2 || Math.abs((f * 3.0f) - ((float) iArr[2])) >= 3.0f * f2 || Math.abs(f - ((float) iArr[3])) >= f2 || Math.abs(f - ((float) iArr[4])) >= f2) {
            return false;
        }
        return true;
    }

    private int[] getCrossCheckStateCount() {
        clearCounts(this.crossCheckStateCount);
        return this.crossCheckStateCount;
    }

    protected final void clearCounts(int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = 0;
        }
    }

    protected final void shiftCounts2(int[] iArr) {
        iArr[0] = iArr[2];
        iArr[1] = iArr[3];
        iArr[2] = iArr[4];
        iArr[3] = 1;
        iArr[4] = 0;
    }

    private boolean crossCheckDiagonal(int i, int i2) {
        int[] crossCheckStateCount = getCrossCheckStateCount();
        int i3 = 0;
        while (i >= i3 && i2 >= i3 && this.image.get(i2 - i3, i - i3)) {
            crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
            i3++;
        }
        if (crossCheckStateCount[2] == 0) {
            return false;
        }
        while (i >= i3 && i2 >= i3 && !this.image.get(i2 - i3, i - i3)) {
            crossCheckStateCount[1] = crossCheckStateCount[1] + 1;
            i3++;
        }
        if (crossCheckStateCount[1] == 0) {
            return false;
        }
        while (i >= i3 && i2 >= i3 && this.image.get(i2 - i3, i - i3)) {
            crossCheckStateCount[0] = crossCheckStateCount[0] + 1;
            i3++;
        }
        if (crossCheckStateCount[0] == 0) {
            return false;
        }
        int i4;
        int i5;
        i3 = this.image.getHeight();
        int width = this.image.getWidth();
        int i6 = 1;
        while (true) {
            int i7 = i + i6;
            if (i7 >= i3) {
                break;
            }
            i4 = i2 + i6;
            if (i4 >= width || !this.image.get(i4, i7)) {
                break;
            }
            crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
            i6++;
        }
        while (true) {
            i5 = i + i6;
            if (i5 >= i3) {
                break;
            }
            i4 = i2 + i6;
            if (i4 >= width || this.image.get(i4, i5)) {
                break;
            }
            crossCheckStateCount[3] = crossCheckStateCount[3] + 1;
            i6++;
        }
        if (crossCheckStateCount[3] == 0) {
            return false;
        }
        while (true) {
            i5 = i + i6;
            if (i5 >= i3) {
                break;
            }
            i4 = i2 + i6;
            if (i4 >= width || !this.image.get(i4, i5)) {
                break;
            }
            crossCheckStateCount[4] = crossCheckStateCount[4] + 1;
            i6++;
        }
        if (crossCheckStateCount[4] == 0) {
            return false;
        }
        return foundPatternDiagonal(crossCheckStateCount);
    }

    private float crossCheckVertical(int i, int i2, int i3, int i4) {
        BitMatrix bitMatrix = this.image;
        int height = bitMatrix.getHeight();
        int[] crossCheckStateCount = getCrossCheckStateCount();
        int i5 = i;
        while (i5 >= 0 && bitMatrix.get(i2, i5)) {
            crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
            i5--;
        }
        if (i5 < 0) {
            return Float.NaN;
        }
        while (i5 >= 0 && !bitMatrix.get(i2, i5) && crossCheckStateCount[1] <= i3) {
            crossCheckStateCount[1] = crossCheckStateCount[1] + 1;
            i5--;
        }
        if (i5 >= 0 && crossCheckStateCount[1] <= i3) {
            while (i5 >= 0 && bitMatrix.get(i2, i5) && crossCheckStateCount[0] <= i3) {
                crossCheckStateCount[0] = crossCheckStateCount[0] + 1;
                i5--;
            }
            if (crossCheckStateCount[0] > i3) {
                return Float.NaN;
            }
            i++;
            while (i < height && bitMatrix.get(i2, i)) {
                crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
                i++;
            }
            if (i == height) {
                return Float.NaN;
            }
            while (i < height && !bitMatrix.get(i2, i) && crossCheckStateCount[3] < i3) {
                crossCheckStateCount[3] = crossCheckStateCount[3] + 1;
                i++;
            }
            if (i != height && crossCheckStateCount[3] < i3) {
                while (i < height && bitMatrix.get(i2, i) && crossCheckStateCount[4] < i3) {
                    crossCheckStateCount[4] = crossCheckStateCount[4] + 1;
                    i++;
                }
                if (crossCheckStateCount[4] < i3 && Math.abs(((((crossCheckStateCount[0] + crossCheckStateCount[1]) + crossCheckStateCount[2]) + crossCheckStateCount[3]) + crossCheckStateCount[4]) - i4) * 5 < i4 * 2 && foundPatternCross(crossCheckStateCount)) {
                    return centerFromEnd(crossCheckStateCount, i);
                }
            }
        }
        return Float.NaN;
    }

    private float crossCheckHorizontal(int i, int i2, int i3, int i4) {
        BitMatrix bitMatrix = this.image;
        int width = bitMatrix.getWidth();
        int[] crossCheckStateCount = getCrossCheckStateCount();
        int i5 = i;
        while (i5 >= 0 && bitMatrix.get(i5, i2)) {
            crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
            i5--;
        }
        if (i5 < 0) {
            return Float.NaN;
        }
        while (i5 >= 0 && !bitMatrix.get(i5, i2) && crossCheckStateCount[1] <= i3) {
            crossCheckStateCount[1] = crossCheckStateCount[1] + 1;
            i5--;
        }
        if (i5 >= 0 && crossCheckStateCount[1] <= i3) {
            while (i5 >= 0 && bitMatrix.get(i5, i2) && crossCheckStateCount[0] <= i3) {
                crossCheckStateCount[0] = crossCheckStateCount[0] + 1;
                i5--;
            }
            if (crossCheckStateCount[0] > i3) {
                return Float.NaN;
            }
            i++;
            while (i < width && bitMatrix.get(i, i2)) {
                crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
                i++;
            }
            if (i == width) {
                return Float.NaN;
            }
            while (i < width && !bitMatrix.get(i, i2) && crossCheckStateCount[3] < i3) {
                crossCheckStateCount[3] = crossCheckStateCount[3] + 1;
                i++;
            }
            if (i != width && crossCheckStateCount[3] < i3) {
                while (i < width && bitMatrix.get(i, i2) && crossCheckStateCount[4] < i3) {
                    crossCheckStateCount[4] = crossCheckStateCount[4] + 1;
                    i++;
                }
                if (crossCheckStateCount[4] < i3 && Math.abs(((((crossCheckStateCount[0] + crossCheckStateCount[1]) + crossCheckStateCount[2]) + crossCheckStateCount[3]) + crossCheckStateCount[4]) - i4) * 5 < i4 && foundPatternCross(crossCheckStateCount)) {
                    return centerFromEnd(crossCheckStateCount, i);
                }
            }
        }
        return Float.NaN;
    }

    @Deprecated
    protected final boolean handlePossibleCenter(int[] iArr, int i, int i2, boolean z) {
        return handlePossibleCenter(iArr, i, i2);
    }

    protected final boolean handlePossibleCenter(int[] iArr, int i, int i2) {
        boolean z = false;
        int i3 = (((iArr[0] + iArr[1]) + iArr[2]) + iArr[3]) + iArr[4];
        i2 = (int) centerFromEnd(iArr, i2);
        float crossCheckVertical = crossCheckVertical(i, i2, iArr[2], i3);
        if (!Float.isNaN(crossCheckVertical)) {
            int i4 = (int) crossCheckVertical;
            float crossCheckHorizontal = crossCheckHorizontal(i2, i4, iArr[2], i3);
            if (!Float.isNaN(crossCheckHorizontal) && crossCheckDiagonal(i4, (int) crossCheckHorizontal)) {
                float f = ((float) i3) / 7.0f;
                for (i3 = 0; i3 < this.possibleCenters.size(); i3++) {
                    FinderPattern finderPattern = (FinderPattern) this.possibleCenters.get(i3);
                    if (finderPattern.aboutEquals(f, crossCheckVertical, crossCheckHorizontal)) {
                        this.possibleCenters.set(i3, finderPattern.combineEstimate(crossCheckVertical, crossCheckHorizontal, f));
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    ResultPoint finderPattern2 = new FinderPattern(crossCheckHorizontal, crossCheckVertical, f);
                    this.possibleCenters.add(finderPattern2);
                    ResultPointCallback resultPointCallback = this.resultPointCallback;
                    if (resultPointCallback != null) {
                        resultPointCallback.foundPossibleResultPoint(finderPattern2);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private int findRowSkip() {
        if (this.possibleCenters.size() <= 1) {
            return 0;
        }
        ResultPoint resultPoint = null;
        for (ResultPoint resultPoint2 : this.possibleCenters) {
            if (resultPoint2.getCount() >= 2) {
                if (resultPoint == null) {
                    resultPoint = resultPoint2;
                } else {
                    this.hasSkipped = true;
                    return ((int) (Math.abs(resultPoint.getX() - resultPoint2.getX()) - Math.abs(resultPoint.getY() - resultPoint2.getY()))) / 2;
                }
            }
        }
        return 0;
    }

    private boolean haveMultiplyConfirmedCenters() {
        int size = this.possibleCenters.size();
        float f = 0.0f;
        int i = 0;
        float f2 = 0.0f;
        for (FinderPattern finderPattern : this.possibleCenters) {
            if (finderPattern.getCount() >= 2) {
                i++;
                f2 += finderPattern.getEstimatedModuleSize();
            }
        }
        if (i < 3) {
            return false;
        }
        float f3 = f2 / ((float) size);
        for (FinderPattern estimatedModuleSize : this.possibleCenters) {
            f += Math.abs(estimatedModuleSize.getEstimatedModuleSize() - f3);
        }
        if (f <= f2 * 0.05f) {
            return true;
        }
        return false;
    }

    private FinderPattern[] selectBestPatterns() throws NotFoundException {
        int size = this.possibleCenters.size();
        if (size >= 3) {
            float f = 0.0f;
            if (size > 3) {
                float f2 = 0.0f;
                float f3 = 0.0f;
                for (FinderPattern estimatedModuleSize : this.possibleCenters) {
                    float estimatedModuleSize2 = estimatedModuleSize.getEstimatedModuleSize();
                    f2 += estimatedModuleSize2;
                    f3 += estimatedModuleSize2 * estimatedModuleSize2;
                }
                float f4 = (float) size;
                f2 /= f4;
                f4 = (float) Math.sqrt((double) ((f3 / f4) - (f2 * f2)));
                Collections.sort(this.possibleCenters, new FurthestFromAverageComparator(f2));
                f4 = Math.max(0.2f * f2, f4);
                int i = 0;
                while (i < this.possibleCenters.size() && this.possibleCenters.size() > 3) {
                    if (Math.abs(((FinderPattern) this.possibleCenters.get(i)).getEstimatedModuleSize() - f2) > f4) {
                        this.possibleCenters.remove(i);
                        i--;
                    }
                    i++;
                }
            }
            if (this.possibleCenters.size() > 3) {
                for (FinderPattern estimatedModuleSize3 : this.possibleCenters) {
                    f += estimatedModuleSize3.getEstimatedModuleSize();
                }
                Collections.sort(this.possibleCenters, new CenterComparator(f / ((float) this.possibleCenters.size())));
                List list = this.possibleCenters;
                list.subList(3, list.size()).clear();
            }
            return new FinderPattern[]{(FinderPattern) this.possibleCenters.get(0), (FinderPattern) this.possibleCenters.get(1), (FinderPattern) this.possibleCenters.get(2)};
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
