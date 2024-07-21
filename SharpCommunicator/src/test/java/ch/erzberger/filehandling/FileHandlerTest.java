package ch.erzberger.filehandling;

import ch.erzberger.commandline.PocketPcDevice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileHandlerTest {

    @Test
    void crlf() {
        List<String> lines = FileHandler.readTextFile("src/test/resources/crlf.bas");
        assertEquals(3, lines.size());
    }

    @Test
    void cr() {
        List<String> lines = FileHandler.readTextFile("src/test/resources/cr.bas");
        assertEquals(3, lines.size());
    }

    @Test
    void lf() {
        List<String> lines = FileHandler.readTextFile("src/test/resources/lf.bas");
        assertEquals(3, lines.size());
    }

    @Test
    void lfandeof() {
        List<String> lines = FileHandler.readTextFile("src/test/resources/lfandeof.bas");
        assertEquals(3, lines.size());
    }

    @Test
    void eofatendoflastline() {
        List<String> lines = FileHandler.readTextFile("src/test/resources/eofatendoflastline.bas");
        assertEquals(3, lines.size());
    }
}