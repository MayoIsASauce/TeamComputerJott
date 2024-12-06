package computer.exceptions;

import provided.Token;

public class RuntimeException extends Exception
{
    public RuntimeException(String msg, Token token)
    {
        super(msg + "\n" + token.getFilename() + ":" + token.getLineNum());
    }
}