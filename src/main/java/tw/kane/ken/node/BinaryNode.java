package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Token;
import tw.kane.ken.error.SyntaxError;
import tw.kane.ken.error.UnexpectedTokenError;

import java.io.IOException;
import java.util.ArrayList;

import static tw.kane.ken.Token.TokenType.*;

public class BinaryNode extends Node {

    public final Node leftNode, rightNode;
    public final Token operator;

    public BinaryNode(Node leftNode, Token operator, Node rightNode, ArrayList<Token> tokens, ExecuteFile executeFile) throws IOException {
        super(tokens, executeFile);
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.operator = operator;
    }

    @Override
    public Object execute() throws SyntaxError, UnexpectedTokenError, IOException {
        int leftI, rightI;

        Object resultL = leftNode.execute(), resultR = rightNode.execute();

        if(resultL instanceof Integer)
            leftI = (int) resultL;
        else throw new SyntaxError(
                tokens.get(0).value,
                input.get(tokens.get(0).position.row - 1),
                tokens.get(0).position,
                    executeFile
            );

        if(resultR instanceof Integer)
            rightI = (int) resultR;
        else throw new SyntaxError(
                tokens.get(1).value,
                input.get(tokens.get(1).position.row - 1),
                tokens.get(1).position,
                executeFile
        );

        if(operator.type == PLUS)
            return leftI + rightI;
        if(operator.type == MINUS)
            return leftI - rightI;
        if(operator.type == MULTIPLE)
            return leftI * rightI;
        if(operator.type == DIV)
            return leftI / rightI;

        throw new UnexpectedTokenError(
                tokens.get(1).value,
                input.get(tokens.get(1).position.row - 1),
                tokens.get(1).position,
                executeFile
        );
    }
}
