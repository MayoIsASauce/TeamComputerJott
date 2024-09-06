package provided;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.util.ArrayList;
import java.util.stream.Collector.Characteristics;
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
          
            String line = "";
            int line_num = 0;

            while ((line = reader.readLine()) != null) {
                line_num++;

                boolean EOL = false;
                for (int i=0; i < line.length()-1; i++) {
                    int c = line.charAt(i);
                    
                    if (i+1 >= line.length()-1) EOL = true; // check if we have the last character in the line

                    //#region DFA_LOGIC

                    // =
                    if (c == '=') {
                        if (!EOL && line.charAt(i+1) == '=') {
                            tokens.add(new Token("==", filename, line_num, TokenType.REL_OP));
                        } else {
                            tokens.add(new Token("=", filename, line_num, TokenType.ASSIGN));
                        }
                        continue;
                    }

                    // <>
                    if (c == '<' || c == '>') {
                        if (!EOL && line.charAt(i+1) == '=') {
                            tokens.add(new Token(c + "=", filename, line_num, TokenType.REL_OP));
                        } else {
                            tokens.add(new Token(Character.toString(c), filename, line_num, TokenType.REL_OP));
                        }
                        continue;
                    }

                    // /+-*
                    if (c == '/' || c == '+' || c == '-' || c == '*') {
                        tokens.add(new Token(Character.toString(c), filename, line_num, TokenType.MATH_OP));
                        continue;
                    }

                    // ;
                    if (c == ';') {
                        tokens.add(new Token(Character.toString(c), filename, line_num, TokenType.SEMICOLON));
                        continue;
                    }

                    
                    //#endregion
                }
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}