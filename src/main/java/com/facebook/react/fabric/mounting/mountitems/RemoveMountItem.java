package com.facebook.react.fabric.mounting.mountitems;

import com.facebook.react.fabric.mounting.MountingManager;

public class RemoveMountItem implements MountItem {
    private int mIndex;
    private int mParentReactTag;
    private int mReactTag;

    public RemoveMountItem(int i, int i2, int i3) {
        this.mReactTag = i;
        this.mParentReactTag = i2;
        this.mIndex = i3;
    }

    public void execute(MountingManager mountingManager) {
        mountingManager.removeViewAt(this.mParentReactTag, this.mIndex);
    }

    public int getParentReactTag() {
        return this.mParentReactTag;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RemoveMountItem [");
        stringBuilder.append(this.mReactTag);
        stringBuilder.append("] - parentTag: ");
        stringBuilder.append(this.mParentReactTag);
        stringBuilder.append(" - index: ");
        stringBuilder.append(this.mIndex);
        return stringBuilder.toString();
    }
}
