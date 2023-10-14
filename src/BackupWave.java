import org.apache.commons.cli.*;

public class BackupWave {
    private int inPort = 4040;
    private int outPort = 4041;

    public static void main(String[] args){
        // banner

        // arguments handling
        int argumentReturn;
        try {
            argumentReturn = argumentHandler(args);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }

        // TODO: ceck project structure here

    }

    // ARGUMENT HANDLER
    /**
     * Parses the given arguments with the rules given in {@link #optionInit()} and then calls the
     * method needed in order to satisfy the arguments. It will return a global status code
     *
     * @param args the arguments to be parsed
     * @return a status call:
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
            switch (cmd.getOptionValue("d")){
                case "1":
                    returnStatus = Debug.openPort(4040);
                    break;
                case "2":
                    if(cmd.hasOption("t")){
                        returnStatus = Debug.discoveryRequest(cmd.getOptionValue("t"));
                    } else {
                        throw new IllegalArgumentException("Invalid debug argument (discover mode needs a target ip [-t])");
                    }
                    break;
                case "3":
                    returnStatus = Debug.localhostInformation();
                    break;
                case "4":
                    if(cmd.hasOption("p")){
                        returnStatus = Debug.fileFragmentation(cmd.getOptionValue("p"));
                    } else {
                        throw new IllegalArgumentException("Invalid debug argument (discover mode needs a target file or directory path [-p])");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid debug argument (debug option needs a valid value [1,2,3,4])");
            }
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
        Option remove = new Option("r", "remove", true, "remove a file or directory from stage");
        Option add = new Option("a", "add", true, "add a new file or directory to stage");
        Option backup = new Option("b", "backup", true, "backup a file or a directory");

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
