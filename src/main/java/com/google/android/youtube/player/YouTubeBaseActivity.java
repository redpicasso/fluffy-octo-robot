package com.google.android.youtube.player;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;

public class YouTubeBaseActivity extends Activity {
    private a a;
    private YouTubePlayerView b;
    private int c;
    private Bundle d;

    private final class a implements b {
        private a() {
        }

        /* synthetic */ a(YouTubeBaseActivity youTubeBaseActivity, byte b) {
            this();
        }

        public final void a(YouTubePlayerView youTubePlayerView) {
            if (!(YouTubeBaseActivity.this.b == null || YouTubeBaseActivity.this.b == youTubePlayerView)) {
                YouTubeBaseActivity.this.b.c(true);
            }
            YouTubeBaseActivity.this.b = youTubePlayerView;
            if (YouTubeBaseActivity.this.c > 0) {
                youTubePlayerView.a();
            }
            if (YouTubeBaseActivity.this.c >= 2) {
                youTubePlayerView.b();
            }
        }

        public final void a(YouTubePlayerView youTubePlayerView, String str, OnInitializedListener onInitializedListener) {
            Activity activity = YouTubeBaseActivity.this;
            youTubePlayerView.a(activity, youTubePlayerView, str, onInitializedListener, activity.d);
            YouTubeBaseActivity.this.d = null;
        }
    }

    final b a() {
        return this.a;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = new a(this, (byte) 0);
        this.d = bundle != null ? bundle.getBundle("YouTubeBaseActivity.KEY_PLAYER_VIEW_STATE") : null;
    }

    protected void onDestroy() {
        YouTubePlayerView youTubePlayerView = this.b;
        if (youTubePlayerView != null) {
            youTubePlayerView.b(isFinishing());
        }
        super.onDestroy();
    }

    protected void onPause() {
        this.c = 1;
        YouTubePlayerView youTubePlayerView = this.b;
        if (youTubePlayerView != null) {
            youTubePlayerView.c();
        }
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        this.c = 2;
        YouTubePlayerView youTubePlayerView = this.b;
        if (youTubePlayerView != null) {
            youTubePlayerView.b();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        YouTubePlayerView youTubePlayerView = this.b;
        bundle.putBundle("YouTubeBaseActivity.KEY_PLAYER_VIEW_STATE", youTubePlayerView != null ? youTubePlayerView.e() : this.d);
    }

    protected void onStart() {
        super.onStart();
        this.c = 1;
        YouTubePlayerView youTubePlayerView = this.b;
        if (youTubePlayerView != null) {
            youTubePlayerView.a();
        }
    }

    protected void onStop() {
        this.c = 0;
        YouTubePlayerView youTubePlayerView = this.b;
        if (youTubePlayerView != null) {
            youTubePlayerView.d();
        }
        super.onStop();
    }
}
