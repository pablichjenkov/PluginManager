package com.ncl.plugin.analytics;

import android.app.Activity;

/**
 *  Intended to pass the different termination parameters for all the Plugin implementations. If
 *  a particular AnalyticsPlugin needs different data than the others. Use this class to map to the
 *  respective parameter.
 * */
public class AnalyticsDisposeInfo {


    public final Activity activity;


    public AnalyticsDisposeInfo(Activity activity) {
        this.activity = activity;
    }

}
