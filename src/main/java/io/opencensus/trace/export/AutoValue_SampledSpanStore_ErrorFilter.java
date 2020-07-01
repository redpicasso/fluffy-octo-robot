package io.opencensus.trace.export;

import io.opencensus.trace.Status.CanonicalCode;
import io.opencensus.trace.export.SampledSpanStore.ErrorFilter;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_SampledSpanStore_ErrorFilter extends ErrorFilter {
    private final CanonicalCode canonicalCode;
    private final int maxSpansToReturn;
    private final String spanName;

    AutoValue_SampledSpanStore_ErrorFilter(String str, @Nullable CanonicalCode canonicalCode, int i) {
        if (str != null) {
            this.spanName = str;
            this.canonicalCode = canonicalCode;
            this.maxSpansToReturn = i;
            return;
        }
        throw new NullPointerException("Null spanName");
    }

    public String getSpanName() {
        return this.spanName;
    }

    @Nullable
    public CanonicalCode getCanonicalCode() {
        return this.canonicalCode;
    }

    public int getMaxSpansToReturn() {
        return this.maxSpansToReturn;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ErrorFilter{spanName=");
        stringBuilder.append(this.spanName);
        stringBuilder.append(", canonicalCode=");
        stringBuilder.append(this.canonicalCode);
        stringBuilder.append(", maxSpansToReturn=");
        stringBuilder.append(this.maxSpansToReturn);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /* JADX WARNING: Missing block: B:14:0x0032, code:
            if (r4.maxSpansToReturn == r5.getMaxSpansToReturn()) goto L_0x0036;
     */
    public boolean equals(java.lang.Object r5) {
        /*
        r4 = this;
        r0 = 1;
        if (r5 != r4) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = r5 instanceof io.opencensus.trace.export.SampledSpanStore.ErrorFilter;
        r2 = 0;
        if (r1 == 0) goto L_0x0037;
    L_0x0009:
        r5 = (io.opencensus.trace.export.SampledSpanStore.ErrorFilter) r5;
        r1 = r4.spanName;
        r3 = r5.getSpanName();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x0035;
    L_0x0017:
        r1 = r4.canonicalCode;
        if (r1 != 0) goto L_0x0022;
    L_0x001b:
        r1 = r5.getCanonicalCode();
        if (r1 != 0) goto L_0x0035;
    L_0x0021:
        goto L_0x002c;
    L_0x0022:
        r3 = r5.getCanonicalCode();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x0035;
    L_0x002c:
        r1 = r4.maxSpansToReturn;
        r5 = r5.getMaxSpansToReturn();
        if (r1 != r5) goto L_0x0035;
    L_0x0034:
        goto L_0x0036;
    L_0x0035:
        r0 = 0;
    L_0x0036:
        return r0;
    L_0x0037:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.opencensus.trace.export.AutoValue_SampledSpanStore_ErrorFilter.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int hashCode = (this.spanName.hashCode() ^ 1000003) * 1000003;
        CanonicalCode canonicalCode = this.canonicalCode;
        return ((hashCode ^ (canonicalCode == null ? 0 : canonicalCode.hashCode())) * 1000003) ^ this.maxSpansToReturn;
    }
}
