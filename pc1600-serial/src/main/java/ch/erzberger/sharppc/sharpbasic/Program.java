package ch.erzberger.sharppc.sharpbasic;

import lombok.extern.java.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * Top-level token, represents the Basic program is its entirety.
 * A program consists of a program name (which is not a token), and a list of Lines.
 * The program takes care of writing the CE-158 header that is required when loading through the CE-158.
 */
@Log
public class Program extends Token {
    private final String programName;
    private final List<Line> lines;

    public Program(String programName, List<String> lineInput) {
        this.programName = programName;
        List<Line> tempLines = new ArrayList<>();
        for (String line : lineInput) {
            tempLines.add(new Line(line));
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
        return appendBytes(makeHeader(programName, program.length), program);
    }

    byte[] makeHeader(String programName, int size) {
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
}
