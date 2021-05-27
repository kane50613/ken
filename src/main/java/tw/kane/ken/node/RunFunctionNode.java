package tw.kane.ken.node;

import tw.kane.ken.Token;
import tw.kane.ken.error.SyntaxException;
import tw.kane.ken.function.BuiltInFunction;
import tw.kane.ken.function.Function;

import java.util.ArrayList;
import java.util.List;

public class RunFunctionNode extends Node {

    private Function function;
    private final List<String> input;

    public RunFunctionNode(ArrayList<Token> tokens, List<String> input) throws SyntaxException {
        this.input = input;

        for (Function func : BuiltInFunction.functions)
            if(func.getName().equals(tokens.get(0).value))
                function = func;

        if(function == null)
            throw new SyntaxException(
                    tokens.get(0).value,
                    input.get(tokens.get(0).position.row - 1),
                    tokens.get(0).position
            );
    }

    @Override
    public Object execute() {
        return null;
    }
}
