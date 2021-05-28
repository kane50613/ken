package tw.kane.ken;

import tw.kane.ken.error.IllegalCharacterException;
import tw.kane.ken.error.MissingCharacterException;
import tw.kane.ken.error.SyntaxException;
import tw.kane.ken.error.UnexpectedTokenException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0) {
            try {
                Lexer lexer = new Lexer(new ExecuteFile(args[0]));
                lexer.parse();
                Parser parser = new Parser(lexer.getTokens(), new ExecuteFile(args[0]));
                parser.parse();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalCharacterException e) {
                System.out.printf(
                        "illegal character %s at %d:%d\n%s\n%s%n",
                        e.character,
                        e.position.row,
                        e.position.col,
                        e.line,
                        Util.makeSpace(e.position.col - 1) + Util.repeatString("^", e.character.length())
                );
            } catch (SyntaxException e) {
                e.printStackTrace();
            } catch (MissingCharacterException e) {
                e.printStackTrace();
            } catch (UnexpectedTokenException e) {
                e.printStackTrace();
            }
        } else System.out.println("ken");
    }
}
