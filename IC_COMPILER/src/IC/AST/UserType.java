package IC.AST;

/**
 * User-defined data type AST node.
 * 
 * @author Tovi Almozlino
 */
public class UserType extends Type {

	private String name;

	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Constructs a new user-defined data type node.
	 * 
	 * @param line
	 *            Line number of type declaration.
	 * @param name
	 *            Name of data type.
	 */
	public UserType(int line, String name) {
		super(line);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public Type clone(int newDimension) {
		Type cloned = new UserType(getLine(), name);
		for(int i = 0; i < newDimension; i++){
			cloned.incrementDimension();
		}
		return cloned;
	}

	@Override
	public boolean subTypeOf(Type otherType) {
		// TODO Auto-generated method stub
		
		return otherType == this;
	}

}
