import java.util.HashMap;

abstract class Command {
    private String command = "";
    String[] commandElements = null;

    static HashMap<String, String> segments = null;

    Command(String command) {
        setCommand(command);
        splitCommand();

        if (segments == null) {
            initializeSegments();
        }
    }

    static private void initializeSegments() {
        segments = new HashMap<>();
        segments.put("local", "LCL");
        segments.put("argument", "ARG");
        segments.put("this", "THIS");
        segments.put("that", "THAT");
        segments.put("temp", "5");
    }

    abstract String parse();

    private void splitCommand() {
        commandElements = command.split(" ");
    }

    String getCommand() {
        return command;
    }

    String getCommandAsComment() {
        return "// " + command + "\n";
    }

    private void setCommand(String command) {
        this.command = command;
    }

    String getArg(int argument) {
        return commandElements[argument];
    }
}
