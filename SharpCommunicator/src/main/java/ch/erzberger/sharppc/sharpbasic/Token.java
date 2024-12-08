package ch.erzberger.sharppc.sharpbasic;

import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Root token class.
 * Despite its name, this class and its subclasses do not attempt to fully parse and tokenize the Basic program.
 * This is only done up to a level necessary to create a binary version of the program, for the purpose of quicker loading.
 * The instance hierarchy is as follows:
 * Program --> *Line --> LineNumber
 *                   --> *Statement --> *Keyword
 *                                  --> *Other
 */
@Getter
public abstract class Token {
    private String inputMinusToken;
    private boolean valid = false;

    protected void validate() {
        this.valid = true;
    }

    protected void setInputMinusToken(String inputMinusToken) {
        this.inputMinusToken = inputMinusToken;
    }

    public abstract String getNormalizedRepresentation();
    public abstract String getShortRepresentation();
    public abstract byte[] getBinaryRepresentation();

    byte[] convertIntToTwoByteByteArray(int value) {
        byte[] intermediary = ByteBuffer.allocate(4).putInt(value).array();
        byte[] retVal = new byte[2];
        retVal[0] = intermediary[2];
        retVal[1] = intermediary[3];
        return retVal;
    }

    byte[] convertIntToFourByteByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    String findSubstring(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return null;
        }
    }

    /**
     * Find the first index of a substring, but do not search inside of already escaped keywords.
     * @param source String to find something inside
     * @param toFind String to find
     * @return First index of found keyword, -1 if nothing was found.
     */
    int findIndexOfSubstring(String source, String toFind) {
        boolean insideCurly = false;
        StringBuilder masked = new StringBuilder();
        for (char c : source.toCharArray()) {
            if ('{' == c) {
                insideCurly = true;
            }
            if (insideCurly) {
                masked.append('#');
                if ('}' == c) {
                    insideCurly = false;
                }
            } else {
                masked.append(c);
            }
        }
        return masked.toString().indexOf(toFind);
    }

    static byte[] appendBytes(byte[] source, byte[] toAppend) {
        byte[] retVal = new byte[source.length + toAppend.length];
        System.arraycopy(source, 0, retVal, 0, source.length);
        System.arraycopy(toAppend, 0, retVal, source.length, toAppend.length);
        return retVal;
    }
}
