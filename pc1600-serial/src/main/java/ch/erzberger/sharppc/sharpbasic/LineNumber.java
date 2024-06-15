package ch.erzberger.sharppc.sharpbasic;

/**
 * Represents the line number at the start of the Basic line.
 * Its binary format is:
 * - 2 bytes is the line number in binary (e.g. 000A for "10")
 * - 1 byte for the length of the line
 * The length will be zero here, because this can only be patched after the line has been constructed.
 */
public class LineNumber extends Token {
    private final String normalizedRepresentation;
    public LineNumber(String input) {
        // A line number must start with digits and end with zero or more blanks.
        String lineNumberAsString = findSubstring(input, "^\\d+ *");
        if (lineNumberAsString == null) {
            normalizedRepresentation = "";
            setInputMinusToken(input);
            return;
        }
        setInputMinusToken(input.substring(lineNumberAsString.length()));
        int lineNumber = Integer.parseInt(lineNumberAsString.trim());
        setBinary(convertIntToTwoByteByteArray(lineNumber));
        normalizedRepresentation = lineNumberAsString.trim() + " ";
        validate();
    }

    @Override
    public String getNormalizedRepresentation() {
        return normalizedRepresentation;
    }
}
