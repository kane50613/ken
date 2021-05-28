package tw.kane.ken.error;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Position;

public class SyntaxError extends Error {
    public SyntaxError(String character, String line, Position position, ExecuteFile executeFile) {
        super("Syntax Error", character, line, position, executeFile);
    }
}
