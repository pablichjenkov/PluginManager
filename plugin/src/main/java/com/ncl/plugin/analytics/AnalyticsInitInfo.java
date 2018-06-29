package com.ncl.plugin.analytics;

import android.app.Activity;

/**
 *  Intended to pass the different initialization parameters for all the Plugin implementations. If
 *  a particular AnalyticsPlugin needs different data than the others. Use this class to map to the
 *  respective parameter.
 * */
public class AnalyticsInitInfo {

    public final Activity activity;


    public AnalyticsInitInfo(Activity activity) {
        this.activity = activity;
    }

}
