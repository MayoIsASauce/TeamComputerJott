package provided;

import java.util.ArrayList;

import provided.*;
import computer.exceptions.SemanticException;
import computer.parsernodes.ProgramNode;
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

        ProgramNode parse_tree = (ProgramNode)JottParser.parse(tokens);
        if (parse_tree != null && parse_tree.validateTree()) {
            parse_tree.execute();
        }
    }
}