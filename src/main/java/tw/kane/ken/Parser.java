package tw.kane.ken;

import java.util.ArrayList;

import static tw.kane.ken.Token.TokenType.*;

public class Parser {

    private final ArrayList<Token> tokens;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() {

    }

    public boolean isToken(Token.TokenType token) {
        return token == tokens.get(0).type;
    }

    public void shift() {
        tokens.remove(0);
    }
}
