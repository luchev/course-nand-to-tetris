import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

class File {
    static ArrayList<String> readLines(String filename) {
        try {
            return new ArrayList<>(Files.readAllLines(Paths.get(filename)));
        } catch (IOException e) {
            System.out.println("Failed to read " + filename);
            return new ArrayList<>();
        }
    }

    static String readToString(String filename) {
        StringBuilder builder = new StringBuilder();
        try(Stream<String> lines = Files.lines(Paths.get(filename), StandardCharsets.UTF_8)) {
            lines.forEach(line -> builder.append(line).append("\n"));
            return builder.toString();
        } catch (IOException e) {
            System.out.println("Failed to read " + filename);
            return "";
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
