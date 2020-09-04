public class VMTranslator {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Provide at least one file to parse");
            return;
        }

        Parser parser = new Parser();
        for (String arg : args) {
            parser.parseFile(arg);
        }
    }
}
