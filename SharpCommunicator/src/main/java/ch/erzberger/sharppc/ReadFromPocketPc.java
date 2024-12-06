package ch.erzberger.sharppc;

import ch.erzberger.commandline.PocketPcDevice;
import ch.erzberger.serialhandler.ByteProcessor;
import ch.erzberger.serialhandler.Watchdog;
import lombok.extern.java.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HexFormat;
import java.util.logging.Level;

@Log
public class ReadFromPocketPc implements ByteProcessor {
    private final ByteArrayOutputStream buffer;
    private final long timeout;
    private Watchdog watchdog;
    private boolean dataReady = false;

    public ReadFromPocketPc(PocketPcDevice device) {
        if (PocketPcDevice.PC1500.equals(device)) {
            // Through trial and error, it looks line 3 Seconds is enough to write even a long line
            timeout = 3000L;
        } else {
            // The PC-1600 is much faster. Even 0.5 Seconds is probably too much.
            timeout = 500L;
        }
        buffer = new ByteArrayOutputStream();
        log.log(Level.FINE, "Reading from PocketPc, set timeout to {0}ms", timeout);
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
                log.log(Level.FINE, "Timer fired, releasing the lock");
                synchronized (this) {
                    dataReady = true;
                    notifyAll(); // Release the data
                }
            }, timeout);
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

    /**
     * The method returns the bytes received from a PocketPC device. It blocks until all data has
     * been received.
     *
     * @return File bytes
     */
    public synchronized byte[] getDataWhenReady() {
        // Wait for the data to become ready
        log.log(Level.FINE, "Waiting for data from the PocketPc");
        while (!dataReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.log(Level.SEVERE, "Thread Interrupted, data might be inconsistent");
            }
        }
        return buffer.toByteArray();
    }
}
