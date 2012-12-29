package IC.SymbolTable;

import java.util.HashMap;
import java.util.Map;

import IC.SemanticError;
import IC.AST.ICClass;

public class GlobalSymbolTable extends SymbolTable {
	private Map<String, ClassSymbol> table = new HashMap<String, ClassSymbol>();
	private Map<String, ClassSymbolTable> childrenSymbolTables = new HashMap<String, ClassSymbolTable>();

	public GlobalSymbolTable(SymbolTable parent) {
		super(parent);
	}
	
	@Override
	public Symbol lookup(String name) {
		return getClassSymbol(name);
	}
	
	public void insert(ICClass new_class) throws SemanticError {
		if(table.containsKey(new_class.getName())){
			throw new SemanticError("Duplicate declaration of class " + new_class.getName());
		}
		table.put(new_class.getName(), new ClassSymbol(new_class));
	}
	
	public void insertChildSymbolTable(ClassSymbolTable childTable){
		childrenSymbolTables.put(childTable.getName(), childTable);
	}
	
	public ClassSymbol getClassSymbol(String className){
		return table.get(className);
	}
}
