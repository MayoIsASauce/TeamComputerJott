package computer.exceptions;

import provided.Token;

public class SemanticException extends Exception
{
    public SemanticException(String msg, Token token)
    {
        super(msg + "\n" + token.getFilename() + ":" + token.getLineNum());
    }
}