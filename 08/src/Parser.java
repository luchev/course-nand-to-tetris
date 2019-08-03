import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Parser {
    // Used for static variables and labeling
    private String inputFileName;

    /**
     * Parse a single file WITHOUT bootstrapping
     *
     * @param inputFile - file name
     */
    private void parseFile(String inputFile) {
        System.out.println("Processing file " + inputFile);
        this.inputFileName = Paths.get(inputFile).getFileName().toString().replaceAll(".vm$", "").trim();
        String outputFile = inputFile.replaceAll(".vm$", ".asm");

        ArrayList<String> lines = File.readLines(inputFile);
        ArrayList<Command> commands = parseLinesToCommands(lines);

        String parsed = parseCommandsToString(commands);

        System.out.println("Saving output file " + outputFile);
        File.writeString(outputFile, parsed);
    }

    /**
     * Parse all files in a directory WITH bootstrapping
     *
     * @param inputFile - directory name
     */
    private void parseDir(String inputFile) {
        System.out.println("Processing directory " + inputFile);

        Path directory = Paths.get(inputFile);
        this.inputFileName = directory.getFileName().toString();
        String outputFile = inputFile + "/" + inputFileName + ".asm";
        String asm = initBootstrap() + parseFilesInDirectory(directory);

        System.out.println("Saving file " + outputFile);
        File.writeString(outputFile, asm);
    }

    private String initBootstrap() {
        return "@256\nD=A\n@0\nM=D\n" + new CommandCall("call Sys.init 0").parse();
    }

    private String parseFilesInDirectory(Path directory) {
        StringBuilder output = new StringBuilder();

        try (Stream<Path> walk = Files.walk(directory)) {
            ArrayList<String> files = walk.filter(Files::isRegularFile).map(Path::toString)
                    .filter(file -> file.endsWith(".vm")).collect(Collectors.toCollection(ArrayList::new));

            for (String file : files) {
                parseFile(file);
                output.append(File.readToString(file.replaceAll(".vm$", ".asm")));
            }

        } catch (IOException e) {
            System.out.println("Can't process directory " + directory.toString());
        }

        return output.toString();
    }

    void parse(String inputFile) {
        if (Files.isDirectory(Paths.get(inputFile))) {
            parseDir(inputFile);
        } else {
            parseFile(inputFile);
        }
    }

    String parseString(String input) {
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(input.split("\n")));
        ArrayList<Command> commands = parseLinesToCommands(lines);

        return parseCommandsToString(commands);
    }

    private String parseCommandsToString(ArrayList<Command> commands) {
        StringBuilder output = new StringBuilder();
        for (Command i : commands) {
            output.append(i.parse());
        }
        return output.toString();
    }

    private ArrayList<Command> parseLinesToCommands(ArrayList<String> lines) {
        ArrayList<Command> commands = new ArrayList<>();
        for (String line : lines) {
            // Remove comments and trim to leave only the command
            line = line.replaceAll("//.*$", "").trim();
            if (line.startsWith("pop")) {
                commands.add(new CommandPop(line, inputFileName));
            } else if (line.startsWith("push")) {
                commands.add(new CommandPush(line, inputFileName));
            } else if (line.startsWith("add")) {
                commands.add(new CommandAdd(line));
            } else if (line.startsWith("sub")) {
                commands.add(new CommandSub(line));
            } else if (line.startsWith("eq")) {
                commands.add(new CommandEq(line, inputFileName));
            } else if (line.startsWith("lt")) {
                commands.add(new CommandLt(line, inputFileName));
            } else if (line.startsWith("gt")) {
                commands.add(new CommandGt(line, inputFileName));
            } else if (line.startsWith("and")) {
                commands.add(new CommandAnd(line));
            } else if (line.startsWith("neg")) {
                commands.add(new CommandNeg(line));
            } else if (line.startsWith("or")) {
                commands.add(new CommandOr(line));
            } else if (line.startsWith("not")) {
                commands.add(new CommandNot(line));
            } else if (line.startsWith("label")) {
                commands.add(new CommandLabel(line));
            } else if (line.startsWith("goto")) {
                commands.add(new CommandGoto(line));
            } else if (line.startsWith("if-goto")) {
                commands.add(new CommandIfGoto(line));
            } else if (line.startsWith("function")) {
                commands.add(new CommandFunction(line));
            } else if (line.startsWith("return")) {
                commands.add(new CommandReturn(line));
            } else if (line.startsWith("call")) {
                commands.add(new CommandCall(line));
            }
        }

        return commands;
    }
}
