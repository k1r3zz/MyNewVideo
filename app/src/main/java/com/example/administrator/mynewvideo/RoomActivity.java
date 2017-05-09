package com.example.administrator.mynewvideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.until.NewsUntil;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class RoomActivity extends AppCompatActivity {
    private VideoView mVideoView;
    private String path;
    private String roomid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);
        Intent intent = getIntent();
        roomid = intent.getStringExtra("roomid");

        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        } else {
        }

        if (!roomid.equals("")) {
            path = "rtmp://live-send.xljbear.com/live/room" + roomid;
            //path ="rtsp://xgrammyawardsx.is.livestream-api.com/livestreamiphone/grammyawards";
            //path = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
            /*options = new HashMap<>();
            options.put("rtmp_playpath", "");
            options.put("rtmp_swfurl", "");
            options.put("rtmp_live", "1");
            options.put("rtmp_pageurl", "");*/
            mVideoView.setVideoPath(path);
            //mVideoView.setVideoURI(Uri.parse(path), options);
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.requestFocus();

            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setPlaybackSpeed(1.0f);
                    mVideoView.start();
                }
            });
        }


    }


}
