package ch.erzberger.sharppc.binaryfile;

import ch.erzberger.commandline.PocketPcDevice;
import lombok.Getter;
import lombok.extern.java.Log;

import java.nio.ByteBuffer;
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
            return null;
            //return new Pc1600Header(type, filename, startAddr, length, runAddr);
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
            header = null;
            //header = new Pc1600Header(headerBytes);
        } catch (IllegalArgumentException ex) {
            log.log(Level.SEVERE, "Not a Serial header");
            log.log(Level.FINEST, "Stack Trace", ex);
        }
        return header;
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
    static int makeInt(byte[] source, int start) {
        if (start > source.length - 2) {
            log.log(Level.SEVERE, "Invalid start index: " + start);
            return 0;
        }
        byte[] temp = new byte[2];
        temp[0] = source[start];
        temp[1] = source[start + 1];
        ByteBuffer wrapped = ByteBuffer.wrap(temp);
        // Java does not have a 2 byte unsigned int (short is 2 bytes, but signed), therefore using an int (4 bytes)
        // so that even 0xFFFF remains positive. Autoboxing converts 0xFFFF into a negative 1 int, therefore
        // use bit arithmetic to remove the upper half, leaving us with the 2 byte unsigned short that we want.
        return wrapped.getShort() & 0xFFFF;
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
        BASIC, MACHINE, RESERVE
    }
}
