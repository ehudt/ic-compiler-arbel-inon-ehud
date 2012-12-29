package IC.SymbolTable;

import java.util.HashMap;
import java.util.Map;

import IC.SemanticError;
import IC.AST.LocalVariable;


public class BlockSymbolTable extends SymbolTable {
	protected Map<String, VarSymbol> locals = new HashMap<String, VarSymbol>();
	
	public BlockSymbolTable(SymbolTable parent){
		super(parent);
	}
	
	public void insert(LocalVariable local) throws SemanticError{
		if(locals.containsKey(local.getName())){
			throw new SemanticError("Duplicate declaration of local variable " + local.getName());
		}
		locals.put(local.getName(), new VarSymbol(local));
	}

	@Override
	public Symbol lookup(String name) {
		if(locals.containsKey(name)){
			return locals.get(name);
		}
		else return parent.lookup(name);
	}
}
