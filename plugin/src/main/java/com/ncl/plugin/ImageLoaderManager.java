package com.ncl.plugin;

import android.content.Context;
import android.widget.ImageView;
import com.ncl.plugin.imageloader.ImageLoaderPlugin;


public class ImageLoaderManager {

    private ImageLoaderPlugin imageLoaderPlugin;


    /* package */ ImageLoaderManager(PluginManager pluginManager) {
        imageLoaderPlugin = pluginManager.newImageLoaderPlugin();
    }

    public void loadImage(String url, Context context, ImageView imageView) {
        imageLoaderPlugin.loadImage(url, context, imageView);
    }

}
