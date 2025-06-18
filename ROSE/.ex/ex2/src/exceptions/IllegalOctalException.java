package exceptions;

public class IllegalOctalException extends LexicalException {
    public IllegalOctalException() {
        this("Octal error.");
    }

    public IllegalOctalException(String var1) {
        super(var1);
    }
}
