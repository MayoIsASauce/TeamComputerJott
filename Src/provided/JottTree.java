package provided;

import computer.exceptions.ReturnException;
import computer.exceptions.RuntimeException;
import computer.exceptions.SemanticException;

/**
 * Interface for all Jott parse tree nodes
 *
 * @author Scott C Johnson
 */
public interface JottTree {

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott();

    /**
     * This will validate that the tree follows the semantic rules of Jott
	 * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree() throws SemanticException;
	
	/**
	 * This will execute the Jott code represented by this JottTree node.
     * Requires checking
	 */
	public void execute() throws RuntimeException, ReturnException;
}