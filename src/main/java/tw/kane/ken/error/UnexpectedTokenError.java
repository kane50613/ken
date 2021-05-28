package tw.kane.ken.error;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Position;

public class UnexpectedTokenError extends Error {
    public UnexpectedTokenError(String character, String line, Position position, ExecuteFile executeFile) {
        super("Unexpected Token", character, line, position, executeFile);
    }
}
