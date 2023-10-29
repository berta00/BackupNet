package DataTypes;

public class FragmentedFileData {
    byte[][] fileFragments;
    int parityBytes;

    public FragmentedFileData(byte[][]fileFragments, int parityBytes){
        this.fileFragments = fileFragments;
        this.parityBytes = parityBytes;
    }
    public byte[][] getFileFragments(){
        return this.fileFragments;
    }
    public void setFileFragments(byte[][] fileFragments) {
        this.fileFragments = fileFragments;
    }
    public int getParityBytes(){
        return this.parityBytes;
    }
    public void setParityBytes(){
        this.parityBytes = parityBytes;
    }
}
