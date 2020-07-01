package com.google.android.cameraview;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.CamcorderProfile;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.ParcelableCompat;
import androidx.core.os.ParcelableCompatCreatorCallbacks;
import androidx.core.view.ViewCompat;
import com.facebook.react.bridge.ReadableMap;
import com.google.common.primitives.Ints;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;

public class CameraView extends FrameLayout {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int FACING_BACK = 0;
    public static final int FACING_FRONT = 1;
    public static final int FLASH_AUTO = 3;
    public static final int FLASH_OFF = 0;
    public static final int FLASH_ON = 1;
    public static final int FLASH_RED_EYE = 4;
    public static final int FLASH_TORCH = 2;
    private boolean mAdjustViewBounds;
    protected Handler mBgHandler;
    protected HandlerThread mBgThread;
    private final CallbackBridge mCallbacks;
    private Context mContext;
    private final DisplayOrientationDetector mDisplayOrientationDetector;
    CameraViewImpl mImpl;

    public static abstract class Callback {
        public void onCameraClosed(CameraView cameraView) {
        }

        public void onCameraOpened(CameraView cameraView) {
        }

        public void onFramePreview(CameraView cameraView, byte[] bArr, int i, int i2, int i3) {
        }

        public void onMountError(CameraView cameraView) {
        }

        public void onPictureTaken(CameraView cameraView, byte[] bArr, int i) {
        }

        public void onRecordingEnd(CameraView cameraView) {
        }

        public void onRecordingStart(CameraView cameraView, String str, int i, int i2) {
        }

        public void onVideoRecorded(CameraView cameraView, String str, int i, int i2) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Facing {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flash {
    }

    protected static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        });
        boolean autoFocus;
        String cameraId;
        float exposure;
        int facing;
        int flash;
        float focusDepth;
        Size pictureSize;
        boolean playSoundOnCapture;
        AspectRatio ratio;
        boolean scanning;
        int whiteBalance;
        float zoom;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel);
            this.facing = parcel.readInt();
            this.cameraId = parcel.readString();
            this.ratio = (AspectRatio) parcel.readParcelable(classLoader);
            boolean z = true;
            this.autoFocus = parcel.readByte() != (byte) 0;
            this.flash = parcel.readInt();
            this.exposure = parcel.readFloat();
            this.focusDepth = parcel.readFloat();
            this.zoom = parcel.readFloat();
            this.whiteBalance = parcel.readInt();
            this.playSoundOnCapture = parcel.readByte() != (byte) 0;
            if (parcel.readByte() == (byte) 0) {
                z = false;
            }
            this.scanning = z;
            this.pictureSize = (Size) parcel.readParcelable(classLoader);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.facing);
            parcel.writeString(this.cameraId);
            parcel.writeParcelable(this.ratio, 0);
            parcel.writeByte((byte) this.autoFocus);
            parcel.writeInt(this.flash);
            parcel.writeFloat(this.exposure);
            parcel.writeFloat(this.focusDepth);
            parcel.writeFloat(this.zoom);
            parcel.writeInt(this.whiteBalance);
            parcel.writeByte((byte) this.playSoundOnCapture);
            parcel.writeByte((byte) this.scanning);
            parcel.writeParcelable(this.pictureSize, i);
        }
    }

    private class CallbackBridge implements Callback {
        private final ArrayList<Callback> mCallbacks = new ArrayList();
        private boolean mRequestLayoutOnOpen;

        CallbackBridge() {
        }

        public void add(Callback callback) {
            this.mCallbacks.add(callback);
        }

        public void remove(Callback callback) {
            this.mCallbacks.remove(callback);
        }

        public void onCameraOpened() {
            if (this.mRequestLayoutOnOpen) {
                this.mRequestLayoutOnOpen = false;
                CameraView.this.requestLayout();
            }
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onCameraOpened(CameraView.this);
            }
        }

        public void onCameraClosed() {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onCameraClosed(CameraView.this);
            }
        }

        public void onPictureTaken(byte[] bArr, int i) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onPictureTaken(CameraView.this, bArr, i);
            }
        }

        public void onRecordingStart(String str, int i, int i2) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onRecordingStart(CameraView.this, str, i, i2);
            }
        }

        public void onRecordingEnd() {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onRecordingEnd(CameraView.this);
            }
        }

        public void onVideoRecorded(String str, int i, int i2) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onVideoRecorded(CameraView.this, str, i, i2);
            }
        }

        public void onFramePreview(byte[] bArr, int i, int i2, int i3) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onFramePreview(CameraView.this, bArr, i, i2, i3);
            }
        }

        public void onMountError() {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onMountError(CameraView.this);
            }
        }

        public void reserveRequestLayoutOnOpen() {
            this.mRequestLayoutOnOpen = true;
        }
    }

    public CameraView(Context context, boolean z) {
        this(context, null, z);
    }

    public CameraView(Context context, AttributeSet attributeSet, boolean z) {
        this(context, attributeSet, 0, z);
    }

    public CameraView(Context context, AttributeSet attributeSet, int i, boolean z) {
        super(context, attributeSet, i);
        this.mBgThread = new HandlerThread("RNCamera-Handler-Thread");
        this.mBgThread.start();
        this.mBgHandler = new Handler(this.mBgThread.getLooper());
        if (isInEditMode()) {
            this.mCallbacks = null;
            this.mDisplayOrientationDetector = null;
            return;
        }
        this.mAdjustViewBounds = true;
        this.mContext = context;
        PreviewImpl createPreviewImpl = createPreviewImpl(context);
        this.mCallbacks = new CallbackBridge();
        if (z || VERSION.SDK_INT < 21 || Camera2.isLegacy(context)) {
            this.mImpl = new Camera1(this.mCallbacks, createPreviewImpl, this.mBgHandler);
        } else if (VERSION.SDK_INT < 23) {
            this.mImpl = new Camera2(this.mCallbacks, createPreviewImpl, context, this.mBgHandler);
        } else {
            this.mImpl = new Camera2Api23(this.mCallbacks, createPreviewImpl, context, this.mBgHandler);
        }
        this.mDisplayOrientationDetector = new DisplayOrientationDetector(context) {
            public void onDisplayOrientationChanged(int i, int i2) {
                CameraView.this.mImpl.setDisplayOrientation(i);
                CameraView.this.mImpl.setDeviceOrientation(i2);
            }
        };
    }

    public void cleanup() {
        if (this.mBgThread != null) {
            if (VERSION.SDK_INT < 18) {
                this.mBgThread.quit();
            } else {
                this.mBgThread.quitSafely();
            }
            this.mBgThread = null;
        }
    }

    @NonNull
    private PreviewImpl createPreviewImpl(Context context) {
        if (VERSION.SDK_INT < 14) {
            return new SurfaceViewPreview(context, this);
        }
        return new TextureViewPreview(context, this);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.mDisplayOrientationDetector.enable(ViewCompat.getDisplay(this));
        }
    }

    protected void onDetachedFromWindow() {
        if (!isInEditMode()) {
            this.mDisplayOrientationDetector.disable();
        }
        super.onDetachedFromWindow();
    }

    protected void onMeasure(int i, int i2) {
        if (isInEditMode()) {
            super.onMeasure(i, i2);
            return;
        }
        if (!this.mAdjustViewBounds) {
            super.onMeasure(i, i2);
        } else if (isCameraOpened()) {
            int mode = MeasureSpec.getMode(i);
            int mode2 = MeasureSpec.getMode(i2);
            if (mode == Ints.MAX_POWER_OF_TWO && mode2 != Ints.MAX_POWER_OF_TWO) {
                mode = (int) (((float) MeasureSpec.getSize(i)) * getAspectRatio().toFloat());
                if (mode2 == Integer.MIN_VALUE) {
                    mode = Math.min(mode, MeasureSpec.getSize(i2));
                }
                super.onMeasure(i, MeasureSpec.makeMeasureSpec(mode, Ints.MAX_POWER_OF_TWO));
            } else if (mode == Ints.MAX_POWER_OF_TWO || mode2 != Ints.MAX_POWER_OF_TWO) {
                super.onMeasure(i, i2);
            } else {
                mode2 = (int) (((float) MeasureSpec.getSize(i2)) * getAspectRatio().toFloat());
                if (mode == Integer.MIN_VALUE) {
                    mode2 = Math.min(mode2, MeasureSpec.getSize(i));
                }
                super.onMeasure(MeasureSpec.makeMeasureSpec(mode2, Ints.MAX_POWER_OF_TWO), i2);
            }
        } else {
            this.mCallbacks.reserveRequestLayoutOnOpen();
            super.onMeasure(i, i2);
            return;
        }
        i = getMeasuredWidth();
        i2 = getMeasuredHeight();
        AspectRatio aspectRatio = getAspectRatio();
        if (this.mDisplayOrientationDetector.getLastKnownDisplayOrientation() % 180 == 0) {
            aspectRatio = aspectRatio.inverse();
        }
        if (i2 < (aspectRatio.getY() * i) / aspectRatio.getX()) {
            this.mImpl.getView().measure(MeasureSpec.makeMeasureSpec(i, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec((i * aspectRatio.getY()) / aspectRatio.getX(), Ints.MAX_POWER_OF_TWO));
        } else {
            this.mImpl.getView().measure(MeasureSpec.makeMeasureSpec((aspectRatio.getX() * i2) / aspectRatio.getY(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(i2, Ints.MAX_POWER_OF_TWO));
        }
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.facing = getFacing();
        savedState.cameraId = getCameraId();
        savedState.ratio = getAspectRatio();
        savedState.autoFocus = getAutoFocus();
        savedState.flash = getFlash();
        savedState.exposure = getExposureCompensation();
        savedState.focusDepth = getFocusDepth();
        savedState.zoom = getZoom();
        savedState.whiteBalance = getWhiteBalance();
        savedState.playSoundOnCapture = getPlaySoundOnCapture();
        savedState.scanning = getScanning();
        savedState.pictureSize = getPictureSize();
        return savedState;
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            setFacing(savedState.facing);
            setCameraId(savedState.cameraId);
            setAspectRatio(savedState.ratio);
            setAutoFocus(savedState.autoFocus);
            setFlash(savedState.flash);
            setExposureCompensation(savedState.exposure);
            setFocusDepth(savedState.focusDepth);
            setZoom(savedState.zoom);
            setWhiteBalance(savedState.whiteBalance);
            setPlaySoundOnCapture(savedState.playSoundOnCapture);
            setScanning(savedState.scanning);
            setPictureSize(savedState.pictureSize);
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public void setUsingCamera2Api(boolean z) {
        if (VERSION.SDK_INT >= 21) {
            boolean isCameraOpened = isCameraOpened();
            Parcelable onSaveInstanceState = onSaveInstanceState();
            if (z && !Camera2.isLegacy(this.mContext)) {
                if (isCameraOpened) {
                    stop();
                }
                if (VERSION.SDK_INT < 23) {
                    this.mImpl = new Camera2(this.mCallbacks, this.mImpl.mPreview, this.mContext, this.mBgHandler);
                } else {
                    this.mImpl = new Camera2Api23(this.mCallbacks, this.mImpl.mPreview, this.mContext, this.mBgHandler);
                }
                onRestoreInstanceState(onSaveInstanceState);
            } else if (!(this.mImpl instanceof Camera1)) {
                if (isCameraOpened) {
                    stop();
                }
                this.mImpl = new Camera1(this.mCallbacks, this.mImpl.mPreview, this.mBgHandler);
            } else {
                return;
            }
            if (isCameraOpened) {
                start();
            }
        }
    }

    public void start() {
        this.mImpl.start();
    }

    public void stop() {
        this.mImpl.stop();
    }

    public boolean isCameraOpened() {
        return this.mImpl.isCameraOpened();
    }

    public void addCallback(@NonNull Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void removeCallback(@NonNull Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public void setAdjustViewBounds(boolean z) {
        if (this.mAdjustViewBounds != z) {
            this.mAdjustViewBounds = z;
            requestLayout();
        }
    }

    public boolean getAdjustViewBounds() {
        return this.mAdjustViewBounds;
    }

    public View getView() {
        CameraViewImpl cameraViewImpl = this.mImpl;
        return cameraViewImpl != null ? cameraViewImpl.getView() : null;
    }

    public void setFacing(int i) {
        this.mImpl.setFacing(i);
    }

    public int getFacing() {
        return this.mImpl.getFacing();
    }

    public void setCameraId(String str) {
        this.mImpl.setCameraId(str);
    }

    public String getCameraId() {
        return this.mImpl.getCameraId();
    }

    public Set<AspectRatio> getSupportedAspectRatios() {
        return this.mImpl.getSupportedAspectRatios();
    }

    public List<Properties> getCameraIds() {
        return this.mImpl.getCameraIds();
    }

    public void setAspectRatio(@NonNull AspectRatio aspectRatio) {
        if (this.mImpl.setAspectRatio(aspectRatio)) {
            requestLayout();
        }
    }

    @Nullable
    public AspectRatio getAspectRatio() {
        return this.mImpl.getAspectRatio();
    }

    public SortedSet<Size> getAvailablePictureSizes(@NonNull AspectRatio aspectRatio) {
        return this.mImpl.getAvailablePictureSizes(aspectRatio);
    }

    public void setPictureSize(@NonNull Size size) {
        this.mImpl.setPictureSize(size);
    }

    public Size getPictureSize() {
        return this.mImpl.getPictureSize();
    }

    public void setAutoFocus(boolean z) {
        this.mImpl.setAutoFocus(z);
    }

    public boolean getAutoFocus() {
        return this.mImpl.getAutoFocus();
    }

    public void setFlash(int i) {
        this.mImpl.setFlash(i);
    }

    public ArrayList<int[]> getSupportedPreviewFpsRange() {
        return this.mImpl.getSupportedPreviewFpsRange();
    }

    public int getFlash() {
        return this.mImpl.getFlash();
    }

    public void setExposureCompensation(float f) {
        this.mImpl.setExposureCompensation(f);
    }

    public float getExposureCompensation() {
        return this.mImpl.getExposureCompensation();
    }

    public int getCameraOrientation() {
        return this.mImpl.getCameraOrientation();
    }

    public void setAutoFocusPointOfInterest(float f, float f2) {
        this.mImpl.setFocusArea(f, f2);
    }

    public void setFocusDepth(float f) {
        this.mImpl.setFocusDepth(f);
    }

    public float getFocusDepth() {
        return this.mImpl.getFocusDepth();
    }

    public void setZoom(float f) {
        this.mImpl.setZoom(f);
    }

    public float getZoom() {
        return this.mImpl.getZoom();
    }

    public void setWhiteBalance(int i) {
        this.mImpl.setWhiteBalance(i);
    }

    public int getWhiteBalance() {
        return this.mImpl.getWhiteBalance();
    }

    public void setPlaySoundOnCapture(boolean z) {
        this.mImpl.setPlaySoundOnCapture(z);
    }

    public boolean getPlaySoundOnCapture() {
        return this.mImpl.getPlaySoundOnCapture();
    }

    public void setScanning(boolean z) {
        this.mImpl.setScanning(z);
    }

    public boolean getScanning() {
        return this.mImpl.getScanning();
    }

    public void takePicture(ReadableMap readableMap) {
        this.mImpl.takePicture(readableMap);
    }

    public boolean record(String str, int i, int i2, boolean z, CamcorderProfile camcorderProfile, int i3, int i4) {
        return this.mImpl.record(str, i, i2, z, camcorderProfile, i3, i4);
    }

    public void stopRecording() {
        this.mImpl.stopRecording();
    }

    public void resumePreview() {
        this.mImpl.resumePreview();
    }

    public void pausePreview() {
        this.mImpl.pausePreview();
    }

    public void setPreviewTexture(SurfaceTexture surfaceTexture) {
        this.mImpl.setPreviewTexture(surfaceTexture);
    }

    public Size getPreviewSize() {
        return this.mImpl.getPreviewSize();
    }
}
