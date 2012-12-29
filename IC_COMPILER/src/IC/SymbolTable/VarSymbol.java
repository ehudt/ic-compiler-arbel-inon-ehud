package IC.SymbolTable;

import IC.AST.LocalVariable;
import IC.AST.Type;

public class VarSymbol extends Symbol{

	Type type;
	boolean isParam;
	
	//constructor
	
	public VarSymbol(LocalVariable lv, boolean param)
	{
		super(lv.getName(),Kind.VARIABLE);
		this.isParam=param;
		this.type=lv.getType();
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
}
