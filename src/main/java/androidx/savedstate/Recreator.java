package androidx.savedstate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.SavedStateRegistry.AutoRecreated;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@SuppressLint({"RestrictedApi"})
final class Recreator implements GenericLifecycleObserver {
    static final String CLASSES_KEY = "classes_to_restore";
    static final String COMPONENT_KEY = "androidx.savedstate.Restarter";
    private final SavedStateRegistryOwner mOwner;

    static final class SavedStateProvider implements androidx.savedstate.SavedStateRegistry.SavedStateProvider {
        final Set<String> mClasses = new HashSet();

        SavedStateProvider(SavedStateRegistry savedStateRegistry) {
            savedStateRegistry.registerSavedStateProvider(Recreator.COMPONENT_KEY, this);
        }

        @NonNull
        public Bundle saveState() {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(Recreator.CLASSES_KEY, new ArrayList(this.mClasses));
            return bundle;
        }

        void add(String str) {
            this.mClasses.add(str);
        }
    }

    Recreator(SavedStateRegistryOwner savedStateRegistryOwner) {
        this.mOwner = savedStateRegistryOwner;
    }

    public void onStateChanged(LifecycleOwner lifecycleOwner, Event event) {
        if (event == Event.ON_CREATE) {
            lifecycleOwner.getLifecycle().removeObserver(this);
            Bundle consumeRestoredStateForKey = this.mOwner.getSavedStateRegistry().consumeRestoredStateForKey(COMPONENT_KEY);
            if (consumeRestoredStateForKey != null) {
                ArrayList stringArrayList = consumeRestoredStateForKey.getStringArrayList(CLASSES_KEY);
                if (stringArrayList != null) {
                    Iterator it = stringArrayList.iterator();
                    while (it.hasNext()) {
                        reflectiveNew((String) it.next());
                    }
                    return;
                }
                throw new IllegalStateException("Bundle with restored state for the component \"androidx.savedstate.Restarter\" must contain list of strings by the key \"classes_to_restore\"");
            }
            return;
        }
        throw new AssertionError("Next event must be ON_CREATE");
    }

    private void reflectiveNew(String str) {
        StringBuilder stringBuilder;
        try {
            Class asSubclass = Class.forName(str, false, Recreator.class.getClassLoader()).asSubclass(AutoRecreated.class);
            try {
                asSubclass = asSubclass.getDeclaredConstructor(new Class[0]);
                asSubclass.setAccessible(true);
                try {
                    ((AutoRecreated) asSubclass.newInstance(new Object[0])).onRecreated(this.mOwner);
                } catch (Throwable e) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to instantiate ");
                    stringBuilder.append(str);
                    throw new RuntimeException(stringBuilder.toString(), e);
                }
            } catch (Throwable e2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Class");
                stringBuilder.append(asSubclass.getSimpleName());
                stringBuilder.append(" must have default constructor in order to be automatically recreated");
                throw new IllegalStateException(stringBuilder.toString(), e2);
            }
        } catch (Throwable e3) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Class ");
            stringBuilder.append(str);
            stringBuilder.append(" wasn't found");
            throw new RuntimeException(stringBuilder.toString(), e3);
        }
    }
}
