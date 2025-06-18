package exceptions;

public class IllegalDecimalException extends LexicalException {
    public IllegalDecimalException() {
        this("Decimal error.");
    }

    public IllegalDecimalException(String var1) {
        super(var1);
    }
}
