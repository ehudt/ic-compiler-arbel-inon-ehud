package IC.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import IC.SemanticError;
import IC.AST.Formal;
import IC.AST.LocalVariable;
import IC.AST.Visitor;

public class MethodSymbolTable extends BlockSymbolTable {
	private boolean isStatic;
	private Map<String, VarSymbol> parameters = new HashMap<String, VarSymbol>();
	
	public MethodSymbolTable(SymbolTable parent, String name, boolean isStatic) {
		super(parent, name);
		this.isStatic = isStatic;
	}

	public boolean isStatic() {
		return isStatic;
	}
	
	@Override
	public Symbol lookup(String name){
		if(parameters.containsKey(name)){
			return parameters.get(name);
		}
		else if(locals.containsKey(name)){
			return locals.get(name);
		}
		else return parent.lookup(name);
	}
	
	@Override
	public Symbol staticLookup(String name) {
		if(parameters.containsKey(name)){
			return parameters.get(name);
		}
		else if(locals.containsKey(name)){
			return locals.get(name);
		}
		else return null;
	}
	
	
	
	public boolean hasLocalOrParameter(String name){
		return locals.containsKey(name) || 
			parameters.containsKey(name);
	}

	public List<VarSymbol> getParameterSymbols(){
		return new ArrayList<VarSymbol>(parameters.values());
	}
	
	public void insert(Formal formal) throws SemanticError{
		if(hasLocalOrParameter(formal.getName())){
			throw new SemanticError("Duplicate declaration of variable or parameter " + formal.getName());
		}
		parameters.put(formal.getName(), new VarSymbol(formal));
	}
	
	public void insert(LocalVariable local) throws SemanticError{
		if(hasLocalOrParameter(local.getName())){
			throw new SemanticError("Duplicate declaration of variable or parameter " + local.getName());
		}
		locals.put(local.getName(), new VarSymbol(local));
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return getName();
	}
	
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}