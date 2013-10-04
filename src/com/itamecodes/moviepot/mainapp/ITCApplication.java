package com.itamecodes.moviepot.mainapp;

import java.io.File;
import java.io.IOException;

import android.app.Application;
import android.net.http.HttpResponseCache;
import android.util.Log;

import com.androidquery.callback.BitmapAjaxCallback;

public class ITCApplication extends Application {
    final String TAG="MAIN APPLICATION";
    public int activeKey;
    
	@Override
	public void onCreate() {
		super.onCreate();

	       try {
	           File httpCacheDir = new File(getApplicationContext().getCacheDir(), "http");
	           long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
	           HttpResponseCache.install(httpCacheDir, httpCacheSize);
	       }catch (IOException e) {
	           Log.i(TAG, "HTTP response cache installation failed:" + e);
	       }

		// This configuration tuning is custom. You can tune every option, you may tune some of them, 
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
	       /*
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
			.threadPoolSize(3)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.memoryCacheSize(1500000) // 1.5 Mb
			.denyCacheImageMultipleSizesInMemory()
			.discCacheFileNameGenerator(new Md5FileNameGenerator())
			.enableLogging() // Not necessary in common
			.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);*/
	}
	
	@Override
    public void onLowMemory(){  

        //clear all memory cached images when system is in low memory
        //note that you can configure the max image cache count, see CONFIGURATION
        BitmapAjaxCallback.clearCache();
    }
	public static boolean isNotNullNotEmptyNotWhiteSpaceOnly(final String string)
	{
	   return string != null && !string.isEmpty() && !string.trim().isEmpty();
	}

        


}