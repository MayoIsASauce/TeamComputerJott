package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;
import computer.parsernodes.BodyNode;
import computer.parsernodes.Types;


public class FuncReturnNode implements JottTree {

    TypeNode returnType;
    public FuncReturnNode(TypeNode returnType) {
        this.returnType = returnType;
    }

    public Types type() {
        return returnType.type();
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {
        if (returnType != null) {
            return returnType.convertToJott();
        } else {
            return "Void";
        }
        
    }

    public static FuncReturnNode parse(ArrayList<Token> tokens) throws ParseException {
        Token currToken = tokens.get(0);

        if (currToken.getTokenType() == TokenType.ID_KEYWORD) {

            if (currToken.getToken().equals("Void"))
            {
                tokens.remove(0);
                return new FuncReturnNode(null);
            }

            return new FuncReturnNode(TypeNode.parse(tokens));
        }
        String msg = "Parser Exception\nFuncReturnNode received invalid type \""
                        +currToken.getTokenType()+"\", expected: ID_KEYWORD";
        throw new ParseException(msg);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}