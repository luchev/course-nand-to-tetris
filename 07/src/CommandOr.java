class CommandOr extends Command {
    CommandOr(String command) {
        super(command);
    }

    @Override
    String parse() {
        return "@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M\nD=D|M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
    }
}
