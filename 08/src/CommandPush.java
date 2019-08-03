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

    /**
     * Stack: ..., SP
     *
     * Get the current segment label from the hash
     * Save its value in D (which is 5 for temp)
     * Add the offset we want to access to D (5 + offset)
     * Point A to that index
     * Keep what that index holds in D
     * Go to the stack pointer
     * Set the current stack pointer to D
     * Go to the stack pointer and increment it with 1
     */
    private String pushTemp() {
        return "@" + segments.get(getArg(1)) + "\nD=A\n@" + getArg(2) + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
    }

    /**
     * Stack: ..., SP
     *
     * Keep the constant in D
     * Go to the stack pointer
     * Set the current stack pointer to D
     * Go to the stack pointer and increment it with 1
     */
    private String pushConstant() {
        return "@" + getArg(2) + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
    }

    /**
     * Stack: ..., SP
     *
     * Get the current segment label from the hash
     * Save its value in D
     * Add the offset we want to access to D
     * Point A to that index
     * Keep what that index holds in D
     * Go to the stack pointer
     * Set the current stack pointer value to D
     * Go to the stack pointer and increment it with 1
     */
    private String pushSegment() {
        return "@" + segments.get(getArg(1)) + "\nD=M\n@" + getArg(2) + "\nD=D+A\nA=D\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
    }

    /**
     * Stack: ..., SP
     *
     * Point A to the static label
     * Keep the value pointed by A in D
     * Go to the stack pointer
     * Set the current stack pointer value to D
     * Go to the stack pointer and increment it with 1
     */
    private String pushStatic() {
        return "@" + filename + "." + getArg(2) + "\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
    }

    /**
     * Stack: ..., SP
     *
     * Point A to THIS/THAT
     * Keep the value pointed by A in D
     * Go to the stack pointer
     * Set the current stack pointer value to D
     * Go to the stack pointer and increment it with 1
     */
    private String pushPointer() {
        if (getArg(2).equals("0")) {
            return "@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        } else {
            return "@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        }
    }
}
