import java.util.LinkedList;

public class BackupNode {
    private int id;
    private int[] ip;
    private String hostname;
    private byte[] macAddress;
    private int storageCapacity;
    private int[] backupId; // parallel array
    private int[] backupFragment; // parallel array

    public BackupNode(int id, int[] ip){
        this.id = id;
        this.ip = ip.clone();
        this.backupId = new int[100];
        this.backupFragment = new int[100];

        //todo: request to the host hostname, and check if port of backup service is available
        //todo: if backup service is available get storageCapacity and backupId
    }

    public int getId(){return this.id;}
    public int[] getIp(){return this.ip.clone();}
    public String getHostname(){return this.hostname;}
    public int getStorageCapacity(){return this.storageCapacity;}
    public void setStorageCapacity(int storageCapacity){this.storageCapacity = storageCapacity;}
    public int getStoredBackupsNumber(){
        return this.backupId.length;
    }
    public int[] getStoredBackupId(){
        return this.backupId.clone();
    }
    public int getStoredBackupIdByIndex(int index){
        return this.backupId[index];
    }
    public int getStoredFragmentNumberByBackupId(int backupId){
        return this.backupFragment[backupId];
    }
    public byte[] getStoredFragmentByBackupIdAndFragmentId(int backupId, int fragmentId){
        int a = 0;
        boolean exit = false;
        while(!exit){
            if(this.backupId[a] == backupId && this.backupFragment[a] == fragmentId){
                return getFragmentFromHost(a);
            }
        }
        return null;
    }

    private byte[] getFragmentFromHost(int fragmentIndex){
        return null;
    }

    public BackupNode clone(){
        BackupNode copy = new BackupNode(this.id, this.ip.clone());
        copy.hostname = this.hostname;
        copy.macAddress = this.macAddress.clone();
        copy.storageCapacity = this.storageCapacity;
        copy.backupId = this.backupId.clone();

        return copy;
    }
}
