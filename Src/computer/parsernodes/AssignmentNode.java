package computer.parsernodes;

import java.util.ArrayList;

import computer.SymbolTable;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import computer.exceptions.RuntimeException;
import provided.Token;
import provided.TokenType;

public class AssignmentNode implements BodyStatementNode {
    IDNode id;
    ExprNode expr;

    public AssignmentNode(IDNode id, ExprNode expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public boolean isReturnable(Types returnType) { return false; }

    @Override
    public boolean validateTree() throws SemanticException {
        // Ensure the identifier exists in the current scope
        if (!SymbolTable.instance().isVariableInCurrentScope(id.id()))
        {
            throw new SemanticException("Variable '" + id.id() +
                        "' is not in the current scope.", id.getToken());
        }

        if ( !Character.isLowerCase(id.id().charAt(0)) )
        {
            throw new SemanticException("Identifier " + id.id() + 
                        " must start with lowercase letter.", id.getToken());
        }
        
        expr.validateTree();
        if (id.getDataType() != expr.getDataType()) {
            throw new SemanticException("Attempt to assign expression of type "
                    + expr.getDataType() + " to variable of type "
                    + id.getDataType(), id.getToken());
        }

        return true;
    }

    @Override
    public String convertToJott() {
        return String.format("%s=%s;", id.convertToJott(), expr.convertToJott());
    }

    public static AssignmentNode parse(ArrayList<Token> tokens) throws ParseException{
        IDNode nId;
        ExprNode nExpr;

        nId = IDNode.parse(tokens);

        if (tokens.get(0).getTokenType() != TokenType.ASSIGN) {
            throw new ParseException("Missing '=' in AssignmentNode");
        }
        tokens.remove(0); // eat assignment = sign

        nExpr = ExprNode.parse(tokens);

        if (tokens.get(0).getTokenType() == TokenType.SEMICOLON) {
            tokens.remove(0);
            return new AssignmentNode(nId, nExpr);
        } else {
            throw new ParseException("Missing semicolon at the end of assignment");
        }
    }

    @Override
    public void execute(Object outparam) throws RuntimeException
    {
        // TODO: Get expr value

        // SymbolTable.instance().setVariableValue(id.id(), value);
    }
}