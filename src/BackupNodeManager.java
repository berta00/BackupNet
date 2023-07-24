import javax.imageio.ImageTranscoder;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class BackupNodeManager {
    private static LinkedList<BackupNode> currentAvailableNodes = new LinkedList<BackupNode>(); // nodes that are currently available (at the last update)
    private static LinkedList<BackupNode> currentAvailableStorageNodes = new LinkedList<BackupNode>(); // nodes that are currently available (at the last update)
    private static LinkedList<BackupNode> storageNodes = new LinkedList<BackupNode>(); // nodes that has storage in them (even the inactive ones)
    private static LinkedList<BackupFile> backups = new LinkedList<BackupFile>(); // all the backups made

    // discovery
    public static String discoveryRequest(String address) throws IOException {
        Socket socket = new Socket(address, 4040);

        InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        PrintWriter outputWriter = new PrintWriter(socket.getOutputStream());

        // write discovery request in the socket
        outputWriter.println("discovery request");
        // wait for response
        return bufferedReader.readLine();
    }

    // current available nodes
    public static LinkedList<BackupNode> getCurrentAvailableNodes(){
        updateNodes();
        return currentAvailableNodes;
    }
    public static int getCurrentAvailableNodesNumber(){
        updateNodes();
        return currentAvailableNodes.size();
    }
    private static void updateNodes(){
        //todo: update the all available nodes in the network
    }

    // storage nodes
    public static LinkedList<BackupNode> getStorageNodes(){
        return currentAvailableNodes;
    }
    public static int getStorageNodesNumber(){
        return storageNodes.size();
    }

    // available storage nodes and storage match search
    public static LinkedList<BackupNode> getAvailableStorageNodes(){
        updateNodes();
        return currentAvailableStorageNodes;
    }
    /*
    public LinkedList<BackupNode> getAvailableStorageNodesByBackupId(int id){
        LinkedList<BackupNode> availableStorageNodesWithBackup = new LinkedList<BackupNode>();

        for(int a = 0; a < this.currentAvailableStorageNodes.size(); a++){
            for(int b = 0; b < this.currentAvailableStorageNodes.get(a).getStoredBackupsNumber(); b++){
                if(this.currentAvailableStorageNodes.get(a).getStoredBackupIdByIndex(b) == id){
                    availableStorageNodesWithBackup.add(this.currentAvailableStorageNodes.get(a));
                }
            }
        }

        return availableStorageNodesWithBackup;
    }
    */

    // search for backup
    public static byte[] getBackupById(int backupId){
        BackupFile matchingBackup = null;

        int a = 0;
        boolean exit = false;
        while(!exit){
            if(backups.get(a).getId() == backupId){
                exit = true;
                matchingBackup = backups.get(a);
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
    private static byte[] getFragmentFromStorageNode(int backupId, int fragmentId){
        updateNodes();
        byte[] retrivedFragment = null;

        int a = 0;
        boolean exit = false;
        while(!exit){
            if(backups.get(a).getId() == backupId){

            }
            a++;
        }

        return retrivedFragment;
    }
}
