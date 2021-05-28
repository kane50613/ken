package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Token;
import tw.kane.ken.error.IllegalCharacterError;
import tw.kane.ken.error.MissingCharacterError;
import tw.kane.ken.error.SyntaxError;
import tw.kane.ken.error.UnexpectedTokenError;
import tw.kane.ken.function.BuiltInFunction;
import tw.kane.ken.function.Function;

import java.util.ArrayList;
import java.util.List;

import static tw.kane.ken.Token.TokenType.*;

public class RunFunctionNode extends Node {

    public Function function;
    public int end = 1, lParen = 0, rParen = 0, firstP = 0, finalP = 0;
    public ArrayList<ArrayList<Token>> args = new ArrayList<>();
    public ExecuteFile executeFile;

    public RunFunctionNode(ArrayList<Token> tokens, List<String> input, ExecuteFile executeFile) throws SyntaxError, IllegalCharacterError, MissingCharacterError, UnexpectedTokenError {
        this.executeFile = executeFile;

        for (Function func : BuiltInFunction.functions)
            if(func.getName().equals(tokens.get(0).value))
                function = func;

        if(function == null)
            throw new SyntaxError(
                    tokens.get(0).value,
                    input.get(tokens.get(0).position.row - 1),
                    tokens.get(0).position,
                    executeFile
            );

        for(int i = 0; i < tokens.size(); i++) {
            end++;
            if(tokens.get(i).type == LPAREN) {
                if(lParen == 0) {
                    if(i > 1)
                        throw new IllegalCharacterError(
                                tokens.get(i).value,
                                input.get(tokens.get(0).position.row - 1),
                                tokens.get(0).position,
                                executeFile
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
            throw new MissingCharacterError(
                    RPAREN.getName(),
                    input.get(tokens.get(finalP).position.row - 1),
                    tokens.get(finalP).position,
                    executeFile
            );

        ArrayList<Token> arg = new ArrayList<>();
        for (Token t : tokens.subList(firstP + 1, finalP)) {
            if(t.type == COMMA) {
                if(arg.size() == 0)
                    throw new UnexpectedTokenError(
                            t.value,
                            input.get(t.position.row - 1),
                            t.position,
                            executeFile
                    );
                args.add(arg);
                arg = new ArrayList<>();
            }
            else arg.add(t);
        }

        if(arg.size() == 0)
            throw new UnexpectedTokenError(
                    tokens.get(finalP - 1).value,
                    input.get(tokens.get(finalP - 1).position.row - 1),
                    tokens.get(finalP - 1).position,
                    executeFile
            );
        args.add(arg);
    }

    @Override
    public Object execute() {
//        return function.execute(args.stream().map(x -> {
//            if(x.type == INTEGER)
//                return Integer.parseInt(x.value);
//            if(x.type == FLOAT)
//                return Float.parseFloat(x.value);
//            if(x.type == TRUE || x.type == FALSE)
//                return Boolean.parseBoolean(x.value);
//            if(x.type == STRING)
//                return String.valueOf(x.value);
//            return null;
//        }).toArray());
        return null;
    }
}
