package tw.kane.ken;

import tw.kane.ken.error.SyntaxException;
import tw.kane.ken.node.FunctionNode;
import tw.kane.ken.node.Node;
import tw.kane.ken.node.RunFunctionNode;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static tw.kane.ken.Token.TokenType.*;

public class Parser {

    private final ArrayList<Token> tokens;
    private final ArrayList<Node> nodes;

    private List<String> input;

    public Parser(ArrayList<Token> tokens, ExecuteFile file) throws IOException {
        this.tokens = tokens;
        nodes = new ArrayList<>();
        input = Files.readAllLines(file.getFile().toPath()).stream()
                .filter(x -> x.length() > 0)
                .collect(Collectors.toList());
    }

    public void parse() throws SyntaxException {
        if(tokens.get(0).type == METHOD)
            nodes.add(new RunFunctionNode(tokens, input));
    }

    public boolean isToken(Token.TokenType token) {
        return token == tokens.get(0).type;
    }

    public void shift() {
        tokens.remove(0);
    }
}
