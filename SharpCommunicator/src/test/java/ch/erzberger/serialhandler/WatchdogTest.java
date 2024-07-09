package ch.erzberger.serialhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WatchdogTest {
    private boolean timerFired = false;
    private Watchdog watchdog;
    private static final long TIMEOUT_MS = 50L;
    private final TestTimerExpiryHandler handler = new TestTimerExpiryHandler();

    @BeforeEach
    void setUp() {
        timerFired = false;
    }

    @Test
    void doNotExpireBeforeStarted() throws Exception {
        // The watchdog must not fire before it got started.
        watchdog = new Watchdog(handler, TIMEOUT_MS);
        Thread.sleep(TIMEOUT_MS * 2);
        assertFalse(timerFired);
    }

    @Test
    void resetBeforeStart() throws Exception {
        // Resetting before start must neither throw an exception, nor start the timer
        watchdog = new Watchdog(handler, TIMEOUT_MS);
        assertDoesNotThrow(() -> watchdog.reset());
        Thread.sleep(TIMEOUT_MS * 2);
        assertFalse(timerFired);
    }

    @Test
    void expireWithoutExtension() throws Exception {
        watchdog = new Watchdog(handler, TIMEOUT_MS);
        watchdog.start();
        Thread.sleep(TIMEOUT_MS * 2);
        assertTrue(timerFired);
    }

    @Test
    void doNotExpireThanksToExtension() throws Exception {
        watchdog = new Watchdog(handler, TIMEOUT_MS);
        watchdog.start();
        Thread.sleep(TIMEOUT_MS / 2);
        assertFalse(timerFired);
        watchdog.reset();
        Thread.sleep(TIMEOUT_MS / 2);
        assertFalse(timerFired);
        Thread.sleep(TIMEOUT_MS);
        assertTrue(timerFired);
    }

    private class TestTimerExpiryHandler implements TimerExpiryHandler {
        @Override
        public void timerExpired() {
            timerFired = true;
        }
    }
}