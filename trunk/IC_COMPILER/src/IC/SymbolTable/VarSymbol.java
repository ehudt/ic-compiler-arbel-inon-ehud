package IC.SymbolTable;

import IC.AST.Formal;
import IC.AST.LocalVariable;
import IC.AST.Type;
import IC.Types.TypeTable;

public class VarSymbol extends Symbol{

	Type type;
	boolean isParam;
	
	//constructor
	
	public VarSymbol(LocalVariable lv)
	{
		super(lv.getName(),Kind.VARIABLE, lv.getLine());
		this.isParam=false;
		this.type = TypeTable.getType(lv.getType());
	}
	
	public VarSymbol(Formal f)
	{
		super(f.getName(),Kind.VARIABLE, f.getLine());
		this.isParam=true;
		this.type = TypeTable.getType(f.getType());
	}
	//geters
	
	public boolean isParam()
	{
		return this.isParam;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append(isParam() ? "Parameter: " : "Local variable: ");
		str.append(getType());
		str.append(" ");
		str.append(getID());
		return str.toString();
	}
}
