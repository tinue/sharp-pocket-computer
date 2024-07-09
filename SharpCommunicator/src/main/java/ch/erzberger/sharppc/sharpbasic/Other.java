package ch.erzberger.sharppc.sharpbasic;

import lombok.extern.java.Log;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

/**
 * Represents "other" characters in-between of Basic keywords. This is where a full parser / tokenizer would
 * go much further, and distinguish between numbers, variables, labels etc. But for the purpose of making a binary
 * file, this is not needed: Everything is just converted from ASCII to binary.
 */
@Log public class Other extends Token{
    private final String otherString;
    public Other(String input) {
        String foundString = findSubstring(input, "^(?:(?!\\{).)*");
        if (foundString == null || foundString.isEmpty()) {
            // Not a String
            this.otherString =null;
            setInputMinusToken(input);
        } else {
            setInputMinusToken(input.substring(foundString.length()));
            this.otherString = foundString;
            validate();
        }
    }
    @Override
    public String getNormalizedRepresentation() {
        return otherString;
    }

    @Override
    public byte[] getBinaryRepresentation() {
        try {
            return otherString.getBytes("Cp437");
        } catch (UnsupportedEncodingException e) {
            log.log(Level.SEVERE, "Codepage 437 not installed");
            System.exit(1);
            return new byte[0];
        }
    }
}
