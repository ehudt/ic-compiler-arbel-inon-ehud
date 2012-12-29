package SymbolTable;

public class VarSymbol extends Symbol{

	boolean isParam;
	
	//constructor
	
	public VarSymbol(String varname,boolean param)
	{
		super(varname,Kind.VARIABLE);
		isParam=param;
	}
	
	//geters
	
	public boolean isParam()
	{
		return this.isParam;
	}
}
