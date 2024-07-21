package ch.erzberger.sharppc;

import ch.erzberger.commandline.PocketPcDevice;
import ch.erzberger.filehandling.UtilsHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilsHandlerTest {

    @Test
    void getSerialUtilBasicApp() {
        List<String> result = UtilsHandler.getSerialUtilBasicApp(PocketPcDevice.PC1600);
        assertEquals(15, result.size());
    }
}