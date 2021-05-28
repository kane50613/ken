package tw.kane.ken.error;

import tw.kane.ken.ExecuteFile;
import tw.kane.ken.Position;

public abstract class Error extends Exception {
    public String character, line;
    public Position position;
    public ExecuteFile executeFile;

    public Error(String message, String character, String line, Position position, ExecuteFile executeFile) {
        super(message);
        this.character = character;
        this.line = line;
        this.position = position;
        this.executeFile = executeFile;
    }
}
