package com.vault.videostube.ui.player

import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.google.android.youtube.player.YouTubeBaseActivity
import com.vault.videostube.BaseActivity
import com.vault.videostube.R
import javax.inject.Inject
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.vault.videostube.BuildConfig


class PlayerActivity: YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private var player: YouTubePlayer? = null

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        if (!wasRestored) {
            player?.cueVideo(videoId)
            this.player = player
        }
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val ID_KEY: String = "ID_VIDEO_KEY"
    }

    @BindView(R.id.youtube_view)
    lateinit var videoPlayerView: YouTubePlayerView

    private lateinit var unBinder: Unbinder
    private lateinit var videoId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)
        unBinder = ButterKnife.bind(this)
        videoId = intent.getStringExtra(ID_KEY)
        videoPlayerView.initialize(BuildConfig.API_KEY, this)
       /* playerView.player = player
        player.playWhenReady = playWhenReady
        player.seekTo(currentWindow, playbackPosition)

        var uri = Uri.parse("#EXTM3U header https://www.youtube.com/watch?v=${intent.getStringExtra(ID_KEY)}")
        //uri = Uri.parse(getString(R.string.media_url_dash))
        Log.e("log", "player $player id $uri")

        var mediaSource: MediaSource = HlsMediaSource.Factory(
                DefaultHttpDataSourceFactory("videos-tube")).createMediaSource(uri)
        player.prepare(mediaSource, true, false)*/
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
        unBinder.unbind()
    }

}