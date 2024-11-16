package computer.parsernodes;

import java.util.ArrayList;
import java.util.List;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;

public class ParamsNode implements JottTree {

    // List to hold the parsed parameters
    private List<ExprNode> parameters;

    // Constructor to initialize the parameters list
    public ParamsNode(List<ExprNode> parameters) {
        this.parameters = parameters;
    }

    public List<ExprNode> parameters() {
        return parameters;
    }

    @Override
    public boolean validateTree() throws SemanticException {
        // Validate all parameters in the list
        for (ExprNode param : parameters) {
            param.validateTree();
        }
        return true;
    }

    @Override
    public String convertToJott() {
        // Convert the parameters to a comma-separated string inside square brackets
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parameters.size(); i++) {
            result.append(parameters.get(i).convertToJott());
            if (i < parameters.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    // Static method to parse a ParamsNode from tokens
    public static ParamsNode parse(ArrayList<Token> tokens) throws ParseException {
        List<ExprNode> params = new ArrayList<>();

        if (tokens.get(0).getTokenType() == TokenType.R_BRACE) {
            // Empty params
            return new ParamsNode(params);
        }

        // Loop to collect parameters, separated by commas
        while (!tokens.get(0).getToken().equals("]")) {
            // Parse each parameter (an expression)
            ExprNode param = ExprNode.parse(tokens);
            if (param == null) {
                throw new ParseException("Invalid parameter expression");
            }
            params.add(param);

            // Check if the next token is a comma, if not, expect the closing bracket
            if (tokens.get(0).getToken().equals(",")) {
                tokens.remove(0); // Consume the comma and continue
            } else if (!tokens.get(0).getToken().equals("]")) {
                throw new ParseException("Expected ']' or ',' after parameter");
            }
        }

        // Expecting the closing bracket "]"
        if (!tokens.get(0).getToken().equals("]")) {
            throw new ParseException("Expected ']' at the end of parameters");
        }

        // Return the ParamsNode with the parsed parameters
        return new ParamsNode(params);
    }

    @Override
    public void execute() {
        // TODO: Implement in Phase 4
    }
}