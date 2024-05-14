package ch.erzberger.sharppc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Pc1600SerialUtilsTest {

    @Test
    void getSerialUtilBasicApp() {
        List<String> result = Pc1600SerialUtils.getSerialUtilBasicApp();
        assertEquals(15, result.size());
    }
}