package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;

public class ParamsTailNode implements JottTree {

    private JottTree nextParam; // The next parameter in the list

    // Constructor to initialize the next parameter
    public ParamsTailNode(JottTree nextParam) {
        this.nextParam = nextParam;
    }

    @Override
    public boolean validateTree() {
        // TODO: Implement in Phase 3
        return false;
    }

    @Override
    public String convertToJott() {
        // Convert the tail parameter to Jott code (should be in the form of ", param")
        return ", " + nextParam.convertToJott();
    }

    // Static method to parse a ParamsTailNode from tokens
    public static ParamsTailNode parse(ArrayList<Token> tokens) throws ParseException {
        // Expecting a comma before the next parameter
        if (!tokens.get(0).getToken().equals(",")) {
            throw new ParseException("Expected ',' before the next parameter");
        }
        tokens.remove(0); // Consume the comma

        // Parse the next parameter (which is an expression)
        JottTree nextParam = ExprNode.parse(tokens);
        if (nextParam == null) {
            throw new ParseException("Invalid parameter expression after ','");
        }

        // Return the ParamsTailNode with the parsed next parameter
        return new ParamsTailNode(nextParam);
    }

    @Override
    public void execute() {
        // TODO: Implement in Phase 4
    }
}
