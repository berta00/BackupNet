import configuration.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.*;

public class BackupWave {
    // RELEASE INFORMATION
    private static final String softwareVersion = "1.0";

    // CONFIGURATION ATTRIBUTES
    private static int inPort = 4040;
    private static int outPort = 4041;
    private static String banner = null;
    private static String networkInterface = null;

    // ARGUMENT ATTRIBUTES
    private String targetAddress = null;
    private String filePath = null;

    public static void main(String[] args){
        // configuration parser
        String relativeFilePath = System.getProperty("user.dir") + "/config/backupWave.properties";
        System.out.println(relativeFilePath);

        try {
            inPort = Configuration.parseConfigFile(relativeFilePath).getInPort();
            outPort = Configuration.parseConfigFile(relativeFilePath).getOutPort();
            banner = Configuration.parseConfigFile(relativeFilePath).getBanner();
            networkInterface = Configuration.parseConfigFile(relativeFilePath).getNetworkInterface();
        } catch (FileNotFoundException e1){
            System.out.println("Error: " + e1.getMessage());
            System.exit(-1);
        } catch (IOException e2){
            System.out.println("Error: " + e2.getMessage());
            System.exit(-1);
        }

        // banner
        System.out.println(banner);

        // arguments handling
        int argumentReturn = 0;

        try {
            argumentReturn = argumentHandler(args);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    // ARGUMENTS HANDLER
    /**
     * Parses the given arguments with the rules given in {@link #optionInit()} and then calls the
     * method needed in order to satisfy the arguments. It will return a global status code
     *
     * @param args the arguments to be parsed
     * @return
     *      0 - the method has everything done;
     *      1 - the method has started a background task
     * @throws Exception if there are any problem during the parsing
     */
    public static int argumentHandler(String[] args) throws Exception {
        int returnStatus = 0;

        Options options = optionInit();
        CommandLine cmd = commandLineParser(options, args);

        // debug modes
        if(cmd.hasOption("d")){
            returnStatus = Debug.debugInit(cmd, cmd.getOptionValue("d"), softwareVersion, inPort, outPort, networkInterface);
        }

        // TODO: other modes

        return returnStatus;
    }

    // ARGUMENTS PARSER
    public static Options optionInit() throws IllegalArgumentException {
        Options options = new Options();

        Option debug = new Option("d", "debug", true, "debug mode for developers");
        Option target = new Option("t", "target", true, "specify the target address of an operation");
        Option path = new Option("p", "path", true, "specify a file or directory path of an operation");
        Option remove = new Option("r", "remove", false, "remove a file or directory from stage");
        Option add = new Option("a", "add", false, "add a new file or directory to stage");
        Option backup = new Option("b", "backup", false, "backup a file or a directory");

        options.addOption(debug);
        options.addOption(target);
        options.addOption(path);
        options.addOption(remove);
        options.addOption(add);
        options.addOption(backup);

        return options;
    }
    public static CommandLine commandLineParser(Options options, String[] arguments) throws ParseException {
        CommandLineParser parser = new BasicParser();
        HelpFormatter helper = new HelpFormatter();

        CommandLine cmd = parser.parse(options, arguments);

        return cmd;
    }
}
