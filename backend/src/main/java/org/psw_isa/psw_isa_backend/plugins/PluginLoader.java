package org.psw_isa.psw_isa_backend.plugins;

import java.net.MalformedURLException;
import java.net.URL;

public class PluginLoader {
    public Plugin loadPlugin(String jarPath, String pluginName) throws MalformedURLException {
        URL[] urls = new URL[1];
        urls[0] = new URL("jar:file:" + jarPath + "!/");
        ClassLoader cl = new java.net.URLClassLoader(urls);
        try {
            Class<?> cls = cl.loadClass("org.psw_isa.psw_isa_backend.plugins." + pluginName);
            return (Plugin) cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
