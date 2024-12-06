package computer.parsernodes;

import java.util.ArrayList;
import java.util.List;

import provided.Token;
import provided.TokenType;
import computer.FunctionInfo;
import computer.SymbolTable;
import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;

public class FuncCallNode implements OperandNode, BodyStatementNode {

    IDNode funcName;
    ParamsNode params;

    boolean isInBody;

    public FuncCallNode(IDNode funcName, ParamsNode params)
    {
        this.funcName = funcName;
        this.params = params;
        this.isInBody = false;
    }

    public void setInBody()
    {
        isInBody = true;
    }

    @Override
    public boolean isReturnable(Types returnType) { return false; }

    @Override
    public boolean validateTree() throws SemanticException
    {
        params.validateTree();
        
        if (SymbolTable.instance().isReservedFunction(funcName.id()))
        {
            if (funcName.id().equals("print"))
            {
                if (params.parameters().size() != 1)
                {
                    throw new SemanticException("'print' takes in 1 argument.",
                    funcName.getToken());
                }
                
                return true;
            }

            else if (funcName.id().equals("concat"))
            {
                if (params.parameters().size() != 2 ||
                    params.parameters().get(0).getDataType() != Types.STRING ||
                    params.parameters().get(1).getDataType() != Types.STRING)
                {
                    throw new SemanticException("'concat' takes in 2 Strings.",
                    funcName.getToken());
                }

                return true;
            }

            else
            {
                if (params.parameters().size() != 1 ||
                    params.parameters().get(0).getDataType() != Types.STRING)
                {
                    throw new SemanticException("'length' takes in 1 String.",
                    funcName.getToken());
                }

                return true;
            }
        }

        if (!SymbolTable.instance().containsFunction(funcName.id()))
        {
            throw new SemanticException("Call to unknown function '" + 
                    funcName.id() + "'", funcName.getToken());
        }

        // child nodes are valid, now make sure that arguments match parameters
        FunctionInfo calledFunction = SymbolTable.instance().functionInfo(funcName.id());
        List<ExprNode> exprs = params.parameters();
        List<Types> calledFunctionTypes = calledFunction.parameterTypes();

        if (exprs.size() != calledFunctionTypes.size()) {
            throw new SemanticException("Expected " + calledFunctionTypes.size()
                    + " arguments to function " + funcName.id() + " but got "
                    + exprs.size() + " arguments.", funcName.getToken());
        }

        for (int i = 0; i < exprs.size(); ++i)
        {
            if (exprs.get(i).getDataType() != calledFunctionTypes.get(i)) {
                Token token = funcName.getToken();
                throw new SemanticException("Expected argument of type "
                        + calledFunctionTypes.get(i) + " for argument at index "
                        + i + " to function " + funcName.id() + " but got "
                        + exprs.get(i).getDataType(), token);
            }
        }
        return true;
    }

    @Override
    public Token getToken() { return funcName.getToken(); }

    @Override
    public Types getDataType() {
        return SymbolTable.instance().functionInfo(funcName.id()).returnType();
    }

    @Override
    public String convertToJott()
    {
        String result = "::" + funcName.convertToJott() + "[" + params.convertToJott() + "]";

        if (isInBody)
        {
            result += ";";
        }

        return result;
    }

    public static FuncCallNode parse(ArrayList<Token> tokens) throws ParseException
    {
        if (tokens.get(0).getTokenType() != TokenType.FC_HEADER)
        {
            throw new ParseException("Function call node must start with \"::\"");
        }

        tokens.remove(0);

        IDNode funcName = IDNode.parse(tokens);

        // "["
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET)
        {
            throw new ParseException("'[' missing after function name in function call");
        }

        tokens.remove(0);

        ParamsNode params = ParamsNode.parse(tokens);

        // "]"
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET)
        {
            throw new ParseException("']' missing after parameters in function call");
        }

        tokens.remove(0);

        return new FuncCallNode(funcName, params); 
    }

    @Override
    public void execute() throws RuntimeException {
        /// dont call this function, exprs should return something
        assert false;
    }

    @Override
    public Object executeAndReturnData() throws RuntimeException {
        // TODO get function node from symbol table, execute it, catch return
        // exception, return the payload inside the return exception
    }
}