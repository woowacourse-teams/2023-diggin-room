package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import android.view.ViewGroup.LayoutParams
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.digginroom.digginroom.feature.room.customview.RoomPlayerThumbnail
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.SongModel
import com.digginroom.digginroom.views.customview.roomview.RoomInfo

class YoutubeRoomPlayer(
    context: Context,
    private val onYoutubePlay: () -> Unit,
    private val onScrapClickListener: (Long) -> Unit,
) : WebView(context), RoomPlayer {

    private val thumbnail: RoomPlayerThumbnail = RoomPlayerThumbnail(context)
    private var videoId = ""
    private var isPlayerLoaded = false
    private var roomId = 0L

    lateinit var roomInfo: RoomInfo

    init {
        addRoomInfoView()
        preventTouchEvent()
        initThumbnail()
        initYoutubePlayer()
    }

    override fun play() {
        loadUrl("javascript:play()")
    }

    override fun pause() {
        loadUrl("javascript:pause()")
    }

    override fun navigate(room: RoomModel) {
        if (videoId == room.videoId) {
            return
        }

        thumbnail.load(room)

        if (isPlayerLoaded) {
            loadUrl("javascript:navigate(\"${room.videoId}\")")
        }
        videoId = room.videoId
        roomInfo.setRoomInfo(
            RoomModel(
                room.videoId,
                SongModel("spicy", "앨범명", "에스파", listOf(), listOf()),
                true,
                room.roomId,
            ),
        )
        roomId = room.roomId
    }

    private fun preventTouchEvent() {
        addRoomInfoView()
        setOnTouchListener { _, _ -> true }
        focusable = NOT_FOCUSABLE
        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false
    }

    private fun initThumbnail() {
        thumbnail.scaleType = ImageView.ScaleType.CENTER_CROP
        thumbnail.layoutParams = LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
        )
        addView(thumbnail)
    }

    private fun initYoutubePlayer() {
        val iframe = """
            <!DOCTYPE html>
            <html lang="en">
              <script>
                var tag = document.createElement('script');

                tag.src = "https://www.youtube.com/iframe_api";
                var firstScriptTag = document.getElementsByTagName('script')[0];
                firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

                var isPlayerLoaded = false
                var player

                function play() {
                    if (!isPlayerLoaded) return
                    player.playVideo()
                    player.unMute()
                }
                
                function pause() {
                    if (!isPlayerLoaded) return
                    player.mute()
                }
                
                function mute() {
                    if (!isPlayerLoaded) return
                    player.mute()
                }

                function unMute() {
                    if (!isPlayerLoaded) return
                    player.unMute()
                }

                function navigate(videoId) {
                    if (!isPlayerLoaded) return
                    player.loadVideoById(videoId, 0, 'highres')
                }

                function onYouTubeIframeAPIReady() {
                    player = new YT.Player('player', {
                        events: {
                            'onReady': onPlayerReady,
                            'onStateChange': onPlayerStateChange
                        },
                        videoId: '',
                        playerVars: {
                            autoplay: 1,
                            controls: 0,
                            disablekb: 1,
                            fs: 0,
                            loop: 1,
                            modestbranding: 1,
                            rel: 0,
                            showinfo: 0,
                            mute: 1,
                            autohide: 1,
                        },
                    })
                }

                function onPlayerReady() {
                    Player.onLoaded()
                    isPlayerLoaded = true
                }
                
                function onPlayerStateChange(event) {
                    if (event.data == 0)
                        player.playVideo()
                    if (event.data == 1)
                        Player.onPlay()
                }
              </script>
              <style>
                html,
                body,
                #player,
                .player-container {
                  height: 100%;
                  width: 100%;
                  margin: 0;
                  overflow: hidden;
                }
            
                .player-container {
                  position: absolute;
                  top: -60px;
                  height: calc(100% + 120px);
                }
              </style>
              <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <title>Youtube API wrapper</title>
              </head>
              <body>
                <div class="player-container">
                  <div id="player"></div>
                </div>
              </body>
            </html>
        """.trimIndent()

        initJavascriptInterface()
        setRendererPriorityPolicy(RENDERER_PRIORITY_IMPORTANT, false)
        settings.javaScriptEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        loadDataWithBaseURL("https://www.youtube.com", iframe, "text/html", "utf-8", null)
    }

    private fun initJavascriptInterface() {
        addJavascriptInterface(
            object {
                @JavascriptInterface
                fun onLoaded() {
                    isPlayerLoaded = true
                    if (videoId.isEmpty()) return
                    this@YoutubeRoomPlayer.post {
                        loadUrl("javascript:navigate(\"$videoId\")")
                    }
                }

                @JavascriptInterface
                fun onPlay() {
                    thumbnail.post {
                        onYoutubePlay()
                        thumbnail.turnOff()
                    }
                }
            },
            "Player",
        )
    }

    private fun addRoomInfoView() {
        roomInfo = RoomInfo(context) { onScrapClickListener(roomId) }
        val myLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
        )
        roomInfo.layoutParams = myLayoutParams
        roomInfo.y = resources.displayMetrics.heightPixels.toFloat() - roomInfo.layoutHeight
        addView(roomInfo)
    }
}
