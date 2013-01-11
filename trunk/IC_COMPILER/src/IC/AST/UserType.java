package IC.AST;

import IC.SemanticError;
import IC.Types.TypeTable;

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
	
	/**
	 * This method allows to clone an object with another primitive type but with the requested dimension
	 */
	@Override
	public Type clone(int newDimension) {
		Type cloned = new UserType(getLine(), name);
		for(int i = 0; i < newDimension; i++){
			cloned.incrementDimension();
		}
		return cloned;
	}
	/**
	 * A method to check if this type is a subType another Type. 
	 * i.e if it both types are arrays then we only need to check if the references are equal.
	 * otherwise if they are usertypes with dimension 0 we need to recursively check A<=B and B<=C then A<=C
	 */
	@Override
	public boolean subTypeOf(Type otherType) {
		boolean returnVal = false;
		if (this.getDimension() > 0 || otherType.getDimension() > 0) {
			return this == otherType;
		}
		if (otherType instanceof UserType) {
			try {
				returnVal = TypeTable.getUserTypeByName(this.getName()).subTypeOf(TypeTable.getUserTypeByName(otherType.getName()));
			} catch (SemanticError e) {	}
		}
		return returnVal;
	}

	@Override
	public boolean equals(Object other) {
		return 	(other instanceof UserType) && 
				this.name == ((UserType)other).name &&
				this.dimension == ((UserType)other).dimension;
	}

}
