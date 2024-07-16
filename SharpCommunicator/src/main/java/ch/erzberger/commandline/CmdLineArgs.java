package ch.erzberger.commandline;

import lombok.Data;

@Data
public class CmdLineArgs {
    private String inputFile = null;
    private String outputFile = null;
    private PocketPcDevice device = null;
    private boolean ascii = false;
    private boolean util = false;
    private boolean compact = false;
}
