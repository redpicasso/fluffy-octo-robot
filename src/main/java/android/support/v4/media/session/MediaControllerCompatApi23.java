package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.RequiresApi;

@RequiresApi(23)
class MediaControllerCompatApi23 {

    public static class TransportControls {
        public static void playFromUri(Object obj, Uri uri, Bundle bundle) {
            ((android.media.session.MediaController.TransportControls) obj).playFromUri(uri, bundle);
        }

        private TransportControls() {
        }
    }

    private MediaControllerCompatApi23() {
    }
}
