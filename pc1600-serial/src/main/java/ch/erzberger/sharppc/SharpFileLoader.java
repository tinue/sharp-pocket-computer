package ch.erzberger.sharppc;

import ch.erzberger.commandline.PocketPcDevice;
import lombok.extern.java.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Stream;

/**
 * This class will load a text file into a byte array. Any sole CR or LF will be converted into CR/LF,
 * and at the end an end-of-file marker (Ctrl-Z) will be added if necessary.
 */
@Log
public class SharpFileLoader {
    private SharpFileLoader() {
        // Prevent instantiation
    }

    public static List<String> loadAsciiFile(String fileName, boolean addUtils, PocketPcDevice device) {
        List<String> lines;
        if (fileName == null) {
            log.log(Level.SEVERE, "File name is null");
            return Collections.emptyList();
        }
        Path path = Paths.get(fileName);
        try {
            lines = normalizeLines(Files.readAllLines(path));
            if (addUtils) {
                List<String> util = Pc1600SerialUtils.getSerialUtilBasicApp(device);
                return Stream.concat(lines.stream(), util.stream()).toList();
            }
            return lines;
        } catch (IOException ex) {
            logException(ex);
            return Collections.emptyList();
        }
    }

    static List<String> normalizeLines(List<String> lines) {
        List<String> normalizedLines = new ArrayList<>();
        for (String line : lines) {
            // Ignore empty lines, and the last line with only an EOF character
            if (line.isEmpty() || (line.length() == 1 && 0x1A == line.charAt(0))) {
                continue;
            }
            // Remove an EOF at the end of a non-empty line
            if (0x1A == line.charAt(line.length() - 1)) {
                line = line.substring(0, line.length() - 1);
            }
            normalizedLines.add(line);
        }
        return normalizedLines;
    }

    static byte[] convertStringIntoByteArray(String line, PocketPcDevice device) {
        boolean isPc1600 = PocketPcDevice.PC1600.equals(device);
        if (line == null || line.isEmpty()) {
            log.log(Level.SEVERE, "Line is null or empty");
            return new byte[0];
        }
        // Java Strings are UTF-8, but we need the Sharp Charset. CP437 is close enough for Basic programs.
        byte[] lineBytes = line.getBytes(Charset.forName("Cp437"));
        // Allocate a new buffer. Size is old buffer plus room for the end of line char(s)
        int sizeOfEol = isPc1600 ? 2 : 1;
        byte[] newlineBytes = new byte[lineBytes.length + sizeOfEol];
        // Copy the buffer into the new buffer
        System.arraycopy(lineBytes, 0, newlineBytes, 0, lineBytes.length);
        // Add the carriage return
        newlineBytes[lineBytes.length] = 0x0D;
        if (isPc1600) {
            newlineBytes[lineBytes.length+1] = 0x0A;
        }
        return newlineBytes;
    }

    static void logException(Throwable e) {
        log.log(Level.SEVERE, "Could not read the file.", e);
    }
}
