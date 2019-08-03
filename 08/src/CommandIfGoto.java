class CommandIfGoto extends Command {
    CommandIfGoto(String command) {
        super(command);
    }

    @Override
    String parse() {
        return getCommandAsComment() + "@SP\nM=M-1\nA=M\nD=M\n@" + getArg(1) + "\nD;JNE\n";
    }
}
