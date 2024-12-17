package ch.erzberger.commandline;

import ch.erzberger.filehandling.ManifestHandler;
import lombok.extern.java.Log;
import org.apache.commons.cli.*;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static final String STARTADDR = "start-address";
    public static final String AUTORUN = "autorun-address";

    public CmdLineArgs checkArgs(String[] args) { //NOSONAR: Complexity dealt with using unit tests
        // Set up the command line parameters
        Options options = createOptions();
        // Parse the parameters into a transfer object. A HelpFormatter is needed to return helpful messages
        HelpFormatter formatter = new HelpFormatter();
        CmdLineArgs populatedArgs = parseCommandLine(options, args, formatter);
        // Check basic constraints, such as at least one filename is given
        if (!checkPreConstraints(options, populatedArgs, formatter)) {
            return null;
        }
        // Add default values
        populatedArgs = addDefaults(populatedArgs);
        // Check more subtle constraints, after the defaults have been populates, such as "cannot de-tokenize"
        if (checkPostConstraints(options, populatedArgs, formatter)) {
            return populatedArgs;
        } else {
            return null;
        }
    }

    private boolean isBasic(String filename) {
        return (filename.toLowerCase().endsWith(".bas") || "clip".equalsIgnoreCase(filename));
    }

    private Options createOptions() {
        Options options = new Options();
        options.addOption(new Option("h", "help", false, "Print usage."));
        options.addOption(new Option("v", VERBOSEARG, false, "Verbose mode (more logging)."));
        options.addOption(new Option("vv", DEBUGARG, false, "Debug mode (even more logging)."));
        options.addOption(new Option("V", VERSIONARG, false, "Prints version information."));
        options.addOption(new Option("u", UTILARG, false, "Add the serial utilities when loading the Basic program."));
        options.addOption(Option.builder("d").longOpt(DEVICE).desc("Device to use (pc1500, pc1500a or pc1600). Default is pc1500.").hasArg().build());
        options.addOption(Option.builder("i").longOpt(INPUT_FILE).desc("Input file. If this argument is missing, the input is taken from the PC-1500/1600 via serial.").hasArg().build());
        options.addOption(Option.builder("if").longOpt(INPUT_FORMAT).desc("Format of the input file (ascii or binary). Default is ascii for files with an extension of .bas, binary for all other files.").hasArg().build());
        options.addOption(Option.builder("o").longOpt(OUTPUT_FILE).desc("Output file. If this argument is missing, the output is sent to the PC-1500/1600 via serial.").hasArg().build());
        options.addOption(Option.builder("of").longOpt(OUTPUT_FORMAT).desc("Format of the output file (ascii, asciicompact or binary). Default is ascii for files with an extension of .bas, binary for all other files.").hasArg().build());
        options.addOption(Option.builder("sa").longOpt(STARTADDR).desc("Start address of a machine language program. Pass the value in hex, for example '38c5' or '38C5'").hasArg().build());
        options.addOption(Option.builder("ra").longOpt(AUTORUN).desc("Autorun address of a machine language program. Pass the value in hex, for example '38c5' or '38C5'. If the parameter is skipped, the program will not start by itself after loading").hasArg().build());
        return options;
    }

    private CmdLineArgs parseCommandLine(Options options, String[] args, HelpFormatter formatter) {
        // create the command line converters and parse the arguments
        CommandLineParser parser = new DefaultParser();
        CmdLineArgs cmdLineArgs = new CmdLineArgs();
        try {
            // Parse the command line arguments, start with the ones that cause direct action here in this method.
            CommandLine line = parser.parse(options, args);
            // First, check for help. Dump and exit.
            if (line.hasOption("help")) {
                formatter.printHelp(TOOLNAME, options);
                return null;
            }
            // Version is like help: No other arguments are even checked. Just dump the version and exit.
            if (line.hasOption(VERSIONARG)) {
                String version = ManifestHandler.getVersionFromManifest();
                log.log(Level.INFO, "SharpCommunicator, Version {0}", version);
                return null;
            }
            // Update log level if necessary.
            Level logLevel = line.hasOption(DEBUGARG) ? Level.FINEST : (line.hasOption(VERBOSEARG)) ? Level.FINE : null;
            if (logLevel != null) {
                Logger appLogger = Logger.getLogger("ch.erzberger");
                appLogger.setLevel(logLevel);
                for (Handler h : appLogger.getHandlers()) {
                    h.setLevel(logLevel);
                }
                log.log(logLevel, "Log level is {0}", logLevel);
            }
            // From here and further down, populate the arguments that actually go into CmdLineArgs
            if (line.hasOption(UTILARG)) {
                cmdLineArgs.setUtil(true);
            }
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
            if (line.hasOption(STARTADDR)) {
                Integer startAddr = inputToInt(line.getOptionValue(STARTADDR));
                if (startAddr == null) {
                    formatter.printHelp(TOOLNAME, null, options, "ERROR: Start address is not a number");
                } else {
                    cmdLineArgs.setStartAddr(startAddr); // Autoboxing is ok here
                }
            }
            if (line.hasOption(AUTORUN)) {
                Integer autoRun = inputToInt(line.getOptionValue(AUTORUN));
                if (autoRun == null) {
                    formatter.printHelp(TOOLNAME, null, options, "ERROR: Autorun address is not a number");
                } else {
                    cmdLineArgs.setRunAddr(autoRun); // Autoboxing is ok here
                }
            }
        } catch (MissingOptionException ex) {
            formatter.printHelp("Help", options);
            return null;
        } catch (ParseException exp) {
            formatter.printHelp(TOOLNAME, null, options, "ERROR: A file name is required");
            return null;
        }
        return cmdLineArgs;
    }

    private CmdLineArgs addDefaults(CmdLineArgs cmdLineArgs) {
        if (cmdLineArgs == null) {
            // No defaults to be added if the arguments could not be parsed before
            return null;
        }
        // If a start address is set, but not a run address, set the run address to 0xFFFF
        if (cmdLineArgs.getStartAddr() != null && cmdLineArgs.getRunAddr() == null) {
            cmdLineArgs.setRunAddr(0xFFFF);
        }
        // Set defaults for missing format values. The defaults depend on the source and target.
        boolean sourceFileExists = cmdLineArgs.getInputFile() != null;
        boolean targetFileExists = cmdLineArgs.getOutputFile() != null;
        // First: From file to file (no Pocket Computer involved). The source file format depends on the file extension,
        if (sourceFileExists && targetFileExists) {
            // Default for input file format
            if (cmdLineArgs.getInputFormat() == null) {
                cmdLineArgs.setInputFormat(isBasic(cmdLineArgs.getInputFile()) ? FileFormat.ASCII : FileFormat.BINARY);
            }
            // Output: Default for a .bas is ASCIICOMPACT, for a .bin is BINARY
            if (cmdLineArgs.getOutputFormat() == null) {
                cmdLineArgs.setOutputFormat(isBasic(cmdLineArgs.getOutputFile()) ? FileFormat.ASCIICOMPACT : FileFormat.BINARY);
            }
            return cmdLineArgs; // We are done, return with the arguments
        }
        // Second, from PC to Pocket Computer. Input format depends on the file extension, output is binary by default
        if (sourceFileExists) {
            if (cmdLineArgs.getInputFormat() == null) {
                cmdLineArgs.setInputFormat(isBasic(cmdLineArgs.getInputFile()) ? FileFormat.ASCII : FileFormat.BINARY);
            }
            if (cmdLineArgs.getOutputFormat() == null) {
                cmdLineArgs.setOutputFormat(FileFormat.BINARY);
            }
            return cmdLineArgs; // We are done, return with the arguments
        }
        // Third: From Pocket Computer to PC. The input file format is unknown. It could be analyzed, but this is not currently done.
        // Hence, the default input format depends on the output format. Essentially binary -> binary and ascii -> ascii
        if (targetFileExists) {
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
        // Something went wrong
        return null;
    }

    private boolean checkPreConstraints(Options options, CmdLineArgs cmdLineArgs, HelpFormatter formatter) {
        if (cmdLineArgs == null) {
            // Clearly not plausible, if they could not be parsed before.
            return false;
        }
        // Validate the file arguments
        // At least one of inputfile or outputfile must be selected
        if (cmdLineArgs.getInputFile() == null && cmdLineArgs.getOutputFile() == null) {
            // Zero options selected
            formatter.printHelp(TOOLNAME, null, options, "ERROR: Neither input nor output file has been given");
            return false;
        }
        // Cannot have a run address without a start address
        if (cmdLineArgs.getRunAddr() != null && cmdLineArgs.getStartAddr() == null) {
            formatter.printHelp(TOOLNAME, null, options, "ERROR: Cannot have a run address without a start address");
            return false;
        }
        return true;
    }

    private boolean checkPostConstraints(Options options, CmdLineArgs cmdLineArgs, HelpFormatter formatter) {
        if (cmdLineArgs == null) {
            // Clearly not plausible, if they could not be parsed before.
            return false;
        }
        // Cannot convert from a binary input to an ASCII output (de-tokenizing)
        if (FileFormat.BINARY.equals(cmdLineArgs.getInputFormat()) && !FileFormat.BINARY.equals(cmdLineArgs.getOutputFormat())) {
            formatter.printHelp(TOOLNAME, null, options, "ERROR: Cannot convert from binary to ASCII (for now)");
            return false;
        }
        // The address parameters are only meaningful for machine language files from disk. If any data is received from
        // a Pocket Computer, then a header is already present. For non-machine language files, these parameters are
        // irrelevant.
        if (cmdLineArgs.getStartAddr() != null && (cmdLineArgs.getInputFile() == null || !cmdLineArgs.getInputFormat().equals(FileFormat.BINARY))) {
            formatter.printHelp(TOOLNAME, null, options, "ERROR: Address parameters can only be used with a machine language input file.");
            return false;
        }
        return true;
    }

    /**
     * The input can be decimal or hex. The simple Integer.parseInt in not sufficient, and some extra
     * functionality is required
     *
     * @param input An address, decimal or hex
     * @return Integer, representing the address; Null if the input cannot be parsed
     */
    Integer inputToInt(String input) {
        if (input == null || input.isEmpty()) {
            log.log(Level.FINE, "Input string is null or empty");
            return null;
        }
        String hexInput = null;
        if (input.startsWith("0x")) {
            hexInput = input.substring(2);
        } else if (input.startsWith("$") || input.startsWith("&")) {
            hexInput = input.substring(1);
        }
        try {
            return hexInput == null ? Integer.parseInt(input) : Integer.parseInt(hexInput, 16);
        } catch (NumberFormatException e) {
            log.log(Level.FINE, "Input string is not a number");
            return null;
        }
    }
}
