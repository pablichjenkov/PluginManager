package com.ncl.plugin;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import com.ncl.plugin.debug.DebugPlugin;
import com.squareup.okhttp.OkHttpClient;


public class DebugManager {

    private DebugPlugin debugPlugin;


    /* package */ DebugManager(PluginManager pluginManager) {
        debugPlugin = pluginManager.newDebugPlugin();
    }

    public void init(Context context) {
        debugPlugin.init(context);
    }

    public void onBindToOkHttpClient(OkHttpClient okHttpClient) {
        debugPlugin.onBindToOkHttpClient(okHttpClient);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public DebugPlugin getDebugPlugin() {
        return debugPlugin;
    }
}
