package utilities;

import java.io.*;

public class FileUtilities {

    // FILE PARSING
    public static byte[] parseFile(String filePath) throws Exception {
        return new FileInputStream(new File(filePath)).readAllBytes();
    }

    // FILE FRAGMENTATION
    public static byte[][] fragmentFile(byte[] parsedFile, int fragmentNumber){
        // find the greatest comune divisor (will be the fragment number)
        int fragmentParityBytes = getFragmentParityBytes(parsedFile.length, fragmentNumber);

        byte[][] fragmentedFile = new byte[fragmentNumber][(int) Math.ceil((double) parsedFile.length / fragmentNumber) + 1];
        for(int y = 0, x2 = 0; y < fragmentedFile.length; y++){
            for(int x = 0; x < fragmentedFile[y].length; x++){
                // first byte of the fragment contains the position tag
                if(x == 0){
                    fragmentedFile[y][x] = (byte) y;
                } else if(x == 1 && fragmentParityBytes <= y){
                    fragmentedFile[y][x] = 0; // null
                } else {
                    fragmentedFile[y][x] = parsedFile[x2];
                    x2++;
                }
            }
        }

        return fragmentedFile;
    }

    // FILE RECOVER
    public static void recoverFile(byte[][] fragmentedFileBytes, String filePath) throws IOException {
        int fileSize = 0;

        for(int y = 0; y < fragmentedFileBytes.length; y++){
            for(int x = 0; x < fragmentedFileBytes[y].length; x++){
                if(!(fragmentedFileBytes[y][x] == 0 && x == 1) && x != 0){
                    fileSize++;
                }
            }
        }

        byte[] fileBytes = new byte[fileSize];
        for(int y = 0, x2 = 0; y < fragmentedFileBytes.length; y++){
            for(int x = 0; x < fragmentedFileBytes[y].length; x++){
                if(!(fragmentedFileBytes[y][x] == 0 && x == 1) && x != 0){
                    fileBytes[x2] = fragmentedFileBytes[y][x];
                    x2++;
                }
            }
        }

        File newFile = new File(filePath);
        newFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        fileOutputStream.write(fileBytes);
        fileOutputStream.close();
    }

    // FRAGMENT PARITY BYTES
    public static int getFragmentParityBytes(int parsedFileLength, int fragmentNumber){
        return parsedFileLength - ((int) Math.floor(parsedFileLength / fragmentNumber) * fragmentNumber);
    }
}
