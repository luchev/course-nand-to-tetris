import java.util.HashMap;

class CommandEq extends Command {
    private String filename;
    private static HashMap<String, Integer> labels = new HashMap<>();

    CommandEq(String command, String filename) {
        super(command);
        this.filename = filename;
    }

    /**
     * Subtract the 2 numbers
     * If the result == 0 jump to label_eq_mid which sets the value to -1
     *  set D to 0
     *  set SP - 1 to D
     *  add label_eq
     * If the result != 0 continue to set value to 0 and then jump to the end label label_eq
     *  set A to 0
     *  set D to A-1 = -1
     *  set SP - 1 to D
     *  jump label_eq
     */
    @Override
    String parse() {
        // Generate the label for this comparison
        if (!labels.containsKey(filename)) {
            labels.put(filename, 0);
        }
        labels.put(filename, labels.get(filename) + 1);
        String label = filename + "_" + labels.get(filename).toString();

        return getCommandAsComment() + "@SP\nM=M-1\nA=M\nD=M\n@SP\nA=M-1\nD=D-M\n" +
                "@" + label + "_eq_mid" + "\nD;JNE\n@0\nD=A-1\n@SP\nA=M-1\nM=D\n@" + label + "_eq\n" + "0;JMP\n" +
                "(" + label + "_eq_mid)\n@0\nD=A\n@SP\nA=M-1\nM=D\n(" + label + "_eq" + ")\n";
    }
}
