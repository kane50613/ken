package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Token;

import java.io.IOException;
import java.util.ArrayList;

public class BooleanNode extends Node {
    boolean bool;

    public BooleanNode(boolean bool, ArrayList<Token> tokens, ExecuteFile executeFile) throws IOException {
        super(tokens, executeFile);
        this.bool = bool;
    }

    @Override
    public Object execute() {
        return bool;
    }
}
