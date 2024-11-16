package computer.parsernodes;

import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import computer.parsernodes.Types;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;

public class ElseIfNode implements JottTree {

    private JottTree condition;
    private BodyNode body;

    public ElseIfNode(JottTree condition, BodyNode body) {
        this.condition = condition;
        this.body = body;
    }

    public boolean isReturnable(Types returnType) {
        return body.isReturnable(returnType);
    }

    @Override
    public boolean validateTree() throws SemanticException {
        assert condition != null && body != null;
        condition.validateTree(); body.validateTree();

        return true;
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
        BodyNode body = BodyNode.parse(tokens);
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