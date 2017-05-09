package com.example.administrator.mynewvideo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
//test
    private void initView() {
//        im=(ImageView)findViewById(R.id.im);
//        NewsUntil.LoadImage("http://live.xljbear.com/Public/user_face/avater.jpg",im);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }


}
