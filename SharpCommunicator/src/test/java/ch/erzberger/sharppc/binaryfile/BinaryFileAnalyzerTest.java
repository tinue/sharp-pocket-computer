package ch.erzberger.sharppc.binaryfile;

import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
class BinaryFileAnalyzerTest {
    static Path workingDir;

    @BeforeAll
    public static void init() {
        workingDir = Path.of("", "src/test/resources");
    }

    @Test
    void pc1500Basic() throws Exception {
        assertEquals(FileType.PC1500_BINARY_BASIC, BinaryFileAnalyzer.analyze(readFile("typetest-basic-1500.bin")));
        assertEquals(FileType.PC1500_BINARY_MACHINE, BinaryFileAnalyzer.analyze(readFile("typetest-memory-1500.bin")));
        assertEquals(FileType.PC1500_BINARY_RESERVE, BinaryFileAnalyzer.analyze(readFile("typetest-reserve-1500.bin")));
        assertEquals(FileType.PC1500_BASIC, BinaryFileAnalyzer.analyze(readFile("typetest-basic-1500.bas")));
    }

    private byte[] readFile(String fileName) throws Exception {
        Path file = workingDir.resolve(fileName);
        return Files.readAllBytes(file);
    }
}