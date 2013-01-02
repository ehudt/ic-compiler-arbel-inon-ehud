package IC.AST;

/**
 * Abstract base class for data type AST nodes.
 * 
 * @author Tovi Almozlino
 */
public abstract class Type extends ASTNode {

	/**
	 * Number of array 'dimensions' in data type. For example, int[][] ->
	 * dimension = 2.
	 */
	protected int dimension = 0;
	protected int typeTableID;
	
	/**
	 * Constructs a new type node. Used by subclasses.
	 * 
	 * @param line
	 *            Line number of type declaration.
	 */
	protected Type(int line) {
		super(line);
	}

	public abstract String getName();
	public abstract Type clone(int newDimension);
	
	public int getDimension() {
		return dimension;
	}

	public void incrementDimension() {
		++dimension;
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append(this.getName());
		for(int i = 0; i < dimension; i++){
			str.append("[]");
		}
		return str.toString();
	}
	
	public int getTypeTableID()
	{
		return typeTableID;
	}
	public void setTypeTableID(int id)
	{
		typeTableID=id;
	}
	
	
}