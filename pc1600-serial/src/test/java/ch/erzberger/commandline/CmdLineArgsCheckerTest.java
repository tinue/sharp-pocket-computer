package ch.erzberger.commandline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--save", "theSaveFile.bas"});
        assertEquals("theSaveFile.bas", cmdLineArgs.filename());
        assertEquals(Direction.FROMPOCKETOPC, cmdLineArgs.direction());
    }

    @Test
    void saveShort() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        CmdLineArgs cmdLineArgsLong = checker.checkArgs(new String[]{"SharpCommunicator", "--save", "theSaveFile.bas"});
        CmdLineArgs cmdLineArgsShort = checker.checkArgs(new String[]{"SharpCommunicator", "-s", "theSaveFile.bas"});
        assertEquals(cmdLineArgsLong, cmdLineArgsShort);
    }

    @Test
    void load() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--load", "theSaveFile.bas"});
        assertEquals("theSaveFile.bas", cmdLineArgs.filename());
        assertEquals(Direction.FROMPCTOPOCKET, cmdLineArgs.direction());
    }

    @Test
    void tooManyArgs() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        assertNull(checker.checkArgs(new String[]{"SharpCommunicator", "--load", "theLoadFile.bas", "--save", "theSaveFile.bas"}));
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