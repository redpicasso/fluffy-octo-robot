package com.facebook.react.uimanager.layoutanimation;

enum LayoutAnimationType {
    CREATE,
    UPDATE,
    DELETE;

    /* renamed from: com.facebook.react.uimanager.layoutanimation.LayoutAnimationType$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$uimanager$layoutanimation$LayoutAnimationType = null;

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$facebook$react$uimanager$layoutanimation$LayoutAnimationType[com.facebook.react.uimanager.layoutanimation.LayoutAnimationType.DELETE.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.facebook.react.uimanager.layoutanimation.LayoutAnimationType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$react$uimanager$layoutanimation$LayoutAnimationType = r0;
            r0 = $SwitchMap$com$facebook$react$uimanager$layoutanimation$LayoutAnimationType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.react.uimanager.layoutanimation.LayoutAnimationType.CREATE;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$react$uimanager$layoutanimation$LayoutAnimationType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.react.uimanager.layoutanimation.LayoutAnimationType.UPDATE;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$react$uimanager$layoutanimation$LayoutAnimationType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.react.uimanager.layoutanimation.LayoutAnimationType.DELETE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.layoutanimation.LayoutAnimationType.1.<clinit>():void");
        }
    }

    public static String toString(LayoutAnimationType layoutAnimationType) {
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$uimanager$layoutanimation$LayoutAnimationType[layoutAnimationType.ordinal()];
        if (i == 1) {
            return "create";
        }
        if (i == 2) {
            return "update";
        }
        if (i == 3) {
            return "delete";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported LayoutAnimationType: ");
        stringBuilder.append(layoutAnimationType);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
