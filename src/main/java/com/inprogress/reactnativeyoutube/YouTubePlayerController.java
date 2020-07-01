package com.inprogress.reactnativeyoutube;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.brentvatne.react.ReactVideoViewManager;
import com.facebook.react.bridge.ReadableArray;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import java.util.ArrayList;
import java.util.List;

public class YouTubePlayerController implements OnInitializedListener, PlayerStateChangeListener, PlaybackEventListener, OnFullscreenListener {
    private static final int PLAYLIST_MODE = 2;
    private static final int VIDEOS_MODE = 1;
    private static final int VIDEO_MODE = 0;
    private int mControls = 1;
    private boolean mFullscreen = false;
    private boolean mIsLoaded = false;
    private boolean mIsReady = false;
    private boolean mLoop = false;
    private int mMode = 0;
    private boolean mPlay = false;
    private String mPlaylistId = null;
    private boolean mResumePlay = true;
    private boolean mShowFullscreenButton = true;
    private String mVideoId = null;
    private List<String> mVideoIds = new ArrayList();
    private int mVideosIndex = 0;
    private YouTubePlayer mYouTubePlayer;
    private YouTubeView mYouTubeView;

    public YouTubePlayerController(YouTubeView youTubeView) {
        this.mYouTubeView = youTubeView;
    }

    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean z) {
        if (!z) {
            this.mYouTubePlayer = youTubePlayer;
            this.mYouTubePlayer.setPlayerStateChangeListener(this);
            this.mYouTubePlayer.setPlaybackEventListener(this);
            this.mYouTubePlayer.setOnFullscreenListener(this);
            updateFullscreen();
            updateShowFullscreenButton();
            updateControls();
            if (this.mVideoId != null) {
                loadVideo();
            } else if (!this.mVideoIds.isEmpty()) {
                loadVideos();
            } else if (this.mPlaylistId != null) {
                loadPlaylist();
            }
        }
    }

    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this.mYouTubeView.getReactContext().getCurrentActivity(), 0).show();
        }
        this.mYouTubeView.receivedError(youTubeInitializationResult.toString());
    }

    public void onPlaying() {
        this.mYouTubeView.didChangeToState("playing");
    }

    public void onPaused() {
        this.mYouTubeView.didChangeToState(ReactVideoViewManager.PROP_PAUSED);
    }

    public void onStopped() {
        this.mYouTubeView.didChangeToState("stopped");
    }

    public void onBuffering(boolean z) {
        ProgressBar progressBar;
        if (z) {
            this.mYouTubeView.didChangeToState("buffering");
        }
        int i = 0;
        try {
            progressBar = (ProgressBar) ((ViewGroup) ((ViewGroup) this.mYouTubeView.getChildAt(0)).getChildAt(3)).getChildAt(2);
        } catch (Throwable unused) {
            progressBar = findProgressBar(this.mYouTubeView);
        }
        if (!z) {
            i = 4;
        }
        if (progressBar != null) {
            progressBar.setVisibility(i);
        }
    }

    public void onSeekTo(int i) {
        this.mYouTubeView.didChangeToSeeking(i);
    }

    public void onLoading() {
        this.mYouTubeView.didChangeToState("loading");
    }

    public void onLoaded(String str) {
        if (isVideosMode()) {
            setVideosIndex(this.mVideoIds.indexOf(str));
        }
        if (!this.mIsReady) {
            this.mYouTubeView.playerViewDidBecomeReady();
            setLoaded(true);
            this.mIsReady = true;
        }
    }

    public void onAdStarted() {
        this.mYouTubeView.didChangeToState("adStarted");
    }

    public void onVideoStarted() {
        this.mYouTubeView.didChangeToState("started");
    }

    public void onVideoEnded() {
        this.mYouTubeView.didChangeToState("ended");
        if (!isLoop()) {
            return;
        }
        if (isVideoMode()) {
            loadVideo();
        } else if (isVideosMode() && getVideosIndex() == this.mVideoIds.size() - 1) {
            playVideoAt(0);
        }
    }

    public void onFullscreen(boolean z) {
        this.mYouTubeView.didChangeToFullscreen(z);
    }

    public void onError(ErrorReason errorReason) {
        this.mYouTubeView.receivedError(errorReason.toString());
    }

    public void seekTo(int i) {
        if (isLoaded()) {
            this.mYouTubePlayer.seekToMillis(i * 1000);
        }
    }

    public int getCurrentTime() {
        return this.mYouTubePlayer.getCurrentTimeMillis() / 1000;
    }

    public int getDuration() {
        return this.mYouTubePlayer.getDurationMillis() / 1000;
    }

    public void nextVideo() {
        if (!isLoaded()) {
            return;
        }
        if (this.mYouTubePlayer.hasNext()) {
            this.mYouTubePlayer.next();
        } else if (!isLoop()) {
        } else {
            if (isVideosMode()) {
                playVideoAt(0);
            } else if (isPlaylistMode()) {
                loadPlaylist();
            } else {
                loadVideo();
            }
        }
    }

    public void previousVideo() {
        if (!isLoaded()) {
            return;
        }
        if (this.mYouTubePlayer.hasPrevious()) {
            this.mYouTubePlayer.previous();
        } else if (!isLoop()) {
        } else {
            if (isVideosMode()) {
                playVideoAt(this.mVideoIds.size() - 1);
            } else if (isPlaylistMode()) {
                loadPlaylist();
            } else {
                loadVideo();
            }
        }
    }

    public void playVideoAt(int i) {
        if (!isLoaded() || !isVideosMode()) {
            return;
        }
        if (setVideosIndex(i)) {
            loadVideos();
        } else {
            this.mYouTubeView.receivedError("Video index is out of bound for videoIds[]");
        }
    }

    private void loadVideo() {
        if (isPlay()) {
            this.mYouTubePlayer.loadVideo(this.mVideoId);
        } else {
            this.mYouTubePlayer.cueVideo(this.mVideoId);
        }
        setVideosIndex(0);
        setVideoMode();
    }

    private void loadVideos() {
        if (isPlay()) {
            this.mYouTubePlayer.loadVideos(this.mVideoIds, getVideosIndex(), 0);
        } else {
            this.mYouTubePlayer.cueVideos(this.mVideoIds, getVideosIndex(), 0);
        }
        setVideosMode();
    }

    private void loadPlaylist() {
        if (isPlay()) {
            this.mYouTubePlayer.loadPlaylist(this.mPlaylistId);
        } else {
            this.mYouTubePlayer.cuePlaylist(this.mPlaylistId);
        }
        setVideosIndex(0);
        setPlaylistMode();
    }

    private void updateControls() {
        int i = this.mControls;
        if (i == 0) {
            this.mYouTubePlayer.setPlayerStyle(PlayerStyle.CHROMELESS);
        } else if (i == 1) {
            this.mYouTubePlayer.setPlayerStyle(PlayerStyle.DEFAULT);
        } else if (i == 2) {
            this.mYouTubePlayer.setPlayerStyle(PlayerStyle.MINIMAL);
        }
    }

    private void updateFullscreen() {
        this.mYouTubePlayer.setFullscreen(this.mFullscreen);
    }

    private void updateShowFullscreenButton() {
        this.mYouTubePlayer.setShowFullscreenButton(this.mShowFullscreenButton);
    }

    private ProgressBar findProgressBar(View view) {
        if (view instanceof ProgressBar) {
            return (ProgressBar) view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                ProgressBar findProgressBar = findProgressBar(viewGroup.getChildAt(i));
                if (findProgressBar != null) {
                    return findProgressBar;
                }
            }
        }
        return null;
    }

    private void setLoaded(boolean z) {
        this.mIsLoaded = z;
    }

    private boolean isLoaded() {
        return this.mIsLoaded;
    }

    private void setVideoMode() {
        this.mMode = 0;
    }

    private boolean isVideoMode() {
        return this.mMode == 0;
    }

    private void setVideosMode() {
        this.mMode = 1;
    }

    private boolean isVideosMode() {
        return this.mMode == 1;
    }

    private void setPlaylistMode() {
        this.mMode = 2;
    }

    private boolean isPlaylistMode() {
        return this.mMode == 2;
    }

    private boolean setVideosIndex(int i) {
        if (i < 0 || i >= this.mVideoIds.size()) {
            return false;
        }
        this.mVideosIndex = i;
        return true;
    }

    public int getVideosIndex() {
        return this.mVideosIndex;
    }

    public void onVideoFragmentResume() {
        if (isResumePlay() && this.mYouTubePlayer != null) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    YouTubePlayerController.this.mYouTubePlayer.play();
                }
            }, 1);
        }
    }

    private boolean isPlay() {
        return this.mPlay;
    }

    private boolean isLoop() {
        return this.mLoop;
    }

    private boolean isFullscreen() {
        return this.mFullscreen;
    }

    private int getControls() {
        return this.mControls;
    }

    private boolean isResumePlay() {
        return this.mResumePlay;
    }

    public void setVideoId(String str) {
        this.mVideoId = str;
        if (isLoaded()) {
            loadVideo();
        }
    }

    public void setVideoIds(ReadableArray readableArray) {
        if (readableArray != null) {
            int i = 0;
            setVideosIndex(0);
            this.mVideoIds.clear();
            while (i < readableArray.size()) {
                this.mVideoIds.add(readableArray.getString(i));
                i++;
            }
            if (isLoaded()) {
                loadVideos();
            }
        }
    }

    public void setPlaylistId(String str) {
        this.mPlaylistId = str;
        if (isLoaded()) {
            loadPlaylist();
        }
    }

    public void setPlay(boolean z) {
        this.mPlay = z;
        if (!isLoaded()) {
            return;
        }
        if (isPlay()) {
            this.mYouTubePlayer.play();
        } else {
            this.mYouTubePlayer.pause();
        }
    }

    public void setLoop(boolean z) {
        this.mLoop = z;
    }

    public void setFullscreen(boolean z) {
        this.mFullscreen = z;
        if (isLoaded()) {
            updateFullscreen();
        }
    }

    public void setControls(int i) {
        if (i >= 0 && i <= 2) {
            this.mControls = i;
            if (isLoaded()) {
                updateControls();
            }
        }
    }

    public void setShowFullscreenButton(boolean z) {
        this.mShowFullscreenButton = z;
        if (isLoaded()) {
            updateShowFullscreenButton();
        }
    }

    public void setResumePlay(boolean z) {
        this.mResumePlay = z;
    }
}
