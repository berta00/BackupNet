import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        System.out.println("BackupWave  file sync and backup\n");

        // "/Users/marcobertagnolli/Desktop/red_hat_enterprise_linux-7-system_administrators_guide-en-us.pdf"
        // "/Users/marcobertagnolli/Desktop/try.txt"
        BackupFile nodeManager1 = new BackupFile(0, 5, "/Users/marcobertagnolli/Desktop/red_hat_enterprise_linux-7-system_administrators_guide-en-us.pdf");
        try {
            byte[][] frammentedFile = nodeManager1.backup();
            for(int a = 0; a < frammentedFile.length; a++){
                String curStr = "";
                for(int b = 0; b < frammentedFile[0].length; b++){
                    curStr += (char)frammentedFile[a][b];
                }
                System.out.println("Frammento " + a + ": " + curStr);
            }

        } catch(Exception e) {
            System.out.println("file not found: " + e.getMessage());
        }
    }
}