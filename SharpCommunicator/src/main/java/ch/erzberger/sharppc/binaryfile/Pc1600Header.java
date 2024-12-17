package ch.erzberger.sharppc.binaryfile;

import ch.erzberger.commandline.PocketPcDevice;
import lombok.extern.java.Log;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

/**
 * Represents a PC-1600 header. The header structure is taken from the PC-1600 Technical Reference Manual, page 45,
 * section "3.3.1 Files Handles in BASIC". The same information can be found on page 167, differently written.
 * There is no documentation available for reserve area files, the below was found by trial and error.
 * The PC-1600 header is 16 bytes long. This is its structure:
 * - 0x00 Magic marker: 0xFF100000
 * - 0x04 File Type. These exist:
 * - 0x21 is a Basic program
 * - ???? is the reserve area
 * - 0x10 is binary data
 * - ???? is PRINT# output (not currently supported)
 * - 0x05 Length of data (3 bytes)
 * - 0x08 Binary load start address (3 bytes, ignored except for 0x10)
 * - 0x0B Binary autorun address (3 bytes, ignored except for 0x10)
 * - 0x0E End marker / filler: 0x000F
 * <br/>
 */
@Log
public class Pc1600Header extends SerialHeader {
    /**
     * Internally used constructor
     */
    protected Pc1600Header(FileType type, String filename, int startAddr, int length, int runAddr) {
        super(type, filename, startAddr, length, runAddr);
        super.device = PocketPcDevice.PC1600;
    }

    /**
     * Internally used constructor
     */
    protected Pc1600Header(byte[] header) {
        // Start by checking if this can be a header in the first place.
        // First, length: A header is 16 bytes, so anything short can't be a header
        if (header.length < 16) {
            // Can't be a header
            throw new IllegalArgumentException("Not a header, the length is shorter than 16 bytes: " + header.length);
        }
        // Check for the magic marker
        if (!(header[0] == (byte) 0xFF && header[1] == 0x10 && header[2] == 0 && header[3] == 0)) {
            throw new IllegalArgumentException("Not a header, magic marker is missing");
        }
        // The header is plausible, determine the file type
        super.type = getFileType((char) header[4]);
        // Add the addresses. They are swapped. Just hack a bit:
        byte[] temp = new byte[3];
        temp[0] = header[7];
        temp[1] = header[6];
        temp[2] = header[5];
        this.length = makeInt(temp, 0, 3);
        temp[0] = header[10];
        temp[1] = header[9];
        temp[2] = header[8];
        this.startAddr = FileType.MACHINE.equals(type) ? makeInt(temp, 0, 3) : 0;
        temp[0] = header[13];
        temp[1] = header[12];
        temp[2] = header[11];
        this.runAddr = FileType.MACHINE.equals(type) ? makeInt(temp, 0, 3) : 0;
    }

    /**
     * Build a binary PC-1600 header from the variables given in the constructor. Note that if the instance
     * is created using the binary constructor, the resulting binary header may be different. This is because
     * the fields that are declared "don't care" in the manual are always filled with zeroes, even if the input
     * binary contained some other value.
     *
     * @return 16 bit PC-1600 header binary representation.
     */
    public byte[] getHeader() {
        // Start with the magic marker, 0xFF100000
        byte[] headerBytes = new byte[]{(byte)0xFF,0x10,0,0,0};
        // Add the file type
        headerBytes[4] = (byte)getTypeChar(this.getType());
        // Add the three two-byte values
        headerBytes = appendInt(headerBytes, startAddr);
        headerBytes = appendInt(headerBytes, length);
        headerBytes = appendInt(headerBytes, runAddr);
        return headerBytes;
    }

    char getTypeChar(FileType type) {
        return switch (type) {
            case BASIC -> 0x21;
            case MACHINE -> 0x10;
            default -> 0;
        };
    }

    FileType getFileType(char typeChar) {
        return switch (typeChar) {
            case 0x21 -> FileType.BASIC;
            case 0x10 -> FileType.MACHINE;
            // TODO: Figure out Reserve and Variable
            default -> throw new IllegalArgumentException("Not a header, unknown file type: " + typeChar);
        };
    }
}
