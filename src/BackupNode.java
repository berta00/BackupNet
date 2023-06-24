public class BackupNode {
    private int id;
    private int[] ip;
    private String hostname;
    private byte[] macAddress;
    private int storageCapacity;
    private int[] backupId;

    public BackupNode(int id, int[] ip){
        this.id = id;
        this.ip = ip.clone();

        //todo: request to the host hostname, and check if port of backup service is available
        //todo: if backup service is available get storageCapacity and backupId
    }

    public int getId(){return this.id;}
    public int[] getIp(){return this.ip;}
    public String getHostname(){return this.hostname;}
    public int getStorageCapacity(){return this.storageCapacity;}
    public void setStorageCapacity(int storageCapacity){this.storageCapacity = storageCapacity;}

}
