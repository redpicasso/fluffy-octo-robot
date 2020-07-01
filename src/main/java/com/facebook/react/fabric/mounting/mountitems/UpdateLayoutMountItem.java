package com.facebook.react.fabric.mounting.mountitems;

import com.facebook.react.fabric.mounting.MountingManager;

public class UpdateLayoutMountItem implements MountItem {
    private final int mHeight;
    private final int mReactTag;
    private final int mWidth;
    private final int mX;
    private final int mY;

    public UpdateLayoutMountItem(int i, int i2, int i3, int i4, int i5) {
        this.mReactTag = i;
        this.mX = i2;
        this.mY = i3;
        this.mWidth = i4;
        this.mHeight = i5;
    }

    public void execute(MountingManager mountingManager) {
        mountingManager.updateLayout(this.mReactTag, this.mX, this.mY, this.mWidth, this.mHeight);
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UpdateLayoutMountItem [");
        stringBuilder.append(this.mReactTag);
        stringBuilder.append("] - x: ");
        stringBuilder.append(this.mX);
        stringBuilder.append(" - y: ");
        stringBuilder.append(this.mY);
        stringBuilder.append(" - height: ");
        stringBuilder.append(this.mHeight);
        stringBuilder.append(" - width: ");
        stringBuilder.append(this.mWidth);
        return stringBuilder.toString();
    }
}
