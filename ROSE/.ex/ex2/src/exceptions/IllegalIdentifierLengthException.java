package exceptions;

public class IllegalIdentifierLengthException extends LexicalException {
    public IllegalIdentifierLengthException() {
        this("Identifier Length Exceeded 24.");
    }

    public IllegalIdentifierLengthException(String var1) {
        super(var1);
    }
}
