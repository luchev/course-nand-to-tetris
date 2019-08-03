class CommandFunction extends Command {
    CommandFunction(String command) {
        super(command);
    }

    @Override
    String parse() {
        String label = "(" + getArg(1) + ")\n";
        String push = new String(new char[Integer.parseInt(getArg(2))])
                        .replace("\0", "@0\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
        return getCommandAsComment() + label + push;
    }
}
