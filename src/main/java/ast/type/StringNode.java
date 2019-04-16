package ast.type;

import astvisitors.AstLevelVisitor;
import astvisitors.AstVisitor;

public class StringNode extends ValNode {
	
	public StringNode(String val) {
		this.val = val;
	}

    @Override
    public void accept(AstVisitor v) {
        v.visit(this);
    }
	
	@Override
	public void acceptLevel(AstLevelVisitor v, int level) {
		v.visitLevel(this, level);
	}
}