package com.example.administrator.until;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class UILApplication extends Application {
    private static UILApplication instance;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("unused")
    @Override
    public void onCreate() {

        super.onCreate();

        initImageLoader(getApplicationContext());

        instance = this;
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        File cacheDir = StorageUtils.getCacheDirectory(context);
        UnlimitedDiskCache cache = new UnlimitedDiskCache(cacheDir);
        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(context)
                .discCache(cache)
                .discCacheFileCount(20)
                .threadPoolSize(3)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new WeakMemoryCache())
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .discCacheFileCount(200)
                .denyCacheImageMultipleSizesInMemory()
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(imageLoaderConfig);
    }


    public static UILApplication getInstance() {

        if (instance == null)
            instance = new UILApplication();

        return instance;
    }


}
