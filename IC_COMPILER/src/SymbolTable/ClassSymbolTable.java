package SymbolTable;

public class ClassSymbolTable extends SymbolTable {
	private String name;
	private boolean staticMethod;
	
	public String getName() {
		return name;
	}
	public boolean isStaticMethod() {
		return staticMethod;
	}
	
}
