package ch.erzberger.commandline;

public enum PocketPcDevice {
    PC1500, PC1500A, PC1600;

    public boolean isPC1500() {
        return this == PC1500 || this == PC1500A;
    }

    public boolean isPC1600() {
        return this == PC1600;
    }
}
