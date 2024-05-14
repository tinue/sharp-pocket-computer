package ch.erzberger.sharppc;

import lombok.extern.java.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public static byte[] loadFile(String fileName, boolean addUtils) {
        byte[] buffer = new byte[0];
        if (fileName == null) {
            log.log(Level.SEVERE, "File name is null");
            return buffer;
        }
        Path path = Paths.get(fileName);
        if (fileName.toLowerCase().contains(".bas")) {
            try {
                List<String> lines = Files.readAllLines(path);
                if (addUtils) {
                    List<String> util = Pc1600SerialUtils.getSerialUtilBasicApp();
                    List<String> combinedList = Stream.concat(lines.stream(), util.stream()).toList();
                    return convertLinesIntoByteArray(combinedList);
                }
                return convertLinesIntoByteArray(lines);
            } catch (IOException ex) {
                logException(ex);
                return buffer;
            }
        } else {
            try {
                buffer = Files.readAllBytes(path);
            } catch (IOException ex) {
                logException(ex);
            }
            return buffer;
        }
    }

    static byte[] convertLinesIntoByteArray(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            log.log(Level.SEVERE, "Lines is null or empty");
            return new byte[0];
        }
        byte[] buffer = new byte[0];
        for (String line : lines) {
            // Ignore empty lines, and the last line with only an EOF character
            if (line.isEmpty() || (line.length() == 1 && 0x1A == line.charAt(0))) {
                continue;
            }
            // Remove an EOF at the end of a non-empty line
            if (0x1A == line.charAt(line.length() - 1)) {
                line = line.substring(0, line.length() - 1);
            }
            // Read the current line into a byte array
            byte[] lineBytes = line.getBytes();
            // Allocate a new buffer. Size is old buffer plus the line plus two for CR/LF
            byte[] newBuffer = new byte[buffer.length + lineBytes.length + 2];
            // First copy the buffer into the new buffer
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            // Next, append the current line to the new buffer
            System.arraycopy(lineBytes, 0, newBuffer, buffer.length, lineBytes.length);
            // Last, add CR / LF at the end
            newBuffer[newBuffer.length - 2] = 0x0D;
            newBuffer[newBuffer.length - 1] = 0x0A;
            // Then replace the retval with the new array
            buffer = newBuffer;
        }
        // Add the EOF marker to the very end of the array
        byte[] retVal = new byte[buffer.length + 1]; // Make room
        System.arraycopy(buffer, 0, retVal, 0, buffer.length);
        retVal[retVal.length - 1] = 0x1A; // Add EOF marker
        return retVal;
    }

    static void logException(Throwable e) {
        log.log(Level.SEVERE, "Could not read the file.", e);
    }
}
