package com.inprogress.reactnativeyoutube;

import android.app.FragmentManager;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import com.brentvatne.react.ReactVideoView;
import com.brentvatne.react.ReactVideoViewManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class YouTubeView extends FrameLayout {
    private boolean mHasSavedInstance = false;
    private VideoFragment mVideoFragment;
    private YouTubePlayerController mYouTubeController;

    public YouTubeView(ReactContext reactContext) {
        super(reactContext);
        init();
    }

    public ReactContext getReactContext() {
        return (ReactContext) getContext();
    }

    public void init() {
        inflate(getContext(), R.layout.youtube_layout, this);
        this.mVideoFragment = VideoFragment.newInstance(this);
        this.mYouTubeController = new YouTubePlayerController(this);
    }

    @Nullable
    protected Parcelable onSaveInstanceState() {
        this.mHasSavedInstance = true;
        return super.onSaveInstanceState();
    }

    protected void onAttachedToWindow() {
        if (!this.mHasSavedInstance) {
            getReactContext().getCurrentActivity().getFragmentManager().beginTransaction().add(getId(), this.mVideoFragment).commit();
        }
        super.onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        if (getReactContext().getCurrentActivity() != null) {
            FragmentManager fragmentManager = getReactContext().getCurrentActivity().getFragmentManager();
            if (this.mVideoFragment != null) {
                boolean z = false;
                if (VERSION.SDK_INT >= 19) {
                    z = getReactContext().getCurrentActivity().isDestroyed();
                }
                if (!z) {
                    fragmentManager.beginTransaction().remove(this.mVideoFragment).commitAllowingStateLoss();
                }
            }
        }
        super.onDetachedFromWindow();
    }

    public void seekTo(int i) {
        this.mYouTubeController.seekTo(i);
    }

    public int getCurrentTime() {
        return this.mYouTubeController.getCurrentTime();
    }

    public int getDuration() {
        return this.mYouTubeController.getDuration();
    }

    public void nextVideo() {
        this.mYouTubeController.nextVideo();
    }

    public void previousVideo() {
        this.mYouTubeController.previousVideo();
    }

    public void playVideoAt(int i) {
        this.mYouTubeController.playVideoAt(i);
    }

    public int getVideosIndex() {
        return this.mYouTubeController.getVideosIndex();
    }

    public void onVideoFragmentResume() {
        this.mYouTubeController.onVideoFragmentResume();
    }

    public void receivedError(String str) {
        WritableMap createMap = Arguments.createMap();
        ReactContext reactContext = getReactContext();
        String str2 = ReactVideoView.EVENT_PROP_ERROR;
        createMap.putString(str2, str);
        createMap.putInt("target", getId());
        ((RCTEventEmitter) reactContext.getJSModule(RCTEventEmitter.class)).receiveEvent(getId(), str2, createMap);
    }

    public void playerViewDidBecomeReady() {
        WritableMap createMap = Arguments.createMap();
        ReactContext reactContext = getReactContext();
        createMap.putInt("target", getId());
        ((RCTEventEmitter) reactContext.getJSModule(RCTEventEmitter.class)).receiveEvent(getId(), "ready", createMap);
    }

    public void didChangeToSeeking(int i) {
        WritableMap createMap = Arguments.createMap();
        String str = "state";
        createMap.putString(str, "seeking");
        createMap.putInt(ReactVideoView.EVENT_PROP_CURRENT_TIME, i / 1000);
        createMap.putInt("target", getId());
        ((RCTEventEmitter) getReactContext().getJSModule(RCTEventEmitter.class)).receiveEvent(getId(), str, createMap);
    }

    public void didChangeToState(String str) {
        WritableMap createMap = Arguments.createMap();
        String str2 = "state";
        createMap.putString(str2, str);
        createMap.putInt("target", getId());
        ((RCTEventEmitter) getReactContext().getJSModule(RCTEventEmitter.class)).receiveEvent(getId(), str2, createMap);
    }

    public void didChangeToQuality(String str) {
        WritableMap createMap = Arguments.createMap();
        String str2 = "quality";
        createMap.putString(str2, str);
        createMap.putInt("target", getId());
        ((RCTEventEmitter) getReactContext().getJSModule(RCTEventEmitter.class)).receiveEvent(getId(), str2, createMap);
    }

    public void didChangeToFullscreen(boolean z) {
        WritableMap createMap = Arguments.createMap();
        ReactContext reactContext = getReactContext();
        createMap.putBoolean("isFullscreen", z);
        createMap.putInt("target", getId());
        ((RCTEventEmitter) reactContext.getJSModule(RCTEventEmitter.class)).receiveEvent(getId(), ReactVideoViewManager.PROP_FULLSCREEN, createMap);
    }

    public void setApiKey(String str) {
        try {
            this.mVideoFragment.initialize(str, this.mYouTubeController);
        } catch (Exception e) {
            receivedError(e.getMessage());
        }
    }

    public void setVideoId(String str) {
        this.mYouTubeController.setVideoId(str);
    }

    public void setVideoIds(ReadableArray readableArray) {
        this.mYouTubeController.setVideoIds(readableArray);
    }

    public void setPlaylistId(String str) {
        this.mYouTubeController.setPlaylistId(str);
    }

    public void setPlay(boolean z) {
        this.mYouTubeController.setPlay(z);
    }

    public void setLoop(boolean z) {
        this.mYouTubeController.setLoop(z);
    }

    public void setFullscreen(boolean z) {
        this.mYouTubeController.setFullscreen(z);
    }

    public void setControls(int i) {
        this.mYouTubeController.setControls(i);
    }

    public void setShowFullscreenButton(boolean z) {
        this.mYouTubeController.setShowFullscreenButton(z);
    }

    public void setResumePlay(boolean z) {
        this.mYouTubeController.setResumePlay(z);
    }
}
