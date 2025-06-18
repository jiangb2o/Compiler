package exceptions;

public class IllegalDigitLengthException extends LexicalException {
    public IllegalDigitLengthException() {
        this("Digit Length Exceeded 12.");
    }

    public IllegalDigitLengthException(String var1) {
        super(var1);
    }
}
