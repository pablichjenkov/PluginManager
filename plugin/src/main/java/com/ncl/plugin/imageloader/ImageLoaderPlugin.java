package com.ncl.plugin.imageloader;

import android.content.Context;
import android.widget.ImageView;


public interface ImageLoaderPlugin {

    void loadImage(String url, Context context, ImageView imageView);

}
