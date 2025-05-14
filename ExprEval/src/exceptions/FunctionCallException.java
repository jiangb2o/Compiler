package exceptions;

public class FunctionCallException extends SyntacticException {
    public FunctionCallException() {
        this("Syntactic error in function call.");
    }

    public FunctionCallException(String var1) {
        super(var1);
    }
}
