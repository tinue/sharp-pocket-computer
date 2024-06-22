package ch.erzberger.sharppc.sharpbasic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Top-level token, represents the Basic program is its entirety.
 * A program consists of a program name (which is not a token), and a list of Lines.
 * The program takes care of writing the CE-158 header that is required when loading through the CE-158.
 */
public class Program extends Token{
    private final String programName;
    private final List<Line> lines;

    public Program(String programName, List<String> lineInput) {
        this.programName = programName;
        List<Line> lines = new ArrayList<>();
        for (String line : lineInput) {
            lines.add(new Line(line));
        }
        this.lines = Collections.unmodifiableList(lines);
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
        return new byte[0];
    }
}
