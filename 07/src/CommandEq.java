import java.util.HashMap;

class CommandEq extends Command {
    private String filename;
    private static HashMap<String, Integer> labels = new HashMap<>();
    CommandEq(String command, String filename) {
        super(command);
        this.filename = filename;
    }

    @Override
    String parse() {
        String a = "@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M\nD=D-M\n";
        if (!labels.containsKey(filename)) {
            labels.put(filename, 0);
        }
        labels.put(filename, labels.get(filename) + 1);
        String label = filename + "_" + labels.get(filename).toString();

        String b = "@" + label + "_eq_mid" + "\nD;JNE\n@0\nD=A\nD=D-1\n@SP\nA=M\nM=D\n@SP\nM=M+1\n@" + label + "_eq\n" + "0;JMP\n";
        String c = "(" + label + "_eq_mid)\n@0\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n(" + label + "_eq" + ")\n";
        return getCommandAsComment() + a + b + c;
    }
}
