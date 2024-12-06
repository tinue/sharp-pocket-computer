package ch.erzberger.serialhandler;

import lombok.extern.java.Log;

import java.util.logging.Level;

/**
 * The Watchdog class will raise a callback after a timeout expired.
 * The timer can be reset at any time using the reset() method.
 */
@Log
public class Watchdog {
    private final TimerExpiryHandler handler;
    private final long delay;
    private Thread timerThread;

    public Watchdog(TimerExpiryHandler handler, long delay) {
        this.handler = handler;
        this.delay = delay;
    }

    public void start() {
        log.log(Level.FINEST, "Watchdog starting");
        timerThread = new Thread(() -> {
            try {
                log.log(Level.FINEST, "Watchdog thread starts to sleep for {0} ms", delay);
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                // The timer has been interrupted. Log the fact and exit the thread
                log.log(Level.FINEST, "Timer thread got interrupted");
                Thread.currentThread().interrupt();
                return;
            }
            log.log(Level.FINEST, "Watchdog thread expired, raising callback");
            handler.timerExpired();
        });
        timerThread.start();
    }

    public void reset() {
        if (timerThread != null) {
            log.log(Level.FINEST, "Watchdog resetting");
            timerThread.interrupt();
            this.start();
        }
    }
}
