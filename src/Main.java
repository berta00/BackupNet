import java.io.*;
import java.net.*;
import java.util.Properties;

public class Main {
    private static int debugMode = 0;
    private static String filePath = "";
    private static boolean backupFlag = false;
    private static boolean fragmentationFlag = false;
    private static boolean syncFile = false;

    // software data
    private static String localSoftwareVersion = "";
    private static String banner = "";

    // local data
    private static String localHostname = "";
    private static String localIp = "";

    public static void main(String[] args) {
        // read config file
        String configFilePath = "backupWave.config";
        Properties configFile = new Properties();
        try(FileInputStream configFileIS = new FileInputStream(configFilePath)){
            configFile.load(configFileIS);
            // assign config variables to attributes
            localSoftwareVersion = configFile.getProperty("backupWave.version");
            banner = configFile.getProperty("backupWave.banner");
        } catch(IOException e ){
            throwError("Coudn't find the configuration file", true);
        }

        // startup banner
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(banner);

        // assign local host data
        try(Socket socket = new Socket()){
            socket.connect(new InetSocketAddress("google.com", 80));
            localIp = socket.getLocalAddress().getHostAddress();
            localHostname = InetAddress.getLocalHost().getHostName();
        } catch(Exception e ){
            throwError("Please assign an ip to the computer in order to use this software", true);
        }

        int debugMode = 0;
        String filePath = "";
        String targetAddress = "";
        boolean backupFlag = false;
        boolean fragmentationFlag = false;
        boolean syncFile = false;

        // parse arguments TODO: (argument parsing shoud be revised for better exceptions catching)
        boolean argFlag = true;
        for(int argI = 0; argI < args.length; argI++){
            if(argFlag){
                switch(args[argI]){
                    case "-d":
                        argFlag = false;
                        debugMode = Integer.parseInt(args[argI +1]);
                        break;
                    case "-a":
                        argFlag = false;
                        targetAddress = args[argI +1];
                        break;
                    case "-f":
                        argFlag = false;
                        filePath = args[argI +1];
                        break;
                    case "-b":
                        backupFlag = true;
                        break;
                    case "-Fr":
                        fragmentationFlag = true;
                        break;
                    case "-s":
                        syncFile = true;
                        break;
                    default:
                        throwError("Invalid argument", true);
                }
            } else {
                argFlag = true;
            }
        }

        // enable debug mode according to the arguments
        switch(debugMode){
            case 1:
                try {
                    System.out.println("⚠\uFE0F  Debug mode 1 enabled ⚠\uFE0F");
                    openServiceAtPort(4040);
                } catch(IOException e){
                    System.out.println("error: " + e.getMessage());
                }
                break;
            case 2:
                System.out.println("⚠\uFE0F  Debug mode 2 enabled ⚠\uFE0F");
                String discoveryResponse = "";
                try {
                    discoveryResponse = BackupNodeManager.discoveryRequest(targetAddress);
                } catch(IOException e){
                    throwError("IOException during a discovery connection", true);
                }
                // print the response
                System.out.println(discoveryResponse);
                break;
            case 3:
                System.out.println("⚠\uFE0F  Debug mode 3 enabled ⚠\uFE0F");
                System.out.println("version:  " + localSoftwareVersion);
                System.out.println("hostname: " + localHostname);
                System.out.println("ip:       " + localIp);
                System.exit(1);
                break;
            default:
                throwError("Invalid argument", true);
        }

    }

    private static void openServiceAtPort(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("\nListening for requests:");

        // listen for connections
        boolean forceExit = false;
        while(!forceExit){
            Socket socket = serverSocket.accept();

            // TODO: shoud create a new thread to handle every socket

            // input
            InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedInputReader = new BufferedReader(inputReader);

            // output
            OutputStream output = socket.getOutputStream();
            PrintWriter outputWriter = new PrintWriter(output);

            // read incoming requests
            String request = bufferedInputReader.readLine();

            if(request.equals("discovery request")){
                String response = "discovery response;" + localSoftwareVersion + ";ip=" + localIp + ";hostname=" + localHostname;
                outputWriter.println(response);
            } else if(request.equals("remote-software-shutdown request")){
                String response = "remote-software-shutdown response";
                outputWriter.println(response);
                forceExit = true;
            }
        }
    }

    // error handling
    private static void throwError(String errorMessage, boolean exit){
        System.err.println("Error: " + errorMessage);
        if(exit){
            System.exit(1);
        }
    }
}
