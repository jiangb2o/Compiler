package exceptions;

public class LexicalException extends Exception {
    public LexicalException() {
        this("Lexical error.");
    }

    public LexicalException(String var1) {
        super(var1);
    }
}