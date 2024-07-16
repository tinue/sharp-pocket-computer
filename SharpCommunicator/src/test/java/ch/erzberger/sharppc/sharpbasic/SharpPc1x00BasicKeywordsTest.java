package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.commandline.PocketPcDevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SharpPc1x00BasicKeywordsTest {
    private SharpBasicKeywordLookup lookup;

    @BeforeEach
    void setUp() {
        lookup = new SharpBasicKeywordLookup(PocketPcDevice.PC1500);
    }

    @Test
    void testGetCode() {
        assertEquals(0xF16F, lookup.getCodeFromKeyword("PEEK"));
        assertEquals(0xF16F, lookup.getCodeFromKeyword("peek"));
        assertEquals(0xF16E, lookup.getCodeFromKeyword("PEEK#"));
        assertEquals(0xF17A, lookup.getCodeFromKeyword("LEFT$"));
        assertEquals(0x0000, lookup.getCodeFromKeyword("PEEK "));
        assertEquals(0x0000, lookup.getCodeFromKeyword(""));
        assertEquals(0x0000, lookup.getCodeFromKeyword(null));
        assertEquals(0x0000, lookup.getCodeFromKeyword("DoesNotExist"));
    }

    @Test
    void matchKeyword() {
        assertEquals(2, lookup.matchKeyword("TO"));
        assertEquals(2, lookup.matchKeyword("TO100"));
        assertEquals(4, lookup.matchKeyword("PEEK(10)"));
        assertEquals(5, lookup.matchKeyword("PEEK#(10)"));
        assertEquals(8, lookup.matchKeyword("GLCURSOR"));
        assertEquals(8, lookup.matchKeyword("GLCURSOR(4)"));
        assertEquals(-1, lookup.matchKeyword("55"));
        assertEquals(-1, lookup.matchKeyword("T"));
        assertEquals(-1, lookup.matchKeyword(""));
        assertEquals(-1, lookup.matchKeyword(null));
    }
}