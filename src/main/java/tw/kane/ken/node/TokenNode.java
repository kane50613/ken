package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Token;

import java.io.IOException;
import java.util.ArrayList;

public class TokenNode extends Node {

    public Token token;

    public TokenNode(Token token, ArrayList<Token> tokens, ExecuteFile executeFile) throws IOException {
        super(tokens, executeFile);
        this.token = token;
    }

    @Override
    public Object execute() {
        return token;
    }
}
