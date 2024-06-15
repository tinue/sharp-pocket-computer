package ch.erzberger.sharppc.sharpbasic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatementTest {
    @Test
    void normalizeStatement() {
        testNormalization("Hithere", "Hi there");
        testNormalization("\"Hi there\"", "\"Hi there\"");
        testNormalization("Hellothere\"Hi there\"helloagain", "Hello     there   \"Hi there\" hello again  ");
        testNormalization("A=10REMThis is a test", "A=10 REM This is a test");
        testNormalization("A=10REMThis is a test", "A=10 R  E M This is a test");
        testNormalization("A=10REMThis is a test", "A=10 R  E MThis is a test");
        testNormalization("A=10REMThis \"is a\" test", "A=10 REM This \"is a\" test");
    }

    private void testNormalization(String expectedResult, String input) {
        Statement statement = new Statement("FOR");
        String result = statement.normalizeStatement(input);
        assertEquals(expectedResult, result);
    }

    @Test
    void escapeBasicKeywords() {
        testEscape("{FOR}I=1{TO}100","FOR I=1 TO 100");
        testEscape("A{FOR}{LET}","AFORLET");
        testEscape("{FOR}I","FO RI");
        testEscape("A=10{REMThis is a test}","A = 10 REMThis is a test");
        testEscape("{LPRINT}AB","LPRINTAB");
        testEscape("{LPRINT}\"Hi there\"","LPRINT \"Hi there\"");
        testEscape("10*5{PROTOCOL}{IF}3*3","10*5 PROTOCOLIF 3*3");
        testEscape("\"A\"","\"A\"");
        testEscape("77*100","77 * 100");
    }

    private void testEscape(String expectedResult, String input) {
        Statement statement = new Statement("FOR");
        String result = statement.escapeBasicKeywords(input);
        assertEquals(expectedResult, result);
    }
}