package tw.kane.ken.node;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Token;
import tw.kane.ken.error.SyntaxError;
import tw.kane.ken.error.UnexpectedTokenError;
import tw.kane.ken.error.UnknownError;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Node {
    public abstract Object execute() throws SyntaxError, UnexpectedTokenError, IOException, UnknownError;

    public final ArrayList<Token> tokens;
    public final List<String> input;
    public final ExecuteFile executeFile;

    public Node(ArrayList<Token> tokens, ExecuteFile executeFile) throws IOException {
        this.tokens = tokens;
        this.executeFile = executeFile;
        input = Files.readAllLines(executeFile.getFile().toPath()).stream()
                .filter(x -> x.length() > 0)
                .collect(Collectors.toList());
    }
}
