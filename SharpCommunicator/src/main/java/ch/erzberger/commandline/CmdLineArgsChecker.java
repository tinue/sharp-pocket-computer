package ch.erzberger.commandline;

import lombok.extern.java.Log;
import org.apache.commons.cli.*;

@Log
public class CmdLineArgsChecker {
    public static final String INPUT = "input";
    public static final String INPUTFILEARG = "inputfile";
    public static final String OUTPUT = "output";
    public static final String OUTPUTFILEARG = "outputfile";
    public static final String COMPACTARG = "compact";
    public static final String UTILARG = "addutil";
    public static final String ASCIIARG = "ascii";
    public static final String TOOLNAME = "SharpCommunicator";
    public static final String PC1500ARG = "1500";

    public CmdLineArgs checkArgs(String[] args) {
        // Set up the command line parameters
        Options options = new Options();
        options.addOption(new Option("h", "help", false, "Print usage."));
        options.addOption(new Option("u", UTILARG, false, "Add the serial utilities when loading the Basic program."));
        options.addOption(new Option("a", ASCIIARG, false, "Load / Save a Basic program as ASCII."));
        options.addOption(new Option("5", PC1500ARG, false, "Device is a PC1500/A connected to a CE-158X (default is a PC-1600)."));
        options.addOption(new Option("c", COMPACTARG, false, "Only valid together with --ascii and --output; The output will be as compact as possible to reduce typing efforts."));
        options.addOption(Option.builder("i").longOpt(INPUT)
                .desc("Input file. If this argument is missing, the input is taken from the PC-1500/1600 via serial.")
                .hasArg().argName(INPUTFILEARG)
                .build());
        options.addOption(Option.builder("o").longOpt(OUTPUT)
                .desc("Output file. If this argument is missing, the output is sent to the PC-1500/1600 via serial.")
                .hasArg().argName(OUTPUTFILEARG)
                .build());

        // create the command line converters and parse the arguments
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CmdLineArgs cmdLineArgs = new CmdLineArgs();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            if (line.hasOption(PC1500ARG)) {
                cmdLineArgs.setDevice(PocketPcDevice.PC1500);
            } else {
                cmdLineArgs.setDevice(PocketPcDevice.PC1600);
            }
            if (line.hasOption(ASCIIARG)) {
                cmdLineArgs.setAscii(true);
            }
            if (line.hasOption(UTILARG)) {
                cmdLineArgs.setUtil(true);
            }
            if (line.hasOption(COMPACTARG)) {
                if (line.hasOption(OUTPUT) && line.hasOption(ASCIIARG)) {
                    cmdLineArgs.setCompact(true);
                } else {
                    formatter.printHelp(TOOLNAME, null, options, "ERROR: --compact is only valid for output files in ascii");
                    return null;
                }
            }
            if (line.hasOption(INPUT)) {
                cmdLineArgs.setInputFile(line.getOptionValue(INPUT));
            }
            if (line.hasOption(OUTPUT)) {
                cmdLineArgs.setOutputFile(line.getOptionValue(OUTPUT));
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
        if (cmdLineArgs.getInputFile() == null && cmdLineArgs.getOutputFile() == null) {
            // Zero options selected
            formatter.printHelp(TOOLNAME, null, options, "ERROR: Neither input nor output file has been selected");
            return null;
        }
        return cmdLineArgs;
    }
}
