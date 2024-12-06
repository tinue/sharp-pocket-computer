package ch.erzberger.commandline;

import lombok.extern.java.Log;
import org.apache.commons.cli.*;

@Log
public class CmdLineArgsChecker {
    public static final String INPUT_FILE = "in-file";
    public static final String INPUT_FORMAT = "in-format";
    public static final String OUTPUT_FILE = "out-file";
    public static final String OUTPUT_FORMAT = "out-format";
    public static final String UTILARG = "add-utils";
    public static final String TOOLNAME = "SharpCommunicator";
    public static final String DEVICE = "device";
    public static final String VERBOSEARG = "verbose";
    public static final String DEBUGARG = "debug";
    public static final String VERSIONARG = "version";

    public CmdLineArgs checkArgs(String[] args) { //NOSONAR: Complexity dealt with using unit tests
        // Set up the command line parameters
        Options options = new Options();
        options.addOption(new Option("h", "help", false, "Print usage."));
        options.addOption(new Option("v", VERBOSEARG, false, "Verbose mode (more logging)."));
        options.addOption(new Option("vv", DEBUGARG, false, "Debug mode (even more logging)."));
        options.addOption(new Option("V", VERSIONARG, false, "Prints version information."));
        options.addOption(new Option("u", UTILARG, false, "Add the serial utilities when loading the Basic program."));
        options.addOption(Option.builder("d").longOpt(DEVICE)
                .desc("Device to use (pc1500, pc1500a or pc1600). Default is pc1500.")
                .hasArg()
                .build());
        options.addOption(Option.builder("i").longOpt(INPUT_FILE)
                .desc("Input file. If this argument is missing, the input is taken from the PC-1500/1600 via serial.")
                .hasArg()
                .build());
        options.addOption(Option.builder("if").longOpt(INPUT_FORMAT)
                .desc("Format of the input file (ascii or binary). Default is ascii for files with an extension of .bas, binary for all other files.")
                .hasArg()
                .build());
        options.addOption(Option.builder("o").longOpt(OUTPUT_FILE)
                .desc("Output file. If this argument is missing, the output is sent to the PC-1500/1600 via serial.")
                .hasArg()
                .build());
        options.addOption(Option.builder("of").longOpt(OUTPUT_FORMAT)
                .desc("Format of the output file (ascii, asciicompact or binary). Default is ascii for files with an extension of .bas, binary for all other files.")
                .hasArg()
                .build());

        // create the command line converters and parse the arguments
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CmdLineArgs cmdLineArgs = new CmdLineArgs();
        try {
            // parse the command line arguments, start with the simple ones
            CommandLine line = parser.parse(options, args);
            // Version is like help: No other arguments are even checked.
            if (line.hasOption(VERSIONARG)) {
                cmdLineArgs.setVersion(true);
                return cmdLineArgs;
            }
            if (line.hasOption(VERBOSEARG)) {
                cmdLineArgs.setVerbose(true);
            }
            if (line.hasOption(DEBUGARG)) {
                cmdLineArgs.setDebug(true);
            }
            if (line.hasOption(UTILARG)) {
                cmdLineArgs.setUtil(true);
            }
            if (line.hasOption("help")) {
                formatter.printHelp(TOOLNAME, options);
                return null;
            }
            // Now the ones with a parameter
            if (line.hasOption(DEVICE)) {
                switch (line.getOptionValue(DEVICE)) {
                    case "pc1600":
                        cmdLineArgs.setDevice(PocketPcDevice.PC1600);
                        break;
                    case "pc1500":
                        cmdLineArgs.setDevice(PocketPcDevice.PC1500);
                        break;
                    case "pc1500a":
                        cmdLineArgs.setDevice(PocketPcDevice.PC1500A);
                        break;
                    default:
                        formatter.printHelp(TOOLNAME, null, options, "ERROR: Invalid device");
                        return null;
                }
            } else {
                cmdLineArgs.setDevice(PocketPcDevice.PC1500); // Default
            }
            if (line.hasOption(INPUT_FILE)) {
                cmdLineArgs.setInputFile(line.getOptionValue(INPUT_FILE));
            }
            if (line.hasOption(INPUT_FORMAT)) {
                switch (line.getOptionValue(INPUT_FORMAT).toLowerCase()) {
                    case "ascii":
                        cmdLineArgs.setInputFormat(FileFormat.ASCII);
                        break;
                    case "binary":
                        cmdLineArgs.setInputFormat(FileFormat.BINARY);
                        break;
                    default:
                        formatter.printHelp(TOOLNAME, null, options, "ERROR: Invalid input file format");
                        return null;
                }
            }
            if (line.hasOption(OUTPUT_FILE)) {
                cmdLineArgs.setOutputFile(line.getOptionValue(OUTPUT_FILE));
            }
            if (line.hasOption(OUTPUT_FORMAT)) {
                switch (line.getOptionValue(OUTPUT_FORMAT).toLowerCase()) {
                    case "ascii":
                        cmdLineArgs.setOutputFormat(FileFormat.ASCII);
                        break;
                    case "asciicompact":
                        cmdLineArgs.setOutputFormat(FileFormat.ASCIICOMPACT);
                        break;
                    case "binary":
                        cmdLineArgs.setOutputFormat(FileFormat.BINARY);
                        break;
                    default:
                        formatter.printHelp(TOOLNAME, null, options, "ERROR: Invalid output file format");
                        return null;
                }
            }
        } catch (MissingOptionException ex) {
            formatter.printHelp("Help", options);
            return null;
        } catch (ParseException exp) {
            formatter.printHelp(TOOLNAME, null, options, "ERROR: A file name is required");
            return null;
        }
        // Validate the file arguments
        // At least one of inputfile or outputfile must be selected
        if (cmdLineArgs.getInputFile() == null && cmdLineArgs.getOutputFile() == null) {
            // Zero options selected
            formatter.printHelp(TOOLNAME, null, options, "ERROR: Neither input nor output file has been given");
            return null;
        }
        // Set defaults for missing format values. The defaults depend on the source and target.
        boolean sourceFileExists = cmdLineArgs.getInputFile() != null;
        boolean targetFileExists = cmdLineArgs.getOutputFile() != null;
        // First: From file to file (no Pocket Computer involved). The source file format depends on the file extension,
        // and the target file format is by default ASCIICOMPACT.
        if (sourceFileExists && targetFileExists) {
            if (FileFormat.BINARY.equals(cmdLineArgs.getInputFormat())) {
                // One additional check, apart from setting defaults, because this is not currently supported
                formatter.printHelp(TOOLNAME, null, options, "ERROR: Source file can't be binary (at least not for now)");
                return null;
            }
            // But now go on with actually setting the defaults. Start with the input file format
            if (cmdLineArgs.getInputFormat() == null) {
                cmdLineArgs.setInputFormat(isBasic(cmdLineArgs.getInputFile()) ? FileFormat.ASCII : FileFormat.BINARY);
            }
            // Output: Default is ASCIICOMPACT
            if (cmdLineArgs.getOutputFormat() == null) {
                cmdLineArgs.setOutputFormat(FileFormat.ASCIICOMPACT);
            }
            return cmdLineArgs; // We are done, return with the arguments
        }
        if (sourceFileExists) {
            // From PC to Pocket Computer. Input format depends on the file extension, output is binary by default
            if (cmdLineArgs.getInputFormat() == null) {
                cmdLineArgs.setInputFormat(isBasic(cmdLineArgs.getInputFile()) ? FileFormat.ASCII : FileFormat.BINARY);
            }
            if (cmdLineArgs.getOutputFormat() == null) {
                cmdLineArgs.setOutputFormat(FileFormat.BINARY);
            }
            return cmdLineArgs; // We are done, return with the arguments
        }
        // Last case: From Pocket Computer to PC. The input file format is unknown. It could be analyzed, but this is not currently done.
        // Hence, the default input format depends on the output format. Essentially binary -> binary and ascii -> ascii
        // First check the special case: Input binary and output ascii is not supported.
        if (FileFormat.BINARY.equals(cmdLineArgs.getInputFormat()) && !FileFormat.BINARY.equals(cmdLineArgs.getOutputFormat())) {
            formatter.printHelp(TOOLNAME, null, options, "ERROR: Cannot convert from binary to ASCII (for now)");
            return null;
        }
        // Start with setting the default output format
        if (cmdLineArgs.getOutputFormat() == null) {
            cmdLineArgs.setOutputFormat(isBasic(cmdLineArgs.getOutputFile()) ? FileFormat.ASCII : FileFormat.BINARY);
        }
        // Now, set the default input format to the previously determined output format.
        if (cmdLineArgs.getInputFormat() == null) {
            cmdLineArgs.setInputFormat(cmdLineArgs.getOutputFormat().equals(FileFormat.BINARY) ? FileFormat.BINARY : FileFormat.ASCII);
        }
        return cmdLineArgs;
    }

    private boolean isBasic(String filename) {
        return (filename.toLowerCase().endsWith(".bas") || "clip".equalsIgnoreCase(filename));
    }
}
