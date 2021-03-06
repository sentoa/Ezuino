package astvisitors;


import ast.*;
import ast.expr.*;
import ast.type.*;

public abstract class AstLevelVisitor {
	
	public abstract void visitLevel(Func_callStmtNode node, int level);
	public abstract void visitLevel(Func_callExprNode node, int level);
	public abstract void visitLevel(BlockNode node, int level);
	public abstract void visitLevel(Func_defNode node, int level);
	public abstract void visitLevel(Return_stmtNode node, int level);
	public abstract void visitLevel(If_stmtNode node, int level);
	public abstract void visitLevel(StartNode node, int level);
	public abstract void visitLevel(BooleanLiteral node, int level);
	public abstract void visitLevel(StmtsNode node, int level);
	public abstract void visitLevel(DclNode node, int level);
	public abstract void visitLevel(DclsNode node, int level);
	public abstract void visitLevel(While_stmtNode node, int level);
	public abstract void visitLevel(ParametersNode node, int level);
	public abstract void visitLevel(AdditiveExprNode node, int level);
	public abstract void visitLevel(MultiplicativeExprNode node, int level);
	public abstract void visitLevel(LogicalAndExprNode node, int level);
	public abstract void visitLevel(RelationalExprNode node, int level);
	public abstract void visitLevel(EqualityExprNode node, int level);
	public abstract void visitLevel(ParenthesisExprNode node, int level);
    public abstract void visitLevel(UnaryExprNode node, int level);

	
	//One added assignment nodes.
	public abstract void visitLevel(Assign_stmtNode node, int level);

	
	//"Type"
	public abstract void visitLevel(IntegerLiteral node, int level);
	public abstract void visitLevel(DoubleLiteral node, int level);
	public abstract void visitLevel(StringLiteral node, int level);
	public abstract void visitLevel(IdNode node, int level);
	
	
	
	public void visitLevel(AstNode astNode, int level) {
		System.out.println("In ASTNode visit:\t" + astNode);
		astNode.acceptLevel(this, level);
	}

}
