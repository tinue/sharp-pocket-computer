package ch.erzberger.commandline;

import lombok.extern.java.Log;
import org.apache.commons.cli.*;

@Log
public class CmdLineArgsChecker {
    public static final String SAVEARG = "save";
    public static final String LOADARG = "load";
    public static final String UTILARG = "addutil";
    public static final String FILEARG = "filename";
    public static final String ASCIIARG = "ascii";
    public static final String TOOLNAME = "SharpCommunicator";
    public static final String PC1500ARG = "1500";

    public CmdLineArgs checkArgs(String[] args) {
        PocketPcDevice device;
        Direction direction = null;
        boolean util = false;
        boolean ascii = false;
        String fileName = null;
        // Set up the command line parameters
        Options options = new Options();
        options.addOption(new Option("h", "help", false, "Print usage."));
        options.addOption(new Option("u", UTILARG, false, "Add the serial utilities when loading the Basic program."));
        options.addOption(new Option("a", ASCIIARG, false, "Load / Save a Basic program as ASCII."));
        options.addOption(new Option("5", PC1500ARG, false, "Device is a PC1500/A connected to a CE-158X (default is a PC-1600)."));
        options.addOption(Option.builder("s").longOpt(SAVEARG)
                .desc(String.format("Save the file received from the PC-1500/1600 to disk. Note: Start %s first, then launch (C)SAVE on the PocketPC.", TOOLNAME))
                .hasArg().argName(FILEARG)
                .build());
        options.addOption(Option.builder("l").longOpt(LOADARG)
                .desc(String.format("Load a file from disk onto the PC-1500/1600. Note: Launch (C)LOAD on the PocketPC first, then launch %s.", TOOLNAME))
                .hasArg().argName(FILEARG)
                .build());

        // create the command line parser and parse the arguments
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            if (line.hasOption(PC1500ARG)) {
                device = PocketPcDevice.PC1500;
            } else {
                device = PocketPcDevice.PC1600;
            }
            if (line.hasOption(LOADARG) && line.hasOption(SAVEARG)) {
                formatter.printHelp(TOOLNAME, null, options, "ERROR: Either save or load must be selected, but not both");
                return null;
            }
            if (line.hasOption(SAVEARG)) {
                direction = Direction.FROMPOCKETOPC;
                fileName = line.getOptionValue(SAVEARG);
            }
            if (line.hasOption(ASCIIARG)) {
                ascii = true;
            }
            if (line.hasOption(LOADARG)) {
                direction = Direction.FROMPCTOPOCKET;
                fileName = line.getOptionValue(LOADARG);
                if (line.hasOption(UTILARG)) {
                    util = true;
                }
            }
            if (line.hasOption("help")) {
                formatter.printHelp(TOOLNAME, options);
                return null;
            }
        } catch (MissingOptionException ex) {
            formatter.printHelp("Help", options);
            return null;
        } catch (ParseException exp) {
            formatter.printHelp(TOOLNAME, null, options, "ERROR: A file name is required");
            return null;
        }
        // Exactly one of the options must have been selected. Zero or more than one is an error.
        if (direction == null) {
            // Zero options selected
            formatter.printHelp(TOOLNAME, null, options, "ERROR: No option has been selected");
            return null;
        }
        return new CmdLineArgs(fileName, device, util, ascii, direction);
    }
}
