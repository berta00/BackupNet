import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class BackupFile {
    private int id;
    private int size;
    private String clientPath;
    private int[] fragmentsSize;
    private BackupNode[] fragmentsBabkupNode;

    public BackupFile(int id, String clientPath, int fragmentsNumber){
        this.id = id;
        this.clientPath = clientPath;
        this.fragmentsSize = new int[fragmentsNumber];
        this.fragmentsBabkupNode = new BackupNode[fragmentsNumber];
    }

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
