package com.ncl.plugin.analytics;


public interface AnalyticsPlugin {

    void init(AnalyticsInitInfo initInfo);

    void sendEvent(String eventName);

    void dispose(AnalyticsDisposeInfo analyticsDisposeInfo);

}
