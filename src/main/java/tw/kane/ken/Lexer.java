package tw.kane.ken;

import tw.kane.ken.error.IllegalCharacterError;
import tw.kane.ken.function.BuiltInFunction;
import tw.kane.ken.function.Function;

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
    private final ExecuteFile executeFile;
    private final ArrayList<Token> tokens;

    public Lexer(ExecuteFile file) throws IOException {
        position = new Position();
        tokens = new ArrayList<>();
        executeFile = file;
        input = Files.readAllLines(file.getFile().toPath()).stream()
                .filter(x -> x.length() > 0)
                .collect(Collectors.toList());
    }

    public ArrayList<Token> parse() throws IllegalCharacterError {
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
            String string = makeString(new StringBuilder());
            tokens.add(new Token(STRING, string, position));
            position.move(string.length(), 0);
            return parse();
        }

        if(isNumber("")) {
            String number = makeNumber(new StringBuilder());
            tokens.add(new Token(number.contains(".") ? FLOAT : INTEGER, number, position));
            position.move(number.length(), 0);
            return parse();
        }

        if(isToken(LET)) {
            tokens.add(new Token(LET, LET.getName(), position));
            position.move(LET.length(), 0);
            String name = makeVariable(new StringBuilder());

            //check if name already exist or is token word
            for(Token.TokenType token : values())
                if(name.equals(token.getName()))
                    throw new IllegalCharacterError(
                            name,
                            input.get(position.row - 1),
                            position,
                            executeFile
                    );

            for (Function function : BuiltInFunction.functions)
                if(name.equals(function.getName()))
                    throw new IllegalCharacterError(
                            name,
                            input.get(position.row - 1),
                            position,
                            executeFile
                    );

            tokens.add(new Token(VARIABLE, name, position));
            position.move(name.length(), 0);
            return parse();
        }

        for(Token.TokenType token : values())
            if(isToken(token)) {
                tokens.add(new Token(token, getString(token.length()), position));
                position.move(token.length(), 0);
                return parse();
            }

        for (Function function : BuiltInFunction.functions)
            if(getString(function.getName().length()).equals(function.getName())) {
                tokens.add(new Token(METHOD, function.getName(), position));
                position.move(function.getName().length(), 0);
                return parse();
            }

        throw new IllegalCharacterError(
                getString(1),
                input.get(position.row - 1),
                position,
                executeFile
        );
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    private String makeNumber(StringBuilder stringBuilder) {
        if(isNumber(stringBuilder.toString())) {
            stringBuilder.append(getString(1));
            position.move(1, 0);
            return makeNumber(stringBuilder);
        }
        position.back(stringBuilder.toString().length(), 0);
        return stringBuilder.toString();
    }

    private String makeString(StringBuilder stringBuilder) throws IllegalCharacterError {
        if (getString(1).length() == 0)
            throw new IllegalCharacterError(
                    "\"",
                    input.get(position.row - 1),
                    new Position(position.col - stringBuilder.toString().length() - 1, position.row),
                    executeFile
            );

        if(isToken(ESCAPE)) {
            position.move(1, 0);
            stringBuilder.append(getString(1));
            position.move(1, 0);
            return makeString(stringBuilder);
        }
        if(isToken(QUOTE)) {
            position.back(stringBuilder.toString().length(), 0);
            return stringBuilder.toString();
        }

        stringBuilder.append(getString(1));
        position.move(1, 0);
        return makeString(stringBuilder);
    }

    public String makeVariable(StringBuilder stringBuilder) {
        if(!getString(1).equals(SPACE.getName()) && getString(1).length() > 0) {
            stringBuilder.append(getString(1));
            position.move(1, 0);
            return makeVariable(stringBuilder);
        }
        position.back(stringBuilder.toString().length(), 0);
        return stringBuilder.toString();
    }

    private boolean isNumber(String num) {
        return Arrays.asList(Token.DIGITS).contains(getString(1)) ||
                getString(1).equals(".") ||
                (
                        num.length() == 0 && getString(1).equals("-") &&
                        (
                                tokens.get(tokens.size() - 1) == null ||
                                        (tokens.get(tokens.size() - 1).type != INTEGER && tokens.get(tokens.size() - 1).type != FLOAT)
                        ) &&
                                Arrays.asList(Token.DIGITS).contains(getString(2).substring(1))
                );
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
