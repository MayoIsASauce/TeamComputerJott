package computer.parsernodes;

import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;

public class ElseIfNode implements JottTree {

    private JottTree condition;
    private JottTree body;

    public ElseIfNode(JottTree condition, JottTree body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public boolean validateTree() throws SemanticException {
        boolean cond_valid = condition != null && condition.validateTree();
        boolean body_valid = body != null && body.validateTree();

        if (!cond_valid || !body_valid) {
            throw new SemanticException("Semantic Error\nProvided "
                            + (!cond_valid ? "condition" : "body") + " is null in ElseIfNode");
        }

        return cond_valid && body_valid;
    }

    @Override
    public String convertToJott() {
        return "Elseif[" + condition.convertToJott() + "]{" + body.convertToJott() + "}";
    }

    public static ElseIfNode parse(ArrayList<Token> tokens) throws ParseException {
        // Expecting "Elseif"
        if (!tokens.get(0).getToken().equals("Elseif")) {
            throw new ParseException("Expected 'Elseif'");
        }
        tokens.remove(0);

        // Expecting '['
        if (!tokens.get(0).getToken().equals("[")) {
            throw new ParseException("Expected '[' after Elseif");
        }
        tokens.remove(0);

        // Parse condition
        JottTree condition = ExprNode.parse(tokens);
        if (condition == null) {
            throw new ParseException("Invalid condition in Elseif");
        }

        // Expecting ']'
        if (!tokens.get(0).getToken().equals("]")) {
            throw new ParseException("Expected ']'");
        }
        tokens.remove(0);

        // Expecting '{'
        if (!tokens.get(0).getToken().equals("{")) {
            throw new ParseException("Expected '{' after condition");
        }
        tokens.remove(0);

        // Parse body
        JottTree body = BodyNode.parse(tokens);
        if (body == null) {
            throw new ParseException("Invalid body in Elseif");
        }

        // Expecting '}'
        if (!tokens.get(0).getToken().equals("}")) {
            throw new ParseException("Expected '}' after Elseif body");
        }
        tokens.remove(0);

        // Return new ElseIfNode
        return new ElseIfNode(condition, body);
    }

    @Override
    public void execute() {
        // Phase 4 logic
    }
}
