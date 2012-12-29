package SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import IC.AST.ICClass;

public class GlobalSymbolTable extends SymbolTable {
	private Map<String, ClassSymbol> table = new HashMap<String, ClassSymbol>();
	private Map<String, ClassSymbolTable> childrenSymbolTables = new HashMap<String, ClassSymbolTable>();
	
	public Symbol lookup
	
	public void insertClass(ICClass new_class){
		table.put(new_class.getName(), new ClassSymbol(new_class));
	}
	
	public void insertChildSymbolTable(ClassSymbolTable childTable){
		childrenSymbolTables.put(childTable.getName(), childTable);
	}
	
}
