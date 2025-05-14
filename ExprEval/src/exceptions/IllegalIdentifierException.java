package exceptions;

public class IllegalIdentifierException extends LexicalException {
    public IllegalIdentifierException() {
        this("Not a predefined identifier.");
    }

    public IllegalIdentifierException(String var1) {
        super(var1);
    }
}
