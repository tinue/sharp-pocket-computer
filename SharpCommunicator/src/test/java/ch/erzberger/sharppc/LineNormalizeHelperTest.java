package ch.erzberger.sharppc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineNormalizeHelperTest {

    @Test
    void normalizeLineBreaks() {
    }

    @Test
    void cleanupWhiteSpace() {
        assertEquals("abcdef", LineNormalizeHelper.cleanupWhiteSpace("abc def"));
        assertEquals("abcdef\"hij klm\"nopqrs", LineNormalizeHelper.cleanupWhiteSpace("abc def \"hij klm\" nop qrs"));
        assertEquals("10:FORI=1TO100:PRINT\"I = \";I:NEXTI", LineNormalizeHelper.cleanupWhiteSpace(" 10: FOR I=1 TO 100: PRINT\"I = \";I: NEXT I"));
        assertEquals("10REM This is a test", LineNormalizeHelper.cleanupWhiteSpace(" 10 REM This is a test"));
        assertEquals("10RaEbMThisisatest", LineNormalizeHelper.cleanupWhiteSpace(" 10 RaEbM This is a test"));
        assertEquals("10REM This \"is a test", LineNormalizeHelper.cleanupWhiteSpace(" 10 REM This \"is a test"));
        assertEquals("10PRINT\"REM This is a test\":NEXTI", LineNormalizeHelper.cleanupWhiteSpace(" 10 PRINT \"REM This is a test\": NEXT I"));
        assertEquals("10REM This \"is\" a test", LineNormalizeHelper.cleanupWhiteSpace(" 10 REM This \"is\" a test"));
        assertEquals("10:PRINTI:REM This is a test", LineNormalizeHelper.cleanupWhiteSpace(" 10: PRINT I: R  E M This is a test"));
        assertEquals("10RE\"abc def\"M:PRINTI", LineNormalizeHelper.cleanupWhiteSpace("10 RE\"abc def\"M: PRINT I"));
    }
}