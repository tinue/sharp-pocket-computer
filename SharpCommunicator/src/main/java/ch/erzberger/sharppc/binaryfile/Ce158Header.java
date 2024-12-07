package ch.erzberger.sharppc.binaryfile;

import lombok.Getter;
import lombok.extern.java.Log;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * Represents a CE-158 header.
 * The PC-1500 CE-158 header is 27 bytes long. This is its structure:
 * - 0x00 Magic marker: 0x01 + "xCOM" (5 bytes). "X" means:
 * - @COM is a Basic program
 * - ACOM is the reserve area
 * - BCOM is binary data
 * - 0x05 Filename: 16 bytes, in Sharp ASCII
 * - 0x15 Binary start address (2 bytes, only for BCOM)
 * - 0x17 Length of data (2 bytes)
 * - 0x19 Binary run address (2 bytes, only for BCOM)
 */
@Log @Getter
public class Ce158Header {
    private String type;
    private String filename;
    private int startAddr;
    private int length;
    private int runAddr;

    public Ce158Header(String type, String filename, int startAddr, int length, int runAddr) {
        this.type = checkType(type); // Throw a NoClassDefFoundError if an invalid type is given
        this.filename = filename;
        this.startAddr = "B".equals(type) ? startAddr : 0; // Ignore the value if the type is not binary
        this.length = length; // The length is always required.
        this.runAddr = "B".equals(type) ? runAddr : 0; // Ignore the value if the type is not binary
    }

    public Ce158Header(byte[] header) {
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
        // The header is plausible, continue
        this.type=String.valueOf((char)header[1]);
        // Filename
        byte[] stringBytes = Arrays.copyOfRange(header, 0x05, 0x15);
        try {
            this.filename = new String(stringBytes, "Cp437").trim();
        } catch (UnsupportedEncodingException e) {
            throw new NoClassDefFoundError("Code page 437 is invalid on your system");
        }
        this.startAddr = "B".equals(type) ? makeInt(header, 0x15) : 0;
        this.length = makeInt(header, 0x17);
        this.runAddr = "B".equals(type) ? makeInt(header, 0x19) : 0;
    }

    /**
     * Helper class to append a byte array to another byte array
     *
     * @param source   Byte array to append to
     * @param toAppend Byte array that is being appended at the end of the source
     * @return Combined array
     */
    static byte[] appendBytes(byte[] source, byte[] toAppend) {
        byte[] retVal = new byte[source.length + toAppend.length];
        System.arraycopy(source, 0, retVal, 0, source.length);
        System.arraycopy(toAppend, 0, retVal, source.length, toAppend.length);
        return retVal;
    }

    /**
     * Very specific Sharp helper. Java doesn't know 2 byte Integers, Sharp does. This
     * helper converts the Java Integer into a two byte array. There is no check for overflow,
     * so use this helper with care.
     *
     * @param source   Byte array to append to
     * @param toAppend Integer that is being converted and appended at the end of the source
     * @return Combined array
     */
    static byte[] appendInt(byte[] source, int toAppend) {
        byte[] intermediary = ByteBuffer.allocate(4).putInt(toAppend).array();
        byte[] byteToAppend = new byte[2];
        byteToAppend[0] = intermediary[2];
        byteToAppend[1] = intermediary[3];
        return appendBytes(source, byteToAppend);
    }

    /**
     * Simple helper, because Java does not allow complex logic in a constructor.
     * The helper checks if the type is valid, and throws an error if not.
     *
     * @param type Header type ("@", “A", and "B" are valid types).
     * @return The input, if it is valid. Throws an exception if not.
     */
    static String checkType(String type) {
        if (!("@".equals(type) || "A".equals(type) || "B".equals(type))) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        return type;
    }

    public byte[] getHeader() {
        // Trim the filename to 16 chars (limit of CE-158 header)
        String normalizedFilename = filename;
        if (normalizedFilename.length() > 16) {
            normalizedFilename = normalizedFilename.substring(0, 16);
        }
        // Start with the magic marker, 0x01 + COMx. Append to this the filename.
        String typeAndFileName = type + "COM" + normalizedFilename;
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

    static int makeInt(byte[] source, int start) {
        if (start > source.length -2) {
            log.log(Level.SEVERE, "Invalid start index: " + start);
            return 0;
        }
        byte[] temp = new byte[2];
        temp[0] = source[start];
        temp[1] = source[start+1];
        ByteBuffer wrapped = ByteBuffer.wrap(temp);
        // Java does not have a 2 byte unsigned int (short is 2 bytes, but signed), therefore using an int (4 bytes)
        // so that even 0xFFFF remains positive. Autoboxing converts 0xFFFF into a negative 1 int, therefore
        // use bit arithmetic to remove the upper half, leaving us with the 2 byte unsigned short that we want.
        return wrapped.getShort() & 0xFFFF;
    }
}
