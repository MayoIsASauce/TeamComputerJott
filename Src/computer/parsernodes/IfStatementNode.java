package computer.parsernodes;

import computer.exceptions.ParseException;
import computer.exceptions.SemanticException;

import java.util.ArrayList;
import provided.JottTree;
import provided.Token;


public class IfStatementNode implements BodyStatementNode {

    ExprNode expr;
    BodyNode body;
    ArrayList<ElseIfNode> elseIfs;
    ElseNode elseNode;

    public IfStatementNode(ExprNode expr, BodyNode body, ArrayList<ElseIfNode> elseIfs, ElseNode elseNode) {
        this.expr = expr;
        this.body = body;
        this.elseIfs = elseIfs;
        this.elseNode = elseNode;
    }

    @Override
    public boolean isReturnable(Types returnType) {
        if (!body.isReturnable(returnType))
            return false;
        for (ElseIfNode elif : elseIfs)
            if (!elif.isReturnable(returnType))
                return false;

        return elseNode != null && elseNode.isReturnable(returnType);
    }

    @Override
    public boolean validateTree() throws SemanticException {
        assert expr != null && body != null;
        expr.validateTree(); body.validateTree();

        if (elseIfs != null) { // if we have elseIf nodes
            for (ElseIfNode node : elseIfs) {
                assert node != null; // this shouldnt be null
                node.validateTree();
            }
        }

        if (elseNode != null) { // check if we have an elseNode and if it is valid
            elseNode.validateTree();
        }
        
        return true;
    }

    @Override
    public String convertToJott() {
        String toReturn = "If [";
        toReturn += expr.convertToJott();
        toReturn += "] {";
        toReturn += body.convertToJott();
        toReturn += "}";
        while(!elseIfs.isEmpty()) {
            toReturn += elseIfs.get(0).convertToJott();
            elseIfs.remove(0);
        }
        if (elseNode != null) {
            toReturn += elseNode.convertToJott();
        }
        return toReturn;
    }

    public static IfStatementNode parse(ArrayList<Token> tokens) throws ParseException{

        if(!tokens.get(0).getToken().equals("If")) {
            throw new ParseException("If expected");
        }

        tokens.remove(0);

        if(!tokens.get(0).getToken().equals("[")) {
            throw new ParseException("[ expected]");
        }

        tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);

        if(!tokens.get(0).getToken().equals("]")) {
            throw new ParseException("] expected");
        }

        tokens.remove(0);

        if(!tokens.get(0).getToken().equals("{")) {
            throw new ParseException("{ expected");
        }

        tokens.remove(0);

        BodyNode body = BodyNode.parse(tokens);

        if(!tokens.get(0).getToken().equals("}")) {
            throw new ParseException("} expected");
        }

        tokens.remove(0);

        ArrayList<ElseIfNode> elseIfs = new ArrayList<>();

        while(tokens.get(0).getToken().equals("ElseIf")) {
            elseIfs.add(ElseIfNode.parse(tokens));
        }

        ElseNode elseNode = null;

        if(tokens.get(0).getToken().equals("Else")) {
            elseNode = ElseNode.parse(tokens);
        }

        return new IfStatementNode(expr, body, elseIfs, elseNode);

    }

    @Override
    public void execute(Object outparam) {
        // TODO Auto-generated method stub
    }
}