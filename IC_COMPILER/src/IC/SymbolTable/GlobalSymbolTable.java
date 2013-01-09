package IC.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import IC.SemanticError;
import IC.AST.ICClass;
import IC.AST.Visitor;

public class GlobalSymbolTable extends SymbolTable {
	private Map<String, ClassSymbol> table = new LinkedHashMap<String, ClassSymbol>();
	
	public List<ClassSymbol> getClassSymbols(){
		List<ClassSymbol> classSymbols = new ArrayList<ClassSymbol>(table.values());
		return classSymbols;
	}

	public GlobalSymbolTable(SymbolTable parent) {
		super(parent);
	}
	
	@Override
	public Symbol lookup(String name) {
		return getClassSymbol(name);
	}
	

	@Override
	public Symbol staticLookup(String name) {
		return lookup(name);
	}
	
	public void insert(ICClass new_class) throws SemanticError {
		if(table.containsKey(new_class.getName())){
			throw new SemanticError("Duplicate declaration of class " + new_class.getName());
		}
		table.put(new_class.getName(), new ClassSymbol(new_class));
		
	}
	
	public ClassSymbol getClassSymbol(String className){
		return table.get(className);
	}
	
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
	

}
