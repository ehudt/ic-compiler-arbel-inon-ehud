package IC.AST;

import IC.SemanticError;
import IC.SymbolTable.ClassSymbolTable;
import IC.SymbolTable.SymbolTable;

/**
 * Abstract AST node base class.
 * 
 * @author Tovi Almozlino
 */
public abstract class ASTNode {

	private int line;
	int column = -1;
	
	private SymbolTable enclosingScope;

	/**
	 * Double dispatch method, to allow a visitor to visit a specific subclass.
	 * 
	 * @param visitor
	 *            The visitor.
	 * @return A value propagated by the visitor.
	 * @throws SemanticError 
	 */
	public abstract Object accept(Visitor visitor);

	/**
	 * Constructs an AST node corresponding to a line number in the original
	 * code. Used by subclasses.
	 * 
	 * @param line
	 *            The line number.
	 */
	protected ASTNode(int line) {
		this.line = line;
	}

	public int getLine() {
		return line;
	}
	
	public SymbolTable getEnclosingScope() {
		return enclosingScope;
	}
	
	public ClassSymbolTable getEnclosingClassTable() {
		return getEnclosingScope().getEnclosingClassTable();
	}

	public void setEnclosingScope(SymbolTable enclosingScope) {
		this.enclosingScope = enclosingScope;
	}
	
	public int getColumn() {
		return column;
	}


}
