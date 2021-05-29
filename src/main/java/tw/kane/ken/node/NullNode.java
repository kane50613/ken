package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Token;
import tw.kane.ken.error.SyntaxError;
import tw.kane.ken.error.UnexpectedTokenError;
import tw.kane.ken.error.UnknownError;

import java.io.IOException;
import java.util.ArrayList;

public class NullNode extends Node {
    public NullNode(ArrayList<Token> tokens, ExecuteFile executeFile) throws IOException {
        super(tokens, executeFile);
    }

    @Override
    public Object execute() throws SyntaxError, UnexpectedTokenError, IOException, UnknownError {
        return null;
    }
}
