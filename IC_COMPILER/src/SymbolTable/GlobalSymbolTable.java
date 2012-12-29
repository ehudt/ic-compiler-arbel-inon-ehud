package SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GlobalSymbolTable extends SymbolTable {
	private Map<String, ClassSymbol> classes = new HashMap<String, ClassSymbol>();
	private List<ClassSymbolTable> childrenSymbolTables = new ArrayList<ClassSymbolTable>();
	
	public GlobalSymbolTable(){
		
	}
}
