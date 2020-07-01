package com.inprogress.reactnativeyoutube;

import com.google.android.youtube.player.YouTubePlayerFragment;

public class VideoFragment extends YouTubePlayerFragment {
    private YouTubeView mYouTubeView;

    public void setYoutubeView(YouTubeView youTubeView) {
        this.mYouTubeView = youTubeView;
    }

    public static VideoFragment newInstance(YouTubeView youTubeView) {
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setYoutubeView(youTubeView);
        return videoFragment;
    }

    public void onResume() {
        YouTubeView youTubeView = this.mYouTubeView;
        if (youTubeView != null) {
            youTubeView.onVideoFragmentResume();
        }
        super.onResume();
    }
}
