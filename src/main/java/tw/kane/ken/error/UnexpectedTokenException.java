package tw.kane.ken.error;

import tw.kane.ken.Position;

public class UnexpectedTokenException extends Exception {
    public String character, line;
    public Position position;

    public UnexpectedTokenException(String character, String line, Position position) {
        this.character = character;
        this.line = line;
        this.position = position;
    }
}
