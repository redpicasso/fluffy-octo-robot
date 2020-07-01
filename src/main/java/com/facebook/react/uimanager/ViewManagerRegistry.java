package com.facebook.react.uimanager;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.UIManagerModule.ViewManagerResolver;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public final class ViewManagerRegistry {
    @Nullable
    private final ViewManagerResolver mViewManagerResolver;
    private final Map<String, ViewManager> mViewManagers;

    public ViewManagerRegistry(ViewManagerResolver viewManagerResolver) {
        this.mViewManagers = MapBuilder.newHashMap();
        this.mViewManagerResolver = viewManagerResolver;
    }

    public ViewManagerRegistry(List<ViewManager> list) {
        Map newHashMap = MapBuilder.newHashMap();
        for (ViewManager viewManager : list) {
            newHashMap.put(viewManager.getName(), viewManager);
        }
        this.mViewManagers = newHashMap;
        this.mViewManagerResolver = null;
    }

    public ViewManagerRegistry(Map<String, ViewManager> map) {
        Map map2;
        if (map2 == null) {
            map2 = MapBuilder.newHashMap();
        }
        this.mViewManagers = map2;
        this.mViewManagerResolver = null;
    }

    public ViewManager get(String str) {
        ViewManager viewManager = (ViewManager) this.mViewManagers.get(str);
        if (viewManager != null) {
            return viewManager;
        }
        ViewManagerResolver viewManagerResolver = this.mViewManagerResolver;
        if (viewManagerResolver != null) {
            viewManager = viewManagerResolver.getViewManager(str);
            if (viewManager != null) {
                this.mViewManagers.put(str, viewManager);
                return viewManager;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No ViewManager defined for class ");
        stringBuilder.append(str);
        throw new IllegalViewOperationException(stringBuilder.toString());
    }
}
