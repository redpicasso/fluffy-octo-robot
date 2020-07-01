package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.SurfaceTexture;
import android.os.Build.VERSION;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import javax.annotation.Nullable;

public class ARTSurfaceViewShadowNode extends LayoutShadowNode implements SurfaceTextureListener, LifecycleEventListener {
    @Nullable
    private Integer mBackgroundColor;
    @Nullable
    private Surface mSurface;

    public boolean isVirtual() {
        return false;
    }

    public boolean isVirtualAnchor() {
        return true;
    }

    public void onHostDestroy() {
    }

    public void onHostPause() {
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    @ReactProp(customType = "Color", name = "backgroundColor")
    public void setBackgroundColor(Integer num) {
        this.mBackgroundColor = num;
        markUpdated();
    }

    public void onCollectExtraUpdates(UIViewOperationQueue uIViewOperationQueue) {
        super.onCollectExtraUpdates(uIViewOperationQueue);
        drawOutput(false);
        uIViewOperationQueue.enqueueUpdateExtraData(getReactTag(), this);
    }

    private void drawOutput(boolean z) {
        Object e;
        StringBuilder stringBuilder;
        Surface surface = this.mSurface;
        if (surface == null || !surface.isValid()) {
            markChildrenUpdatesSeen(this);
            return;
        }
        try {
            Canvas lockCanvas = this.mSurface.lockCanvas(null);
            int i = 0;
            lockCanvas.drawColor(0, Mode.CLEAR);
            if (this.mBackgroundColor != null) {
                lockCanvas.drawColor(this.mBackgroundColor.intValue());
            }
            Paint paint = new Paint();
            while (i < getChildCount()) {
                ARTVirtualNode aRTVirtualNode = (ARTVirtualNode) getChildAt(i);
                aRTVirtualNode.draw(lockCanvas, paint, 1.0f);
                if (z) {
                    aRTVirtualNode.markUpdated();
                } else {
                    aRTVirtualNode.markUpdateSeen();
                }
                i++;
            }
            if (this.mSurface != null) {
                this.mSurface.unlockCanvasAndPost(lockCanvas);
            }
        } catch (IllegalArgumentException e2) {
            e = e2;
            stringBuilder = new StringBuilder();
            stringBuilder.append(e.getClass().getSimpleName());
            stringBuilder.append(" in Surface.unlockCanvasAndPost");
            FLog.e(ReactConstants.TAG, stringBuilder.toString());
        } catch (IllegalStateException e3) {
            e = e3;
            stringBuilder = new StringBuilder();
            stringBuilder.append(e.getClass().getSimpleName());
            stringBuilder.append(" in Surface.unlockCanvasAndPost");
            FLog.e(ReactConstants.TAG, stringBuilder.toString());
        }
    }

    public void setupSurfaceTextureListener(ARTSurfaceView aRTSurfaceView) {
        SurfaceTexture surfaceTexture = aRTSurfaceView.getSurfaceTexture();
        aRTSurfaceView.setSurfaceTextureListener(this);
        if (surfaceTexture != null && this.mSurface == null) {
            this.mSurface = new Surface(surfaceTexture);
            drawOutput(true);
        }
    }

    private void markChildrenUpdatesSeen(ReactShadowNode reactShadowNode) {
        for (int i = 0; i < reactShadowNode.getChildCount(); i++) {
            ReactShadowNode childAt = reactShadowNode.getChildAt(i);
            childAt.markUpdateSeen();
            markChildrenUpdatesSeen(childAt);
        }
    }

    public void setThemedContext(ThemedReactContext themedReactContext) {
        super.setThemedContext(themedReactContext);
        if (VERSION.SDK_INT > 24) {
            themedReactContext.addLifecycleEventListener(this);
        }
    }

    public void dispose() {
        super.dispose();
        if (VERSION.SDK_INT > 24) {
            getThemedContext().removeLifecycleEventListener(this);
        }
    }

    public void onHostResume() {
        drawOutput(false);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurface = new Surface(surfaceTexture);
        drawOutput(false);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.mSurface.release();
        this.mSurface = null;
        return true;
    }
}