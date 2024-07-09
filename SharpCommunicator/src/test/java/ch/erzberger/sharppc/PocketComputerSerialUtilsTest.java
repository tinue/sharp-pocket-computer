package ch.erzberger.sharppc;

import ch.erzberger.commandline.PocketPcDevice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PocketComputerSerialUtilsTest {

    @Test
    void getSerialUtilBasicApp() {
        List<String> result = PocketComputerSerialUtils.getSerialUtilBasicApp(PocketPcDevice.PC1600);
        assertEquals(15, result.size());
    }
}