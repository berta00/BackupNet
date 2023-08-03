import java.util.LinkedList;

public class BackupNode {
    private int id;
    private String ip;
    private String hostname;
    private String macAddress;
    private int storageCapacity;

    // need to get a ceck
    private int[] backupId; // parallel array
    private int[] backupFragment; // parallel array

    public BackupNode(int id, String ip, String hostname, String macAddress){
        this.id = id;
        this.ip = ip;
        this.hostname = hostname;
        this.macAddress = macAddress;

        // need to get a ceck
        this.storageCapacity = 0;
        this.backupId = new int[100];
        this.backupFragment = new int[100];
    }

    public int getId(){return this.id;}
    public String getIp(){return this.ip;}
    public String getHostname(){return this.hostname;}
    public String getMacAddress(){return this.macAddress;}
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

    public String toPrintableFormat(){
        return "" +
                "ip:       " + this.ip         + "\n" +
                "mac:      " + this.macAddress + "\n" +
                "hostname: " + this.hostname   + "\n" +
                "";
    }

    public BackupNode clone(){
        BackupNode copy = new BackupNode(this.id, this.ip, this.hostname, this.macAddress);
        copy.storageCapacity = this.storageCapacity;
        copy.backupId = this.backupId.clone();
        return copy;
    }
}
