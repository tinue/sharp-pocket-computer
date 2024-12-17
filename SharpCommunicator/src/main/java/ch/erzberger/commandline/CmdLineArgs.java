package ch.erzberger.commandline;

import lombok.Data;

@Data
public class CmdLineArgs {
    private String inputFile = null;
    private FileFormat inputFormat = null;
    private String outputFile = null;
    private FileFormat outputFormat = null;
    private PocketPcDevice device = null;
    private boolean util = false; // Default
    private Integer startAddr = null;
    private Integer runAddr = null;
}
