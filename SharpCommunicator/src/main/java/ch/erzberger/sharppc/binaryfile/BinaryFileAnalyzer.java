package ch.erzberger.sharppc.binaryfile;

import ch.erzberger.commandline.PocketPcDevice;
import ch.erzberger.sharppc.sharpbasic.Program;
import lombok.extern.java.Log;

import java.util.logging.Level;

/**
 * The class has a simple job: Given a byte array, figure out what type of file it is. It can distinguish between:
 * - PC-1500 Basic file (ascii)
 * - PC-1500 Basic file (binary, with CE-158 header)
 * - PC-1500 Reserve area (binary, with CE-158 header)
 * - PC-1500 Machine language file (binary, with CE-158 header)
 * - PC-1600: tbd
 */
@Log
public class BinaryFileAnalyzer {
    public static FileType analyze(byte[] data) {
        // Check for a CE-158 header
        try {
            Ce158Header header = new Ce158Header(data);
            log.log(Level.INFO, "CE-158 header, type {0}", header.getType());
            return switch (header.getType()) {
                case "@" -> FileType.PC1500_BINARY_BASIC;
                case "A" -> FileType.PC1500_BINARY_RESERVE;
                case "B" -> FileType.PC1500_BINARY_MACHINE;
                default -> FileType.UNKNOWN;
            };
        } catch (IllegalArgumentException ex) {
            log.log(Level.FINE, "The file does not start with a CE-158 header");
        }
        // Try to parse the file into a Basic Program
        Program program = new Program("Basic", data, PocketPcDevice.PC1500);
        if (program.isValid()) {
            return FileType.PC1500_BASIC;
        } else {
            return FileType.UNKNOWN;
        }
    }
}
