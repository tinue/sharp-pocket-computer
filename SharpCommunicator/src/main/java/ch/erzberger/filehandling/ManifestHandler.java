package ch.erzberger.filehandling;

import lombok.extern.java.Log;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;

@Log
public class ManifestHandler {
    public static String getVersionFromManifest() {
        Manifest mf = new Manifest();
        try {
            mf.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
        } catch (IOException e) {
            log.log(Level.SEVERE, "Cannot read manifest", e);
            return null;
        }
        Attributes atts = mf.getMainAttributes();
        String buildTime = atts.getValue("Build-Time");
        String version = atts.getValue("Implementation-Version");
        return version == null ? "" : version + (buildTime == null || buildTime.isEmpty() ? "" : " - pre-release from " + buildTime);
    }
}
