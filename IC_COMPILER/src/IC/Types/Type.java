package IC.Types;

public abstract class Type {
	protected String name;
	protected int dimension;
	
	public Type(String name){
		this(name, 0);
	}
	
	public Type(String name, int dimension){
		this.name = name;
		this.dimension = dimension;
	}
}
