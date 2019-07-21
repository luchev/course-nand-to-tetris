import java.util.HashMap;

class CInstruction extends Instruction {
    private static HashMap<String, String> computation = new HashMap<>();
    private static HashMap<String, String> destination = new HashMap<>();
    private static HashMap<String, String> jump = new HashMap<>();

    CInstruction(String input) {
        super(input);
        initializeComputation();
        initializeDestination();
        initializeJump();
    }

    String parse() {
        if (input.length() < 2) {
            return "";
        }

        return "111" + computation.get(getComputation()) +
                destination.get(getDestination()) + jump.get(getJump());
    }

    private String getDestination() {
        int equalsIndex = input.indexOf("=");
        int semicolonIndex = input.indexOf(";");
        if (equalsIndex == -1) {
            return "";
        } else if (semicolonIndex == -1) {
            return input.substring(0, equalsIndex);
        } else {
            return input.substring(0, Math.max(equalsIndex, semicolonIndex));
        }
    }

    private String getComputation() {
        int equalsIndex = input.indexOf("=");
        int semicolonIndex = input.indexOf(";");
        if (equalsIndex == -1 && semicolonIndex == -1) {
            return input;
        } else if (equalsIndex == -1 && semicolonIndex > 0) {
            return input.substring(0, semicolonIndex);
        } else if (equalsIndex > 0 && semicolonIndex == -1) {
            return input.substring(equalsIndex + 1);
        } else {
            return input.substring(equalsIndex, semicolonIndex);
        }
    }

    private String getJump() {
        int semicolonIndex = input.indexOf(";");
        if (semicolonIndex == -1) {
            return "";
        } else {
            return input.substring(semicolonIndex + 1);
        }
    }

    private static void initializeComputation() {
        if (computation.isEmpty()) {
            computation.put("0", "0101010");
            computation.put("1", "0111111");
            computation.put("-1", "0111010");
            computation.put("D", "0001100");
            computation.put("A", "0110000");
            computation.put("!D", "0001101");
            computation.put("!A", "0110001");
            computation.put("-D", "0001111");
            computation.put("-A", "0110011");
            computation.put("D+1", "0011111");
            computation.put("A+1", "0110111");
            computation.put("D-1", "0001110");
            computation.put("A-1", "0110010");
            computation.put("D+A", "0000010");
            computation.put("D-A", "0010011");
            computation.put("A-D", "0000111");
            computation.put("D&A", "0000000");
            computation.put("D|A", "0010101");
            computation.put("M", "1110000");
            computation.put("!M", "1110001");
            computation.put("-M", "1110011");
            computation.put("M+1", "1110111");
            computation.put("M-1", "1110010");
            computation.put("D+M", "1000010");
            computation.put("D-M", "1010011");
            computation.put("M-D", "1000111");
            computation.put("D&M", "1000000");
            computation.put("D|M", "1010101");
        }
    }

    private static void initializeDestination() {
        if (destination.isEmpty()) {
            destination.put("", "000");
            destination.put("M", "001");
            destination.put("D", "010");
            destination.put("MD", "011");
            destination.put("A", "100");
            destination.put("AM", "101");
            destination.put("AD", "110");
            destination.put("AMD", "111");
        }
    }

    private static void initializeJump() {
        if (jump.isEmpty()) {
            jump.put("", "000");
            jump.put("JGT", "001");
            jump.put("JEQ", "010");
            jump.put("JGE", "011");
            jump.put("JLT", "100");
            jump.put("JNE", "101");
            jump.put("JLE", "110");
            jump.put("JMP", "111");
        }
    }
}
