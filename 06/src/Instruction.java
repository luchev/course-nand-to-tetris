abstract class Instruction {
    String input;

    Instruction(String input) {
        this.input = input;
    }

    abstract String parse();

    void setInput(String input) {
        this.input = input;
    }
}
