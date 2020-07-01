package com.facebook.react.fabric.mounting.mountitems;

import com.facebook.react.fabric.mounting.MountingManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class CreateMountItem implements MountItem {
    private final String mComponent;
    private final ThemedReactContext mContext;
    private final boolean mIsLayoutable;
    private final int mReactTag;
    private final int mRootTag;

    public CreateMountItem(ThemedReactContext themedReactContext, int i, int i2, String str, boolean z) {
        this.mContext = themedReactContext;
        this.mComponent = str;
        this.mRootTag = i;
        this.mReactTag = i2;
        this.mIsLayoutable = z;
    }

    public void execute(MountingManager mountingManager) {
        mountingManager.createViewWithProps(this.mContext, this.mComponent, this.mReactTag, null, this.mIsLayoutable);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CreateMountItem [");
        stringBuilder.append(this.mReactTag);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
