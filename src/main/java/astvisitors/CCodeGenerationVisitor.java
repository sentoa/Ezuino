package astvisitors;

import ast.*;
import ast.expr.*;
import ast.expr.aexpr.AExpr;
import ast.type.DoubleNode;
import ast.type.IdNode;
import ast.type.IntegerNode;
import ast.type.StringNode;

import java.io.PrintStream;
import java.util.Iterator;

public class CCodeGenerationVisitor extends AstVisitor {
    private PrintStream out;

    public CCodeGenerationVisitor(PrintStream printStream) {
        this.out = printStream;
    }

    @Override
    public void visit(Func_callStmtNode node) {
        out.print(node.getID() + "(");
        for (Iterator<AExpr> iterator = node.getParameters().iterator(); iterator.hasNext(); ) {
            AExpr exp = iterator.next();
            exp.accept(this);
            // Adds separating comma if there is another parameter
            if (iterator.hasNext()) {
                out.print(", ");
            }
        }
        out.print(");\n");
    }

    @Override
    public void visit(Func_callExprNode node) {
        out.print(node.getID() + "(");
        for (Iterator<AExpr> iterator = node.getParameters().iterator(); iterator.hasNext(); ) {
            AExpr exp = iterator.next();
            exp.accept(this);
            // Adds separating comma if there is another parameter
            if (iterator.hasNext()) {
                out.print(", ");
            }
        }
        out.print(");\n");
    }

    @Override
    public void visit(BlockNode node) {
        out.print("{\n");
        // Checks for declarations
        if (node.getDclsNode().getChildCount() != 0) {
            node.getDclsNode().accept(this);
            out.print("\n");
        }
        // Checks for statement calls
        if (node.getStmtsNode().getChildCount() != 0) {
            node.getStmtsNode().accept(this);
            out.print("\n");
        }
        node.getReturnstmtNode().accept(this);  // Might need implementation for exiting block without return
        out.print(";\n}\n");  // Formatting
    }

    @Override
    public void visit(Func_defNode node) {
        out.print(node.getType() + " " + node.getId() + "(");
        for (Iterator<DclNode> iterator = node.getParameters().iterator(); iterator.hasNext(); ) {
            DclNode dclNode = iterator.next();
            dclNode.accept(this);
            // Adds separating comma if there is another parameter
            if (iterator.hasNext()) {
                out.print(", ");
            }
        }
        out.print(") {\n");
        node.getBlockNode().accept(this);
        out.print("}\n");
    }

    @Override
    public void visit(Return_stmtNode node) {
        out.print("return ");
        node.getReturnExpr().accept(this);
    }

    @Override
    public void visit(If_stmtNode node) {
        out.print("if (");
        node.getExpr().accept(this);
        out.print(") ");
        node.getIfBlock().accept(this);
        out.print("else ");
        node.getElseBlock().accept(this);
    }

    @Override
    public void visit(StartNode node) {
        node.getDcls().accept(this);
        node.getStmts().accept(this);
    }

    @Override
    public void visit(BooleantfNode node) {
        out.print(node.getBoolval());
    }

    @Override
    public void visit(StmtsNode node) {
        for (Iterator<StmtNode> iterator = node.getChildIterator(); iterator.hasNext(); ) {
            StmtNode stmtNode = iterator.next();
            stmtNode.accept(this);
        }
    }

    @Override
    public void visit(DclNode node) {
        // Formats the antlr defined type to C types
        if (node.getType().equals(Type.INT)) {
            out.print("int " + node.getID() + ";");
        }
        if (node.getType().equals(Type.DOUBLE)) {
            out.print("double " + node.getID() + ";");
        }
        // Converts the java string into a C char array of size 255
        if (node.getType().equals(Type.STRING)) {
            out.print("char array[255] " + node.getID() + ";");
        }
        if (node.getType().equals(Type.BOOL)) {
            out.print("bool " + node.getID() + ";");
        }
    }

    @Override
    public void visit(DclsNode node) {
        if (node.getChildCount() == 1) {
            node.getChild(0).accept(this);
            out.print("\n");
        }
        else {
            for (Iterator<DclNode> iterator = node.getChildIterator(); iterator.hasNext(); ) {
                DclNode dclNode = iterator.next();
                dclNode.accept(this);
                out.print("\n");
            }
        }
    }

    @Override
    public void visit(While_stmtNode node) {
        out.print("while (");
        node.getExprNode().accept(this);
        out.print(") {\n");
        node.getBlockNode().accept(this);
        out.print("}\n");
    }

    @Override
    public void visit(Assign_stmtNode node) {
        out.print(node.getId() + " = " );
        node.getExprNode().accept(this);
        out.print(";\n");
    }

    @Override
    public void visit(ParametersNode node) {
        // ParametersNode does currently not have an implementation
    }

    @Override
    public void visit(ParenthesisExprNode node) {
        out.print("(");
        node.getNode().accept(this);
        out.print(")");
    }

    @Override
    public void visit(UnaryExprNode node) {
        out.print(node.getOperator());
        node.getNode().accept(this);
    }

    @Override
    public void visit(MultiplicativeExprNode node) {
        node.getLeftNode().accept(this);
        out.print(node.getOperator());
        node.getRightNode().accept(this);
    }

    @Override
    public void visit(AdditiveExprNode node) {
        node.getLeftNode().accept(this);
        out.print(node.getOperator());
        node.getRightNode().accept(this);
    }

    @Override
    public void visit(RelationalExprNode node) {
        node.getLeftNode().accept(this);
        out.print(node.getOperator());
        node.getRightNode().accept(this);
    }

    @Override
    public void visit(EqualityExprNode node) {
        node.getLeftNode().accept(this);
        out.print(node.getOperator());
        node.getRightNode().accept(this);
    }

    @Override
    public void visit(LogicalAndExprNode node) {
        node.getLeftNode().accept(this);
        out.print("&&");
        node.getRightNode().accept(this);
    }

    @Override
    public void visit(IntegerNode node) {
        out.print(node.getVal());
    }

    @Override
    public void visit(DoubleNode node) {
        out.print(node.getVal());
    }

    @Override
    public void visit(StringNode node) {
        out.print(node.getVal());
    }

    @Override
    public void visit(IdNode node) {
        out.print(node.getVal());
    }

    @Override
    public void visit(AstNode astNode) {
        super.visit(astNode);
    }
}
