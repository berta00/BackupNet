import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

class DebugTest {

    @org.junit.jupiter.api.Test
    void fileFragmentation() throws Exception {
        // file1.txt
        System.out.println("\n\n");
        Debug.fileFragmentation("test/fragmentation/files/file1.txt");
        FileInputStream file_1_1_in_str = new FileInputStream(new File("test/fragmentation/files/file1.txt"));
        FileInputStream file_1_1_2_in_str = new FileInputStream(new File("test/fragmentation/files/file1-recovered.txt"));

        assertArrayEquals(file_1_1_in_str.readAllBytes(), file_1_1_2_in_str.readAllBytes());

        // Dell-PowerEdge-R620-Spec-Sheet.pdf
        System.out.println("\n\n");
        Debug.fileFragmentation("test/fragmentation/files/Dell-PowerEdge-R620-Spec-Sheet.pdf");
        FileInputStream file_2_1_in_str = new FileInputStream(new File("test/fragmentation/files/Dell-PowerEdge-R620-Spec-Sheet.pdf"));
        FileInputStream file_2_2_in_str = new FileInputStream(new File("test/fragmentation/files/Dell-PowerEdge-R620-Spec-Sheet-recovered.pdf"));

        assertArrayEquals(file_2_1_in_str.readAllBytes(), file_2_2_in_str.readAllBytes());

        // ultraWide4.jpg
        System.out.println("\n\n");
        Debug.fileFragmentation("test/fragmentation/files/ultraWide4.jpg");
        FileInputStream file_3_1_in_str = new FileInputStream(new File("test/fragmentation/files/ultraWide4.jpg"));
        FileInputStream file_3_2_in_str = new FileInputStream(new File("test/fragmentation/files/ultraWide4-recovered.jpg"));

        assertArrayEquals(file_3_1_in_str.readAllBytes(), file_3_2_in_str.readAllBytes());

        // jwt-handbook-v0_14_1.pdf
        System.out.println("\n\n");
        Debug.fileFragmentation("test/fragmentation/files/jwt-handbook-v0_14_1.pdf");
        FileInputStream file_4_1_in_str = new FileInputStream(new File("test/fragmentation/files/jwt-handbook-v0_14_1.pdf"));
        FileInputStream file_4_2_in_str = new FileInputStream(new File("test/fragmentation/files/jwt-handbook-v0_14_1-recovered.pdf"));

        assertArrayEquals(file_4_1_in_str.readAllBytes(), file_4_2_in_str.readAllBytes());

/*
        // IMG_0070.MOV (too mutch)
        System.out.println("\n\n");
        Debug.fileFragmentation("test/fragmentation/files/IMG_0070.MOV");
        FileInputStream file_3_1_in_str = new FileInputStream(new File("test/fragmentation/files/IMG_0070.MOV"));
        FileInputStream file_3_2_in_str = new FileInputStream(new File("test/fragmentation/files/IMG_0070-recovered.MOV"));

        assertArrayEquals(file_3_1_in_str.readAllBytes(), file_3_2_in_str.readAllBytes());
*/
    }
}