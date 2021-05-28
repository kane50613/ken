package tw.kane.ken;

import tw.kane.ken.error.IllegalCharacterException;
import tw.kane.ken.error.MissingCharacterException;
import tw.kane.ken.error.SyntaxException;
import tw.kane.ken.error.UnexpectedTokenException;
import tw.kane.ken.node.Node;
import tw.kane.ken.node.RunFunctionNode;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static tw.kane.ken.Token.TokenType.METHOD;

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

    public void parse() throws SyntaxException, IllegalCharacterException, MissingCharacterException, UnexpectedTokenException {
        if(tokens.get(0).type == METHOD) {
            RunFunctionNode node = new RunFunctionNode(tokens, input);
            for(int i = 0; i < node.end; i++)
                shift();
            nodes.add(node);

            System.out.println("print");
            for (Token arg : node.args) {
                System.out.println(arg.type +": "+ arg.value);
            }
        }
    }

    public boolean isToken(Token.TokenType token) {
        return token == tokens.get(0).type;
    }

    public void shift() {
        tokens.remove(0);
    }
}
