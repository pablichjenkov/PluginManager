package com.ncl.plugin.debug.ncldebug;

import android.content.Context;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.ncl.plugin.debug.DebugPlugin;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;


public class StethoDebugPlugin implements DebugPlugin {

    @Override
    public void init(Context context) {
        Stetho.initializeWithDefaults(context);
    }

    @Override
    public void onBindToOkHttpClient(OkHttpClient okHttpClient) {

        // Adds Stetho network interceptor
        okHttpClient.networkInterceptors().add(new StethoInterceptor());

        // Adds Logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.interceptors().add(loggingInterceptor);
    }

}
