package ch.erzberger.sharppc.sharpbasic;

import lombok.extern.java.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Log
public class SharpPc1600BasicKeywords {
    private static final Map<String, Integer> BASIC_KEYWORDS;

    static {
        BASIC_KEYWORDS = Collections.unmodifiableMap(makeBasicKeywordsMap());
    }

    private SharpPc1600BasicKeywords() {
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
     *
     * @param candidate String to check
     * @return length of the longest keyword found, -1 if the candidate does not start wit a keyword
     */
    public static int matchKeyword(String candidate) {
        if (candidate == null || candidate.isEmpty()) {
            log.log(Level.WARNING, "Empty string to be checked for keyword");
            return -1;
        }
        int end = Math.min(candidate.length(), 8);
        for (int i = end; i > 1; i--) {
            Integer found = BASIC_KEYWORDS.get(candidate.substring(0, i));
            if (found != null) {
                return i;
            }
        }
        return -1;
    }

    private static Map<String, Integer> makeBasicKeywordsMap() {
        // Basic Tokens used in the PC-1600. Source: Sharp PC-1600 Technical Reference Manual, Page 157ff
        // Command "LCURSOR" has two codes assigned: 0xE683 and 0xF0A5; This class uses the one that is used in the PC-1500 as well.
        // Also, the list below fixes some errors, for example "GCURSOR" is F093, and not F092 as in the manual.
        Map<String, Integer> basicKeywords = new HashMap<>();
        basicKeywords.put("ABS", 0xF170);
        basicKeywords.put("ACS", 0xF174);
        basicKeywords.put("ADIN", 0xF280);
        basicKeywords.put("AIN", 0xF25A);
        basicKeywords.put("ALARM$", 0xF25C);
        basicKeywords.put("AND", 0xF150);
        basicKeywords.put("AOFF", 0xF2BC);
        basicKeywords.put("APPEND", 0xF2BF);
        basicKeywords.put("AREAD", 0xF180);
        basicKeywords.put("ARUN", 0xF181);
        basicKeywords.put("AS", 0xF2BD);
        basicKeywords.put("ASC", 0xF160);
        basicKeywords.put("ASN", 0xF173);
        basicKeywords.put("ATN", 0xF175);
        basicKeywords.put("AUTO", 0xF2B6);
        basicKeywords.put("BEEP", 0xF182);
        basicKeywords.put("BLOAD", 0xF290);
        basicKeywords.put("BREAK", 0xF0B3);
        basicKeywords.put("BSAVE", 0xF291);
        basicKeywords.put("CALL", 0xF282);
        basicKeywords.put("CHAIN", 0xF0B2);
        basicKeywords.put("CHR$", 0xF163);
        basicKeywords.put("CLEAR", 0xF187);
        basicKeywords.put("CLOAD", 0xF089);
        basicKeywords.put("CLOSE", 0xF292);
        basicKeywords.put("CLS", 0xF088);
        basicKeywords.put("COLOR", 0xF0B5);
        basicKeywords.put("COM", 0xF2A3);
        basicKeywords.put("COM$", 0xE858);
        basicKeywords.put("CONSOLE", 0xF0B1);
        basicKeywords.put("CONT", 0xF183);
        basicKeywords.put("COPY", 0xF293);
        basicKeywords.put("COS", 0xF17E);
        basicKeywords.put("CSAVE", 0xF095);
        basicKeywords.put("CSIZE", 0xE680);
        basicKeywords.put("CURSOR", 0xF084);
        basicKeywords.put("DATA", 0xF18D);
        basicKeywords.put("DATE$", 0xF257);
        basicKeywords.put("DEG", 0xF165);
        basicKeywords.put("DEGREE", 0xF18C);
        basicKeywords.put("DELETE", 0xF2B9);
        basicKeywords.put("DEV$", 0xE857);
        basicKeywords.put("DIM", 0xF18B);
        basicKeywords.put("DMS", 0xF166);
        basicKeywords.put("DSKF", 0xF274);
        basicKeywords.put("DTE", 0xE884);
        basicKeywords.put("ELSE", 0xF283);
        basicKeywords.put("END", 0xF18E);
        basicKeywords.put("EOF", 0xF271);
        basicKeywords.put("ERASE", 0xF2B7);
        basicKeywords.put("ERL", 0xF053);
        basicKeywords.put("ERN", 0xF052);
        basicKeywords.put("ERROR", 0xF1B4);
        basicKeywords.put("EXP", 0xF178);
        basicKeywords.put("FEED", 0xF0B0);
        basicKeywords.put("FILES", 0xF098);
        basicKeywords.put("FOR", 0xF1A5);
        basicKeywords.put("GCURSOR", 0xF093);
        basicKeywords.put("GLCURSOR", 0xE682);
        basicKeywords.put("GOSUB", 0xF194);
        basicKeywords.put("GOTO", 0xF192);
        basicKeywords.put("GPRINT", 0xF09F);
        basicKeywords.put("GRAD", 0xF186);
        basicKeywords.put("GRAPH", 0xE681);
        basicKeywords.put("HEX$", 0xF265);
        basicKeywords.put("IF", 0xF196);
        basicKeywords.put("INIT", 0xF294);
        basicKeywords.put("INKEY$", 0xF15C);
        basicKeywords.put("INP", 0xF266);
        basicKeywords.put("INPUT", 0xF091);
        basicKeywords.put("INSTAT", 0xE859);
        basicKeywords.put("INSTR", 0xF267);
        basicKeywords.put("INT", 0xF171);
        basicKeywords.put("KBUFF$", 0xF284);
        basicKeywords.put("KEY", 0xF285);
        basicKeywords.put("KEYSTAT", 0xF286);
        basicKeywords.put("KILL", 0xF287);
        basicKeywords.put("LCURSOR", 0xE683);
        basicKeywords.put("LEFT$", 0xF17A);
        basicKeywords.put("LEN", 0xF164);
        basicKeywords.put("LET", 0xF198);
        basicKeywords.put("LF", 0xF0B6);
        basicKeywords.put("LFILES", 0xF0A0);
        basicKeywords.put("LINE", 0xF099);
        basicKeywords.put("LIST", 0xF090);
        basicKeywords.put("LLINE", 0xF0B7);
        basicKeywords.put("LLIST", 0xF0B8);
        basicKeywords.put("LN", 0xF176);
        basicKeywords.put("LOAD", 0xF295);
        basicKeywords.put("LOC", 0xF272);
        basicKeywords.put("LOCK", 0xF1B5);
        basicKeywords.put("LOF", 0xF273);
        basicKeywords.put("LOG", 0xF177);
        basicKeywords.put("LPRINT", 0xF0B9);
        basicKeywords.put("MAXFILES", 0xF288);
        basicKeywords.put("MEM", 0xF158);
        basicKeywords.put("MERGE", 0xF08F);
        basicKeywords.put("MID$", 0xF17B);
        basicKeywords.put("MOD", 0xF250);
        basicKeywords.put("MOOE", 0xF2B3);
        basicKeywords.put("NAME", 0xF297);
        basicKeywords.put("NEW", 0xF19B);
        basicKeywords.put("NEXT", 0xF19A);
        basicKeywords.put("NOT", 0xF16D);
        basicKeywords.put("OFF", 0xF19E);
        basicKeywords.put("ON", 0xF19C);
        basicKeywords.put("OPEN", 0xF296);
        basicKeywords.put("OPN", 0xF19D);
        basicKeywords.put("OR", 0xF151);
        basicKeywords.put("OUT", 0xF28A);
        basicKeywords.put("OUTPUT", 0xF2BE);
        basicKeywords.put("OUTSTAT", 0xE880);
        basicKeywords.put("PAPER", 0xE381);
        basicKeywords.put("PASS", 0xF2B8);
        basicKeywords.put("PAUSE", 0xF1A2);
        basicKeywords.put("PCONSOLE", 0xF2B1);
        basicKeywords.put("PEEK", 0xF26D);
        basicKeywords.put("PEEK#", 0xF26E);
        basicKeywords.put("PHONE", 0xF2A0);
        basicKeywords.put("PI", 0xF15D);
        basicKeywords.put("PITCH", 0xF0A4);
        basicKeywords.put("RMT", 0xE7A9);
        basicKeywords.put("POINT", 0xF168);
        basicKeywords.put("POKE", 0xF28C);
        basicKeywords.put("POWER", 0xF28B);
        basicKeywords.put("PRESET", 0xF09A);
        basicKeywords.put("PRINT", 0xF097);
        basicKeywords.put("PSET", 0xF09B);
        basicKeywords.put("PZONE", 0xF2B4);
        basicKeywords.put("RADIAN", 0xF1AA);
        basicKeywords.put("RANDOM", 0xF1A8);
        basicKeywords.put("RCVSTAT", 0xF2A4);
        basicKeywords.put("READ", 0xF1A6);
        basicKeywords.put("REM", 0xF1AB);
        basicKeywords.put("RENUM", 0xF2B5);
        basicKeywords.put("RESTORE", 0xF1A7);
        basicKeywords.put("RESUME", 0xF28D);
        basicKeywords.put("RETI", 0xF28E);
        basicKeywords.put("RETURN", 0xF199);
        basicKeywords.put("RIGHT$", 0xF172);
        basicKeywords.put("RINKEY$", 0xE85A);
        basicKeywords.put("RLINE", 0xF0BA);
        basicKeywords.put("RND", 0xF17C);
        basicKeywords.put("ROTATE", 0xE685);
        basicKeywords.put("RUN", 0xF1A4);
        basicKeywords.put("RXD$", 0xF256);
        basicKeywords.put("SAVE", 0xF299);
        basicKeywords.put("SET", 0xF298);
        basicKeywords.put("SETCOM", 0xE882);
        basicKeywords.put("SETDEV", 0xE886);
        basicKeywords.put("SGN", 0xF179);
        basicKeywords.put("SIN", 0xF17D);
        basicKeywords.put("SNDBRK", 0xF2A1);
        basicKeywords.put("SNDSTAT", 0xF2A2);
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
        basicKeywords.put("TIME$", 0xF258);
        basicKeywords.put("TITLE", 0xF2BA);
        basicKeywords.put("TO", 0xF1B1);
        basicKeywords.put("TRANSMIT", 0xE885);
        basicKeywords.put("TROFF", 0xF1B0);
        basicKeywords.put("TRON", 0xF1AF);
        basicKeywords.put("UNLOCK", 0xF1B6);
        basicKeywords.put("USING", 0xF085);
        basicKeywords.put("VAL", 0xF162);
        basicKeywords.put("WAIT", 0xF1B3);
        basicKeywords.put("WAKE$", 0xF261);
        basicKeywords.put("WIDTH", 0xF087);
        basicKeywords.put("XCALL", 0xF18A);
        basicKeywords.put("XOR", 0xF251);
        basicKeywords.put("XPEEK", 0xF16F);
        basicKeywords.put("XPEEK#", 0xF16E);
        basicKeywords.put("XPOKE", 0xF1A1);
        basicKeywords.put("XPOKE#", 0xF1A0);
        basicKeywords.put("ZONE", 0xF0B4);
        basicKeywords.put("PROTOCOL", 0xE881); // This one is missing in the TechRef. It's used in the CE-158
        return basicKeywords;
    }
}
