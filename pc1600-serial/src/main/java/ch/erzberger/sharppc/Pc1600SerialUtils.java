package ch.erzberger.sharppc;

import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@Log
public class Pc1600SerialUtils {
    private Pc1600SerialUtils() {
        // Prevent instantiation
    }

    public static List<String> getSerialUtilBasicApp() {
        try (InputStream in = Pc1600SerialUtils.class.getResourceAsStream("/setcom.bas")) {
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
