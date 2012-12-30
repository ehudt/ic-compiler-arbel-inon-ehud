package IC.SymbolTable;

import IC.AST.Method;
import IC.Types.MethodType;

public class MethodSymbol extends Symbol{

	private MethodType type;
	private boolean isStatic;
	//constructor
	public MethodSymbol(Method met)
	{
		super(met.getName(),Kind.METHOD);
		this.type=new MethodType(met);
		this.isStatic=false;
	}
	
	public MethodSymbol(Method met,boolean isStatic)
	{
		super(met.getName(),Kind.METHOD);
		this.type=new MethodType(met);
		this.isStatic=isStatic;
	}
	//getters
	public MethodType getMetType()
	{
		return this.type;
	}
	public boolean isStatic()
	{
		return this.isStatic;
	}
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append(isStatic() ? "Static" : "Virtual");
		str.append(" method: " );
		str.append(getID());
		str.append(getMetType());
		return str.toString();
	}
}
