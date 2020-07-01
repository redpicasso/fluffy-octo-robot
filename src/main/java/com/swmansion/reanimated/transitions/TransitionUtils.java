package com.swmansion.reanimated.transitions;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionPropagation;
import androidx.transition.TransitionSet;
import androidx.transition.Visibility;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ViewProps;
import javax.annotation.Nullable;

class TransitionUtils {
    TransitionUtils() {
    }

    @Nullable
    static Transition inflate(ReadableMap readableMap) {
        String string = readableMap.getString("type");
        if ("group".equals(string)) {
            return inflateGroup(readableMap);
        }
        if ("in".equals(string)) {
            return inflateIn(readableMap);
        }
        if ("out".equals(string)) {
            return inflateOut(readableMap);
        }
        if ("change".equals(string)) {
            return inflateChange(readableMap);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unrecognized transition type ");
        stringBuilder.append(string);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    }

    @Nullable
    private static Transition inflateGroup(ReadableMap readableMap) {
        Transition transitionSet = new TransitionSet();
        String str = "sequence";
        int i = 0;
        if (readableMap.hasKey(str) && readableMap.getBoolean(str)) {
            transitionSet.setOrdering(1);
        } else {
            transitionSet.setOrdering(0);
        }
        ReadableArray array = readableMap.getArray("transitions");
        int size = array.size();
        while (i < size) {
            Transition inflate = inflate(array.getMap(i));
            if (inflate != null) {
                transitionSet.addTransition(inflate);
            }
            i++;
        }
        return transitionSet;
    }

    static Visibility createVisibilityTransition(String str) {
        if (str == null || ViewProps.NONE.equals(str)) {
            return null;
        }
        if ("fade".equals(str)) {
            return new Fade(3);
        }
        if ("scale".equals(str)) {
            return new Scale();
        }
        if ("slide-top".equals(str)) {
            return new Slide(48);
        }
        if ("slide-bottom".equals(str)) {
            return new Slide(80);
        }
        if ("slide-right".equals(str)) {
            return new Slide(5);
        }
        if ("slide-left".equals(str)) {
            return new Slide(3);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid transition type ");
        stringBuilder.append(str);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    }

    private static Transition inflateIn(ReadableMap readableMap) {
        Transition createTransition = createTransition(readableMap.getString("animation"));
        if (createTransition == null) {
            return null;
        }
        createTransition.setMode(1);
        configureTransition(createTransition, readableMap);
        return createTransition;
    }

    private static Transition inflateOut(ReadableMap readableMap) {
        Transition createTransition = createTransition(readableMap.getString("animation"));
        if (createTransition == null) {
            return null;
        }
        createTransition.setMode(2);
        configureTransition(createTransition, readableMap);
        return createTransition;
    }

    private static Transition inflateChange(ReadableMap readableMap) {
        Transition changeTransition = new ChangeTransition();
        configureTransition(changeTransition, readableMap);
        return changeTransition;
    }

    private static Visibility createTransition(String str) {
        if (str == null || ViewProps.NONE.equals(str)) {
            return null;
        }
        if ("fade".equals(str)) {
            return new Fade(3);
        }
        if ("scale".equals(str)) {
            return new Scale();
        }
        if ("slide-top".equals(str)) {
            return new Slide(48);
        }
        if ("slide-bottom".equals(str)) {
            return new Slide(80);
        }
        if ("slide-right".equals(str)) {
            return new Slide(5);
        }
        if ("slide-left".equals(str)) {
            return new Slide(3);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid transition type ");
        stringBuilder.append(str);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    }

    private static void configureTransition(Transition transition, ReadableMap readableMap) {
        String str = "durationMs";
        if (readableMap.hasKey(str)) {
            transition.setDuration((long) readableMap.getInt(str));
        }
        str = "interpolation";
        if (readableMap.hasKey(str)) {
            str = readableMap.getString(str);
            if (str.equals("easeIn")) {
                transition.setInterpolator(new AccelerateInterpolator());
            } else if (str.equals("easeOut")) {
                transition.setInterpolator(new DecelerateInterpolator());
            } else if (str.equals("easeInOut")) {
                transition.setInterpolator(new AccelerateDecelerateInterpolator());
            } else if (str.equals("linear")) {
                transition.setInterpolator(new LinearInterpolator());
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid interpolation type ");
                stringBuilder.append(str);
                throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
            }
        }
        str = "propagation";
        if (readableMap.hasKey(str)) {
            str = readableMap.getString(str);
            TransitionPropagation saneSidePropagation = new SaneSidePropagation();
            if (ViewProps.TOP.equals(str)) {
                saneSidePropagation.setSide(80);
            } else if (ViewProps.BOTTOM.equals(str)) {
                saneSidePropagation.setSide(48);
            } else if (ViewProps.LEFT.equals(str)) {
                saneSidePropagation.setSide(5);
            } else if (ViewProps.RIGHT.equals(str)) {
                saneSidePropagation.setSide(3);
            }
            transition.setPropagation(saneSidePropagation);
        } else {
            transition.setPropagation(null);
        }
        str = "delayMs";
        if (readableMap.hasKey(str)) {
            transition.setStartDelay((long) readableMap.getInt(str));
        }
    }
}
