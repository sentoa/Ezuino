package ast.expr.aexpr;

import ast.AstNode;

public abstract class AExpr extends AstNode {
    private String stringFromFinalExpression;

    public String getStringFromFinalExpression() {
        return stringFromFinalExpression;
    }

    public void setStringFromFinalExpression(String stringFromFinalExpression) {
        this.stringFromFinalExpression = stringFromFinalExpression;
    }
}
