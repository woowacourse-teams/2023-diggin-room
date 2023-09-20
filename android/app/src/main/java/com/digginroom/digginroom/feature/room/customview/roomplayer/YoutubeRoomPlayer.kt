package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import android.view.LayoutInflater
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.digginroom.digginroom.databinding.ItemRoomInfoBinding
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoUiState
import com.digginroom.digginroom.model.mapper.ScrapCountFormatter
import java.io.ByteArrayInputStream
import java.io.InputStream

class YoutubeRoomPlayer(
    context: Context,
    private val onYoutubePlay: () -> Unit
) : FrameLayout(context), RoomPlayer {

    private val roomInfoBinding: ItemRoomInfoBinding =
        ItemRoomInfoBinding.inflate(LayoutInflater.from(context))
    private val webView: WebView = WebView(context)

    private var videoId = ""
    private var isPlayerLoaded = false

    init {
        initLayout()
        initYoutubePlayer()
    }

    override fun play() {
        webView.loadUrl("javascript:play()")
    }

    override fun pause() {
        webView.loadUrl("javascript:pause()")
    }

    override fun navigate(roomInfoUiState: RoomInfoUiState) {
        roomInfoBinding.roomInfoUiState = roomInfoUiState

        if (videoId == roomInfoUiState.roomModel.videoId) {
            return
        }

        if (isPlayerLoaded) {
            webView.loadUrl("javascript:navigate(\"${roomInfoUiState.roomModel.videoId}\")")
        }
        videoId = roomInfoUiState.roomModel.videoId
    }

    private fun initLayout() {
        addView(webView)
        roomInfoBinding.scrapCountFormatter = ScrapCountFormatter
        addView(roomInfoBinding.root)
        preventTouchEvent()
    }

    private fun preventTouchEvent() {
        webView.setOnTouchListener { _, _ -> true }
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
                
                function vh(percent) {
                    var h = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);
                    return (percent * h) / 100;
                }
        
                function vw(percent) {
                    var w = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
                    return (percent * w) / 100;
                }

                function navigate(videoId) {
                    if (!isPlayerLoaded) return
                    
                    const ele = document.getElementsByClassName("player-container")[0]
                    ele.style.position = "absolute"
                    ele.style.width = "0%"
                    ele.style.left = "0%"
                    ele.style.right = "0%"
                    const url = "https://www.youtube.com/oembed?url=http%3A//www.youtube.com/watch?v%3D" + videoId + "&format=json"
                    fetch(url).then(function (response) {
                        return response.json();
                    }).then(function (data) {
                        const whRatio = data["width"] / data["height"]
                        ele.style.width = vh(whRatio * 100).toString() + "px"
                        ele.style.left = "50%"
                        ele.style.transform = "translate(-50%, 0%)"
                    })
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
        webView.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT, false)
        webView.settings.javaScriptEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                if (request?.url.toString().contains("ad.youtube.com") || request?.url.toString()
                    .contains("ads.youtube.com")
                ) {
                    val textStream = ByteArrayInputStream("".toByteArray())
                    return getTextWebResource(textStream)
                }
                return super.shouldInterceptRequest(view, request)
            }
        }
        webView.loadDataWithBaseURL("https://www.youtube.com", iframe, "text/html", "utf-8", null)
    }

    private fun getTextWebResource(data: InputStream): WebResourceResponse? {
        return WebResourceResponse("text/plain", "UTF-8", data)
    }

    private fun initJavascriptInterface() {
        webView.addJavascriptInterface(
            object {
                @JavascriptInterface
                fun onLoaded() {
                    isPlayerLoaded = true
                    if (videoId.isEmpty()) return
                    post {
                        webView.loadUrl("javascript:navigate(\"$videoId\")")
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
