package ast;

import astvisitors.AstLevelVisitor;
import astvisitors.AstVisitor;

public class ConvertingToDouble extends AstNode {

    private AstNode child;

    public ConvertingToDouble(AstNode child) {
        this.child = child;
    }

    public AstNode getChild() {
        return child;
    }

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }

    @Override
    public void acceptLevel(AstLevelVisitor v, int level) {

    }

    @Override
    public Integer getTypeForTypeChecking() {
        return super.getTypeForTypeChecking();
    }

    @Override
    public void setTypeForTypeChecking(Integer typeForTypeChecking) {
        super.setTypeForTypeChecking(typeForTypeChecking);
    }
}
