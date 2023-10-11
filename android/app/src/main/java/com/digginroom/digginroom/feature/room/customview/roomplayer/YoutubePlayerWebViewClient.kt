package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.ByteArrayInputStream
import java.io.InputStream

class YoutubePlayerWebViewClient : WebViewClient() {
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

    private fun getTextWebResource(data: InputStream): WebResourceResponse? {
        return WebResourceResponse("text/plain", "UTF-8", data)
    }
}
