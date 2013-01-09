package IC.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import IC.SemanticError;
import IC.AST.LocalVariable;
import IC.AST.Method;
import IC.AST.Visitor;


public class BlockSymbolTable extends SymbolTable {
	private static Integer enumerateIds = 0;
	protected Map<String, VarSymbol> locals = new LinkedHashMap<String, VarSymbol>();
	
	public BlockSymbolTable(SymbolTable parent){
		this(parent, "block" + enumerateIds.toString());
		enumerateIds++;
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
	
	@Override
	public Symbol staticLookup(String name) {
		return locals.get(name);
	}
	
	public String toString(){
		return "statement block in " + this.parent.toString();
	}
	
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public String getCurrentMethodName() {
		return parent.getCurrentMethodName();
	}

}
