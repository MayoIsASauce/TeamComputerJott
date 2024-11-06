package provided;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import java.util.ArrayList;

import computer.SymbolTable;
import computer.exceptions.ParseException;
import computer.parsernodes.ProgramNode;

public class JottParser {
    /**
     * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens) {

      new SymbolTable(); // instantiate SymbolTable singleton
      int lastLine = 0;
      String filename = "NOFILE";

      try {
        if (tokens.size() > 0) {
          lastLine = tokens.get(tokens.size() - 1).getLineNum();
          filename = tokens.get(tokens.size() - 1).getFilename();
        }
        ProgramNode node = ProgramNode.parse(tokens); 
        SymbolTable.instance().finalize();

        return node;
      } 
      catch (ParseException | IndexOutOfBoundsException e) {
        System.err.println("Syntax Error");
        if (e instanceof IndexOutOfBoundsException) {
          System.err.println("Early EOF");
          System.err.println(filename + ":" + Integer.toString(lastLine));
          return null;
        }
        e.printStackTrace(System.out);

        if (!tokens.isEmpty()) {
          Token t = tokens.get(0);
          System.err.println(e.getMessage());
          System.err.println(filename + ":" + Integer.toString(t.getLineNum()));
        }
		return null;
      }
    }
}