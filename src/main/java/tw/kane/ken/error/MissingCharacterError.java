package tw.kane.ken.error;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Position;

public class MissingCharacterError extends Error {
    public MissingCharacterError(String character, String line, Position position, ExecuteFile executeFile) {
        super("Missing Character", character, line, position, executeFile);
    }
}
