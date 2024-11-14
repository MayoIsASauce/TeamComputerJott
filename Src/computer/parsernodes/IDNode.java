package computer.parsernodes;

import java.util.ArrayList;

import provided.Token;
import provided.TokenType;
import computer.exceptions.ParseException;

public class IDNode implements OperandNode {
    String id;

    public IDNode(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Types getDataType() {
        // TODO figure out how to get symboltable info here. maybe
        // SymbolTable.instance().currentScopeVar(id) is the move? - Ian
        assert false;
        return Types.BOOLEAN;
    }


    @Override
    public String convertToJott() {
        return id;
    }

    public static IDNode parse(ArrayList<Token> tokens) throws ParseException {
        Token token = tokens.get(0);

        if (token.getTokenType() != TokenType.ID_KEYWORD)
            throw new ParseException("Attempt to parse non-ID token as ID: " + token.getToken());

        tokens.remove(0);

        return new IDNode(token.getToken());
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}