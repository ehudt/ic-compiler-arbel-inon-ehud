package IC.SymbolTable;

import IC.AST.Field;
import IC.AST.Type;
import IC.Types.TypeTable;

public class FieldSymbol extends Symbol{
	
	private Field field;
	Type type;
	
	//constructor
	public FieldSymbol(Field f)
	{
		super(f.getName(),Kind.FIELD);
		this.field=f;
		this.type= TypeTable.getType(f.getType());
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
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("Field: ");
		str.append(getType());
		str.append(" ");
		str.append(getID());
		return str.toString();
	}
}
