package ch.erzberger.sharppc;

import ch.erzberger.commandline.PocketPcDevice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SharpFileLoaderTest {

    @Test
    void crlf() {
        List<String> lines = SharpFileLoader.loadAsciiFile("src/test/resources/crlf.bas", false, PocketPcDevice.PC1600);
        assertEquals(3, lines.size());
        lines = SharpFileLoader.loadAsciiFile("src/test/resources/crlf.bas", true, PocketPcDevice.PC1600);
        assertEquals(18, lines.size());
        lines = SharpFileLoader.loadAsciiFile("src/test/resources/crlf.bas", true, PocketPcDevice.PC1500);
        assertEquals(12, lines.size());
    }

    @Test
    void cr() {
        List<String> lines = SharpFileLoader.loadAsciiFile("src/test/resources/cr.bas", false, PocketPcDevice.PC1600);
        assertEquals(3, lines.size());
        lines = SharpFileLoader.loadAsciiFile("src/test/resources/cr.bas", true, PocketPcDevice.PC1600);
        assertEquals(18, lines.size());
    }

    @Test
    void lf() {
        List<String> lines = SharpFileLoader.loadAsciiFile("src/test/resources/lf.bas", false, PocketPcDevice.PC1600);
        assertEquals(3, lines.size());
        lines = SharpFileLoader.loadAsciiFile("src/test/resources/lf.bas", true, PocketPcDevice.PC1600);
        assertEquals(18, lines.size());
    }

    @Test
    void lfandeof() {
        List<String> lines = SharpFileLoader.loadAsciiFile("src/test/resources/lfandeof.bas", false, PocketPcDevice.PC1600);
        assertEquals(3, lines.size());
        lines = SharpFileLoader.loadAsciiFile("src/test/resources/lfandeof.bas", true, PocketPcDevice.PC1600);
        assertEquals(18, lines.size());
    }

    @Test
    void eofatendoflastline() {
        List<String> lines = SharpFileLoader.loadAsciiFile("src/test/resources/eofatendoflastline.bas", false, PocketPcDevice.PC1600);
        assertEquals(3, lines.size());
        lines = SharpFileLoader.loadAsciiFile("src/test/resources/eofatendoflastline.bas", true, PocketPcDevice.PC1600);
        assertEquals(18, lines.size());
    }
}