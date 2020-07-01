package me.leolin.shortcutbadger.impl;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import java.util.Collections;
import java.util.List;
import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;

public class ZukHomeBadger implements Badger {
    private final Uri CONTENT_URI = Uri.parse("content://com.android.badge/badge");

    @TargetApi(11)
    public void executeBadge(Context context, ComponentName componentName, int i) throws ShortcutBadgeException {
        Bundle bundle = new Bundle();
        bundle.putInt("app_badge_count", i);
        context.getContentResolver().call(this.CONTENT_URI, "setAppBadgeCount", null, bundle);
    }

    public List<String> getSupportLaunchers() {
        return Collections.singletonList("com.zui.launcher");
    }
}
