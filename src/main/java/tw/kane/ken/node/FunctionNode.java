package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.ParenParser;
import tw.kane.ken.Token;

import java.io.IOException;
import java.util.ArrayList;

public class FunctionNode extends Node {
    public FunctionNode(ArrayList<Token> tokens, ExecuteFile executeFile) throws IOException {
        super(tokens, executeFile);

    }

    @Override
    public Object execute() {
        return null;
    }
}
