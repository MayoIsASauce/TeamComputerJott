package computer.parsernodes;

import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import computer.parsernodes.Types;
import computer.parsernodes.ExprNode;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import computer.exceptions.RuntimeException;

public class ElseIfNode implements JottTree {

    private ExprNode condition;
    private BodyNode body;

    public ElseIfNode(ExprNode condition, BodyNode body) {
        this.condition = condition;
        this.body = body;
    }

    public boolean isReturnable(Types returnType) {
        return body.isReturnable(returnType);
    }

    public boolean getConditionalValue() throws RuntimeException
    {
        return (boolean) condition.executeAndReturnData();
    }

    @Override
    public boolean validateTree() throws SemanticException {
        assert condition != null && body != null;
        condition.validateTree(); body.validateTree();

        if (condition.getDataType() != Types.BOOLEAN)
        {
            throw new SemanticException("ElseIf condition must return a boolean",
                         condition.getToken());
        }

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
        ExprNode condition = ExprNode.parse(tokens);
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
    public void execute() throws RuntimeException {
        body.execute();
    }
}