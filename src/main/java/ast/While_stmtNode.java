package ast;

import ezuino.AstVisitor;

public class While_stmtNode extends AstNode {

	@Override
	public void accept(AstVisitor v) {
		v.visit(this);
		
	}
}
