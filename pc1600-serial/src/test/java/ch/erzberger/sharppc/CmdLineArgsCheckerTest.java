package ch.erzberger.sharppc;

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
    void printer() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        assertEquals("p", checker.checkArgs(new String[]{"SharpCommunicator", "--printer"}));
    }

    @Test
    void save() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        assertEquals("s(theSaveFile.bas)", checker.checkArgs(new String[]{"SharpCommunicator", "--save", "theSaveFile.bas"}));
    }

    @Test
    void saveShort() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        assertEquals("s(theSaveFile.bas)", checker.checkArgs(new String[]{"SharpCommunicator", "-s", "theSaveFile.bas"}));
    }

    @Test
    void load() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        assertEquals("l(theLoadFile.bas)", checker.checkArgs(new String[]{"SharpCommunicator", "--load", "theLoadFile.bas"}));
    }

    @Test
    void tooManyArgs() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        assertNull(checker.checkArgs(new String[]{"SharpCommunicator", "--printer", "--load", "theLoadFile.bas", "--save", "theSaveFile.bas"}));
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