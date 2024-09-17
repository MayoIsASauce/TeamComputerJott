package provided;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.util.ArrayList;
import java.util.stream.Collector.Characteristics;

import computer.exceptions.SyntaxException;

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

            int line_num = 1;

            while (next_chr != -1) {
                curr_chr = next_chr;
                next_chr = reader.read();

                if (curr_chr == '\n') line_num++;

                //#region DFA_LOGIC

                // Whitespace
                if (Character.isWhitespace(curr_chr))
                {
                    continue;
                }

                // ,
                if (curr_chr == ',')
                {
                    tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.COMMA));
                    continue;
                }

                // ]
                if (curr_chr == ']')
                {
                    tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.R_BRACKET));
                    continue;
                }

                // [
                if (curr_chr == '[')
                {
                    tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.L_BRACKET));
                    continue;
                }

                // }
                if (curr_chr == '}')
                {
                    tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.R_BRACE));
                    continue;
                }

                // {
                if (curr_chr == '{')
                {
                    tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.L_BRACE));
                    continue;
                }

                // Comments
                if (curr_chr == '#')
                {
                    while (curr_chr != '\n' && next_chr != -1)
                    {
                        curr_chr = next_chr;
                        next_chr = reader.read();
                    }

                    line_num++;

                    continue;
                }

                // numbers
                if (Character.isDigit(curr_chr) || curr_chr == '.')
                {
                    String token = "";
                    // only one decimal allowed!
                    boolean is_floating = curr_chr == '.';

                    // pipe chars into token until nondigit or second decimal
                    while (true)
                    {
                        token += Character.toString(curr_chr);
                        // move to the next character and analyze it
                        if (Character.isDigit(next_chr) || next_chr == '.'){
                            curr_chr = next_chr;
                            next_chr = reader.read();
                        } else {
                            break;
                        }

                        // break on invalid state- may be bad number or end of number, we dont care here
                        boolean is_decimal = curr_chr == '.';
                        // report if floating for next loop iteration or error handling
                        
                        if ((is_decimal && is_floating) || (!is_decimal && !Character.isDigit(curr_chr)))
                            break;
                        
                        if (is_decimal)
                            is_floating = true;
                    }

                    // token loop exit- a non-digit character was met, handle potential err
                    String err_msg = "Invalid number token \"" + token + "\" on line " + line_num + ". ";
                    if (token.length() == 0 && is_floating)
                    {
                        // TODO: custom parse exception?
                        throw new SyntaxException(err_msg + "Contains only decimal and no digits.");
                    }
                    else if (token.equals("."))
                    {
                        throw new SyntaxException(err_msg + "Incomplete floating point number token.");
                    }

                    tokens.add(new Token(token, filename, line_num, TokenType.NUMBER));
                    // have to do this because a character is read at the beginning of the loop always
                    if (curr_chr == '\n') line_num++;
                    continue;
                }

                // id, keyword
                if (Character.isAlphabetic(curr_chr))
                {
                    String tokenStr = "";

                    // Keep adding to token while letter or digit
                    do
                    {
                        tokenStr += Character.toString(curr_chr);

                        if (next_chr == -1)
                        {
                            break;
                        }

                        if (Character.isAlphabetic(next_chr) || Character.isDigit(next_chr)) {
                            curr_chr = next_chr;
                            next_chr = reader.read();
                        } else {
                            break;
                        }
                    }
                    while (Character.isAlphabetic(curr_chr) || Character.isDigit(curr_chr));

                    tokens.add(new Token(tokenStr, filename, line_num, TokenType.ID_KEYWORD));

                    if (curr_chr == '\n') line_num++;

                    continue;
                }
                
                // "
                if (curr_chr == '\"') {
                    String tokenStr = Character.toString(curr_chr);
                    
                    curr_chr = next_chr;
                    next_chr = reader.read();

                    while (curr_chr != '\"')
                    {
                        tokenStr += Character.toString(curr_chr);

                        if (curr_chr == -1)
                        {
                            throw new SyntaxException("Syntax Exception\nEOF detected before string closing at "+filename+":"+line_num+".");
                        }
                        else if (curr_chr == '\n'){
                            throw new SyntaxException("Syntax Exception\nString is missing closing \" at "+filename+":"+line_num+".");
                        }
                        else if (!Character.isAlphabetic(curr_chr) & !Character.isDigit(curr_chr) & curr_chr != ' ') {
                            throw new SyntaxException("Syntax Exception\nInvalid token `"+Character.toString(curr_chr)+"` at "+filename+":"+line_num+".");
                        }

                        curr_chr = next_chr;
                        next_chr = reader.read();
                    }
                    tokenStr += '\"';

                    tokens.add(new Token(tokenStr, filename, line_num, TokenType.STRING));
                    continue;
                }

                // :
                if (curr_chr == ':')
                {
                    if (next_chr == ':') {
                        next_chr = reader.read();
                        tokens.add(new Token("::", filename, line_num, TokenType.FC_HEADER));
                    } else {
                        tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.COLON));
                    }
                    continue;
                }


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
                        tokens.add(new Token(Character.toString(curr_chr) + "=", filename, line_num, TokenType.REL_OP));
                    } else {
                        tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.REL_OP));
                    }
                    continue;
                }

                // /+-*
                if (curr_chr == '/' || curr_chr == '+' || curr_chr == '-' || curr_chr == '*') {
                    tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.MATH_OP));
                    continue;
                }

                // ;
                if (curr_chr == ';') {
                    tokens.add(new Token(Character.toString(curr_chr), filename, line_num, TokenType.SEMICOLON));
                    continue;
                }

                // !
                if (curr_chr == '!') {
                    if (next_chr == '=') { // !=
                        next_chr = reader.read(); // consume next_chr
                        tokens.add(new Token("!=", filename, line_num, TokenType.REL_OP));
                    } else {
                        throw new SyntaxException("Syntax Exception\n`!` used without a following `=` at "+filename+":"+line_num+".\n");
                    }
                    continue;
                }


                //#endregion
            }
        }
        catch (IOException | SyntaxException e)
        {
            e.printStackTrace();
            return null;
        }

        return tokens;
    }
}