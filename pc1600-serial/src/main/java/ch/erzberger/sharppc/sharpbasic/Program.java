package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.commandline.PocketPcDevice;
import lombok.extern.java.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Top-level token, represents the Basic program is its entirety.
 * A program consists of a program name (which is not a token), and a list of Lines.
 * The Program also takes care of making the CE-158 or PC-1600 header that is required when loading through serial.
 */
@Log
public class Program extends Token {
    private final String programName;
    private final List<Line> lines;
    private final PocketPcDevice device;

    public Program(String programName, List<String> lineInput, PocketPcDevice device) {
        this.device = device;
        this.programName = programName;
        List<Line> tempLines = new ArrayList<>();
        for (String line : lineInput) {
            tempLines.add(new Line(line, device));
        }
        this.lines = Collections.unmodifiableList(tempLines);
        validate(); // Set the token as valid.
        setInputMinusToken(""); // Should not be necessary, because each line does this of its own.
    }

    @Override
    public String getNormalizedRepresentation() {
        StringBuilder sb = new StringBuilder();
        for (Line line : lines) {
            sb.append(line.getNormalizedRepresentation());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public byte[] getBinaryRepresentation() {
        byte[] program = new byte[0];
        for (Line line : lines) {
            program = appendBytes(program, line.getBinaryRepresentation());
        }
        if (PocketPcDevice.PC1600.equals(device)) {
            return appendBytes(make1600header(program.length), program);
        } else {
            return appendBytes(make1500header(programName, program.length), program);
        }
    }

    byte[] make1500header(String programName, int size) {
        if (programName.length() > 16) {
            programName = programName.substring(0, 16);
        }
        String normalizedProgramName = "@COM" + programName.toUpperCase();
        byte[] progName = new byte[]{0x01};
        try {
            progName = appendBytes(progName, normalizedProgramName.getBytes("Cp437"));
        } catch (UnsupportedEncodingException e) {
            log.log(Level.SEVERE, "Codepage 437 not found");
            System.exit(-1);
        }
        byte[] sizePartOfHeader = new byte[27 - progName.length];
        byte[] retVal = appendBytes(progName, sizePartOfHeader);
        byte[] sizeBytes = convertIntToTwoByteByteArray(size - 1);
        retVal[23] = sizeBytes[0];
        retVal[24] = sizeBytes[1];
        return retVal;
    }

    byte[] make1600header(int size) {
        // See PC-1600 Technical Reference Manual, Page 45
        byte[] header = new byte[16];
        header[0] = (byte)0xFF; // Indicates that a header is present
        header[1] = (byte)0x10; // ID Code, must be 0x10
        header[4] = (byte)0x21; // Mode, 0x21 is Basic program
        byte[] sizeBytes = convertIntToFourByteByteArray(size);
        header[5] = sizeBytes[3]; // Size, low byte
        header[6] = sizeBytes[2]; // Size, middle byte
        header[7] = sizeBytes[1]; // Size, high byte
        header[14] = (byte)0x00; // Reserved
        header[15] = (byte)0xF0; // Reserved
        return header;
    }
}
