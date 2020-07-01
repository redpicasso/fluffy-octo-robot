package com.facebook.drawee.backends.pipeline.info;

import android.graphics.Rect;
import com.facebook.common.time.MonotonicClock;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.info.internal.ImagePerfControllerListener;
import com.facebook.drawee.backends.pipeline.info.internal.ImagePerfImageOriginListener;
import com.facebook.drawee.backends.pipeline.info.internal.ImagePerfRequestListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.imagepipeline.listener.ForwardingRequestListener;
import com.facebook.imagepipeline.listener.RequestListener;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;

public class ImagePerfMonitor {
    private boolean mEnabled;
    @Nullable
    private ForwardingRequestListener mForwardingRequestListener;
    @Nullable
    private ImageOriginListener mImageOriginListener;
    @Nullable
    private ImageOriginRequestListener mImageOriginRequestListener;
    @Nullable
    private ImagePerfControllerListener mImagePerfControllerListener;
    @Nullable
    private List<ImagePerfDataListener> mImagePerfDataListeners;
    @Nullable
    private ImagePerfRequestListener mImagePerfRequestListener;
    private final ImagePerfState mImagePerfState = new ImagePerfState();
    private final MonotonicClock mMonotonicClock;
    private final PipelineDraweeController mPipelineDraweeController;

    public ImagePerfMonitor(MonotonicClock monotonicClock, PipelineDraweeController pipelineDraweeController) {
        this.mMonotonicClock = monotonicClock;
        this.mPipelineDraweeController = pipelineDraweeController;
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
        ImageOriginListener imageOriginListener;
        ControllerListener controllerListener;
        RequestListener requestListener;
        if (z) {
            setupListeners();
            imageOriginListener = this.mImageOriginListener;
            if (imageOriginListener != null) {
                this.mPipelineDraweeController.addImageOriginListener(imageOriginListener);
            }
            controllerListener = this.mImagePerfControllerListener;
            if (controllerListener != null) {
                this.mPipelineDraweeController.addControllerListener(controllerListener);
            }
            requestListener = this.mForwardingRequestListener;
            if (requestListener != null) {
                this.mPipelineDraweeController.addRequestListener(requestListener);
                return;
            }
            return;
        }
        imageOriginListener = this.mImageOriginListener;
        if (imageOriginListener != null) {
            this.mPipelineDraweeController.removeImageOriginListener(imageOriginListener);
        }
        controllerListener = this.mImagePerfControllerListener;
        if (controllerListener != null) {
            this.mPipelineDraweeController.removeControllerListener(controllerListener);
        }
        requestListener = this.mForwardingRequestListener;
        if (requestListener != null) {
            this.mPipelineDraweeController.removeRequestListener(requestListener);
        }
    }

    public void addImagePerfDataListener(@Nullable ImagePerfDataListener imagePerfDataListener) {
        if (imagePerfDataListener != null) {
            if (this.mImagePerfDataListeners == null) {
                this.mImagePerfDataListeners = new LinkedList();
            }
            this.mImagePerfDataListeners.add(imagePerfDataListener);
        }
    }

    public void removeImagePerfDataListener(ImagePerfDataListener imagePerfDataListener) {
        List list = this.mImagePerfDataListeners;
        if (list != null) {
            list.remove(imagePerfDataListener);
        }
    }

    public void clearImagePerfDataListeners() {
        List list = this.mImagePerfDataListeners;
        if (list != null) {
            list.clear();
        }
    }

    public void notifyStatusUpdated(ImagePerfState imagePerfState, int i) {
        imagePerfState.setImageLoadStatus(i);
        if (this.mEnabled) {
            List list = this.mImagePerfDataListeners;
            if (list != null && !list.isEmpty()) {
                if (i == 3) {
                    addViewportData();
                }
                ImagePerfData snapshot = imagePerfState.snapshot();
                for (ImagePerfDataListener onImageLoadStatusUpdated : this.mImagePerfDataListeners) {
                    onImageLoadStatusUpdated.onImageLoadStatusUpdated(snapshot, i);
                }
            }
        }
    }

    public void notifyListenersOfVisibilityStateUpdate(ImagePerfState imagePerfState, int i) {
        if (this.mEnabled) {
            List list = this.mImagePerfDataListeners;
            if (list != null && !list.isEmpty()) {
                ImagePerfData snapshot = imagePerfState.snapshot();
                for (ImagePerfDataListener onImageVisibilityUpdated : this.mImagePerfDataListeners) {
                    onImageVisibilityUpdated.onImageVisibilityUpdated(snapshot, i);
                }
            }
        }
    }

    public void addViewportData() {
        DraweeHierarchy hierarchy = this.mPipelineDraweeController.getHierarchy();
        if (hierarchy != null && hierarchy.getTopLevelDrawable() != null) {
            Rect bounds = hierarchy.getTopLevelDrawable().getBounds();
            this.mImagePerfState.setOnScreenWidth(bounds.width());
            this.mImagePerfState.setOnScreenHeight(bounds.height());
        }
    }

    private void setupListeners() {
        if (this.mImagePerfControllerListener == null) {
            this.mImagePerfControllerListener = new ImagePerfControllerListener(this.mMonotonicClock, this.mImagePerfState, this);
        }
        if (this.mImagePerfRequestListener == null) {
            this.mImagePerfRequestListener = new ImagePerfRequestListener(this.mMonotonicClock, this.mImagePerfState);
        }
        if (this.mImageOriginListener == null) {
            this.mImageOriginListener = new ImagePerfImageOriginListener(this.mImagePerfState, this);
        }
        ImageOriginRequestListener imageOriginRequestListener = this.mImageOriginRequestListener;
        if (imageOriginRequestListener == null) {
            this.mImageOriginRequestListener = new ImageOriginRequestListener(this.mPipelineDraweeController.getId(), this.mImageOriginListener);
        } else {
            imageOriginRequestListener.init(this.mPipelineDraweeController.getId());
        }
        if (this.mForwardingRequestListener == null) {
            this.mForwardingRequestListener = new ForwardingRequestListener(this.mImagePerfRequestListener, this.mImageOriginRequestListener);
        }
    }

    public void reset() {
        clearImagePerfDataListeners();
        setEnabled(false);
        this.mImagePerfState.reset();
    }
}