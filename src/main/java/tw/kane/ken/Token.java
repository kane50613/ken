package tw.kane.ken;

public class Token {

    public TokenType type;
    public String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public static String[] DIGITS = {"0","1","2","3","4","5","6","7","8","9"};

    public enum TokenType {
        SPACE(" "),
        LPAREN("("),
        RPAREN(")"),
        QUOTE("\""),
        SINGLE_QUOTE("'"),
        ESCAPE("\\"),
        NEW_LINE("\n"),
        PRINT("print"),
        STRING("STRING"),
        NUMBER("NUMBER");

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
