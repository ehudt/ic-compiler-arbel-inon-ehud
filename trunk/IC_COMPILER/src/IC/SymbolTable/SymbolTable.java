package IC.SymbolTable;

import IC.SemanticError;

public abstract class SymbolTable {
	protected SymbolTable parent;
	protected int depth;
	
	abstract public Symbol lookup(String name);
		
	public int getDepth() {
		return depth;
	}
	public SymbolTable getParent() {
		return parent;
	}
	
}
