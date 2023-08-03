import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Properties;

public class Main {
    private static int debugMode = 0;
    private static String filePath = "";
    private static boolean backupFlag = false;
    private static boolean fragmentationFlag = false;
    private static boolean syncFile = false;

    // software data
    private static String localSoftwareVersion = "";
    private static int localSoftwarePort = 0;
    private static String banner = "";

    // local node
    private static BackupNode localNode;
    public static void main(String[] args) {
        // read config file
        String configFilePath = "backupWave.config";
        Properties configFile = new Properties();
        try(FileInputStream configFileIS = new FileInputStream(configFilePath)){
            configFile.load(configFileIS);
            // assign config variables to attributes
            localSoftwareVersion = configFile.getProperty("backupWave.version");
            localSoftwarePort = Integer.parseInt(configFile.getProperty("backupWave.port"));
            banner = configFile.getProperty("backupWave.banner");

        } catch(IOException e){
            throwError("Coudn't find the configuration file", true);
        }

        // startup banner
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(banner);

        // assign local host data
        String localIp = "", localHostname = "", localMacAddr = "";
        try(Socket socket = new Socket()){
            socket.connect(new InetSocketAddress("google.com", 80));
            // get local ip
            localIp = socket.getLocalAddress().getHostAddress();
            // get local mac address
            byte[] localByteMacAddr = NetworkInterface.getByInetAddress(socket.getLocalAddress()).getHardwareAddress();
            StringBuilder stringBuilder = new StringBuilder();
            for(int a = 0; a < localByteMacAddr.length; a++){
                stringBuilder.append(String.format("%02X%s", localByteMacAddr[a], (a < localByteMacAddr.length - 1) ? "-" : ""));
            }
            localMacAddr = stringBuilder.toString();
            // get local hostname
            localHostname = InetAddress.getLocalHost().getHostName();
            // assign all infos to the local backupNode
            localNode = new BackupNode(0, localIp, localHostname, localMacAddr);
        } catch(Exception e ){
            throwError("Please assign an ip to the computer in order to use this software " + e.getMessage(), true);
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
                    // opens the service
                    BackupNodeManager.openServiceAtPort(localSoftwarePort, localNode);
                } catch(IOException e){
                    System.out.println("error: " + e.getMessage());
                }
                break;
            case 2:
                System.out.println("⚠\uFE0F  Debug mode 2 enabled ⚠\uFE0F");
                BackupNode discoveryResponse = null;
                try {
                    String localData = localSoftwareVersion + ";ip=" + localIp + ";hostname=" + localHostname;
                    // makes a discovery request
                    discoveryResponse = BackupNodeManager.discoveryRequest(targetAddress, localSoftwarePort, localData);
                } catch(IOException e){
                    throwError("IOException during a discovery connection", true);
                }
                // print the response
                System.out.println("ds-response:" + discoveryResponse.toPrintableFormat());
                break;
            case 3:
                System.out.println("⚠\uFE0F  Debug mode 3 enabled ⚠\uFE0F");
                System.out.println("version:  " + localSoftwareVersion);
                System.out.println(localNode.toPrintableFormat());
                System.exit(1);
                break;
            default:
                throwError("Invalid argument", true);
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
