package utilities;

import java.io.File;

public class PathUtilities {
    // FILE-PATH RECONSTRUCT
    public static String reconstructFilePathWithTag(String filePath, String tag) throws Exception {
        String fileName = "";
        String fileFormat = "";

        if(!new File(filePath).isDirectory()){
            for(int x = 0; x < filePath.length(); x++){
                if(fileFormat.equals("")){
                    if(filePath.charAt(x) == '.'){
                        fileName += "-" + tag;
                        fileFormat = ".";
                    } else {
                        fileName += filePath.charAt(x);
                    }
                } else {
                    fileFormat += filePath.charAt(x);
                }
            }
        } else {
            throw new Exception("Cannot reconstruct directory paths with reconstructFilePathWithTag(), use reconstructDirectoryPathWithTag() instead");
        }

        return fileName + fileFormat;
    }

    // DIRECTORY PATH RECONSTRUCT
    public static String reconstructDirectoryPathWithTag(String directoryPath, String tag) throws Exception{
        String directoryName = "";

        if(new File(directoryPath).isDirectory()){
            if(directoryPath.charAt(directoryPath.length() - 1) == '/' || directoryPath.charAt(directoryPath.length() - 1) == '\\'){
                directoryName = directoryPath.substring(0, directoryPath.length() - 1) + "-" + tag + directoryPath.charAt(directoryName.length() - 1);
            } else {
                directoryName = directoryPath + "-" + tag;
            }
        } else {
            throw new Exception("Cannot reconstruct file paths with reconstructDirectoryPathWithTag(), use reconstructFilePathWithTag() instead");
        }

        return directoryName;
    }
}
