package ch.erzberger.sharppc.sharpbasic;

import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Root token class.
 * The Basic program is composed with Tokens:
 * Program --> Lines -->* Line -->* Statement
 */
@Getter
public abstract class Token {
    private String inputMinusToken;
    private byte[] binary;
    private boolean valid = false;

    protected void validate() {
        this.valid = true;
    }

    protected void setInputMinusToken(String inputMinusToken) {
        this.inputMinusToken = inputMinusToken;
    }

    void setBinary(byte[] binary) {
        this.binary = binary;
    }

    public abstract String getNormalizedRepresentation();

    byte[] convertIntToTwoByteByteArray(int value) {
        byte[] intermediary = ByteBuffer.allocate(4).putInt(value).array();
        byte[] retVal = new byte[2];
        retVal[0] = intermediary[2];
        retVal[1] = intermediary[3];
        return retVal;
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

    int findIndexOfSubstring(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.start();
        } else {
            return -1;
        }
    }
}
