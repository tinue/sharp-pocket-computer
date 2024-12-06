package ch.erzberger.serialhandler;

import ch.erzberger.commandline.PocketPcDevice;

import java.util.List;

public class SerialToDeviceSender {
    private final SerialPortWrapper serial;
    private final PocketPcDevice device;

    public SerialToDeviceSender(SerialPortWrapper serial, PocketPcDevice device) {
        this.serial = serial;
        this.device = device;
    }

    public void sendData(byte[] data) {
        if (PocketPcDevice.PC1500.equals(device)) {
            // The PC-1500 needs two things:
            // 1. The first 28 bytes are a header, and after the header a pause is required (at least 100ms)
            // 2. It can't keep up with the fixed 19200 baud of the CE-158X. A pause is required between bytes.
            // Split into header and program
            byte[] header = new byte[28];
            byte[] programBytes = new byte[data.length - 28];
            System.arraycopy(data, 0, header, 0, 28);
            System.arraycopy(data, 28, programBytes, 0, programBytes.length);
            // Write the header. It can be sent with full speed, 28 bytes seem to not be an issue
            serial.writeBytes(header);
            // Now wait for the header to be processed
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Next, send the program byte by byte and wait 1ms after each byte
            serial.writeBytes(programBytes, 1L);
            // Wait a bit after the last byte before closing the port.
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            // The PC-1600 has no issues with full speed sending. Its 16 byte header is just loaded along with the rest
            serial.writeBytes(data);
        }
    }

    public void sendData(List<String> data) {
        for (String line : data) {
            serial.writeAscii(line, device);
            // The PC-1500 needs more time to handle one line. Add some wait.
            if (PocketPcDevice.PC1500.equals(device)) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        // To finalize, send an End-Of-File marker
        if (PocketPcDevice.PC1500.equals(device)) {
            // The PC-1500 stops receiving when two CRs are received in a row
            serial.writeBytes(new byte[]{0x0D});
        } else {
            // The PC-1600 stops on an EOF ASCII code
            serial.writeBytes(new byte[]{0x1A});
        }
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
