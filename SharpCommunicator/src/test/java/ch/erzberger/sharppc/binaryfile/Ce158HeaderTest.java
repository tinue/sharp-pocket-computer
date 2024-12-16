package ch.erzberger.sharppc.binaryfile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Ce158HeaderTest {
    private final byte[] binary = new byte[]{0x01, 0x42, 0x43, 0x4F, 0x4D, 0x56, 0x41, 0x52, 0x53, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x78, (byte) 0xC0, 0, 0x1F, (byte) 0xFF, (byte) 0xFF};
    private final byte[] basic = new byte[]{0x01, 0x40, 0x43, 0x4F, 0x4D, 0x54, 0x65, 0x73, 0x74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0B, 0, 0};
    private final byte[] reserve = new byte[]{0x01, 0x41, 0x43, 0x4F, 0x4D, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte) 0xBB, 0, 0};
    private final byte[] reserveDoNotCare = new byte[]{0x01, 0x41, 0x43, 0x4F, 0x4D, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x01, 0x02, 0, (byte) 0xBB, (byte) 0xA0, (byte) 0xFF};

    @Test
    void binary() {
        Ce158Header header = new Ce158Header(SerialHeader.FileType.MACHINE, "VARS", 0x78C0, 0x001F, 0xFFFF);
        assertArrayEquals(binary, header.getHeader());
    }

    @Test
    void reserve() {
        Ce158Header header = new Ce158Header(SerialHeader.FileType.RESERVE, "", 0x08, 0xBB, 0);
        assertArrayEquals(reserve, header.getHeader());
    }

    @Test
    void basic() {
        Ce158Header header = new Ce158Header(SerialHeader.FileType.BASIC, "Test", 0, 0x0B, 0);
        assertArrayEquals(basic, header.getHeader());
    }

    @Test
    void headerBasic() {
        Ce158Header header = new Ce158Header(basic);
        assertEquals(SerialHeader.FileType.BASIC, header.getType());
        assertEquals("Test", header.getFilename());
        assertEquals(0x0B, header.getLength());
        assertEquals(0, header.getStartAddr());
        assertEquals(0, header.getRunAddr());
    }

    @Test
    void headerReserve() {
        Ce158Header header = new Ce158Header(reserve);
        assertEquals(SerialHeader.FileType.RESERVE, header.getType());
        assertEquals("", header.getFilename());
        assertEquals(0xBB, header.getLength());
        assertEquals(0, header.getStartAddr());
        assertEquals(0, header.getRunAddr());
    }

    @Test
    void headerBinary() {
        Ce158Header header = new Ce158Header(binary);
        assertEquals(SerialHeader.FileType.MACHINE, header.getType());
        assertEquals("VARS", header.getFilename());
        assertEquals(0x1F, header.getLength());
        assertEquals(0x78C0, header.getStartAddr());
        assertEquals(0xFFFF, header.getRunAddr());
    }

    @Test
    void backAndForth() {
        assertArrayEquals(binary, new Ce158Header(binary).getHeader());
        assertArrayEquals(basic, new Ce158Header(basic).getHeader());
        assertArrayEquals(reserve, new Ce158Header(reserve).getHeader());
        // Special case: The reserveDoNotCare header has some "do not care" value set to non-zero. These should get removed.
        assertArrayEquals(reserve, new Ce158Header(reserveDoNotCare).getHeader());
    }
}