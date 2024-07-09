package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.commandline.PocketPcDevice;
import org.junit.jupiter.api.Test;

import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;

class StatementTest {
    private final PocketPcDevice DEVICE = PocketPcDevice.PC1500;

    @Test
    void escapeBasicKeywords() {
        testEscape("{IF}{INKEY$}<>\"\"{AND}C$=\"BEGIN\"{GOTO}260","IFINKEY$<>\"\"ANDC$=\"BEGIN\"GOTO260");
        testEscape("{INPUT}\"Letter:\";L$", "INPUT\"Letter:\";L$");
        testEscape("{FOR}{PRINT}","FORPRINT");
        testEscape("{CHR$}", "CHR$");
        testEscape("{PRINT}{FOR}{PRINT}","PRINTFORPRINT");
        testEscape("{PRINT}{PRINT}{PRINT}","PRINTPRINTPRINT");
        testEscape("{FOR}I=1{TO}100","FORI=1TO100");
        testEscape("A{FOR}{LET}","AFORLET");
        testEscape("{FOR}I","FORI");
        testEscape("A=10{REMThis is a test}","A=10REMThis is a test");
        testEscape("{LPRINT}AB","LPRINTAB");
        testEscape("{LPRINT}\"Hi there\"","LPRINT\"Hi there\"");
        testEscape("10*5{PROTOCOL}{IF}3*3","10*5PROTOCOLIF3*3");
        testEscape("\"A\"","\"A\"");
        testEscape("77*100","77*100");
    }

    @Test
    void makeStatement() {
        Statement statement = new Statement("FORPRINTLET", DEVICE);
        assertEquals("FOR PRINT LET ", statement.getNormalizedRepresentation());
        assertEquals("f1a5f097f198", HexFormat.of().formatHex(statement.getBinaryRepresentation()));
        statement = new Statement("PRINTCHR$127;", DEVICE);
        assertEquals("PRINT CHR$ 127;", statement.getNormalizedRepresentation());
        statement = new Statement("FOR\"bla bla\"", DEVICE);
        assertEquals("FOR \"bla bla\"", statement.getNormalizedRepresentation());
        statement = new Statement("\"bla bla\"FOR", DEVICE);
        assertEquals("\"bla bla\"FOR ", statement.getNormalizedRepresentation());
        statement = new Statement("FORI=1TO100", DEVICE);
        assertEquals("FOR I=1TO 100", statement.getNormalizedRepresentation());
        assertEquals("f1a5493d31f1b1313030", HexFormat.of().formatHex(statement.getBinaryRepresentation()));
        statement = new Statement("IFINKEY$<>\"\"ANDC$=\"BEGIN\"GOTO260", PocketPcDevice.PC1600);
        assertEquals("IF INKEY$ <>\"\"AND C$=\"BEGIN\"GOTO 260", statement.getNormalizedRepresentation());
        statement = new Statement("INPUT\"Letter:\";L$", PocketPcDevice.PC1600);
        assertEquals("INPUT \"Letter:\";L$", statement.getNormalizedRepresentation());
    }

    private void testEscape(String expectedResult, String input) {
        Statement statement = new Statement("FOR", DEVICE);
        String result = statement.escapeBasicKeywords(input, DEVICE);
        assertEquals(expectedResult, result);
    }
}