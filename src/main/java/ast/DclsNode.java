package ast;

import ezuino.AstVisitor;

public class DclsNode extends BranchNode<DclNode> {

	@Override
	public void accept(AstVisitor v) {
		v.visit(this);
		
	}
}
