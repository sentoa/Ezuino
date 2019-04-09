package ast;

import astvisitors.AstLevelVisitor;
import astvisitors.AstVisitor;
import symbolTable.SymbolTableManager;

public abstract class AstNode {

	private Integer typeForTypeChecking = null;
	public abstract void accept(AstVisitor v);
	public abstract void acceptLevel(AstLevelVisitor v, int level);

	public Integer getTypeForTypeChecking() {
		return typeForTypeChecking;
	}

	public void setTypeForTypeChecking(Integer typeForTypeChecking) {
		this.typeForTypeChecking = typeForTypeChecking;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
