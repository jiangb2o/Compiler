package parser.token;

public class Decimal extends Token {
    private final double value;

    public Decimal(ETokenType tokenType, String tokenString) {
        super(tokenType, tokenString);
        value = Double.parseDouble(tokenString);
    }

    /**
     * Get value of decimal
     * @return value
     */
    public double getValue() {
        return value;
    }
}