import java.util.LinkedList;

public class BackupNodeManager {
    private LinkedList<BackupNode> currentAvailableNodes; // nodes that are currently available
    private LinkedList<BackupNode> storageNodes; // nodes that has storage in them (even the inactive ones)

    public BackupNodeManager(){
        this.currentAvailableNodes = new LinkedList<BackupNode>();
        this.storageNodes = new LinkedList<BackupNode>();

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
        //todo: update the nodes available nodes in the network
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
        LinkedList<BackupNode> availableStorageNode = new LinkedList<BackupNode>();

        //todo: compare currenly available node and storage nodes

        return availableStorageNode;
    }
    public LinkedList<BackupNode> getAvailableStorageNodesByBackupId(int id){
        LinkedList<BackupNode> availableStorageNodesWithBackup = new LinkedList<BackupNode>();

        //todo: get all the nodes with a specific backup

        return availableStorageNodesWithBackup;
    }
}
