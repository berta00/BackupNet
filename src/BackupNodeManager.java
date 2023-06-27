import javax.imageio.ImageTranscoder;
import java.util.LinkedList;

public class BackupNodeManager {
    private LinkedList<BackupNode> currentAvailableNodes; // nodes that are currently available (at the last update)
    private LinkedList<BackupNode> currentAvailableStorageNodes; // nodes that are currently available (at the last update)
    private LinkedList<BackupNode> storageNodes; // nodes that has storage in them (even the inactive ones)
    private LinkedList<BackupFile> backups; // all the backups made

    public BackupNodeManager(){
        this.currentAvailableNodes = new LinkedList<BackupNode>();
        this.currentAvailableStorageNodes = new LinkedList<BackupNode>();
        this.storageNodes = new LinkedList<BackupNode>();
        this.backups = new LinkedList<BackupFile>();

        updateNodes();
    }
    
    // current available nodes
    public LinkedList<BackupNode> getCurrentAvailableNodes(){
        updateNodes();
        return this.currentAvailableNodes;
    }
    public int getCurrentAvailableNodesNumber(){
        updateNodes();
        return this.currentAvailableNodes.size();
    }
    private void updateNodes(){
        //todo: update the all available nodes in the network
    }

    // storage nodes
    public LinkedList<BackupNode> getStorageNodes(){
        return this.currentAvailableNodes;
    }
    public int getStorageNodesNumber(){
        return this.storageNodes.size();
    }

    // available storage nodes and storage match search
    public LinkedList<BackupNode> getAvailableStorageNodes(){
        updateNodes();
        return this.currentAvailableStorageNodes;
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
    public byte[] getBackupById(int backupId){
        BackupFile matchingBackup = null;

        int a = 0;
        boolean exit = false;
        while(!exit){
            if(this.backups.get(a).getId() == backupId){
                exit = true;
                matchingBackup = this.backups.get(a);
            }
            a++;
        }

        byte[] retrivedBackupData = new byte[matchingBackup.getSize()];
        for(int b = 0, c = 0; b < matchingBackup.getSize(); b++){
            retrivedBackupData[b] = matchingBackup.
        }

    }
    private byte[] getFragmentFromStorageNode(int backupId, int fragmentId){
        updateNodes();
        byte[] retrivedFragment = null;

        int a = 0;
        boolean exit = false;
        while(!exit){
            if(this.backups.get(a).getId() == backupId){

            }
            a++;
        }

        return retrivedFragment;
    }
}
