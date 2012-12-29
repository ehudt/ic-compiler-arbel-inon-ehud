package IC.SymbolTable;

import java.util.HashMap;
import java.util.Map;

import IC.SemanticError;
import IC.AST.Formal;
import IC.AST.LocalVariable;

public class MethodSymbolTable extends BlockSymbolTable {
	private String name;
	private Map<String, VarSymbol> parameters = new HashMap<String, VarSymbol>();
	
	
	public MethodSymbolTable(SymbolTable parent, String name) {
		super(parent);
		this.setName(name);
	}
	
	public boolean hasLocalOrParameter(String name){
		return locals.containsKey(name) || 
			parameters.containsKey(name);
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


	private void setName(String name) {
		this.name = name;
	}

}