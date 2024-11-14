package computer.parsernodes;

import computer.exceptions.ParseException;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.SymbolTable;
import computer.FunctionInfo;

public class ReturnStatementNode implements JottTree {

    ExprNode expr;


    public Types getDataType() {
        return this.expr.getDataType();
    }

    public ReturnStatementNode(ExprNode expr) {
        this.expr = expr;
    }

    public ReturnStatementNode()
    {
        this.expr = null;
    }

    @Override
    public boolean validateTree() {
        if(!expr.validateTree()){
            return false;
        }

        FunctionInfo scopeInformation = SymbolTable.instance().currentScopeInfo();

        if(scopeInformation.returnType() != expr.getDataType()) {
            return false;
        }


        return true;
    }

    @Override
    public String convertToJott() {
        if (this.expr == null)
        {
            return "";
        }

        String toReturn = "Return ";
        toReturn += expr.convertToJott();
        toReturn += ";";
        return toReturn;
    }

    public static ReturnStatementNode parse(ArrayList<Token> tokens) throws ParseException{
        
        if(!tokens.get(0).getToken().equals("Return")
            && tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new ParseException("Return or '}' expected");
        }
        
        if (tokens.get(0).getTokenType() == TokenType.R_BRACE)
        {
            return new ReturnStatementNode();
        }
        tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);

        if (tokens.get(0).getTokenType() != TokenType.SEMICOLON)
        {
            throw new ParseException("Missing ';' in return statement");
        }
        tokens.remove(0);
        
        return new ReturnStatementNode(expr);

    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}