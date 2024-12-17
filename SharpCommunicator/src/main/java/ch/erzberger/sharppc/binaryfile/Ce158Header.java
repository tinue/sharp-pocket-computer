package ch.erzberger.sharppc.binaryfile;

import ch.erzberger.commandline.PocketPcDevice;
import lombok.extern.java.Log;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * Represents a CE-158 header. The header structure is taken from the CE-158 manual, page 29, section
 * "CSAVE, CSAVEa, CSAVEr, PRINT # transmission format"
 * There is no documentation available for machine language files, the below was found by trial and error.
 * The PC-1500 CE-158 header is 27 bytes long. This is its structure:
 * - 0x00 Magic marker: 0x01 + "xCOM" (5 bytes). "X" means:
 *   - @COM is a Basic program
 *   - ACOM is the reserve area
 *   - BCOM is binary data
 *   - HCOM is PRINT# output (not currently supported)
 * - 0x05 Filename: 16 bytes, in Sharp ASCII
 * - 0x15 Binary load start address (2 bytes, ignored except for BCOM)
 * - 0x17 Length of data (2 bytes)
 * - 0x19 Binary autorun address (2 bytes, ignored except for BCOM)
 * <br/>
 * Note: When saving a tokenized Basic program, the PC-1500 saves the Basic start pointer to 0x15, and the
 * Basic end pointer to 0x19. Because these values are ignored when loading a Basic program, it can still be loaded
 * back into a PC-1500 with a different memory setup, e.g. a memory card installed.
 * The SharpCommunicator writes zeroes into these fields.
 */
@Log
public class Ce158Header extends SerialHeader {
    /**
     * Internally used constructor
     */
    protected Ce158Header(FileType type, String filename, int startAddr, int length, int runAddr) {
        super(type, filename, startAddr, length, runAddr);
        super.device = PocketPcDevice.PC1500;
    }

    /**
     * Internally used constructor
     */
    protected Ce158Header(byte[] header) {
        // Start by checking if this can be a header in the first place.
        // First, length: A header is 27 bytes, so anything short can't be a header
        if (header.length < 27) {
            // Can't be a header
            throw new IllegalArgumentException("Not a header, the length is shorter than 27 bytes: " + header.length);
        }
        // Check for the magic marker
        if (!(header[0] == 1 && header[2] == 0x43 && header[3] == 0x4F && header[4] == 0x4D)) {
            throw new IllegalArgumentException("Not a header, magic marker is missing");
        }
        // The header is plausible, determine the file type
        super.type = getFileType((char) header[1]);
        // Filename
        byte[] stringBytes = Arrays.copyOfRange(header, 0x05, 0x15);
        try {
            this.filename = new String(stringBytes, "Cp437").trim();
        } catch (UnsupportedEncodingException e) {
            throw new NoClassDefFoundError("Code page 437 is invalid on your system");
        }
        this.startAddr = FileType.MACHINE.equals(type) ? makeInt(header, 0x15, 2) : 0;
        this.length = makeInt(header, 0x17, 2);
        this.runAddr = FileType.MACHINE.equals(type) ? makeInt(header, 0x19, 2) : 0;
    }

    /**
     * Build a binary CE-158 header from the variables given in the constructor. Note that if the instance
     * is created using the binary constructor, the resulting binary header may be different. This is because
     * the fields that are declared "don't care" in the manual are always filled with zeroes, even if the input
     * binary contained some other value.
     *
     * @return 27 bit CE-158 header binary representation.
     */
    public byte[] getHeader() {
        // Trim the filename to 16 chars (limit of CE-158 header)
        String normalizedFilename = filename;
        if (normalizedFilename.length() > 16) {
            normalizedFilename = normalizedFilename.substring(0, 16);
        }
        // Start with the magic marker, 0x01 + COMx. Append to this the filename.
        String typeAndFileName = getTypeChar(type) + "COM" + normalizedFilename;
        byte[] headerBytes = new byte[]{0x01};
        try {
            headerBytes = appendBytes(headerBytes, typeAndFileName.getBytes("Cp437"));
        } catch (UnsupportedEncodingException e) {
            log.log(Level.SEVERE, "Codepage 437 not found");
            System.exit(-1);
        }
        // If the file name is shorter than 16 bytes, the remaining space must be filled with zeroes.
        int numToPad = 16 - filename.length();
        if (numToPad > 0) {
            headerBytes = appendBytes(headerBytes, new byte[numToPad]);
        }
        // Add the three two-byte values
        headerBytes = appendInt(headerBytes, startAddr);
        headerBytes = appendInt(headerBytes, length);
        headerBytes = appendInt(headerBytes, runAddr);
        return headerBytes;
    }

    char getTypeChar(FileType type) {
        return switch (type) {
            case BASIC -> '@';
            case RESERVE -> 'A';
            case MACHINE -> 'B';
            case VARIABLES -> 'H';
        };
    }

    FileType getFileType(char typeChar) {
        return switch (typeChar) {
            case '@' -> FileType.BASIC;
            case 'A' -> FileType.RESERVE;
            case 'B' -> FileType.MACHINE;
            case 'H' -> FileType.VARIABLES;
            default -> throw new IllegalArgumentException("Not a header, unknown file type: " + typeChar);
        };
    }
}
