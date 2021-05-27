package tw.kane.ken.error;

import tw.kane.ken.Position;

public class IllegalCharacterException extends Exception {
    public IllegalCharacterException(String character, Position position) {
        super(String.format(
                "Illegal character usage %s at %d:%d",
                character,
                position.row,
                position.col
        ));
    }
}
