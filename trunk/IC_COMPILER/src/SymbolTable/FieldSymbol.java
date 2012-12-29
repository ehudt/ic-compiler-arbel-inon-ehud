package SymbolTable;

import IC.AST.Field;

public class FieldSymbol extends Symbol{
	
	private Field field;
	
	//constructor
	public FieldSymbol(Field f)
	{
		super(f.getName(),Kind.FIELD);
		this.field=f;
	}
	//getters
	
	public Field getField()
	{
		return field;
	}

}
