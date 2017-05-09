package com.example.administrator.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.entiy.Room;
import com.example.administrator.mynewvideo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;


/**
 * 滚动条 adapter
 *
 * @author baopeng
 */
public class BannerPagerAdapter extends PagerAdapter {
    protected static final String TAG = "BannerPagerAdapter";

    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private LayoutInflater inflater;
    private ImageView iv_spread_image;
    private Context context;
    private List<Room> bannerList;
    private View view;
    // private Handler mHandler;
    private BannerEventClick eventClick;

    public BannerPagerAdapter(Context context, List<Room> list, BannerEventClick eventClick) {
        this.eventClick = eventClick;
        this.bannerList = list;
        this.context = context;
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheOnDisc(true).cacheInMemory(true).build();
        this.inflater = LayoutInflater.from(context);
    }

    // public BannerPagerAdapter(Context context, List<ZMList> list) {
    // this.eventClick = null;
    // this.bannerList = list;
    // this.context = context;
    // imageLoader = ImageLoader.getInstance();
    // options = new
    // DisplayImageOptions.Builder().cacheOnDisc(true).cacheInMemory(true).build();
    // this.inflater = LayoutInflater.from(context);
    // }

    public void setList(List<Room> list) {
        if (list != null) {
            this.bannerList = list;
        }
    }

    @Override
    public int getCount() {
        // 设置成最大，使用户看不到边界
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @SuppressLint("InflateParams")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 对ViewPager页号求模取出View列表中要显示的项
        final int i = position;
        view = inflater.inflate(R.layout.item_image_layout, null);
        iv_spread_image = (ImageView) view.findViewById(R.id.iv_spread_image);

        if (bannerList == null || bannerList.size() == 0) {
            return view;
        }
        position %= bannerList.size();
        if (position < 0) {
            position = bannerList.size() + position;
        }
        final Room ZMList;
        ZMList = bannerList.get(position);
        imageLoader.displayImage("http://live.xljbear.com" + ZMList.getRoomface_url(), iv_spread_image, options, null);
        iv_spread_image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (eventClick != null) {
                    eventClick.eventClick(ZMList.getUsername(), ZMList.getId());
                }
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("position", i + "");
                map.put("name", ZMList.getName());
                //MobclickAgent.onEvent(context, "10014", map);
            }
        });
        ((ViewPager) container).removeView(view);

        ((ViewPager) container).addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
