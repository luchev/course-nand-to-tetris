import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: assembler input.asm output.hack");
            return;
        }

        ArrayList<String> fileContents = File.readFile(args[0]);
        if (fileContents.isEmpty()) {
            System.out.println("Nothing to do for " + args[0]);
        } else {
            Assembler assembler = new Assembler(fileContents);
            ArrayList<String> binary = assembler.parse();

            StringBuilder output = new StringBuilder();
            for (String line : binary) {
                output.append(line);
                output.append("\n");
            }
            File.writeFile(args[1], output.toString());

        }
    }
}
