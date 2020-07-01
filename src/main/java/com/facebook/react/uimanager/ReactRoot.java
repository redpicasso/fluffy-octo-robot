package com.facebook.react.uimanager;

import android.os.Bundle;
import android.view.ViewGroup;
import javax.annotation.Nullable;

public interface ReactRoot {
    @Nullable
    Bundle getAppProperties();

    @Nullable
    String getInitialUITemplate();

    ViewGroup getRootViewGroup();

    int getRootViewTag();

    int getUIManagerType();

    void onStage(int i);

    void runApplication();

    void setRootViewTag(int i);
}
