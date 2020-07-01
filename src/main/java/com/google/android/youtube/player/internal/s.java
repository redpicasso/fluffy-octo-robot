package com.google.android.youtube.player.internal;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.PlaylistEventListener;
import com.google.android.youtube.player.internal.e.a;
import java.util.List;

public final class s implements YouTubePlayer {
    private b a;
    private d b;

    public s(b bVar, d dVar) {
        this.a = (b) ab.a((Object) bVar, (Object) "connectionClient cannot be null");
        this.b = (d) ab.a((Object) dVar, (Object) "embeddedPlayer cannot be null");
    }

    public final View a() {
        try {
            return (View) v.a(this.b.s());
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void a(Configuration configuration) {
        try {
            this.b.a(configuration);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void a(boolean z) {
        try {
            this.b.a(z);
            this.a.a(z);
            this.a.d();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final boolean a(int i, KeyEvent keyEvent) {
        try {
            return this.b.a(i, keyEvent);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final boolean a(Bundle bundle) {
        try {
            return this.b.a(bundle);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void addFullscreenControlFlag(int i) {
        try {
            this.b.d(i);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void b() {
        try {
            this.b.m();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void b(boolean z) {
        try {
            this.b.e(z);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final boolean b(int i, KeyEvent keyEvent) {
        try {
            return this.b.b(i, keyEvent);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void c() {
        try {
            this.b.n();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void cuePlaylist(String str) {
        cuePlaylist(str, 0, 0);
    }

    public final void cuePlaylist(String str, int i, int i2) {
        try {
            this.b.a(str, i, i2);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void cueVideo(String str) {
        cueVideo(str, 0);
    }

    public final void cueVideo(String str, int i) {
        try {
            this.b.a(str, i);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void cueVideos(List<String> list) {
        cueVideos(list, 0, 0);
    }

    public final void cueVideos(List<String> list, int i, int i2) {
        try {
            this.b.a((List) list, i, i2);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void d() {
        try {
            this.b.o();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void e() {
        try {
            this.b.p();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void f() {
        try {
            this.b.q();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void g() {
        try {
            this.b.l();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final int getCurrentTimeMillis() {
        try {
            return this.b.h();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final int getDurationMillis() {
        try {
            return this.b.i();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final int getFullscreenControlFlags() {
        try {
            return this.b.j();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final Bundle h() {
        try {
            return this.b.r();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final boolean hasNext() {
        try {
            return this.b.d();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final boolean hasPrevious() {
        try {
            return this.b.e();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final boolean isPlaying() {
        try {
            return this.b.c();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void loadPlaylist(String str) {
        loadPlaylist(str, 0, 0);
    }

    public final void loadPlaylist(String str, int i, int i2) {
        try {
            this.b.b(str, i, i2);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void loadVideo(String str) {
        loadVideo(str, 0);
    }

    public final void loadVideo(String str, int i) {
        try {
            this.b.b(str, i);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void loadVideos(List<String> list) {
        loadVideos(list, 0, 0);
    }

    public final void loadVideos(List<String> list, int i, int i2) {
        try {
            this.b.b((List) list, i, i2);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void next() {
        try {
            this.b.f();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void pause() {
        try {
            this.b.b();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void play() {
        try {
            this.b.a();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void previous() {
        try {
            this.b.g();
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void release() {
        a(true);
    }

    public final void seekRelativeMillis(int i) {
        try {
            this.b.b(i);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void seekToMillis(int i) {
        try {
            this.b.a(i);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void setFullscreen(boolean z) {
        try {
            this.b.b(z);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void setFullscreenControlFlags(int i) {
        try {
            this.b.c(i);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void setManageAudioFocus(boolean z) {
        try {
            this.b.d(z);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void setOnFullscreenListener(final OnFullscreenListener onFullscreenListener) {
        try {
            this.b.a(new a() {
                public final void a(boolean z) {
                    onFullscreenListener.onFullscreen(z);
                }
            });
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void setPlaybackEventListener(final PlaybackEventListener playbackEventListener) {
        try {
            this.b.a(new f.a() {
                public final void a() {
                    playbackEventListener.onPlaying();
                }

                public final void a(int i) {
                    playbackEventListener.onSeekTo(i);
                }

                public final void a(boolean z) {
                    playbackEventListener.onBuffering(z);
                }

                public final void b() {
                    playbackEventListener.onPaused();
                }

                public final void c() {
                    playbackEventListener.onStopped();
                }
            });
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void setPlayerStateChangeListener(final PlayerStateChangeListener playerStateChangeListener) {
        try {
            this.b.a(new g.a() {
                public final void a() {
                    playerStateChangeListener.onLoading();
                }

                public final void a(String str) {
                    playerStateChangeListener.onLoaded(str);
                }

                public final void b() {
                    playerStateChangeListener.onAdStarted();
                }

                /* JADX WARNING: Removed duplicated region for block: B:2:0x0005 A:{ExcHandler: java.lang.IllegalArgumentException (unused java.lang.IllegalArgumentException), Splitter: B:0:0x0000} */
                /* JADX WARNING: Missing block: B:2:0x0005, code:
            r2 = com.google.android.youtube.player.YouTubePlayer.ErrorReason.UNKNOWN;
     */
                public final void b(java.lang.String r2) {
                    /*
                    r1 = this;
                    r2 = com.google.android.youtube.player.YouTubePlayer.ErrorReason.valueOf(r2);	 Catch:{ IllegalArgumentException -> 0x0005, IllegalArgumentException -> 0x0005 }
                    goto L_0x0007;
                L_0x0005:
                    r2 = com.google.android.youtube.player.YouTubePlayer.ErrorReason.UNKNOWN;
                L_0x0007:
                    r0 = r3;
                    r0.onError(r2);
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.youtube.player.internal.s.3.b(java.lang.String):void");
                }

                public final void c() {
                    playerStateChangeListener.onVideoStarted();
                }

                public final void d() {
                    playerStateChangeListener.onVideoEnded();
                }
            });
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void setPlayerStyle(PlayerStyle playerStyle) {
        try {
            this.b.a(playerStyle.name());
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void setPlaylistEventListener(final PlaylistEventListener playlistEventListener) {
        try {
            this.b.a(new h.a() {
                public final void a() {
                    playlistEventListener.onPrevious();
                }

                public final void b() {
                    playlistEventListener.onNext();
                }

                public final void c() {
                    playlistEventListener.onPlaylistEnded();
                }
            });
        } catch (RemoteException e) {
            throw new q(e);
        }
    }

    public final void setShowFullscreenButton(boolean z) {
        try {
            this.b.c(z);
        } catch (RemoteException e) {
            throw new q(e);
        }
    }
}
