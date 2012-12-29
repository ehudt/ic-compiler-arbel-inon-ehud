package IC.SymbolTable;

import IC.AST.Formal;
import IC.AST.LocalVariable;
import IC.AST.Type;

public class VarSymbol extends Symbol{

	Type type;
	boolean isParam;
	
	//constructor
	
	public VarSymbol(LocalVariable lv)
	{
		super(lv.getName(),Kind.VARIABLE);
		this.isParam=false;
		this.type=lv.getType();
	}
	
	public VarSymbol(Formal f)
	{
		super(f.getName(),Kind.VARIABLE);
		this.isParam=true;
		this.type=f.getType();
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
