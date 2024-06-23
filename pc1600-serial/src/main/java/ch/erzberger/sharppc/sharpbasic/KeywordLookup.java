package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.commandline.PocketPcDevice;

/**
 * Wrapper around the two different Basic keyword lookup helpers.
 */
public class KeywordLookup {
    private KeywordLookup() {
        // Prevent instantiation
    }
    public static Integer getCode(String basicKeyword, PocketPcDevice device) {
        if (basicKeyword == null || basicKeyword.isEmpty()) {
            return null;
        }
        if (PocketPcDevice.PC1600.equals(device)) {
            return SharpPc1600BasicKeywords.getCode(basicKeyword);
        } else {
            return SharpPc1500BasicKeywords.getCode(basicKeyword);
        }
    }

}
