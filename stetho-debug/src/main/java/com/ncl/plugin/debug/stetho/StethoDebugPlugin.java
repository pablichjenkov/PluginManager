package com.ncl.plugin.debug.stetho;

import android.content.Context;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.ncl.plugin.debug.DebugPlugin;
import com.squareup.okhttp.OkHttpClient;


public class StethoDebugPlugin implements DebugPlugin {

    @Override
    public void init(Context context) {
        Stetho.initializeWithDefaults(context);
    }

    @Override
    public void onBindToOkHttpClient(OkHttpClient okHttpClient) {
        okHttpClient.networkInterceptors().add(new StethoInterceptor());
    }

}
