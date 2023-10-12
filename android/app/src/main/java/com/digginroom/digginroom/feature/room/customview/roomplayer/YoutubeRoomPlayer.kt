package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.content.Context
import android.view.LayoutInflater
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.FrameLayout
import com.digginroom.digginroom.databinding.ItemRoomInfoBinding
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoUiState
import com.digginroom.digginroom.model.mapper.ScrapCountFormatter

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
        initJavascriptInterface()
        webView.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT, false)
        webView.settings.javaScriptEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.webViewClient = YoutubePlayerWebViewClient()
        val displayMetrics = context.resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        webView.loadDataWithBaseURL(
            "https://www.youtube.com",
            youtubePlayerIframe(dpHeight.toInt()),
            "text/html",
            "utf-8",
            null
        )
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
