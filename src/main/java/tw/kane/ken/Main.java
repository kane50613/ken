package tw.kane.ken;

import tw.kane.ken.error.IllegalCharacterException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0) {
            try {
                ExecuteFile.init(args[0]);
                new Parser(
                        new Lexer(ExecuteFile.getFile()).parse()
                ).parse();
            } catch (IOException | IllegalCharacterException e) {
                e.printStackTrace();
            }
        }
    }
}
