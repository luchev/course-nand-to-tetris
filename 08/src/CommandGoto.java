class CommandGoto extends Command {
    CommandGoto(String command) {
        super(command);
    }

    @Override
    String parse() {
        return getCommandAsComment() + "@" + getArg(1) + "\n0;JMP\n";
    }
}
