package parser;

import parser.expr.*;
import parser.token.*;
import exceptions.*;

import java.util.ArrayList;

/**
 * Parser class
 * use stack and lookahead to chose action
 */
public class Parser {
    /**
     * 0: shift
     * 1: reduce
     * 2: acc
     * 3: MissingOperator Exception
     * 4: Missing Operand Exception
     * 5: Missing Left Parenthesis Exception
     * 6: Missing Right Parenthesis Exception
     * 7: Function Call Exception
     * 8: Trinary Operation Exception
     * 9: Type Mismatched Exception
     */
    private static final int[][] oppTable = {
            {3, 3, 3, 1, 3, 3, 1, 1, 1, 1, 9, 1, 1, 1, 1, 1, 1},// decimal
            {3, 3, 3, 1, 3, 3, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1},// bool
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 6},// (
            {3, 3, 3, 1, 3, 3, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1},// )
            {7, 7, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},// func
            {0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},// unary -
            {0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 9, 1, 1, 1, 1, 1, 1},// ^
            {0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 9, 1, 1, 1, 1, 1, 1},// * /
            {0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 9, 1, 1, 1, 1, 1, 1},// + -
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 3, 1, 1, 1, 9, 9, 1},// relation
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 9, 1},// !
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 9, 1},// &
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 9, 1},// |
            {0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8},// ?
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},// :
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6},// ,
            {0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 2} // $
    };


    private final Scanner scanner; /* Scanner */

    private final Reducer reducer; /* Reducer */

    private final ArrayList<Symbol> stack; /* storage operator symbol */

    private Token lookahead;

    /* Constructor */
    public Parser(String expression) throws EmptyExpressionException {
        // trim white space at begin and end
        expression = expression.trim();
        if(expression.isEmpty()) {
            throw new EmptyExpressionException();
        }

        scanner = new Scanner(expression);
        stack = new ArrayList<>();
        stack.add(new Dollar());
        reducer = new Reducer(stack);
    }

    /**
     * Begin parse expression
     * @return expression value
     * @throws ExpressionException e
     */
    public double parse() throws ExpressionException {
        lookahead = scanner.nextToken();
        while(true){
            switch (getOPP()) {
                case 0 -> shift();
                case 1 -> reduce();
                case 2 -> {
                    return accept();
                }
                case 3 -> throw new MissingOperatorException();
                case 4 -> throw new MissingOperandException();
                case 5 -> throw new MissingLeftParenthesisException();
                case 6 -> throw new MissingRightParenthesisException();
                case 7 -> throw new FunctionCallException();
                case 8 -> throw new TrinaryOperationException();
                case 9 -> throw new TypeMismatchedException();
            }
        }
    }

    /**
     * shift
     * @throws LexicalException e
     */
    private void shift() throws LexicalException {
        stack.add(lookahead);
        lookahead = scanner.nextToken();
    }

    /**
     * reduce
     * @throws ExpressionException e
     */
    private void reduce() throws ExpressionException {
        reducer.reduce();
    }

    /**
     * accept
     * @return final value
     * @throws TypeMismatchedException e
     */
    private double accept() throws TypeMismatchedException {
        Expr resultExpr = getTopExpr();
        if(resultExpr instanceof ArithExpr) {
            return  ((ArithExpr) resultExpr).getValue();
        } else {
            throw new TypeMismatchedException();
        }
    }



    /**
     * get opp with stack top and lookahead
     * @return opp value
     */
    private int getOPP() {
        return oppTable[getTopToken().getTokenType().ordinal()][lookahead.getTokenType().ordinal()];
    }

    /**
     * Get top token of stack
     * @return top token
     */
    private Token getTopToken() {
        for(int i = stack.size() - 1; i >= 0; i--) {
            if(stack.get(i) instanceof Token) {
                return (Token) stack.get(i);
            }
        }
        return null;
    }

    /**
     * Get top Expr of stack
     * @return top expr
     */
    private Expr getTopExpr() {
        for(int i = stack.size() - 1; i >= 0; i--) {
            if(stack.get(i) instanceof Expr) {
                return (Expr) stack.get(i);
            }
        }
        return null;
    }
}
