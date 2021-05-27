package tw.kane.ken;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static tw.kane.ken.Token.TokenType.*;

public class Lexer {
    Position position;
    List<String> input;
    ArrayList<Token> tokens;

    public Lexer(File file) throws IOException {
        position = new Position();
        tokens = new ArrayList<>();
        input = Files.readAllLines(file.toPath()).stream()
                .filter(x -> x.length() > 0)
                .collect(Collectors.toList());
    }

    public ArrayList<Token> parse() {

        if(position.row > input.size())
            return tokens;

        if(position.col > input.get(position.row - 1).length()) {
            position.jumpTo(1, position.row + 1);
            return parse();
        }

        if(isToken(SPACE)) {
            position.move(1, 0);
            return parse();
        }

        if(isToken(QUOTE)) {
            position.move(1, 0);
            tokens.add(new Token(STRING, makeString(new StringBuilder())));
            return parse();
        }

        if(isNumber()) {
            tokens.add(new Token(NUMBER, makeNumber(new StringBuilder())));
            return parse();
        }

        for (Token.TokenType token : values()) {
            if(isToken(token)) {
                tokens.add(new Token(token, getString(token.length())));
                position.move(token.length(), 0);
                return parse();
            }
        }

        position.move(1, 0);
        return parse();
    }

    public String makeNumber(StringBuilder stringBuilder) {
        if(isNumber()) {
            stringBuilder.append(getString(1));
            position.move(1, 0);
            return makeNumber(stringBuilder);
        }

        position.move(1, 0);
        return stringBuilder.toString();
    }

    public String makeString(StringBuilder stringBuilder) {
        if (getString(1).length() == 0) {
            System.out.println("err");
            return "";
        }
        if(isToken(ESCAPE)) {
            position.move(1, 0);
            stringBuilder.append(getString(1));
            return makeString(stringBuilder);
        }
        if(isToken(QUOTE)) {
            position.move(1, 0);
            return stringBuilder.toString();
        }
        stringBuilder.append(getString(1));
        position.move(1, 0);
        return makeString(stringBuilder);
    }

    public boolean isNumber() {
        return Arrays.asList(Token.DIGITS).contains(getString(1));
    }

    public boolean isToken(Token.TokenType token) {
        return getString(token.getName().length()).equals(token.getName());
    }

    public String getString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length; i++)
            try {
                stringBuilder.append(
                        input.get(position.row - 1),
                        position.col - 1 + i,
                        position.col + i);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        return stringBuilder.toString();
    }
}
