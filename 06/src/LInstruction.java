class LInstruction extends Instruction {

    LInstruction(String input) {
        super(input);
    }

    @Override
    String parse() {
        return input.replace("(", "").replace(")", "");
    }
}
