import DataTypes.*;
import utilities.*;

import java.io.File;
import java.net.*;
import java.util.List;

import org.apache.commons.cli.*;

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
                    throw new IllegalArgumentException("Invalid debug argument (file fragmentation debug mode require a file path [-p])");
                }
                break;
            case "5":
                if(cmd.hasOption("p")){
                    returnStatus = Debug.directoryFragmentation(cmd.getOptionValue("p"));
                } else {
                    throw new IllegalArgumentException("Invalid debug argument (file fragmentation debug mode require a directory path [-p])");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid debug argument (debug option needs a valid value [1,2,3,4,5])");
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
        System.out.println();

        // parse and fragment the file
        FragmentedFileData fragmentedFile = null;
        try {
            fragmentedFile = FileUtilities.parseAndFragmentFile(new File(System.getProperty("user.dir") + "/" + filePath), frangmentsNumber, true);
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        // recover the file (only debug purpose)
        try {
            FileUtilities.recoverFile(fragmentedFile, filePath, PathUtilities.reconstructFilePathWithTag(filePath, "recovered"), true);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        return 0;
    }
    public static int directoryFragmentation(String directoryPath){
        System.out.println("Debug mode 5 - directory fragmentation test \n");

        int fragmentNumber = 6;

        // parse and fragment the file
        FileStructure<File> parsedDirectory = null;
        try {
            parsedDirectory = DirectoryUtilities.parseAndFragmentDirectory(new File(directoryPath), fragmentNumber, true);
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        // recover the file (only debug purpose)
        try {
            DirectoryUtilities.recoverDirectory(parsedDirectory, directoryPath, PathUtilities.reconstructDirectoryPathWithTag(directoryPath, "recovered"), true);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        return 0;
    }
}
