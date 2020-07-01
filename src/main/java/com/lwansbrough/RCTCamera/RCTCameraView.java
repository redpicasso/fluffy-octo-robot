package com.lwansbrough.RCTCamera;

import android.content.Context;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import java.util.List;

public class RCTCameraView extends ViewGroup {
    private int _actualDeviceOrientation = -1;
    private int _aspect = 1;
    private int _captureMode = 0;
    private String _captureQuality = RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_HIGH;
    private boolean _clearWindowBackground = false;
    private final Context _context;
    private int _flashMode = -1;
    private final OrientationEventListener _orientationListener;
    private int _torchMode = -1;
    private RCTCameraViewFinder _viewFinder = null;
    private int _zoom = 0;

    public RCTCameraView(Context context) {
        super(context);
        this._context = context;
        RCTCamera.createInstance(getDeviceOrientation(context));
        this._orientationListener = new OrientationEventListener(context, 3) {
            public void onOrientationChanged(int i) {
                RCTCameraView rCTCameraView = RCTCameraView.this;
                if (rCTCameraView.setActualDeviceOrientation(rCTCameraView._context)) {
                    RCTCameraView.this.layoutViewFinder();
                }
            }
        };
        if (this._orientationListener.canDetectOrientation()) {
            this._orientationListener.enable();
        } else {
            this._orientationListener.disable();
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutViewFinder(i, i2, i3, i4);
    }

    public void onViewAdded(View view) {
        View view2 = this._viewFinder;
        if (view2 != view) {
            removeView(view2);
            addView(this._viewFinder, 0);
        }
    }

    public void setAspect(int i) {
        this._aspect = i;
        layoutViewFinder();
    }

    public void setCameraType(int i) {
        RCTCameraViewFinder rCTCameraViewFinder = this._viewFinder;
        if (rCTCameraViewFinder != null) {
            rCTCameraViewFinder.setCameraType(i);
            RCTCamera.getInstance().adjustPreviewLayout(i);
            return;
        }
        this._viewFinder = new RCTCameraViewFinder(this._context, i);
        i = this._flashMode;
        if (-1 != i) {
            this._viewFinder.setFlashMode(i);
        }
        i = this._torchMode;
        if (-1 != i) {
            this._viewFinder.setTorchMode(i);
        }
        i = this._zoom;
        if (i != 0) {
            this._viewFinder.setZoom(i);
        }
        this._viewFinder.setClearWindowBackground(this._clearWindowBackground);
        addView(this._viewFinder);
    }

    public void setCaptureMode(int i) {
        this._captureMode = i;
        RCTCameraViewFinder rCTCameraViewFinder = this._viewFinder;
        if (rCTCameraViewFinder != null) {
            rCTCameraViewFinder.setCaptureMode(i);
        }
    }

    public void setCaptureQuality(String str) {
        this._captureQuality = str;
        RCTCameraViewFinder rCTCameraViewFinder = this._viewFinder;
        if (rCTCameraViewFinder != null) {
            rCTCameraViewFinder.setCaptureQuality(str);
        }
    }

    public void setTorchMode(int i) {
        this._torchMode = i;
        RCTCameraViewFinder rCTCameraViewFinder = this._viewFinder;
        if (rCTCameraViewFinder != null) {
            rCTCameraViewFinder.setTorchMode(i);
        }
    }

    public void setFlashMode(int i) {
        this._flashMode = i;
        RCTCameraViewFinder rCTCameraViewFinder = this._viewFinder;
        if (rCTCameraViewFinder != null) {
            rCTCameraViewFinder.setFlashMode(i);
        }
    }

    public void setZoom(int i) {
        this._zoom = i;
        RCTCameraViewFinder rCTCameraViewFinder = this._viewFinder;
        if (rCTCameraViewFinder != null) {
            rCTCameraViewFinder.setZoom(i);
        }
    }

    public void setOrientation(int i) {
        RCTCamera.getInstance().setOrientation(i);
        if (this._viewFinder != null) {
            layoutViewFinder();
        }
    }

    public void setBarcodeScannerEnabled(boolean z) {
        RCTCamera.getInstance().setBarcodeScannerEnabled(z);
    }

    public void setBarCodeTypes(List<String> list) {
        RCTCamera.getInstance().setBarCodeTypes(list);
    }

    public void setClearWindowBackground(boolean z) {
        this._clearWindowBackground = z;
        RCTCameraViewFinder rCTCameraViewFinder = this._viewFinder;
        if (rCTCameraViewFinder != null) {
            rCTCameraViewFinder.setClearWindowBackground(z);
        }
    }

    public void stopPreview() {
        RCTCameraViewFinder rCTCameraViewFinder = this._viewFinder;
        if (rCTCameraViewFinder != null) {
            rCTCameraViewFinder.stopPreview();
        }
    }

    public void startPreview() {
        RCTCameraViewFinder rCTCameraViewFinder = this._viewFinder;
        if (rCTCameraViewFinder != null) {
            rCTCameraViewFinder.startPreview();
        }
    }

    private boolean setActualDeviceOrientation(Context context) {
        int deviceOrientation = getDeviceOrientation(context);
        if (this._actualDeviceOrientation == deviceOrientation) {
            return false;
        }
        this._actualDeviceOrientation = deviceOrientation;
        RCTCamera.getInstance().setActualDeviceOrientation(this._actualDeviceOrientation);
        return true;
    }

    private int getDeviceOrientation(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getOrientation();
    }

    private void layoutViewFinder() {
        layoutViewFinder(getLeft(), getTop(), getRight(), getBottom());
    }

    /* JADX WARNING: Missing block: B:10:0x001d, code:
            if (r0 > r2) goto L_0x002c;
     */
    /* JADX WARNING: Missing block: B:12:0x002a, code:
            if (r0 < r2) goto L_0x002c;
     */
    /* JADX WARNING: Missing block: B:13:0x002c, code:
            r9 = (int) (r2 / r8);
            r8 = (int) r6;
     */
    /* JADX WARNING: Missing block: B:14:0x0030, code:
            r8 = (int) r0;
     */
    private void layoutViewFinder(int r6, int r7, int r8, int r9) {
        /*
        r5 = this;
        r0 = r5._viewFinder;
        if (r0 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r8 = r8 - r6;
        r6 = (float) r8;
        r9 = r9 - r7;
        r7 = (float) r9;
        r8 = r5._aspect;
        if (r8 == 0) goto L_0x0020;
    L_0x000d:
        r9 = 1;
        if (r8 == r9) goto L_0x0013;
    L_0x0010:
        r8 = (int) r6;
    L_0x0011:
        r9 = (int) r7;
        goto L_0x0032;
    L_0x0013:
        r8 = r0.getRatio();
        r0 = (double) r7;
        r0 = r0 * r8;
        r2 = (double) r6;
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r4 <= 0) goto L_0x0030;
    L_0x001f:
        goto L_0x002c;
    L_0x0020:
        r8 = r0.getRatio();
        r0 = (double) r7;
        r0 = r0 * r8;
        r2 = (double) r6;
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r4 >= 0) goto L_0x0030;
    L_0x002c:
        r2 = r2 / r8;
        r9 = (int) r2;
        r8 = (int) r6;
        goto L_0x0032;
    L_0x0030:
        r8 = (int) r0;
        goto L_0x0011;
    L_0x0032:
        r0 = (float) r8;
        r0 = r6 - r0;
        r1 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r0 / r1;
        r0 = (int) r0;
        r2 = (float) r9;
        r2 = r7 - r2;
        r2 = r2 / r1;
        r1 = (int) r2;
        r2 = com.lwansbrough.RCTCamera.RCTCamera.getInstance();
        r3 = r5._viewFinder;
        r3 = r3.getCameraType();
        r6 = (int) r6;
        r7 = (int) r7;
        r2.setPreviewVisibleSize(r3, r6, r7);
        r6 = r5._viewFinder;
        r8 = r8 + r0;
        r9 = r9 + r1;
        r6.layout(r0, r1, r8, r9);
        r6 = r5.getLeft();
        r7 = r5.getTop();
        r8 = r5.getRight();
        r9 = r5.getBottom();
        r5.postInvalidate(r6, r7, r8, r9);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lwansbrough.RCTCamera.RCTCameraView.layoutViewFinder(int, int, int, int):void");
    }
}
