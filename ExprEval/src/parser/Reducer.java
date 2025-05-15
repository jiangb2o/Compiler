package parser;

import parser.expr.ArithExpr;
import parser.expr.ArithExprList;
import parser.expr.BoolExpr;
import parser.expr.Expr;
import parser.token.*;

import exceptions.*;

import java.util.ArrayList;

/**
 * Chose production to reduce
 */
public class Reducer {
    /* storage operator symbol */
    private final ArrayList<Symbol> stack;

    public Reducer(ArrayList<Symbol> stack) {
        this.stack = stack;
    }

    /**
     * reduce current token
     * @throws ExpressionException e
     */
    public void reduce() throws ExpressionException {
        Token top = getTopTerminal();
        switch (top.getTokenType()) {
            case ETokenType.DECIMAL -> reduceDecimal();
            case ETokenType.BOOL -> reduceBool();
            case ETokenType.RPAREN -> reduceRightParen();
            case ETokenType.NEG -> reduceNeg();
            case ETokenType.POWER, ETokenType.MUL_DIV, ETokenType.ADD_SUB, ETokenType.RELATION -> reduceBinaryDecimalOperator();
            case ETokenType.NOT -> reduceNot();
            case ETokenType.AND, ETokenType.OR -> reduceAndOr();
            case ETokenType.COLON -> reduceTrinaryOperator();
        }
    }

    /**
     * ArithExpr -> decimal
     * @throws ExpressionException e
     */
    private void reduceDecimal() throws ExpressionException {
        Decimal decimal = (Decimal) popTopTerminal();
        ArithExpr expr = new ArithExpr(decimal.getValue());
        stack.add(expr);
    }

    /**
     * BoolExpr -> ture | false
     * @throws ExpressionException e
     */
    private void reduceBool() throws ExpressionException {
        Bool bool = (Bool) popTopTerminal();
        BoolExpr expr = new BoolExpr(bool.getValue());
        stack.add(expr);
    }

    /**
     * ArithExpr -> (ArithExpr)
     * ArithExpr -> sin(ArithExpr) | cos(ArithExpr)
     * ArithExpr -> max(ArithExpr, ArithExprList) | min(ArithExpr, ArithExprList)
     * BoolExpr -> (BoolExpr)
     * @throws ExpressionException e
     */
    private void reduceRightParen() throws ExpressionException {
        Expr resultExpr = null;
        // )
        Token rightParen = popTopTerminal();

        if(stackTopIsTerminal()) {
            throw new MissingOperandException();
        }
        // ArithExpr/BoolExpr/ArithExprList
        Expr rightExpr = popTopNonTerminal();


        if(!stackTopIsTerminal()) {
            throw new MissingLeftParenthesisException();
        }
        // , (
        Token lastSecondToken = popTopTerminal();


        // (ArithExpr) sin(ArithExpr) cos(ArithExpr) (BoolExpr) min(ArithExprList) max(ArithExprList)
        if(lastSecondToken.getTokenType() == ETokenType.LPAREN) {
            if(stackTopIsTerminal()) {
                Token lastThirdToken = getTopTerminal();
                // sin(ArithExpr)
                if(lastThirdToken.getTokenString().equals("sin")) {
                    popTopTerminal();
                    if(rightExpr instanceof ArithExprList) {
                        throw new FunctionCallException();
                    }
                    if(rightExpr instanceof BoolExpr) {
                        throw new TypeMismatchedException();
                    }
                    resultExpr = new ArithExpr(Math.sin(((ArithExpr) rightExpr).getValue()));
                }
                // cos(ArithExpr)
                else if(lastThirdToken.getTokenString().equals("cos")){
                    popTopTerminal();
                    if(!(rightExpr instanceof ArithExpr)) {
                        throw new FunctionCallException();
                    }
                    resultExpr = new ArithExpr(Math.cos(((ArithExpr) rightExpr).getValue()));
                }
                // min(ArithExprList)
                else if(lastThirdToken.getTokenString().equals("min")) {
                    popTopTerminal();
                    if(rightExpr instanceof ArithExpr) {
                        throw new MissingOperandException();
                    }
                    if(rightExpr instanceof BoolExpr) {
                        throw new TypeMismatchedException();
                    }
                    resultExpr = new ArithExpr(((ArithExprList) rightExpr).getMinArg());
                }
                else if(lastThirdToken.getTokenString().equals("max")) {
                    popTopTerminal();
                    if(rightExpr instanceof ArithExpr) {
                        throw new MissingOperandException();
                    }
                    if(rightExpr instanceof BoolExpr) {
                        throw new TypeMismatchedException();
                    }
                    resultExpr = new ArithExpr(((ArithExprList) rightExpr).getMaxArg());
                }
                // (ArithExpr) (BoolExpr)
                else if(rightExpr instanceof BoolExpr || rightExpr instanceof ArithExpr) {
                    if(rightExpr instanceof BoolExpr) {
                        resultExpr = new BoolExpr(((BoolExpr) rightExpr).getValue());
                    } else {
                        resultExpr = new ArithExpr(((ArithExpr) rightExpr).getValue());
                    }
                } else {
                    throw new TypeMismatchedException();
                }
            }

        }
        // ArithExprList -> ArithExpr, ArithExpr/ArithExprList
        else if (lastSecondToken.getTokenType() == ETokenType.COMMA) {
            if(rightExpr instanceof BoolExpr) {
                throw new TypeMismatchedException();
            }
            if (stackTopIsTerminal()) {
                throw new MissingOperandException();
            }
            // left ArithExpr
            Expr leftExpr = popTopNonTerminal();
            if (!(leftExpr instanceof ArithExpr)) {
                throw new TypeMismatchedException();
            }

            resultExpr = new ArithExprList();
            if (rightExpr instanceof ArithExpr) {
                ((ArithExprList) resultExpr).addArg(((ArithExpr) rightExpr).getValue());
            } else {
                ((ArithExprList) resultExpr).addArg(((ArithExprList) rightExpr).getArgs());
            }
            ((ArithExprList) resultExpr).addArg(((ArithExpr) leftExpr).getValue());
        } else {
            throw new MissingLeftParenthesisException();
        }

        stack.add(resultExpr);
        if(resultExpr instanceof ArithExprList) {
            stack.add(new Operator(ETokenType.RPAREN, ")"));
        }
    }

    /**
     * ArithExpr -> -ArithExpr
     * @throws ExpressionException e
     */
    private void reduceNeg() throws ExpressionException {
        popTopTerminal();

        if(stackTopIsTerminal()) {
            throw new MissingOperandException();
        }
        Expr expr = popTopNonTerminal();

        if(!(expr instanceof ArithExpr)) {
            throw new TypeMismatchedException();
        }

        ArithExpr negExpr = new ArithExpr(-((ArithExpr)expr).getValue());
        stack.add(negExpr);
    }

    /**
     * ArithExpr -> ArithExpr +-/*^ ArithExpr
     * BoolExpr -> ArithExpr > >= < <= = <> ArithExpr
     * @throws ExpressionException e
     */
    private void reduceBinaryDecimalOperator() throws ExpressionException {
        Operator op = (Operator) popTopTerminal();

        Expr rightOperand = popTopNonTerminal();
        Expr leftOperand = popTopNonTerminal();

        if(!(leftOperand instanceof ArithExpr) || !(rightOperand instanceof ArithExpr)) {
            throw new TypeMismatchedException();
        }

        double lValue = ((ArithExpr) leftOperand).getValue();
        double rValue = ((ArithExpr) rightOperand).getValue();
        Expr resultExpr;
        switch (op.getTokenString()) {
            // ArithExpr result
            case "+" -> resultExpr = new ArithExpr(lValue + rValue);
            case "-" -> resultExpr = new ArithExpr(lValue - rValue);
            case "*" -> resultExpr = new ArithExpr(lValue * rValue);
            case "/" -> {
                if(rValue == 0) {
                    throw new DividedByZeroException();
                }
                resultExpr = new ArithExpr(lValue / rValue);
            }
            case "^" -> resultExpr = new ArithExpr(Math.pow(lValue, rValue));
            // BoolExpr result
            case ">" -> resultExpr = new BoolExpr(lValue > rValue);
            case ">=" -> resultExpr = new BoolExpr(lValue >= rValue);
            case "<" -> resultExpr = new BoolExpr(lValue < rValue);
            case "<=" -> resultExpr = new BoolExpr(lValue <= rValue);
            case "=" -> resultExpr = new BoolExpr(lValue == rValue);
            case "<>" -> resultExpr = new BoolExpr(lValue != rValue);
            default -> throw new IllegalSymbolException();
        }
        stack.add(resultExpr);
    }

    /**
     * BoolExpr -> !BoolExpr
     * @throws ExpressionException e
     */
    private void reduceNot() throws ExpressionException {
        popTopTerminal();
        if(stackTopIsTerminal()) {
            throw new MissingOperandException();
        }
        Expr expr = popTopNonTerminal();

        if(!(expr instanceof BoolExpr)) {
            throw new TypeMismatchedException();
        }

        BoolExpr notExpr = new BoolExpr(!((BoolExpr)expr).getValue());
        stack.add(notExpr);
    }

    /**
     * BoolExpr -> BoolExpr & | BoolExpr
     * @throws ExpressionException e
     */
    private void reduceAndOr() throws ExpressionException {
        Operator op = (Operator) popTopTerminal();

        Expr leftOperand = popTopNonTerminal();
        Expr rightOperand = popTopNonTerminal();

        if(!(leftOperand instanceof BoolExpr) || !(rightOperand instanceof BoolExpr)) {
            throw new TypeMismatchedException();
        }

        boolean lValue = ((BoolExpr) leftOperand).getValue();
        boolean rValue = ((BoolExpr) rightOperand).getValue();
        Expr resultExpr;
        switch (op.getTokenString()) {
            case "&" -> resultExpr = new BoolExpr(lValue & rValue);
            case "|" -> resultExpr = new BoolExpr(lValue | rValue);
            default -> throw new IllegalSymbolException();
        }
        stack.add(resultExpr);
    }

    /**
     * ArithExpr -> BoolExpr ? ArithExpr : ArithExpr
     * @throws ExpressionException e
     */
    private void reduceTrinaryOperator() throws ExpressionException {
        if(stackTopIsTerminal()) {
            throw new MissingOperandException();
        }
        Expr rightOperand = popTopNonTerminal();
        if(!(rightOperand instanceof ArithExpr)) {
            throw new TypeMismatchedException();
        }

        Token colon = popTopTerminal();

        if(stackTopIsTerminal()) {
            throw new MissingOperandException();
        }
        Expr leftOperand = popTopNonTerminal();
        if(!(leftOperand instanceof ArithExpr)) {
            throw new TypeMismatchedException();
        }

        Token question = popTopTerminal();
        if(!question.getTokenString().equals("?")) {
            throw new TrinaryOperationException();
        }

        if(stackTopIsTerminal()) {
            throw new MissingOperandException();
        }
        Expr boolOperand = popTopNonTerminal();
        if(!(boolOperand instanceof BoolExpr)) {
            throw new TypeMismatchedException();
        }

        double result;
        if(((BoolExpr) boolOperand).getValue()) {
            result = ((ArithExpr) leftOperand).getValue();
        } else {
            result = ((ArithExpr) rightOperand).getValue();
        }

        ArithExpr resultExpr = new ArithExpr(result);
        stack.add(resultExpr);
    }



    /**
     * stack top is terminal symbol
     * @return ture if terminal else false
     */
    private boolean stackTopIsTerminal() {
        return stack.getLast().isTerminal();
    }

    /**
     * Get top token of stack
     * @return top token
     * @throws MissingOperatorException e
     */
    private Token getTopTerminal() throws MissingOperatorException {
        for(int i = stack.size() - 1; i >= 0; i--) {
            if(stack.get(i) instanceof Token) {
                return (Token) stack.get(i);
            }
        }
        throw new MissingOperatorException();
    }

    /**
     * Pop top token of stack
     * @return top token
     * @throws MissingOperatorException e
     */
    private Token popTopTerminal() throws MissingOperatorException {
        Token token = getTopTerminal();
        stack.remove(token);
        return token;
    }

    /**
     * Get top Expr of stack
     * @return top expr
     * @throws MissingOperandException e
     */
    private Expr getTopNonTerminal() throws MissingOperandException {
        for(int i = stack.size() - 1; i >= 0; i--) {
            if(stack.get(i) instanceof Expr) {
                return (Expr) stack.get(i);
            }
        }
        throw new MissingOperandException();
    }

    /**
     * Pop top Expr of stack
     * @return top expr
      @throws MissingOperandException e
     */
    private Expr popTopNonTerminal() throws MissingOperandException {
        Expr expr = getTopNonTerminal();
        stack.remove(expr);
        return expr;
    }
}
