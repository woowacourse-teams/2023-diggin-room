package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.digginroom.digginroom.feature.room.RoomEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.RoomInfoView
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowRoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.ShowCommentsListener
import com.digginroom.digginroom.model.RoomModel
import java.io.ByteArrayInputStream
import java.io.InputStream

class YoutubeRoomPlayer(
    context: Context,
    private val onYoutubePlay: () -> Unit
) : WebView(context), RoomPlayer {

    private val roomInfoView: RoomInfoView = RoomInfoView(context)

    private var videoId = ""
    private var isPlayerLoaded = false

    init {
        preventTouchEvent()
        initYoutubePlayer()
    }

    fun updateOnScrapListener(callback: RoomEventListener) {
        roomInfoView.updateOnScrapListener(callback)
    }

    fun updateOnRemoveScrapListener(callback: RoomEventListener) {
        roomInfoView.updateOnRemoveScrapListener(callback)
    }

    fun updateShowRoomInfoListener(showRoomInfoListener: ShowRoomInfoListener) {
        roomInfoView.updateOnShowRoomInfoListener(showRoomInfoListener)
    }

    fun updateShowCommentsListener(showCommentsListener: ShowCommentsListener) {
        roomInfoView.updateShowCommentsListener(showCommentsListener)
    }

    override fun play() {
        loadUrl("javascript:play()")
    }

    override fun pause() {
        loadUrl("javascript:pause()")
    }

    override fun navigate(room: RoomModel) {
        roomInfoView.setRoomInfo(room)

        if (videoId == room.videoId) {
            return
        }

        if (isPlayerLoaded) {
            loadUrl("javascript:navigate(\"${room.videoId}\")")
        }
        videoId = room.videoId
    }

    private fun preventTouchEvent() {
        setOnTouchListener { _, _ -> true }
        focusable = NOT_FOCUSABLE
        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false
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
        webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                if (request?.url.toString().contains("ad.youtube.com") ||
                    request?.url.toString().contains("ads.youtube.com")
                ) {
                    val textStream = ByteArrayInputStream("".toByteArray())
                    return getTextWebResource(textStream)
                }
                return super.shouldInterceptRequest(view, request)
            }
        }
        loadDataWithBaseURL("https://www.youtube.com", iframe, "text/html", "utf-8", null)
    }

    private fun getTextWebResource(data: InputStream): WebResourceResponse? {
        return WebResourceResponse("text/plain", "UTF-8", data)
    }

    private fun initJavascriptInterface() {
        addJavascriptInterface(
            object {
                @JavascriptInterface
                fun onLoaded() {
                    isPlayerLoaded = true
                    if (videoId.isEmpty()) return
                    post {
                        loadUrl("javascript:navigate(\"$videoId\")")
                    }
                }

                @JavascriptInterface
                fun onPlay() {
                    post {
                        onYoutubePlay()
                    }
                }
            },
            "Player"
        )
    }
}
