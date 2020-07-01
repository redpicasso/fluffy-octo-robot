package io.invertase.firebase.notifications;

import android.content.Intent;
import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import javax.annotation.Nullable;

public class RNFirebaseBackgroundNotificationActionsService extends HeadlessJsTaskService {
    @Nullable
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        if (!RNFirebaseBackgroundNotificationActionReceiver.isBackgroundNotficationIntent(intent)) {
            return null;
        }
        return new HeadlessJsTaskConfig("RNFirebaseBackgroundNotificationAction", RNFirebaseBackgroundNotificationActionReceiver.toNotificationOpenMap(intent), 60000, true);
    }
}
