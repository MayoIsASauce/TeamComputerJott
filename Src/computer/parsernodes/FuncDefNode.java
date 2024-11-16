package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;
import computer.parsernodes.IDNode;
import computer.parsernodes.FuncBodyNode;
import computer.parsernodes.FuncReturnNode;
import computer.parsernodes.FuncDefParamsNode;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;
import computer.SymbolTable;


public class FuncDefNode implements JottTree {
    IDNode funcName;
    FuncDefParamsNode params;
    FuncReturnNode returnNode;
    FuncBodyNode body;

    public FuncDefNode(IDNode name, FuncDefParamsNode params,
            FuncReturnNode returnNode, FuncBodyNode body)
    {
        this.funcName = name;
        this.params = params;
        this.returnNode = returnNode;
        this.body = body;
    }

    public static FuncDefNode parse(ArrayList<Token> tokens) throws ParseException
    {

        if (!tokens.get(0).getToken().equals("Def"))
        {
            throw new ParseException("Function Definition must start with the 'Def' keyword");
        }

        tokens.remove(0);

        IDNode name = IDNode.parse(tokens);

        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET)
        {
            throw new ParseException("'[' missing after function name in function definition");
        }

        tokens.remove(0);

        FuncDefParamsNode params = FuncDefParamsNode.parse(tokens);

        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET)
        {
            throw new ParseException("']' missing after parameters in function definition");
        }

        tokens.remove(0);

        if (tokens.get(0).getTokenType() != TokenType.COLON)
        {
            throw new ParseException("':' missing after parameters in function definition");
        }

        tokens.remove(0);

        FuncReturnNode returnNode = FuncReturnNode.parse(tokens);

        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) 
        {
            throw new ParseException("'{' missing after function return in function definition");
        }

        tokens.remove(0);

        SymbolTable.instance().beginBuildingNewScope(name.id(), returnNode.type(), params.typesRepresentation());

        params.addToSymbolTable();

        // FuncBodyNode's parse should call variable declaration node's parse,
        // which will insert variables into the symbol table
        FuncBodyNode bodyNode = FuncBodyNode.parse(tokens);

        SymbolTable.instance().finishBuildingCurrentScope();

        if (!tokens.get(0).getToken().equals("}")) 
        {
            throw new ParseException("'}' missing after function body in function definition");
        }

        tokens.remove(0);

        return new FuncDefNode(name, params, returnNode, bodyNode);
    }

    @Override
    public boolean validateTree() throws SemanticException
    {
        SymbolTable.instance().enterScope(funcName.id());

        if (SymbolTable.instance().isReservedFunction(funcName.id()))
        {
            throw new SemanticException( "'" + funcName.id() + "' is a "
            + "reserved keyword.", funcName.getToken());
        }
        
        params.validateTree();
        returnNode.validateTree();
        body.validateTree();

        SymbolTable.instance().exitScope();

        return true;
    }

    @Override
    public String convertToJott()
    {
        String result = "Def " + funcName.convertToJott() + "[" + 
            params.convertToJott() + "]:" + returnNode.convertToJott() + 
            "{" + body.convertToJott() + "}";

        return result;
    }

    @Override
    public void execute()
    {
        // TODO Auto-generated method stub

    }
}