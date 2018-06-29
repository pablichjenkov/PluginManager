package com.ncl.plugin;

import android.content.Context;
import com.ncl.plugin.debug.DebugPlugin;
import com.squareup.okhttp.OkHttpClient;


public class DebugManager implements DebugPlugin {

    private static DebugManager debugManager = new DebugManager();
    private static DebugPlugin debugPlugin;

    private DebugManager() {
        debugPlugin = PluginManager.instance().newDebugPlugin();
    }

    public static DebugManager instance() {
        return debugManager;
    }

    @Override
    public void init(Context context) {
        debugPlugin.init(context);
    }

    @Override
    public void onBindToOkHttpClient(OkHttpClient okHttpClient) {
        debugPlugin.onBindToOkHttpClient(okHttpClient);
    }

}
