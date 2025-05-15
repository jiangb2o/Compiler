package parser.token;

import parser.Symbol;

public class Token implements Symbol {
    protected ETokenType tokenType;
    // A token type may have multiple tokens, use token string to confirm.
    protected String tokenString;

    public Token(ETokenType tokenType, String tokenString) {
        this.tokenType = tokenType;
        this.tokenString = tokenString;
    }

    /**
     * Get type of token
     * @return Type of token
     */
    public ETokenType getTokenType(){
        return tokenType;
    }

    /**
     * Get string of token
     * @return String of token
     */
    public String getTokenString() {
        return tokenString;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
