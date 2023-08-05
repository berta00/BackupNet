import javax.imageio.ImageTranscoder;
import java.io.*;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import com.google.gson.*;

public class BackupNodeManager {
    private static LinkedList<BackupNode> backupNodes = new LinkedList<>();
    private static LinkedList<BackupNode> availableNodes = new LinkedList<>();
    private static LinkedList<BackupFile> backupFiles = new LinkedList<>();

    // main request response

    public static void openServiceAtPort(int port, int backlog, BackupNode localNode) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port, backlog);
        System.out.println("Server running on:");
        System.out.println("address: " + serverSocket.getLocalSocketAddress());
        System.out.println("port:    " + serverSocket.getLocalPort());
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
            PrintWriter outputWriter = new PrintWriter(output, true);

            // read incoming requests
            String requestLine = bufferedInputReader.readLine();

            if(requestLine.substring(0,6).equals("ds-req")){  // ceck for discovery request
                Gson jsonMarshal = new Gson();
                String response = "ds-res;" + jsonMarshal.toJson(localNode);
                outputWriter.println(response);
            } else if(requestLine.substring(0,6).equals("sd-req")){  // ceck for remote shutdown request
                String response = "sd-res;";
                outputWriter.println(response);
                forceExit = true;
            }
        }
    }

    // discovery
    public static BackupNode discoveryRequest(String address, int port, String localInfos) throws IOException { // localInfos: softwareVersion;ip;hostname
        Socket socket = new Socket(address, port);

        InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);

        // write discovery request in the socket
        outputWriter.println("ds-req;" + localInfos);
        // parse the response
        // create a new backup node
        Gson jsonUnmarshal = new Gson();
        BackupNode discoveredNode = jsonUnmarshal.fromJson(bufferedReader.readLine().substring(7), BackupNode.class);
        // wait for response
        return discoveredNode;
    }

    // current available nodes
    public static LinkedList<BackupNode> getAvailableNodes(){
        updateNodes();
        return availableNodes;
    }
    public static int getAvailableNodesSize(){
        updateNodes();
        return availableNodes.size();
    }
    private static void updateNodes(){
        //todo: update the all available nodes in the network
    }

    // search for backup
    public static byte[] getBackupFileById(int backupId){
        BackupFile matchingBackup = null;

        int a = 0;
        boolean exit = false;
        while(!exit){
            if(backupFiles.get(a).getId() == backupId){
                exit = true;
                matchingBackup = backupFiles.get(a);
            }
            a++;
        }

        byte[] retrivedBackupData = new byte[matchingBackup.getSize()];
        for(int b = 0, d = 0; b < matchingBackup.getFragmentsSize().length; b++){
            byte[] retrivedFrame = matchingBackup.getFragmentBackupNodeById(backupId).getStoredFragmentByBackupIdAndFragmentId(backupId, b);
            for(int c = 0; c < retrivedFrame.length; c++){
                retrivedBackupData[d] = retrivedFrame[c];
                d++;
            }
        }

        return retrivedBackupData;
    }
    private static byte[] getFragmentFromNode(int backupId, int fragmentId){
        updateNodes();
        byte[] retrivedFragment = null;

        int a = 0;
        boolean exit = false;
        while(!exit){
            if(backupFiles.get(a).getId() == backupId){

            }
            a++;
        }

        return retrivedFragment;
    }
}
