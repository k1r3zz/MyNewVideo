package com.example.administrator.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.entiy.Room;
import com.example.administrator.mynewvideo.R;
import com.example.administrator.until.NewsUntil;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/27.
 */

public class RoomAdapter extends UltimateViewAdapter<RoomAdapter.MyViewHoler> {

    private ArrayList<Room> rooms = new ArrayList<>();
    private Context context;

    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public RoomAdapter(Context context, ArrayList<Room> rooms) {
        this.context = context;
        this.rooms = rooms;

    }

    @Override
    public MyViewHoler getViewHolder(View view) {
        return new MyViewHoler(view);
    }

    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_item, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public int getAdapterItemCount() {
        return rooms.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(final MyViewHoler holder, final int position) {
        if (holder != null) {

            if (rooms.get(position).getName().equals("null")) {
                holder.room_roomname.setText(rooms.get(position).getUsername() + "的直播间");
            } else {
                holder.room_roomname.setText(rooms.get(position).getName());

            }

            if (rooms.get(position).getOnLine().equals("1")) {
                holder.room_name.setText(rooms.get(position).getUsername() + "(直播中)");
            } else {
                holder.room_name.setText(rooms.get(position).getUsername());
            }

            if (rooms.get(position).getRoomface_url().equals("null")) {
                NewsUntil.LoadImage("http://live.xljbear.com/Public/images/room_offline.jpg", holder.room_im);

            } else {
                NewsUntil.LoadImage("http://live.xljbear.com" + rooms.get(position).getRoomface_url(), holder.room_im);
            }
//            imageLoader.displayImage("http://live.xljbear.com/Public/user_face/avater.jpg", holder.room_userim, options);
//            if (rooms.get(position).getUserface_url().equals("/Public/user_face/")) {
//                NewsUntil.LoadImage("http://live.xljbear.com/Public/user_face/avater.jpg", holder.room_userim);
//
//            } else {
//                NewsUntil.LoadImage("http://live.xljbear.com" + rooms.get(position).getUserface_url(), holder.room_userim);
//            }


            if (mOnItemClickLitener != null) {
                holder.room_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, position);
                    }
                });
            }

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.room_item, parent, false);

        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class MyViewHoler extends UltimateRecyclerviewViewHolder {
        ImageView room_im, room_userim;
        TextView room_name, room_roomname;
        LinearLayout room_ll;

        public MyViewHoler(View itemView) {
            super(itemView);
            room_im = (ImageView) itemView.findViewById(R.id.room_im);
            room_name = (TextView) itemView.findViewById(R.id.room_name);
            room_roomname = (TextView) itemView.findViewById(R.id.room_roomname);
            room_userim = (ImageView) itemView.findViewById(R.id.room_userim);
            room_ll = (LinearLayout) itemView.findViewById(R.id.room_ll);

        }
    }


}
