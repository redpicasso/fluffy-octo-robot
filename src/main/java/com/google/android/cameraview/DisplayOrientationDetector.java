package com.google.android.cameraview;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.OrientationEventListener;

abstract class DisplayOrientationDetector {
    static final SparseIntArray DISPLAY_ORIENTATIONS = new SparseIntArray();
    Display mDisplay;
    private int mLastKnownDeviceOrientation = 0;
    private int mLastKnownDisplayOrientation = 0;
    private final OrientationEventListener mOrientationEventListener;

    public abstract void onDisplayOrientationChanged(int i, int i2);

    static {
        DISPLAY_ORIENTATIONS.put(0, 0);
        DISPLAY_ORIENTATIONS.put(1, 90);
        DISPLAY_ORIENTATIONS.put(2, 180);
        DISPLAY_ORIENTATIONS.put(3, 270);
    }

    public DisplayOrientationDetector(Context context) {
        this.mOrientationEventListener = new OrientationEventListener(context) {
            private int mLastKnownRotation = -1;

            /* JADX WARNING: Removed duplicated region for block: B:22:0x0037  */
            /* JADX WARNING: Removed duplicated region for block: B:25:0x0049  */
            /* JADX WARNING: Removed duplicated region for block: B:30:? A:{SYNTHETIC, RETURN} */
            /* JADX WARNING: Removed duplicated region for block: B:27:0x004e  */
            /* JADX WARNING: Removed duplicated region for block: B:22:0x0037  */
            /* JADX WARNING: Removed duplicated region for block: B:25:0x0049  */
            /* JADX WARNING: Removed duplicated region for block: B:27:0x004e  */
            /* JADX WARNING: Removed duplicated region for block: B:30:? A:{SYNTHETIC, RETURN} */
            /* JADX WARNING: Removed duplicated region for block: B:22:0x0037  */
            /* JADX WARNING: Removed duplicated region for block: B:25:0x0049  */
            /* JADX WARNING: Removed duplicated region for block: B:30:? A:{SYNTHETIC, RETURN} */
            /* JADX WARNING: Removed duplicated region for block: B:27:0x004e  */
            public void onOrientationChanged(int r5) {
                /*
                r4 = this;
                r0 = -1;
                if (r5 == r0) goto L_0x0059;
            L_0x0003:
                r0 = com.google.android.cameraview.DisplayOrientationDetector.this;
                r0 = r0.mDisplay;
                if (r0 != 0) goto L_0x000a;
            L_0x0009:
                goto L_0x0059;
            L_0x000a:
                r0 = 315; // 0x13b float:4.41E-43 double:1.556E-321;
                r1 = 0;
                if (r5 > r0) goto L_0x002d;
            L_0x000f:
                r2 = 45;
                if (r5 >= r2) goto L_0x0014;
            L_0x0013:
                goto L_0x002d;
            L_0x0014:
                r3 = 135; // 0x87 float:1.89E-43 double:6.67E-322;
                if (r5 <= r2) goto L_0x001d;
            L_0x0018:
                if (r5 >= r3) goto L_0x001d;
            L_0x001a:
                r5 = 90;
                goto L_0x002e;
            L_0x001d:
                r2 = 225; // 0xe1 float:3.15E-43 double:1.11E-321;
                if (r5 <= r3) goto L_0x0026;
            L_0x0021:
                if (r5 >= r2) goto L_0x0026;
            L_0x0023:
                r5 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
                goto L_0x002e;
            L_0x0026:
                if (r5 <= r2) goto L_0x002d;
            L_0x0028:
                if (r5 >= r0) goto L_0x002d;
            L_0x002a:
                r5 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
                goto L_0x002e;
            L_0x002d:
                r5 = 0;
            L_0x002e:
                r0 = com.google.android.cameraview.DisplayOrientationDetector.this;
                r0 = r0.mLastKnownDeviceOrientation;
                r2 = 1;
                if (r0 == r5) goto L_0x003d;
            L_0x0037:
                r0 = com.google.android.cameraview.DisplayOrientationDetector.this;
                r0.mLastKnownDeviceOrientation = r5;
                r1 = 1;
            L_0x003d:
                r5 = com.google.android.cameraview.DisplayOrientationDetector.this;
                r5 = r5.mDisplay;
                r5 = r5.getRotation();
                r0 = r4.mLastKnownRotation;
                if (r0 == r5) goto L_0x004c;
            L_0x0049:
                r4.mLastKnownRotation = r5;
                r1 = 1;
            L_0x004c:
                if (r1 == 0) goto L_0x0059;
            L_0x004e:
                r0 = com.google.android.cameraview.DisplayOrientationDetector.this;
                r1 = com.google.android.cameraview.DisplayOrientationDetector.DISPLAY_ORIENTATIONS;
                r5 = r1.get(r5);
                r0.dispatchOnDisplayOrientationChanged(r5);
            L_0x0059:
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.cameraview.DisplayOrientationDetector.1.onOrientationChanged(int):void");
            }
        };
    }

    public void enable(Display display) {
        this.mDisplay = display;
        this.mOrientationEventListener.enable();
        dispatchOnDisplayOrientationChanged(DISPLAY_ORIENTATIONS.get(display.getRotation()));
    }

    public void disable() {
        this.mOrientationEventListener.disable();
        this.mDisplay = null;
    }

    public int getLastKnownDisplayOrientation() {
        return this.mLastKnownDisplayOrientation;
    }

    void dispatchOnDisplayOrientationChanged(int i) {
        this.mLastKnownDisplayOrientation = i;
        onDisplayOrientationChanged(i, this.mLastKnownDeviceOrientation);
    }
}
