import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class File {
    static ArrayList<String> readLines(String filename) {
        try {
            return new ArrayList<>(Files.readAllLines(Paths.get(filename)));
        } catch (IOException e) {
            System.out.println("Failed to read " + filename);
            return new ArrayList<>();
        }
    }

    static void writeString(String filename, String contents) {
        try {
            Files.write(Paths.get(filename), contents.getBytes());
        } catch (IOException e) {
            System.out.println("Failed to write " + filename);
        }
    }
}
