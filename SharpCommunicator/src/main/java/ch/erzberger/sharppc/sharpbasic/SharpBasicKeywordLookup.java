package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.commandline.PocketPcDevice;
import lombok.extern.java.Log;

import java.util.List;
import java.util.logging.Level;

/**
 * Implements the various lookup methods for the Device-specific keyword lists.
 */
@Log
public class SharpBasicKeywordLookup {
    private static final BasicKeyword UNKNOWN = new BasicKeyword("UNKNOWN", "UNKNOWN", 0x0000);
    private final List<BasicKeyword> basicKeywords;

    public SharpBasicKeywordLookup(PocketPcDevice device) {
        if (device.isPC1500()) {
            basicKeywords = SharpPc1500BasicKeywords.getBasicKeywords();
        } else {
            basicKeywords = SharpPc1600BasicKeywords.getBasicKeywords();
        }
    }

    public Integer getCodeFromKeyword(String basicKeyword) {
        if (basicKeyword == null || basicKeyword.isEmpty()) {
            return UNKNOWN.getCode();
        }
        var shortList = basicKeywords.stream().filter(k -> k.getName().equals(basicKeyword.toUpperCase())).toList();
        if (shortList.size() == 1) {
            return shortList.get(0).getCode();
        } else {
            return UNKNOWN.getCode();
        }
    }

    public BasicKeyword getKeywordFromKeyword(String basicKeyword) {
        if (basicKeyword == null || basicKeyword.isEmpty()) {
            return UNKNOWN;
        }
        var shortList = basicKeywords.stream().filter(k -> k.getName().equals(basicKeyword.toUpperCase())).toList();
        if (shortList.size() == 1) {
            return shortList.get(0);
        } else {
            return UNKNOWN;
        }
    }

    /**
     * Checks if the candidate starts with a keyword.
     *
     * @param candidate String to check
     * @return length of the longest keyword found, -1 if the candidate does not start wit a keyword
     */
    public int matchKeyword(String candidate) {
        if (candidate == null || candidate.isEmpty()) {
            log.log(Level.WARNING, "Empty string to be checked for keyword");
            return -1;
        }
        int end = Math.min(candidate.length(), 8);
        for (int i = end; i > 1; i--) {
            final var finalI = i;
            var shortList = basicKeywords.stream().filter(k -> k.getName().equals(candidate.substring(0, finalI))).toList();
            if (shortList.size() == 1) {
                return finalI;
            }
        }
        return -1;
    }

    public String getAbbreviationFromKeyword(String basicKeyword) {
        var shortList = basicKeywords.stream().filter(k -> k.getName().equals(basicKeyword)).toList();
        if (shortList.size() == 1) {
            return shortList.get(0).getAbbreviation();
        } else {
            return UNKNOWN.getAbbreviation();
        }
    }
}
