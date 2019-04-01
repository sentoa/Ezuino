package astvisitors;

import ast.*;
import ast.expr.ExprNode;
import ast.type.ValNode;

public abstract class AstVisitor {

	public abstract void visit(Func_callNode node);
	public abstract void visit(ParamNode node);
	public abstract void visit(BlockNode node);
	public abstract void visit(Func_defNode node);
	public abstract void visit(Print_lNode node);
	public abstract void visit(Switch_blockNode node);
	public abstract void visit(Return_stmtNode node);
	public abstract void visit(If_stmtNode node);
	public abstract void visit(StartNode node);
	public abstract void visit(BooleantfNode node);
	public abstract void visit(List_addNode node);
	public abstract void visit(StmtNode node);
	public abstract void visit(StmtsNode node);
	public abstract void visit(Switch_stmtNode node);
	public abstract void visit(DclNode node);
	public abstract void visit(List_removeNode node);
	public abstract void visit(TypeNode node);
	public abstract void visit(DclsNode node);
	public abstract void visit(ValNode node);
	public abstract void visit(While_stmtNode node);
	public abstract void visit(ExprNode node);
	public abstract void visit(ParametersNode node);

	//One added assignment nodes.
	public abstract void visit(Assign_stmtNode node);

	//"New" Declarations
	public abstract void visit(Int_dclNode node);
	public abstract void visit(Double_dclNode node);
	public abstract void visit(Boolean_dclNode node);
	public abstract void visit(String_dclNode node);


	public void visit(AstNode astNode) {
		System.out.println("In ASTNode visit:\t" + astNode);
		astNode.accept(this);
	}

}