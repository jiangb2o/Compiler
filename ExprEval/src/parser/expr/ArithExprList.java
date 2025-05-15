package parser.expr;

import java.util.ArrayList;
import java.util.Collections;

public class ArithExprList extends Expr {
    ArrayList<Double> args;

    public ArithExprList() {
        args = new ArrayList<>();
        type = EExprType.EXPR_LIST;
    }

    public void addArg(double arg) {
        args.add(arg);
    }

    public void addArg(ArrayList<Double> args) {
        this.args.addAll(args);
    }

    public ArrayList<Double> getArgs() {
        return args;
    }

    public double getMaxArg() {
        return Collections.max(args);
    }

    public double getMinArg() {
        return Collections.min(args);
    }
}
