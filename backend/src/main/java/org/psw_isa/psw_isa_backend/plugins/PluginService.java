package org.psw_isa.psw_isa_backend.plugins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.psw_isa.psw_isa_backend.FormyConfiguration;
import org.psw_isa.psw_isa_backend.Logger;

@Service
public class PluginService {

    private HashMap<String, Plugin> loadedPlugins;

    @Autowired
    private FormyConfiguration formyConfiguration;

    public PluginService() {
        if (formyConfiguration == null) {
            Logger.getInstance().error("FormyConfiguration is null");
            return;
        }
        try {
            this.loadedPlugins = this.loadAllPlugins();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Logger.getInstance().error("Error loading plugins: " + e.getMessage());
        }
    }

    public HashMap<String, Plugin> loadAllPlugins() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        HashMap<String, Plugin> plugins = new HashMap<>();
        File pluginDir = new File(formyConfiguration.getPluginDir());
        File[] files = pluginDir.listFiles((dir, name) -> name.endsWith(".tar.gz"));
        if (files != null) {
            for (File file : files) {
                Plugin plugin = loadPluginFromArchive(file.getAbsolutePath());
                if (plugin != null) {
                    String pluginName = file.getName().replace(".tar.gz", "");
                    plugins.put(pluginName, plugin);
                }
            }
        }
        return plugins;
    }

    private Plugin loadPluginFromArchive(String archivePath) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Plugin loadedPlugin;
        File tempDir = Files.createTempDirectory("plugins").toFile();
        tempDir.deleteOnExit();
        extractArchive(archivePath, tempDir);

        // Assuming there's only one JAR in the archive for simplicity
        File[] files = tempDir.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files != null && files.length > 0) {
            String pluginName = files[0].getName().replace(".jar", "");
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{files[0].toURI().toURL()});
            // Load the Plugin class - this assumes the plugin class name is known or discoverable
            Class<?> pluginClass = Class.forName("org.formy.plugins." + pluginName, true, classLoader);
            loadedPlugin = (Plugin) pluginClass.newInstance();
            loadedPlugin.initialize();
            return loadedPlugin;
        }
        return null;
    }

    private void extractArchive(String archivePath, File destDir) throws IOException {
        try (FileInputStream fis = new FileInputStream(archivePath);
             GzipCompressorInputStream gis = new GzipCompressorInputStream(fis);
             TarArchiveInputStream tarIs = new TarArchiveInputStream(gis)) {
            TarArchiveEntry entry;
            while ((entry = tarIs.getNextTarEntry()) != null) {
                if (!entry.isDirectory()) {
                    File curfile = new File(destDir, entry.getName());
                    File parent = curfile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(curfile);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = tarIs.read(buf)) > 0) {
                        fos.write(buf, 0, len);
                    }
                    fos.close();
                }
            }
        }
    }

    // Method to serve Vue component code
    public String getVueComponentCode(String pluginName, String widgetName) {
        Plugin loadedPlugin = this.loadedPlugins.get(pluginName);
        if (loadedPlugin != null) {
            return loadedPlugin.getVueComponentCode(widgetName);
        }
        return "";
    }

    public ResponseEntity<?> dispatchRequest(String pluginName, HttpServletRequest request) {
        Plugin loadedPlugin = this.loadedPlugins.get(pluginName);
        if (loadedPlugin != null) {
            return loadedPlugin.handleRequest(request);
        }
        return ResponseEntity.notFound().build();
    }
}
