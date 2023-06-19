import java.io.FileInputStream;
import java.io.InputStream;

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

    public String[] backup() throws Exception {
        String fragmentedFile[] = new String[this.fragmentsSize.length];
        String fileString = readFile(this.clientPath);

        fragmentedFile = fragmentFileEqualy(fileString);

        return fragmentedFile;
    }

    private String[] fragmentFileEqualy(String stringFile) {
        int chunkLength = stringFile.length() / fragmentsSize.length;
        String splittedFile[] = new String[this.fragmentsSize.length];
        for(int a = 0; a < this.fragmentsSize.length; a++){
            this.fragmentsSize[a] = chunkLength;
        }

        int fileIndex = 0;
        for(int a = 0; a < this.fragmentsSize.length; a ++){
            splittedFile[a] = stringFile.substring(fileIndex, fileIndex + chunkLength-1);
            fileIndex += chunkLength;
        }

        return splittedFile;
    }
    private String readFile(String filePath) throws Exception {
        InputStream sourceFile = new FileInputStream(filePath);
        byte byteFile[] = new byte[sourceFile.available()];
        char charFile[] = new char[sourceFile.available()];
        String stringFile;

        sourceFile.read(byteFile);
        for(int a = 0; a < byteFile.length; a++){
            charFile[a] = (char)byteFile[a];
        }

        stringFile = new String(charFile);
        return stringFile;
    }
}
