class CommandLabel extends Command {
    CommandLabel(String command) {
        super(command);
    }

    @Override
    String parse() {
        return getCommandAsComment() + "(" + getArg(1) + ")\n";
    }
}
