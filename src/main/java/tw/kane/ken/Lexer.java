package tw.kane.ken;

import tw.kane.ken.error.IllegalCharacterException;
import tw.kane.ken.function.BuiltInFunction;
import tw.kane.ken.function.Function;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static tw.kane.ken.Token.TokenType.*;

public class Lexer {
    private final Position position;
    private final List<String> input;
    private final ArrayList<Token> tokens;

    public Lexer(ExecuteFile file) throws IOException {
        position = new Position();
        tokens = new ArrayList<>();
        input = Files.readAllLines(file.getFile().toPath()).stream()
                .filter(x -> x.length() > 0)
                .collect(Collectors.toList());
    }

    public ArrayList<Token> parse() throws IllegalCharacterException {
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
            tokens.add(new Token(STRING, makeString(new StringBuilder()), position));
            return parse();
        }

        if(isNumber()) {
            tokens.add(new Token(NUMBER, makeNumber(new StringBuilder()), position));
            return parse();
        }

        for(Token.TokenType token : values()) {
            if(isToken(token)) {
                tokens.add(new Token(token, getString(token.length()), position));
                position.move(token.length(), 0);
                return parse();
            }
        }

        for (Function function : BuiltInFunction.functions) {
            if(getString(function.getName().length()).equals(function.getName())) {
                tokens.add(new Token(METHOD, function.getName(), position));
                position.move(function.getName().length(), 0);
                return parse();
            }
        }

        throw new IllegalCharacterException(getString(1), input.get(position.row - 1), position);
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    private String makeNumber(StringBuilder stringBuilder) {
        if(isNumber()) {
            stringBuilder.append(getString(1));
            position.move(1, 0);
            return makeNumber(stringBuilder);
        }
        return stringBuilder.toString();
    }

    private String makeString(StringBuilder stringBuilder) throws IllegalCharacterException {
        if (getString(1).length() == 0)
            throw new IllegalCharacterException(
                    "\"",
                    input.get(position.row - 1),
                    new Position(position.col - stringBuilder.toString().length() - 1, position.row)
            );

        if(isToken(ESCAPE)) {
            position.move(1, 0);
            stringBuilder.append(getString(1));
            position.move(1, 0);
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

    private boolean isNumber() {
        return Arrays.asList(Token.DIGITS).contains(getString(1));
    }

    private boolean isToken(Token.TokenType token) {
        return getString(token.getName().length()).equals(token.getName());
    }

    private String getString(int length) {
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
