package com.example.administrator.until;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.mynewvideo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/20.
 */
public class NewsUntil {
    private static DisplayImageOptions options;
    private static ImageLoader imageLoader;
    public static String TOKEN = "";


    // Preference的名称
    public static final String CONFIG = "appCfg";
    public static final String STORE_USERNAME = "store_username";
    public static final String STORE_USERICON = "store_usericon";
    public static final String STORE_USERTEL = "store_usertel";
    public static final String STORE_USERNICKNAME = "store_usernickname";
    public static final String STORE_USERID = "store_userid";
    public static final String STORE_APPVERSION = "store_appversion";


    /**
     * Log
     *
     * @param log
     */
    public static void Log(String log) {
        Log.i("opp", log);
    }

    /**
     * 储存String
     */
    public static void store(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(NewsUntil.CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 储存int
     */
    public static void store(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(NewsUntil.CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 从preference加载String
     */
    public static String load(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(NewsUntil.CONFIG, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 从preference加载String
     */
    public static int load(Context context, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(NewsUntil.CONFIG, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }


    public static void LoadImage(String Url, ImageView im) {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.like) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.like) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.like) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        imageLoader.displayImage(Url, im, options);
    }

    /**
     * String转UTF-8
     *
     * @param xml
     * @return
     */
    public static String getUTF8XMLString(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8 = "";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
            System.out.println("utf-8 编码：" + xmlUTF8);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }

    /*
         * 将时间戳转换为时间
         */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt * 1000L);
        res = simpleDateFormat.format(date);
        return res;
    }
}
