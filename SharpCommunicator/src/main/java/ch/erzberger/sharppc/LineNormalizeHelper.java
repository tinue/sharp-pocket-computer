package ch.erzberger.sharppc;

import java.util.ArrayList;
import java.util.List;

public class LineNormalizeHelper {
    private LineNormalizeHelper() {
        // Prevent instantiation
    }

    /**
     * Deals with platform specific line breaks and empty lines. It will remove any empty or null lines,
     * and it will remove an end of file character that may be present in PC-1600 files.
     *
     * @param lines Lines to clean up
     * @return Cleaned up lines
     */
    public static List<String> normalizeLineBreaks(List<String> lines) {
        List<String> normalizedLines = new ArrayList<>();
        for (String line : lines) {
            // Ignore empty lines, and the last line with only an EOF character
            if (line.isEmpty() || (line.length() == 1 && 0x1A == line.charAt(0))) {
                continue;
            }
            // Remove an EOF at the end of a non-empty line
            if (0x1A == line.charAt(line.length() - 1)) {
                line = line.substring(0, line.length() - 1);
            }
            normalizedLines.add(line);
        }
        return normalizedLines;
    }

    /**
     * In Basic listings, white space is often used to make the code more readable. The same white space makes
     * parsing the line more complex, though. This helper removes all extra white space from a line.
     * Retained is white space inside of quotes, and after a REM statement.
     *
     * @param input String to be cleaned up.
     * @return String with extra white space removed.
     */
    public static String cleanupWhiteSpace(String input) {
        var sb = new StringBuilder(); // Capture the result in this variable
        int numRemMatched = 0; // Counts how many characters of the expression "REM" have been captured
        boolean inQuotes = false; // Indicator for being inside a quoted part of the String
        int start = 0;
        for (int current = 0; current < input.length(); current++) {
            char currentChar = input.charAt(current);
            // First, check for REM, but only if not inside a quote
            if (!inQuotes) {
                numRemMatched = matchRem(currentChar, numRemMatched);
                if (numRemMatched == 3) {
                    // We have a REM statement. Abort further processing
                    sb.append(input.substring(start));
                    return sb.toString();
                }
            }
            // Next, toggle the "in quote" state if necessary
            if (currentChar == '\"') inQuotes = !inQuotes;
            // Then remove whitespace
            else if (Character.isWhitespace(currentChar) && !inQuotes) {
                sb.append(input, start, current);
                start = current + 1;
            }
        }
        sb.append(input.substring(start));
        return sb.toString();
    }

    private static int matchRem(char characterToCheck, int numMatched) {
        if (Character.isWhitespace(characterToCheck)) {
            return numMatched;
        }
        if (numMatched == 0 && characterToCheck == 'R'){
            return 1;
        }
        if (numMatched == 1 && characterToCheck == 'E'){
            return 2;
        }
        if (numMatched == 2 && characterToCheck == 'M'){
            return 3;
        }
        return 0;
    }
}
