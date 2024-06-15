package ch.erzberger.tokenizer;

import lombok.extern.java.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Log
public class SharpPc1500BasicKeywords {
    private static final Map<String, Integer> BASIC_KEYWORDS;

    static {
        BASIC_KEYWORDS = Collections.unmodifiableMap(makeBasicKeywordsMap());
    }

    private SharpPc1500BasicKeywords() {
        // Prevent class from instantiation
    }

    public static Integer getCode(String basicKeyword) {
        if (basicKeyword == null || basicKeyword.isEmpty()) {
            return null;
        }
        return BASIC_KEYWORDS.get(basicKeyword.trim().toUpperCase());
    }

    /**
     * Checks if the candidate starts with a keyword.
     * @param candidate String to check
     * @return length of the longest keyword found, -1 if the candidate does not start wit a keyword
     */
    public static int matchKeyword(String candidate) {
        if (candidate == null || candidate.isEmpty()) {
            log.log(Level.WARNING, "Empty string to be checked for keyword");
            return -1;
        }
        int end = Math.min(candidate.length(), 8);
        for (int i=end; i>1; i--) {
            Integer found = BASIC_KEYWORDS.get(candidate.substring(0, i));
            if (found != null) {
                return i;
            }
        }
        return -1;
    }

    private static Map<String, Integer> makeBasicKeywordsMap() {
        // Basic Tokens used in the PC-1500. Source: Sharp PC-1500 Technical Reference Manual, Page 115
        Map<String, Integer> basicKeywords = new HashMap<>();
        basicKeywords.put("ABS", 0xF170);
        basicKeywords.put("ACS", 0xF174);
        basicKeywords.put("AND", 0xF150);
        basicKeywords.put("AREAD", 0xF180);
        basicKeywords.put("ARUN", 0xF181);
        basicKeywords.put("ASC", 0xF160);
        basicKeywords.put("ASN", 0xF173);
        basicKeywords.put("ATN", 0xF175);
        basicKeywords.put("BEEP", 0xF182);
        basicKeywords.put("BREAK", 0xF0B3);
        basicKeywords.put("CALL", 0xF18A);
        basicKeywords.put("CHAIN", 0xF0B2);
        basicKeywords.put("CHR$", 0xF163);
        basicKeywords.put("CLEAR", 0xF187);
        basicKeywords.put("CLOAD", 0xF089);
        basicKeywords.put("CLS", 0xF088);
        basicKeywords.put("COM$", 0xE858);
        basicKeywords.put("CONSOLE", 0xF0B1);
        basicKeywords.put("CONT", 0xF183);
        basicKeywords.put("COLOR", 0xF0B5);
        basicKeywords.put("COS", 0xF17E);
        basicKeywords.put("CSAVE", 0xF095);
        basicKeywords.put("CSIZE", 0xE680);
        basicKeywords.put("CURSOR", 0xF084);
        basicKeywords.put("DATA", 0xF18D);
        basicKeywords.put("DEG", 0xF165);
        basicKeywords.put("DEGREE", 0xF18C);
        basicKeywords.put("DEV$", 0xE857);
        basicKeywords.put("DIM", 0xF18B);
        basicKeywords.put("DMS", 0xF166);
        basicKeywords.put("DTE", 0xE884);
        basicKeywords.put("END", 0xF18E);
        basicKeywords.put("ERL", 0xF053);
        basicKeywords.put("ERN", 0xF052);
        basicKeywords.put("ERROR", 0xF1B4);
        basicKeywords.put("EXP", 0xF178);
        basicKeywords.put("FEED", 0xF0B0);
        basicKeywords.put("FOR", 0xF1A5);
        basicKeywords.put("GCURSOR", 0xF093);
        basicKeywords.put("GLCURSOR", 0xE682);
        basicKeywords.put("GOSUB", 0xF194);
        basicKeywords.put("GOTO", 0xF192);
        basicKeywords.put("GPRINT", 0xF09F);
        basicKeywords.put("GRAD", 0xF186);
        basicKeywords.put("GRAPH", 0xE681);
        basicKeywords.put("IF", 0xF196);
        basicKeywords.put("INKEY$", 0xF15C);
        basicKeywords.put("INPUT", 0xF091);
        basicKeywords.put("INSTAT", 0xE859);
        basicKeywords.put("INT", 0xF171);
        basicKeywords.put("LCURSOR", 0xE683);
        basicKeywords.put("LEFT$", 0xF17A);
        basicKeywords.put("LEN", 0xF164);
        basicKeywords.put("LET", 0xF198);
        basicKeywords.put("LF", 0xF0B6);
        basicKeywords.put("LINE", 0xF0B7);
        basicKeywords.put("LIST", 0xF090);
        basicKeywords.put("LLIST", 0xF0B8);
        basicKeywords.put("LN", 0xF176);
        basicKeywords.put("LOCK", 0xF1B5);
        basicKeywords.put("LOG", 0xF177);
        basicKeywords.put("LPRINT", 0xF0B9);
        basicKeywords.put("MEM", 0xF158);
        basicKeywords.put("MERGE", 0xF08F);
        basicKeywords.put("MID$", 0xF17B);
        basicKeywords.put("NEW", 0xF19B);
        basicKeywords.put("NEXT", 0xF19A);
        basicKeywords.put("NOT", 0xF16D);
        basicKeywords.put("OFF", 0xF19E);
        basicKeywords.put("ON", 0xF19C);
        basicKeywords.put("OPN", 0xF19D);
        basicKeywords.put("OR", 0xF151);
        basicKeywords.put("OUTSTAT", 0xE880);
        basicKeywords.put("PAUSE", 0xF1A2);
        basicKeywords.put("PEEK", 0xF16F);
        basicKeywords.put("PEEK#", 0xF16E);
        basicKeywords.put("PI", 0xF15D);
        basicKeywords.put("POINT", 0xF168);
        basicKeywords.put("POKE", 0xF1A1);
        basicKeywords.put("POKE#", 0xF1A0);
        basicKeywords.put("PRINT", 0xF097);
        basicKeywords.put("RADIAN", 0xF1AA);
        basicKeywords.put("RANDOM", 0xF1A8);
        basicKeywords.put("READ", 0xF1A6);
        basicKeywords.put("REM", 0xF1AB);
        basicKeywords.put("RESTORE", 0xF1A7);
        basicKeywords.put("RETURN", 0xF199);
        basicKeywords.put("RIGHT$", 0xF172);
        basicKeywords.put("RINKEY$", 0xE85A);
        basicKeywords.put("RLINE", 0xF0BA);
        basicKeywords.put("RMT", 0xE7A9);
        basicKeywords.put("RND", 0xF17C);
        basicKeywords.put("ROTATE", 0xE685);
        basicKeywords.put("RUN", 0xF1A4);
        basicKeywords.put("SETCOM", 0xE882);
        basicKeywords.put("SETDEV", 0xE886);
        basicKeywords.put("SGN", 0xF179);
        basicKeywords.put("SIN", 0xF17D);
        basicKeywords.put("SORGN", 0xE684);
        basicKeywords.put("SPACE$", 0xF061);
        basicKeywords.put("SQR", 0xF16B);
        basicKeywords.put("STATUS", 0xF167);
        basicKeywords.put("STEP", 0xF1AD);
        basicKeywords.put("STOP", 0xF1AC);
        basicKeywords.put("STR$", 0xF161);
        basicKeywords.put("TAB", 0xF0BB);
        basicKeywords.put("TAN", 0xF17F);
        basicKeywords.put("TERMINAL", 0xE883);
        basicKeywords.put("TEST", 0xF0BC);
        basicKeywords.put("TEXT", 0xE686);
        basicKeywords.put("THEN", 0xF1AE);
        basicKeywords.put("TIME", 0xF15B);
        basicKeywords.put("TO", 0xF1B1);
        basicKeywords.put("TRANSMIT", 0xE885);
        basicKeywords.put("TROFF", 0xF1B0);
        basicKeywords.put("TRON", 0xF1AF);
        basicKeywords.put("UNLOCK", 0xF1B6);
        basicKeywords.put("USING", 0xF085);
        basicKeywords.put("VAL", 0xF162);
        basicKeywords.put("WAIT", 0xF1B3);
        basicKeywords.put("ZONE", 0xF0B4);
        basicKeywords.put("PROTOCOL", 0xE881); // This one is missing in the TechRef. It's used in the CE-158
        return basicKeywords;
    }
}
