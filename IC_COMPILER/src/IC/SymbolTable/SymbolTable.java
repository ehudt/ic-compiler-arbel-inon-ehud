package IC.SymbolTable;

import java.util.HashMap;
import java.util.Map;

public abstract class SymbolTable {
	protected SymbolTable parent;
	protected String name;
	protected Map<String, SymbolTable> childrenSymbolTables = new HashMap<String, SymbolTable>();
	
	public SymbolTable(SymbolTable parent){
		this(parent, "");
	}
	
	public SymbolTable(SymbolTable parent, String name){
		this.parent = parent;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	abstract public Symbol lookup(String name);

	public SymbolTable getParent() {
		return parent;
	}
	
	public void insertChildSymbolTable(SymbolTable childTable){
		childrenSymbolTables.put(childTable.getName(), childTable);
	}
}
