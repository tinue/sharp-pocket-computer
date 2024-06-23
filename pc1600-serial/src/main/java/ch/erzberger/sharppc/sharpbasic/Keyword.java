package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.commandline.PocketPcDevice;
import lombok.extern.java.Log;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

@Log
public class Keyword extends Token {
    private final String normalizedRepresentation;
    private final byte[] binaryRepresentation;

    public Keyword(String input, PocketPcDevice device) {
        if (input.startsWith("{")) {
            // This will be a Basic keyword (escaped)
            String pureKeyword = input.substring(1, input.indexOf("}"));
            String remark = "";
            if (pureKeyword.startsWith("REM")) {
                // REM is packaged a bit differently: It also contains the actual remark
                remark = pureKeyword.substring(3).trim();
                pureKeyword = "REM";
            }
            Integer code = KeywordLookup.getCode(pureKeyword, device);
            if (code == null) {
                log.log(Level.SEVERE, "Impossible: Found escaped Basic keyword, but it does not resolve: {0}", pureKeyword);
                normalizedRepresentation = null;
                binaryRepresentation = new byte[0];
                return;
            }
            byte[] remarkBytes;
            try {
                remarkBytes = remark.getBytes("Cp437");
            } catch (UnsupportedEncodingException e) {
                log.log(Level.SEVERE, "CP437 not installed");
                remarkBytes = new byte[0];
            }
            byte[] binaryRepresentationTemp = convertIntToTwoByteByteArray(code);
            if (remarkBytes.length > 0) {
                binaryRepresentation = appendBytes(binaryRepresentationTemp, remarkBytes);
            } else {
                binaryRepresentation = binaryRepresentationTemp;
            }
            normalizedRepresentation = pureKeyword + " " + remark;
            setInputMinusToken(input.substring(input.indexOf("}") + 1));
            validate();
        } else {
            log.log(Level.FINEST, "Not a Basic keyword: {0}", input);
            normalizedRepresentation = null;
            binaryRepresentation = new byte[0];
        }
    }

    @Override
    public String getNormalizedRepresentation() {
        return normalizedRepresentation;
    }

    @Override
    public byte[] getBinaryRepresentation() {
        return binaryRepresentation;
    }
}
