package com.ncl.plugin;

import com.ncl.plugin.analytics.AnalyticsDisposeInfo;
import com.ncl.plugin.analytics.AnalyticsInitInfo;
import com.ncl.plugin.analytics.AnalyticsPlugin;


public class AnalyticsManager implements AnalyticsPlugin {

    private static AnalyticsManager analyticsManager = new AnalyticsManager();
    private static AnalyticsPlugin analyticsPlugin;


    private AnalyticsManager() {
        analyticsPlugin = PluginManager.instance().newAnalyticsPlugin();
    }

    public static AnalyticsManager instance() {
        return analyticsManager;
    }

    @Override
    public void init(AnalyticsInitInfo initInfo) {

    }

    @Override
    public void sendEvent(String eventName) {

    }

    @Override
    public void dispose(AnalyticsDisposeInfo analyticsDisposeInfo) {

    }

}
