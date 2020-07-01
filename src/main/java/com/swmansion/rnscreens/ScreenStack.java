package com.swmansion.rnscreens;

import android.content.Context;
import com.swmansion.rnscreens.Screen.StackAnimation;
import com.swmansion.rnscreens.Screen.StackPresentation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ScreenStack extends ScreenContainer {
    private final Set<Screen> mDismissed = new HashSet();
    private final ArrayList<Screen> mStack = new ArrayList();
    private Screen mTopScreen = null;

    /* renamed from: com.swmansion.rnscreens.ScreenStack$2 */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation = new int[StackAnimation.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:5:0x001f, code:
            return;
     */
        static {
            /*
            r0 = com.swmansion.rnscreens.Screen.StackAnimation.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation = r0;
            r0 = $SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.swmansion.rnscreens.Screen.StackAnimation.NONE;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.swmansion.rnscreens.Screen.StackAnimation.FADE;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.swmansion.rnscreens.ScreenStack.2.<clinit>():void");
        }
    }

    public ScreenStack(Context context) {
        super(context);
    }

    public void dismiss(Screen screen) {
        this.mDismissed.add(screen);
        onUpdate();
    }

    public Screen getTopScreen() {
        for (int screenCount = getScreenCount() - 1; screenCount >= 0; screenCount--) {
            Screen screenAt = getScreenAt(screenCount);
            if (!this.mDismissed.contains(screenAt)) {
                return screenAt;
            }
        }
        throw new IllegalStateException("Stack is empty");
    }

    public Screen getRootScreen() {
        int screenCount = getScreenCount();
        for (int i = 0; i < screenCount; i++) {
            Screen screenAt = getScreenAt(i);
            if (!this.mDismissed.contains(screenAt)) {
                return screenAt;
            }
        }
        throw new IllegalStateException("Stack has no root screen set");
    }

    protected void removeScreenAt(int i) {
        this.mDismissed.remove(getScreenAt(i));
        super.removeScreenAt(i);
    }

    protected void onUpdate() {
        int size;
        Screen screen;
        Iterator it = this.mStack.iterator();
        while (it.hasNext()) {
            Screen screen2 = (Screen) it.next();
            if (!this.mScreens.contains(screen2) || this.mDismissed.contains(screen2)) {
                getOrCreateTransaction().remove(screen2.getFragment());
            }
        }
        Screen screen3 = null;
        Screen screen4 = null;
        for (size = this.mScreens.size() - 1; size >= 0; size--) {
            screen = (Screen) this.mScreens.get(size);
            if (!this.mDismissed.contains(screen)) {
                if (screen4 != null) {
                    screen3 = screen;
                    break;
                } else if (screen.getStackPresentation() != StackPresentation.TRANSPARENT_MODAL) {
                    screen4 = screen;
                    break;
                } else {
                    screen4 = screen;
                }
            }
        }
        it = this.mScreens.iterator();
        while (it.hasNext()) {
            screen = (Screen) it.next();
            if (!(this.mStack.contains(screen) || this.mDismissed.contains(screen))) {
                getOrCreateTransaction().add(getId(), screen.getFragment());
            }
            if (!(screen == screen4 || screen == screen3 || this.mDismissed.contains(screen))) {
                getOrCreateTransaction().hide(screen.getFragment());
            }
        }
        if (screen3 != null) {
            getOrCreateTransaction().show(screen3.getFragment()).runOnCommit(new Runnable() {
                public void run() {
                    screen4.bringToFront();
                }
            });
        }
        getOrCreateTransaction().show(screen4.getFragment());
        int i;
        if (this.mStack.contains(screen4)) {
            Screen screen5 = this.mTopScreen;
            if (!(screen5 == null || screen5.equals(screen4))) {
                size = 8194;
                i = AnonymousClass2.$SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation[this.mTopScreen.getStackAnimation().ordinal()];
                if (i == 1) {
                    size = 0;
                } else if (i == 2) {
                    size = 4099;
                }
                getOrCreateTransaction().setTransition(size);
            }
        } else if (this.mTopScreen != null) {
            size = 4097;
            i = AnonymousClass2.$SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation[this.mTopScreen.getStackAnimation().ordinal()];
            if (i == 1) {
                size = 0;
            } else if (i == 2) {
                size = 4099;
            }
            getOrCreateTransaction().setTransition(size);
        }
        this.mTopScreen = screen4;
        this.mStack.clear();
        this.mStack.addAll(this.mScreens);
        tryCommitTransaction();
    }
}
