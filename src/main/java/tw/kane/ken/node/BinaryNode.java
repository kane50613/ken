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
            if(tokenNodes.get(0) instanceof TokenNode)
                throw new SyntaxError(
                        ((Token) tokenNodes.get(0).execute()).value,
                        input.get(((Token) tokenNodes.get(0).execute()).position.row - 1),
                        ((Token) tokenNodes.get(0).execute()).position,
                        executeFile
                );
            throw new UnknownError("Wrong usage of operator");
        }

        if(resultR instanceof Integer)
            rightI = (int) resultR;
        else {
            if(tokenNodes.get(1) instanceof TokenNode)
                throw new SyntaxError(
                        ((Token) tokenNodes.get(1).execute()).value,
                        input.get(((Token) tokenNodes.get(1).execute()).position.row - 1),
                        ((Token) tokenNodes.get(1).execute()).position,
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
                tokens.get(1).value,
                input.get(tokens.get(1).position.row - 1),
                tokens.get(1).position,
                executeFile
        );
    }
}
