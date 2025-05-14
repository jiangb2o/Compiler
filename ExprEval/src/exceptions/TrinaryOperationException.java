package exceptions;

public class TrinaryOperationException extends SyntacticException {
    public TrinaryOperationException() {
        this("Syntactic error in trinary operation.");
    }

    public TrinaryOperationException(String var1) {
        super(var1);
    }
}
