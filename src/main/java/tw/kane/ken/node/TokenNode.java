package tw.kane.ken.node;

import tw.kane.ken.Token;

public class TokenNode extends Node {

    public Token token;

    public TokenNode(Token token) {
        this.token = token;
    }

    @Override
    public Object execute() {
        return token;
    }
}
