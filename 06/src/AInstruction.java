class AInstruction extends Instruction {
    AInstruction(String input) {
        super(input);
    }

    String getContent() {
        return input.substring(1);
    }

    String parse() {
        if (input.length() < 2) {
            return "";
        }

        int number = Integer.parseInt(input.substring(1));
        String numberInBinary = Integer.toBinaryString(number);
        StringBuilder output = new StringBuilder("0");
        for (int i = 0; i < 15 - numberInBinary.length(); i++) {
            output.append("0");
        }
        output.append(numberInBinary);

        return output.toString();
    }
}
