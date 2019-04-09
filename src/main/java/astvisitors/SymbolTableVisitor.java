package astvisitors;
import ast.*;
import ast.expr.*;
import ast.expr.aexpr.AExpr;
import ast.type.DoubleNode;
import ast.type.IdNode;
import ast.type.IntegerNode;
import ast.type.StringNode;
import exceptions.AlreadyInTableException;
import symbolTable.*;

public class SymbolTableVisitor extends AstVisitor {
    public static SymbolTableManager symbolTableManager = new SymbolTableManager();

    // Types (primitive types i think)
    private int INTTYPE = 0;
    private int DOUBLETYPE = 1;
    private int BOOLTYPE = 2;
    private int STRINGTYPE = 3;

    @Override
    public void visit(StartNode node) {
        symbolTableManager.openScope();
        System.out.println("Open global scope");
        node.getDcls().accept(this);
        node.getStmts().accept(this);
        //System.out.println(symbolTableManager.getLatestSymbolTable());
        System.out.println("Close global scope");
        symbolTableManager.closeScope();
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
        symbolTableManager.openScope();
        System.out.println("Open Level " + symbolTableManager.getLevel());
        if (node.getDclsNode() != null) {
            node.getDclsNode().accept(this);
        }
        if (node.getStmtsNode() != null) {
            node.getStmtsNode().accept(this);
        }
        if (node.getReturnstmtNode() != null) {
            node.getReturnstmtNode().accept(this);
        }
        symbolTableManager.closeScope();
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
        Symbol symbol = new Symbol(node.getID(), node);
        SymbolTable s = SymbolTableVisitor.symbolTableManager.getLatestSymbolTable();
        try
        {
            s.insert(symbol);
        } catch (AlreadyInTableException e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void visit(TypeNode node) {
        System.out.println("In TypeNode");

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
        System.out.println("In ExprNode");

    }

    @Override
    public void visit(ParametersNode node) {
        System.out.println("In ParametersNode");

    }

    @Override
    public void visit(Assign_stmtNode node) {
        Symbol symbol = new Symbol(node.getId(), node);
        symbolTableManager.getLatestSymbolTable().IfDeclaredUpdate(symbol, node);
        node.getExprNode().accept(this);
        // After the value has been assigned to the symbol table do typeChecking
        //Symbol symbolWithTheSameRightNode = SymbolTableVisitor.symbolTableManager.getSymbolMadeFromNode(node);
        //System.out.println(symbolWithTheSameRightNode.getIdentity());
        symbolTableManager.getSymbolTableStack().forEach(symbolTable -> {
            System.out.println(symbolTable);
        });
        System.out.println(node.getId() + " = " + convertTypeKeywordStringToTypeInt(node.getId()));
        System.out.println("For " + node.getId());
        System.out.println("Returned type: " + node.getExprNode().getTypeForTypeChecking());
    }

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
