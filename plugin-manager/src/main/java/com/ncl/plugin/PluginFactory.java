package com.ncl.plugin;

public interface PluginFactory<T> {
    T newInstance();
}
