import java.util.HashMap;

class CommandGt extends Command {
    private String filename;
    private static HashMap<String, Integer> labels = new HashMap<>();
    CommandGt(String command, String filename) {
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

        String b = "@" + label + "_gt_mid" + "\nD;JGE\n@0\nD=A\nD=D-1\n@SP\nA=M\nM=D\n@SP\nM=M+1\n@" + label + "_gt\n" + "0;JMP\n";
        String c = "(" + label + "_gt_mid)\n@0\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n(" + label + "_gt" + ")\n";
        return getCommandAsComment() + a + b + c;
    }
}
