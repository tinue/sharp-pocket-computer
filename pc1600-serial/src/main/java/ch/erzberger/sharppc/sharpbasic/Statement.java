package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.tokenizer.SharpPc1500BasicKeywords;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * One Basic statement (e.g. "LET A=3", or "FOR I=1 TO 100")
 * Some rules, derived from using a real PC-1500:
 * - Basic keywords are always matched, even if this does not make sense (e.g. "FORLET" is expanded into two keywords)
 * - One can't simply go from left to right. For example, "AFORLET" is not parsed as a variable, bus as "A FOR LET".
 * - Blanks are ignored when matching, e.g. "FO RI" results in "FOR I"
 * - Keywords can be abbreviated. For example "FO.RI" results in "FOR RI"
 * - Keywords are parsed from left to right: "LPRINTAB" is "LPRINT AB", not "LPRIN TAB"
 * <p>
 * Hence the algorithm used:
 * - First escape the Basic keywords with curly braces, e.g. "LET" becomes "{LET}"
 * - Then match from left to right, and when encountering a curly brace, replace it with a Basic token.
 */
public class Statement extends Token {
    private final String normalizedRepresentation;

    public Statement(String input) {
        String escapedInput = escapeBasicKeywords(input);

        // Dummy implementation
        normalizedRepresentation = input;
        setInputMinusToken("");
        validate();
    }

    @Override
    public String getNormalizedRepresentation() {
        return normalizedRepresentation;
    }

    String normalizeStatement(String input) {
        // Do we have a REM statement?
        Pattern pattern = Pattern.compile("R *E *M *");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String remStatement = input.substring(matcher.end());
            String firstPart = input.substring(0, matcher.end());
            firstPart = firstPart.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
            return firstPart + remStatement;
        }
        return input.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");
    }

    String escapeBasicKeywords(String input) {
        /* Basic algorithm:
         * - Try each 8 char substring from left to right whether it matches or not
         *  - If so, replace it with the code
         * - Then try each 7 char substring, etc. until 2 char substrings are tested last
         */
        input = normalizeStatement(input); // Remove blanks from input
        // Before parsing, the line must be checked for a "REM" statement
        String rem = null;
        int index = input.indexOf("REM");
        if (index != -1) {
            rem = "{" + input.substring(index) + "}";
            input = input.substring(0, index);
        }
        var result = new StringBuilder(); // Initialize the result
        int endOfLastMatch = 0; // Initially, nothing has matched
        for (int wordLength = 8; wordLength > 1; wordLength--) { // Go from length 8 down to 2
            String dummy = new String(new char[wordLength]).replace('\0', '#'); // Cache dummy for this length
            for (int currentPos = 0; currentPos <= input.length() - wordLength; currentPos++) { // Go through the input, be sure not to overstep
                String searchWord = input.substring(currentPos, currentPos + wordLength); // Extract the search word
                Integer code = SharpPc1500BasicKeywords.getCode(searchWord); // Check if a code can be found for the search word.
                if (code != null) {
                    // A code could be found -> We have a keyword.
                    String replacementKeyword = "{" + searchWord + "}"; // Escape with curly braces
                    result.append(input, endOfLastMatch, currentPos); // Append all non-keyword chars between the last and the current match
                    result.append(replacementKeyword); // Append the escaped keyword
                    endOfLastMatch = currentPos + wordLength; // Update the last match position
                    // To avoid a re-match with a shorter keyword (FOR -> OR), replace the found keyword with a dummy of the same length
                    input = input.substring(0, currentPos) + dummy + input.substring(currentPos + wordLength);
                }
            }
        }
        result.append(input.substring(endOfLastMatch)); // Append non-keyword chars after the last match that was found
        if (rem != null) {
            result.append(rem);
        }
        return result.toString();
    }
}
