class CommandNeg extends Command {
    CommandNeg(String command) {
        super(command);
    }

    @Override
    String parse() {
        return "@SP\nA=M-1\nM=-M\n";
    }
}
