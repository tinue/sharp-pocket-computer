package ch.erzberger.sharppc;

import ch.erzberger.commandline.PocketPcDevice;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Helper to fetch the serial utils Basic program to be appended to the loaded Basic program.
 */
@Log
public class PocketComputerSerialUtils {
    private PocketComputerSerialUtils() {
        // Prevent instantiation
    }

    public static List<String> getSerialUtilBasicApp(PocketPcDevice device) {
        String utilName = PocketPcDevice.PC1500.equals(device) ? "setcom1500.bas" : "setcom1600.bas";
        try (InputStream in = PocketComputerSerialUtils.class.getResourceAsStream("/" + utilName)) {
            if (in == null) {
                log.log(Level.SEVERE, "Could not find the setcom.bas resource");
                return Collections.emptyList();
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                List<String> result = new ArrayList<>();
                while (reader.ready()) {
                    result.add(reader.readLine());
                }
                return result;
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Could not find the setcom.bas resource", e);
            return Collections.emptyList();
        }
    }
}
