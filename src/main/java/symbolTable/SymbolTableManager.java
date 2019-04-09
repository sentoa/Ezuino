package symbolTable;

import ast.AstNode;

import java.util.Iterator;
import java.util.Stack;

public class SymbolTableManager {
    //private ArrayList<SymbolTable> symbolTables = new ArrayList<SymbolTable>();

    private int level = 0;
    private Stack<SymbolTable> symbolTableStack = new Stack();

    public void openScope(){
        SymbolTable symbolTable = new SymbolTable();
        level += 1;
        symbolTableStack.push(symbolTable);
    }

    public void closeScope() {
        level -= 1;
        symbolTableStack.pop();
    }

    public Symbol getSymbolMadeFromNode(AstNode node){
        Iterator<SymbolTable> symbolTableIterator = symbolTableStack.iterator();
        while(symbolTableIterator.hasNext()){
            SymbolTable symbolTable = symbolTableIterator.next();
            for(Symbol symbol : symbolTable.symbolList){
                if(symbol.getNode().equals(node)) {
                    return symbol;
                }
            }
        }
        System.out.println("returned null");
        return null;
    }

    public SymbolTable getLatestSymbolTable(){
        return symbolTableStack.peek();
    }

    public void addSymbolTable(SymbolTable symbolTable){
        symbolTableStack.add(symbolTable);
    }

    public int getSymbolTableSize(){
        return symbolTableStack.size();
    }

    public int getLevel()
    {
        return level;
    }



    public Stack<SymbolTable> getSymbolTableStack() {
        return symbolTableStack;
    }
}
