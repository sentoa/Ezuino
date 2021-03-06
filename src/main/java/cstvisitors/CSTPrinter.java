package cstvisitors;

import org.antlr.v4.runtime.InterpreterRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import generated.EzuinoBaseVisitor;

public class CSTPrinter extends EzuinoBaseVisitor<Void> {

	@Override
	public Void visit(ParseTree tree) {
		printTree(tree, "", true);
		return null;
	}

	public void printTree(ParseTree tree, String indent, boolean last) {
		String name = "";
		if (tree instanceof InterpreterRuleContext) {
			InterpreterRuleContext t = (InterpreterRuleContext) tree;
			name = t.getRuleContext().toString();

		} else if (tree instanceof TerminalNodeImpl) {
			TerminalNodeImpl t = (TerminalNodeImpl) tree;
			name = t.symbol.getText();
		} else {
			name = tree.getClass().getSimpleName();
		}

		System.out.println(indent + "+- " + name);
		indent += last ? "   " : "|   ";

		for (int i = 0; i < tree.getChildCount(); i++) {
			printTree(tree.getChild(i), indent, i == tree.getChildCount() - 1);
		}
	}

}
