package com.ncl.plugin;

public class PluginInfo {

    public enum Type {
        Analytics,
        Network,
        ImageLoader,
        Debug
    }

    public String className;
    public Type type;
    public String id;

}
