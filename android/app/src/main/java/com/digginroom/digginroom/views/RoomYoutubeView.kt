package com.digginroom.digginroom.views

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.digginroom.model.room.Room

class RoomYoutubeView(context: Context, attributeSet: AttributeSet) :
    WebView(context, attributeSet), RoomView {

    init {
        val iframe = """
            <!DOCTYPE html>
            <html lang="en">
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
              <script src="https://www.youtube.com/iframe_api"></script>
              <script>
                var player
                var nextState = 0
                var nextVideo = ''
                
                function play() {
                    if (nextState == 3)
                        nextState = 1
                    else
                        player.playVideo()
                }
                
                function pause() {
                    if (nextState == 3)
                        nextState = 2
                    else
                        player.pauseVideo()
                }
                
                function navigate(videoId) {
                    console.log(videoId)
                    console.log(player)
                    if (player === undefined)
                        nextVideo = videoId
                    else
                        player.loadVideoById(videoId, 0, "large")
                    nextState = 3
                }
                
                const playerConfig = {
                  events: {
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
                    mute: 0,
                    autohide: 1,
                  },
                };
            
                function onYouTubeIframeAPIReady() {
                  player = new YT.Player("player", playerConfig)
                  if (nextVideo !== '') {
                    player.loadVideoById(videoId, 0, "large")
                    nextVideo = ''
                  }
                }
                
                function onPlayerStateChange(event) {
                    console.log(event.data)
                    console.log(nextState)
                    if (event.data == 2 && nextState == 1) {
                        player.playVideo()
                        nextState = 0
                    }
                    if (event.data == 1 && nextState == 2) {
                        player.pauseVideo()
                        nextState = 0
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
            </html>
        """.trimIndent()
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

    override fun navigate(room: Room) {
        loadUrl("javascript:navigate(\"${room.videoId}\")")
    }
}
