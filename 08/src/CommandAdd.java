class CommandAdd extends Command {
    CommandAdd(String command) {
        super(command);
    }

    /**
     * Stack: ..., E, R, SP
     * in position E we want to have E + R
     *
     * Decrease SP by 1 to point R
     * Keep R in D
     * Point A to SP - 1 (which is E, as SP points R now)
     * Add D to M (M points E, D contains R so we add them and save the result directly in E)
     */
    @Override
    String parse() {
        return getCommandAsComment() + "@SP\nM=M-1\nA=M\nD=M\n@SP\nA=M-1\nM=D+M\n";
    }

}
