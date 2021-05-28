package tw.kane.ken.error;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Position;

public class IllegalCharacterError extends Error {
    public IllegalCharacterError(String character, String line, Position position, ExecuteFile executeFile) {
        super("Illegal character", character, line, position, executeFile);
    }
}
