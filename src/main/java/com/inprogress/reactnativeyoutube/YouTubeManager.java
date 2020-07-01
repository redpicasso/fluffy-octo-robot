package com.inprogress.reactnativeyoutube;

import androidx.annotation.Nullable;
import com.brentvatne.react.ReactVideoView;
import com.brentvatne.react.ReactVideoViewManager;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.Map;

public class YouTubeManager extends SimpleViewManager<YouTubeView> {
    private static final int COMMAND_NEXT_VIDEO = 2;
    private static final int COMMAND_PLAY_VIDEO_AT = 4;
    private static final int COMMAND_PREVIOUS_VIDEO = 3;
    private static final int COMMAND_SEEK_TO = 1;

    public String getName() {
        return "ReactYouTube";
    }

    protected YouTubeView createViewInstance(ThemedReactContext themedReactContext) {
        return new YouTubeView(themedReactContext);
    }

    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("seekTo", Integer.valueOf(1), "nextVideo", Integer.valueOf(2), "previousVideo", Integer.valueOf(3), "playVideoAt", Integer.valueOf(4));
    }

    public void receiveCommand(YouTubeView youTubeView, int i, @Nullable ReadableArray readableArray) {
        Assertions.assertNotNull(youTubeView);
        Assertions.assertNotNull(readableArray);
        if (i == 1) {
            youTubeView.seekTo(readableArray.getInt(0));
        } else if (i == 2) {
            youTubeView.nextVideo();
        } else if (i == 3) {
            youTubeView.previousVideo();
        } else if (i == 4) {
            youTubeView.playVideoAt(readableArray.getInt(0));
        } else {
            throw new IllegalArgumentException(String.format("Unsupported command %d received by %s.", new Object[]{Integer.valueOf(i), getClass().getSimpleName()}));
        }
    }

    @Nullable
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        String str = "registrationName";
        return MapBuilder.of(ReactVideoView.EVENT_PROP_ERROR, MapBuilder.of(str, "onYouTubeError"), "ready", MapBuilder.of(str, "onYouTubeReady"), "state", MapBuilder.of(str, "onYouTubeChangeState"), "quality", MapBuilder.of(str, "onYouTubeChangeQuality"), ReactVideoViewManager.PROP_FULLSCREEN, MapBuilder.of(str, "onYouTubeChangeFullscreen"));
    }

    public int getCurrentTime(YouTubeView youTubeView) {
        return youTubeView.getCurrentTime();
    }

    public int getDuration(YouTubeView youTubeView) {
        return youTubeView.getDuration();
    }

    public int getVideosIndex(YouTubeView youTubeView) {
        return youTubeView.getVideosIndex();
    }

    @ReactProp(name = "apiKey")
    public void setApiKey(YouTubeView youTubeView, @Nullable String str) {
        youTubeView.setApiKey(str);
    }

    @ReactProp(name = "videoId")
    public void setPropVideoId(YouTubeView youTubeView, @Nullable String str) {
        youTubeView.setVideoId(str);
    }

    @ReactProp(name = "videoIds")
    public void setPropVideoIds(YouTubeView youTubeView, @Nullable ReadableArray readableArray) {
        youTubeView.setVideoIds(readableArray);
    }

    @ReactProp(name = "playlistId")
    public void setPropPlaylistId(YouTubeView youTubeView, @Nullable String str) {
        youTubeView.setPlaylistId(str);
    }

    @ReactProp(name = "play")
    public void setPropPlay(YouTubeView youTubeView, @Nullable boolean z) {
        youTubeView.setPlay(z);
    }

    @ReactProp(name = "loop")
    public void setPropLoop(YouTubeView youTubeView, @Nullable boolean z) {
        youTubeView.setLoop(z);
    }

    @ReactProp(name = "fullscreen")
    public void setPropFullscreen(YouTubeView youTubeView, @Nullable boolean z) {
        youTubeView.setFullscreen(z);
    }

    @ReactProp(name = "controls")
    public void setPropControls(YouTubeView youTubeView, @Nullable int i) {
        youTubeView.setControls(i);
    }

    @ReactProp(name = "showFullscreenButton")
    public void setPropShowFullscreenButton(YouTubeView youTubeView, @Nullable boolean z) {
        youTubeView.setShowFullscreenButton(z);
    }

    @ReactProp(name = "resumePlayAndroid")
    public void setPropResumePlay(YouTubeView youTubeView, @Nullable boolean z) {
        youTubeView.setResumePlay(z);
    }
}
