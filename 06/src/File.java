import java.io.*;
import java.util.ArrayList;

class File {
    static ArrayList<String> readFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            ArrayList<String> lines = new ArrayList<>();
            String line;

            while((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();

            return lines;

        } catch (IOException e) {
            System.out.println("Cannot read file " + filename);
            return new ArrayList<>();
        }
    }

    static void writeFile(String filename, String contents) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(contents);
            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot write file " + filename);
        }
    }
}
