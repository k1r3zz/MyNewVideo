package com.example.administrator.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.entiy.Room;
import com.example.administrator.mynewvideo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 滚动条控件 Author: baopeng
 */
public class BannerView {
	private static final String TAG = "BannerView";

	private Context mContext;
	private ViewGroup pointView;
	private TextView titleText;

	private List<Room> infoList = new ArrayList<Room>();
	public List<ImageView> viewList = new ArrayList<ImageView>();
	public List<String> textList = new ArrayList<String>();
	private ViewPager viewPager;
	private BannerEventClick eventClick;

	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] points;
	public boolean isRun = false;
	public boolean isDown = false;
	
	private boolean isMove = true;

	public void setMove(boolean isMove) {
		this.isMove = isMove;
	}

	private int sleeptime = 2500;

	private BannerPagerAdapter mBannerPagerAdapter;

	/**
	 * 设置默认时间
	 * 
	 * @param sleeptime
	 */
	public void setSleeptime(int sleeptime) {
		this.sleeptime = sleeptime;
	}

	public BannerView(Context context, ViewPager viewPager, ViewGroup pointView, List<Room> list, TextView textView) {
		this.mContext = context;
		this.pointView = pointView;
		this.viewPager = viewPager;
		this.titleText = textView;
		this.infoList = list;
		this.eventClick = null;
	}

	public BannerView(Context context, ViewPager viewPager, ViewGroup pointView, List<Room> list, TextView textView, BannerEventClick eventClick) {
		this.mContext = context;
		this.pointView = pointView;
		this.viewPager = viewPager;
		this.titleText = textView;
		this.infoList = list;
		this.eventClick = eventClick;
	}

	public BannerView(Context context, ViewPager viewPager, ViewGroup pointView, List<Room> list) {
		this.mContext = context;
		this.pointView = pointView;
		this.viewPager = viewPager;
		this.titleText = null;
		this.infoList = list;
		this.eventClick = null;
	}

	public BannerView(Context context, ViewPager viewPager, ViewGroup pointView, List<Room> list, BannerEventClick eventClick) {
		this.mContext = context;
		this.pointView = pointView;
		this.viewPager = viewPager;
		this.titleText = null;
		this.infoList = list;
		this.eventClick = eventClick;

	}

	public void setViewPagerList(List<Room> list) {
		this.infoList = list;
		if (infoList.size() == 0) {
			return;
		}
		mBannerPagerAdapter = new BannerPagerAdapter(mContext, infoList, eventClick);
		if(viewPager==null){
			Log.i("opp", "sba");
		}
		
		try {
			viewPager.setAdapter(mBannerPagerAdapter);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// viewPager.setCurrentItem(mList.size() * 100);
		mBannerPagerAdapter.notifyDataSetChanged();
		initDate();
		initEvents();
		// 自动滑动
		if (infoList.size() == 1) {
			isMove = false;
			isRun = false;
		}else {
			isRun = true;
			isMove = true;
		}
		handler.removeCallbacksAndMessages(null);
		if (isMove) {
			handler.sendEmptyMessageDelayed(0, sleeptime);
		}
	}
	
	private void initDate() {
		// 将点点加入到ViewGroup中
		//CommonUtil.debug(TAG, "===initDate==size==" + infoList.size());
		if (infoList != null && infoList.size() > 0) {
			pointView.removeAllViews();
			points = new ImageView[infoList.size()];
			for (int i = 0; i < points.length; i++) {
				ImageView imageView = new ImageView(mContext);
				imageView.setLayoutParams(new LayoutParams(0, 0));
				points[i] = imageView;
				if (i == 0) {
					points[i].setBackgroundResource(R.drawable.dot_focused);
				} else {
					points[i].setBackgroundResource(R.drawable.dot_normal);
				}
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LayoutParams(dp2px(5), dp2px(5)));
				// layoutParams.gravity=Gravity.RIGHT;
				layoutParams.leftMargin = 5;
				layoutParams.rightMargin = 5;
				pointView.addView(imageView, layoutParams);
			}
		}
	}

	private void initEvents() {
		// 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			boolean isScrolled = false;

			@Override
			public void onPageSelected(int arg0) {
				if (infoList.size() > 0 && infoList != null) {
					setImageBackgroundAndTitle(arg0 % infoList.size());
				}
				if (infoList.size() == 1) {
					isMove = false;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

		});

		viewPager.setOnTouchListener(new View.OnTouchListener() {

			@Override
			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					isRun = false;
					isDown = true;
					handler.removeCallbacksAndMessages(null);
				case MotionEvent.ACTION_MOVE:
					isMove = true;
					isDown = true;
					isRun = false;
					handler.removeCallbacksAndMessages(null);
					break;
				case MotionEvent.ACTION_UP:
					isRun = true;
					isDown = false;
					handler.removeCallbacksAndMessages(null);
					handler.sendEmptyMessageDelayed(1, 500);
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackgroundAndTitle(int selectItems) {
		if (points != null && points.length > 0) {
			for (int i = 0; i < points.length; i++) {
				if (i == selectItems) {
					points[i].setBackgroundResource(R.drawable.dot_focused);
				} else {
					points[i].setBackgroundResource(R.drawable.dot_normal);
				}
				if (titleText != null) {
					titleText.setText(infoList.get(selectItems).getName());
				}
			}
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (!isMove) {
				return ;
			}
			switch (msg.what) {
			case 0:
				try {
					viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
				} catch (Exception e) {

				}
				if (isRun && !isDown) {
					this.sendEmptyMessageDelayed(0, sleeptime);
				}
				break;
			case 1:
				if (isRun && !isDown) {
					this.sendEmptyMessageDelayed(0, sleeptime);
				}
				break;
			}
		}
	};
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				mContext.getResources().getDisplayMetrics());
	}


}
