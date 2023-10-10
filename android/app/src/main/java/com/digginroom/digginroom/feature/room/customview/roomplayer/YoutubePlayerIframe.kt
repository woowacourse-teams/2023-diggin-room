package com.digginroom.digginroom.feature.room.customview.roomplayer

fun youtubePlayerIframe(height: Int) = """
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
                const ele = document.getElementsByClassName("player-container")[0]
                const url = "https://www.youtube.com/oembed?url=http%3A//www.youtube.com/watch?v%3D" + videoId + "&format=json"
                fetch(url).then(function (response) {
                    return response.json();
                }).then(function (data) {
                    const whRatio = $height / data["height"] / 2
                    ele.style.transform = "scale(" + whRatio.toString() + ")"
                }).catch(error => {
                    ele.style.transform = "scale(1)"
                });
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
                        ecver: 2,
                        
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
