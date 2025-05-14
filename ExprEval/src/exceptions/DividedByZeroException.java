package exceptions;

public class DividedByZeroException extends SemanticException {
    public DividedByZeroException() {
        this("Divided by 0.");
    }

    public DividedByZeroException(String var1) {
        super(var1);
    }
}