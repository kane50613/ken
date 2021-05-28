package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Token;

import java.io.IOException;
import java.util.ArrayList;

public class StringNode extends Node {

    public String string;

    public StringNode(String string, ArrayList<Token> tokens, ExecuteFile executeFile) throws IOException {
        super(tokens, executeFile);
        this.string = string;
    }

    @Override
    public Object execute() {
        return string;
    }
}
