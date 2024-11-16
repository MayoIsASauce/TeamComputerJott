package computer.parsernodes;

import computer.FunctionInfo;
import computer.SymbolTable;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import java.util.ArrayList;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class ReturnStatementNode implements JottTree {

    ExprNode expr;
    Token lineSave;


    public Types getDataType() {
        if (expr != null)
            return this.expr.getDataType();
        else
            return Types.VOID;
    }

    public ReturnStatementNode()
    {
        this.expr = null;
        this.lineSave = null;
    }

    public ReturnStatementNode(ExprNode expr, Token lineSave) {
        this.expr = expr;
        this.lineSave = lineSave;
    }

    public ReturnStatementNode(Token lineSave)
    {
        this.expr = null;
        this.lineSave = lineSave;
    }

    @Override
    public boolean validateTree() throws SemanticException {

        if (expr != null)
        {
            expr.validateTree();
        }

        FunctionInfo scopeInformation = SymbolTable.instance().currentScopeInfo();
        Types exprDataType = getDataType();

        // NOTE: R_BRACE check is so that, if this return was created by just having
        // no return statement (so the block "returns" void), we don't throw an error
        // about a void-returning block in a nonvoid function
        if(lineSave.getTokenType() != TokenType.R_BRACE && scopeInformation.returnType() != exprDataType) {
            String msg = "Function " + SymbolTable.instance().currentScope() + " returns " + scopeInformation.returnType() + " but found " + exprDataType;
            SemanticException ex = new SemanticException(msg, lineSave);
            throw ex;
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
            return new ReturnStatementNode(tokens.get(0));
        }
        Token lineSave = tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);

        if (tokens.get(0).getTokenType() != TokenType.SEMICOLON)
        {
            throw new ParseException("Missing ';' in return statement");
        }
        tokens.remove(0);
        
        return new ReturnStatementNode(expr, lineSave);

    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}