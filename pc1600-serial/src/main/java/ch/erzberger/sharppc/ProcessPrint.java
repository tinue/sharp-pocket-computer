package ch.erzberger.sharppc;

import ch.erzberger.serialhandler.ByteProcessor;
import lombok.extern.java.Log;

import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.logging.Level;

@Log
public class ProcessPrint implements ByteProcessor {
    private boolean previousWasCr = false;
    @Override
    public void processByte(byte byteReceived) {
        String byteInHex = HexFormat.of().formatHex(new byte[]{byteReceived});
        // The PC-1600 can be set to CR, LF or CR/LF as the line ending. Deal with any of them.
        if (13 == byteReceived) {
            log.log(Level.FINEST, "CR received");
            previousWasCr = true;
            System.out.println(); // Perform the end of line printing
        } else if (10 == byteReceived) {
            log.log(Level.FINEST, "LF received");
            if (previousWasCr) {
                // The CR has already been done, so don't do it again
                previousWasCr = false;
            } else {
                // Stand-alone LF, process it
                System.out.println();
            }
        } else if (Byte.toUnsignedInt(byteReceived) < 0x20) { // byte is signed, but we need to keep values > 127
            // Ignore any non-printable characters below a blank (0x20)
            log.log(Level.WARNING, "Non-printable character received, code: {0}", byteInHex);
        } else {
            log.log(Level.FINEST, "Character received, code: {0}", byteInHex);
            // Convert the byte to a String using the proper codepage
            String outchar = new String(new byte[]{byteReceived}, StandardCharsets.ISO_8859_1);
            System.out.print(outchar); // No line feed, just append the one character to stdout
        }
    }
}

// TODOs:
// - Allow printing to file
// - Use proper code page for translation
