package ch.erzberger.sharppc;

public class LineNormalizeHelper {
    private LineNormalizeHelper() {
        // Prevent instantiation
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
        if (numMatched == 0 && characterToCheck == 'R') {
            return 1;
        }
        if (numMatched == 1 && characterToCheck == 'E') {
            return 2;
        }
        if (numMatched == 2 && characterToCheck == 'M') {
            return 3;
        }
        return 0;
    }
}
