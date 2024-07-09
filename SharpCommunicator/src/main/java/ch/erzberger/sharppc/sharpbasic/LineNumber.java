package ch.erzberger.sharppc.sharpbasic;

/**
 * Represents the line number at the start of the Basic line.
 * Its binary format is:
 * - 2 bytes is the line number in binary (e.g. 000A for "10")
 * - 1 byte for the length of the line
 * The length will be zero here, because this can only be patched after the entire Line has been constructed.
 */
public class LineNumber extends Token {
    private final String normalizedRepresentation;
    private final byte[] binaryRepresentation;
    public LineNumber(String input) {
        // In many listings, the line number ends with a colon. Search for a number, optionally followed by one colon
        String lineNumberAsString = findSubstring(input, "^\\d+:?");
        if (lineNumberAsString == null) {  // The String does not start with a line number
            normalizedRepresentation = "";
            binaryRepresentation = new byte[0];
            setInputMinusToken(input);
            return;
        }
        setInputMinusToken(input.substring(lineNumberAsString.length())); // Remove the line number part
        lineNumberAsString = lineNumberAsString.replace(":",""); // Remove colon, if present
        int lineNumber = Integer.parseInt(lineNumberAsString);
        binaryRepresentation = new byte[3];
        byte[] pureLine = convertIntToTwoByteByteArray(lineNumber);
        binaryRepresentation[0] = pureLine[0];
        binaryRepresentation[1] = pureLine[1];
        binaryRepresentation[2] = 0;
        // Use the LLIST format for the line number, i.e. indented to three digits, and a colon as separator
        normalizedRepresentation = String.format("%3d", lineNumber) + ":";
        validate();
    }

    @Override
    public String getNormalizedRepresentation() {
        return normalizedRepresentation;
    }

    @Override
    public byte[] getBinaryRepresentation() {
        return binaryRepresentation;
    }
}
