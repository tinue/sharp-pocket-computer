package ch.erzberger.serialhandler;

public interface ByteProcessor {
    /**
     * Processes one byte received via Serial
     *
     * @param byteReceived the one byte that was received via Infrared port
     */
    void processByte(byte byteReceived);

    default void processBytes(byte[] bytes) {
        for (byte currentByte : bytes) {
            processByte(currentByte);
        }
    }
}
