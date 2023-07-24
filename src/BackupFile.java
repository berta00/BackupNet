import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class BackupFile {
    private int id;
    private int size;
    private String clientPath;
    private int[] fragmentsSize; // parallel array 1
    private BackupNode[] fragmentsBakupNodes; // parallel array 2

    public BackupFile(int id, int fragmentsNumber, String clientPath){
        this.id = id;
        this.clientPath = clientPath;
        this.fragmentsSize = new int[fragmentsNumber];
        this.fragmentsBakupNodes = new BackupNode[fragmentsNumber];
    }

    public int getId(){
        return this.id;
    }
    public int getSize(){
        return this.size;
    }
    public String getClientPath(){
        return this.clientPath;
    }
    public void setClientPath(String clientPath){
        this.clientPath = clientPath;
    }
    public int[] getFragmentsSize(){
        return this.fragmentsSize.clone();
    }
    public void setFragmentsSize(int[] fragmentsSize){ // attention: parameter must have the same length of the attribute
        for(int a = 0; a < fragmentsSize.length; a++){
            this.fragmentsSize[a] = fragmentsSize[a];
        }
    }
    public BackupNode[] getFragmentBabkupNodes(){
        BackupNode[] copy = new BackupNode[this.fragmentsBakupNodes.length];
        for(int a = 0; a < copy.length; a++){
            copy[a] = this.fragmentsBakupNodes[a].clone();
        }
        return copy;
    }
    public BackupNode getFragmentBackupNodeByIndex(int index){
        return this.fragmentsBakupNodes[index].clone();
    }
    public void setFragmentsBabkupNodesByIndex(int index, BackupNode node){
        this.fragmentsBakupNodes[index] = node.clone();
    }
    public BackupNode getFragmentBackupNodeById(int id){
        int a = 0;
        boolean exit = false;
        while(!exit){
            if(a >= this.fragmentsBakupNodes.length){
                exit = true;
            } else if(this.fragmentsBakupNodes[a].getId() == id){
                return this.fragmentsBakupNodes[a];
            }
            a++;
        }
        return null;
    }

    // backup and backup utilities
    public byte[][] backup() throws Exception {
        byte fragmentedFile[][] = fragmentFileEqualy(readFile(this.clientPath));

        return fragmentedFile;
    }

    private byte[][] fragmentFileEqualy(byte[] byteFile) {
        int chunkLength = byteFile.length / fragmentsSize.length;
        byte splittedFile[][] = new byte[this.fragmentsSize.length][chunkLength];
        for(int a = 0; a < this.fragmentsSize.length; a++){
            this.fragmentsSize[a] = chunkLength;
        }

        int fileIndex = 0;
        for(int a = 0; a < this.fragmentsSize.length; a ++){
            splittedFile[a] = Arrays.copyOfRange(byteFile, fileIndex, fileIndex + chunkLength-1);
            fileIndex += chunkLength;
        }

        return splittedFile;
    }

    private byte[] readFile(String filePath) throws Exception {
        InputStream sourceFile = new FileInputStream(filePath);
        byte byteFile[] = new byte[sourceFile.available()];

        sourceFile.read(byteFile);

        return byteFile;
    }
}
