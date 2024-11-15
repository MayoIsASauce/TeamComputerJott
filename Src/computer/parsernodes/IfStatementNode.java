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
    public boolean validateTree() throws SemanticException {
        boolean expr_valid = expr != null && expr.validateTree();
        boolean body_valid = body != null && body.validateTree();

        if (elseIfs != null) { // if we have elseIf nodes
            for (ElseIfNode node : elseIfs) {
                if (node == null) { // check the validity of each
                    throw new SemanticException("Semantic Error\nProvided ElseIf is null in IfStatementNode");
                } 
                node.validateTree();
            }
        }

        if (elseNode != null) { // check if we have an elseNode and if it is valid
            elseNode.validateTree();
        }
        
        if (!expr_valid || !body_valid) {
            throw new SemanticException("Semantic Error\nProvided "
                            + (!expr_valid ? "expression" : "body") + " is null in IfStatementNode");
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
    public void execute() {
        // TODO Auto-generated method stub
    }
}