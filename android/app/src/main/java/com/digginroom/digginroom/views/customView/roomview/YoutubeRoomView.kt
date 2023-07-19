package com.digginroom.digginroom.views.customView.roomview

import android.content.Context
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.digginroom.digginroom.views.model.RoomModel

class YoutubeRoomView(context: Context) :
    WebView(context), RoomView {
    private var videoId = ""

    init {
        setOnTouchListener { v, event -> true }
        isClickable = false
        isContextClickable = false
        isFocusableInTouchMode = false
        focusable = View.NOT_FOCUSABLE
        val iframe = """
            <!DOCTYPE html>
            <html lang="en">
              <script>
                  var scriptUrl = 'https:\/\/www.youtube.com\/s\/player\/4cc5d082\/www-widgetapi.vflset\/www-widgetapi.js';try{var ttPolicy=window.trustedTypes.createPolicy("youtube-widget-api",{createScriptURL:function(x){return x}});scriptUrl=ttPolicy.createScriptURL(scriptUrl)}catch(e){}var YT;if(!window["YT"])YT={loading:0,loaded:0};var YTConfig;if(!window["YTConfig"])YTConfig={"host":"https://www.youtube.com"};
                  if(!YT.loading){YT.loading=1;(function(){var l=[];YT.ready=function(f){if(YT.loaded)f();else l.push(f)};window.onYTReady=function(){YT.loaded=1;var i=0;for(;i<l.length;i++)try{l[i]()}catch(e${'$'}m864811347${'$'}0){}};YT.setConfig=function(c){var k;for(k in c)if(c.hasOwnProperty(k))YTConfig[k]=c[k]};var a=document.createElement("script");a.type="text/javascript";a.id="www-widgetapi-script";a.src=scriptUrl;a.async=true;var c=document.currentScript;if(c){var n=c.nonce||c.getAttribute("nonce");if(n)a.setAttribute("nonce",
                  n)}var b=document.getElementsByTagName("script")[0];b.parentNode.insertBefore(a,b)})()};
                      
                var player = new YT.Player('player', {
                  events: {
                    'onReady': onPlayerReady
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
                    mute: 0,
                    autohide: 1,
                  },
                })
                var isPlayerLoaded = false
                
                function play() {
                    if (!isPlayerLoaded) return
                    player.playVideo()
                }
                
                function pause() {
                    if (!isPlayerLoaded) return
                    player.pauseVideo()
                }
                
                function navigate(videoId) {
                    if (!isPlayerLoaded) return
                    player.loadVideoById(videoId, 0, 'large')
                }
                
                function onYouTubeIframeAPIReady() {
                    console.log('system onYoutbuawe')
                }
                
                function onPlayerReady() {
                    Player.onLoaded()
                    isPlayerLoaded = true
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
            loadUrl("javascript:navigate(\"${room.videoId}\")")
        }
        videoId = room.videoId
    }
}
