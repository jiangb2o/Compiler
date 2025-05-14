package parser.expr;

public class ArithExpr extends Expr {
    private final double value;
    public ArithExpr(double value) {
        this.value = value;
        type = EExprType.DECIMAL;
    }

    public double getValue() {
        return value;
    }
}
