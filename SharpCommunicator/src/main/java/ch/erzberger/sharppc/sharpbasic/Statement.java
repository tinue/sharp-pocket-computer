package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.commandline.PocketPcDevice;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * One Basic statement (e.g. "LET A=3", or "FOR I=1 TO 100")
 * Some rules, derived from using a real PC-1500:
 * - Basic keywords are always matched, even semantically senseless ones (e.g. "FORLET" is expanded into two keywords)
 * - One can't simply go from left to right. For example, "AFORLET" is not parsed as a variable, bus as "A FOR LET".
 * - Blanks are ignored when matching, e.g. "FO RI" results in "FOR I"
 * - Keywords can be abbreviated. For example "FO.RI" results in "FOR RI"; Note: This is not currently supported, see README
 * - Keywords are parsed from left to right: "LPRINTAB" is "LPRINT AB", not "LPRIN TAB"
 * <p>
 * Hence the algorithm used:
 * - First escape the Basic keywords with curly braces, e.g. "LET" becomes "{LET}"
 * - Then match from left to right, and when encountering a curly brace, replace it with a Basic token.
 */
@Log
public class Statement extends Token {
    private final List<Token> tokenList = new ArrayList<>();

    public Statement(String input, PocketPcDevice device) {
        // Prepare for matching by surrounding all Basic keywords with curly braces
        String escapedInput = escapeBasicKeywords(input, device);
        // Repeatedly match Basic keywords and "Other" in-between, until nothing is left
        while (!escapedInput.isEmpty()) {
            // Basic keyword
            Keyword keyword = new Keyword(escapedInput, device);
            if (keyword.isValid()) {
                escapedInput = addToken(keyword);
                continue;
            }
            // "Other" string
            Other string = new Other(escapedInput);
            if (string.isValid()) {
                escapedInput = addToken(string);
                continue;
            }
            // Nothing could be matched, must be an invalid Statement
            log.log(Level.WARNING, "This part of a Statement could not be matched: {0}", escapedInput);
            setInputMinusToken(input);
            return;
        }
        // Done, Statement is valid
        setInputMinusToken("");
        validate();
    }

    private String addToken(Token token) {
        tokenList.add(token);
        return token.getInputMinusToken();
    }

    @Override
    public String getNormalizedRepresentation() {
        if (!isValid()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Token token : tokenList) {
            sb.append(token.getNormalizedRepresentation());
        }
        return sb.toString();
    }

    @Override
    public String getShortRepresentation() {
        if (!isValid()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Token token : tokenList) {
            sb.append(token.getShortRepresentation());
        }
        return sb.toString();
    }

    @Override
    public byte[] getBinaryRepresentation() {
        byte[] retVal = new byte[0];
        for (Token token : tokenList) {
            byte[] binaryRep = token.getBinaryRepresentation();
            retVal = appendBytes(retVal, binaryRep);
        }
        return retVal;
    }

    String escapeBasicKeywords(String input, PocketPcDevice device) {
        /* Basic algorithm:
         * - Try each 8 char substring from left to right whether it matches or not
         *  - If so, replace it with the code
         * - Then try each 7 char substring, etc. until 2 char substrings are tested last
         */
        SharpBasicKeywordLookup lookup = new SharpBasicKeywordLookup(device);
        // Before parsing, the line must be checked for a "REM" statement
        String rem = null;
        int index = findIndexOfSubstringExcludeQuoted(input, "REM");
        if (index != -1) {
            rem = "{" + input.substring(index) + "}";
            input = input.substring(0, index);
        }
        String result = input; // Initialize the result
        // Also before parsing, quoted Strings must be removed from the input (but left in the result, of course)
        input = input.replaceAll("\"[^\"]*\"", ""); // Then text plus the double quotes
        for (int wordLength = 8; wordLength > 1; wordLength--) { // Go from length 8 down to 2
            String dummy = new String(new char[wordLength]).replace('\0', '#'); // Cache dummy for this length
            for (int currentPos = 0; currentPos <= input.length() - wordLength; currentPos++) { // Go through the input, be sure not to overstep
                String searchWord = input.substring(currentPos, currentPos + wordLength); // Extract the search word
                int code = lookup.getCodeFromKeyword(searchWord); // Check if a code can be found for the search word.
                if (code != 0x0000) {
                    // A code could be found -> We have a keyword.
                    // Find the same match in the result, must not yet have been escaped before, i.e. not inside curly braces
                    int startMatchInResult = findIndexOfSubstringExcludeCurly(result, searchWord);
                    // Add everything before the match, then the escaped keyword, then everything after the match
                    result = result.substring(0, startMatchInResult) + "{" + searchWord + "}" + result.substring(startMatchInResult + searchWord.length());
                    // To avoid a re-match with a shorter keyword (FOR -> OR), replace the found keyword with a dummy of the same length
                    input = input.substring(0, currentPos) + dummy + input.substring(currentPos + wordLength);
                }
            }
        }
        if (rem != null) {
            result = result + rem;
        }
        return result;
    }
}
