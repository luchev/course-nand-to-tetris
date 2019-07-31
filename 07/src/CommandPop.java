class CommandPop extends Command {
    private String filename;

    CommandPop(String command, String filename) {
        super(command);
        this.filename = filename;
    }

    @Override
    String parse() {
        if (commandElements.length != 3) {
            return null;
        }
        if (!getArg(0).equals("pop")) {
            return null;
        }

        switch (getArg(1)) {
            case "static":
                return getCommandAsComment() + popStatic();
            case "pointer":
                return getCommandAsComment() + popPointer();
            case "temp":
                return getCommandAsComment() + popTemp();
            default:
                return getCommandAsComment() + popSegment();
        }
    }

    private String popTemp() {
        String a = "@" + segments.get(getArg(1)) + "\nD=A\n@" + commandElements[2] + "\nD=D+A\n@R15\nM=D\n";
        String b = "@SP\nM=M-1\n";
        String c = "A=M\nD=M\n@R15\nA=M\nM=D\n";
        return a + b + c;
    }

    private String popSegment() {
        String a = "@" + segments.get(getArg(1)) + "\nD=M\n@" + commandElements[2] + "\nD=D+A\n@R15\nM=D\n";
        String b = "@SP\nM=M-1\n";
        String c = "A=M\nD=M\n@R15\nA=M\nM=D\n";
        return a + b + c;
    }

    private String popStatic() {
        return "@SP\nM=M-1\nA=M\nD=M\n@" + filename + "." + commandElements[2] + "\nM=D\n";
    }

    private String popPointer() {
        if (getArg(2).equals("0")) {
            return "@SP\nM=M-1\nA=M\nD=M\n@THIS\nM=D\n";
        } else {
            return "@SP\nM=M-1\nA=M\nD=M\n@THAT\nM=D\n";
        }
    }
}
