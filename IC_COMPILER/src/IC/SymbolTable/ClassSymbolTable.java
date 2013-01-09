package IC.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import IC.SemanticError;
import IC.AST.Field;
import IC.AST.Method;
import IC.AST.Visitor;

public class ClassSymbolTable extends SymbolTable {

	private Map<String, FieldSymbol> classFieldTable = new LinkedHashMap<String, FieldSymbol>();
	private Map<String, MethodSymbol> classMethodTable = new LinkedHashMap<String, MethodSymbol>();
	
	public List<MethodSymbol> getMethodSymbols(){
		return new ArrayList<MethodSymbol>(classMethodTable.values());
	}
	
	public List<FieldSymbol> getFieldSymbols(){
		return new ArrayList<FieldSymbol>(classFieldTable.values());
	}
	
	public ClassSymbolTable(SymbolTable parent, String name){
		super(parent, name);
	}
	
	public void insert(Field newField) throws SemanticError {
		if(hasFieldOrMethod(newField.getName())){
			throw new SemanticError("Class " + name + " already has a method or a field with name " + newField.getName());
		}
		classFieldTable.put(newField.getName(), new FieldSymbol(newField));
	}
	
	public void insert(Method newMethod) throws SemanticError {
		if(hasFieldOrMethod(newMethod.getName())){
			throw new SemanticError("Class " + name + " already has a method or a field with name " + newMethod.getName());
		}
		classMethodTable.put(newMethod.getName(), new MethodSymbol(newMethod));
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
		else return parent.lookup(name);
	}

	@Override
	public Symbol staticLookup(String name) {
		if(classFieldTable.containsKey(name)){
			return classFieldTable.get(name);
		} 
		else if(classMethodTable.containsKey(name)){
			return classMethodTable.get(name);
		}
		else return null;
	}
	
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
	@Override
	public ClassSymbolTable getEnclosingClassTable() {
		return this;
	}
}
