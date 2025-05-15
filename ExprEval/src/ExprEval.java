import exceptions.ExpressionException;
import parser.Parser;

public class ExprEval {
    public ExprEval() {
    }

    public static void main(String[] args) throws ExpressionException {
        System.out.println(new Parser("sin(32.5>65)").parse());
    }
}