package com.facebook.common.util;

import com.facebook.infer.annotation.Functional;
import javax.annotation.Nullable;

public enum TriState {
    YES,
    NO,
    UNSET;

    /* renamed from: com.facebook.common.util.TriState$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$common$util$TriState = null;

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$facebook$common$util$TriState[com.facebook.common.util.TriState.UNSET.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.facebook.common.util.TriState.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$common$util$TriState = r0;
            r0 = $SwitchMap$com$facebook$common$util$TriState;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.common.util.TriState.YES;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$common$util$TriState;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.common.util.TriState.NO;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$common$util$TriState;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.common.util.TriState.UNSET;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.common.util.TriState.1.<clinit>():void");
        }
    }

    @Functional
    public boolean isSet() {
        return this != UNSET;
    }

    @Functional
    public static TriState valueOf(boolean z) {
        return z ? YES : NO;
    }

    @Functional
    public static TriState valueOf(Boolean bool) {
        return bool != null ? valueOf(bool.booleanValue()) : UNSET;
    }

    @Functional
    public boolean asBoolean() {
        int i = AnonymousClass1.$SwitchMap$com$facebook$common$util$TriState[ordinal()];
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return false;
        }
        if (i != 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unrecognized TriState value: ");
            stringBuilder.append(this);
            throw new IllegalStateException(stringBuilder.toString());
        }
        throw new IllegalStateException("No boolean equivalent for UNSET");
    }

    @Functional
    public boolean asBoolean(boolean z) {
        int i = AnonymousClass1.$SwitchMap$com$facebook$common$util$TriState[ordinal()];
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return false;
        }
        if (i == 3) {
            return z;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unrecognized TriState value: ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Nullable
    @Functional
    public Boolean asBooleanObject() {
        int i = AnonymousClass1.$SwitchMap$com$facebook$common$util$TriState[ordinal()];
        if (i == 1) {
            return Boolean.TRUE;
        }
        if (i == 2) {
            return Boolean.FALSE;
        }
        if (i == 3) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unrecognized TriState value: ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Functional
    public int getDbValue() {
        int i = AnonymousClass1.$SwitchMap$com$facebook$common$util$TriState[ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 3;
            }
        }
        return i2;
    }

    @Functional
    public static TriState fromDbValue(int i) {
        if (i == 1) {
            return YES;
        }
        if (i != 2) {
            return UNSET;
        }
        return NO;
    }
}
