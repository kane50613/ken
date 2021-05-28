package tw.kane.ken;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import tw.kane.ken.error.IllegalCharacterError;
import tw.kane.ken.error.MissingCharacterError;
import tw.kane.ken.error.SyntaxError;
import tw.kane.ken.error.UnexpectedTokenError;

import java.io.IOException;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.RED_TEXT;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0) {
            try {
                Lexer lexer = new Lexer(new ExecuteFile(args[0]));
                lexer.parse();
                Parser parser = new Parser(lexer.getTokens(), new ExecuteFile(args[0]));
                parser.parse();
                parser.nodes.get(0).execute();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MissingCharacterError | UnexpectedTokenError | IllegalCharacterError | SyntaxError e) {
                System.out.printf(
                        colorize("%s %s at %s:%d:%d\n%s\n%s%n", RED_TEXT()),
                        e.getMessage(),
                        e.character,
                        e.executeFile.getFile().getName(),
                        e.position.row,
                        e.position.col,
                        e.line,
                        Util.makeSpace(e.position.col - 1) + Util.repeatString("^", e.character.length())
                );
            }
        } else System.out.println("ken");
    }
}
