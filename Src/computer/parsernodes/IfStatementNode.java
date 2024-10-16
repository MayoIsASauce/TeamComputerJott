package computer.parsernodes;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class IfStatementNode implements JottTree {


    ExprNode expr;
    BodyNode body;
    ArrayList<ElseIfNode> elseIfs;
    ElseNode elseNode;

    pubic IfStatementNode(ExprNode expr, BodyNode body, ArrayList<ElseIfNode> elseIfs, ElseNode elseNode) {
        this.expr = expr;
        this.body = body;
        this.elseIfs = elseIfs;
        this.elseNode = elseNode;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String convertToJott() {
        String toReturn = "If [";
        toReturn += expr.convertToJott();
        toReturn += "] {";
        toReturn += body.convertToJott();
        toReturn += "}";
        while(elseIfs.size() > 0) {
            toReturn += elseIfs.get(0).convertToJott();
            elseIfs.remove(0);
        }
        toReturn += elseNode.convertToJott();
        return toReturn;
    }

    public static IfStatementNode parse(ArrayList<Token> tokens) {

        if(!tokens.get(0).getToken().equals("If")) {
            throw new Exception();
        }

        tokens.remove(0);

        if(!tokens.get(0).getToken().equals("[")) {
            throw new Exception();
        }

        tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);

        if(!tokens.get(0).getToken().equals("]")) {
            throw new Exception();
        }

        tokens.remove(0);

        if(!tokens.get(0).getToken().equals("{")) {
            throw new Exception();
        }

        tokens.remove(0);

        BodyNode body = BodyNode.parse(tokens);

        if(!tokens.get(0).getToken().equals("}")) {
            throw new Exception();
        }

        tokens.remove(0);

        ArrayList<ElseIfNode> elseIfs = new ArrayList<ElseIfNode>();

        while(tokens.get(0).getToken().equals("ElseIf")) {
            elseIfs.add(ElseIfNode.parse(tokens));
        }

        if(tokens.get(0).getToken().equals("Else")) {
            //Do we want to throw an error if there is no else clause?
            ElseNode elseNode = ElseNode.parse(tokens);
        }
        
        // if(!tokens.get(0).getToken().equals("Else")) {
        //     throw new Exception();
        // }

        

        return new IfStatementNode(expr, body, elseIfs, elseNode);

        

    





        

        return new IfStatementNode();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }
}