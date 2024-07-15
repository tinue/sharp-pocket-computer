package ch.erzberger.sharppc.sharpbasic;

import lombok.extern.java.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Log
public class SharpPc1500BasicKeywords {
    private static final Map<String, Integer> BASIC_KEYWORDS;
    private static final Map<String, String> BASIC_ABBREV;

    static {
        BASIC_KEYWORDS = Collections.unmodifiableMap(makeBasicKeywordsMap());
        BASIC_ABBREV = Collections.unmodifiableMap(makeBasicAbbrevMap());
    }

    private SharpPc1500BasicKeywords() {
        // Prevent class from instantiation
    }

    static Map<String, Integer> getKeywordMap() {
        return BASIC_KEYWORDS;
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

    public static String getAbbreviation(String basicKeyword) {
        return BASIC_ABBREV.get(basicKeyword);
    }

    private static Map<String, Integer> makeBasicKeywordsMap() {
        // Basic Tokens used in the PC-1500. Source: Sharp PC-1500 Technical Reference Manual, Appendix A, Page 138
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

    private static Map<String, String> makeBasicAbbrevMap() {
        // Abbreviations for Basic keywords, without the dot. Source: Sharp PC-1500 Technical Reference Manual, Page 115
        Map<String, String> basicAbbrevs = new HashMap<>();
        basicAbbrevs.put("ABS", "AB");
        basicAbbrevs.put("ACS", "AC");
        basicAbbrevs.put("AND", "AN");
        basicAbbrevs.put("AREAD", "A");
        basicAbbrevs.put("ARUN", "ARU");
        basicAbbrevs.put("ASC", "ASC");
        basicAbbrevs.put("ASN", "AS");
        basicAbbrevs.put("ATN", "AT");
        basicAbbrevs.put("BEEP", "B");
        basicAbbrevs.put("BREAK", "BREAK");
        basicAbbrevs.put("CALL", "CA");
        basicAbbrevs.put("CHAIN", "CHA");
        basicAbbrevs.put("CHR$", "CH");
        basicAbbrevs.put("CLEAR", "CL");
        basicAbbrevs.put("CLOAD", "CLO");
        basicAbbrevs.put("CLS", "CLS");
        basicAbbrevs.put("COM$", "COM$");
        basicAbbrevs.put("CONSOLE", "CONSOLE");
        basicAbbrevs.put("CONT", "C");
        basicAbbrevs.put("COLOR", "COL");
        basicAbbrevs.put("COS", "COS");
        basicAbbrevs.put("CSAVE", "CS");
        basicAbbrevs.put("CSIZE", "CSI");
        basicAbbrevs.put("CURSOR", "CU");
        basicAbbrevs.put("DATA", "DA");
        basicAbbrevs.put("DEG", "DEG");
        basicAbbrevs.put("DEGREE", "DE");
        basicAbbrevs.put("DEV$", "DEV$");
        basicAbbrevs.put("DIM", "D");
        basicAbbrevs.put("DMS", "DM");
        basicAbbrevs.put("DTE", "DTE");
        basicAbbrevs.put("END", "E");
        basicAbbrevs.put("ERL", "ERL");
        basicAbbrevs.put("ERN", "ERN");
        basicAbbrevs.put("ERROR", "ER");
        basicAbbrevs.put("EXP", "EX");
        basicAbbrevs.put("FEED", "FEED");
        basicAbbrevs.put("FOR", "F");
        basicAbbrevs.put("GCURSOR", "GCU");
        basicAbbrevs.put("GLCURSOR", "GL");
        basicAbbrevs.put("GOSUB", "GOS");
        basicAbbrevs.put("GOTO", "G");
        basicAbbrevs.put("GPRINT", "GP");
        basicAbbrevs.put("GRAD", "GR");
        basicAbbrevs.put("GRAPH", "GRAP");
        basicAbbrevs.put("IF", "IF");
        basicAbbrevs.put("INKEY$", "INK");
        basicAbbrevs.put("INPUT", "I");
        basicAbbrevs.put("INSTAT", "INSTAT");
        basicAbbrevs.put("INT", "INT");
        basicAbbrevs.put("LCURSOR", "LCU");
        basicAbbrevs.put("LEFT$", "LEF");
        basicAbbrevs.put("LEN", "LEN");
        basicAbbrevs.put("LET", "LE");
        basicAbbrevs.put("LF", "LF");
        basicAbbrevs.put("LINE", "LIN");
        basicAbbrevs.put("LIST", "L");
        basicAbbrevs.put("LLIST", "LL");
        basicAbbrevs.put("LN", "LN");
        basicAbbrevs.put("LOCK", "LOC");
        basicAbbrevs.put("LOG", "LO");
        basicAbbrevs.put("LPRINT", "LP");
        basicAbbrevs.put("MEM", "M");
        basicAbbrevs.put("MERGE", "MER");
        basicAbbrevs.put("MID$", "MI");
        basicAbbrevs.put("NEW", "NEW");
        basicAbbrevs.put("NEXT", "N");
        basicAbbrevs.put("NOT", "NO");
        basicAbbrevs.put("OFF", "OFF");
        basicAbbrevs.put("ON", "O");
        basicAbbrevs.put("OPN", "OPN");
        basicAbbrevs.put("OR", "OR");
        basicAbbrevs.put("OUTSTAT", "OUTSTAT");
        basicAbbrevs.put("PAUSE", "PA");
        basicAbbrevs.put("PEEK", "PEEK");
        basicAbbrevs.put("PEEK#", "PE");
        basicAbbrevs.put("PI", "PI");
        basicAbbrevs.put("POINT", "POI");
        basicAbbrevs.put("POKE", "POKE");
        basicAbbrevs.put("POKE#", "PO");
        basicAbbrevs.put("PRINT", "P");
        basicAbbrevs.put("RADIAN", "RAD");
        basicAbbrevs.put("RANDOM", "RA");
        basicAbbrevs.put("READ", "REA");
        basicAbbrevs.put("REM", "REM");
        basicAbbrevs.put("RESTORE", "RES");
        basicAbbrevs.put("RETURN", "RE");
        basicAbbrevs.put("RIGHT$", "RI");
        basicAbbrevs.put("RINKEY$", "RINKEY$");
        basicAbbrevs.put("RLINE", "RL");
        basicAbbrevs.put("RMT", "RM");
        basicAbbrevs.put("RND", "RN");
        basicAbbrevs.put("ROTATE", "RO");
        basicAbbrevs.put("RUN", "R");
        basicAbbrevs.put("SETCOM", "SETCOM");
        basicAbbrevs.put("SETDEV", "SETDEV");
        basicAbbrevs.put("SGN", "SG");
        basicAbbrevs.put("SIN", "SI");
        basicAbbrevs.put("SORGN", "SO");
        basicAbbrevs.put("SPACE$", "SPACE$");
        basicAbbrevs.put("SQR", "SQ");
        basicAbbrevs.put("STATUS", "STA");
        basicAbbrevs.put("STEP", "STE");
        basicAbbrevs.put("STOP", "S");
        basicAbbrevs.put("STR$", "STR");
        basicAbbrevs.put("TAB", "TAB");
        basicAbbrevs.put("TAN", "TA");
        basicAbbrevs.put("TERMINAL", "TERMINAL");
        basicAbbrevs.put("TEST", "TE");
        basicAbbrevs.put("TEXT", "TEX");
        basicAbbrevs.put("THEN", "T");
        basicAbbrevs.put("TIME", "TI");
        basicAbbrevs.put("TO", "TO");
        basicAbbrevs.put("TRANSMIT", "TRANSMIT");
        basicAbbrevs.put("TROFF", "TROF");
        basicAbbrevs.put("TRON", "TR");
        basicAbbrevs.put("UNLOCK", "UN");
        basicAbbrevs.put("USING", "U");
        basicAbbrevs.put("VAL", "V");
        basicAbbrevs.put("WAIT", "W");
        basicAbbrevs.put("ZONE", "ZONE");
        basicAbbrevs.put("PROTOCOL", "PROTOCOL"); // This one is missing in the TechRef. It's used in the CE-158
        return basicAbbrevs;
    }
}
