package ch.erzberger.sharppc;

import ch.erzberger.serialhandler.ByteProcessor;
import ch.erzberger.serialhandler.SerialPortWrapper;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;

@Log
public class SharpCommunicator {
    static {
        // Load the logging properties from the jar file
        try (InputStream is = SharpCommunicator.class.getClassLoader().
                getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Cannot load log properties from jar file");
        }
    }

    public static void main(String[] args) {
        String operation = new CmdLineArgsChecker().checkArgs(args);
        if (operation == null) {
            System.exit(-1);
        }
        SerialPortWrapper wrapper = new SerialPortWrapper(null);
        if ("p".equals(operation)) {
            // Printer simulator
            ByteProcessor byteProcessor = new ProcessPrint();
            wrapper.openPort(byteProcessor);
            // The main thread will end, but the VM remains active due to the printer thread. This is what we want.
            // To stop, use Ctrl-C.
            return;
        }
        String loadSave = operation.substring(0, 1);
        String filename = operation.substring(2, operation.length() - 1);
        wrapper.openPort();
        if ("s".equals(loadSave)) {
            // Save, PC-1600 -> Disk
            ByteProcessor byteProcessor = new ProcessFiles(filename);
            wrapper.openPort(byteProcessor);
            return;
        }
        if ("l".equals(loadSave)) {
            // Load, Disk -> PC-1600
            byte[] buffer = SharpFileLoader.loadFile(filename);
            log.log(Level.INFO, "Read {0} bytes from buffer", buffer.length);
            int bytesWritten = wrapper.writeBytes(buffer);
            log.log(Level.FINE, "Written {0} bytes", bytesWritten);
            wrapper.closePort();
        }
    }
}
