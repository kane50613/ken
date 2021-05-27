package tw.kane.ken;

public class Token {

    public TokenType type;
    public String value;
    public Position position;

    public Token(TokenType type, String value, Position position) {
        this.type = type;
        this.value = value;
        this.position = new Position();
        this.position.jumpTo(position.col, position.row);
    }

    public static String[] DIGITS = {"0","1","2","3","4","5","6","7","8","9"};

    public enum TokenType {
        SPACE(" "),
        LPAREN("("),
        RPAREN(")"),
        MID_LPAREN("["),
        MID_RPAREN("]"),
        BIG_LPAREN("{"),
        BIG_RPAREN("}"),
        QUOTE("\""),
        SINGLE_QUOTE("'"),
        COMMA(","),
        SEMICOLON(";"),
        ESCAPE("\\"),
        NEW_LINE("\n"),
        STRING("STRING"),
        NUMBER("NUMBER"),
        TRUE("true"),
        FALSE("false"),
        FUNCTION("function"),
        IF("if"),
        ELSE("else"),
        METHOD("method");

        private final String name;

        TokenType(String name) {
            this.name = name;
        }

        public int length() {
            return name.length();
        }

        public String getName() {
            return name;
        }
    }
}
