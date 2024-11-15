package computer.parsernodes;

import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;

import java.util.ArrayList;
import provided.JottTree;
import provided.Token;

public class ElseNode implements JottTree {

    BodyNode body;


    public ElseNode(BodyNode body) {
        this.body = body;
    }

    @Override
    public boolean validateTree() throws SemanticException {
        assert body != null;
        body.validateTree();

        return true;
    }

    @Override
    public String convertToJott() {
        String toReturn = "Else {";
        toReturn += body.convertToJott();
        toReturn += "}";
        return toReturn;
    }

    public static ElseNode parse(ArrayList<Token> tokens) throws ParseException{

        if(!tokens.get(0).getToken().equals("Else")) {
            throw new ParseException("expected Else");
        }

        tokens.remove(0);

        if(!tokens.get(0).getToken().equals("{")) {
            throw new ParseException("expected {");
        }

        tokens.remove(0);

        BodyNode body = BodyNode.parse(tokens);

        if(!tokens.get(0).getToken().equals("}")) {
            throw new ParseException("Expected }");
        }

        tokens.remove(0);


        return new ElseNode(body);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}