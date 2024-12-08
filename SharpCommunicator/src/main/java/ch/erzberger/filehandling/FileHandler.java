package ch.erzberger.filehandling;

import lombok.extern.java.Log;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * This class reads and writes ASCII and binary files in a Sharp-specific way.
 */
@Log
public class FileHandler {
    private FileHandler() {
        // Prevent instantiation
    }

    /**
     * Read a text file from disk, and split the content into a List of Strings.
     *
     * @param fileName Name of the file to read
     * @return Cleaned up list of Basic lines (no empty lines, no in-line CR or LF characters)
     */
    public static List<String> readTextFile(String fileName) {
        if ("clip".equals(fileName)) {
            log.log(Level.SEVERE, "Input from clipboard is not supported");
            System.exit(1);
        }
        if (fileName == null) {
            log.log(Level.SEVERE, "File name is null");
            return Collections.emptyList();
        }
        Path path = Paths.get(fileName);
        try {
            log.log(Level.FINEST, "Reading text file {0}", path);
            return normalizeLineBreaks(Files.readAllLines(path));
        } catch (IOException ex) {
            logException(ex);
            return Collections.emptyList();
        }
    }

    /**
     * Reads a binary file from disk // TODO: , and removes the header if one is present.
     *
     * @param fileName The file to load.
     * @return File content as a byte array.
     */
    public static byte[] readBinaryFile(String fileName) {
        if ("clip".equals(fileName)) {
            log.log(Level.SEVERE, "Input from clipboard is not supported");
            System.exit(1);
        }
        if (fileName == null) {
            log.log(Level.SEVERE, "File name is null");
            return new byte[0];
        }
        Path path = Paths.get(fileName);
        try {
            log.log(Level.FINEST, "Reading binary file {0}", path);
            return Files.readAllBytes(path);
        } catch (IOException ex) {
            logException(ex);
            return new byte[0];
        }
    }

    public static void writeTextFile(String fileName, List<String> text) {
        if ("clip".equalsIgnoreCase(fileName)) {
            log.log(Level.INFO, "Text output goes to clipboard");
            // Place the text to the clipboard
            String textBlock = String.join("\n", text);
            StringSelection stringSelection = new StringSelection(textBlock);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        } else {
            Path newFilePath = Paths.get(fileName);
            try {
                log.log(Level.FINEST, "Writing text file {0}", newFilePath);
                Files.deleteIfExists(newFilePath);
                Files.createFile(newFilePath);
                Files.write(newFilePath, text);
            } catch (IOException ex) {
                log.log(Level.SEVERE, "Error persisting the bytes to a file", ex);
            }
        }
    }

    public static void writeBinaryFile(String fileName, byte[] binaryData) {
        // Java does not seem to support copying binary data to the system clipboard. As a compromise,
        // send the data in base64 format as text.
        if ("clip".equalsIgnoreCase(fileName)) {
            log.log(Level.INFO, "Binary output goes to clipboard");
            String encoded = Base64.getEncoder().encodeToString(binaryData);
            StringSelection stringSelection = new StringSelection(encoded);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        } else {
            Path newFilePath = Paths.get(fileName);
            try {
                log.log(Level.FINEST, "Writing binary file {0}", newFilePath);
                Files.deleteIfExists(newFilePath);
                Files.createFile(newFilePath);
                Files.write(newFilePath, binaryData);
            } catch (IOException ex) {
                log.log(Level.SEVERE, "Error persisting the bytes to a file", ex);
            }
        }
    }


    /**
     * Deals with platform specific line breaks and empty lines. It will remove any empty or null lines,
     * and it will remove an end of file character that may be present in PC-1600 files.
     *
     * @param lines Lines to clean up
     * @return Cleaned up lines
     */
    public static List<String> normalizeLineBreaks(List<String> lines) {
        log.log(Level.FINEST, "Normalizing line breaks; Number of lines to normalize: {0}", lines.size());
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
        log.log(Level.FINEST, "Finished normalizing line breaks; Number of lines after normalization: {0}", normalizedLines.size());
        return normalizedLines;
    }

    static void logException(Throwable e) {
        log.log(Level.SEVERE, "An error occurred while reading the file. Reason: {0}", new Object[]{e});
    }
}
