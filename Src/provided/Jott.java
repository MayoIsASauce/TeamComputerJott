package provided;

import java.util.ArrayList;

import provided.*;
import computer.exceptions.SemanticException;
public class Jott {
    public static String helpMessage() {
        return """
                Jott <input file>.jott
                Interprets a Jott.java file.
                """;
    }
    public static void main(String[] args) throws SemanticException {
        if (args.length != 1) {
            System.out.println(helpMessage());
            return;
        }

        String filename = args[0];
        
        ArrayList<Token> tokens = JottTokenizer.tokenize(filename);

        JottTree parse_tree = JottParser.parse(tokens);
        if (parse_tree.validateTree()) {
            // TODO: execute
        }
    }
}