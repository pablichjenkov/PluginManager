package com.ncl.plugin.debug;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;


public interface DebugPlugin {

    void init(Context context);

    void onBindToOkHttpClient(OkHttpClient okHttpClient);

}
