package ch.erzberger.tokenizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SharpPc1500BasicKeywordsTest {
    @Test
    void testGetCode() {
        assertEquals(0xF16F, SharpPc1500BasicKeywords.getCode("PEEK"));
        assertEquals(0xF16F, SharpPc1500BasicKeywords.getCode("peek"));
        assertEquals(0xF16E, SharpPc1500BasicKeywords.getCode("PEEK#"));
        assertNull(SharpPc1500BasicKeywords.getCode(""));
        assertNull(SharpPc1500BasicKeywords.getCode(null));
        assertNull(SharpPc1500BasicKeywords.getCode("DoesNotExist"));
    }

    @Test
    void matchKeyword() {
        assertEquals(2, SharpPc1500BasicKeywords.matchKeyword("TO"));
        assertEquals(2, SharpPc1500BasicKeywords.matchKeyword("TO100"));
        assertEquals(4, SharpPc1500BasicKeywords.matchKeyword("PEEK(10)"));
        assertEquals(5, SharpPc1500BasicKeywords.matchKeyword("PEEK#(10)"));
        assertEquals(8, SharpPc1500BasicKeywords.matchKeyword("GLCURSOR"));
        assertEquals(8, SharpPc1500BasicKeywords.matchKeyword("GLCURSOR(4)"));
        assertEquals(-1, SharpPc1500BasicKeywords.matchKeyword("55"));
        assertEquals(-1, SharpPc1500BasicKeywords.matchKeyword("T"));
        assertEquals(-1, SharpPc1500BasicKeywords.matchKeyword(""));
        assertEquals(-1, SharpPc1500BasicKeywords.matchKeyword(null));
    }
}