package com.facebook.react.views.text;

import java.text.BreakIterator;

public enum TextTransform {
    NONE,
    UPPERCASE,
    LOWERCASE,
    CAPITALIZE,
    UNSET;

    /* renamed from: com.facebook.react.views.text.TextTransform$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$views$text$TextTransform = null;

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$facebook$react$views$text$TextTransform[com.facebook.react.views.text.TextTransform.CAPITALIZE.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.facebook.react.views.text.TextTransform.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$react$views$text$TextTransform = r0;
            r0 = $SwitchMap$com$facebook$react$views$text$TextTransform;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.react.views.text.TextTransform.UPPERCASE;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$react$views$text$TextTransform;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.react.views.text.TextTransform.LOWERCASE;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$react$views$text$TextTransform;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.react.views.text.TextTransform.CAPITALIZE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.text.TextTransform.1.<clinit>():void");
        }
    }

    public static String apply(String str, TextTransform textTransform) {
        if (str == null) {
            return null;
        }
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$views$text$TextTransform[textTransform.ordinal()];
        if (i == 1) {
            str = str.toUpperCase();
        } else if (i == 2) {
            str = str.toLowerCase();
        } else if (i == 3) {
            str = capitalize(str);
        }
        return str;
    }

    private static String capitalize(String str) {
        BreakIterator wordInstance = BreakIterator.getWordInstance();
        wordInstance.setText(str);
        StringBuilder stringBuilder = new StringBuilder(str.length());
        int first = wordInstance.first();
        while (true) {
            int i = first;
            first = wordInstance.next();
            if (first == -1) {
                return stringBuilder.toString();
            }
            String substring = str.substring(i, first);
            if (Character.isLetterOrDigit(substring.charAt(0))) {
                stringBuilder.append(Character.toUpperCase(substring.charAt(0)));
                stringBuilder.append(substring.substring(1).toLowerCase());
            } else {
                stringBuilder.append(substring);
            }
        }
    }
}
