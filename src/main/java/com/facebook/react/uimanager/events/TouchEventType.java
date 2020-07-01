package com.facebook.react.uimanager.events;

public enum TouchEventType {
    START,
    END,
    MOVE,
    CANCEL;

    /* renamed from: com.facebook.react.uimanager.events.TouchEventType$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$uimanager$events$TouchEventType = null;

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:8:?, code:
            $SwitchMap$com$facebook$react$uimanager$events$TouchEventType[com.facebook.react.uimanager.events.TouchEventType.CANCEL.ordinal()] = 4;
     */
        static {
            /*
            r0 = com.facebook.react.uimanager.events.TouchEventType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$react$uimanager$events$TouchEventType = r0;
            r0 = $SwitchMap$com$facebook$react$uimanager$events$TouchEventType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.react.uimanager.events.TouchEventType.START;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$react$uimanager$events$TouchEventType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.react.uimanager.events.TouchEventType.END;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$react$uimanager$events$TouchEventType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.react.uimanager.events.TouchEventType.MOVE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$facebook$react$uimanager$events$TouchEventType;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.facebook.react.uimanager.events.TouchEventType.CANCEL;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.events.TouchEventType.1.<clinit>():void");
        }
    }

    public static String getJSEventName(TouchEventType touchEventType) {
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$uimanager$events$TouchEventType[touchEventType.ordinal()];
        if (i == 1) {
            return "topTouchStart";
        }
        if (i == 2) {
            return TouchesHelper.TOP_TOUCH_END_KEY;
        }
        if (i == 3) {
            return "topTouchMove";
        }
        if (i == 4) {
            return TouchesHelper.TOP_TOUCH_CANCEL_KEY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected type ");
        stringBuilder.append(touchEventType);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
