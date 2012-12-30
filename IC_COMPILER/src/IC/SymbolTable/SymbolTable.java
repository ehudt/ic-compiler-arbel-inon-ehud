package IC.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import IC.AST.Visitor;

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
	
	public List<SymbolTable> getSymbolTables(){
		List<SymbolTable> list = new ArrayList<SymbolTable>(childrenSymbolTables.values());
		return list;
	}
	
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
	
	public String toString(){
		return name;
	}
}
