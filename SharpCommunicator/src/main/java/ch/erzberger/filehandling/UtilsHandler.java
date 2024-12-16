package ch.erzberger.filehandling;

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
import java.util.stream.Stream;

/**
 * Helper to fetch the serial utils Basic program to be appended to the loaded Basic program.
 */
@Log
public class UtilsHandler {
    private UtilsHandler() {
        // Prevent instantiation
    }

    public static List<String> getSerialUtilBasicApp(PocketPcDevice device) {
        String utilName = device.isPC1500() ? "setcom1500.bas" : "setcom1600.bas";
        try (InputStream in = UtilsHandler.class.getResourceAsStream("/" + utilName)) {
            if (in == null) {
                log.log(Level.SEVERE, "Could not find the setcom.bas resource");
                return Collections.emptyList();
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                List<String> result = new ArrayList<>();
                while (reader.ready()) {
                    result.add(reader.readLine());
                }
                log.log(Level.FINEST, "Successfully read the file {0}", utilName);
                return result;
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Could not find the setcom.bas resource", e);
            return Collections.emptyList();
        }
    }

    public static List<String> addSerialUtilBasicApp(List<String> program, PocketPcDevice device) {
        log.log(Level.FINEST, "Adding serial util bas. Program size before: {0}", program.size());
        Stream<String> combinedStream = Stream.concat(program.stream(), getSerialUtilBasicApp(device).stream());
        List<String> combinedList = combinedStream.toList();
        log.log(Level.FINEST, "Finished adding serial util bas. Program size after: {0}", combinedList.size());
        return combinedList;
    }
}
