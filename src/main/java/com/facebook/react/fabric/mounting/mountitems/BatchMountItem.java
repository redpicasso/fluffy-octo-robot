package com.facebook.react.fabric.mounting.mountitems;

import com.facebook.common.logging.FLog;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.fabric.FabricUIManager;
import com.facebook.react.fabric.mounting.MountingManager;
import com.facebook.systrace.Systrace;

@DoNotStrip
public class BatchMountItem implements MountItem {
    private final MountItem[] mMountItems;
    private final int mSize;

    public BatchMountItem(MountItem[] mountItemArr, int i) {
        if (mountItemArr == null) {
            throw new NullPointerException();
        } else if (i < 0 || i > mountItemArr.length) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid size received by parameter size: ");
            stringBuilder.append(i);
            stringBuilder.append(" items.size = ");
            stringBuilder.append(mountItemArr.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else {
            this.mMountItems = mountItemArr;
            this.mSize = i;
        }
    }

    public void execute(MountingManager mountingManager) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FabricUIManager::mountViews - ");
        stringBuilder.append(this.mSize);
        stringBuilder.append(" items");
        Systrace.beginSection(0, stringBuilder.toString());
        for (int i = 0; i < this.mSize; i++) {
            MountItem mountItem = this.mMountItems[i];
            if (FabricUIManager.DEBUG) {
                String str = FabricUIManager.TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Executing mountItem: ");
                stringBuilder2.append(mountItem);
                FLog.d(str, stringBuilder2.toString());
            }
            mountItem.execute(mountingManager);
        }
        Systrace.endSection(0);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BatchMountItem - size ");
        stringBuilder.append(this.mMountItems.length);
        return stringBuilder.toString();
    }
}
