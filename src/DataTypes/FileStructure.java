package DataTypes;

import java.util.ArrayList;

public class FileStructure<T> {
    private final String path; // directory / file path
    private byte[][] fragments = null;
    private int fragmentParityBytes;
    private FileStructure<T> parent;
    private final ArrayList<FileStructure<T>> children;

    // directory node constructor
    public FileStructure(String path){
        this.path = path;
        this.fragments = new byte[0][0];
        this.fragmentParityBytes = 0;
        children = new ArrayList<>();
    }
    // file node constructure
    public FileStructure(String path, FragmentedFileData fragmentedFileData){
        this.path = path;
        this.fragmentParityBytes = fragmentedFileData.getParityBytes();
        this.fragments = fragmentedFileData.getFileFragments().clone();
        children = new ArrayList<>();
    }
    // directory adder
    public FileStructure<T> addChild(String child){
        FileStructure<T> childNode = new FileStructure<>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    // file adder
    public FileStructure<T> addChild(String child, FragmentedFileData fragmentedFileData){
        FileStructure<T> childNode = new FileStructure<>(child, fragmentedFileData);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    public FileStructure<T> addChild(FileStructure<T> child){
        FileStructure<T> childNode = child;
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    public FileStructure<T> getChildren(int x){
        return children.get(x);
    }
    public String getPath(){
        return this.path;
    }
    public String getName(){
        return this.path.split("/")[this.path.split("/").length - 1];
    }
    public int getFragmentParityBytes(){
        return fragmentParityBytes;
    }
    public byte[][] getFragments(){
        return this.fragments.clone();
    }
    public boolean isDirectory(){
        if(this.fragments.length == 0){
            return true;
        } else {
            return false;
        }
    }
    public boolean isRoot(){
        return parent == null;
    }
    public boolean isLeaf(){
        return children.size() == 0;
    }
    public int getChildrenSize(){
        return children.size();
    }
    public int getLevel(){
        if(this.isRoot()){
            return 0;
        } else {
            return parent.getLevel() + 1;
        }
    }
    public String toString(){
        return path != null ? path.toString() : "null";
    }
    public String treeToJson(){
        // todo: return a proper json string
        String prevTree = "", childrenJson = "";

        if(parent == null){
            for(int x = 0; x < this.children.size(); x++){
                if(!(fragments.length == 0)){
                    childrenJson += ((x == 0) ? "" : ", ") + path;
                } else {
                    System.out.println(this.children.get(x).treeToJson());
                    childrenJson += ((x == 0) ? "" : ", ") + this.children.get(x).treeToJson();
                }
            }
            prevTree += "{" + childrenJson + "}";
        } else {
            for(int x = 0; x < this.children.size(); x++){
                if(!(fragments.length == 0)){
                    childrenJson += ((x == 0) ? "" : ", ") + path;
                } else {
                    childrenJson += ((x == 0) ? "" : ", ") + this.children.get(x).treeToJson();
                }
            }
            prevTree += "[" + childrenJson + "]";
        }

        return prevTree;
    }

}
