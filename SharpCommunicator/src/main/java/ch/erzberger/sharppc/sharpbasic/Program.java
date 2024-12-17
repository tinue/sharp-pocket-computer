package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.commandline.PocketPcDevice;
import ch.erzberger.sharppc.binaryfile.SerialHeader;
import lombok.extern.java.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        this.programName = programName == null || programName.trim().isEmpty() ? "BASIC" : programName.trim();
        List<Line> tempLines = new ArrayList<>();
        for (String line : lineInput) {
            tempLines.add(new Line(line, device));
        }
        this.lines = Collections.unmodifiableList(tempLines);
        validate(); // Set the token as valid.
        setInputMinusToken(""); // Should not be necessary, because each line does this of its own.
    }

    public Program(String programName, byte[] programBytes, PocketPcDevice device) {
        this(programName, convertByteArrayToList(programBytes), device);
    }

    private static List<String> convertByteArrayToList(byte[] bytes) {
        String fullProgram;
        try {
            fullProgram = new String(bytes, "Cp437");
        } catch (UnsupportedEncodingException e) {
            throw new NoClassDefFoundError("Codepage 437 does not exist on your platform");
        }
        String[] lines = fullProgram.split("\\r");  // Sharp uses a CR only for line end
        return Arrays.stream(lines).toList();
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

    public List<String> getNormalizedRepresentationAsList() {
        List<String> result = new ArrayList<>();
        for (Line line : lines) {
            result.add(line.getNormalizedRepresentation());
        }
        return result;
    }

    @Override
    public String getShortRepresentation() {
        StringBuilder sb = new StringBuilder();
        for (Line line : lines) {
            sb.append(line.getShortRepresentation());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public List<String> getShortRepresentationAsList() {
        List<String> result = new ArrayList<>();
        for (Line line : lines) {
            result.add(line.getShortRepresentation());
        }
        return result;
    }

    @Override
    public byte[] getBinaryRepresentation() {
        byte[] program = new byte[0];
        for (Line line : lines) {
            program = appendBytes(program, line.getBinaryRepresentation());
        }
        if (device.isPC1600()) {
            return appendBytes(make1600header(program.length), program);
        } else {
            return appendBytes(SerialHeader.makeHeader(PocketPcDevice.PC1500, programName, program.length - 1).getHeader(), program);
        }
    }

    byte[] make1600header(int size) {
        // See PC-1600 Technical Reference Manual, Page 45
        byte[] header = new byte[16];
        header[0] = (byte) 0xFF; // Indicates that a header is present
        header[1] = (byte) 0x10; // ID Code, must be 0x10
        header[4] = (byte) 0x21; // Mode, 0x21 is Basic program
        byte[] sizeBytes = convertIntToFourByteByteArray(size);
        header[5] = sizeBytes[3]; // Size, low byte
        header[6] = sizeBytes[2]; // Size, middle byte
        header[7] = sizeBytes[1]; // Size, high byte
        header[14] = (byte) 0x00; // Reserved
        header[15] = (byte) 0x0F; // Reserved
        return header;
    }
}
