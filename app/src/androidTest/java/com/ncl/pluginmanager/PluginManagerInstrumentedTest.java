package com.ncl.pluginmanager;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ncl.plugin.DebugManager;
import com.ncl.plugin.PluginManager;
import com.ncl.plugin.debug.DebugPlugin;
import com.ncl.plugin.debug.DefaultDebugPlugin;
import com.ncl.plugin.debug.stetho.StethoDebugPlugin;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PluginManagerInstrumentedTest {


    @Test
    public void test_getAppContext() {

        // Context of the app under test.
        Context appContext = getAppContext();

        // Check the context is not null
        assertNotNull(appContext);

        // Check this context belongs to our application by checking the package name.
        assertEquals("com.ncl.pluginmanager", appContext.getPackageName());
    }

    /**
     * To run this test make sure that the plugin_config.json file contains a valid
     * StethoDebugPlugin entry. The Manager will create an instance of it for you and will return
     * it when asked.
     * */
    @Test
    public void test_StethoDebugPlugin() {

        Context appContext = getAppContext();

        PluginManager pluginManager = new PluginManager(appContext);

        DebugManager debugManager = pluginManager.getDebugToolManager();

        // Check the debugPlugin is not null
        assertNotNull(debugManager);

        Class<StethoDebugPlugin> stethoDebugPluginClass = StethoDebugPlugin.class;

        DebugPlugin debugPluginInstance = debugManager.getDebugPlugin();

        // Check a debugPlugin class instance was returned
        assertEquals(stethoDebugPluginClass, debugPluginInstance.getClass());
    }

    /**
     * Before running this test, remove or modify any valid DebugPlugin from the plugin_config.json
     * file in the assets directory. The PluginManager will fail when trying to create a valid
     * DebugPlugin instance and must return an instance of DefaultDebugPlugin.class
     * */
    //@Test
    public void test_DefaultDebugPluginIsReturnedWhenError() {

        Context appContext = getAppContext();

        PluginManager pluginManager = new PluginManager(appContext);

        DebugManager debugManager = pluginManager.getDebugToolManager();

        // Check the debugPlugin is not null
        assertNotNull(debugManager);

        Class<DefaultDebugPlugin> defaultDebugPluginClass = DefaultDebugPlugin.class;

        DebugPlugin debugPluginInstance = debugManager.getDebugPlugin();

        // Check a debugPlugin class instance was returned
        assertEquals(defaultDebugPluginClass, debugPluginInstance.getClass());
    }

    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }

}
