package ch.erzberger.sharppc;

import lombok.extern.java.Log;
import org.apache.commons.cli.*;

@Log
public class CmdLineArgsChecker {
    public static final String PRINTARG = "printer";
    public static final String SAVEARG = "save";
    public static final String LOADARG = "load";
    public static final String FILEARG = "filename";
    public static final String TOOLNAME = "SharpCommunicator";

    String checkArgs(String[] args) {
        boolean print = false;
        boolean save = false;
        boolean load = false;
        String fileName = null;
        // Set up the command line parameters
        Options options = new Options();
        options.addOption(new Option("h", "help", false, "Print usage."));
        options.addOption(new Option("p", PRINTARG, false, "Simulate a printer."));
        options.addOption(Option.builder("s").longOpt(SAVEARG)
                .desc(String.format("Save the file received from the PC-1600 to disk. Note: Start %s first, then launch SAVE on the PC-1600.", TOOLNAME))
                .hasArg().argName(FILEARG)
                .build());
        options.addOption(Option.builder("l").longOpt(LOADARG)
                .desc(String.format("Load a file from disk onto the PC-1600. Note: Launch LOAD on the PC-1600 first, then launch %s.", TOOLNAME))
                .hasArg().argName(FILEARG)
                .build());

        // create the command line parser and parse the arguments
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            if (line.hasOption(PRINTARG)) {
                print = true;
            }
            if (line.hasOption(SAVEARG)) {
                save = true;
                fileName = line.getOptionValue(SAVEARG);
            }
            if (line.hasOption(LOADARG)) {
                load = true;
                fileName = line.getOptionValue(LOADARG);
            }
            if (line.hasOption("help")) {
                formatter.printHelp(TOOLNAME, options);
                return null;
            }
        } catch (MissingOptionException ex) {
            formatter.printHelp("Help", options);
            return null;
        } catch (ParseException exp) {
            formatter.printHelp(TOOLNAME, null, options, "A file name is required");
            return null;
        }
        // Exactly one of the options must have been selected. Zero or more than one is an error.
        if (!print && !save && !load) {
            // Zero options selected
            formatter.printHelp(TOOLNAME, null, options, "No option has been selected");
            return null;
        }
        if ((print && load) || (print && save) || (save && load)) {
            // Two or three selected
            formatter.printHelp(TOOLNAME, null, options, "Only one option can be selected");
            return null;
        }
        if (print) {
            return "p";
        }
        if (save) {
            return String.format("s(%s)", fileName);
        }
        return String.format("l(%s)", fileName);
    }
}
