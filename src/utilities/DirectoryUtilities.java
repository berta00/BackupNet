package utilities;

import DataTypes.FileStructure;
import DataTypes.FragmentedFileData;

import java.io.File;

public class DirectoryUtilities {

    // DIRECTORY PARSING AND PARSING - FRAGMENTATION
    public static FileStructure<File> parseAndFragmentDirectory(File directory, int fragmentNumber, boolean debug) throws Exception {

        FileStructure<File> returnFileStructure = new FileStructure<>(directory.getAbsolutePath());

        File[] directoryContent = directory.listFiles();
        for(int x = 0; x < directoryContent.length; x++){
            if(directoryContent[x].isDirectory()){
                returnFileStructure.addChild(parseAndFragmentDirectory(directoryContent[x], fragmentNumber, debug));
            } else {
                returnFileStructure.addChild(directoryContent[x].getPath(), FileUtilities.parseAndFragmentFile(directoryContent[x], fragmentNumber, false));
            }
        }

        return returnFileStructure;
    }

    // DIRECTORY RECOVER
    public static void recoverDirectory(FileStructure<File> fileStructure, String filePath, String destinationPath, boolean debug) throws Exception {

        if(fileStructure.isRoot()){
            File newDirectory = new File(destinationPath);

            if(!newDirectory.mkdirs()){
                throw new Exception("Error during the directory recovery phase, the root directory already exist");
            }
        }

        for(int x = 0; x < fileStructure.getChildrenSize(); x++){
            if(fileStructure.getChildren(x).isDirectory()){
                File newDirectory = new File(destinationPath + "/" + fileStructure.getChildren(x).getName());
                if(newDirectory.mkdirs()){
                    recoverDirectory(fileStructure.getChildren(x), fileStructure.getChildren(x).getPath(), destinationPath + "/" + fileStructure.getChildren(x).getName(), debug);
                } else {
                    throw new Exception("Error during the directory recovery phase, a directory was already existing");
                }
            } else {
                FileUtilities.recoverFile(new FragmentedFileData(fileStructure.getChildren(x).getFragments(), fileStructure.getChildren(x).getFragmentParityBytes()), fileStructure.getChildren(x).getPath(), destinationPath + "/"  + fileStructure.getChildren(x).getName(), debug);
            }
        }

    }
}
