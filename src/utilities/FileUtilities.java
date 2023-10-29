package utilities;

import DataTypes.FragmentedFileData;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class FileUtilities {

    // FILE PARSING
    public static byte[] parseFile(String filePath) throws Exception {
        return new FileInputStream(new File(filePath)).readAllBytes();
    }

    // FILE FRAGMENTATION
    public static FragmentedFileData fragmentFile(byte[] parsedFile, int fragmentNumber){
        // find the greatest comune divisor (will be the fragment number)
        int fragmentParityBytes = getFragmentParityBytes(parsedFile.length, fragmentNumber);
        int fragmentLength = (int) Math.ceil((double) parsedFile.length / fragmentNumber);
        int parsedFileChunkLength = (int) Math.floor((double) parsedFile.length / fragmentNumber);

        byte[][] fragmentedFile = new byte[fragmentNumber][fragmentLength + 1];
        byte[] currentFragment = new byte[fragmentLength + 1];
        for(int y = 0, start = 0, end = parsedFileChunkLength; y < fragmentedFile.length; y++){
            // segment id
            currentFragment[0] = (byte) y;
            // segment body & parity byte
            if(y <= fragmentParityBytes){
                System.arraycopy(Arrays.copyOfRange(parsedFile, start, end), 0, currentFragment, 1, fragmentLength - 2);
                end++;
            } else {
                System.arraycopy(Arrays.copyOfRange(parsedFile, start, end), 0, currentFragment, 1, fragmentLength - 3);
            }

            fragmentedFile[y] = currentFragment.clone();

            System.out.println("-------");
            System.out.println(Arrays.toString(fragmentedFile[y]));

            start = end + 1;
            end += parsedFileChunkLength;
        }

        return new FragmentedFileData(fragmentedFile, fragmentParityBytes);
    }

    // FILE RECOVER
    public static void recoverFile(FragmentedFileData fragmentedFileBytes, String filePath) throws IOException {
        int fileSize = fragmentedFileBytes.getFileFragments().length * (fragmentedFileBytes.getFileFragments()[0].length - 2) + fragmentedFileBytes.getParityBytes();
        int parsedFileChunkLength = (int) Math.ceil((double) fileSize / fragmentedFileBytes.getFileFragments().length) + 1;

        byte[] fileBytes = new byte[fileSize];
        for(int y = 0, start = 0; y < fragmentedFileBytes.getFileFragments().length; y++){
            if(y < fragmentedFileBytes.getParityBytes()){
                System.arraycopy(fragmentedFileBytes.getFileFragments()[y], 1, fileBytes, start, parsedFileChunkLength - 1);
                start += parsedFileChunkLength - 1;
            } else {
                System.arraycopy(fragmentedFileBytes.getFileFragments()[y], 1, fileBytes, start, parsedFileChunkLength - 2);
                start += parsedFileChunkLength - 2;
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
