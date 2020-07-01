package androidx.core.util;

import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import java.io.PrintWriter;

@RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
public final class TimeUtils {
    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static char[] sFormatStr = new char[24];
    private static final Object sFormatSync = new Object();

    private static int accumField(int i, int i2, boolean z, int i3) {
        return (i > 99 || (z && i3 >= 3)) ? i2 + 3 : (i > 9 || (z && i3 >= 2)) ? i2 + 2 : (z || i > 0) ? i2 + 1 : 0;
    }

    private static int printField(char[] cArr, int i, char c, int i2, boolean z, int i3) {
        if (!z && i <= 0) {
            return i2;
        }
        int i4;
        if ((!z || i3 < 3) && i <= 99) {
            i4 = i2;
        } else {
            int i5 = i / 100;
            cArr[i2] = (char) (i5 + 48);
            i4 = i2 + 1;
            i -= i5 * 100;
        }
        if ((z && i3 >= 2) || i > 9 || i2 != i4) {
            i2 = i / 10;
            cArr[i4] = (char) (i2 + 48);
            i4++;
            i -= i2 * 10;
        }
        cArr[i4] = (char) (i + 48);
        i4++;
        cArr[i4] = c;
        return i4 + 1;
    }

    private static int formatDurationLocked(long j, int i) {
        long j2 = j;
        int i2 = i;
        if (sFormatStr.length < i2) {
            sFormatStr = new char[i2];
        }
        char[] cArr = sFormatStr;
        int i3 = (j2 > 0 ? 1 : (j2 == 0 ? 0 : -1));
        int i4;
        if (i3 == 0) {
            i4 = i2 - 1;
            while (i4 > 0) {
                cArr[0] = ' ';
            }
            cArr[0] = '0';
            return 1;
        }
        char c;
        int i5;
        int i6;
        int i7;
        int i8;
        if (i3 > 0) {
            c = '+';
        } else {
            c = '-';
            j2 = -j2;
        }
        int i9 = (int) (j2 % 1000);
        i4 = (int) Math.floor((double) (j2 / 1000));
        if (i4 > SECONDS_PER_DAY) {
            i5 = i4 / SECONDS_PER_DAY;
            i4 -= SECONDS_PER_DAY * i5;
        } else {
            i5 = 0;
        }
        if (i4 > 3600) {
            i6 = i4 / 3600;
            i4 -= i6 * 3600;
        } else {
            i6 = 0;
        }
        if (i4 > 60) {
            i3 = i4 / 60;
            i7 = i4 - (i3 * 60);
            i4 = i3;
        } else {
            i7 = i4;
            i4 = 0;
        }
        if (i2 != 0) {
            i3 = accumField(i5, 1, false, 0);
            i3 += accumField(i6, 1, i3 > 0, 2);
            i3 += accumField(i4, 1, i3 > 0, 2);
            i3 += accumField(i7, 1, i3 > 0, 2);
            i8 = 0;
            for (i3 += accumField(i9, 2, true, i3 > 0 ? 3 : 0) + 1; i3 < i2; i3++) {
                cArr[i8] = ' ';
                i8++;
            }
        } else {
            i8 = 0;
        }
        cArr[i8] = c;
        int i10 = i8 + 1;
        Object obj = i2 != 0 ? 1 : null;
        int i11 = i10;
        i3 = printField(cArr, i5, 'd', i10, false, 0);
        i3 = printField(cArr, i6, 'h', i3, i3 != i11, obj != null ? 2 : 0);
        i3 = printField(cArr, i4, 'm', i3, i3 != i11, obj != null ? 2 : 0);
        i3 = printField(cArr, i7, 's', i3, i3 != i11, obj != null ? 2 : 0);
        i10 = (obj == null || i3 == i11) ? 0 : 3;
        i4 = printField(cArr, i9, 'm', i3, true, i10);
        cArr[i4] = 's';
        return i4 + 1;
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    public static void formatDuration(long j, StringBuilder stringBuilder) {
        synchronized (sFormatSync) {
            stringBuilder.append(sFormatStr, 0, formatDurationLocked(j, 0));
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    public static void formatDuration(long j, PrintWriter printWriter, int i) {
        synchronized (sFormatSync) {
            printWriter.print(new String(sFormatStr, 0, formatDurationLocked(j, i)));
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    public static void formatDuration(long j, PrintWriter printWriter) {
        formatDuration(j, printWriter, 0);
    }

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    public static void formatDuration(long j, long j2, PrintWriter printWriter) {
        if (j == 0) {
            printWriter.print("--");
        } else {
            formatDuration(j - j2, printWriter, 0);
        }
    }

    private TimeUtils() {
    }
}
