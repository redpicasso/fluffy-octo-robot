package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import com.android.vending.expansion.zipfile.APEZProvider;
import java.util.Arrays;
import java.util.List;
import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.util.CloseHelper;

public class SamsungHomeBadger implements Badger {
    private static final String[] CONTENT_PROJECTION = new String[]{APEZProvider.FILEID, "class"};
    private static final String CONTENT_URI = "content://com.sec.badge/apps?notify=true";
    private DefaultBadger defaultBadger;

    public SamsungHomeBadger() {
        if (VERSION.SDK_INT >= 21) {
            this.defaultBadger = new DefaultBadger();
        }
    }

    public void executeBadge(Context context, ComponentName componentName, int i) throws ShortcutBadgeException {
        DefaultBadger defaultBadger = this.defaultBadger;
        if (defaultBadger == null || !defaultBadger.isSupported(context)) {
            Uri parse = Uri.parse(CONTENT_URI);
            ContentResolver contentResolver = context.getContentResolver();
            try {
                ContentResolver contentResolver2 = contentResolver;
                Uri uri = parse;
                Cursor query = contentResolver2.query(uri, CONTENT_PROJECTION, "package=?", new String[]{componentName.getPackageName()}, null);
                if (query != null) {
                    String className = componentName.getClassName();
                    Object obj = null;
                    while (query.moveToNext()) {
                        int i2 = query.getInt(0);
                        contentResolver.update(parse, getContentValues(componentName, i, false), "_id=?", new String[]{String.valueOf(i2)});
                        if (className.equals(query.getString(query.getColumnIndex("class")))) {
                            obj = 1;
                        }
                    }
                    if (obj == null) {
                        contentResolver.insert(parse, getContentValues(componentName, i, true));
                    }
                }
                CloseHelper.close(query);
            } catch (Throwable th) {
                CloseHelper.close(null);
            }
        } else {
            this.defaultBadger.executeBadge(context, componentName, i);
        }
    }

    private ContentValues getContentValues(ComponentName componentName, int i, boolean z) {
        ContentValues contentValues = new ContentValues();
        if (z) {
            contentValues.put("package", componentName.getPackageName());
            contentValues.put("class", componentName.getClassName());
        }
        contentValues.put("badgecount", Integer.valueOf(i));
        return contentValues;
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.sec.android.app.launcher", "com.sec.android.app.twlauncher"});
    }
}
