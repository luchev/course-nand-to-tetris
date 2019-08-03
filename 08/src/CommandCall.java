class CommandCall extends Command {
    private static int returnAddressCounter = 1;

    CommandCall(String command) {
        super(command);
    }

    @Override
    String parse() {
        Parser parser = new Parser();
        String returnAddress = "return_" + returnAddressCounter;
        returnAddressCounter++;

        // push return address
        String a = "@" + returnAddress + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        // push LCL
        String b = "@LCL\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        // push ARG
        String c = "@ARG\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        // push THIS
        String d = "@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        // push THAT
        String e = "@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        // ARG = *SP - 5 - nArgs
        String f = "@SP\nD=M\n@5\nD=D-A\n@" + getArg(2) + "\nD=D-A\n@ARG\nM=D\n";
        // LCL = *SP
        String g = "@SP\nD=M\n@LCL\nM=D\n";
        // goto function name
        String h = "@" + getArg(1) + "\n0;JMP\n";
        // label to return to
        String i = "(" + returnAddress + ")\n";

        return getCommandAsComment() + a + b + c + d + e + f + g + h + i;
    }
}
