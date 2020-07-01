package io.invertase.firebase.messaging;

import android.content.Intent;
import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.google.firebase.messaging.RemoteMessage;
import javax.annotation.Nullable;

public class RNFirebaseBackgroundMessagingService extends HeadlessJsTaskService {
    @Nullable
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        if (intent.getExtras() == null) {
            return null;
        }
        return new HeadlessJsTaskConfig("RNFirebaseBackgroundMessage", MessagingSerializer.parseRemoteMessage((RemoteMessage) intent.getParcelableExtra("message")), 60000, false);
    }
}
