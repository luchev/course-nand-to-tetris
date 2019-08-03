class CommandReturn extends Command {
    CommandReturn(String command) {
        super(command);
    }

    @Override
    String parse() {

        // R14 holds the return address
        String a = "@LCL\nD=M\n@5\nA=D-A\nD=M\n@R14\nM=D\n";
        // *ARG = pop()
        String b = "@SP\nA=M-1\nD=M\n@ARG\nA=M\nM=D\n";
        // SP = *(ARG + 1)
        String c = "@ARG\nD=M+1\n@SP\nM=D\n";
        // THAT = *(LCL - 1)
        String d = "@LCL\nD=M\n@1\nA=D-A\nD=M\n@THAT\nM=D\n";
        // THAT = *(LCL - 2)
        String e = "@LCL\nD=M\n@2\nA=D-A\nD=M\n@THIS\nM=D\n";
        // ARG = *(LCL - 3)
        String f = "@LCL\nD=M\n@3\nA=D-A\nD=M\n@ARG\nM=D\n";
        // LCL = *(LCL - 4)
        String g = "@LCL\nD=M\n@4\nA=D-A\nD=M\n@LCL\nM=D\n";
        // goto *R14
        String h = "@R14\nA=M\n0;JMP\n";

        return getCommandAsComment() + a + b + c + d + e + f + g + h;
    }
}
