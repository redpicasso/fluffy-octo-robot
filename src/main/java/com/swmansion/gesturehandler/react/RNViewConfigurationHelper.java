package com.swmansion.gesturehandler.react;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ReactPointerEventsView;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.views.view.ReactViewGroup;
import com.swmansion.gesturehandler.PointerEventsConfig;
import com.swmansion.gesturehandler.ViewConfigurationHelper;

public class RNViewConfigurationHelper implements ViewConfigurationHelper {

    /* renamed from: com.swmansion.gesturehandler.react.RNViewConfigurationHelper$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$uimanager$PointerEvents = new int[PointerEvents.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$facebook$react$uimanager$PointerEvents[com.facebook.react.uimanager.PointerEvents.NONE.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.facebook.react.uimanager.PointerEvents.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$react$uimanager$PointerEvents = r0;
            r0 = $SwitchMap$com$facebook$react$uimanager$PointerEvents;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.react.uimanager.PointerEvents.BOX_ONLY;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$react$uimanager$PointerEvents;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.react.uimanager.PointerEvents.BOX_NONE;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$react$uimanager$PointerEvents;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.react.uimanager.PointerEvents.NONE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.swmansion.gesturehandler.react.RNViewConfigurationHelper.1.<clinit>():void");
        }
    }

    public PointerEventsConfig getPointerEventsConfigForView(View view) {
        PointerEvents pointerEvents = view instanceof ReactPointerEventsView ? ((ReactPointerEventsView) view).getPointerEvents() : PointerEvents.AUTO;
        if (!view.isEnabled()) {
            if (pointerEvents == PointerEvents.AUTO) {
                return PointerEventsConfig.BOX_NONE;
            }
            if (pointerEvents == PointerEvents.BOX_ONLY) {
                return PointerEventsConfig.NONE;
            }
        }
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$uimanager$PointerEvents[pointerEvents.ordinal()];
        if (i == 1) {
            return PointerEventsConfig.BOX_ONLY;
        }
        if (i == 2) {
            return PointerEventsConfig.BOX_NONE;
        }
        if (i != 3) {
            return PointerEventsConfig.AUTO;
        }
        return PointerEventsConfig.NONE;
    }

    public View getChildInDrawingOrderAtIndex(ViewGroup viewGroup, int i) {
        if (viewGroup instanceof ReactViewGroup) {
            return viewGroup.getChildAt(((ReactViewGroup) viewGroup).getZIndexMappedChildIndex(i));
        }
        return viewGroup.getChildAt(i);
    }

    public boolean isViewClippingChildren(ViewGroup viewGroup) {
        if (VERSION.SDK_INT < 18 || viewGroup.getClipChildren()) {
            return true;
        }
        if (!(viewGroup instanceof ReactViewGroup)) {
            return false;
        }
        return ViewProps.HIDDEN.equals(((ReactViewGroup) viewGroup).getOverflow());
    }
}
