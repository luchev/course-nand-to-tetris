import java.util.ArrayList;

class Parser {
    // Used for static variables and labeling
    private String inputFileName;

    void parseFile(String inputFile) {
        System.out.println("Processing file " + inputFile);
        this.inputFileName = inputFile.replaceAll(".vm$", "").substring(inputFile.lastIndexOf("/") + 1).trim();
        String outputFile = inputFile.replaceAll(".vm$", ".asm");

        ArrayList<String> lines = File.readLines(inputFile);
        ArrayList<Command> commands = parseLinesToCommands(lines);

        StringBuilder output = new StringBuilder();
        for (Command i : commands) {
            output.append(i.parse());
        }

        File.writeString(outputFile, output.toString());
        System.out.println("Saving output file " + outputFile);
    }

    private ArrayList<Command> parseLinesToCommands(ArrayList<String> lines) {
        ArrayList<Command> commands = new ArrayList<>();

        for (String line : lines) {
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
            }
        }

        return commands;
    }
}
