package org.devio.rn.splashscreen;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build.VERSION;
import java.lang.ref.WeakReference;

public class SplashScreen {
    private static WeakReference<Activity> mActivity;
    private static Dialog mSplashDialog;

    public static void show(final Activity activity, final int i) {
        if (activity != null) {
            mActivity = new WeakReference(activity);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (!activity.isFinishing()) {
                        SplashScreen.mSplashDialog = new Dialog(activity, i);
                        SplashScreen.mSplashDialog.setContentView(R.layout.launch_screen);
                        SplashScreen.mSplashDialog.setCancelable(false);
                        if (!SplashScreen.mSplashDialog.isShowing()) {
                            SplashScreen.mSplashDialog.show();
                        }
                    }
                }
            });
        }
    }

    public static void show(Activity activity, boolean z) {
        show(activity, z ? R.style.SplashScreen_Fullscreen : R.style.SplashScreen_SplashTheme);
    }

    public static void show(Activity activity) {
        show(activity, false);
    }

    public static void hide(Activity activity) {
        if (activity == null) {
            WeakReference weakReference = mActivity;
            if (weakReference != null) {
                activity = (Activity) weakReference.get();
            } else {
                return;
            }
        }
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (SplashScreen.mSplashDialog != null && SplashScreen.mSplashDialog.isShowing()) {
                        boolean z = false;
                        if (VERSION.SDK_INT >= 17) {
                            z = activity.isDestroyed();
                        }
                        if (!(activity.isFinishing() || z)) {
                            SplashScreen.mSplashDialog.dismiss();
                        }
                        SplashScreen.mSplashDialog = null;
                    }
                }
            });
        }
    }
}
