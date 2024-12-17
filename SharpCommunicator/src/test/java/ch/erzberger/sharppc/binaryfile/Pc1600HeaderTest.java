package ch.erzberger.sharppc.binaryfile;

import ch.erzberger.commandline.PocketPcDevice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Pc1600HeaderTest {
    private final byte[] basic = new byte[]{(byte)0xFF, 0x10, 0, 0, 0x21, 0x0A, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0F, 0, 0x0A, 0x07, (byte)0xF0, (byte)0x97, 0x22, 0x48, 0x69, 0x22, 0x0D};
    private final byte[] binary = new byte[]{(byte)0xFF, 0x10, 0, 0, 0x10, 0x05, 0, 0, 0x01, 0, 0x01, 0x03, 0, 0x01, 0, 0x0F, (byte)0xF3, (byte)0xC3, 0x23, 0x03, (byte)0xC3};

    @Test
    void binary() {
        SerialHeader header = SerialHeader.makeHeader(PocketPcDevice.PC1600, SerialHeader.FileType.MACHINE, "", 1, 5, 3);
        assertArrayEquals(binary, header.getHeader());
    }

    @Test
    void basic() {
        SerialHeader header = SerialHeader.makeHeader(PocketPcDevice.PC1600, SerialHeader.FileType.BASIC, "", 0, 5, 0);
        assertArrayEquals(basic, header.getHeader());
    }

    @Test
    void headerBasic() {
        SerialHeader header = SerialHeader.makeHeader(basic);
        assertEquals(SerialHeader.FileType.BASIC, header.getType());
        assertEquals(0x0A, header.getLength());
        assertEquals(0, header.getStartAddr());
        assertEquals(0, header.getRunAddr());
    }

    @Test
    void headerBinary() {
        SerialHeader header = SerialHeader.makeHeader(binary);
        assertEquals(SerialHeader.FileType.MACHINE, header.getType());
        assertEquals(0x05, header.getLength());
        assertEquals(0x010001, header.getStartAddr()); // First 01 is the bank
        assertEquals(0x010003, header.getRunAddr());   // First 01 is the bank
    }

    @Test
    void backAndForth() {
        assertArrayEquals(binary, SerialHeader.makeHeader(binary).getHeader());
        assertArrayEquals(basic, SerialHeader.makeHeader(basic).getHeader());
    }
}