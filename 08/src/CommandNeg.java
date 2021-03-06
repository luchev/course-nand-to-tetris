class CommandNeg extends Command {
    CommandNeg(String command) {
        super(command);
    }

    /**
     * Stack: ..., E, SP
     * in position E we want to have -E
     *
     * Point A to SP - 1 (which is E)
     * Negate M (which points E)
     */
    @Override
    String parse() {
        return "@SP\nA=M-1\nM=-M\n";
    }
}
