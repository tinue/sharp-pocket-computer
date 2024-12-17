package ch.erzberger.sharppc.binaryfile;

import ch.erzberger.commandline.PocketPcDevice;
import lombok.Getter;
import lombok.extern.java.Log;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * Represents a serial header. The concrete header will either be for a PC-1600, or for a CE-158.
 */
@Log
@Getter
public abstract class SerialHeader {
    FileType type;
    String filename;
    int startAddr;
    int length;
    int runAddr;
    PocketPcDevice device;

    /**
     * Internally used constructor.
     */
    protected SerialHeader(FileType type, String filename, int startAddr, int length, int runAddr) {
        this.type = type;
        this.filename = filename;
        this.startAddr = FileType.MACHINE.equals(type) ? startAddr : 0; // Ignore the value if the type is not binary
        this.length = length; // The length is always required.
        this.runAddr = FileType.MACHINE.equals(type) ? runAddr : 0; // Ignore the value if the type is not binary
    }

    /**
     * Internally used constructor.
     */
    protected SerialHeader() {
    }

    /**
     * Factory method to create a serial header from parameters.
     *
     * @param device    The device used
     * @param type      The type of header to be created (e.g. tokenized Basic program, or machine language program)
     * @param filename  The name of the program or file
     * @param startAddr For machine language programs, the address to load the program to
     * @param length    The length of the program
     * @param runAddr   When set, gives the start address of a machine language program
     * @return Populated and validated header
     */
    public static SerialHeader makeHeader(PocketPcDevice device, FileType type, String filename, int startAddr, int length, int runAddr) {
        if (PocketPcDevice.PC1600.equals(device)) {
            return new Pc1600Header(type, filename, startAddr, length, runAddr);
        } else {
            return new Ce158Header(type, filename, startAddr, length, runAddr);
        }
    }

    /**
     * Convenience factory method for tokenized Basic programs,
     *
     * @param device   The device used
     * @param filename The name of the Basic program
     * @param length   The length of the tokenized Basic program
     * @return Populated and validated header
     */
    public static SerialHeader makeHeader(PocketPcDevice device, String filename, int length) {
        if (PocketPcDevice.PC1600.equals(device)) {
            // TODO: PC-1600
            return null;
            //return new Pc1600Header(FileType.BASIC, filename, 0, length, 0);
        } else {
            return new Ce158Header(FileType.BASIC, filename, 0, length, 0);
        }
    }

    /**
     * Factory method to create a header from a binary representation.
     *
     * @param headerBytes The bytes that make up the header. The array is allowed to contain much more than the header,
     *                    but it must start with the header in order to be recognized.
     * @return Populated and validated header
     */
    public static SerialHeader makeHeader(byte[] headerBytes) {
        SerialHeader header = null;
        try {
            header = new Ce158Header(headerBytes);
        } catch (IllegalArgumentException ex) {
            log.log(Level.FINE, "Not a Ce158 header");
            log.log(Level.FINEST, "Stack Trace", ex);
        }
        if (header != null) {
            return header;
        }
        try {
            header = new Pc1600Header(headerBytes);
        } catch (IllegalArgumentException ex) {
            log.log(Level.SEVERE, "Not a Serial header");
            log.log(Level.FINEST, "Stack Trace", ex);
        }
        return header;
    }

    /**
     * Checks the input, and prepends a header if necessary
     * @param inputBytes The program as loaded, it may or may not contain a header
     * @param startAddr Address to load the machine language program to
     * @param runAddr Auto-run address of the machine language program
     * @return Program with a header (if applicable)
     */
    public static byte[] prependHeaderIfNecessary(byte[] inputBytes, Integer startAddr, Integer runAddr, PocketPcDevice device) {
        // Only consider adding a header if an address is passed
        if (startAddr == null) {
            log.log(Level.FINE, "startAddr is null, not adding a header");
            return inputBytes;
        }
        // Analyze the byte array that is passed in and check if it already has a header
        try {
            SerialHeader.makeHeader(inputBytes);
            // If this line is reached,a good header is already present. Log and return the original array.
            log.log(Level.FINE, "No header required, one is already present");
            return inputBytes;
        } catch (IllegalArgumentException e) {
            // There is no header present, so one can be added
            SerialHeader newHeader = SerialHeader.makeHeader(device, FileType.MACHINE, "", startAddr, inputBytes.length, runAddr);
            // TODO: PC-1600 handling is missing.
            if (newHeader == null) {
                throw new IllegalArgumentException("PC-1600 device is not yet supported");
            }
            byte[] newHeaderBytes = newHeader.getHeader();
            byte[] outputBytes = Arrays.copyOf(newHeaderBytes, inputBytes.length + newHeaderBytes.length);
            System.arraycopy(inputBytes, 0, outputBytes, newHeaderBytes.length, inputBytes.length);
            return outputBytes;
        }
    }

    /**
     * Internal helper class to append a byte array to another byte array
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
     * Internal, very specific Sharp helper. Java doesn't know 2 byte Integers, Sharp does. This
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
     * Internal, very specific Sharp helper. Similar to the above method, a Java int is
     * created and directly returned (not appended to an array).
     *
     * @param source Byte array to append to
     * @param start  Integer that is being converted and appended at the end of the source
     * @return Combined array
     */
    static int makeInt(byte[] source, int start, int length) {
        if (length < 1 || length > 3) {
            throw new IllegalArgumentException("Length must be between 1 and 3");
        }
        if (start > source.length - length) {
            log.log(Level.SEVERE, "Invalid start index: " + start);
            return 0;
        }
        byte[] temp = new byte[4];
        for (int i = start; i < start + length; i++) {
            temp[i-start+(4-length)] = source[i];
        }
        ByteBuffer wrapped = ByteBuffer.wrap(temp);
        // Java does not have a 2 or three byte unsigned int (short is 2 bytes, but signed), therefore using an int (4 bytes)
        // so that even 0xFFFF or 0xFFFFFF remains positive. Autoboxing converts already a 0xFFFF into a negative 1 int, therefore
        // use bit arithmetic to remove unused left bits, leaving us with the 2 or 3 byte unsigned short that we want.
        int mask = (1 << (length << 3)) -1;
        return wrapped.getInt() & mask;
    }

    static byte[] convertIntToThreeByteByteArray(int value) {
        return ByteBuffer.allocate(3).putInt(value).array();
    }

    static int convertThreeByteByteArrayToInt(byte[] value) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(value);
        buffer.rewind();
        return buffer.getInt();
    }

    /**
     * Abstract method to convert between a FileType and the specific char in the header.
     *
     * @param type The FileType
     * @return Char that represents the type in this specific device
     */
    abstract char getTypeChar(FileType type);

    /**
     * bstract method to convert between a specific char in the header and the FileType.
     *
     * @param typeChar Char that represents the type in this specific device
     * @return The matching FileType
     */
    abstract FileType getFileType(char typeChar);

    /**
     * Build a serial header from the variables given in the constructor.
     *
     * @return serial header binary representation.
     */
    public abstract byte[] getHeader();

    /**
     * ENUM that represents the allowed file types in a safe way.
     * - BASIC: A tokenizes Basic program
     * - MACHINE: A machine language program
     * - RESERVE: The reserve area
     */
    public enum FileType {
        BASIC, MACHINE, RESERVE, VARIABLES
    }
}
