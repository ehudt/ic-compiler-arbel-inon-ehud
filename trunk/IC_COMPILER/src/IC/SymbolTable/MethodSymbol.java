package IC.SymbolTable;

import IC.AST.Method;
import IC.Types.MethodType;

public class MethodSymbol extends Symbol{

	private MethodType type;
	
	//constructor
	public MethodSymbol(Method met)
	{
		super(met.getName(),Kind.METHOD);
		this.type=new MethodType(met);
	}
	
	//getters
	public MethodType getMetType()
	{
		return this.type;
	}
}
