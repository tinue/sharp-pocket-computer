package ch.erzberger.sharppc.sharpbasic;

import ch.erzberger.commandline.PocketPcDevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProgramTest {
    List<String> theProgram;
    Program program;

    @BeforeEach
    void setUp() {
        theProgram = new ArrayList<>();
        theProgram.add("10 REM Test program");
        theProgram.add("20 FOR I=1 TO 100:PRINT I");
        theProgram.add("190 NEXT I");
        theProgram.add("55555 END");
        program = new Program("the Program", theProgram, PocketPcDevice.PC1500);
    }

    @Test
    void getNormalizedRepresentation() {
        String[] normalizedRepresentation = program.getNormalizedRepresentation().split(System.lineSeparator());
        assertEquals(4, normalizedRepresentation.length);
        assertEquals(" 10:REM Test program", normalizedRepresentation[0]);
        assertEquals(" 20:FOR I=1TO 100:PRINT I", normalizedRepresentation[1]);
        assertEquals("190:NEXT I", normalizedRepresentation[2]);
        assertEquals("55555:END", normalizedRepresentation[3]);
    }

    @Test
    void binary() {
        theProgram = new ArrayList<>();
        theProgram.add("10FORI=1TO100");
        // Setup both programs
        Program program1500 = new Program("Test", theProgram, PocketPcDevice.PC1500);
        Program program1600 = new Program("Test", theProgram, PocketPcDevice.PC1600);
        byte[] binary1500 = program1500.getBinaryRepresentation();
        byte[] binary1600 = program1600.getBinaryRepresentation();
        assertEquals(11, binary1500.length - binary1600.length); // Header of PC-1500 is 27 bytes, PC-1600 is 16 bytes
    }
}