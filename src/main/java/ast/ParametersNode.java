package ast;

import ezuino.AstVisitor;

public class ParametersNode extends AstNode {

	@Override
	public void accept(AstVisitor v) {
		v.visit(this);
		
	}
}