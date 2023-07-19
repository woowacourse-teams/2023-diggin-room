package com.digginroom.digginroom.views.customView.roomview

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.digginroom.digginroom.views.model.RoomModel

class YoutubeRoomView(context: Context) :
    WebView(context), RoomView {
    private var videoId = ""

    init {
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
                    player.pauseVideo()
                }

                function navigate(videoId) {
                    if (!isPlayerLoaded) return
                    realPlay = false
                    player.loadVideoById(videoId, 0, 'large')
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
                            //modestbranding: 1,
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
                    if (event.data == 1 && realPlay == false) {
                        player.pauseVideo()
                    }
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
                    if (videoId.isEmpty()) return
                    this@YoutubeRoomView.post {
                        println(videoId)
                        loadUrl("javascript:navigate(\"$videoId\")")
                    }
                }
            },
            "Player"
        )
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
        if (videoId.isNotEmpty()) {
            println(room.videoId)
            loadUrl("javascript:navigate(\"${room.videoId}\")")
        }
        videoId = room.videoId
    }
}
