package ch.erzberger.sharppc.sharpbasic;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@Log
public class SharpPc1600BasicKeywords {
    private static final List<BasicKeyword> BASIC_KEYWORDS;

    static {
        BASIC_KEYWORDS = makeBasicKeywordList();
    }

    private SharpPc1600BasicKeywords() {
        // Prevent class from instantiation
    }


    public static List<BasicKeyword> getBasicKeywords() {
        return BASIC_KEYWORDS;
    }

    private static List<BasicKeyword> makeBasicKeywordList() {
        // Basic Tokens used in the PC-1600. Source: Sharp PC-1600 Technical Reference Manual, Page 157ff
        // Command "LCURSOR" has two codes assigned: 0xE683 and 0xF0A5; This class uses the one that is used in the PC-1500 as well.
        // Also, the list below fixes some errors, for example "GCURSOR" is F093, and not F092 as in the manual.
        // PC-1600 keywords do not have abbreviations. //TODO: Verify
        List<BasicKeyword> basicKeywords = new ArrayList<>();
        basicKeywords.add(new BasicKeyword("ABS", 0xF170));
        basicKeywords.add(new BasicKeyword("ACS", 0xF174));
        basicKeywords.add(new BasicKeyword("ADIN", 0xF280));
        basicKeywords.add(new BasicKeyword("AIN", 0xF25A));
        basicKeywords.add(new BasicKeyword("ALARM$", 0xF25C));
        basicKeywords.add(new BasicKeyword("AND", 0xF150));
        basicKeywords.add(new BasicKeyword("AOFF", 0xF2BC));
        basicKeywords.add(new BasicKeyword("APPEND", 0xF2BF));
        basicKeywords.add(new BasicKeyword("AREAD", 0xF180));
        basicKeywords.add(new BasicKeyword("ARUN", 0xF181));
        basicKeywords.add(new BasicKeyword("AS", 0xF2BD));
        basicKeywords.add(new BasicKeyword("ASC", 0xF160));
        basicKeywords.add(new BasicKeyword("ASN", 0xF173));
        basicKeywords.add(new BasicKeyword("ATN", 0xF175));
        basicKeywords.add(new BasicKeyword("AUTO", 0xF2B6));
        basicKeywords.add(new BasicKeyword("BEEP", 0xF182));
        basicKeywords.add(new BasicKeyword("BLOAD", 0xF290));
        basicKeywords.add(new BasicKeyword("BREAK", 0xF0B3));
        basicKeywords.add(new BasicKeyword("BSAVE", 0xF291));
        basicKeywords.add(new BasicKeyword("CALL", 0xF282));
        basicKeywords.add(new BasicKeyword("CHAIN", 0xF0B2));
        basicKeywords.add(new BasicKeyword("CHR$", 0xF163));
        basicKeywords.add(new BasicKeyword("CLEAR", 0xF187));
        basicKeywords.add(new BasicKeyword("CLOAD", 0xF089));
        basicKeywords.add(new BasicKeyword("CLOSE", 0xF292));
        basicKeywords.add(new BasicKeyword("CLS", 0xF088));
        basicKeywords.add(new BasicKeyword("COLOR", 0xF0B5));
        basicKeywords.add(new BasicKeyword("COM", 0xF2A3));
        basicKeywords.add(new BasicKeyword("COM$", 0xE858));
        basicKeywords.add(new BasicKeyword("CONSOLE", 0xF0B1));
        basicKeywords.add(new BasicKeyword("CONT", 0xF183));
        basicKeywords.add(new BasicKeyword("COPY", 0xF293));
        basicKeywords.add(new BasicKeyword("COS", 0xF17E));
        basicKeywords.add(new BasicKeyword("CSAVE", 0xF095));
        basicKeywords.add(new BasicKeyword("CSIZE", 0xE680));
        basicKeywords.add(new BasicKeyword("CURSOR", 0xF084));
        basicKeywords.add(new BasicKeyword("DATA", 0xF18D));
        basicKeywords.add(new BasicKeyword("DATE$", 0xF257));
        basicKeywords.add(new BasicKeyword("DEG", 0xF165));
        basicKeywords.add(new BasicKeyword("DEGREE", 0xF18C));
        basicKeywords.add(new BasicKeyword("DELETE", 0xF2B9));
        basicKeywords.add(new BasicKeyword("DEV$", 0xE857));
        basicKeywords.add(new BasicKeyword("DIM", 0xF18B));
        basicKeywords.add(new BasicKeyword("DMS", 0xF166));
        basicKeywords.add(new BasicKeyword("DSKF", 0xF274));
        basicKeywords.add(new BasicKeyword("DTE", 0xE884));
        basicKeywords.add(new BasicKeyword("ELSE", 0xF283));
        basicKeywords.add(new BasicKeyword("END", 0xF18E));
        basicKeywords.add(new BasicKeyword("EOF", 0xF271));
        basicKeywords.add(new BasicKeyword("ERASE", 0xF2B7));
        basicKeywords.add(new BasicKeyword("ERL", 0xF053));
        basicKeywords.add(new BasicKeyword("ERN", 0xF052));
        basicKeywords.add(new BasicKeyword("ERROR", 0xF1B4));
        basicKeywords.add(new BasicKeyword("EXP", 0xF178));
        basicKeywords.add(new BasicKeyword("FEED", 0xF0B0));
        basicKeywords.add(new BasicKeyword("FILES", 0xF098));
        basicKeywords.add(new BasicKeyword("FOR", 0xF1A5));
        basicKeywords.add(new BasicKeyword("GCURSOR", 0xF093));
        basicKeywords.add(new BasicKeyword("GLCURSOR", 0xE682));
        basicKeywords.add(new BasicKeyword("GOSUB", 0xF194));
        basicKeywords.add(new BasicKeyword("GOTO", 0xF192));
        basicKeywords.add(new BasicKeyword("GPRINT", 0xF09F));
        basicKeywords.add(new BasicKeyword("GRAD", 0xF186));
        basicKeywords.add(new BasicKeyword("GRAPH", 0xE681));
        basicKeywords.add(new BasicKeyword("HEX$", 0xF265));
        basicKeywords.add(new BasicKeyword("IF", 0xF196));
        basicKeywords.add(new BasicKeyword("INIT", 0xF294));
        basicKeywords.add(new BasicKeyword("INKEY$", 0xF15C));
        basicKeywords.add(new BasicKeyword("INP", 0xF266));
        basicKeywords.add(new BasicKeyword("INPUT", 0xF091));
        basicKeywords.add(new BasicKeyword("INSTAT", 0xE859));
        basicKeywords.add(new BasicKeyword("INSTR", 0xF267));
        basicKeywords.add(new BasicKeyword("INT", 0xF171));
        basicKeywords.add(new BasicKeyword("KBUFF$", 0xF284));
        basicKeywords.add(new BasicKeyword("KEY", 0xF285));
        basicKeywords.add(new BasicKeyword("KEYSTAT", 0xF286));
        basicKeywords.add(new BasicKeyword("KILL", 0xF287));
        basicKeywords.add(new BasicKeyword("LCURSOR", 0xE683));
        basicKeywords.add(new BasicKeyword("LEFT$", 0xF17A));
        basicKeywords.add(new BasicKeyword("LEN", 0xF164));
        basicKeywords.add(new BasicKeyword("LET", 0xF198));
        basicKeywords.add(new BasicKeyword("LF", 0xF0B6));
        basicKeywords.add(new BasicKeyword("LFILES", 0xF0A0));
        basicKeywords.add(new BasicKeyword("LINE", 0xF099));
        basicKeywords.add(new BasicKeyword("LIST", 0xF090));
        basicKeywords.add(new BasicKeyword("LLINE", 0xF0B7));
        basicKeywords.add(new BasicKeyword("LLIST", 0xF0B8));
        basicKeywords.add(new BasicKeyword("LN", 0xF176));
        basicKeywords.add(new BasicKeyword("LOAD", 0xF295));
        basicKeywords.add(new BasicKeyword("LOC", 0xF272));
        basicKeywords.add(new BasicKeyword("LOCK", 0xF1B5));
        basicKeywords.add(new BasicKeyword("LOF", 0xF273));
        basicKeywords.add(new BasicKeyword("LOG", 0xF177));
        basicKeywords.add(new BasicKeyword("LPRINT", 0xF0B9));
        basicKeywords.add(new BasicKeyword("MAXFILES", 0xF288));
        basicKeywords.add(new BasicKeyword("MEM", 0xF158));
        basicKeywords.add(new BasicKeyword("MERGE", 0xF08F));
        basicKeywords.add(new BasicKeyword("MID$", 0xF17B));
        basicKeywords.add(new BasicKeyword("MOD", 0xF250));
        basicKeywords.add(new BasicKeyword("MOOE", 0xF2B3));
        basicKeywords.add(new BasicKeyword("NAME", 0xF297));
        basicKeywords.add(new BasicKeyword("NEW", 0xF19B));
        basicKeywords.add(new BasicKeyword("NEXT", 0xF19A));
        basicKeywords.add(new BasicKeyword("NOT", 0xF16D));
        basicKeywords.add(new BasicKeyword("OFF", 0xF19E));
        basicKeywords.add(new BasicKeyword("ON", 0xF19C));
        basicKeywords.add(new BasicKeyword("OPEN", 0xF296));
        basicKeywords.add(new BasicKeyword("OPN", 0xF19D));
        basicKeywords.add(new BasicKeyword("OR", 0xF151));
        basicKeywords.add(new BasicKeyword("OUT", 0xF28A));
        basicKeywords.add(new BasicKeyword("OUTPUT", 0xF2BE));
        basicKeywords.add(new BasicKeyword("OUTSTAT", 0xE880));
        basicKeywords.add(new BasicKeyword("PAPER", 0xE381));
        basicKeywords.add(new BasicKeyword("PASS", 0xF2B8));
        basicKeywords.add(new BasicKeyword("PAUSE", 0xF1A2));
        basicKeywords.add(new BasicKeyword("PCONSOLE", 0xF2B1));
        basicKeywords.add(new BasicKeyword("PEEK", 0xF26D));
        basicKeywords.add(new BasicKeyword("PEEK#", 0xF26E));
        basicKeywords.add(new BasicKeyword("PHONE", 0xF2A0));
        basicKeywords.add(new BasicKeyword("PI", 0xF15D));
        basicKeywords.add(new BasicKeyword("PITCH", 0xF0A4));
        basicKeywords.add(new BasicKeyword("RMT", 0xE7A9));
        basicKeywords.add(new BasicKeyword("POINT", 0xF168));
        basicKeywords.add(new BasicKeyword("POKE", 0xF28C));
        basicKeywords.add(new BasicKeyword("POWER", 0xF28B));
        basicKeywords.add(new BasicKeyword("PRESET", 0xF09A));
        basicKeywords.add(new BasicKeyword("PRINT", 0xF097));
        basicKeywords.add(new BasicKeyword("PSET", 0xF09B));
        basicKeywords.add(new BasicKeyword("PZONE", 0xF2B4));
        basicKeywords.add(new BasicKeyword("RADIAN", 0xF1AA));
        basicKeywords.add(new BasicKeyword("RANDOM", 0xF1A8));
        basicKeywords.add(new BasicKeyword("RCVSTAT", 0xF2A4));
        basicKeywords.add(new BasicKeyword("READ", 0xF1A6));
        basicKeywords.add(new BasicKeyword("REM", 0xF1AB));
        basicKeywords.add(new BasicKeyword("RENUM", 0xF2B5));
        basicKeywords.add(new BasicKeyword("RESTORE", 0xF1A7));
        basicKeywords.add(new BasicKeyword("RESUME", 0xF28D));
        basicKeywords.add(new BasicKeyword("RETI", 0xF28E));
        basicKeywords.add(new BasicKeyword("RETURN", 0xF199));
        basicKeywords.add(new BasicKeyword("RIGHT$", 0xF172));
        basicKeywords.add(new BasicKeyword("RINKEY$", 0xE85A));
        basicKeywords.add(new BasicKeyword("RLINE", 0xF0BA));
        basicKeywords.add(new BasicKeyword("RND", 0xF17C));
        basicKeywords.add(new BasicKeyword("ROTATE", 0xE685));
        basicKeywords.add(new BasicKeyword("RUN", 0xF1A4));
        basicKeywords.add(new BasicKeyword("RXD$", 0xF256));
        basicKeywords.add(new BasicKeyword("SAVE", 0xF299));
        basicKeywords.add(new BasicKeyword("SET", 0xF298));
        basicKeywords.add(new BasicKeyword("SETCOM", 0xE882));
        basicKeywords.add(new BasicKeyword("SETDEV", 0xE886));
        basicKeywords.add(new BasicKeyword("SGN", 0xF179));
        basicKeywords.add(new BasicKeyword("SIN", 0xF17D));
        basicKeywords.add(new BasicKeyword("SNDBRK", 0xF2A1));
        basicKeywords.add(new BasicKeyword("SNDSTAT", 0xF2A2));
        basicKeywords.add(new BasicKeyword("SORGN", 0xE684));
        basicKeywords.add(new BasicKeyword("SPACE$", 0xF061));
        basicKeywords.add(new BasicKeyword("SQR", 0xF16B));
        basicKeywords.add(new BasicKeyword("STATUS", 0xF167));
        basicKeywords.add(new BasicKeyword("STEP", 0xF1AD));
        basicKeywords.add(new BasicKeyword("STOP", 0xF1AC));
        basicKeywords.add(new BasicKeyword("STR$", 0xF161));
        basicKeywords.add(new BasicKeyword("TAB", 0xF0BB));
        basicKeywords.add(new BasicKeyword("TAN", 0xF17F));
        basicKeywords.add(new BasicKeyword("TERMINAL", 0xE883));
        basicKeywords.add(new BasicKeyword("TEST", 0xF0BC));
        basicKeywords.add(new BasicKeyword("TEXT", 0xE686));
        basicKeywords.add(new BasicKeyword("THEN", 0xF1AE));
        basicKeywords.add(new BasicKeyword("TIME", 0xF15B));
        basicKeywords.add(new BasicKeyword("TIME$", 0xF258));
        basicKeywords.add(new BasicKeyword("TITLE", 0xF2BA));
        basicKeywords.add(new BasicKeyword("TO", 0xF1B1));
        basicKeywords.add(new BasicKeyword("TRANSMIT", 0xE885));
        basicKeywords.add(new BasicKeyword("TROFF", 0xF1B0));
        basicKeywords.add(new BasicKeyword("TRON", 0xF1AF));
        basicKeywords.add(new BasicKeyword("UNLOCK", 0xF1B6));
        basicKeywords.add(new BasicKeyword("USING", 0xF085));
        basicKeywords.add(new BasicKeyword("VAL", 0xF162));
        basicKeywords.add(new BasicKeyword("WAIT", 0xF1B3));
        basicKeywords.add(new BasicKeyword("WAKE$", 0xF261));
        basicKeywords.add(new BasicKeyword("WIDTH", 0xF087));
        basicKeywords.add(new BasicKeyword("XCALL", 0xF18A));
        basicKeywords.add(new BasicKeyword("XOR", 0xF251));
        basicKeywords.add(new BasicKeyword("XPEEK", 0xF16F));
        basicKeywords.add(new BasicKeyword("XPEEK#", 0xF16E));
        basicKeywords.add(new BasicKeyword("XPOKE", 0xF1A1));
        basicKeywords.add(new BasicKeyword("XPOKE#", 0xF1A0));
        basicKeywords.add(new BasicKeyword("ZONE", 0xF0B4));
        basicKeywords.add(new BasicKeyword("PROTOCOL", 0xE881)); // This one is missing in the TechRef. It's used in the CE-15)8
        return basicKeywords;
    }
}
