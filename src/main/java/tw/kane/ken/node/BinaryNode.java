package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Token;
import tw.kane.ken.error.SyntaxError;
import tw.kane.ken.error.UnexpectedTokenError;
import tw.kane.ken.error.UnknownError;

import java.io.IOException;
import java.util.ArrayList;

import static tw.kane.ken.Token.TokenType.*;

public class BinaryNode extends Node {

    public final Node leftNode, rightNode;
    public final Token operator;
    public final ArrayList<Node> tokenNodes;

    public BinaryNode(Node leftNode, Token operator, Node rightNode, ArrayList<Node> tokenNodes, ArrayList<Token> tokens, ExecuteFile executeFile) throws IOException {
        super(tokens, executeFile);
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.operator = operator;
        this.tokenNodes = tokenNodes;
    }

    @Override
    public Object execute() throws SyntaxError, UnexpectedTokenError, IOException, UnknownError {
        int leftI, rightI;

        Object resultL = leftNode.execute(), resultR = rightNode.execute();

        if(resultL instanceof Integer)
            leftI = (int) resultL;
        else {
            if(leftNode instanceof TokenNode)
                throw new SyntaxError(
                        ((Token) leftNode.execute()).value,
                        input.get(((Token) leftNode.execute()).position.row - 1),
                        ((Token) leftNode.execute()).position,
                        executeFile
                );
            throw new UnknownError("Wrong usage of operator");
        }

        if(resultR instanceof Integer)
            rightI = (int) resultR;
        else {
            if(rightNode instanceof TokenNode)
                throw new SyntaxError(
                        ((Token) rightNode.execute()).value,
                        input.get(((Token) rightNode.execute()).position.row - 1),
                        ((Token) rightNode.execute()).position,
                        executeFile
                );
            throw new UnknownError("Wrong usage of operator");
        }

        if(operator.type == PLUS)
            return leftI + rightI;
        if(operator.type == MINUS)
            return leftI - rightI;
        if(operator.type == MULTIPLE)
            return leftI * rightI;
        if(operator.type == DIV)
            return leftI / rightI;

        throw new UnexpectedTokenError(
                operator.value,
                input.get(operator.position.row - 1),
                operator.position,
                executeFile
        );
    }
}
