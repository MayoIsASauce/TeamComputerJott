package computer.parsernodes;

import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.parsernodes.ExprNode;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;

public class WhileLoopNode implements BodyStatementNode {

    private ExprNode condition; // The condition of the while loop
    private JottTree body; // The body of the while loop

    // Constructor to initialize the condition and body of the loop
    public WhileLoopNode(ExprNode condition, JottTree body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public boolean isReturnable(Types returnType) { return false; }

    @Override
    public boolean validateTree() throws SemanticException {
        assert condition != null && body != null; // these shouldnt be null
        condition.validateTree(); body.validateTree();

        return true;
    }

    @Override
    public String convertToJott() {
        // Convert the while loop to Jott code
        return "While[" + condition.convertToJott() + "]{" + body.convertToJott() + "}";
    }

    // Static parse method to create a WhileLoopNode from tokens
    public static WhileLoopNode parse(ArrayList<Token> tokens) throws ParseException {
        // Ensure the first token is "While"
        if (!tokens.get(0).getToken().equals("While")) {
            throw new ParseException("Expected 'While' keyword");
        }
        tokens.remove(0); // Consume "While"
        
        // Ensure the next token is '[' (start of the condition)
        if (!tokens.get(0).getToken().equals("[")) {
            throw new ParseException("Expected '[' after 'While'");
        }
        tokens.remove(0); // Consume '['

        // Parse the condition inside the while loop
        ExprNode condition = ExprNode.parse(tokens);
        if (condition == null) {
            throw new ParseException("Invalid condition in While loop");
        }

        // Ensure the next token is ']' (end of the condition)
        if (!tokens.get(0).getToken().equals("]")) {
            throw new ParseException("Expected ']' after condition");
        }
        tokens.remove(0); // Consume ']'

        // Ensure the next token is '{' (start of the body)
        if (!tokens.get(0).getToken().equals("{")) {
            throw new ParseException("Expected '{' for While loop body");
        }
        tokens.remove(0); // Consume '{'

        // Parse the body of the while loop
        JottTree body = BodyNode.parse(tokens);
        if (body == null) {
            throw new ParseException("Invalid body in While loop");
        }

        // Ensure the next token is '}' (end of the body)
        if (!tokens.get(0).getToken().equals("}")) {
            throw new ParseException("Expected '}' after While loop body");
        }
        tokens.remove(0); // Consume '}'

        // Return the WhileLoopNode with the parsed condition and body
        return new WhileLoopNode(condition, body);
    }

    @Override
    public void execute() throws RuntimeException {
        while ((Boolean)condition.executeAndReturnData() == true) {
            body.execute();
        }
    }
}