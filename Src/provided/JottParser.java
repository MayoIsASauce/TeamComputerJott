package provided;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import java.util.ArrayList;

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

      try {
        return ProgramNode.parse(tokens); 
      } 
      catch (ParseException e) {
        System.out.println(e.getMessage());
		    return null;
      }
    }
}