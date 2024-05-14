package ch.erzberger.sharppc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SharpFileLoaderTest {

    @Test
    void convertLinesIntoByteArray() {
        List<String> lines = Arrays.asList("Line1", "Line2", "Line3");
        byte[] result = SharpFileLoader.convertLinesIntoByteArray(lines);
        assertEquals(22, result.length); // 3 times 5 chars, plus 3 time 2 for CR/LF, plus EOF at the end
        assertEquals(0x0D, result[5]);
        assertEquals(0x0A, result[6]);
        assertEquals(0x1A, result[result.length - 1]);
    }

    @Test
    void crlf() {
        byte[] result = SharpFileLoader.loadFile("src/test/resources/crlf.bas", false);
        assertEquals(50, result.length);
        result = SharpFileLoader.loadFile("src/test/resources/crlf.bas", true);
        assertEquals(400, result.length);
    }

    @Test
    void cr() {
        byte[] result = SharpFileLoader.loadFile("src/test/resources/cr.bas", false);
        assertEquals(50, result.length);
        result = SharpFileLoader.loadFile("src/test/resources/cr.bas", true);
        assertEquals(400, result.length);
    }

    @Test
    void lf() {
        byte[] result = SharpFileLoader.loadFile("src/test/resources/lf.bas", false);
        assertEquals(50, result.length);
        result = SharpFileLoader.loadFile("src/test/resources/lf.bas", true);
        assertEquals(400, result.length);
    }

    @Test
    void lfandeof() {
        byte[] result = SharpFileLoader.loadFile("src/test/resources/lfandeof.bas", false);
        assertEquals(50, result.length);
        result = SharpFileLoader.loadFile("src/test/resources/lfandeof.bas", true);
        assertEquals(400, result.length);
    }

    @Test
    void eofatendoflastline() {
        byte[] result = SharpFileLoader.loadFile("src/test/resources/eofatendoflastline.bas", false);
        assertEquals(50, result.length);
        result = SharpFileLoader.loadFile("src/test/resources/eofatendoflastline.bas", true);
        assertEquals(400, result.length);
    }

    @Test
    void binary() {
        byte[] result = SharpFileLoader.loadFile("src/test/resources/binary.img", false);
        assertEquals(46, result.length); // Misses three CR and the EOF
        result = SharpFileLoader.loadFile("src/test/resources/binary.img", true);
        assertEquals(46, result.length); // On binary files the addon gets ignored
    }
}