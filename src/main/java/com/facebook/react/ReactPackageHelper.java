package com.facebook.react;

import androidx.annotation.NonNull;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ModuleHolder;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.ReactConstants;
import java.util.Iterator;
import java.util.List;

public class ReactPackageHelper {
    public static Iterable<ModuleHolder> getNativeModuleIterator(ReactPackage reactPackage, ReactApplicationContext reactApplicationContext, ReactInstanceManager reactInstanceManager) {
        List createNativeModules;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(reactPackage.getClass().getSimpleName());
        stringBuilder.append(" is not a LazyReactPackage, falling back to old version.");
        FLog.d(ReactConstants.TAG, stringBuilder.toString());
        if (reactPackage instanceof ReactInstancePackage) {
            createNativeModules = ((ReactInstancePackage) reactPackage).createNativeModules(reactApplicationContext, reactInstanceManager);
        } else {
            createNativeModules = reactPackage.createNativeModules(reactApplicationContext);
        }
        return new Iterable<ModuleHolder>() {
            @NonNull
            public Iterator<ModuleHolder> iterator() {
                return new Iterator<ModuleHolder>() {
                    int position = 0;

                    public ModuleHolder next() {
                        List list = createNativeModules;
                        int i = this.position;
                        this.position = i + 1;
                        return new ModuleHolder((NativeModule) list.get(i));
                    }

                    public boolean hasNext() {
                        return this.position < createNativeModules.size();
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("Cannot remove methods ");
                    }
                };
            }
        };
    }
}
