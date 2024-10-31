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

      try {
        ProgramNode node = ProgramNode.parse(tokens); 
        SymbolTable.instance().finalize();

        return node;
      } 
      catch (ParseException | IndexOutOfBoundsException e) {
        if (e instanceof IndexOutOfBoundsException) {
            System.err.println("Early EOF while parsing. incomplete program");
            return null;
        }
        e.printStackTrace(System.out);

        if (!tokens.isEmpty()) {
          Token t = tokens.get(0);
          System.err.println("Failed on line " + t.getLineNum() + ", token was \"" + t.getToken() + "\" of type " + t.getTokenType().toString());
        }
		    return null;
      }
    }
}