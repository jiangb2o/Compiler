package exceptions;

public class IllegalDecimalException extends LexicalException {
    public IllegalDecimalException() {
        this("Malformed decimal constant.");
    }

    public IllegalDecimalException(String var1) {
        super(var1);
    }
}
