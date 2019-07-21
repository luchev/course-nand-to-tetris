import java.util.ArrayList;
import java.util.HashMap;

class Assembler {
    private ArrayList<String> lines;
    private ArrayList<Instruction> instructions;

    private HashMap<String, String> labels = new HashMap<>();

    private void initializeBuiltInLabels() {
        if (labels.isEmpty()) {
            // Add RX
            for (int i = 0; i <= 15; i++) {
                labels.put("R" + i, Integer.toString(i));
            }

            labels.put("SCREEN", "16384");
            labels.put("KBD", "24576");
            labels.put("SP", "0");
            labels.put("LCL", "1");
            labels.put("ARG", "2");
            labels.put("THIS", "3");
            labels.put("THAT", "4");
        }
    }

    Assembler(ArrayList<String> lines) {
        this.lines = lines;
        instructions = new ArrayList<>();

        initializeBuiltInLabels();
    }

    private void clearInstructions() {
        instructions.clear();
    }

    private void parseLinesToInstructions() {
        for (String line : lines) {
            String clearString = removeComments(line).replaceAll("\\s", "");
            if (clearString.length() > 0) {
                if (clearString.charAt(0) == '@') {
                    instructions.add(new AInstruction(clearString));
                } else if (clearString.charAt(0) == '(') {
                    instructions.add(new LInstruction(clearString));
                } else {
                    instructions.add(new CInstruction(clearString));
                }
            }
        }
    }

    private String removeComments(String input) {
        int commentIndex = input.indexOf("//");
        if (commentIndex >= 0) {
            return input.substring(0, commentIndex);
        } else {
            return input;
        }
    }

    private ArrayList<String> parseInstructionsToString() {
        ArrayList<String> binaryLines = new ArrayList<>();
        for (Instruction instruction : instructions) {
            if (instruction instanceof LInstruction) {
                continue;
            }
            binaryLines.add(instruction.parse());
        }
        return binaryLines;
    }

    private void indexLabels() {
        int lineNumber = 0;
        for (Instruction instruction : instructions) {
            if (instruction instanceof LInstruction) {
                String label = instruction.parse();
                if (!labels.containsKey(label)) {
                    labels.put(label, Integer.toString(lineNumber));
                }
            } else {
                lineNumber++;
            }
        }
    }

    private void resolveLabels() {
        int freeRegister = 16;
        for (Instruction instruction : instructions) {
            if (instruction instanceof AInstruction) {
                String label = ((AInstruction) instruction).getContent();
                if (!label.matches("^\\d+$")) {
                    if (labels.containsKey(label)) {
                        instruction.setInput("@" + labels.get(label));
                    } else {
                        labels.put(label, Integer.toString(freeRegister));
                        instruction.setInput("@" + freeRegister);
                        freeRegister++;
                    }
                }
            }

        }
    }

    ArrayList<String> parse() {
        clearInstructions();
        parseLinesToInstructions();
        indexLabels();
        resolveLabels();
        return parseInstructionsToString();
    }
}
