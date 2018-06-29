package com.ncl.plugin;


public class PluginFactoryBase<T> implements PluginFactory<T> {

    protected PluginInfo mPluginInfo;


    public PluginFactoryBase(PluginInfo pluginInfo) {
        mPluginInfo = pluginInfo;
    }

    @Override
    public T newInstance() {
        return createInstance(mPluginInfo.className);
    }

    private T createInstance(String className) {
        try {

            Class<?> clazz = Class.forName(className);
            return (T)clazz.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
