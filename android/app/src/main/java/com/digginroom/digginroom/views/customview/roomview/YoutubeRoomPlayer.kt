package com.digginroom.digginroom.views.customview.roomview

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.digginroom.digginroom.views.model.RoomModel

class YoutubeRoomPlayer(context: Context) :
    WebView(context), RoomPlayer {
    private var videoId = ""
    private var isPlayerLoaded = false

    init {
        setOnTouchListener { _, _ -> true }
        focusable = NOT_FOCUSABLE
        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false
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
                var realPlay = false

                function play() {
                    if (!isPlayerLoaded) return
                    realPlay = true
                    player.playVideo()
                    player.unMute()
                }
                
                function pause() {
                    if (!isPlayerLoaded) return
                    //player.pauseVideo()
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
                    realPlay = false
                    player.loadVideoById(videoId, 0, 'highres')
                }

                function onYouTubeIframeAPIReady() {
                    player = new YT.Player('player', {
                        events: {
                            'onReady': onPlayerReady,
                            'onStateChange': onPlayerStateChange
                        },
                        videoId: 'bHQqvYy5KYo',
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
            },
            "Player"
        )
        setRendererPriorityPolicy(RENDERER_PRIORITY_IMPORTANT, false)
        settings.javaScriptEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        loadDataWithBaseURL("https://www.youtube.com", iframe, "text/html", "utf-8", null)
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
        if (isPlayerLoaded) {
            loadUrl("javascript:navigate(\"${room.videoId}\")")
        }
        videoId = room.videoId
    }
}
