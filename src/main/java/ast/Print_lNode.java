package ast;

import astvisitors.AstVisitor;

public class Print_lNode extends AstNode {

	@Override
	public void accept(AstVisitor v) {
		v.visit(this);
		
	}

}
