package com.ncl.plugin.debug;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;


public class DefaultDebugPlugin implements DebugPlugin {

    @Override
    public void init(Context context) { }

    @Override
    public void onBindToOkHttpClient(OkHttpClient okHttpClient) { }

}
