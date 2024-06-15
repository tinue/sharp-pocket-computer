package ch.erzberger.sharppc.sharpbasic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void keyword()  {
        Keyword keyword = new Keyword("{POKE#}&FFFF,X");
        assertTrue(keyword.isValid());
        assertEquals(2, keyword.getBinary().length);
        assertEquals((byte)0xF1, keyword.getBinary()[0]);
        assertEquals((byte)0xA0, keyword.getBinary()[1]);
        assertEquals("POKE# ", keyword.getNormalizedRepresentation());
        assertEquals("&FFFF,X", keyword.getInputMinusToken());
        assertFalse(new Keyword("A=10").isValid());
        assertFalse(new Keyword("A=10{TO}").isValid());
        assertFalse(new Keyword("{NOPE}").isValid());
    }

    @Test
    void lineNumber() {
        examineLineNumbers(new LineNumber("10 FOR I = 1 TO 100"));
        examineLineNumbers(new LineNumber("10   FOR I = 1 TO 100"));
        examineLineNumbers(new LineNumber("10FOR I = 1 TO 100"));
        assertEquals("", new LineNumber("10 ").getInputMinusToken());
        assertTrue(new LineNumber("10").isValid());
        assertFalse(new LineNumber("LET").isValid());
        assertEquals("LET", new LineNumber("LET").getInputMinusToken());
        assertFalse(new LineNumber(" 10 FOR").isValid());
        assertEquals(" 10 FOR", new LineNumber(" 10 FOR").getInputMinusToken());
    }

    private void examineLineNumbers(LineNumber line) {
        assertTrue(line.isValid());
        assertEquals(2, line.getBinary().length);
        assertEquals((byte)0x00, line.getBinary()[0]);
        assertEquals((byte)0x0A, line.getBinary()[1]);
        assertEquals("10 ", line.getNormalizedRepresentation());
        assertEquals("FOR I = 1 TO 100", line.getInputMinusToken());
    }

    @Test
    void line() {
        Line line = new Line("10 FOR I = 1 TO 100:PRINT I:NEXT I");
        assertTrue(line.isValid());
        assertEquals("10 FOR I = 1 TO 100:PRINT I:NEXT I", line.getNormalizedRepresentation());
        assertEquals("", line.getInputMinusToken());
    }
}