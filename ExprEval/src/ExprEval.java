import exceptions.ExpressionException;
import parser.Parser;

public class ExprEval {
    public ExprEval() {
    }

    public static void main(String[] args) throws ExpressionException {
        System.out.println(new Parser("max(sin(0.15), cos(0.15), sin(cos(0.15)))").parse());
    }
}