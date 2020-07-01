package androidx.core.os;

import android.os.Build.VERSION;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

final class LocaleListCompatWrapper implements LocaleListInterface {
    private static final Locale EN_LATN = LocaleListCompat.forLanguageTagCompat("en-Latn");
    private static final Locale LOCALE_AR_XB = new Locale("ar", "XB");
    private static final Locale LOCALE_EN_XA = new Locale("en", "XA");
    private static final Locale[] sEmptyList = new Locale[0];
    private final Locale[] mList;
    @NonNull
    private final String mStringRepresentation;

    @Nullable
    public Object getLocaleList() {
        return null;
    }

    public Locale get(int i) {
        if (i >= 0) {
            Locale[] localeArr = this.mList;
            if (i < localeArr.length) {
                return localeArr[i];
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return this.mList.length == 0;
    }

    public int size() {
        return this.mList.length;
    }

    public int indexOf(Locale locale) {
        int i = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i >= localeArr.length) {
                return -1;
            }
            if (localeArr[i].equals(locale)) {
                return i;
            }
            i++;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocaleListCompatWrapper)) {
            return false;
        }
        Locale[] localeArr = ((LocaleListCompatWrapper) obj).mList;
        if (this.mList.length != localeArr.length) {
            return false;
        }
        int i = 0;
        while (true) {
            Locale[] localeArr2 = this.mList;
            if (i >= localeArr2.length) {
                return true;
            }
            if (!localeArr2[i].equals(localeArr[i])) {
                return false;
            }
            i++;
        }
    }

    public int hashCode() {
        int i = 1;
        int i2 = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i2 >= localeArr.length) {
                return i;
            }
            i = (i * 31) + localeArr[i2].hashCode();
            i2++;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        int i = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i < localeArr.length) {
                stringBuilder.append(localeArr[i]);
                if (i < this.mList.length - 1) {
                    stringBuilder.append(',');
                }
                i++;
            } else {
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
        }
    }

    public String toLanguageTags() {
        return this.mStringRepresentation;
    }

    LocaleListCompatWrapper(@NonNull Locale... localeArr) {
        if (localeArr.length == 0) {
            this.mList = sEmptyList;
            this.mStringRepresentation = "";
            return;
        }
        Locale[] localeArr2 = new Locale[localeArr.length];
        HashSet hashSet = new HashSet();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (i < localeArr.length) {
            Locale locale = localeArr[i];
            String str = "list[";
            StringBuilder stringBuilder2;
            if (locale == null) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append(i);
                stringBuilder2.append("] is null");
                throw new NullPointerException(stringBuilder2.toString());
            } else if (hashSet.contains(locale)) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append(i);
                stringBuilder2.append("] is a repetition");
                throw new IllegalArgumentException(stringBuilder2.toString());
            } else {
                locale = (Locale) locale.clone();
                localeArr2[i] = locale;
                toLanguageTag(stringBuilder, locale);
                if (i < localeArr.length - 1) {
                    stringBuilder.append(',');
                }
                hashSet.add(locale);
                i++;
            }
        }
        this.mList = localeArr2;
        this.mStringRepresentation = stringBuilder.toString();
    }

    @VisibleForTesting
    static void toLanguageTag(StringBuilder stringBuilder, Locale locale) {
        stringBuilder.append(locale.getLanguage());
        String country = locale.getCountry();
        if (country != null && !country.isEmpty()) {
            stringBuilder.append('-');
            stringBuilder.append(locale.getCountry());
        }
    }

    private static String getLikelyScript(Locale locale) {
        String str = "";
        if (VERSION.SDK_INT >= 21) {
            String script = locale.getScript();
            if (!script.isEmpty()) {
                return script;
            }
        }
        return str;
    }

    private static boolean isPseudoLocale(Locale locale) {
        return LOCALE_EN_XA.equals(locale) || LOCALE_AR_XB.equals(locale);
    }

    @IntRange(from = 0, to = 1)
    private static int matchScore(Locale locale, Locale locale2) {
        int i = 1;
        if (locale.equals(locale2)) {
            return 1;
        }
        if (!locale.getLanguage().equals(locale2.getLanguage()) || isPseudoLocale(locale) || isPseudoLocale(locale2)) {
            return 0;
        }
        String likelyScript = getLikelyScript(locale);
        if (!likelyScript.isEmpty()) {
            return likelyScript.equals(getLikelyScript(locale2));
        }
        String country = locale.getCountry();
        if (!(country.isEmpty() || country.equals(locale2.getCountry()))) {
            i = 0;
        }
        return i;
    }

    private int findFirstMatchIndex(Locale locale) {
        int i = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i >= localeArr.length) {
                return Integer.MAX_VALUE;
            }
            if (matchScore(locale, localeArr[i]) > 0) {
                return i;
            }
            i++;
        }
    }

    /* JADX WARNING: Missing block: B:12:0x001b, code:
            if (r6 < Integer.MAX_VALUE) goto L_0x0021;
     */
    private int computeFirstMatchIndex(java.util.Collection<java.lang.String> r5, boolean r6) {
        /*
        r4 = this;
        r0 = r4.mList;
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 != r3) goto L_0x0008;
    L_0x0007:
        return r2;
    L_0x0008:
        r0 = r0.length;
        if (r0 != 0) goto L_0x000d;
    L_0x000b:
        r5 = -1;
        return r5;
    L_0x000d:
        r0 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        if (r6 == 0) goto L_0x001e;
    L_0x0012:
        r6 = EN_LATN;
        r6 = r4.findFirstMatchIndex(r6);
        if (r6 != 0) goto L_0x001b;
    L_0x001a:
        return r2;
    L_0x001b:
        if (r6 >= r0) goto L_0x001e;
    L_0x001d:
        goto L_0x0021;
    L_0x001e:
        r6 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
    L_0x0021:
        r5 = r5.iterator();
    L_0x0025:
        r1 = r5.hasNext();
        if (r1 == 0) goto L_0x0040;
    L_0x002b:
        r1 = r5.next();
        r1 = (java.lang.String) r1;
        r1 = androidx.core.os.LocaleListCompat.forLanguageTagCompat(r1);
        r1 = r4.findFirstMatchIndex(r1);
        if (r1 != 0) goto L_0x003c;
    L_0x003b:
        return r2;
    L_0x003c:
        if (r1 >= r6) goto L_0x0025;
    L_0x003e:
        r6 = r1;
        goto L_0x0025;
    L_0x0040:
        if (r6 != r0) goto L_0x0043;
    L_0x0042:
        return r2;
    L_0x0043:
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.os.LocaleListCompatWrapper.computeFirstMatchIndex(java.util.Collection, boolean):int");
    }

    private Locale computeFirstMatch(Collection<String> collection, boolean z) {
        int computeFirstMatchIndex = computeFirstMatchIndex(collection, z);
        if (computeFirstMatchIndex == -1) {
            return null;
        }
        return this.mList[computeFirstMatchIndex];
    }

    public Locale getFirstMatch(@NonNull String[] strArr) {
        return computeFirstMatch(Arrays.asList(strArr), false);
    }
}
