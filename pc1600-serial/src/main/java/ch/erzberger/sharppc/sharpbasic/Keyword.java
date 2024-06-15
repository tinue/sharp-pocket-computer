package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.tokenizer.SharpPc1500BasicKeywords;
import lombok.extern.java.Log;

import java.util.logging.Level;

@Log
public class Keyword extends Token {
    private final String normalizedRepresentation;

    public Keyword(String input) {
        if (input.startsWith("{")) {
            // This will be a Basic keyword (escaped)
            String pureKeyword = input.substring(1, input.indexOf("}"));
            Integer code = SharpPc1500BasicKeywords.getCode(pureKeyword);
            if (code == null) {
                log.log(Level.SEVERE, "Impossible: Found escaped Basic keyword, but it does not resolve: {0}", pureKeyword);
                normalizedRepresentation = null;
                return;
            }
            setBinary(convertIntToTwoByteByteArray(code));
            normalizedRepresentation = pureKeyword + " ";
            setInputMinusToken(input.substring(input.indexOf("}")+1));
            validate();
        } else {
            log.log(Level.FINEST, "Not a Basic keyword: {0}", input);
            normalizedRepresentation = null;
        }
    }

    @Override
    public String getNormalizedRepresentation() {
        return normalizedRepresentation;
    }
}
