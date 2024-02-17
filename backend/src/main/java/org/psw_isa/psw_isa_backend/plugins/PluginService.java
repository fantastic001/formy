package org.psw_isa.psw_isa_backend.plugins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.psw_isa.psw_isa_backend.FormyConfiguration;

@Service
public class PluginService {

    private Plugin loadedPlugin;

    @Autowired
    private FormyConfiguration formyConfiguration;

    public PluginService() {
        try {
            loadPluginFromArchive(formyConfiguration.getPluginDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPluginFromArchive(String archivePath) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        File tempDir = Files.createTempDirectory("plugins").toFile();
        tempDir.deleteOnExit();
        extractArchive(archivePath, tempDir);

        // Assuming there's only one JAR in the archive for simplicity
        File[] files = tempDir.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files != null && files.length > 0) {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{files[0].toURI().toURL()});
            // Load the Plugin class - this assumes the plugin class name is known or discoverable
            Class<?> pluginClass = Class.forName("com.example.PluginImpl", true, classLoader);
            this.loadedPlugin = (Plugin) pluginClass.newInstance();
            this.loadedPlugin.initialize();
        }
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
    public String getVueComponentCode() {
        if (this.loadedPlugin != null) {
            return this.loadedPlugin.getVueComponentCode();
        }
        return null;
    }
}
