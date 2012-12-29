package SymbolTable;

import IC.SemanticError;
import IC.AST.ICClass;

public class ClassSymbol extends Symbol {

	private ICClass pclass;
	
	public ClassSymbol(ICClass cl) throws SemanticError
	{
		super(cl.getName(),Kind.CLASS);
		this.pclass =cl;
	}
	//getters
	public ICClass getPClass()
	{
		return pclass;
	}
}
