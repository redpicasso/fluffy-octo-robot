package com.facebook.react.views.scroll;

public enum ScrollEventType {
    BEGIN_DRAG,
    END_DRAG,
    SCROLL,
    MOMENTUM_BEGIN,
    MOMENTUM_END;

    /* renamed from: com.facebook.react.views.scroll.ScrollEventType$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$views$scroll$ScrollEventType = null;

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:11:0x0040, code:
            return;
     */
        static {
            /*
            r0 = com.facebook.react.views.scroll.ScrollEventType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$react$views$scroll$ScrollEventType = r0;
            r0 = $SwitchMap$com$facebook$react$views$scroll$ScrollEventType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.react.views.scroll.ScrollEventType.BEGIN_DRAG;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$react$views$scroll$ScrollEventType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.react.views.scroll.ScrollEventType.END_DRAG;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$react$views$scroll$ScrollEventType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.react.views.scroll.ScrollEventType.SCROLL;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$facebook$react$views$scroll$ScrollEventType;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.facebook.react.views.scroll.ScrollEventType.MOMENTUM_BEGIN;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r0 = $SwitchMap$com$facebook$react$views$scroll$ScrollEventType;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = com.facebook.react.views.scroll.ScrollEventType.MOMENTUM_END;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0040 }
            r2 = 5;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.scroll.ScrollEventType.1.<clinit>():void");
        }
    }

    public static String getJSEventName(ScrollEventType scrollEventType) {
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$views$scroll$ScrollEventType[scrollEventType.ordinal()];
        if (i == 1) {
            return "topScrollBeginDrag";
        }
        if (i == 2) {
            return "topScrollEndDrag";
        }
        if (i == 3) {
            return "topScroll";
        }
        if (i == 4) {
            return "topMomentumScrollBegin";
        }
        if (i == 5) {
            return "topMomentumScrollEnd";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported ScrollEventType: ");
        stringBuilder.append(scrollEventType);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
