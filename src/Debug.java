import utilities.FileUtilities;
import utilities.IpUtilities;
import utilities.PathUtilities;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class Debug {

    // DEBUG ARGUMENT PARSER
    public static int debugInit(CommandLine cmd, String debugMode, String softwareVersion, int inPort, int outPort, String networkInterface) throws IllegalArgumentException {
        int returnStatus = 0;

        switch (debugMode){
            case "1":
                returnStatus = Debug.openPort(inPort);
                break;
            case "2":
                if(cmd.hasOption("t")){
                    returnStatus = Debug.discoveryRequest(cmd.getOptionValue("t"));
                } else {
                    throw new IllegalArgumentException("Invalid debug argument (discover mode needs a target ip [-t])");
                }
                break;
            case "3":
                returnStatus = Debug.localhostInformation(networkInterface, softwareVersion);
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

        return returnStatus;
    }

    // DEBUG MODES
    public static int openPort(int port){
        System.out.println("Debug mode 1 - open port " + port + " in listen mode on this host\n");
        return 0;
    }
    public static int discoveryRequest(String remoteAddress){
        System.out.println("Debug mode 2 - discovery request to " + remoteAddress + "\n");
        return 0;
    }
    public static int localhostInformation(String networkInterfaceName, String softwareVersion){
        System.out.println("Debug mode 3 - local host informations\n");

        // get information
        String macAddress = "none";
        String ipv4Address = "none";
        short ipv4PrefixLength = 0;
        String ipv6Address = "none";
        short ipv6PrefixLength = 0;
        String hostaname = "none";

        try {
            NetworkInterface networkInterface = NetworkInterface.getByName(networkInterfaceName);
            // mac address
            macAddress = IpUtilities.fromMacAddressToString(networkInterface.getHardwareAddress());
            // addresses
            List<InterfaceAddress> addresses = networkInterface.getInterfaceAddresses();
            for(int x = 0; x < addresses.size(); x++){
                // if ipv4
                if(addresses.get(x).getAddress() instanceof Inet4Address){
                    // ipv4
                    ipv4Address = addresses.get(x).getAddress().getHostAddress();
                    // ipv4 prefix length
                    ipv4PrefixLength = addresses.get(x).getNetworkPrefixLength();
                }
                // if ipv6
                if(addresses.get(x).getAddress() instanceof Inet6Address){
                    // ipv6
                    ipv6Address = addresses.get(x).getAddress().getHostAddress();
                    // ipv6 prefix length
                    ipv6PrefixLength = addresses.get(x).getNetworkPrefixLength();
                }
            }
            // hostname
            hostaname = InetAddress.getLocalHost().getHostName();
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        // print informaiton
        System.out.println("Software version:   " + softwareVersion);
        System.out.println("Network interface:  " + networkInterfaceName);
        System.out.println("Mac address:        " + macAddress);
        System.out.println("Ipv4 address:       " + ipv4Address + " \\" + ipv4PrefixLength);
        System.out.println("Ipv6 address:       " + ipv6Address + " \\" + ipv6PrefixLength);
        System.out.println("Hostname:           " + hostaname);

        return 0;
    }
    public static int fileFragmentation(String filePath){
        System.out.println("Debug mode 4 - file fragmentation test \n");

        // print information about the operation
        int frangmentsNumber = 9;
        System.out.println("fragments number:   " + frangmentsNumber);
        System.out.println("file path:          " + filePath);

        // parse the file
        byte[] parsedFile = null;
        try {
            parsedFile = FileUtilities.parseFile(System.getProperty("user.dir") + "/" + filePath);
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        // fragment the file
        byte[][] fragmentedFile = FileUtilities.fragmentFile(parsedFile, frangmentsNumber);

/*
        // print fragments
        for(int y = 0; y < fragmentedFile.length; y++){
            String fullFragment = "";
            for(int x = 0; x < fragmentedFile[y].length; x++){
                fullFragment += Integer.toHexString(fragmentedFile[y][x]);
            }
        }

        // recover the file (to demonstrate the fragmentation relaiability)
        try {
            // reconstrict the file-path
            String recoverFilePath = PathUtilities.reconstructFilePathWithTag(filePath, "recovered");
            // actual recover
            FileUtilities.recoverFile(fragmentedFile, recoverFilePath);
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
*/
        return 0;
    }
}
