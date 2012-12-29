package IC.SymbolTable;

import java.util.HashMap;
import java.util.Map;

import IC.SemanticError;
import IC.AST.Field;

public class ClassSymbolTable extends SymbolTable {
	private String name;
	private boolean isStatic;
	private Map<String, FieldSymbol> classFieldTable = new HashMap<String, FieldSymbol>();
	private Map<String, MethodSymbol> classMethodTable = new HashMap<String, MethodSymbol>();
	private GlobalSymbolTable parentTable;
	
	public ClassSymbolTable(GlobalSymbolTable parent, String name, boolean isStatic){
		parentTable = parent;
		this.name = name;
		this.isStatic = isStatic;
	}
	
	public String getName() {
		return name;
	}
	public boolean isStatic() {
		return isStatic;
	}
	
	public void insert(Field newField) throws SemanticError {
		if(hasFieldOrMethod(newField.getName())){
			throw new SemanticError("Class " + name + " already has a method or a field with name " + newField.getName());
		}
		classFieldTable.put(newField.getName(), new FieldSymbol(newField));
	}
	
	private boolean hasFieldOrMethod(String name) {
		return classFieldTable.containsKey(name) || classMethodTable.containsKey(name);
	}
	@Override
	public Symbol lookup(String name) {
		if(classFieldTable.containsKey(name)){
			return classFieldTable.get(name);
		} 
		else if(classMethodTable.containsKey(name)){
			return classMethodTable.get(name);
		}
		else return parentTable.lookup(name);
	}
	
}
