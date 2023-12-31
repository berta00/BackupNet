package utilities;

import DataTypes.FragmentedFileData;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class FileUtilities {

    // FILE PARSING AND PARSING - FRAGMENTATION
    public static byte[] parseFile(File file, boolean debug) throws Exception {
        if(debug){
            System.out.print("[ ] parsing file:         " + file.getName());
        }

        byte[] parsedFile = new FileInputStream(file).readAllBytes();

        if(debug){
            System.out.print("\r" + "[*] parsing file:      " + file.getName() + "\n");
        }

        return parsedFile;
    }
    public static FragmentedFileData parseAndFragmentFile(File file, int fragmentNumber, boolean debug) throws Exception {
        if(debug){
            System.out.print("[ ] parsing file:     " + file.getName());
        }

        byte[] parsedFile = new FileInputStream(file).readAllBytes();

        if(debug){
            System.out.print("\r" + "[*] parsing file:      " + file.getName() + "\n");
        }

        return fragmentFile(parsedFile, fragmentNumber, debug, file.getName());
    }

    // FILE FRAGMENTATION
    public static FragmentedFileData fragmentFile(byte[] parsedFile, int fragmentNumber, boolean debug, String fileName){
        if(debug){
            System.out.print("[ ] fragmenting file: " + fileName);
        }

        // find the greatest comune divisor (will be the fragment number)
        int fragmentParityBytes = getFragmentParityBytes(parsedFile.length, fragmentNumber);
        int fragmentLength = (int) Math.ceil((double) parsedFile.length / fragmentNumber) + 1;
        int parsedFileChunkLength = (int) Math.floor((double) parsedFile.length / fragmentNumber);

        byte[][] fragmentedFile = new byte[fragmentNumber][fragmentLength];
        byte[] currentFragment = new byte[fragmentLength];
        for(int y = 0, start = 0, end = parsedFileChunkLength; y < fragmentedFile.length; y++){
            // segment id
            currentFragment[0] = (byte) y;
            // segment body & parity byte
            if(y < fragmentParityBytes){
                System.arraycopy(Arrays.copyOfRange(parsedFile, start, parsedFile.length), 0, currentFragment, 1, fragmentLength - 1);

                start += parsedFileChunkLength + 1;
            } else {
                System.arraycopy(Arrays.copyOfRange(parsedFile, start, parsedFile.length), 0, currentFragment, 1, fragmentLength - 2);

                start += parsedFileChunkLength;
            }

            fragmentedFile[y] = currentFragment.clone();
        }

        if(debug){
            System.out.print("\r" + "[*] fragmenting file:  " + fileName + "\n");
        }

        return new FragmentedFileData(fragmentedFile, fragmentParityBytes);
    }
    
    // FILE RECOVER
    public static void recoverFile(FragmentedFileData fragmentedFileBytes, String filePath, String destinationPath, boolean debug) throws Exception {
        if(debug){
            System.out.print("[ ] recovering file:  " + filePath.split("/")[filePath.split("/").length - 1]);
        }

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

        File newFile = new File(destinationPath);

        if(newFile.createNewFile()){
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            fileOutputStream.write(fileBytes);
            fileOutputStream.close();
        } else {
            throw new Exception("Error during the file recovery phase, a file was already existing");
        }

        if(debug){
            System.out.print("\r" + "[*] recovering file:   " + filePath.split("/")[filePath.split("/").length - 1] + "\n");
        }
    }

    // FRAGMENT PARITY BYTES
    public static int getFragmentParityBytes(int parsedFileLength, int fragmentNumber){
        return parsedFileLength - ((int) Math.floor(parsedFileLength / fragmentNumber) * fragmentNumber);
    }
}
