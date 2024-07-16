package ch.erzberger.sharppc.sharpbasic;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@Log
public class SharpPc1500BasicKeywords {
    private static final List<BasicKeyword> BASIC_KEYWORDS;

    static {
        BASIC_KEYWORDS = makeBasicKeywordList();
    }

    private SharpPc1500BasicKeywords() {
        // Prevent class from instantiation
    }

    public static List<BasicKeyword> getBasicKeywords() {
        return BASIC_KEYWORDS;
    }

    private static List<BasicKeyword> makeBasicKeywordList() {
        // Abbreviations for Basic keywords, without the dot. Source: Sharp PC-1500 Technical Reference Manual, Page 115
        List<BasicKeyword> basicKeywords = new ArrayList<>();
        basicKeywords.add(new BasicKeyword("ABS", "AB", 0xF170));
        basicKeywords.add(new BasicKeyword("ACS", "AC", 0xF174));
        basicKeywords.add(new BasicKeyword("AND", "AN", 0xF150));
        basicKeywords.add(new BasicKeyword("AREAD", "A", 0xF180));
        basicKeywords.add(new BasicKeyword("ARUN", "ARU", 0xF181));
        basicKeywords.add(new BasicKeyword("ASC", 0xF160));
        basicKeywords.add(new BasicKeyword("ASN", "AS", 0xF173));
        basicKeywords.add(new BasicKeyword("ATN", "AT", 0xF175));
        basicKeywords.add(new BasicKeyword("BEEP", "B", 0xF182));
        basicKeywords.add(new BasicKeyword("BREAK", 0xF0B3));
        basicKeywords.add(new BasicKeyword("CALL", "CA", 0xF18A));
        basicKeywords.add(new BasicKeyword("CHAIN", "CHA", 0xF0B2));
        basicKeywords.add(new BasicKeyword("CHR$", "CH", 0xF163));
        basicKeywords.add(new BasicKeyword("CLEAR", "CL", 0xF187));
        basicKeywords.add(new BasicKeyword("CLOAD", "CLO", 0xF089));
        basicKeywords.add(new BasicKeyword("CLS", 0xF088));
        basicKeywords.add(new BasicKeyword("COM$", 0xE858));
        basicKeywords.add(new BasicKeyword("CONSOLE", 0xF0B1));
        basicKeywords.add(new BasicKeyword("CONT", "C", 0xF183));
        basicKeywords.add(new BasicKeyword("COLOR", "COL", 0xF0B5));
        basicKeywords.add(new BasicKeyword("COS", 0xF17E));
        basicKeywords.add(new BasicKeyword("CSAVE", "CS", 0xF095));
        basicKeywords.add(new BasicKeyword("CSIZE", "CSI", 0xE680));
        basicKeywords.add(new BasicKeyword("CURSOR", "CU", 0xF084));
        basicKeywords.add(new BasicKeyword("DATA", "DA", 0xF18D));
        basicKeywords.add(new BasicKeyword("DEG", 0xF165));
        basicKeywords.add(new BasicKeyword("DEGREE", "DE", 0xF18C));
        basicKeywords.add(new BasicKeyword("DEV$", 0xE857));
        basicKeywords.add(new BasicKeyword("DIM", "D", 0xF18B));
        basicKeywords.add(new BasicKeyword("DMS", "DM", 0xF166));
        basicKeywords.add(new BasicKeyword("DTE", 0xE884));
        basicKeywords.add(new BasicKeyword("END", "E", 0xF18E));
        basicKeywords.add(new BasicKeyword("ERL", 0xF053));
        basicKeywords.add(new BasicKeyword("ERN", 0xF052));
        basicKeywords.add(new BasicKeyword("ERROR", "ER", 0xF1B4));
        basicKeywords.add(new BasicKeyword("EXP", "EX", 0xF178));
        basicKeywords.add(new BasicKeyword("FEED", 0xF0B0));
        basicKeywords.add(new BasicKeyword("FOR", "F", 0xF1A5));
        basicKeywords.add(new BasicKeyword("GCURSOR", "GCU", 0xF093));
        basicKeywords.add(new BasicKeyword("GLCURSOR", "GL", 0xE682));
        basicKeywords.add(new BasicKeyword("GOSUB", "GOS", 0xF194));
        basicKeywords.add(new BasicKeyword("GOTO", "G", 0xF192));
        basicKeywords.add(new BasicKeyword("GPRINT", "GP", 0xF09F));
        basicKeywords.add(new BasicKeyword("GRAD", "GR", 0xF186));
        basicKeywords.add(new BasicKeyword("GRAPH", "GRAP", 0xE681));
        basicKeywords.add(new BasicKeyword("IF", 0xF196));
        basicKeywords.add(new BasicKeyword("INKEY$", "INK", 0xF15C));
        basicKeywords.add(new BasicKeyword("INPUT", "I", 0xF091));
        basicKeywords.add(new BasicKeyword("INSTAT", 0xE859));
        basicKeywords.add(new BasicKeyword("INT", 0xF171));
        basicKeywords.add(new BasicKeyword("LCURSOR", "LCU", 0xE683));
        basicKeywords.add(new BasicKeyword("LEFT$", "LEF", 0xF17A));
        basicKeywords.add(new BasicKeyword("LEN", "LEN", 0xF164));
        basicKeywords.add(new BasicKeyword("LET", "LE", 0xF198));
        basicKeywords.add(new BasicKeyword("LF", "LF", 0xF0B6));
        basicKeywords.add(new BasicKeyword("LINE", "LIN", 0xF0B7));
        basicKeywords.add(new BasicKeyword("LIST", "L", 0xF090));
        basicKeywords.add(new BasicKeyword("LLIST", "LL", 0xF0B8));
        basicKeywords.add(new BasicKeyword("LN", "LN", 0xF176));
        basicKeywords.add(new BasicKeyword("LOCK", "LOC", 0xF1B5));
        basicKeywords.add(new BasicKeyword("LOG", "LO", 0xF177));
        basicKeywords.add(new BasicKeyword("LPRINT", "LP", 0xF0B9));
        basicKeywords.add(new BasicKeyword("MEM", "M", 0xF158));
        basicKeywords.add(new BasicKeyword("MERGE", "MER", 0xF08F));
        basicKeywords.add(new BasicKeyword("MID$", "MI", 0xF17B));
        basicKeywords.add(new BasicKeyword("NEW", 0xF19B));
        basicKeywords.add(new BasicKeyword("NEXT", "N", 0xF19A));
        basicKeywords.add(new BasicKeyword("NOT", "NO", 0xF16D));
        basicKeywords.add(new BasicKeyword("OFF", 0xF19E));
        basicKeywords.add(new BasicKeyword("ON", "O", 0xF19C));
        basicKeywords.add(new BasicKeyword("OPN", 0xF19D));
        basicKeywords.add(new BasicKeyword("OR", 0xF151));
        basicKeywords.add(new BasicKeyword("OUTSTAT", 0xE880));
        basicKeywords.add(new BasicKeyword("PAUSE", "PA", 0xF1A2));
        basicKeywords.add(new BasicKeyword("PEEK", 0xF16F));
        basicKeywords.add(new BasicKeyword("PEEK#", "PE", 0xF16E));
        basicKeywords.add(new BasicKeyword("PI", 0xF15D));
        basicKeywords.add(new BasicKeyword("POINT", "POI", 0xF168));
        basicKeywords.add(new BasicKeyword("POKE", 0xF1A1));
        basicKeywords.add(new BasicKeyword("POKE#", "PO", 0xF1A0));
        basicKeywords.add(new BasicKeyword("PRINT", "P", 0xF097));
        basicKeywords.add(new BasicKeyword("RADIAN", "RAD", 0xF1AA));
        basicKeywords.add(new BasicKeyword("RANDOM", "RA", 0xF1A8));
        basicKeywords.add(new BasicKeyword("READ", "REA", 0xF1A6));
        basicKeywords.add(new BasicKeyword("REM", 0xF1AB));
        basicKeywords.add(new BasicKeyword("RESTORE", "RES", 0xF1A7));
        basicKeywords.add(new BasicKeyword("RETURN", "RE", 0xF199));
        basicKeywords.add(new BasicKeyword("RIGHT$", "RI", 0xF172));
        basicKeywords.add(new BasicKeyword("RINKEY$", 0xE85A));
        basicKeywords.add(new BasicKeyword("RLINE", "RL", 0xF0BA));
        basicKeywords.add(new BasicKeyword("RMT", "RM", 0xE7A9));
        basicKeywords.add(new BasicKeyword("RND", "RN", 0xF17C));
        basicKeywords.add(new BasicKeyword("ROTATE", "RO", 0xE685));
        basicKeywords.add(new BasicKeyword("RUN", "R", 0xF1A4));
        basicKeywords.add(new BasicKeyword("SETCOM", 0xE882));
        basicKeywords.add(new BasicKeyword("SETDEV", 0xE886));
        basicKeywords.add(new BasicKeyword("SGN", "SG", 0xF179));
        basicKeywords.add(new BasicKeyword("SIN", "SI", 0xF17D));
        basicKeywords.add(new BasicKeyword("SORGN", "SO", 0xE684));
        basicKeywords.add(new BasicKeyword("SPACE$", 0xF061));
        basicKeywords.add(new BasicKeyword("SQR", "SQ", 0xF16B));
        basicKeywords.add(new BasicKeyword("STATUS", "STA", 0xF167));
        basicKeywords.add(new BasicKeyword("STEP", "STE", 0xF1AD));
        basicKeywords.add(new BasicKeyword("STOP", "S", 0xF1AC));
        basicKeywords.add(new BasicKeyword("STR$", "STR", 0xF161));
        basicKeywords.add(new BasicKeyword("TAB", 0xF0BB));
        basicKeywords.add(new BasicKeyword("TAN", "TA", 0xF17F));
        basicKeywords.add(new BasicKeyword("TERMINAL", 0xE883));
        basicKeywords.add(new BasicKeyword("TEST", "TE", 0xF0BC));
        basicKeywords.add(new BasicKeyword("TEXT", "TEX", 0xE686));
        basicKeywords.add(new BasicKeyword("THEN", "T", 0xF1AE));
        basicKeywords.add(new BasicKeyword("TIME", "TI", 0xF15B));
        basicKeywords.add(new BasicKeyword("TO", 0xF1B1));
        basicKeywords.add(new BasicKeyword("TRANSMIT", 0xE885));
        basicKeywords.add(new BasicKeyword("TROFF", "TROF", 0xF1B0));
        basicKeywords.add(new BasicKeyword("TRON", "TR", 0xF1AF));
        basicKeywords.add(new BasicKeyword("UNLOCK", "UN", 0xF1B6));
        basicKeywords.add(new BasicKeyword("USING", "U", 0xF085));
        basicKeywords.add(new BasicKeyword("VAL", "V", 0xF162));
        basicKeywords.add(new BasicKeyword("WAIT", "W", 0xF1B3));
        basicKeywords.add(new BasicKeyword("ZONE", 0xF0B4));
        basicKeywords.add(new BasicKeyword("PROTOCOL", 0xE881)); // This one is missing in the TechRef. It's used in the CE-158
        return basicKeywords;
    }
}
