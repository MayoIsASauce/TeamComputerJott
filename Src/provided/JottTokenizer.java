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
            int curr_chr = 0;
            int next_chr = reader.read();

            int line_num = 0;

            while (next_chr != -1) {
                curr_chr = next_chr;
                next_chr = reader.read();

                if (curr_chr == '\n') line_num++;

                //#region DFA_LOGIC

                // =
                if (curr_chr == '=') {
                    if (next_chr == '=') { // ==
                        next_chr = reader.read(); // consume next_chr
                        tokens.add(new Token("==", filename, line_num, TokenType.REL_OP));
                    } else {
                        tokens.add(new Token("=", filename, line_num, TokenType.ASSIGN));
                    }
                    continue;
                }

                // <>
                if (curr_chr == '<' || curr_chr == '>') {
                    if (next_chr == '=') { // <= or >=
                        next_chr = reader.read(); // consume next_chr
                        tokens.add(new Token(curr_chr + "=", filename, line_num, TokenType.REL_OP));
                    } else {
                        tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.REL_OP));
                    }
                    continue;
                }

                // /+-*
                if (curr_chr == '/' || curr_chr == '+' || curr_chr == '-' || curr_chr == '*') {
                    tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.MATH_OP));
                }

                // ;
                if (curr_chr == ';') {
                    tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.SEMICOLON));
                }

                //#endregion
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}