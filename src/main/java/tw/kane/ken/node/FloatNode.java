package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Token;

import java.io.IOException;
import java.util.ArrayList;

public class FloatNode extends Node {
    float _float;

    public FloatNode(float _float, ArrayList<Token> tokens, ExecuteFile executeFile) throws IOException {
        super(tokens, executeFile);
        this._float = _float;
    }

    public float getNumber() {
        return _float;
    }

    @Override
    public Object execute() {
        return _float;
    }
}

