package ch.erzberger.sharppc.sharpbasic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        program = new Program("the Program", theProgram);
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
}