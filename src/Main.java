import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        System.out.println("BackupWave  file sync and backup\n");

        // "/Users/marcobertagnolli/Desktop/red_hat_enterprise_linux-7-system_administrators_guide-en-us.pdf"
        // "/Users/marcobertagnolli/Desktop/try.txt"
        BackupFile prova1 = new BackupFile(0, "/Users/marcobertagnolli/Desktop/try.txt", 5);
        try {
            prova1.backup();
        } catch(Exception e) {
            System.out.println("file not found: " + e.getMessage());
        }
    }
}