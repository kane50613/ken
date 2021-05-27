package tw.kane.ken;

import tw.kane.ken.error.IllegalCharacterException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0) {
            try {
                Lexer lexer = new Lexer(new ExecuteFile(args[0]));
                lexer.parse().forEach(x ->
                    System.out.println(x.type +": "+ x.value)
                );
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
            }
        } else System.out.println("ken");
    }
}
