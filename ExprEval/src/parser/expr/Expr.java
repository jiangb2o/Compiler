package parser.expr;


import parser.Symbol;

public class Expr implements Symbol {
    protected EExprType type;

    public EExprType getType() {
        return type;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}