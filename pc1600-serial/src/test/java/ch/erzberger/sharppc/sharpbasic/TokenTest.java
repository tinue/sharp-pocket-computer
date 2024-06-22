package ch.erzberger.sharppc.sharpbasic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void keyword() {
        Keyword keyword = new Keyword("{POKE#}&FFFF,X");
        assertTrue(keyword.isValid());
        assertEquals(2, keyword.getBinary().length);
        assertEquals((byte) 0xF1, keyword.getBinary()[0]);
        assertEquals((byte) 0xA0, keyword.getBinary()[1]);
        assertEquals("POKE# ", keyword.getNormalizedRepresentation());
        assertEquals("&FFFF,X", keyword.getInputMinusToken());
        assertFalse(new Keyword("A=10").isValid());
        assertFalse(new Keyword("A=10{TO}").isValid());
        assertFalse(new Keyword("{NOPE}").isValid());
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
    }

    private void examineLineNumbers(LineNumber line) {
        assertTrue(line.isValid());
        assertEquals(2, line.getBinary().length);
        assertEquals((byte) 0x00, line.getBinary()[0]);
        assertEquals((byte) 0x0A, line.getBinary()[1]);
        assertEquals(" 10:", line.getNormalizedRepresentation());
        assertEquals("FOR I = 1 TO 100", line.getInputMinusToken());
    }

    @Test
    void line() {
        Line line = new Line("10 FOR I = 1 TO 100:PRINT I:NEXT I");
        assertTrue(line.isValid());
        assertEquals(" 10:FOR I = 1 TO 100:PRINT I:NEXT I", line.getNormalizedRepresentation());
        assertEquals("", line.getInputMinusToken());
    }

    @Test
    void sharpString() {
        SharpString sharpString = new SharpString("\"This is a string\"");
        assertEquals("\"This is a string\"", sharpString.getNormalizedRepresentation());
        sharpString = new SharpString("Something else");
        assertFalse(sharpString.isValid());
    }

    @Test
    void variable() {
        Variable variable = new Variable("I");
        assertEquals("I", variable.getNormalizedRepresentation());
        variable = new Variable("I$");
        assertEquals("I$", variable.getNormalizedRepresentation());
        variable = new Variable("A1A1");
        assertEquals("A1A1", variable.getNormalizedRepresentation());
        variable = new Variable("A1A1=");
        assertEquals("A1A1", variable.getNormalizedRepresentation());
        assertEquals("=", variable.getInputMinusToken());
        variable = new Variable("Aa");
        assertEquals("A", variable.getNormalizedRepresentation());
        assertEquals("a", variable.getInputMinusToken());
        variable = new Variable("+");
        assertFalse(variable.isValid());
        variable = new Variable("a");
        assertFalse(variable.isValid());
        assertEquals("a", variable.getInputMinusToken());
    }
}