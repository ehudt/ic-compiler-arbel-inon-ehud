package IC.SymbolTable;

import IC.AST.Field;
import IC.AST.Type;

public class FieldSymbol extends Symbol{
	
	private Field field;
	Type type;
	
	//constructor
	public FieldSymbol(Field f)
	{
		super(f.getName(),Kind.FIELD);
		this.field=f;
		this.type=f.getType();
	}
	//getters
	
	public Field getField()
	{
		return field;
	}
	
	public Type getType()
	{
		return this.type;
	}

}
