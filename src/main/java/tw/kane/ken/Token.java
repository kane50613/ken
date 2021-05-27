package tw.kane.ken;

public class Token {
    public static String[] DIGITS = {"0","1","2","3","4","5","6","7","8","9"};

    public static enum tokens {
        SPACE(" "),
        LPAREN("("),
        RPAREN(")"),
        QUOTE("\""),
        SINGLE_QUOTE("'"),
        ESCAPE("\\"),
        PRINT("print");

        private final String name;

        tokens(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
