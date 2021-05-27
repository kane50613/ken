package tw.kane.ken;

import java.util.ArrayList;

public class Lexer {
    Position position;
    String input;
    ArrayList<Token> tokens;

    public Lexer(String input) {
        this.position = new Position();
        tokens = new ArrayList<>();
    }

    public void parse() {

    }

    public void makeNumber() {

    }

    public void makeString() {

    }

    public String getString(int length) {
        return input.substring(position.index, length);
    }
}
