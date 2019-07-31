class CommandPush extends Command {
    private String filename;

    CommandPush(String command, String filename) {
        super(command);
        this.filename = filename;
    }

    @Override
    String parse() {
        if (commandElements.length != 3) {
            return null;
        }
        if (!getArg(0).equals("push")) {
            return null;
        }

        switch (getArg(1)) {
            case "static":
                return getCommandAsComment() + pushStatic();
            case "pointer":
                return getCommandAsComment() + pushPointer();
            case "constant":
                return getCommandAsComment() + pushConstant();
            case "temp":
                return getCommandAsComment() + pushTemp();
            default:
                return getCommandAsComment() + pushSegment();
        }
    }

    private String pushTemp() {
        String a = "@" + segments.get(getArg(1)) + "\nD=A\n@" + getArg(2) + "\nD=D+A\nA=D\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        return a;
    }

    private String pushConstant() {
        return "@" + commandElements[2] + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
    }

    private String pushSegment() {
        String a = "@" + segments.get(getArg(1)) + "\nD=M\n@" + getArg(2) + "\nD=D+A\nA=D\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        return a;
    }

    private String pushStatic() {
        return "@" + filename + "." + commandElements[2] + "\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
    }

    private String pushPointer() {
        if (getArg(2).equals("0")) {
            return "@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        } else {
            return "@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        }
    }
}
