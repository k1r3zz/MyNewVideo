package com.example.administrator.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.administrator.http.HttpTool;
import com.example.administrator.mynewvideo.R;
import com.example.administrator.mynewvideo.StreamingActivity;
import com.example.administrator.until.NewsUntil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class SecongFragment extends Fragment {

    private Button livestart, live_login, live_out;
    private EditText username_et, passord_et;
    private TextView roomname;
    private LinearLayout live_ll;
    private HashMap<String, String> map = new HashMap<String, String>();
    private String status = "";
    private String roompath = "";


    public SecongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        // roompath = "rtmp://live-send.xljbear.com/live/room13?room=13&key=3adb20062201f3ed286276f7b77fff5d";

        username_et = (EditText) view.findViewById(R.id.live_username);
        passord_et = (EditText) view.findViewById(R.id.live_passwad);
        live_login = (Button) view.findViewById(R.id.live_login);
        live_ll = (LinearLayout) view.findViewById(R.id.live_ll);
        live_out = (Button) view.findViewById(R.id.live_out);
        roomname = (TextView) view.findViewById(R.id.room_name);

        livestart = (Button) view.findViewById(R.id.live_start);

        live_out.setClickable(false);


        livestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!roompath.equals("")) {
                    Intent intent = new Intent(getActivity(), StreamingActivity.class);
                    intent.putExtra("push_url", roompath);
                    intent.putExtra("res_w", 360);//1920
                    intent.putExtra("res_h", 360);//1080
                    intent.putExtra("frame_rate", 15);//18
                    intent.putExtra("bitrate", 600);//2000
                    intent.putExtra("oritation_landscape", false);
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }

            }
        });
        live_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("username", username_et.getText().toString());
                map.put("password", passord_et.getText().toString());
                map.put("type", "login");
                HttpTool.Posthttp(getActivity(), "http://live.xljbear.com/live/api.html?type=login", map, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        NewsUntil.Log(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");
                            if ("1".equals(status)) {
                                Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();

                                live_out.setClickable(true);
                                live_ll.setVisibility(View.GONE);

                                NewsUntil.TOKEN = jsonObject.getString("token");
                                if (!NewsUntil.TOKEN.equals("")) {
                                    getRoomInfo();
                                }
                            } else {
                                Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();

                                NewsUntil.TOKEN = jsonObject.getString("");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

        live_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsUntil.TOKEN = "";
                roompath = "";
                live_ll.setVisibility(View.VISIBLE);
                username_et.setText("");
                passord_et.setText("");
                live_out.setClickable(false);
            }
        });


    }

    public void getRoomInfo() {
        HttpTool.Gethttp(getActivity(), "http://live.xljbear.com/live/api.html?type=roominfo&token=" + NewsUntil.TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            roompath = jsonObject.getString("rtmp");
                            roomname.setText(jsonObject.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }
}
