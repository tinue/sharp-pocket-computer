package ch.erzberger.sharppc;

import ch.erzberger.commandline.CmdLineArgs;
import ch.erzberger.commandline.CmdLineArgsChecker;
import ch.erzberger.commandline.Direction;
import ch.erzberger.commandline.PocketPcDevice;
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
        CmdLineArgs cmdLineArgs = new CmdLineArgsChecker().checkArgs(args);
        if (cmdLineArgs == null) {
            System.exit(-1);
        }
        SerialPortWrapper wrapper = new SerialPortWrapper(null);
        Direction direction = cmdLineArgs.direction();
        String filename = cmdLineArgs.filename();
        // Next two values are good for PC-1600
        int baudRate = 9600;
        boolean handShake = true;
        if (PocketPcDevice.PC1500.equals(cmdLineArgs.device())) {
            // PC-1500 needs no handshake, and a higher baud rate
            baudRate = 19200;
            handShake = false;
        }
        wrapper.openPort(baudRate, handShake);
        if (Direction.FROMPOCKETOPC.equals(direction)) {
            // Save, PC-1600 -> Disk
            ByteProcessor byteProcessor = new ProcessFiles(filename);
            wrapper.openPort(baudRate, handShake, byteProcessor);
        } else {
            // Load, Disk -> PC-1600
            byte[] buffer = SharpFileLoader.loadFile(filename, cmdLineArgs.addUtil(), cmdLineArgs.device());
            log.log(Level.INFO, "Read {0} bytes from buffer", buffer.length);
            int bytesWritten = wrapper.writeBytes(buffer);
            log.log(Level.FINE, "Written {0} bytes", bytesWritten);
            wrapper.closePort();
        }
    }
}
