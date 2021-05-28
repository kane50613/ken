package tw.kane.ken;

import tw.kane.ken.error.UnknownError;
import tw.kane.ken.error.*;
import tw.kane.ken.node.Node;

import java.io.IOException;
import java.util.stream.Collectors;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.RED_TEXT;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0) {
            try {
                Lexer lexer = new Lexer(new ExecuteFile(args[0]));
                lexer.parse();
//                System.out.println(lexer.getTokens().stream().map(x -> x.type +": "+x.value).collect(Collectors.joining(", ")));
                Parser parser = new Parser(lexer.getTokens(), new ExecuteFile(args[0]));
                parser.parse();
                for(Node node : parser.nodes)
                    node.execute();
            } catch(IOException e) {
                System.out.printf(
                        colorize("Wrong input file %s"),
                        args[0]
                );
            } catch(MissingCharacterError | UnexpectedTokenError | IllegalCharacterError | SyntaxError e) {
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
                e.printStackTrace();
            } catch (UnknownError unknownError) {
                unknownError.printStackTrace();
            }
        } else System.out.println("ken");
    }
}
