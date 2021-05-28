package tw.kane.ken.node;

import tw.kane.ken.Token;
import tw.kane.ken.error.IllegalCharacterException;
import tw.kane.ken.error.MissingCharacterException;
import tw.kane.ken.error.SyntaxException;
import tw.kane.ken.error.UnexpectedTokenException;
import tw.kane.ken.function.BuiltInFunction;
import tw.kane.ken.function.Function;

import java.util.ArrayList;
import java.util.List;

import static tw.kane.ken.Token.TokenType.*;

public class RunFunctionNode extends Node {

    public Function function;
    public int end = 1, lParen = 0, rParen = 0, firstP = 0, finalP = 0;
    public ArrayList<Token> args = new ArrayList<>();

    public RunFunctionNode(ArrayList<Token> tokens, List<String> input) throws SyntaxException, IllegalCharacterException, MissingCharacterException, UnexpectedTokenException {

        for (Function func : BuiltInFunction.functions)
            if(func.getName().equals(tokens.get(0).value))
                function = func;

        if(function == null)
            throw new SyntaxException(
                    tokens.get(0).value,
                    input.get(tokens.get(0).position.row - 1),
                    tokens.get(0).position
            );

        for(int i = 0; i < tokens.size(); i++) {
            end++;
            if(tokens.get(i).type == LPAREN) {
                if(lParen == 0) {
                    if(i > 1)
                        throw new IllegalCharacterException(
                                tokens.get(i).value,
                                input.get(tokens.get(0).position.row - 1),
                                tokens.get(0).position
                        );
                    firstP = i;
                }
                lParen++;
            }
            if(tokens.get(i).type == RPAREN) {
                rParen++;
                finalP = i;
            }
            if(tokens.get(i).type == SEMICOLON)
                break;
            if(lParen == rParen && lParen > 0)
                break;
        }

        if(lParen > rParen)
            throw new MissingCharacterException(
                    RPAREN.getName(),
                    input.get(tokens.get(finalP).position.row - 1),
                    tokens.get(finalP).position
            );

        ArrayList<Token> arg = new ArrayList<>();
        for (Token t : tokens.subList(firstP + 1, finalP)) {
            if(t.type == COMMA) {
                if(arg.size() > 1)
                    throw new UnexpectedTokenException(
                            arg.get(1).value,
                            input.get(arg.get(1).position.row - 1),
                            arg.get(1).position
                    );
                if(arg.size() == 0)
                    throw new UnexpectedTokenException(
                            t.value,
                            input.get(t.position.row - 1),
                            t.position
                    );
                args.add(arg.get(0));
                arg = new ArrayList<>();
            }
            else arg.add(t);
        }

        if(arg.size() > 1)
            throw new UnexpectedTokenException(
                    arg.get(1).value,
                    input.get(arg.get(1).position.row - 1),
                    arg.get(1).position
            );
        args.add(arg.get(0));
    }

    @Override
    public Object execute() {
        return null;
    }
}
