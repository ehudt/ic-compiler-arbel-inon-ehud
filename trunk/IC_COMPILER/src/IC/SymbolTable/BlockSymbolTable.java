package IC.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import IC.SemanticError;
import IC.AST.LocalVariable;
import IC.AST.Visitor;


public class BlockSymbolTable extends SymbolTable {
	protected Map<String, VarSymbol> locals = new HashMap<String, VarSymbol>();
	
	public BlockSymbolTable(SymbolTable parent){
		super(parent);
	}
	
	public BlockSymbolTable(SymbolTable parent, String name) {
		super(parent, name);
	}
	
	public List<VarSymbol> getLocalSymbols(){
		return new ArrayList<VarSymbol>(locals.values());
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
	
	public String toString(){
		return "statement block in " + this.parent.getName();
	}
	
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
