package ch.erzberger.commandline;

import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

@Log
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
    void output() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas"});
        assertEquals("theSaveFile.bas", cmdLineArgs.getOutputFile());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "-o", "theSaveFile.bas"});
        assertEquals("theSaveFile.bas", cmdLineArgs.getOutputFile());
    }

    @Test
    void fromPcToPc() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        // All defaults
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "--in-file", "theSaveFile.bas"});
        assertEquals(FileFormat.ASCII, cmdLineArgs.getInputFormat());
        assertEquals(FileFormat.ASCIICOMPACT, cmdLineArgs.getOutputFormat());
        // Output given
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "--in-file", "theSaveFile.bas", "--out-format", "BINARY"});
        assertEquals(FileFormat.ASCII, cmdLineArgs.getInputFormat());
        assertEquals(FileFormat.BINARY, cmdLineArgs.getOutputFormat());
        // Input invalid
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "--in-file", "theSaveFile.bas", "--in-format", "BINARY"});
        assertNull(cmdLineArgs);
    }

    @Test
    void fromPcToPocketPc() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        // All defaults
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--in-file", "theSaveFile.bas"});
        assertEquals(FileFormat.ASCII, cmdLineArgs.getInputFormat());
        assertEquals(FileFormat.BINARY, cmdLineArgs.getOutputFormat());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--in-file", "theSaveFile.bin"});
        assertEquals(FileFormat.BINARY, cmdLineArgs.getInputFormat());
        assertEquals(FileFormat.BINARY, cmdLineArgs.getOutputFormat());
        // Output given
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--in-file", "theSaveFile.bas", "--out-format", "ASCII"});
        assertEquals(FileFormat.ASCII, cmdLineArgs.getInputFormat());
        assertEquals(FileFormat.ASCII, cmdLineArgs.getOutputFormat());
        // Override basic
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--in-file", "theSaveFile.bas", "--in-format", "binary"});
        assertEquals(FileFormat.BINARY, cmdLineArgs.getInputFormat());
        assertEquals(FileFormat.BINARY, cmdLineArgs.getOutputFormat());
    }

    @Test
    void fromPocketPcToPc() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        // All defaults
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas"});
        assertEquals(FileFormat.ASCII, cmdLineArgs.getInputFormat());
        assertEquals(FileFormat.ASCII, cmdLineArgs.getOutputFormat());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bin"});
        assertEquals(FileFormat.BINARY, cmdLineArgs.getInputFormat());
        assertEquals(FileFormat.BINARY, cmdLineArgs.getOutputFormat());
        // Override ascii
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "--out-format", "binary"});
        assertEquals(FileFormat.BINARY, cmdLineArgs.getInputFormat());
        assertEquals(FileFormat.BINARY, cmdLineArgs.getOutputFormat());
        // Override binary
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bin", "-of", "ascii"});
        assertEquals(FileFormat.ASCII, cmdLineArgs.getInputFormat());
        assertEquals(FileFormat.ASCII, cmdLineArgs.getOutputFormat());
        // Invalid
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "--in-format", "binary"});
        assertNull(cmdLineArgs);
    }

    @Test
    void device() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        // All defaults
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas"});
        assertEquals(PocketPcDevice.PC1500, cmdLineArgs.getDevice());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "--device", "pc1500a"});
        assertEquals(PocketPcDevice.PC1500A, cmdLineArgs.getDevice());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "-d", "pc1600"});
        assertEquals(PocketPcDevice.PC1600, cmdLineArgs.getDevice());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "--device", "pc1403h"});
        assertNull(cmdLineArgs);

    }

    @Test
    void utils() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        // All defaults
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas"});
        assertFalse(cmdLineArgs.isUtil());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "--add-utils"});
        assertTrue(cmdLineArgs.isUtil());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "-u"});
        assertTrue(cmdLineArgs.isUtil());
    }

    @Test
    void actionargs() {
        // These arguments can only be checked indirectly, because they do not update CmdLineArgs. They cause direct
        // action in the parser, such as increasing the log level.
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        // All defaults
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas"});
        assertNotNull(cmdLineArgs);
        assertEquals(Level.INFO, getCurrentLogLevel());
        // Log Levels
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "-v"});
        assertNotNull(cmdLineArgs);
        assertEquals(Level.FINE, getCurrentLogLevel());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "-vv"});
        assertNotNull(cmdLineArgs);
        assertEquals(Level.FINEST, getCurrentLogLevel());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "--verbose"});
        assertNotNull(cmdLineArgs);
        assertEquals(Level.FINE, getCurrentLogLevel());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "--debug"});
        assertNotNull(cmdLineArgs);
        assertEquals(Level.FINEST, getCurrentLogLevel());
        // Version
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "-V"});
        assertNull(cmdLineArgs);
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--out-file", "theSaveFile.bas", "--version"});
        assertNull(cmdLineArgs);
    }

    @Test
    void input() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        CmdLineArgs cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "--in-file", "theSaveFile.bas"});
        assertEquals("theSaveFile.bas", cmdLineArgs.getInputFile());
        cmdLineArgs = checker.checkArgs(new String[]{"SharpCommunicator", "-i", "theSaveFile.bas"});
        assertEquals("theSaveFile.bas", cmdLineArgs.getInputFile());
    }

    @Test
    void notEnoughArgs() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        assertNull(checker.checkArgs(new String[]{"SharpCommunicator"}));
    }

    @Test
    void missingFilename() {
        CmdLineArgsChecker checker = new CmdLineArgsChecker();
        assertNull(checker.checkArgs(new String[]{"SharpCommunicator", "--in-file"}));
    }

    private Level getCurrentLogLevel() {
        return log.getParent().getLevel();
    }
}