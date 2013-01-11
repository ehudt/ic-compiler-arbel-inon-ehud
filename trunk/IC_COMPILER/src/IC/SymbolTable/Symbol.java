package IC.SymbolTable;

/**
 * 
 * An abstract class that defines Symbol
 * Its fields are: id, line and kind.
 */
public abstract class Symbol {
	protected String id;
	protected int line;
	protected Kind kind;
	
	/** 
	 * Constructor for Symbol 
	 **/
	public Symbol(String id,Kind kind,int line)
	{
		this.id=id;
		this.kind=kind;
		this.line=line;
	}
	
	/**
	 * getters and setters 
	 */
	public String getID()
	{
		return this.id;
	}
	
	public Kind getKind()
	{
		return this.kind;
	}
	
	public int getLine()
	{
		return this.line;
	}
	public void setLine(int line)
	{
		this.line=line;
	}
}
