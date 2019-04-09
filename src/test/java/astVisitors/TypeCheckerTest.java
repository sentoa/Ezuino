package astVisitors;

import ast.AstNode;
import ast.DclNode;
import ast.Type;
import astvisitors.SymbolTableVisitor;
import astvisitors.TypeChecker;
import cstvisitors.BuildAstVisitor;
import generated.EzuinoLexer;
import generated.EzuinoParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TypeCheckerTest {
    BuildAstVisitor buildAstVisitor = new BuildAstVisitor();
    SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();
    TypeChecker typeChecker = new TypeChecker();




    private EzuinoParser createParser(String testString) throws IOException {
        CharStream stream = CharStreams.fromString(testString);
        EzuinoLexer lexer = new EzuinoLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        EzuinoParser parser = new EzuinoParser(tokens);
        return parser;
    }
}