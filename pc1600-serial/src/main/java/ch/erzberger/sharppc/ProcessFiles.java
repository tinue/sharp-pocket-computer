package ch.erzberger.sharppc;

import ch.erzberger.serialhandler.ByteProcessor;
import ch.erzberger.serialhandler.Watchdog;
import lombok.extern.java.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HexFormat;
import java.util.logging.Level;

@Log
public class ProcessFiles implements ByteProcessor {
    private final ByteArrayOutputStream buffer;
    private final String filename;
    private Watchdog watchdog;

    public ProcessFiles(String filename) {
        this.filename = filename;
        buffer = new ByteArrayOutputStream();
    }

    @Override
    public void processByte(byte byteReceived) {
        processBytes(new byte[]{byteReceived});
    }

    @Override
    public void processBytes(byte[] bytes) {
        log.log(Level.FINEST, "Bytes received: {0}", HexFormat.of().formatHex(bytes));
        if (watchdog == null) {
            log.log(Level.FINE, "First data packet received, starting watchdog");
            watchdog = new Watchdog(() -> {
                log.log(Level.FINE, "Timer fired, writing file");
                persistBytes(filename);
                System.exit(0);
            }, 200L);
            watchdog.start();
        } else {
            log.log(Level.FINEST, "Data received, resetting watchdog.");
            watchdog.reset();
        }
        try {
            buffer.write(bytes);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Error writing bytes received", ex);
        }
    }

    public void persistBytes(String filename) {
        Path newFilePath = Paths.get(filename);
        try {
            Files.deleteIfExists(newFilePath);
            Files.createFile(newFilePath);
            Files.write(newFilePath, buffer.toByteArray());
            buffer.close();
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Error persisting the bytes to a file", ex);
        }
    }
}
