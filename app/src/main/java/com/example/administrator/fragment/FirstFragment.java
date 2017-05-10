package com.example.administrator.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.administrator.adapter.RoomAdapter;
import com.example.administrator.entiy.Room;
import com.example.administrator.http.HttpTool;
import com.example.administrator.mynewvideo.R;
import com.example.administrator.mynewvideo.RoomActivity;
import com.example.administrator.until.DataUrl;
import com.example.administrator.until.EndlessRecyclerOnScrollListener;
import com.example.administrator.until.NewsUntil;
import com.example.administrator.weight.BannerEventClick;
import com.example.administrator.weight.BannerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FirstFragment extends Fragment {

    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Room> Bannerlist = new ArrayList<>();

    private String temp;
    private RecyclerView roomlist;
    private RoomAdapter roomAdapter;
    private GridLayoutManager gridLayoutManager;

    private ViewPager viewPager;
    private BannerView mBannerView;
    private ViewGroup group;

    private SwipeRefreshLayout swipeRefreshLayout;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu, container, false);
        initView(view);
        initData();
        return view;
    }


    private void initView(View view) {

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        roomlist = (RecyclerView) view.findViewById(R.id.room_list);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.color_818181,
                R.color.colorBlue,
                R.color.green,
                R.color.colorPrimaryDark
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        roomlist.setHasFixedSize(true);
        roomAdapter = new RoomAdapter(getActivity(), rooms);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        roomlist.setLayoutManager(gridLayoutManager);
        roomlist.setAdapter(roomAdapter);

        //roomlist.setHasFixedSize(true);
        // roomlist.setNormalHeader(getActivity().getLayoutInflater().inflate(R.layout.item_head, roomlist.mRecyclerView, false));

        // 设置item动画
        roomlist.setItemAnimator(new DefaultItemAnimator());

        roomAdapter.setmOnItemClickLitener(new RoomAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(MenuActivity.this, "主播在赶来的路上哦"+position, Toast.LENGTH_SHORT).show();
                if (rooms.get(position).getOnLine().equals("1")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), RoomActivity.class);
                    intent.putExtra("roomid", rooms.get(position).getId());
                   // startActivity(intent);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(),view, "share").toBundle());
                } else {
                    Toast.makeText(getActivity(), "主播在赶来的路上哦", Toast.LENGTH_SHORT).show();
                }
            }
        });

        roomlist.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 3000);
                Toast.makeText(getActivity(), "11234", Toast.LENGTH_SHORT).show();

            }
        });


        // Banners
        RecyclerViewHeader header = RecyclerViewHeader.fromXml(getActivity(), R.layout.item_head);
        header.attachTo(roomlist);

        viewPager = (ViewPager) header.findViewById(R.id.vp_app);
        viewPager = (ViewPager) header.findViewById(R.id.vp_app);
        group = (ViewGroup) view.findViewById(R.id.ll_point_app);
        mBannerView = new BannerView(getActivity(), viewPager, group, null, new BannerEventClick() {
            @Override
            public void eventClick(String username, String url) {
//                Intent intent = new Intent();
//                intent.putExtra("type", type);
//                intent.putExtra("url", url);
//                intent.putExtra("what", "0");
//                intent.setClass(getActivity(), WebViewActivity.class);
//
//                startActivity(intent);
            }
        });

    }

    private void initData() {
        HttpTool.Gethttp(getActivity(), DataUrl.LiveList, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Room room = new Room();
                        temp = jsonArray.getString(i);
                        JSONObject obj = new JSONObject(temp);
                        room.setId(obj.getString("id"));
                        room.setUsername(obj.getString("username"));
                        room.setUserface_url(obj.getString("avater"));
                        JSONObject objroom = obj.getJSONObject("room");
                        room.setOnLine(objroom.getString("online"));
                        room.setRoomface_url(objroom.getString("room_face"));
                        room.setName(objroom.getString("room_name"));
                        room.setType(objroom.getString("room_type"));
                        room.setText(objroom.getString("room_short_introduct"));
                        rooms.add(room);
                        if (!room.getRoomface_url().equals("null")) {
                            Bannerlist.add(room);
                        }
                    }
                    roomAdapter.notifyDataSetChanged();

                    mBannerView.setViewPagerList(Bannerlist);

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    NewsUntil.Log(rooms.size() + rooms.get(0).getUsername());
                }
            }
        });
    }

}
