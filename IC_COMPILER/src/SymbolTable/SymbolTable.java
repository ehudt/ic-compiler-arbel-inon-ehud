package SymbolTable;

public abstract class SymbolTable {
	protected SymbolTable parent;
	protected int depth;
	
	
	
	public int getDepth() {
		return depth;
	}
	public SymbolTable getParent() {
		return parent;
	}
	
}
