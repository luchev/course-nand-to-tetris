import java.util.HashMap;

class CommandGt extends Command {
    private String filename;
    private static HashMap<String, Integer> labels = new HashMap<>();

    CommandGt(String command, String filename) {
        super(command);
        this.filename = filename;
    }

    /**
     * Subtract the 2 numbers
     * If the result > 0 jump to label_gt_mid which sets the value to -1
     *  set D to 0
     *  set SP - 1 to D
     *  add label_gt
     * If the result <= 0 continue to set value to 0 and then jump to the end label label_gt
     *  set A to 0
     *  set D to A-1 = -1
     *  set SP - 1 to D
     *  jump label_gt
     */
    @Override
    String parse() {
        // Generate the label for this comparison
        if (!labels.containsKey(filename)) {
            labels.put(filename, 0);
        }
        labels.put(filename, labels.get(filename) + 1);
        String label = filename + "_" + labels.get(filename).toString();

        String a = "@SP\nM=M-1\nA=M\nD=M\n@SP\nA=M-1\nD=D-M\n";
        String b = "@" + label + "_gt_mid" + "\nD;JGE\n@0\nD=A-1\n@SP\nA=M-1\nM=D\n@" + label + "_gt\n" + "0;JMP\n";
        String c = "(" + label + "_gt_mid)\n@0\nD=A\n@SP\nA=M-1\nM=D\n(" + label + "_gt" + ")\n";
        return getCommandAsComment() + a + b + c;
    }
}
