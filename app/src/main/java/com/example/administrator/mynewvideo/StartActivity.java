package com.example.administrator.mynewvideo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends Activity {
    public static final String RTMPURL_MESSAGE = "com.alex.com.alex.livertmppushsdk.demo.rtmpurl";

    private Button _startRtmpPushButton = null;
    private EditText _rtmpUrlEditText = null;

    private View.OnClickListener _startRtmpPushOnClickedEvent = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {


            Intent intent = new Intent(StartActivity.this, StreamingActivity.class);
            intent.putExtra("push_url", "rtmp://live-send.xljbear.com/live/room13?room=13&key=3adb20062201f3ed286276f7b77fff5d");
            intent.putExtra("res_w", 1920);
            intent.putExtra("res_h", 1080);
            intent.putExtra("frame_rate", 18);
            intent.putExtra("bitrate", 2000);
            intent.putExtra("oritation_landscape", false);
            startActivity(intent);
        }
    };

    private void InitUI() {
        _rtmpUrlEditText = (EditText) findViewById(R.id.rtmpUrleditText);
        _startRtmpPushButton = (Button) findViewById(R.id.startRtmpButton);

        _rtmpUrlEditText.setText("rtmp://live-send.xljbear.com/live/room13?room=13&key=3adb20062201f3ed286276f7b77fff5d");

        _startRtmpPushButton.setOnClickListener(_startRtmpPushOnClickedEvent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        InitUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted

            } else {
                // Permission Denied
                //  displayFrameworkBugMessageAndExit();
                Toast.makeText(this, "请在应用管理中打开“相机,video”访问权限！", Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }

}
