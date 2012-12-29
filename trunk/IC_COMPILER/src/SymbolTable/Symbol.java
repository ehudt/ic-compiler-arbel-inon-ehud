package SymbolTable;

public abstract class Symbol {
	protected String id;
	protected Type type;
	protected int line;
	protected Kind kind;
	
	public Symbol(String id,Kind kind)
	{
		this.id=id;
		this.kind=kind;
	}
	
//getters
	public String getID()
	{
		return this.id;
	}
	
	public Kind getKind()
	{
		return this.kind;
	}
	
	public Type getType()
	{
		return this.type;
	}
}
