package IC.SymbolTable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import IC.AST.Visitor;

/**
 * Abstract superclass for representing a symbol table.
 * Each table has a name, a list of children symbol tables and a pointer
 * to the parent symbol table.
 * @author ehud
 *
 */

public abstract class SymbolTable {
	protected SymbolTable parent;
	protected String name;
	protected Map<String, SymbolTable> childrenSymbolTables = new LinkedHashMap<String, SymbolTable>();
	
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
	abstract public Symbol staticLookup(String name);

	public SymbolTable getParent() {
		return parent;
	}
	
	public void setParent(SymbolTable parent) {
		this.parent = parent;
	}
	
	public void insertChildSymbolTable(SymbolTable childTable){
		childrenSymbolTables.put(childTable.getName(), childTable);
	}
	
	public List<SymbolTable> getSymbolTables(){
		List<SymbolTable> list = new ArrayList<SymbolTable>(childrenSymbolTables.values());
		return list;
	}
	
	public SymbolTable getSymbolTable(String tableName){
		return childrenSymbolTables.get(tableName);
	}
	
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
	
	public String toString(){
		return name;
	}
	
	public SymbolTable removeChildTable(String tableName){
		if(childrenSymbolTables.containsKey(tableName)){
			return childrenSymbolTables.remove(tableName);
		} else return null;
	}

	public ClassSymbolTable getEnclosingClassTable() {
		return parent.getEnclosingClassTable();
	}

	public abstract String getCurrentMethodName();
}
