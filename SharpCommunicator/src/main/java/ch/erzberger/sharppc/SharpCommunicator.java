package ch.erzberger.sharppc;

import ch.erzberger.commandline.CmdLineArgs;
import ch.erzberger.commandline.CmdLineArgsChecker;
import ch.erzberger.commandline.FileFormat;
import ch.erzberger.commandline.PocketPcDevice;
import ch.erzberger.filehandling.FileHandler;
import ch.erzberger.filehandling.UtilsHandler;
import ch.erzberger.serialhandler.ByteProcessor;
import ch.erzberger.serialhandler.SerialPortWrapper;
import ch.erzberger.serialhandler.SerialToDeviceSender;
import ch.erzberger.sharppc.binaryfile.SerialHeader;
import ch.erzberger.sharppc.sharpbasic.Program;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            System.exit(1);
        }
        // The input will either be loaded into a byte array, or into a List of Strings. Declare both variables.
        byte[] inputFileBytes = null;
        List<String> inputFileLines = null;
        // Step one: Read the file
        String inputFileName = cmdLineArgs.getInputFile();
        if (inputFileName == null) {
            // If there is no input file, it will be loaded from the PocketPC
            ReadFromPocketPc byteProcessor = new ReadFromPocketPc(cmdLineArgs.getDevice());
            SerialPortWrapper wrapper = initPort(cmdLineArgs.getDevice(), byteProcessor);
            inputFileBytes = byteProcessor.getDataWhenReady();
            wrapper.closePort();
        } else {
            // Read from the File given on the command line
            if (FileFormat.BINARY.equals(cmdLineArgs.getInputFormat())) {
                // Binary read into the byte array
                inputFileBytes = FileHandler.readBinaryFile(inputFileName);
            } else {
                // ASCII read into the List of Strings
                inputFileLines = FileHandler.readTextFile(inputFileName);
            }
        }
        // Step two: Convert the input if necessary
        // The result will either be in a byte array, or in a List of Strings. Declare the variables.
        byte[] outputFileBytes = null;
        List<String> outputFileLines = null;
        if (inputFileBytes != null) {
            // A binary input can only be sent to a binary output. Currently, the software cannot parse a binary file into ASCII
            if (!FileFormat.BINARY.equals(cmdLineArgs.getOutputFormat())) {
                log.log(Level.SEVERE, "A binary input cannot currently be converted to an ASCII output (save " +
                        "to a .bin file, or for a '.bas' file use '--out-format binary'");
                System.exit(1);
            } else {
                // Prepend a serial header if necessary
                outputFileBytes = SerialHeader.prependHeaderIfNecessary(inputFileBytes, cmdLineArgs.getStartAddr(), cmdLineArgs.getRunAddr(), cmdLineArgs.getDevice());
            }
        } else {
            // The input is ASCII, but better doublecheck if not both are null
            if (inputFileLines == null) {
                log.log(Level.SEVERE, "Both binary and ascii input is null, the software is seriously broken");
                System.exit(1);
            }
            // If desired, add the serial utilities, which are device specific.
            if (cmdLineArgs.isUtil()) {
                inputFileLines = UtilsHandler.addSerialUtilBasicApp(inputFileLines, cmdLineArgs.getDevice());
            }
            // Produce a meaningful filename
            Path path = Paths.get(cmdLineArgs.getInputFile());
            String filename = path.getFileName().toString().substring(0, path.getFileName().toString().lastIndexOf('.'));
            // Next, parse everything into a Program
            Program theProgram = new Program(filename, inputFileLines, cmdLineArgs.getDevice());
            // Now it depends on the output file type
            switch (cmdLineArgs.getOutputFormat()) {
                case BINARY:
                    outputFileBytes = theProgram.getBinaryRepresentation();
                    break;
                case ASCII:
                    outputFileLines = theProgram.getNormalizedRepresentationAsList();
                    break;
                case ASCIICOMPACT:
                    outputFileLines = theProgram.getShortRepresentationAsList();
                    break;
            }
        }
        // Step three: Output the result into a file, or to the PocketPC.
        // Sanity check
        if ((outputFileBytes != null && outputFileBytes.length == 0) || (outputFileLines != null && outputFileLines.isEmpty()) ||
                (outputFileBytes == null && outputFileLines == null)) {
            log.log(Level.FINE, "There was an error loading or converting the program, aborting");
            System.exit(1);
        }
        if (cmdLineArgs.getOutputFile() == null) {
            // No output file was given, so the output goes to the PocketPC
            SerialPortWrapper wrapper = initPort(cmdLineArgs.getDevice(), null);
            SerialToDeviceSender sender = new SerialToDeviceSender(wrapper, cmdLineArgs.getDevice());
            if (outputFileBytes != null) {
                // Binary output
                if (cmdLineArgs.getDevice().isPC1500()) {
                    // The PC-1500 needs two things:
                    // 1. The first 27 bytes are a header, and after the header a pause is required (at least 100ms)
                    // 2. It can't keep up with the fixed 19200 baud of the CE-158X. A pause is required between bytes.
                    // Split into header and program
                    byte[] header = new byte[27];
                    byte[] programBytes = new byte[outputFileBytes.length - 27];
                    System.arraycopy(outputFileBytes, 0, header, 0, 27);
                    System.arraycopy(outputFileBytes, 27, programBytes, 0, programBytes.length);
                    // Write the header. It can be sent with full speed, 28 bytes seem to not be an issue
                    wrapper.writeBytes(header);
                    // Now wait for the header to be processed
                    try {
                        Thread.sleep(300L);
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
                    wrapper.writeBytes(outputFileBytes);
                }
                sender.sendData(outputFileBytes);
            } else {
                // ASCII output
                sender.sendData(outputFileLines);
            }
            wrapper.closePort();
        } else {
            // Output goes to file
            if (outputFileLines != null) {
                FileHandler.writeTextFile(cmdLineArgs.getOutputFile(), outputFileLines);
            } else {
                FileHandler.writeBinaryFile(cmdLineArgs.getOutputFile(), outputFileBytes);
            }
        }
    }

    private static SerialPortWrapper initPort(PocketPcDevice device, ByteProcessor byteProcessor) {
        // Transform data from one file format to another
        SerialPortWrapper wrapper = new SerialPortWrapper(null);
        // Next two values are good for PC-1600
        int baudRate = 9600;
        boolean handShake = true;
        if (device.isPC1500()) {
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
}
