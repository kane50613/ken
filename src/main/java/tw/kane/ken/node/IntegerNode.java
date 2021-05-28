package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Token;

import java.io.IOException;
import java.util.ArrayList;

public class IntegerNode extends Node {
    public int integer;

    public IntegerNode(int integer, ArrayList<Token> tokens, ExecuteFile executeFile) throws IOException {
        super(tokens, executeFile);
        this.integer = integer;
    }

    public int getNumber() {
        return integer;
    }

    @Override
    public Object execute() {
        return integer;
    }
}
