package parser.expr;

public class BoolExpr extends Expr {
    private boolean value;
    public BoolExpr(boolean value) {
        this.value = value;
        type = EExprType.BOOL;
    }

    public boolean getValue() {
        return value;
    }
}
