package provided;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class JottTokenizer
{

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename)
    {
        ArrayList<Token> tokens = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
          int c = reader.read();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}