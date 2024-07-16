package ch.erzberger.commandline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CmdLineArgsCheckerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void help() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        assertNull(checker.checkArgs(new String[]{"SharpCommunicator", "--help"}));
    }

    @Test
    void save() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--output", "theSaveFile.bas"});
        assertEquals("theSaveFile.bas", cmdLineArgs.getOutputFile());
    }

    @Test
    void saveShort() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        CmdLineArgs cmdLineArgsLong = checker.checkArgs(new String[]{"SharpCommunicator", "--output", "theSaveFile.bas"});
        CmdLineArgs cmdLineArgsShort = checker.checkArgs(new String[]{"SharpCommunicator", "-o", "theSaveFile.bas"});
        assertEquals(cmdLineArgsLong, cmdLineArgsShort);
    }

    @Test
    void load() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--input", "theSaveFile.bas"});
        assertEquals("theSaveFile.bas", cmdLineArgs.getInputFile());
    }

    @Test
    void compact() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--input", "theSaveFile.bas"});
        assertFalse(cmdLineArgs.isCompact());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--output", "theSaveFile.bas"});
        assertFalse(cmdLineArgs.isCompact());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--output", "theSaveFile.bas", "--ascii", "--compact"});
        assertTrue(cmdLineArgs.isCompact());
        assertNull(checker.checkArgs(new String[]{"SharpCommunicator", "--output", "theSaveFile.bas", "--compact"}));
        assertNull(checker.checkArgs(new String[]{"SharpCommunicator", "--ascii", "--compact"}));
        assertNull(checker.checkArgs(new String[]{"SharpCommunicator", "--input", "theSaveFile.bas", "--ascii", "--compact"}));
    }

    @Test
    void notEnoughArgs() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        assertNull(checker.checkArgs(new String[]{"SharpCommunicator"}));
    }

    @Test
    void missingFilename() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        assertNull(checker.checkArgs(new String[]{"SharpCommunicator", "--load"}));
    }
}