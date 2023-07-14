package com.digginroom.digginroom

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.digginroom.digginroom.views.RoomYoutubeView
import com.digginroom.model.room.Room
import com.digginroom.model.room.Song

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val roomYoutubeView = findViewById<RoomYoutubeView>(R.id.t)
        roomYoutubeView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                roomYoutubeView.navigate(
                    Room(
                        "ucZl6vQ_8Uo",
                        Song("", "", "", emptyList(), emptyList()),
                        false
                    )
                )
                roomYoutubeView.pause()
            }
        }
        findViewById<Button>(R.id.z).setOnClickListener {
            roomYoutubeView.play()
        }
    }
}
