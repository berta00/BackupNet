package utilities;

public class PathUtilities {
    // FILE-PATH RECONSTRUCT
    public static String reconstructFilePathWithTag(String filePath, String tag){
        String fileName = "";
        String fileFormat = "";

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

        return fileName + fileFormat;
    }
}
