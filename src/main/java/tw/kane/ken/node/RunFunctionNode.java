package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.ParenParser;
import tw.kane.ken.Token;
import tw.kane.ken.error.MissingCharacterError;
import tw.kane.ken.error.SyntaxError;
import tw.kane.ken.error.UnexpectedTokenError;
import tw.kane.ken.error.UnknownError;
import tw.kane.ken.function.BuiltInFunction;
import tw.kane.ken.function.Function;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static tw.kane.ken.Token.TokenType.*;

public class RunFunctionNode extends Node {

    public Function function;
    public int end = 1, lParen = 0, rParen = 0, firstP = -1, finalP = -1;
    public ArrayList<ArrayList<Token>> args = new ArrayList<>();
    public ExecuteFile executeFile;

    public RunFunctionNode(ArrayList<Token> tokens, List<String> input, ExecuteFile executeFile) throws SyntaxError, MissingCharacterError, UnexpectedTokenError, IOException {
        super(tokens, executeFile);
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

//        if(tokens.get(1).type != LPAREN)
//            throw new UnexpectedTokenError(
//                    tokens.get(1).value,
//                    input.get(tokens.get(1).position.row - 1),
//                    tokens.get(1).position,
//                    executeFile
//            );

        for(int i = 0; i < tokens.size(); i++) {
            end++;
            if(tokens.get(i).type == LPAREN) {
                if(firstP == -1) {
                    if(i > 1)
                        throw new UnexpectedTokenError(
                                tokens.get(i).value,
                                input.get(tokens.get(i).position.row - 1),
                                tokens.get(i).position,
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
                    input.get(tokens.get(lParen).position.row - 1),
                    tokens.get(lParen).position,
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
            return;

        args.add(arg);

        if(tokens.size() > end - 1 && tokens.get(end - 1).type == SEMICOLON)
            end++;
    }

    @Override
    public Object execute() throws UnexpectedTokenError, SyntaxError, IOException, UnknownError {
        ArrayList<Object> executeArgs = new ArrayList<>();
        for (ArrayList<Token> arg : args) {
            ParenParser parenParser = new ParenParser(arg, input, executeFile);
            executeArgs.add(parenParser.parse().execute());
        }

        return function.execute(executeArgs.toArray());
    }
}
