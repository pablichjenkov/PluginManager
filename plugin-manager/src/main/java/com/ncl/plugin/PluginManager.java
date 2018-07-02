package com.ncl.plugin;

import android.content.Context;
import android.content.res.AssetManager;
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
import java.util.HashMap;
import java.util.List;


public class PluginManager {

    private static HashMap<Class<?>, Object> serviceSingletonCache = new HashMap<>();
    private Context context;
    private String pluginConfigFilePath = "plugin_config.json";
    private EnumMap<PluginInfo.Type, List<PluginFactory>> pluginRegistryByType;


    public PluginManager(Context context) {
        this.context = context;
        pluginRegistryByType = new EnumMap<>(PluginInfo.Type.class);

        serviceSingletonCache.clear();
        init();
    }

    public PluginManager(Context context, String pluginConfigFilePath) {
        this.context = context;
        this.pluginConfigFilePath = pluginConfigFilePath;
        pluginRegistryByType = new EnumMap<>(PluginInfo.Type.class);

        init();
    }

    private void init() {

        PluginConfiguration pluginConfiguration = loadPluginConfigurationFile();

        if (pluginConfiguration == null) {
            Log.e("PluginManager", "Error initializing PluginManager, make sure a file " +
                    "named: "+ pluginConfigFilePath+ " exist either in the asset directory or " +
                    "under res/raw directory, and make sure it is formatted in json.");

            return;
        }

        for (PluginInfo plugin : pluginConfiguration.pluginList) {

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

    public AnalyticsManager getAnalyticsManager() {

        // It is safe to cast here since we only instances of AnalyticsManager class in the map
        AnalyticsManager analyticsManager = (AnalyticsManager)serviceSingletonCache.get(AnalyticsManager.class);

        if (analyticsManager == null) {
            analyticsManager = new AnalyticsManager(this);
            serviceSingletonCache.put(AnalyticsManager.class, analyticsManager);
        }

        return analyticsManager;
    }

    public DebugManager getDebugToolManager() {

        // It is safe to cast here since we only instances of DebugManager class in the map
        DebugManager debugManager = (DebugManager)serviceSingletonCache.get(DebugManager.class);

        if (debugManager == null) {
            debugManager = new DebugManager(this);
            serviceSingletonCache.put(DebugManager.class, debugManager);
        }

        return debugManager;
    }

    public ImageLoaderManager getImageLoaderManager() {

        // It is safe to cast here since we only instances of ImageLoaderManager class in the map
        ImageLoaderManager imageLoaderManager =
                (ImageLoaderManager)serviceSingletonCache.get(ImageLoaderManager.class);

        if (imageLoaderManager == null) {
            imageLoaderManager = new ImageLoaderManager(this);
            serviceSingletonCache.put(ImageLoaderManager.class, imageLoaderManager);
        }

        return imageLoaderManager;
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

    private PluginConfiguration loadPluginConfigurationFile() {
        try {

            AssetManager manager = context.getAssets();
            InputStream inputStream = manager.open(pluginConfigFilePath);

            if (inputStream == null) {

                String rawFilePath = pluginConfigFilePath.replace(".json", "");

                inputStream = context.getResources().openRawResource(
                        context.getResources()
                                .getIdentifier(rawFilePath, "raw", context.getPackageName()));
            }

            Gson gson = new GsonBuilder().create();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");

            return gson.fromJson(reader, PluginConfiguration.class);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
