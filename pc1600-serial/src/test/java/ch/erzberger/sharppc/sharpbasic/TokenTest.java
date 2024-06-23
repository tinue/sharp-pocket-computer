package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.commandline.PocketPcDevice;
import org.junit.jupiter.api.Test;

import java.util.HexFormat;

import static ch.erzberger.sharppc.sharpbasic.Token.appendBytes;
import static org.junit.jupiter.api.Assertions.*;

class TokenTest {
    private final PocketPcDevice DEVICE = PocketPcDevice.PC1500;

    @Test
    void keyword() {
        Keyword keyword = new Keyword("{POKE#}&FFFF,X", DEVICE);
        assertTrue(keyword.isValid());
        assertEquals(2, keyword.getBinaryRepresentation().length);
        assertEquals((byte) 0xF1, keyword.getBinaryRepresentation()[0]);
        assertEquals((byte) 0xA0, keyword.getBinaryRepresentation()[1]);
        assertEquals("POKE# ", keyword.getNormalizedRepresentation());
        assertEquals("&FFFF,X", keyword.getInputMinusToken());
        keyword = new Keyword("{REMBla Bla}", DEVICE);
        assertEquals("REM Bla Bla", keyword.getNormalizedRepresentation());
        assertEquals("f1ab426c6120426c61", HexFormat.of().formatHex(keyword.getBinaryRepresentation()));
        keyword = new Keyword("{REM Bla Bla}", DEVICE);
        assertEquals("REM Bla Bla", keyword.getNormalizedRepresentation());
        assertFalse(new Keyword("A=10", DEVICE).isValid());
        assertFalse(new Keyword("A=10{TO}", DEVICE).isValid());
        assertFalse(new Keyword("{NOPE}", DEVICE).isValid());
    }

    @Test
    void lineNumber() {
        examineLineNumbers(new LineNumber("10 FOR I = 1 TO 100"));
        examineLineNumbers(new LineNumber("10:FOR I = 1 TO 100"));
        examineLineNumbers(new LineNumber(" 10:FOR I = 1 TO 100"));
        examineLineNumbers(new LineNumber("10 : FOR I = 1 TO 100"));
        assertEquals("FOR I = 1 TO 100", new LineNumber("10 : FOR I = 1 TO 100").getInputMinusToken());
        examineLineNumbers(new LineNumber("10   FOR I = 1 TO 100"));
        assertEquals("FOR I = 1 TO 100", new LineNumber("10   FOR I = 1 TO 100").getInputMinusToken());
        examineLineNumbers(new LineNumber("10FOR I = 1 TO 100"));
        assertEquals("", new LineNumber("10 ").getInputMinusToken());
        assertTrue(new LineNumber("10").isValid());
        assertFalse(new LineNumber(":").isValid());
        assertFalse(new LineNumber("LET").isValid());
        assertEquals("LET", new LineNumber("LET").getInputMinusToken());
        // Special case: Two colons after line number. The second colon must not be part of the line number
        LineNumber result = new LineNumber("10:: FOR");
        assertTrue(result.isValid());
        assertEquals(": FOR", result.getInputMinusToken());
        assertEquals((byte) 0x00, result.getBinaryRepresentation()[0]);
        assertEquals((byte) 0x0A, result.getBinaryRepresentation()[1]);
    }

    private void examineLineNumbers(LineNumber line) {
        assertTrue(line.isValid());
        assertEquals(3, line.getBinaryRepresentation().length);
        assertEquals((byte) 0x00, line.getBinaryRepresentation()[0]);
        assertEquals((byte) 0x0A, line.getBinaryRepresentation()[1]);
        assertEquals(" 10:", line.getNormalizedRepresentation());
        assertEquals("FOR I = 1 TO 100", line.getInputMinusToken());
    }

    @Test
    void line() {
        Line line = new Line("10 FOR I = 1 TO 100:PRINT I:NEXT I", DEVICE);
        assertTrue(line.isValid());
        assertEquals(" 10:FOR I=1TO 100:PRINT I:NEXT I", line.getNormalizedRepresentation());
        assertEquals("", line.getInputMinusToken());
        line = new Line("10 REM Test program", DEVICE);
        assertTrue(line.isValid());
        line = new Line("10 \"BIO\":CLEAR :INPUT \"Biorhythm, Year? \";L, \"Month?\";M", DEVICE);
        assertEquals(" 10:\"BIO\":CLEAR :INPUT \"Biorhythm, Year? \";L,\"Month?\";M", line.getNormalizedRepresentation());
        assertEquals("000A2C2242494F223AF1873AF0912242696F72687974686D2C20596561723F20223B4C2C224D6F6E74683F223B4D0D", HexFormat.of().formatHex(line.getBinaryRepresentation()).toUpperCase());
    }

    @Test
    void other() {
        Other other = new Other("I=1{TO}");
        assertEquals("I=1", other.getNormalizedRepresentation());
        assertEquals("{TO}", other.getInputMinusToken());
        other = new Other("{TO}");
        assertFalse(other.isValid());
    }

    @Test
    void combineArrays() {
        byte[] one = new byte[]{(byte)0x1A, (byte)0x2A};
        byte[] two = new byte[]{(byte)0xFF};
        byte[] three = appendBytes(one, two);
        assertEquals(3, three.length);
        assertEquals((byte)0x1A, three[0]);
        assertEquals((byte)0xFF, three[2]);
    }
}