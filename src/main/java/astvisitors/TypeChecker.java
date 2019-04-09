package astvisitors;

import ast.*;
import ast.expr.*;
import ast.expr.aexpr.AExpr;
import ast.type.*;
import exceptions.AlreadyInTableException;
import symbolTable.Symbol;
import symbolTable.SymbolTable;

public class TypeChecker extends AstVisitor{

    private int INTTYPE = 0;
    private int DOUBLETYPE = 1;
    private int BOOLTYPE = 2;
    private int STRINGTYPE = 3;


    @Override
    public void visit(Assign_stmtNode node) {
        Symbol symbolWithTheSameRightNode = SymbolTableVisitor.symbolTableManager.getSymbolMadeFromNode(node);
        System.out.println(symbolWithTheSameRightNode.getIdentity());
        node.getExprNode().accept(this);
        System.out.println(node.getId() + " = " + convertTypeKeywordStringToTypeInt(node.getId()));
        System.out.println("For " + node.getId());
        System.out.println("Returned type: " + node.getExprNode().getTypeForTypeChecking());
    }

    @Override
    public void visit(IntegerNode node) {
        System.out.println("Sets type to integer");
        node.setTypeForTypeChecking(INTTYPE);
    }

    @Override
    public void visit(DoubleNode node) {
        System.out.println("Sets type to double");
        node.setTypeForTypeChecking(DOUBLETYPE);
    }

    @Override
    public void visit(BooleantfNode node) {
        System.out.println("Sets type to boolean");
        node.setTypeForTypeChecking(BOOLTYPE);
    }

    @Override
    public void visit(StringNode node) {
        System.out.println("Sets type to string");
        node.setTypeForTypeChecking(STRINGTYPE);
    }

    @Override
	public void visit(StartNode node) {
		node.getDcls().accept(this);
		node.getStmts().accept(this);
	}

	@Override
	public void visit(Func_callStmtNode node) {

		for (AExpr child : node.getParameters()) {
			child.accept(this);
		}
	}

	@Override
	public void visit(Func_callExprNode node) {

		for (AExpr child : node.getParameters()) {
			child.accept(this);
		}
	}

	@Override
	public void visit(BlockNode node) {
		if (node.getDclsNode() != null) {
			node.getDclsNode().accept(this);
		}
		if (node.getStmtsNode() != null) {
			node.getStmtsNode().accept(this);
		}
		if (node.getReturnstmtNode() != null) {
			node.getReturnstmtNode().accept(this);
		}

	}

	@Override
	public void visit(Func_defNode node) {

		for (DclNode parameter : node.getParameters()) {
			parameter.accept(this);
		}
		node.getBlockNode().accept(this);
	}

	@Override
	public void visit(Print_lNode node) {

		node.getExprNode().accept(this);

	}

	@Override
	public void visit(Return_stmtNode node) {

		node.getReturnExpr().accept(this);
	}

	@Override
	public void visit(If_stmtNode node) {

		node.getExpr().accept(this);
		node.getIfBlock().accept(this);
		BlockNode elseBlock = node.getElseBlock();
		if (elseBlock != null) {
			elseBlock.accept(this);
		}

	}




	@Override
	public void visit(StmtsNode node) {

		int childCount = node.getChildCount();
		for (int i = 0; i < childCount; i++) {
			node.getChild(i).accept(this);
		}

	}

	@Override
	public void visit(DclNode node) {
	}

	@Override
	public void visit(TypeNode node) {
	}

	@Override
	public void visit(DclsNode node) {

		int childCount = node.getChildCount();
		for (int i = 0; i < childCount; i++) {
			node.getChild(i).accept(this);
		}
	}

	@Override
	public void visit(While_stmtNode node) {

		node.getExprNode().accept(this);
		node.getBlockNode().accept(this);
	}

	@Override
	public void visit(ExprNode node) {
	}

	@Override
	public void visit(ParametersNode node) {
	}



	@Override
	public void visit(IdNode node) {
	}

	@Override
	public void visit(Built_in_funcNode node) {

		if (node.getPrintlNode() != null) {
			node.getPrintlNode().accept(this);
		}
	}

	@Override
	public void visit(RelationalExprNode node) {

		node.getLeftNode().accept(this);
		node.getRightNode().accept(this);
	}

	@Override
	public void visit(EqualityExprNode node) {

		node.getLeftNode().accept(this);
		node.getRelationalExprNode().accept(this);
	}

	@Override
	public void visit(ParenthesisExprNode node) {

		node.getNode().accept(this);
	}

	@Override
	public void visit(LogicalAndExprNode node) {

		node.getLeftNode().accept(this);
		node.getRightNode().accept(this);
	}

	@Override
	public void visit(AdditiveExprNode node) {

		node.getLeftNode().accept(this);
		node.getRightNode().accept(this);

	}

	@Override
	public void visit(MultiplicativeExprNode node) {

		node.getLeftNode().accept(this);
		node.getRightNode().accept(this);

	}

	/* Skal måske laves om til exception, tog hans fordi det måske er den vi generelt skal bruge siden det er mega nemt og tilladt. */
    private void error(String message) {
        throw new Error(message);
    }

    /*
    private int generalize(int t1, int t2){
        if (t1 == FLTTYPE || t2 == FLTTYPE)
            return FLTTYPE;
        else return INTTYPE;
    }
    */

    /*
    private AstNode convert(AstNode n, int t){
        if (n.getType() == FLTTYPE && t == INTTYPE) error("Illegal type conversion");
        else if (n.getType() == INTTYPE && t == FLTTYPE) return new ConvertingToFloat(n);
        return n;
    }
    */

    private int convertTypeKeywordStringToTypeInt(String keyWord){
        int type = 99;  //It didnt enter any
        if(keyWord == "int")
            type = INTTYPE;
        if(keyWord == "double")
            type = DOUBLETYPE;
        if(keyWord == "boolean")
            type = BOOLTYPE;
        if(keyWord == "string")
            type = STRINGTYPE;
        return type;
    }

}
