package ch.erzberger.sharppc;

import ch.erzberger.commandline.CmdLineArgs;
import ch.erzberger.commandline.CmdLineArgsChecker;
import ch.erzberger.commandline.PocketPcDevice;
import ch.erzberger.serialhandler.ByteProcessor;
import ch.erzberger.serialhandler.SerialPortWrapper;
import ch.erzberger.sharppc.sharpbasic.Program;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
        if (cmdLineArgs.getInputFile() != null && cmdLineArgs.getOutputFile() == null) {
            // Send data from the input file to the PocketPC
            sendFileToPocketPc(cmdLineArgs);
        }
        if (cmdLineArgs.getInputFile() == null && cmdLineArgs.getOutputFile() != null) {
            // Receive data from the PocketPC and send it to a file
            receiveFileFromPocketPc(cmdLineArgs);
            return;
        }
        // Transform data from one file format to another
        convertFile(cmdLineArgs);
    }

    private static SerialPortWrapper initPort(PocketPcDevice device, ByteProcessor byteProcessor) {
        // Transform data from one file format to another
        SerialPortWrapper wrapper = new SerialPortWrapper(null);
        // Next two values are good for PC-1600
        int baudRate = 9600;
        boolean handShake = true;
        if (PocketPcDevice.PC1500.equals(device)) {
            // PC-1500 needs no handshake, and a higher baud rate
            baudRate = 19200;
            handShake = false;
        }
        if (byteProcessor == null) {
            wrapper.openPort(baudRate, handShake);
        } else {
            wrapper.openPort(baudRate, handShake, byteProcessor);
        }
        return wrapper;
    }

    private static void convertFile(CmdLineArgs cmdLineArgs) {
        String filename = cmdLineArgs.getInputFile();
        PocketPcDevice device = cmdLineArgs.getDevice();
        List<String> lines = SharpFileLoader.loadAsciiFile(filename, cmdLineArgs.isUtil(), device);
        Program theProgram = new Program(filename, lines, device);
        if (cmdLineArgs.isCompact()) {
            System.out.println(theProgram.getShortRepresentation());
        } else {
            System.out.println(theProgram.getNormalizedRepresentation());
        }
    }

    private static void receiveFileFromPocketPc(CmdLineArgs cmdLineArgs) {
        // Save, Pocket Computer -> Disk, just save what's coming, no conversions.
        ByteProcessor byteProcessor = new ProcessFiles(cmdLineArgs.getOutputFile(), cmdLineArgs.getDevice());
        initPort(cmdLineArgs.getDevice(), byteProcessor);
    }

    private static void sendFileToPocketPc(CmdLineArgs cmdLineArgs) {
        // Load a program from the PC to the Sharp Pocket Computer
        // Logic:
        // .BAS files will be converted to binary first for the PC-1500. --ascii option prevents this.
        // on the PC-1600, the file can't yet be converted. --ascii therefore has no effect there.
        // Any other file will be loaded as binary.
        SerialPortWrapper wrapper = initPort(cmdLineArgs.getDevice(), null);
        String filename = cmdLineArgs.getInputFile();
        PocketPcDevice device = cmdLineArgs.getDevice();
        boolean isBasicProgram = filename.toUpperCase().endsWith(".BAS");
        boolean needToConvert = isBasicProgram && !cmdLineArgs.isAscii();
        boolean binaryload = needToConvert || !isBasicProgram;
        if (binaryload) {
            // Binary load is required.
            byte[] program;
            if (needToConvert) {
                List<String> lines = SharpFileLoader.loadAsciiFile(filename, cmdLineArgs.isUtil(), device);
                Program theProgram = new Program(filename, lines, device);
                program = theProgram.getBinaryRepresentation();
            } else {
                program = SharpFileLoader.loadBinaryFile(filename, device);
            }
            if (PocketPcDevice.PC1500.equals(device)) {
                // The PC-1500 needs two things:
                // 1. The first 28 bytes are a header, and after the header a pause is required (at least 100ms)
                // 2. It can't keep up with the fixed 19200 baud of the CE-158X. A pause is required between bytes.
                // Split into header and program
                byte[] header = new byte[28];
                byte[] programBytes = new byte[program.length - 28];
                System.arraycopy(program, 0, header, 0, 28);
                System.arraycopy(program, 28, programBytes, 0, programBytes.length);
                // Write the header. It can be sent with full speed, 28 bytes seem to not be an issue
                wrapper.writeBytes(header);
                // Now wait for the header to be processed
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // Next, send the program byte by byte and wait 1ms after each byte
                wrapper.writeBytes(programBytes, 1L);
                // Wait a bit after the last byte before closing the port.
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                // The PC-1600 has no issues with full speed sending. Its 16 byte header is just loaded along with the rest
                wrapper.writeBytes(program);
            }
        } else {
            // ASCII mode
            // Load the file into a list fo Strings. The helper will normalize the file, i.e. remove extra end of file markers etc.
            List<String> lines = SharpFileLoader.loadAsciiFile(filename, cmdLineArgs.isUtil(), device);
            // Send each line individually to the PocketPC. This gives the device time to process the line
            for (String line : lines) {
                byte[] lineBytes = SharpFileLoader.convertStringIntoByteArray(line, device);
                wrapper.writeBytes(lineBytes);
                // The PC-1500 needs more time to handle one line. Add some wait.
                if (PocketPcDevice.PC1500.equals(device)) {
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            // To finalize, send an End-Of-File marker
            if (PocketPcDevice.PC1500.equals(device)) {
                // The PC-1500 stops receiving when two CRs are received in a row
                wrapper.writeBytes(new byte[]{0x0D});
            } else {
                // The PC-1600 stops on an EOF ASCII code
                wrapper.writeBytes(new byte[]{0x1A});
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        wrapper.closePort();
    }
}
