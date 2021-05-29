package tw.kane.ken;

import tw.kane.ken.error.SyntaxError;
import tw.kane.ken.error.UnexpectedTokenError;
import tw.kane.ken.error.UnknownError;
import tw.kane.ken.node.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static tw.kane.ken.Token.TokenType.*;

public class ParenParser {

    private final ArrayList<Token> tokens;
    private ArrayList<Node> tokenNodes;
    private final ExecuteFile executeFile;
    private final List<String> input;

    public ParenParser(ArrayList<Token> tokens, List<String> input, ExecuteFile executeFile) throws IOException {
        this.executeFile = executeFile;
        this.tokens = tokens;
        this.input = input;
        tokenNodes = new ArrayList<>();

        for(int i = 0; i < tokens.size(); i++)
            tokenNodes.add(new TokenNode(
                    tokens.get(i),
                    new ArrayList<>(tokens.subList(i, i+1)),
                    executeFile
            ));
    }

    public ParenParser(ArrayList<Node> tokenNodes, ArrayList<Token> tokens, List<String> input, ExecuteFile executeFile) {
        this.executeFile = executeFile;
        this.input = input;
        this.tokens = tokens;
        this.tokenNodes = tokenNodes;
    }

    public Node parse() throws IOException, UnexpectedTokenError, SyntaxError, UnknownError {
        if(tokenNodes.size() == 0)
            return new NullNode(new ArrayList<>(), executeFile);
        int lParen, rParen;

        do {
            lParen = -1;
            rParen = -1;

            for(int i = 0; i < tokenNodes.size(); i++) {
                if(!(tokenNodes.get(i) instanceof TokenNode))
                    continue;
                if(((Token) tokenNodes.get(i).execute()).type == LPAREN)
                    lParen = i;
                if(((Token) tokenNodes.get(i).execute()).type == RPAREN) {
                    rParen = i;
                    break;
                }
            }

            if(lParen != -1 && rParen != -1) {
                ArrayList<Node> _node = new ArrayList<>();
                _node.add(new ParenParser(new ArrayList<>(tokenNodes.subList(lParen + 1, rParen)), tokens, input, executeFile).parse());
                tokenNodes = replaceArrayFromTo(
                        tokenNodes,
                        lParen,
                        rParen,
                        _node
                );
            }
        } while(lParen != -1 || rParen != -1);

        if(tokenNodes.size() == 1)
            return tokenNodes.get(0) instanceof TokenNode ?
                    makeNode((Token) tokenNodes.get(0).execute()) :
                    tokenNodes.get(0);

        int plusOrMinus = -1;
        for(int i = 0; i < tokenNodes.size(); i++) {
            if(!(tokenNodes.get(i) instanceof TokenNode))
                continue;
            if(((Token) tokenNodes.get(i).execute()).type == PLUS || ((Token) tokenNodes.get(i).execute()).type == MINUS)
                plusOrMinus = i;
        }
        if(plusOrMinus != -1)
            return makeBinaryNode(plusOrMinus);

        int mulOrDiv = -1;
        for(int i = 0; i < tokenNodes.size(); i++) {
            if(!(tokenNodes.get(i) instanceof TokenNode))
                continue;
            if(((Token) tokenNodes.get(i).execute()).type == MULTIPLE || ((Token) tokenNodes.get(i).execute()).type == DIV)
                mulOrDiv = i;
        }
        if(mulOrDiv != -1)
            return makeBinaryNode(mulOrDiv);

        if(tokenNodes.size() > 0 && tokenNodes.get(1) instanceof TokenNode)
            throw new UnexpectedTokenError(
                    ((Token) tokenNodes.get(1).execute()).value,
                    input.get(((Token) tokenNodes.get(1).execute()).position.row - 1),
                    ((Token) tokenNodes.get(1).execute()).position,
                    executeFile
            );
        throw new UnknownError("Unknown node error");
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

    public BinaryNode makeBinaryNode(int position) throws IOException, UnexpectedTokenError, SyntaxError, UnknownError {
        return new BinaryNode(
                new ParenParser(new ArrayList<>(tokenNodes.subList(0, position)), tokens, input, executeFile).parse(),
                (Token) tokenNodes.get(position).execute(),
                new ParenParser(new ArrayList<>(tokenNodes.subList(position + 1, tokenNodes.size())), tokens, input, executeFile).parse(),
                tokenNodes,
                tokens,
                executeFile
        );
    }

    public <T> ArrayList<T> replaceArrayFromTo(ArrayList<T> array, int start, int end, ArrayList<T> replace) {
        ArrayList<T> _array = new ArrayList<>();
        for (int i = 0; i < start; i++)
            _array.add(array.get(i));
        _array.addAll(replace);
        for (int i = end + 1; i <= array.size() - 1; i++)
            _array.add(array.get(i));
        return _array;
    }
}
