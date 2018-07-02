package com.ncl.plugin;

import com.ncl.plugin.analytics.AnalyticsDisposeInfo;
import com.ncl.plugin.analytics.AnalyticsInitInfo;
import com.ncl.plugin.analytics.AnalyticsPlugin;


public class AnalyticsManager {

    private AnalyticsPlugin analyticsPlugin;


    /* package */ AnalyticsManager(PluginManager pluginManager) {
        analyticsPlugin = pluginManager.newAnalyticsPlugin();
    }

    public void init(AnalyticsInitInfo initInfo) {
        analyticsPlugin.init(initInfo);
    }

    public void sendEvent(String eventName) {
        analyticsPlugin.sendEvent(eventName);
    }

    public void dispose(AnalyticsDisposeInfo analyticsDisposeInfo) {
        analyticsPlugin.dispose(analyticsDisposeInfo);
    }

}
