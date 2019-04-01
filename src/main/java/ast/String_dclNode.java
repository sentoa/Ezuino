package ast;

import astvisitors.AstVisitor;

public class String_dclNode extends AstNode {

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
}