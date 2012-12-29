package IC.SymbolTable;

import IC.SemanticError;

public abstract class SymbolTable {
	protected SymbolTable parent;
	
	public SymbolTable(SymbolTable parent){
		this.parent = parent;
	}
	
	abstract public Symbol lookup(String name);

	public SymbolTable getParent() {
		return parent;
	}
	
}
