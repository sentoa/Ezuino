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

    // Types
    private int INTTYPE = 0;
    private int DOUBLETYPE = 1;
    private int BOOLEANTYPE = 2;
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
    public void visit(DclNode node) {
        //Remembers the type in the getType shared across all AstNodes.
        node.setTypeForTypeChecking(convertTypeStringToTypeInt(node.getType().toString()));
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
    public void visit(Assign_stmtNode node) {
        /* When an assignment occurs, check if the variable has been declared ie. exist in the symbol table, and if it does,
           update the right side of the variable. an new right side to an variable, check if the variable has already  already exist in the symbol table and if it
           does update the variable in the symbol table. */
        Symbol symbol = new Symbol(node.getId(), node);
        symbolTableManager.getLatestSymbolTable().IfDeclaredUpdate(symbol, node);

        node.getExprNode().accept(this);
        //String test = node.getExprNode()

        // Prints the stack
        //symbolTableManager.getSymbolTableStack().forEach(System.out::println);

        /* Type checks that what is assigned to the variable is what the same type the variable was declared as */
        Symbol symbolFromAssignmentNode = symbolTableManager.getSymbolWithLevel(node.getId(), symbolTableManager.getLevel());

        //int typeFromCurrentValueInSymbolTable = symbolFromAssignmentNode.getNode().getTypeForTypeChecking();
        System.out.println("LOOK HERE: " +  node.getExprNode());
        System.out.println(node.getExprNode().getTypeForTypeChecking());
        //System.out.println(node.getExprNode());
        //int typeFromNewAssignedValue = node.getExprNode().getTypeForTypeChecking();

        //boolean comparedValuesAreIntOrDouble = (typeFromCurrentValueInSymbolTable == INTTYPE || typeFromCurrentValueInSymbolTable == DOUBLETYPE) &&
        //        (typeFromNewAssignedValue == INTTYPE || typeFromNewAssignedValue == DOUBLETYPE);

        int generalizedType;

        //if(comparedValuesAreIntOrDouble){
        //    generalizedType = generalize(typeFromCurrentValueInSymbolTable, typeFromNewAssignedValue);
        //}

        //System.out.println("typeFromCurrentValueInSymbolTable: = " + typeFromCurrentValueInSymbolTable);
        //System.out.println("typeFromNewAssignedValue: = " + typeFromNewAssignedValue);
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
        node.setTypeForTypeChecking(BOOLEANTYPE);
    }

    @Override
    public void visit(StringNode node) {
        System.out.println("Sets type to string");
        node.setTypeForTypeChecking(STRINGTYPE);
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

        /* Type checking: Just sets the node to the type of the expression inside it.  */
        node.setTypeForTypeChecking(node.getReturnExpr().getTypeForTypeChecking());
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
        /* Will not get an type for typechecking, since it is only used in func def, that will never be a part of an expression
        * or the right side of an assignment. */
        System.out.println("In ParametersNode");
    }



    @Override
    public void visit(IdNode node) {
        /* Sets the type of the id node to the corresponding variable in the symbol table. */
        Symbol symbolFromSymbolTable = symbolTableManager.getSymbolWithLevel(node.getVal(), symbolTableManager.getLevel());
        int type = symbolFromSymbolTable.getNode().getTypeForTypeChecking();
        node.setTypeForTypeChecking(type);
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

        /* Type checking */
        int leftNodeType = node.getLeftNode().getTypeForTypeChecking();
        int rightNodeType = node.getRightNode().getTypeForTypeChecking();
        node.setTypeForTypeChecking(generalize(leftNodeType, rightNodeType));
    }

    @Override
    public void visit(EqualityExprNode node) {

        node.getLeftNode().accept(this);
        node.getRelationalExprNode().accept(this);

        /* Type checking */
        int leftNodeType = node.getLeftNode().getTypeForTypeChecking();
        int relationalExprType = node.getRelationalExprNode().getTypeForTypeChecking();
        node.setTypeForTypeChecking(generalize(leftNodeType, relationalExprType));
    }

    @Override
    public void visit(ParenthesisExprNode node) {
        node.getNode().accept(this);
        /* Type checking: Just sets the parentheses node to the type of the expression inside it.  */
        node.setTypeForTypeChecking(node.getNode().getTypeForTypeChecking());
    }

    @Override
    public void visit(LogicalAndExprNode node) {

        node.getLeftNode().accept(this);
        node.getRightNode().accept(this);

        /* Type checking */
        int leftNodeType = node.getLeftNode().getTypeForTypeChecking();
        int rightNodeType = node.getRightNode().getTypeForTypeChecking();
        node.setTypeForTypeChecking(generalize(leftNodeType, rightNodeType));
    }

    @Override
    public void visit(AdditiveExprNode node) {
        node.getLeftNode().accept(this);
        node.getRightNode().accept(this);
        System.out.println("left node " + node.getLeftNode());
        System.out.println("right node " + node.getRightNode());

        /* Type checking */
        int leftNodeType = node.getLeftNode().getTypeForTypeChecking();
        int rightNodeType = node.getRightNode().getTypeForTypeChecking();
        node.setTypeForTypeChecking(generalize(leftNodeType, rightNodeType));
    }

    @Override
    public void visit(MultiplicativeExprNode node) {
        node.getLeftNode().accept(this);
        node.getRightNode().accept(this);

        /* Type checking */
        int leftNodeType = node.getLeftNode().getTypeForTypeChecking();
        int rightNodeType = node.getRightNode().getTypeForTypeChecking();
        node.setTypeForTypeChecking(generalize(leftNodeType, rightNodeType));
    }


    private int convertTypeStringToTypeInt(String keyWord){
        int type = 99;  //It didnt enter any
        if(keyWord == "INT")
            type = INTTYPE;
        if(keyWord == "DOUBLE")
            type = DOUBLETYPE;
        if(keyWord == "BOOLEAN")
            type = BOOLEANTYPE;
        if(keyWord == "STRING")
            type = STRINGTYPE;
        return type;
    }

    /* Determines what the type should be converted to. If the expression is of an int and an double, the type should be double.
      * If it boolean or string both types must be the same. */
    private int generalize(int type1, int type2) {
        boolean typesAreIntOrDouble = (type1 == INTTYPE || type1 == DOUBLETYPE) &&
                (type2 == INTTYPE || type2 == DOUBLETYPE);
        boolean typesAreBoolean = type1 == BOOLEANTYPE && type2 == BOOLEANTYPE;
        boolean typesAreString = type1 == STRINGTYPE && type2 == STRINGTYPE;

        if(typesAreIntOrDouble){
            if (type1 == DOUBLETYPE || type2 == DOUBLETYPE)
                return DOUBLETYPE;
            else return INTTYPE;
        }
        if(typesAreBoolean){
            return BOOLEANTYPE;
        }
        if(typesAreString){
            return STRINGTYPE;
        }
        else {
            System.err.println("Error in generalize: Illegal type usage in expression ");
        }
        return 99;
    }

    private void convert(AstNode n, int t){
        if (n.getTypeForTypeChecking() == DOUBLETYPE && t == INTTYPE) error("Illegal type conversion");
        else if (n.getTypeForTypeChecking() == INTTYPE && t == DOUBLETYPE) n.setTypeForTypeChecking(DOUBLETYPE);
    }

    private void error(String message) {
        throw new Error(message);
    }
}
