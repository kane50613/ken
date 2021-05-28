package tw.kane.ken;

import tw.kane.ken.error.UnexpectedTokenError;
import tw.kane.ken.node.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static tw.kane.ken.Token.TokenType.*;

public class ParenParser {

    private final ArrayList<Token> tokens;
    private final ExecuteFile executeFile;
    private final List<String> input;

    public ParenParser(ArrayList<Token> tokens, List<String> input, ExecuteFile executeFile) {
        this.executeFile = executeFile;
        this.tokens = tokens;
        this.input = input;
    }

    public Node parse() throws IOException, UnexpectedTokenError {
        if(tokens.size() == 1)
            return makeNode(tokens.get(0));

        int plusOrMinus = -1;
        for(int i = 0; i < tokens.size(); i++)
            if(tokens.get(i).type == PLUS || tokens.get(i).type == MINUS)
                plusOrMinus = i;
        if(plusOrMinus != -1)
            return makeBinaryNode(plusOrMinus);

        int mulOrDiv = -1;
        for(int i = 0; i < tokens.size(); i++)
            if(tokens.get(i).type == MULTIPLE || tokens.get(i).type == DIV)
                mulOrDiv = i;
        if(mulOrDiv != -1)
            return makeBinaryNode(mulOrDiv);

        throw new UnexpectedTokenError(
                tokens.get(1).value,
                input.get(tokens.get(1).position.row - 1),
                tokens.get(1).position,
                executeFile
        );
    }

    public Node makeNode(Token token) throws IOException, UnexpectedTokenError {
        ArrayList<Token> _tokens = new ArrayList<>();
        _tokens.add(token);
        if(token.type == INTEGER)
            return new IntegerNode(Integer.parseInt(token.value), _tokens, executeFile);
        if(token.type == FLOAT)
            return new FloatNode(Float.parseFloat(token.value), _tokens, executeFile);
        if(token.type == STRING)
            return new StringNode(token.value, _tokens, executeFile);
        if(token.type == TRUE || token.type == FALSE)
            return new BooleanNode(Boolean.parseBoolean(token.value), _tokens, executeFile);
        throw new UnexpectedTokenError(
                token.value,
                input.get(token.position.row - 1),
                token.position,
                executeFile
        );
    }

    public BinaryNode makeBinaryNode(int position) throws IOException, UnexpectedTokenError {
        return new BinaryNode(
                new ParenParser(new ArrayList<>(tokens.subList(0, position)), input, executeFile).parse(),
                tokens.get(1),
                new ParenParser(new ArrayList<>(tokens.subList(position + 1, tokens.size())), input, executeFile).parse(),
                tokens,
                executeFile
        );
    }
}
