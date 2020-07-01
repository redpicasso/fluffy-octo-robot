package com.facebook.react.fabric.mounting.mountitems;

import androidx.annotation.Nullable;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.fabric.FabricUIManager;
import com.facebook.react.fabric.mounting.MountingManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class PreAllocateViewMountItem implements MountItem {
    private final String mComponent;
    private final ThemedReactContext mContext;
    private final boolean mIsLayoutable;
    @Nullable
    private final ReadableMap mProps;
    private final int mReactTag;
    private final int mRootTag;

    public PreAllocateViewMountItem(ThemedReactContext themedReactContext, int i, int i2, String str, @Nullable ReadableMap readableMap, boolean z) {
        this.mContext = themedReactContext;
        this.mComponent = str;
        this.mRootTag = i;
        this.mProps = readableMap;
        this.mReactTag = i2;
        this.mIsLayoutable = z;
    }

    public void execute(MountingManager mountingManager) {
        if (FabricUIManager.DEBUG) {
            String str = FabricUIManager.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Executing pre-allocation of: ");
            stringBuilder.append(toString());
            FLog.d(str, stringBuilder.toString());
        }
        mountingManager.preallocateView(this.mContext, this.mComponent, this.mReactTag, this.mProps, this.mIsLayoutable);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PreAllocateViewMountItem [");
        stringBuilder.append(this.mReactTag);
        stringBuilder.append("] - component: ");
        stringBuilder.append(this.mComponent);
        stringBuilder.append(" rootTag: ");
        stringBuilder.append(this.mRootTag);
        stringBuilder.append(" isLayoutable: ");
        stringBuilder.append(this.mIsLayoutable);
        stringBuilder.append(" props: ");
        stringBuilder.append(this.mProps);
        return stringBuilder.toString();
    }
}
