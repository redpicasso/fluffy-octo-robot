package com.google.firebase.database.core.utilities;

import java.util.Random;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class PushIdGenerator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String PUSH_CHARS = "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
    private static long lastPushTime = 0;
    private static final int[] lastRandChars = new int[12];
    private static final Random randGen = new Random();

    public static synchronized String generatePushChildName(long j) {
        String stringBuilder;
        synchronized (PushIdGenerator.class) {
            int i = 0;
            Object obj = j == lastPushTime ? 1 : null;
            lastPushTime = j;
            char[] cArr = new char[8];
            StringBuilder stringBuilder2 = new StringBuilder(20);
            for (int i2 = 7; i2 >= 0; i2--) {
                cArr[i2] = PUSH_CHARS.charAt((int) (j % 64));
                j /= 64;
            }
            stringBuilder2.append(cArr);
            if (obj == null) {
                for (int i3 = 0; i3 < 12; i3++) {
                    lastRandChars[i3] = randGen.nextInt(64);
                }
            } else {
                incrementArray();
            }
            while (i < 12) {
                stringBuilder2.append(PUSH_CHARS.charAt(lastRandChars[i]));
                i++;
            }
            stringBuilder = stringBuilder2.toString();
        }
        return stringBuilder;
    }

    private static void incrementArray() {
        int i = 11;
        while (i >= 0) {
            int[] iArr = lastRandChars;
            if (iArr[i] != 63) {
                iArr[i] = iArr[i] + 1;
                return;
            } else {
                iArr[i] = 0;
                i--;
            }
        }
    }
}
