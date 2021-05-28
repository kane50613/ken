package tw.kane.ken;

import tw.kane.ken.error.IllegalCharacterError;
import tw.kane.ken.error.MissingCharacterError;
import tw.kane.ken.error.SyntaxError;
import tw.kane.ken.error.UnexpectedTokenError;
import tw.kane.ken.node.Node;
import tw.kane.ken.node.RunFunctionNode;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static tw.kane.ken.Token.TokenType.*;

public class Parser {

    public final ArrayList<Node> nodes;

    private final ArrayList<Token> tokens;
    private final List<String> input;
    private final ExecuteFile executeFile;

    public Parser(ArrayList<Token> tokens, ExecuteFile file) throws IOException {
        this.tokens = tokens;
        nodes = new ArrayList<>();
        executeFile = file;
        input = Files.readAllLines(file.getFile().toPath()).stream()
                .filter(x -> x.length() > 0)
                .collect(Collectors.toList());
    }

    public void parse() throws SyntaxError, IllegalCharacterError, MissingCharacterError, UnexpectedTokenError, IOException {
        if(tokens.get(0).type == METHOD) {
            RunFunctionNode node = new RunFunctionNode(tokens, input, executeFile);
            for(int i = 0; i < node.end; i++)
                shift();
            nodes.add(node);
        }
    }

    public boolean isToken(Token.TokenType token) {
        return token == tokens.get(0).type;
    }

    public void shift() {
        tokens.remove(0);
    }
}
