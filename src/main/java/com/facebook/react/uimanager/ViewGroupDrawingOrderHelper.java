package com.facebook.react.uimanager;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.annotation.Nullable;

public class ViewGroupDrawingOrderHelper {
    @Nullable
    private int[] mDrawingOrderIndices;
    private int mNumberOfChildrenWithZIndex = 0;
    private final ViewGroup mViewGroup;

    public ViewGroupDrawingOrderHelper(ViewGroup viewGroup) {
        this.mViewGroup = viewGroup;
    }

    public void handleAddView(View view) {
        if (ViewGroupManager.getViewZIndex(view) != null) {
            this.mNumberOfChildrenWithZIndex++;
        }
        this.mDrawingOrderIndices = null;
    }

    public void handleRemoveView(View view) {
        if (ViewGroupManager.getViewZIndex(view) != null) {
            this.mNumberOfChildrenWithZIndex--;
        }
        this.mDrawingOrderIndices = null;
    }

    public boolean shouldEnableCustomDrawingOrder() {
        return this.mNumberOfChildrenWithZIndex > 0;
    }

    public int getChildDrawingOrder(int i, int i2) {
        if (this.mDrawingOrderIndices == null) {
            Object arrayList = new ArrayList();
            for (int i3 = 0; i3 < i; i3++) {
                arrayList.add(this.mViewGroup.getChildAt(i3));
            }
            Collections.sort(arrayList, new Comparator<View>() {
                public int compare(View view, View view2) {
                    Integer viewZIndex = ViewGroupManager.getViewZIndex(view);
                    Integer valueOf = Integer.valueOf(0);
                    if (viewZIndex == null) {
                        viewZIndex = valueOf;
                    }
                    Integer viewZIndex2 = ViewGroupManager.getViewZIndex(view2);
                    if (viewZIndex2 == null) {
                        viewZIndex2 = valueOf;
                    }
                    return viewZIndex.intValue() - viewZIndex2.intValue();
                }
            });
            this.mDrawingOrderIndices = new int[i];
            for (int i4 = 0; i4 < i; i4++) {
                this.mDrawingOrderIndices[i4] = this.mViewGroup.indexOfChild((View) arrayList.get(i4));
            }
        }
        return this.mDrawingOrderIndices[i2];
    }

    public void update() {
        int i = 0;
        this.mNumberOfChildrenWithZIndex = 0;
        while (i < this.mViewGroup.getChildCount()) {
            if (ViewGroupManager.getViewZIndex(this.mViewGroup.getChildAt(i)) != null) {
                this.mNumberOfChildrenWithZIndex++;
            }
            i++;
        }
        this.mDrawingOrderIndices = null;
    }
}
