package ch.erzberger.commandline;

public record CmdLineArgs(String filename, PocketPcDevice device, boolean addUtil, Direction direction) {
}
