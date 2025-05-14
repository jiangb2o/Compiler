
package exceptions;

public class EmptyExpressionException extends SyntacticException {
    public EmptyExpressionException() {
        this("The expression is empty.");
    }

    public EmptyExpressionException(String var1) {
        super(var1);
    }
}
