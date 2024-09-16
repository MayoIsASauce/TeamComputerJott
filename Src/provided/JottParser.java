package provided;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import java.util.ArrayList;

import computer.exceptions.ParseException;
import computer.parsernodes.*;

public class JottParser {
    /**
     * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens) {
      // This template will change into root node?
      JottTree parseTree = new Template(); 

      try {
        throw new ParseException("Stubbed"); // delete me ;)

        /*
         * I'm guessing that we just throw a 
         * loop here and kinda do a BFS? (shoutout APX)
         * 
         * I created the parse exception because I 
         * figured we could just do a custom exception
         * kind of like we did for phase 1 to handle
         * everything.
         * 
         */
      } 
      catch (ParseException e) {
		    return null;
      }

      // return parseTree;
    }
}
