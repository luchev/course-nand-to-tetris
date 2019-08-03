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

    /**
     * Stack: ..., E, SP
     *
     * Get the current segment label from the hash
     * Save its value in D (which is 5 for temp)
     * Add the offset we want to access to D (5 + offset)
     * Save that index in R15
     * Decrease SP by 1 to point E
     * Keep E in D
     * Point A to the index (R15)
     * Let the current address (M) have the value in D
     */
    private String popTemp() {
        return "@" + segments.get(getArg(1)) + "\nD=A\n@" + getArg(2) + "\nD=D+A\n@R15\nM=D\n" +
                "@SP\nM=M-1\nA=M\nD=M\n" +
                "@R15\nA=M\nM=D\n";
    }

    /**
     * Stack: ..., E, SP
     *
     * Get the current segment label from the hash
     * Save its value in D
     * Add the index we want to access to D
     * Save that index in R15
     * Decrease SP by 1 to point E
     * Keep E in D
     * Point A to the index (R15)
     * Let the current address (M) have the value in D
     */
    private String popSegment() {
        return "@" + segments.get(getArg(1)) + "\nD=M\n@" + getArg(2) + "\nD=D+A\n@R15\nM=D\n" +
                "@SP\nM=M-1\nA=M\nD=M\n" +
                "@R15\nA=M\nM=D\n";
    }

    /**
     * Stack: ..., E, SP
     *
     * Decrease SP by 1 to point E
     * Keep E in D
     * Point A to the static label
     * Let the current address (M) have the value in D
     */
    private String popStatic() {
        return "@SP\nM=M-1\nA=M\nD=M\n@" + filename + "." + getArg(2) + "\nM=D\n";
    }

    /**
     * Stack: ..., E, SP
     *
     * Decrease SP by 1 to point E
     * Keep E in D
     * Load the address of THIS or THAT
     * Let the current address (M) have the value in D
     */
    private String popPointer() {
        if (getArg(2).equals("0")) {
            return "@SP\nM=M-1\nA=M\nD=M\n@THIS\nM=D\n";
        } else {
            return "@SP\nM=M-1\nA=M\nD=M\n@THAT\nM=D\n";
        }
    }
}
