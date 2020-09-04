public class VMTranslator {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Provide at least one file to parse. Bootstrapping will be applied if " +
                    "the path provided is a directory. If it's a regular file there will be no bootstrapping " +
                    "applied.");
            return;
        }

        Parser parser = new Parser();
        for (String arg : args) {
            parser.parse(arg);
        }
    }
}
