package parser.token;

public class Bool extends Token {
    private final boolean value;

    public Bool(ETokenType tokenType, String tokenString) {
        super(tokenType, tokenString);
        value = tokenString.equals("true");
    }

    /**
     * Get value of bool
     * @return value
     */
    public boolean getValue() {
        return value;
    }
}
