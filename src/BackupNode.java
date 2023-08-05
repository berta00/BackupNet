import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.LinkedList;

public class BackupNode {
    private int id;
    private String version;
    private InetAddress address;
    private int port;
    private String hostname;
    private String macAddress;
    private int storageCapacity;

    // need to get a ceck
    private int[] backupId; // parallel array
    private int[] backupFragment; // parallel array

    public BackupNode(int id, String version, InetAddress address, int port, String hostname, String macAddress){
        this.id = id;
        this.version = version;
        this.address = address;
        this.port = port;
        this.hostname = hostname;
        this.macAddress = macAddress;

        // need to get a ceck
        this.storageCapacity = 0;
        this.backupId = new int[100];
        this.backupFragment = new int[100];
    }

    public int getId(){return this.id;}
    public InetAddress getAddress(){return this.address;}
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
        String portStatus = "available";
        try(ServerSocket testSocket = new ServerSocket(this.port);){} catch (IOException e){
            portStatus = "already in use";
        }

        return "" +
                "version:  " + this.version                         + "\n" +
                "ip:       " + this.address.getHostAddress()        + "\n" +
                "port:     " + this.port       + " - " + portStatus + "\n" +
                "mac:      " + this.macAddress                      + "\n" +
                "hostname: " + this.hostname                        + "\n" +
                "";
    }

    public BackupNode clone(){
        BackupNode copy = new BackupNode(this.id, this.version, this.address, this.port, this.hostname, this.macAddress);
        copy.storageCapacity = this.storageCapacity;
        copy.backupId = this.backupId.clone();
        return copy;
    }
}
