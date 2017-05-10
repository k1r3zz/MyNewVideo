package com.example.administrator.mynewvideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;

import com.example.administrator.until.NewsUntil;
import com.example.administrator.weight.MyVideoView;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class RoomActivity extends AppCompatActivity {
    private MyVideoView mVideoView;
    private String path;
    private String roomid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = (MyVideoView) findViewById(R.id.vitamio_videoView);
        Intent intent = getIntent();
        roomid = intent.getStringExtra("roomid");

        Slide slideTransition = new Slide(Gravity.LEFT);
        slideTransition.setDuration(1000L);
        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(1000L);
        getWindow().setEnterTransition(slideTransition);//注意，下面是必须的
        getWindow().setExitTransition(slideTransition);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().setSharedElementEnterTransition(changeBoundsTransition);
        getWindow().setSharedElementExitTransition(changeBoundsTransition);
        ViewCompat.setTransitionName(mVideoView, "share");

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
