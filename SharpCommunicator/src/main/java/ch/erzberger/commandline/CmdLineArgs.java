package ch.erzberger.commandline;

public record CmdLineArgs(String filename, PocketPcDevice device, boolean addUtil, boolean ascii, Direction direction) {
}
