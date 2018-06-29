package com.ncl.plugin;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ncl.plugin.analytics.AnalyticsPlugin;
import com.ncl.plugin.analytics.AnalyticsPluginFactory;
import com.ncl.plugin.debug.DebugPlugin;
import com.ncl.plugin.debug.DebugPluginFactory;
import com.ncl.plugin.debug.DefaultDebugPlugin;
import com.ncl.plugin.imageloader.ImageLoaderPlugin;
import com.ncl.plugin.imageloader.ImageLoaderPluginFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;


public class PluginManager {

    private static PluginManager sPluginManager;
    private EnumMap<PluginInfo.Type, List<PluginFactory>> pluginRegistryByType;


    public PluginManager() {
        pluginRegistryByType = new EnumMap<>(PluginInfo.Type.class);
    }

    public static PluginManager instance() {
        if (sPluginManager == null) {
            synchronized (PluginManager.class) {
                if (sPluginManager == null) {
                    sPluginManager = new PluginManager();
                }
            }
        }
        return sPluginManager;
    }

    public void dispose() {
        sPluginManager = null;
    }

    public void init(Context context) {

        // TODO(pablo): Create plugins tests mock
        PluginConfiguration pluginConfiguration = loadPlugins(context);

        if (pluginConfiguration == null) {
            Log.e("PluginManager", "Error initializing PluginManager, make sure a file " +
                    "named: plugin_config.json exist under res/raw directory and is valid json formatted.");

            return;
        }

        for (PluginInfo plugin : pluginConfiguration.pluginList) {

            // TODO(pablo): Abstract this logic to the PluginInstanceCreator interface
            if (PluginInfo.Type.Analytics.equals(plugin.type)) {

                List<PluginFactory> analyticsPlugins
                        = pluginRegistryByType.get(PluginInfo.Type.Analytics);

                if (analyticsPlugins == null) {
                    analyticsPlugins = new ArrayList<>();
                    pluginRegistryByType.put(PluginInfo.Type.Analytics, analyticsPlugins);
                }

                analyticsPlugins.add(new AnalyticsPluginFactory(plugin));

            }
            else if (PluginInfo.Type.ImageLoader.equals(plugin.type)) {
                List<PluginFactory> imageLoaderPlugins = pluginRegistryByType.get(PluginInfo.Type.ImageLoader);

                if (imageLoaderPlugins == null) {
                    imageLoaderPlugins = new ArrayList<>();
                    pluginRegistryByType.put(PluginInfo.Type.ImageLoader, imageLoaderPlugins);
                }

                imageLoaderPlugins.add(new ImageLoaderPluginFactory(plugin));

            }
            else if (PluginInfo.Type.Debug.equals(plugin.type)) {
                List<PluginFactory> debugPlugins = pluginRegistryByType.get(PluginInfo.Type.Debug);

                if (debugPlugins == null) {
                    debugPlugins = new ArrayList<>();
                    pluginRegistryByType.put(PluginInfo.Type.Debug, debugPlugins);
                }

                debugPlugins.add(new DebugPluginFactory(plugin));

            }
        }
    }

    /* package */ AnalyticsPlugin newAnalyticsPlugin() {
        try {

            List<PluginFactory> plugins
                    = pluginRegistryByType.get(PluginInfo.Type.Analytics);

            return ((AnalyticsPluginFactory)plugins.get(0)).newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /* package */ DebugPlugin newDebugPlugin() {

        DebugPlugin debugPlugin = null;
        List<PluginFactory> debugPlugins = pluginRegistryByType.get(PluginInfo.Type.Debug);

        if (debugPlugins != null && debugPlugins.size() > 0) {
            debugPlugin = ((DebugPluginFactory)debugPlugins.get(0)).newInstance();
        }
        else {
            Log.e("PluginManager", "No DebugPlugin found in the plugin_config.json file. Using DefaultPlugin");
        }

        if (debugPlugin == null) {
            debugPlugin = new DefaultDebugPlugin();
        }

        return debugPlugin;
    }

    /* package */ ImageLoaderPlugin newImageLoaderPlugin() {
        try {

            List<PluginFactory> plugins
                    = pluginRegistryByType.get(PluginInfo.Type.ImageLoader);

            return ((ImageLoaderPluginFactory)plugins.get(0)).newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private PluginConfiguration loadPlugins(Context context) {
        try {
            InputStream ins = context.getResources().openRawResource(
                    context.getResources()
                            .getIdentifier("plugin_config", "raw", context.getPackageName()));

            Gson gson = new GsonBuilder().create();
            Reader reader = new InputStreamReader(ins, "UTF-8");

            return gson.fromJson(reader, PluginConfiguration.class);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
